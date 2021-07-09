package com.ds.geeks.arrays;

import java.util.Arrays;

import javax.crypto.spec.PSource.PSpecified;

public class ArrayRotations {

  static void leftRotate(int arr[], int d) // Time complexity: O(n)
  {
    // Rotate an element by d.
    // Input: 1 2 3 4 5 6 7, d=2
    // Output: 3 4 5 6 7 1 2
    if (d == 0)
      return;

    int n = arr.length;
    // in case the rotating factor is
    // greater than array length
    d = d % n;
    reverseArray(arr, 0, d - 1);// Ar
    reverseArray(arr, d, n - 1);// Br
    reverseArray(arr, 0, n - 1);// (ArBr)r = BA
    /**
     * Let the array be arr[] = [1, 2, 3, 4, 5, 6, 7], d =2 and n = 7 A = [1, 2] and
     * B = [3, 4, 5, 6, 7] Reverse A, we get ArB = [2, 1, 3, 4, 5, 6, 7] Reverse B,
     * we get ArBr = [2, 1, 7, 6, 5, 4, 3] Reverse all, we get (ArBr)r = [3, 4, 5,
     * 6, 7, 1, 2]
     */
  }

  static void printMultipleLeftRotationsOfArray(int arr[], int k) {
    int n = arr.length;
    // Print array after
    // k rotations
    /**
     * Input is {1, 3, 5, 7, 9} k=2, 5 7 9 1 3 k=3, 7 9 1 3 5 k=4, 9 1 3 5 7
     */
    for (int i = k; i < k + n; i++)
      System.out.print(arr[i % n] + " ");

  }

  static void rightRotate(int arr[], int d) {
    /**
     * Input: arr[] = {121, 232, 33, 43 ,5} k = 2 Output: 43 5 121 232 33
     * 
     * Solution: 1. reverse array: 5, 43, 33, 232, 121 2. reverse d elements: 43 5
     * 33 232 121 3. reverse d..n-1 elements: 43 5 121 232 33
     */
    int n = arr.length;
    reverseArray(arr, 0, n - 1);
    reverseArray(arr, 0, d - 1);
    reverseArray(arr, d, n - 1);
  }

  static void cyclicalRotateAnArrayByOne(int arr[]) {
    /**
     * Given an array, cyclically rotate the array clockwise by one. Examples:
     * 
     * Input: arr[] = {1, 2, 3, 4, 5} Output: arr[] = {5, 1, 2, 3, 4}
     * 
     * Solution:
     * 
     * We can use two pointers, say i and j which point to first and last element of
     * array respectively. As we know in cyclic rotation we will bring last element
     * to first and shift rest in forward direction, so start swaping arr[i] and
     * arr[j] and keep j fixed and i moving towards j. Repeat till i is not equal to
     * j.
     */
    int i = 0, j = arr.length - 1;
    while (i != j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
      i++;
    }
  }

  static int searchAnElementInSortedAndRotatedArray(int arr[], int n, int key) {
    //O(logn)
    /**
     * Suppose we rotate an ascending order sorted array at some pivot. So for
     * instance, 1 2 3 4 5 might become 3 4 5 1 2. Devise a way to find an element
     * in the rotated array in O(log n) time.
     * 
     * Input : arr[] = {30, 40, 50, 10, 20} key = 10 Output : Found at index 3
     */

    /*
     * Searches an element key in a pivoted sorted array arrp[] of size n
     */
    int pivot = findPivot(arr, 0, n - 1);

    // If we didn't find a pivot, then
    // array is not rotated at all
    if (pivot == -1)
      return binarySearch(arr, 0, n - 1, key);

    // If we found a pivot, then first
    // compare with pivot and then
    // search in two subarrays around pivot
    if (arr[pivot] == key)
      return pivot;
    if (key >= arr[0])
      return binarySearch(arr, 0, pivot - 1, key);
    return binarySearch(arr, pivot + 1, n - 1, key);
  }

  /*
   * Function to get pivot. For array 3, 4, 5, 6, 1, 2 it returns 3 (index of 6), increasing and decreasing.
   */
  static int findPivot(int arr[], int low, int high) {
    // base cases
    if (high < low)
      return -1;
    if (high == low)
      return low;
      
    /* low + (high - low)/2; */
    int mid = (low + high) / 2;
    if (mid < high && arr[mid] > arr[mid + 1])
      return mid;
    if (mid > low && arr[mid] < arr[mid - 1])
      return (mid - 1);
    if (arr[low] >= arr[mid]) // 6 7 1 2 3 4 5, low is 6, mid is 2.
      return findPivot(arr, low, mid - 1);
    return findPivot(arr, mid + 1, high); // 3 4 5 6 7 8 1  low is 3, mid is 6
  }

