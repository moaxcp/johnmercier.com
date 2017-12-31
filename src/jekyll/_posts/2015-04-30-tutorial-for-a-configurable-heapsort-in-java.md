---
layout: post
title: Tutorial For A Configurable Heapsort In Java
date: 2015-04-30 23:50:57.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
Heapsort is implemented by creating a heap and continuously removing the root element until the heap is empty. A heap is a tree with two properties: shape and heap. The shape property requires that the tree is always a complete tree. The heap property requires the tree to be in min-heap order or max-heap order. A min-heap is a tree where each node is smaller than it's children. A max-heap is a tree where each node is larger than it's children. d-ary is used to describe the maximum number of children a node can have in the heap. Typical heapsort algorithms use a 2-ary heap but others are possible including a 1-ary heap.

From the above concepts two choices are made when designing a heapsort. What heap property to use and what d-ary to use? A configurable heapsort would allow a user to make these decisions instead of being hard coded into the algorithm. If a user could make those choices what would the heapsort look like? How can the algorithm change between min-heap and max-heap? How can the algorithm change between 2-ary, 3-ary, 1-ary, or 15-ary? First, lets look at a heap sort.

# A 2-ary heap sort
To get things started the following is a 2-ary heapsort

    import java.util.Arrays;
    
    public class HeapSort {
        private void swap(int[] list, int i, int j) {
            int temp = list[j];
            list[j] = list[i];
            list[i] = temp;
        }
    
        private void siftDown(int[] list, int start, int end) {
            int root = start;
            while (root * 2 + 1 <= end) {
                int child = root * 2 + 1;
                int swap = root;
                if (list[swap] < list[child]) {
                    swap = child;
                }
                if (child + 1 <= end && list[swap] < list[child + 1]) {
                    swap = child + 1;
                }
                if (swap != root) {
                    swap(list, root, swap);
                    root = swap;
                } else {
                    return;
                }
            }
        }
    
        private void heapify(int[] list) {
            int start = (list.length - 2) / 2;
            while (start >= 0) {
                siftDown(list, start, list.length - 1);
                start -= 1;
            }
        }
    
        public void sort(int[] list) {
            heapify(list);
            int end = list.length - 1;
            while (end > 0) {
                swap(list, end, 0);
                end -= 1;
                siftDown(list, 0, end);
            }
        }
    
        public static void main(String... args) {
            int[] list = {5, 6, 4, 9, 1, 2, 8, 7, 3};
            System.out.println("before: " + Arrays.toString(list));
            new HeapSort().sort(list);
            System.out.println("after: " + Arrays.toString(list));
        }
    }

# Adding the heap property

Changing the order requires switching the relational operator on line 21 and 17 from less than to greater than. This requires an if statement to decided which comparison to make.

There are a few different designs that could be implemented. There could be two implementations of an abstract HeapSort. One that implements a min-heap and another that implements a max-heap. siftDown is the only method that needs to be abstract. This could get complicated when implementing a configuration for d-ary. MaxHeapSort and MinHeapSort would both need to implement the code for d-ary which will mean overriding the other methods.

For now, I am going with a simple approach of using an enum to configure the heap behavior. I split the siftDown method into two different methods: siftDownMin and siftDownMax. Which method is used when siftDown is called depends on the value of the enum member variable. The performance benefit of using two different methods is to only make one comparison of the enum when siftDown is called rather than in the while loop for siftDown. This design does call for copying almost the exact code in the two methods. Since the methods are short and well defined this exception is reasonable.

    import java.util.Arrays;
    
    public class HeapSortSelectHeap {
        public enum Heap {MINHEAP, MAXHEAP};
        private Heap heap;
    
        public HeapSortSelectHeap(Heap heap) {
            this.heap = heap;
        }
    
        private void swap(int[] list, int i, int j) {
            int temp = list[j];
            list[j] = list[i];
            list[i] = temp;
        }
    
        private void siftDown(int[] list, int start, int end) {
            switch (heap) {
                case MINHEAP:
                    siftDownMin(list, start, end);
                    break;
                case MAXHEAP:
                    siftDownMax(list, start, end);
                    break;
            }
        }
    
        private void siftDownMin(int[] list, int start, int end) {
            int root = start;
            while (root * 2 + 1 <= end) {
                int child = root * 2 + 1;
                int swap = root;
                if (list[swap] > list[child]) {
                    swap = child;
                }
                if (child + 1 <= end && list[swap] > list[child + 1]) {
                    swap = child + 1;
                }
                if (swap != root) {
                    swap(list, root, swap);
                    root = swap;
                } else {
                    return;
                }
            }
        }
    
        private void siftDownMax(int[] list, int start, int end) {
            int root = start;
            while (root * 2 + 1 <= end) {
                int child = root * 2 + 1;
                int swap = root;
                if (list[swap] < list[child]) {
                    swap = child;
                }
                if (child + 1 <= end && list[swap] < list[child + 1]) {
                    swap = child + 1;
                }
                if (swap != root) {
                    swap(list, root, swap);
                    root = swap;
                } else {
                    return;
                }
            }
        }
    
        private void heapify(int[] list) {
            int start = (list.length - 2) / 2;
            while (start >= 0) {
                siftDown(list, start, list.length - 1);
                start -= 1;
            }
        }
    
        public void sort(int[] list) {
            heapify(list);
            int end = list.length - 1;
            while (end > 0) {
                swap(list, end, 0);
                end -= 1;
                siftDown(list, 0, end);
            }
        }
    
        public static void runList(int[] list, Heap heap) {
            System.out.println("before: " + Arrays.toString(list));
            new HeapSortSelectHeap(heap).sort(list);
            System.out.println("after: " + Arrays.toString(list));
        }
    
        public static void main(String... args) {
            int[] list = {5, 6, 4, 9, 1, 2, 0, 8, 7, 3};
            runList(list, HeapSortSelectHeap.Heap.MINHEAP);
            list = new int[]{7, 9, 2, 6, 4, 1, 0, 3, 5, 8};
            runList(list, HeapSortSelectHeap.Heap.MAXHEAP);
        }
    }

<a title="This" href="https://github.com/moaxcp/heapsort">This</a> is the repository for the heapsort project. In the next post I will show how to make a ternary heapsort and generalize it into any possible d-ary heapsort.

Edit: I fixed an error in siftDownMax and siftDownMin. They used the wrong comparison operators.
