---
title: accumulo devserver
layout: post
comments: true
---
Today I am messing with [this](https://github.com/joshelser/accumulo-devserver) project to create an accumulo dev server.

Since I already have virtualbox on my system I only needed to figure out how to install vagrant. Using my gentoo overlay I found an [ebuild](http://gpo.zugaina.org/app-emulation/vagrant-bin) for the latest version and added it. Once unmasked and installed the vagrant command works.

I ran into problems setting up the box. I had google a precise64 box because the default did not download (404 error). Using the command found [here](http://docs.vagrantup.com/v2/providers/basic_usage.html) I was able to setup a box.

After sshing into the server it looks like I cannot run accumulo shell. I guess I will have to try this again tomorrow.

I think vagrant is the way to go for setting these dev boxes up. There is a gentoo box that could be used to setup a dev environment. The only problem is finding ebuilds for hadoop, accumulo, and zookeeper. Also hadoop requires ProtocolBuffer but there is already an updated ebuild for it in the master overlay.
