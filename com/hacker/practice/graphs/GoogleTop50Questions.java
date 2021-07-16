package com.hacker.practice.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GoogleTop50Questions {

    static class MaximalRectangleInMatrix_findMaxAreaOfRectangleInBinaryMatrix_heightOfHistogram_DP_andStack {
        public static int maximalRectangleUsingDP(char[][] matrix) {
            /**
             * Given a rows x cols binary matrix filled with 0's and 1's, find the largest
             * rectangle containing only 1's and return its area.
             * 
             * 
             * 
             * Example 1:
             * 
             * 
             * Input: matrix =
             * [["1","0","1","0","0"],
             * ["1","0","1","1","1"],
             * ["1","1","1","1","1"],
             * ["1","0","0","1","0"]]
             * Output: 6 
             * 
             * Explanation: The maximal rectangle is shown in the above picture.
             * 
             * Example 2:
             * 
             * Input: matrix = [] Output: 0 Example 3:
             * 
             * Input: matrix = [["0"]] Output: 0 Example 4:
             * 
             * Input: matrix = [["1"]] Output: 1 Example 5:
             * 
             * Input: matrix = [["0","0"]] Output: 0
             * 
             */
            /**
             * We can compute the maximum width of a rectangle that ends at a given
             * coordinate in constant time. We do this by keeping track of the number of
             * consecutive ones each square in each row. As we iterate over each row we
             * update the maximum possible width at that point. This is done using row[i] =
             * row[i - 1] + 1 if row[i] == '1' .
             * 
             * !?!../Documents/85_maximal_rectangle_anim1.json:2000,500!?!
             * 
             * Once we know the maximum widths for each point above a given point, we can
             * compute the maximum rectangle with the lower right corner at that point in
             * linear time. As we iterate up the column, we know that the maximal width of a
             * rectangle spanning from the original point to the current point is the
             * running minimum of each maximal width we have encountered.
             * 
             * We define:
             * 
             * maxWidth=min(maxWidth,widthHere)
             * 
             * curArea=maxWidth∗(currentRow−originalRow+1)
             * 
             * maxArea=max(maxArea,curArea)
             * 
             * The following animation makes this more clear. Given the maximal width of all
             * points above it, let's calculate the maximum area of any rectangle at the
             * bottom yellow square:
             * 
             * !?!../Documents/85_maximal_rectangle_anim3.json:1400,1125!?!
             * 
             * Repeating this process for every point in our input gives us the global
             * maximum.
             * 
             * Note that our method of precomputing our maximum width essentially breaks
             * down our input into a set of histograms, with each column being a new
             * histogram. We are computing the maximal area for each histogram.
             */
            if (matrix.length == 0) return 0;
            int maxarea = 0;
            int n = matrix[0].length;
            int[][] dp = new int[matrix.length][matrix[0].length];
            //Time complexity: O(NM) for the outermost loop. O(N) to find the max area the inner loop.
            for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix[0].length; j++){
                    if (matrix[i][j] == '1'){
    
                        // compute the maximum width and update dp with it
                        dp[i][j] = j == 0? 1 : dp[i][j-1] + 1;
    
                        int width = dp[i][j];
    
                        // compute the maximum area rectangle with a lower right corner at [i, j]
                        for(int k = i; k >= 0; k--){
                            width = Math.min(width, dp[k][j]);
                            maxarea = Math.max(maxarea, width * (i - k + 1));
                        }
                    }
                }
                System.out.println(dp[i][n-1]); 
                // The last column of the dp matrix contains the number of consecutive 1s without 0s:
                // For given input, it will print: 0, 3, 5, 0
                // At i=2 row, dp[i][lastCol] is 5. 
                // To find the min width, traverse upward in the column. 
                // And to find max area, multiply the min width with difference in size between rows (i-k+1)
            } return maxarea;
        }
       
        // Get the maximum area in a histogram given its heights
        public static int leetcode84(int[] heights) {
            Stack<Integer> stack = new Stack<>();
            stack.push(-1);
            int maxarea = 0;
            for (int i = 0; i < heights.length; ++i) {
                while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) 
                // when the next smaller element is encountered, get its index and multiply the difference in index with 
                    maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
                stack.push(i);
            }
            while (stack.peek() != -1)
                maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
            return maxarea;
        }

        public static int maximalRectangleUsingStack(char[][] matrix) {

            if (matrix.length == 0)
                return 0;
            int maxarea = 0;
            int[] dp = new int[matrix[0].length];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {

                    // update the state of this row's histogram using the last row's histogram
                    // by keeping track of the number of consecutive ones

                    dp[j] = matrix[i][j] == '1' ? dp[j] + 1 : 0;
                }
                // update maxarea with the maximum area from this row's histogram
                maxarea = Math.max(maxarea, leetcode84(dp));
            }
            return maxarea;
        }
        public static void main(String[] args) {
            char[][] matrix = new char[][] { { '1', '0', '1', '0', '0' }, { '1', '0', '1', '1', '1' },
                    { '1', '1', '1', '1', '1' }, { '1', '0', '0', '1', '0' } };
            // System.out.println("Maximal rectange size is " + maximalRectangleUsingDP(matrix));
            System.out.println("Maximal rectange size is " + maximalRectangleUsingStack(matrix));
        }
    }

    static class RangeSumQuery2DMatrix_FindSumOfElementsInRegionInMatrix_usingRowWiseCumulativeSum {
        /**
         * Given a 2D matrix matrix, handle multiple queries of the following type:
         * 
         * Calculate the sum of the elements of matrix inside the rectangle defined by
         * its upper left corner (row1, col1) and lower right corner (row2, col2).
         * Implement the NumMatrix class:
         * 
         * NumMatrix(int[][] matrix) Initializes the object with the integer matrix
         * matrix. int sumRegion(int row1, int col1, int row2, int col2) Returns the sum
         * of the elements of matrix inside the rectangle defined by its upper left
         * corner (row1, col1) and lower right corner (row2, col2).
         * 
         * 
         * Example 1:
         * 
         * 
         * Input ["NumMatrix", "sumRegion", "sumRegion", "sumRegion"] [[[[3, 0, 1, 4,
         * 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2,
         * 1, 4, 3], [1, 1, 2, 2], [1, 2, 2, 4]] Output [null, 8, 11, 12]
         * 
         * Explanation NumMatrix numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3,
         * 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]);
         * numMatrix.sumRegion(2, 1, 4, 3); // return 8 (i.e sum of the red rectangle)
         * numMatrix.sumRegion(1, 1, 2, 2); // return 11 (i.e sum of the green
         * rectangle) numMatrix.sumRegion(1, 2, 2, 4); // return 12 (i.e sum of the blue
         * rectangle)
         * Constraints:
         * 
         * m == matrix.length n == matrix[i].length 1 <= m, n <= 200 -105 <=
         * matrix[i][j] <= 105 0 <= row1 <= row2 < m 0 <= col1 <= col2 < n At most 104
         * calls will be made to sumRegion.
         */


         /**
          * 
          * Remember from the 1D version where we used a cumulative sum array? Could we
          * apply that directly to solve this 2D version?
          * 
          * Try to see the 2D matrix as mm rows of 1D arrays. To find the region sum, we
          * just accumulate the sum in the region row by row.
          */
          /**
          * Complexity analysis
          * 
          * Time complexity : O(m)O(m) time per query, O(mn)O(mn) time pre-computation.
          * The pre-computation in the constructor takes O(mn)O(mn) time. The sumRegion
          * query takes O(m)O(m) time.
          * 
          * Space complexity : O(mn)O(mn). The algorithm uses O(mn)O(mn) space to store
          * the cumulative sum of all rows.
          */
        private int[][] dp;

        public void numMatrix(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0) return;
            dp = new int[matrix.length][matrix[0].length + 1];
            for (int r = 0; r < matrix.length; r++) {
                for (int c = 0; c < matrix[0].length; c++) {
                    dp[r][c + 1] = dp[r][c] + matrix[r][c];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            int sum = 0;
            for (int row = row1; row <= row2; row++) {
                sum += dp[row][col2 + 1] - dp[row][col1];
            }
            return sum;
        }
        /**
         * We used a cumulative sum array in the 1D version. We notice that the
         * cumulative sum is computed with respect to the origin at index 0. Extending
         * this analogy to the 2D case, we could pre-compute a cumulative region sum
         * with respect to the origin at (0, 0)(0,0).
         * 
         * Sum OD Sum(OD) is the cumulative region sum with respect to the origin at (0,
         * 0). O represents origin.
         * 
         * How do we derive Sum(ABCD)Sum(ABCD) using the pre-computed cumulative region
         * sum?
         * 
         * Sum OB Sum(OB) is the cumulative region sum on top of the rectangle.
         * 
         * Sum OC Sum(OC) is the cumulative region sum to the left of the rectangle.
         * 
         * Sum OA Sum(OA) is the cumulative region sum to the top left corner of the
         * rectangle.
         * 
         * Note that the region Sum(OA)Sum(OA) is covered twice by both Sum(OB)Sum(OB)
         * and Sum(OC)Sum(OC). We could use the principle of inclusion-exclusion to
         * calculate Sum(ABCD)Sum(ABCD) as following:
         * 
         * Sum(ABCD) = Sum(OD) - Sum(OB) - Sum(OC) +
         * Sum(OA)Sum(ABCD)=Sum(OD)−Sum(OB)−Sum(OC)+Sum(OA)
         */
        private int[][] dpS;

        /**
         * Complexity analysis
         * 
         * Time complexity : O(1)O(1) time per query, O(mn)O(mn) time pre-computation.
         * The pre-computation in the constructor takes O(mn)O(mn) time. Each sumRegion
         * query takes O(1)O(1) time.
         * 
         * Space complexity : O(mn)O(mn). The algorithm uses O(mn)O(mn) space to store
         * the cumulative region sum.
         */
        public void NumMatrixSmarter(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0)
                return;
            dpS = new int[matrix.length + 1][matrix[0].length + 1];// Note +1 in both rows and cols.
            // This means dpS[1,1] indicates the first element
            // Stores the sum of elements which ends at current index from the origin.
            
            for (int r = 0; r < matrix.length; r++) {
                for (int c = 0; c < matrix[0].length; c++) {
                    dpS[r + 1][c + 1] = dpS[r + 1][c] + dpS[r][c + 1] + matrix[r][c] - dpS[r][c];
                    // row =0, col=0
                    // dpS[1,1] = dpS[1][0] + dpS[0][1] + matrix[0][0] - dpS[0][0];
                    // dpS[1,1] = 0 + 0 + matrix[0][0] - 0;
                    // calculate bottom right.
                    /**
                     * 
                    * If we want to maintain sums in dp array like this, ??? -> should have sum of all 4 elements, clearly 
                    cell4 = cell4 + (cell2 + cell3 - cell1) 
                        [
                            [   (0,0)          (0,0) + (0,1)   ],
                            [   (0,0)+(1,0)          ???         ]
                        ]
                     */
                }
            }
        }

        public int sumRegionSmarter(int row1, int col1, int row2, int col2) {
            // SUM(OD) - SUM(OB) - SUM(OC) + SUM(OA)
            // SUM(OB) is the top of the rectange from origin.
            // SUM(OC) is the left of the rectangle
            // SUM(OA) is the top left of the rectangle.
            return dpS[row2 + 1][col2 + 1] - dpS[row1][col2 + 1] - dpS[row2 + 1][col1] + dpS[row1][col1];
        }
    }

    static class RangeSumQueryMutable_1DArray_usingBlockDecomposition {
        /**
         * Given an integer array nums, handle multiple queries of the following types:
         * 
         * Update the value of an element in nums. Calculate the sum of the elements of
         * nums between indices left and right inclusive where left <= right. Implement
         * the NumArray class:
         * 
         * NumArray(int[] nums) Initializes the object with the integer array nums. void
         * update(int index, int val) Updates the value of nums[index] to be val. int
         * sumRange(int left, int right) Returns the sum of the elements of nums between
         * indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... +
         * nums[right]).
         * 
         * 
         * Example 1:
         * 
         * Input ["NumArray", "sumRange", "update", "sumRange"] [[[1, 3, 5]], [0, 2],
         * [1, 2], [0, 2]] Output [null, 9, null, 8]
         * 
         * Explanation NumArray numArray = new NumArray([1, 3, 5]); numArray.sumRange(0,
         * 2); // return 1 + 3 + 5 = 9 numArray.update(1, 2); // nums = [1, 2, 5]
         * numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
         * 
         * 
         * Constraints:
         * 
         * 1 <= nums.length <= 3 * 104 -100 <= nums[i] <= 100 0 <= index < nums.length
         * -100 <= val <= 100 0 <= left <= right < nums.length At most 3 * 104 calls
         * will be made to update and sumRange.
         */
        /**
         * 
         * Intuition
         * 
         * The idea is to split the array in blocks with length of \sqrt{n} n ​ . Then
         * we calculate the sum of each block and store it in auxiliary memory b. To
         * query RSQ(i, j), we will add the sums of all the blocks lying inside and
         * those that partially overlap with range [i \ldots j][i…j].
         * 
         * Algorithm
         * 
         * Range sum query using SQRT decomposition
         * 
         * Figure 1. Range sum query using SQRT decomposition.
         * 
         * In the example above, the array nums's length is 9, which is split into
         * blocks of size \sqrt{9} 9 ​ . To get RSQ(1, 7) we add b[1]. It stores the sum
         * of range [3, 5] and partially sums from block 0 and block 2, which are
         * overlapping boundary blocks.
         */

         /**
          * Time complexity : O(n) - preprocessing, O(\sqrt{n}) - range sum
          * query, O(1) - update query
          * 
          * For range sum query in the worst-case scenario we have to sum approximately 3
          * \sqrt{n} ​ elements. In this case the range includes \sqrt{n} - 2 
          * blocks, which total sum costs \sqrt{n} - 2 operations. In addition to
          * this we have to add the sum of the two boundary blocks. This takes another 2
          * (\sqrt{n} - 1)operations. The total amount of operations is around
          * 3 \sqrt{n} .
          * 
          * Space complexity : O(\sqrt{n}).
          * 
          * We need additional \sqrt{n}​ memory to store all block sums.
          */
        private int[] b;
        private int len;
        private int[] nums;

        public void NumArray(int[] nums) {
            this.nums = nums;
            double l = Math.sqrt(nums.length);
            len = (int) Math.ceil(nums.length / l);
            b = new int[len];
            for (int i = 0; i < nums.length; i++)
                b[i / len] += nums[i];
        }

        public int sumRange(int i, int j) {
            int sum = 0;
            int startBlock = i / len;
            int endBlock = j / len;
            if (startBlock == endBlock) {
                for (int k = i; k <= j; k++)
                    sum += nums[k];
            } else {
                for (int k = i; k <= (startBlock + 1) * len - 1; k++)
                    sum += nums[k];
                for (int k = startBlock + 1; k <= endBlock - 1; k++)
                    sum += b[k];
                for (int k = endBlock * len; k <= j; k++)
                    sum += nums[k];
            }
            return sum;
        }

        public void update(int i, int val) {
            int b_l = i / len;
            b[b_l] = b[b_l] - nums[i] + val;
            nums[i] = val;
        }
    }

    static class SegmentTree_querySumOfGivenRange {
        /**
         * Time complexity : O(n) for build tree.
         * 
         * Time complexity : O(logn) for update and query operation, space complexity: O(1)
         */
        int MAX = 500020; // 4*N.
        int tree[] = new int[MAX];
        int[] A;

        void Build_Tree(int node, int st, int end) {
            if (st == end) {
                tree[node] = A[st];
                return;
            }
            int mid = (st + end) / 2;
            Build_Tree(node * 2, st, mid);

            Build_Tree(node * 2 + 1, mid + 1, end);

            tree[node] = tree[2 * node] + tree[2 * node + 1];
            return;
        }

        void update(int node, int st, int end, int idx, int value) {
            if (st == end) {
                tree[node] = value;
                A[idx] = value;
                return;
            }

            int mid = (st + end) / 2;
            if (idx <= mid) {
                update(2 * node, st, mid, idx, value);
            } else {
                update(2 * node + 1, mid + 1, end, idx, value);
            }

            tree[node] = tree[2 * node] + tree[2 * node + 1];
            return;
        }

        int RangeSum(int node, int st, int end, int l, int r) {

             /**
              *     
              *      0,3 Parent

                 0,1      2,3 Internal 
                
               0    1    2    3   Leaf 

               For 0, 3, query, prev
               Traverse left, to find the leaft node 1
               Traverse right, the internal node, 2,3 will be returned.
             */
                
            if (l <= st && end <= r) {
                return tree[node];
            }

            if (st > r || end < l)
                return 0;

            int mid = (st + end) / 2;

            int ans1 = RangeSum(2 * node, st, mid, l, r);
            // traverse left to find the internal node or the leaf.
            int ans2 = RangeSum(2 * node + 1, mid + 1, end, l, r);
            return ans1 + ans2;
        }

        private void main() {
            this.A = new int[] { 1, 3, 5 };
            int n = this.A.length;
            for (int i = 0; i < MAX; i++)
                tree[i] = 0;

            Build_Tree(1, 0, n-1);

            System.out.println(RangeSum(1, 0, n-1, 0, 2)); // [0, 2] query, answer is 9
            update(1, 0, n-1, 1, 2); // index 1, value 2.
            System.out.println(RangeSum(1, 0, n-1, 0, 2));// answer is 8
        }

        public static void main(String[] args) {
            SegmentTree_querySumOfGivenRange obj = new SegmentTree_querySumOfGivenRange();
            obj.main();
        }
    }

    static class CountSmallerElementsUsingBST_and_MergeSort {
        // UseBST: Time complexity: O(NlogN)
        // Merge sort approach: O(N)
        public List<Integer> countSmaller(int[] nums) {
            int[] counts = new int[nums.length];
            Node root = new Node(Integer.MAX_VALUE);
            for (int i = nums.length-1;i>=0;i--) {
                // Start from the right most last element, as there can't be next smaller element.
                counts[i] = insert(root, nums[i]);
            }
            List<Integer> res = new ArrayList<>();
            for (int count: counts) {
                res.add(count);
            }
            return res;
        }
        
        private int insert(Node root, int val) {
    
            if (val > root.val) {
                root.rightCount++; // increment the right count as we're adding the right child.
                int count = root.selfCount + root.leftCount;//Number of smaller element than the current node is self + all elements in left child.
                if (root.right == null) {
                    root.right = new Node(val);
                    return count;
                } else {
                    return count + insert(root.right, val); //The right subtree may also have smaller elements, add them as well.
                }
            } else if (val < root.val) {//The root element is greater, so we shouldn't add it to the result.
                root.leftCount++;
                if (root.left == null) {
                    root.left = new Node(val);
                    return 0;
                } else {
                    return insert(root.left, val); // The left subtree can have smaller elements than the current element.
                }
            } else {
                // when the element is equal, return only the left count.
                root.selfCount++;
                return root.leftCount;
            }
        }
        
        private static class Node {
            Node left;
            Node right;
            int leftCount;
            int rightCount;
            int selfCount;
            int val;
            Node(int val) {
                this.val = val;
                this.selfCount = 1;
            }
        }

        public static void main(String[] args) {
            CountSmallerElementsUsingBST_and_MergeSort obj = new CountSmallerElementsUsingBST_and_MergeSort();
            System.out.println(obj.countSmaller(new int[]{5, 2, 6, 1}));
        }
    
        class Pair {
            int index;
            int val;
            public Pair(int index, int val) {
                this.index = index;
                this.val = val;
            }
        }
        public List<Integer> countSmallerMergeSort(int[] nums) {
            /**
             * Use the idea of merge sort. Key algorithm:
                ex:
                index: 0, 1
                left: 2, 5
                right: 1, 6
                Each time we choose a left to the merged array. We want to know how many numbers from array right are moved before this number.
                For example we take 1 from right array and merge sort it first. Then it’s 2 from left array. We find that there are j numbers moved before this left[i], in this case j == 1.
                So the array smaller[original index of 2] += j.
                Then we take 5 from array left. We also know that j numbers moved before this 5.
                smaller[original index of 6] += j.
                ex:
                index: 0, 1, 2
                left: 4, 5, 6
                right: 1, 2, 3
                when we take 4 for merge sort. We add j (j == 3) because we already take j numbers before take this 4.

                During the merge sort, we have to know number and it’s original index. We use a class called Pair to encapsulate them together.
                We need to pass the array smaller to merge sort method call because it might be changed during any level of merge sort. And the final smaller number is add up of all the numbers moved before this value.
             */
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) {
                return res;
            }
            Pair[] arr = new Pair[nums.length];
            Integer[] smaller = new Integer[nums.length];
            Arrays.fill(smaller, 0);
            for (int i = 0; i < nums.length; i++) {
                arr[i] = new Pair(i, nums[i]);
            }
            mergeSort(arr, smaller);
            res.addAll(Arrays.asList(smaller));
            return res;
        }
        private Pair[] mergeSort(Pair[] arr, Integer[] smaller) {
            if (arr.length <= 1) {
                return arr;
            }
            int mid = arr.length / 2;
            Pair[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid), smaller);
            Pair[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length), smaller);
            for (int i = 0, j = 0; i < left.length || j < right.length;) {
                if (j == right.length || i < left.length && left[i].val <= right[j].val) {
                    // If j is exhausted and the left index is smaller than right, add element to the sorted array.
                    arr[i + j] = left[i];
                    smaller[left[i].index] += j;
                    // Each time we choose a left to the merged array. We want to know how many numbers from array right are moved before this number.
                    i++;
                } else {
                    arr[i + j] = right[j];
                    j++;
                }
            }
            return arr;
        }
    }
    
    static class LongestIncreasingPathInMatrixUsingDFS {
        /**
         * Given an m x n integers matrix, return the length of the longest increasing
         * path in matrix.
         * 
         * From each cell, you can either move in four directions: left, right, up, or
         * down. You may not move diagonally or move outside the boundary (i.e.,
         * wrap-around is not allowed).
         * 
         * Example 1:
         * 
         * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]] Output: 4 Explanation: The longest
         * increasing path is [1, 2, 6, 9]. Example 2:
         * 
         * 
         * Input: matrix = [[3,4,5],[3,2,6],[2,2,1]] Output: 4 Explanation: The longest
         * increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed. Example 3:
         * 
         * Input: matrix = [[1]] Output: 1
         */

         /**
          * To get max length of increasing sequences:

            Do DFS from every cell
            Compare every 4 direction and skip cells that are out of boundary or smaller
            Get matrix max from every cell's max
            Use matrix[x][y] <= matrix[i][j] so we don't need a visited[m][n] array
            The key is to cache the distance because it's highly possible to revisit a cell
          */
        public static final int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

        public int longestIncreasingPath(int[][] matrix) {
            if (matrix.length == 0)
                return 0;
            int m = matrix.length, n = matrix[0].length;
            int[][] cache = new int[m][n];
            int max = 1;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int len = dfs(matrix, i, j, m, n, cache);
                    max = Math.max(max, len);
                }
            }
            return max;
        }

        public int dfs(int[][] matrix, int i, int j, int m, int n, int[][] cache) {
            if (cache[i][j] != 0)
                return cache[i][j];
            int max = 1;
            for (int[] dir : dirs) {
                int x = i + dir[0], y = j + dir[1];
                if (x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j])
                    continue;
                int len = 1 + dfs(matrix, x, y, m, n, cache);
                max = Math.max(max, len);
            }
            cache[i][j] = max;
            return max;
        }
    }

    static class LFUCache {
        /**
         * Design and implement a data structure for a Least Frequently Used (LFU)
         * cache.
         * 
         * Implement the LFUCache class:
         * 
         * LFUCache(int capacity) Initializes the object with the capacity of the data
         * structure. int get(int key) Gets the value of the key if the key exists in
         * the cache. Otherwise, returns -1. void put(int key, int value) Update the
         * value of the key if present, or inserts the key if not already present. When
         * the cache reaches its capacity, it should invalidate and remove the least
         * frequently used key before inserting a new item. For this problem, when there
         * is a tie (i.e., two or more keys with the same frequency), the least recently
         * used key would be invalidated. To determine the least frequently used key, a
         * use counter is maintained for each key in the cache. The key with the
         * smallest use counter is the least frequently used key.
         * 
         * When a key is first inserted into the cache, its use counter is set to 1 (due
         * to the put operation). The use counter for a key in the cache is incremented
         * either a get or put operation is called on it.
         * 
         * The functions get and put must each run in O(1) average time complexity.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get",
         * "get", "get"] [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3],
         * [4]] Output [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
         * 
         * Explanation // cnt(x) = the use counter for key x // cache=[] will show the
         * last used order for tiebreakers (leftmost element is most recent) LFUCache
         * lfu = new LFUCache(2); lfu.put(1, 1); // cache=[1,_], cnt(1)=1 lfu.put(2, 2);
         * // cache=[2,1], cnt(2)=1, cnt(1)=1 lfu.get(1); // return 1 // cache=[1,2],
         * cnt(2)=1, cnt(1)=2 lfu.put(3, 3); // 2 is the LFU key because cnt(2)=1 is the
         * smallest, invalidate 2. // cache=[3,1], cnt(3)=1, cnt(1)=2 lfu.get(2); //
         * return -1 (not found) lfu.get(3); // return 3 // cache=[3,1], cnt(3)=2,
         * cnt(1)=2 lfu.put(4, 4); // Both 1 and 3 have the same cnt, but 1 is LRU,
         * invalidate 1. // cache=[4,3], cnt(4)=1, cnt(3)=2 lfu.get(1); // return -1
         * (not found) lfu.get(3); // return 3 // cache=[3,4], cnt(4)=1, cnt(3)=3
         * lfu.get(4); // return 4 // cache=[3,4], cnt(4)=2, cnt(3)=3
         */
        final int capacity;
        int curSize;
        int minFrequency;
        Map<Integer, DLLNode> cache;
        Map<Integer, DoubleLinkedList> frequencyMap;

        /* . */
        /*
         * @param capacity: total capacity of LFU Cache
         * 
         * @param curSize: current size of LFU cache
         * 
         * @param minFrequency: frequency of the last linked list (the minimum frequency
         * of entire LFU cache)
         * 
         * @param cache: a hash map that has key to Node mapping, which used for storing
         * all nodes by their keys
         * 
         * @param frequencyMap: a hash map that has key to linked list mapping, which
         * used for storing all double linked list by their frequencies
         */
        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.curSize = 0;
            this.minFrequency = 0;

            this.cache = new HashMap<>();
            this.frequencyMap = new HashMap<>();
        }

        /**
         * get node value by key, and then update node frequency as well as relocate
         * that node
         **/
        public int get(int key) {
            DLLNode curNode = cache.get(key);
            if (curNode == null) {
                return -1;
            }
            updateNode(curNode);
            return curNode.val;
        }

        /**
         * add new node into LFU cache, as well as double linked list condition 1: if
         * LFU cache has input key, update node value and node position in list
         * condition 2: if LFU cache does NOT have input key - sub condition 1: if LFU
         * cache does NOT have enough space, remove the Least Recent Used node in
         * minimum frequency list, then add new node - sub condition 2: if LFU cache has
         * enough space, add new node directly
         **/
        public void put(int key, int value) {
            // corner case: check cache capacity initialization
            if (capacity == 0) {
                return;
            }

            if (cache.containsKey(key)) {
                DLLNode curNode = cache.get(key);
                curNode.val = value;
                updateNode(curNode);
            } else {
                curSize++;
                if (curSize > capacity) {
                    // get minimum frequency list
                    DoubleLinkedList minFreqList = frequencyMap.get(minFrequency);
                    DLLNode deleteNode = minFreqList.removeTail();
                    cache.remove(deleteNode.key);
                    curSize--;
                }
                // reset min frequency to 1 because of adding new node
                minFrequency = 1;
                DLLNode newNode = new DLLNode(key, value);

                // get the list with frequency 1, and then add new node into the list, as well
                // as into LFU cache
                DoubleLinkedList curList = frequencyMap.getOrDefault(1, new DoubleLinkedList());
                curList.addNode(newNode);
                frequencyMap.put(1, curList);
                cache.put(key, newNode);
            }
        }

        public void updateNode(DLLNode curNode) {
            int curFreq = curNode.frequency;
            DoubleLinkedList curList = frequencyMap.get(curFreq);
            curList.removeNode(curNode);

            // if current list the the last list which has lowest frequency and current node
            // is the only node in that list
            // we need to remove the entire list and then increase min frequency value by 1
            if (curFreq == minFrequency && curList.listSize == 0) {
                minFrequency++;
                // Since node.cnt is increasing by one, if the node was the last element in the min frequency's list in countMap, minFrequency is also increased by one, so that it can be removed next.
            }

            curNode.frequency++;
            // add current node to another list has current frequency + 1,
            // if we do not have the list with this frequency, initialize it
            DoubleLinkedList newList = frequencyMap.getOrDefault(curNode.frequency, new DoubleLinkedList());
            newList.addNode(curNode);
            frequencyMap.put(curNode.frequency, newList);
        }

        /*
         * @param key: node key
         * 
         * @param val: node value
         * 
         * @param frequency: frequency count of current node (all nodes connected in
         * same double linked list has same frequency)
         * 
         * @param prev: previous pointer of current node
         * 
         * @param next: next pointer of current node
         */
        class DLLNode {
            int key;
            int val;
            int frequency;
            DLLNode prev;
            DLLNode next;

            public DLLNode(int key, int val) {
                this.key = key;
                this.val = val;
                this.frequency = 1;
            }
        }

        /*
         * @param listSize: current size of double linked list
         * 
         * @param head: head node of double linked list
         * 
         * @param tail: tail node of double linked list
         */
        class DoubleLinkedList {
            int listSize;
            DLLNode head;
            DLLNode tail;

            public DoubleLinkedList() {
                this.listSize = 0;
                this.head = new DLLNode(0, 0);
                this.tail = new DLLNode(0, 0);
                head.next = tail;
                tail.prev = head;
            }

            /** add new node into head of list and increase list size by 1 **/
            public void addNode(DLLNode curNode) {
                DLLNode nextNode = head.next;
                curNode.next = nextNode;
                curNode.prev = head;
                head.next = curNode;
                nextNode.prev = curNode;
                listSize++;
            }

            /** remove input node and decrease list size by 1 **/
            public void removeNode(DLLNode curNode) {
                DLLNode prevNode = curNode.prev;
                DLLNode nextNode = curNode.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                listSize--;
            }

            /** remove tail node **/
            public DLLNode removeTail() {
                // DO NOT FORGET to check list size
                if (listSize > 0) {
                    DLLNode tailNode = tail.prev;
                    removeNode(tailNode);
                    return tailNode;
                }
                return null;
            }
        }
    }

    static class RobotRoomCleaner_BackTrack {
        interface Robot {
                // Returns true if the cell in front is open and robot moves into the cell.
                 // Returns false if the cell in front is blocked and robot stays in the current cell.
                 public boolean move();
            
                 // Robot will stay in the same cell after calling turnLeft/turnRight.
                 // Each turn will be 90 degrees.
                 public void turnLeft();
                 public void turnRight();
            
                 // Clean the current cell.
                 public void clean();
         }
            
        public void cleanRoom(Robot robot) {
            // A number can be added to each visited cell
            // use string to identify the class
            Set<String> set = new HashSet<>();
            int dir = 0; // 0: up, 90: right, 180: down, 270: left
            backtrack(robot, set, 0, 0, dir);
        }

        public void backtrack(Robot robot, Set<String> set, int i, int j, int dir) {
            String tmp = i + "->" + j;
            if (set.contains(tmp)) {
                return;
            }

            robot.clean();
            set.add(tmp);

            for (int n = 0; n < 4; n++) {
                // the robot can to four directions, we use right turn
                if (robot.move()) {
                    // can go directly. Find the (x, y) for the next cell based on current direction
                    int x = i, y = j;
                    switch (dir) {
                        case 0:
                            // go up, reduce i
                            x = i - 1;
                            break;
                        case 90:
                            // go right
                            y = j + 1;
                            break;
                        case 180:
                            // go down
                            x = i + 1;
                            break;
                        case 270:
                            // go left
                            y = j - 1;
                            break;
                        default:
                            break;
                    }

                    backtrack(robot, set, x, y, dir);
                    // go back to the starting position
                    // Imagine the robot is in the up direction in a cell,
                    // To get back to the starting cell, robot has to turn down (180 degree), for which turnLeft is called.
                    // Move to the starting cell, and get back to the original direction. Make 180 degree again
                    robot.turnLeft();
                    robot.turnLeft();
                    robot.move();
                    robot.turnRight();
                    robot.turnRight();

                }
                // If the direction is done, turn to next direction, which is 
                // if up, turn right, or when right, down, or when down, left. So, turn right to get to the next cell.
                // turn to next direction
                robot.turnRight();
                dir += 90; //Change the direction sign.
                dir %= 360;
            }

        }
    }

    static class RandomPickWithWeight {
        /**
         * 
         * 
         * You are given an array of positive integers w where w[i] describes the weight
         * of ith index (0-indexed).
         * 
         * We need to call the function pickIndex() which randomly returns an integer in
         * the range [0, w.length - 1]. pickIndex() should return the integer
         * proportional to its weight in the w array. For example, for w = [1, 3], the
         * probability of picking the index 0 is 1 / (1 + 3) = 0.25 (i.e 25%) while the
         * probability of picking the index 1 is 3 / (1 + 3) = 0.75 (i.e 75%).
         * 
         * More formally, the probability of picking index i is w[i] / sum(w).
         * 
         * 
         * 
         * Example 1:
         * 
         * Input ["Solution","pickIndex"] [[[1]],[]] Output [null,0]
         * 
         * Explanation Solution solution = new Solution([1]); solution.pickIndex(); //
         * return 0. Since there is only one single element on the array the only option
         * is to return the first element. Example 2:
         * 
         * Input
         * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
         * [[[1,3]],[],[],[],[],[]] Output [null,1,1,1,1,0]
         * 
         * Explanation Solution solution = new Solution([1, 3]); solution.pickIndex();
         * // return 1. It's returning the second element (index = 1) that has
         * probability of 3/4. solution.pickIndex(); // return 1 solution.pickIndex();
         * // return 1 solution.pickIndex(); // return 1 solution.pickIndex(); // return
         * 0. It's returning the first element (index = 0) that has probability of 1/4.
         * 
         * Since this is a randomization problem, multiple answers are allowed so the
         * following outputs can be considered correct : [null,1,1,1,1,0]
         * [null,1,1,1,1,1] [null,1,1,1,0,0] [null,1,1,1,0,1] [null,1,0,1,0,0] ......
         * and so on.
         */

        private double[] probabilities;

        public RandomPickWithWeight(int[] w) {
            /**
             * The problem is, we need to randomly pick an index proportional to its weight.
             * What this means? We have weights array, each weights[i] represents weight of
             * index i. The more the weight is, then high chances of getting that index
             * randomly.
             * 
             * suppose weights = [1, 3] then 3 is larger, so there are high chances to get
             * index 1.
             * 
             * We can know the chances of selecting each index by knowing their probability.
             * 
             * P(i) = weight[i]/totalWeight
             * 
             * totalWeight = 1 + 3 = 4 So, for index 0, P(0) = 1/4 = 0.25 = 25% for index 1,
             * P(1) = 3/4 = 0.75 = 75%
             * 
             * So, there are 25% of chances to pick index 0 and 75% chances to pick index 1.
             * 
             * I have provided java code for this problem in the comment section. If you are
             * interested, you can check that. Happy coding.
             * 
             */
            /**
             * Solution: 
             * Given array is 1 3 5 2 4, 
             * Which means that
             * the indexes for the weights are
             * 1: 0
             * 3: 1 1 1
             * 5: 2 2 2 2 2
             * 2: 3 3
             * 4: 4 4 4 4
             * 
             * If we asked a blindfolded person to randomly pick a chit of paper from this jar, 
             * then what is the probability that they would pick “2”? That would be 5/15. 
             * What is the probability that they would pick “0”? That’s 1/15. Hmm, I think we’re onto something.

             Now let’s turn this jar into an array (I’ll call this the jar_array) which looks like : 
             [0, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4]

             If we had a uniform random number generator which generates a number between 0-14, we'd effectively
             end up picking an index proportional to its weight.

             So, if the random number generator chose 6 we would return ‘2’ for our example.
              This ‘2’ was chosen with a probability of 5/15 or 1/3.

            However, as you might have already figured out that this solution consumes a lot of extra space 
            (in fact the space complexity is proportional to sum of weights in the original array)
             * 
             * 
             *  Imagine if we turned our original array [1, 3, 5, 2, 4] into a sum-of-weights array 
             * like so [1, 4, 9, 11, 15].  We’re simply accumulating the sums by traversing the array from left to right.
             * 
             * Now, if we modified our algorithm slightly to generate uniform random numbers between [1–15] do you notice something interesting? We can find out the index to which our random number belongs in linear-time by scanning the sum-of-weights array from left to right. Here’s a breakdown of the relationship between a random number and the index/container to which it belongs for our example:
                Index 0: Holds random numbers from 1–1 (Total 1 element)
                Index 1: Holds random numbers from 2–4 (Total 3 elements)
                Index 2: Holds random numbers from 5–9 (Total 5 elements)
                Index 3: Holds random numbers from 10–11 (Total 2 elements)
                Index 4: Holds random numbers from 12–15 (Total 4 elements)

                Are we done? Not quite. Do you notice a special property of our sum-of-weights array? 
                Of course, it’s sorted! This implies that we can use binary search to find the index to which our random number belongs. 
                This binary search implementation will be a slight modification to our original binary search algorithm 
                because we’re actually looking for a container to which our search key (i.e. the random number) 
                belongs and not the exact search key itself. 
             */
            double sum = 0;
            this.probabilities = new double[w.length];
            for(int weight : w)
                sum += weight;
            for (int i = 0; i < w.length; i++) {
                w[i] += (i == 0) ? 0 : w[i - 1];
                probabilities[i] = w[i] / sum;
                System.out.println("I->" + i + " w[i]-> " + w[i] + " probability -> " + w[i] / sum);
            }
            // System.out.println(Arrays.asList(probabilities));
        }

        public int pickIndex() {
            return Math.abs(Arrays.binarySearch(this.probabilities, Math.random())) - 1;
        }

        public static void main(String[] args) {
            RandomPickWithWeight obj = new RandomPickWithWeight(new int[]{1, 3, 5, 2, 4});
            System.out.println(obj.pickIndex());
            System.out.println(obj.pickIndex());
            System.out.println(obj.pickIndex());
            System.out.println(obj.pickIndex());
        }

    }

    static class StudentAttendanceRecord_DP {
        /**
         * An attendance record for a student can be represented as a string where each
         * character signifies whether the student was absent, late, or present on that
         * day. The record only contains the following three characters:
         * 
         * 'A': Absent. 'L': Late. 'P': Present. Any student is eligible for an
         * attendance award if they meet both of the following criteria:
         * 
         * The student was absent ('A') for strictly fewer than 2 days total. The
         * student was never late ('L') for 3 or more consecutive days. Given an integer
         * n, return the number of possible attendance records of length n that make a
         * student eligible for an attendance award. The answer may be very large, so
         * return it modulo 109 + 7.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: n = 2 Output: 8 Explanation: There are 8 records with length 2 that
         * are eligible for an award: "PP", "AP", "PA", "LP", "PL", "AL", "LA", "LL"
         * Only "AA" is not eligible because there are 2 absences (there need to be
         * fewer than 2). Example 2:
         * 
         * Input: n = 1 Output: 3 Example 3:
         * 
         * Input: n = 10101 Output: 183236316
         */
        static final int mod = (int) (1e9 + 7);

        public int checkRecord(int n) {
            /**
             * Solution: 
             * This is similar to fibonacci problem. Think of it like this:
             * 
             * At length 1, you have only strings 'P' and 'L'.
             * Don't consider A for now.
             * 
             * At length 2, to get a string ending with P, 
             * you can append to strings which end with L or P at n-1. (Think of recursion.)
             * 
             * At length 2, to get a string ending with L, 
             * you can append to strings which end with P at n-1, or with P at n-2 (in the latter case, you can append 2 Ls i.e. PLL)
             * The total of strings therefore at say length 3 which ends with L include, 
             * PLL (n-2), LPL (n-1), PPL (n-1).
             * 
             * Get the sum of P[n] and L[n] in res.
             * Now we need to insert a character A, for which you will need to find the number of ways 
             * that you can insert a A at every position in string of length n, 
             *
             * To do this, you just have to multiply the (P+L) count at i, and (P(n-i-1)+L(n-i-1)) for every position i in 0..N
             
             For example, in a string of length 2, you have PP, PL, LP, LL
             * Consider the matrix
             *     0  1 Index
             * P   1  1 [Number of string ends with P]
             * 
             * L   0  1 [Number of string ends with L]
             * 
             * Now at i=0, P+L at 0 is 1, eP + eL (where e indicates end index) is 2, 
             * If we replace the end index with A, then the number of strings which can end with A is PA, LA. 
             * 
             * Multiplication of (P+L) * (eP+eL) therefore gives the number of strings which end with A at position n-i-1.
             * 
             * At i=1, P+L at 1 is 2, eP + eL (where e indicates end index) is 1,
             * If we replace the end index (0 in this case) with A, then number of strings which can start with A is AP, AL.
             * 
             * NOTE: Multiplication of (P+L) * (eP+eL) therefore gives the number of strings which end with A at position n-i-1.
             */
            long[] P = new long[n + 1]; // end with P w/o A
            long[] L = new long[n + 1]; // end with L w/o A
            P[0] = P[1] = L[1] = 1;
            for (int i = 2; i <= n; i++) {
                P[i] = (P[i - 1] + L[i - 1]) % mod;
                L[i] = (P[i - 1] + P[i - 2]) % mod;
                System.out.println("Number of strings ends with P is "+P[i]);
                System.out.println("Number of strings ends with L is "+L[i]);
            }
            long res = (P[n] + L[n]) % mod;
            // insert A
            for (int i = 0; i < n; i++) {
                System.out.println("P,L (" + i + ") is " + P[i] +" , " +L[i]);
            }
            for (int i = 0; i < n; i++) {
                long s = ((P[i] + L[i]) % mod * (P[n - i - 1] + L[n - i - 1]) % mod) % mod;
                System.out.println("Number of ways you can insert A at the end "+i+"is "+ s);
                res = (res + s) % mod;
            }
            return (int) res;
        }

        public static void main(String[] args) {
            int n = 2;
            StudentAttendanceRecord_DP object = new StudentAttendanceRecord_DP();
            System.out.println(object.checkRecord(n));
        }
    }

    static class LongestLineOfConsecutiveOneInMatrix {
        /**
         * Given a 01 matrix M, find the longest line of consecutive one in the matrix.
         * The line could be horizontal, vertical, diagonal or anti-diagonal. Example:
         * Input: [[0,1,1,0], [0,1,1,0], [0,0,0,1]]
         * 
         * Output: 3 Hint: The number of elements in the given matrix will not exceed
         * 10,000.
         */
        public int longestLine(int[][] M) {
            if (M == null || M.length == 0) {
                return 0;
            }

            int m = M.length, n = M[0].length;
            int res = 0;

            // dp[i][j][0] means the longest consecutive 1 horizontally at (i, j)
            // dp[i][j][1] means the longest consecutive 1 vertically at (i, j)
            // dp[i][j][2] means the longest consecutive 1 diagnolly at (i, j)
            // dp[i][j][3] means the longest consecutive 1 anti-diagnolly (i, j)
            int[][][] dp = new int[m][n][4];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (M[i][j] == 1) {
                        // Horizontal
                        dp[i][j][0] = j > 0 ? dp[i][j - 1][0] + 1 : 1;
                        // Vertical
                        dp[i][j][1] = i > 0 ? dp[i - 1][j][1] + 1 : 1;
                        // Diagnol
                        dp[i][j][2] = (i > 0 && j > 0) ? dp[i - 1][j - 1][2] + 1 : 1;
                        // Anti-Diagnol
                        dp[i][j][3] = (i > 0 && j < n - 1) ? dp[i - 1][j + 1][3] + 1 : 1;
                        res = Math.max(res, Math.max(dp[i][j][0], dp[i][j][1]));
                        res = Math.max(res, Math.max(dp[i][j][2], dp[i][j][3]));
                    }
                }
            }
            return res;
        }
    }

    static class Trie_insertAndsearchKey{
          
        /**
         * 
         * Every node of Trie consists of multiple branches. Each branch represents a
         * possible character of keys. We need to mark the last node of every key as end
         * of word node. A Trie node field isEndOfWord is used to distinguish the node
         * as end of word node. A simple structure to represent nodes of the English
         * alphabet can be as following,
         * 
         * 
         * 
         * Inserting a key into Trie is a simple approach. Every character of the input
         * key is inserted as an individual Trie node. Note that the children is an
         * array of pointers (or references) to next level trie nodes. The key character
         * acts as an index into the array children. If the input key is new or an
         * extension of the existing key, we need to construct non-existing nodes of the
         * key, and mark end of the word for the last node. If the input key is a prefix
         * of the existing key in Trie, we simply mark the last node of the key as the
         * end of a word. The key length determines Trie depth.
         * 
         *  * 
                * // Trie node 
        struct TrieNode 
        { 
            struct TrieNode *children[ALPHABET_SIZE];
            // isEndOfWord is true if the node 
            // represents end of a word 
            bool isEndOfWord; 
        }; 
         * 
         * 
         * Searching for a key is similar to insert operation, however, we only compare
         * the characters and move down. The search can terminate due to the end of a
         * string or lack of key in the trie. In the former case, if the isEndofWord
         * field of the last node is true, then the key exists in the trie. In the
         * second case, the search terminates without examining all the characters of
         * the key, since the key is not present in the trie. The following picture
         * explains construction of trie using keys given in the example below,
         * 
         *       root
                    /   \    \
                    t   a     b
                    |   |     |
                    h   n     y
                    |   |  \  |
                    e   s  y  e
                 /  |   |
                 i  r   w
                 |  |   |
                 r  e   e
                        |
                        r
         * 
         * 
         * In the picture, every character is of type trie_node_t. For example, the root
         * is of type trie_node_t, and it’s children a, b and t are filled, all other
         * nodes of root will be NULL. Similarly, “a” at the next level is having only
         * one child (“n”), all other children are NULL. The leaf nodes are in blue.
         */
        // Alphabet size (# of symbols)
        static final int ALPHABET_SIZE = 26;

        // trie node
        static class TrieNode {
            TrieNode[] children = new TrieNode[ALPHABET_SIZE];

            // isEndOfWord is true if the node represents
            // end of a word
            boolean isEndOfWord;

            TrieNode() {
                isEndOfWord = false;
                for (int i = 0; i < ALPHABET_SIZE; i++)
                    children[i] = null;
            }
        };

        static TrieNode root;

        // If not present, inserts key into trie
        // If the key is prefix of trie node,
        // just marks leaf node
        static void insert(String key) {
            int level;
            int length = key.length();
            int index;

            TrieNode pCrawl = root;

            for (level = 0; level < length; level++) {
                index = key.charAt(level) - 'a';
                if (pCrawl.children[index] == null)
                    pCrawl.children[index] = new TrieNode();

                pCrawl = pCrawl.children[index];
            }

            // mark last node as leaf
            pCrawl.isEndOfWord = true;
        }

        // Returns true if key presents in trie, else false
        static boolean search(String key) {
            int level;
            int length = key.length();
            int index;
            TrieNode pCrawl = root;

            for (level = 0; level < length; level++) {
                index = key.charAt(level) - 'a';

                if (pCrawl.children[index] == null)
                    return false;

                pCrawl = pCrawl.children[index];
            }

            return (pCrawl.isEndOfWord);
        }

        // Driver
        public static void main(String args[]) {
            // Input keys (use only 'a' through 'z' and lower case)
            String keys[] = { "the", "a", "there", "answer", "any", "by", "bye", "their" };

            String output[] = { "Not present in trie", "Present in trie" };

            root = new TrieNode();

            // Construct trie
            int i;
            for (i = 0; i < keys.length; i++)
                insert(keys[i]);

            // Search for different keys
            if (search("the") == true)
                System.out.println("the --- " + output[1]);
            else
                System.out.println("the --- " + output[0]);

            if (search("these") == true)
                System.out.println("these --- " + output[1]);
            else
                System.out.println("these --- " + output[0]);

            if (search("their") == true)
                System.out.println("their --- " + output[1]);
            else
                System.out.println("their --- " + output[0]);

            if (search("thaw") == true)
                System.out.println("thaw --- " + output[1]);
            else
                System.out.println("thaw --- " + output[0]);

        }
    }

    static class AutoCompleteSystemUsingTriePriorityQueueAndHashMap {
        /**
         * Design a search autocomplete system for a search engine. Users may input a
         * sentence (at least one word and end with a special character '#'). For each
         * character they type except '#', you need to return the top 3 historical hot
         * sentences that have prefix the same as the part of sentence already typed.
         * Here are the specific rules:
         * 
         * The hot degree for a sentence is defined as the number of times a user typed
         * the exactly same sentence before. The returned top 3 hot sentences should be
         * sorted by hot degree (The first is the hottest one). If several sentences
         * have the same degree of hot, you need to use ASCII-code order (smaller one
         * appears first). If less than 3 hot sentences exist, then just return as many
         * as you can. When the input is a special character, it means the sentence
         * ends, and in this case, you need to return an empty list. Your job is to
         * implement the following functions: The constructor function:
         * 
         * AutocompleteSystem(String[] sentences, int[] times): This is the constructor.
         * The input is historical data. Sentences is a string array consists of
         * previously typed sentences. Times is the corresponding times a sentence has
         * been typed. Your system should record these historical data.
         * 
         * Now, the user wants to input a new sentence. The following function will
         * provide the next character the user types:
         * 
         * List input(char c): The input c is the next character typed by the user. The
         * character will only be lower-case letters ('a' to 'z'), blank space (' ') or
         * a special character ('#'). Also, the previously typed sentence should be
         * recorded in your system. The output will be the top 3 historical hot
         * sentences that have prefix the same as the part of sentence already typed.
         * 
         * Example:
         * 
         * Operation: AutocompleteSystem(["i love you", "island","ironman", "i love
         * leetcode"], [5,3,2,2]) The system have already tracked down the following
         * sentences and their corresponding times: "i love you" : 5 times "island" : 3
         * times "ironman" : 2 times "i love leetcode" : 2 times Now, the user begins
         * another search:
         * 
         * Operation: input('i') Output: ["i love you", "island","i love leetcode"]
         * Explanation: There are four sentences that have prefix "i". Among them,
         * "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII
         * code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of
         * "ironman". Also we only need to output top 3 hot sentences, so "ironman" will
         * be ignored.
         * 
         * Operation: input(' ') Output: ["i love you","i love leetcode"] Explanation:
         * There are only two sentences that have prefix "i ".
         * 
         * Operation: input('a') Output: [] Explanation: There are no sentences that
         * have prefix "i a".
         * 
         * Operation: input('#') Output: [] Explanation: The user finished the input,
         * the sentence "i a" should be saved as a historical sentence in system. And
         * the following input will be counted as a new search. Note: The input sentence
         * will always start with a letter and end with '#', and only one blank space
         * will exist between two words. The number of complete sentences that to be
         * searched won't exceed 100. The length of each sentence including those in the
         * historical data won't exceed 100. Please use double-quote instead of
         * single-quote when you write test cases even for a character input. Please
         * remember to RESET your class variables declared in class AutocompleteSystem,
         * as static/class variables are persisted across multiple test cases. Please
         * see here for more details.
         */
        class TrieNode {
            Map<Character, TrieNode> children;
            // This is to store children of every character node. [a] -> [n] -> [y], [a] -> [s]
            Map<String, Integer> counts;
            // To store counts of a string at every node, for example
            /**
             * 
             * ["i love you", "island","ironman", "i love leetcode"], [5,3,2,2]
             * At node i, all the strings will be added with their count. 
             * At node i , strings 1 and 4 will be added with their counts.
             * This makes easy to return the hot and relevant elements as the user types in.* 
             */
            boolean isWord;//to indicate end of the word.

            public TrieNode() {
                children = new HashMap<>();
                counts = new HashMap<>();
                isWord = false;
            }
        }

        TrieNode root;
        String prefix;

        public AutoCompleteSystemUsingTriePriorityQueueAndHashMap(String[] sentences, int[] times) {
            root = new TrieNode();
            prefix = "";

            for (int i = 0; i < sentences.length; i++) {
                add(sentences[i], times[i]);
            }
        }

        private void add(String s, int count) {
            TrieNode curr = root;
            for (char c : s.toCharArray()) {
                curr.children.putIfAbsent(c, new TrieNode()); // Create a char node if absent.
                curr = curr.children.get(c);// Get the char node
                curr.counts.put(s, curr.counts.getOrDefault(s, 0) + count); 
                // Add the string with it's count in all character paths of a string, for example a, n, y -> for all the char nodes, string any will be added with it's count.
            }
            curr.isWord = true;
        }

        public List<String> input(char c) {
            if (c == '#') { //If the special character is typed, add the string to the trie tree.
                add(prefix, 1);
                prefix = "";
                return new ArrayList<String>();// return empty as per the constraint.
            }
            prefix = prefix + c; // add this new character to prefix.

            TrieNode curr = root;

            for (char ch : prefix.toCharArray()) {
                if (!curr.children.containsKey(ch)) { // If the char is not found, return empty
                    return new ArrayList<String>();
                }
                curr = curr.children.get(ch); // Get the last matched node in the trie tree
            }

            Comparator<Map.Entry<String, Integer>> cmp = new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                    return a.getValue() == b.getValue() ? b.getKey().compareTo(a.getKey())
                             : a.getValue() - b.getValue();
                            // If the frequency count are not same, comparator is in ascending order, which means the priority queue will function as min heap, 
                            // If the counts are same, this comparator returns the minimum ascii one.
                }
            };
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(cmp);
            int k = 3;
            for (Map.Entry<String, Integer> entry : curr.counts.entrySet()) {
                pq.offer(entry); // for the matched node, add all the strings to PQ.
                while (!pq.isEmpty() && pq.size() > k) {// retain K min elements.
                    pq.poll();
                }
            }

            ArrayList<String> res = new ArrayList<>();
            while (!pq.isEmpty()) {// dequeue k elements and add them in reverse order to the list in reverse order
                // first min element will be at the last, third min element will be at the top.
                System.out.println(pq.peek());
                res.add(0, pq.poll().getKey());
            }
            System.out.println(res);
            return res;
        }

        public static void main(String[] args) {
            String[] sentences = new  String[] {"i love you", "island","ironman", "i love leetcode"};
            int[] times = new int[]{5,3,2,2};
            AutoCompleteSystemUsingTriePriorityQueueAndHashMap object = new AutoCompleteSystemUsingTriePriorityQueueAndHashMap(sentences, times);
            System.out.println(object.input('i'));
            System.out.println(object.input(' '));
        }
    }

    static class EmployeeImportanceUsingDFS {
        /**
         * You have a data structure of employee information, which includes the
         * employee's unique id, their importance value, and their direct subordinates'
         * id.
         * 
         * You are given an array of employees employees where:
         * 
         * employees[i].id is the ID of the ith employee. employees[i].importance is the
         * importance value of the ith employee. employees[i].subordinates is a list of
         * the IDs of the subordinates of the ith employee. Given an integer id that
         * represents the ID of an employee, return the total importance value of this
         * employee and all their subordinates.
         */
        /**
         * Let's use a hashmap emap = {employee.id -> employee} to query employees
         * quickly.
         * 
         * Now to find the total importance of an employee, it will be the importance of
         * that employee, plus the total importance of each of that employee's
         * subordinates. This is a straightforward depth-first search.
         */
        class Employee {
            int id;
            int importance;
            int[] subordinates;
        }

        Map<Integer, Employee> emap;
        public int getImportance(List<Employee> employees, int queryid) {
            emap = new HashMap();
            for (Employee e: employees) emap.put(e.id, e);
            return dfs(queryid);
        }
        public int dfs(int eid) {
            Employee employee = emap.get(eid);
            int ans = employee.importance;
            for (Integer subid: employee.subordinates)
                ans += dfs(subid);
            return ans;
        }
    }

    static class RangeModule_IntervalDS_splitAndMergeIntervals {
        /**
         * 
         * A Range Module is a module that tracks ranges of numbers. Design a data structure to track the ranges represented as half-open intervals and query about them.

            A half-open interval [left, right) denotes all the real numbers x where left <= x < right.

            Implement the RangeModule class:

            RangeModule() Initializes the object of the data structure.
            void addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval. Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval [left, right) that are not already tracked.
            boolean queryRange(int left, int right) Returns true if every real number in the interval [left, right) is currently being tracked, and false otherwise.
            void removeRange(int left, int right) Stops tracking every real number currently being tracked in the half-open interval [left, right).
            

            Example 1:

            Input
            ["RangeModule", "addRange", "removeRange", "queryRange", "queryRange", "queryRange"]
            [[], [10, 20], [14, 16], [10, 14], [13, 15], [16, 17]]
            Output
            [null, null, null, true, false, true]

            Explanation
            RangeModule rangeModule = new RangeModule();
            rangeModule.addRange(10, 20);
            rangeModule.removeRange(14, 16);
            rangeModule.queryRange(10, 14); // return True,(Every number in [10, 14) is being tracked)
            rangeModule.queryRange(13, 15); // return False,(Numbers like 14, 14.03, 14.17 in [13, 15) are not being tracked)
            rangeModule.queryRange(16, 17); // return True, (The number 16 in [16, 17) is still being tracked, despite the remove operation)
         */

        TreeMap<Integer, Integer> intervals = new TreeMap<>();

        public void addRange(int left, int right) {
            // Ranges (l,r) are stored in key-value pair.
            /** 
             * Case 1:
             *  For example, assume the map already has {10 = 20} 
             *  For 14, 16 which is within the range, 
             *  start and end will be 10. floor(14) and floor(16) is 10. 
             
             *  If r of start interval (which is 20) is greater than the left 14, then left is start (10)
             *  If r of end interval (which is 20) is greater than the right 16, then right is (20).
             * 
             *  Add the left, right pair to the tree map sorted by key.
             * 
             * CASE 2: Pay attention 
             * Exists two pairs: {10=20, 21=30} Input: {15,23} (L, R)
             * 
             * 1) Find the two intervals that overlap for left and right.
             * Start = 10, end = 21
             * 2) For the start interval, if the right is greater than left of the input, then set left to start interval
             * 3) For the end interval, if the right is greater than right of the input, then set right to right of end interval.
             * Merged interval should be the output: {10, 30}
             * 
             * Clear the interval exclusive of left of merged interval and inclusive of right of merged interval.
             * Basically, keys which are greater than 10 and less than 30, will be removed. This should be done because the merged interval covers the path.
            */
            Integer start = intervals.floorKey(left);
            Integer end = intervals.floorKey(right);
            // System.out.println("Start:End "+start+":"+end);
            if (start != null && intervals.get(start) >= left) { 
                left = start;
            }
            if (end != null && intervals.get(end) > right) {
                right = intervals.get(end);
            }
            // System.out.println("Left:Right "+left+":"+right);
            intervals.put(left, right);
            // System.out.println("Before clear Intervals "+ intervals);

            intervals.subMap(left, false, right, true).clear();// exclusive of left, inclusive of right.
            // System.out.println("After clear intervals "+ intervals);
        }

        public boolean queryRange(int left, int right) {
            // Returns if the left and right number inclusive is being tracked in the interval tree.
            Integer start = intervals.floorKey(left);
            if (start == null)
                return false;
            return intervals.get(start) >= right;
        }

        public void removeRange(int left, int right) {
            /**
             * Case 1:
             *  With 10,30 as the interval, when the input is 14, 16, the range should be divided.
             * 
             *  1) Find the overlapping intervals for the given input. start is 10, end is 10. 
             *  2) When right < right of end, then put <right, right of end> i.e. <16, 30>
             *  3) When left < right of start, then put <start, left>
             * 
             * 
             *  Case 2:
             *  
             * 1) Exists: {5=9, 10=30} input: {8, 16}
             * 2) Start = 5, end = 10
             * 3) Merge will happen as shared above: {5=8, 16=30}, but it will contain {10,30}
             * 4) {8:16} is left:right. Any keys greater than or equal to 8 but less than 16 is cleared. 10, 30 in this case.
             */
            Integer start = intervals.floorKey(left);
            Integer end = intervals.floorKey(right);
            System.out.println("Start:End "+start+":"+end);

            if (end != null && intervals.get(end) > right) {
                intervals.put(right, intervals.get(end));
            }
            if (start != null && intervals.get(start) > left) {
                intervals.put(start, left);
            }
            System.out.println("Before clear Intervals "+ intervals);
            System.out.println("Left:right "+left+":"+right);
            intervals.subMap(left, true, right, false).clear();// inclusive of left, exclusive of right.
            System.out.println("After clear intervals "+ intervals);

        }
        public static void main(String[] args){
            RangeModule_IntervalDS_splitAndMergeIntervals obj = new RangeModule_IntervalDS_splitAndMergeIntervals();
            obj.addRange(10, 20);
            obj.addRange(21, 30);
            obj.addRange(15, 23);
            obj.addRange(5, 9);
            obj.removeRange(8, 16);
            System.out.println(obj.queryRange(7, 12));
            System.out.println(obj.queryRange(17, 23));
            System.out.println(obj.intervals);
        }
    }

    static class RemoveCommentsInSource {
        public List<String> removeComments(String[] source) {
            // //Given a C++ program, remove comments from it. The program source is an
            // array of strings source where source[i] is the ith line of the source code.
            // This represents the result of splitting the original source code string by
            // the newline character '\n'.

            // In C++, there are two types of comments, line comments, and block comments.

            // The string "//" denotes a line comment, which represents that it and the rest
            // of the characters to the right of it in the same line should be ignored.
            // The string "/*" denotes a block comment, which represents that all characters
            // until the next (non-overlapping) occurrence of "*/" should be ignored. (Here,
            // occurrences happen in reading order: line by line from left to right.) To be
            // clear, the string "/*/" does not yet end the block comment, as the ending
            // would be overlapping the beginning.
            // The first effective comment takes precedence over others.

            // For example, if the string "//" occurs in a block comment, it is ignored.
            // Similarly, if the string "/*" occurs in a line or block comment, it is also
            // ignored.
            // If a certain line of code is empty after removing comments, you must not
            // output that line: each string in the answer list will be non-empty.

            // There will be no control characters, single quote, or double quote
            // characters.

            // For example, source = "string s = "/* Not a comment. */";" will not be a test
            // case.
            // Also, nothing else such as defines or macros will interfere with the
            // comments.

            // It is guaranteed that every open block comment will eventually be closed, so
            // "/*" outside of a line or block comment always starts a new comment.

            // Finally, implicit newline characters can be deleted by block comments. Please
            // see the examples below for details.

            // After removing the comments from the source code, return the source code in
            // the same format.

            // Example 1:

            // Input: source = ["/*Test program */", "int main()", "{ ", " // variable
            // declaration ", "int a, b, c;", "/* This is a test", " multiline ", " comment
            // for ", " testing */", "a = b + c;", "}"]
            // Output: ["int main()","{ "," ","int a, b, c;","a = b + c;","}"]
            // Explanation: The line by line code is visualized as below:
            // /*Test program */
            // int main()
            // {
            // // variable declaration
            // int a, b, c;
            // /* This is a test
            // multiline
            // comment for
            // testing */
            // a = b + c;
            // }
            // The string /* denotes a block comment, including line 1 and lines 6-9. The
            // string // denotes line 4 as comments.
            // The line by line output code is visualized as below:
            // int main()
            // {

            // int a, b, c;
            // a = b + c;
            // }
            // Example 2:

            // Input: source = ["a/*comment", "line", "more_comment*/b"]
            // Output: ["ab"]
            // Explanation: The original source string is
            // "a/*comment\nline\nmore_comment*/b", where we have bolded the newline
            // characters. After deletion, the implicit newline characters are deleted,
            // leaving the string "ab", which when delimited by newline characters becomes
            // ["ab"].

            List<String> res = new ArrayList<>();
            StringBuilder sb = new StringBuilder();     
            boolean mode = false;
            for (String s : source) {
                for (int i = 0; i < s.length(); i++) {
                    if (mode) {
                        if (s.charAt(i) == '*' && i < s.length() - 1 && s.charAt(i + 1) == '/') {
                            mode = false;
                            i++;        //skip '/' on next iteration of i
                        }
                    }
                    else {
                        if (s.charAt(i) == '/' && i < s.length() - 1 && s.charAt(i + 1) == '/') {
                            break;      //ignore remaining characters on line s
                        }
                        else if (s.charAt(i) == '/' && i < s.length() - 1 && s.charAt(i + 1) == '*') {
                            mode = true;
                            i++;           //skip '*' on next iteration of i
                        }
                        else    sb.append(s.charAt(i));     //not a comment
                    }
                }
                if (!mode && sb.length() > 0) {
                    res.add(sb.toString());
                    sb = new StringBuilder();   //reset for next line of source code
                }
            }
            return res;
        }
    }

    static class MinimumContiguousSubstringWindowSubsequence {
        /**
         * Given strings S and T, find the minimum (contiguous) substring W of S, so
         * that T is a subsequence of W. If there is no such window in S that covers all
         * characters in T, return the empty string "". If there are multiple such
         * minimum-length windows, return the one with the left-most starting index.
         * Example 1: Input: S = "abcdebdde", T = "bde" Output: "bcde" Explanation:
         * "bcde" is the answer because it occurs before "bdde" which has the same
         * length. "deb" is not a smaller window because the elements of T in the window
         * must occur in order.
         * 
         * Note: All the strings in the input will only contain lowercase letters. The
         * length of S will be in the range [1, 20000]. The length of T will be in the
         * range [1, 100]
         * 
         * Analysis: This is a two-sequence DP problem. We define dp[S.length() +
         * 1][T.length() + 1], where dp[i][j] means the START POSITION of the minimum
         * windows subsequnce for the first i charactgers from S and first j characters
         * from T.
         */
        public String minWindow(String S, String T) {
            if (S == null || S.length() == 0 || T == null || T.length() == 0) {
                return "";
            }

            int[][] dp = new int[S.length() + 1][T.length() + 1];
            for (int i = 1; i <= T.length(); i++) {
                dp[0][i] = -1;
            }

            for (int i = 1; i <= S.length(); i++) {
                dp[i][0] = i;
            }

            int minLen = Integer.MAX_VALUE;
            int startPos = -1;
            for (int i = 1; i <= S.length(); i++) {
                for (int j = 1; j <= T.length(); j++) {
                    dp[i][j] = -1;

                    if (S.charAt(i - 1) == T.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                        //If it's not upper triangle, then you have to refer back
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }

                if (dp[i][T.length()] != -1) {
                    // If the target string is found on a char in source string, record the min length and start position
                    int len = i - dp[i][T.length()];
                    if (len < minLen) {
                        startPos = dp[i][T.length()];
                        minLen = len;
                    }
                }
            }

            return startPos == -1 ? "" : S.substring(startPos, startPos + minLen);
        }
    }

    static class MyCalendarI{
        /**
         * You are implementing a program to use as your calendar. We can add a new
         * event if adding the event will not cause a double booking.
         * 
         * A double booking happens when two events have some non-empty intersection
         * (i.e., some moment is common to both events.).
         * 
         * The event can be represented as a pair of integers start and end that
         * represents a booking on the half-open interval [start, end), the range of
         * real numbers x such that start <= x < end.
         * 
         * Implement the MyCalendar class:
         * 
         * MyCalendar() Initializes the calendar object. boolean book(int start, int
         * end) Returns true if the event can be added to the calendar successfully
         * without causing a double booking. Otherwise, return false and do not add the
         * event to the calendar.
         * 
         * 
         * Example 1:
         * 
         * Input ["MyCalendar", "book", "book", "book"] [[], [10, 20], [15, 25], [20,
         * 30]] Output [null, true, false, true]
         * 
         * Explanation MyCalendar myCalendar = new MyCalendar(); myCalendar.book(10,
         * 20); // return True myCalendar.book(15, 25); // return False, It can not be
         * booked because time 15 is already booked by another event.
         * myCalendar.book(20, 30); // return True, The event can be booked, as the
         * first event takes every time less than 20, but not including 20.
         */
        /**
         * Intuition
         * 
         * If we maintained our events in sorted order, we could check whether an event
         * could be booked in O(logN) time (where NN is the number of events
         * already booked) by binary searching for where the event should be placed. We
         * would also have to insert the event in our sorted structure.
         * 
         * Algorithm
         * 
         * We need a data structure that keeps elements sorted and supports fast
         * insertion. In Java, a TreeMap is the perfect candidate. In Python, we can
         * build our own binary tree structure.
         * 
         * For Java, we will have a TreeMap where the keys are the start of each
         * interval, and the values are the ends of those intervals. When inserting the
         * interval [start, end), we check if there is a conflict on each side with
         * neighboring intervals: we would like calendar.get(prev)) <= start <= end <=
         * next for the booking to be valid (or for prev or next to be null
         * respectively.)
         * 
         * For Python, we will create a binary tree. Each node represents some interval
         * [self.start, self.end) while self.left, self.right represents nodes that are
         * smaller or larger than the current node.
         */
        TreeMap<Integer, Integer> calendar;

        MyCalendarI() {
            calendar = new TreeMap();
        }

        public boolean book(int start, int end) {
            Integer prev = calendar.floorKey(start), next = calendar.ceilingKey(start);
            if ((prev == null || calendar.get(prev) <= start) && (next == null || end <= next)) {
                calendar.put(start, end);
                return true;
            }
            return false;
        }
    }

    static class CrackingTheSafeDFSHamiltonian {
        /**
         * There is a box protected by a password. The password is a sequence of n
         * digits where each digit can be in the range [0, k - 1].
         * 
         * While entering a password, the last n digits entered will automatically be
         * matched against the correct password.
         * 
         * For example, assuming the correct password is "345", if you type "012345",
         * the box will open because the correct password matches the suffix of the
         * entered password. Return any password of minimum length that is guaranteed to
         * open the box at some point of entering it.
         * 
         * Example 1:
         * 
         * Input: n = 1, k = 2 Output: "10" Explanation: "01" will be accepted too.
         * Example 2:
         * 
         * Input: n = 2, k = 2 Output: "01100" Explanation: "01100", "10011", "11001"
         * will be accepted too.
         */
        /**
         * In order to guarantee to open the box at last, the input password ought to
         * contain all length-n combinations on digits [0..k-1] - there should be k^n
         * combinations in total.
         * 
         * To make the input password as short as possible, we'd better make each
         * possible length-n combination on digits [0..k-1] occurs exactly once as a
         * substring of the password. The existence of such a password is proved by De
         * Bruijn sequence:
         * 
         * A de Bruijn sequence of order n on a size-k alphabet A is a cyclic sequence
         * in which every possible length-n string on A occurs exactly once as a
         * substring. It has length k^n, which is also the number of distinct substrings
         * of length n on a size-k alphabet; de Bruijn sequences are therefore optimally
         * short.
         * 
         * We reuse last n-1 digits of the input-so-far password as below:
         * 
         * e.g., n = 2, k = 2
        all 2-length combinations on [0, 1]: 
        00 (`00`110), 
         01 (0`01`10), 
          11 (00`11`0), 
           10 (001`10`)
           
        the password is 00110
         * 
         * We can utilize DFS to find the password:
         * 
         * goal: to find the shortest input password such that each possible n-length
         * combination of digits [0..k-1] occurs exactly once as a substring.
         * 
         * node: current input password
         * 
         * edge: if the last n - 1 digits of node1 can be transformed to node2 by
         * appending a digit from 0..k-1, there will be an edge between node1 and node2
         * 
         * start node: n repeated 0's end node: all n-length combinations among digits
         * 0..k-1 are visited
         * 
         * visitedComb: all combinations that have been visited
         */
        public String crackSafe(int n, int k) {
            // Initialize pwd to n repeated 0's as the start node of DFS.
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < n; i++) {// Make n copies.
                builder.append("0");
            }
            String strPwd = builder.toString();
            StringBuilder sbPwd = new StringBuilder(strPwd);
            
            Set<String> visitedComb = new HashSet<>();
            visitedComb.add(strPwd);// Add the string as visited
        
            int targetNumVisited = (int) Math.pow(k, n);
            // There can be total k^n combinations. When we reached all of them return. 
            // The string which contains all the combinations is the min length
            
            crackSafeAfter(sbPwd, visitedComb, targetNumVisited, n, k);
            return sbPwd.toString();
        }
        
        private boolean crackSafeAfter(StringBuilder pwd, Set<String> visitedComb, int targetNumVisited, int n, int k) {
            // This is similar to hamiltonian cycle where every node should be visited once and added to the path.
            // Hamiltonian path is the shortest path (which contains all the node once) for the password.

            // We will need to make sure that the pwd contains all possible combinations of k digits.
            // Base case: all n-length combinations in a string among digits 0..k-1 are visited. 
            if (visitedComb.size() == targetNumVisited) {
                return true;
            }
            
            /**
             *  For k=2,
             *             
             * 00 -> 01 -> 11 -> 10: This gives the password 00110
             * 
             * Add arrow when you add an element to end of node which overlaps with the other node in the graph.
             * So, in this example, 00 when added with 1, it becomes 001, where 01 overlaps with 00.
             * 
             * 00, add 0 to the end, 000 becomes the target.
             * 
             * Refer this: https://www.youtube.com/watch?v=iPLQgXUiU14
             * 
             * 00110 is the password (path where every node is visited only once)
             * 
             * As you can see from the above example, we will need to append an element to a node of length 2, 
             * and check if it's not already visited, if it's not, then add the char to result.
             * 
             * Below is the algorithm for the same
             * 
             */
            String lastDigits = pwd.substring(pwd.length() - n + 1); // Last n-1 digits of pwd.
            for (char ch = '0'; ch < '0' + k; ch++) { // try out all digits of k, 
                String newComb = lastDigits + ch; // Add the char to last.
                if (!visitedComb.contains(newComb))  {
                    visitedComb.add(newComb); 
                    System.out.println("New unvisited combination "+ newComb);
                    System.out.println("before pwd "+pwd);
                    pwd.append(ch);
                    // pwd stores the path we've traversed in the graph, if it doesn't yield true result, we remove that from the path, and try out other chars/vertices.
                    // Note that you're appending the char/digit, in the next iteration, the string with appeneded char will be extracted for next level of DFS.
                    System.out.println("after pwd "+pwd);
                    if (crackSafeAfter(pwd, visitedComb, targetNumVisited, n, k)) {
                        return true;
                    }
                    visitedComb.remove(newComb);
                    pwd.deleteCharAt(pwd.length() - 1); // If the above has not resulted in cracking the safe, remove the char.
                }
            }
            
            return false;
        }

        public static void main(String[] args){
            int n=3, k=2;
            CrackingTheSafeDFSHamiltonian obj = new CrackingTheSafeDFSHamiltonian();
            System.out.println(obj.crackSafe(n, k));
        }

    }

    static class FindSecretWordWith10GuessesInWordList {
        /**
         * . Clarify
            Does the algorithm I design have to find the secret word within 10 times of guesses? The answer is No, you only need to design an algorithm that can find the secret word in as less times as possible.

         * Idea: 
         * 
         * Because if we find the secret word after 10 times of calling master.guess(), we fail the test case, let's call master.guess() 10 times or until we find the secret word, whichever comes first.

            Because there is no guanrantee that we can find the secret word within 10 calls, now our goal is to design a algorithm to find the secret word in as less times of calling master.guess() as possible.

            How to find the secret word in as less times of calling master.guess() as possible? The answer is trying to narrow the candidates after each time we call master.guess()

            For now, the structure of our algorithm is:

            //pseudocode version 1
            for(int i = 0, matches = 0; i < 10 && matches != 6; i ++){
                matches = master.guess(a word in candidate);
                reduce the number of words in candidate
            }
            Now the key problem is, how to narrow the candidates?
            we have x = master.guess(word)
            if x == 6, we find the secret word, the algorithm ends
            if x != 6, it means secret has exactly x matches with word

            Because secret has exactly x matches with word, we can just search in the candidates, and only keep the ones that have exact x matches with word. In this way, we narrow the candidates after we call master.guess(), and guarantee that secret is in candidates left

            For now, the structure of our algorithm is changed to

            //pseudocode version 2
            for(int i = 0, matches = 0; i < 10 && matches != 6; i ++){
                matches = master.guess(a word in candidates);

                for(String candidate: candidates){
                    if(matches == getMatches(candidate, word)){
                        tempCandidates.add(candidate);
                    }
                }

                candidates = tempCandidates;
            }
            So we need a helper method to check the number of matches between two words:

            int getMatches(String word1, String word2){
                int matches = 0;
                for(int i = 0; i < 6; i ++){
                    if(word1.charAt(i) == word2.charAt(i)){
                        matches ++;
                    }
                }
            }

            Now the key problem is how to select word in candidates as the input of master.guess()?
            We have two options:

            option-1: select the first candidate as the input of master.guess() every time
            option-2: randomly select one word from candidates as the input of master.guess()

            option2 is better

            For now, the structure of our algorithm is changed to

            //pseudocode version 3
            for(int i = 0, matches = 0; i < 10 && matches != 6; i ++){
                matches = master.guess(randomly select a word in candidates);
                for(String candidate: candidates){
                    if(matches == getMatches(candidate, word)){
                        tempCandidates.add(candidate);
                    }
                }

                candidates = tempCandidates;
            }

         */
        interface Master {
            int guess(String guess);
        }
        public void findSecretWord(String[] wordlist, Master master) {
            Random random = new Random();
            for(int i = 0, matches = 0; i < 10 && matches != 6; i ++){
                String guess = wordlist[random.nextInt(wordlist.length)];
                matches = master.guess(guess);
                List<String> candidates = new ArrayList<>();
                for(String word: wordlist){
                    if(matches == getMatches(guess, word)){
                        candidates.add(word);
                    }
                }
                
                wordlist = candidates.toArray(new String[0]);
            }
        }
        
        private int getMatches(String word1, String word2){
            int matches = 0;
            for(int i = 0; i < word1.length(); i ++){
                if(word1.charAt(i) == word2.charAt(i)){
                    matches ++;
                }
            }
            
            return matches;
        }
    }

    static class MinNumberOfKConsecutiveBitFlips {
        /**
         * In an array nums containing only 0s and 1s, a k-bit flip consists of choosing a (contiguous) subarray of length k 
         * and simultaneously changing every 0 in the subarray to 1, and every 1 in the subarray to 0.
         * 
            Return the minimum number of k-bit flips required so that there is no 0 in the array.  If it is not possible, return -1.
            Example 1:

            Input: nums = [0,1,0], k = 1
            Output: 2
            Explanation: Flip nums[0], then flip nums[2].
            Example 2:

            Input: nums = [1,1,0], k = 2
            Output: -1
            Explanation: No matter how we flip subarrays of size 2, we can't make the array become [1,1,1].
            Example 3:

            Input: nums = [0,0,0,1,0,1,1,0], k = 3
            Output: 3
            Explanation:
            Flip nums[0],nums[1],nums[2]: nums becomes [1,1,1,1,0,1,1,0]
            Flip nums[4],nums[5],nums[6]: nums becomes [1,1,1,1,1,0,0,0]
            Flip nums[5],nums[6],nums[7]: nums becomes [1,1,1,1,1,1,1,1]
         */
        int minKBitFlips(int[] A, int K) {
            int n = A.length;
            /*** 
             * 
             * 
            */
           /*Keeps track of the last index of a k-window flip
            The size of the queue at anytime gives us the total number of flips that
            have happened within any k-interval
            
            1) for example, [0 0 0] -> queue will be [2] i+K-1 = 0 + 3 - 1 = 2, queue size is 1, which means all the K elements are flipped once. 
            and the last element flipped is at index 2.

            2) When this window is over, we pop this last element index when we pass the index >= last element index (this means the window is done).

            3) [1 0 1], queue is empty and the first element is 1, do nothing. second element is 0, we will need to flip the window i+K-1 = 4+3-1 = 6

            Note in the example below, that index 0, 1, 1 from index 4 - 6 is flipped leaving out index 3.
             Input: nums = [0,0,0,1,0,1,1,0], k = 3
            Output: 3
            Explanation:
            Flip nums[0],nums[1],nums[2]: nums becomes [1,1,1,1,0,1,1,0]
            Flip nums[4],nums[5],nums[6]: nums becomes [1,1,1,1,1,0,0,0]
            Flip nums[5],nums[6],nums[7]: nums becomes [1,1,1,1,1,1,1,1]

               Until index 6, all elements are flipped once, this index is added to queue. Queue is odd size, so it's flipped only once.

               Third element is 1, queue is odd sized (1) element-[6], which means this element is flipped to zero, we will need to flip again. Add the window's last index 
               again to queue. Queue is [6, 7] Number of flips so far is 2.

            4) [1 0] Queue: [6,7] even sized, 1 is encountered, even flip of 1 is 1. So we do nothing. Remove the element 6 from queue, given the current index 6 >= queue's front index.
            Queue: [7], element is zero, queue size is odd means the element is already flipped in the window, do nothing. lastly pop the element from the queue.

            5) Now the total flip done is 3.

            As you can see from the above example, queue size shows the number of flips that have happened so far at any given index.

            The idea is, as we move through the array, everytime I see a 0, I need to do a k-flip.
            As a result of a single k-flip, the k-1 elements to the right are also affected.
            I use a queue, to keep track of how many valid flips have been executed for the current index.
            By maintaining the last index for which a flip is valid, I can easily pop it off, when I pass that (last) index.
            Essentially this makes, the current size of queue equal to the number of flips the current index has undergone and can determine if its 0 or 1, based on that.

            So, if the number of flips is even, and my a[i] == 0, then I need to do one more flip. I add that flip's last index to the queue
            In a similar way, if the number of flips is odd, and mu a[i] == 1, then i need to do one more flip.
            */
            Queue<Integer> isflipped = new LinkedList<>(); //Space is O(K) max
            int count = 0; //min k-bit flips
            
            for(int i = 0; i < n; i++){ //O(N)
              //If current index is 0, then we need to process it/do sth about it
              if(A[i] == 0){
                System.out.println("Before Queue when a[i] "+i+" is zero: "+ isflipped);
                
                /*If our queue is empty or queue is even size
                (i.e our current index has been flipped even # of times , thus ending back at state 0), 
                then we need to flip this 0 to a 1*/
                if(isflipped.isEmpty() || isflipped.size() % 2 == 0){
                  count++; //Flip it
                  /*Populate the queue with the index of the end of this k interval that was flipped*/
                  isflipped.add(i + K - 1);
                  System.out.println("After Queue when a[i] "+i+"  is zero: "+ isflipped);
                }

                /*Lets say we are here, that means our original element is a zero and the queu size is
                odd sized which means that this 0 has been flipped odd # of times, giving us the result 1.
                So we do nothing since we know the element has been flipped to 1 already*/
              }else{ //A[i] == 1
                /*If A[i] == 1, we need to process it only in one case*/
                System.out.println("Before Queue when a[i] "+i+"  is one: "+ isflipped);
                if(isflipped.size() % 2 != 0){
                  /*this means a '1' has been flipped odd # of times , thus ending at state '0', 
                  we need to flip this then*/
                  count++;

                  /*Populate the queue with the index of the end of this k interval that was flipped*/
                  isflipped.add(i + K - 1);
                System.out.println("After Queue when a[i] "+i+"  is one: "+ isflipped);
                }
              }
              
              //Anytime we are done with a interval, we pop the index out of the queue
              //MISTAKE: Make sure to check the q is emoty or not before popping off the value
              if(!isflipped.isEmpty() && i >= isflipped.peek()) 
              {   
                  System.out.println("Before remove element from queue  i is "+ i + " peek index is "+isflipped.peek());
                  isflipped.remove();
                  System.out.println("After remove element from queue, queue is " + isflipped);
              }
            }
            
            /*Return the minimum number of K-bit flips required so that there is no 0 in the array.  If it is not possible, return -1*/
            return isflipped.isEmpty() ? count : -1;
          }

          public static void main(String[] args){
            MinNumberOfKConsecutiveBitFlips obj = new MinNumberOfKConsecutiveBitFlips();
            System.out.println(obj.minKBitFlips(new int[]{0,0,0,1,0,1,1,0}, 3));
          }

    }

    static class AlphabetBoardPath_MultiBFS {
        /**
         * On an alphabet board, we start at position (0, 0), corresponding to character
         * board[0][0].
         * 
         * Here, board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"], as shown in
         * the diagram below.
         * 
         * 
         * 
         * We may make the following moves:
         * 
         * 'U' moves our position up one row, if the position exists on the board; 'D'
         * moves our position down one row, if the position exists on the board; 'L'
         * moves our position left one column, if the position exists on the board; 'R'
         * moves our position right one column, if the position exists on the board; '!'
         * adds the character board[r][c] at our current position (r, c) to the answer.
         * (Here, the only positions that exist on the board are positions with letters
         * on them.)
         * 
         * Return a sequence of moves that makes our answer equal to target in the
         * minimum number of moves. You may return any path that does so.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: target = "leet" Output: "DDR!UURRR!!DDD!"
         * 
         * Example 2:
         * 
         * Input: target = "code" Output: "RR!DDRR!UUL!R!"
         */
        class BoardPoint {
            public int X;
            public int Y;

            public List<Character> Directions;

            public BoardPoint(int x, int y) {
                X = x;
                Y = y;
                Directions = new ArrayList<Character>();
            }

            public BoardPoint(int x, int y, List<Character> directions) {
                this(x, y);
                Directions = directions;
            }
        }

        public String AlphabetBoardPath_MultiBFS(String target) {
            char[][] board = new char[][] { "abcde".toCharArray(), "fghij".toCharArray(), "klmno".toCharArray(),
                    "pqrst".toCharArray(), "uvwxy".toCharArray(), "z".toCharArray() };

            if (target == null || target.length() == 0) {
                return null;
            }
            return BFS(board, target);
        }

        public String BFS(char[][] board, String target)
        {
            StringBuilder sb = new StringBuilder();
            boolean[] visited = new boolean[26];
            Queue<BoardPoint> q = new LinkedList<BoardPoint>();

            int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1} };
            char[] charDirections = new char[] { 'U', 'D', 'L', 'R' };
            // The order  is super important, it should be as given in the problem.
            int m = board.length;
            // int n = board[0].length;

            q.add(new BoardPoint(0, 0));
            visited[0] = true;

            int nextchar = 0;
            char targetChar = target.charAt(nextchar++);

            while (q.size() > 0)
            {
                BoardPoint point = q.poll();
                if (board[point.X][point.Y] == targetChar)
                {
                    q.clear();
                    for (int i=0; i< point.Directions.size(); i++)
                    {
                        sb.append(point.Directions.get(i));
                    }
                    sb.append('!');
                    if (nextchar == target.length())
                    {
                        return sb.toString();
                    }

                    for (int i = 0; i < visited.length; i++)
                    { 
                        // reset visited to false for all.
                        visited[i] = false;
                    }

                    targetChar = target.charAt(nextchar++);
                    q.add(new BoardPoint(point.X, point.Y));
                    continue;
                }

                for (int i = 0; i < directions.length; i++)
                {
                    int x = point.X + directions[i][0];
                    int y = point.Y + directions[i][1];
                    System.out.println("x,y is "+x + "," + y + " char is "+charDirections[i]);
                    if (x >= 0 && x < m && y >= 0 && y < board[x].length && !visited[board[x][y] - 'a'])
                    {
                        //board[x].length is because the last row will have less number of elements.

                        visited[board[x][y] - 'a'] = true;
                        
                        // var pairDirections = pair.Directions.Select(p => p).ToList();
                        List<Character> adjDirections = point.Directions.stream().collect(Collectors.toList());
                        adjDirections.add(charDirections[i]); // Indicate which way we're traversing
                        
                        // if(board[x][y]=='f'||board[x][y]=='g'||board[x][y]=='l')
                            // System.out.println("char is "+ board[x][y]+" directions "+point.Directions);
                        // For the target "leet", we will traverse down to f, char D will be added in the first iteration
                        // On second iteration, in f, char R will be added 
                        BoardPoint adjacent = new BoardPoint(x, y, adjDirections);
                        q.add(adjacent);
                    }
                }
            }
            return null;
        }

        public static void main(String[] args) {
            String target = "leet";
            AlphabetBoardPath_MultiBFS obj = new AlphabetBoardPath_MultiBFS();
            System.out.println(obj.AlphabetBoardPath_MultiBFS(target));
        }

        }

    static class TilingRectangeWithFewestSquares {
        /**
         * Given a rectangle of size n x m, find the minimum number of integer-sided
         * squares that tile the rectangle.
         
         * https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/
         * 
         * 
         * Example 1:
         * 
         * 
         * 
         * Input: n = 2, m = 3 Output: 3 Explanation: 3 squares are necessary to cover
         * the rectangle. 2 (squares of 1x1) 1 (square of 2x2) Example 2:
         * 
         * 
         * 
         * Input: n = 5, m = 8 Output: 5 Example 3:
         * 
         * 
         * 
         * Input: n = 11, m = 13 Output: 6
         */
        /**
         * Solution: Refer this.
         * https://medium.com/@poojagl85/1240-tiling-a-rectangle-with-the-fewest-squares-leetcode-c6e6b348d42e
         * 
         * 
         * Constraints 1 <=n, m <=13 Recursion
         * 
         * Bigger problem : to tile a rectangle with sides n X m,
         * 
         * Smaller problems : Case 1:
         * 
         * Sub-problem 1: Tiling a rectangle with sides n X m-x.
         * 
         * Sub-problem 2: Tiling a rectangle with sides n-x X x. Recursion Result 1=
         * result by sub-problem 1 + result by sub-problem 2 + 1(Self -work ).
         * 
         * 
         * Case 2 :
         * 
         * Sub-problem 1: Tiling a rectangle with sides x X m-x.
         * 
         * Sub-problem 2: Tiling a rectangle with sides n-x X m.
         * 
         * Recursion Result 2= result by sub-problem 1 + result by sub-problem 2 +
         * 1(Self -work ).
         * 
         * Actual result by recursion = Minimum (recursion result 1 , recursion result
         * 2).
         * 
         * Now the question is what values can x take ? So, x can take any values from 1
         * to Minimum(n, m ) . What if n is equal to m ? return 1 (why ?)
         * 
         * Now you must be thinking what about the third test case ? Under the given
         * constraints 11 X 13 rectangle is the only special case (hardcode it).
         */
        public static int tilingRectangeDP(int n, int m)
        {
            int[][] strg = new int[n+1][m+1];

            for(int i=1;i<=n;i++){
                for (int j=1;j<=m;j++)
                {
                    if(n==m){
                        strg[i][j] = 1;
                        continue;
                    }
                    if(i==13 && j==11)
                    {
                        strg[i][j] = 6;
                        continue;
                    }
                    if(i==11 && j==13){
                        strg[i][j] = 6;
                        continue;
                    }
                    int rr1 = Integer.MAX_VALUE;
                    int rr2 = Integer.MAX_VALUE;
                    int min = Integer.MAX_VALUE;
                    for(int x=1; x<=Math.min(i,j);x++)
                    {
                        if(j-x<0 || i-x<0)
                            break;
                        rr1 = strg[i][j-x] + strg[i-x][x];
                        rr2 = strg[i-x][j] + strg[x][j-x];
                        min = Math.min(rr1, Math.min(rr2, min));
                    }
                    strg[i][j] = min+1;
                }
            }
        
            return strg[n][m];
        }

        public static void main(String[] args) {
            System.out.println(tilingRectangeDP(5, 8));
            System.out.println(tilingRectangeDP(2, 3));
        }
    }

    static class CountSquareSubmatricesWithAll1s_DP{
        /**
         * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.
            Example 1:

            Input: matrix =
            [
            [0,1,1,1],
            [1,1,1,1],
            [0,1,1,1]
            ]
            Output: 15
            Explanation: 
            There are 10 squares of side 1.
            There are 4 squares of side 2.
            There is  1 square of side 3.
            Total number of squares = 10 + 4 + 1 = 15.
            Example 2:

            Input: matrix = 
            [
            [1,0,1],
            [1,1,0],
            [1,1,0]
            ]
            Output: 7
            Explanation: 
            There are 6 squares of side 1.  
            There is 1 square of side 2. 
            Total number of squares = 6 + 1 = 7.
         */
        /**
         * dp[i][j] means the size of biggest square with A[i][j] as bottom-right
         * corner. dp[i][j] also means the number of squares with A[i][j] as
         * bottom-right corner.
         * 
         * If A[i][j] == 0, no possible square. If A[i][j] == 1, we compare the size of
         * square dp[i-1][j-1], dp[i-1][j] and dp[i][j-1]. min(dp[i-1][j-1], dp[i-1][j],
         * dp[i][j-1]) + 1 is the maximum size of square that we can find.
         */
        public int countSquares(int[][] A) {
            int res = 0;
            for (int i = 0; i < A.length; ++i) {
                for (int j = 0; j < A[0].length; ++j) {
                    if (A[i][j] > 0 && i > 0 && j > 0) {
                        A[i][j] = Math.min(A[i - 1][j - 1], Math.min(A[i - 1][j], A[i][j - 1])) + 1;
                    }
                    res += A[i][j];// This is same as finding max square, except that in this line, we sum it to
                                   // result.
                }
            }
            return res;
        }
    }

    static class ShortestPathInMatrixWithObstacleElimination_BFS {
        /**
         * Given a m * n grid, where each cell is either 0 (empty) or 1 (obstacle). 
         * In one step, you can move up, down, left or right from and to an empty cell.

            Return the minimum number of steps to walk from the upper left corner (0, 0) 
            to the lower right corner (m-1, n-1) given that you can eliminate at most k obstacles. 
            If it is not possible to find such walk return -1.

            

            Example 1:

            Input: 
            grid = 
            [[0,0,0],
            [1,1,0],
            [0,0,0],
            [0,1,1],
            [0,0,0]], 
            k = 1
            Output: 6
            Explanation: 
            The shortest path without eliminating any obstacle is 10. 
            The shortest path with one obstacle elimination at position (3,2) is 6. 
            Such path is (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
            

            Example 2:

            Input: 
            grid = 
            [[0,1,1],
            [1,1,1],
            [1,0,0]], 
            k = 1
            Output: -1
            Explanation: 
            We need to eliminate at least two obstacles to find such a walk.
         */
        public int shortestPath(int[][] grid, int k) {
            int step = 0;
            int m = grid.length;
            int n = grid[0].length;
            int[][] DIRS = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
            int[][] seen = new int[m][n]; // min obstacles elimination from (0,0) to (x, y)
            for (int i = 0; i < m; i++) {
                Arrays.fill(seen[i], Integer.MAX_VALUE);
            }
            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[] { 0, 0, 0 });
            seen[0][0] = 0;//Seen is not the visited array, it stores the min obstacles to reach a[i][J].
            // Number of obstacles you can eliminate should be less than k as well.
            while (!q.isEmpty()) {
                int size = q.size();
                while (size-- > 0) {
                    int[] cur = q.poll();
                    if (cur[0] == m - 1 && cur[1] == n - 1) {
                        return step;
                    }
                    for (int[] dir : DIRS) {
                        int x = dir[0] + cur[0];
                        int y = dir[1] + cur[1];
                        if (x < 0 || x >= m || y < 0 || y >= n) {
                            continue;
                        }
                        int o = grid[x][y] + cur[2];
                        if (o >= seen[x][y] || o > k) {
                            // If the number of obstance in the path > k, skip.
                            // The obstacle > min obstacle found so far, skip adding to the queue
                            continue;
                        }
                        seen[x][y] = o;
                        q.offer(new int[] { x, y, o });
                    }
                }
                ++step;
            }
            return -1;
        } 
    
        public static void main(String[] args){
            int[][] grid = new int[][]
            {{0,0,0},
             {1,1,0},
             {0,0,0},
             {0,1,1},
             {0,0,0}}; 
            int k = 1;
            ShortestPathInMatrixWithObstacleElimination_BFS obj = new ShortestPathInMatrixWithObstacleElimination_BFS();
            System.out.println(obj.shortestPath(grid, k));
        }
    }

    static class CountRectangleSubmatricesWithAll1s_LinearAndStack {
        /**
         * Given a rows * columns matrix mat of ones and zeros, return how many submatrices have all ones.
            Example 1:

            Input: mat = [
                        [1,0,1],
                        [1,1,0],
                        [1,1,0]
                        ]
            Output: 13
            Explanation:
            There are 6 rectangles of side 1x1.
            There are 2 rectangles of side 1x2.
            There are 3 rectangles of side 2x1.
            There is 1 rectangle of side 2x2. 
            There is 1 rectangle of side 3x1.
            Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
            Example 2:

            Input: mat = [[0,1,1,0],
                        [0,1,1,1],
                        [1,1,1,0]]
            Output: 24
            Explanation:
            There are 8 rectangles of side 1x1.
            There are 5 rectangles of side 1x2.
            There are 2 rectangles of side 1x3. 
            There are 4 rectangles of side 2x1.
            There are 2 rectangles of side 2x2. 
            There are 2 rectangles of side 3x1. 
            There is 1 rectangle of side 3x2. 
            Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
            Example 3:

            Input: mat = [[1,1,1,1,1,1]]
            Output: 21
            Example 4:

            Input: mat = [[1,0,1],[0,1,0],[1,0,1]]
            Output: 5
         */
        //This naive approach is same as finding max sum rectangle in a matrix where row wise sum is calculated,
        //where here column wise 1's are calculated.
        public static int numSubmat(int[][] mat) {
            /**
             * 
             *  Example: 
             * [[1,0,1],
             * [1,1,0],
             * [1,1,0]]
             * 
             * up -> 0 down -> 0
               h= [1 0 1]
               res= 2
                up -> 0 down -> 1
               h= 1 0 0 
               res= 3
                up -> 0 down -> 2
               h= 1 0 0 
               res= 4
                up -> 1 down -> 1
               h= 1 1 0  [Why 7? refer countOneRow method] length matrix: [1 2 0] = sum of that is 3.
               res= 7
                up -> 1 down -> 2
               h= 1 1 0 
               res= 10
                up -> 2 down -> 2
               h= 1 1 0 
               res = 13
             * 
             * 
             * Algorithm:
             * 1. For every "up" row, find all the submatrices/rectangles of length 1, 2, 3. which start with it.
             * 2. For example, when up is 0, down is 0, number of matrices which are of length 1 that start with up'th row is 2
             * 3. when up is 0, down is 1, number of matrices which are of length 2 that start with up'th row is 1.
             * 4. when up is 0, down is 2, number of matrices which are of length 3 that start with up'th row is 1.
             * Total matrices which start with up'th row  is 4.
             * Similarly we have to find the submatrices for all the rows
             */
        
             /**This problem can be solved in here
              * 
            1074. Number of Submatrices That Sum to Target 
            85. Maximal Rectangle (Using DP and Stack - refer this file)
            363. Max Sum of Rectangle No Larger Than K (Refer dynamic programming java file for this)
              */
            int M = mat.length, N = mat[0].length;
        
            int res = 0;
            for (int up = 0; up < M; ++up) {
                int[] h = new int[N];
                Arrays.fill(h, 1);
                for (int down = up; down < M; ++down) {
                    for (int k = 0; k < N; ++k) h[k] &= mat[down][k];
                    System.out.println("up -> "+up+" down -> "+down);
                    printOneRow(h);
                    res += countOneRow(h);
                    System.out.println(res);
                }
            }
        
            return res;
        }
        private static void printOneRow(int[] A)
        {
            for (int i = 0; i < A.length; ++i) {
                System.out.print(A[i]+" ");
            }
            System.out.println();
            // System.out.println(new ArrayList(Arrays.asList(A)));
        }
        private static int countOneRow(int[] A) {
            // Imagine you have an one-dimension array, how to count number of all 1 submatrices (size is 1 * X)
            // [1 1 1] = number of submatrices
            // length matrix will be [1 2 3] = [6], number of submatrices end at ith index is sum of all submatrices before ith index + 1
            int res = 0, length = 0;
            for (int i = 0; i < A.length; ++i) {
                length = (A[i] == 0 ? 0 : length + 1);
                // System.out.println("length "+ length);
                res += length;
            }
            return res;
        }

        public static int numSubmatStack(int[][] mat) {
        
            int M = mat.length, N = mat[0].length;
        
            int res = 0;
        
            int[] h = new int[N];
            // The below method returns outputs
            /**
             * 
             * Example: [[1,0,1], [1,1,0], [1,1,0]]
             * 
             * 
             * 1 0 1 for i=0 2 1 0, i=1 3 2 0, i=2
             * 
             * The array h at a column j represents number of continous 1 in column j from
             * row i up to row 0. The H[] matrix as you can above contains the number of
             * matrices which contain jth column as the right border from top down. For
             * example, a[2][0]=3 means number of matrixes that can end with a[0][2] is 3.
             * How? a[1...2][0] is one matrix, a[1,2][0] is another matrix, a[3][0] is the
             * last matrix.
             * 
             * Now we've computed the matrixes from top down. We will need to find the
             * matrices that can be formed from left to right at ith column. How we can do
             * that? Use finding largest rectange in histogram in H[] to find the number of
             * common matrix between preIndex (left) and i (right), where preIndex is
             * smaller or less than current index value.
             * 
             * 
             * 1 0 1
             * 
             * Sum[0] = 1*(1) stack is empty NOTE: SUM[i] means the number of submatrices
             * with the column "i" as the right border. 1 - only one matrix can contain this
             * column as the right border. Sum[1] = 0*(2) stack is empty 0 Preindex Sum[1]
             * is 0, stack is not empty, preIndex is 1, whose value is 0 less than the
             * currIndex value 1. Number of matrices that can be formed with preIndex is
             * arr[i]*(i-preindex)= 1*(2-1) = 1
             * 
             * Sum[2] = 1*(2-1) 1
             * 
             * Result of 0th row is 2 2 1 0
             * 
             * Sum[0] = 2*(1) stack is empty 2 Sum[1] = 1*(2) stack is empty 2 Sum[2] =
             * 0*(3) stack is empty 0
             * 
             * Result of 1th row is 4
             * 
             * 3 2 0
             * 
             * Sum[0] = 3*(1) stack is empty 3 Sum[1] = 2*(2) stack is empty 4: 4
             * submatrices end with the column 1 in row 2 Sum[2] = 0*(3) stack is empty 0
             * 
             * Result of 2th row is 7 13
             * 
             * 
             * Official explanation: O(M*N)
             * 
             * Now in the code, the h[j] means: number of continius 1 in column j from row i
             * up to row 0. By using mono-stack, what we want to achieve is to find the
             * first previous index "preIndex", whose number of continuous 1 is less than
             * current column index i. And the value of index between preIndex and i are all
             * equal or larger than index i. So it can form a big sub-matrix.
             * 
             * Note: sum[i] means the number of submatrices with the column "i" as the right
             * border.
             * 
             * If stack is empty, meaning: all previous columns has more/equal ones than
             * current column. So, the number of matrixs can form is simply A[i] * (i + 1);
             * (0-index) If stack is not empty, meaning: there is a shorter column which
             * breaks our road. Now, the number of matrixs can form is sum[i] += A[i] * (i -
             * preIndex). And plus, we can form a longer submatrices with that previou
             * shorter column sum[preIndex]. The best way to understand is to draw a graph.
             */
            for (int i = 0; i < M; ++i) {
                for (int j = 0; j < N; ++j) {
                    h[j] = (mat[i][j] == 0 ? 0 : h[j] + 1);
                }
                printOneRow(h);
                int helperResult = helper(h);
                System.out.println("Result of " + i + "th row is " + helperResult);
                res += helperResult;
            }
        
            return res;
        }
        
        private static int helper(int[] A) {
        
            int[] sum = new int[A.length];
            Stack<Integer> stack = new Stack<>();
        
            System.out.println();

            for (int i = 0; i < A.length; ++i) {
        
                while (!stack.isEmpty() && A[stack.peek()] >= A[i]) stack.pop();
        
                if (!stack.isEmpty()) {
                    int preIndex = stack.peek();
                    sum[i] = sum[preIndex];
                    sum[i] += A[i] * (i - preIndex);
                    System.out.println("Preindex Sum[" + preIndex + "] is " + sum[preIndex]);
                    System.out.println("Sum[" + i + "] = " + A[i] + "*(" + i + "-" + preIndex + ")");
                    System.out.println(sum[i]);
                } else {
                    sum[i] = A[i] * (i + 1);
                    System.out.println("Sum[" + i + "] = " + A[i] + "*(" + (i + 1) + ")");
                    System.out.println(sum[i]);
                }
        
                stack.push(i);
            }
            System.out.println();
        
            int res = 0;
            for (int s : sum) res += s;
        
            return res;
        }

        public static void main(String[] args){
            int[][] mat = new int[][]{{1,0,1},
            {1,1,0},
            {1,1,0}};
            System.out.println(numSubmatStack(mat));
            // System.out.println(countOneRow(new int[]{1, 1, 1}));

        }
    }

    static class NumberOfSubMatricesThatSumToTarget {
        /**
         * Given a matrix and a target, return the number of non-empty submatrices that
         * sum to target.
         * 
         * A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x
         * <= x2 and y1 <= y <= y2.
         * 
         * Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different if
         * they have some coordinate that is different: for example, if x1 != x1'.
         * 
         * 
         * 
         * Example 1:
         * 
         * 
         * Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0 Output: 4 Explanation:
         * The four 1x1 submatrices that only contain 0. Example 2:
         * 
         * Input: matrix = [[1,-1],[-1,1]], target = 0 Output: 5 Explanation: The two
         * 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.
         * Example 3:
         * 
         * Input: matrix = [[904]], target = 0 Output: 0
         */
        /**
         * 
         * Intuition
            Preaquis: 560. Subarray Sum Equals K
            Find the Subarray with Target Sum in linear time.


            Explanation
            For each row, calculate the prefix sum.
            For each pair of columns,
            calculate the accumulated sum of rows.
            Now this problem is same to, "Find the Subarray with Target Sum".


            Complexity
            Time O(mnn)
            Space O(m)
         */
        /**
         * 
         * 1. calculate prefix sum for each row in a matrix.
         * 
         * 2. Now we will need to find the sum of matrices from ith column to jth column, for that, 
         * borrow the above principle:  for every possible range between two columns, 
         * accumulate the prefix sum of submatrices that can be formed between these two columns 
         * by adding up the sum of values between these two columns for every row.
         * 
         * This is also used in finding the max sum rectangle in DP.java
         * 
         * for (int leftCols = 0; leftCols < n; leftCols++) {
                for (int rightCols = leftCols; rightCols < n; rightCols++) {
                    counter.clear();
                    counter.put(0, 1);
                    int cur = 0;
                    for (int i = 0; i < m; i++) {
                        cur += A[i][rightCols] - (leftCols > 0 ? A[i][leftCols - 1] : 0);
                        res += counter.getOrDefault(cur - target, 0);
                        counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                    }
                }
            }
         * 
         * 3. To understand what this triple-for loop does, let us try an example, assume leftCol = 1 and rightCol = 3, then for this part of code:
                * Map<Integer, Integer> counter = new HashMap<>();
                * counter.put(0, 1);
                * int cur = 0;
                    for (int i = 0; i < m; i++) {
                        cur += A[i][rightCols] - (leftCols > 0 ? A[i][leftCols - 1] : 0);
                        res += counter.getOrDefault(cur - target, 0);
                        counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                    }

             I will break this piece of code into two major part:

                Map<Integer, Integer> counter = new HashMap<>();
                counter.put(0, 1);
                3.1 key of this hashmap present the unique value of all possible prefix sum that we've seen so far
                3.2 value of this hashmap represents the count (number of appearances) of each prefix sum value we've seen so far
                3.3. an empty submatrix trivially has a sum of 0

                for (int i = 0; i < m; i++) {
                        cur += A[i][rightCols] - (leftCols > 0 ? A[i][leftCols - 1] : 0);
                        res += counter.getOrDefault(cur - target, 0);
                        counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                }
            Here we are actually calculating the prefix sum of submatrices which has column 1, 2, and 3, 
            by adding up the sum of matrix[0][1...3], matrix[1][1...3] ... matrix[m-1][1...3] row by row, 
            starting from the first row (row 0). 
            The way of getting the number of submatrices whose sum equals to K uses the same idea of 
            Subarray Sum Equals K so I won't repeat it again.
         * 
         */
        public int numSubmatrixSumTarget(int[][] A, int target) {
            int res = 0, m = A.length, n = A[0].length;
            for (int i = 0; i < m; i++)
                for (int j = 1; j < n; j++)
                    A[i][j] += A[i][j - 1];
            Map<Integer, Integer> counter = new HashMap<>();
            for (int leftCols = 0; leftCols < n; leftCols++) {
                for (int rightCols = leftCols; rightCols < n; rightCols++) {
                    counter.clear();
                    counter.put(0, 1);
                    int cur = 0;
                    for (int i = 0; i < m; i++) {
                        cur += A[i][rightCols] - (leftCols > 0 ? A[i][leftCols - 1] : 0);
                        res += counter.getOrDefault(cur - target, 0);
                        counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                    }
                }
            }
            return res;
        }
    }

    static class MostSimilarPathInGraph_using_DFS_and_DP {
        /**
         * Key:
         * 1) use visited 2D array to store distance for a node to reach it's target indices. 
         * 2) Track 2D array: nextChoiceForMin for a node to go to next node which guarantees minimal edit distance.
         * 
         */
        /**
         * We have n cities and m bi-directional roads where roads[i] = [ai, bi]
         * connects city ai with city bi. Each city has a name consisting of exactly 3
         * upper-case English letters given in the string array names. Starting at any
         * city x, you can reach any city y where y != x (i.e. the cities and the roads
         * are forming an undirected connected graph).
         * 
         * You will be given a string array targetPath. You should find a path in the
         * graph of the same length and with the minimum edit distance to targetPath.
         * 
         * You need to return the order of the nodes in the path with the minimum edit
         * distance, The path should be of the same length of targetPath and should be
         * valid (i.e. there should be a direct road between ans[i] and ans[i + 1]). If
         * there are multiple answers return any one of them.
         * 
         * The edit distance is defined as follows:
         * 
         * 
         * 
         * Follow-up: If each node can be visited only once in the path, What should you
         * change in your solution?
         * 
         *//** 

            Example 1:


            Input: n = 5, roads = [[0,2],[0,3],[1,2],[1,3],[1,4],[2,4]], names = ["ATL","PEK","LAX","DXB","HND"], targetPath = ["ATL","DXB","HND","LAX"]
            Output: [0,2,4,2]
            Explanation: [0,2,4,2], [0,3,0,2] and [0,3,1,2] are accepted answers.
            [0,2,4,2] is equivalent to ["ATL","LAX","HND","LAX"] which has edit distance = 1 with targetPath.
            [0,3,0,2] is equivalent to ["ATL","DXB","ATL","LAX"] which has edit distance = 1 with targetPath.
            [0,3,1,2] is equivalent to ["ATL","DXB","PEK","LAX"] which has edit distance = 1 with targetPath.
            Example 2:


            Input: n = 4, roads = [[1,0],[2,0],[3,0],[2,1],[3,1],[3,2]], names = ["ATL","PEK","LAX","DXB"], targetPath = ["ABC","DEF","GHI","JKL","MNO","PQR","STU","VWX"]
            Output: [0,1,0,1,0,1,0,1]
            Explanation: Any path in this graph has edit distance = 8 with targetPath.
            Example 3:



            Input: n = 6, roads = [[0,1],[1,2],[2,3],[3,4],[4,5]], names = ["ATL","PEK","LAX","ATL","DXB","HND"], targetPath = ["ATL","DXB","HND","DXB","ATL","LAX","PEK"]
            Output: [3,4,5,4,3,2,1]
            Explanation: [3,4,5,4,3,2,1] is the only path with edit distance = 0 with targetPath.
            It's equivalent to ["ATL","DXB","HND","DXB","ATL","LAX","PEK"]
         */

         /** Explanation: 
          *  Target path is given which is incorrect, you will need to find the possible traversals in the graph
            such that you make minimum edits to the target path.
          * 

          Graph 

             ATL [0]  -----------  DXB [3]
             |                       |
             |                       | 
             LAX, 2   -----------  PEK [1]
               \                  /
                 \              /
                   \          /
                     \      /
                      HND [4] 

          Indexes     0     1     2     3    4
          names:    ["ATL","PEK","LAX","DXB","HND"]
          
          Indexes        0    1    2    3
          Target path:  [ATL, DXB, HND, LAX]

          DFS
         1. Do DFS at any starting position in the vertex. 
         2. Let's say ATL is the starting position
            [ATL, ] You ask the current node am I in, is it different from the index 0 of target path?
            No, so, don't add any cost.
            [ATL, DXB], Don't add any cost
            [ATL, DXB, ATL], Add cost +1
            [ATL, DXB, ATL, LAX], Don't add any cost.
            So, a traversal is found with min 1.

        3. We will have to calculate the min distance and track which path returned the min distance.
          */

        /**
         * 
         * DFS Traversal
         * 
         * ATL at idx=0, is it different? no, so add to cost + 0 = 0
         *     DXB at idx=1 is it different? no, so add to cost + 0 = 0
         *         ATL at idx=2 is it different? yes, so add to cost + 1 = 1
         *             DXB at idx=3 is it different? yes, so add to cost + 1 AND return = 2
         *             LAX at idx=3 is it different? no, so add to cost + 0 AND return = 1
         *             BACK TRACK: WE'VE EXHAUSTED THE TARGET LENGTH
         *         PEK at idx=2 is it different? yes, so add to cost + 1 = 1
         *             DXB at idx=3 SOLVED ALREADY RETURN PREV 
         *             LAX at idx=3 SOLVED ALREADY RETURN PREV
         *             HND at idx=3 is it different? yes, so add to cost+1 AND RETURN
         *      LAX at idx=1 is it different? yes, so add to cost+1
         *          ATL at idx=2, SOLVED ALREADY RETURN PREV 
         *          PEK at idx=2, SOLVED ALREADY RETURN PREV
         *          HND at idx=2 is it different? no, so add to cost + 0
         *              PEK at idx=3 is it different? yes, so add to cost +
         *              LAX at idx=3 SOLVED ALREADY RETURN PREV
         */
          List<List<Integer>> adjMatrix;
         String[] names;
         String[] targetPath;
         int[][] visited; 
         // stores the min distance for a node to reach indexes [0, 1, 2, 3] in the target path.
         // It also works as visited array, 
         // so for example, DXB (namesIdx=1) -> targetPahtIdx=3, when it's already computed/visited, it's returned.
         int[][] nextChoiceForMin;
         // To find the next min edit distance choice for the node to reach indexes [0, 1, 2, 3] in the target path
         // for example, ["ATL","DXB","ATL","LAX"] has one edit distance
         // This 2D array will store the following: 
         // ATL (namesIdx = 0) -> targetPathIdx (1) = DXB (namesIdx = 3), 
         // note it's not the target path index - 1,2,3 we store, but the current path idx which is 0,1,2, next optimal choice of ATL at its currentIndex is stored.
         // DXB (nameIdx = 3) -> targetPathIdx (2) = ATL (namesIdx = 1)
         // ATL (namesIdx = 0) -> targetPathIdx (3) = LAX (namesIdx = 2)
         // So for the targetpath [0...3], the min distance traversal is ATL -> DXB -> ATL -> LAX

         // stores the next best choice for minimum edit distance
         public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
             // INITIALIZE VARIABLES
             this.visited = new int[names.length][targetPath.length];
             this.nextChoiceForMin = new int[names.length][targetPath.length];
             this.targetPath = targetPath;
             this.names = names;
             this.adjMatrix = new ArrayList<List<Integer>>();

             for (int[] x : visited)
                 Arrays.fill(x, -1);

             // Build adjacency matrix
             for (int i = 0; i < n; i++)
                 adjMatrix.add(new ArrayList<Integer>());
             for (int[] road : roads) {
                 adjMatrix.get(road[0]).add(road[1]);
                 adjMatrix.get(road[1]).add(road[0]);
             }

             // From each node, calculate min cost and the city that gave the min cost
             int min = Integer.MAX_VALUE;
             int start = 0;
             for (int i = 0; i < names.length; i++) {
                 int resp = dfs(i, 0);
                 if (resp < min) {
                     min = resp;
                     start = i;
                 }
             }

             // Build the answer based on what's the best next choice.
             List<Integer> ans = new ArrayList<>();
             while (ans.size() < targetPath.length) {
                 ans.add(start);
                 start = nextChoiceForMin[start][ans.size() - 1];
                 /**
                // ATL (namesIdx = 0) -> currPathIndex (0) = DXB (namesIdx = 3)
                // DXB (nameIdx = 3) -> currPathIndex (1) = ATL (namesIdx = 1)
                // ATL (namesIdx = 0) -> currPathIndex (2) = LAX (namesIdx = 2)
                // LAX (namesIdx=2) -> currPathIndex (3) = NONE. [END]
                  */
             }
             return ans;
         }

         public int dfs(int namesIdx, int currPathIdx) {
             // If we visited it already, return the previous count
             if (visited[namesIdx][currPathIdx] != -1)
             //If the node already covered the target path path index, return it.
                 return visited[namesIdx][currPathIdx];

             // if its different, add 1 else add 0
             int editDist = (names[namesIdx].equals(targetPath[currPathIdx])) ? 0 : 1; // add the cost.

             // If we filled the path, we are done
             if (currPathIdx == targetPath.length - 1) // we shouldn't do the traversal when the currPath reached the targetPath length.
                 return editDist;

             int min = Integer.MAX_VALUE;
             for (int neighbor : adjMatrix.get(namesIdx)) { // Adjacency traversal
                 int neighborCost = dfs(neighbor, currPathIdx + 1);// Go to neighbor with currPathIdx + 1
                 if (neighborCost < min) {
                     min = neighborCost; // save the min cost.
                     nextChoiceForMin[namesIdx][currPathIdx] = neighbor; 
                     // Store the next choice for current index as neighbor.
                 }
             }

             editDist += min; // update our edit distance
             visited[namesIdx][currPathIdx] = editDist; // save our min edit distance for the index to navigate to next in its current index.
             return editDist;// return our edit distance
         }

         public static void main(String[] args){
             int n = 5;
             int[][] roads = new int[][]{{0,2},{0,3},{1,2},{1,3},{1,4},{2,4}};
             String[] names = new String[]{"ATL","PEK","LAX","DXB","HND"};
             String[] targetPath = new String[]{"ATL","DXB","HND","LAX"};
             MostSimilarPathInGraph_using_DFS_and_DP object = new MostSimilarPathInGraph_using_DFS_and_DP();
             System.out.println(object.mostSimilar(n, roads, names, targetPath));
         }
    }

    static class CollectMaxCherriesOrPointsByRobotUsingTwoTraversalsFromTopToBottom {
        /**
         * In this part, we will explain how to think of this approach step by step.
         * 
         * If you are only interested in the pure algorithm, you can jump to the
         * algorithm part.
         * 
         * We need to move two robots! Note that the order of moving robot1 or robot2
         * does not matter since it would not impact the cherries we can pick. The
         * number of cherries we can pick only depends on the tracks of our robots.
         * 
         * Therefore, we can move the robot1 and robot2 in any order we want. We aim to
         * apply DP, so we are looking for an order that suitable for DP.
         * 
         * Let's try a few possible moving orders.
         * 
         * Can we move robot1 firstly to the bottom row, and then move robot2?
         * 
         * Maybe not. In this case, the movement of robot1 will impact the movement of
         * robot2. In other words, the optimal track of robot2 depends on the track of
         * robot1. If we want to apply DP, we need to record the whole track of robot1
         * as the state. The number of sub-problems is too much.
         * 
         * In fact, in any case, when we move one robot several steps earlier than the
         * other, the movement of the first robot will impact the movement of the second
         * robot.
         * 
         * Unless we move them synchronously (i.e., move one step of robot1 and robot2
         * at the same time).
         * 
         * Let's define the DP state as (row1, col1, row2, col2), where (row1, col1)
         * represents the location of robot1, and (row2, col2) represents the location
         * of robot2.
         * 
         * If we move them synchronously, robot1 and robot2 will always on the same row.
         * Therefore, row1 == row2.
         * 
         * Let row = row1. The DP state is simplified to (row, col1, col2), where (row,
         * col1) represents the location of robot1, and (row, col2) represents the
         * location of robot2.
         * 
         * OK, time to define the DP function.
         * 
         * Since it's a top-down DP approach, we try to solve the problem with the DP
         * function. Check approach 2 for DP array (bottom-up).
         * 
         * Let dp(row, col1, col2) return the maximum cherries we can pick if robot1
         * starts at (row, col1) and robot2 starts at (row, col2).
         * 
         * You can try changing different dp meaning to yield some other similar
         * approaches. For example, let dp(row, col1, col2) mean the maximum cherries we
         * can pick if robot1 ends at (row, col1) and robot2 ends at (row, col2).
         * 
         * The base cases are that robot1 and robot2 both start at the bottom line. In
         * this case, they do not need to move. All we need to do is to collect the
         * cherries at current cells. Remember not to double count if robot1 and robot2
         * are at exactly the same cell.
         * 
         * In other cases, we need to add the maximum cherries robots can pick in the
         * future. Here comes the transition function.
         * 
         * Since we move robots synchronously, and each robot has three different
         * movements for one step, we totally have 3*3 = 93∗3=9 possible movements for
         * two robots:
         * 
                    *   ROBOT1 | ROBOT2
            ------------------------
            LEFT DOWN |  LEFT DOWN
            LEFT DOWN |       DOWN
            LEFT DOWN | RIGHT DOWN
                DOWN |  LEFT DOWN
                DOWN |       DOWN
                DOWN | RIGHT DOWN
            RIGHT DOWN |  LEFT DOWN
            RIGHT DOWN |       DOWN  
            RIGHT DOWN | RIGHT DOWN  
         * 
         * The maximum cherries robots can pick in the future would be the max of those
         * 9 movements, which is the maximum of dp(row+1, new_col1, new_col2), where
         * new_col1 can be col1, col1+1, or col1-1, and new_col2 can be col2, col2+1, or
         * col2-1.
         * 
         * Remember to use a map or an array to store the results of our dp function to
         * prevent redundant calculating.
         * 
         * Algorithm
         * 
         * Define a dp function that takes three integers row, col1, and col2 as input.
         * 
         * (row, col1) represents the location of robot1, and (row, col2) represents the
         * location of robot2.
         * 
         * The dp function returns the maximum cherries we can pick if robot1 starts at
         * (row, col1) and robot2 starts at (row, col2).
         * 
         * In the dp function:
         * 
         * Collect the cherry at (row, col1) and (row, col2). Do not double count if
         * col1 == col2.
         * 
         * If we do not reach the last row, we need to add the maximum cherries we can
         * pick in the future.
         * 
         * The maximum cherries we can pick in the future is the maximum of dp(row+1,
         * new_col1, new_col2), where new_col1 can be col1, col1+1, or col1-1, and
         * new_col2 can be col2, col2+1, or col2-1.
         * 
         * Return the total cherries we can pick.
         * 
         * Finally, return dp(row=0, col1=0, col2=last_column) in the main function.
         */
        public int cherryPickup(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            int[][][] dpCache = new int[m][n][n];
            // initial all elements to -1 to mark unseen
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        dpCache[i][j][k] = -1;
                    }
                }
            }
            return dp(0, 0, n - 1, grid, dpCache);
        }
    
        private int dp(int row, int col1, int col2, int[][] grid, int[][][] dpCache) {
            if (col1 < 0 || col1 >= grid[0].length || col2 < 0 || col2 >= grid[0].length) {
                return 0;
            }
            // check cache
            if (dpCache[row][col1][col2] != -1) {
                return dpCache[row][col1][col2];
            }
            // current cell
            int result = 0;
            result += grid[row][col1];
            if (col1 != col2) {
                result += grid[row][col2];
            }
            // transition
            if (row != grid.length - 1) {
                int max = 0;
                for (int newCol1 = col1 - 1; newCol1 <= col1 + 1; newCol1++) {
                    for (int newCol2 = col2 - 1; newCol2 <= col2 + 1; newCol2++) {
                        max = Math.max(max, dp(row + 1, newCol1, newCol2, grid, dpCache));
                    }
                }
                result += max;
            }
    
            dpCache[row][col1][col2] = result;
            return result;
        }
    }

    static class TrappingRainWater {
        /**
         * Given n non-negative integers representing an elevation map where the width
         * of each bar is 1, compute how much water it can trap after raining.
         * 
         * Example 1:
         * 
         * 
         * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1] Output: 6 Explanation: The above
         * elevation map (black section) is represented by array
         * [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section)
         * are being trapped. Example 2:
         * 
         * Input: height = [4,2,0,3,2,5] Output: 9
         */
        /**
         * Solution:
         * 
         * Do as directed in question. For each element in the array, we find the
         * maximum level of water it can trap after the rain, which is equal to the
         * minimum of maximum height of bars on both the sides minus its own height.
         * 
         * Algorithm
         * 
         * 1. Initialize ans=0
         * 2. Iterate the array from left to right
         *     2.1. Initalize left_max = 0 and right_max = 0
         *     2.2. Iterate from the current element to the beginning of array updating:
         *          left_max = max(left_max, height[j])
         *     2.3. Iterate from the current element to the end of array updating:
         *          right_max = max(right_max, height[j])
         *     2.4. Add min(left_max, right_max) - height[i] to ans.
         * 
         * Time complexity: O(n2), for each element of array, we iterate the left and right parts
         * Space complexity: O(1)
         * 
         * DP
         * 
         * 1. Find max height of bar from left end upto an index i in the array left_max
         * 2. Find max height of bar from the right end upto an index i in the array right_max
         * 3. Iterate over the height array and update ans:
         *    add min(left_max[i], right_max[i]) - height[i] to ans.
         */
        int trapWater(int[] height) {
            if (height.length == 0)
                return 0;
            int ans = 0;
            int size = height.length;
            int[] left_max = new int[size];
            int[] right_max = new int[size];
            left_max[0] = height[0];
            for (int i = 1; i < size; i++) {
                left_max[i] = Math.max(height[i], left_max[i - 1]);
            }
            right_max[size - 1] = height[size - 1];
            for (int i = size - 2; i >= 0; i--) {
                right_max[i] = Math.max(height[i], right_max[i + 1]);
            }
            for (int i = 1; i < size - 1; i++) {
                ans += Math.min(left_max[i], right_max[i]) - height[i];
            }
            return ans;
        }

        int trapWaterUsingStack(int[] height)
        {
            
            int ans = 0, current = 0;
            Stack<Integer> st = new Stack<>();
            while (current < height.length) {
                while (!st.empty() && height[current] > height[st.peek()]) {
                    int top = st.peek();
                    st.pop();
                    if (st.empty())
                        break;
                    int distance = current - st.peek() - 1;
                    int bounded_height = Math.min(height[current], height[st.peek()]) - height[top];
                    ans += distance * bounded_height;
                }
                st.push(current++);
            }
            return ans;
        }
        
        int trapWaterUsingTwoPointers(int[] height) {
            /**
             * As in Approach 2, instead of computing the left and right parts seperately,
             * we may think of some way to do it in one iteration. From the figure in
             * dynamic programming approach, notice that as long as
             * ""{right_max}[i]>""{left_max}[i] (from element 0 to 6), the water trapped
             * depends upon the left_max, and similar is the case when
             * ""{left_max}[i]>""{right_max}[i] (from element 8 to 11). So, we can say that
             * if there is a larger bar at one end (say right), we are assured that the
             * water trapped would be dependant on height of bar in current direction (from
             * left to right). As soon as we find the bar at other end (right) is smaller,
             * we start iterating in opposite direction (from right to left). We must
             * maintain ""{left_max} and ""{right_max} during the iteration, but now we can
             * do it in one iteration using 2 pointers, switching between the two.
             * 
             * Algorithm
             * 
             * Initialize ""{left}left pointer to 0 and ""{right}right pointer to size-1
             * 
             * While ""{left}< ""{right}left<right, do:
             *
             *  If ""{height[left]} is smaller than ""{height[right]} 
             * 
             * If height[left]≥left_max, update ""{left_max}left_max 
             * Else add left_max−height[left] to ans
             * Add 1 to left.
             * 
             * Else
             * 
             *  If height[right]≥right_max, update right_max
             * 
             *   Else add right_max−height[right] to ans
             * 
             *  Subtract 1 from right.
             */
            int left = 0, right = height.length - 1;
            int ans = 0;
            int left_max = 0, right_max = 0;
            while (left < right) {
                if (height[left] < height[right]) {
                    if (height[left] >= left_max) {
                        left_max = height[left];
                    } else {
                        ans += (left_max - height[left]);
                    }
                    ++left;
                } else {
                    if (height[right] >= right_max) {
                        right_max = height[right];
                    } else {
                        ans += (right_max - height[right]);
                    }
                    --right;
                }
            }
            return ans;
        }
    }

    static class Search2dMatrix {
        /**
         * Write an efficient algorithm that searches for a target value in an m x n
         * integer matrix. The matrix has the following properties:
         * 
         * Integers in each row are sorted in ascending from left to right. Integers in
         * each column are sorted in ascending from top to bottom.
         * 
         * 
         * Example 1:
         * 
         * 
         * Input: matrix =
         * [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]],
         * target = 5 Output: true
         */
        /**
         * We start search the matrix from top right corner, initialize the current
         * position to top right corner, if the target is greater than the value in
         * current position, then the target can not be in entire row of current
         * position because the row is sorted, if the target is less than the value in
         * current position, then the target can not in the entire column because the
         * column is sorted too. We can rule out one row or one column each time, so the
         * time complexity is O(m+n).
         * 
         */
        public boolean searchMatrix(int[][] matrix, int target) {
            if(matrix == null || matrix.length < 1 || matrix[0].length <1) {
                return false;
            }
            int col = matrix[0].length-1;
            int row = 0;
            while(col >= 0 && row <= matrix.length-1) {
                if(target == matrix[row][col]) {
                    return true;
                } else if(target < matrix[row][col]) {
                    col--;
                } else if(target > matrix[row][col]) {
                    row++;
                }
            }
            return false;
        }
    }

    static class CreateSpiralMatrixFromNOrGivenArray {
        /**
         * Given an array, the task is to form a spiral matrix 

        Examples: 

        Input:
        arr[] = { 1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16 };
        Output:
        1  2  3  4
        12 13 14  5
        11 16 15  6
        10  9  8  7

        Input:
        arr[] = { 1, 2, 3, 4, 5, 6,
                7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18 };
        Output:
        1   2  3  4  5 6 
        14 15 16 17 18 7 
        13 12 11 10  9 8

        Approach: This problem is just the reverse of this problem “Print a given matrix in spiral form“. The approach to do so is: 

            Traverse the given array and pick each element one by one.
            Fill each of this element in the spiral matrix order.
            Spiral matrix order is maintained with the help of 4 loops – left, right, top, and bottom.
            Each loop prints its corresponding row/column in the spiral matrix.
            Below is the implementation of the above approach:
         */
        static final int R = 3;
        static final int C = 6;

        // Function to form the spiral matrix
        static void formSpiralMatrix(int arr[], int mat[][]) {
            int top = 0, bottom = R - 1, left = 0, right = C - 1;

            int index = 0;

            while (true) {
                if (left > right)
                    break;

                // print top row
                for (int i = left; i <= right; i++)
                    mat[top][i] = arr[index++];
                top++;

                if (top > bottom)
                    break;

                // print right column
                for (int i = top; i <= bottom; i++)
                    mat[i][right] = arr[index++];
                right--;

                if (left > right)
                    break;

                // print bottom row
                for (int i = right; i >= left; i--)
                    mat[bottom][i] = arr[index++];
                bottom--;

                if (top > bottom)
                    break;

                // print left column
                for (int i = bottom; i >= top; i--)
                    mat[i][left] = arr[index++];
                left++;
            }
        }

        // Function to print the spiral matrix
        static void printSpiralMatrix(int mat[][]) {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++)
                    System.out.print(mat[i][j] + " ");
                System.out.println();
            }
        }

        // Driver code
        public static void main(String[] args) {
            int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };

            int mat[][] = new int[R][C];

            formSpiralMatrix(arr, mat);
            printSpiralMatrix(mat);
        }
    }

    static class PrintMatrixInSpiralOrder {
        /**
         * Given a 2D array, print it in spiral form. See the following examples.

        Examples: 

        Input:  1    2   3   4
                5    6   7   8
                9   10  11  12
                13  14  15  16
        Output: 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10 
        Explanation: The output is matrix in spiral format. 

        Input:  1   2   3   4  5   6
                7   8   9  10  11  12
                13  14  15 16  17  18
        Output: 1 2 3 4 5 6 12 18 17 16 15 14 13 7 8 9 10 11
        Explanation :The output is matrix in spiral format.
         */
        // Function print matrix in spiral form
        static void spiralPrint(int bottom, int right, int a[][]) {
            int i, top = 0, left = 0;

            /*
             * k - starting row index m - ending row index l - starting column index n -
             * ending column index i - iterator
             */

            while (top < bottom && left < right) {
                // Print the first row from the remaining rows
                for (i = left; i < right; ++i) {
                    System.out.print(a[top][i] + " ");
                }
                top++;

                // Print the last column from the remaining
                // columns
                for (i = top; i < bottom; ++i) {
                    System.out.print(a[i][right - 1] + " ");
                }
                right--;

                // Print the last row from the remaining rows */
                if (top < bottom) {
                    for (i = right - 1; i >= left; --i) {
                        System.out.print(a[bottom - 1][i] + " ");
                    }
                    bottom--;
                }

                // Print the first column from the remaining
                // columns */
                if (left < right) {
                    for (i = bottom - 1; i >= top; --i) {
                        System.out.print(a[i][left] + " ");
                    }
                    left++;
                }
            }
        }
    }
    
    static class MinNumberOfFlipsToConvertBinaryToZeroMatrix {
        public static boolean reachedDestination(int[][] mat, int n, int m){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(mat[i][j] == 1) return false;
                }
            }
            return true;
        }
        
        public static void flip(int[][] mat, int n, int m, int i, int j){
            mat[i][j] = mat[i][j] ^ 1;
            if(i - 1 >= 0) mat[i - 1][j] = mat[i - 1][j] ^ 1;
            if(j - 1 >= 0) mat[i][j - 1] = mat[i][j - 1] ^ 1;
            if(i + 1 < n) mat[i + 1][j] = mat[i + 1][j] ^ 1;
            if(j + 1 < m) mat[i][j + 1] = mat[i][j + 1] ^ 1;
        }
        
        public static int func(int[][] mat, int n, int m, HashSet<String> set, HashMap<String, Integer> dp){
            if(reachedDestination(mat, n, m)) return 0;
            String t = "";
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    t += Integer.toString(mat[i][j]);
                }
            }
            
            if(dp.containsKey(t)) return dp.get(t); // get the stored value.
            if(set.contains(t)) return Integer.MAX_VALUE;
            
            set.add(t);
            int minCost = Integer.MAX_VALUE;
            for(int i = 0; i < n; i++){
                for (int j = 0; j < m; j++) {
                    flip(mat, n, m, i, j);
                    int currMin = func(mat, n, m, set, dp);
                    if (currMin != Integer.MAX_VALUE)
                        minCost = Math.min(minCost, 1 + currMin);
                    flip(mat, n, m, i, j);
                }
            }
            set.remove(t);
            dp.put(t, minCost);
            return minCost;
        }
        
        public int minFlips(int[][] mat) {
            int n = mat.length, m = mat[0].length;
            HashMap<String, Integer> dp = new HashMap<>();
            int ans = func(mat, n, m, new HashSet<>(), dp);
            return ans == Integer.MAX_VALUE ? -1 : ans;
        }

        private int[] dx = { -1, 0, 1, 0 }, dy = { 0, 1, 0, -1 };
        private int[] dp; // dp[i]: the minimum flips needed transitioning from state i to state 0
        private boolean[] seen;

        public int minFlipsUsingDFS(int[][] mat) {
            int m = mat.length, n = mat[0].length, ans = Integer.MAX_VALUE;
            dp = new int[1 << (m * n)]; // 1 * (2^(m+n)) = 16
            // The state can be of max 15. refer the getState method.
            System.out.println(dp.length);
            seen = new boolean[1 << (m * n)]; // 16 
            System.out.println(seen.length);
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;

            return dfs(mat);
        }

        private int dfs(int[][] mat) {
            int state = getState(mat);
            if (seen[state]) {
                return -1;
            }
            if (dp[state] < Integer.MAX_VALUE) {
                return dp[state];
            }
            seen[state] = true;
            int minCost = Integer.MAX_VALUE;
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++) {
                    flipAt(mat, i, j);
                    int currMin = dfs(mat);
                    if (currMin >= 0) {
                        minCost = Math.min(minCost, currMin);
                    }
                    flipAt(mat, i, j);
                }
            }
            dp[state] = (minCost == Integer.MAX_VALUE ? -1 : minCost + 1);
            seen[state] = false;
            return dp[state];
        }

        private void flipAt(int[][] mat, int x, int y) {
            mat[x][y] = (mat[x][y] == 0 ? 1 : 0);
            for (int dir = 0; dir < 4; dir++) {
                int x1 = x + dx[dir];
                int y1 = y + dy[dir];
                if (x1 >= 0 && x1 < mat.length && y1 >= 0 && y1 < mat[0].length) {
                    mat[x1][y1] = (mat[x1][y1] == 0 ? 1 : 0);
                }
            }
        }

        private int getState(int[][] mat) {
            // A matrix of different combination will return unique state.
            int state = 0, cnt = 0;
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++) {
                    state = (state | (mat[i][j] << cnt)); // matrix[i][j]*2^count
                    // Existing state is added to the result of matrix[i][j]*2^count
                    // System.out.println("mat "+ (mat[i][j]<<cnt));
                    // System.out.println("state "+ state);
                    cnt++;
                }
            }
            return state;
        }
        public static void main(String[] args) {
            int[][] matrix = new int[][]{{1,1}, {1,1}};
            MinNumberOfFlipsToConvertBinaryToZeroMatrix obj = new MinNumberOfFlipsToConvertBinaryToZeroMatrix();
            obj.getState(matrix);
        }
    }
}

