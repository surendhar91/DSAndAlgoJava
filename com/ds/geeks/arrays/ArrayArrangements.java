package com.ds.geeks.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ArrayArrangements {
    /**
     * Key points
     * 
     * 1. Jump +2 for even and odd number arrangement in an array. n/2 indicates the
     * even positions in an array.
     * 
     * 2. Use pivot to rearrange the data.
     * 
     * 3. Use sliding window on array. i to track the prev window, j to track the
     * new window. decrement the prev window, increment the new window.
     * 
     * 4. Try array sorting.
     * 
     * 5. Use a new index to track consecutive positive integers which you can use
     * to rearrange data.
     * 
     * 6. Try using the right/left rotation to rearrange the data. for example.
     * alternative positive negative numbers in an array.
     * 
     * 4. Use two pointer algorithm. One in the first, and in the last. Or try two pointers in 0, and 1 position.
     */
    static void rearrangeArraySuchThatIndexIcontainsDataI(int[] arr) {
        // { -1, -1, 6, 1, 9, 3, 2, -1, 4, -1 }
        // This approach swaps elements in array. swap arr[i] with arr[arr[i]]
        // It works in all cases.
        // Another approach is to use hashset. Add all elements to hashset.
        // If the element is not in hashset, set it as -1 in the output
        for (int i = 0; i < arr.length;) {
            if (arr[i] >= 0 && arr[i] != i) {
                int ele = arr[arr[i]];
                arr[arr[i]] = arr[i];
                arr[i] = ele;
            } else {
                i++;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    static void elementsAtEvenPositionAreGreaterBeforeItAndOddPositionsAreLessThanElementsBeforeIt(int arr[]) {
        /**
         * Input : arr[] = {1, 2, 3, 4, 5, 6, 7} Output : 4 5 3 6 2 7 1 Input : arr[] =
         * {1, 2, 1, 4, 5, 6, 8, 8} Output : 4 5 2 6 1 8 1 8 Elements at even position
         * are greater than before it. Element 6 at index 4 is greater than before it.
         * Elements at odd position are lesser than before it. Element 2 at index 5 is
         * lesser than before it.
         * 
         * The idea to solve this problem is to first create an auxiliary copy of the
         * original array and sort the copied array. Now total number of even position
         * in array with n elements will be floor(n/2) and remaining is the number of
         * odd positions. Now fill the odd and even positions in the original array
         * using the sorted array in the below manner:
         * 
         * 1. Total odd positions will be n – floor(n/2). Start from (n-floor(n/2))th
         * position in the sorted array and copy the element to 1st position of sorted
         * array. Start traversing the sorted array from this position towards left and
         * keep filling the odd positions in the original array towrds right.
         * 
         * 
         * 2. Start traversing the sorted array starting from (n-floor(n/2)+1)th
         * position towards right and keep filling the original array starting from 2nd
         * position.
         * 
         */
        int n = arr.length;
        // total even positions
        int evenPos = n / 2; // even position is 3

        // total odd positions
        int oddPos = n - evenPos; // odd position is 4.

        int[] tempArr = new int[n];

        // copy original array in an
        // auxiliary array
        for (int i = 0; i < n; i++)
            tempArr[i] = arr[i];

        // sort the auxiliary array
        Arrays.sort(tempArr);
        // 1 2 3 4 5 6 7
        int j = oddPos - 1;

        // fill up odd position in
        // original array
        for (int i = 0; i < n; i += 2) {
            // Iterate the sorted array backward from 4...1, and store the element
            // alternatively from index 0. [0, 2, 4]
            arr[i] = tempArr[j];
            j--;
        }

        j = oddPos;

        // fill up even positions in
        // original array
        for (int i = 1; i < n; i += 2) {
            arr[i] = tempArr[j];
            // Iterate the sorted array forward from 5..7, and store the element
            // alternatively from 1.
            j++;
        }

        // display array
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }

    // function which works in the condition when number of
    // negative numbers are lesser or equal than positive
    // numbers
    static void fill1(int a[], int neg, int pos) {
        if (neg % 2 == 1) {
            // -1 -2 -3 1 2 3 4 5
            for (int i = 1; i < neg; i += 2) {
                int c = a[i];
                int d = a[i + neg];
                int temp = c;
                a[i] = d;
                a[i + neg] = temp;
            }
        } else {
            // -1 -2 1 2 3 4 5
            for (int i = 1; i <= neg; i += 2) {
                int c = a[i];
                int d = a[i + neg - 1];
                int temp = c;
                a[i] = d;
                a[i + neg - 1] = temp;
            }
        }
    }

    // function which works in the condition when number of
    // negative numbers are greater than positive numbers
    static void fill2(int a[], int neg, int pos) {
        if (pos % 2 == 1) {
            // -1 -2 -3 -4 -5 1 2 3
            for (int i = 1; i < pos; i += 2) {
                int c = a[i];
                int d = a[i + pos];
                int temp = c;
                a[i] = d;
                a[i + pos] = temp;
            }
        } else {
            // -1 -2 -3 -4 -5 1 2 3 4
            for (int i = 1; i <= pos; i += 2) {
                int c = a[i];
                int d = a[i + pos - 1];
                int temp = c;
                a[i] = d;
                a[i + pos - 1] = temp;
            }
        }
    }

    static void reverse(int a[], int n) {
        int i, t;
        for (i = 0; i < n / 2; i++) {
            t = a[i];
            a[i] = a[n - i - 1];
            a[n - i - 1] = t;
        }
    }

    static void print(int a[], int n) {
        for (int i = 0; i < n; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }

    // Utility function to right rotate all elements
    // between [outofplace, cur]
    static void rightrotate(int arr[], int n, int outofplace, int cur) {
        int tmp = arr[cur];
        for (int i = cur; i > outofplace; i--)
            arr[i] = arr[i - 1];
        arr[outofplace] = tmp;
    }

    static void alternatingPositiveNEgativeNumbersUsingRotationEfficientMethod(int[] arr) {
        // O(N2) time. This method preserves the order in the input array.
        /**
         * The idea is to process array from left to right. While processing, find the
         * first out of place element in the remaining unprocessed array. An element is
         * out of place if it is negative and at odd index, or it is positive and at
         * even index. Once we find an out of place element, we find the first element
         * after it with opposite sign. We right rotate the subarray between these two
         * elements (including these two).
         * 
         * 
         * Input: arr[] = {1, 2, 3, -4, -1, 4} Output: arr[] = {-4, 1, -1, 2, 3, 4}
         * 
         * Input: arr[] = {-5, -2, 5, 2, 4, 7, 1, 8, 0, -8} output: arr[] = {-5, 5, -2,
         * 2, -8, 4, 7, 1, 8, 0}
         */
        int outofplace = -1;
        int n = arr.length;
        for (int index = 0; index < n; index++) {
            if (outofplace >= 0) {
                // find the item which must be moved into
                // the out-of-place entry if out-of-place
                // entry is positive and current entry is
                // negative OR if out-of-place entry is
                // negative and current entry is negative
                // then right rotate
                //
                // [...-3, -4, -5, 6...] --> [...6, -3,
                // -4, -5...]
                // ^ ^
                // | |
                // outofplace --> outofplace
                //
                if (((arr[index] >= 0) && (arr[outofplace] < 0)) || ((arr[index] < 0) && (arr[outofplace] >= 0))) {
                    rightrotate(arr, n, outofplace, index);

                    // the new out-of-place entry is now 2
                    // steps ahead
                    if (index - outofplace >= 2)
                        outofplace = outofplace + 2;
                    else
                        outofplace = -1;
                }
            }

            // if no entry has been flagged out-of-place
            if (outofplace == -1) {
                // check if current entry is out-of-place
                if (((arr[index] >= 0) && ((index & 0x01) == 0)) || ((arr[index] < 0) && (index & 0x01) == 1))
                    outofplace = index;
            }
        }
    }

    static void alternatingPositiveNegativeNumbersUsingSort(int[] arr) {
        int n = arr.length;

        int neg = 0, pos = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] < 0)
                neg++;
            else
                pos++;
        }
        // Sort the array
        Arrays.sort(arr);

        // if negative is less or equal to positive
        if (neg <= pos) {
            fill1(arr, neg, pos);
        } else {
            // if negative is greater than positive.
            // reverse the array in this condition
            print(arr, n);
            reverse(arr, n);
            System.out.println("reversed");
            print(arr, n);
            fill2(arr, neg, pos);
            System.out.println("Sorted");
            print(arr, n);
        }
        print(arr, n);
    }

    static void alternatingPositiveNegativeNumbersWithQuickSortPartition(int[] arr) {
        // Time complexity: O(n)
        // This approach doesn't maintain the order of elements. Use quick sort
        // partition consider 0 as pivot. Negative elements are placed first and then
        // the positive elements.
        // Swap alternatively the positive and negative indexes.
        // The following few lines are similar to partition
        // process of QuickSort. The idea is to consider 0
        // as pivot and divide the array around it.
        int n = arr.length;
        int i = -1, temp = 0;
        for (int j = 0; j < n; j++) {
            if (arr[j] < 0) {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Now all positive numbers are at end and negative numbers at
        // the beginning of array. Initialize indexes for starting point
        // of positive and negative numbers to be swapped
        int pos = i + 1, neg = 0;

        // Increment the negative index by 2 and positive index by 1, i.e.,
        // swap every alternate negative number with next positive number
        while (pos < n && neg < pos && arr[neg] < 0) {
            temp = arr[neg];
            arr[neg] = arr[pos];
            arr[pos] = temp;
            pos++;
            neg += 2;
        }
    }

    static void positiveNumbersAtEvenAndNegativeNumbersAtOddPositions(int[] arr) {
        /**
         * You have been given an array and you have to make a program to convert that
         * array such that positive elements occur at even numbered places in the array
         * and negative elements occur at odd numbered places in the array. We have to
         * do it in place. There can be unequal number of positive and negative values
         * and the extra values have to left as it is.
         * 
         * Examples:
         * 
         * Input : arr[] = {1, -3, 5, 6, -3, 6, 7, -4, 9, 10} Output : arr[] = {1, -3,
         * 5, -3, 6, 6, 7, -4, 9, 10}
         * 
         * Input : arr[] = {-1, 3, -5, 6, 3, 6, -7, -4, -9, 10} Output : arr[] = {3, -1,
         * 6, -5, 3, -7, 6, -4, 10, -9}
         * 
         * The idea is to use Hoare’s partition process of Quick Sort. We take two
         * pointers positive and negative. We set the positive pointer at start of the
         * array and the negative pointer at 1st position of the array.
         * 
         * We move positive pointer two steps forward till it finds a negative element.
         * Similarly, we move negative pointer forward by two places till it finds a
         * positive value at its position.
         * 
         * If the positive and negative pointers are in the array then we will swap the
         * values at these indexes otherwise we will stop executing the process.
         */

        int positive = 0, negative = 1, temp;
        int size = arr.length;
        while (true) {

            /*
             * Move forward the positive pointer till negative number number not encountered
             */
            while (positive < size && arr[positive] >= 0)
                positive += 2;

            /*
             * Move forward the negative pointer till positive number number not encountered
             */
            while (negative < size && arr[negative] <= 0)
                negative += 2;

            // Swap array elements to fix their position.
            if (positive < size && negative < size) {
                temp = arr[positive];
                arr[positive] = arr[negative];
                arr[negative] = temp;
            }

            /*
             * Break from the while loop when any index exceeds the size of the array
             */
            else
                break;
        }

    }

    static void moveAllZeroesToEndOfArray(int arr[]) {
        /**
         * Traverse the given array ‘arr’ from left to right. While traversing, maintain
         * count of non-zero elements in array. Let the count be ‘count’. For every
         * non-zero element arr[i], put the element at ‘arr[count]’ and increment
         * ‘count’. After complete traversal, all non-zero elements have already been
         * shifted to front end
         */
        int n = arr.length;
        int count = 0; // Count of non-zero elements

        // Traverse the array. If element encountered is
        // non-zero, then replace the element at index 'count'
        // with this element
        for (int i = 0; i < n; i++)
            if (arr[i] != 0)
                arr[count++] = arr[i]; // here count is
                                       // incremented

        // Now all non-zero elements have been shifted to
        // front and 'count' is set as index of first 0.
        // Make all elements 0 from count to end.
        while (count < n)
            arr[count++] = 0;
    }

    static int minSwapsToKeepElementsLessThanOrEqualToKTogether(int arr[], int k) {
        /**
         * A simple approach is to use two pointer technique and sliding window.
         * 
         * 1. Find count of all elements which are less than or equals to ‘k’. Let’s say
         * the count is ‘cnt’.
         * 
         * 2. Using two pointer technique for window of length ‘cnt’, each time keep
         * track of how many elements in this range are greater than ‘k’. Let’s say the
         * total count is ‘bad’.
         * 
         * 3. Repeat step 2, for every window of length ‘cnt’ and take minimum of count
         * ‘bad’ among them. This will be the final answer
         */
        // Find count of elements which are
        // less than equals to k
        int n = arr.length;
        int count = 0;
        for (int i = 0; i < n; ++i)
            if (arr[i] <= k)
                ++count;

        // Find unwanted elements in current
        // window of size 'count'
        int bad = 0;
        for (int i = 0; i < count; ++i)
            if (arr[i] > k)
                ++bad;

        // Initialize answer with 'bad' value of
        // current window
        int ans = bad;
        // System.out.println(ans);

        // int arr1[] = {11, 7, 9, 5, 2, 7, 4}, K=5, the output should be 1.
        // Min swap required is 1. 2 is swapped with 7, the above block will print 3.
        // window of size 2 is covered above.
        for (int i = 0, j = count; j < n; ++i, ++j) {

            // Decrement count of previous window
            if (arr[i] > k) // > 5
                --bad;

            // Increment count of current window
            if (arr[j] > k)
                ++bad;

            // Update ans if count of 'bad'
            // is less in current window
            ans = Math.min(ans, bad);
        }
        return ans;

    }

    static void rearrangeEvenPositionElementsAreGreaterThanOddNLogN(int arr[]) {
        /**
         * Given an array A of n elements, sort the array according to the following
         * relations :
         * 
         * A[i] >= A[i-1], if i is even.
         * 
         * A[i] <= A[i-1], if i is odd. Print the resultant array. Examples :
         * 
         * Input : A[] = {1, 2, 2, 1} Output : 1 2 1 2 Explanation : For 1st element, 1
         * 1, i = 2 is even. 3rd element, 1 1, i = 4 is even.
         * 
         * Input : A[] = {1, 3, 2} Output : 1 3 2 Explanation : Here, the array is also
         * sorted as per the conditions. 1 1 and 2 < 3.
         * 
         * Observe that array consists of [n/2] even positioned elements. If we assign
         * the largest [n/2] elements to the even positions and the rest of the elements
         * to the odd positions, our problem is solved. Because element at the odd
         * position will always be less than the element at the even position as it is
         * the maximum element and vice versa. Sort the array and assign the first [n/2]
         * elements at even positions. Below is the implementation of the above
         * approach:
         */
        // Sort the array
        int n = arr.length;
        Arrays.sort(arr);
        // 1 1 2 2
        int ans[] = new int[n];
        int p = 0, q = n - 1;
        for (int i = 0; i < n; i++) {

            // Assign even indexes with maximum elements
            if ((i + 1) % 2 == 0)
                // assign the last elements when the position is even.
                ans[i] = arr[q--];

            // Assign odd indexes with remaining elements
            else
                // assign the first elements when the position is odd.
                ans[i] = arr[p++];
        }

        // Print result
        for (int i = 0; i < n; i++)
            System.out.print(ans[i] + " ");
    }

    static void rearrangeEvenPositionElementsAreGreaterThanOddNTime(int arr[]) {
        /**
         * One other approach is to traverse the array from the second element and swap
         * the element with the previous one if the condition is not satisfied. This is
         * implemented as follows:
         */
        int n = arr.length;
        for (int i = 1; i < n; i++) {

            // if index is even (means the element's position is odd).
            if (i % 2 == 0) {
                // when the odd position is > even position, swap.
                if (arr[i] > arr[i - 1]) {

                    // swap two elements
                    int temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                }
            }

            // if index is odd (means the element's position is even)
            else {
                // when the even position is < odd position, swap
                if (arr[i] < arr[i - 1]) {

                    // swap two elements
                    int temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    static void rearrangeOddIndexGreaterThanEvenIndexNTime(int[] arr) {
        /**
         * Rearrange array such that even index elements are smaller and odd index
         * elements are greater
         * 
         * Given an array, rearrange the array such that :
         * 
         * If index i is even, arr[i] <= arr[i+1] If index i is odd, arr[i] >= arr[i+1]
         * Note : There can be multiple answers. Examples:
         * 
         * Input : arr[] = {2, 3, 4, 5} Output : arr[] = {2, 4, 3, 5} Explanation :
         * Elements at even indexes are smaller and elements at odd indexes are greater
         * than their next elements
         * 
         * Note : Another valid answer is arr[] = {3, 4, 2, 5}
         * 
         * Input :arr[] = {6, 4, 2, 1, 8, 3} Output :arr[] = {4, 6, 1, 8, 2, 3}
         * 
         * An efficient solution is to iterate over the array and swap the elements as
         * per the given condition. If we have an array of length n, then we iterate
         * from index 0 to n-2 and check the given condition. At any point of time if i
         * is even and arr[i] > arr[i+1], then we swap arr[i] and arr[i+1]. Similarly,
         * if i is odd and arr[i] < arr[i+1], then we swap arr[i] and arr[i+1]. For the
         * given example: Before rearrange, arr[] = {2, 3, 4, 5} Start iterating over
         * the array till index 2 (as n = 4)
         * 
         * First Step: At i = 0, arr[i] = 2 and arr[i+1] = 3. As i is even and arr[i] <
         * arr[i+1], don’t need to swap.
         * 
         * Second step: At i = 1, arr[i] = 3 and arr[i+1] = 4. As i is odd and arr[i] <
         * arr[i+1], swap them. Now arr[] = {2, 4, 3, 5}
         *
         * 
         * Third step: At i = 2, arr[i] = 3 and arr[i+1] = 5. So, don’t need to swap
         * them
         * 
         * After rearrange, arr[] = {2, 4, 3, 5}
         */
        int temp;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            if (i % 2 == 0 && arr[i] > arr[i + 1]) {
                // when the index is even, maintain property arr[i] <= arr[i+1]
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
            if (i % 2 != 0 && arr[i] < arr[i + 1]) {
                // when the index is odd, maintain property arr[i] >= arr[i+1]
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
    }

    static void increasingLargestAndSmallestElementsInArray(int arr[]) {
        /**
         * Given an array of integers, task is to print the array in the order –
         * smallest number, Largest number, 2nd smallest number, 2nd largest number, 3rd
         * smallest number, 3rd largest number and so on….. Examples:
         * 
         * 
         * Input : arr[] = [5, 8, 1, 4, 2, 9, 3, 7, 6] Output :arr[] = {1, 9, 2, 8, 3,
         * 7, 4, 6, 5}
         * 
         * Input : arr[] = [1, 2, 3, 4] Output :arr[] = {1, 4, 2, 3}
         * 
         * An efficient solution is to use sorting.
         * 
         * 1. Sort the elements of array.
         * 
         * 2.Take two variables say i and j and point them to the first and last index
         * of the array respectively.
         * 
         * 3. Now run a loop and store the elements in the array one by one by
         * incrementing i and decrementing j.
         * 
         * Let’s take an array with input 5, 8, 1, 4, 2, 9, 3, 7, 6 and sort them so the
         * array become 1, 2, 3, 4, 5, 6, 7, 8, 9. Now take two variables say i and j
         * and point them to the first and last index of the array respectively, run a
         * loop and store value into new array by incrementing i and decrementing j. We
         * get final result as 1 9 2 8 3 7 4 6 5.
         */
        // Sorting the array elements
        int n = arr.length;
        Arrays.sort(arr);

        int[] tempArr = new int[n]; // To store modified array

        // Adding numbers from sorted array to
        // new array accordingly
        int ArrIndex = 0;

        // Traverse from begin and end simultaneously
        for (int i = 0, j = n - 1; i <= n / 2 || j > n / 2; i++, j--) {
            if (ArrIndex < n) {
                tempArr[ArrIndex] = arr[i];
                ArrIndex++;
            }

            if (ArrIndex < n) {
                tempArr[ArrIndex] = arr[j];
                ArrIndex++;
            }
        }

        // Modifying original array
        for (int i = 0; i < n; i++)
            arr[i] = tempArr[i];

    }

    static void pushZerosToEndUtil(int arr[], int n) {
        // Count of non-zero elements
        int count = 0;

        // Traverse the array. If element
        // encountered is non-zero, then
        // replace the element at index
        // 'count' with this element
        for (int i = 0; i < n; i++)
            if (arr[i] != 0)

                // here count is incremented
                arr[count++] = arr[i];

        // Now all non-zero elements
        // have been shifted to front and
        // 'count' is set as index of first 0.
        // Make all elements 0 from count to end.
        while (count < n)
            arr[count++] = 0;
    }

    static void doubleFirstElementAndMoveZeroToEnd(int arr[]) {
        /**
         * For a given array of n integers and assume that ‘0’ is an invalid number and
         * all others as a valid number. Convert the array in such a way that if both
         * current and next element is valid then double current value and replace the
         * next number with 0. After the modification, rearrange the array such that all
         * 0’s shifted to the end. Examples:
         * 
         * Input : arr[] = {2, 2, 0, 4, 0, 8} Output : 4 4 8 0 0 0
         * 
         * Input : arr[] = {0, 2, 2, 2, 0, 6, 6, 0, 0, 8} Output : 4 2 12 8 0 0 0 0 0 0
         * 
         */
        int n = arr.length;
        // if 'arr[]' contains a single element
        // only
        if (n == 1)
            return;

        // traverse the array
        for (int i = 0; i < n - 1; i++) {

            // if true, perform the required modification
            if ((arr[i] != 0) && (arr[i] == arr[i + 1])) {

                // double current index value
                arr[i] = 2 * arr[i];

                // put 0 in the next index
                arr[i + 1] = 0;

                // increment by 1 so as to move two
                // indexes ahead during loop iteration
                i++;
            }
        }

        // push all the zeros at
        // the end of 'arr[]'
        pushZerosToEndUtil(arr, n);
    }

    static void reorderArrayAccordingToGivenIndexes(int arr[], int index[]) {
        /**
         * Input: arr[] = [10, 11, 12]; index[] = [1, 0, 2]; Output: arr[] = [11, 10,
         * 12] index[] = [0, 1, 2]
         * 
         * Input: arr[] = [50, 40, 70, 60, 90] index[] = [3, 0, 4, 1, 2] Output: arr[] =
         * [40, 60, 90, 50, 70] index[] = [0, 1, 2, 3, 4]
         */

        int arr1[] = Arrays.copyOf(arr, arr.length);
        int index1[] = Arrays.copyOf(index, index.length);

        /**
         * A Simple Solution is to use an auxiliary array temp[] of same size as given
         * arrays. Traverse the given array and put all elements at their correct place
         * in temp[] using index[]. Finally copy temp[] to arr[] and set all values of
         * index[i] as i.
         */
        int temp[] = new int[arr.length];
        // arr[i] should be present at index[i] index
        for (int i = 0; i < arr.length; i++)
            temp[index[i]] = arr[i];

        // Copy temp[] to arr[]
        for (int i = 0; i < arr.length; i++) {
            arr[i] = temp[i];
            index[i] = i;
        }

        /**
         * We can solve it Without Auxiliary Array. This swaps the elements source and
         * target in both arr and index array. Below is the algorithm.
         * 
         * 1) Do following for every element arr[i]
         * 
         * a) While index[i] is not equal to i
         * 
         * (i) Store array and index values of the target (or correct) position where
         * arr[i] should be placed. The correct position for arr[i] is index[i]
         * 
         * (ii)Place arr[i] at its correct position. Also update index value of correct
         * position.
         * 
         * (iii) Copy old values of correct position (Stored in step (i)) to arr[i] and
         * index[i] as the while loop continues for i.
         */
        // Fix all elements one by one
        for (int i = 0; i < arr1.length; i++) {
            // While index[i] and arr[i] are not fixed
            while (index1[i] != i) {
                // Store values of the target (or correct)
                // position before placing arr[i] there
                int oldTargetI = index1[index1[i]];
                char oldTargetE = (char) arr1[index1[i]];

                // Place arr[i] at its target (or correct)
                // position. Also copy corrected index for
                // new position
                arr1[index1[i]] = arr1[i];
                index1[index1[i]] = index1[i];

                // Copy old target values to arr[i] and
                // index[i]
                index1[i] = oldTargetI;
                arr1[i] = oldTargetE;
            }
        }
    }

    static void mergeNegativeFollowedByPositiveUtil(int arr[], int l, int m, int r) {
        int i, j, k;
        int n1 = m - l + 1;
        int n2 = r - m;

        /* create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /* Copy data to temp arrays L[] and R[] */
        for (i = 0; i < n1; i++)
            L[i] = arr[l + i];
        for (j = 0; j < n2; j++)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays back into arr[l..r] */
        // Initial index of first subarray
        i = 0;

        // Initial index of second subarray
        j = 0;

        // Initial index of merged subarray
        k = l;

        // Note the order of appearance of elements should
        // be maintained - we copy elements of left subarray
        // first followed by that of right subarray

        // copy negative elements of left subarray
        while (i < n1 && L[i] < 0)
            arr[k++] = L[i++];

        // copy negative elements of right subarray
        while (j < n2 && R[j] < 0)
            arr[k++] = R[j++];

        // copy positive elements of left subarray
        while (i < n1)
            arr[k++] = L[i++];

        // copy positive elements of right subarray
        while (j < n2)
            arr[k++] = R[j++];
    }

    static void negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_ExtraSpace(int arr[], int l, int r) {
        /**
         * Given an array of positive and negative numbers, arrange them such that all
         * negative integers appear before all the positive integers in the array
         * without using any additional data structure like hash table, arrays, etc. The
         * order of appearance should be maintained.
         * 
         * Examples:
         * 
         * Input: [12 11 -13 -5 6 -7 5 -3 -6] Output: [-13 -5 -7 -3 -6 12 11 6 5]
         */
        /**
         * Merge method of standard merge sort algorithm can be modified to solve this
         * problem. While merging two sorted halves say left and right, we need to merge
         * in such a way that negative part of left and right sub-array is copied first
         * followed by positive part of left and right sub-array.
         * 
         * Below is the implementation of the idea –
         */
        if (l < r) {
            // Same as (l + r)/2, but avoids overflow for
            // large l and h
            int m = l + (r - l) / 2;

            // Sort first and second halves
            negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_ExtraSpace(arr, l, m);
            negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_ExtraSpace(arr, m + 1, r);

            mergeNegativeFollowedByPositiveUtil(arr, l, m, r);
        }
    }

    static void reverse(int arr[], int l, int r) {
        if (l < r) {
            arr = swap(arr, l, r);
            reverse(arr, ++l, --r);
        }
    }

    static int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    static void mergeNegativeFollowedByPositiveUtilEfficient(int arr[], int l, int m, int r) {
        int i = l; // Initial index of 1st subarray
        int j = m + 1; // Initial index of IInd

        while (i <= m && arr[i] < 0)
            i++;// iterate until negatives are covered.

        // arr[i..m] is positive Left positive

        while (j <= r && arr[j] < 0)
            j++;// iterative until negatives are covered.

        // arr[j..r] is positive Right positive

        // reverse positive part of
        // left sub-array (arr[i..m])
        reverse(arr, i, m);

        // reverse negative part of
        // right sub-array (arr[m+1..j-1])
        reverse(arr, m + 1, j - 1);

        // reverse arr[i..j-1]
        reverse(arr, i, j - 1);// LP LN RP RN is the result.
    }

    static void negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_Efficient(int arr[], int l, int r) {
        /**
         * Given an array of positive and negative numbers, arrange them such that all
         * negative integers appear before all the positive integers in the array
         * without using any additional data structure like hash table, arrays, etc. The
         * order of appearance should be maintained.
         * 
         * Examples:
         * 
         * Input: [12 11 -13 -5 6 -7 5 -3 -6] Output: [-13 -5 -7 -3 -6 12 11 6 5]
         */
        /**
         * The time complexity of above solution is O(n log n). The problem with this
         * approach is we are using auxiliary array for merging but we’re not allowed to
         * use any data structure to solve this problem. We can do merging in-place
         * without using any data-structure. The idea is taken from here. Let Ln and Lp
         * denotes the negative part and positive part of left sub-array respectively.
         * Similarly, Rn and Rp denote the negative and positive part of right sub-array
         * respectively.
         * 
         * Below are the steps to convert [Ln Lp Rn Rp] to [Ln Rn Lp Rp] without using
         * extra space.
         * 
         * 1. Reverse Lp and Rn. We get [Lp] -> [Lp'] and [Rn] -> [Rn'] [Ln Lp Rn Rp] ->
         * [Ln Lp’ Rn’ Rp]
         * 
         * 2. Reverse [Lp’ Rn’]. We get [Rn Lp]. [Ln Lp’ Rn’ Rp] -> [Ln Rn Lp Rp] Below
         * is the implementation of above idea –
         * 
         */
        if (l < r) {
            // Same as (l+r)/2, but avoids overflow for
            // large l and h
            int m = l + (r - l) / 2;

            // Sort first and second halves
            negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_Efficient(arr, l, m);
            negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_Efficient(arr, m + 1, r);

            mergeNegativeFollowedByPositiveUtilEfficient(arr, l, m, r);
        }
    }

    static void arrangeGiveNumbersInArrayToFormBiggestNumber(List<String> arr) {
        /**
         * Given an array of numbers, arrange them in a way that yields the largest
         * value. For example, if the given numbers are {54, 546, 548, 60}, the
         * arrangement 6054854654 gives the largest value. And if the given numbers are
         * {1, 34, 3, 98, 9, 76, 45, 4}, then the arrangement 998764543431 gives the
         * largest value.
         * 
         * A simple solution that comes to our mind is to sort all numbers in descending
         * order, but simply sorting doesn’t work. For example, 548 is greater than 60,
         * but in output 60 comes before 548. As a second example, 98 is greater than 9,
         * but 9 comes before 98 in output.
         * 
         * So how do we go about it? The idea is to use any comparison based sorting
         * algorithm. In the used sorting algorithm, instead of using the default
         * comparison, write a comparison function myCompare() and use it to sort
         * numbers.
         * 
         * Given two numbers X and Y, how should myCompare() decide which number to put
         * first – we compare two numbers XY (Y appended at the end of X) and YX (X
         * appended at the end of Y). If XY is larger, then X should come before Y in
         * output, else Y should come before. For example, let X and Y be 542 and 60. To
         * compare X and Y, we compare 54260 and 60542. Since 60542 is greater than
         * 54260, we put Y first.
         * 
         * Following is the implementation of the above approach.
         */

        Collections.sort(arr, new Comparator<String>() {
            // A comparison function which is used by
            // sort() in printLargest()
            @Override
            public int compare(String X, String Y) {

                // first append Y at the end of X
                String XY = X + Y;

                // then append X at the end of Y
                String YX = Y + X;

                // Now see which of the two
                // formed numbers
                // is greater
                return XY.compareTo(YX) > 0 ? -1 : 1;
            }
        });

        Iterator it = arr.iterator();

        while (it.hasNext())
            System.out.print(it.next());
    }

    static void arrangeArraySuchThatJthElementBecomesIWhenIthElementIsJ(int[] arr) {
        /**
         * Given an array of size n where all elements are distinct and in range from 0
         * to n-1, change contents of arr[] so that arr[i] = j is changed to arr[j] = i.
         * Examples:
         * 
         * 
         * Example 1: Input: arr[] = {1, 3, 0, 2}; Output: arr[] = {2, 0, 3, 1};
         * Explanation for the above output. Since arr[0] is 1, arr[1] is changed to 0
         * Since arr[1] is 3, arr[3] is changed to 1 Since arr[2] is 0, arr[0] is
         * changed to 2 Since arr[3] is 2, arr[2] is changed to 3
         * 
         * A Simple Solution is to create a temporary array and one by one copy ‘i’ to
         * ‘temp[arr[i]]’ where i varies from 0 to n-1. But, this takes O(n) time and
         * O(n) space
         */
        /**
         * Solution 1: Can we solve this in O(n) time and O(1) auxiliary space? The idea
         * is based on the fact that the modified array is basically a permutation of
         * the input array. We can find the target permutation by storing the next item
         * before updating it. Let us consider array ‘{1, 3, 0, 2}’ for example. We
         * start with i = 0, arr[i] is 1. So we go to arr[1] and change it to 0 (because
         * i is 0). Before we make the change, we store the old value of arr[1] as the
         * old value is going to be our new index i. In the next iteration, we have i =
         * 3, arr[3] is 2, so we change arr[2] to 3. Before making the change we store
         * next i as old value of arr[2]. The below code gives idea about this approach.
         */

        // 'val' is the value to be stored at 'arr[i]'
        int val = 0; // The next value is determined
        // using current index
        int i = arr[0]; // The next index is determined
        // using current value

        // While all elements in cycle are not processed
        while (i != 0) {
            // Store value at index as it is going to be
            // used as next index
            int new_i = arr[i];

            // Update arr[]
            arr[i] = val;

            // Update value and index for next iteration
            val = i;
            i = new_i;
        }

        arr[0] = val; // Update the value at arr[0]
        // The above function doesn’t work for inputs like {2, 0, 1, 4, 5, 3}; as there
        // are two cycles. One cycle is (2, 0, 1) and other cycle is (4, 5, 3).

    }

    static void rearrangeJthElementBecomesIWhenIthElementIsJUtil(int arr[], int n, int i) {
        // 'val' is the value to be stored at 'arr[i]'

        // The next value is determined using current index
        int val = -(i + 1);// To indicate that it is processed.

        // The next index is determined
        // using current value
        i = arr[i] - 1;

        // While all elements in cycle are not processed
        while (arr[i] > 0) {
            // Store value at index as it is going to be
            // used as next index
            int new_i = arr[i] - 1;

            // Update arr[]
            arr[i] = val;

            // Update value and index for next iteration
            val = -(i + 1);
            i = new_i;
        }
    }

    static void arrangeArraySuchThatJthElementBecomesIWhenIthElementIsJ_WorkingSolution(int[] arr) {
        /**
         * The above function doesn’t work for inputs like {2, 0, 1, 4, 5, 3}; as there
         * are two cycles. One cycle is (2, 0, 1) and other cycle is (4, 5, 3). How to
         * handle multiple cycles with the O(1) space constraint? The idea is to process
         * all cycles one by one. To check whether an element is processed or not, we
         * change the value of processed items arr[i] as -arr[i]. Since 0 can not be
         * made negative, we first change all arr[i] to arr[i] + 1. In the end, we make
         * all values positive and subtract 1 to get old values back.
         */

        // Increment all values by 1, so that all elements
        // can be made negative to mark them as visited
        int n = arr.length;
        int i;
        for (i = 0; i < n; i++)
            arr[i]++;

        // Process all cycles
        for (i = 0; i < n; i++) {
            // Process cycle starting at arr[i] if this cycle is
            // not already processed
            if (arr[i] > 0)
                rearrangeJthElementBecomesIWhenIthElementIsJUtil(arr, n, i);
        }

        // Change sign and values of arr[] to get the original
        // values back, i.e., values in range from 0 to n-1
        for (i = 0; i < n; i++)
            arr[i] = (-arr[i]) - 1;

    }

    static void rearrangeAnArrayInMaximumMinimumForm(int arr[]) {
        /**
         * Given a sorted array of positive integers, rearrange the array alternately
         * i.e first element should be maximum value, second minimum value, third second
         * max, fourth second min and so on.
         * 
         * Examples:
         * 
         * Input: arr[] = {1, 2, 3, 4, 5, 6, 7} Output: arr[] = {7, 1, 6, 2, 5, 3, 4}
         * 
         * Input: arr[] = {1, 2, 3, 4, 5, 6} Output: arr[] = {6, 1, 5, 2, 4, 3}
         */
        /**
         * The idea is to use an auxiliary array. We maintain two pointers one to
         * leftmost or smallest element and other to rightmost or largest element. We
         * move both pointers toward each other and alternatively copy elements at these
         * pointers to an auxiliary array. Finally, we copy the auxiliary array back to
         * the original array.
         */
        // Auxiliary array to hold modified array
        int n = arr.length;
        int temp[] = arr.clone();

        // Indexes of smallest and largest elements
        // from remaining array.
        int small = 0, large = n - 1;

        // To indicate whether we need to copy rmaining
        // largest or remaining smallest at next position
        boolean flag = true;

        // Store result in temp[]
        for (int i = 0; i < n; i++) {
            if (flag)
                arr[i] = temp[large--];
            else
                arr[i] = temp[small++];

            flag = !flag;
        }
    }

    static void negativeNumbersFirstPositiveNumbersLastWithoutOrder_Ntime(int arr[]) {
        int n = arr.length;
        /**
         * Approach 1: The idea is to simply apply the partition process of quicksort.
         */
        int j = 0, temp;
        for (int i = 1; i < n; i++) {
            if (arr[i] < 0) {
                if (i != j) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
                j++;
            }
        }
        /**
         * Approach 2:Two Pointer Approach: The idea is to solve this problem with
         * constant space and linear time is by using a two-pointer or two-variable
         * approach where we simply take two variables like left and right which hold
         * the 0 and N-1 indexes. Just need to check that :
         * 
         * 1. Check If the left and right pointer elements are negative then simply
         * increment the left pointer. 2. Otherwise, if the left element is positive and
         * the right element is negative then simply swap the elements, and
         * simultaneously increment and decrement the left and right pointers. 3. Else
         * if the left element is positive and the right element is also positive then
         * simply decrement the right pointer. 4. Repeat the above 3 steps until the
         * left pointer ≤ right pointer.
         */
        int left = 0, right = n - 1;
        // Loop to iterate over the
        // array from left to the right
        while (left <= right) {

            // Condition to check if the left
            // and the right elements are
            // negative
            if (arr[left] < 0 && arr[right] < 0)
                left++;

            // Condition to check if the left
            // pointer element is positive and
            // the right pointer element is negative
            else if (arr[left] > 0 && arr[right] < 0) {
                int temp1 = arr[left];
                arr[left] = arr[right];
                arr[right] = temp1;
                left++;
                right--;
            }

            // Condition to check if both the
            // elements are positive
            else if (arr[left] > 0 && arr[right] > 0)
                right--;
            else {
                left++;
                right--;
            }
        }
        // Both the appraoches take O(N) complexity.
    }

    static void replaceEveryArrayElementByMultiplicationOfPrevAndNext(int arr[]) {
        int n = arr.length;
        // Nothing to do when array size is 1
        if (n <= 1)
            return;

        // store current value of arr[0] and update it
        int prev = arr[0];
        arr[0] = arr[0] * arr[1];

        // Update rest of the array elements
        for (int i = 1; i < n - 1; i++) {
            // Store current value of next interation
            int curr = arr[i];

            // Update current value using previous value
            arr[i] = prev * arr[i + 1];

            // Update previous value
            prev = curr;
        }

        // Update last array element
        arr[n - 1] = prev * arr[n - 1];
    }

    static void randomizeAGivenArrayInNTime(int arr[]) {
        /**
         * Given an array, write a program to generate a random permutation of array
         * elements. This question is also asked as “shuffle a deck of cards” or
         * “randomize a given array”. Here shuffle means that every permutation of array
         * element should equally likely.
         * 
         * Let the given array be arr[]. A simple solution is to create an auxiliary
         * array temp[] which is initially a copy of arr[]. Randomly select an element
         * from temp[], copy the randomly selected element to arr[0] and remove the
         * selected element from temp[]. Repeat the same process n times and keep
         * copying elements to arr[1], arr[2], … . The time complexity of this solution
         * will be O(n^2).
         * 
         * Fisher–Yates shuffle Algorithm works in O(n) time complexity. The assumption
         * here is, we are given a function rand() that generates random number in O(1)
         * time. The idea is to start from the last element, swap it with a randomly
         * selected element from the whole array (including last). Now consider the
         * array from 0 to n-2 (size reduced by 1), and repeat the process till we hit
         * the first element. Following is the detailed algorithm
         * 
         * 
         * To shuffle an array a of n elements (indices 0..n-1): for i from n - 1 downto
         * 1 do j = random integer with 0 <= j <= i exchange a[j] and a[i]
         */
        // Creating a object for Random class
        Random r = new Random();
        int n = arr.length;

        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = n - 1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = r.nextInt(i + 1);

            // Swap arr[i] with the element at random index
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        // Prints the random array
        System.out.println(Arrays.toString(arr));
    }

    static void segregateEvenAndOddNumbers(int[] arr) {
        /**
         * Given an array of integers, segregate even and odd numbers in the array. All
         * the even numbers should be present first, and then the odd numbers.
         * 
         * Examples:
         * 
         * Input: arr[] = 1 9 5 3 2 6 7 11 Output: 2 6 5 3 1 9 7 11
         * 
         * Input: arr[] = 1 3 2 4 7 6 9 10 Output: 2 4 6 10 7 1 9 3
         */
        /**
         * Efficient Approach:
         * 
         * The optimization for above approach is based on Lomuto’s Partition Scheme
         * 
         * Maintain a pointer to the position before first odd element in the array.
         * Traverse the array and if even number is encountered then swap it with the
         * first odd element. Continue the traversal.
         */
        int n = arr.length;
        int i = -1, j = 0;
        while (j != n) {
            if (arr[j] % 2 == 0) {
                i++;

                // Swapping even and
                // odd numbers
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
            j++;
        }

        // Printing segregated array
        for (int k = 0; k < n; k++)
            System.out.print(arr[k] + " ");
    }

    // Start of Test Data
    static void testDataAlternatingPositiveNegativeItemsUsingSortMethod() {
        int[] arr = { -5, -2, 5, 2, 4, 7, 1, 8, 0, -8 }; // positive > negative
        alternatingPositiveNegativeNumbersUsingSort(arr);

        int[] arr1 = { -5, -2, 5, 2, -3, -7, -1, 8, 0, -8 };// negative > positive
        System.out.print("input array is ");
        print(arr1, arr1.length);
        alternatingPositiveNegativeNumbersUsingSort(arr1);
    }

    static void testDataAlternatingPositiveNEgativeNumbersUsingRotationEfficientMethod() {
        int[] arr = { -5, -2, 5, 2, 4, 7, 1, 8, 0, -8 }; // positive > negative
        alternatingPositiveNEgativeNumbersUsingRotationEfficientMethod(arr);
        print(arr, arr.length);

        int[] arr1 = { -5, -2, 5, 2, -3, -7, -1, 8, 0, -8 };// negative > positive
        alternatingPositiveNEgativeNumbersUsingRotationEfficientMethod(arr1);
        print(arr1, arr1.length);
    }

    static void testDataPositiveNumbersAtEvenAndNegativeNumbersAtOddPositions() {
        int arr[] = { 1, -3, 5, 6, -3, 6, 7, -4, 9, 10 };
        int n = arr.length;

        positiveNumbersAtEvenAndNegativeNumbersAtOddPositions(arr);
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }

    static void testDataRearrangeArraySuchThatIndexIcontainsDataI() {
        int[] arr = { -1, -1, 6, 1, 9, 3, 2, -1, 4, -1 };
        rearrangeArraySuchThatIndexIcontainsDataI(arr);
    }

    static void testDataElementsAtEvenPositionAreGreaterBeforeItAndOddPositionsAreLessThanElementsBeforeIt() {
        int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7 };
        elementsAtEvenPositionAreGreaterBeforeItAndOddPositionsAreLessThanElementsBeforeIt(arr);
    }

    static void testDataAlternatingPositiveNegativeNumbersWithQuickSortParition() {
        int arr[] = { -1, 2, -3, 4, 5, 6, -7, 8, 9 };
        int n = arr.length;
        alternatingPositiveNegativeNumbersWithQuickSortPartition(arr);
        System.out.println("Array after rearranging: ");
        print(arr, n);
    }

    static void testDataMoveAllZeroesToEndOfArray() {
        int arr[] = { 1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0, 9 };
        int n = arr.length;
        moveAllZeroesToEndOfArray(arr);
        System.out.println("Array after pushing zeros to the back: ");
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }

    static void testDataMinSwapsToKeepElementsLessThanOrEqualToKTogether() {
        int arr[] = { 2, 1, 5, 6, 3 };
        int k = 3;
        System.out.print(minSwapsToKeepElementsLessThanOrEqualToKTogether(arr, k) + "\n");
        int arr1[] = { 10, 12, 20, 20, 5, 19, 19, 12, 1, 20, 1 };
        k = 14;
        System.out.print(minSwapsToKeepElementsLessThanOrEqualToKTogether(arr1, k));
    }

    static void testDataRearrangeEvenPositionElementsAreGreaterThanOddNLogN() {
        int arr[] = { 1, 3, 2, 2, 5 };
        rearrangeEvenPositionElementsAreGreaterThanOddNLogN(arr);
    }

    static void testDataRearrangeEvenPositionElementsAreGreaterThanOddNTime() {
        int arr[] = { 1, 2, 2, 1 };
        rearrangeEvenPositionElementsAreGreaterThanOddNTime(arr);
    }

    static void testDataRearrangeOddIndexGreaterThanEvenIndexNTime() {
        int arr[] = { 6, 4, 2, 1, 8, 3 };
        int n = arr.length;

        System.out.print("Before rearranging: \n");
        print(arr, n);

        rearrangeOddIndexGreaterThanEvenIndexNTime(arr);

        System.out.print("After rearranging: \n");
        print(arr, n);
    }

    static void testDataRearrangeIncreasingLargestAndSmallestElementsInArray() {
        int arr[] = { 5, 8, 1, 4, 2, 9, 3, 7, 6 };
        increasingLargestAndSmallestElementsInArray(arr);
    }

    static void testDataDoubleFirstElementAndMoveZeroToEnd() {
        int arr[] = { 0, 2, 2, 2, 0, 6, 6, 0, 0, 8 };
        int n = arr.length;

        System.out.print("Original array: ");
        print(arr, n);

        doubleFirstElementAndMoveZeroToEnd(arr);

        System.out.print("Modified array: ");
        print(arr, n);
    }

    static void testDataReorderArrayAccordingToGivenIndexes() {
        int arr[] = { 50, 40, 70, 60, 90 };
        int index[] = { 3, 0, 4, 1, 2 };
        int n = arr.length;

        reorderArrayAccordingToGivenIndexes(arr, index);

        System.out.println("Reordered array is: ");
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");

        System.out.println();

        System.out.println("Modified Index array is: ");
        for (int i = 0; i < n; i++)
            System.out.print(index[i] + " ");
    }

    static void testDataNegativeNumbersFirstPositiveNumbersLastWithOrderMaintained_ExtraSpace() {
        int arr[] = { -12, 11, -13, -5, 6, -7, 5, -3, -6 };
        int arr_size = arr.length;
        negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_ExtraSpace(arr, 0, arr_size - 1);
        print(arr, arr_size);
    }

    static void testDataNegativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_Efficient() {
        int arr[] = { -12, 11, -13, -5, 6, -7, 5, -3, -6 };
        int arr_size = arr.length;
        negativeNumbersFirstPositiveNumbersLastInArrayWithOrderMaintained_Efficient(arr, 0, arr_size - 1);
        print(arr, arr_size);
    }

    static void testDataArrangeGiveNumbersInArrayToFormBiggestNumber() {
        List<String> arr;
        arr = new ArrayList<>();

        // output should be 6054854654
        arr.add("54");
        arr.add("546");
        arr.add("548");
        arr.add("60");
        arrangeGiveNumbersInArrayToFormBiggestNumber(arr);
    }

    static void testDataArrangeArraySuchThatJthElementBecomesIWhenIthElementIsJ_WorkingSolution() {
        int arr[] = { 2, 0, 1, 4, 5, 3 };
        int n = arr.length;

        System.out.println("Given array is ");
        print(arr, n);

        // arrangeArraySuchThatJthElementBecomesIWhenIthElementIsJ(arr);
        arrangeArraySuchThatJthElementBecomesIWhenIthElementIsJ_WorkingSolution(arr);

        System.out.println("Modified array is ");
        print(arr, n);
    }

    static void testDataRearrangeAnArrayInMaximumMinimumForm() {
        int arr[] = new int[] { 1, 2, 3, 4, 5, 6 };

        System.out.println("Original Array ");
        System.out.println(Arrays.toString(arr));

        rearrangeAnArrayInMaximumMinimumForm(arr);

        System.out.println("Modified Array ");
        System.out.println(Arrays.toString(arr));
    }

    static void testDataNegativeNumbersFirstPositiveNumbersLastWithoutOrder_Ntime() {
        int[] arr = { -12, 11, -13, -5, 6, -7, 5, -3, 11 };
        negativeNumbersFirstPositiveNumbersLastWithoutOrder_Ntime(arr);
    }

    static void testDataReplaceEveryArrayElementByMultiplicationOfPrevAndNext() {
        int arr[] = { 2, 3, 4, 5, 6 };
        int n = arr.length;
        replaceEveryArrayElementByMultiplicationOfPrevAndNext(arr);
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }

    static void testDataRandomizeAGivenArrayInNTime() {
        int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8 };
        randomizeAGivenArrayInNTime(arr);
    }

    static void testDataSegregateEvenAndOddNumbers() {
        int arr[] = { 1, 3, 2, 4, 7, 6, 9, 10 };
        int n = arr.length;
        segregateEvenAndOddNumbers(arr);
    }

    public static void main(String[] args) {
        testDataAlternatingPositiveNEgativeNumbersUsingRotationEfficientMethod();
    }

}
