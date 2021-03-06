/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geek.dynamicpgm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author surendhar-2481
 */
public class DynamicProgramming {

    // Overlapping subproblems: Dynamic Programming is mainly used when solutions of
    // same subproblems are needed again and again. In dynamic programming, computed
    // solutions to subproblems are stored in a table so that these don’t have to
    // recomputed. So Dynamic Programming is not useful when there are no common
    // (overlapping) subproblems because there is no point storing the solutions if
    // they are not needed again.
    // A given problems has Optimal Substructure Property if optimal solution of the
    // given problem can be obtained by using optimal solutions of its subproblems.

    int lookup[] = new int[100];
    int NIL = -1;

    void _initialize() {
        int i;
        for (i = 0; i < 100; i++) {
            lookup[i] = NIL;
        }
    }

    /**       
                         fib(5)
                     /             \
               fib(4)                fib(3)
             /      \                /     \
         fib(3)      fib(2)         fib(2)    fib(1)
        /     \        /    \       /    \
  fib(2)   fib(1)  fib(1) fib(0) fib(1) fib(0)
  /    \
fib(1) fib(0) */
    int fib(int n) {// Memoized approach
        // Top down fashion, look up table is filled, on demand.
        if (lookup[n] == NIL) {
            if (n <= 1) {
                lookup[n] = n;
            } else {
                lookup[n] = fib(n - 1) + fib(n - 2);
            }
        }
        return lookup[n];
    }

