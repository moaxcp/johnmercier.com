---
title: connecting to wifi from nixos minimal install cd
layout: post
comments: true
---

One step in installing nixos I had trouble performing was connecting to wifi. On the minimal install boot
wpa_supplicant is installed but there is no service. There are a few instructions for this online but this is
what I did to get it working.

# wpa_supplicant.conf

```
nano /etc/wpa_supplicant.conf #this file can really be anywhere
```

Once the file is opened add

```
ctrl_interface=/var/run/wpa_supplicant
ctrl_interface_group=0
```

Then start wpa_supplicant for your interface.

```
wpa_supplicant -B -i iwp3s0 -c /etc/wpa_supplicant.conf
```

Once wpa_supplicant is running on your system you can add a network through the cli.

```
wpa_cli
scan
scan_results
add_network
set_network 0 ssid "..."
set_network 0 psk "..."
enable_network 0
quit
```

Next test the connection.

    ping www.google.com

