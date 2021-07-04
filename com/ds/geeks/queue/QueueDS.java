package com.ds.geeks.queue;

import java.util.*;

public class QueueDS {

  static class RotOrangesBFS {

    public static final int R = 3;
    public static final int C = 5;

    // structure for storing coordinates of the cell
    static class Orange {

      int x = 0;
      int y = 0;

      Orange(int x, int y) {
        this.x = x;
        this.y = y;
      }
    }

    // function to check whether a cell is valid / invalid
    static boolean isValid(int i, int j) {
      return (i >= 0 && j >= 0 && i < R && j < C);
    }

    // Function to check whether the cell is delimiter
    // which is (-1, -1)
    static boolean isDelim(Orange temp) {
      return (temp.x == -1 && temp.y == -1);
    }

    // Function to check whether there is still a fresh
    // orange remaining
    static boolean checkAll(int arr[][]) {
      for (int i = 0; i < R; i++)
        for (int j = 0; j < C; j++)
          if (arr[i][j] == 1)
            return true;
      return false;
    }

    // This function finds if it is possible to rot all oranges or not.
    // If possible, then it returns minimum time required to rot all,
    // otherwise returns -1
    static int rotOranges(int arr[][]) {
      // Create a queue of cells
      Queue<Orange> Q = new LinkedList<Orange>();
      Orange temp;
      int ans = 0;
      // Store all the cells having rotten orange in first time frame
      for (int i = 0; i < R; i++) {
        for (int j = 0; j < C; j++) {
          if (arr[i][j] == 2)
            Q.add(new Orange(i, j));
        }
      }

      // Separate these rotten oranges from the oranges which will rotten
      // due the oranges in first time frame using delimiter which is (-1, -1)
      Q.add(new Orange(-1, -1));

      // Process the grid while there are rotten oranges in the Queue
      while (!Q.isEmpty()) {
        // This flag is used to determine whether even a single fresh
        // orange gets rotten due to rotten oranges in the current time
        // frame so we can increase the count of the required time.
        boolean flag = false;

        // Process all the rotten oranges in current time frame.
        while (!isDelim(Q.peek())) {
          temp = Q.peek();

          // Check right adjacent cell that if it can be rotten
          if (isValid(temp.x + 1, temp.y) && arr[temp.x + 1][temp.y] == 1) {
            if (!flag) {
              // if this is the first orange to get rotten, increase
              // count and set the flag.
              ans++;
              flag = true;
            }
            // Make the orange rotten
            arr[temp.x + 1][temp.y] = 2;

            // push the adjacent orange to Queue
            Q.add(new Orange(temp.x + 1, temp.y));
          }

          // Check left adjacent cell that if it can be rotten
          if (isValid(temp.x - 1, temp.y) && arr[temp.x - 1][temp.y] == 1) {
            if (!flag) {
              ans++;
              flag = true;
            }
            arr[temp.x - 1][temp.y] = 2;
            Q.add(new Orange(temp.x - 1, temp.y)); // push this cell to Queue
          }

          // Check top adjacent cell that if it can be rotten
          if (isValid(temp.x, temp.y + 1) && arr[temp.x][temp.y + 1] == 1) {
            if (!flag) {
              ans++;
              flag = true;
            }
            arr[temp.x][temp.y + 1] = 2;
            Q.add(new Orange(temp.x, temp.y + 1)); // Push this cell to Queue
          }

          // Check bottom adjacent cell if it can be rotten
          if (isValid(temp.x, temp.y - 1) && arr[temp.x][temp.y - 1] == 1) {
            if (!flag) {
              ans++;
              flag = true;
            }
            arr[temp.x][temp.y - 1] = 2;
            Q.add(new Orange(temp.x, temp.y - 1)); // push this cell to Queue
          }
          Q.remove();
        }
        // Pop the delimiter
        Q.remove();

        // If oranges were rotten in current frame than separate the
        // rotten oranges using delimiter for the next frame for processing.
        if (!Q.isEmpty()) {
          Q.add(new Orange(-1, -1));
        }
        // If Queue was empty than no rotten oranges left to process so exit
      }

      // Return -1 if all arranges could not rot, otherwise ans
      return (checkAll(arr)) ? -1 : ans;
    }
  }

  // Construct MatrixGraphBFS from Matrix N*M and Do a BFS
  static class MatrixGraphBFS {

    LinkedList<Integer> aList[];
    int n, m;
    private static final int MAX = 500;

    MatrixGraphBFS(int a, int b) {
      this.n = a;
      this.m = b;
      aList = new LinkedList[500];
      for (int i = 0; i < 500; i++) {
        aList[i] = new LinkedList<Integer>();
      }
    }