    int fibTab(int n) {// Bottom up approach
        // Tabulated version
        // all the entries are filled one by one, starting from the first entry
        int f[] = new int[n + 1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }

    int lis(int arr[], int n) {
        /**
         * Optimal Substructure: Let arr[0..n-1] be the input array and L(i) be the length of the LIS ending at index i such that arr[i] is the last element of the LIS.

            Then, L(i) can be recursively written as: 

            L(i) = 1 + max( L(j) ) where 0 < j < i and arr[j] < arr[i]; or
            L(i) = 1, if no such j exists.

            To find the LIS for a given array, we need to return max(L(i)) where 0 < i < n.
            Formally, the length of the longest increasing subsequence ending at index i, will be 1 greater than the maximum of lengths of all longest increasing subsequences ending at indices before i, where arr[j] < arr[i] (j < i).
            Thus, we see the LIS problem satisfies the optimal substructure property as the main problem can be solved using solutions to subproblems.

            The recursive tree given below will make the approach clearer:  

            Input  : arr[] = {3, 10, 2, 11}
            f(i): Denotes LIS of subarray ending at index 'i'

            (LIS(1)=1)

                f(4)  {f(4) = 1 + max(f(1), f(2), f(3))}
            /    |    \
            f(1)  f(2)  f(3) {f(3) = 1, f(2) and f(1) are > f(3)}
                |      |  \
                f(1)  f(2)  f(1) {f(2) = 1 + max(f(1)}
                        |
                        f(1) {f(1) = 1}
         * 
        */
        int[] lis = new int[n];
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            lis[i] = 1;
        }
        // Let arr[0..n-1] be the input array and L(i) be the length of the LIS till
        // index i such that arr[i] is part of LIS and arr[i] is the last element in
        // LIS, then L(i) can be recursively written as.
        // L(i) = { 1 + Max ( L(j) ) } where j < i and arr[j] < arr[i] and if there is
        // no such j then L(i) = 1
        // To get LIS of a given array, we need to return max(L(i)) where 0 < i < n So
        // the LIS problem has optimal substructure property as the main problem can be
        // solved using solutions to subproblems.
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && lis[j] + 1 > lis[i]) {// should be in ascending order and adding this element
                                                             // should result to already existing lis
                    lis[i] = lis[j] + 1;
                    parent[i] = j;
                }
            }
        }
        int max = 0;
        int maxIndex = 0;
        // pick maximum of all lis values
        for (int i = 0; i < n; i++) {
            if (max < lis[i]) {
                max = lis[i];
                maxIndex = i;
            }
        }
        // for(int i=0;i<n;i++){
        // System.out.print("index "+i+" parent "+parent[i]);
        // }
        System.out.print(arr[maxIndex]);
        printElement(parent, arr, maxIndex);
        return max;

    }

    void printElement(int[] parent, int arr[], int maxIndex) {
        if (maxIndex > 0) {
            System.out.print(" " + arr[parent[maxIndex]]);
            printElement(parent, arr, parent[maxIndex]);
        }
    }

    int lcs(char[] X, char[] Y, int m, int n) {
        /*
        Let the input sequences be X[0..m-1] and Y[0..n-1] of lengths m and n respectively. 
        And let L(X[0..m-1], Y[0..n-1]) be the length of LCS of the two sequences X and Y. 
        Following is the recursive definition of L(X[0..m-1], Y[0..n-1]).

        If last characters of both sequences match (or X[m-1] == Y[n-1]) then
        L(X[0..m-1], Y[0..n-1]) = 1 + L(X[0..m-2], Y[0..n-2])

        If last characters of both sequences do not match (or X[m-1] != Y[n-1]) then
        L(X[0..m-1], Y[0..n-1]) = MAX ( L(X[0..m-2], Y[0..n-1]), L(X[0..m-1], Y[0..n-2]) )

         * Examples: 1) Consider the input strings “AGGTAB” and “GXTXAYB”. Last
         * characters match for the strings. So length of LCS can be written as:
         * L(“AGGTAB”, “GXTXAYB”) = 1 + L(“AGGTA”, “GXTXAY”)
         * 
         * 2) Consider the input strings “ABCDGH” and “AEDFHR. Last characters do not
         * match for the strings. So length of LCS can be written as: L(“ABCDGH”,
         * “AEDFHR”) = MAX ( L(“ABCDG”, “AEDFHR”), L(“ABCDGH”, “AEDFH”) )
         * 
         * So the LCS problem has optimal substructure property as the main problem can
         * be solved using solutions to subproblems.
         */
        int L[][] = new int[m + 1][n + 1];// because we would iterate equal to m and n, and (0,m), (n,0) should be set to zero.
        
        /*
         * Following steps build L[m+1][n+1] in bottom up fashion. Note that L[i][j]
         * contains length of LCS of X[0..i-1] and Y[0..j-1]
         */
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    L[i][j] = 0;// Doesn't have any character. 
                } else if (X[i - 1] == Y[j - 1]) {// if the characters are equal
                    L[i][j] = 1 + L[i - 1][j - 1];
                } else {// if the characaters are not equal, then get max
                    L[i][j] = Math.max(L[i][j - 1], L[i - 1][j]);
                }
            }
        }

        System.out.println("Length of LCS is " + L[m][n]);
        int index = L[m][n];
        char result[] = new char[index + 1];
        result[index] = '\0';

        int i = m, j = n;// start from right most bottom corner
        while (i > 0 && j > 0) {
            if (X[i - 1] == Y[j - 1]) {// if characters equal add it to the result
                result[index] = X[i - 1];
                i--;
                j--;
                index--;
            } else if (L[i - 1][j] > L[i][j - 1]) {
                i--; // If not same, then find the larger of two and
                // go in the direction of larger value
            } else {
                j--;
            }
        }
        System.out.println("Lcs of X " + new String(X) + " and Y " + new String(Y) + " is " + new String(result));
        return L[m][n];
    }

    int editDistanceDP(String str1, String str2, int m, int n) {
        /*
         * Given two strings str1 and str2 and below operations that can performed on
         * str1. Find minimum number of edits (operations) required to convert ‘str1′
         * into ‘str2′.
         * 
         * Insert Remove Replace All of the above operations are of equal cost.
         * 
         * Examples:
         * 
         * Input: str1 = "geek", str2 = "gesek" Output: 1 We can convert str1 into str2
         * by inserting a 's'.
         * 
         * 
         * Input:   str1 = "cat", str2 = "cut"
            Output:  1
            We can convert str1 into str2 by replacing 'a' with 'u'.

            Input:   str1 = "sunday", str2 = "saturday"
            Output:  3
            Last three and first characters are same.  We basically
            need to convert "un" to "atur".  This can be done using
            below three operations. 
            Replace 'n' with 'r', insert t, insert a
         * 
         * The idea is process all characters one by one staring from either from left
         * or right sides of both strings. Let we traverse from right corner, there are
         * two possibilities for every pair of character being traversed.
         * 
         * m: Length of str1 (first string) n: Length of str2 (second string) If last
         * characters of two strings are same, nothing much to do. Ignore last
         * characters and get count for remaining strings. So we recur for lengths m-1
         * and n-1. Else (If last characters are not same), we consider all operations
         * on ‘str1′, consider all three operations on last character of first string,
         * recursively compute minimum cost for all three operations and take minimum of
         * three values.
         * 
         * Insert: Recur for m and n-1 Remove: Recur for m-1 and n Replace: Recur for
         * m-1 and n-1
         * 
         * https://media.geeksforgeeks.org/wp-content/cdn-uploads/EditDistance-1024x618.png
         * 
         * We can see that many subproblems are solved, again and again, for example, eD(2, 2) is called three times. 
         * Since same subproblems are called again, this problem has Overlapping Subproblems property.
         * 
         */
        char[] strChar1 = str1.toCharArray();
        char[] strChar2 = str2.toCharArray();
        int[][] dp = new int[m + 1][n + 1];// Create a table to store results of subproblems
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;// if the string str1 is empty, then cost is equal to the length of str2
                } else if (j == 0) {
                    dp[i][j] = i;// If the string str2 is empty, then cost is equal to the length of str1
                } else if (strChar1[i - 1] == strChar2[j - 1]) {
                    // if the last characters are equal, then ignore the last character and recur
                    // for the remaining
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // if the last characaters are not equal, then any one of the operations should
                    // be performed
                    dp[i][j] = 1 + Math.min(dp[i][j - 1] // insert
                            , Math.min(dp[i - 1][j] // remove
                                    , dp[i - 1][j - 1]));// replace
                }
            }
        }
        /*
         * Time Complexity: O(m x n) Auxiliary Space: O(m x n) There are many practical
         * applications of edit distance algorithm, refer Lucene API for sample.
         * 
         * 
         * Another example, display all the words in a dictionary that are near
         * proximity to a given word\incorrectly spelled word.
         */
        return dp[m][n];// returns minimum number of operation required to convert str1 to str2
    }

    int minCostPath(int[][] cost, int m, int n) {
        /*
         * Given a cost matrix cost[][] and a position (m, n) in cost[][], write a
         * function that returns cost of minimum cost path to reach (m, n) from (0, 0).
         * Each cell of the matrix represents a cost to traverse through that cell.
         * Total cost of a path to reach (m, n) is sum of all the costs on that path
         * (including both source and destination).
         * 
         * You can only traverse down, right and diagonally lower cells from a given
         * cell, i.e., from a given cell (i, j), cells (i+1, j), (i, j+1) and (i+1, j+1)
         * can be traversed. You may assume that all costs are positive integers.
         * 
         * 1) Optimal Substructure The path to reach (m, n) must be through one of the 3
         * cells: (m-1, n-1) or (m-1, n) or (m, n-1). So minimum cost to reach (m, n)
         * can be written as “minimum of the 3 cells plus cost[m][n]”.
         * 
         * minCost(m, n) = min (minCost(m-1, n-1), minCost(m-1, n), minCost(m, n-1)) +
         * cost[m][n]
         * 
         * 
         * It should be noted that the above function computes the same subproblems again and again. 
         * See the following recursion tree, there are many nodes which appear more than once. 
         * The time complexity of this naive recursive solution is exponential and it is terribly slow. 

        mC refers to minCost()
                                    mC(2, 2)
                          /            |           \
                         /             |            \             
                 mC(1, 1)           mC(1, 2)             mC(2, 1)
              /     |     \       /     |     \           /     |     \ 
             /      |      \     /      |      \         /      |       \
       mC(0,0) mC(0,1) mC(1,0) mC(0,1) mC(0,2) mC(1,1) mC(1,0) mC(1,1) mC(2,0)
         */

        // Time Complexity of the DP implementation is O(mn) which is much better than
        // Naive Recursive implementation.
        // Minimum cost path to reach the cell m,n
        // Allowing only movement - down, right, diagonal
        int tc[][] = new int[m + 1][n + 1];

        tc[0][0] = cost[0][0];// cost of the first cell is the total cost of first cell

        // Initialize first column of tc array
        for (int i = 1; i <= m; i++) {// Iterating through m rows and initialize the first column
            tc[i][0] = tc[i - 1][0] + cost[i][0];// The only min cost way to reach the column is down
        }

        for (int j = 1; j <= n; j++) {// Initializing first row of tc array
            tc[0][j] = tc[0][j - 1] + cost[0][j];// The only min cost way to reach the first row columns is right
        }

        // constructing rest of the array
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                tc[i][j] = Math.min(Math.min(tc[i - 1][j - 1]// For the cell i,j total cost to reach through diagonal
                        , tc[i - 1][j]), tc[i][j - 1])// traverse left
                        + cost[i][j];// Cost of the cell i,j
            }
        }
        return tc[m][n];

    }

    int totalWaysToMakeChangeForValueNGivenNumberOfCoins(int coins[], int noOfCoins, int n) {
        // Number of permutations of a subset summing up to a value N (the subset
        // element may repeat itself)
        /*

            https://www.youtube.com/watch?v=L27_JpN6Z1Q

         * Given a value N, if we want to make change for N cents, and we have infinite
         * supply of each of S = { S1, S2, .. , Sm} valued coins, how many ways can we
         * make the change? The order of coins doesn’t matter.
         * 
         * For example, for N = 4 and S = {1,2,3}, there are four solutions:
         * {1,1,1,1},{1,1,2},{2,2},{1,3}.
         * 
         * So output should be 4. For N = 10 and S = {2, 5, 3, 6}, there are five
         * solutions: {2,2,2,2,2}, {2,2,3,3}, {2,2,6}, {2,3,5} and {5,5}. So the output
         * should be 5.
         * 
         * 
         * To count total number solutions, we can divide all set solutions in two sets.
         * 1) Solutions that do not contain mth coin (or Sm). 2) Solutions that contain
         * at least one Sm. Let count(S[], m, n) be the function to count the number of
         * solutions, then it can be written as sum of count(S[], m-1, n) and count(S[],
         * m, n-Sm).
         * 
         * Therefore, the problem has optimal substructure property as the problem can
         * be solved using solutions to subproblems.
         * 
         * See the following recursion tree for S = {1, 2, 3} and n = 5. 
         The function C({1}, 3) is called two times. If we draw the complete tree, then we can see that there are many subproblems being called more than once. 
            

            C() --> count()
                                        C({1,2,3}, 5)                     
                                    /             \    
                                    /                 \                  
                        C({1,2,3}, 2)                 C({1,2}, 5)
                        /       \                      /      \         
                    /         \                    /         \   
            C({1,2,3}, -1)  C({1,2}, 2)        C({1,2}, 3)    C({1}, 5)
                        /    \             /     \           /     \
                        /       \           /       \         /        \
                C({1,2},0)  C({1},2)   C({1,2},1) C({1},3)    C({1}, 4)  C({}, 5)
                            / \     / \        /\         /     \         
                            /   \   /   \     /   \       /       \  
                            .      .  .     .   .     .   C({1}, 3) C({}, 4)
                                                        / \ 
                                                        /   \   
                                                        .      .
         * The solution to find the number of ways to make a change for N with coins M is 
         * 1) Exclude the coin, 2) Include the coin, 3) Add 1 + 2
         */
        int T[][] = new int[noOfCoins + 1][n + 1];
        for (int i = 0; i <= noOfCoins; i++) {
            T[i][0] = 1;// if the amount is zero, then return empty set.
            // There is only one way to get the value 0, which is don't choose any of the coins.
        }
        for (int j = 0; j <= n; j++) {
            T[0][j] = 0;// if no coins given, then there is zero solution.
        }

        for (int i = 1; i <= noOfCoins; i++) {
            int coinI = i - 1;
            for (int j = 1; j <= n; j++) {
                // if the jth value is greater than the coin value, then only we can include the coin, otherwise, we will have to exclude the coin (refer else)
                if (j >= coins[coinI]) {// coins[i-1] //i-1 is necessary, since the data will be stored in 0,1,2 index in coins table.
                    T[i][j] = T[i - 1][j] // solution excluding this coin
                            + T[i][j - coins[coinI]];// solution including this coin atleast once, reducing the amount
                                                     // by coin value, go to it's subproblem
                } else {// extend from topmost..
                    T[i][j] = T[i - 1][j];
                }
            }
        }
        // for(int i=0;i<=m;i++){
        // for(int j=0;j<=n;j++){
        // System.out.print(T[i][j]+"\t");
        // }
        // System.out.println("");
        // }
        return T[noOfCoins][n];

    }

    int matrixChainOrder(int p[], int n) {

        // Refer
        // http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/Dynamic/chainMatrixMult.htm
        // p defines the dimension of matrix at i
        /*
         * m[i,j] = Minimum number of scalar multiplications needed to compute the
         * matrix A[i]A[i+1]...A[j] = A[i..j] where dimension of A[i] is p[i-1] x p[i]
        
         * 
         * Find the order to perform the multiplications such that the arithmetic operations is minimum
         * 
         * the order in which we parenthesize the product affects the number of simple arithmetic operations needed to compute the product, or the efficiency. 
         * For example, suppose A is a 10 × 30 matrix, B is a 30 × 5 matrix, and C is a 5 × 60 matrix. Then,  

        (AB)C = (10×30×5) + (10×5×60) = 1500 + 3000 = 4500 operations
        A(BC) = (30×5×60) + (10×30×60) = 9000 + 18000 = 27000 operations.
         * 
         *
         * 
         * Four matrix will have five dimensions: p0...p4 where for A[i] is p[i-1]*p[i]
         * 
         * Let matrices are A1 (5*4) A2 (4*6) A3 (6*2) A4(2*7)
         * 
         * A1 A2 A3 A4 = (A1)(A2..A4) + (A1 A2)(A3 A4)+ (A1 A2 A3)(A4)
         * 
         * p[] = {5, 4, 6, 2, 7} where A[i] is p[i-1]*p[i]
         * 
         * m[1,4] = min {m[1,1]+m[2,4]+p[0]*p[1]*p[4], m[1,2]+m[3,4]+p[0]*[2]*p[4], m[1,3]+m[4,4]+p[0]*p[3]*p[4]} where m[i][j] represents cost.
         * 
         * m[i,j] = min {m[i,k] + m[k+1, j]+ p[i-1]*p[k]*p[j]} where i<k<j
         * 
         * 
         */
        int[][] m = new int[n][n];// 0th row and 0th column were not used for simplicity
        int[][] bracket = new int[n][n];
        // cost is zero when multiplying one matrix
        for (int i = 0; i < n; i++) {
            m[i][i] = 0;
        }
        int j, q;
        for (int L = 2; L < n; L++) {// sub chain
            // L represents chain length. Firstly we will want to calculate matrix multiplication of 2, 3..n. i.e. A1*A2, A1*A2*A3...
            // L also indicates the difference between i and j in the upper triangular matrix. The diagnonal of the matrix will be 0. 
            // Below loop for defining the placement of paranthesis
            // for example of chain length 3, with 4 matrix, there are exactly two ways we
            // can place paranthesis
            for (int i = 1; i < n - L + 1; i++) {// i value is for the rows in the rotated matrix. Refer rotated matrix here: https://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/Dynamic/chainMatrixMult.htm
                j = i + L - 1;// j is for columns in the rotated matrix
                //System.out.println("I is "+i+" L is "+L+" J "+j);
                /**
                 *  L is 2, 
                 * (I,J) are (1,2), (2,3), (3, 4) (Difference is L-1 i.e. 2-1 = 1). J can be obtained by (I+L-1). These indices represent the diagonals of difference 1 (between the indices).
                 * 
                 * L is 3 (I,J) are (1,3) (2,4) (Difference is 3-1= 2). Diagnonals of difference 2.
                 * 
                 * L is 4 (I,J) are 1,4 (Difference is 3). Diagonals of difference 3. 
                 * 
                 j 0 1 2 3 4  
               i 0 0 0 0 0 0   
                 1   0 1 2 3
                 2     0 4 5
                 3       0 6
                 4         0
                 */
                m[i][j] = Integer.MAX_VALUE;// defining infinity cost for multiplication of matrix A[i..j]
                for (int k = i; k < j; k++) {
                    // k is the place wher paranthesis should be placed..

                    // optimal solution will be obtained at the end of the loop
                    q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        bracket[i][j] = k;
                    }

                }
            }
        }
        printParanthesis(1, n - 1, bracket, 'A');
        return m[1][n - 1];// return m[1][n] max
    }

    void printParanthesis(int i, int j, int bracket[][], char name) {
        if (i > j)
            return;
        if (i == j) {
            // int charInt = (int)name;
            System.out.print(name);
            // System.out.print(charInt++);
            return;

        }
        System.out.print("(");

        printParanthesis(i, bracket[i][j], bracket, name);

        printParanthesis(bracket[i][j] + 1, j, bracket, name);

        System.out.print(")");

    }

    void binomialCoefficient(int n, int k) {

        // Optimal substructure: C(n,k) = c(n-1,k-1)+c(n-1,k)
        // C(n,0) = C(n,n) =1 nC0 = nCn = 1
        // Write a function that takes two parameters n and k and returns the value of
        // Binomial Coefficient C(n, k).

        // Calling c(n-1,k-1) + c(n-1,k) is a recursive call, having overlapping
        // problems, hence we need to store the calculated data in bottom up manner

        int[][] C = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, k); j++) {// as per the formula
                if (j == 0 || j == n) {
                    C[i][j] = 1;
                } else {
                    C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
                }
            }
        }
        System.out.println("Binomial coefficient is " + C[n][k]);

        // optimized version
        /*
         * 
         * Explanation: 1==========>> n = 0, C(0,0) = 1 1–1========>> n = 1, C(1,0) = 1,
         * C(1,1) = 1 1–2–1======>> n = 2, C(2,0) = 1, C(2,1) = 2, C(2,2) = 1
         * 1–3–3–1====>> n = 3, C(3,0) = 1, C(3,1) = 3, C(3,2) = 3, C(3,3)=1
         * 1–4–6–4–1==>> n = 4, C(4,0) = 1, C(4,1) = 4, C(4,2) = 6, C(4,3)=4, C(4,4)=1
         * So here every loop on i, builds i’th row of pascal triangle, using (i-1)th
         * row
         * 
         * At any time, every element of array C will have some value (ZERO or more) and
         * in next iteration, value for those elements comes from previous iteration. In
         * statement, C[j] = C[j] + C[j-1]
         * 
         * Let's say we want to calculate C(4, 3), i.e. n=4, k=3:
         * 
         * All elements of array C of size 4 (k+1) are initialized to ZERO.
         * 
         * i.e. C[0] = C[1] = C[2] = C[3] = C[4] = 0; Then C[0] is set to 1
         * 
         * For i = 1: C[1] = C[1] + C[0] = 0 + 1 = 1 ==>> C(1,1) = 1
         * 
         * For i = 2: C[2] = C[2] + C[1] = 0 + 1 = 1 ==>> C(2,2) = 1 C[1] = C[1] + C[0]
         * = 1 + 1 = 2 ==>> C(2,2) = 2
         * 
         * For i=3: C[3] = C[3] + C[2] = 0 + 1 = 1 ==>> C(3,3) = 1 C[2] = C[2] + C[1] =
         * 1 + 2 = 3 ==>> C(3,2) = 3 C[1] = C[1] + C[0] = 2 + 1 = 3 ==>> C(3,1) = 3
         * 
         * For i=4: C[4] = C[4] + C[3] = 0 + 1 = 1 ==>> C(4,4) = 1 C[3] = C[3] + C[2] =
         * 1 + 3 = 4 ==>> C(4,3) = 4 C[2] = C[2] + C[1] = 3 + 3 = 6 ==>> C(4,2) = 6 C[1]
         * = C[1] + C[0] = 3 + 1 = 4 ==>> C(4,1) = 4
         * 
         * C(4,3) = 4 is would be the answer in our example.
         */
        int[] optC = new int[k + 1];
        optC[0] = 1;
        for (int i = 1; i <= n; i++) {// Start the iteration from 1
            for (int j = Math.min(i, k); j > 0; j--) {
                optC[j] = optC[j] + optC[j - 1];
            }
        }
        System.out.println("Binomial coefficient is " + optC[k]);

    }

    int knapSack(int W, int wt[], int val[], int n) {
        /*
         * Given weights and values of n items, put these items in a knapsack of
         * capacity W to get the maximum total value in the knapsack. In other words,
         * given two integer arrays val[0..n-1] and wt[0..n-1] which represent values
         * and weights associated with n items respectively.
         * 
         * Also given an integer W which represents knapsack capacity, find out the
         * maximum value subset of val[] such that sum of the weights of this subset is
         * smaller than or equal to W.
         * 
         * You cannot break an item, either pick the complete item, or don’t pick it
         * (0-1 property).
         */
        /*
         * 
         * Algorithm:
         * 
         * To consider all subsets of items, there can be two cases for every item: (1)
         * the item is included in the optimal subset, (2) not included in the optimal
         * set. Therefore, the maximum value that can be obtained from n items is max of
         * following two values. 1) Maximum value obtained by n-1 items and W weight
         * (excluding nth item). 2) Value of nth item plus maximum value obtained by n-1
         * items and W minus weight of the nth item (including nth item).
         * 
         * If weight of nth item is greater than W, then the nth item cannot be included
         * and case 1 is the only possibility.
         * 
         * . See the following recursion tree, K(1, 1) is being evaluated twice. The time complexity of this naive recursive solution is exponential (2^n).  

            In the following recursion tree, K() refers 
            to knapSack(). The two parameters indicated in the
            following recursion tree are n and W.
            The recursion tree is for following sample inputs.
            wt[] = {1, 1, 1}, W = 2, val[] = {10, 20, 30}
                                K(n, W)
                                K(3, 2)  
                            /            \ 
                            /                \               
                        K(2, 2)                  K(2, 1)
                    /       \                  /    \ 
                    /           \              /        \
                K(1, 2)      K(1, 1)        K(1, 1)     K(1, 0)
                /  \         /   \              /        \
                /      \     /       \          /            \
            K(0, 2)  K(0, 1)  K(0, 1)  K(0, 0)  K(0, 1)   K(0, 0)
            Recursion tree for Knapsack capacity 2 
            units and 3 items of 1 unit weight.
         */
        int K[][] = new int[n + 1][W + 1];// create K of n+1, W+1

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    K[i][w] = 0;// initializing basic case
                } else if (wt[i - 1] <= w)// i-1 is the current item
                {
                    K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);// get the max of Case (1,2)
                    // 1 -> Adding value of ith item to maximum value obtained by i-1 items
                    // W minus weight of the ith item.
                    // Note that i-1 in val, wt refer to the current item whereas i-1 in K refers to
                    // previous item
                    // 2-> Don't add ith item and persist maximum value obtained by i-1 items, no
                    // change in weight. [As we excluded in total weight]
                } else {// we cannot add the item to Knapsack as the weight exceeds
                    K[i][w] = K[i - 1][w];
                }
            }
        }
        return K[n][W];// returns the maximum value that can be obtained in this knapsack
    }

    int eggDrop(int noOfEggs, int kFloors) {// n eggs, k floors

        /*
         * 
         * Suppose that we wish to know which stories in a 36-story building are safe to
         * drop eggs from, and which will cause the eggs to break on landing.
         * 
         * If only one egg is available and we wish to be sure of obtaining the right
         * result, the experiment can be carried out in only one way. Drop the egg from
         * the first-floor window; if it survives, drop it from the second floor window.
         * Continue upward until it breaks. In the worst case, this method may require
         * 36 droppings. Suppose 2 eggs are available. What is the least number of
         * egg-droppings that is guaranteed to work in all cases? The problem is not
         * actually to find the critical floor, but merely to decide floors from which
         * eggs should be dropped so that total number of trials are minimized.
         * 
         * Optimal substructure When we drop an egg from a floor x, there can be two
         * cases (1) The egg breaks (2) The egg doesn’t break.
         * 
         * 1) If the egg breaks after dropping from xth floor, then we only need to
         * check for floors lower than x with remaining eggs; so the problem reduces to
         * x-1 floors and n-1 eggs 2) If the egg doesn’t break after dropping from the
         * xth floor, then we only need to check for floors higher than x; so the
         * problem reduces to k-x floors and n eggs.
         * 
         * Since we need to minimize the number of trials in worst case, we take the
         * maximum of two cases. We consider the max of above two cases for every floor
         * and choose the floor which yields minimum number of trials.
         * 
        k ==> Number of floors 
        n ==> Number of Eggs 
        
        eggDrop(n, k) ==> Minimum number of trials needed to find the critical 
        floor in worst case.
        
        eggDrop(n, k) = 1 + min{max(eggDrop(n – 1, x – 1), eggDrop(n, k – x)), where x is in {1, 2, …, k}}
        
        2 eggs and 4 floors
        
        let's say you're dropping at floor x=1, E(2,4) = max(E(1, 0), E(2, 3)), 1st case is the egg breaks (best case possibility), if the egg doesn't break, you will need to try out with 2 eggs on 3 floors
        let's say x=2, E(2,4) = max(E(1, 1), E(2, 2)). 

        There will many repeated subproblems when you draw the complete recursion tree even for small values of n and k. 
 

                                E(2, 4)
                                |                      
                ------------------------------------- 
                |             |           |         |   
                |             |           |         |       
            x=1/          x=2/      x=3/     x=4/ 
                /             /         ....      ....
            /             /    
        E(1, 0)  E(2, 3)     E(1, 1)  E(2, 2)
                /  /...         /  
            x=1/                 .....
                /    
            E(1, 0)  E(2, 2)
                    /   
                    ......

        Partial recursion tree for 2 eggs and 4 floors.

        Concept of worst case: 
        
        For example : 
        Let there be ‘2’ eggs and ‘2’ floors then-:
        If we try throwing from ‘1st’ floor: 
        Number of tries in worst case= 1+max(0, 1) 
        0=>If the egg breaks from first floor then it is the threshold (expected) floor (best case possibility). 
        1=>If the egg does not break from first floor we will now have ‘2’ eggs and 1 floor [e(2,1)] to test which will give answer as 
        ‘1’ where 1 indicates that you will need to do 1 more trial (worst case possibility).
        We take the worst case possibility in account, so 1+max(0, 1)=2
       
        If we try throwing from ‘2nd’ floor: 
        Number of tries in worst case= 1+max(1, 0) 
        1=>If the egg breaks from second floor then we will have 1 egg and 1 floor to find threshold floor.(Worst Case) 
        0=>If egg does not break from second floor then it is threshold (expected) floor.
        
        (Best Case) 
        We take worst case possibility for surety, so 1+max(1, 0)=2.
        The final answer is min(1st, 2nd, 3rd….., kth floor) 
        So answer here is ‘2’. 

        6 floors, 2 eggs
             f1   f2  f3  f4  f5  f6
        e1   1    2   3   4   5   6
        e2   1    2   2   3   3   3
        
        eggDrop(n, k) = 1 + min{max(eggDrop(n – 1, x – 1), eggDrop(n, k – x)), where x is in {1, 2, …, k}}
        
        n=2, k=6 
       
        (e2, f2): (2, 2) n=2, k=2
        x=1: max(E[1, 0], E[2, 1]): max(0, 1) = 1
        x=2: max(E[1, 1], E[2, 0]): max(1, 0) = 1
        (e2,f2) = 1+min(1,1) = 1+1 = 2

        (e2, f3): (2,3) n=2, k=3
        x=1: max(E[1, 0], E[2, 2]) = max(0, 2) = 2
        x=2: max(E[1, 1], E[2, 1]) = max(1, 1) = 1
        x=3: max(E[1, 2], E[2, 0]) = max(2, 0) = 2
        Min(2, 1, 2) = 1 + 1 = 2
        (e2,f3) = 2
         */
        int eggDrop[][] = new int[noOfEggs + 1][kFloors + 1];
        // 0 not considered.
        // n egss, k floors
        for (int i = 1; i <= noOfEggs; i++) {// Base case 1
            eggDrop[i][0] = 0;// 0th floor egg drop trial will be zero
            eggDrop[i][1] = 1;// eggs in the first floor, the trial will be 1
        }

        for (int j = 1; j <= kFloors; j++) {// Base case 2
            eggDrop[1][j] = j;// for j floors with only one egg, the trial will be j floors.
            // for 36 floors with only one egg, the trail will be 36 times.
        }

        for (int i = 2; i <= noOfEggs; i++) {// start from 2nd egg, because with one egg we would need j trials.
            for (int j = 2; j <= kFloors; j++) {// start from 2nd floor, 1st floor is already filled.
                eggDrop[i][j] = Integer.MAX_VALUE;// trial set to maximum
                for (int x = 1; x <= j; x++) { 
                    //To compute the min trials for eggDrop(i,j) = 1 + min{max(eggDrop(i – 1, x – 1), eggDrop(i, j – x)), where x is in {1, 2, …, j}}
                    // Consider all droppings from 1st floor to kth floor and
                    // return the minimum of these values plus 1.
                    int res = 1 + Math.max(eggDrop[i - 1][x - 1], // if the egg breaks after dropping from x floor, then
                                                                  // check for floors lower than x with remaining eggs.
                            eggDrop[i][j - x] // if the egg doesn't break, then problem reduces to k-x floors and n eggs
                    );// 1+ is to indicate trial needed for this floor
                    // Since we need to minimize the number of trials in WORST CASE, we take the
                    // maximum of two cases. We consider the max of above two cases for every floor,
                    // that's why we are iterating for every floor
                    if (res < eggDrop[i][j]) {
                        eggDrop[i][j] = res;// deciding floors from which egss should be dropped, so that number of
                                            // trials can be minimized.
                    }
                }

            }
        }

        // X -- 1...j
        // Explanation: magine we drop our first egg from floor n, if it breaks, we can
        // step through the previous (n-1) floors one-by-one.
        //
        // If it doesn’t break, rather than jumping up another n floors, instead we
        // should step up just (n-1) floors (because we have one less drop available if
        // we have to switch to one-by-one floors), so the next floor we should try is
        // floor n + (n-1)
        //
        // Similarly, if this drop does not break, we next need to jump up to floor n +
        // (n-1) + (n-2), then floor n + (n-1) + (n-2) + (n-3) …
        //
        // We keep reducing the step by one each time we jump up, until that step-up is
        // just one floor, and get the following equation for a 100 floor building:
        //
        // n + (n-1) + (n-2) + (n-3) + (n-4) + … + 1 >= 100
        return eggDrop[noOfEggs][kFloors];

        // Remember, that we have calculated eggDrop min trials in bottom up approach.

    }

    int longestPalindromicSubsequence(String seq) {
        /**
         * As another example, if the given sequence is “BBABCBCAB”, then the output should be 7 as “BABCBAB” is the longest palindromic subsequence in it. 
         * “BBBBB” and “BBCBB” are also palindromic subsequences of the given sequence, but not the longest ones.
         * 
         * Let X[0..n-1] be the input sequence of length n and L(0, n-1) be the length of the longest palindromic subsequence of X[0..n-1]. 
            If last and first characters of X are same, then L(0, n-1) = L(1, n-2) + 2. 
            Else L(0, n-1) = MAX (L(1, n-1), L(0, n-2)). 

            Algorithm: 

            // Every single character is a palindrome of length 1
            L(i, i) = 1 for all indexes i in given sequence

            // IF first and last characters are not same
            If (X[i] != X[j])  L(i, j) =  max{L(i + 1, j),L(i, j - 1)} 

            // If there are only 2 characters and both are same
            Else if (j == i + 1) L(i, j) = 2  

            // If there are more than two characters, and first and last 
            // characters are same
            Else L(i, j) =  L(i + 1, j - 1) + 2 

                    L(0, 5)
                    /        \ 
                    /          \  
                L(1,5)          L(0,4)
            /    \            /    \
            /      \          /      \
        L(2,5)    L(1,4)  L(1,4)  L(0,3)

        In the above partial recursion tree, L(1, 4) is being solved twice. 
        If we draw the complete recursion tree, then we can see that there are many subproblems which are solved again and again. 
        Since the same subproblems are called again, this problem has Overlapping Subproblems property.
         */
        int n = seq.length();
        int[][] L = new int[n][n];// length of longest subsequence
        int cl;
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;// Base case 1: same character length is set to 1
        }
        int j;
        for (cl = 2; cl <= n; cl++) { // Computes the matrix for chain length from 2...n. The matrix is computed from bottom up fashion.
            for (int i = 0; i < n - cl + 1; i++) {// It's useless to cover the lowest triangular matrix
                // L is a n*n matrix which contains n characters for the given string on both sides, so it's useless to compute the lower triangular matrix.
                j = i + cl - 1;// Covers only the upper triangular matrix
                if (seq.charAt(i) == seq.charAt(j) && i + 1 == j) {// Base case 2: if there is only two characters, and
                                                                   // are equal
                    L[i][j] = 2;
                } else if (seq.charAt(i) == seq.charAt(j)) {// if equal
                    L[i][j] = L[i + 1][j - 1] + 2;// Two characters equal hence add it to longest array
                } else {
                    L[i][j] = Math.max(L[i + 1][j], L[i][j - 1]);
                }
            }
        }
        return L[0][n - 1];
        /**
         *         // Create a String length index+1 and
        // fill it with \0
        char[] lcs = new char[index + 1];
 
        // Start from the right-most-bottom-most
        // corner and one by one store characters
        // in lcs[]
        int i = m, j = n;
        while (i > 0 && j > 0) {
            // If current character in X[] and Y
            // are same, then current character
            // is part of LCS
            if (X[i - 1] == Y[j - 1]) {
                // Put current character in result
                lcs[index - 1] = X[i - 1];
                i--;
                j--;
 
                // reduce values of i, j and index
                index--;
            } // If not same, then find the larger of
            // two and go in the direction of larger
            // value
            else if (L[i - 1][j] > L[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        String ans = "";
        for (int x = 0; x < lcs.length; x++) {
            ans += lcs[x];
        }
         */
    }

    int cutRod(int price[], int n) {
        /**
         *  Given a rod of length n and an array or prices for lengths 1...m, find the max obtainable value (price) if we cut the rod of length n into a number of pieces of length 1...m.
         * 
         * if the length of the rod is 8 and the values of different pieces are given as the following, then the maximum obtainable value is 22 (by cutting in two pieces of lengths 2 and 6) 

            length   | 1   2   3   4   5   6   7   8  
            --------------------------------------------
            price    | 1   5   8   9  10  17  17  20

            And if the prices are as following, then the maximum obtainable value is 24 (by cutting in eight pieces of length 1) 

            length   | 1   2   3   4   5   6   7   8  
            --------------------------------------------
            price    | 3   5   8   9  10  17  17  20

            We can get the best price by making a cut at different positions and comparing the values obtained after a cut. We can recursively call the same function for a piece obtained after a cut.
            Let cutRod(n) be the required (best possible price) value for a rod of length n. cutRod(n) can be written as follows.
            cutRod(n) = max(price[i] + cutRod(n-i-1)) for all i in {0, 1 .. n-1}

                        cR() ---> cutRod() 

                                        cR(4)
                            /        /           
                            /        /              
                        cR(3)       cR(2)     cR(1)   cR(0)
                        /  |         /         |
                    /   |        /          |  
                cR(2) cR(1) cR(0) cR(1) cR(0) cR(0)
                /        |          |
                /         |          |   
            cR(1) cR(0) cR(0)      cR(0)
            /
            /
            CR(0)
         */
        // cutting rod of size n
        int[] cr = new int[n + 1];
        // To store maximum prize of cutting rod of size i
        cr[0] = 0;
        for (int i = 1; i <= n; i++) {// for each rod of length i, get the maximum prize
            int max_val = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++) {
                if (i == n) {
                    // Example
                    /*
                     * 5-->0 4 11 cost of cutting rod of length 1 + cost of cutting rod of length 4
                     * 5-->1 3 13 5-->2 2 13 5-->3 1 10 5-->4 0 10
                     */
                    // j start from left, i-j-1 start from right.
                    System.out.println(i + "-->" + j + " " + (i - j - 1) + " " + (price[j] + cr[i - j - 1]));

                    // why cr[i-j-1], because already we have calculaed maximum price obtained by
                    // cutting rod
                }
                max_val = Math.max(max_val, price[j] + cr[i - j - 1]);// Get the max price of i-j-1
                // add price of jth length + already calculated price of i-j-1
                // From the formula, cutRod(n) = max(price[i] + cutRod(n-i-1)) for all i in {0, 1 .. n-1}
                // cutRod(i) = max(price[j]+cutRod[i-j-1]) for all j in {0....j}, 
            }
            cr[i] = max_val;
            System.out.println("" + cr[i]);
        }

        return cr[n];

    }

    void maxSumIncreasingSubSequence(int arr[], int n) {
        /**Given an array of n positive integers. Write a program to find the sum of maximum sum subsequence of the given array such that the integers in the subsequence are sorted in increasing order. 
         * For example, if input is {1, 101, 2, 3, 100, 4, 5}, then output should be 106 (1 + 2 + 3 + 100), 
         * if the input array is {3, 4, 5, 10}, then output should be 22 (3 + 4 + 5 + 10) and if the input array is {10, 5, 4, 3}, then output should be 10 */
        // This method is similar to maximum increasing subsequence, except for the fact
        // that we will be using sum as a criteria, instead of length of increasing
        // subsequence

        int[] maxis = new int[n];

        for (int i = 0; i < n; i++) {
            maxis[i] = arr[i];// for all the elmenets, initialize the value to array
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] // increasing subsequence same as longest increasing subsequence)
                        && maxis[j] + arr[i] > maxis[i]) {// if adding this element arr[i] with maxis[j] ranging from o
                                                          // to i, results in the maximum sum sequence of i
                    maxis[i] = maxis[j] + arr[i];
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (max < maxis[i]) {
                max = maxis[i];
            }
        }
        System.out.println("Maximum sum increasing subsequence is " + max);

    }

    void longestBitonicSubSeq(int arr[], int n) {
        /*
         * Given an array arr[0 … n-1] containing n positive integers, a subsequence of
         * arr[] is called Bitonic if it is first increasing, then decreasing. Write a
         * function that takes an array as argument and returns the length of the
         * longest bitonic subsequence. A sequence, sorted in increasing order is
         * considered Bitonic with the decreasing part as empty. Similarly, decreasing
         * order sequence is considered Bitonic with the increasing part as empty.
         * 
         * Examples:
         * 
         * Input arr[] = {1, 11, 2, 10, 4, 5, 2, 1}; Output: 6 (A Longest Bitonic
         * Subsequence of length 6 is 1, 2, 10, 4, 2, 1)
         * 
         * Input arr[] = {12, 11, 40, 5, 3, 1} Output: 5 (A Longest Bitonic Subsequence
         * of length 5 is 12, 11, 5, 3, 1)
         * 
         * Input arr[] = {80, 60, 30, 40, 20, 10} Output: 5 (A Longest Bitonic
         * Subsequence of length 5 is 80, 60, 30, 20, 10)
         */

        /*
         * problem is similar to identifying longest increasing subsequence Let the
         * input array be arr[] of length n. We need to construct two arrays lis[] and
         * lds[] using Dynamic Programming solution of LIS problem. lis[i] stores the
         * length of the Longest Increasing subsequence ending with arr[i]. lds[i]
         * stores the length of the longest Decreasing subsequence starting from arr[i].
         * Finally, we need to return the max value of lis[i] + lds[i] – 1 where i is
         * from 0 to n-1.
         */
        int lis[] = new int[n];
        int lds[] = new int[n];

        for (int i = 0; i < n; i++) {
            lis[i] = 1;
            lds[i] = 1;
        }
        // find the lis starting with arr[i]
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]// increasing
                        && lis[j] + 1 > lis[i]) {
                    lis[i] = lis[j] + 1;
                }
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {// to find out the increasing subsequence from right to left - results in
                                             // identifying the decreasing
                if (arr[j] < arr[i] && lds[j] + 1 > lds[i]) {
                    lds[i] = lds[j] + 1;
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (max < (lis[i] + lds[i] - 1)) {
                max = lis[i] + lds[i] - 1;
            }
        }
        System.out.println("Longest bitonic subsequence is " + max);
    }

    int minPalindromicPartitioningOrCutStrings(String str) {
        System.out.println("String length is " + str.length());
        /*
         * Given a string, a partitioning of the string is a palindrome partitioning if
         * every substring of the partition is a palindrome. For example,
         * “aba|b|bbabb|a|b|aba” is a palindrome partitioning of “ababbbabbababa”.
         * Determine the fewest cuts needed for palindrome partitioning of a given
         * string.
         * 
         * For example, minimum 3 cuts are needed for “ababbbabbababa”. The three cuts
         * are “a|babbbab|b|ababa”. If a string is palindrome, then minimum 0 cuts are
         * needed. If a string of length n containing all different characters, then
         * minimum n-1 cuts are needed.
         * 
         * Using recursion:
         * // i is the starting index and j is the ending index. i must be passed as 0 and j as n-1
            minPalPartion(str, i, j) = 0 if i == j. // When string is of length 1.
            minPalPartion(str, i, j) = 0 if str[i..j] is palindrome.

            // If none of the above conditions is true, then minPalPartion(str, i, j) can be 
            // calculated recursively using the following formula.
            minPalPartion(str, i, j) = Min { minPalPartion(str, i, k) + 1 +
                                            minPalPartion(str, k+1, j) } 
                                    where k varies from i to j-1
         */

        /*
         * 
         * This problem is a variation of Matrix Chain Multiplication problem. If the
         * string is palindrome, then we simply return 0. Else, like the Matrix Chain
         * Multiplication problem, we try making cuts at all possible places,
         * recursively calculate the cost for each cut and return the minimum value.
         */
        int n = str.length();
        boolean[][] P = new boolean[n][n];// To store if the substring from i to j is a palindrome or not
        int[][] C = new int[n][n];// To store the minimum number of cuts needed for substring from i to j, C[i][j]
                                  // is the substring is palindrome (i.e when P[i][j] is true)

        for (int i = 0; i < n; i++) {
            P[i][i] = true;// to it's own element, mark the character as palindrome
            C[i][i] = 0;
        }
        /*
         * L is substring length. Build the solution in bottom up manner by considering
         * all substrings of length starting from 2 to n.
         */
        int j;
        for (int L = 2; L <= n; L++) {
            // upper triangular matrix
            // For substring of length L, set different possible starting indexes
            // System.out.println("L is "+L);
            // System.out.println("i ending index is "+(n-L+1));
            for (int i = 0; i < n - L + 1; i++) {// 1st iteration i ranges from 0 to 13, j ranges from
                j = i + L - 1; // set ending indeces
                // System.out.println("i is "+i);
                // System.out.println("j is "+j);
                if (L == 2) {// if there is only two characters, then we need to compare if the character is
                             // palindrome to each other
                    if (str.charAt(i) == str.charAt(j)) {
                        P[i][j] = true;
                        C[i][j] = 0;
                    }
                } else {
                    // More than two characters
                    // Else
                    // Need to check two corner characters and value of P[i+1][j-1]
                    P[i][j] = (str.charAt(i) == str.charAt(j)) && P[i + 1][j - 1];
                }

                if (P[i][j] == true) {
                    C[i][j] = 0;// if it's palindrome, then number of cuts required is zero
                } else {
                    C[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k <= j - 1; k++) {// Make a cut at every possible localtion starting from i to j,
                        // and get the minimum cost cut.
                        C[i][j] = Math.min(C[i][j], (C[i][k] + C[k + 1][j] + 1));// find the minimum number of cuts
                                                                                 // that's required.
                        // for example: aba|b, str[0..2] cost is zero, because it's palindrome, str[3] is palindrome cost is zero, then min cuts required for str[0...3] is 1.
                        }
                }
            }
        }
        // Return the min cut value for complete string. i.e., str[0..n-1]
        return C[0][n - 1];
    }

    boolean partitionArrayEquallyOrFindPartitonOfEqualSum(int[] arr, int n) {
        /**
         * Partition problem is to determine whether a given set can be partitioned into two subsets such that the sum of elements in both subsets is the same. 

            Examples: 

            arr[] = {1, 5, 11, 5}
            Output: true 
            The array can be partitioned as {1, 5, 5} and {11}

            arr[] = {1, 5, 3}
            Output: false 
            The array cannot be partitioned into equal sum sets.

            Following are the two main steps to solve this problem: 
            1) Calculate sum of the array. If sum is odd, there can not be two subsets with equal sum, so return false. 
            2) If sum of array elements is even, calculate sum/2 and find a subset of array with sum equal to sum/2. 
            The first step is simple. The second step is crucial, it can be solved either using recursion or Dynamic Programming.

            Recursion: 
            
            Let isSubsetSum(arr, n, sum/2) be the function that returns true if 
            there is a subset of arr[0..n-1] with sum equal to sum/2

            The isSubsetSum problem can be divided into two subproblems
            a) isSubsetSum() without considering last element 
                (reducing n to n-1)
            b) isSubsetSum considering the last element 
                (reducing sum/2 by arr[n-1] and n to n-1)
            If any of the above subproblems return true, then return true. 
            isSubsetSum (arr, n, sum/2) = isSubsetSum (arr, n-1, sum/2) ||
                                        isSubsetSum (arr, n-1, sum/2 - arr[n-1])

            The problem can be solved using dynamic programming when the sum of the elements is not too big. 
            We can create a 2D array part[][] of size (sum/2 + 1)*(n+1). 
            And we can construct the solution in a bottom-up manner such that every filled entry has the following property  

            part[i][j] = true if a subset of {arr[0], arr[1], ..arr[j-1]} has sum 
            equal to i, otherwise false, in other words, the part[sum][j] is true, when the subset of elements in 0...j equals sum.
         */
        /**
         * set[]={3, 4, 5, 2}
        target=6
        
            0    1    2    3    4    5    6

        0   T    F    F    F    F    F    F

        3   T    F    F    T    F    F    F
            
        4   T    F    F    T    T    F    F   
            
        5   T    F    F    T    T    T    F

        2   T    F    T    T    T    T    T
         */
        // Returns true if arr[] can be partitioned in two subsets of
        // equal sum, otherwise false
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        if (sum % 2 != 0) {
            // if the sum is odd, return false, as there can't exist two subsets equal to
            // the same sum
            return false;
        }
        boolean[][] part = new boolean[sum / 2 + 1][n + 1];// including 0 in row and column

        // initialize top row as true - row (0)
        for (int j = 0; j <= n; j++) {
            part[0][j] = true;// Base case: for zero sum, all elements set to true.
        }
        for (int i = 1; i <= sum / 2; i++) {
            // initialize leftmost column, except part[0][0], as 0
            // 0th element for all sum is initialized false.
            part[i][0] = false;// ith row, 0th column is false.

        }
        // NOTE: part[i][j] indicates that whether there is a subset of array
        // (arr[0]...arr[j-1]) that sums to i.
        // fill the partition table in bottom up manner
        for (int i = 1; i <= sum / 2; i++) {
            for (int j = 1; j <= n; j++) {
                part[i][j] = part[i][j - 1];// Not including the element j for the summation to i
                if (i >= arr[j - 1]) {// only the array elements whose value is less than the current sum are
                                      // considered, else they will be having the part[i][j-1]
                    part[i][j] = part[i][j] // not including the element j for the summation to i
                            || part[i - arr[j - 1]][j - 1];// subtracting the arr[j-1] element from i, and j to j-1,
                                                           // recursive, go for the sub problem
                }
            }
        }
        // below block prints the matrix constructed.
        // for (int i = 0; i <= sum/2; i++)
        // {
        // for (int j = 0; j <= n; j++)
        // System.out.print(part[i][j]+"\t");
        // System.out.println(""+i+"\t");
        // }
        return part[sum / 2][n];// are we able to find the subset whose sum is equal to sum/2
    }

    int INF = Integer.MAX_VALUE;

    void solveWordWrap(int l[], int n, int M) {
        /*
         * Given a sequence of words, and a limit on the number of characters that can
         * be put in one line (line width). Put line breaks in the given sequence such
         * that the lines are printed neatly. Assume that the length of each word is
         * smaller than the line width.
         * 
         * The word processors like MS Word do task of placing line breaks. The idea is
         * to have balanced lines. In other words, not have few lines with lots of extra
         * spaces and some lines with small amount of extra spaces.
         * 
         * 
         * The extra spaces includes spaces put at the end of every line except the last
         * one. The problem is to minimize the following total cost. Cost of a line =
         * (Number of extra spaces in the line)^3 Total Cost = Sum of costs for all
         * lines
         * 
         * For example, consider the following string and line width M = 15
         * Geeks for Geeks 
         * presents word 
         * wrap problem
         * 
         * Following is the optimized arrangement of words in 3 lines Geeks for Geeks
         * presents word wrap problem
         * 
         * The total extra spaces in line 1, line 2 and line 3 are 0, 2 and 3
         * respectively. So optimal value of total cost is 0 + 2*2 + 3*3 = 13 Please
         * note that the total cost function is not sum of extra spaces, but sum of
         * cubes (or square is also used) of extra spaces. The idea behind this cost
         * function is to balance the spaces among lines. For example, consider the
         * following two arrangement of same set of words:
         * 
         * 1) There are 3 lines. One line has 3 extra spaces and all other lines have 0
         * extra spaces. Total extra spaces = 3 + 0 + 0 = 3. Total cost = 3*3*3 + 0*0*0
         * + 0*0*0 = 27.
         * 
         * 2) There are 3 lines. Each of the 3 lines has one extra space. Total extra
         * spaces = 1 + 1 + 1 = 3. Total cost = 1*1*1 + 1*1*1 + 1*1*1 = 3.
         * 
         * Total extra spaces are 3 in both scenarios, but second arrangement should be
         * preferred because extra spaces are balanced in all three lines. The cost
         * function with cubic sum serves the purpose because the value of total cost in
         * second scenario is less.
         * 
         */
        /**The greedy solution is to place as many words as possible in the first line. 
         * Then do the same thing for the second line and so on until all words are placed.
         * This solution gives optimal solution for many cases, but doesn’t give optimal solution in all cases. 
         * For example, consider the following string “aaa bb cc ddddd” and line width as 6. Greedy method will produce following output. 

            aaa bb 
            cc 
            ddddd

            Extra spaces in the above 3 lines are 0, 4 and 1 respectively. So total cost is 0 + 64 + 1 = 65.
            But the above solution is not the best solution. Following arrangement has more balanced spaces. Therefore less value of total cost function.

            aaa
            bb cc
            ddddd

            Extra spaces in the above 3 lines are 3, 1 and 1 respectively. So total cost is 27 + 1 + 1 = 29.
            Despite being sub-optimal in some cases, the greedy approach is used by many word processors like MS Word and OpenOffice.org Writer. */
        /*
         * 
         * Dynamic programming: 
         * 
         * First we compute costs of all possible lines in a 2D table lc[][]. The value
         * lc[i][j] indicates the cost to put words from i to j(indeces of word in a
         * sentence) in a single line where i and j are indexes of words in the input
         * sequences. If a sequence of words from i to j cannot fit in a single line,
         * then lc[i][j] is considered infinite (to avoid it from being a part of the
         * solution). Once we have the lc[][] table constructed, we can calculate total
         * cost using following recursive formula. In the following formula, C[j] is the
         * optimized total cost for arranging words from [1 to j], and not i to j. 
         * lc is the cost to put words from i to j, 
         * so total cost at j is total cost  at i-1 + cost to put words from i to j.
         * 
         *  c[j] = 0, if j=0,
         *       = min(c[i-1]+lc[i,j]), if j>0, where 1<i<j
         * 
         * The array c[] can be computed from left to right, since each value depends only on earlier values. 
         * To print the output, we keep track of what words go on what lines, we can
         * keep a parallel p array that points to where each c value came from. The last
         * line starts at word p[n] and goes through word n.
         * 
         * The previous line starts at word p[p[n]] and goes through word p[n] – 1,
         * etc. The function printSolution() uses p[] to print the solution.
         * 
         * In the below program, input is an array l[] that represents lengths of words
         * in a sequence. The value l[i] indicates length of the ith word (i starts from
         * 1) in theinput sequence.
         */

        /*
         * l indicates the length of different words in the input sequence. For example,
         * l[] = {3, 2, 2, 5} is for a sentence like "aaa bb cc ddddd". n is size of l
         * array. M is line width ( maximum number of characters that can be fit in a
         * line.
         * 
         * M is 6
         * 
         * aaa
         * bb cc 
         * ddddd
         * 
         * Extra spaces in the above 3 lines are 3, 1 and 1 respectively. So total cost
         * is 27 + 1 + 1 = 29.
         */

        // for simplicity, 1 extra space is used in all below arrays

        int[][] extras = new int[n + 1][n + 1];// extras will have number of spaces if words from i to j are put in a
                                               // single line

        int[][] lc = new int[n + 1][n + 1]; // lc will have cost of lines when putting words from i to j

        for (int i = 1; i <= n; i++) {
            extras[i][i] = M - l[i - 1];// Number of extra spaces when putting word aaa will be M - l[i] -> 6-3 = 3
            // System.out.println("For i.."+i);
            for (int j = i + 1; j <= n; j++) {
                // aaa bb
                // aaa - extra space is 3, l[j-1] is length of the word at jth location (which
                // is 2 for bb) => 3-2-1 => 0
                extras[i][j] = extras[i][j - 1] // extras[i][j-1] - already calculated number of spaces excluding the
                                                // previous word
                        - l[j - 1] - 1;// 1 extra space is used in all the arrays (Because there has to be a space
                                       // between each word)
                // j-1 is the length of word at jth position of array l
                // System.out.print(extras[i][j]+"\t");
            }
        }

        /*
         * System.out.println("Extra space matrix.."); for(int i=1;i<=n;i++){ for(int
         * j=1;j<=n;j++){ System.out.print(extras[i][j]+"\t"); } System.out.println("");
         * 
         * Extra space matrix.. 3 0 -3 -9 0 4 1 -5 0 0 4 -2 0 0 0 1
         * 
         * }
         */

        // To calculate the line cost corresponding to the above calculated extra
        // spaces. The value lc[i][j] indicates cost of putting words from word number i
        // to j in a single line.
        for (int i = 1; i <= n; i++) {
            // iterating word 1 by 1
            for (int j = i; j <= n; j++) {// starting from i to j (note, that lc[i][i] should also be considered..
                if (extras[i][j] < 0) {// if the extra space is max, then we cannot put the line
                    lc[i][j] = INF;// line cost set to high, not possible to put the words from i to j in a single
                                   // line..
                } else if (j == n && extras[i][j] > 0) {// if the last word, then set the line cost of words, to zero..
                    lc[i][j] = 0;
                    System.out.println(
                            extras[i][j] + "Line cost of words from i " + i + " to j " + j + " is set to zero ");
                } else {
                    lc[i][j] = extras[i][j] * extras[i][j]; // we are using square approach to calculate the line cost
                                                            // when putting words from i to j
                }
            }
        }
        // System.out.println("Line cost matrix..");
        // for(int i=1;i<=n;i++){
        // for(int j=1;j<=n;j++){
        // System.out.print(lc[i][j]+"\t");
        // }
        // System.out.println("");
        // }
        // Now that we have calculated line cost from i to j, we need to find the
        // minimum cost and it's arrangement of words
        int c[] = new int[n + 1];// will have total cost of optimal arrangement of words from 1 to j

        int p[] = new int[n + 1];// p[] is used to print the solution

        /*
         * formula
         * 
         * c[j] = { 0 if j = 0 { min (c[i-1]+lc[i,j] if j > 0, 1 <equal i < equal j
         */
        c[0] = 0;
        for (int j = 1; j <= n; j++) {
            c[j] = INF;
            for (int i = 1; i <= j; i++) {// bottom up manner,to fill the c value
                // we should not iterate from i=j+1 to n, that would not fill the c value in
                // bottom up manner
                if (c[i - 1] != INF && lc[i][j] != INF && (c[i - 1] + lc[i][j]) < c[j]) {// minimal cost of putting word
                                                                                         // from 1 to i and adding cost
                                                                                         // from i to j -> if less than
                                                                                         // calculated value from 1 to j
                    c[j] = c[i - 1] + lc[i][j];
                    p[j] = i;// putting the word from i to j
                    System.out.println("P[" + j + "] is " + i);
                }
            }
        }
        System.out.println("cost matrix..");
        for (int i = 1; i <= n + 1; i++) {
            System.out.print(c[i - 1] + "\t");
        }
        // System.out.println("parent matrix..");
        // for(int i=1;i<=n;i++){
        // System.out.print(p[i]+"\t");
        // }
        printSolution(p, p[n]);
        // p[j] = i, p indicates the words arranged from i to j.
    }

    int printSolution(int p[], int n) {
        int k;
        if (p[n] == 1) {
            k = 1;// k is used to print the line
        } else {
            k = printSolution(p, p[n] - 1) + 1;
        }
        System.out.println("Line number " + k + " from word no " + p[n] + " to " + n);
        /*
         * Traversing backwards to print the solution i is p[n] to j is n - the order we
         * stored the value as in wordwrap
         * 
         * Line number 1: From word no. 1 to 1 //p[1] = 1, k=1, print the solution 
         * Line number 2: From word no. 2 to 3 // p[3] = 2, p[3]-1 = 2-1 = 1 
         * Line number 3: From word no. 4 to 4 //p[n]-1 is 4 - 1 = 3
         */
        return k;
    }

    private void printAllSubSetEqualSum(int[] arr, int i, int sum, boolean[][] dp, ArrayList<Integer> printList) {
        // since n-1 is passed, i am letting it be i.
        if (i == 0 && sum == 0) {// if all the elements are traversed and sum is also zero..
            System.out.println(printList);
            return;
        }
        if (i == 0 && sum != 0 && dp[sum][0]) {
            // if sum is not equal to zero, but arr[0] is equal to the sum, add this element
            // to printlist
            printList.add(arr[0]);
            System.out.println(printList);
            return;
        }

        // back track - ignore the element being traversed and include the element
        if (dp[sum][i - 1]) {
            ArrayList<Integer> ignorePrintList;
            ignorePrintList = (ArrayList) printList.clone();
            // we are not adding the current element if the prev element equals sum.
            printAllSubSetEqualSum(arr, i - 1, sum, dp, ignorePrintList);
        }

        if (arr[i - 1] <= sum && dp[sum - arr[i - 1]][i - 1])// including this element
        {// since n is passed, we need to use arr[i-1] in order to get the appropriate
         // element.
            printList.add(arr[i - 1]);//add the current element and subtract the element from sum.
            printAllSubSetEqualSum(arr, i - 1, sum - arr[i - 1], dp, printList);
        }

    }

    class Pair {
        int a, b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "(" + this.a + "," + this.b + ")";
        }
    }

    int findMaxChainLengthOfPairsOrLongestChain(Pair arr[], int n) {
        /*
         * You are given n pairs of numbers. In every pair, the first number is always
         * smaller than the second number. A pair (c, d) can follow another pair (a, b)
         * if b < c.
         * 
         * Chain of pairs can be formed in this fashion. Find the longest chain which
         * can be formed from a given set of pairs.
         * 
         * For example, if the given pairs are {{5, 24}, {39, 60}, {15, 28}, {27, 40},
         * {50, 90} }, then the longest chain that can be formed is of length 3, and the
         * chain is {{5, 24}, {27, 40}, {50, 90}}
         * 
         * This problem is a variation of standard Longest Increasing Subsequence
         * problem. Following is a simple two step process. 
         * 
         * 1) Sort given pairs in
         * increasing order of first (or smaller) element. Why do not need sorting? Consider the example 
         * {{6, 8}, {3, 4}} to understand the need of sorting.
         *  If we proceed to second step without sorting, we get output as 1. But the correct output is 2. 
         * 
         * 2) Now run a modified LIS
         * process where we compare the second element of already finalized LIS with the
         * first element of new LIS being constructed
         */
        Arrays.sort(arr, new Comparator<Pair>(){

            @Override
            public int compare(Pair o1, Pair o2) {
                // TODO Auto-generated method stub
                return o1.a - o2.a;
            }
        });
        
        int[] mcl = new int[n];

        for (int i = 0; i < n; i++) {
            mcl[i] = 1;// maxchain length for all the elements initially set to zero.
        }

        for (int i = 1; i < n; i++) {// processing 0th element is not needed, hence proceed from 1st element -> i
            for (int j = 0; j < i; j++) {
                // a pair (c,d) can follow another pair (a,b) if b<c
                if (arr[j].b < arr[i].a // b<c
                        && mcl[j] + 1 > mcl[i] // if adding this element results in longest chain length, then add it to
                                               // the max chain length (similar to max increasing subsequence)
                ) {
                    System.out.println("Pair (a,b) " + arr[j] + " -> (c,d) " + arr[i]);
                    mcl[i] = mcl[j] + 1;
                }
            }
        }
        // mcl[i] now stores the maximum chain length ending with pair i

        /* Pick maximum of all MCL values */
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (max < mcl[i]) {
                max = mcl[i];
            }
        }
        return max;
    }

    int lisOptimized(int arr[], int size) {

        // for more explanation refer:
        // http://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
        /*
         * Our strategy determined by the following conditions,
         * 
         * 1. If A[i] is smallest among all end candidates of active lists, we will
         * start new active list of length 1.
         * 
         * 2. If A[i] is largest among all end candidates of active lists, we will clone
         * the largest active list, and extend it by A[i].
         * 
         * 3. If A[i] is in between, we will find a list with largest end element that
         * is smaller than A[i]. Clone and extend this list by A[i]. We will discard all
         * other lists of same length as that of this modified list. let us take example
         * from wiki {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}.
         * 
         * A[0] = 0. Case 1. There are no active lists, create one. 0.
         * -----------------------------------------------------------------------------
         * A[1] = 8. Case 2. Clone and extend. 0. 0, 8.
         * -----------------------------------------------------------------------------
         * A[2] = 4. Case 3. Clone, extend and discard. 0. 0, 4. 0, 8. Discarded
         * -----------------------------------------------------------------------------
         * A[3] = 12. Case 2. Clone and extend. 0. 0, 4. 0, 4, 12.
         * -----------------------------------------------------------------------------
         * A[4] = 2. Case 3. Clone, extend and discard. 0. 0, 2. 0, 4. Discarded. 0, 4,
         * 12.
         * 
         * Querying length of longest is fairly easy. Note that we are dealing with end
         * elements only. We need not to maintain all the lists. We can store the end
         * elements in an array. Discarding operation can be simulated with replacement,
         * and extending a list is analogous to adding more elements to array.
         * 
         * To discard an element, we will trace ceil value of A[i] in auxiliary array
         * (again observe the end elements in your rough work), and replace ceil value
         * with A[i].
         * 
         * We extend a list by adding element to auxiliary array. We also maintain a
         * counter to keep track of auxiliary array length.
         */

        int[] tailTable = new int[size];// in worst case, tail table will have N lists of end element

        tailTable[0] = arr[0];
        int len = 1;
        for (int i = 1; i < size; i++) {
            if (arr[i] < tailTable[0]) {
                tailTable[0] = arr[i];// start a new list, if the end element of 1 element list is lower than the
                                      // already existing ones.
            } else if (arr[i] > tailTable[len - 1]) {// Extend the already existing sequence by adding this element to
                                                     // the end of the list.
                tailTable[len++] = arr[i];
            } else {
                // if the element is in between the existing list, we need to find the ceil
                // value which is largest than the current element and replace the existing end
                // element with this element
                tailTable[ceilIndex(tailTable, -1, len - 1, arr[i])] = arr[i];// find the ceil value from -1 to len-1
                                                                              // where arr[i] is the key, need to use
                                                                              // binary search to find the ceil Index
            }
        }

        // The loop runs for N elements. In the worst case (what is worst case input?),
        // we may end up querying ceil value using binary search (log i) for many A[i].
        return len;// returns the maximum increasing subsequence length.

    }

    int ceilIndex(int A[], int l, int r, int key) {
        while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (A[m] >= key)
                r = m;
            else
                l = m;
        }

        return r;
    }

    class CityPairs {
        int north, south;
        CityPairs(int north, int south) // Constructor
        {
            this.north = north;
            this.south = south;
        }
    }
    int buildingBridgesBetweenCities(CityPairs[] pairs, int n) {
        /*
         * 1. Building Bridges: Consider a 2-D map with a horizontal river passing
         * through its center.
         * 
         * There are n cities on the southern bank with x-coordinates a(1) … a(n) and n
         * cities on the northern bank with x-coordinates b(1) … b(n).
         * 
         * You want to connect as many north-south pairs of cities as possible with
         * bridges such that no two bridges cross.
         * 
         * When connecting cities, you can only connect city i on the northern bank to
         * city i on the southern bank.
         * 
            8     1     4     3     5     2     6     7  
        <---- Cities on the other bank of river---->
        --------------------------------------------
        <--------------- River--------------->
        --------------------------------------------
        1     2     3     4     5     6     7     8
        <------- Cities on one bank of river------->
         * 
         * when would two bridges cross?
         * 
         * -> Suppose that we sort all of the bridges built by their first city. If two
         * bridges cross, then we must have that there is some bridge (ai, bi) such that
         * for some other bridge (aj, bj) one of the following holds:
         * 
         * ai < aj and bi > bj ai > aj and bi < bj
         * 
         * -> In order to arrive at the fact that the no two bridges should cross
         * 
         * we need to ensure that for every set of bridges, we have that exactly one of
         * the two following properties holds for any pair of bridges (ai, bi), (aj,
         * bj): either
         * 
         * ai ≤ aj and bi ≤ bj or
         * 
         * ai ≥ aj and bi ≥ bj
         * 
         * Example:- ---------
         * 
         * First consider the pairs: (2,6), (5, 4), (8, 1), (10, 2), sort it according
         * to the first element of the pairs (in this case are already sorted) and
         * compute the list on the second element of the pairs, thus compute the LIS on
         * 6 4 1 2, that is 1 2.
         * 
         * Therefore the non overlapping lines you are looking for are (8, 1) and (10,
         * 2).
         * 
         * 
         */
        /**
        Input : 
        6 4 2 1
        2 3 6 5
        Output : Maximum number of bridges = 2
        Explanation: Let the north-south x-coordinates
        be written in increasing order.

        1  2  3  4  5  6
        \  \
        \  \        For the north-south pairs
            \  \       (2, 6) and (1, 5)
            \  \      the bridges can be built.
            \  \     We can consider other pairs also,
            \  \    but then only one bridge can be built 
                \  \   because more than one bridge built will
                \  \  then cross each other.
                \  \
        1  2  3  4  5  6 

        Input : 8 1 4 3 5 2 6 7 
                1 2 3 4 5 6 7 8
        Output : Maximum number of bridges = 5
         */
        /**
        Sort the north-south pairs on the basis of increasing order of south x-coordinates.
        If two south x-coordinates are same, then sort on the basis of increasing order of north x-coordinates.
        Now find the Longest Increasing Subsequence of the north x-coordinates.
        One thing to note that in the increasing subsequence a value can be greater as well as can be equal to its previous value.
         */
        int[] LIS = new int[n];
        // By default single city has LIS = 1.
        Arrays.fill(LIS, 1);
 
        Arrays.sort(pairs, new Comparator<CityPairs>(){

            @Override
            public int compare(CityPairs cp1, CityPairs cp2) {
                        // If 2 cities have same north coordinates
                // then sort them in increasing order
                // according to south coordinates.
                if (cp1.north == cp2.north)
                    return cp1.south - cp2.south;

                // Sort in increasing order of
                // north coordinates.
                return cp1.north - cp2.north;
            }
            
        }); // Sorting->
                                         // calling
        // our self made comparator
 
        // Logic for Longest increasing subsequence
        // applied on south coordinates.
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[i].south >= pairs[j].south) // greater or equal to previous value.
                    LIS[i] = Math.max(LIS[i], LIS[j] + 1);
            }
        }
        int max = LIS[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, LIS[i]);
        }
 
        // Return the max number of bridges that can be
        // built.
        return max;
    }

    class Box {
        // We are assuming that width w<= depth d of the box.
        public int height, width, length;

        Box(int h, int w, int d) {
            this.height = h;
            this.width = w;
            this.length = d;
        }

        Box() {

        }

        public String toString() {
            return this.height + "*" + this.width + "*" + this.length;
        }
    }

    public static Comparator<Box> baseAreaComparator = new Comparator<Box>() {

        @Override
        public int compare(Box o1, Box o2) {
            return (o2.width * o2.length) - (o1.length * o1.width);// Always choose the second object for decreasing order.
        }

    };

    int maxBoxStackHeight(Box arr[], int n) {
        /*
         * Box Stacking You are given a set of n types of rectangular 3-D boxes, where
         * the i^th box has height h(i), width w(i) and depth d(i) (all real numbers).
         * 
         * You want to create a stack of boxes which is as tall as possible, but you can
         * only stack a box on top of another box if the dimensions of the 2-D base of
         * the lower box are each strictly larger than those of the 2-D base of the
         * higher box.
         * 
         * Of course, you can rotate a box so that any side functions as its base. It is
         * also allowable to use multiple instances of the same type of box.
         */

        /*
         * Following are the key points to note in the problem statement: 1) A box can
         * be placed on top of another box only if both width and depth of the upper
         * placed box are smaller than width and depth of the lower box respectively.
         * 
         * 2) We can rotate boxes. For example, if there is a box with dimensions
         * {1x2x3} where 1 is height, 2×3 is base, then there can be three
         * possibilities, {1x2x3}, {2x1x3} and {3x1x2}.
         * 
         * 3) We can use multiple instances of boxes. What it means is, we can have two
         * different rotations of a box as part of our maximum height stack.
         * 
         * Following is the solution based on DP solution of LIS problem.
         * 
         * 1) Generate all 3 rotations of all boxes. The size of rotation array becomes
         * 3 times the size of original array. For simplicity, we consider depth as
         * always smaller than or equal to width.
         * 
         * 2) Sort the above generated 3n boxes in decreasing order of base area.
         * 
         * 3) After sorting the boxes, the problem is same as LIS with following optimal
         * substructure property. MSH(i) = Maximum possible Stack Height with box i at
         * top of stack MSH(i) = { Max ( MSH(j) ) + height(i) } where j < i and width(j)
         * > width(i) and depth(j) > depth(i). If there is no such j then MSH(i) =
         * height(i)
         * 
         * 4) To get overall maximum height, we return max(MSH(i)) where 0 < i < n
         */
        Box rot[] = new Box[n * 3];

        // Generate all possiblities of Rotations
        int index = 0;
        for (int i = 0; i < n; i++) {
            rot[index] = arr[i];
            index++;

            rot[index] = new Box();
            // First rotation of box
            rot[index].height = arr[i].length;
            rot[index].length = Math.max(arr[i].height, arr[i].width);// length should always be maximum
            rot[index].width = Math.min(arr[i].height, arr[i].width);// width should always be minimum hence the function calls were
                                                        // used.

            index++;

            rot[index] = new Box();
            // Second rotation of box
            rot[index].height = arr[i].width;
            rot[index].length = Math.max(arr[i].height, arr[i].length);// length should always be maximum
            rot[index].width = Math.min(arr[i].height, arr[i].length);
            index++;
        }
        n = 3 * n;// now the resultant n is 3 times the original.

        // We will want to form a pyramid such that the bottom has high dimension. So, we sort by base area (length*width). 
        Arrays.sort(rot, baseAreaComparator);// sort the array by base area.
        for (int i = 0; i < n; i++) {// prints all rotated boxed ordered by base area in ascending order..
            System.out.println(rot[i]);
        }
        // Note that the sorted base doesn't guarantee that w1<w2 or l1<l2, one dimension may be very large than the other, 
        // making base area more but both dimensions must be less to solve the problem, so we use the below if condition

        int msh[] = new int[n];// to store maximum stack height

        for (int i = 0; i < n; i++) {
            msh[i] = rot[i].height;// initialize the maximums stack height for all the boxes equivalent to height.
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // This below if condition finds the longest decreasing sequence
                // The constraint is the higher box should've less width and height than the lower box.
                // But, Because the array is sorted in descending order, we check that width and length of j > width and length of i
                if (rot[j].width > rot[i].width && rot[j].length > rot[i].length // placing i box should satisfy the property that width
                                                               // and depth should be greater than j's width, depth
                                                               // respectively
                        && msh[j] + rot[i].height > msh[i]) {// if adding this ith box results in obtaining the maximum stack
                                                        // height.
                    msh[i] = msh[j] + rot[i].height;
                }
            }
        }

        /* Pick maximum of all msh values */
        int max = -1;
        for (int i = 0; i < n; i++) {
            if (max < msh[i]) {
                max = msh[i];

            }
        }
        return max;

    }

    int minJumps(int arr[], int n) {
        /*
         * Given an array of integers where each element represents the MAX number of
         * steps that can be made forward from that element. Write a function to return
         * the minimum number of jumps to reach the end of the array (starting from the
         * first element). If an element is 0, then cannot move through that element.
         * 
         * Example:
         * 
         * Input: arr[] = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9} Output: 3 (1-> 3 -> 8 ->9)
         * 
         * A naive approach is to start from the first element and recursively call 
         * for all the elements reachable from first element. 
         * The minimum number of jumps to reach end from first can be calculated 
         * using minimum number of jumps needed to reach end from the elements reachable from first. 

        minJumps(start, end) = Min ( minJumps(k, end) ) for all k reachable from start 

        For example, minJumps(1, 0) = Min (minJumps(3, 9)) 
        minJumps(3, 9) = Min(minJumps(5,9), minJumps(8,9), minJumps(9,9)))
        minJumps(8,9) = Min(minJumps(9,9).....minJumps(9,9))

        For example, minJumps(3, 9) will be called two times as arr[3] is reachable from arr[1] and arr[2].
        As you can see, same problem is resolved multiple times.
         */

         /**
          * Approach: 

            1. In this way, make a jumps[] array from left to right such that jumps[i] indicate the minimum number of jumps needed to reach arr[i] from arr[0].
            2. To fill the jumps array run a nested loop inner loop counter is j and outer loop count is i.
            3. Outer loop from 1 to n-1 and inner loop from 0 to n-1.
            4. if i is less than j + arr[j] then set jumps[i] to minimum of jumps[i] and jumps[j] + 1. initially set jump[i] to INT MAX
            5. Finally, return jumps[n-1].
          */

        if (n == 0 || arr[0] == 0) {
            return Integer.MAX_VALUE;
        }
        int jumps[] = new int[n];//To store the min jumps at i position.
        jumps[0] = 0;
        for (int i = 1; i < n; i++) {
            jumps[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                // jth element must reach i, so j+arr[j] >= i
                if (i <= j + arr[j]// jth index, arr[j] indicates the jumps required from j
                                   // i<=j+arr[j] -> jump from j + arr[j] should be within i, only then we should
                                   // calculate the min jump
                        && jumps[j] != Integer.MAX_VALUE) {
                    // System.out.println("i-> "+i+"j-> "+j+" jumps[i]-> "+jumps[i]+" jumps[j]->
                    // "+jumps[j]);
                
                    jumps[i] = Math.min(jumps[i], jumps[j] + 1);// jumps[j]+1 is the important one, it denotes that 1
                                                                // more step from jumps[j] is required for movement to
                                                                // reach i
                    // System.out.println("Final jumps[i] "+jumps[i]);
                    break;// If the first most element in the array, is found to reach the ith element,
                          // then what obtained is the minimum number of jumps to reach i.
                }
            }
        }
        return jumps[n - 1];
    }

    void maxSizeOfSubSquareMatrixWithAll1sInBinaryMatrix(int M[][], int R, int C) {

        /*
         * Given a binary matrix, find out the maximum size square sub-matrix with all
         * 1s.
         * 
         * For example, consider the below binary matrix.
         * 
         * 
         * 0 1 1 0 1 
         * 1 1 0 1 0 
         * 0 1 1 1 0 
         * 1 1 1 1 0 
         * 1 1 1 1 1 
         * 0 0 0 0 0 
         * 
         * The maximum
         * square sub-matrix with all set bits is
         * 
         * 1 1 1 
         * 1 1 1 
         * 1 1 1
         * 
         * Algorithm: Let the given binary matrix be M[R][C]. The idea of the algorithm
         * is to construct an auxiliary size matrix S[][] in which each entry S[i][j]
         * represents size of the square sub-matrix with all 1s including M[i][j] where
         * M[i][j] is the RIGHTMOST and BOTTOMMOST entry in sub-matrix.
         * 
         * 1) Construct a sum matrix S[R][C] for the given M[R][C]. a) Copy first row
         * and first columns as it is from M[][] to S[][] b) For other entries, use
         * following expressions to construct S[][] If M[i][j] is 1 then S[i][j] =
         * min(S[i][j-1], S[i-1][j], S[i-1][j-1]) + 1 Else If M[i][j] is 0 S[i][j] = 0
         * 2) Find the maximum entry in S[R][C] 3) Using the value and coordinates of
         * maximum entry in S[i], print sub-matrix of M[][] For the given M[R][C] in
         * above example, constructed S[R][C] would be:
         * 
         * 0 1 1 0 1 
         * 1 1 0 1 0 
         * 0 1 1 1 0 
         * 1 1 2 2 0 
         * 1 2 2 3 1 
         * 0 0 0 0 0 
         * 
         * NOTE THAT EACH point in the matrix represent the size of sub-matrix with all 1s including itself,
         * So we have to copy the first row and first column as such because there exists no sub square matrix.
         * 
         * The value of
         * maximum entry in above matrix is 3 and coordinates of the entry are (4, 3).
         * Using the maximum value and its coordinates, we can find out the required
         * sub-matrix.
         * 
         */
        int max_of_s, max_i, max_j;
        System.out.println("R " + R + " C " + C);
        int S[][] = new int[R][C];

        for (int i = 0; i < R; i++) {
            S[i][0] = M[i][0];// copy the first column
        }
        for (int j = 0; j < C; j++) {
            S[0][j] = M[0][j];// copy the first row.
        }

        for (int i = 1; i < R; i++) {
            for (int j = 1; j < C; j++) {
                if (M[i][j] == 1) {
                    S[i][j] = Math.min(Math.min(S[i][j - 1], S[i - 1][j]), S[i - 1][j - 1]) + 1;// get the minimum and
                                                                                                // increment it to 1.
                } else {//if the point is zero, then there is no sub-matrix at all.
                    S[i][j] = 0;
                }
            }
        }
        // The constructed matrix will now have the form as below

        /*
         * 0 1 1 0 1 1 1 0 1 0 0 1 1 1 0 1 1 2 2 0 1 2 2 3 1 0 0 0 0 0
         */
        max_of_s = S[0][0];
        max_i = 0;
        max_j = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (max_of_s < S[i][j]) {
                    max_of_s = S[i][j];
                    max_i = i;
                    max_j = j;
                }
            }
        }

        // Time Complexity: O(m*n) where m is number of rows and n is number of columns
        // in the given matrix.

        System.out.println("Maximum Size of sub square matrix is " + max_of_s);
        for (int i = max_i; i > max_i - max_of_s; i--) {
            for (int j = max_j; j > max_j - max_of_s; j--) {
                System.out.print(M[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    void uglyNumber(int n) {
        /*
         * Ugly numbers are numbers whose only prime factors are 2, 3 or 5. The sequence
         * 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, … shows the first 11 ugly numbers. By
         * convention, 1 is included. Write a program to find and print the 150’th ugly
         * number.
         * 
         * Naive approach
         * Loop for all positive integers until ugly number count is smaller than n, if an integer is ugly than increment ugly number count.
            To check if a number is ugly, divide the number by greatest divisible powers of 2, 3 and 5, if the number becomes 1 then it is an ugly number otherwise not. 

            For example, let us see how to check for 300 is ugly or not. Greatest divisible power of 2 is 4, after dividing 300 by 4 we get 75. 
            Greatest divisible power of 3 is 3, after dividing 75 by 3 we get 25. Greatest divisible power of 5 is 25, after dividing 25 by 25 we get 1. Since we get 1 finally, 300 is ugly number.
         * 
                * static int maxDivide(int a, int b)
            {
                while (a % b == 0)
                    a = a / b;
                return a;
            }
        
            static int isUgly(int no)
            {
                no = maxDivide(no, 2);
                no = maxDivide(no, 3);
                no = maxDivide(no, 5);
        
                return (no == 1) ? 1 : 0;
            }
        

            static int getNthUglyNo(int n)
            {
                int i = 1;
        
                // ugly number count
                int count = 1;
        
                // check for all integers
                // until count becomes n
                while (n > count) {
                    i++;
                    if (isUgly(i) == 1)
                        count++;
                }
                return i;
            }
         * 
         * Here is a time efficient solution with O(n) extra space. The ugly-number
         * sequence is 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, … because every number can
         * only be divided by 2, 3, 5, one way to look at the sequence is to split the
         * sequence to three groups as below: (1) 1×2, 2×2, 3×2, 4×2, 5×2, … (2) 1×3,
         * 2×3, 3×3, 4×3, 5×3, … (3) 1×5, 2×5, 3×5, 4×5, 5×5, …
         * 
         * We can find that every subsequence is the ugly-sequence itself (1, 2, 3, 4,
         * 5, …) multiply 2, 3, 5. For example, 2*7 is 21, which isn't the result, so the subsequence is the ugly-sequence itself.
         * 
         * Then we use similar merge method as merge sort, to get every ugly number from
         * the three subsequence.
         * 
         * Every step we choose the smallest one, and move one step after.
         */
        /**
         * initialize
        ugly[] =  | 1 |
        i2 =  i3 = i5 = 0;

        First iteration
        ugly[1] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
                    = Min(2, 3, 5)
                    = 2
        ugly[] =  | 1 | 2 |
        i2 = 1,  i3 = i5 = 0  (i2 got incremented ) 
        
        i2 has processed ugly index 1, i3 and i5 are still at 0. Once one of them isare popped, 
        it will point to the ugly index 1 where the ugly number is 2, which is then multipled by 3 and considered as the next multiple.

        Second iteration
            ugly[2] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
                    = Min(4, 3, 5)
                    = 3
            ugly[] =  | 1 | 2 | 3 |
            i2 = 1,  i3 =  1, i5 = 0  (i3 got incremented ) 

        Third iteration
            ugly[3] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
                    = Min(4, 6, 5)
                    = 4
            ugly[] =  | 1 | 2 | 3 |  4 |
            i2 = 2,  i3 =  1, i5 = 0  (i2 got incremented )

        Fourth iteration
            ugly[4] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
                    = Min(6, 6, 5)
                    = 5
            ugly[] =  | 1 | 2 | 3 |  4 | 5 |
            i2 = 2,  i3 =  1, i5 = 1  (i5 got incremented )

        Fifth iteration
            ugly[5] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
                    = Min(6, 6, 10)
                    = 6
            ugly[] =  | 1 | 2 | 3 |  4 | 5 | 6 |
            i2 = 3,  i3 =  2, i5 = 1  (i2 and i3 got incremented )

        Will continue same way till I < 150
         */

        int[] ugly = new int[n];

        ugly[0] = 1;
        int i2 = 0, i3 = 0, i5 = 0;// similar to merge sort, using three pointers i2,i3,i5 in an array ugly.

        int next_multiple_of_2 = ugly[i2] * 2;
        int next_multiple_of_3 = ugly[i3] * 3;
        int next_multiple_of_5 = ugly[i5] * 5;
        // choose the min of above 3, and add it to the resultant array ugly. Then,
        // increment the corresponding pointer index.

        int next_ugly_no = 0;
        for (int i = 1; i < n; i++) {
            next_ugly_no = Math.min(Math.min(next_multiple_of_2, next_multiple_of_3), next_multiple_of_5);// get the min
                                                                                                          // of these 3
                                                                                                          // element
            //  
            ugly[i] = next_ugly_no;// store the next ugly number in the array so that it can be reused by the pointers

            if (next_ugly_no == next_multiple_of_2) {
                i2 = i2 + 1;// increment i2 pointer index
                next_multiple_of_2 = ugly[i2] * 2; //Order is important.
            }
            if (next_ugly_no == next_multiple_of_3) {
                i3 = i3 + 1;// increment i3 pointer index
                next_multiple_of_3 = ugly[i3] * 3; //multiply 3 with the already found ugly number at index i3. 
                //The index i will be 1, 2, 3, 4... where the elements will be 2, 3, 5, 6...
            }
            if (next_ugly_no == next_multiple_of_5) {
                i5 = i5 + 1;// increment i2 pointer index
                next_multiple_of_5 = ugly[i5] * 5;
            }
        }
        System.out.println("Ugly number number of n " + n + " is " + next_ugly_no);

    }

    void largestSuMContiguousSubArrayWithNegativeAndPositiveIntegers(int arr[], int n) {// given a list of positive and negative integers in an array, calculate the
                                           // sum
    /**
     * Write an efficient program to find the sum of contiguous subarray 
     * within a one-dimensional array of numbers that has the largest sum.  
     * 
     * Lets take the example:
        {-2, -3, 4, -1, -2, 1, 5, -3}

        4+(-1)+(-2)+1+5 = 7 Max contiguous sum is 7.
     * */
    /**
     * Kadane’s Algorithm:

        Initialize:
            max_so_far = INT_MIN
            max_ending_here = 0

        Loop for each element of the array
        (a) max_ending_here = max_ending_here + a[i]
        (b) if(max_so_far < max_ending_here)
                    max_so_far = max_ending_here
        (c) if(max_ending_here < 0)
                    max_ending_here = 0
        return max_so_far

        Explanation: 
        The simple idea of Kadane’s algorithm is to look for all positive contiguous segments of the array (max_ending_here is used for this). 
        And keep track of maximum sum contiguous segment among all positive segments (max_so_far is used for this). 
        Each time we get a positive-sum compare it with max_so_far and update max_so_far if it is greater than max_so_far 

        
    Lets take the example:
    {-2, -3, 4, -1, -2, 1, 5, -3}

    max_so_far = max_ending_here = 0

    for i=0,  a[0] =  -2
    max_ending_here = max_ending_here + (-2)
    Set max_ending_here = 0 because max_ending_here < 0

    for i=1,  a[1] =  -3
    max_ending_here = max_ending_here + (-3)
    Set max_ending_here = 0 because max_ending_here < 0

    for i=2,  a[2] =  4
    max_ending_here = max_ending_here + (4)
    max_ending_here = 4
    max_so_far is updated to 4 because max_ending_here greater 
    than max_so_far which was 0 till now

    for i=3,  a[3] =  -1
    max_ending_here = max_ending_here + (-1)
    max_ending_here = 3

    for i=4,  a[4] =  -2
    max_ending_here = max_ending_here + (-2)
    max_ending_here = 1

    for i=5,  a[5] =  1
    max_ending_here = max_ending_here + (1)
    max_ending_here = 2

    for i=6,  a[6] =  5
    max_ending_here = max_ending_here + (5)
    max_ending_here = 7
    max_so_far is updated to 7 because max_ending_here is 
    greater than max_so_far

    for i=7,  a[7] =  -3
    max_ending_here = max_ending_here + (-3)
    max_ending_here = 4
     */
        int max_ending_here = 0;
        int max_so_far = 0;
        for (int i = 0; i < n; i++) {
            max_ending_here = max_ending_here + arr[i];
            if (max_ending_here < 0) {
                max_ending_here = 0;
            }
            if (max_ending_here > max_so_far) {
                max_so_far = max_ending_here;
            }
        }
        System.out.println("Maximum largest positive contiguous sum sub array is " + max_so_far);
    }

    void longestPalindromeSubString(String str) {

        /**
         * Given a string, find the longest substring which is palindrome. 

            For example, 

            Input: Given string :"forgeeksskeegfor", 
            Output: "geeksskeeg"

            Input: Given string :"Geeks", 
            Output: "ee"

            This is different from longest palindrome subsequence 
            where you find the subsequence in a string for longest palindrome.
            
         */
        int n = str.length();
        // table[i][j] will be false if substring str[i..j]
        // is not palindrome.
        // Else table[i][j] will be true
        boolean[][] table = new boolean[n][n];

        int maxLength = 1;
        for (int i = 0; i < n; i++) {
            table[i][i] = true;// string of length 1 is palindromic to itself, hence mark them as palindrome
        }
        int start = 0;// This is required, for calculating the longest palindrome substring size
                      // greater than 3
        for (int i = 0; i < n - 1; i++) {// check for substring of length 2
            if (str.charAt(i) == str.charAt(i + 1)) {
                table[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        for (int k = 3; k <= n; k++) {// Check for lengths greater than 2. k is length
            // of substring
            int j;
            // Fix the starting index
            for (int i = 0; i < n - k + 1; i++) {
                // Get the ending index of substring from
                // starting index i and length k
                j = i + k - 1;
                if (table[i + 1][j - 1] && str.charAt(i) == str.charAt(j)) {
                    table[i][j] = true;
                    if (maxLength < k) {
                        start = i;
                        maxLength = k;
                    }
                }
            }

        }
        System.out.println("Max length " + maxLength + "Longest palindromic sub string is "
                + str.substring(start, start + maxLength));

    }

    int optimalBinarySearchTree(int[] keys, int freq[], int n) {
        // Refer: https://dhruvilkotecha.medium.com/optimal-binary-search-tree-b91665db450c
        /*
         * Given a sorted array keys[0.. n-1] of search keys and an array freq[0.. n-1]
         * of frequency counts, where freq[i] is the number of searches to keys[i].
         * Construct a binary search tree of all keys such that the total cost of all
         * the searches is as small as possible.
         * 
         * Let us first define the cost of a BST. The cost of a BST node is level of
         * that node multiplied by its frequency. Level of root is 1.
         * 
         * Input:  keys[] = {10, 12}, freq[] = {34, 50}
            There can be following two possible BSTs 
                    10                       12
                    \                     / 
                    12                 10
                    I                     II
            Frequency of searches of 10 and 12 are 34 and 50 respectively.
            The cost of tree I is 34*1 + 50*2 = 134
            The cost of tree II is 50*1 + 34*2 = 118 


            Input:  keys[] = {10, 12, 20}, freq[] = {34, 8, 50}
            There can be following possible BSTs
                10                12                 20         10              20
                \             /    \              /             \            /
                12          10     20           12               20         10  
                    \                            /                 /           \
                    20                        10                12             12  
                I               II             III             IV             V
            Among all possible BSTs, cost of the fifth BST is minimum.  
            Cost of the fifth BST is 1*50 + 2*34 + 3*8 = 142 
         * 
         * 
         * 1) Optimal Substructure: 
        The optimal cost for freq[i..j] can be recursively calculated using the following formula. 

        optcost(i,j) = freq[k] + min [optcost(i, r-1)+optcost(r+1, j)] where i<k<j, i<r<j

        We need to calculate optCost(0, n-1) to find the result. 
        The idea of above formula is simple, we one by one try all nodes as root (r varies from i to j in second term). 
        When we make rth node as root, we recursively calculate optimal cost from i to r-1 and r+1 to j. 
        We add sum of frequencies from i to j (see first term in the above formula), 
        this is added because every search will go through root and one comparison will be done for every search.
         * 
         */
        /*
         * cost[i][j] = Optimal cost of binary search tree that can be formed from
         * keys[i] to keys[j]. cost[0][n-1] will store the resultant cost
         */
        int cost[][] = new int[n][n];
        // We need to construct all types of binary search trees with the keys given and
        // find out the optimal cost of BST

        for (int i = 0; i < n; i++) {
            cost[i][i] = freq[i];// cost of single elements will be the frequency of that element itself
        }

        // Now, It's required to construct only the upper triangular matrix, since
        // calculation repetition can be avoided.

        // Now we need to consider chains of length 2, 3, ... . (chains here means sub
        // problem)
        // L is chain length.
        for (int L = 2; L <= n; L++) {

            // i is row number in cost[][]
            System.out.println("L is " + L);
            for (int i = 0; i < n - L + 1; i++) {// must have to be less than
                // Get column number j from row number i and chain length L
                int j = i + L - 1;
                System.out.println(i + "-> " + j);
                cost[i][j] = Integer.MAX_VALUE;

                for (int r = i; r <= j; r++) {// consider r as root,
                    //example, c[1,4] = min{ c[1,1] + c[2,4], c[1,2] + c[3,4], c[1,3] + c[4,4] } + 11 
                    int c = ((r > i) ? cost[i][r - 1] : 0) + ((r < j) ? cost[r + 1][j] : 0);// r is
                                                                                                              // considered
                                                                                                              // as
                                                                                                              // root.
                    // When we make rth node as root, we recursively calculate optimal cost from i
                    // to r-1 and r+1 to j.
                    cost[i][j] = Math.min(cost[i][j], c);
                }
                cost[i][j]+= sum(freq, i, j);// We add sum of frequencies from i to j (see first term in the above formula),
                // this is added because every search will go through root and one comparison
                // will be done for every search.
            }
        }
        return cost[0][n - 1];// returns the cost of optimized binary search tree
    }

    int sum(int freq[], int i, int j) {
        int s = 0;
        for (int k = i; k <= j; k++) {
            s += freq[k];
        }
        return s;
    }

    boolean findAllSubsetEqualSum(int set[], int n, int sum) {
        // Similar to findEqualPartitionSubSet
        /**
         * Given a set of non-negative integers, and a value sum, determine if there is a subset of the given set with sum equal to given sum. 

            Example: 

            Input: set[] = {3, 34, 4, 12, 5, 2}, sum = 9
            Output: True  
            There is a subset (4, 5) with sum 9.

            Input: set[] = {3, 34, 4, 12, 5, 2}, sum = 30
            Output: False
            There is no subset that add up to 30.

            For the recursive approach we will consider two cases. 

        Consider the last element and now the required sum = target sum – value of ‘last’ element and number of elements = total elements – 1
        Leave the ‘last’ element and now the required sum = target sum and number of elements = total elements – 1

        Following is the recursive formula for isSubsetSum() problem. 

        isSubsetSum(set, n, sum) 
        = isSubsetSum(set, n-1, sum) || 
        isSubsetSum(set, n-1, sum-set[n-1])
        Base Cases:
        isSubsetSum(set, n, sum) = false, if sum > 0 and n == 0
        isSubsetSum(set, n, sum) = true, if sum == 0 

        set[]={3, 4, 5, 2}
        sum=9
        (x, y)= 'x' is the left number of elements,
        'y' is the required sum
        
                    (4, 9)
                    {True}
                /        \  
                (3, 6)       (3, 9)
                    
                /    \        /   \ 
            (2, 2)  (2, 6)   (2, 5)  (2, 9)
            {True}  
            /   \ 
        (1, -3) (1, 2)  
        {False}  {True} 
                /    \
            (0, 0)  (0, 2)
            {True} {False} 
        
        Dynamic Programming:-

        So we will create a 2D array of size (arr.size() + 1) * (target + 1) of type boolean. The state DP[i][j] will be true if there exists a subset of elements from A[0….i] with sum value = ‘j’. The approach for the problem is: 

        if (A[i-1] > j)
        DP[i][j] = DP[i-1][j]
        else 
        DP[i][j] = DP[i-1][j] OR DP[i-1][j-A[i-1]]

        This means that if current element has value greater than ‘current sum value’ we will copy the answer for previous cases
        And if the current sum value is greater than the ‘ith’ element we will see if any of previous states have already experienced the sum=’j’ 
        OR any previous states experienced a value ‘j – A[i]’ which will solve our purpose.

        The below simulation will clarify the above approach: 

        set[]={3, 4, 5, 2}
        target=6
        
            0    1    2    3    4    5    6

        0   T    F    F    F    F    F    F

        3   T    F    F    T    F    F    F
            
        4   T    F    F    T    T    F    F   
            
        5   T    F    F    T    T    T    F

        2   T    F    T    T    T    T    T
         */
        boolean subset[][] = new boolean[sum + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            subset[0][i] = true;// If sum is 0, then answer is true
        }
        for (int i = 1; i <= sum; i++) {
            subset[i][0] = false;// If sum is not 0 and set is empty, then answer is false
        }
        for (int i = 1; i <= sum; i++) {
            for (int j = 1; j <= n; j++) {
                subset[i][j] = subset[i][j - 1];// excluding the element j to the sum i
                if (i >= set[j - 1]) {// if and only if the sum i is greater than the set[j-1] element,
                    // i.e. to decide whether the element j has to be included or not
                    subset[i][j] = subset[i][j] // exclude
                            || subset[i - set[j - 1]][j - 1];// set[j-1] means the jth element.

                }
            }
        }
        ArrayList<Integer> printList = new ArrayList<Integer>();
        printAllSubSetEqualSum(set, n, sum, subset, printList);
        return subset[sum][n];
    }

    int[] kadane(int a[]) {
        // result[0] - max contiguous sum result[1] -> start result [2] -> end
        int[] result = new int[] { Integer.MIN_VALUE, 0, -1 };
        // a Array would hold the row wise sum, we need to find the largest contiguous
        // sum in this 1 dimensional array and find the start and end index
        int currentSum = 0;
        int localStart = 0;// i.e start index in the 1 dimensional array
        for (int i = 0; i < a.length; i++) {// similar to max sub array sum
            currentSum += a[i];// max ending here.
            if (currentSum < 0) {
                currentSum = 0;
                localStart = i + 1;
            } else if (currentSum > result[0]) {// current sum greater than the max contiguous sum calculated so far.
                                                // (Max so far)
                result[0] = currentSum;
                result[1] = localStart;
                result[2] = i;
            }
        }
        if (result[2] == -1) {// end index not found, means that all numbers are negative
            // find the positive element
            result[0] = 0;
            for (int i = 0; i < a.length; i++) {
                if (a[i] > result[0]) {
                    result[0] = a[i];
                    result[1] = i;
                    result[2] = i;
                }
            }
        }

        return result;
    }

    void findMaxSuMInRectangleMatrix(int matrix[][]) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int left = 0, right = 0, top = 0, bottom = 0;
        int maxSum = Integer.MIN_VALUE;
        int currentResult[];
        for (int leftCols = 0; leftCols < cols; leftCols++) {
            int tmp[] = new int[rows];
            for (int rightCols = leftCols; rightCols < cols; rightCols++) {
                // For every pair of column do the following..
                // for leftCols=1, rightCols vary from 1...cols.
                for (int i = 0; i < rows; i++) {
                    // calculate the row wise sum
                    tmp[i] += matrix[i][rightCols];
                    //Calculate the row wise sum between leftCols and rightCols 
                }
                currentResult = kadane(tmp);// At the end of rightCols,we will be having the tmp with row wise sum from
                                            // leftCols to end of columns
                if (currentResult[0] > maxSum) {// Do this for every pair, and find the maximum sum of sub rectangular
                                                // matrix
                    // now that we have found the top and bottom from largest contiguous sum of 1d
                    // array.
                    maxSum = currentResult[0];// setting the maximum sum
                    top = currentResult[1];
                    bottom = currentResult[2];
                    left = leftCols;
                    right = rightCols;
                }
            }
        }
        System.out
                .println("MaxSum: " + maxSum + ", range: [(" + left + ", " + top + ")(" + right + ", " + bottom + ")]");

    }

    int countNoOfBinaryStringsWithoutConsecutive1s(int n) {
        /*
         * Given a positive integer N, count all possible distinct binary strings of
         * length N such that there are no consecutive 1’s.
         * 
         * Examples:
         * 
         * Input: N = 2 Output: 3 // The 3 strings are 00, 01, 10
         * 
         * Input: N = 3 Output: 5 // The 5 strings are 000, 001, 010, 100, 101
         * 
         * This problem can be solved using Dynamic Programming. Let a[i] be the number
         * of binary strings of length i which do not contain any two consecutive 1’s
         * and which end in 0.
         * 
         * Similarly, let b[i] be the number of such strings which end in 1.
         * 
         * We can append either 0 or 1 to a string ending in 0, but we can only append 0
         * to a string ending in 1. This yields the recurrence relation:
         * 
         * a[i] = a[i - 1] + b[i - 1] 
         * 
         * b[i] = a[i - 1]
         * 
         * 
         * First iteration
         * 
         * a[0] = {1}, b[0] = {1}
         * a[1] = a[0] + b[0] = 2: 2 strings of length 2 is possible: 00, 10
         * b[1] = a[0]: 1 string of length 2 is possible which ends in 1: 01 (There shouldn't be consecutive 1's)
         * 
         * a[1] = 2, b[1] = 1
         * a[2] = 3: 000, 010, 100
         * b[2] = 2: 001, 101
         */
        int a[] = new int[n + 1];
        int b[] = new int[n + 1];
        a[0] = 1;
        b[0] = 1;
        for (int i = 1; i < n; i++) {
            // Note that a[i] would always ends with 0..
            a[i] = a[i - 1] + b[i - 1]; // a[i-1] provides the number of binary string of length i-1 ending in 0
            // b[i-1] provides number of binary string of length i-1 ending in 1
            b[i] = a[i - 1];// appending 0 to a string ending in 1
            
        }
        return a[n - 1] + b[n - 1];
        // If we look at the pattern, this is related to fibonacci series.

    }

    void countBinaryStringTestData() {
        System.out.println("Number of binary strings without consecutive 1s formed with 3 chars is "
                + countNoOfBinaryStringsWithoutConsecutive1s(3));
    }

    int countBooleanParenthesization(char symb[], char oper[], int n) {
        // Returns count of all possible parenthesizations that lead to
        // result true for a boolean expression with symbols like true
        // and false and operators like &, | and ^ filled between symbols
        /*
         * Given a boolean expression with following symbols.
         * 
         * Symbols 
         * 'T' ---> true 
         * 'F' ---> false 
         * 
         * And following operators filled between
         * symbols
         * 
         * Operators 
         * & ---> boolean AND 
         * | ---> boolean OR 
         * ^ ---> boolean XOR 
         * 
         * Count the
         * number of ways we can parenthesize the expression so that the value of
         * expression evaluates to true.
         * 
         * Let the input be in form of two arrays one contains the symbols (T and F) in
         * order and other contains operators (&, | and ^}
         * 
         * Examples:
         * 
         * Input: symbol[] = {T, F, T} operator[] = {^, &} Output: 2 The given
         * expression is "T ^ F & T", it evaluates true in two ways "((T ^ F) & T)" and
         * "(T ^ (F & T))"
         * 
         * 
         * Input: symbol[]    = {T, F, F}
        operator[]  = {^, |}
        Output: 2
        The given expression is "T ^ F | F", it evaluates true
        in two ways "( (T ^ F) | F )" and "( T ^ (F | F) )". 

        Input: symbol[]    = {T, T, F, T}
            operator[]  = {|, &, ^}
        Output: 4
        The given expression is "T | T & F ^ T", it evaluates true
        in 4 ways ((T|T)&(F^T)), (T|(T&(F^T))), (((T|T)&F)^T) 
        and (T|((T&F)^T)). 
         */
        /*
         * Let T(i, j) represents the number of ways to parenthesize the symbols between
         * i and j (both inclusive) such that the subexpression between i and j
         * evaluates to true.
         */
        int T[][] = new int[n][n];
        /*
         * Let F(i, j) represents the number of ways to parenthesize the symbols between
         * i and j (both inclusive) such that the subexpression between i and j
         * evaluates to false.
         */
        int F[][] = new int[n][n];

        /*
         * // Fill diaginal entries first 
         * // All diagonal entries in T[i][i] are 1 if
         * symbol[i] is T (true). 
         * Similarly, all F[i][i] entries are 1 if symbol[i] is false.
         * 
         if Base Case:
         * 
         * T(i,i) = 1 if symbol[i] = 'T' T(i,i) = 0 if symbol[i] = 'F'
         * 
         * F(i,i) = 1, if symbol[i] = 'F' F(i,i) = 0, if symbol[i] = 'T'
         */
        for (int i = 0; i < n; i++) {
            T[i][i] = (symb[i] == 'T') ? 1 : 0;
            F[i][i] = (symb[i] == 'F') ? 1 : 0;
        }

        // Optimal substructure
        // Now fill T[i][i+1], T[i][i+2], T[i][i+3]... in order
        // And F[i][i+1], F[i][i+2], F[i][i+3]... in order
        for (int L = 2; L <= n; L++) {// chain length
            for (int i = 0; i < n - L + 1; i++) {
                int j = i + L - 1;
                T[i][j] = 0;
                F[i][j] = 0;
                for (int k = i; k < j; k++) {// K has to range from i to j-1
                    // Find place of parenthesization using current value
                    // of gap
                    // Find the total ways that we can parenthize
                    int tik = T[i][k] + F[i][k];//total ways before paranthesis
                    int tkj = T[k + 1][j] + F[k + 1][j];// total ways after paranthesis.

                    if (oper[k] == '&') {
                        T[i][j] += T[i][k] * T[k + 1][j];// for the and operator to be true, both subexpressions should
                                                         // be true
                        F[i][j] += (tik * tkj - T[i][k] * T[k + 1][j]); // For the and operator to be false, 1. either
                                                                        // of the expression has to be false 2. both has
                                                                        // to be false
                        // This can be achieved by calculating the total ways to parenthesize - number
                        // of ways in both subexpressions to be true.
                    }
                    if (oper[k] == '|') {
                        F[i][j] += F[i][k] * F[k + 1][j];// For the or operator to be false, both subexpressions has to
                                                         // be false
                        T[i][j] += (tik * tkj - F[i][k] * F[k + 1][j]);// for the or operator to be true, 1. either of
                                                                       // the expression has to be true 2. both has to
                                                                       // be true
                        // Total ways ot parenthesize - number of ways in both subexpressions is false
                    }
                    if (oper[k] == '^') {
                        T[i][j] += F[i][k] * T[k + 1][j] + T[i][k] * F[k + 1][j];// Xor operator to be true, the operands have to be different. [0 1] [1 0]
                        F[i][j] += T[i][k] * T[k + 1][j] + F[i][k] * F[k + 1][j];// X or operator to be false [1 1][0 0]
                    }
                }
            }
        }
        return T[0][n - 1];// Returns count of all possible parenthesization that returns true with the
                           // given symbols and operators.

    }

    int countWaysToReachNthStair(int n, int m) {
        /*
         * There are n stairs, a person standing at the bottom wants to reach the top.
         * The person can climb either 1 stair or 2 stairs at a time. Count the number
         * of ways, the person can reach the top.
         * 
         * Input: n = 1 Output: 1 There is only one way to climb 1 stair
         * 
         * Input: n = 2 Output: 2 There are two ways: (1, 1) and (2)
         * 
         * Input: n = 4 Output: 5 (1, 1, 1, 1), (1, 1, 2), (2, 1, 1), (1, 2, 1), (2, 2)
         * 
         * We can easily find recursive nature in above problem.
         * 
         * 
         * The person can reach n’th stair from either (n-1)’th stair or from (n-2)’th
         * stair.
         * 
         * Let the total number of ways to reach n’t stair be ‘ways(n)’. The value of
         * ‘ways(n)’ can be written as following.
         * 
         * ways(n) = ways(n-1) + ways(n-2) The above expression is actually the
         * expression for Fibonacci numbers, but there is one thing to notice, the value
         * of ways(n) is equal to fibonacci(n+1).
         * 
         * ways(1) = fib(2) = 1 ways(2) = fib(3) = 2 ways(3) = fib(4) = 3
         * 
         * How to count number of ways if the person can climb up to m stairs for a
         * given value m? For example if m is 4, the person can climb 1 stair or 2
         * stairs or 3 stairs or 4 stairs at a time.
         * 
         * We can write the recurrence as following.
         * 
         * ways(n, m) = ways(n-1, m) + ways(n-2, m) + ... ways(n-m, m)
         */
        int res[] = new int[n];
        // Base case: reaching 0th or 1th stair takes only one step..
        res[0] = 1;
        res[1] = 1;
        for (int i = 2; i < n; i++) {
            res[i] = 0;
            for (int j = 1; j <= m && j <= i; j++) {
                res[i] += res[i - j];// number of ways taking j step, j iterated within m
                // j should be less than or equal to i, why equal to i, if reaching 2 stair can
                // be achieved by taking 2 steps, then j is equal to i
            }
        }
        return res[n - 1];// callee has s+1
    }

    class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // to find distance between two points in a plane
    double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    // To calculate the cost of a triangle with three vertices i,j,k
    // it's sum of length of all edges in the triangle.(perimeter of the triangle)
    double cost(Point points[], int i, int j, int k) {
        Point p1 = points[i];
        Point p2 = points[j];
        Point p3 = points[k];
        return dist(p1, p2) + dist(p2, p3) + dist(p3, p1);// length of all edges in the triangle and it's sum
    }

    double minCostTriangulation(Point points[], int n) {

        if (n < 3) {
            // There must be 3 vertices to form a triangle, if not return 0
            return 0.0;
        }
        // The idea is to divide the polygon into three parts: a single triangle, the
        // sub-polygon to the left, and the sub-polygon to the right.

        // Refer http://www.geeksforgeeks.org/minimum-cost-polygon-triangulation/
        // We try all possible divisions like this and find the one that minimizes the
        // cost of the triangle plus the cost of the triangulation of the two
        // sub-polygons.
        /*
        Naive Problem:-
        Let Minimum Cost of triangulation of vertices from i to j be minCost(i, j)
        If j < i + 2 Then
        minCost(i, j) = 0
        Else
        minCost(i, j) = Min { minCost(i, k) + minCost(k, j) + cost(i, k, j) }
                        Here k varies from 'i+1' to 'j-1'

        Cost of a triangle formed by edges (i, j), (j, k) and (k, i) is 
        cost(i, j, k)  = dist(i, j) + dist(j, k) + dist(k, i)
         * Formula:-
         * 
         * Let Minimum Cost of triangulation of vertices from i to j be minCost(i, j) If
         * j <= i + 2 Then minCost(i, j) = 0 Else minCost(i, j) = Min { minCost(i, k) +
         * minCost(k, j) + cost(i, k, j) } cost(i,k,j) is the triangle formed,
         * minCost(i,k) is the cost of the triangle formed in the sub polygon to the
         * left minCost(k,j) is the cost of the triangle formed in the sub polyon to the
         * right. Here k varies from 'i+1' to 'j-1'
         * 
         * Cost of a triangle formed by edges (i, j), (j, k) and (k, j) is cost(i, j, k)
         * = dist(i, j) + dist(j, k) + dist(k, j)
         * 
         */
        // table to store results of subproblems. table[i][j] stores cost of
        // triangulation of points from i to j. The entry table[0][n-1] stores
        // the final result.
        double table[][] = new double[n][n];

        // We are going to achieve the above recursion through dynamic programming in
        // bottom up manner.
        // Fill table using above recursive formula. Note that the table
        // is filled in diagonal fashion i.e., from diagonal elements to
        // table[0][n-1] which is the result.
        for (int L = 3; L <= n; L++) {// Process the chain length from 3 onwards, as 3 vertices are required for
                                      // triangulation..
            for (int i = 0; i < n - L + 1; i++) {
                int j = i + L - 1;// i - row 2, j - col 5, we can process 3 and 4 vertices..
                if (j < i + 2) {// atleast 3 vertices is needed for processing.
                    // for example i - 1 j - 4 we can process k as 2, 3
                    // if i- 2 j-3 there is no k in between, and we can't triangulate the polygon
                    // with the vertices 2 and 3, hence set the table[i][j] to 0
                    table[i][j] = 0.0;
                } else {
                    table[i][j] = Integer.MAX_VALUE;// set the value to max..
                    for (int k = i + 1; k < j; k++) {// i+1 to j-1
                        // why iterating from i+1 to j-1, say if given 3 vertices , and formulate a
                        // triangle with these vertices (given i and j), then we need to iterate k from
                        // i+1 to j-1
                        double val = table[i][k] + table[k][j] + cost(points, i, j, k);
                        if (val < table[i][j]) {
                            table[i][j] = val;// minimum cost to triangulate the polygon..
                        }
                    }
                }
            }
        }
        return table[0][n - 1];

    }

    int getMobileKeyPadPossibleNumbersOfLengthOrTraverseMatrix(char[][] keypad, int n) {
        /*
         * Given the mobile numeric keypad. You can only press buttons that are up,
         * left, right or down to the current button. You are not allowed to press
         * bottom row corner buttons (i.e. * and # ). Given a number N, find out the
         * number of possible numbers of given length.
         * 
         * Examples: For N=1, number of possible numbers would be 10 (0, 1, 2, 3, …., 9)
         * For N=2, number of possible numbers would be 36 Possible numbers: 00,08
         * 11,12,14 22,21,23,25 and so on. If we start with 0, valid numbers will be 00,
         * 08 (count: 2) If we start with 1, valid numbers will be 11, 12, 14 (count: 3)
         * If we start with 2, valid numbers will be 22, 21, 23,25 (count: 4) If we
         * start with 3, valid numbers will be 33, 32, 36 (count: 3) If we start with 4,
         * valid numbers will be 44,41,45,47 (count: 4) If we start with 5, valid
         * numbers will be 55,54,52,56,58 (count: 5)
         */

        if (n == 0) {
            return 0;
        }
        // Base case: if the n is 1, then number of possible numbers would be 10
        if (n == 1) {
            return 10;
        }
        /*
         * For N > 1, we need to start from some button, then move to any of the four
         * direction (up, left, right or down) which takes to a valid button (should not
         * go to *, #).
         * 
         * Keep doing this until N length number is obtained (depth first traversal).
         * 
         * Mobile Keypad is a rectangular grid of 4X3 (4 rows and 3 columns)
         * 
         * Lets say Count(i, j, N) represents the count of N length numbers starting
         * from position (i, j)
         * 
         * If N = 1 Count(i, j, N) = 10 Else Count(i, j, N) = Sum of all Count(r, c,
         * N-1) where (r, c) is new position after valid move of length 1 from current
         * position (i, j)
         */

        // current, left, up, right, down move from current location..
        int row[] = new int[] { 0, 0, -1, 0, 1 };
        int col[] = new int[] { 0, -1, 0, 1, 0 };

        int[][] count = new int[10][n + 1];// we are having 10 numbers only and of length n
        // This matrix stores the number of possible ways of length j for numbers 0...9.

        // We are going to populate this matrix
        for (int i = 0; i <= 9; i++) {
            count[i][0] = 0;// length zero for all numbers is 0
            count[i][1] = 1;// length 1 for all numbers is 1
        }
        int num, nextNum;
        for (int k = 2; k <= n; k++) {
            // for length n, solve sub problems from 2 to n..
            for (int i = 0; i < 4; i++) {// only 4 rows in key pad

                for (int j = 0; j < 3; j++)// only 3 cols
                {
                    if (keypad[i][j] != '*' && keypad[i][j] != '#') {
                        // Except for * and # do the processing..

                        // Here we are counting the numbers starting with
                        // digit keypad[i][j] and of length k keypad[i][j]
                        // will become 1st digit, and we need to look for
                        // (k-1) more digits
                        num = keypad[i][j] - '0';// current number
                        count[num][k] = 0;

                        // move left, up, right, down from current location
                        // and if new location is valid, then get number
                        // count of length (k-1) from that new digit and
                        // add in count we found so far
                        for (int move = 0; move < 5; move++) {
                            int r = i + row[move];
                            int c = j + col[move];
                            if (r >= 0 && r <= 3 && c >= 0 && c <= 2 && keypad[r][c] != '*' && keypad[r][c] != '#') {
                                // calculate the cost
                                nextNum = keypad[r][c] - '0';// get the next number on move
                                count[num][k] += count[nextNum][k - 1];// add the count of next number with length k-1
                                // Note that the nextnum can be the same num. for example count[3][3] += count[3][2].
                            }
                        }
                    }
                }
            }
        }
        // Now that we have calculated the count of all digits of length k
        // Add them to the toal count..
        int totalCount = 0;
        for (int i = 0; i <= 9; i++) {
            totalCount += count[i][n];
        }
        return totalCount;
    }

    double sumDigitLookup[][] = new double[101][50001];


    double countOfNDigitEqualToSum(int n, int sum) {
        /**Given two integers ‘n’ and ‘sum’, find count of all n digit numbers with sum of digits as ‘sum’. Leading 0’s are not counted as digits. 
           1 <= n <= 100 and 
           1 <= sum <= 500

           Example: 

           Input:  n = 2, sum = 2
           Output: 2
           Explanation: Numbers are 11 and 20

           Input:  n = 2, sum = 5
           Output: 5
           Explanation: Numbers are 14, 23, 32, 41 and 50

           Input:  n = 3, sum = 6
           Output: 21 
           
           The idea is simple, we subtract all values from 0 to 9 from given sum and recur for sum minus that digit. Below is recursive formula.  

           countRec(n, sum) = ∑countRec(n-1, sum-x)
                                   where 0 =< x = 9

           One important observation is, leading 0's must be
           handled explicitly as they are not counted as digits.
           So our final count can be written as below.
           finalCount(n, sum) = ∑countRec(n-1, sum-x)
                               where 1 =< x = 9

           */

       if(n==0||sum==0){
           return 0.0;
       }

       for (int i = 0; i < sumDigitLookup.length; i++) {
           for (int j = 0; j < sumDigitLookup[0].length; j++) {
               sumDigitLookup[i][j] = -1;
           }
       }

       double ans = 0;
       for (int i = 1; i <= 9; i++) {// Leading zeros are avoided here..
           if (sum - i >= 0) {// this is required for example, if the sum is 7, going for 8 and 9 in i is
                              // useless
               ans += countOfNDigitSumUtil(n - 1, sum - i);
           }
       }
       return ans;
   }
    double countOfNDigitSumUtil(int n, int sum) {

        if (n == 0) {
            return sum == 0 ? 1.0 : 0.0;// if the sum is zero, then 1 element is found..
        }

        if (sumDigitLookup[n][sum] != -1) { //Memoization based approach.
            return sumDigitLookup[n][sum];// if the n digits whose sum is already calculated then return as such
        }

        double ans = 0;
        for (int i = 0; i <= 9; i++) {
            if (sum - i >= 0) {// only there is still sum left, recurse down..
                ans += countOfNDigitSumUtil(n - 1, sum - i);
            }
        }
        sumDigitLookup[n][sum] = ans;
        return sumDigitLookup[n][sum];
    }

    int minimumPositiveInitialPointsToReachDestination(int points[][]) {

        /*
         * Given a grid with each cell consisting of positive, negative or no points
         * i.e, zero points. We can move across a cell only if we have positive points (
         * > 0 ). Whenever we pass through a cell, points in that cell are added to our
         * overall points. We need to find minimum initial points to reach cell (m-1,
         * n-1) from (0, 0).
         * 
         * Constraints :
         * 
         * From a cell (i, j) we can move to (i+1, j) down or (i, j+1) right. We cannot move from
         * (i, j) if your overall points at (i, j) is <= 0. We have to reach at (n-1,
         * m-1) with minimum positive points i.e., > 0. Example:
         * 
         * Input: points[m][n] = { 
         * {-2, -3, 3}, 
         * {-5, -10, 1}, 
         * {10, 30, -5} }; Output: 7
         * 
         * Explanation: 7 is the minimum value to reach destination with positive
         * throughout the path. Below is the path.
         * 
         * (0,0) -> (0,1) -> (0,2) -> (1, 2) -> (2, 2)
         * 
         * We start from (0, 0) with 7, we reach(0, 1) with 5, (0, 2) with 2, (1, 2)
         * with 5, (2, 2) with and finally we have 1 point (we needed greater than 0
         * points at the end).
         * 
         * At the first look, this problem looks similar Max/Min Cost Path, but maximum overall points gained will not guarantee the minimum initial points. 
         * Also, it is compulsory in the current problem that the points never drops to zero or below. For instance, Suppose following two paths exists from source to destination cell.

        We can solve this problem through bottom-up table filling dynamic programing technique.

        To begin with, we should maintain a 2D array dp of the same size as the grid, where dp[i][j] represents the minimum points that guarantees the continuation of the journey to destination before entering the cell (i, j). 
        It’s but obvious that dp[0][0] is our final solution. Hence, for this problem, we need to fill the table from the bottom right corner to left top.
         * 
         * Now, let us decide minimum points needed to leave cell (i, j) (remember we
         * are moving from bottom to up).
         * 
         * There are only two paths to choose: (i+1, j) and (i, j+1). Of course we will
         * choose the cell that the player can finish the rest of his journey with a
         * smaller initial points. Therefore we have: min_Points_on_exit =
         * min(dp[i+1][j], dp[i][j+1])
         * 
         * Now we know how to compute min_Points_on_exit, but we need to fill the table
         * dp[][] to get the solution in dp[0][0].
         * 
         * How to compute dp[i][j]? The value of dp[i][j] can be written as below.
         * 
         * dp[i][j] = max(min_Points_on_exit – points[i][j], 1)
         * 
         * Let us see how above expression covers all cases.
         * 
         * If points[i][j] == 0, then nothing is gained in this cell; the player can
         * leave the cell with the same points as he enters the room with, i.e. dp[i][j]
         * = min_Points_on_exit.
         * 
         * If dp[i][j] < 0, then the player must have points greater than
         * min_Points_on_exit before entering (i, j) in order to compensate for the
         * points lost in this cell. The minimum amount of compensation is
         * " - points[i][j] ", so we have dp[i][j] = min_Points_on_exit - points[i][j].
         * 
         * If dp[i][j] > 0, then the player could enter (i, j) with points as little as
         * min_Points_on_exit – points[i][j]. since he could gain “points[i][j]” points
         * in this cell. However, the value of min_Points_on_exit – points[i][j] might
         * drop to 0 or below in this situation. When this happens, we must clip the
         * value to 1 in order to make sure dp[i][j] stays positive:
         * 
         * 
         * 
         */
        int m = points.length;
        int n = points[0].length;
        // dp[i][j] represents the minimum initial points player
        // should have so that when starts with cell(i, j) successfully
        // reaches the destination cell(m-1, n-1)
        int dp[][] = new int[m][n];

        // Base case
        dp[m - 1][n - 1] = points[m - 1][n - 1] > 0 ? 1 : (Math.abs(points[m - 1][n - 1]) + 1);// even if the last cell
                                                                                               // is negative, we are
                                                                                               // making it poisitve and
                                                                                               // dding 1, this is
                                                                                               // because to reach
                                                                                               // destination one would
                                                                                               // require
        // negative points + 1, if the cell is negative
        // 1 itself is enough, if the cell is postive

        // Fill the last row and last column for constructing solution in bottom up
        // manner.
        // note that, we are going from bottom to up

        // fill the last column
        for (int i = m - 2; i >= 0; i--) { // start from last row and traverse up
            dp[i][n - 1] = Math.max(dp[i + 1][n - 1] - points[i][n - 1], 1);//n-1 is the last column.
        }
        
        // fill the last row
        for (int j = n - 2; j >= 0; j--) {//start from the last column and traverse left most.
            dp[m - 1][j] = Math.max(dp[m - 1][j + 1] - points[m - 1][j], 1);// m-1 is the last row
        }

        for (int i = m - 2; i >= 0; i--) {//row
            for (int j = n - 2; j >= 0; j--) {//col
                int min_count_on_exit = Math.min(dp[i][j + 1], dp[i + 1][j]);// allowed to traverse right and down, get
                                                                             // the minimum of the two, as we were to
                                                                             // find the minimum initial points required
                                                                             // to reach destination, stated in the
                                                                             // given expression
                dp[i][j] = Math.max(min_count_on_exit - points[i][j], 1);

                /*
                 * example:
                 * 
                 * for all positive points which are greater than min_count_on_exit
                 * 
                 * we will obtain min_count_on_exit as negative,
                 * 
                 * dp[i][j] should be 1, because reaching the cell i,j would require cost of 1 
                 * (as the points gained in the cell will help reach the destination given positive point in the cell > min point required to reach destination.)
                 * and thereafter moving from i,j to somewhere else, they would gain this
                 * positive max point..
                 * 
                 */
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println("");
        }

        return dp[0][0];

    }

    double findTotalNonDecreasingNumbersWithNDigits(int n) {
        /*
         * A number is non-decreasing if every digit (except the first one) is greater
         * than or equal to previous digit. For example, 223, 4455567, 899, are
         * non-decreasing numbers.
         * 
         * So, given the number of digits n, you are required to find the count of total
         * non-decreasing numbers with n digits.
         * 
         * Examples:
         * 
         * Input: n = 1 Output: count = 10
         * 
         * Input: n = 2 Output: count = 55
         * 
         * Input: n = 3 Output: count = 220
         * 
         * One way to look at the problem is, count of numbers is equal to count n digit number ending with 9 plus count of ending with digit 8 plus count for 7 and so on. How to get count ending with a particular digit? We can recur for n-1 length and digits smaller than or equal to the last digit. So below is recursive formula.

        Count of n digit numbers = (Count of (n-1) digit numbers Ending with digit 9) +
                                    (Count of (n-1) digit numbers Ending with digit 8) +
                                    .............................................+ 
                                    .............................................+
                                    (Count of (n-1) digit numbers Ending with digit 0) 
         * 
         * Let count ending with digit ‘d’ and length n be count(n, d)
         * 
         * count(n, d) = ∑ (count(n-1, i)) where i varies from 0 to d
         * 
         * say d is 5
         * 
         * to maintain non decreasing order i should vary from 0 to 5 and calculate the
         * count on n-1 length recursively
         * 
         */
        double dp[][] = new double[10][n + 1];// 10 digits of length n
        // digits ending with d, of length n
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = 0;
            }
        }
        // Base case 0 1 2 3 4 5 6 7 8 9
        for (int i = 0; i < 10; i++) {
            dp[i][1] = 1;// fill table for non decreasing numbers of length 1
        }

        for (int digit = 0; digit <= 9; digit++) {

            for (int len = 2; len <= n; len++) {
                // Compared to other for loops you have seen in DP, this is different because the problem statement is find the count of non-decreasing numbers with N digits, 
                // so ex., when we find the count for digit 8 with length 7, we already need to have the count for digit<=8, and length 7-1 (6).
                // len 1 is already covered

                // Need to ensure that non decreasing order is maintained

                // sum of all numbers of length of len-1 in which last digit x is <=digit
                for (int x = 0; x <= digit; x++) {
                    // say digit is 5, iterate from 0 to 5
                    dp[digit][len] += dp[x][len - 1];
                    // for this digit 5, get all the count of numbers of len-1 iterating from 0 to 5

                    // for length 2, digit 3
                    // number of digits of length 1 is 0,1,2 - 3 numbers are possible
                    // hence 03, 13, 23 are the non decreasing numbers..
                }
            }
        }
        double totalCount = 0;
        for (int digit = 0; digit <= 9; digit++) {
            totalCount += dp[digit][n];// get count of numbers of length n ending with all digits, in non decreasing
                                       // order..
        }

        return totalCount;

    }

    boolean isAdjacent(char prev, char cur) {
        return (cur - prev) == 1;
    }

    boolean isValid(int i, int j, char mat[][]) {
        int R = mat.length;
        int C = mat[0].length;
        if (i < 0 || j < 0 || i >= R || j >= C) {
            return false;
        }
        return true;
    }

    int getLongestConsecutiveCharMatrixUtil(char mat[][], int i, int j, char prev, int dp[][]) {
        // prev - previous character visited
        // mat[i][j] is the current character we are in
        if (!isValid(i, j, mat) || !isAdjacent(prev, mat[i][j])) {
            return 0;// not valid or adjacent to each other, return as such
        }
        if (dp[i][j] != -1) { //memoization approach.
            return dp[i][j];// if the length is already calculated then return it
        }
        // tool matrices to recur for adjacent cells.
        int x[] = { 0, 1, 1, -1, 1, 0, -1, -1 };
        int y[] = { 1, 0, 1, 1, -1, -1, 0, -1 };

        int ans = 0;
        for (int k = 0; k < x.length; k++) {
            ans = Math.max(ans, 1 + getLongestConsecutiveCharMatrixUtil(mat, i + x[k], j + y[k], mat[i][j], dp));
        }
        return ans;
    }

    void getLongestConsecutivePathCharMatrixGivenStartCharacter(char mat[][], char start) {

        int R = mat.length;
        int C = mat[0].length;
        int dp[][] = new int[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                dp[i][j] = -1;
            }
        }

        int ans = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (mat[i][j] == start) {
                    // as we encounter the starting character calculate the longest consecutive
                    // character length
                    int x[] = { 0, 1, 1, -1, 1, 0, -1, -1 };
                    int y[] = { 1, 0, 1, 1, -1, -1, 0, -1 };
                    for (int k = 0; k < x.length; k++) {
                        ans = Math.max(ans,
                                1 + getLongestConsecutiveCharMatrixUtil(mat, i + x[k], j + y[k], mat[i][j], dp));
                    }
                }
            }
        }
        System.out.println("Longest consecutive path length from start character " + start + " is " + ans);

    }

    void tilingProblem() {
        /*
         * Given a “2 x n” board and tiles of size “2 x 1″, count the number of ways to
         * tile the given board using the 2 x 1 tiles. A tile can either be placed
         * horizontally i.e., as a 1 x 2 tile or vertically i.e., as 2 x 1 tile.
         * 
         * Examples:
         * 
         * Input n = 3 Output: 3 Explanation: We need 3 tiles to tile the board of size
         * 2 x 3. We can tile the board using following ways 1) Place all 3 tiles
         * vertically. 2) Place first tile vertically and remaining 2 tiles
         * horizontally. 3) Place first 2 tiles horizontally and remaining tiles
         * vertically
         * 
         * Input n = 4 Output: 5 Explanation: For a 2 x 4 board, there are 5 ways 1) All
         * 4 vertical 2) All 4 horizontal 3) First 2 vertical, remaining 2 horizontal 4)
         * First 2 horizontal, remaining 2 vertical 5) Corner 2 vertical, middle 2
         * horizontal
         * 
         * 
         * Let “count(n)” be the count of ways to place tiles on a “2 x n” grid, we have
         * following two ways to place first tile. 1) If we place first tile vertically,
         * the problem reduces to “count(n-1)” 2) If we place first tile horizontally,
         * we have to place second tile also horizontally. So the problem reduces to
         * “count(n-2)”
         * 
         * Therefore, count(n) can be written as below.
         * 
         * count(n) = n if n = 1 or n = 2 count(n) = count(n-1) + count(n-2)
         * 
         * Ex., count(4) = count(3) when placed vertically + count(2) when placed horizontally.
         * 
         * The above recurrence is noting but Fibonacci Number expression.
         */
        // Image: https://media.geeksforgeeks.org/wp-content/cdn-uploads/tilingproblem.png
    }

    int getMinNumberOfSquaresWhoseSuMEqualsToGivenNumberN(int n) {
        // Returns count of minimum squares that sum to n
        /*
         * A number can always be represented as a sum of squares of other numbers. Note
         * that 1 is a square and we can always break a number as (1*1 + 1*1 + 1*1 + …).
         * Given a number n, find the minimum number of squares that sum to X.
         * 
         * Examples:
         * 
         * Input: n = 100 Output: 1 
         * 
         * 100 can be written as 10^2. Note that 100 can also be
         * written as 5^2 + 5^2 + 5^2 + 5^2, but this representation requires 4 squares.
         * 
         * Input: n = 6 Output: 3
         * 
         * The idea is simple, we start from 1 and go till a number whose square is
         * smaller than or equals to n. For every number x, we recur for n-x. Below is
         * the recursive formula.
         * 
         * // Go through all smaller numbers
            // to recursively find minimum
            for (int x = 1; x <= n; x++)
            {
                int temp = x * x;
                if (temp > n)
                    break;
                else
                    res = min(res, 1 + getMinSquares
                                        (n - temp));
            }   
         * 
         * If n <= 3, then return n Else minSquares(n) = min {1 + minSquares(n - x*x)}
         * where x >= 1 and x*x <= n
         */
        int minSquare[] = new int[n + 1];// dp stores the count of minimum squares that sum to i
        minSquare[0] = 0;
        minSquare[1] = 1;
        minSquare[2] = 2;
        minSquare[3] = 3;

        for (int i = 4; i <= n; i++) {// calculate the sub problem, dp[n] would contain the minimum number of squares
                                      // summing to n.
            // max value is i as i can always be represented
            // as 1*1 + 1*1 + ...
            minSquare[i] = i;
            for (int x = 1; x <= i; x++) {
                int temp = x * x;
                if (temp > i) {// if the sum is greater than i, then break out of the loop
                    break;
                }
                // otherwise, we need to consider adding this temp to count
                else {
                    minSquare[i] = Math.min(minSquare[i]// already calculated count
                            , 1 + minSquare[i - temp]);// adding 1 to the count
                    // example, for i -> 6 2*2 => 4 dp[6-4] = dp[2] + 2*2
                }
            }
        }

        return minSquare[n];

    }

    int getMinNumberOfCoinsToMakeAChange(int coins[], int V) {
        /*
         * Given a value V, if we want to make change for V cents, and we have infinite
         * supply of each of C = { C1, C2, .. , Cm} valued coins, what is the minimum
         * number of coins to make the change?
         * 
         * Examples:
         * 
         * Input: coins[] = {25, 10, 5}, V = 30 Output: Minimum 2 coins required We can
         * use one coin of 25 cents and one of 5 cents
         * 
         * Input: coins[] = {9, 6, 5, 1}, V = 11 Output: Minimum 2 coins required We can
         * use one coin of 6 cents and 1 coin of 5 cents
         * 
         * This problem is a variation of the problem discussed Coin Change Problem.
         * Here instead of finding total number of possible solutions, we need to find
         * the solution with minimum number of coins.
         * 
         * The minimum number of coins for a value V can be computed using below
         * recursive formula.
         * 
         * If V == 0, then 0 coins required. If V > 0 minCoin(coins[0..m-1], V) = min {1
         * + minCoins(V-coin[i])} where i varies from 0 to m-1 and coin[i] <= V
         */
        int m = coins.length;
        int table[] = new int[V + 1];// table[i] will represent the minimum number of coins required to make a change
                                     // for i

        for (int i = 1; i <= V; i++) {// must start from 1
            table[i] = Integer.MAX_VALUE;// Initialize the value to infinity
        }
        // table[0] is zero by default.

        for (int i = 1; i <= V; i++) {
            // calculate the minimum number of coins for sub problems
            for (int j = 0; j < m; j++) {
                if (coins[j] <= i) {// coin value must be less than V
                    int sub_res = table[i - coins[j]];// get the minimum number of coins required for i-coins[j] value
                    if (sub_res != Integer.MAX_VALUE && sub_res + 1 < table[i]) {// adding this coin to the result
                                                                                 // guaranteees the minimum
                        table[i] = sub_res + 1;
                    }
                }
            }
        }
        return table[V];
    }

    boolean isValid(int x, int y1, int y2, int arr[][]) {
        int R = arr.length;
        int C = arr[0].length;
        return (x >= 0 && x < R && y1 >= 0 && y2 < C && y2 >= 0 && y2 < C);

    }

    int collectMaxPointsInGridUsingTwoTraversal(int arr[][], int mem[][][], int x, int y1, int y2) {

        /*
         * 
         * Given a matrix where every cell represents points. How to collect maximum
         * points using two traversals under following conditions?
         * 
         * Let the dimensions of given grid be R x C.
         * 
         * 1) The first traversal starts from top left corner, i.e., (0, 0) and should
         * reach left bottom corner, i.e., (R-1, 0). The second traversal starts from
         * top right corner, i.e., (0, C-1) and should reach bottom right corner, i.e.,
         * (R-1, C-1)/
         * 
         * 2) From a point (i, j), we can move to (i+1, j+1) or (i+1, j-1) or (i+1, j)
         * 
         * 3) A traversal gets all points of a particular cell through which it passes.
         * If one traversal has already collected points of a cell, then the other
         * traversal gets no points if goes through that cell again.
         * 
         * Input : int arr[R][C] = {
         * {3, 6, 8, 2}, 
         * {5, 2, 4, 3}, 
         * {1, 1, 20, 10}, 
         * {1, 1, 20, 10}, 
         * {1, 1, 20, 10}, };
         * 
         * Output: 73 First traversal collects total points of value 3 + 2 + 20 + 1 + 1
         * = 27
         * 
         * Second traversal collects total points of value 2 + 4 + 10 + 20 + 10 = 46.
         * 
         * Total Points collected = 27 + 46 = 73.
         * 
         */

        // Idea here is to do the traversal simulatenously, We start first from (0, 0)
        // and second traversal from (0, C-1) simultaneously.
        // The important thing to note is, at any particular step both traversals will
        // be in same row as in all possible three moves, row number is increased. Let
        // (x1, y1) and (x2, y2) denote current positions of first and second traversals
        // respectively.
        // Thus at any time x1 will be equal to x2 as both of them move forward but
        // variation is possible along y

        if (!isValid(x, y1, y2, arr)) {
            return Integer.MIN_VALUE; // return the min value if the data is invalid.
        }
        int R = arr.length;
        int C = arr[0].length;
        if (x == R - 1 && y1 == 0 && y2 == C - 1)// destination is reached by both traversals, return the sum
        {
            return arr[x][y1] + arr[x][y2];
        }

        if (x == R - 1) {
            // last is reached, but not reached the destination.
            return Integer.MIN_VALUE;
        }

        if (mem[x][y1][y2] != -1) {// If the data is alreayd calculated, then return as such.
            return mem[x][y1][y2];
        }

        int temp = (y1 == y2) ? arr[x][y1] : arr[x][y1] + arr[x][y2];
        // if two traversal meeting at the same location, then only that cell's value
        // shall be added only once.

        int ans = Integer.MIN_VALUE; // We are going to get the maximum value

        /*
         * Recur for all possible cases, then store and return the one with max value
         */
        // As per the constraint traversing as given
        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1, y2));
        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1, y2 + 1));
        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1, y2 - 1));

        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1 + 1, y2));
        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1 + 1, y2 + 1));
        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1 + 1, y2 - 1));

        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1 - 1, y2));
        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1 - 1, y2 + 1));
        ans = Math.max(ans, temp + collectMaxPointsInGridUsingTwoTraversal(arr, mem, x + 1, y1 - 1, y2 - 1));

        mem[x][y1][y2] = ans;

        return ans;
    }

    void collectMaxPointsInGridUsingTwoTraversalTestData() {
        int arr[][] = new int[][] { { 3, 6, 8, 2 }, { 5, 2, 4, 3 }, { 1, 1, 20, 10 }, { 1, 1, 20, 10 },
                { 1, 1, 20, 10 }, };
        int R = arr.length;
        int C = arr[0].length;
        int mem[][][] = new int[R][C][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                for (int k = 0; k < C; k++) {
                    mem[i][j][k] = -1;
                }
            }
        }
        System.out.println(
                "Maximum points collected is " + collectMaxPointsInGridUsingTwoTraversal(arr, mem, 0, 0, C - 1));
    }

    int shortestSuperSequence(String x, String y) {
        /*
         * Given two strings str1 and str2, find the shortest string that has both str1
         * and str2 as subsequences.
         * 
         * Examples:
         * 
         * Input: str1 = "geek", str2 = "eke" Output: "geeke"
         * 
         * Input: str1 = "AGGTAB", str2 = "GXTXAYB" Output: "AGXGTXAYB"
         * 
         * 1) Find Longest Common Subsequence (lcs) of two given strings. For example,
         * lcs of “geek” and “eke” is “ek”.
         * 
         * 2) Insert non-lcs characters (in their original order in strings) to the lcs
         * found above, and return the result. So “ek” becomes “geeke” which is shortest
         * common supersequence.
         * 
         * Let us consider another example, str1 = “AGGTAB” and str2 = “GXTXAYB”. LCS of
         * str1 and str2 is “GTAB”. Once we find LCS, we insert characters of both
         * strings in order and we get “AGXGTXAYB”
         * 
         * How does this work?
         * 
         * We need to find a string that has both strings as subsequences and is
         * shortest such string. If both strings have all characters different, then
         * result is sum of lengths of two given strings. If there are common
         * characters, then we don’t want them multiple times as the task is to minimize
         * length. Therefore, we fist find the longest common subsequence, take one
         * occurrence of this subsequence and add extra characters.
         * 
         * 
         * Length of the shortest supersequence = (Sum of lengths of given two strings)
         * - (Length of LCS of two given strings)
         * 
         */
        int m = x.length();
        int n = y.length();
        int l = lcs(x.toCharArray(), y.toCharArray(), m, n);
        return m + n - l;// If there is a common sequence, we need not count that twice, only once that
                         // should be considered.
    }

    int sumOfDigitsFrom1ToN(int n) {

        /*
         * Let us take few examples.
         * 
         * sum(9) = 1 + 2 + 3 + 4 ........... + 9 = 9*10/2 = 45
         * 
         * sum(99) = 45 + (10 + 45) + (20 + 45) + ..... (90 + 45) = 45*10 + (10 + 20 +
         * 30 ... 90) = 45*10 + 10(1 + 2 + ... 9) = 45*10 + 45*10 = sum(9)*10 + 45*10
         * 
         * sum(999) = sum(99)*10 + 45*100
         * 
         * 
         * Algorithm: sum(n)
         * 
         * 1) Find number of digits minus one in n. Let this value be 'd'. For 328, d is
         * 2.
         * 
         * 2) Compute some of digits in numbers from 1 to 10d - 1. Let this sum be w.
         * For 328, we compute sum of digits from 1 to 99 using above formula.
         * 
         * 3) Find Most significant digit (msd) in n. For 328, msd is 3.
         * 
         * 4) Overall sum is sum of following terms
         * 
         * a) Sum of digits in 1 to "msd * 10d - 1". For 328, sum of digits in numbers
         * from 1 to 299. 10 to the power of d. For 328, we compute 3*sum(99) + (1 +
         * 2)*100. Note that sum of sum(299) is sum(99) + sum of digits from 100 to 199
         * + sum of digits from 200 to 299. Sum of 100 to 199 is sum(99) + 1*100 and sum
         * of 299 is sum(99) + 2*100. In general, this sum can be computed as w*msd +
         * (msd*(msd-1)/2)*10d
         * 
         * b) Sum of digits in msd * 10d to n. For 328, sum of digits in 300 to 328. 29
         * times has 3 occurred -> from 300, 301,302.. to 328 For 328, this sum is
         * computed as 3*29 + recursive call "sum(28)" In general, this sum can be
         * computed as msd * (n % (10d) + 1) + sum(n % (10d))
         */
        if (n < 10) {
            return n * (n + 1) / 2;// if n is less than 10, then do calculating the sum of n natural numbers
        }

        double digit = Math.log10(n);// provides the number of digits minus 1
        int d = (int) digit;

        int a[] = new int[d + 1];
        a[0] = 0;
        a[1] = 45;// sum(9)
        for (int i = 2; i <= d; i++) {// if the digit is of length 2, then calculate a[d]
            a[i] = a[i - 1] * 10 + 45 * ((int) Math.ceil(Math.pow(10, i - 1)));// sum(999) = sum(99)*10 + 45*100 -> as
                                                                               // per the formulae.
        }

        int p = (int) Math.pow(10, d);

        int msd = n / p;// get the most siginificant digit..
        System.out.println("Msd is " + msd + " " + n % p);
        return msd * a[d] + (msd * (msd - 1) / 2) * p // This term calculates the step (a)
                + msd * (1 + n % p) + // This calculates 3*29, we would be having 3 in terms from 300 to 328 (hence 29
                                      // times 3 occurs)
                sumOfDigitsFrom1ToN(n % p);// calculate recursively for sum(28)

        // EXPLANATION FOR FIRST and SECOND TERMS IN BELOW LINE OF CODE
        // First two terms compute sum of digits from 1 to 299
        // (sum of digits in range 1-99 stored in a[d]) +
        // (sum of digits in range 100-199, can be calculated as 1*100 + a[d]
        // (sum of digits in range 200-299, can be calculated as 2*100 + a[d]
        // The above sum can be written as 3*a[d] + (1+2)*100

        // EXPLANATION FOR THIRD AND FOURTH TERMS IN BELOW LINE OF CODE
        // The last two terms compute sum of digits in number from 300 to 328
        // The third term adds 3*29 to sum as digit 3 occurs in all numbers
        // from 300 to 328
        // The fourth term recursively calls for 28
    }

    int countPossibleWaysToConstructBuildings(int n) {
        /*
         *   Similar to binary problem where two consecutive 1's shouldn't exist.

         * Given an input number of sections and each section has 2 plots on either
         * sides of the road. Find all possible ways to construct buildings in the plots
         * such that there is a space between any 2 buildings.
         * 
         * Example:
         * 
         * N = 1 Output = 4 Place a building on one side. Place a building on other side
         * Do not place any building. Place a building on both sides.
         * 
         * N = 3 Output = 25 3 sections, which means possible ways for one side are BSS,
         * BSB, SSS, SBS, SSB where B represents a building and S represents an empty
         * space
         * 
         * if one side has BSS then there is 4 possible ways (BSB,SSS,SBS,SSB) on the
         * other side For each pattern in one side, there will be 4 possible ways on the
         * other side. (total - 5 in one section) There is 5 pattern, hence (5*5) 
         * -> 25 possible ways to construct buildings.
         * 
         * Total possible ways are 25, because a way to place on one side can correspond
         * to any of 5 ways on other side.
         * 
         * N = 4 Output = 64 We can simplify the problem to first calculate for one side
         * only. If we know the result for one side, we can always do square of the
         * result and get result for two sides.
         * 
         * A new building can be placed on a section if section just before it has
         * space. A space can be placed anywhere (it doesn’t matter whether the previous
         * section has a building or not).
         * 
         * Let countB(i) be count of possible ways with i sections ending with a
         * building. countS(i) be count of possible ways with i sections ending with a
         * space.
         * 
         * // A space can be added after a building or after a space. countS(N) =
         * countB(N-1) + countS(N-1)
         * 
         * // A building can only be added after a space. countB[N] = countS(N-1)
         * 
         * // Result for one side is sum of the above two counts. result1(N) = countS(N)
         * + countB(N)
         * 
         * // Result for two sides is square of result1(N) result2(N) = result1(N) *
         * result1(N)
         */

        if (n == 1)
            return 4;

        int countS, countB, prev_count_S, prev_count_B;
        countS = 1;
        countB = 1;
        //res for 1st section is 4. 2 on one side. 2*2: 4 for 2 sides.
        // 2nd section is 9. 3 on one side. 3*3: 9 for both the sides
        for (int i = 2; i <= n; i++) {

            prev_count_S = countS;// get the previous count
            prev_count_B = countB;

            countS = prev_count_B + prev_count_S;// A space can be added after a building or after a space.
            countB = prev_count_S;// a building can be added after a space.

        }
        int result1 = countB + countS;// result for one side is sum of the above two counts
        int result2 = result1 * result1;

        return result2;
    }

    // Returns maximum profit with two transactions on a given
    // list of stock prices, price[0..n-1]
    int getMaxProfitFromStockPriceWith2Transactions(int price[]) {
        /*
         * In a daily share trading, a buyer buys shares in the morning and sells it on
         * same day. If the trader is allowed to make at most 2 transactions in a day,
         * where as second transaction can only start after first one is complete
         * (Sell->buy->sell->buy). Given stock prices throughout day, find out maximum
         * profit that a share trader could have made.
         * 
         * Examples:
         * 
         * Input: price[] = {10, 22, 5, 75, 65, 80} Output: 87 Trader earns 87 as sum of
         * 12 and 75 Buy at price 10, sell at 22, buy at 5 and sell at 80
         * 
         * Input: price[] = {2, 30, 15, 10, 8, 25, 80} Output: 100 Trader earns 100 as
         * sum of 28 and 72 Buy at price 2, sell at 30, buy at 8 and sell at 80
         * 
         * Input: price[] = {100, 30, 15, 10, 8, 25, 80}; Output: 72 Buy at price 8 and
         * sell at 80.
         * 
         * Input: price[] = {90, 80, 70, 60, 50} Output: 0 Not possible to earn.
         * 
         * Formula: Max profit with at most two transactions = MAX {max profit with one
         * transaction and subarray price[0..i] + max profit with one transaction and
         * aubarray price[i+1..n-1] }
         * 
         * We can do this O(n) using following Efficient Solution. The idea is to store
         * maximum possible profit of every subarray and solve the problem in following
         * two phases.
         * 
         * 1) Create a table profit[0..n-1] and initialize all values in it 0.
         * 
         * 2) Traverse price[] from right to left and update profit[i] such that
         * profit[i] stores maximum profit achievable from one transaction in subarray
         * price[i..n-1]
         * 
         * 3) Traverse price[] from left to right and update profit[i] such that
         * profit[i] stores maximum profit such that profit[i] contains maximum
         * achievable profit from two transactions in subarray price[0..i].
         * 
         * 4) Return profit[n-1]
         * 
         * To do step 1, we need to keep track of maximum price from right to left side
         * and to do step 2, we need to keep track of minimum price from left to right.
         * Why we traverse in reverse directions? The idea is to save space, in second
         * step, we use same array for both purposes, maximum with 1 transaction and
         * maximum with 2 transactions. After an iteration i, the array profit[0..i]
         * contains maximum profit with 2 transactions and profit[i+1..n-1] contains
         * profit with two transactions.
         */

        int n = price.length;
        int profit[] = new int[n];
        // profit[n-1] would return the result

        for (int i = 0; i < n; i++) {
            profit[i] = 0;// initialize the profit value to 0
        }

        int max_price = price[n - 1];

        /*
         * Get the maximum profit with only one transaction allowed. After this loop,
         * profit[i] contains maximum profit from price[i..n-1] using at most one trans.
         */

        // Traverse from right to left, update profit[i]
        // This traversal will store only one transaction Buy->Sell
        for (int i = n - 2; i >= 0; i--) {
            // max_price has maximum of price[i..n-1]
            if (price[i] > max_price) {
                max_price = price[i];
            }

            profit[i] = Math.max(profit[i + 1], // already calculated profit from i+1..n
                    max_price - price[i]);// Or buy selling at the price i

            // we can get profit[i] by taking maximum of:
            // a) previous maximum, i.e., profit[i+1]
            // b) profit by buying at price[i] and selling at
            // max_price

        }

        // Traverse from left to right, update profit[i] such that the already done
        // transaction has to be maintained..
        int min_price = price[0];
        for (int i = 1; i < n; i++) {
            /*
             * Get the maximum profit with two transactions allowed After this loop,
             * profit[n-1] contains the result
             */
            if (price[i] < min_price) {
                min_price = price[i];
            }
            // min_price is minimum price in price[0..i]
            profit[i] = Math.max(profit[i - 1], // already calculated profit from 0..i-1
                    profit[i] + (price[i] - min_price));// the profit done in first transaction + buying at the minimum
                                                        // price and selling at the ith price

            // Maximum profit is maximum of:
            // a) previous maximum, i.e., profit[i-1]
            // b) (Buy, Sell) at (min_price, price[i]) and add
            // profit of other trans. stored in profit[i]
        }

        System.out.println("Maximum profit obtained is " + profit[n - 1]);

        return profit[n - 1];
    }

    int printMaxNoOfAsUsingFourKeys(int N) {

        /*
         * Below is the problem statement.
         * 
         * Imagine you have a special keyboard with the following keys: Key 1: Prints
         * 'A' on screen Key 2: (Ctrl-A): Select screen Key 3: (Ctrl-C): Copy selection
         * to buffer Key 4: (Ctrl-V): Print buffer on screen appending it after what has
         * already been printed.
         * 
         * If you can only press the keyboard for N times (with the above four keys),
         * write a program to produce maximum numbers of A's. That is to say, the input
         * parameter is N (No. of keys that you can press), the output is M (No. of As
         * that you can produce).
         * 
         * Examples:
         * 
         * Input: N = 3 Output: 3 We can at most get 3 A's on screen by pressing
         * following key sequence. A, A, A
         * 
         * Input: N = 7 Output: 9 We can at most get 9 A's on screen by pressing
         * following key sequence. A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
         * 
         * Input: N = 11 Output: 27 We can at most get 27 A's on screen by pressing
         * following key sequence. A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V, Ctrl A, Ctrl
         * C, Ctrl V, Ctrl V
         * 
         */

        /*
         * Resolving the problem is to find a break point b after which there will be
         * only Ctrl A, C, V.. obtaining the maximum length
         * 
         * 
         */
        if (N <= 6)// why less than 6, N before 6 optimal length that can be obtained is 6 itself
            return N;

        int b;// to pick a break point

        int screen[] = new int[N];//allowed number of key presses

        for (int i = 1; i <= 6; i++) {
            screen[i - 1] = i;// o will have 1. 1 will have 2
        }

        for (int n = 7; n <= N; n++) {// bottom up manner, substructure

            screen[n - 1] = 0;// initialize the optimal length

            // For any keystroke n, we need to loop from n-3 keystrokes
            // back to 1 keystroke to find a breakpoint 'b' after which we
            // will have ctrl-a, ctrl-c and then only ctrl-v all the way.

            for (b = n - 3; b >= 1; b--) {// why n-3, we need 3 key strokes for ctrl a, ctrl c, ctrl v
                // if the breakpoint is at b'th keystroke then
                // the optimal string would have length
                // (n-b-1)*screen[b-1];
                int res = (n - b - 1) * screen[b - 1];
                // screen[b-1] -> max result obtained at break point b
                // n-b-1, number of places left for ctrl a, c, and followed by v's only

                // for example
                /*
                 * N = 7 b = 4 screen[3] = 4 n-b-1 = 7-4-1 = 2 If we had a break point at
                 * position 4, we can obtain max of 8 optimal length
                 * 
                 * b= 3 screen[2] = 3; n-b-1 = 7-3-1 = 3 if we had a break point at position 3,
                 * we can obtain max of 9 optimal length
                 * 
                 */

                if (screen[n - 1] < res) {
                    screen[n - 1] = res;
                }
            }
        }

        return screen[N - 1];

    }

    int minCostToReachDestinationOrTrainFromStartPositionZero(int cost[][]) {

        /*
         * The minimum cost to reach N-1 from 0 can be recursively written as following:
         * 
         * minCost(0, N-1) = MIN { cost[0][n-1], cost[0][1] + minCost(1, N-1),
         * minCost(0, 2) + minCost(2, N-1), ........, minCost(0, N-2) + cost[N-2][n-1] }
         * 
         * Time complexity of the above implementation is exponential as it tries every
         * possible path from 0 to N-1. The above solution solves same subrpoblems
         * multiple times (it can be seen by drawing recursion tree for
         * minCostPathRec(0, 5).
         * 
         * We can solve this problem using O(N) extra space and O(N2) time. The idea is
         * based on the fact that given input matrix is a Directed Acyclic Graph (DAG).
         * 
         * 
         * The idea in below code is to first calculate min cost for station 1, then for
         * station 2, and so on. These costs are stored in an array dist[0...N-1].
         * 
         * 1) The min cost for station 0 is 0, i.e., dist[0] = 0
         * 
         * 2) The min cost for station 1 is cost[0][1], i.e., dist[1] = cost[0][1]
         * 
         * 3) The min cost for station 2 is minimum of following two. a) dist[0] +
         * cost[0][2] b) dist[1] + cost[1][2]
         * 
         * 3) The min cost for station 3 is minimum of following three. a) dist[0] +
         * cost[0][3] b) dist[1] + cost[1][3] c) dist[2] + cost[2][3]
         * 
         * Similarly, dist[4], dist[5], ... dist[N-1] are calculated.
         */
        // This function returns the smallest possible cost to
        // reach station N-1 from station 0.
        int N = cost.length;

        int dist[] = new int[N];// min cost to reach destination J from 0
        // N represents number of stations.
        

        for (int i = 0; i < N; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[0] = 0;

        // Go through every station, and check if using that as intermediate obtains the
        // better path
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (dist[j] > dist[i] + cost[i][j]) {// obtain minimum cost to reach destination J from I.
                    dist[j] = dist[i] + cost[i][j];// update the min cost to reach J through I.
                }
            }
        }
        return dist[N - 1];// min cost to reach the destination N-1 from 0.

    }

    int countNumberOfWaysToReachTotalScore(int n) {
        /*
         * Consider a game where a player can score 3 or 5 or 10 points in a move. Given
         * a total score n, find number of ways to reach the given score.
         * 
         * Examples:
         * 
         * Input: n = 20 Output: 4 There are following 4 ways to reach 20 (10, 10) (5,
         * 5, 10) (5, 5, 5, 5) (3, 3, 3, 3, 3, 5)
         * 
         * Input: n = 13 Output: 2 There are following 2 ways to reach 13 (3, 5, 5) (3,
         * 10)
         * 
         * similar to coin change problem
         */
        int table[] = new int[n + 1];//Total score.

        for (int i = 0; i <= n; i++) {
            table[i] = 0;
        }
        table[0] = 1;// base case set to 0, hence score 3, 5 , 10 will be picked

        // pick score one by one
        // One by one consider given 3 moves and update the table[]
        // values after the index greater than or equal to the
        // value of the picked move

        // After the index value
        for (int i = 3; i <= n; i++) {
            table[i] += table[i - 3];
            // why i-3, for 6 score, get the optimal solution obtained at 3 (i.e. number of
            // ways one can reach score 3)
        }
        for (int i = 5; i <= n; i++) {
            table[i] += table[i - 5];
        }

        for (int i = 10; i <= n; i++) {
            table[i] += table[i - 10];

            // why i-10, for 15 score, get the optimal solution obtained at 5, which would
            // inturn have been calculated using 3 / 5 as subset..
        }
        /**
         * Example: n=13
         * 
         * Initial 
         * 0 1 2 3 4 5 6 7 8 9 10 11 12 13 [Base case: 1]
         * 1 0 0 0 0 0 0 0 0 0 0  0  0  0
         * 
         * Number of ways to reach score i with score 3 alone 
         * 0 1 2 3 4 5 6 7 8 9 10 11 12 13 
         * 1 0 0 1 0 0 1 0 0 1 0  0  1  0
         * 
         * Number of ways to reach score i with score 5. (It considers output of 3 as well)
         * 0 1 2 3 4 5 6 7 8 9 10 11 12 13 
         * 1 0 0 1 0 1 1 0 1 1 1  1  1  1
         * 
         * * Number of ways to reach score i with score 10. (It considers output of 5 as well) 
         * 0 1 2 3 4 5 6 7 8 9 10 11 12 13 
         * 1 0 0 1 0 1 1 0 1 1 2  1  1  2
         * 
         * output[13] is 2.
         */

        return table[n];
    }

    class Job {
        int start, finish, profit;

        Job(int start, int finish, int profit) {
            this.start = start;
            this.finish = finish;
            this.profit = profit;
        }
    }

    // Find the latest job (in sorted array) that doesn't
    // conflict with the job[i]
    int latestNonConflict(Job arr[], int i) {
        for (int j = i - 1; j >= 0; j--) {
            if (arr[j].finish <= arr[i - 1].start) {// jth finish time is less than the start time of i-1
                return j;
            }
        }
        return -1;
    }

    int findMaxProfitWeightedJobScheduling(Job arr[], int n) {
        /**
         * Given N jobs where every job is represented by following three elements of it.

            Start Time
            Finish Time
            Profit or Value Associated (>= 0)

        Find the maximum profit subset of jobs such that no two jobs in the subset overlap. 

        Example: 

        Input: Number of Jobs n = 4
            Job Details {Start Time, Finish Time, Profit}
            Job 1:  {1, 2, 50} 
            Job 2:  {3, 5, 20}
            Job 3:  {6, 19, 100}
            Job 4:  {2, 100, 200}
        Output: The maximum profit is 250.
        We can get the maximum profit by scheduling jobs 1 and 4.
        Note that there is longer schedules possible Jobs 1, 2 and 3 
        but the profit with this schedule is 20+50+100 which is less than 250.
         */
        // arr array should be sorted according to finish time

        Comparator<Job> finishTimeComp = new Comparator<Job>() {

            @Override
            public int compare(Job o1, Job o2) {
                return o1.finish - o2.finish;
            }

        };
        Arrays.sort(arr, finishTimeComp);

        int table[] = new int[n];
        // Create an array to store solutions of subproblems. table[i]
        // stores the profit for jobs till arr[i] (including arr[i])
        table[0] = arr[0].profit;
        // Fill entries in M[] using recursive property
        for (int i = 1; i < n; i++) {
            int incl = arr[i].profit;// including the current job
            int l = latestNonConflict(arr, i);// finding the last job such that its finish time is less than start time
                                              // of the current job i. If it is found, return its index.
            if (l != -1) {
                incl += table[l];// add the last found job profit to the current job's profit.
            }
            // Store maximum of including and excluding
            table[i] = Math.max(incl, table[i - 1]);// find the maximum profit obtained either through including /
                                                    // excluding the current job
        }
        return table[n - 1];// returns the max profit obtained

    }

    void findMaxProfitWeightedJobSchedulingTestData() {
        Job arr[] = new Job[4];
        arr[0] = new Job(3, 10, 20);
        arr[1] = new Job(1, 2, 50);
        arr[2] = new Job(6, 19, 100);
        arr[3] = new Job(2, 100, 200);
        System.out.println("Maximum profit obtained through weighted job scheduling is "
                + findMaxProfitWeightedJobScheduling(arr, arr.length));
    }

    int findLengthOfLongestSubstringTwoHalvesEqual(char[] str) {
        /*
         * Given a string ‘str’ of digits, find length of the longest substring of
         * ‘str’, such that the length of the substring is 2k digits and sum of left k
         * digits is equal to the sum of right k digits.
         * 
         * Examples:
         * 
         * Input: str = "123123" Output: 6 The complete string is of even length and sum
         * of first and second half digits is same
         * 
         * Input: str = "1538023" Output: 4 The longest substring with same first and
         * second half sum is "5380"
         */
        int n = str.length;
        int maxlength = 0;
        // A 2D table where sum[i][j] stores sum of digits
        // from str[i] to str[j]. Only filled entries are
        // the entries where j >= i
        int sum[][] = new int[n][n];
        // sum will store the sum of digits from i to j in the string str.

        // Diagonally fill the matrix
        for (int i = 0; i < n; i++) {
            sum[i][i] = str[i] - '0';// strings of length 1 will have the digit as such
        }

        for (int L = 2; L <= n; L++) {
            for (int i = 0; i < n - L + 1; i++) {

                int j = i + L - 1;

                int k = L / 2;// halving the half length
                // Calculate value of sum[i][j]
                sum[i][j] = sum[i][j - k]// first half
                        + sum[j - k + 1][j];// second half
                // Update result if 'len' is even, left and right
                // sums are same and len is more than maxlen
                if (L % 2 == 0 // length of the longest substring has to be even
                        && sum[i][j - k] == sum[j - k + 1][j]// length of left and right substring is equal
                        && maxlength < L) {
                    maxlength = L;
                }
            }
        }

        return maxlength;

    }

    void findLengthOfLongestSubstringTwoHalvesEqualTestData() {

        String str = "153803";
        System.out.println("Length of the substring such that two halves sum are equal "
                + findLengthOfLongestSubstringTwoHalvesEqual(str.toCharArray()));
    }

    void countNumberOfWaysToReachTotalScoreTestData() {
        System.out.println("Number of ways to get total score of 20 is " + countNumberOfWaysToReachTotalScore(20));
        System.out.println("Number of ways to get total score of 13 is " + countNumberOfWaysToReachTotalScore(13));

    }

    void minCostTrainReachTestData() {
        int INF = Integer.MAX_VALUE;
        int cost[][] = new int[][] { { 0, 15, 80, 90 }, { INF, 0, 40, 50 }, { INF, INF, 0, 70 }, { INF, INF, INF, 0 } };
        System.out.println("Minimum cost to reach station " + cost.length + " is " + minCostToReachDestinationOrTrainFromStartPositionZero(cost));

    }

    void maxNoOfAsUsingFourKeyTestData() {
        // for the rest of the array we will rely on the previous
        // entries to compute new ones
        int N;
        for (N = 1; N <= 20; N++) {
            System.out.println("Maximum Number of A's with " + N + " keystrokes is " + printMaxNoOfAsUsingFourKeys(N));
        }
    }

    void getMaxProfitFromStockPriceTestData() {
        int price[] = new int[] { 2, 30, 15, 10, 8, 25, 80 };
        getMaxProfitFromStockPriceWith2Transactions(price);
    }

    void countPossibleWaysToConstructBuildingsTestData() {
        int N = 3;
        System.out.println("Number of ways to construct building is " + countPossibleWaysToConstructBuildings(2));
    }

    void sumOfDigitsFrom1ToNTestData() {
        int n = 328;
        System.out.println("Sum of digits in numbers from 1 to " + n + " is " + sumOfDigitsFrom1ToN(n));
    }

    void shortestSuperSequenceTestData() {
        String x = "AGGTAB";
        String y = "GXTXAYB";
        System.out.println("Length of the shortest supersequence is " + shortestSuperSequence(x, y));
    }

    void getMinNumberOfCoinsToMakeAChangeTestData() {
        int coins[] = new int[] { 9, 6, 5, 1 };
        int V = 11;
        System.out.println("Minimum number of coins required to make change for 11 is "
                + getMinNumberOfCoinsToMakeAChange(coins, V));
    }

    void getMinSquaresTestData() {
        System.out.println("Minimum square required for 6 is " + getMinNumberOfSquaresWhoseSuMEqualsToGivenNumberN(6));
    }

    void getLongestConsecutivePathCharTestData() {
        char mat[][] = new char[][] { { 'a', 'c', 'd' }, { 'h', 'b', 'a' }, { 'i', 'g', 'f' } };
        getLongestConsecutivePathCharMatrixGivenStartCharacter(mat, 'a');
        getLongestConsecutivePathCharMatrixGivenStartCharacter(mat, 'e');
        getLongestConsecutivePathCharMatrixGivenStartCharacter(mat, 'b');
        getLongestConsecutivePathCharMatrixGivenStartCharacter(mat, 'f');

    }

    void countNonDecreasingNumbersTestData() {
        System.out.println("Total number of non decreasing numbers with 3 digits is "
                + findTotalNonDecreasingNumbersWithNDigits(3));
    }

    void minPositivePointsToReachDestinationTestData() {
        int points[][] = new int[][] { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 } };

        /*
         * output
         * 
         * 7 5 2 6 11 5 1 1 6 Minimum initial points required to reach destination is 7
         */
        System.out.println("Minimum initial points required to reach destination is "
                + minimumPositiveInitialPointsToReachDestination(points));
    }

    void countOfNDigitEqualToSumTestData() {
        int n = 3, sum = 5;
        System.out.println("Count of 3 digit equal to sum 5 is " + countOfNDigitEqualToSum(n, sum));
    }

    void keyPadNumberTestData() {
        char keypad[][] = new char[][] { { '1', '2', '3' }, { '4', '5', '6' }, { '7', '8', '9' }, { '*', '0', '#' } };
        System.out.println("Count for numbers of length 5 is " + getMobileKeyPadPossibleNumbersOfLengthOrTraverseMatrix(keypad, 5));
    }

    void minCostTriangulationTestData() {
        Point points[] = new Point[5];
        points[0] = new Point(0, 0);
        points[1] = new Point(1, 0);
        points[2] = new Point(2, 1);
        points[3] = new Point(1, 2);
        points[4] = new Point(0, 2);
        System.out.println("Minimum cost to triangulate the polygon of given vertices is "
                + minCostTriangulation(points, points.length));

    }

    void countStairsTestData() {

        int s = 4, m = 2;
        System.out.println("Nuber of ways to reach 4 stair is " + countWaysToReachNthStair(s + 1, m));
    }

    void booleanParenthesizationTestData() {
        char symbols[] = new char[] { 'T', 'T', 'F', 'T' };
        char operators[] = new char[] { '|', '&', '^' };
        System.out.println(
                "Parenthesization count is " + countBooleanParenthesization(symbols, operators, symbols.length));
    }

    void findMaxSuMInRectangle2DMatrixTestData() {
        findMaxSuMInRectangleMatrix(
                new int[][] { { 1, 2, -1, -4, -20 }, { -8, -3, 4, 2, 1 }, { 3, 8, 10, 1, 3 }, { -4, -1, 1, 7, -6 } });
    }

    void isSubSetSumTestData() {
        int set[] = { 3, 34, 4, 6, 5, 2 };
        int sum = 9;
        if (findAllSubsetEqualSum(set, set.length, sum) == true) {
            System.out.println("Subset found to the sum " + sum);
        } else {
            System.out.println("Subset not found to the sum " + sum);
        }
    }

    void optimalBSTTestData() {
        int keys[] = new int[] { 10, 12, 20 };
        int freq[] = new int[] { 34, 8, 50 };

        System.out.println("Cost of optimal binary search tree is " + optimalBinarySearchTree(keys, freq, keys.length));
    }

    void longestPalindromicTestData() {
        String str = "forgeeksskeegfor";
        longestPalindromeSubString(str);
    }

    void maxSubArraySumTestData() {
        int a[] = { -2, -3, 4, -1, -2, 1, 5, -3 };
        largestSuMContiguousSubArrayWithNegativeAndPositiveIntegers(a, a.length);
    }

    void uglyNumberTestData() {
        uglyNumber(150);
    }

    void maxSubSquareBinaryTestData() {
        int M[][] = new int[][] { { 0, 1, 1, 0, 1 }, { 1, 1, 0, 1, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 } };
        maxSizeOfSubSquareMatrixWithAll1sInBinaryMatrix(M, 6, 5);
    }

    void minJumpsTestData() {
        int arr[] = new int[] { 1, 3, 6, 1, 0, 9 };
        System.out.println("Minimum number of jumps to reach end is " + minJumps(arr, arr.length));
    }

    void maxBoxStackHeightTestData() {
        Box arr[] = new Box[4];
        arr[0] = new Box(4, 6, 7);
        arr[1] = new Box(1, 2, 3);
        arr[2] = new Box(4, 5, 6);
        arr[3] = new Box(10, 12, 32);
        System.out.println("The maximum possible height of stack is " + maxBoxStackHeight(arr, arr.length));
    }

    void lisOptimizedTestData() {
        int A[] = { 2, 5, 3, 7, 11, 8, 10, 13, 6 };
        int n = A.length;
        System.out.println("Length of Longest Increasing Subsequence is " + lisOptimized(A, n));
    }

    void maxChainLengthTestData() {
        Pair arr[] = new Pair[4];
        arr[0] = new Pair(5, 24);
        arr[1] = new Pair(15, 25);
        arr[2] = new Pair(27, 40);
        arr[3] = new Pair(50, 60);
        int n = 4;
        System.out.println("Length of maximum size chain is " + findMaxChainLengthOfPairsOrLongestChain(arr, n));
    }

    void wordWrapTestData() {
        int l[] = { 3, 2, 2, 5 };
        int M = 6;
        solveWordWrap(l, l.length, M);
    }

    void findEqualPartitionSubSetTestData() {
        int arr[] = { 3, 1, 1, 2, 2, 1 };
        if (partitionArrayEquallyOrFindPartitonOfEqualSum(arr, arr.length)) {
            System.out.println("Can be divided into two " + "subsets of equal sum");
        } else {
            System.out.println("Can not be divided into" + " two subsets of equal sum");
        }
    }

    void minPalindromicPartitionTestData() {
        String str = "ababbbabbababa";
        System.out.println("Minimum number of cuts required is " + minPalindromicPartitioningOrCutStrings(str));
    }

    void longestBitonicSubSeqTestData() {
        int arr[] = new int[] { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
        longestBitonicSubSeq(arr, arr.length);
    }

    void maxIncreasingSuMSubSeqTestData() {
        int arr[] = { 1, 101, 2, 3, 100, 4, 5 };
        maxSumIncreasingSubSequence(arr, arr.length);
    }

    void cutRodTestData() {
        int arr[] = new int[] { 1, 5, 8, 9, 10, 17, 17, 20 };
        int size = arr.length;
        System.out.println("Maximum Obtainable Value is " + cutRod(arr, 5));
    }

    void lpsTestData() {
        String seq = "GEEKSFORGEEKS";
        int n = seq.length();
        System.out.println("The lnegth of the lps is " + longestPalindromicSubsequence(seq));
    }

    void eggDropTestData() {
        int n = 2, k = 36;
        System.out.println("Minimum number of trials with eggs " + n + " of floors " + k + " is " + eggDrop(n, k));
    }

    void knapSackTestData() {
        // Time complexity - o(nW)
        int val[] = new int[] { 60, 100, 120 };
        int wt[] = new int[] { 10, 20, 30 };
        int W = 50;
        int n = val.length;
        System.out.println("Total value in knapsack is " + knapSack(W, wt, val, n));
    }

    void binomialCoefficientTestData() {
        int n = 5, k = 2;
        binomialCoefficient(n, k);
    }

    void matrixChainOrderTestData() {
        int arr[] = new int[] { 10, 20, 30, 40, 30 };
        int size = arr.length;

        System.out.println("Minimum number of multiplications is " + matrixChainOrder(arr, size));
    }

    void countCoinChangeTestData() {
        int arr[] = { 1, 2, 3 };
        int n = 5;
        System.out.println(
                "Number of solutions we can make change for n  " + n + " is " + totalWaysToMakeChangeForValueNGivenNumberOfCoins(arr, arr.length, n));
    }

    void lisTestData() {
        int arr[] = { 10, 22, 9, 33, 21, 50, 41, 60 };
        System.out.println("Longest increasing subsequene is " + lis(arr, arr.length));
    }

    void lcsTestData() {
        char X[] = "AGGTAB".toCharArray();
        char Y[] = "GXTXAYB".toCharArray();

        lcs(X, Y, X.length, Y.length);

    }

    void fibonacciTestData() {
        int n = 9;
        _initialize();// initialization of table is important
        System.out.println("Fibonacci number is " + fib(n));
        System.out.println("Fibonacci number is " + fibTab(n));

    }

    void editDistanceTestData() {
        String str1 = "sunday";
        String str2 = "saturday";

        System.out.println(
                "Minimum cost to Edit distance is " + editDistanceDP(str1, str2, str1.length(), str2.length()));
    }

    void minCostPathTestData() {
        int cost[][] = new int[][] { { 1, 2, 3 }, { 4, 8, 2 }, { 1, 5, 3 } };
        System.out.println("Minimum cost path is " + minCostPath(cost, 2, 2));

    }

    void buildingBridgesTestData() {
        int n = 4;
        CityPairs[] pairs = new CityPairs[n];
        pairs[0] = new CityPairs(6, 2);
        pairs[1] = new CityPairs(4, 3);
        pairs[2] = new CityPairs(2, 6);
        pairs[3] = new CityPairs(1, 5);
        System.out.println("Maximum number of bridges = "
                           + buildingBridgesBetweenCities(pairs, n));
    }

    public static void main(String[] args){
        DynamicProgramming dp = new DynamicProgramming();
        dp.dynamicProgrammingTestData();
    }
    public void dynamicProgrammingTestData() {
        // fibonacciTestData();
        // lisTestData();
        // lcsTestData();
        // editDistanceTestData();
        // minCostPathTestData();
        // countCoinChangeTestData();
        matrixChainOrderTestData();
        // binomialCoefficientTestData();
        // knapSackTestData();
        // eggDropTestData();
        // lpsTestData();
        // cutRodTestData();
        // maxIncreasingSuMSubSeqTestData();
        // longestBitonicSubSeqTestData();
        // minPalindromicPartitionTestData();
        // findEqualPartitionSubSetTestData();
        // wordWrapTestData();
        // maxChainLengthTestData();
        // lisOptimizedTestData();
        // variationsOfLIS();
        // maxBoxStackHeightTestData();
        // minJumpsTestData();
        // maxSubSquareBinaryTestData();
        // uglyNumberTestData();
        // maxSubArraySumTestData();
        // longestPalindromicTestData();
        // optimalBSTTestData();
        // isSubSetSumTestData();
        // findMaxSuMInRectangle2DMatrixTestData();
        // countBinaryStringTestData();
        // booleanParenthesizationTestData();
        // countStairsTestData();
        // minCostTriangulationTestData();
        // keyPadNumberTestData();
        // countOfNDigitEqualToSumTestData();
        // minPositivePointsToReachDestinationTestData();
        // countNonDecreasingNumbersTestData();
        // getLongestConsecutivePathCharTestData();
        // getMinSquaresTestData();
        // getMinNumberOfCoinsToMakeAChangeTestData();
        // collectMaxPointsInGridUsingTwoTraversalTestData();
        // shortestSuperSequenceTestData();
        // sumOfDigitsFrom1ToNTestData();
        // countPossibleWaysToConstructBuildingsTestData();
        // getMaxProfitFromStockPriceTestData();
        // maxNoOfAsUsingFourKeyTestData();
        // minCostTrainReachTestData();
        // countNumberOfWaysToReachTotalScoreTestData();
        // findMaxProfitWeightedJobSchedulingTestData();
        // findLengthOfLongestSubstringTwoHalvesEqualTestData();
    }
    /*
     * For example the shortest path problem has following optimal substructure
     * property:
     * 
     * If a node x lies in the shortest path from a source node u to destination
     * node v then the shortest path from u to v is combination of shortest path
     * from u to x and shortest path from x to v.
     * 
     * The standard All Pair Shortest Path algorithms like Floyd–Warshall and
     * Bellman–Ford are typical examples of Dynamic Programming. On the other hand
     * the Longest path problem doesn’t have the Optimal Substructure property. Here
     * by Longest Path we mean longest simple path (path without cycle) between two
     * nodes.
     * 
     * Consider the following unweighted graph given in the CLRS book. There are two
     * longest paths from q to t: q -> r ->t and q ->s->t. Unlike shortest paths,
     * these longest paths do not have the optimal substructure property. For
     * example, the longest path q->r->t is not a combination of longest path from q
     * to r and longest path from r to t, because the longest path from q to r is
     * q->s->t->r.
     * 
     */
}
