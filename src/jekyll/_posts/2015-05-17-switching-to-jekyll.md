---
layout: post
title: Switching to jekyll
date: 2015-05-17 13:33:42.000000000 -04:00
categories: []
tags: []
status: draft
type: post
published: false
meta:
  _wpcom_is_markdown: '1'
author:
  login: moaxcp
  email: moaxcp@gmail.com
  display_name: moaxcp
  first_name: ''
  last_name: ''
excerpt: !ruby/object:Hpricot::Doc
  options: {}
comments: true
---
Since I am onboard with github now I would like to switch to jekyll for blogging. There are a number of reasons to do this but the most important one is support for markdown. I haven't not been able to figure out how to use markdown on wordpress.com. Apparently it is really simple in the wordpress application but on this free site it is impossible for now. I would also like to use latex but I'm not sure if it will be supported by github. To get things started I installed jekyll on my gentoo system.

    emerge www-apps/jekyll

Then I created a site

    jekyll new .

To test I ran

    jekyll serve
