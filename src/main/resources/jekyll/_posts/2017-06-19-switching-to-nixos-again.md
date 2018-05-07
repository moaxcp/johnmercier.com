---
title: switching to nixos
layout: post
comments: true
---

I have decided to switch from gentoo to nixos. I tried nixos over a year ago but had a lot of troubles. This was
probably because I used the unstable release and couldn't really get gradle stuff to work. This time I will only use
the stable release and try to import newer stuff from unstable if I decided I need it. Hopefully I will not have as
many problems as I did with the unstable release.

First, I downloaded the latest minimal install cd.

Second, I backed up my stuff.

    rsync -r /home/john /mnt/backup


The biggest worry I had with this install was getting the partitions and boot setup. A few years ago I had to replace
my motherboard because installing the uefi boot loader completely messed up the whole machine. I also considered using
zfs but I'm not sure I want to dive into that technology yet. So I decided to reuse the setup I used for gentoo. This
helped save time and gave me less to worry about during the whole process.

The partition setup is somewhat unique and I was able to piece it together by reading about setting up mdadm,
luks, and lvm with gentoo and arch. The partitions look something like this:

```
partitions:

/dev/sda
|----/dev/sda1----|------------/dev/sda2------------|

/dev/sdb
|----/dev/sdb1----|------------/dev/sdb2------------|
```

The the first partitions of each drive are setup by mdadm as a raid1. The second partitions are setup by mdadm as a
raid0. This is the logical view of the raid arrays.

```
raid arrays:

|----/dev/md/0----|------------/dev/md/1------------|
```

/dev/md/0 is setup with ext4 and it becomes /boot in the filesystem. /dev/md/1 is slightly more complicated. It is a
luks encrypted drive which contains lvm's volume group 0. vg0 contains swap and '/'.

I know there is a lot more risk with raid0 but I also have a third drive which is used for backups using obnam. This
mitigates any real risks of losing data.

```
backup partition:

/dev/sdc
|------------/dev/sdc1------------|
```

I read a few posts on reddit where the users coppied the current '/' to a different directory before starting the
install. I decided to try this out since it would make recovering my home directory very easy. The gentoo root was
moved to /gentoo-root. This ended up helping a lot when I needed to setup the mdadm.conf. I just copied most of the
content from the gentoo-root.

Getting nixos to use the gentoo setup went smoothly. Here is the grub setup.

```nix
boot.loader.grub.enable = true;
boot.loader.grub.version = 2;
boot.loader.grub.devices = [ "/dev/sda" "/dev/sdb" ];
```

Grub needs to be installed on both drives (/dev/sda and /dev/sdb) so that if either goes down it will still boot (not
sure this would really work given the raid0). Nixos can open the luks encrypted drive by setting up
`boot.initrd.luks.devices`.

```nix
boot.initrd.luks.devices = [
  {
    name = "root";
    device = "/dev/md/1";
    preLVM = true;
  }
];
```

This sets up initrd to open the luks device before searching for lvm volumes. If this doesn't happen before the lvm
scan the boot will fail to find swap and '/'. The concept for this setup is 'lvm on luks'.

Remember how my machine has two raid arrays? An interesting thing happens when you have two raid arrays. On each boot
which one gets named /dev/md126 is non-deterministic. As a corollary which one gets named /dev/md127 is
non-deterministic. It is impossible to determine which array contains the luks device ahead of time using these names.
I found that sometimes the boot would ask for the passphrase and sometimes it would not be able to start at all. The
solution is to setup named devices in mdadm.conf and use the correct name in the luks config above. Luckily nixos
supports mdadm.conf within initrd.

```nix
boot.initrd.mdadmConf = ''
  DEVICE /dev/sda1 /dev/sdb1
  DEVICE /dev/sda2 /dev/sdb2
  ARRAY /dev/md/0 metadata=1.2 UUID=2a7ffaec:fd80f34a:48cebc26:5caf521c name=g1:0
  ARRAY /dev/md/1 metadata=1.2 UUID=70e06d78:4e14590a:a4e13279:9e6e387f name=g1:1
'';
```

This added the names /dev/md/0 and /dev/md/1 which should be used at boot instead of /dev/md126 and /dev/md127. This
solved all the problems with initrd finding the luks device at boot.

Getting this right was a real pain. I'm not going to say nixos makes it easier because I learned a lot going through
this with gentoo. I believe the gentoo experience made setting this up in nixos easier.