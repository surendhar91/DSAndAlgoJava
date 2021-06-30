package com.ds.geeks.stack;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

public class StackDS {

  static class StackUsingSingleQueue {

    // Stack using a single queue.
    Queue<Integer> q = new LinkedList<Integer>();

    // Push operation
    void push(int val) {
      // get previous size of queue
      int size = q.size();

      // Add current element
      q.add(val);

      // Pop (or Dequeue) all previous
      // elements and put them after current
      // element
      for (int i = 0; i < size; i++) {
        // this will add front element into
        // rear of queue
        int x = q.remove();
        q.add(x);
      }
    }

    // Removes the top element
    int pop() {
      if (q.isEmpty()) {
        System.out.println("No elements");
        return -1;
      }
      int x = q.remove();
      return x;
    }

    // Returns top of stack
    int top() {
      if (q.isEmpty()) return -1;
      return q.peek();
    }

    // Returns true if Stack is empty else false
    boolean isEmpty() {
      return q.isEmpty();
    }
  }

  static class StackUsingQueues { // To maintain current number of

    // elements
    static int curr_size; // Two inbuilt queues
    static Queue<Integer> q1 = new LinkedList<Integer>();
    static Queue<Integer> q2 = new LinkedList<Integer>();

    StackUsingQueues() {
      curr_size = 0;
    }

    static void push(int x) {
      curr_size++;

      // Push x first in empty q2
      q2.add(x);

      // Push all the remaining
      // elements in q1 to q2.
      while (!q1.isEmpty()) {
        q2.add(q1.peek());
        q1.remove();
      }

      // swap the names of two queues
      Queue<Integer> q = q1;
      q1 = q2;
      q2 = q;
    }

    static void pop() {
      // if no elements are there in q1
      if (q1.isEmpty()) return;
      q1.remove();
      curr_size--;
    }

    static int top() {
      if (q1.isEmpty()) return -1;
      return q1.peek();
    }

    static int size() {
      return curr_size;
    }
  }

  static class SortStackUsingRecursion {

    // Recursive Method to insert an item x in sorted way
    static void sortedInsert(Stack<Integer> s, int x) {
      // Base case: Either stack is empty or newly
      // inserted item is greater than top (more than all
      // existing)
      if (s.isEmpty() || x > s.peek()) {
        s.push(x);
        return;
      }

      // If top is greater, remove the top item and recur
      int temp = s.pop();
      sortedInsert(s, x);

      // Put back the top item removed earlier
      s.push(temp);
    }

    // Method to sort stack
    static void sortStackRecur(Stack<Integer> s) {
      // If stack is not empty
      if (!s.isEmpty()) {
        // Remove the top item
        int x = s.pop();

        // Sort remaining stack
        sortStackRecur(s);

        // Push the top item back in sorted stack
        sortedInsert(s, x);
      }
    }

    // Utility Method to print contents of stack
    static void printStack(Stack<Integer> s) {
      ListIterator<Integer> lt = s.listIterator();

      // forwarding
      while (lt.hasNext()) lt.next();

      // printing from top to bottom
      while (lt.hasPrevious()) System.out.print(lt.previous() + " ");
    }
  }

  // This function return the sorted stack
  public static Stack<Integer> sortStackUsingTempStack(Stack<Integer> input) {
    Stack<Integer> tmpStack = new Stack<Integer>();
    while (!input.isEmpty()) {
      // pop out the first element
      int tmp = input.pop();

      // while temporary stack is not empty and
      // top of stack is greater than temp
      while (!tmpStack.isEmpty() && tmpStack.peek() > tmp) {
        // pop from temporary stack and
        // push it to the input stack
        input.push(tmpStack.pop());
      }

      // push temp in tempory of stack
      tmpStack.push(tmp);
    }
    return tmpStack;
  }

  static class FindMaxDiffBwnLeftAndRightSmallerElement {

    // Find prev smaller and next smaller element.
    // Java program to find the difference b/w left and
    // right smaller element of every element in array

