---
layout: post
title: Connecting to a WPA Wireless Network From The Gentoo Minimal Install CD
date: 2014-06-01 14:48:00.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
Connecting to a wpa enabled wireless network can be difficult in the gentoo minimum install cd. Most sources suggest not to use the cd if this is an issue and to use a cd from a different distribution. There is no reason to do this because the minimal install CD comes with wpa_supplicant. wpa_supplicant is the basic tool and library that most network configuration tools for all linux distributions use to connect to wpa networks.

When using the minimal install cd the network card should be configured to use wpa_supplicant and wpa_cli should be used to connect to the network. Using the configuration files that most tutorials show is useless as any changes will only be temporary and will be lost once the system is restarted.

1. Get the wireless interface name

```console
livecd ~ # iwconfig
lo      no wireless extensions.

enp1s0  no wireless extensions.

wlp2s0  IEE802.11bgn ESSID:off/any
       Mode:Managed Access Point: Not-Associated Tx-Power=15 dBm
       Retry long limit:7 RTS thr:off Fragment thr:off
       Encryption key:off
       Power Management:off
```

2. Use the wireless interface name to create the init script for the interface

```console
livecd~ # cp /etc/init.d/net.lo /etc/init.d/net.wlp2s0
```

2. Create the init script for the wlp2s0 interface

```console
livecd~ # nano /etc/conf.d/net
...
modules="wpa_supplicant"
wpa_supplicant_wlp2s0="-Dwext -iwlp2s0 -c/etc/wpa_supplicant/wpa_supplicant.conf"
config_wlp2s0="dhcp"
```

4. Edit /etc/wpa_supplicant/wpa_supplicant.conf

```console
livecd ~ # nano /etc/wpa_supplicant/wpa_supplicant.conf
...
ctrl_interface=/var/run/wpa_supplicant
ctrl_interface_group=0
```

5. Start the wlp2s0 interface

```console
livecd ~ # /etc/init.d/net.wlp2s0 start
```

6. Connect to the network using wpa_cli

```console
livecd ~ # wpa_cli&gt;scan
CTRL-EVENT-SCAN-RESULTS
>scan_results
>add_network
0
>set_network 0 ssid "NetworkName"
OK
>set_network 0 psk "passphrase"
OK
>enable_network 0
OK
>quit
```

7. Test the network with a ping

```console
livecd ~ # ping www.google.com
```

