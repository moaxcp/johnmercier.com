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
<p>I started creating a new app called <a title="factordb" href="https://github.com/moaxcp/factordb">factordb</a>. I am using the new grails 3.0.1. I have a few ideas for what it should do</p>
<ul>
<li>add new numbers and their factors
<ul>
<li>this will be validated by multiplying the two numbers (no authentication needed)</li>
</ul>
</li>
<li>web service to add new numbers</li>
<li>add prime numbers
<ul>
<li>These are numbers that cannot be factored</li>
<li>These will be submitted as requests, added once processed</li>
</ul>
</li>
<li>maintain a list of numbers to factor</li>
<li>maintain factoring program which distributes jobs
<ul>
<li>start, stop, send numbers to factor</li>
</ul>
</li>
<li>display stats for factoring program</li>
</ul>
<p>These are just some ideas for now. The factoring program will be a separate project based off of <a title="umuc-team-factor" href="https://github.com/moaxcp/umuc-team-factor">umuc-team-factor</a>.</p>
