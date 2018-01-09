---
layout: post
title: $PS1 in gentoo
date: 2015-04-15 02:10:51.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
I finally changed my terminal to get rid of long paths. Right now it is

```
john@john-gentoo ~/Dropbox/programming/groovy/factordb/grails-app/conf $
```

And after the change it is

```
john@john-gentoo conf $
```

This is what I added to my .bashrc

```
PS1="\[33[01;32m\]\u\[33[01;34m\] \W \$\[33[00m\]"
```
