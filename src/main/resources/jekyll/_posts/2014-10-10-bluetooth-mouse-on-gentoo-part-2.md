---
layout: post
title: Bluetooth mouse on Gentoo part 2
date: 2014-10-10 23:08:00.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
simple-agent is no longer available in bluez. The gentoo wiki must be very outdated. The version I am using is 5.21.
The test-programs use flag is no longer used. So I removed it from package.use. After searching for commands that start
with blue I found bluetoothctl which did everything I needed. Here is what I did once bluetoothctl opened.

`list`

displays the local controllers on the computer.

`devices`

displays the devices.

`scan on`

starts scanning for new devices to add. (put the device in pairing mode).

`pair {mac address}`

pairs the device with the controller.

From here I thought the mouse would work well but the mouse was still flashing for pairing. The help menu has a connect
command in it so I tried that.

`connect {mac address}`

Now the mouse is working. I also have a bluetooth remote keyboard that I could add in the future. In the past I had to
enter keys on it to pair. It will be interesting to see how bluetoothctl handles this.
