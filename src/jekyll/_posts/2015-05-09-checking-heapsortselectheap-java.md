---
layout: post
title: Checking HeapSortSelectHeap.java
date: 2015-05-09 16:54:14.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
I had to fix my previous post on HeapSort because I used the wrong comparison operators for siftDownMax and siftDownMin. To check these I made heapify public and printed the heap before sorting in runList.

    public static void runList(int[] list, Heap heap) {
        System.out.println("before: " + Arrays.toString(list));
        HeapSortSelectHeap s = new HeapSortSelectHeap(heap);
        s.heapify(list);
        System.out.println("heap: " + Arrays.toString(list));
        s.sort(list);
        System.out.println("after: " + Arrays.toString(list));
    }

This results in:
    MINHEAP
    before: [5, 6, 4, 9, 1, 2, 0, 8, 7, 3]
    heap: [0, 1, 2, 7, 3, 5, 4, 8, 9, 6]
    after: [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
    MAXHEAP
    before: [7, 9, 2, 6, 4, 1, 0, 3, 5, 8]
    heap: [9, 8, 2, 6, 7, 1, 0, 3, 5, 4]
    after: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

As you can see a min heap results in descending order and max heap results in ascending order.
