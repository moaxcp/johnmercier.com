---
layout: post
title: Tutorial For A Configurable Heapsort In Java 2
date: 2015-05-07 01:55:01.000000000 -04:00
categories: []
tags: []
status: draft
type: post
published: false
meta:
  _wpcom_is_markdown: '1'
  _edit_last: '77255856'
  geo_public: '0'
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
In the previous post I showed how to create a heapsort with a configurable heap property. The next step is to add a configuration for the d-ary property.

The d-ary property defines the number of children each node can have in a heap. In the previous post the heap is always a binary heap. A d-ary heap is a generalization of the binary heap which allows each node to have d children. Before creating the d-ary heapsort I want to start by creating a ternary heapsort and compare the differences with the binary heapsort.

A ternary heap is a heap where each parent has 3 child nodes instead of two.
