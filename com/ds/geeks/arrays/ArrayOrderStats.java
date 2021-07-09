package com.ds.geeks.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

public class ArrayOrderStats {
    /**
     * Key points
     * 
     * 1. Use priority queue (min heap) to find kth element in k sorted lists, or
     * row and column sorted matrix. In matrix, add the first row to queue, in
     * lists, add heads to the queue
     * 
     * 2. Use quick sort's random pivot to find the kth element in O(N) time
     * 
     * 3. Use tree map for insertions in sorted order. Iterations will be in sorted
     * order as well.
     * 
     * 4. Use Max heap on k elements, compare the elements k..n-1, if element is
     * less, make it root and heapify. finally, the root is kth element.
     * 
     * 5. To print both smallest and largest elements at once, use the quick sort
     * partition method.
     * 
     * 6. Consider using 2 or 3 pointers: first, second, and third to print elements
     * which have at least 2 greater elements.
     * 
     * 7. Use max heap on smaller elements and min heap on larger elements to
     * identify the median
     * 
     * 8. Use prefix sum on array to calculate contiguous sum of sub-arrays.
     * preSum[j] - preSum[i-1] gives the sum beween i and j.
     * 
     * 9. Given two arrays: a1, and a2, Use index array to track the next element to
     * move to in a2 to find k smallest pairs.
     * 
     * 10. To find k maximum sums in contiguous subarray, use minKSum[], and
     * maxKSum[]. prefixSum[i] - minSum[i] = maxSum[i]
     * 
     * 11. Use binary search to find the kth absolute difference between two
     * elements in an array on sorting it, and finding the count of pairs is less or
     * greater than k.
     * 
     * 12. For finding missing in a sorted array of N elements, use binary search
     * with comparison on the array element == index.
     * 
     * 13. For finding max sum such that no elements are adjacent, use two variable.
     * incl_prev, excl_prev. incl_curr = excl_prev+arr[i],
     * excl_curr=max(incl_prev,excl_prev)
     */
    static int kthSmallestElementUsingSort(int[] arr, int k) {
        // Sort the given array
        Arrays.sort(arr);

        // Return k'th element in
        // the sorted array
        return arr[k - 1];
    }

    static int kthSmallestUsingMinHeap(int arr[], int k) {
        // Time complexity of this solution is O(n + kLogn).
        // klogn because we heapify only for k elements. n is to store the array.
        int n = arr.length;
        // Build a heap of first k elements: O(k) time
        MinHeap mh = new MinHeap(arr, n);

        // Process remaining n-k elements. If current element is
        // smaller than root, replace root with current element
        for (int i = 0; i < k - 1; i++)
            mh.extractMin();
        // we call extractMin k times, so the complexity is O(klogn)

        // Return root
        return mh.getMin();
    }

    static int kthSmallestelementUsingMaxHeap(int arr[], int k) {
        // Time complexity is O(k + (n-k) log k)
        /**
         * We can also use Max Heap for finding the k’th smallest element. Following is
         * an algorithm. 1) Build a Max-Heap MH of the first k elements (arr[0] to
         * arr[k-1]) of the given array. O(k) 2) For each element, after the k’th
         * element (arr[k] to arr[n-1]), compare it with root of MH. ……a) If the element
         * is less than the root then make it root and call heapify for MH ……b) Else
         * ignore it. // The step 2 is O((n-k)*logk) 3) Finally, the root of the MH is
         * the kth smallest element. Time complexity of this solution is O(k +
         * (n-k)*Logk)
         */
        // Build a heap of first k elements: O(k) time
        int n = arr.length;
        MaxHeap mh = new MaxHeap(arr, k);

        // Process remaining n-k elements. If current element is
        // smaller than root, replace root with current element
        for (int i = k; i < n; i++)
            if (arr[i] < mh.getMax())
                mh.replaceMax(arr[i]);

        // Return root
        return mh.getMax();
    }

    // Standard partition process of QuickSort.
    // It considers the last element as pivot
    // and moves all smaller element to left of
    // it and greater elements to right
    static int partition(int[] arr, int l, int r) {
        int x = arr[r], i = l;
        for (int j = l; j <= r - 1; j++) {
            if (arr[j] <= x) {
                // Swapping arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                i++;// i stores the smallest element <=x
            }
        }

        // Swapping arr[i] and arr[r]
        int temp = arr[i];
        arr[i] = arr[r];
        arr[r] = temp;

        return i;
    }

    // This function returns k'th smallest element
    // in arr[l..r] using QuickSort based method.
    // ASSUMPTION: ALL ELEMENTS IN ARR[] ARE DISTINCT
    static int kthSmallestElementsUsingQuickSelect(int arr[], int l, int r, int k) {
        /**
         * This is an optimization over method 1 if QuickSort is used as a sorting
         * algorithm in first step. In QuickSort, we pick a pivot element, then move the
         * pivot element to its correct position and partition the surrounding array.
         * The idea is, not to do complete quicksort, but stop at the point where pivot
         * itself is k’th smallest element. Also, not to recur for both left and right
         * sides of pivot, but recur for one of them according to the position of pivot.
         * The worst case time complexity of this method is O(n2), but it works in O(n)
         * on average.
         */
        // If k is smaller than number of elements
        // in array
        if (k > 0 && k <= r - l + 1) {
            // Partition the array around last
            // element and get position of pivot
            // element in sorted array
            int pos = partition(arr, l, r);

            // If position is same as k
            if (pos - l == k - 1)
                return arr[pos];

            // If position is more, recur for
            // left subarray
            if (pos - l > k - 1)
                return kthSmallestElementsUsingQuickSelect(arr, l, pos - 1, k);

            // Else recur for right subarray
            return kthSmallestElementsUsingQuickSelect(arr, pos + 1, r, k - pos + l - 1);
        }

        // If k is more than number of elements
        // in array
        return Integer.MAX_VALUE;
    }

