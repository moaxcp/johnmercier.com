---
layout: post
title: VirtualBox Gentoo Template
date: 2015-05-17 03:10:29.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
I am creating a new virtualbox gentoo instance. This will be used as a template for other gentoo vms on my laptop. For now it will be very basic.

* 1 cpu
* 512 MB memory
* 8 GB harddrive space

Being able to increase the template may be important. I have had problems in the past with increasing cpus in a gentoo vm. Adding memory shouldn't be a problem. Adding harddrive space would be better if I used LVM.

# Default apps
So far I only need java and ssh.

# make.conf

For now I am using the settings I have on my local computer. This could cause problems with portability especially with cpu flags and the march option (corei7-avx).

# Planned uses
1. create a hadoop cluster with accumulo
--Since this is what I use at work
2. create an ingestion server to run pentaho jobs
--Since this is what I do at work
3. create a application server
--This is for all webapps that interact with the accumulo db
--crud/search services/apps
