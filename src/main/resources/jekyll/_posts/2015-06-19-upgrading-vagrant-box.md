---
title: upgrading vagrant box
layout: post
comments: true
---
I'm upgrading [moaxcp/gentoo64](https://vagrantcloud.com/moaxcp/boxes/gentoo64) and these are my notes.

After an `emerge --sync` and `emerge -avuND @world` I found that gentoo release a new kernel (4.0.5). I followed 
[this](https://wiki.gentoo.org/wiki/Kernel/Upgrade) to upgrade making sure to use --lvm. I added a new script 
/root/buildkernel.sh which will build the kernel with the correct options. I made sure to remove the old kernel 
to save space.

There is a script cleanup_diskspace.sh which will remove some files and make the .box file smaller when it is 
built.

When I originally created the vm I used lvm and made the root partition 7GB. Now is the time to resize it to 
something more useful. I changed the disk size to 120GB using `VBoxManage modifyhd gentoo-vagrant.vdi --resize 
122880`

LVM was not installed on the system even though the modules are in the kernel. When installing lvm there was an 
error related to [this 
thread](https://forums.gentoo.org/viewtopic-t-986982.html?sid=6a911ac3752245becdc98096c9dd8081). The advice on 
the thread is to remove parallel compilation by setting MAKEOPTS="-j1". After this change boost still did not 
compile. I added the ~amd64 keyword for dev-libs/boost to pick up a newer package. Even then I still had errors.

The problem with parallel compilation is the amount of memory used. I increased the memory and got a different
error. This time the error is No space left on device.

        df -h

        Filesystem            Size  Used Avail Use% Mounted on
        udev                   10M  4.0K   10M   1% /dev
        /dev/mapper/vg0-root  6.8G  4.5G  2.0G  70% /
        tmpfs                 201M  388K  201M   1% /run
        shm                  1005M     0 1005M   0% /dev/shm
        cgroup_root            10M     0   10M   0% /sys/fs/cgroup

This shows root as only 70% full but checking the inode count I get

        df -i

        Filesystem           Inodes  IUsed  IFree IUse% Mounted on
        udev                 256513    409 256104    1% /dev
        /dev/mapper/vg0-root 458752 458745      7  100% /
        tmpfs                257103    355 256748    1% /run
        shm                  257103      1 257102    1% /dev/shm
        cgroup_root          257103      4 257099    1% /sys/fs/cgroup

This makes me want to rethink the entire gentoo64 build. I shouldn't have to use lvm and I'm not sure why I
installed it. I think I'm going to rebuild the whole system without lvm and make a larger hard drive.