  // This function returns
  // count of number of pairs
  // with sum equals to x.
  static int pairsInSortedRotated(int arr[], int n, int x) {
    // Find the pivot element.
    // Pivot element is largest
    // element of array.
    int i;
    for (i = 0; i < n - 1; i++)
      if (arr[i] > arr[i + 1])
        break;

    // l is index of
    // smallest element.
    int l = (i + 1) % n;

    // r is index of
    // largest element.
    int r = i;

    // Variable to store
    // count of number
    // of pairs.
    int cnt = 0;

    // Find sum of pair
    // formed by arr[l]
    // and arr[r] and
    // update l, r and
    // cnt accordingly.
    /**
     * Input: {11, 15, 6, 7, 9, 10}; 
     * Sum = 16 
     * L is 15, R is 6 
     * L is 11, R is 6 
     * L is 10, R is 6 = SUM 
     * L is 9, R is 7 = SUM 
     * L == (R-1+N)%N, return. 
     * If the left and right indexes are same, return the count
     */
    while (l != r) {
      // If we find a pair with
      // sum x, then increment
      // cnt, move l and r to
      // next element.
      if (arr[l] + arr[r] == x) {
        cnt++;

        // This condition is required
        // to be checked, otherwise
        // l and r will cross each
        // other and loop will never
        // terminate.
        if (l == (r - 1 + n) % n) {
          return cnt;
        }

        l = (l + 1) % n; // NOTE: Traverse forward
        r = (r - 1 + n) % n;// NOTE: Traverse backward in a rotated array.
      }

      // If current pair sum
      // is less, move to
      // the higher sum side.
      else if (arr[l] + arr[r] < x)
        l = (l + 1) % n;

      // If current pair sum
      // is greater, move
      // to the lower sum side.
      else
        r = (n + r - 1) % n;
    }

    return cnt;
  }

  static int maxSumOfElementsAfterEveryRotationsOnArray(int arr[]) {

    // time complexity: O(n), space complexity: O(1)
    // Find max sum of = i*arr[i] for rotations from 0...n-1

    /**
     * Let Rj be value of i*arr[i] with j rotations. The idea is to calculate next
     * rotation value from previous rotation, i.e., calculate Rj from Rj-1. We can
     * calculate initial value of result as R0, then keep calculating next rotation
     * values.
     * 
     * Rj - Rj-1 = arrSum - n * arr[n-j]
     * 
     * How we dervied this?
     * 
     * Let arr = [A0, A1, A2, A3]
     * 
     * R0 = 0*A0 + 1*A1 + 2*A2 + 3*A3 R1 = 0*A3 + 1*A0 + 2*A1 + 3*A2
     * 
     * R1 - R0 = A0 + (2*A1 - 1*A1) + (3*A2 - 2*A2) - 3*A3 = A0 + A1 + A2 - 3*A3 ->
     * This has missing A3 in arrSum = A0 + A1 + A2 + (A3 - A3) - 3*A3 -> Adding
     * Subtracting A3 = A0 + A1 + A2 + A3 - 4*A3 = arrSum - n*arr[n-j]
     * 
     * R1 = R0 + arrSum - n*arr[n-j]
     */
    int arrSum = 0; // Stores sum of arr[i]
    int currVal = 0; // Stores sum of i*arr[i]
    for (int i = 0; i < arr.length; i++) {
      arrSum = arrSum + arr[i];
      currVal = currVal + (i * arr[i]);
    }

    // Initialize result as 0 rotation sum
    int maxVal = currVal;

    // Try all rotations one by one and find
    // the maximum rotation sum.
    for (int j = 1; j < arr.length; j++) {
      currVal = currVal + arrSum - arr.length * arr[arr.length - j];
      if (currVal > maxVal)
        maxVal = currVal;
    }

    // Return result
    return maxVal;
  }

  // Returns count of rotations for an array
  // which is first sorted in ascending order,
  // then rotated
  static int countRotations(int arr[], int low, int high) {
    // This condition is needed to handle
    // the case when array is not rotated
    // at all
    if (high < low)
      return 0;

    // If there is only one element left
    if (high == low)
      return low;

    // Find mid
    // /*(low + high)/2;*/
    int mid = low + (high - low) / 2;

    // Check if element (mid+1) is minimum
    // element. Consider the cases like
    // {3, 4, 5, 1, 2}
    if (mid < high && arr[mid + 1] < arr[mid])
      return (mid + 1);//gives the number of array elements rotated.

    // Check if mid itself is minimum element
    if (mid > low && arr[mid] < arr[mid - 1])
      return mid;

    // Decide whether we need to go to left
    // half or right half
    if (arr[high] > arr[mid])
      return countRotations(arr, low, mid - 1);

    return countRotations(arr, mid + 1, high);
  }

