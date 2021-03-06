---
layout: post
title: Create an Installer for Gentoo
date: 2014-05-18 18:20:00.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
As with many linux distributions gentoo provides Live CDs which can be used to try out gentoo and install it. Gentoo
offers two Live CDs one is a complete is with a GUI and the other is a minimal-install CD with only the tools needed to
install Gentoo. These instructions will create gentoo's minimal install cd.

There are three steps to creating a Live CD for most Linux distributions: download the files, check the integrity of
the files, and create and install medium. This example will create a usb stick with gentoo's minimal install CD on it.

# Download the Files
First the iso file and digest file must be downloaded. This can be accomplished by going to gentoo.org, clicking the
get Gentoo link, and following the links to download the minimal install iso along with the digest file.

# Check File Integrity

To check the integirty of the iso file first open the DIGEST file in a text editor and remove everything but the
hashcode for the method you will be using to check the iso file. This is an example of checking the sha512 hash.

![editdigestfile](/images/2-editdigestfile.png)

Next open a terminal and check the integrity of the iso file.

![checkdigest](/images/3-checkdigest.png)

If the file is OK then it is valid and can be used for the installation.

# Create Install Medium

The next step is to either burn the iso file to a cd or write it to a usb drive. Burning a CD can be performed with any
program that can burn an image. Here is an example of burning the image using brasero:

![burncd](/images/4-burncd.png)

This is an example of writing the iso to a usb drive using usb-imagewriter:

![write image usb](/images/6writeimageusb.png)

# Boot the System

Once the installer is ready the next step is to boot into it.

![screen](/images/2014-04-0619-28-30.jpg)

Note: There is another option for building Gentoo on a usb drive manually at
[https://wiki.gentoo.org/wiki/LiveUSB/HOWTO](https://wiki.gentoo.org/wiki/LiveUSB/HOWTO). There are two advantages with
this method. First all the space on the usbdrive can be used and second the extra space can be used to install
applications and change the configuration.
