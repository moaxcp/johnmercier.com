---
layout: post
title: Changing keyboard shortcuts in the notion windows manager
date: 2014-10-15 23:36:00.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
There are two problems with notion's keyboard shortcuts: using the Alt key and function key for commands.  First the Alt key is used for things like splitting and resizing frames. The Alt key is usually used to select menus in a window. For instance, in Netbeans Alt+r selects the run menu. When I push Alt+r notion tries to resize the frame instead of letting netbeans open the menu. This is why most Tiling windows managers use the Super key (windows key) instead, allowing applications to use the Alt key.  The second problem is, function keys are used for things like creating workspaces, opening a terminal, and running commands. Notion hijacks these keys so applications cannot use them. The most recent example I ran into is with alsamixer. Alsamixer uses the function keys for selecting the sound card and viewing system info.  Notion's keyboard shortcuts break applications. This is a problem for a mouse free windows manager. Notion should design a new shortcut layout that is simple and works with other X11 applications.
