---
title: gentoo vagrant box
layout: post
comments: true
---
I finally got my gentoo vagrant box up and running. There was a [bug](https://github.com/mitchellh/vagrant/issues/5070#issuecomment-75738232) in vagrant which I was able to find a workaround. I also had to make sure that the vboxsf module is loaded on boot. Otherwise, I got and error:

        Failed to mount folders in Linux guest. This is usually because
        the "vboxsf" file system is not available. Please verify that
        the guest additions are properly installed in the guest and
        can work properly. The command attempted was:

        mount -t vboxsf -o uid=`id -u vagrant`,gid=`getent group vagrant | cut -d: -f3` vagrant /vagrant
        mount -t vboxsf -o uid=`id -u vagrant`,gid=`id -g vagrant` vagrant /vagrant

        The error output from the last command was:

        /sbin/mount.vboxsf: mounting failed with the error: No such device

To fix this add

        modules="vboxsf"

to the file /etc/conf.d/modules.

I was able to package the box after following another [fix](https://github.com/mitchellh/vagrant/issues/5186#issuecomment-77355450) but I won't be able to release the package until the above problems are fixed in the original vm.
