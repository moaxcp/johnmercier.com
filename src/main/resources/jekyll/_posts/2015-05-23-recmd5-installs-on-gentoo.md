---
layout: post
title: recMD5 installs on gentoo
date: 2015-05-23 01:52:59.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
For the final step of the recmd5 project I want create a gentoo ebuild that will build recmd5 and install it on the
system for use.

I'm starting by using an example ebuild that seems to be a maven project similar to mine as far as building goes:
[gson](https://sources.gentoo.org/cgi-bin/viewvc.cgi/gentoo-x86/dev-java/gson/gson-2.3.1.ebuild?view=markup).

The final ebuild looks like [this](https://github.com/moaxcp/moaxcp-gentoo-overlay/blob/master/dev-java/recMD5/recMD5-1.0.1.ebuild).

Currently it is getting the source from the maven repository rather than the github archive of the source. I know the
source is the same but I'm not sure which to pick. It is easier to get it from maven because the github name is recMD5
while the source file is recmd5.

I'm very excited to finally package my software for an os. I may work on getting my download manager installing on
gentoo as well.