    static int kthSmallestElementsUsingTreeMap(int arr[], int k) {
        /**
         * A map based STL approach is although very much similar to the quickselect and
         * counting sort algorithm but much easier to implement. We can use an ordered
         * map (Treemap in java which sorts on its keys by natural order) and map each
         * element with it’s frequency. And as we know that an ordered map would store
         * the data in an sorted manner, we keep on adding the frequency of each element
         * till it does not become greater than or equal to k so that we reach the k’th
         * element from the start i.e. the k’th smallest element.
         * 
         * Eg –
         * 
         * Array={7,0,25,6,16,17,0}
         * 
         * k=3
         * 
         * Now in order to get the k’th largest element, we need to add the frequencies
         * till it becomes greater than or equal to 3. It is clear from the above that
         * the frequency of 0 + frequency of 6 will become equal to 3 so the third
         * smallest number in the array will be 6.
         */
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(arr[i]))
                map.put(arr[i], map.get(arr[i]) + 1);
            else
                map.put(arr[i], 1);
        }
        int freq = 0;
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            freq += entry.getValue(); // adding the frequencies of
            // each element
            if (freq >= k) // if at any point frequency becomes
                           // greater than or equal to k then
                           // return that element
            {
                return entry.getKey();
            }
        }
        return -1;
    }

    static int kthSmallesElementUsingQuickSortRandomPartition_NTime(int arr[], int l, int r, int k) {

        /**
         * Given an array and a number k where k is smaller than the size of the array,
         * we need to find the k’th smallest element in the given array. It is given
         * that all array elements are distinct.
         * 
         * Examples:
         * 
         * Input: arr[] = {7, 10, 4, 3, 20, 15} k = 3 Output: 7
         * 
         * Input: arr[] = {7, 10, 4, 3, 20, 15} k = 4 Output: 10
         */
        /**
         * In this post method 5 is discussed which is mainly an extension of method 4
         * (QuickSelect) discussed in the previous post. The idea is to randomly pick a
         * pivot element. To implement randomized partition, we use a random function,
         * rand() to generate index between l and r, swap the element at randomly
         * generated index with the last element, and finally call the standard
         * partition process which uses last element as pivot.
         * 
         * Following is an implementation of the above Randomized QuickSelect.
         */
        // If k is smaller than number
        // of elements in array
        if (k > 0 && k <= r - l + 1) {
            // Partition the array around a
            // random element and get position
            // of pivot element in sorted array
            int pos = randomPartition(arr, l, r);

            // If position is same as k
            if (pos - l == k - 1)
                return arr[pos];

            // If position is more, recur
            // for left subarray
            if (pos - l > k - 1)
                return kthSmallesElementUsingQuickSortRandomPartition_NTime(arr, l, pos - 1, k);

            // Else recur for right subarray
            return kthSmallesElementUsingQuickSortRandomPartition_NTime(arr, pos + 1, r, k - pos + l - 1);
        }

        // If k is more than number of
        // elements in array
        return Integer.MAX_VALUE;
    }

    // Standard partition process of QuickSort().
    // It considers the last element as pivot and
    // moves all smaller element to left of it
    // and greater elements to right. This function
    // is used by randomPartition()
    static int randPartitionUtil(int[] arr, int l, int r) {
        int x = arr[r], i = l;
        for (int j = l; j <= r - 1; j++) {
            if (arr[j] <= x) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, r);
        return i;
    }

    // Utility method to swap arr[i] and arr[j]
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Picks a random pivot element between
    // l and r and partitions arr[l..r]
    // around the randomly picked element
    // using partition()
    static int randomPartition(int[] arr, int l, int r) {
        int n = r - l + 1;
        int pivot = (int) (Math.random() * (n - 1));
        swap(arr, l + pivot, r);
        return randPartitionUtil(arr, l, r);
    }

    static void kLargestElementsWithPriorityQueueMinHeap(int arr[], int k) {
        // Creating Min Heap for given
        // array with only k elements
        // Create min heap with priority queue
        int size = arr.length;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 0; i < k; i++) {
            minHeap.add(arr[i]);
        }

        // Loop For each element in array
        // after the kth element
        for (int i = k; i < size; i++) {

            // If current element is smaller
            // than minimum ((top element of
            // the minHeap) element, do nothing
            // and continue to next element
            if (minHeap.peek() > arr[i])
                continue;

            // Otherwise Change minimum element
            // (top element of the minHeap) to
            // current element by polling out
            // the top element of the minHeap
            else {
                minHeap.poll();
                minHeap.add(arr[i]);
            }
        }

        // Now min heap contains k maximum
        // elements, Iterate and print
        Iterator iterator = minHeap.iterator();

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }

    static void smallestLargestKElements(int a[], int low, int high, int k, int n) {
        if (low == high)
            return;
        else {
            int pivotIndex = randomPartition(a, low, high);

            if (k == pivotIndex) {
                System.out.println(k + " smallest elements are : ");
                for (int i = 0; i < pivotIndex; i++)
                    System.out.print(a[i] + "  ");

                System.out.println(k + " largest elements are : ");
                for (int i = (n - pivotIndex); i < n; i++)
                    System.out.print(a[i] + "  ");
            }

            else if (k < pivotIndex)
                smallestLargestKElements(a, low, pivotIndex - 1, k, n);

            else if (k > pivotIndex)
                smallestLargestKElements(a, pivotIndex + 1, high, k, n);
        }
    }

    static int kthSmallestElementInSortedMatrix(int[][] mat, int n, int k) {
        /**
         * Time Complexity: The above solution involves following steps. Building a
         * min-heap which takes O(n) time Heapify k times which takes O(k Logn) time.
         * Space Complexity: O(R), where R is the length of a row, as the Min-Heap
         * stores one row at a time.
         */

        /**
         * Given an n x n matrix, where every row and column is sorted in non-decreasing
         * order. Find the kth smallest element in the given 2D array. Example,
         * 
         * Input:k = 3 and array =
         * 
         * 10, 20, 30, 40
         * 
         * 15, 25, 35, 45
         * 
         * 24, 29, 37, 48
         * 
         * 32, 33, 39, 50
         * 
         * Output: 20 Explanation: The 3rd smallest element is 20
         * 
         * Input:k = 7 and array =
         * 
         * 10, 20, 30, 40
         * 
         * 15, 25, 35, 45
         * 
         * 24, 29, 37, 48
         * 
         * 32, 33, 39, 50
         * 
         * Output: 30
         * 
         * Explanation: The 7th smallest element is 30
         */
        /**
         * Approach: So the idea is to find the kth minimum element. Each row and each
         * column is sorted. So it can be thought as C sorted lists and the lists have
         * to be merged into a single list, the kth element of the list has to be found
         * out. So the approach is similar, the only difference is when the kth element
         * is found the loop ends. Algorithm:
         * 
         * 1. The idea is to use min heap. Create a Min-Heap to store the elements
         * 
         * 2. Traverse the first row from start to end and build a min heap of elements
         * from first row. A heap entry also stores row number and column number.
         * 
         * 3. Now Run a loop k times to extract min element from heap in each iteration
         * 
         * 4. Get minimum element (or root) from Min-Heap.
         * 
         * 5. Find row number and column number of the minimum element.
         * 
         * 6. Replace root with the next element from same column and min-heapify the
         * root.
         * 
         * 7. Print the last extracted element, which is the kth minimum element
         * 
         */
        // k must be greater than 0 and
        // smaller than n*n
        if (k < 0 && k >= n * n)
            return Integer.MAX_VALUE;

        // Create a min heap of elements
        // from first row of 2D array
        HeapNode harr[] = new HeapNode[n];

        for (int i = 0; i < n; i++) {
            harr[i] = new HeapNode(mat[0][i], 0, i);
        }

        HeapNode hr = new HeapNode(0, 0, 0);

        for (int i = 1; i <= k; i++) {

            // Get current heap root
            hr = harr[0];

            // Get next value from column of root's
            // value. If the value stored at root was
            // last value in its column, then assign
            // INFINITE as next value
            int nextVal = hr.r < n - 1 ? mat[hr.r + 1][hr.c] : Integer.MAX_VALUE;

            // Update heap root with next value
            harr[0] = new HeapNode(nextVal, hr.r + 1, hr.c);

            // Heapify root
            minHeapify(harr, 0, n);
        }

        // Return the value at last extracted root
        return hr.val;
    }

    static void printThreelargestElement(int arr[], int arr_size) {
        // given an array of distinct integers, print 3 largest elements.
        int i, first, second, third;

        /* There should be atleast three elements */
        if (arr_size < 3) {
            System.out.print(" Invalid Input ");
            return;
        }

        third = first = second = Integer.MIN_VALUE;
        for (i = 0; i < arr_size; i++) {
            /*
             * If current element is greater than first
             */
            if (arr[i] > first) {
                third = second;
                second = first;
                first = arr[i];
            }

            /*
             * If arr[i] is in between first and second then update second
             */
            else if (arr[i] > second) {
                third = second;
                second = arr[i];
            }

            else if (arr[i] > third)
                third = arr[i];
        }

        System.out.println("Three largest elements are " + first + " " + second + " " + third);
    }

    static void findElementsWhichHaveAtleast2GreaterElements(int arr[], int n) {

        int first = Integer.MIN_VALUE;
        int second = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            // If current element is smaller
            // than first then update both
            // first and second
            if (arr[i] > first) {
                second = first;
                first = arr[i];
            }

            /*
             * If arr[i] is in between first and second then update second
             */
            else if (arr[i] > second)
                second = arr[i];
        }

        for (int i = 0; i < n; i++)
            if (arr[i] < second)
                System.out.print(arr[i] + " ");

    }

    static void medianInStreamOfRunningIntegers(int[] a) {
        /**
         * Median can be defined as the element in the data set which separates the
         * higher half of the data sample from the lower half. In other words, we can
         * get the median element as, when the input size is odd, we take the middle
         * element of sorted data. If the input size is even, we pick an average of
         * middle two elements in the sorted stream. Examples:
         * 
         * 
         * Input: 5 10 15 Output: 5, 7.5, 10
         * 
         * Explanation: Given the input stream as an array of integers [5,10,15]. Read
         * integers one by one and print the median correspondingly. So, after reading
         * first element 5,median is 5. After reading 10,median is 7.5 After reading 15
         * ,median is 10.
         * 
         * Input: 1, 2, 3, 4 Output: 1,1.5, 2, 2.5
         * 
         * Explanation: Given the input stream as an array of integers [1, 2, 3, 4].
         * Read integers one by one and print the median correspondingly. So, after
         * reading first element 1,median is 1. After reading 2,median is 1.5 After
         * reading 3 ,median is 2.After reading 4 ,median is 2.5.
         */
        /**
         * Approach: The idea is to use max heap and min heap to store the elements of
         * higher half and lower half. Max heap and min heap can be implemented using
         * priority_queue in C++ STL. Below is the step by step algorithm to solve this
         * problem. Algorithm:
         * 
         * 
         * 1. Create two heaps. One max heap to maintain elements of lower half and one
         * min heap to maintain elements of higher half at any point of time..
         * 
         * 2. Take initial value of median as 0.
         * 
         * 3. For every newly read element, insert it into either max heap or min-heap
         * and calculate the median based on the following conditions:
         * 
         * 3.1. If the size of max heap is greater than the size of min-heap and the
         * element is less than the previous median then pop the top element from max
         * heap and insert into min-heap and insert the new element to max heap else
         * insert the new element to min-heap. Calculate the new median as the average
         * of top of elements of both max and min heap.
         * 
         * 3.2. If the size of max heap is less than the size of min-heap and the
         * element is greater than the previous median then pop the top element from
         * min-heap and insert into the max heap and insert the new element to min heap
         * else insert the new element to the max heap. Calculate the new median as the
         * average of top of elements of both max and min heap.
         * 
         * 3.3. If the size of both heaps is the same. Then check if the current is less
         * than the previous median or not. If the current element is less than the
         * previous median then insert it to the max heap and a new median will be equal
         * to the top element of max heap. If the current element is greater than the
         * previous median then insert it to min-heap and new median will be equal to
         * the top element of min heap.
         */

        double med = a[0];

        // Smaller half elements are maintained in a max heap and larger half in a min
        // heap.
        // so to compute median, the top of max and min heap can be used.

        // MAX HEAP to store the smaller half elements
        PriorityQueue<Integer> smaller = new PriorityQueue<>(Collections.reverseOrder());

        // MIN HEAP to store the greater half elements
        PriorityQueue<Integer> greater = new PriorityQueue<>();

        smaller.add(a[0]);
        System.out.println(med);

        // reading elements of stream one by one
        /*
         * At any time we try to make heaps balanced and their sizes differ by at-most
         * 1. If heaps are balanced,then we declare median as average of
         * min_heap_right.top() and max_heap_left.top() If heaps are unbalanced,then
         * median is defined as the top element of heap of larger size
         */
        // [5, 10, 15, 20]
        // smaller: [5]
        for (int i = 1; i < a.length; i++) {

            int x = a[i];

            // case1(left side heap has more elements)
            if (smaller.size() > greater.size()) {
                if (x < med) {
                    greater.add(smaller.remove());
                    smaller.add(x);
                } else
                    greater.add(x); // greater: [10]
                med = (double) (smaller.peek() + greater.peek()) / 2;
            }

            // case2(both heaps are balanced) smaller: [5], greater: [10]
            else if (smaller.size() == greater.size()) {
                if (x < med) {
                    smaller.add(x);
                    med = (double) smaller.peek();
                } else {
                    greater.add(x);// greater: [10, 15]
                    med = (double) greater.peek();
                }
            }

            // case3(right side heap has more elements)
            else {// smaller: [5], gerater: [10, 15], med: 10, x: 20
                if (x > med) {
                    smaller.add(greater.remove());// smaller: [5, 10], greater: [15]
                    greater.add(x);// greater: [15, 20]
                } else
                    smaller.add(x);
                med = (double) (smaller.peek() + greater.peek()) / 2;// 10 + 15/2 = 12.5

            }
            System.out.println(med);
        }
    }

    static int minProductOfKIntegersInAnArray(int[] arr, int k) {
        // Time complexity: O(nlogn), use quick select random partition for O(n)
        // solution.
        /**
         * Given an array of n positive integers. We are required to write a program to
         * print the minimum product of k integers of the given array. Examples:
         * 
         * 
         * Input : 198 76 544 123 154 675 k = 2 Output : 9348 We get minimum product
         * after multiplying 76 and 123.
         * 
         * Input : 11 8 5 7 5 100 k = 4 Output : 1400
         * 
         * The idea is simple, we find the smallest k elements and print multiplication
         * of them. In below implementation, we have used simple Heap based approach
         * where we insert array elements into a min heap and then find product of top k
         * elements.
         */

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = arr.length;
        for (int i = 0; i < n; i++)
            pq.add(arr[i]);

        int count = 0, ans = 1;

        // One by one extract items
        while (pq.isEmpty() == false && count < k) {
            ans = ans * pq.element();
            pq.remove();
            count++;
        }
        return ans;
    }

    static int kthLargestSumInContiguousSubArray(int[] arr, int k) {
        // Time complexity is: O(n^2 logk)
        /**
         * Given an array of integers. Write a program to find the K-th largest sum of
         * contiguous subarray within the array of numbers which has negative and
         * positive numbers.
         * 
         * Examples:
         * 
         * Input: a[] = {20, -5, -1} k = 3 Output: 14 Explanation: All sum of contiguous
         * subarrays are (20, 15, 14, -5, -6, -1) so the 3rd largest sum in contiguous
         * subarray is 14.
         * 
         * Input: a[] = {10, -10, 20, -40} k = 6 Output: -10 Explanation: The 6th
         * largest sum among sum of all contiguous subarrays is -10.
         */
        /**
         * An efficient approach is to store the pre-sum of the array in a sum[] array.
         * We can find sum of contiguous subarray from index i to j as sum[j]-sum[i-1]
         * 
         * Now for storing the Kth largest sum, use a min heap (priority queue) in which
         * we push the contiguous sums till we get K elements, once we have our K
         * elements, check if the element is greater than the Kth element it is inserted
         * to the min heap with popping out the top element in the min-heap, else not
         * inserted. In the end, the top element in the min-heap will be your answer.
         */
        // array to store predix sums
        int n = arr.length;
        int sum[] = new int[n + 1];
        sum[0] = 0;
        sum[1] = arr[0];
        for (int i = 2; i <= n; i++)
            sum[i] = sum[i - 1] + arr[i - 1];

        // priority_queue of min heap
        PriorityQueue<Integer> Q = new PriorityQueue<Integer>();

        // loop to calculate the contiguous subarray
        // sum position-wise
        for (int i = 1; i <= n; i++) {

            // loop to traverse all positions that
            // form contiguous subarray
            for (int j = i; j <= n; j++) {
                // calculates the contiguous subarray
                // sum from j to i index
                int x = sum[j] - sum[i - 1];

                // if queue has less then k elements,
                // then simply push it
                if (Q.size() < k)
                    Q.add(x);

                else {
                    // it the min heap has equal to
                    // k elements then just check
                    // if the largest kth element is
                    // smaller than x then insert
                    // else its of no use
                    if (Q.peek() < x) {
                        Q.poll();
                        Q.add(x);
                    }
                }
            }
        }

        // the top element will be then kth
        // largest element
        return Q.poll();
    }

    // Here cand and maxi arrays are in non-increasing
    // order beforehand. Now, j is the index of the
    // next cand element and i is the index of next
    // maxi element. Traverse through maxi array.
    // If cand[j] > maxi[i] insert cand[j] at the ith
    // position in the maxi array and remove the minimum
    // element of the maxi array i.e. the last element
    // and increase j by 1 i.e. take the next element
    // from cand.
    static void maxMerge(ArrayList<Integer> max, ArrayList<Integer> cand) {
        int k = max.size();
        int j = 0;
        for (int i = 0; i < k; i++) {
            if (cand.get(j) > max.get(i)) {
                max.add(i, cand.get(j));
                max.remove(k);
                j++;
            }
        }
    }

    // Traverse the mini array from left to right.
    // If prefix_sum[i] is less than any element
    // then insert prefix_sum[i] at that position
    // and delete maximum element of the min
    // i.e. the rightmost element from the min.
    static void minMerge(ArrayList<Integer> min, int ms) {
        for (int i = 0; i < min.size(); i++) {
            if (min.get(i) > ms) {
                min.add(i, ms);
                min.remove(min.size() - 1);
                break;
            }
        }
    }

    static int maximumSumInContiguousSubarrayUsingPrefixSum_NTime(int[] arr) {
        /**
         * Given an Array of Positive and Negative Integers, find out the Maximum
         * Subarray Sum in that Array. Input1 : arr = {-2, -3, 4, -1, -2, 1, 5, -3}
         * Output1 : 7
         * 
         * Input2 : arr = {4, -8, 9, -4, 1, -8, -1, 6} Output2 : 9
         */
        /**
         * Implementation: 1. Calculate the prefix sum of the input array.
         * 
         * 2. Initialize : min_prefix_sum = 0, res = -infinite
         * 
         * 3. Maintain a loop for i = 0 to n. (n is the size of the input array).
         * 
         * a) cand = prefix_sum[i] – mini
         * 
         * b) If cand is greater than res (maximum subarray sum so far), then update res
         * by cand.
         * 
         * c) If prefix_sum[i] is less than min_prefix_sum (minimum prefix sum so far),
         * then update min_prefix_sum by prefix_sum[i].
         * 
         * 4. Return res.
         * 
         */
        int n = arr.length;
        // Initialize minimum
        // prefix sum to 0.
        int min_prefix_sum = 0;

        // Initialize maximum subarray
        // sum so far to -infinity.
        int res = Integer.MIN_VALUE;

        // Initialize and compute
        // the prefix sum array.
        int prefix_sum[] = new int[n];
        prefix_sum[0] = arr[0];
        for (int i = 1; i < n; i++)
            prefix_sum[i] = prefix_sum[i - 1] + arr[i];

        // loop through the array, keep
        // track of minimum prefix sum so
        // far and maximum subarray sum.
        for (int i = 0; i < n; i++) {
            res = Math.max(res, prefix_sum[i] - min_prefix_sum);
            min_prefix_sum = Math.min(min_prefix_sum, prefix_sum[i]);
        }
        return res;

    }

    static void kMaxSumsInOverlappingContiguousSubarrays(int aa[], int k) {
        /**
         * The ‘insertMini’ and ‘maxMerge’ functions runs in O(k) time and it takes O(k)
         * time to update the ‘cand’ array. We do this process for n times. Hence, the
         * overall time complexity is O(k*n).
         */
        ArrayList<Integer> max = new ArrayList<Integer>();
        ArrayList<Integer> min = new ArrayList<Integer>();
        ArrayList<Integer> currentSum = new ArrayList<Integer>();
        int n = aa.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = aa[0];
        for (int i = 1; i < n; i++)
            prefixSum[i] = prefixSum[i - 1] + aa[i];
        for (int i = 0; i < k; i++) {
            max.add(Integer.MIN_VALUE);
            min.add(Integer.MAX_VALUE);
        }
        min.add(0, 0);
        min.remove(k);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                // for k min (sum) elements (identified from prefix - refer below),
                // identify the current k max sum which ends at index i
                currentSum.add(prefixSum[i] - min.get(j));
            }
            System.out.println("Index i " + i + " current sum array is " + currentSum);
            // check if the current k max sum has to be merged to the maxMerge overall.
            maxMerge(max, currentSum);
            // stores K minimum element found in prefix array so far.
            minMerge(min, prefixSum[i]);
            currentSum.clear();
        }

        for (int i = 0; i < k; i++) {
            System.out.print(max.get(i) + " ");
        }
    }

    static void kMaxSumsInNonOverlappingContiguousSubarraysUsingKadaneAlgorithm(int arr[], int k) {
        /**
         * Given an Array of Integers and an Integer value k, find out k non-overlapping
         * sub-arrays which have k maximum sums.
         * 
         * 
         * 
         * 
         * Examples:
         * 
         * Input : arr1[] = {4, 1, 1, -1, -3, -5, 6, 2, -6, -2}, k = 3. Output : Maximum
         * non-overlapping sub-array sum1: 8, starting index: 6, ending index: 7.
         * 
         * Maximum non-overlapping sub-array sum2: 6, starting index: 0, ending index:
         * 2.
         * 
         * Maximum non-overlapping sub-array sum3: -1, starting index: 3, ending index:
         * 3.
         * 
         * Input : arr2 = {5, 1, 2, -6, 2, -1, 3, 1}, k = 2. Output : Maximum
         * non-overlapping sub-array sum1: 8, starting index: 0, ending index: 2.
         * 
         * Maximum non-overlapping sub-array sum2: 5, starting index: 4, ending index:
         * 7.
         */
        /**
         * Kadane’s algorithm finds out only the maximum subarray sum, but using the
         * same algorithm we can find out k maximum non-overlapping subarray sums. The
         * approach is:
         * 
         * 1. Find out the maximum subarray in the array using Kadane’s algorithm.
         * 
         * 2. Also find out its starting and end indices. Print the sum of this
         * subarray.
         * 
         * 3. Fill each cell of this subarray by -infinity. Repeat process 1 and 2 for k
         * times.
         */
        // In each iteration it will give
        // the ith maximum subarray sum.
        int n = arr.length;
        for (int c = 0; c < k; c++) {
            // Kadane's algorithm.
            int max_so_far = Integer.MIN_VALUE;
            int max_here = 0;

            // compute starting and ending
            // index of each of the sub-array.
            int start = 0, end = 0, s = 0;
            for (int i = 0; i < n; i++) {
                max_here += arr[i];
                if (max_so_far < max_here) {
                    max_so_far = max_here;
                    start = s;
                    end = i;
                }
                if (max_here < 0) {
                    max_here = 0;
                    s = i + 1;
                }
            }

            // Print out the result.
            System.out.println("Maximum non-overlapping sub-arraysum" + (c + 1) + ": " + max_so_far
                    + ", starting index: " + start + ", ending index: " + end + ".");

            // Replace all elements of the maximum subarray
            // by -infinity. Hence these places cannot form
            // maximum sum subarray again.
            for (int l = start; l <= end; l++)
                arr[l] = Integer.MIN_VALUE;
        }
        System.out.println();
    }

    static void kMaxSumCombinationsFromTwoArrays(int A[], int B[], int N, int K) {
        // Time complexity: O(NLogN) due to the sorting involved, refer kMinSum for
        // O(K*N) solution
        /**
         * Given two equally sized arrays (A, B) and N (size of both arrays). A sum
         * combination is made by adding one element from array A and another element of
         * array B. Display the maximum K valid sum combinations from all the possible
         * sum combinations.
         * 
         * Examples:
         * 
         * Input : A[] : {3, 2} B[] : {1, 4} K : 2 [Number of maximum sum combinations
         * to be printed]
         * 
         * Output : 7 // (A : 3) + (B : 4)
         * 
         * 6 // (A : 2) + (B : 4)
         * 
         * Input : A[] : {4, 2, 5, 1} B[] : {8, 0, 3, 5} K : 3 Output : 13 // (A : 5) +
         * (B : 8)
         * 
         * 12 // (A : 4) + (B : 8)
         * 
         * 10 // (A : 2) + (B : 8)
         */
        /**
         * Approach 2 (Sorting, Max heap, Map) : Instead of brute-forcing through all
         * the possible sum combinations, we should find a way to limit our search space
         * to possible candidate sum combinations.
         * 
         * 1. Sort both arrays array A and array B.
         * 
         * 2. Create a max heap i.e priority_queue in C++ to store the sum combinations
         * along with the indices of elements from both arrays A and B which make up the
         * sum. Heap is ordered by the sum.
         * 
         * 3. Initialize the heap with the maximum possible sum combination i.e (A[N –
         * 1] + B[N – 1] where N is the size of array) and with the indices of elements
         * from both arrays (N – 1, N – 1). The tuple inside max heap will be (A[N-1] +
         * B[N – 1], N – 1, N – 1). Heap is ordered by first value i.e sum of both
         * elements.
         * 
         * 4. Pop the heap to get the current largest sum and along with the indices of
         * the element that make up the sum. Let the tuple be (sum, i, j). Next insert
         * (A[i – 1] + B[j], i – 1, j) and (A[i] + B[j – 1], i, j – 1) into the max heap
         * but make sure that the pair of indices i.e (i – 1, j) and (i, j – 1) are not
         * already present in the max heap.
         * 
         * To check this we can use set in C++. Go back to 4 until K times.
         */
        // sort both arrays A and B
        Arrays.sort(A);
        Arrays.sort(B);

        // Max heap which contains Pair of
        // the format (sum, (i, j)) i and j are
        // the indices of the elements from
        // array A and array B which make up the sum.
        PriorityQueue<PairSum> sums = new PriorityQueue<PairSum>();

        // pairs is used to store the indices of
        // the Pair(i, j) we use pairs to make sure
        // the indices doe not repeat inside max heap.
        HashSet<Pair> pairs = new HashSet<Pair>();

        // initialize the heap with the maximum sum
        // combination ie (A[N - 1] + B[N - 1])
        // and also push indices (N - 1, N - 1) along
        // with sum.
        int l = N - 1;
        int m = N - 1;
        pairs.add(new Pair(l, m));
        sums.add(new PairSum(A[l] + B[m], l, m));

        // iterate upto K
        for (int i = 0; i < K; i++) {
            // Poll the element from the
            // maxheap in theformat (sum, (l,m))
            PairSum max = sums.poll();
            System.out.println(max.sum);
            l = max.l - 1;
            m = max.m;
            // insert only if l and m are greater
            // than 0 and the pair (l, m) is
            // not already present inside set i.e.
            // no repeating pair should be
            // present inside the heap.
            if (l >= 0 && m >= 0 && !pairs.contains(new Pair(l, m))) {
                // insert (A[l]+B[m], (l, m))
                // in the heap
                sums.add(new PairSum(A[l] + B[m], l, m));
                pairs.add(new Pair(l, m));
            }

            l = max.l;
            m = max.m - 1;

            // insert only if l and m are
            // greater than 0 and
            // the pair (l, m) is not
            // already present inside
            // set i.e. no repeating pair
            // should be present
            // inside the heap.
            if (l >= 0 && m >= 0 && !pairs.contains(new Pair(l, m))) {
                // insert (A[i1]+B[i2], (i1, i2))
                // in the heap
                sums.add(new PairSum(A[l] + B[m], l, m));
                pairs.add(new Pair(l, m));
            }
        }
    }

    static void kMinSumCombinationsFromTwoArrays(int arr1[], int arr2[], int k) {
        // Time complexity: O(K*N) time
        /**
         * Given two integer arrays arr1[] and arr2[] sorted in ascending order and an
         * integer k. Find k pairs with smallest sums such that one element of a pair
         * belongs to arr1[] and other element belongs to arr2[] Examples:
         * 
         * Input : arr1[] = {1, 7, 11} arr2[] = {2, 4, 6} k = 3 Output : [1, 2], [1, 4],
         * [1, 6] Explanation: The first 3 pairs are returned from the sequence [1, 2],
         * [1, 4], [1, 6], [7, 2], [7, 4], [11, 2], [7, 6], [11, 4], [11, 6]
         */
        /**
         * Efficient: O(k*n)
         * 
         * We one by one find k smallest sum pairs, starting from least sum pair. The
         * idea is to keep track of all elements of arr2[] which have been already
         * considered for every element arr1[i1] so that in an iteration we only
         * consider next element. For this purpose, we use an index array index2[] to
         * track the indexes of next elements in the other array. It simply means that
         * which element of second array to be added with the element of first array in
         * each and every iteration. We increment value in index array for the element
         * that forms next minimum value pair.
         */
        int n1 = arr1.length;
        int n2 = arr2.length;
        if (k > n1 * n2) {
            System.out.print("k pairs don't exist");
            return;
        }

        // Stores current index in arr2[] for
        // every element of arr1[]. Initially
        // all values are considered 0.
        // Here current index is the index before
        // which all elements are considered as
        // part of output.
        int index2[] = new int[n1];
        // This index tracks the next index of arr2 which should be considered for
        // finding the next min pair.

        while (k > 0) {
            // Initialize current pair sum as infinite
            int min_sum = Integer.MAX_VALUE;
            int min_index = 0;

            // To pick next pair, traverse for all
            // elements of arr1[], for every element, find
            // corresponding current element in arr2[] and
            // pick minimum of all formed pairs.
            for (int i1 = 0; i1 < n1; i1++) {
                // Check if current element of arr1[] plus
                // element of array2 to be used gives
                // minimum sum
                if (index2[i1] < n2 && arr1[i1] + arr2[index2[i1]] < min_sum) {
                    // Update index that gives minimum
                    min_index = i1;

                    // update minimum sum
                    min_sum = arr1[i1] + arr2[index2[i1]];
                }
            }

            System.out.print("(" + arr1[min_index] + ", " + arr2[index2[min_index]] + ") ");
            // arr1: [1,3,11], arr2: [2,4,8]
            // Index:[] for every ith index in arr1, index tracks the next element index in
            // arr2.
            // In the below example, for index 0 (element 1), the next index in arr2 is 2.
            // (element 8)
            // (1,2), Index: 1 0 0, min_sum is 3
            // (1,4), Index: 2 0 0, min_sum is 5
            // (1,9), Index: 2 0 0, min_sum is 9
            // (3,2), Index: 2 1 0, min_sum is 5, which is less than 9 Above
            // (3,4), Index: 2 2 0, min_sum is 7, which is less than 9
            index2[min_index]++;
            k--;
        }

    }

    static void kSmallElementsFromGivenArrayInOriginalOrder(int arr[], int k) {
        // Time complexity: O(NLogN)
        /**
         * We are given an array of m-elements, we need to find n smallest elements from
         * the array but they must be in the same order as they are in given array.
         * Examples:
         * 
         * Input : arr[] = {4, 2, 6, 1, 5}, n = 3 Output : 4 2 1 Explanation : 1, 2 and
         * 4 are 3 smallest numbers and 4 2 1 is their order in given array.
         * 
         * Input : arr[] = {4, 12, 16, 21, 25}, n = 3 Output : 4 12 16 Explanation : 4,
         * 12 and 16 are 3 smallest numbers and 4 12 16 is their order in given array.
         * 
         * Make a copy of original array and then sort copy array. After sorting the
         * copy array, save all n smallest numbers. Further for each element in original
         * array, check whether it is in n-smallest number or not if it present in
         * n-smallest array then print it otherwise move forward.
         * 
         * Make copy_arr[]sort(copy_arr)For all elements in arr[] – Find arr[i] in
         * n-smallest element of copy_arr If found then print the element
         */
        // Make copy of array
        int asize = arr.length;
        int[] copy_arr = Arrays.copyOf(arr, asize);

        // Sort copy array
        Arrays.sort(copy_arr);

        // For each arr[i] find whether
        // it is a part of n-smallest
        // with binary search
        for (int i = 0; i < asize; ++i) {
            // iterate the original array, if the element is found in the binary search
            // in bound k, print it.
            if (Arrays.binarySearch(copy_arr, 0, k, arr[i]) > -1)
                System.out.print(arr[i] + " ");
        }
    }

    static void kSmallElementsFromGivenArrayInOriginalOrder_withNoExtraSpace_usingInsertionSort(int arr[], int k) {
        // The above approach finds the k smallest elements with O(N) extra space,
        // complexity: O(NLogN) times
        /**
         * You are given an array of n-elements you have to find k smallest elements
         * from the array but they must be in the same order as they are in given array
         * and we are allowed to use only O(1) extra space. Examples:
         * 
         * 
         * Input : arr[] = {4, 2, 6, 1, 5}, k = 3 Output : 4 2 1 Explanation : 1, 2 and
         * 4 are three smallest numbers and 4 2 1 is their order in given array
         * 
         * Input : arr[] = {4, 12, 16, 21, 25}, k = 3 Output : 4 12 16 Explanation : 4,
         * 12 and 16 are 3 smallest numbers and 4 12 16 is their order in given array
         */
        /**
         * We have discussed efficient solution to find n smallest elements of above
         * problem with using extra space of O(n). To solve it without using any extra
         * space we will use concept of insertion sort. The idea is to move k minimum
         * elements to beginning in same order. To do this, we start from (k+1)-th
         * element and move till end. For every array element, we replace the largest
         * element of first k elements with the current element if current element is
         * smaller than the largest. To keep the order, we use insertion sort idea.
         */

        // For each arr[i] find whether
        // it is a part of n-smallest
        // with insertion sort concept
        int n = arr.length;
        for (int i = k; i < n; ++i) {
            // Find largest from top n-element
            int max_var = arr[k - 1];
            int pos = k - 1;
            for (int j = k - 2; j >= 0; j--) {
                if (arr[j] > max_var) {
                    // find the largest element in 0...k-1
                    max_var = arr[j];
                    pos = j;
                }
            }

            // If largest is greater than arr[i]
            // shift all element one place left in 0...k-2
            if (max_var > arr[i]) {
                int j = pos;
                while (j < k - 1) {
                    arr[j] = arr[j + 1];
                    j++;
                }
                // make arr[k-1] = arr[i]
                arr[k - 1] = arr[i];
            }
        }
        // print result
        for (int i = 0; i < k; i++)
            System.out.print(arr[i] + " ");
    }

    // returns the upper bound
    static int upperbound(int a[], int n, int value) {
        int low = 0;
        int high = n;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value >= a[mid])
                low = mid + 1;// value is mid, return mid+1
            else
                high = mid;
        }

        return low;
    }

    static int countPairs(int[] a, int n, int mid) {
        /**
         * This method determines the number of pairs with absolute difference less than
         * or equal to mid. This means in an array, for every element, the goal is to
         * find the number of j's such that the absolute difference between a[j]-a[i] =
         * mid, note that a[i]+mid < a[j]
         * 
         * Because this is a sorted array, finding the number of j's can be solved by
         * identifying the upper bound next higher than a[i]+mid. Upper bound returns
         * pointer to position of next higher number than a[i]+mid in a[i...n-1]
         *
         * Number of j's = upper bound position (ub) - (i + 1);
         * 
         * Because we have to return the count of pairs, (&not number of j's), we
         * subtract ub-i-1. Ex., arr[0 1 2 3], suppose ub is 2 and i is 0, mid is 1.
         * number of elements<=mid is 0, 1. Number of pairs is 1(0,1)
         * 
         * So, ub - i - 1 = 2 - 0 - 1 = 1
         */
        int res = 0, value;
        for (int i = 0; i < n; i++) {
            // Upper bound returns pointer to position
            // of next higher number than a[i]+mid in
            // a[i..n-1]. We subtract (ub + i + 1) from
            // this position to count
            int ub = upperbound(a, n, a[i] + mid);
            res += (ub - i - 1);
        }
        return res;
    }

    static int kSmallestAbsoluteDifferenceBwnTwoElementsInAnArray_usingBinarySearch(int a[], int k) {
        // Time complexity is O( n*logn + n*logn*logn)
        /**
         * We are given an array of size n containing positive integers. The absolute
         * difference between values at indices i and j is |a[i] – a[j]|. There are
         * n*(n-1)/2 such pairs and we are asked to print the kth (1 <= k <= n*(n-1)/2)
         * the smallest absolute difference among all these pairs. Examples:
         * 
         * Input : a[] = {1, 2, 3, 4} k = 3 Output : 1 The possible absolute differences
         * are : {1, 2, 3, 1, 2, 1}. The 3rd smallest value among these is 1.
         * 
         * Input : n = 2 a[] = {10, 10} k = 1 Output : 0
         */
        /**
         * An Efficient Solution is based on Binary Search.
         * 
         * 
         * 1) Sort the given array a[].
         * 
         * 2) We can easily find the least possible absolute difference in O(n) after
         * sorting. The largest possible difference will be a[n-1] - a[0] after sorting
         * the array. Let low = minimum_difference and high = maximum_difference.
         * 
         * 3) while low < high:
         * 
         * 4) mid = (low + high)/2
         * 
         * 5) if ((number of pairs with absolute difference <= mid) < k):
         * 
         * 6) low = mid + 1
         * 
         * 7) else:
         * 
         * 8) high = mid
         * 
         * 9) return low
         * 
         * We need a function that will tell us the number of pairs with a difference <=
         * mid efficiently. Since our array is sorted, this part can be done like this:
         * 
         * 1) result = 0
         * 
         * 2) for i = 0 to n-1:
         * 
         * 3) result = result + (upper_bound(a+i, a+n, a[i] + mid) - (a+i+1))
         * 
         * 4) return result
         * 
         * Here upper_bound is a variant of binary search which returns a pointer to the
         * first element from a[i] to a[n-1] which is greater than a[i] + mid. Let the
         * pointer returned be j. Then a[i] + mid < a[j]. Thus, subtracting (a+i+1) from
         * this will give us the number of values whose difference with a[i] is <= mid.
         * We sum this up for all indices from 0 to n-1 and get the answer for the
         * current mid.
         */

        /*
         * The idea is to use binary search to calculate the kth absolute difference.
         * find the min and max difference on sorted array. Take the mid of min, max
         * difference. If the count of pairs is <k, set low=mid+1, else, high=mid. Kth
         * absolute difference is found in left/right part of subarray. Return the low.
         */
        // Sort array
        int n = arr.length;
        Arrays.sort(a);// O(nlogn)

        // Minimum absolute difference
        int low = a[1] - a[0];
        for (int i = 1; i <= n - 2; ++i)
            low = Math.min(low, a[i + 1] - a[i]);

        // Maximum absolute difference
        int high = a[n - 1] - a[0];

        // Do binary search for k-th absolute difference
        while (low < high) { // O(logn *nlogn)
            int mid = (low + high) >> 1;
            if (countPairs(a, n, mid) < k)
                low = mid + 1;
            else
                high = mid;
        }
        // Since we do not know whether MID is an actual difference or not so we can't
        // return anything and we pin point the difference such that either LOW equals
        // high or LOW >HIGH and then we return it.

        return low;

    }

    static void printKFrequentNumbersInArray(int[] arr, int k) {
        /**
         * Given an array of n numbers and a positive integer k. The problem is to find
         * k numbers with most occurrences, i.e., the top k numbers having the maximum
         * frequency. If two numbers have the same frequency then the larger number
         * should be given preference. The numbers should be displayed in decreasing
         * order of their frequencies. It is assumed that the array consists of k
         * numbers with most occurrences.
         * 
         * Examples:
         * 
         * Input: arr[] = {3, 1, 4, 4, 5, 2, 6, 1}, k = 2 Output: 4 1 Explanation:
         * Frequency of 4 = 2 Frequency of 1 = 2 These two have the maximum frequency
         * and 4 is larger than 1.
         * 
         * Input : arr[] = {7, 10, 11, 5, 2, 5, 5, 7, 11, 8, 9}, k = 4 Output: 5 11 7 10
         * Explanation: Frequency of 5 = 3 Frequency of 11 = 2 Frequency of 7 = 2
         * Frequency of 10 = 1 These four have the maximum frequency and 5 is largest
         * among rest.
         */
        /**
         * Algorithm : 1. Create a Hashmap hm, to store key-value pair, i.e.
         * element-frequency pair.
         * 
         * 2. Traverse the array from start to end.
         * 
         * 3. For every element in the array update hm[array[i]]++
         * 
         * 4. Store the element-frequency pair in a Priority Queue and create the
         * Priority Queue, this takes O(n) time.
         * 
         * 5. Run a loop k times, and in each iteration remove the top of the priority
         * queue and print the element.
         */
        Map<Integer, Integer> mp = new HashMap<Integer, Integer>();

        // Put count of all the
        // distinct elements in Map
        // with element as the key &
        // count as the value.
        for (int i = 0; i < n; i++) {

            // Get the count for the
            // element if already
            // present in the Map or
            // get the default value
            // which is 0.
            mp.put(arr[i], mp.getOrDefault(arr[i], 0) + 1);
        }

        // Create a Priority Queue
        // to sort based on the
        // count or on the key if the
        // count is same
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(
                (a, b) -> a.getValue().equals(b.getValue()) ? Integer.compare(b.getKey(), a.getKey())
                        : Integer.compare(b.getValue(), a.getValue()));

        // Insert the data from the map
        // to the Priority Queue.
        for (Map.Entry<Integer, Integer> entry : mp.entrySet())
            queue.offer(entry);

        // Print the top k elements
        for (int i = 0; i < k; i++) {
            System.out.println(queue.poll().getKey());
        }
    }

    static int findSmallestMissingNumberInArray(int[] arr, int start, int end) {
        // Time complexity: O(nlogn)
        /**
         * Given a sorted array of n distinct integers where each integer is in the
         * range from 0 to m-1 and m > n. Find the smallest number that is missing from
         * the array.
         * 
         * Examples
         * 
         * Input: {0, 1, 2, 6, 9}, n = 5, m = 10 Output: 3
         * 
         * Input: {4, 5, 10, 11}, n = 4, m = 12 Output: 0
         * 
         * Input: {0, 1, 2, 3}, n = 4, m = 5 Output: 4
         * 
         * Input: {0, 1, 2, 3, 4, 5, 6, 7, 10}, n = 9, m = 11 Output: 8
         */
        /**
         * Method 1 (Use Binary Search) For i = 0 to m-1, do binary search for i in the
         * array. If i is not present in the array then return i. Time Complexity: O(m
         * log n)
         * 
         * Method 2 (Linear Search) If arr[0] is not 0, return 0. Otherwise traverse the
         * input array starting from index 0, and for each pair of elements a[i] and
         * a[i+1], find the difference between them. if the difference is greater than 1
         * then a[i]+1 is the missing number. Time Complexity: O(n)
         * 
         * In the standard Binary Search process, the element to be searched is compared
         * with the middle element and on the basis of comparison result, we decide
         * whether to search is over or to go to left half or right half. In this
         * method, we modify the standard Binary Search algorithm to compare the middle
         * element with its index and make decision on the basis of this comparison.
         * 
         * If the first element is not same as its index then return first index Else
         * get the middle index say mid If arr[mid] greater than mid then the required
         * element lies in left half. Else the required element lies in right half.
         */
        if (start > end) {
            System.out.println("start: " + start + ", end: " + end);
            return end + 1;
        }
        // [0, 1, 2, 3, 4, 5, 6, 7, 10]
        // After 7, start is 8, arr[8] is 10. Mismatch, return 8
        if (start != arr[start])
            // This is important, if the mid index is not the element in the index, it
            // returns mid.
            return start;

        int mid = (start + end) / 2;

        // Left half has all elements from 0 to mid
        if (arr[mid] == mid)
            return findSmallestMissingNumberInArray(arr, mid + 1, end);

        return findSmallestMissingNumberInArray(arr, start, mid);
    }

    static int maxSumSuchThatElementsAreNotAdjacent(int[] arr) {
        /**
         * Given an array of positive numbers, find the maximum sum of a subsequence
         * with the constraint that no 2 numbers in the sequence should be adjacent in
         * the array. So 3 2 7 10 should return 13 (sum of 3 and 10) or 3 2 5 10 7
         * should return 15 (sum of 3, 5 and 7).Answer the question in most efficient
         * way.
         * 
         * Examples :
         * 
         * Input : arr[] = {5, 5, 10, 100, 10, 5} Output : 110
         * 
         * Input : arr[] = {1, 2, 3} Output : 4
         * 
         * Input : arr[] = {1, 20, 3} Output : 20
         */
        int n = arr.length;
        int incl = arr[0];
        int excl = 0;
        int excl_new;
        int i;

        for (i = 1; i < n; i++) {
            /* current max excluding i */
            excl_new = (incl > excl) ? incl : excl;

            /* current max including i */
            incl = excl + arr[i];
            excl = excl_new;
        }

        /* return max of incl and excl */
        return ((incl > excl) ? incl : excl);
    }

    public static class Pair {

        public Pair(int l, int m) {
            this.l = l;
            this.m = m;
        }

        int l;
        int m;

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair obj = (Pair) o;
            return (l == obj.l && m == obj.m);
        }

        @Override
        public int hashCode() {
            return Objects.hash(l, m);
        }
    }

    public static class PairSum implements Comparable<PairSum> {

        public PairSum(int sum, int l, int m) {
            this.sum = sum;
            this.l = l;
            this.m = m;
        }

        int sum;
        int l;
        int m;

        @Override
        public int compareTo(PairSum o) {
            return Integer.compare(o.sum, sum);
            // This converts the priority queue min heap to max heap.
        }
    }

    // A structure to store entry of heap.
    // The entry contains value from 2D array,
    // row and column numbers of the value
    static class HeapNode {

        // Value to be stored
        int val;

        // Row number of value in 2D array
        int r;

        // Column number of value in 2D array
        int c;

        HeapNode(int val, int r, int c) {
            this.val = val;
            this.c = c;
            this.r = r;
        }

    }

    // A utility function to swap two HeapNode items.
    static void swap(int i, int min, HeapNode[] arr) {
        HeapNode temp = arr[i];
        arr[i] = arr[min];
        arr[min] = temp;
    }

    // A utility function to minheapify the node
    // harr[i] of a heap stored in harr[]
    static void minHeapify(HeapNode harr[], int i, int heap_size) {
        int n = harr.length;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int min = i;

        if (l < heap_size && harr[l].val < harr[min].val) {
            min = l;
        }
        if (r < heap_size && harr[r].val == 0) {
            minHeapify(harr, i, n);
            i--;
        }
    }

    static class MinHeap {
        int[] harr; // pointer to array of elements in heap
        int capacity; // maximum possible size of min heap
        int heap_size; // Current number of elements in min heap

        int parent(int i) {
            return (i - 1) / 2;
        }

        int left(int i) {
            return ((2 * i) + 1);
        }

        int right(int i) {
            return ((2 * i) + 2);
        }

        int getMin() {
            return harr[0];
        } // Returns minimum

        // to replace root with new node x and heapify() new root
        void replaceMax(int x) {
            this.harr[0] = x;
            minHeapify(0);
        }

        MinHeap(int a[], int size) {
            heap_size = size;
            harr = a; // store address of array
            int i = (heap_size - 1) / 2;
            while (i >= 0) {
                minHeapify(i);
                i--;
            }
        }

        // Method to remove maximum element (or root) from min heap
        int extractMin() {
            if (heap_size == 0)
                return Integer.MAX_VALUE;

            // Store the maximum vakue.
            int root = harr[0];

            // If there are more than 1 items, move the last item to root
            // and call heapify.
            if (heap_size > 1) {
                harr[0] = harr[heap_size - 1];
                minHeapify(0);
            }
            heap_size--;
            return root;
        }

        // A recursive method to heapify a subtree with root at given index
        // This method assumes that the subtrees are already heapified
        void minHeapify(int i) {
            int l = left(i);
            int r = right(i);
            int smallest = i;
            if (l < heap_size && harr[l] < harr[i])
                smallest = l;
            if (r < heap_size && harr[r] < harr[smallest])
                smallest = r;
            if (smallest != i) {
                int t = harr[i];
                harr[i] = harr[smallest];
                harr[smallest] = t;
                minHeapify(smallest);
            }
        }
    }

    static class MaxHeap {
        int[] harr; // pointer to array of elements in heap
        int capacity; // maximum possible size of max heap
        int heap_size; // Current number of elements in max heap

        int parent(int i) {
            return (i - 1) / 2;
        }

        int left(int i) {
            return (2 * i + 1);
        }

        int right(int i) {
            return (2 * i + 2);
        }

        int getMax() {
            return harr[0];
        } // Returns maximum

        // to replace root with new node x and heapify() new root
        void replaceMax(int x) {
            this.harr[0] = x;
            maxHeapify(0);
        }

        MaxHeap(int a[], int size) {
            heap_size = size;
            harr = a; // store address of array
            int i = (heap_size - 1) / 2;
            while (i >= 0) {
                maxHeapify(i);
                i--;
            }
        }

        // Method to remove maximum element (or root) from max heap
        int extractMax() {
            if (heap_size == 0)
                return Integer.MAX_VALUE;

            // Store the maximum vakue.
            int root = harr[0];

            // If there are more than 1 items, move the last item to root
            // and call heapify.
            if (heap_size > 1) {
                harr[0] = harr[heap_size - 1];
                maxHeapify(0);
            }
            heap_size--;
            return root;
        }

        // A recursive method to heapify a subtree with root at given index
        // This method assumes that the subtrees are already heapified
        void maxHeapify(int i) {
            int l = left(i);
            int r = right(i);
            int largest = i;
            if (l < heap_size && harr[l] > harr[i])
                largest = l;
            if (r < heap_size && harr[r] > harr[largest])
                largest = r;
            if (largest != i) {
                int t = harr[i];
                harr[i] = harr[largest];
                harr[largest] = t;
                maxHeapify(largest);
            }
        }
    }

    static void testDataKSmallestElementUsingSort() {
        int arr[] = new int[] { 12, 3, 5, 7, 19 };
        int k = 2;
        System.out.print("K'th smallest element is " + kthSmallestElementUsingSort(arr, k));
    }

    static void testDataKSmallestelementUsingMinHeap() {
        int arr[] = { 12, 3, 5, 7, 19 };
        int n = arr.length, k = 2;
        System.out.print("K'th smallest element is " + kthSmallestUsingMinHeap(arr, k));
    }

    static void testDataKSmallestElementUsingMaxHeap() {
        int arr[] = { 12, 3, 5, 7, 19 };
        int n = arr.length, k = 4;
        System.out.print("K'th smallest element is " + kthSmallestelementUsingMaxHeap(arr, k));
    }

    static void testDataKSmallestElementUsingQuickSelectPartition() {
        int arr[] = new int[] { 12, 3, 5, 7, 4, 19, 26 };
        int k = 3;
        System.out.print("K'th smallest element is " + kthSmallestElementsUsingQuickSelect(arr, 0, arr.length - 1, k));
    }

    static void testDataKSmallestElementUsingTreeMap() {
        int k = 2;
        int[] arr = new int[] { 12, 3, 5, 7, 4, 19, 26 };
        System.out.print("K'th smallest element is " + kthSmallestElementsUsingTreeMap(arr, k));
    }

    static void testDataPrintKLargestElementsWithPriorityQueueMinHeap() {
        int arr[] = { 11, 3, 2, 1, 15, 5, 4, 45, 88, 96, 50, 45 };

        // Size of Min Heap
        int k = 3;

        kLargestElementsWithPriorityQueueMinHeap(arr, k);
    }

    static void testDataKthSmallesElementUsingQuickSortRandomPartition_NTime() {
        int arr[] = { 12, 3, 5, 7, 4, 19, 26 };
        int n = arr.length, k = 3;
        System.out.println(
                "K'th smallest element is " + kthSmallesElementUsingQuickSortRandomPartition_NTime(arr, 0, n - 1, k));
    }

    static void testDataKthSmallestElementinSortedMatrix() {
        int mat[][] = { { 10, 20, 30, 40 }, { 15, 25, 35, 45 }, { 25, 29, 37, 48 }, { 32, 33, 39, 50 } };

        int res = kthSmallestElementInSortedMatrix(mat, 4, 7);

        System.out.print("7th smallest element is " + res);
    }

    static void testDataPrint3largest() {
        int arr[] = { 12, 13, 1, 10, 34, 1 };
        int n = arr.length;
        printThreelargestElement(arr, n);
    }

    static void testDataSmallestLargestKElements() {
        int a[] = { 11, 3, 2, 1, 15, 5, 4, 45, 88, 96, 50, 45 };
        int n = a.length;

        int low = 0;
        int high = n - 1;

        // Lets assume k is 3
        int k = 4;

        // Function Call
        smallestLargestKElements(a, low, high, k, n);
    }

    static void testDataFindElementsWhichHaveAtleast2GreaterElements() {
        int arr[] = { 2, -6, 3, 5, 1 };
        int n = arr.length;
        findElementsWhichHaveAtleast2GreaterElements(arr, n);
    }

    static void testDataMinProductOfKIntegersInAnArray() {
        int arr[] = { 198, 76, 544, 123, 154, 675 };
        int k = 2;
        int n = arr.length;
        System.out.print("Minimum product is " + minProductOfKIntegersInAnArray(arr, k));
    }

    static void testDataKthLargestSumInContiguousSubArray() {
        int a[] = new int[] { 10, -10, 20, -40 };
        int n = a.length;
        int k = 6;

        // calls the function to find out the
        // k-th largest sum
        System.out.println(kthLargestSumInContiguousSubArray(a, k));
    }

    static void testDataPrintMedianOfStreamingIntegers() {
        // stream of integers
        int[] arr = new int[] { 5, 15, 10, 20, 3 };
        medianInStreamOfRunningIntegers(arr);
    }

    static void testDataFindKMaximumsInOverlapingContiguousSubarray() {
        int[] input = new int[] { -2, -3, 4, -1, -2, 1, 5, -3 };
        int k = 3;
        kMaxSumsInOverlappingContiguousSubarrays(input, k);
    }

    static void testDataFindMaximumInContiguousSubarray() {
        // Test case 1
        int arr1[] = { -2, -3, 4, -1, -2, 1, 5, -3 };
        int n1 = arr1.length;
        System.out.println(maximumSumInContiguousSubarrayUsingPrefixSum_NTime(arr1));

        // Test case 2
        int arr2[] = { 4, -8, 9, -4, 1, -8, -1, 6 };
        int n2 = arr2.length;
        System.out.println(maximumSumInContiguousSubarrayUsingPrefixSum_NTime(arr2));
    }

    static void testDataKMaxSumCombinationsFromTwoArrays() {
        int A[] = { 1, 4, 2, 3 };
        int B[] = { 2, 5, 1, 6 };
        int N = A.length;
        int K = 4;

        // Function Call
        kMaxSumCombinationsFromTwoArrays(A, B, N, K);
    }

    static void testDataFindKMaximumsInNonOverlappingContiguousSubarraysUsingKadaneAlgorithm() {
        // Test case 1
        int arr1[] = { 4, 1, 1, -1, -3, -5, 6, 2, -6, -2 };
        int k1 = 3;

        // Function calling
        kMaxSumsInNonOverlappingContiguousSubarraysUsingKadaneAlgorithm(arr1, k1);

        // Test case 2
        int arr2[] = { 5, 1, 2, -6, 2, -1, 3, 1 };
        int k2 = 2;

        // Function calling
        kMaxSumsInNonOverlappingContiguousSubarraysUsingKadaneAlgorithm(arr2, k2);
    }

    static void testDataPrintKSmallElementsFromGivenArrayInOriginalOrder() {
        int arr[] = { 1, 5, 8, 9, 6, 7, 3, 4, 2, 0 };
        int k = 5;
        kSmallElementsFromGivenArrayInOriginalOrder(arr, k);
    }

    static void testDatafindKSmallElementsFromGivenArrayInOriginalOrder_withNoExtraSpace() {
        int[] arr = { 1, 5, 8, 9, 6, 7, 3, 4, 2, 0 };
        int n = 10;
        int k = 5;
        kSmallElementsFromGivenArrayInOriginalOrder_withNoExtraSpace_usingInsertionSort(arr, k);
    }

    static void testDatakMinSumCombinationsFromTwoArrays() {
        int arr1[] = { 1, 3, 11 };
        int n1 = arr1.length;

        int arr2[] = { 2, 4, 8 };
        int n2 = arr2.length;

        int k = 4;
        kMinSumCombinationsFromTwoArrays(arr1, arr2, k);
    }

    static void testDataKSmallestAbsDifference() {
        int a[] = { 1, 2, 3, 4 };
        int n = a.length;
        int k = 3;
        System.out.println(kSmallestAbsoluteDifferenceBwnTwoElementsInAnArray_usingBinarySearch(a, k));
    }

    static void testDataKMostFrequentNumbers() {
        int arr[] = { 3, 1, 4, 4, 5, 2, 6, 1 };
        int n = arr.length;
        int k = 2;

        // Function call
        printKFrequentNumbersInArray(arr, k);
    }

    static void testDataFindSmallestMissingNumber() {
        int arr[] = { 0, 1, 2, 3, 4, 5, 6, 7, 10 };
        int n = arr.length;
        System.out.println("First Missing element is : " + findSmallestMissingNumberInArray(arr, 0, n - 1));
    }

    static void testDataFindMaxSumSuchThatElementsAreNotAdjacent() {
        int arr[] = new int[] { 5, 5, 10, 100, 10, 5 };
        System.out.println(maxSumSuchThatElementsAreNotAdjacent(arr));
    }

    public static void main(String[] args) {
        // testDataFindKMaximumsInOverlapingContiguousSubarray();
        testDataFindSmallestMissingNumber();
    }
}
