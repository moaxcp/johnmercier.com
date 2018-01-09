---
layout: post
title: Creating factordb
date: 2015-04-14 22:18:11.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
I started creating a new app called [factordb](https://github.com/moaxcp/factordb). I am using the new grails 3.0.1. I
have a few ideas for what it should do

* add new numbers and their factors
  * this will be validated by multiplying the two numbers (no authentication needed)
* web service to add new numbers
* add prime numbers
  * These are numbers that cannot be factored
  * These will be submitted as requests, added once processed
* maintain a list of numbers to factor
* maintain factoring program which distributes jobs
* start, stop, send numbers to factor
* display stats for factoring program

These are just some ideas for now. The factoring program will be a separate project based off of
[umuc-team-factor](https://github.com/moaxcp/umuc-team-factor).
