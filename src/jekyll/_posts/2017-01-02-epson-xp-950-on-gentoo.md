---
title: epson xp-950 on gentoo
layout: post
comments: true
---

Adding a printer/scanner on gentoo can be easy or it can be hard. Here are some notes on how I did it.

# The Printer

My printer is an Epson XP-950. My wife picked it out and I only lightly check to see if it supported linux by checking the provided drivers on the [support website](http://download.ebz.epson.net/dsc/search/01/search/?OSC=LX).

# Adding the printer bits

Epson's print drivers are provided through [net-print/epson-inkjet-printer-escpr](https://packages.gentoo.org/packages/net-print/epson-inkjet-printer-escpr) on gentoo.

    emerge -av net-print/epson-inkjet-printer-escpr

These are the drivers provided through the epson downloads page I had checked before the printer was purchaces. Since I already have cups installed all I needed to do was install this package and add the printer through cups. XP-950 is one of the printers made available to cups through this package.

# Adding the scanner bits
The scanner also has drivers through the [epson download page](http://download.ebz.epson.net/dsc/search/01/search/?OSC=LX) but these are not installed through gentoo. On gentoo it is possible to setup scanners through [SANE](https://wiki.gentoo.org/wiki/SANE). I installed xsane:

    emerge -av media-gfx/xsane

xsane is about as bad as the epson scanner software for windows and android but it seems to work so far. I was able to find a bug pretty quickly:

1. set pages to two
2. select multipage
3. click scan
4. click cancel
5. the program crashes

It also randomly decides there is no scanner for some reason.

## Other options

simple scan

This permenantily does not find the scanner.

gimp

Not tried yet.