    // Function to fill left smaller element for every
    // element of arr[0..n-1]. These values are filled
    // in SE[0..n-1]
    static void leftSmaller(int arr[], int n, int SE[]) {
      // Create an empty stack
      Stack<Integer> S = new Stack<>();

      // Traverse all array elements
      // compute nearest smaller elements of every element
      for (int i = 0; i < n; i++) {
        // Keep removing top element from S while the top
        // element is greater than or equal to arr[i]
        while (!S.empty() && S.peek() >= arr[i]) {
          S.pop();
          // To store the next smallest element, add the below code instead
          /**
           *  int currElementIndex = S.peek();
           *  next[currElementIndex] = arr[i];
           *  S.pop();
           *  prev[currElementIndex] = S.empty()?: 0: S.peek();
           *
           *  Example, consider this example,
           *  Arr: 8 2 4 7 3
           * // stack: [8], 4 is the next smallest element
           *  Next: 4
           *  Prev: 0
           *
           *  // stack: [2, 4, 7], next element is 3
           *        4  7
           *  Next: 3  3
           *  Prev: 2  4
           *  // stack: [2, 3]
           *  while (!stack.isEmpty())
           * {
           *   int currElementIndex = S.peek();
           *   next[currElementIndex] = 0;
           *   S.pop();
           *   prev[currElementIndex] = S.empty()?: 0: S.peek();
           * }
           */
        }

        // Store the smaller element of current element
        if (!S.empty()) { // this stores the left (prev) smallest element, and not the next
          // smallest element.
          SE[i] = S.peek();
        }
        // If all elements in S were greater than arr[i]
        else {
          SE[i] = 0;
        }
        /* To store both the next and prev smallest element, here is the code

             
            
            
            * */
        // Push this element
        S.push(arr[i]);
      }
    }

    // Function returns maximum difference b/w Left &
    // right smaller element
    static int findMaxDiff(int arr[], int n) {
      int[] LS = new int[n]; // To store left smaller elements

      // find left smaller element of every element
      leftSmaller(arr, n, LS);

      // find right smaller element of every element
      // first reverse the array and do the same process
      int[] RRS = new int[n]; // To store right smaller elements in

      // reverse array
      reverse(arr);
      leftSmaller(arr, n, RRS);

      // find maximum absolute difference b/w LS & RRS
      // In the reversed array right smaller for arr[i] is
      // stored at RRS[n-i-1]
      int result = -1;
      for (int i = 0; i < n; i++) {
        result = Math.max(result, Math.abs(LS[i] - RRS[n - 1 - i]));
      }

      // return maximum difference b/w LS & RRS
      return result;
    }

    static void reverse(int a[]) {
      int i, k, n = a.length;
      int t;
      for (i = 0; i < n / 2; i++) {
        t = a[i];
        a[i] = a[n - i - 1];
        a[n - i - 1] = t;
      }
    }
  }

  static class CountNumberOfNGEsToRight {

    static void fillNext(int next[], int a[], int n) {
      // Use stack
      Stack<Integer> s = new Stack<Integer>();

      // push the 0th index to the stack
      s.push(0);

      // traverse in the loop from 1-nth index
      for (int i = 1; i < n; i++) {
        // iterate till loop is empty
        while (s.size() > 0) {
          // get the topmost index in the stack
          int cur = s.peek();

          // if the current element is greater
          // then the top index-th element, then
          // this will be the next greatest index
          // of the top index-th element
          if (a[cur] < a[i]) {
            // initialize the cur index position's
            // next greatest as index
            next[cur] = i;

            // pop the cur index as its greater
            // element has been found
            s.pop();
          }
          // if not greater then break
          else break;
        }

        // push the i index so that its next greatest
        // can be found
        s.push(i);
      }

      // iterate for all other index left inside stack
      while (s.size() > 0) {
        int cur = s.peek();

        // mark it as -1 as no element in greater
        // then it in right
        next[cur] = -1;

        s.pop();
      }
    }

    // function to count the number of
    // next greater numbers to the right
    static void countNumberOfNGEsToRight(int a[], int dp[], int n) {
      // initializes the next array as 0
      int next[] = new int[n];
      for (int i = 0; i < n; i++) next[i] = 0;

      // calls the function to pre-calculate
      // the next greatest element indexes
      fillNext(next, a, n);

      for (int i = n - 2; i >= 0; i--) {
        // if the i-th element has no next
        // greater element to right
        if (next[i] == -1) dp[i] = 0;
        // Count of next greater numbers to right.
        else dp[i] = 1 + dp[next[i]];
      }
    }

