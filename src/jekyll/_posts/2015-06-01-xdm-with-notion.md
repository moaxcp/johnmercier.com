---
title: xdm with notion
layout: post
comments: true
---
Today I am attempting to switch to xdm as my display manager. I currently use LXDE but I am integrating parts of it with the notion windows manager. LXDM is not needed so I would like to replace it with the original xdm.

To start install xdm

        emerge -av x11-apps/xdm

add xdm as the display manager

        nano /etc/conf.d/xdm

set DISPLAYMANAGER to xdm

The [NVIDIA Driver with Optimus Laptops](https://wiki.gentoo.org/wiki/NVIDIA_Driver_with_Optimus_Laptops) guide has some pointers on how to setup xdm correctly. The driver requres that some xrandr commands are executed on xinit. The instructions are easy to follow.

Now that this is complete I am exiting to the console and running the xdm command.

It turns out that xdm is not loading my .xinitrc file.

From my research on how xdm works and gentoo I learned a few things and this is what I did to get things running.

* copy /etc/X11/xinit/xinitrc to ~/.xinitrc
* make ~/.xinitrc executable
* create ~/.xsession
* add export XSESSION=notion to the top of the file
* add any other commands you need to execute
* add ~/.xinitrc as the final command

The gentoo X system is setup in such a way that /etc/X11/xinitrc does everything most people want in a xinitrc file. The only problem is the file is not executable. By copying it to my home directory I am able to resuse it.

Gentoo's xinitrc looks for a XSESSION varaible and executes that session. If the session is not found twm is started.

To get the default session to work x11-apps/xsm needs to be installed along with xterm an twm.

xdm only executes a .xsession file in the user's home directory using the authenticated user's account.

With this setup I am setting the XSESSION variable to run notion. The /etc/X11/Sessions/notion file points to /usr/bin/notion rather than where I installed it /usr/local/bin/notion.

So far, I think this is one thing I can check of my list for building my system.