  static int findMinElementInSortedAndRotatedArray(int arr[], int low, int high) {
    // This condition is needed to handle the case when array
    // is not rotated at all
    if (high < low)
      return arr[0];

    // If there is only one element left
    if (high == low)
      return arr[low];

    // Find mid
    int mid = low + (high - low) / 2; /* (low + high)/2; */

    // Check if element (mid+1) is minimum element. Consider
    // the cases like {3, 4, 5, 1, 2}
    if (mid < high && arr[mid + 1] < arr[mid])
      return arr[mid + 1];

    // Check if mid itself is minimum element
    if (mid > low && arr[mid] < arr[mid - 1])
      return arr[mid];

    // Decide whether we need to go to left half or right half
    if (arr[high] > arr[mid])
      return findMinElementInSortedAndRotatedArray(arr, low, mid - 1);
    return findMinElementInSortedAndRotatedArray(arr, mid + 1, high);
  }

  static int findHammingDistance(int[] x, int[] y) {
    int count = 0;
    for (int i = 0; i < x.length; i++) {
      if (x[i] != y[i]) {
        count += 1;
      }
    }
    return count;
  }

  static int maxHammingDistance(int arr[], int n) {
    /**
     * Given an array of n elements, create a new array which is a rotation of given
     * array and hamming distance between both the arrays is maximum. Hamming
     * distance between two arrays or strings of equal length is the number of
     * positions at which the corresponding character(elements) are different.
     */
    int max_h = Integer.MIN_VALUE;
    int a[] = Arrays.copyOf(arr, n);

    for (int i = 0; i < n; i++) {
      leftRotate(arr, 1);
      int curr_h_dist = findHammingDistance(a, arr);
      if (curr_h_dist > max_h) {
        max_h = curr_h_dist;
      }
    }
    return max_h;
  }

  static int toRotate = 0;

  // Function to solve query of type 1 x.
  static void querytype1(int times, int n) {
    // Decreasing the absolute rotation
    toRotate = ((toRotate) - times) % n;
  }

  // Function to solve query of type 2 y.
  static void querytype2(int times, int n) {
    // Increasing the absolute rotation.
    (toRotate) = ((toRotate) + times) % n;
  }

  // Function to solve queries of type 3 l r.
  static void querytype3(int l, int r, int preSum[], int n) {
    // Finding absolute l and r.
    l = (l + toRotate + n) % n;
    r = (r + toRotate + n) % n;

    // if l is before r.
    if (l <= r)
      System.out.println((preSum[r + 1] - preSum[l]));

    // If r is before l.
    else
      System.out.println((preSum[n] + preSum[r + 1] - preSum[l]));
  }

  static void queriesOnLeftAndRightCircularShiftOnArray(int a[]) {
    // Calculate presum of array. 
    /**
     * Given an array A of N integers. There are three types of commands: 1 x :
     * Right Circular Shift the array x times. If an array is a[0], a[1], …., a[n –
     * 1], then after one right circular shift the array will become a[n – 1], a[0],
     * a[1], …., a[n – 2]. 2 y : Left Circular Shift the array y times. If an array
     * is a[0], a[1], …., a[n – 1], then after one left circular shift the array
     * will become a[1], …., a[n – 2], a[n – 1], a[0]. 3 l r : Print the sum of all
     * integers in the subarray a[l…r] (l and r inclusive). Given Q queries, the
     * task is to execute each query. Examples:
     * 
     * 
     * Input : n = 5, arr[] = { 1, 2, 3, 4, 5 } query 1 = { 1, 3 } query 2 = { 3, 0,
     * 2 } query 3 = { 2, 1 } query 4 = { 3, 1, 4 } Output : 12 11 Initial array
     * arr[] = { 1, 2, 3, 4, 5 } After query 1, arr[] = { 3, 4, 5, 1, 2 }. After
     * query 2, sum from index 0 to index 2 is 12, so output 12. After query 3,
     * arr[] = { 4, 5, 1, 2, 3 }. After query 4, sum from index 1 to index 4 is 11,
     * so output 11.
     */
    /**
     * Solution: We can evaluate the prefix sum of all elements in the array,
     * prefixsum[i] will denote the sum of all the integers upto ith index. Now, if
     * we want to find sum of elements between two indexes i.e l and r, we compute
     * it in constant time by just calculating prefixsum[r] – prefixsum[l – 1]. We
     * just need to track the net rotation. If the tracked number is negative, it
     * means left rotation has dominated else right rotation has dominated.
     */

    int n = a.length;
    int[] preSum = new int[n + 1];
    preSum[0] = 0;

    // Finding Prefix sum
    for (int i = 1; i <= n; i++)
      preSum[i] = preSum[i - 1] + a[i - 1];

    // Solving each query
    querytype1(3, n);
    querytype3(0, 2, preSum, n);
    querytype2(1, n);
    querytype3(1, 4, preSum, n);

  }

