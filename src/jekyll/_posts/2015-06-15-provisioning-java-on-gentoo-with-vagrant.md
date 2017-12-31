---
title: provisioning java on gentoo-with-vagrant
layout: post
comments: true
---
I wrote a [small script](https://github.com/moaxcp/fileServices/blob/master/Vagrantfile) that will provision [oracle-jdk-bin-1.8.0](https://packages.gentoo.org/package/dev-java/oracle-jdk-bin) on my [gentoo64](https://atlas.hashicorp.com/moaxcp/boxes/gentoo64/versions/0.0.2) box. The script can be called multiple times through `vagrant reload --provision` and it will only install the jdk once. Oracle's jdk has some restrictions. On gentoo the license needs to be accepted and a file needs to be manually downloaded and added to `/usr/portage/distfiles`. My Vagrantfile adds a share to `/usr/portage/distfiles` on the guest machine in `/distfiles`. The shell script will copy the file if it doesn't exist, accept the license, and install the package. The only requirement is `jdk-8u45-linux-x64.tar.gz` needs to be located at '/usr/portage/distfiles'. This can be changed in your own project by editing the share in your Vagrantfile.