    // answers all queries in O(1)
    static int answerQuery(int dp[], int index) {
      // returns the number of next greater
      // elements to the right.
      return dp[index];
    }
  }

  //Function to find largest rectangular area possible in a given histogram.
  // Find left and right smaller element of every ith element in an array, and find the
  // largest rectangular area in the histogram.
  public static int getMaxRectangleArea(int arr[], int n) {
    // your code here
    //we create an empty stack here.
    Stack<Integer> s = new Stack<>();
    //we push -1 to the stack because for some elements there will be no previous
    //smaller element in the array and we can store -1 as the index for previous smaller.
    s.push(-1);
    int max_area = arr[0];
    //We declare left_smaller and right_smaller array of size n and initialize them with -1 and n as their default value.
    //left_smaller[i] will store the index of previous smaller element for ith element of the array.
    //right_smaller[i] will store the index of next smaller element for ith element of the array.
    int left_smaller[] = new int[n];
    int right_smaller[] = new int[n];
    for (int i = 0; i < n; i++) {
      left_smaller[i] = -1;
      right_smaller[i] = n;
    }

    int i = 0;
    while (i < n) {
      while (!s.empty() && s.peek() != -1 && arr[i] < arr[s.peek()]) {
        //if the current element is smaller than element with index stored on the
        //top of stack then, we pop the top element and store the current element index
        //as the right_smaller for the poped element.
        right_smaller[s.peek()] = (int) i;
        s.pop();
      }
      if (i > 0 && arr[i] == arr[(i - 1)]) {
        //we use this condition to avoid the unnecessary loop to find the left_smaller.
        //since the previous element is same as current element, the left_smaller will always be the same for both.
        left_smaller[i] = left_smaller[(int) (i - 1)];
      } else {
        //Element with the index stored on the top of the stack is always smaller than the current element.
        //Therefore the left_smaller[i] will always be s.top().
        left_smaller[i] = s.peek();
      }
      s.push(i);
      i++;
    }

    for (i = 0; i < n; i++) {
      //here we find area with every element as the smallest element in their range and compare it with the previous area.
      // in this way we get our max Area form this.
      max_area =
        Math.max(max_area, arr[i] * (right_smaller[i] - left_smaller[i] - 1));
    }

    return max_area;
  }

  static void printMaxOfMinForEveryWindowSizeInArray(int arr[], int n) {
    // Used to find previous and next smaller
    Stack<Integer> s = new Stack<>();

    // Arrays to store previous and next smaller
    int left[] = new int[n + 1];
    int right[] = new int[n + 1];

    // Initialize elements of left[] and right[]
    for (int i = 0; i < n; i++) {
      left[i] = -1;
      right[i] = n;
    }

    // Fill elements of left[] using logic discussed on
    // https://www.geeksforgeeks.org/next-greater-element/
    for (int i = 0; i < n; i++) {
      while (!s.empty() && arr[s.peek()] >= arr[i]) s.pop();

      if (!s.empty()) left[i] = s.peek();

      s.push(i);
    }

    // Empty the stack as stack is
    // going to be used for right[]
    while (!s.empty()) s.pop();

    // Fill elements of right[] using same logic
    for (int i = n - 1; i >= 0; i--) {
      while (!s.empty() && arr[s.peek()] >= arr[i]) s.pop();

      if (!s.empty()) right[i] = s.peek();

      s.push(i);
    }

    // Create and initialize answer array
    int ans[] = new int[n + 1];
    for (int i = 0; i <= n; i++) ans[i] = 0;

    // Fill answer array by comparing minimums of all
    // lengths computed using left[] and right[]
    for (int i = 0; i < n; i++) {
      // length of the interval
      int len = right[i] - left[i] - 1;

      // arr[i] is a possible answer for this length
      // 'len' interval, check if arr[i] is more than
      // max for 'len'
      ans[len] = Math.max(ans[len], arr[i]);
    }

    // Some entries in ans[] may not be filled yet. Fill
    // them by taking values from right side of ans[]
    for (int i = n - 1; i >= 1; i--) ans[i] = Math.max(ans[i], ans[i + 1]);

    // Print the result
    for (int i = 1; i <= n; i++) System.out.print(ans[i] + " ");
  }

