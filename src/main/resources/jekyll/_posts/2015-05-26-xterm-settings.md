---
title: xterm settings
layout: post
comments: true
---

To setup xterm with different colors than the system default edit the file ~.Xresources.

Here are some links to a few good color schemes I found.

[https://wiki.mpich.org/mpich/index.php/Configure_xterm_Fonts_and_Colors_for_Your_Eyeball](https://wiki.mpich.org/mpich/index.php/Configure_xterm_Fonts_and_Colors_for_Your_Eyeball)

[https://wiki.archlinux.org/index.php/x_resources](https://wiki.archlinux.org/index.php/x_resources)

[https://unix4lyfe.org/xterm/](https://unix4lyfe.org/xterm/)

Just copy and paste these into the file.

To enable changes to the file add

~~~ bash
[[ -f ~/.Xresources ]] && xrdb -merge ~/.Xresources
~~~

to ~/.xinitrc and restart your terminal or use source ~/.xinitrc