    // Function to create graph with N*M nodes
    // considering each cell as a node and each
    // boundary as an edge.
    void createGraph() {
      int k = 1; // A number to be assigned to a cell

      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          // If last row, then add edge on right side.
          if (i == n) {
            // If not bottom right cell.
            if (j != m) {
              aList[k].add(k + 1);
              aList[k + 1].add(k);
            }
          }
          // If last column, then add edge toward down.
          else if (j == m) {
            aList[k].add(k + m);
            aList[k + m].add(k);
          }
          // Else makes an edge in all four directions.
          else {
            aList[k].add(k + 1);
            aList[k + 1].add(k);
            aList[k].add(k + m);
            aList[k + m].add(k);
          }

          k++;
        }
      }
    }

    // BFS function to find minimum distance
    void bfs(boolean visit[], int dist[], Queue<Integer> q) {
      while (!q.isEmpty()) {
        int temp = q.peek();
        q.remove();

        for (int i = 0; i < aList[temp].size(); i++) {
          int adjIndex = aList[temp].get(i);
          if (visit[adjIndex] == false) {
            dist[adjIndex] = dist[temp] + 1;
            // min(dist[adjIndex], dist[temp] + 1)
            q.add(adjIndex);
            visit[adjIndex] = true;
          }
        }
      }
    }

    // Printing the solution.
    void print(int dist[]) {
      for (int i = 1, c = 1; i <= n * m; i++, c++) {
        System.out.print(dist[i] + " ");

        if (c % m == 0)
          System.out.println("");
      }
    }

    // Find minimum distance
    static void findMinDistanceInMatrixUsingBFS(int mat[][], int N, int M) {
      /**
       * Given a binary matrix of N x M, containing at least a value 1. The task is to
       * find the distance of nearest 1 in the matrix for each cell. The distance is
       * calculated as |i1 – i2| + |j1 – j2|, where i1, j1 are the row number and
       * column number of the current cell and i2, j2 are the row number and column
       * number of the nearest cell having value 1.
       * 
       * Examples:
       * 
       * Input : N = 3, M = 4 mat[][] = { 0, 0, 0, 1,
       * 
       * 0, 0, 1, 1,
       * 
       * 0, 1, 1, 0 }
       * 
       * Output :
       * 
       * 3 2 1 0
       * 
       * 2 1 0 0
       * 
       * 1 0 0 1 Explanation: For cell at (0, 0), nearest 1 is at (0, 3), so distance
       * = (0 - 0) + (3 - 0) = 3. Similarly, all the distance can be calculated.
       * 
       * Input : N = 3, M = 3 mat[][] = { 1, 0, 0,
       * 
       * 0, 0, 1,
       * 
       * 0, 1, 1 }
       * 
       * Output :
       * 
       * 0 1 1
       * 
       * 1 1 0
       * 
       * 1 0 0 Explanation: For cell at (0, 1), nearest 1 is at (0, 0), so distance is
       * 1. Similarly, all the distance can be calculated.
       */

      /**
       * Approach: The idea is to use multisource Breadth-First Search. Consider each
       * cell as a node and each boundary between any two adjacent cells be an edge.
       * Number each cell from 1 to N*M. Now, push all the node whose corresponding
       * cell value is 1 in the matrix in the queue. Apply BFS using this queue to
       * find the minimum distance of the adjacent node. Algorithm:
       * 
       * 1. Create a graph with values assigned from 1 to M*N to all vertices. The
       * purpose is to store position and adjacent information.
       *
       * 2. Create an empty queue.
       * 
       * 3. Traverse all matrix elements and insert positions of all 1s in queue.
       * 
       * 4. Now do a BFS traversal of graph using above created queue.
       * 
       * 5. Run a loop till the size of the queue is greater than 0 then extract the
       * front node of the queue and remove it and insert all its adjacent and
       * unmarked elements. Update the minimum distance as distance of current node +1
       * and insert the element in the queue.
       */
      // Creating a graph with nodes values assigned
      // from 1 to N x M and matrix adjacent.
      MatrixGraphBFS g1 = new MatrixGraphBFS(N, M);
      g1.createGraph();

      // To store minimum distance
      int[] dist = new int[MAX];

      // To mark each node as visited or not in BFS
      boolean[] visit = new boolean[MAX];

      // Initialising the value of distance and visit.
      for (int i = 1; i <= M * N; i++) {
        dist[i] = Integer.MAX_VALUE;
        visit[i] = false;
      }

      // Inserting nodes whose value in matrix
      // is 1 in the queue.
      int k = 1;
      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
          if (mat[i][j] == 1) {
            dist[k] = 0;
            visit[k] = true;
            q.add(k);
          }
          k++;
        }
      }

      // Calling for Bfs with given Queue.
      g1.bfs(visit, dist, q);

      // Printing the solution.
      g1.print(dist);
    }
  }

  static class MatrixQueueOnlyBFS {

    private static void minimumDistanceLevelWiseTraversal(int[][] matrix) {
      int n = matrix.length;
      int m = matrix[0].length;
      // create an array ans of size same as matrix array
      int ans[][] = new int[n][m];
      // create a queue of coordinates
      // push all the elements that are equals to 1 in the matrix array to the queue
      Queue<Coordinate> queue = new LinkedList<>();
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          if (matrix[i][j] == 1) {
            queue.add(new Coordinate(i, j));
          }
        }
      }
      // initialize minDistance as 0
      int minDistance = 0;
      while (!queue.isEmpty()) {
        // initialize size as size of queue
        int size = queue.size();
        // Run a loop size times
        for (int i = 0; i < size; i++) {
          // remove an element from queue
          Coordinate curr = queue.poll();
          // ans to this coordinate is minDistance
          ans[curr.row][curr.col] = minDistance;
          // enqueue all the valid adjacent cells of curr that are equals to
          // 0 in the matrix array and set them as 1
          // left adjacent
          int leftRow = curr.row - 1;
          int leftCol = curr.col;
          if ((leftRow >= 0 && leftRow < n) && (leftCol >= 0 && leftCol < m)) {
            if (matrix[leftRow][leftCol] == 0) {
              queue.add(new Coordinate(leftRow, leftCol));
              matrix[leftRow][leftCol] = 1;
            }
          }
          // right adjacent
          int rightRow = curr.row + 1;
          int rightCol = curr.col;
          if ((rightRow >= 0 && rightRow < n) && (rightCol >= 0 && rightCol < m)) {
            if (matrix[rightRow][rightCol] == 0) {
              queue.add(new Coordinate(rightRow, rightCol));
              matrix[rightRow][rightCol] = 1;
            }
          }
          // up adjacent
          int upRow = curr.row;
          int upCol = curr.col + 1;
          if ((upRow >= 0 && upRow < n) && (upCol >= 0 && upCol < m)) {
            if (matrix[upRow][upCol] == 0) {
              queue.add(new Coordinate(upRow, upCol));
              matrix[upRow][upCol] = 1;
            }
          }
          // down adjacent
          int downRow = curr.row;
          int downCol = curr.col - 1;
          if ((downRow >= 0 && downRow < n) && (downCol >= 0 && downCol < m)) {
            if (matrix[downRow][downCol] == 0) {
              queue.add(new Coordinate(downRow, downCol));
              matrix[downRow][downCol] = 1;
            }
          }
        }
        // increment minimum distance
        minDistance++;
      }
      // print the elements of the ans array
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          System.out.print(ans[i][j] + " ");
        }
        System.out.println();
      }
      System.out.println();
    }

    // class representing coordinates of a cell in matrix
    static class Coordinate {

      int row;
      int col;

      public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
      }
    }
  }

  static class LRUCache {

    // store keys of cache
    private Deque<Integer> doublyQueue;

    // store references of key in cache
    private HashSet<Integer> hashSet;

    // maximum capacity of cache
    private final int CACHE_SIZE;

    LRUCache(int capacity) {
      doublyQueue = new LinkedList<>();
      hashSet = new HashSet<>();
      CACHE_SIZE = capacity;
    }

    /* Refer the page within the LRU cache */
    public void refer(int page) {
      if (!hashSet.contains(page)) {
        if (doublyQueue.size() == CACHE_SIZE) {
          int last = doublyQueue.removeLast();
          hashSet.remove(last);
        }
      } else {/*
               * The found page may not be always the last element, even if it's an
               * intermediate element that needs to be removed and added to the start of the
               * Queue
               */
        doublyQueue.remove(page);
      }
      doublyQueue.push(page);
      hashSet.add(page);
    }

    // display contents of cache
    public void display() {
      Iterator<Integer> itr = doublyQueue.iterator();
      while (itr.hasNext()) {
        System.out.print(itr.next() + " ");
      }
    }
  }

  static class LRUCacheWithLinkedHashSet {

    Set<Integer> cache;
    int capacity;

    public LRUCacheWithLinkedHashSet(int capacity) {
      this.cache = new LinkedHashSet<Integer>(capacity);
      this.capacity = capacity;
    }

    // This function returns false if key is not
    // present in cache. Else it moves the key to
    // front by first removing it and then adding
    // it, and returns true.
    public boolean get(int key) {
      if (!cache.contains(key))
        return false;
      cache.remove(key);
      cache.add(key);
      return true;
    }

    /* Refers key x with in the LRU cache */
    public void refer(int key) {
      if (get(key) == false)
        put(key);
    }

    // displays contents of cache in Reverse Order
    public void display() {
      LinkedList<Integer> list = new LinkedList<>(cache);

      // The descendingIterator() method of java.util.LinkedList
      // class is used to return an iterator over the elements
      // in this LinkedList in reverse sequential order
      Iterator<Integer> itr = list.descendingIterator();

      while (itr.hasNext())
        System.out.print(itr.next() + " ");
    }

    public void put(int key) {
      if (cache.size() == capacity) {
        int firstKey = cache.iterator().next();
        cache.remove(firstKey);
      }

      cache.add(key);
    }
  }

  static class SlidingWindow {

    // A Dequeue (Double ended queue)
    // based method for printing
    // maximum element of
    // all subarrays of size k
    static void printMax(int arr[], int n, int k) {
      // Create a Double Ended Queue, Qi
      // that will store indexes of array elements
      // The queue will store indexes of
      // useful elements in every window and it will
      // maintain decreasing order of values
      // from front to rear in Qi, i.e.,
      // arr[Qi.front[]] to arr[Qi.rear()]
      // are sorted in decreasing order
      Deque<Integer> Qi = new LinkedList<Integer>();

      /*
       * Process first k (or first window) elements of array
       */
      int i;
      for (i = 0; i < k; ++i) {
        // For every element, the previous
        // smaller elements are useless so
        // remove them from Qi
        while (!Qi.isEmpty() && arr[i] >= arr[Qi.peekLast()])
          Qi.removeLast(); // Remove from rear

        // Add new element at rear of queue
        Qi.addLast(i);
      }

      // Process rest of the elements,
      // i.e., from arr[k] to arr[n-1]
      for (; i < n; ++i) {
        // The element at the front of the
        // queue is the largest element of
        // previous window, so print it
        System.out.print(arr[Qi.peek()] + " ");

        // Remove the elements which
        // are out of this window
        while ((!Qi.isEmpty()) && Qi.peek() <= i - k)
          Qi.removeFirst();

        // Remove all elements smaller
        // than the currently
        // being added element (remove
        // useless elements)
        while ((!Qi.isEmpty()) && arr[i] >= arr[Qi.peekLast()])
          Qi.removeLast();

        // Add current element at the rear of Qi
        Qi.addLast(i);
      }

      // Print the maximum element of last window
      System.out.print(arr[Qi.peek()]);
    }
  }

  // Driver program
  public static void testDataRotOranges() {
    int arr[][] = { { 2, 1, 0, 2, 1 }, { 1, 0, 1, 2, 1 }, { 1, 0, 0, 2, 1 } };
    int ans = RotOrangesBFS.rotOranges(arr);
    if (ans == -1)
      System.out.println("All oranges cannot rot");
    else
      System.out.println("Time required for all oranges to rot = " + ans);
  }

  // Driven Program
  public static void testDataFindDistanceOfNearestCellInBinaryMatrix() {
    int mat[][] = { { 0, 0, 0, 1 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } };

    MatrixGraphBFS.findMinDistanceInMatrixUsingBFS(mat, mat.length, mat[0].length);
  }

  static void testDataFindDistanceOfNearestCellQueueOnlyBFS() {
    // Example 1
    int matrix1[][] = new int[][] { { 0, 1, 0 }, { 0, 0, 0 }, { 1, 0, 0 } };
    MatrixQueueOnlyBFS.minimumDistanceLevelWiseTraversal(matrix1);
    // Example 2
    int matrix2[][] = new int[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 1, 0, 1 } };
    MatrixQueueOnlyBFS.minimumDistanceLevelWiseTraversal(matrix2);
  }

  public static void testDataLRUCacheUsingDLLQueueAndHashSet() {
    LRUCache cache = new LRUCache(4);
    cache.refer(1);
    cache.refer(2);
    cache.refer(3);
    cache.refer(1);
    cache.refer(4);
    cache.refer(5);
    cache.refer(2);
    cache.refer(2);
    cache.refer(1);
    cache.display();
  }

  public static void testDataLRUCacheUsingLinkedHashSet() {
    LRUCacheWithLinkedHashSet ca = new LRUCacheWithLinkedHashSet(4);
    ca.refer(1);
    ca.refer(2);
    ca.refer(3);
    ca.refer(1);
    ca.refer(4);
    ca.refer(5);
    ca.display();
  }

  // Driver code
  public static void testDataSlidingWindowMaximum() {
    int arr[] = { 12, 1, 78, 90, 57, 89, 56 };
    int k = 3;
    SlidingWindow.printMax(arr, arr.length, k);
  }
}
