type=post
title=Tutorial For A Configurable Heapsort In Java 2
date=2015-05-07
status=published
~~~~~~
In the previous post I showed how to create a heapsort with a configurable heap property. The next step is to add a configuration for the d-ary property.

The d-ary property defines the number of children each node can have in a heap. In the previous post the heap is always a binary heap. A d-ary heap is a generalization of the binary heap which allows each node to have d children. Before creating the d-ary heapsort I want to start by creating a ternary heapsort and compare the differences with the binary heapsort.

A ternary heap is a heap where each parent has 3 child nodes instead of two.