  static int findElementInIndexAfterGivenRotations(int[] arr, int[][] ranges, int rotations, int index) {
    /**
     * An array consisting of N integers is given. There are several Right Circular
     * Rotations of range[L..R] that we perform. After performing these rotations,
     * we need to find element at a given index. Examples :
     * 
     * 
     * Input : arr[] : {1, 2, 3, 4, 5} ranges[] = { {0, 2}, {0, 3} } index : 1
     * Output : 3 Explanation : After first given rotation {0, 2} arr[] = {3, 1, 2,
     * 4, 5} After second rotation {0, 3} arr[] = {4, 3, 1, 2, 5} After all
     * rotations we have element 3 at given index 1.
     * 
     * Suppose, our rotate ranges are : [0..2] and [0..3] We run through these
     * ranges from reverse. After range [0..3], index 0 will have the element which
     * was on index 3. So, we can change 0 to 3, i.e. if index = left, index will be
     * changed to right. After range [0..2], index 3 will remain unaffected. So, we
     * can make 3 cases : If index = left, index will be changed to right. If index
     * is not bounds by the range, no effect of rotation. If index is in bounds,
     * index will have the element at index-1.
     * 
     * Index: 1
     * 
     * Rotations: {0,2} {1,4} {0,3}
     * 
     * Answer: Index 1 will have 30 after all the 3 rotations in the order {0,2}
     * {1,4} {0,3}.
     * 
     * We performed {0,2} on A and now we have a new array A1.
     * 
     * We performed {1,4} on A1 and now we have a new array A2.
     * 
     * We performed {0,3} on A2 and now we have a new array A3.
     * 
     * Now we are looking for the value at index 1 in A3.
     * 
     * But A3 is {0,3} done on A2.
     * 
     * So index 1 in A3 is index 0 in A2.
     * 
     * But A2 is {1,4} done on A1.
     * 
     * So index 0 in A2 is also index 0 in A1 as it does not lie in the range {1,4}.
     * 
     * But A1 is {0,2} done on A.
     * 
     * So index 0 in A1 is index 2 in A.
     */

    for (int i = rotations - 1; i >= 0; i--) {

      // Range[left...right]
      int left = ranges[i][0];
      int right = ranges[i][1];

      // Rotation will not have any effect
      if (index >= left && index <= right) {
        if (index == left)
          index = right;
        else
          index--;
      }
    }

    // Returning new element
    return arr[index];

  }

  static void splitArrayAndAddTheFirstPartToEnd(int[] arr, int rotation) {
    /**
     * There is a given array and split it from a specified position, and move the
     * first part of the array add to the end.
     * 
     * Input : arr[] = {12, 10, 5, 6, 52, 36} k = 2 Output : arr[] = {5, 6, 52, 36,
     * 12, 10} Explanation : Split from index 2 and first part {12, 10} add to the
     * end .
     * 
     * Input : arr[] = {3, 1, 2} k = 1 Output : arr[] = {1, 2, 3} Explanation :
     * Split from index 1 and first part add to the end.
     * 
     * Solution: Make a temporary array with double the size and copy our array
     * element into a new array twice . and then copy the element from the new array
     * to our array
     * 
     * Take the rotation as the starting index and iterate until rotation+n , and
     * copy the element to the array.
     */

    // make a temporary array with double the size
    int length = arr.length;
    int[] tmp = new int[length * 2];

    // copy array element in to new array twice
    System.arraycopy(arr, 0, tmp, 0, length);
    System.arraycopy(arr, 0, tmp, length, length);
    for (int i = rotation; i < rotation + length; i++)
      arr[i - rotation] = tmp[i];
  }

