---
layout: post
title: Install bluetooth mouse
date: 2014-10-10 00:51:00.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
I am following the wiki article at: http://wiki.gentoo.org/wiki/Bluetooth

After installing bluez, the gentoo wiki says to use hcitool to list the bluetooth devices. On my computer there were no devices. The wiki suggests using rfkill to unblock the device but I found that I needed to use "hciconfig hci0 up" in order for the device to be listed. rfkill was helpful in listing the bluetooth device as hci0 though.

The wiki uses simple-agent which will only be available from the bluez package if the test-programs use flag is enabled for that package. I added "net-wireless/bluez test-programs" to /etc/portage/package.use and re-emerged the package but it did not add simple-agent.

I will have to continue this tomorrow.