  // Driver program to test above methods
  public static void testDataStackUsingSingleQueue() {
    StackUsingSingleQueue s = new StackUsingSingleQueue();
    s.push(10);
    s.push(20);
    System.out.println("Top element :" + s.top());
    s.pop();
    s.push(30);
    s.pop();
    System.out.println("Top element :" + s.top());
  }

  // driver code
  public static void testDatastackUsingTwoQueeus() {
    StackUsingQueues s = new StackUsingQueues();
    s.push(1);
    s.push(2);
    s.push(3);

    System.out.println("current size: " + s.size());
    System.out.println(s.top());
    s.pop();
    System.out.println(s.top());
    s.pop();
    System.out.println(s.top());

    System.out.println("current size: " + s.size());
  }

  // Driver code
  public static void testDataSortAStackUsingRecursion() {
    Stack<Integer> s = new Stack<>();
    s.push(30);
    s.push(-5);
    s.push(18);
    s.push(14);
    s.push(-3);

    System.out.println("Stack elements before sorting: ");
    SortStackUsingRecursion.printStack(s);

    SortStackUsingRecursion.sortStackRecur(s);

    System.out.println(" \n\nStack elements after sorting:");
    SortStackUsingRecursion.printStack(s);
  }

  // Driver Code
  public static void testDataSortStackUsingTempStack() {
    Stack<Integer> input = new Stack<Integer>();
    input.add(34);
    input.add(3);
    input.add(31);
    input.add(98);
    input.add(92);
    input.add(23);

    // This is the temporary stack
    Stack<Integer> tmpStack = sortStackUsingTempStack(input);
    System.out.println("Sorted numbers are:");

    while (!tmpStack.empty()) {
      System.out.print(tmpStack.pop() + " ");
    }
  }

  // Driver code
  public static void testDataFindMaxDiffBetweenLeftAndRightSmallerElement() {
    int arr[] = { 2, 4, 8, 7, 7, 9, 3 };
    int n = arr.length;
    System.out.println(
      "Maximum diff : " +
      FindMaxDiffBwnLeftAndRightSmallerElement.findMaxDiff(arr, n)
    );
  }

  // driver code
  public static void testDataNumberOfNGEsToRight() {
    int a[] = { 3, 4, 2, 7, 5, 8, 10, 6 };
    int n = a.length;

    int dp[] = new int[n];

    // calls the function to count the number
    // of greater elements to the right for
    // every element.
    CountNumberOfNGEsToRight.countNumberOfNGEsToRight(a, dp, n);

    // query 1 answered
    System.out.println(CountNumberOfNGEsToRight.answerQuery(dp, 3));

    // query 2 answered
    System.out.println(CountNumberOfNGEsToRight.answerQuery(dp, 6));

    // query 3 answered
    System.out.println(CountNumberOfNGEsToRight.answerQuery(dp, 1));
  }

  public static void testDataFindMaxRectangleAreaInHistogram() {
    int hist[] = { 6, 2, 5, 4, 5, 1, 6 };
    System.out.println(
      "Maximum area is " + getMaxRectangleArea(hist, hist.length)
    );
  }

  // Driver method
  public static void testDataPrintMaxOfMinForEveryWindowSizeInArray() {
    int arr[] = { 10, 20, 30, 50, 10, 70, 30 };
    printMaxOfMinForEveryWindowSizeInArray(arr, arr.length);
  }

  public static void main(String args[]) {
    testDataStackUsingSingleQueue();
    //stackUsingTwoQueeusTestData();
    //sortAStackUsingRecursionTestData();
    // sortStackUsingTempStackTestData();
    //findMaxDiffBetweenLeftAndRightSmallerElement();
    //numberOfNGEsToRightTestData();
    //findMaxRectangleAreaInHistogramTestData();
    //printMaxOfMinForEveryWindowSizeInArrayTestData();
  }
}