  /* Standard Binary Search function */
  static int binarySearch(int arr[], int low, int high, int key) {
    if (high < low)
      return -1;

    /* low + (high - low)/2; */
    int mid = (low + high) / 2;
    if (key == arr[mid])
      return mid;
    if (key > arr[mid])
      return binarySearch(arr, (mid + 1), high, key);
    return binarySearch(arr, low, (mid - 1), key);
  }

  /* Function to reverse arr[] from index start to end */
  static void reverseArray(int arr[], int start, int end) {
    int temp;
    while (start < end) {
      temp = arr[start];
      arr[start] = arr[end];
      arr[end] = temp;
      start++;
      end--;
    }
  }

  /* UTILITY FUNCTIONS */
  /* function to print an array */
  static void printArray(int arr[]) {
    for (int i = 0; i < arr.length; i++)
      System.out.print(arr[i] + " ");
  }

  /* Driver program to test above functions */
  public static void leftRotateAnArrayByD(String[] args) {
    int arr[] = { 1, 2, 3, 4, 5, 6, 7 };
    int d = 2;

    ArrayRotations.leftRotate(arr, d); // Rotate array by d
    printArray(arr);
  }

  public static void testDataCyclicalRotateAnArrayByOne() {
    int arr[] = new int[] { 1, 2, 3, 4, 5 };
    System.out.println("Given Array is");
    System.out.println(Arrays.toString(arr));

    ArrayRotations.cyclicalRotateAnArrayByOne(arr);

    System.out.println("Rotated Array is");
    System.out.println(Arrays.toString(arr));
  }

  public static void testDataSearchAnElementInSortedAndPivotedArray() {
    // Let us search 3 in below array
    int arr1[] = { 5, 6, 7, 8, 9, 10, 1, 2, 3 };
    int n = arr1.length;
    int key = 3;
    System.out
        .println("Index of the element is : " + ArrayRotations.searchAnElementInSortedAndRotatedArray(arr1, n, key));
  }

  public static void testDataMaxSumOfElementsWithRotationsOnArray() {
    int arr[] = new int[] { 10, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    System.out.println("Max sum of elements is : " + ArrayRotations.maxSumOfElementsAfterEveryRotationsOnArray(arr));
  }

  public static void testDataCountRotations() {
    int arr[] = { 15, 18, 2, 3, 6, 12 };
    int n = arr.length;

    System.out.println(ArrayRotations.countRotations(arr, 0, n - 1));
  }

  public static void testDataFindMinElementInSortedAndRotatedArray() {
    int arr1[] = { 5, 6, 1, 2, 3, 4 };
    int n1 = arr1.length;
    System.out
        .println("The minimum element is " + ArrayRotations.findMinElementInSortedAndRotatedArray(arr1, 0, n1 - 1));

    int arr2[] = { 1, 2, 3, 4 };
    int n2 = arr2.length;
    System.out
        .println("The minimum element is " + ArrayRotations.findMinElementInSortedAndRotatedArray(arr2, 0, n2 - 1));
  }

  public static void testDataRightRotateArray() {

    int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    int k = 3;

    ArrayRotations.rightRotate(arr, k);
    printArray(arr);
  }

  public static void testDataFindMaxHammingDistance() {
    int arr[] = { 2, 4, 6, 8 };
    int n = arr.length;
    System.out.print(ArrayRotations.maxHammingDistance(arr, n));
  }

  public static void testDataFindElementInIndexAfterGivenRotations() {
    // Driver

    int[] arr = { 1, 2, 3, 4, 5 };

    // No. of rotations
    int rotations = 2;

    // Ranges according to 0-based indexing
    int[][] ranges = { { 0, 2 }, { 0, 3 } };

    int index = 1;
    System.out.println(ArrayRotations.findElementInIndexAfterGivenRotations(arr, ranges, rotations, index));
  }

  public static void testDataSplitArrayAndAddTheFirstPartToEnd() {
    int arr[] = { 12, 10, 5, 6, 52, 36 };
    int n = arr.length;
    int position = 2;

    splitArrayAndAddTheFirstPartToEnd(arr, position);

    for (int i = 0; i < n; ++i)
      System.out.print(arr[i] + " ");
  }
  public static void main(String[] args){
    // testDataCyclicalRotateAnArrayByOne();
    // testDataSearchAnElementInSortedAndPivotedArray();
    // testDataMaxSumOfElementsWithRotationsOnArray();
    //testDataCountRotations();
    // testDataFindMinElementInSortedAndRotatedArray();
    // testDataRightRotateArray();
    // testDataFindMaxHammingDistance();
    // testDataFindElementInIndexAfterGivenRotations();
    // testDataSplitArrayAndAddTheFirstPartToEnd();
  }
}
