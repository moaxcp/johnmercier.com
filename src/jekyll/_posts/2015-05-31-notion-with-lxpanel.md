---
title: notion with lxpanel
layout: post
comments: true
---
Today I am starting a project to use lxpanel from lxde in the notion window manager. I am starting with 
a fresh set of config files.

        cp /usr/local/etc/notion/* /home/john/.notion

Turn off mod_statusbar and turn on mod_dock.

        nano /home/john/.notion/cfg_notion.lua

* comment out dopath("cfg_defaults")
* Uncomment the rest of the modules except for mod_statusbar

Reload the session

* press F12
* type session/restart

There will be a floating dock (black empty box) in the lower right of the workspace. To get rid of it

        nano /home/john/.notion/cfg_dock.lua


The first section creates a dock floating dock that can be used to place the lxpanel application. Set the mode to embedded or floating. Floating will allow the panel to be hidden. Embedded will permenently place the panel at the bottom of the screen. Set floating_hidden=true to automatically hide the panel when the session starts. Restart the session once cfg_dock.lua is saved (F12 + session/restart).

Start lxpanel by pression F3 and typing lxpanel.

After the dock is setup add lxpanel to your .xinitrc This will make sure the panel is started when you log into the computer.

