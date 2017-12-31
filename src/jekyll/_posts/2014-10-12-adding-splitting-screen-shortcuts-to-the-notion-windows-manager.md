---
layout: post
title: Adding splitting screen shortcuts to the notion windows manager
date: 2014-10-12 20:50:00.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
Some time ago I asked a question on stack overflow about adding new commands to the notion window manager. These commands split the entire workspace, adding a new frame to the bottom or side of it. Here is a link: http://stackoverflow.com/questions/24494725/splitting-workspaces-in-the-notion-window-manager

In cfg_tiling.lua add

    kpress(META.."Z", "WTiling.split_top(_, 'bottom'):goto_focus()"),

inside the "defbindings("WTiling", {" block and add

    kpress("Z", "Wtiling.split_top(_, 'left'):goto_focus()"),

inside the "submap(META.."K", {" block

It sets up META..Z and META..K + Z as the key bindings for these actions.

After setting these up on my Dell precision 4600 only META..Z seems to work and the focus is not moving to the new frame.

