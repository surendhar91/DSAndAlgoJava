package com.ds.geeks.searchandsort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SearchAlgorithms {
    /**
     * Key points:
     * 
     * 1. LeftMax[], RightMin[] to find the element before which all elements are
     * smaller and after which all elements are greater.
     * FindMinimumPivotInAnArrayWhichIsDecreasingAndIncreasing
     * 
     * 2. Given a key, find the cross over in the sorted array to find the K closest
     * element to the key.
     * 
     * 3. Given almost sorted array where some element are moved to adjacent
     * positions, use binary search to search the data.
     * SearchAlmostSortedDataUsingBS
     * 
     * 4. Use XOR operation to find the distinct element in a duplicate, or find
     * missing number in an array of N elements. a^a = 0, a^0=a
     * 
     * 5. To find a pair with sum, add arr[i] and find sum-arr[i] in hashset
     * 
     * 6. Find that the triplet sum is zero, with i as position, l as i+1, h as n-1,
     * if the sum < 0, increment l, and decrement h otherwise.
     * 
     * 7. Increase, Decrease, and Increase: use binary search. refer:
     * CheckIfReversingSubArrayMakeArraySorted
     * 
     * 8. Use 2Ps to find closest pair in a sorted array or multiple sorted arrays.
     * Use 3Ps to find common elements in three sorted arrays.
     * 
     * 9. In descending order array, count number of ones in array using BS.
     * CountOnesInArrayUsingBS
     * 
     * 10. Find median of two sorted arrays of same or different size using BS by
     * finding median of the arrays and moving the m1 and m2 pointers left and
     * right. m1>m2, start1 to m1, m2 to end2, m2>m1, start2 to m2, m1 to end1
     * 
     * 11. Ceiling and floor search in sorted array using BS.
     *
     * 12. Count number of occurrences in sorted array by finding the key and its
     * index, traverse left and right to find the number of occurrences.
     * 
     * 13. To find missing and repeating elements, traverse all the elements, use
     * its abs value as index and mark its element with -ve sign. If the -ve sign
     * already exists, print the repeating element. For the missing element, find
     * the +ve.
     * 
     * 14. Find maximum pivot point in an increasing and decreasing array using BS.
     * 
     * 15. Find pair with given sum/difference, sort the array in ascending order,
     * and use 2P to find the pair. Or use hashmap to find if sum-arr[i], or
     * diff+arr[i].
     * 
     * 16. To find peak (large) element among neighbors, use BS.
     * 
     * 17. To find elements that appear more than n/k times, use hashmap to store
     * the frequency.
     * 
     * 18. To find position of element in an array of infinite size, double the high
     * as long as the value at high < the key to search for.
     * 
     * 19. In a sorted array with pairs (duplicates), to find the unique/distinct
     * element, use BS. Observe that the first occurrence will be even, second
     * occurrence will be odd before the unique element, after the unique element,
     * it's vice-versa. Use this property in BS to find the unique element.
     * 
     * 20. Another way to ask question 19 is find the element which is observed odd
     * number of times when all elements are seen even number of times.
     * 
     * 21. To find an element in array when the adjacent element's diff is 1, find
     * the abs diff between the key and the current element. And jump to that index,
     * to search again.
     * 
     * 22. To find 3 closest elements (such that difference between them is minimum
     * or zero) from 3 sorted arrays, or to find the smallest difference triplet
     * from 3 arrays, find the min and max of all the 3 arrays on traversal with i,
     * j and k index. Sett the difference between min and max as min_diff identified
     * so far and store the indices. Increment the pointer of the array which
     * contains the minimum so that difference reduces.
     * 
     * 23. To print all consecutive N numbers which Sum to N, start from 1..N/2, use
     * sum variable, slide i.e. add end when sum<N, subtract start when sum>N.
     * increment start and end index.
     * 
     * 24. Each machine takes a[i] sec to produce an item, find how much min time
     * the machines will take to produce mItems. Find the max possible time to
     * produce mItems: a[max]*m. Binary search from 1 to this result, move mid
     * pointer based on whether the number of items produced in mid sec < mItems or
     * not.
     * 
     * 25. Ternary search: use two mid pointers: m1 and m2: l+(r-l)/2, h-(r-l)/2. if
     * key<m1, key is found in l..m1, if key>m2, key is found in m2..h, else m1..m2
     * 
     * 26. To find min cost to make all elements equal in an array, find the low and
     * high from the array. Use ternary search to rule out 2 parts.
     * 
     * 27. To check if sum of two elements is equal to sum of the array, it means
     * basically we have to divide the arrays into equal size. So, we will need to
     * find a pair which is equal to sum/2. If the sum is odd, return false, because
     * we can't find equal element. Add array element to hashset, find if sum-arr[i]
     * is found in the hashset.
     * 
     * 28. To find the index of integer which is greater than x, and greater than or
     * equal to x, define upper and lower bound in BS.
     * 
     * 29. To search in an array of strings where non-empty strings are sorted, use
     * binary search, where you traverse the left and right of the mid
     * simultaneously to find the next non-empty string and set it as mid.
     */

    static class FindKClosestElementToKeyInArrayUsingBS {
        int findCrossOver(int arr[], int low, int high, int x) {
            // Using binary search to find the cross over point, .
            /*
             * Function to find the cross over point (the point before which elements are
             * smaller than or equal to x and after which greater than x)
             * 
             * decreasing and increasing...
             */
            if (x >= arr[high]) {
                return high;// If x is greater than high, then return high
            }
            if (x < arr[low]) {
                return low; // if x is smaller, then return low index
            }
            int mid = (high + low) / 2;

            // if x is same as middle element, then return x
            // i.e. the elements before or mid are smaller than or equal to x, and after mid
            // are greater than x, then return mid.
            if (arr[mid] <= x && arr[mid] + 1 > x) {
                return mid;
            }

            /*
             * If x is greater than arr[mid], then either arr[mid + 1] is ceiling of x or
             * ceiling lies in arr[mid+1...high]
             */
            if (x > arr[mid]) {
                return findCrossOver(arr, mid + 1, high, x);
            }
            return findCrossOver(arr, low, mid - 1, x);
        }

        void printKClosestElement(int arr[], int x, int k) {

            System.out.println("Closest element to X " + x + " were ");
            int count = 0;
            // find the cross over for element x
            int l = findCrossOver(arr, 0, arr.length - 1, x);
            int r = l + 1;
            int n = arr.length;

            if (arr[l] == x)
                l--;// don't include x element in the result

            while (l >= 0 && r < n && count < k) {
                if (x - arr[l] < arr[r] - x) {
                    System.out.println(arr[l--]);
                } else {
                    System.out.println(arr[r++]);
                }
                count++;
            }

            while (l >= 0 && count < k) {
                // if there are more elements on left side, print them
                System.out.println(arr[l--]);
                count++;
            }

            while (r < n && count < k) {
                System.out.println(arr[r++]);
                count++;
            }

        }

        void printKClosestElementTestData() {
            int arr[] = { 12, 16, 22, 30, 35, 39, 42, 45, 48, 50, 53, 55, 56 };
            int x = 35, k = 4;
            printKClosestElement(arr, x, k);
        }
    }

    static class searchAlmostSortedDataUsingBS {
        int binaryAlmostSortSearch(int arr[], int low, int high, int x) {

            if (low <= high) {

                int mid = (low + (high - low)) / 2;

                if (arr[mid] == x)
                    return mid;

                if (mid < high && arr[mid + 1] == x)// search for next to mid (arr[mid+1])
                    return mid + 1;

                if (mid > low && arr[mid - 1] == x)// search for next to mid (arr[mid-1])
                    return mid - 1;

                if (arr[mid] < x) {
                    return binaryAlmostSortSearch(arr, mid + 2, high, x);// skip by 2.
                }

                return binaryAlmostSortSearch(arr, low, mid - 2, x);

            }
            // when we were not able to find the element, we will reach here..
            return -1;

        }

        void searchAlmostSortedArrayTestData() {

            /*
             * 
             * Given an array which is sorted, but after sorting some elements are moved to
             * either of the adjacent positions, i.e., arr[i] may be present at arr[i+1] or
             * arr[i-1]. Write an efficient function to search an element in this array.
             * Basically the element arr[i] can only be swapped with either arr[i+1] or
             * arr[i-1].
             * 
             * For example consider the array {2, 3, 10, 4, 40}, 4 is moved to next position
             * and 10 is moved to previous position.
             * 
             * Example:
             * 
             * Input: arr[] = {10, 3, 40, 20, 50, 80, 70}, key = 40 Output: 2 Output is
             * index of 40 in given array
             * 
             * Input: arr[] = {10, 3, 40, 20, 50, 80, 70}, key = 90 Output: -1 -1 is
             * returned to indicate element is not present
             * 
             */
            int arr[] = { 3, 2, 10, 4, 40 };
            int n = arr.length;
            int x = 4;
            int result = binaryAlmostSortSearch(arr, 0, n - 1, x);
            if (result == -1) {
                System.out.println("Element is not present in array");
            } else {
                System.out.println("Element is present at index " + result);
            }

        }
    }

    static class ClosestPairUsing2P {
        // ar1[0..m-1] and ar2[0..n-1] are two given sorted
        // arrays/ and x is given number. This function prints
        // the pair from both arrays such that the sum of the
        // pair is closest to x.
        void printClosestPairsInTwoArrays(int ar1[], int ar2[], int m, int n, int x) {
            // Initialize the diff between pair sum and x.
            int diff = Integer.MAX_VALUE;

            // res_l and res_r are result indexes from ar1[] and ar2[]
            // respectively
            int res_l = 0, res_r = 0;

            // Start from left side of ar1[] and right side of ar2[]
            int l = 0, r = n - 1;
            while (l < m && r >= 0) {
                // If this pair is closer to x than the previously
                // found closest, then update res_l, res_r and diff
                if (Math.abs(ar1[l] + ar2[r] - x) < diff) {
                    res_l = l;
                    res_r = r;
                    diff = Math.abs(ar1[l] + ar2[r] - x);
                }

                // If sum of this pair is more than x, move to smaller
                // side
                if (ar1[l] + ar2[r] > x)
                    r--;
                else // move to the greater side
                    l++;
            }

            // Print the result
            System.out.print("The closest pair is [" + ar1[res_l] + ", " + ar2[res_r] + "]");
        }

        // Prints the pair with sum cloest to x
        static void printClosestPairInArray(int arr[], int n, int x) {
            int res_l = 0, res_r = 0; // To store indexes of result pair

            // Initialize left and right indexes and difference between
            // pair sum and x
            int l = 0, r = n - 1, diff = Integer.MAX_VALUE;

            // While there are elements between l and r
            while (r > l) {
                // Check if this pair is closer than the closest pair so far
                if (Math.abs(arr[l] + arr[r] - x) < diff) {
                    res_l = l;
                    res_r = r;
                    diff = Math.abs(arr[l] + arr[r] - x);
                }

                // If this pair has more sum, move to smaller values.
                if (arr[l] + arr[r] > x)
                    r--;
                else // Move to larger values
                    l++;
            }

            System.out.println(" The closest pair is " + arr[res_l] + " and " + arr[res_r]);
        }

        static void testDataPrintClosestPairInTwoSortedArrays() {
            ClosestPairUsing2P ob = new ClosestPairUsing2P();
            int ar1[] = { 1, 4, 5, 7 };
            int ar2[] = { 10, 20, 30, 40 };
            int m = ar1.length;
            int n = ar2.length;
            int x = 38;
            ob.printClosestPairsInTwoArrays(ar1, ar2, m, n, x);
        }

        static void testDataPrintClosestPairInSortedArray() {
            int arr[] = { 10, 22, 28, 29, 30, 40 }, x = 54;
            int n = arr.length;
            printClosestPairInArray(arr, n, x);
        }
    }

    static class FindCommonElementsInThreeSortedArraysUsing3P {
        // This function prints common elements in ar1
        void findCommon(int ar1[], int ar2[], int ar3[]) {
            // Initialize starting indexes for ar1[], ar2[] and ar3[]
            int i = 0, j = 0, k = 0;

            // Iterate through three arrays while all arrays have elements
            while (i < ar1.length && j < ar2.length && k < ar3.length) {
                // If x = y and y = z, print any of them and move ahead
                // in all arrays
                if (ar1[i] == ar2[j] && ar2[j] == ar3[k]) {
                    System.out.print(ar1[i] + " ");
                    i++;
                    j++;
                    k++;
                }

                // x < y
                else if (ar1[i] < ar2[j])
                    i++;

                // y < z
                else if (ar2[j] < ar3[k])
                    j++;

                // We reach here when x > y and z < y, i.e., z is smallest
                else
                    k++;
            }
        }

        static void testDataFindCommonElements() {
            FindCommonElementsInThreeSortedArraysUsing3P ob = new FindCommonElementsInThreeSortedArraysUsing3P();

            int ar1[] = { 1, 5, 10, 20, 40, 80 };
            int ar2[] = { 6, 7, 20, 80, 100 };
            int ar3[] = { 3, 4, 15, 20, 30, 70, 80, 120 };

            System.out.print("Common elements are ");
            ob.findCommon(ar1, ar2, ar3);
        }
    }

    static class CountOnesInArrayUsingBS {
        /*
         * Returns counts of 1's in arr[low..high]. The array is assumed to be sorted in
         * non-increasing order
         */
        int countOnes(int arr[], int low, int high) {
            if (high >= low) {
                // get the middle index
                int mid = low + (high - low) / 2;

                // check if the element at middle index is last
                // 1
                if ((mid == high || arr[mid + 1] == 0) && (arr[mid] == 1))
                    return mid + 1;

                // If element is not last 1, recur for right
                // side
                if (arr[mid] == 1)
                    return countOnes(arr, (mid + 1), high);

                // else recur for left side
                return countOnes(arr, low, (mid - 1));
            }
            return 0;
        }

        static void testDataCountOnes() {
            CountOnesInArrayUsingBS ob = new CountOnesInArrayUsingBS();
            int arr[] = { 1, 1, 1, 1, 0, 0, 0 };
            int n = arr.length;
            System.out.println("Count of 1's in given array is " + ob.countOnes(arr, 0, n - 1));
        }
    }

    static class FindMissingNumberInArrayWithElementsN {
        /**
         * You are given a list of n-1 integers and these integers are in the range of 1
         * to n. There are no duplicates in the list. One of the integers is missing in
         * the list. Write an efficient code to find the missing integer. Example:
         * 
         * Input: arr[] = {1, 2, 4, 6, 3, 7, 8} Output: 5 Explanation: The missing
         * number from 1 to 8 is 5
         * 
         * Input: arr[] = {1, 2, 3, 5} Output: 4 Explanation: The missing number from 1
         * to 5 is 4
         * 
         * Method 2: This method uses the technique of XOR to solve the problem.
         * 
         * Approach: XOR has certain properties Assume a1 ^ a2 ^ a3 ^ …^ an = a and a1 ^
         * a2 ^ a3 ^ …^ an-1 = b Then a ^ b = an Algorithm: Create two variables a = 0
         * and b = 0 Run a loop from 1 to n with i as counter. For every index update a
         * as a = a ^ i Now traverse the array from start to end. For every index update
         * b as b = b ^ array[i] Print the missing number as a ^ b.
         */
        // Function to find missing number
        static int getMissingNo(int a[], int n) {
            int x1 = a[0];
            int x2 = 1;

            /*
             * For xor of all the elements in array
             */
            for (int i = 1; i < n; i++)
                x1 = x1 ^ a[i];

            /*
             * For xor of all the elements from 1 to n+1
             */
            for (int i = 2; i <= n + 1; i++)
                x2 = x2 ^ i;

            return (x1 ^ x2);
        }

        static void testDataFindMissingNumber() {
            int a[] = { 1, 2, 4, 5, 6 };
            int miss = getMissingNo(a, 5);
            System.out.println(miss);
        }
    }

    static class FindMedianOfTwoSortedArraysOfSameSizeUsingBS {
        // Time complexity: O(logn)
        /**
         * This method works by first getting medians of the two sorted arrays and then
         * comparing them. Let ar1 and ar2 be the input arrays.
         * 
         * 
         * 1) Calculate the medians m1 and m2 of the input arrays ar1[] and ar2[]
         * respectively. 2) If m1 and m2 both are equal then we are done. return m1 (or
         * m2) 3) If m1 is greater than m2, then median is present in one of the below
         * two subarrays. a) From first element of ar1 to m1 (ar1[0...|_n/2_|]) b) From
         * m2 to last element of ar2 (ar2[|_n/2_|...n-1]) 4) If m2 is greater than m1,
         * then median is present in one of the below two subarrays. a) From m1 to last
         * element of ar1 (ar1[|_n/2_|...n-1]) b) From first element of ar2 to m2
         * (ar2[0...|_n/2_|]) 5) Repeat the above process until size of both the
         * subarrays becomes 2. 6) If size of the two arrays is 2 then use below formula
         * to get the median. Median = (max(ar1[0], ar2[0]) + min(ar1[1], ar2[1]))/2
         * 
         * Examples :
         * 
         * ar1[] = {1, 12, 15, 26, 38} ar2[] = {2, 13, 17, 30, 45}
         * 
         * For above two arrays m1 = 15 and m2 = 17 For the above ar1[] and ar2[], m1 is
         * smaller than m2. So median is present in one of the following two subarrays.
         * 
         * [15, 26, 38] and [2, 13, 17]
         * 
         * Let us repeat the process for above two subarrays:
         * 
         * m1 = 26 m2 = 13.
         * 
         * m1 is greater than m2. So the subarrays become
         * 
         * [15, 26] and [13, 17] Now size is 2, so median = (max(ar1[0], ar2[0]) +
         * min(ar1[1], ar2[1]))/2 = (max(15, 13) + min(26, 17))/2 = (15 + 17)/2 = 16
         */
        /*
         * This function returns median of ar1[] and ar2[]. Assumptions in this
         * function: Both ar1[] and ar2[] are sorted arrays Both have n elements
         */

        static int getMedian(int[] a, int[] b, int startA, int startB, int endA, int endB) {
            if (endA - startA == 1) {
                return (Math.max(a[startA], b[startB]) + Math.min(a[endA], b[endB])) / 2;
            }
            /*
             * get the median of the first array
             */
            int m1 = median(a, startA, endA);

            /*
             * get the median of the second array
             */
            int m2 = median(b, startB, endB);

            /*
             * If medians are equal then return either m1 or m2
             */
            if (m1 == m2) {
                return m1;
            }

            /*
             * if m1 < m2 then median must exist in ar1[m1....] and ar2[....m2]
             */
            else if (m1 < m2) {
                return getMedian(a, b, (endA + startA + 1) / 2, startB, endA, (endB + startB + 1) / 2);
            }

            /*
             * if m1 > m2 then median must exist in ar1[....m1] and ar2[m2...]
             */
            else {
                return getMedian(a, b, startA, (endB + startB + 1) / 2, (endA + startA + 1) / 2, endB);
            }
        }

        /*
         * Function to get median of a sorted array
         */
        static int median(int[] arr, int start, int end) {
            int n = end - start + 1;
            if (n % 2 == 0) {
                return (arr[start + (n / 2)] + arr[start + (n / 2 - 1)]) / 2;
            } else {
                return arr[start + n / 2];
            }
        }
    }

    static class TwoElementsWhoseSumIsClosestToZeroInArrayOfPositiveAndNegativeIntegersUsing2P {
        /**
         * Question: An Array of integers is given, both +ve and -ve. You need to find
         * the two elements such that their sum is closest to zero. For the below array,
         * program should print -80 and 85.
         */
        /**
         * Algorithm 1) Sort all the elements of the input array. 2) Use two index
         * variables l and r to traverse from left and right ends respectively.
         * Initialize l as 0 and r as n-1. 3) sum = a[l] + a[r] 4) If sum is -ve, then
         * l++ 5) If sum is +ve, then r– 6) Keep track of abs min sum. 7) Repeat steps
         * 3, 4, 5 and 6 while l < r
         */
        static void minAbsSumPair(int arr[], int n) {
            // Variables to keep track of current sum and minimum sum
            int sum, min_sum = 999999;

            // left and right index variables
            int l = 0, r = n - 1;

            // variable to keep track of the left and right pair for min_sum
            int min_l = l, min_r = n - 1;

            /* Array should have at least two elements */
            if (n < 2) {
                System.out.println("Invalid Input");
                return;
            }

            /* Sort the elements */
            Arrays.sort(arr, l, r);

            while (l < r) {
                sum = arr[l] + arr[r];

                /* If abs(sum) is less then update the result items */
                if (Math.abs(sum) < Math.abs(min_sum)) {
                    min_sum = sum;
                    min_l = l;
                    min_r = r;
                }
                if (sum < 0)
                    l++;
                else
                    r--;
            }

            System.out.println(" The two elements whose " + "sum is minimum are " + arr[min_l] + " and " + arr[min_r]);
        }

    }

    static class CeilingSearchInSortedArrayUsingBS {
        /*
         * Function to get index of ceiling of x in arr[low..high]
         */
        // https://www.geeksforgeeks.org/ceiling-in-a-sorted-array/
        static int ceilSearch(int arr[], int low, int high, int x) {
            int mid;

            /*
             * If x is smaller than or equal to the first element, then return the first
             * element
             */
            if (x <= arr[low]) //
                return low;

            /*
             * If x is greater than the last element, then return -1
             */
            if (x > arr[high])
                return -1;

            /*
             * get the index of middle element of arr[low..high]
             */
            mid = (low + high) / 2; /* low + (high - low)/2 */

            /*
             * If x is same as middle element, then return mid
             */
            if (arr[mid] == x)
                return mid;

            /*
             * If x is greater than arr[mid], then either arr[mid + 1] is ceiling of x or
             * ceiling lies in arr[mid+1...high]
             */
            else if (arr[mid] < x) {
                if (mid + 1 <= high && x <= arr[mid + 1])
                    return mid + 1;
                else
                    return ceilSearch(arr, mid + 1, high, x);
            }

            /*
             * If x is smaller than arr[mid], then either arr[mid] is ceiling of x or
             * ceiling lies in arr[low...mid-1]
             */
            else {
                if (mid - 1 >= low && x > arr[mid - 1])
                    return mid;
                else
                    return ceilSearch(arr, low, mid - 1, x);
            }
        }

        /* Driver program to check above functions */
        static void testDataCeilingSearchInSortedArray() {
            int arr[] = { 1, 2, 8, 10, 10, 12, 19 };
            int n = arr.length;
            int x = 8;
            int index = ceilSearch(arr, 0, n - 1, x);
            if (index == -1)
                System.out.println("Ceiling of " + x + " doesn't exist in array");
            else
                System.out.println("ceiling of " + x + " is " + arr[index]);
        }
    }

    static class FloorSearchInSortedArrayUsingBS {
        /**
         * Given a sorted array and a value x, the floor of x is the largest element in
         * array smaller than or equal to x. Write efficient functions to find floor of
         * x. Examples:
         * 
         * 
         * Input : arr[] = {1, 2, 8, 10, 10, 12, 19}, x = 5 Output : 2 2 is the largest
         * element in arr[] smaller than 5.
         * 
         * Input : arr[] = {1, 2, 8, 10, 10, 12, 19}, x = 20 Output : 19 19 is the
         * largest element in arr[] smaller than 20.
         * 
         * Input : arr[] = {1, 2, 8, 10, 10, 12, 19}, x = 0 Output : -1 Since floor
         * doesn't exist, output is -1.
         */
        /**
         * Approach:There is a catch in the problem, the given array is sorted. The idea
         * is to use Binary Search to find the floor of a number x in a sorted array by
         * comparing it to the middle element and dividing the search space into half.
         * Algorithm:
         * 
         * 
         * The algorithm can be implemented recursively or through iteration, but the
         * basic idea remains the same. There is come base cases to handle. If there is
         * no number greater than x then print the last element If the first number is
         * greater than x then print -1 create three variables low = 0, mid and high =
         * n-1 and another variable to store the answer Run a loop or recurse until and
         * unless low is less than or equal to high. check if the middle ( (low + high)
         * /2) element is less than x, if yes then update the low, i.elow = mid + 1, and
         * update answer with the middle element. In this step we are reducing the
         * search space to half. Else update the low, i.ehigh = mid – 1 Print the
         * answer.
         */
        /*
         * Function to get index of floor of x in arr[low..high]
         */
        static int floorSearch(int arr[], int low, int high, int x) {
            // If low and high cross each other
            if (low > high)
                return -1;

            // If last element is smaller than x
            if (x >= arr[high])
                return high;

            // Find the middle point
            int mid = (low + high) / 2;

            // If middle point is floor.
            if (arr[mid] == x)
                return mid;

            // If x lies between mid-1 and mid
            if (mid > 0 && arr[mid - 1] <= x && x < arr[mid])
                return mid - 1;

            // If x is smaller than mid, floor
            // must be in left half.
            if (x < arr[mid])
                return floorSearch(arr, low, mid - 1, x);

            // If mid-1 is not floor and x is
            // greater than arr[mid],
            return floorSearch(arr, mid + 1, high, x);
        }

        /* Driver program to check above functions */
        public static void testDataFloorSearch() {
            int arr[] = { 1, 2, 4, 6, 10, 12, 14 };
            int n = arr.length;
            int x = 7;
            int index = floorSearch(arr, 0, n - 1, x);
            if (index == -1)
                System.out.println("Floor of " + x + " dosen't exist in array ");
            else
                System.out.println("Floor of " + x + " is " + arr[index]);
        }
    }

    static class CountNumberOfOccurrencesInSortedArrayUsingBS {
        /**
         * Given a sorted array arr[] and a number x, write a function that counts the
         * occurrences of x in arr[]. Expected time complexity is O(Logn)
         * 
         * Examples:
         * 
         * Input: arr[] = {1, 1, 2, 2, 2, 2, 3,}, x = 2 Output: 4 // x (or 2) occurs 4
         * times in arr[]
         * 
         * Input: arr[] = {1, 1, 2, 2, 2, 2, 3,}, x = 3 Output: 1
         * 
         * Input: arr[] = {1, 1, 2, 2, 2, 2, 3,}, x = 1 Output: 2
         * 
         * Input: arr[] = {1, 1, 2, 2, 2, 2, 3,}, x = 4 Output: -1 // 4 doesn't occur in
         * arr[]
         */
        // A recursive binary search
        // function. It returns location
        // of x in given array arr[l..r]
        // is present, otherwise -1
        static int binarySearch(int arr[], int l, int r, int x) {
            if (r < l)
                return -1;

            int mid = l + (r - l) / 2;

            // If the element is present
            // at the middle itself
            if (arr[mid] == x)
                return mid;

            // If element is smaller than
            // mid, then it can only be
            // present in left subarray
            if (arr[mid] > x)
                return binarySearch(arr, l, mid - 1, x);

            // Else the element can
            // only be present in
            // right subarray
            return binarySearch(arr, mid + 1, r, x);
        }

        // Returns number of times x
        // occurs in arr[0..n-1]
        static int countOccurrences(int arr[], int n, int x) {
            int ind = binarySearch(arr, 0, n - 1, x);

            // If element is not present
            if (ind == -1)
                return 0;

            // Count elements on left side.
            int count = 1;
            int left = ind - 1;
            while (left >= 0 && arr[left] == x) {
                count++;
                left--;
            }

            // Count elements
            // on right side.
            int right = ind + 1;
            while (right < n && arr[right] == x) {
                count++;
                right++;
            }

            return count;
        }

        static void testDataCountOccurrences() {
            int arr[] = { 1, 2, 2, 2, 2, 3, 4, 7, 8, 8 };
            int n = arr.length;
            int x = 2;
            System.out.print(countOccurrences(arr, n, x));
        }
    }

    static class FindMissingAndRepeatingNumbersInArrayWithElementsN {
        /**
         * Given an unsorted array of size n. Array elements are in the range from 1 to
         * n. One number from set {1, 2, …n} is missing and one number occurs twice in
         * the array. Find these two numbers.
         * 
         * Examples:
         * 
         * Input: arr[] = {3, 1, 3} Output: Missing = 2, Repeating = 3 Explanation: In
         * the array, 2 is missing and 3 occurs twice
         * 
         * Input: arr[] = {4, 3, 6, 2, 1, 1} Output: Missing = 5, Repeating = 1
         */
        /**
         * Method 2 (Use count array) Approach:
         * 
         * Create a temp array temp[] of size n with all initial values as 0. Traverse
         * the input array arr[], and do following for each arr[i] if(temp[arr[i]] == 0)
         * temp[arr[i]] = 1; if(temp[arr[i]] == 1) output “arr[i]” //repeating Traverse
         * temp[] and output the array element having value as 0 (This is the missing
         * element)
         * 
         * Time Complexity: O(n)
         */
        /**
         * Method 3 (Use elements as Index and mark the visited places) Approach:
         * Traverse the array. While traversing, use the absolute value of every element
         * as an index and make the value at this index as negative to mark it visited.
         * If something is already marked negative then this is the repeating element.
         * To find missing, traverse the array again and look for a positive value.
         */
        static void printTwoElements(int arr[], int size) {
            int i;
            System.out.print("The repeating element is ");

            for (i = 0; i < size; i++) {
                int abs_val = Math.abs(arr[i]);
                if (arr[abs_val - 1] > 0)
                    arr[abs_val - 1] = -arr[abs_val - 1];
                else
                    System.out.println(abs_val);
            }

            System.out.print("And the missing element is ");
            for (i = 0; i < size; i++) {
                if (arr[i] > 0)
                    System.out.println(i + 1);
            }
        }
    }

    static class FindFixedPointInArrayThatIndexEqualToElement {
        /**
         * First check whether middle element is Fixed Point or not. If it is, then
         * return it; otherwise check whether index of middle element is greater than
         * value at the index. If index is greater, then Fixed Point(s) lies on the
         * right side of the middle point (obviously only if there is a Fixed Point).
         * Else the Fixed Point(s) lies on left side.
         */
        static int binarySearch(int arr[], int low, int high) {
            if (high >= low) {
                /* low + (high - low)/2; */
                int mid = (low + high) / 2;
                if (mid == arr[mid])
                    return mid;
                if (mid > arr[mid])
                    return binarySearch(arr, (mid + 1), high);
                else
                    return binarySearch(arr, low, (mid - 1));
            }

            /*
             * Return -1 if there is no Fixed Point
             */
            return -1;
        }

        static void testDataFindFixedPointInArray() {
            int arr[] = { -10, -1, 0, 3, 10, 11, 30, 50, 100 };
            int n = arr.length;
            System.out.println("Fixed Point is " + binarySearch(arr, 0, n - 1));
        }
    }

    static class FindMaximumPivotInAnArrayWhichIsIncreasingAndDecreasing {
        // Find the peak.
        /**
         * Given an array of integers which is initially increasing and then decreasing,
         * find the maximum value in the array. Examples :
         * 
         * 
         * Input: arr[] = {8, 10, 20, 80, 100, 200, 400, 500, 3, 2, 1} Output: 500
         * 
         * Input: arr[] = {1, 3, 50, 10, 9, 7, 6} Output: 50
         * 
         * Corner case (No decreasing part) Input: arr[] = {10, 20, 30, 40, 50} Output:
         * 50
         * 
         * Corner case (No increasing part) Input: arr[] = {120, 100, 80, 20, 0} Output:
         * 120
         */
        // function to find the
        // maximum element
        static int findMaximum(int arr[], int low, int high) {

            /*
             * Base Case: Only one element is present in arr[low..high]
             */
            if (low == high)
                return arr[low];

            /*
             * If there are two elements and first is greater then the first element is
             * maximum
             */
            if ((high == low + 1) && arr[low] >= arr[high])
                return arr[low];

            /*
             * If there are two elements and second is greater then the second element is
             * maximum
             */
            if ((high == low + 1) && arr[low] < arr[high])
                return arr[high];

            /* low + (high - low)/2; */
            int mid = (low + high) / 2;

            /*
             * If we reach a point where arr[mid] is greater than both of its adjacent
             * elements arr[mid-1] and arr[mid+1], then arr[mid] is the maximum element
             */
            if (arr[mid] > arr[mid + 1] && arr[mid] > arr[mid - 1])
                return arr[mid];

            /*
             * If arr[mid] is greater than the next element and smaller than the previous
             * element then maximum lies on left side of mid
             */

            if (arr[mid] > arr[mid + 1] && arr[mid] < arr[mid - 1])
                // 1 3 5 10 9 7 6 4, if the mid is in the decreasing part, let's say 7 is mid,
                // 7(mid)>6(mid+1), 7(mid)<9(mid-1)
                return findMaximum(arr, low, mid - 1);
            else
                // If the mid is in the increasing part. 80 100 200, mid is 100, mid>mid-1,
                // mid<mid+1
                return findMaximum(arr, mid + 1, high);
        }

        static void testDataFindMaximumInIncreasingAndDecreasingArray() {
            int arr[] = { 1, 3, 50, 10, 9, 7, 6 };
            int n = arr.length;
            System.out.println("The maximum element is " + findMaximum(arr, 0, n - 1));
        }
    }

    static class FindMinimumPivotInAnArrayWhichIsDecreasingAndIncreasing {
        /**
         * Given an array, find an element before which all elements are smaller than
         * it, and after which all are greater than it. Return the index of the element
         * if there is such an element, otherwise, return -1.
         * 
         * Examples:
         * 
         * Input: arr[] = {5, 1, 4, 3, 6, 8, 10, 7, 9}; Output: 4 Explanation: All
         * elements on left of arr[4] are smaller than it and all elements on right are
         * greater.
         * 
         * Input: arr[] = {5, 1, 4, 4}; Output: -1 Explanation : No such index exits.
         * 
         * Expected time complexity: O(n).
         * 
         * 
         * An Efficient Solution can solve this problem in O(n) time using O(n) extra
         * space. Below is the detailed solution.
         * 
         * Create two arrays leftMax[] and rightMin[]. Traverse input array from left to
         * right and fill leftMax[] such that leftMax[i] contains a maximum element from
         * 0 to i-1 in the input array. Traverse input array from right to left and fill
         * rightMin[] such that rightMin[i] contains a minimum element from to n-1 to
         * i+1 in the input array. Traverse input array. For every element arr[i], check
         * if arr[i] is greater than leftMax[i] and smaller than rightMin[i]. If yes,
         * return i.
         */
        static int findElement(int[] arr, int n) {
            // leftMax[i] stores maximum of arr[0..i-1]
            int[] leftMax = new int[n];
            leftMax[0] = Integer.MIN_VALUE;

            // Fill leftMax[]1..n-1]
            for (int i = 1; i < n; i++)
                leftMax[i] = Math.max(leftMax[i - 1], arr[i - 1]);

            // Initialize minimum from right
            int rightMin = Integer.MAX_VALUE;

            // Traverse array from right
            for (int i = n - 1; i >= 0; i--) {
                // Check if we found a required element
                if (leftMax[i] < arr[i] && rightMin > arr[i])
                    return i;

                // Update right minimum
                rightMin = Math.min(rightMin, arr[i]);
            }

            // If there was no element matching criteria
            return -1;

        }

        // Driver code
        public static void main(String args[]) {
            int[] arr = { 5, 1, 4, 3, 6, 8, 10, 7, 9 };
            int n = arr.length;
            System.out.println("Index of the element is " + findElement(arr, n));
        }
    }

    static class FindPairWithGivenDifference {
        /**
         * Given an unsorted array and a number n, find if there exists a pair of
         * elements in the array whose difference is n. Examples:
         * 
         * 
         * Input: arr[] = {5, 20, 3, 2, 50, 80}, n = 78 Output: Pair Found: (2, 80)
         * 
         * Input: arr[] = {90, 70, 20, 80, 50}, n = 45 Output: No Such Pair
         */
        /**
         * We can use sorting and Binary Search to improve time complexity to O(nLogn).
         * The first step is to sort the array in ascending order. Once the array is
         * sorted, traverse the array from left to right, and for each element arr[i],
         * binary search for arr[i] + n in arr[i+1..n-1]. If the element is found,
         * return the pair. Both first and second steps take O(nLogn). So overall
         * complexity is O(nLogn). The second step of the above algorithm can be
         * improved to O(n). The first step remain same. The idea for second step is
         * take two index variables i and j, initialize them as 0 and 1 respectively.
         * Now run a linear loop. If arr[j] – arr[i] is smaller than n, we need to look
         * for greater arr[j], so increment j. If arr[j] – arr[i] is greater than n, we
         * need to look for greater arr[i], so increment i.
         */
        // The function assumes that the array is sorted
        static boolean findPair(int arr[], int n) {
            int size = arr.length;

            // Initialize positions of two elements
            int i = 0, j = 1;

            // Search for a pair
            while (i < size && j < size) {
                if (i != j && arr[j] - arr[i] == n) {
                    System.out.print("Pair Found: " + "( " + arr[i] + ", " + arr[j] + " )");
                    return true;
                } else if (arr[j] - arr[i] < n)
                    j++;
                else
                    i++;
            }

            System.out.print("No such pair");
            return false;
        }

        // Driver program to test above function
        public static void testDataFindPairWithGivenDifference() {
            int arr[] = { 1, 8, 30, 40, 100 };
            int n = 60;
            findPair(arr, n);
        }
    }

    static class FindMedianOfTwoSortedArraysOfDifferentSize {
        /**
         * Î* Approach: The idea is simple, calculate the median of both the arrays and
         * discard one half of each array. Now, there are some basic corner cases. For
         * array size less than or equal to 2
         * 
         * Suppose there are two arrays and the size of both the arrays is greater than
         * 2. Find the middle element of the first array and middle element of the
         * second array (the first array is smaller than the second) if the middle
         * element of the smaller array is less than the second array, then it can be
         * said that all elements of the first half of smaller array will be in the
         * first half of the output (merged array). So, reduce the search space by
         * ignoring the first half of the smaller array and the second half of the
         * larger array. Else ignore the second half of the smaller array and first half
         * of a larger array.
         * 
         * In addition to that there are more basic corner cases:
         * 
         * If the size of smaller array is 0. Return the median of a larger array. if
         * the size of smaller array is 1. The size of the larger array is also 1.
         * Return the median of two elements. If the size of the larger array is odd.
         * Then after adding the element from 2nd array, it will be even so the median
         * will be an average of two mid elements. So the element from the smaller array
         * will affect the median if and only if it lies between (m/2 – 1)th and (m/2 +
         * 1)th element of the larger array. So, find the median in between the four
         * elements, the element of the smaller array and (m/2)th, (m/2 – 1)th and (m/2
         * + 1)th element of a larger array Similarly, if the size is even, then check
         * for the median of three elements, the element of the smaller array and
         * (m/2)th, (m/2 – 1)th element of a larger array If the size of smaller array
         * is 2 If the larger array also has two elements, find the median of four
         * elements. If the larger array has an odd number of elements, then the median
         * will be one of the following 3 elements Middle element of larger array Max of
         * the first element of smaller array and element just before the middle, i.e
         * M/2-1th element in a bigger array Min of the second element of smaller array
         * and element just after the middle in the bigger array, i.e M/2 + 1th element
         * in the bigger array If the larger array has even number of elements, then the
         * median will be one of the following 4 elements The middle two elements of the
         * larger array Max of the first element of smaller array and element just
         * before the first middle element in the bigger array, i.e M/2 – 2nd element
         * Min of the second element of smaller array and element just after the second
         * middle in the bigger array, M/2 + 1th element
         * 
         * How can one half of each array be discarded?
         * 
         * Let’s take an example to understand this Input :arr[] = {1, 2, 3, 4, 5, 6, 7,
         * 8, 9, 10}, brr[] = { 11, 12, 13, 14, 15, 16, 17, 18, 19 } Dry Run of the
         * code: Recursive call 1: smaller array[] = 1 2 3 4 5 6 7 8 9 10, mid = 5
         * larger array[] = 11 12 13 14 15 16 17 18 19 , mid = 15
         * 
         * 5 < 15 Discard first half of the first array and second half of the second
         * array
         * 
         * Recursive call 2: smaller array[] = 11 12 13 14 15, mid = 13 larger array[] =
         * 5 6 7 8 9 10, mid = 7
         * 
         * 7 < 13 Discard first half of the second array and second half of the first
         * array
         * 
         * Recursive call 3: smaller array[] = 11 12 13 , mid = 12 larger array[] = 7 8
         * 9 10 , mid = 8
         * 
         * 8 < 12 Discard first half of the second array and second half of the first
         * array
         * 
         * Recursive call 4: smaller array[] = 11 12 larger array[] = 8 9 10
         * 
         * Size of the smaller array is 2 and the size of the larger array is odd so,
         * the median will be the median of max( 11, 8), 9, min( 10, 12) that is 9, 10,
         * 11, so the median is 10.
         * 
         * Output:10.000000
         */
        // A utility function to find median of two integers
        static float MO2(int a, int b) {
            return (float) ((a + b) / 2.0);
        }

        // A utility function to find median of three integers
        static float MO3(int a, int b, int c) {
            return a + b + c - Math.max(a, Math.max(b, c)) - Math.min(a, Math.min(b, c));
            // a+b+c if c is max, min is a, then b is median.
        }

        // A utility function to find a median of four integers
        static float MO4(int a, int b, int c, int d) {
            int Max = Math.max(a, Math.max(b, Math.max(c, d)));
            int Min = Math.min(a, Math.min(b, Math.min(c, d)));
            return (float) ((a + b + c + d - Max - Min) / 2.0);
            // a+b+c+d if c is max, min is d, then b + a/2 is median.
        }

        // Utility function to find median of single array
        static float medianSingle(int arr[], int n) {
            if (n == 0)
                return -1;
            if (n % 2 == 0)// when even, n/2, n/2-1 gives the median.
                return (float) ((double) (arr[n / 2] + arr[n / 2 - 1]) / 2);
            return arr[n / 2];
        }

        // This function assumes that N is smaller than or equal to M
        // This function returns -1 if both arrays are empty
        static float findMedianUtil(int A[], int N, int B[], int M) {

            // If smaller array is empty, return median from second array
            if (N == 0)
                return medianSingle(B, M);

            // If the smaller array has only one element
            if (N == 1) {

                // Case 1: If the larger array also has one element,
                // simply call MO2()
                if (M == 1)
                    return MO2(A[0], B[0]);

                // Case 2: If the larger array has odd number of elements,
                // then consider the middle 3 elements of larger array and
                // the only element of smaller array. Take few examples
                // like following
                // A = {9}, B[] = {5, 8, 10, 20, 30} and
                // A[] = {1}, B[] = {5, 8, 10, 20, 30}
                if (M % 2 == 1)
                    return MO2(B[M / 2], (int) MO3(A[0], B[M / 2 - 1], B[M / 2 + 1]));// A[0] will be subtracted if it's
                                                                                      // minimum, M03 here gives the mid
                                                                                      // element when the A[] is added.
                /** In the above example, 1) 9 becomes the mid, 2) 8 becomes the mid */

                // Case 3: If the larger array has even number of element,
                // then median will be one of the following 3 elements
                // ... The middle two elements of larger array
                // ... The only element of smaller array
                // B is {1, 5, 8, 10, 20, 30}, A is [9], Mediain of 8, 10 and 9, is 9.
                return MO3(B[M / 2], B[M / 2 - 1], A[0]);
            }

            // If the smaller array has two elements
            else if (N == 2) {

                // Case 4: If the larger array also has two elements,
                // simply call MO4()
                if (M == 2)
                    return MO4(A[0], A[1], B[0], B[1]);

                // Case 5: If the larger array has odd number of elements,
                // then median will be one of the following 3 elements
                // 1. Middle element of larger array
                // 2. Max of first element of smaller array and element
                // just before the middle in bigger array
                // 3. Min of second element of smaller array and element
                // just after the middle in bigger array
                /**
                 * Example: B is {5, 8, 10, 20, 30}, A is [3, 9], 1. Middle element is 10. 2.
                 * Max: [3, 8], Min [9, 20]: 8, 9 3. Median of (8, 9, 10) is 9. {3, 5, 8, 9, 10,
                 * 20, 30}
                 */
                if (M % 2 == 1)
                    return MO3(B[M / 2], Math.max(A[0], B[M / 2 - 1]), Math.min(A[1], B[M / 2 + 1]));

                // Case 6: If the larger array has even number of elements,
                // then median will be one of the following 4 elements
                // 1) & 2) The middle two elements of larger array
                // 3) Max of first element of smaller array and element
                // just before the first middle element in bigger array
                // 4. Min of second element of smaller array and element
                // just after the second middle in bigger array
                return MO4(B[M / 2], B[M / 2 - 1], Math.max(A[0], B[M / 2 - 2]), Math.min(A[1], B[M / 2 + 1]));
            }

            int idxA = (N - 1) / 2;
            int idxB = (M - 1) / 2;

            /*
             * if A[idxA] <= B[idxB], then median must exist in A[idxA....] and B[....idxB]
             */
            if (A[idxA] <= B[idxB])
                return findMedianUtil(Arrays.copyOfRange(A, idxA, A.length), N / 2 + 1, B, M - idxA);

            /*
             * if A[idxA] > B[idxB], then median must exist in A[...idxA] and B[idxB....]
             */
            return findMedianUtil(A, N / 2 + 1, Arrays.copyOfRange(B, idxB, B.length), M - idxA);
        }

        // A wrapper function around findMedianUtil(). This function
        // makes sure that smaller array is passed as first argument
        // to findMedianUtil
        static float findMedian(int A[], int N, int B[], int M) {
            if (N > M)
                return findMedianUtil(B, M, A, N);

            return findMedianUtil(A, N, B, M);
        }

        // Driver program to test above functions
        public static void testDataFindMedianOfTwoSortedArrays(String[] args) {
            int A[] = { 900 };
            int B[] = { 5, 8, 10, 20 };

            int N = A.length;
            int M = B.length;

            System.out.printf("%f", findMedian(A, N, B, M));
        }
    }

    static class FindPeakElementAmongNeighbors {
        /**
         * Given an array of integers. Find a peak element in it. An array element is a
         * peak if it is NOT smaller than its neighbours. For corner elements, we need
         * to consider only one neighbour.
         * 
         * Example:
         * 
         * Input: array[]= {5, 10, 20, 15} Output: 20 The element 20 has neighbours 10
         * and 15, both of them are less than 20.
         * 
         * Input: array[] = {10, 20, 15, 2, 23, 90, 67} Output: 20 or 90 The element 20
         * has neighbours 10 and 15, both of them are less than 20, similarly 90 has
         * neighbours 23 and 67.
         * 
         * Following corner cases give better idea about the problem.
         * 
         * If input array is sorted in strictly increasing order, the last element is
         * always a peak element. For example, 50 is peak element in {10, 20, 30, 40,
         * 50}. If the input array is sorted in strictly decreasing order, the first
         * element is always a peak element. 100 is the peak element in {100, 80, 60,
         * 50, 20}. If all elements of input array are same, every element is a peak
         * element.
         */
        /**
         * Efficient Approach: Divide and Conquer can be used to find a peak in O(Logn)
         * time. The idea is based on the technique of Binary Search to check if the
         * middle element is the peak element or not. If the middle element is not the
         * peak element, then check if the element on the right side is greater than the
         * middle element then there is always a peak element on the right side. If the
         * element on the left side is greater than the middle element then there is
         * always a peak element on the left side. Form a recursion and the peak element
         * can be found in log n time.
         * 
         * Algorithm:
         * 
         * Create two variables, l and r, initialize l = 0 and r = n-1 Iterate the steps
         * below till l <= r, lowerbound is less than the upperbound Check if the mid
         * value or index mid = (l+r)/2, is the peak element or not, if yes then print
         * the element and terminate. Else if the element on the left side of the middle
         * element is greater then check for peak element on the left side, i.e. update
         * r = mid – 1 Else if the element on the right side of the middle element is
         * greater then check for peak element on the right side, i.e. update l = mid +
         * 1
         */
        // A binary search based function
        // that returns index of a peak element
        static int findPeakUtil(int arr[], int low, int high, int n) {
            // Find index of middle element
            // (low + high)/2
            int mid = low + (high - low) / 2;

            // Compare middle element with its
            // neighbours (if neighbours exist)
            if ((mid == 0 || arr[mid - 1] <= arr[mid]) && (mid == n - 1 || arr[mid + 1] <= arr[mid]))
                return mid;

            // If middle element is not peak
            // and its left neighbor is
            // greater than it, then left half
            // must have a peak element
            else if (mid > 0 && arr[mid - 1] > arr[mid])
                // Example: {1, 3, 20, 4, 1, 0 ,7} middle element is 4, peak must be in the left
                // half, this is because 20 can be the corner element or it's lef neighbor can
                // be smaller than the element in which case it's a peak, Else, the instead of
                // 3, if it's 25, we traverse left,and 25 will be the peak element. The vice
                // versa condition applies for the right array.
                return findPeakUtil(arr, low, (mid - 1), n);

            // If middle element is not peak
            // and its right neighbor
            // is greater than it, then right
            // half must have a peak
            // element
            else
                return findPeakUtil(arr, (mid + 1), high, n);
        }

        // A wrapper over recursive function
        // findPeakUtil()
        static int findPeak(int arr[], int n) {
            return findPeakUtil(arr, 0, n - 1, n);
        }

        static void testDataFindPeakElements() {
            int arr[] = { 1, 3, 20, 4, 1, 0 };
            int n = arr.length;
            System.out.println("Index of a peak point is " + findPeak(arr, n));
        }

    }

    static class FindAllElementsThatAppearMoreThanNdKTimes {
        // Time complexity: O(n), space: O(k)
        /**
         * Given an array of size n, find all elements in array that appear more than
         * n/k times. For example, if the input arrays is {3, 1, 2, 2, 1, 2, 3, 3} and k
         * is 4, then the output should be [2, 3]. Note that size of array is 8 (or n =
         * 8), so we need to find all elements that appear more than 2 (or 8/4) times.
         * There are two elements that appear more than two times, 2 and 3.
         */
        public static void morethanNdK(int a[], int n, int k) {
            int x = n / k;

            // Hash map initialization
            HashMap<Integer, Integer> y = new HashMap<>();

            // count the frequency of each element.
            for (int i = 0; i < n; i++) {
                // is element doesn't exist in hash table
                if (!y.containsKey(a[i]))
                    y.put(a[i], 1);

                // if lement does exist in the hash table
                else {
                    int count = y.get(a[i]);
                    y.put(a[i], count + 1);
                }
            }

            // iterate over each element in the hash table
            // and check their frequency, if it is more than
            // n/k, print it.
            for (Map.Entry m : y.entrySet()) {
                Integer temp = (Integer) m.getValue();
                if (temp > x)
                    System.out.println(m.getKey());
            }
        }

        // A structure to store an element and its current count
        static class eleCount {
            int e; // Element
            int c; // Count
        };

        // Prints elements with more
        // than n/k occurrences in arr[]
        // of size n. If there are no
        // such elements, then it prints
        // nothing.
        static void moreThanNdK(int arr[], int n, int k) {
            // k must be greater than
            // 1 to get some output
            if (k < 2)
                return;

            /*
             * Step 1: Create a temporary array (contains element and count) of size k-1.
             * Initialize count of all elements as 0
             */
            eleCount[] temp = new eleCount[k - 1];
            for (int i = 0; i < k - 1; i++)
                temp[i] = new eleCount();
            for (int i = 0; i < k - 1; i++) {
                temp[i].c = 0;
            }

            /*
             * Step 2: Process all elements of input array
             */
            for (int i = 0; i < n; i++) {
                int j;

                /*
                 * If arr[i] is already present in the element count array, then increment its
                 * count
                 */
                for (j = 0; j < k - 1; j++) {
                    if (temp[j].e == arr[i]) {
                        temp[j].c += 1;
                        break;
                    }
                }

                /* If arr[i] is not present in temp[] */
                if (j == k - 1) {
                    int l;

                    /*
                     * If there is position available in temp[], then place arr[i] in the first
                     * available position and set count as 1
                     */
                    for (l = 0; l < k - 1; l++) {
                        if (temp[l].c == 0) {
                            temp[l].e = arr[i];
                            temp[l].c = 1;
                            break;
                        }
                    }

                    /*
                     * If all the position in the temp[] are filled, then decrease count of every
                     * element by 1
                     */
                    if (l == k - 1)
                        for (l = 0; l < k - 1; l++)
                            temp[l].c -= 1;
                }
            }

            /*
             * Step 3: Check actual counts of potential candidates in temp[]
             */
            for (int i = 0; i < k - 1; i++) {

                // Calculate actual count of elements
                int ac = 0; // actual count
                for (int j = 0; j < n; j++)
                    if (arr[j] == temp[i].e)
                        ac++;

                // If actual count is more than n/k,
                // then print it
                if (ac > n / k)
                    System.out.print("Number:" + temp[i].e + " Count:" + ac + "\n");
            }
        }

        public static void testDataFindElementsGreatThanNdK() {
            int a[] = new int[] { 1, 1, 2, 2, 3, 5, 4, 2, 2, 3, 1, 1, 1 };
            int n = 12;
            int k = 4;
            morethanNdK(a, n, k);
        }
    }

    static class FindPositionOfElementInSortedArrayOfInfiniteNumbers {
        /**
         * Suppose you have a sorted array of infinite numbers, how would you search an
         * element in the array? Source: Amazon Interview Experience. Since array is
         * sorted, the first thing clicks into mind is binary search, but the problem
         * here is that we don’t know size of array. If the array is infinite, that
         * means we don’t have proper bounds to apply binary search. So in order to find
         * position of key, first we find bounds and then apply binary search algorithm.
         * Let low be pointing to 1st element and high pointing to 2nd element of array,
         * Now compare key with high index element, ->if it is greater than high index
         * element then copy high index in low index and double the high index. ->if it
         * is smaller, then apply binary search on high and low indices found.
         */
        // Simple binary search algorithm
        static int binarySearch(int arr[], int l, int r, int x) {
            if (r >= l) {
                int mid = l + (r - l) / 2;
                if (arr[mid] == x)
                    return mid;
                if (arr[mid] > x)
                    return binarySearch(arr, l, mid - 1, x);
                return binarySearch(arr, mid + 1, r, x);
            }
            return -1;
        }

        // Method takes an infinite size array and a key to be
        // searched and returns its position if found else -1.
        // We don't know size of arr[] and we can assume size to be
        // infinite in this function.
        // NOTE THAT THIS FUNCTION ASSUMES arr[] TO BE OF INFINITE SIZE
        // THEREFORE, THERE IS NO INDEX OUT OF BOUND CHECKING
        static int findPos(int arr[], int key) {
            int l = 0, h = 1;
            int val = arr[0];

            // Find h to do binary search
            while (val < key) {
                l = h; // store previous high
                // check that 2*h doesn't exceeds array
                // length to prevent ArrayOutOfBoundException
                if (2 * h < arr.length - 1)
                    h = 2 * h;
                else
                    h = arr.length - 1;

                val = arr[h]; // update new val
            }

            // at this point we have updated low
            // and high indices, thus use binary
            // search between them
            return binarySearch(arr, l, h, key);
        }
    }

    static class FindElementThatAppearsOnceInSortedArrayWithPairs {
        /**
         * Given a sorted array in which all elements appear twice (one after one) and
         * one element appears only once. Find that element in O(log n) complexity.
         * 
         * Example:
         * 
         * Input: arr[] = {1, 1, 3, 3, 4, 5, 5, 7, 7, 8, 8} Output: 4
         * 
         * Input: arr[] = {1, 1, 3, 3, 4, 4, 5, 5, 7, 7, 8} Output: 8
         */
        /**
         * All elements before the required have the first occurrence at even index (0,
         * 2, ..) and the next occurrence at odd index (1, 3, …). And all elements after
         * the required elements have the first occurrence at an odd index and the next
         * occurrence at an even index. 1) Find the middle index, say ‘mid’. 2) If ‘mid’
         * is even, then compare arr[mid] and arr[mid + 1]. If both are the same, then
         * the required element after ‘mid’ and else before mid. 3) If ‘mid’ is odd,
         * then compare arr[mid] and arr[mid – 1]. If both are the same, then the
         * required element after ‘mid’ and else before mid.
         */
        // A Binary Search based method to find the element
        // that appears only once
        public static void search(int[] arr, int low, int high) {
            if (low > high)
                return;
            if (low == high) {
                System.out.println("The required element is " + arr[low]);
                return;
            }

            // Find the middle point
            int mid = (low + high) / 2;

            // If mid is even and element next to mid is
            // same as mid, then output element lies on
            // right side, else on left side
            if (mid % 2 == 0) {
                if (arr[mid] == arr[mid + 1])// Assume 3 as the mid.
                    search(arr, mid + 2, high);
                else
                    // mid and mid+1 is not the same, then the element must be in low...mid
                    search(arr, low, mid);
            }
            // If mid is odd
            else if (mid % 2 == 1) {
                if (arr[mid] == arr[mid - 1])// Assume 3 as the mid
                    search(arr, mid + 1, high);
                else// mid, and mid-1, element must be in the first half
                    search(arr, low, mid - 1);// including mid-1
            }
        }

        // Driver Code
        public static void testDataFindElementThatAppearsOnceInSortedArrayWithDuplicates() {
            int[] arr = { 1, 1, 2, 4, 4, 5, 5, 6, 6 };
            search(arr, 0, arr.length - 1);
        }
    }

    static class FindaElementAppearingOddNumberOfTimesInArrayWithEven {
        /**
         * Given an array where all elements appear even number of times except one. All
         * repeating occurrences of elements appear in pairs and these pairs are not
         * adjacent (there cannot be more than two consecutive occurrences of any
         * element). Find the element that appears odd number of times. Note that input
         * like {2, 2, 1, 2, 2, 1, 1} is valid as all repeating occurrences occur in
         * pairs and these pairs are not adjacent. Input like {2, 1, 2} is invalid as
         * repeating elements don’t appear in pairs. Also, input like {1, 2, 2, 2, 2} is
         * invalid as two pairs of 2 are adjacent. Input like {2, 2, 2, 1} is also
         * invalid as there are three consecutive occurrences of 2. Example :
         * 
         * 
         * Input: arr[] = {1, 1, 2, 2, 1, 1, 2, 2, 13, 1, 1, 40, 40, 13, 13} Output: 13
         * 
         * Input: arr[] = {1, 1, 2, 2, 3, 3, 4, 4, 3, 600, 600, 4, 4} Output: 3
         */
        /**
         * An Efficient Solution can find the required element in O(Log n) time. The
         * idea is to use Binary Search. Below is an observation in input array. Since
         * the element appears odd number of times, there must be a single occurrence of
         * the element. For example, in {2, 1, 1, 2, 2), the first 2 is the odd
         * occurrence. So the idea is to find this odd occurrence using Binary Search.
         * All elements before the odd occurrence have first occurrence at even index
         * (0, 2, ..) and next occurrence at odd index (1, 3, …). And all elements
         * afterhave first occurrence at odd index and next occurrence at even index. 1)
         * Find the middle index, say ‘mid’. 2) If ‘mid’ is even, then compare arr[mid]
         * and arr[mid + 1]. If both are same, then there is an odd occurrence of the
         * element after ‘mid’ else before mid. 3) If ‘mid’ is odd, then compare
         * arr[mid] and arr[mid – 1]. If both are same, then there is an odd occurrence
         * after ‘mid’ else before mid. Below is the implementation based on above idea.
         */

        // A Binary Search based function to find
        // the element that appears odd times
        static void search(int arr[], int low, int high) {
            // Base cases
            if (low > high)
                return;
            if (low == high) {
                System.out.printf("The required element is %d ", arr[low]);
                return;
            }

            // Find the middle point
            int mid = (low + high) / 2;

            // If mid is even and element next to mid is
            // same as mid, then output element lies on
            // right side, else on left side
            if (mid % 2 == 0) {
                if (arr[mid] == arr[mid + 1])
                    search(arr, mid + 2, high);
                else
                    search(arr, low, mid);
            }

            // If mid is odd
            else {
                if (arr[mid] == arr[mid - 1])
                    search(arr, mid + 1, high);
                else
                    search(arr, low, mid - 1);
            }
        }

        // Driver program
        public static void testDataFindOddAppearingElementInArray() {
            int arr[] = { 1, 1, 2, 2, 1, 1, 2, 2, 13, 1, 1, 40, 40 };
            int len = arr.length;
            search(arr, 0, len - 1);
        }
    }

    static class SearchElementInArrayWithAdjacentElementsWithDiffAs1 {
        /**
         * Given an array where difference between adjacent elements is 1, write an
         * algorithm to search for an element in the array and return the position of
         * the element (return the first occurrence). Examples :
         * 
         * Let element to be searched be x
         * 
         * Input: arr[] = {8, 7, 6, 7, 6, 5, 4, 3, 2, 3, 4, 3} x = 3 Output: Element 3
         * found at index 7
         * 
         * Input: arr[] = {1, 2, 3, 4, 5, 4} x = 5 Output: Element 5 found at index 4
         */
        /**
         * The idea is to start comparing from the leftmost element and find the
         * difference between current array element and x. Let this difference be
         * ‘diff’. From the given property of array, we always know that x must be
         * at-least ‘diff’ away, so instead of searching one by one, we jump ‘diff’.
         */
        // x is the element to be searched
        // in arr[0..n-1]
        static int search(int arr[], int n, int x) {

            // Traverse the given array starting
            // from leftmost element
            int i = 0;
            while (i < n) {

                // If x is found at index i
                if (arr[i] == x)
                    return i;

                // Jump the difference between current
                // array element and x
                i = i + Math.abs(arr[i] - x);
            }

            System.out.println("number is not" + " present!");

            return -1;
        }

        // Driver program to test above function
        public static void testDataSearchElementInArrayWithAdjacentElementsInDiffAs1() {

            int arr[] = { 8, 7, 6, 7, 6, 5, 4, 3, 2, 3, 4, 3 };
            int n = arr.length;
            int x = 3;
            System.out.println("Element " + x + " is present at index " + search(arr, n, 3));
        }
    }

    static class FindThreeClosestElementsFromThreeSortedArrays {
        /**
         * Given three sorted arrays A[], B[] and C[], find 3 elements i, j and k from
         * A, B and C respectively such that max(abs(A[i] – B[j]), abs(B[j] – C[k]),
         * abs(C[k] – A[i])) is minimized. Here abs() indicates absolute value.
         * 
         * Example :
         * 
         * Input: A[] = {1, 4, 10} B[] = {2, 15, 20} C[] = {10, 12} Output: 10 15 10 10
         * from A, 15 from B and 10 from C
         * 
         * Input: A[] = {20, 24, 100} B[] = {2, 19, 22, 79, 800} C[] = {10, 12, 23, 24,
         * 119} Output: 24 22 23 24 from A, 22 from B and 23 from C
         */
        /**
         * A Better Solution is to us Binary Search. 1) Iterate over all elements of
         * A[], a) Binary search for element just smaller than or equal to in B[] and
         * C[], and note the difference. 2) Repeat step 1 for B[] and C[]. 3) Return
         * overall minimum. Time complexity of this solution is O(nLogn) Efficient
         * Solution Let ‘p’ be size of A[], ‘q’ be size of B[] and ‘r’ be size of C[]
         * 
         * 1) Start with i=0, j=0 and k=0 (Three index variables for A, B and C
         * respectively)
         * 
         * // p, q and r are sizes of A[], B[] and C[] respectively. 2) Do following
         * while i < p and j < q and k < r a) Find min and maximum of A[i], B[j] and
         * C[k] b) Compute diff = max(X, Y, Z) - min(A[i], B[j], C[k]). c) If new result
         * is less than current result, change it to the new result. d) Increment the
         * pointer of the array which contains the minimum.
         * 
         * Note that we increment the pointer of the array which has the minimum,
         * because our goal is to decrease the difference. Increasing the maximum
         * pointer increases the difference. Increase the second maximum pointer can
         * potentially increase the difference.
         * 
         */
        static void findClosest(int A[], int B[], int C[], int p, int q, int r) {
            /**
             * Time complexity of this solution is O(p + q + r) where p, q and r are sizes
             * of A[], B[] and C[] respectively.
             */
            int diff = Integer.MAX_VALUE; // Initialize min diff

            // Initialize result
            int res_i = 0, res_j = 0, res_k = 0;

            // Traverse arrays
            int i = 0, j = 0, k = 0;
            while (i < p && j < q && k < r) {
                // Find minimum and maximum of current three elements
                int minimum = Math.min(A[i], Math.min(B[j], C[k]));
                int maximum = Math.max(A[i], Math.max(B[j], C[k]));

                // Update result if current diff is
                // less than the min diff so far
                if (maximum - minimum < diff) {
                    res_i = i;
                    res_j = j;
                    res_k = k;
                    diff = maximum - minimum;
                }

                // We can't get less than 0
                // as values are absolute
                if (diff == 0)
                    break;

                // Increment index of array
                // with smallest value
                if (A[i] == minimum)
                    i++;
                /**
                 * Note that we increment the pointer of the array which has the minimum,
                 * because our goal is to decrease the difference. Increasing the maximum
                 * pointer increases the difference. Increase the second maximum pointer can
                 * potentially increase the difference.
                 */
                else if (B[j] == minimum)
                    j++;
                else
                    k++;
            }

            // Print result
            System.out.println(A[res_i] + " " + B[res_j] + " " + C[res_k]);
        }

        // Driver code
        public static void testDataFindThreeClosestElementsFromThreeSortedArrays() {
            int A[] = { 1, 4, 10 };
            int B[] = { 2, 15, 20 };
            int C[] = { 10, 12 };

            int p = A.length;
            int q = B.length;
            int r = C.length;

            // Function calling
            findClosest(A, B, C, p, q, r);
        }
    }

    static class LowerBoundAndUpperBoundOfElementinSortedArrayUsingBS {
        /**
         * Given an array of N integers. There will be Q queries, each include two
         * integer of form q and x, 0 <= q <= 1. Queries are of two types:
         * 
         * 
         * In first query (q = 0), the task is to find count of integers which are not
         * less than x (OR greater than or equal to x).
         * 
         * In second query (q = 1), the task is to find count of integers greater than
         * x.
         * 
         * 
         * Examples:
         * 
         * 
         * Input : arr[] = { 1, 2, 3, 4 } and Q = 3 Query 1: 0 5 Query 2: 1 3 Query 3: 0
         * 3 Output :0 1 2 Explanation: x = 5, q = 0 : There are no elements greater
         * than or equal to it. x = 3, q = 1 : There is one element greater than 3 which
         * is 4. x = 3, q = 0 : There are two elements greater than or equal to 3
         * 
         * An efficient approach can be sort the array and use binary search for each
         * query. This will take O(NlogN + QlogN). Below is the implementation of this
         * approach :
         */
        // Return the index of integer
        // which are not less than x
        // (or greater than or equal
        // to x)
        static int lower_bound(int arr[], int start, int end, int x) {
            while (start < end) {
                int mid = (start + end) >> 1;
                if (x <= arr[mid])
                    end = mid;// mid is x, return greater than or equal to x.
                else
                    start = mid + 1;
            }

            return start;
        }

        // Return the index of integer
        // which are greater than x.
        static int upper_bound(int arr[], int start, int end, int x) {
            while (start < end) {
                int mid = (start + end) >> 1;
                if (x >= arr[mid])
                    start = mid + 1;// mid is x, reutrn greater than x.
                else
                    end = mid;
            }

            return start;
        }

        static void query(int arr[], int n, int type, int x) {
            // Counting number of integer
            // which are greater than x.
            if (type == 1)
                System.out.println(n - upper_bound(arr, 0, n, x));

            // Counting number of integer which
            // are not less than x (Or greater
            // than or equal to x)
            else
                System.out.println(n - lower_bound(arr, 0, n, x));
        }

        static void testDataLowerBoundAndUpperBoundOfElementinSortedArray() {
            int arr[] = { 1, 2, 3, 4 };
            int n = arr.length;

            Arrays.sort(arr);

            query(arr, n, 0, 5);
            query(arr, n, 1, 3);
            query(arr, n, 0, 3);
        }
    }

    static class PrintAllPossibleConsecutiveNumbersWithSumN {
        /**
         * Given a number N. The task is to print all possible consecutive numbers that
         * add up to N.
         * 
         * Examples :
         * 
         * Input: N = 100 Output: 9 10 11 12 13 14 15 16 18 19 20 21 22
         * 
         * Input: N = 125 Output: 8 9 10 11 12 13 14 15 16 17 23 24 25 26 27 62 63
         */
        /**
         * One important fact is we can not find consecutive numbers above N/2 that adds
         * up to N, because N/2 + (N/2 + 1) would be more than N. So we start from start
         * = 1 till end = N/2 and check for every consecutive sequence whether it adds
         * up to N or not. If it is then we print that sequence and start looking for
         * the next sequence by incrementing start point.
         * 
         * Just keep track of the sum you have so far and adjust it depending on how it
         * compares to the desired sum. Time complexity of below code is O(N).
         */
        static void printSums(int N) {
            int start = 1, end = 1;
            int sum = 1;

            while (start <= N / 2) {
                if (sum < N) {
                    end += 1;
                    sum += end;
                } else if (sum > N) {
                    sum -= start;
                    start += 1;
                } else if (sum == N) {
                    for (int i = start; i <= end; ++i)

                        System.out.print(i + " ");

                    System.out.println();
                    sum -= start;
                    start += 1;
                }
            }
        }

        // Driver Code
        public static void testDataPrintAllPossibleConsecutiveNumbersWithSumN(String[] args) {
            printSums(125);
        }
    }

    static class FindMinTimeToProduceMitemsByMachinesOperatingAtGivenSecs {
        /**
         * Consider n machines which produce same type of items but at different rate
         * i.e., machine 1 takes a1 sec to produce an item, machine 2 takes a2 sec to
         * produce an item. Given an array that contains the time required by ith
         * machine to produce an item. Considering all machines are working
         * simultaneously, the task is to find the minimum time required to produce m
         * items.
         * 
         * Examples:
         * 
         * Input : arr[] = {1, 2, 3}, m = 11 Output : 6 In 6 sec, machine 1 produces 6
         * items, machine 2 produces 3 items, and machine 3 produces 2 items. So to
         * produce 11 items minimum 6 sec are required.
         * 
         * Input : arr[] = {5, 6}, m = 11 Output : 30
         */
        /**
         * The idea is to use Binary Search. Maximum possible time required to produce m
         * items will be maximum time in the array, amax, multiplied by m i.e amax * m:
         * So, use binary search between 1 to amax * m and find the minimum time which
         * produce m items.
         * 
         * aMax=3, m=11, max possible time to produce m items will be 33.
         * 
         * Below is the implementation of the above idea :
         */

        // Return the number of items can
        // be produced in temp sec.
        static int findItems(int[] arr, int length, int sec) {
            int ans = 0;
            for (int i = 0; i < length; i++)
                ans += (sec / arr[i]);
            return ans;
        }

        // Binary search to find minimum time
        // required to produce M items.
        static int bs(int[] arr, int length, int mItems, int high)

        {
            int low = 1;

            // Doing binary search to
            // find minimum time.
            while (low < high) {
                // Finding the middle value.
                int mid = (low + high) >> 1;

                // Calculate number of items to
                // be produce in mid sec.
                int noOfItemsInMidSec = findItems(arr, length, mid);

                // If items produce is less than
                // required, set low = mid + 1.
                if (noOfItemsInMidSec < mItems)
                    low = mid + 1;

                // Else set high = mid.
                else
                    high = mid;
            }

            return high;
        }

        // Return the minimum time required to
        // produce m items with given machine.
        static int minTime(int[] arr, int length, int m) {
            int maxval = Integer.MIN_VALUE;

            // Finding the maximum time in the array.
            for (int i = 0; i < length; i++)
                maxval = Math.max(maxval, arr[i]);

            return bs(arr, length, m, maxval * m);// Use binary search to find min time to produce M items.
        }

        // Driven Program
        static public void main(String[] args) {
            int[] arr = { 1, 2, 3 };
            int n = arr.length;
            int m = 11;

            System.out.println(minTime(arr, n, m));
        }
    }

    static class TernarySearch {
        // Function to perform Ternary Search
        static int ternarySearch(int l, int r, int key, int ar[]) {
            if (r >= l) {

                // Find the mid1 and mid2
                int mid1 = l + (r - l) / 3;
                int mid2 = r - (r - l) / 3;

                // Check if key is present at any mid
                if (ar[mid1] == key) {
                    return mid1;
                }
                if (ar[mid2] == key) {
                    return mid2;
                }

                // Since key is not present at mid,
                // check in which region it is present
                // then repeat the Search operation
                // in that region

                if (key < ar[mid1]) {

                    // The key lies in between l and mid1
                    return ternarySearch(l, mid1 - 1, key, ar);
                } else if (key > ar[mid2]) {

                    // The key lies in between mid2 and r
                    return ternarySearch(mid2 + 1, r, key, ar);
                } else {

                    // The key lies in between mid1 and mid2
                    return ternarySearch(mid1 + 1, mid2 - 1, key, ar);
                }
            }

            // Key not found
            return -1;
        }

        // Driver code
        public static void testDataTernarySearch() {
            int l, r, p, key;

            // Get the array
            // Sort the array if not sorted
            int ar[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            // Starting index
            l = 0;

            // length of array
            r = 9;

            // Checking for 5

            // Key to be searched in the array
            key = 5;

            // Search the key using ternarySearch
            p = ternarySearch(l, r, key, ar);

            // Print the result
            System.out.println("Index of " + key + " is " + p);

            // Checking for 50

            // Key to be searched in the array
            key = 50;

            // Search the key using ternarySearch
            p = ternarySearch(l, r, key, ar);

            // Print the result
            System.out.println("Index of " + key + " is " + p);
        }
    }

    static class MakeAllArrayElementsEqualWithMinCostUsingTernarySearch {
        /**
         * Given an array which contains integer values, we need to make all values of
         * this array equal to some integer value with minimum cost where the cost of
         * changing an array value x to y is abs(x-y).
         * 
         * Examples :
         * 
         * Input : arr[] = [1, 100, 101] Output : 100 We can change all its values to
         * 100 with minimum cost, |1 - 100| + |100 - 100| + |101 - 100| = 100
         * 
         * Input : arr[] = [4, 6] Output : 2 We can change all its values to 5 with
         * minimum cost, |4 - 5| + |5 - 6| = 2
         */
        // Utility method to compute cost, when
        // all values of array are made equal to X
        public static int computeCost(int arr[], int N, int X) {
            int cost = 0;
            for (int i = 0; i < N; i++)
                cost += Math.abs(arr[i] - X);
            return cost;
        }

        // Method to find minimum cost to make all
        // elements equal
        public static int minCostToMakeElementEqual(int arr[], int N) {
            int low, high;
            low = high = arr[0];

            // setting limits for ternary search by
            // smallest and largest element
            for (int i = 0; i < N; i++) {
                if (low > arr[i])
                    low = arr[i];
                if (high < arr[i])
                    high = arr[i];
            }

            /*
             * loop until difference between low and high become less than 3, because after
             * that mid1 and mid2 will start repeating
             */
            while ((high - low) > 2) {
                // mid1 and mid2 are representative array
                // equal values of search space
                int mid1 = low + (high - low) / 3;
                int mid2 = high - (high - low) / 3;

                int cost1 = computeCost(arr, N, mid1);
                int cost2 = computeCost(arr, N, mid2);

                // if mid2 point gives more total cost,
                // skip third part
                if (cost1 < cost2)
                    high = mid2;

                // if mid1 point gives more total cost,
                // skip first part
                else
                    low = mid1;
            }

            // computeCost gets optimum cost by sending
            // average of low and high as X
            return computeCost(arr, N, (low + high) / 2);
        }

        /* Driver program to test above function */
        public static void testDataMinCostToMakeElementEqual() {
            int arr[] = { 1, 100, 101 };
            int N = arr.length;
            System.out.println(minCostToMakeElementEqual(arr, N));
        }
    }

    static class CheckIfSumOfTwoElementsInAnArrayEqualToSumOfRestOfArray {
        // Time complexity: O(n)
        /**
         * We have an array of integers and we have to find two such elements in the
         * array such that sum of these two elements is equal to the sum of rest of
         * elements in array. Examples:
         * 
         * 
         * Input : arr[] = {2, 11, 5, 1, 4, 7} Output : Elements are 4 and 11 Note that
         * 4 + 11 = 2 + 5 + 1 + 7
         * 
         * Input : arr[] = {2, 4, 2, 1, 11, 15} Output : Elements do not exist
         */
        /**
         * An efficient solution is to find sum of all array elements. Let this sum be
         * “sum”. Now the task reduces to finding a pair with sum equals to sum/2.
         * Another optimization is, a pair can exist only if the sum of whole array is
         * even because we are basically dividing it into two parts with equal sum. 1-
         * Find the sum of whole array. Let this sum be “sum” 2- If sum is odd, return
         * false. 3- Find a pair with sum equals to “sum/2” using hashing based method
         * discussed here as method 2. If a pair is found, print it and return true. 4-
         * If no pair exists, return false. Below is the implementation of above steps.
         */

        // Function to check whether two elements exist
        // whose sum is equal to sum of rest of the elements.
        static boolean checkPair(int arr[], int n) {
            // Find sum of whole array
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += arr[i];
            }

            // If sum of array is not even than we can not
            // divide it into two part
            if (sum % 2 != 0) {
                return false;
            }

            sum = sum / 2;

            // For each element arr[i], see if there is
            // another element with vaalue sum - arr[i]
            HashSet<Integer> s = new HashSet<Integer>();
            for (int i = 0; i < n; i++) {
                int val = sum - arr[i];

                // If element exist than return the pair
                if (s.contains(val) && val == (int) s.toArray()[s.size() - 1]) {
                    // The and condition is just to print the element when the pair is found
                    System.out.printf("Pair elements are %d and %d\n", arr[i], val);
                    return true;
                }
                s.add(arr[i]);
            }
            return false;
        }

        // Driver program.
        public static void testDataCheckIfSumOfTwoElementsInAnArrayEqualToSumOfRestOfArray() {
            int arr[] = { 2, 11, 5, 1, 4, 7 };
            int n = arr.length;
            if (checkPair(arr, n) == false) {
                System.out.printf("No pair found");
            }
        }
    }

    static class CheckIfReversingSubArrayMakeArraySorted {
        // Time complexity: O(n) time
        // Increasing, Decreasing and Increasing again.
        // Refer findUnsortedSubarray
        /**
         * Given an array of distinct n integers. The task is to check whether reversing
         * one sub-array make the array sorted or not. If the array is already sorted or
         * by reversing a subarray once make it sorted, print “Yes”, else print “No”.
         * Examples:
         * 
         * 
         * Input : arr [] = {1, 2, 5, 4, 3} Output : Yes By reversing the subarray {5,
         * 4, 3}, the array will be sorted.
         * 
         * Input : arr [] = { 1, 2, 4, 5, 3 } Output : No
         */
        // Return true, if reversing the subarray will sort t
        // he array, else return false.
        static boolean checkReverse(int arr[], int n) {
            if (n == 1) {
                return true;
            }

            // Find first increasing part
            int i;
            for (i = 1; arr[i] > arr[i - 1] && i < n; i++)
                ;
            if (i == n) {
                return true;
            }

            // Find reversed part
            int j = i;
            while (j < n && arr[j] < arr[j - 1]) {
                if (i > 1 && arr[j] < arr[i - 2]) {
                    return false;
                }
                j++;
            }

            if (j == n) {
                return true;
            }

            // Find last increasing part
            int k = j;

            // To handle cases like {1,2,3,4,20,9,16,17}
            if (arr[k] < arr[i - 1]) {// 16 < 20, i-1 is 20
                return false;
            }

            while (k > 1 && k < n) {
                if (arr[k] < arr[k - 1]) {
                    return false;
                }
                k++;
            }
            return true;
        }

        // Driven Program
        public static void testDataCheckIfReversingSubArrayMakeArraySorted() {

            int arr[] = { 1, 3, 4, 10, 9, 8 };
            int n = arr.length;

            if (checkReverse(arr, n)) {
                System.out.print("Yes");
            } else {
                System.out.print("No");
            }
        }
    }

    static class FindTripletsWhoseSumIsZero {
        // function to print triplets with 0 sum
        static void findTriplets(int arr[], int n) {
            boolean found = false;

            for (int i = 0; i < n - 1; i++) {
                // Find all pairs with sum equals to
                // "-arr[i]"
                HashSet<Integer> s = new HashSet<Integer>();
                for (int j = i + 1; j < n; j++) {
                    int x = -(arr[i] + arr[j]);
                    if (s.contains(x)) {
                        System.out.printf("%d %d %d\n", x, arr[i], arr[j]);
                        found = true;
                    } else {
                        s.add(arr[j]);
                    }
                }
            }
            if (found == false) {
                System.out.printf(" No Triplet Found\n");
            }
        }

        // function to print triplets with 0 sum
        static void findTripletsUsingBS(int arr[], int n) {
            boolean found = false;

            // sort array elements
            Arrays.sort(arr);

            for (int i = 0; i < n - 1; i++) {
                // initialize left and right
                int l = i + 1;
                int r = n - 1;
                int x = arr[i];
                while (l < r) {
                    if (x + arr[l] + arr[r] == 0) {
                        // print elements if it's sum is zero
                        System.out.print(x + " ");
                        System.out.print(arr[l] + " ");
                        System.out.println(arr[r] + " ");

                        l++;
                        r--;
                        found = true;
                    }

                    // If sum of three elements is less
                    // than zero then increment in left
                    else if (x + arr[l] + arr[r] < 0)
                        l++;

                    // if sum is greater than zero than
                    // decrement in right side
                    else
                        r--;
                }
            }

            if (found == false)
                System.out.println(" No Triplet Found");
        }

        // Driver code
        public static void testDataFindTripletsWhoseSumIsZero() {
            int arr[] = { 0, -1, 2, -3, 1 };
            int n = arr.length;
            findTriplets(arr, n);
            findTripletsUsingBS(arr, n);
        }
    }

    static class SearchInStringArrayWhereNonEmptyStringsAreSorted {
        /**
         * Given an array of strings. The array has both empty and non-empty strings.
         * All non-empty strings are in sorted order. Empty strings can be present
         * anywhere between non-empty strings. Examples:
         * 
         * Input : arr[] = {"for", "", "", "", "geeks", "ide", "", "practice", "" , "",
         * "quiz", "", ""}; str = "quiz" Output : 10 The string "quiz" is present at
         * index 10 in given array.
         */
        /**
         * A better solution is to do modified Binary Search. Like normal binary search,
         * we compare given str with middle string. If middle string is empty, we find
         * the closest non-empty string x (by linearly searching on both sides). Once we
         * find x, we do standard binary search, i.e., we compare given str with x. If
         * str is same as x, we return index of x. if str is greater, we recur for right
         * half, else we recur for left half. Below is the implementation of the idea:
         */
        // Compare two string equals are not
        static int compareStrings(String str1, String str2) {
            int i = 0;
            while (i < str1.length() - 1 && str1.charAt(i) == str2.charAt(i))
                i++;

            if (str1.charAt(i) > str2.charAt(i))
                return -1;

            if (str1.charAt(i) < str2.charAt(i))
                return 1;
            else
                return 0;
        }

        // Main function to find string location
        static int searchStr(String[] arr, String str, int first, int last) {
            if (first > last)
                return -1;

            // Move mid to the middle
            int mid = (last + first) / 2;

            // If mid is empty,
            // find closet non-empty string
            if (arr[mid].isEmpty()) {

                // If mid is empty, search in both sides of mid
                // and find the closest non-empty string, and
                // set mid accordingly.
                int left = mid - 1;
                int right = mid + 1;
                while (true) {
                    if (left < first && right > last)
                        return -1;
                    if (right <= last && !arr[right].isEmpty()) {
                        mid = right;
                        break;
                    }
                    if (left >= first && !arr[left].isEmpty()) {
                        mid = left;
                        break;
                    }
                    right++;
                    left--;
                }
            }

            // If str is found at mid
            if (compareStrings(str, arr[mid]) == 0)
                return mid;

            // If str is greater than mid
            if (compareStrings(str, arr[mid]) < 0)
                return searchStr(arr, str, mid + 1, last);

            // If str is smaller than mid
            return searchStr(arr, str, first, mid - 1);
        }

        // Driver Code
        public static void testDataSearchInStringArrayWhereNonEmptyStringsAreSorted(String[] args) {

            // Input arr of Strings.
            String[] arr = { "for", "", "", "", "geeks", "ide", "", "practice", "", "", "quiz", "", "" };

            // input Search String
            String str = "quiz";
            int n = arr.length;

            System.out.println(searchStr(arr, str, 0, n - 1));
        }
    }
}
