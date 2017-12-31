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
<p>I finally changed my terminal to get rid of long paths. Right now it is</p>
<blockquote><p>john@john-gentoo ~/Dropbox/programming/groovy/factordb/grails-app/conf $</p></blockquote>
<p>And after the change it is</p>
<blockquote><p>john@john-gentoo conf $</p></blockquote>
<p>This is what I added to my .bashrc</p>
<blockquote><p>PS1="\[33[01;32m\]\u\[33[01;34m\] \W \$\[33[00m\]"</p></blockquote>
