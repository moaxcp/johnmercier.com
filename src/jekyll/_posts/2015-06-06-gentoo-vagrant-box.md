---
title: Gentoo Vagrant Box
layout: post
comments: true
---
Today I am working on a vagrant base box using gentoo and VirtualBox. I created a gentoo stage-3 vm in VirtualBox. But this is not enough to run gentoo in VirtualBox let alone as a vagrant box. There are a number of packages that need to be installed to make a stage-3 useful for virtual box and vagrant.

* virtualbox-guest-additions
* acpi - for poweroff
* sudo - to run commands without a password

##virtualbox-guest-additions

        emerge --ask app-emulation/virtualbox-guest-additions

Keep in mind this package installs dbus. I recommend setting up dbus using [this guide](https://wiki.gentoo.org/wiki/D-Bus).

##acpi
This is a service I did not have running on my own system. Follow [this guide](https://wiki.gentoo.org/wiki/ACPI) to get it setup. This will allow gentoo to shutdown when performing acpi shutdown in the Machine menu.

##sudo
This is required to allow the vagrant user to run commands without a password. It is easy to install and configure for vagrant. [This](https://wiki.gentoo.org/wiki/Sudo) is the gentoo guide for sudo.

Now I am following [this guide](http://docs.vagrantup.com/v2/boxes/base.html) to setup the base box.

Another option I would like to try is [this](https://github.com/d11wtq/gentoo-packer) setup.
