---
title: upgrading the kernel
layout: post
comments: true
---
I'm following [this](https://wiki.gentoo.org/wiki/Kernel/Upgrade) guide to upgrade my gentoo kernel.

These are just some notes:

backup current .config

        cp /usr/src/linux/.config ~/kernel-config-`uname -r`

change symlink for /usr/src/linux to new sources

        eselect kernel list
        eselect kernel set ${from list}

Interestingly, my system is running 3.17.8-gentoo-r1 and the latest installed sources is linux-3.18.12-gentoo but only the latest is displayed in the kernel listing.

        john-gentoo src # ls -l
        total 8
        lrwxrwxrwx  1 root root   22 Feb  9 04:39 linux -> linux-3.17.8-gentoo-r1
        drwxr-xr-x 21 root root 4096 May  2 21:40 linux-3.17.8-gentoo-r1
        drwxr-xr-x 24 root root 4096 May 20 20:31 linux-3.18.12-gentoo


        john-gentoo src # eselect kernel list
        Available kernel symlink targets:
          [1]   linux-3.18.12-gentoo

I am selecting the available kernel.

        john-gentoo src # eselect kernel set 1
        john-gentoo src # eselect kernel list                                        
        Available kernel symlink targets:
          [1]   linux-3.18.12-gentoo *
 
There is a make command to convert the old .config for the new kernel.

        make silentoldconfig

I should have used make olddefconfig because I only want defaults for new items.

Now to building the kernel. I have a sript that will build the kernel for my computer.

        genkernel --menuconfig --lvm --mdadm --makeopts="-j9 -l8" all

This will enable lvm, mdadm and let me look at the menu. The make opts will build the kernel faster and use all of the cores in my processor.

Next I need to upgrade grub. The grub settings are located in /etc/default/grub. I needed to change the settings for my kernel and they still should be valid.

        GRUB_CMDLINE_LINUX="domdadm dolvm real_root=/dev/vg0/home resume=/dev/vg0/swap"
        GRUB_DISABLE_LINUX_UUID=true

Rebuild the grub config for the new kernel.

        grub2-mkconfig -o /boot/grub/grub.cfg

Now to restart.

After restarting X would not start. I needed to setup my nvidia drivers again following [this](https://wiki.gentoo.org/wiki/NVIDIA_Driver_with_Optimus_Laptops) guide. I'm not sure how things worked before because I didn't have a xorg.conf and I definately didn't use an edid firmware for the monitor. Now I am glad to say my kernel is current and X starts.
