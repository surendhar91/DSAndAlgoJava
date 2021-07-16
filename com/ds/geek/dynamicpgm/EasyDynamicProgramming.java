package com.ds.geek.dynamicpgm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class EasyDynamicProgramming {
  
    static class FindNumberOfWaysToPartitionSet {
        /**Given a set of n elements, find number of ways of partitioning it. 
        Examples: 


        Input:  n = 2
        Output: Number of ways = 2
        Explanation: Let the set be {1, 2}
                { {1}, {2} } 
                { {1, 2} }

        Input:  n = 3
        Output: Number of ways = 5
        Explanation: Let the set be {1, 2, 3}
                    { {1}, {2}, {3} }
                    { {1}, {2, 3} }
                    { {2}, {1, 3} }
                    { {3}, {1, 2} }
                    { {1, 2, 3} }. 

        Solution to above questions is Bell Number. 
        What is a Bell Number? 
        Let S(n, k) be total number of partitions of n elements into k sets. The value of n’th Bell Number is sum of S(n, k) for k = 1 to n. 
        Bell(n) = \sum_{k=0}^{n}S(n,k)
        Value of S(n, k) can be defined recursively as, S(n+1, k) = k*S(n, k) + S(n, k-1)
        How does above recursive formula work? 
        When we add a (n+1)’th element to k partitions, there are two possibilities. 
        1) It is added as a single element set to existing partitions, i.e, S(n, k-1) 
        2) It is added to all sets of every partition, i.e., k*S(n, k)
        S(n, k) is called Stirling numbers of the second kind
        First few Bell numbers are 1, 1, 2, 5, 15, 52, 203, …. 
        A Simple Method to compute n’th Bell Number is to one by one compute S(n, k) for k = 1 to n and return sum of all computed values. Refer this for computation of S(n, k).
        A Better Method is to use Bell Triangle. Below is a sample Bell Triangle for first few Bell Numbers. 


        1
        1 2
        2 3 5
        5 7 10 15
        15 20 27 37 52

        The triangle is constructed using below formula. 


        // If this is first column of current row 'i'
        If j == 0
        // Then copy last entry of previous row
        // Note that i'th row has i entries
        Bell(i, j) = Bell(i-1, i-1) 

        // If this is not first column of current row
        Else 
        // Then this element is sum of previous element 
        // in current row and the element just above the
        // previous element
        Bell(i, j) = Bell(i-1, j-1) + Bell(i, j-1)

        Interpretation 
        Then Bell(n, k) counts the number of partitions of the set {1, 2, …, n + 1} in which the element k + 1 is the largest element that can be alone in its set.
        For example, Bell(3, 2) is 3, it is count of number of partitions of {1, 2, 3, 4} in which 3 is the largest singleton element. There are three such partitions:

        {1}, {2, 4}, {3}
        {1, 4}, {2}, {3}
        {1, 2, 4}, {3}. 

        Below is Dynamic Programming based implementation of above recursive formula.  */
         // Function to find n'th Bell Number
        static int bellNumber(int n)
        {
            int[][] bell = new int[n+1][n+1];
            bell[0][0] = 1;
            
            for (int i=1; i<=n; i++)
            {
                // Explicitly fill for j = 0
                bell[i][0] = bell[i-1][i-1];
    
                // Fill for remaining values of j
                for (int j=1; j<=i; j++)
                    bell[i][j] = bell[i-1][j-1] + bell[i][j-1];
            }
            
            return bell[n][0];
        }
        
        // Driver program
        public static void testDataBellNumber(String[] args)
        {
            for (int n=0; n<=5; n++)
                System.out.println("Bell Number "+ n +
                                " is "+bellNumber(n));
        }
    }

    static class CollectMaxNumberOfCoinsOrGoldsInMatrix {
        static final int MAX = 100;
        /**Given a gold mine of n*m dimensions. Each field in this mine contains a positive integer which is the amount of gold in tons. Initially the miner is at first column but can be at any row. He can move only (right->,right up /,right down\) that is from a given cell, the miner can move to the cell diagonally up towards the right or right or diagonally down towards the right. Find out maximum amount of gold he can collect. 
        Examples: 
        

        Input : mat[][] = {{1, 3, 3},
                        {2, 1, 4},
                        {0, 6, 4}};
        Output : 12 
        {(1,0)->(2,1)->(2,2)}

        Input: mat[][] = { {1, 3, 1, 5},
                        {2, 2, 4, 1},
                        {5, 0, 2, 3},
                        {0, 6, 1, 2}};
        Output : 16
        (2,0) -> (1,1) -> (1,2) -> (0,3) OR
        (2,0) -> (3,1) -> (2,2) -> (2,3)

        Input : mat[][] = {{10, 33, 13, 15},
                        {22, 21, 04, 1},
                        {5, 0, 2, 3},
                        {0, 6, 14, 2}};
        Output : 83 
        Create a 2-D matrix goldTable[][]) of the same as given matrix mat[][]. If we observe the question closely, we can notice following. 
 

        Amount of gold is positive, so we would like to cover maximum cells of maximum values under given constraints.
        In every move, we move one step toward right side. So we always end up in last column. If we are at the last column, then we are unable to move right

        If we are at the first row or last column, then we are unable to move right-up so just assign 0 otherwise assign the value of goldTable[row-1][col+1] to right_up. If we are at the last row or last column, then we are unable to move right down so just assign 0 otherwise assign the value of goldTable[row+1][col+1] to right up. 
        Now find the maximum of right, right_up, and right_down and then add it with that mat[row][col]. At last, find the maximum of all rows and first column and return it.
        */
        // Returns maximum amount of gold that
        // can be collected when journey started
        // from first column and moves allowed
        // are right, right-up and right-down
        static int getMaxGold(int gold[][], int m, int n) {

            // Create a table for storing
            // intermediate results and initialize
            // all cells to 0. The first row of
            // goldMineTable gives the maximum
            // gold that the miner can collect
            // when starts that row
            int goldTable[][] = new int[m][n];

            for (int[] rows : goldTable)
                Arrays.fill(rows, 0);

            for (int col = n - 1; col >= 0; col--) {
                for (int row = 0; row < m; row++) {

                    // Gold collected on going to
                    // the cell on the right(->)
                    int right = (col == n - 1) ? 0 : goldTable[row][col + 1];

                    // Gold collected on going to
                    // the cell to right up (/)
                    int right_up = (row == 0 || col == n - 1) ? 0 : goldTable[row - 1][col + 1];

                    // Gold collected on going to
                    // the cell to right down (\)
                    int right_down = (row == m - 1 || col == n - 1) ? 0 : goldTable[row + 1][col + 1];

                    // Max gold collected from taking
                    // either of the above 3 paths
                    goldTable[row][col] = gold[row][col] + Math.max(right, Math.max(right_up, right_down));

                }
            }

            // The max amount of gold collected will be
            // the max value in first column of all rows
            int res = goldTable[0][0];

            for (int i = 1; i < m; i++)
                res = Math.max(res, goldTable[i][0]);

            return res;
        }

        // driver code
        public static void main(String arg[]) {
            int gold[][] = { { 1, 3, 1, 5 }, { 2, 2, 4, 1 }, { 5, 0, 2, 3 }, { 0, 6, 1, 2 } };

            int m = 4, n = 4;

            System.out.print(getMaxGold(gold, m, n));
        }

    }

    static class FindNumberOfWaysThataFriendCanRemainSingleOrPairedUp {
        /** Given n friends, each one can remain single or can be paired up with some other friend. Each friend can be paired only once. Find out the total number of ways in which friends can remain single or can be paired up. 
 

        Examples: 

        Input  : n = 3
        Output : 4
        Explanation:
        {1}, {2}, {3} : all single
        {1}, {2, 3} : 2 and 3 paired but 1 is single.
        {1, 2}, {3} : 1 and 2 are paired but 3 is single.
        {1, 3}, {2} : 1 and 3 are paired but 2 is single.
        Note that {1, 2} and {2, 1} are considered same. 
        f(n) = ways n people can remain single 
       or pair up.

        For n-th person there are two choices:
        1) n-th person remains single, we recur 
        for f(n - 1)
        2) n-th person pairs up with any of the 
        remaining n - 1 persons. We get (n - 1) * f(n - 2)

        Therefore we can recursively write f(n) as:
        f(n) = f(n - 1) + (n - 1) * f(n - 2)

        Since the above recursive formula has overlapping subproblems, we can solve it using Dynamic Programming.  
        */

        // Returns count of ways n people
        // can remain single or paired up.
        static int countFriendsPairings(int n) {
            int dp[] = new int[n + 1];

            // Filling dp[] in bottom-up manner using
            // recursive formula explained above.
            for (int i = 0; i <= n; i++) {
                if (i <= 2)
                    dp[i] = i;
                else
                    dp[i] = dp[i - 1] + (i - 1) * dp[i - 2];
            }

            return dp[n];
        }

        // Driver code
        public static void main(String[] args) {
            int n = 4;
            System.out.println(countFriendsPairings(n));
        }
    }

    static class FindSubsetInArrayWithSumDivisibleByM {
        /**Given a set of non-negative distinct integers, and a value m, determine if there is a subset of the given set with sum divisible by m. 
        Input Constraints 
        Size of set i.e., n <= 1000000, m <= 1000
        Examples: 
        

        Input : arr[] = {3, 1, 7, 5};
                m = 6;
        Output : YES

        Input : arr[] = {1, 6};
                m = 5;
        Output : NO 
        
                This problem is a variant of subset sum problem. In subset sum problem we check if given sum subset exists or not, here we need to find if there exists some subset with sum divisible by m or not. Seeing input constraint, it looks like typical DP solution will work in O(nm) time. But in tight time limits in competitive programming, the solution may work. Also auxiliary space is high for DP table, but here is catch.
        If n > m there will always be a subset with sum divisible by m (which is easy to prove with pigeonhole principle). So we need to handle only cases of n <= m .
        For n <= m we create a boolean DP table which will store the status of each value from 0 to m-1 which are possible subset sum (modulo m) which have been encountered so far.
        Now we loop through each element of given array arr[], and we add (modulo m) j which have DP[j] = true, and store all the such (j+arr[i])%m possible subset-sum in a boolean array temp, and at the end of iteration over j, we update DP table with temp. Also we add arr[i] to DP ie.. DP[arr[i]%m] = true. 
        In the end if DP[0] is true then it means YES there exists a subset with sum which is divisible by m, else NO.

        */
        // Returns true if there is a subset
        // of arr[] with sum divisible by m
        static boolean modularSum(int arr[], int n, int m) {
            if (n > m)
                return true;

            // This array will keep track of all
            // the possible sum (after modulo m)
            // which can be made using subsets of arr[]
            // initialising boolean array with all false
            boolean DP[] = new boolean[m];

            Arrays.fill(DP, false);

            // we'll loop through all the elements
            // of arr[]
            for (int i = 0; i < n; i++) {

                // anytime we encounter a sum divisible
                // by m, we are done
                if (DP[0])
                    return true;

                // To store all the new encountered sum
                // (after modulo). It is used to make
                // sure that arr[i] is added only to
                // those entries for which DP[j]
                // was true before current iteration.
                boolean temp[] = new boolean[m];
                Arrays.fill(temp, false);

                // For each element of arr[], we loop
                // through all elements of DP table
                // from 1 to m and we add current
                // element i. e., arr[i] to all those
                // elements which are true in DP table
                for (int j = 0; j < m; j++) {

                    // if an element is true in
                    // DP table
                    if (DP[j] == true) {
                        if (DP[(j + arr[i]) % m] == false)

                            // We update it in temp and update
                            // to DP once loop of j is over
                            temp[(j + arr[i]) % m] = true;
                    }
                }

                // Updating all the elements of temp
                // to DP table since iteration over
                // j is over
                for (int j = 0; j < m; j++)
                    if (temp[j])
                        DP[j] = true;

                // Also since arr[i] is a single
                // element subset, arr[i]%m is one
                // of the possible sum
                DP[arr[i] % m] = true;
            }

            return DP[0];
        }

        // driver code
        public static void main(String arg[]) {
            int arr[] = { 1, 7 };
            int n = arr.length;
            int m = 5;

            if (modularSum(arr, n, m))
                System.out.print("YES\n");
            else
                System.out.print("NO\n");
        }

    }

    static class FindLargestDivisiblePairsSubsetInArray {
        /**
         * Given an array of n distinct elements, find length of the largest subset such
         * that every pair in the subset is such that the larger element of the pair is
         * divisible by smaller element.
         * 
         * Examples:
         * 
         * Input : arr[] = {10, 5, 3, 15, 20} Output : 3 Explanation: The largest subset
         * is 10, 5, 20. 10 is divisible by 5, and 20 is divisible by 10.
         * 
         * Input : arr[] = {18, 1, 3, 6, 13, 17} Output : 4 Explanation: The largest
         * subset is 18, 1, 3, 6, In the subsequence, 3 is divisible by 1, 6 by 3 and 18
         * by 6.
         * 
         * This can be solved using Dynamic Programming. We traverse the sorted array
         * from the end. For every element a[i], we compute dp[i] where dp[i] indicates
         * size of largest divisible subset where a[i] is the smallest element. We can
         * compute dp[i] in array using values from dp[i+1] to dp[n-1]. Finally, we
         * return the maximum value from dp[].
         * 
         * Below is the implementation of the above approach:
         * 
         */
        // function to find the longest Subsequence
        static int largestSubset(int[] a, int n) {
            // dp[i] is going to store size of largest
            // divisible subset beginning with a[i].
            int[] dp = new int[n];

            // Since last element is largest, d[n-1] is 1
            dp[n - 1] = 1;

            // Fill values for smaller elements.
            for (int i = n - 2; i >= 0; i--) {

                // Find all multiples of a[i] and consider
                // the multiple that has largest subset
                // beginning with it.
                int mxm = 0;
                for (int j = i + 1; j < n; j++) {
                    if (a[j] % a[i] == 0 || a[i] % a[j] == 0) {
                        mxm = Math.max(mxm, dp[j]);
                    }
                }

                dp[i] = 1 + mxm;
            }

            // Return maximum value from dp[]
            return Arrays.stream(dp).max().getAsInt();
        }

        // driver code to check the above function
        public static void main(String[] args) {
            int[] a = { 1, 3, 6, 13, 17, 18 };
            int n = a.length;
            System.out.println(largestSubset(a, n));
        }
    } 

    static class ChooseAreaSuchThatGivenPowersDontGoLessThanZero {
        /**
         * Consider a game, in which you have two types of powers, A and B and there are
         * 3 types of Areas X, Y and Z. Every second you have to switch between these
         * areas, each area has specific properties by which your power A and power B
         * increase or decrease. We need to keep choosing areas in such a way that our
         * survival time is maximized. Survival time ends when any of the powers, A or B
         * reaches less than 0.
         * 
         * Examples:
         * 
         * Initial value of Power A = 20 Initial value of Power B = 8
         * 
         * Area X (3, 2) : If you step into Area X, A increases by 3, B increases by 2
         * 
         * Area Y (-5, -10) : If you step into Area Y, A decreases by 5, B decreases by
         * 10
         * 
         * Area Z (-20, 5) : If you step into Area Z, A decreases by 20, B increases by
         * 5
         * 
         * It is possible to choose any area in our first step. We can survive at max 5
         * unit of time by following these choice of areas : X -> Z -> X -> Y -> X
         * 
         * This problem can be solved using recursion, after each time unit we can go to
         * any of the area but we will choose that area which ultimately leads to
         * maximum survival time. As recursion can lead to solving same subproblem many
         * time, we will memoize the result on basis of power A and B, if we reach to
         * same pair of power A and B, we won’t solve it again instead we will take the
         * previously calculated result. Given below is the simple implementation of
         * above approach.
         */

        // structure to represent an area
        static class Area {
            // increment or decrement in A and B
            int a, b;

            Area(int a, int b) {
                this.a = a;
                this.b = b;
            }
        }

        // Utility method to get maximum of 3 integers
        int max(int a, int b, int c) {
            return Math.max(a, Math.max(b, c));
        }

        static class Pair {
            int a, b;

            Pair(int a, int b) {
                this.a = a;
                this.b = b;
            }
        }

        // Utility method to get maximum survival time
        int maxSurvival(int A, int B, Area X, Area Y, Area Z, int last, Map<Pair, Integer> memo) {
            // if any of A or B is less than 0, return 0
            if (A <= 0 || B <= 0)
                return 0;
            Pair cur = new Pair(A, B);

            // if already calculated, return calculated value
            if (memo.get(cur) != null)
                return memo.get(cur);

            int temp = 0;

            // step to areas on basis of last chose area
            switch (last) {
                case 1:
                    temp = 1 + Math.max(maxSurvival(A + Y.a, B + Y.b, X, Y, Z, 2, memo),
                            maxSurvival(A + Z.a, B + Z.b, X, Y, Z, 3, memo));
                    break;
                case 2:
                    temp = 1 + Math.max(maxSurvival(A + X.a, B + X.b, X, Y, Z, 1, memo),
                            maxSurvival(A + Z.a, B + Z.b, X, Y, Z, 3, memo));
                    break;
                case 3:
                    temp = 1 + Math.max(maxSurvival(A + X.a, B + X.b, X, Y, Z, 1, memo),
                            maxSurvival(A + Y.a, B + Y.b, X, Y, Z, 2, memo));
                    break;
            }

            // store the result into map
            memo.put(cur, temp);

            return temp;
        }

        // method returns maximum survival time
        int getMaxSurvivalTime(int A, int B, Area X, Area Y, Area Z) {
            if (A <= 0 || B <= 0)
                return 0;
            Map<Pair, Integer> memo = new HashMap<Pair, Integer>();

            // At first, we can step into any of the area
            return max(maxSurvival(A + X.a, B + X.b, X, Y, Z, 1, memo), maxSurvival(A + Y.a, B + Y.b, X, Y, Z, 2, memo),
                    maxSurvival(A + Z.a, B + Z.b, X, Y, Z, 3, memo));
        }

        // Driver code to test above method
        public static void main(String[] args) {
            Area X = new Area(3, 2);
            Area Y = new Area(-5, -10);
            Area Z = new Area(-20, 5);

            int A = 20;
            int B = 8;
            System.out.println(new ChooseAreaSuchThatGivenPowersDontGoLessThanZero().getMaxSurvivalTime(A, B, X, Y, Z));
        }
    }

    static class Tiling3xNBoardWith2x1Cards {
        /**
         * Given a 3 x n board, find the number of ways to fill it with 2 x 1 dominoes.

        Example 1
        Following are all the 3 possible ways to fill up a 3 x 2 board.

        Example 2
        Here is one possible way of filling a 3 x 8 board. You have to find all the possible ways to do so.

        Examples :

        Input : 2
        Output : 3

        Input : 8
        Output : 153

        Input : 12
        Output : 2131

        Defining Subproblems:
        At any point while filling the board, there are three possible states that the last column can be in:

        1. Completely Filled, Top corner empty and bottom corner empty.

        Note: The following states are impossible to reach: Middle corner empty or filled.

        An =  No. of ways to completely fill a 3 x n board. (We need to find this)
        Bn =  No. of ways to fill a 3 x n board with top corner in last column not filled.
        Cn =  No. of ways to fill a 3 x n board with bottom corner in last column not filled.


        Final Recursive Relations are:

        A_{n} = A_{n-2} + 2*(B_{n-1}) 
        B_{n} = A_{n-1} + B_{n-2} 

        Base Cases:

        A_0 = 1  A_1 = 0 
        B_0 = 0  B_1 = 1 

         */
        static int countWays(int n) {
            int[] A = new int[n + 1];
            int[] B = new int[n + 1];
            A[0] = 1;
            A[1] = 0;
            B[0] = 0;
            B[1] = 1;
            for (int i = 2; i <= n; i++) {
                A[i] = A[i - 2] + 2 * B[i - 1];
                B[i] = A[i - 1] + B[i - 2];
            }

            return A[n];
        }

        // Driver code
        public static void main(String[] args) {
            int n = 8;
            System.out.println(countWays(n));
        }
    }

    static class AssemblyLineScheduling {
        /**A car factory has two assembly lines, each with n stations. A station is denoted by Si,j where i is either 1 or 2 and indicates the assembly line the station is on, and j indicates the number of the station. The time taken per station is denoted by ai,j. Each station is dedicated to some sort of work like engine fitting, body fitting, painting, and so on. So, a car chassis must pass through each of the n stations in order before exiting the factory. The parallel stations of the two assembly lines perform the same task. After it passes through station Si,j, it will continue to station Si,j+1 unless it decides to transfer to the other line. Continuing on the same line incurs no extra cost, but transferring from line i at station j – 1 to station j on the other line takes time ti,j. Each assembly line takes an entry time ei and exit time xi which may be different for the two lines. Give an algorithm for computing the minimum time it will take to build a car chassis.

        The below figure presents the problem in a clear picture: 
        

        The following information can be extracted from the problem statement to make it simpler: 

            Two assembly lines, 1 and 2, each with stations from 1 to n.
            A car chassis must pass through all stations from 1 to n in order(in any of the two assembly lines). i.e. it cannot jump from station i to station j if they are not at one move distance.
            The car chassis can move one station forward in the same line, or one station diagonally in the other line. It incurs an extra cost ti, j to move to station j from line i. No cost is incurred for movement in same line.
            The time taken in station j on line i is ai, j.
            Si, j represents a station j on line i.

        Breaking the problem into smaller sub-problems: 
        We can easily find the ith factorial if (i-1)th factorial is known. Can we apply the similar funda here? 
        If the minimum time taken by the chassis to leave station Si, j-1 is known, the minimum time taken to leave station Si, j can be calculated quickly by combining ai, j and ti, j.
        T1(j) indicates the minimum time taken by the car chassis to leave station j on assembly line 1.
        T2(j) indicates the minimum time taken by the car chassis to leave station j on assembly line 2. 
        
        Base cases: 
        The entry time ei comes into picture only when the car chassis enters the car factory.
        Time taken to leave the first station in line 1 is given by: 
        T1(1) = Entry time in Line 1 + Time spent in station S1,1 
        T1(1) = e1 + a1,1 
        Similarly, time taken to leave the first station in line 2 is given by: 
        T2(1) = e2 + a2,1

        Recursive Relations: 
        If we look at the problem statement, it quickly boils down to the below observations: 
        The car chassis at station S1,j can come either from station S1, j-1 or station S2, j-1.

        Case #1: Its previous station is S1, j-1 
        The minimum time to leave station S1,j is given by: 
        T1(j) = Minimum time taken to leave station S1, j-1 + Time spent in station S1, j 
        T1(j) = T1(j-1) + a1, j

        Case #2: Its previous station is S2, j-1 
        The minimum time to leave station S1, j is given by: 
        T1(j) = Minimum time taken to leave station S2, j-1 + Extra cost incurred to change the assembly line + Time spent in station S1, j 
        T1(j) = T2(j-1) + t2, j + a1, j

        The minimum time T1(j) is given by the minimum of the two obtained in cases #1 and #2. 
        T1(j) = min((T1(j-1) + a1, j), (T2(j-1) + t2, j + a1, j)) 

        Similarly, the minimum time to reach station S2, j is given by: 
        T2(j) = min((T2(j-1) + a2, j), (T1(j-1) + t1, j + a2, j))

        The total minimum time taken by the car chassis to come out of the factory is given by: 
        Tmin = min(Time taken to leave station Si,n + Time taken to exit the car factory) 
        Tmin = min(T1(n) + x1, T2(n) + x2)

        Why dynamic programming? 
        The above recursion exhibits overlapping sub-problems. There are two ways to reach station S1, j:  

        From station S1, j-1
        From station S2, j-1

        So, to find the minimum time to leave station S1, j the minimum time to leave the previous two stations must be calculated(as explained in above recursion).

        Similarly, there are two ways to reach station S2, j: 

            From station S2, j-1
            From station S1, j-1

        Please note that the minimum times to leave stations S1, j-1 and S2, j-1 have already been calculated.
        So, we need two tables to store the partial results calculated for each station in an assembly line. The table will be filled in a bottom-up fashion.

        Note: 
        In this post, the word “leave” has been used in place of “reach” to avoid confusion. Since the car chassis must spend a fixed time in each station, the word leave suits better.

        Implementation: 
        */

        static int NUM_LINE = 2;
        static int NUM_STATION = 4;
         
        // Utility function to find minimum of two numbers
        static int min(int a, int b)
        {
            return a < b ? a : b;
             
        }
         
        static int carAssembly(int a[][], int t[][], int e[], int x[])
        {
            int T1[]= new int [NUM_STATION];
            int T2[] =new int[NUM_STATION] ;
            int i;
         
            // time taken to leave first station in line 1
            T1[0] = e[0] + a[0][0];
             
            // time taken to leave first station in line 2
            T2[0] = e[1] + a[1][0];
         
            // Fill tables T1[] and T2[] using
            // the above given recursive relations
            for (i = 1; i < NUM_STATION; ++i)
            {
                T1[i] = min(T1[i - 1] + a[0][i],
                        T2[i - 1] + t[1][i] + a[0][i]);
                T2[i] = min(T2[i - 1] + a[1][i],
                        T1[i - 1] + t[0][i] + a[1][i]);
            }
         
            // Consider exit times and retutn minimum
            return min(T1[NUM_STATION-1] + x[0],
                        T2[NUM_STATION-1] + x[1]);
        }
         
         
        // Driver code
        public static void main (String[] args)
        {
            int a[][] = {{4, 5, 3, 2},
                        {2, 10, 1, 4}};
            int t[][] = {{0, 7, 4, 5},
                        {0, 9, 2, 8}};
            int e[] = {10, 12}, x[] = {18, 7};
         
            System.out.println(carAssembly(a, t, e, x));   
            // Output:35
        }
    }

    static class FindMaximumSnakeLengthOrLongestSequenceInMatrix {
        /**
         * 
         * Given a grid of numbers, find maximum length Snake sequence and print it. If multiple snake sequences exists with the maximum length, print any one of them.
            A snake sequence is made up of adjacent numbers in the grid such that for each number, the number on the right or the number below it is +1 or -1 its value. For example, if you are at location (x, y) in the grid, you can either move right i.e. (x, y+1) if that number is ± 1 or move down i.e. (x+1, y) if that number is ± 1.
            For example,
            9, 6, 5, 2 
            8, 7, 6, 5 
            7, 3, 1, 6 
            1, 1, 1, 7
            In above grid, the longest snake sequence is: (9, 8, 7, 6, 5, 6, 7)
            Below figure shows all possible paths –

            The idea is to use Dynamic Programming. For each cell of the matrix, we keep maximum length of a snake which ends in current cell. The maximum length snake sequence will have maximum value. The maximum value cell will correspond to tail of the snake. In order to print the snake, we need to backtrack from tail all the way back to snake’s head.
            Let T[i][i] represent maximum length of a snake which ends at cell (i, j), then for given matrix M, the DP relation is defined as –
            T[0][0] = 0 
            T[i][j] = max(T[i][j], T[i][j – 1] + 1) if M[i][j] = M[i][j – 1] ± 1 
            T[i][j] = max(T[i][j], T[i – 1][j] + 1) if M[i][j] = M[i – 1][j] ± 1
            Below is the implementation of the idea – 
         */
        static int M = 4;
        static int N = 4;

        static class Point {
            int x, y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        };

        // Function to find maximum length Snake sequence path
        // (i, j) corresponds to tail of the snake
        static List<Point> findPath(int grid[][], int mat[][], int i, int j) {
            List<Point> path = new LinkedList<>();

            Point pt = new Point(i, j);
            path.add(0, pt);

            while (grid[i][j] != 0) {
                if (i > 0 && grid[i][j] - 1 == grid[i - 1][j]) {
                    pt = new Point(i - 1, j);
                    path.add(0, pt);
                    i--;
                } else if (j > 0 && grid[i][j] - 1 == grid[i][j - 1]) {
                    pt = new Point(i, j - 1);
                    path.add(0, pt);
                    j--;
                }
            }
            return path;
        }

        // Function to find maximum length Snake sequence
        static void findSnakeSequence(int mat[][]) {
            // table to store results of subproblems
            int[][] lookup = new int[M][N];

            // initialize by 0

            // stores maximum length of Snake sequence
            int max_len = 0;

            // store coordinates to snake's tail
            int max_row = 0;
            int max_col = 0;

            // fill the table in bottom-up fashion
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    // do except for (0, 0) cell
                    if (i != 0 || j != 0) {
                        // look above
                        if (i > 0 && Math.abs(mat[i - 1][j] - mat[i][j]) == 1) {
                            lookup[i][j] = Math.max(lookup[i][j], lookup[i - 1][j] + 1);

                            if (max_len < lookup[i][j]) {
                                max_len = lookup[i][j];
                                max_row = i;
                                max_col = j;
                            }
                        }

                        // look left
                        if (j > 0 && Math.abs(mat[i][j - 1] - mat[i][j]) == 1) {
                            lookup[i][j] = Math.max(lookup[i][j], lookup[i][j - 1] + 1);
                            if (max_len < lookup[i][j]) {
                                max_len = lookup[i][j];
                                max_row = i;
                                max_col = j;
                            }
                        }
                    }
                }
            }
            System.out.print("Maximum length of Snake " + "sequence is: " + max_len + "\n");

            // find maximum length Snake sequence path
            List<Point> path = findPath(lookup, mat, max_row, max_col);

            System.out.print("Snake sequence is:");
            for (Point it : path)
                System.out.print("\n" + mat[it.x][it.y] + " (" + it.x + ", " + it.y + ")");
        }

        // Driver code
        public static void main(String[] args) {
            int mat[][] = { { 9, 6, 5, 2 }, { 8, 7, 6, 5 }, { 7, 3, 1, 6 }, { 1, 1, 1, 7 } };

            findSnakeSequence(mat);
            /**Maximum length of Snake sequence is: 6
            Snake sequence is:
            9 (0, 0)
            8 (1, 0)
            7 (1, 1)
            6 (1, 2)
            5 (1, 3)
            6 (2, 3)
            7 (3, 3) */
        }
    }

    static class FindLongestIncreasingSequenceWhereElementGreaterThanOrTwiceOfPrevious {
        /**
         * Given two integers m & n, find the number of possible sequences of length n
         * such that each of the next element is greater than or equal to twice of the
         * previous element but less than or equal to m. Examples :
         * 
         * Input : m = 10, n = 4 Output : 4 There should be n elements and value of last
         * element should be at-most m. The sequences are {1, 2, 4, 8}, {1, 2, 4, 9},
         * {1, 2, 4, 10}, {1, 2, 5, 10}
         * 
         * Input : m = 5, n = 2 Output : 6 The sequences are {1, 2}, {1, 3}, {1, 4}, {1,
         * 5}, {2, 4}, {2, 5}
         * 
         * As per the given condition the n-th value of the sequence can be at most m.
         * There can be two cases for n-th element:
         * 
         * If it is m, then the (n-1)th element is at most m/2. We recur for m/2 and
         * n-1. If it is not m, then it is at most is m-1. We recur for (m-1) and n.
         * 
         * The total number of sequences is the sum of the number of sequences including
         * m and the number of sequences where m is not included. Thus the original
         * problem of finding number of sequences of length n with max value m can be
         * subdivided into independent subproblems of finding number of sequences of
         * length n with max value m-1 and number of sequences of length n-1 with max
         * value m/2.
         */
        // DP based function to find the number of special
        // sequences
        static int getTotalNumberOfSequences(int m, int n) {
            // define T and build in bottom manner to store
            // number of special sequences of length n and
            // maximum value m
            int T[][] = new int[m + 1][n + 1];
            for (int i = 0; i < m + 1; i++) {
                for (int j = 0; j < n + 1; j++) {
                    // Base case : If length of sequence is 0
                    // or maximum value is 0, there cannot
                    // exist any special sequence
                    if (i == 0 || j == 0)
                        T[i][j] = 0;

                    // if length of sequence is more than
                    // the maximum value, special sequence
                    // cannot exist
                    else if (i < j)
                        T[i][j] = 0;

                    // If length of sequence is 1 then the
                    // number of special sequences is equal
                    // to the maximum value
                    // For example with maximum value 2 and
                    // length 1, there can be 2 special
                    // sequences {1}, {2}
                    else if (j == 1)
                        T[i][j] = i;

                    // otherwise calculate
                    else
                        T[i][j] = T[i - 1][j] + T[i / 2][j - 1];
                }
            }
            return T[m][n];
        }

        // main function
        public static void main(String[] args) {
            int m = 10;
            int n = 4;
            System.out.println("Total number of possible sequences " + getTotalNumberOfSequences(m, n));
        }
    }

    static class LongestRepeatedSubsequence {
        /**
         * Given a string, print the longest repeating subsequence such that the two
         * subsequence don’t have same string character at same position, i.e., any i’th
         * character in the two subsequences shouldn’t have the same index in the
         * original string.
         * 
         * 
         * Examples:
         * 
         * Input: str = "aabb" Output: "ab"
         * 
         * Input: str = "aab" Output: "a" The two subsequence are 'a'(first) and 'a'
         * (second). Note that 'b' cannot be considered as part of subsequence as it
         * would be at same index in both.
         * 
         * This problem is just the modification of Longest Common Subsequence problem.
         * The idea is to find the LCS(str, str) where str is the input string with the
         * restriction that when both the characters are same, they shouldn’t be on the
         * same index in the two strings. We have discussed a solution to find length of
         * the longest repeated subsequence.
         */
        // This function mainly returns LCS(str, str)
        // with a condition that same characters at
        // same index are not considered.
        static String longestRepeatedSubSeq(String str) {
            // THIS PART OF CODE IS SAME AS BELOW POST.
            // IT FILLS dp[][]
            // https://www.geeksforgeeks.org/longest-repeating-subsequence/
            // OR the code mentioned above.
            int n = str.length();
            int[][] dp = new int[n + 1][n + 1];
            for (int i = 0; i <= n; i++)
                for (int j = 0; j <= n; j++)
                    dp[i][j] = 0;
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= n; j++)
                    if (str.charAt(i - 1) == str.charAt(j - 1) && i != j)
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                    else
                        dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);

            // THIS PART OF CODE FINDS
            // THE RESULT STRING USING DP[][]
            // Initialize result
            String res = "";

            // Traverse dp[][] from bottom right
            int i = n, j = n;
            while (i > 0 && j > 0) {
                // If this cell is same as diagonally
                // adjacent cell just above it, then
                // same characters are present at
                // str[i-1] and str[j-1]. Append any
                // of them to result.
                if (dp[i][j] == dp[i - 1][j - 1] + 1) {
                    res = res + str.charAt(i - 1);
                    i--;
                    j--;
                }

                // Otherwise we move to the side
                // that that gave us maximum result
                else if (dp[i][j] == dp[i - 1][j])
                    i--;
                else
                    j--;
            }

            // Since we traverse dp[][] from bottom,
            // we get result in reverse order.
            String reverse = "";

            for (int k = res.length() - 1; k >= 0; k--) {
                reverse = reverse + res.charAt(k);
            }

            return reverse;
        }

        // Driver code
        public static void main(String args[]) {
            String str = "AABEBCDD";
            System.out.println(longestRepeatedSubSeq(str));
        }
    }

    static class LongestCommonSubsequenceOf3Strings {
        /**
         * Given 3 strings of all having length < 100,the task is to find the longest
         * common sub-sequence in all three given sequences. Examples:
         * 
         * 
         * Input : str1 = "geeks" str2 = "geeksfor" str3 = "geeksforgeeks" Output : 5
         * Longest common subsequence is "geeks" i.e., length = 5
         * 
         * Input : str1 = "abcd1e2" str2 = "bc12ea" str3 = "bd1ea" Output : 3 Longest
         * common subsequence is "b1e" i.e. length = 3.
         * 
         * This problem is simply an extension of LCS Let the input sequences be
         * X[0..m-1], Y[0..n-1] and Z[0..o-1] of lengths m, n and o respectively. And
         * let L(X[0..m-1], Y[0..n-1], Z[0..o-1]) be the lengths of LCS of the three
         * sequences X, Y and Z. Following is the implementation:
         * 
         * 
         * The idea is to take a 3D array to store the length of common subsequence in
         * all 3 given sequences i. e., L[m + 1][n + 1][o + 1]
         * 
         * 1- If any of the string is empty then there is no common subsequence at all
         * then L[i][j][k] = 0
         * 
         * 2- If the characters of all sequences match (or X[i] == Y[j] ==Z[k]) then
         * L[i][j][k] = 1 + L[i-1][j-1][k-1]
         * 
         * 3- If the characters of both sequences do not match (or X[i] != Y[j] || X[i]
         * != Z[k] || Y[j] !=Z[k]) then L[i][j][k] = max(L[i-1][j][k], L[i][j-1][k],
         * L[i][j][k-1])
         * 
         * Below is implementation of above idea.
         */
        /*
         * Returns length of LCS for X[0..m-1], Y[0..n-1] and Z[0..o-1]
         */
        static int lcsOf3(String X, String Y, String Z, int m, int n, int o) {
            int[][][] L = new int[m + 1][n + 1][o + 1];

            /*
             * Following steps build L[m+1][n+1][o+1] in bottom up fashion. Note that
             * L[i][j][k] contains length of LCS of X[0..i-1] and Y[0..j-1] and Z[0.....k-1]
             */
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    for (int k = 0; k <= o; k++) {
                        if (i == 0 || j == 0 || k == 0)
                            L[i][j][k] = 0;

                        else if (X.charAt(i - 1) == Y.charAt(j - 1) && X.charAt(i - 1) == Z.charAt(k - 1))
                            L[i][j][k] = L[i - 1][j - 1][k - 1] + 1;

                        else
                            L[i][j][k] = Math.max(Math.max(L[i - 1][j][k], L[i][j - 1][k]), L[i][j][k - 1]);
                    }
                }
            }

            /*
             * L[m][n][o] contains length of LCS for X[0..n-1] and Y[0..m-1] and Z[0..o-1]
             */
            return L[m][n][o];
        }

        /* Driver program to test above function */
        public static void main(String args[]) {
            String X = "AGGT12";
            String Y = "12TXAYB";
            String Z = "12XBA";

            int m = X.length();
            int n = Y.length();
            int o = Z.length();

            System.out.println("Length of LCS is " + lcsOf3(X, Y, Z, m, n, o));

        }
    }

    static class MaxSumBitonicSubsequence {
        /**
         * Given an array of integers. A subsequence of arr[] is called Bitonic if it is
         * first increasing, then decreasing. Examples :
         * 
         * 
         * Input : arr[] = {1, 15, 51, 45, 33, 100, 12, 18, 9} Output : 194 Explanation
         * : Bi-tonic Sub-sequence are : {1, 51, 9} or {1, 50, 100, 18, 9} or {1, 15,
         * 51, 100, 18, 9} or {1, 15, 45, 100, 12, 9} or {1, 15, 45, 100, 18, 9} .. so
         * on Maximum sum Bi-tonic sub-sequence is 1 + 15 + 51 + 100 + 18 + 9 = 194
         * 
         * Input : arr[] = {80, 60, 30, 40, 20, 10} Output : 210
         * 
         * This problem is a variation of standard Longest Increasing Subsequence (LIS)
         * problem and longest Bitonic Sub-sequence. We construct two arrays MSIBS[] and
         * MSDBS[]. MSIBS[i] stores the sum of the Increasing subsequence ending with
         * arr[i]. MSDBS[i] stores the sum of the Decreasing subsequence starting from
         * arr[i]. Finally, we need to return the max sum of MSIBS[i] + MSDBS[i] –
         * Arr[i]. Below is the implementation of above idea
         * 
         */
        // Function return maximum sum
        // of Bi-tonic sub-sequence
        static int MaxSumBS(int arr[], int n) {
            int max_sum = Integer.MIN_VALUE;

            // MSIBS[i] ==> Maximum sum Increasing Bi-tonic
            // subsequence ending with arr[i]
            // MSDBS[i] ==> Maximum sum Decreasing Bi-tonic
            // subsequence starting with arr[i]
            // Initialize MSDBS and MSIBS values as arr[i] for
            // all indexes
            int MSIBS[] = new int[n];
            int MSDBS[] = new int[n];
            for (int i = 0; i < n; i++) {
                MSDBS[i] = arr[i];
                MSIBS[i] = arr[i];
            }

            // Compute MSIBS values from left to right */
            for (int i = 1; i < n; i++)
                for (int j = 0; j < i; j++)
                    if (arr[i] > arr[j] && MSIBS[i] < MSIBS[j] + arr[i])
                        MSIBS[i] = MSIBS[j] + arr[i];

            // Compute MSDBS values from right to left
            for (int i = n - 2; i >= 0; i--)
                for (int j = n - 1; j > i; j--)
                    if (arr[i] > arr[j] && MSDBS[i] < MSDBS[j] + arr[i])
                        MSDBS[i] = MSDBS[j] + arr[i];

            // Find the maximum value of MSIBS[i] +
            // MSDBS[i] - arr[i]
            for (int i = 0; i < n; i++)
                max_sum = Math.max(max_sum, (MSDBS[i] + MSIBS[i] - arr[i]));

            // return max sum of bi-tonic
            // sub-sequence
            return max_sum;
        }

        // Driver program
        public static void main(String[] args) {
            int arr[] = { 1, 15, 51, 45, 33, 100, 12, 18, 9 };
            int n = arr.length;
            System.out.println("Maximum Sum : " + MaxSumBS(arr, n));// 194
        }
    }

    static class MaxSumIncreasingSubsequence {
        /**
         * Given an array of n positive integers. Write a program to find the sum of
         * maximum sum subsequence of the given array such that the integers in the
         * subsequence are sorted in increasing order. For example, if input is {1, 101,
         * 2, 3, 100, 4, 5}, then output should be 106 (1 + 2 + 3 + 100), if the input
         * array is {3, 4, 5, 10}, then output should be 22 (3 + 4 + 5 + 10) and if the
         * input array is {10, 5, 4, 3}, then output should be 10
         * 
         * Solution This problem is a variation of standard Longest Increasing
         * Subsequence (LIS) problem. We need a slight change in the Dynamic Programming
         * solution of LIS problem. All we need to change is to use sum as a criteria
         * instead of length of increasing subsequence.
         * 
         * Following are the Dynamic Programming solution to the problem :
         */
        /*
         * maxSumIS() returns the maximum sum of increasing subsequence in arr[] of size
         * n
         */
        static int maxSumIS(int arr[], int n) {
            int i, j, max = 0;
            int msis[] = new int[n];

            /*
             * Initialize msis values for all indexes
             */
            for (i = 0; i < n; i++)
                msis[i] = arr[i];

            /*
             * Compute maximum sum values in bottom up manner
             */
            for (i = 1; i < n; i++)
                for (j = 0; j < i; j++)
                    if (arr[i] > arr[j] && msis[i] < msis[j] + arr[i])
                        msis[i] = msis[j] + arr[i];

            /*
             * Pick maximum of all msis values
             */
            for (i = 0; i < n; i++)
                if (max < msis[i])
                    max = msis[i];

            return max;
        }

        // Driver code
        public static void main(String args[]) {
            int arr[] = new int[] { 1, 101, 2, 3, 100, 4, 5 };
            int n = arr.length;
            System.out.println("Sum of maximum sum " + "increasing subsequence is " + maxSumIS(arr, n));
        }

    }

    static class MaxProductOfIncreasingSubsequence {
        /**
         * Given an array of numbers, find the maximum product formed by multiplying
         * numbers of an increasing subsequence of that array. Note: A single number is
         * supposed to be an increasing subsequence of size 1. Examples:
         * 
         * 
         * Input : arr[] = { 3, 100, 4, 5, 150, 6 } Output : 45000 Maximum product is
         * 45000 formed by the increasing subsequence 3, 100, 150. Note that the longest
         * increasing subsequence is different {3, 4, 5, 6}
         * 
         * Input : arr[] = { 10, 22, 9, 33, 21, 50, 41, 60 } Output : 21780000 Maximum
         * product is 21780000 formed by the increasing subsequence 10, 22, 33, 50, 60.
         * 
         * Prerequisite : Longest Increasing Subsequence Approach: Use a dynamic
         * approach to maintain a table mpis[]. The value of mpis[i] stores product
         * maximum product increasing subsequence ending with arr[i]. Initially all the
         * values of increasing subsequence table are initialized to arr[i]. We use
         * recursive approach similar to LIS problem to find the result.
         */
        // Returns product of maximum product
        // increasing subsequence.
        static int lis(int[] arr, int n) {
            int[] mpis = new int[n];
            int max = Integer.MIN_VALUE;

            /* Initialize MPIS values */
            for (int i = 0; i < n; i++)
                mpis[i] = arr[i];

            /*
             * Compute optimized MPIS values considering every element as ending element of
             * sequence
             */
            for (int i = 1; i < n; i++)
                for (int j = 0; j < i; j++)
                    if (arr[i] > arr[j] && mpis[i] < (mpis[j] * arr[i]))
                        mpis[i] = mpis[j] * arr[i];

            /*
             * Pick maximum of all product values using for loop
             */
            for (int k = 0; k < mpis.length; k++) {
                if (mpis[k] > max) {
                    max = mpis[k];
                }
            }

            return max;
        }

        // Driver program to test above function
        static public void main(String[] args) {

            int[] arr = { 3, 100, 4, 5, 150, 6 };
            int n = arr.length;

            System.out.println(lis(arr, n));
        }
    }

    static class CountAllSubsequencesHavingProductLessThanK {
        /**
         * Given a non negative array, find the number of subsequences having product
         * smaller than K. Examples:
         * 
         * 
         * Input : [1, 2, 3, 4] k = 10 Output :11 The subsequences are {1}, {2}, {3},
         * {4}, {1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {1, 2, 3}, {1, 2, 4}
         * 
         * Input : [4, 8, 7, 2] k = 50 Output : 9
         * 
         * This problem can be solved using dynamic programming where dp[i][j] = number
         * of subsequences having product less than i using first j terms of the array.
         * Which can be obtained by : number of subsequences using first j-1 terms +
         * number of subsequences that can be formed using j-th term.
         * 
         */
        // Function to count numbers of such
        // subsequences having product less than k.
        public static int productSubSeqCount(ArrayList<Integer> arr, int k) {
            int n = arr.size();
            int dp[][] = new int[k + 1][n + 1];

            for (int i = 1; i <= k; i++) {
                for (int j = 1; j <= n; j++) {

                    // number of subsequence using j-1 terms
                    dp[i][j] = dp[i][j - 1];

                    // if arr[j-1] > i it will surely make
                    // product greater thus it won't contribute
                    // then
                    if (arr.get(j - 1) <= i && arr.get(j - 1) > 0)

                        // number of subsequence using 1 to j-1
                        // terms and j-th term
                        dp[i][j] += dp[i / arr.get(j - 1)][j - 1] + 1;
                }
            }
            return dp[k][n];
        }

        // Driver code
        public static void main(String args[]) {
            ArrayList<Integer> A = new ArrayList<Integer>();
            A.add(1);
            A.add(2);
            A.add(3);
            A.add(4);
            int k = 10;
            System.out.println(productSubSeqCount(A, k));
        }
    }

    static class MaxSumSubsequenceSuchThatNoThreeAreConsecutive {
        /**
         * Given a sequence of positive numbers, find the maximum sum that can be formed
         * which has no three consecutive elements present. Examples :
         * 
         * Input: arr[] = {1, 2, 3} Output: 5 We can't take three of them, so answer is
         * 2 + 3 = 5
         * 
         * Input: arr[] = {3000, 2000, 1000, 3, 10} Output: 5013 3000 + 2000 + 3 + 10 =
         * 5013
         * 
         * Input: arr[] = {100, 1000, 100, 1000, 1} Output: 2101 100 + 1000 + 1000 + 1 =
         * 2101
         * 
         * Input: arr[] = {1, 1, 1, 1, 1} Output: 4
         * 
         * Input: arr[] = {1, 2, 3, 4, 5, 6, 7, 8} Output: 27
         * 
         * This problem is mainly an extension of below problem. Maximum sum such that
         * no two elements are adjacent We maintain an auxiliary array sum[] (of same
         * size as input array) to find the result.
         * 
         * sum[i] : Stores result for subarray arr[0..i], i.e., maximum possible sum in
         * subarray arr[0..i] such that no three elements are consecutive.
         * 
         * sum[0] = arr[0]
         * 
         * // Note : All elements are positive sum[1] = arr[0] + arr[1]
         * 
         * // We have three cases // 1) Exclude arr[2], i.e., sum[2] = sum[1] // 2)
         * Exclude arr[1], i.e., sum[2] = sum[0] + arr[2] // 3) Exclude arr[0], i.e.,
         * sum[2] = arr[1] + arr[2] sum[2] = max(sum[1], arr[0] + arr[2], arr[1] +
         * arr[2])
         * 
         * In general, // We have three cases // 1) Exclude arr[i], i.e., sum[i] =
         * sum[i-1] // 2) Exclude arr[i-1], i.e., sum[i] = sum[i-2] + arr[i] // 3)
         * Exclude arr[i-2], i.e., sum[i-3] + arr[i] + arr[i-1] sum[i] = max(sum[i-1],
         * sum[i-2] + arr[i], sum[i-3] + arr[i] + arr[i-1])
         * 
         * Below is implementation of above idea.
         */

        // Returns maximum subsequence sum such that no three
        // elements are consecutive
        static int maxSumWO3Consec(int arr[], int n) {
            // Stores result for subarray arr[0..i], i.e.,
            // maximum possible sum in subarray arr[0..i]
            // such that no three elements are consecutive.
            int sum[] = new int[n];

            // Base cases (process first three elements)
            if (n >= 1)
                sum[0] = arr[0];

            if (n >= 2)
                sum[1] = arr[0] + arr[1];

            if (n > 2)
                sum[2] = Math.max(sum[1], Math.max(arr[1] + arr[2], arr[0] + arr[2]));

            // Process rest of the elements
            // We have three cases
            // 1) Exclude arr[i], i.e., sum[i] = sum[i-1]
            // 2) Exclude arr[i-1], i.e., sum[i] = sum[i-2] + arr[i]
            // 3) Exclude arr[i-2], i.e., sum[i-3] + arr[i] + arr[i-1]
            for (int i = 3; i < n; i++)
                sum[i] = Math.max(Math.max(sum[i - 1], sum[i - 2] + arr[i]), arr[i] + arr[i - 1] + sum[i - 3]);

            return sum[n - 1];
        }

        // Driver code
        public static void main(String[] args) {
            int arr[] = { 100, 1000, 100, 1000, 1 };
            int n = arr.length;
            System.out.println(maxSumWO3Consec(arr, n));
        }
    }

    static class LongestSubsequenceSuchThatDifferenceBwnAdjacentsIsOne {
        /**
         * Given an array of n size, the task is to find the longest subsequence such
         * that difference between adjacents is one.
         * 
         * Examples:
         * 
         * Input : arr[] = {10, 9, 4, 5, 4, 8, 6} Output : 3 As longest subsequences
         * with difference 1 are, "10, 9, 8", "4, 5, 4" and "4, 5, 6"
         * 
         * Input : arr[] = {1, 2, 3, 2, 3, 7, 2, 1} Output : 7 As longest consecutive
         * sequence is "1, 2, 3, 2, 3, 2, 1"
         * 
         * This problem is based upon the concept of Longest Increasing Subsequence
         * Problem.
         * 
         * Let arr[0..n-1] be the input array and dp[i] be the length of the longest
         * subsequence (with differences one) ending at index i such that arr[i] is the
         * last element of the subsequence.
         * 
         * Then, dp[i] can be recursively written as: dp[i] = 1 + max(dp[j]) where 0 < j
         * < i and [arr[j] = arr[i] -1 or arr[j] = arr[i] + 1] dp[i] = 1, if no such j
         * exists.
         * 
         * To find the result for a given array, we need to return max(dp[i]) where 0 <
         * i < n.
         * 
         * Following is a Dynamic Programming based implementation. It follows the
         * recursive structure discussed above.
         */

        // Function to find the length of longest
        // subsequence
        static int longestSubseqWithDiffOne(int arr[], int n) {
            // Initialize the dp[] array with 1 as a
            // single element will be of 1 length
            int dp[] = new int[n];
            for (int i = 0; i < n; i++)
                dp[i] = 1;

            // Start traversing the given array
            for (int i = 1; i < n; i++) {
                // Compare with all the previous
                // elements
                for (int j = 0; j < i; j++) {
                    // If the element is consecutive
                    // then consider this subsequence
                    // and update dp[i] if required.
                    if ((arr[i] == arr[j] + 1) || (arr[i] == arr[j] - 1))

                        dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            // Longest length will be the maximum
            // value of dp array.
            int result = 1;
            for (int i = 0; i < n; i++)
                if (result < dp[i])
                    result = dp[i];
            return result;
        }

        // Driver code
        public static void main(String[] args) {
            // Longest subsequence with one
            // difference is
            // {1, 2, 3, 4, 3, 2}
            int arr[] = { 1, 2, 3, 4, 5, 3, 2 };
            int n = arr.length;
            System.out.println(longestSubseqWithDiffOne(arr, n));
        }
    }

    static class MaxLengthLongestSubsequenceWithDifferenceBwnAdjacentAsZeroOrOne {
        /**
         * Given an array of n integers. The problem is to find maximum length of the
         * subsequence with difference between adjacent elements as either 0 or 1.
         * Examples:
         * 
         * 
         * Input : arr[] = {2, 5, 6, 3, 7, 6, 5, 8} Output : 5 The subsequence is {5, 6,
         * 7, 6, 5}.
         * 
         * Input : arr[] = {-2, -1, 5, -1, 4, 0, 3} Output : 4 The subsequence is {-2,
         * -1, -1, 0}. The solution to this problem closely resembles the Longest
         * Increasing Subsequence problem. The only difference is that here we have to
         * check whether the absolute difference between the adjacent elements of the
         * subsequence is either 0 or 1.
         */
        // function to find maximum length subsequence
        // with difference between adjacent elements as
        // either 0 or 1
        public static int maxLenSub(int arr[], int n) {
            int mls[] = new int[n], max = 0;

            // Initialize mls[] values for all indexes
            for (int i = 0; i < n; i++)
                mls[i] = 1;

            // Compute optimized maximum length
            // subsequence values in bottom up manner
            for (int i = 1; i < n; i++)
                for (int j = 0; j < i; j++)
                    if (Math.abs(arr[i] - arr[j]) <= 1 && mls[i] < mls[j] + 1)
                        mls[i] = mls[j] + 1;

            // Store maximum of all 'mls' values in 'max'
            for (int i = 0; i < n; i++)
                if (max < mls[i])
                    max = mls[i];

            // required maximum length subsequence
            return max;
        }

        /* Driver program to test above function */
        public static void main(String[] args) {
            int arr[] = { 2, 5, 6, 3, 7, 6, 5, 8 };
            int n = arr.length;
            System.out.println("Maximum length subsequence = " + maxLenSub(arr, n));

        }
    }

    static class PrintMaximumLengthChainOfPairs {
        /**
         * You are given n pairs of numbers. In every pair, the first number is always
         * smaller than the second number. A pair (c, d) can follow another pair (a, b)
         * if b < c. Chain of pairs can be formed in this fashion. Find the longest
         * chain which can be formed from a given set of pairs.
         * 
         * Examples:
         * 
         * Input: (5, 24), (39, 60), (15, 28), (27, 40), (50, 90) Output: (5, 24), (27,
         * 40), (50, 90)
         * 
         * Input: (11, 20), {10, 40), (45, 60), (39, 40) Output: (11, 20), (39, 40),
         * (45, 60)
         * 
         * In previous post, we have discussed about Maximum Length Chain of Pairs
         * problem. However, the post only covered code related to finding the length of
         * maximum size chain, but not to the construction of maximum size chain. In
         * this post, we will discuss how to construct Maximum Length Chain of Pairs
         * itself.
         * 
         * The idea is to first sort given pairs in increasing order of their first
         * element. Let arr[0..n-1] be the input array of pairs after sorting. We define
         * vector L such that L[i] is itself is a vector that stores Maximum Length
         * Chain of Pairs of arr[0..i] that ends with arr[i]. Therefore for an index i,
         * L[i] can be recursively written as –
         * 
         * L[0] = {arr[0]} L[i] = {Max(L[j])} + arr[i] where j < i and arr[j].b <
         * arr[i].a = arr[i], if there is no such j
         * 
         * For example, for (5, 24), (39, 60), (15, 28), (27, 40), (50, 90)
         * 
         * L[0]: (5, 24) L[1]: (5, 24) (39, 60) L[2]: (15, 28) L[3]: (5, 24) (27, 40)
         * L[4]: (5, 24) (27, 40) (50, 90)
         * 
         * Please note sorting of pairs is done as we need to find the maximum pair
         * length and ordering doesn’t matter here. If we don’t sort, we will get pairs
         * in increasing order but they won’t be maximum possible pairs.
         * 
         * Below is implementation of above idea –
         */

        public static void main(String[] args) throws java.lang.Exception {
            int arr[][] = new int[][] { { 5, 29 }, { 39, 40 }, { 15, 28 }, { 27, 40 }, { 50, 90 } };
            findMaxLengthPair(arr);
        }

        public static void findMaxLengthPair(int[][] arr) {
            int maxLength[] = new int[arr.length];
            maxLength[0] = 1;
            for (int i = 1; i < arr.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[j][1] < arr[i][0] && maxLength[i] < maxLength[j] + 1)
                        maxLength[i] = maxLength[j] + 1;
                }
            }

            printMaxLength(maxLength, arr);
        }

        public static void printMaxLength(int[] maxLengthArray, int[][] arr) {

            int maxLength = maxLengthArray[0];
            int maxLengthIndex = 0;
            for (int i = 1; i < maxLengthArray.length; i++) {
                if (maxLengthArray[i] > maxLength) {
                    maxLength = maxLengthArray[i];
                    maxLengthIndex = i;
                }
            }

            Stack<int[]> stack = new Stack<int[]>();
            stack.push(arr[maxLengthIndex]);
            int j = maxLengthIndex - 1;
            while (j >= 0) {
                if (arr[j][1] < arr[maxLengthIndex][0] && maxLengthArray[maxLengthIndex] == maxLengthArray[j] + 1) {
                    stack.push(arr[j]);
                    maxLengthIndex = j;
                    j = maxLengthIndex - 1;
                } else {
                    j--;
                }
            }

            while (!stack.isEmpty()) {
                int[] temp = stack.pop();
                System.out.print("{" + temp[0] + "," + temp[1] + "}" + " ");
            }
        }
    }

    static class FindPathInMatrixUsingMaximumAverageValue {
        /**
         * Given a square matrix of size N*N, where each cell is associated with a specific cost. A path is defined as a specific sequence of cells which starts from the top-left cell move only right or down and ends on bottom right cell. We want to find a path with the maximum average over all existing paths. Average is computed as total cost divided by the number of cells visited in the path. 
        Examples: 
        

        Input : Matrix = [1, 2, 3
                        4, 5, 6
                        7, 8, 9]
        Output : 5.8
        Path with maximum average is, 1 -> 4 -> 7 -> 8 -> 9
        Sum of the path is 29 and average is 29/5 = 5.8

        One interesting observation is, the only allowed moves are down and right, we need N-1 down moves and N-1 right moves to reach the destination (bottom rightmost). So any path from top left corner to bottom right corner requires 2N – 1 cells. In average value, the denominator is fixed and we need to just maximize numerator. Therefore we basically need to find the maximum sum path. Calculating maximum sum of path is a classic dynamic programming problem, if dp[i][j] represents maximum sum till cell (i, j) from (0, 0) then at each cell (i, j), we update dp[i][j] as below,

        for all i, 1 <= i <= N
            dp[i][0] = dp[i-1][0] + cost[i][0];    
        for all j, 1 <= j <= N
            dp[0][j] = dp[0][j-1] + cost[0][j];            
        otherwise    
            dp[i][j] = max(dp[i-1][j], dp[i][j-1]) + cost[i][j]; 

        Once we get maximum sum of all paths we will divide this sum by (2N – 1) and we will get our maximum average. 
         */
        // method returns maximum average of all
        // path of cost matrix
        public static double maxAverageOfPath(int cost[][], int N) {
            int dp[][] = new int[N + 1][N + 1];
            dp[0][0] = cost[0][0];

            /*
             * Initialize first column of total cost(dp) array
             */
            for (int i = 1; i < N; i++)
                dp[i][0] = dp[i - 1][0] + cost[i][0];

            /* Initialize first row of dp array */
            for (int j = 1; j < N; j++)
                dp[0][j] = dp[0][j - 1] + cost[0][j];

            /* Construct rest of the dp array */
            for (int i = 1; i < N; i++)
                for (int j = 1; j < N; j++)
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + cost[i][j];

            // divide maximum sum by constant path
            // length : (2N - 1) for getting average
            return (double) dp[N - 1][N - 1] / (2 * N - 1);
        }

        /* Driver program to test above function */
        public static void main(String[] args) {
            int cost[][] = { { 1, 2, 3 }, { 6, 5, 4 }, { 7, 3, 9 } };

            System.out.println(maxAverageOfPath(cost, 3));
        }
    }

    static class MaximumGamesPlayedByWinner {
        /**
         * There are N players which are playing a tournament. We need to find the
         * maximum number of games the winner can play. In this tournament, two players
         * are allowed to play against each other only if the difference between games
         * played by them is not more than one. Examples:
         * 
         * 
         * Input : N = 3 Output : 2 Maximum games winner can play = 2 Assume that player
         * are P1, P2 and P3 First, two players will play let (P1, P2) Now winner will
         * play against P3, making total games played by winner = 2
         * 
         * Input : N = 4 Output : 2 Maximum games winner can play = 2 Assume that player
         * are P1, P2, P3 and P4 First two pairs will play lets (P1, P2) and (P3, P4).
         * Now winner of these two games will play against each other, making total
         * games played by winner = 2
         * 
         * We can solve this problem by first computing minimum number of players
         * required such that the winner will play x games. Once this is computed actual
         * problem is just inverse of this. Now assume that dp[i] denotes minimum number
         * of players required so that winner plays i games. We can write a recursive
         * relation among dp values as, dp[i + 1] = dp[i] + dp[i – 1] because if runner
         * up has played (i – 1) games and winner has played i games and all players
         * against which they have played the match are disjoint, total games played by
         * winner will be addition of those two sets of players. Above recursive
         * relation can be written as dp[i] = dp[i – 1] + dp[i – 2] Which is same as the
         * Fibonacci series relation, so our final answer will be the index of the
         * maximal Fibonacci number which is less than or equal to given number of
         * players in the input.
         * 
         */

        // method returns maximum games a winner needs
        // to play in N-player tournament
        static int maxGameByWinner(int N) {
            int[] dp = new int[N];

            // for 0 games, 1 player is needed
            // for 1 game, 2 players are required
            dp[0] = 1;
            dp[1] = 2;

            // loop until i-th Fibonacci number is
            // less than or equal to N
            int i = 2;
            do {
                dp[i] = dp[i - 1] + dp[i - 2];
            } while (dp[i++] <= N);

            // result is (i - 2) because i will be
            // incremented one extra in while loop
            // and we want the last value which is
            // smaller than N, so one more decrement
            return (i - 2);
        }

        // Driver code to test above methods
        public static void main(String args[]) {
            int N = 10;
            System.out.println(maxGameByWinner(N));
        }
    }  

    static class MaximumPathSumInTriangle {
        /**We have given numbers in form of triangle, by starting at the top of the triangle and moving to adjacent numbers on the row below, find the maximum total from top to bottom. 
        Examples : 
        

        Input : 
            3
           7 4
          2 4 6
         8 5 9 3
        Output : 23
        Explanation : 3 + 7 + 4 + 9 = 23 

        Input :
          8
        -4 4
        2 2 6
        1 1 1 1
        Output : 19
        Explanation : 8 + 4 + 6 + 1 = 19  
        
        We can go through the brute force by checking every possible path but that is much time taking so we should try to solve this problem with the help of dynamic programming which reduces the time complexity. 
        If we should left shift every element and put 0 at each empty position to make it a regular matrix, then our problem looks like minimum cost path. 
        So, after converting our input triangle elements into a regular matrix we should apply the dynamic programmic concept to find the maximum path sum. 
        Applying, DP in bottom-up manner we should solve our problem as: 
        Example: 
        

            3
           7 4
          2 4 6
         8 5 9 3

        Step 1 :
        3 0 0 0
        7 4 0 0
        2 4 6 0
        8 5 9 3

        Step 2 :
        3  0  0  0
        7  4  0  0
        10 13 15 0

        Step 3 :
        3  0  0  0
        20 19 0  0

        Step 4:
        23 0 0 0

        output : 23
        */
        static int N = 3;
   
        // Function for finding maximum sum
        static int maxPathSum(int tri[][], int m, int n) {
            // loop for bottom-up calculation
            for (int i = m - 1; i >= 0; i--) {
                for (int j = 0; j <= i; j++) {
                    // for each element, check both
                    // elements just below the number
                    // and below right to the number
                    // add the maximum of them to it
                    if (tri[i + 1][j] > tri[i + 1][j + 1])
                        tri[i][j] += tri[i + 1][j];
                    else
                        tri[i][j] += tri[i + 1][j + 1];
                }
            }

            // return the top element
            // which stores the maximum sum
            return tri[0][0];
        }

        /* Driver program to test above functions */
        public static void main(String[] args) {
            int tri[][] = { { 1, 0, 0 }, { 4, 8, 0 }, { 1, 5, 3 } };
            System.out.println(maxPathSum(tri, 2, 2));
        }
    }

    static class MinimumSumPathInTriangle {
        /**
         * Given a triangular structure of numbers, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

        Examples : 

        Input : 
            2
           3 7
          8 5 6
         6 1 9 3
        Output : 11
        Explanation : 2 + 3 + 5 + 1 = 11

        Input :
            3
           6 4
          5 2 7
         9 1 8 6
        Output : 10
        Explanation : 3 + 4 + 2 + 1 = 10
         */
        /**
         * Naive Approach : Going through the Naive approach by traversing every
         * possible path. But, this is costly. So, use Dynamic Programming here in order
         * to reduce the time complexity. There are two ways to reach the solution :
         * 
         * 1. Memoization – Starting from the top node, traverse recursively with each
         * node, till the pathsum of that node is calculated. And then store the result
         * in an array. But this will take O(N^2) space to maintain the array.
         * 
         * 2. Bottom up – Start from the nodes on the bottom row; the min pathsum for
         * these nodes are the values of the nodes themselves. And after that, minimum
         * pathsum at the ith node of kth row would be the minimum of the pathsum of its
         * two children + the node’s value, i.e.:
         * 
         * memo[k][i] = min( memo[k+1][i], memo[k+1][i+1]) + A[k][i];
         * 
         * OR Simply set memo as a 1D array, and update it this will be space efficient
         * also :
         * 
         * For the row k :
         * 
         * memo[i] = min( memo[i], memo[i+1]) + A[k][i];
         * 
         * Below is the implementation of above dynamic programming approach :
         */
        static int A[][] = { { 2 }, { 3, 9 }, { 1, 6, 7 } };

        // Util function to find
        // minimum sum for a path
        static int minSumPath() {
            // For storing the result
            // in a 1-D array, and
            // simultaneously updating
            // the result.
            int[] memo = new int[A.length];
            int n = A.length - 1;

            // For the bottom row
            for (int i = 0; i < A[n].length; i++)
                memo[i] = A[n][i];

            // Calculation of the
            // remaining rows, in
            // bottom up manner.
            for (int i = A.length - 2; i >= 0; i--)
                for (int j = 0; j < A[i].length; j++)
                    memo[j] = A[i][j] + (int) Math.min(memo[j], memo[j + 1]);

            // return the
            // top element
            return memo[0];
        }

        // Driver Code
        public static void main(String args[]) {
            System.out.print(minSumPath());
        }
    }

    static class MaximumSumPathInRightNumberTriangle {
        /**Given a right triangle of numbers, find the largest of the sum of numbers that appear on the paths starting from the top towards the base, so that on each path the next number is located directly below or below-and-one-place-to-the-right.
        Examples : 
        

        Input : 1
                1 2
                4 1 2
                2 3 1 1        
        Output : 9
        Explanation : 1 + 1 + 4 + 3

        Input : 2
                4 1
                1 2 7
        Output : 10
        Explanation : 2 + 1 + 7 */
        /**
         * The idea is to find the largest sum ending at every cell of the last row and
         * return a maximum of these sums. We can recursively compute these sums by
         * recursively considering the above two cells. Since there are overlapping
         * subproblems, we use dynamic programming to find the maximum sum ending at a
         * particular cell of the last row. Below is the implementation of the above
         * idea.
         */
          
        // function to find maximum sum path
        static int maxSum(int tri[][], int n) {

            // Adding the element of row 1 to both the
            // elements of row 2 to reduce a step from
            // the loop
            if (n > 1)
                tri[1][1] = tri[1][1] + tri[0][0];
            tri[1][0] = tri[1][0] + tri[0][0];

            // Traverse remaining rows
            for (int i = 2; i < n; i++) {
                tri[i][0] = tri[i][0] + tri[i - 1][0];
                tri[i][i] = tri[i][i] + tri[i - 1][i - 1];

                // Loop to traverse columns
                for (int j = 1; j < i; j++) {

                    // Checking the two conditions,
                    // directly below and below right.
                    // Considering the greater one

                    // tri[i] would store the possible
                    // combinations of sum of the paths
                    if (tri[i][j] + tri[i - 1][j - 1] >= tri[i][j] + tri[i - 1][j])

                        tri[i][j] = tri[i][j] + tri[i - 1][j - 1];

                    else
                        tri[i][j] = tri[i][j] + tri[i - 1][j];
                }
            }

            // array at n-1 index (tri[i]) stores
            // all possible adding combination,
            // finding the maximum one out of them
            int max = tri[n - 1][0];

            for (int i = 1; i < n; i++) {
                if (max < tri[n - 1][i])
                    max = tri[n - 1][i];
            }

            return max;
        }

        // Driver code
        public static void main(String[] args) {
            int tri[][] = { { 1 }, { 2, 1 }, { 3, 3, 2 } };

            System.out.println(maxSum(tri, 3));
        }
    }

    static class FindLengthOfSubarrayWithMaxSum {
        /**
         * An array is given, find length of the subarray having maximum sum.
         * 
         * Examples :
         * 
         * Input : a[] = {1, -2, 1, 1, -2, 1} Output : Length of the subarray is 2
         * Explanation: Subarray with consecutive elements and maximum sum will be {1,
         * 1}. So length is 2
         * 
         * Input : ar[] = { -2, -3, 4, -1, -2, 1, 5, -3 } Output : Length of the
         * subarray is 5 Explanation: Subarray with consecutive elements and maximum sum
         * will be {4, -1, -2, 1, 5}.
         * 
         * This problem is mainly a variation of Largest Sum Contiguous Subarray
         * Problem. The idea is to update starting index whenever sum ending here
         * becomes less than 0.
         */
        static int maxSubArraySum(int a[], int size) {
            int max_so_far = Integer.MIN_VALUE, max_ending_here = 0, start = 0, end = 0, s = 0;

            for (int i = 0; i < size; i++) {
                max_ending_here += a[i];

                if (max_so_far < max_ending_here) {
                    max_so_far = max_ending_here;
                    start = s;
                    end = i;
                }

                if (max_ending_here < 0) {
                    max_ending_here = 0;
                    s = i + 1;
                }
            }
            return (end - start + 1);
        }

        // Driver code
        public static void main(String[] args) {
            int a[] = { -2, -3, 4, -1, -2, 1, 5, -3 };
            int n = a.length;
            System.out.println(maxSubArraySum(a, n));
        }
    }

    static class FindMaxSumOfPairsInArrayWithSpecificDifference {
        /**
         * Given an array of integers and a number k. We can pair two numbers of the
         * array if the difference between them is strictly less than k. The task is to
         * find the maximum possible sum of disjoint pairs. Sum of P pairs is the sum of
         * all 2P numbers of pairs.
         * 
         * Examples:
         * 
         * Input : arr[] = {3, 5, 10, 15, 17, 12, 9}, K = 4 Output : 62 Explanation:
         * Then disjoint pairs with difference less than K are, (3, 5), (10, 12), (15,
         * 17) So maximum sum which we can get is 3 + 5 + 12 + 10 + 15 + 17 = 62 Note
         * that an alternate way to form disjoint pairs is, (3, 5), (9, 12), (15, 17),
         * but this pairing produces lesser sum.
         * 
         * Input : arr[] = {5, 15, 10, 300}, k = 12 Output : 25
         */
        /**
         * Approach: First, we sort the given array in increasing order. Once array is
         * sorted, we traverse the array. For every element, we try to pair it with its
         * previous element first. Why do we prefer previous element? Let arr[i] can be
         * paired with arr[i-1] and arr[i-2] (i.e. arr[i] – arr[i-1] < K and
         * arr[i]-arr[i-2] < K). Since the array is sorted, value of arr[i-1] would be
         * more than arr[i-2]. Also, we need to pair with difference less than k, it
         * means if arr[i-2] can be paired, then arr[i-1] can also be paired in a sorted
         * array. Now observing the above facts, we can formulate our dynamic
         * programming solution as below, Let dp[i] denotes the maximum disjoint pair
         * sum we can achieve using first i elements of the array. Assume currently, we
         * are at i’th position, then there are two possibilities for us.
         * 
         * Pair up i with (i-1)th element, 
         *  i.e. dp[i] = dp[i-2] + arr[i] + arr[i-1]
         * Don't pair up, 
         *  i.e. dp[i] = dp[i-1]
         * 
         * Above iteration takes O(N) time and sorting of array will take O(N log N)
         * time so total time complexity of the solution will be O(N log N)
         */
        // method to return maximum sum we can get by
        // finding less than K difference pair
        static int maxSumPairWithDifferenceLessThanK(int arr[], int N, int K) {

            // Sort input array in ascending order.
            Arrays.sort(arr);

            // dp[i] denotes the maximum disjoint pair sum
            // we can achieve using first i elements
            int dp[] = new int[N];

            // if no element then dp value will be 0
            dp[0] = 0;

            for (int i = 1; i < N; i++) {
                // first give previous value to dp[i] i.e.
                // no pairing with (i-1)th element
                dp[i] = dp[i - 1];

                // if current and previous element can form a pair
                if (arr[i] - arr[i - 1] < K) {

                    // update dp[i] by choosing maximum between
                    // pairing and not pairing
                    if (i >= 2)
                        dp[i] = Math.max(dp[i], dp[i - 2] + arr[i] + arr[i - 1]);
                    else
                        dp[i] = Math.max(dp[i], arr[i] + arr[i - 1]);
                }
            }

            // last index will have the result
            return dp[N - 1];
        }

        // Driver code to test above methods
        public static void main(String[] args) {

            int arr[] = { 3, 5, 10, 15, 17, 12, 9 };
            int N = arr.length;
            int K = 4;

            System.out.println(maxSumPairWithDifferenceLessThanK(arr, N, K));

        }
    }

    static class FindMaxNumberOfSegmentsOfLengthsABandCThatCanBeFormedFromN {
        /**
         * Maximum number of segments of lengths a, b and c Given a positive integer N,
         * find the maximum number of segments of lengths a, b and c that can be formed
         * from N . Examples :
         * 
         * 
         * Input : N = 7, a = 5, b, = 2, c = 5 Output : 2 N can be divided into 2
         * segments of lengths 2 and 5. For the second example,
         * 
         * Input : N = 17, a = 2, b = 1, c = 3 Output : 17 N can be divided into 17
         * segments of 1 or 8 segments of 2 and 1 segment of 1. But 17 segments of 1 is
         * greater than 9 segments of 2 and 1.
         * 
         * Approach : The approach used is Dynamic Programming. The base dp0 will be 0
         * as initially it has no segments. After that, iterate from 1 to n, and for
         * each of the 3 states i.e, dpi+a, dpi+b and dpi+c, store the maximum value
         * obtained by either using or not using the a, b or c segment. The 3 states to
         * deal with are :
         * 
         * 
         * dpi+a=max(dpi+1, dpi+a); dpi+b=max(dpi+1, dpi+b); dpi+c=max(dpi+1, dpi+c);
         * 
         * Below is the implementation of above idea :
         * 
         */
        // function to find the maximum
        // number of segments
        static int maximumSegments(int n, int a, int b, int c) {
            // stores the maximum number of
            // segments each index can have
            int dp[] = new int[n + 10];

            // initialize with -1
            Arrays.fill(dp, -1);

            // 0th index will have 0 segments
            // base case
            dp[0] = 0;

            // traverse for all possible
            // segments till n
            for (int i = 0; i < n; i++) {
                if (dp[i] != -1) {

                    // conditions
                    if (i + a <= n) // avoid buffer overflow
                        dp[i + a] = Math.max(dp[i] + 1, dp[i + a]);

                    if (i + b <= n) // avoid buffer overflow
                        dp[i + b] = Math.max(dp[i] + 1, dp[i + b]);

                    if (i + c <= n) // avoid buffer overflow
                        dp[i + c] = Math.max(dp[i] + 1, dp[i + c]);
                }
            }
            return dp[n];
        }

        // Driver code
        public static void main(String arg[]) {
            int n = 7, a = 5, b = 2, c = 5;
            System.out.print(maximumSegments(n, a, b, c));
        }
    }

    static class RecursivelyBreakNumberIn3PartsToGetMaxSum {
        /**
         * Given a number n, we can divide it in only three parts n/2, n/3 and n/4 (we
         * will consider only integer part). The task is to find the maximum sum we can
         * make by dividing number in three parts recursively and summing up them
         * together. Examples:
         * 
         * 
         * Input : n = 12 Output : 13 // We break n = 12 in three parts {12/2, 12/3,
         * 12/4} // = {6, 4, 3}, now current sum is = (6 + 4 + 3) = 13 // again we break
         * 6 = {6/2, 6/3, 6/4} = {3, 2, 1} = 3 + // 2 + 1 = 6 and further breaking 3, 2
         * and 1 we get maximum // summation as 1, so breaking 6 in three parts produces
         * // maximum sum 6 only similarly breaking 4 in three // parts we can get
         * maximum sum 4 and same for 3 also. // Thus maximum sum by breaking number in
         * parts is=13
         * 
         * Input : n = 24 Output : 27 // We break n = 24 in three parts {24/2, 24/3,
         * 24/4} // = {12, 8, 6}, now current sum is = (12 + 8 + 6) = 26 // As seen in
         * example, recursively breaking 12 would // produce value 13. So our maximum
         * sum is 13 + 8 + 6 = 27. // Note that recursively breaking 8 and 6 doesn't
         * produce // more values, that is why they are not broken further.
         * 
         * Input : n = 23 Output : 23 // we break n = 23 in three parts {23/2, 23/3,
         * 23/4} = // {10, 7, 5}, now current sum is = (10 + 7 + 5) = 22. // Since after
         * further breaking we can not get maximum // sum hence number is itself maximum
         * i.e; answer is 23
         */
        /**
         * A simple solution for this problem is to do it recursively. In each call we
         * have to check only max((max(n/2) + max(n/3) + max(n/4)), n) and return it.
         * Because either we can get maximum sum by breaking number in parts or number
         * is itself maximum. Below is the implementation of recursive algorithm. An
         * efficient solution for this problem is to use Dynamic programming because
         * while breaking the number in parts recursively we have to perform some
         * overlapping problems. For example part of n = 30 will be {15,10,7} and part
         * of 15 will be {7,5,3} so we have to repeat the process for 7 two times for
         * further breaking. To avoid this overlapping problem we are using Dynamic
         * programming. We store values in an array and if for any number in recursive
         * calls we have already solution for that number currently so we diretly
         * extract it from array.
         * 
         */
        final int MAX = 1000000;

        // Function to find the maximum sum
        static int breakSum(int n) {
            int dp[] = new int[n + 1];

            // base conditions
            dp[0] = 0;
            dp[1] = 1;

            // Fill in bottom-up manner using recursive
            // formula.
            for (int i = 2; i <= n; i++)
                dp[i] = Math.max(dp[i / 2] + dp[i / 3] + dp[i / 4], i);

            return dp[n];
        }

        // Driver program to test the above function
        public static void main(String[] args) {
            int n = 24;
            System.out.println(breakSum(n));
        }

    }

    static class MaxSumWithTheChoiceOfDivingOrConsideringAsItIs {
        /**
         * We are given a number n, we need to find the maximum sum possible with the
         * help of following function: F(n) = max( (F(n/2) + F(n/3) + F(n/4) + F(n/5)),
         * n). To calculate F(n, ) we may either have n as our result or we can further
         * break n into four part as in given function definition. This can be done as
         * much time as we can. Find the maximum possible sum you can get from a given
         * N. Note : 1 can not be break further so F(1) = 1 as a base case.
         * 
         * Examples :
         * 
         * Input : n = 10 Output : MaxSum = 12 Explanation: f(10) = f(10/2) + f(10/3) +
         * f(10/4) + f(10/5) = f(5) + f(3) + f(2) + f(2) = 12 5, 3 and 2 cannot be
         * further divided.
         * 
         * Input : n = 2 Output : MaxSum = 2 Approach : This problem can be solve with
         * recursive approach but that will cost us a high complexity because of its
         * overlapping sub problems. So we apply dynamic programming concept to solve
         * this question in bottom up manner as:
         */
        // function for calculating max
        // possible result
        static int maxDP(int n) {
            int res[] = new int[n + 1];
            res[0] = 0;
            res[1] = 1;

            // Compute remaining values in
            // bottom up manner.
            for (int i = 2; i <= n; i++)
                res[i] = Math.max(i, (res[i / 2] + res[i / 3] + res[i / 4] + res[i / 5]));

            return res[n];
        }

        // Driver Code
        public static void main(String[] args) {
            int n = 60;
            System.out.println("MaxSum = " + maxDP(n));
        }
    }

    static class FindMaxWeightCostPathEndingAtAnyElementOfLastRowInMatrix {
        /**
         * Given a matrix of integers where every element represents weight of the cell. Find the path having the maximum weight in matrix [N X N]. Path Traversal Rules are: 
            It should begin from top left element.
            The path can end at any element of last row.
            We can move to following two cells from a cell (i, j). 
                Down Move : (i+1, j)
                Diagonal Move : (i+1, j+1)

        Examples: 
        

        Input : N = 5
                mat[5][5] = {{ 4, 2 ,3 ,4 ,1 },
                            { 2 , 9 ,1 ,10 ,5 },
                            {15, 1 ,3 , 0 ,20 },
                            {16 ,92, 41, 44 ,1},
                            {8, 142, 6, 4, 8} };
        Output : 255
        Path with max weight : 4 + 2 +15 + 92 + 142 = 255                 
         */
        /**
         * The above problem can be recursively defined. 

        Let maxCost(i, j) be the cost maximum cost to
        reach mat[i][j].  Since end point can be any point
        in last row, we finally return maximum of all values
        maxCost(N-1, j) where j varies from 0 to N-1.

        If i == 0 and j == 0
        maxCost(0, 0) = mat[0][0]

        // We can traverse through first column only by
        // down move
        Else if j = 0
        maxCost(i, 0) = maxCost(i-1, 0) + mat[i][0]

        // In other cases, a cell mat[i][j] can be reached
        // through previous two cells ma[i-1][j] and 
        // mat[i-1][j-1]
        Else
        maxCost(i, j) = mat[i][j] + max(maxCost(i-1, j),
                                        maxCost(i-1, j-1)),

        If we draw recursion tree of above recursive solution, we can observe overlapping subproblems. Since the problem has overlapping subproblems, we can solve it efficiently using Dynamic Programming. Below is Dynamic Programming based solution. 
         */
        /*
         * Function which return the maximum weight path sum
         */
        public static int maxCost(int mat[][], int N) {
            // create 2D matrix to store the sum of
            // the path
            int dp[][] = new int[N][N];

            dp[0][0] = mat[0][0];

            // Initialize first column of total
            // weight array (dp[i to N][0])
            for (int i = 1; i < N; i++)
                dp[i][0] = mat[i][0] + dp[i - 1][0];

            // Calculate rest path sum of weight matrix
            for (int i = 1; i < N; i++)
                for (int j = 1; j < i + 1 && j < N; j++)
                    dp[i][j] = mat[i][j] + Math.max(dp[i - 1][j - 1], dp[i - 1][j]);

            // find the max weight path sum to reach
            // the last row
            int result = 0;
            for (int i = 0; i < N; i++)
                if (result < dp[N - 1][i])
                    result = dp[N - 1][i];

            // return maximum weight path sum
            return result;
        }

        /* Driver program to test above function */
        public static void main(String[] args) {
            int mat[][] = { { 4, 1, 5, 6, 1 }, { 2, 9, 2, 11, 10 }, { 15, 1, 3, 15, 2 }, { 16, 92, 41, 4, 3 },
                    { 8, 142, 6, 4, 8 } };
            int N = 5;
            System.out.println("Maximum Path Sum : " + maxCost(mat, N));
        }
    }

    static class FindMaxSumIn2xnGridSuchThatNoTwoElementsAreAdjacent {
        /**
                  Given a rectangular grid of dimension 2 x n. We need to find out the maximum sum such that no two chosen numbers are adjacent, vertically, diagonally, or horizontally. 
        Examples: 

        Input: 1 4 5
               2 0 0
        Output: 7
        If we start from 1 then we can add only 5 or 0. 
        So max_sum = 6 in this case.
        If we select 2 then also we can add only 5 or 0.
        So max_sum = 7 in this case.
        If we select from 4 or 0  then there is no further 
        elements can be added.
        So, Max sum is 7.

        Input: 1 2 3 4 5
               6 7 8 9 10
        Output: 24
        */
        /**
         * Approach:
         * 
         * This problem is an extension of Maximum sum such that no two elements are
         * adjacent. The only thing to be changed is to take a maximum element of both
         * rows of a particular column. We traverse column by column and maintain the
         * maximum sum considering two cases. 1) An element of the current column is
         * included. In this case, we take a maximum of two elements in the current
         * column. 2) An element of the current column is excluded (or not included)
         * Below is the implementation of the above steps.
         */
         // Function to find max sum without adjacent
        public static int maxSum(int grid[][], int n) {
            // Sum including maximum element of first
            // column
            int incl = Math.max(grid[0][0], grid[1][0]);

            // Not including first column's element
            int excl = 0, excl_new;

            // Traverse for further elements
            for (int i = 1; i < n; i++) {
                // Update max_sum on including or
                // excluding of previous column
                excl_new = Math.max(excl, incl);

                // Include current column. Add maximum element
                // from both row of current column
                incl = excl + Math.max(grid[0][i], grid[1][i]);

                // If current column doesn't to be included
                excl = excl_new;
            }

            // Return maximum of excl and incl
            // As that will be the maximum sum
            return Math.max(excl, incl);
        }

        /* Driver program to test above function */
        public static void main(String[] args) {
            int grid[][] = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 } };

            int n = 5;
            System.out.println(maxSum(grid, n));
        }
    }

    static class FindMaxDifferenceOfZerosAndOnesInBinaryString {
        /**
         * Given a binary string of 0s and 1s. The task is to find the maximum
         * difference between the number of 0s and number of 1s in any sub-string of the
         * given binary string. That is maximize ( number of 0s – number of 1s ) for any
         * sub-string in the given binary string. Examples:
         * 
         * 
         * Input : S = "11000010001" Output : 6 From index 2 to index 9, there are 7 0s
         * and 1 1s, so number of 0s - number of 1s is 6.
         * 
         * Input : S = "1111" Output : -1
         */
        /**
         * We have discussed Dynamic Programing approach in below post : Maximum
         * difference of zeros and ones in binary string | Set 1. In the post we seen an
         * efficient method that work in O(n) time and in O(1) extra space. Idea behind
         * that if we convert all zeros into 1 and all ones into -1.now our problem
         * reduces to find out the maximum sum sub_array Using Kadane’s Algorithm.
         * 
         * 
         * Input : S = "11000010001" After converting '0' into 1 and '1' into -1 our S
         * Look Like S = -1 -1 1 1 1 1 -1 1 1 1 -1 Now we have to find out Maximum Sum
         * sub_array that is : 6 is that case
         * 
         * Output : 6
         * 
         * Below is the implementation of above idea.
         * 
         */
        // Find the length of substring with maximum
        // difference of zeros and ones in binary
        // string
        public static int findLength(String str, int n) {

            int current_sum = 0;
            int max_sum = 0;

            // traverse a binary string from left to right
            for (int i = 0; i < n; i++) {

                // add current value to the current_sum
                // according to the Character
                // if it's '0' add 1 else -1
                current_sum += (str.charAt(i) == '0' ? 1 : -1);

                if (current_sum < 0)
                    current_sum = 0;

                // update maxium sum
                max_sum = Math.max(current_sum, max_sum);
            }
            // return -1 if string does not contain any zero
            // that means string contains all ones otherwise max_sum
            return max_sum == 0 ? -1 : max_sum;
        }

        public static void main(String[] args) {
            String str = "11000010001";
            int n = str.length();

            System.out.println(findLength(str, n));
        }
    }

    static class MaxPathSumForEachPositionWithJumpsUnderDivisibilityCondition {
        /**
         * Given an array of n positive integers. Initially we are at first position. We
         * can jump to position y (1 <= y <= n) from position x (1 <= x <= n) if x
         * divides y and x < y. The task is to print maximum sum path ending at every
         * position x. Note : Since first element is at position 1, we can jump to any
         * position from here as 1 divides all other position numbers. Examples :
         * 
         * Input : arr[] = {2, 3, 1, 4, 6, 5} Output : 2 5 3 9 8 10 Explanation: Maximum
         * sum path ending with position 1 is 2. For position 1, last position to visit
         * is 1 only. So maximum sum for position 1 = 2.
         * 
         * Maximum sum path ending with position 2 is 5. For position 2, path can be
         * jump from position 1 to 2 as 1 divides 2. So maximum sum for position 2 = 2 +
         * 3 = 5.
         * 
         * For position 3, path can be jump from position 1 to 3 as 1 divides 3. So
         * maximum sum for position 3 = 2 + 1 = 3.
         * 
         * For position 4, path can be jump from position 1 to 2 and 2 to 4. So maximum
         * sum for position 4 = 2 + 3 + 4 = 9.
         * 
         * For position 5, path can be jump from position 1 to 5. So maximum sum for
         * position 5 = 2 + 6 = 8.
         * 
         * For position 6, path can be jump from position 1 to 2 and 2 to 6 or 1 to 3
         * and 3 to 6. But path 1 -> 2 -> 6 gives maximum sum for position 6 = 2 + 3 + 5
         * = 10.
         */
        /**
         * Approach:
         * 
         * The idea is to use Dynamic Programming to solve this problem.
         * 
         * Create an 1-D array dp[] where each element dp[i] stores maximum sum path
         * ending at index i (or position x where x = i+1) with divisible jumps.
         * 
         * The recurrence relation for dp[i] can be defined as:
         * 
         * dp[i] = max(dp[i], dp[divisor of i+1] + arr[i])
         * 
         * To find all the divisor of i+1, move from 1 divisor to sqrt(i+1).
         * 
         * Below is the implementation of this approach:
         */
        static void printMaxSum(int arr[], int n) {
            // Create an array such that dp[i]
            // stores maximum path sum ending with i.
            int dp[] = new int[n];
            Arrays.fill(dp, 0);

            // Calculating maximum sum
            // path for each element.
            for (int i = 0; i < n; i++) {
                dp[i] = arr[i];

                // Finding previous step for arr[i]
                // Moving from 1 to sqrt(i+1) since all the
                // divisors are present from sqrt(i+1).
                int maxi = 0;
                for (int j = 1; j <= Math.sqrt(i + 1); j++) {

                    // Checking if j is divisor of i+1.
                    if (((i + 1) % j == 0) && (i + 1) != j) {

                        // Checking which divisor will
                        // provide greater value.
                        if (dp[j - 1] > maxi)
                            maxi = dp[j - 1];
                        if (dp[(i + 1) / j - 1] > maxi && j != 1)
                            maxi = dp[(i + 1) / j - 1];
                    }
                }

                dp[i] += maxi;
            }

            // Printing the answer (Maximum path sum
            // ending with every position i+1.)
            for (int i = 0; i < n; i++)
                System.out.print(dp[i] + " ");
        }

        // Driver code
        public static void main(String[] args) {
            int arr[] = { 2, 3, 1, 4, 6, 5 };
            int n = arr.length;

            // Function calling
            printMaxSum(arr, n);
        }
    }

    static class MaximizeSumOfSelectedNumbersFromArrayToMakeItEmptyWithDeletionOfAdjacents {
        /**
         * Given an array of N numbers, we need to maximize the sum of selected numbers.
         * At each step, you need to select a number Ai, delete one occurrence of it and
         * delete all occurrences of Ai-1 and Ai+1 (if they exist) in the array. Repeat
         * these steps until the array gets empty. The problem is to maximize the sum of
         * the selected numbers. Note: We have to delete all the occurrences of Ai+1 and
         * Ai-1 elements if they are present in the array and not Ai+1 and Ai-1.
         * Examples:
         * 
         * 
         * Input : a[] = {1, 2, 3} Output : 4 Explanation: At first step we select 1, so
         * 1 and 2 are deleted from the sequence leaving us with 3. Then we select 3
         * from the sequence and delete it. So the sum of selected numbers is 1+3 = 4.
         * 
         * 
         * Input : a[] = {1, 2, 2, 2, 3, 4} Output : 10 Explanation : Select one of the
         * 2's from the array, so 2, 2-1, 2+1 will be deleted and we are left with {2,
         * 2, 4}, since 1 and 3 are deleted. Select 2 in next two steps, and then select
         * 4 in the last step. We get a sum of 2+2+2+4=10 which is the maximum possible.
         */
        /**
         * Our aim is to maximize the sum of selected numbers. The idea is to
         * pre-calculate the occurrence of all numbers x in the array a[].
         * 
         * Approach:
         * 
         * Calculate the MAX value in the array. Create an array of size MAX and store
         * the occurrences of each element in it. Since we want to maximize our answer,
         * we will start iterating from the MAX value to 0. If the occurrence of the ith
         * element is greater than 0, then add it to our answer decrease the occurrences
         * of the i-1th element by 1, and also decrease the occurrence of ith by 1 since
         * we have added it to our answer. We don’t have to decrease the occurrence of
         * the i+1th element because we are already starting from the end so i+1th is
         * already processed. There might be multiple occurrences of the ith element
         * that’s why do not decrease i yet, to stay on the same element.
         * 
         * Below is the implementation of above idea:
         */
        // Function to maximise the sum of selected nummbers
        // by deleting occurences of Ai-1 and Ai+1
        public static int getMaximumSum(int arr[]) {

            // Number of elements in the array
            int n = arr.length;

            // Largest element in the array
            int max = -1;
            for (int i = 0; i < n; i++) {
                max = Math.max(max, arr[i]);
            }

            // An array to count the occurence of each element
            int[] freq = new int[max + 1];

            for (int i = 0; i < n; i++) {
                freq[arr[i]]++;
            }

            // ans to store the result
            int ans = 0, i = max;

            // Using the above mentioned approach
            while (i > 0) {

                // if occurence is greater than 0
                if (freq[i] > 0) {
                    // add it to ans
                    ans += i;

                    // decrease i-1th element by 1
                    freq[i - 1]--;

                    // decrease ith element by 1
                    freq[i]--;
                } else {
                    // decrease i
                    i--;
                }
            }

            return ans;
        }

        // Driver code
        public static void main(String[] args) {
            int[] a = { 1, 2, 3 };

            System.out.println(getMaximumSum(a));
        }
    }

    static class MaximumSubarraySumInArrayCreatedAfterRepeatedConcatenation {
        /**
         * Given an array and a number k, find the largest sum of contiguous array in
         * the modified array which is formed by repeating the given array k times.
         * Examples :
         * 
         * Input : arr[] = {-1, 10, 20}, k = 2 Output : 59 After concatenating array
         * twice, we get {-1, 10, 20, -1, 10, 20} which has maximum subarray sum as 59.
         * 
         * Input : arr[] = {-1, -2, -3}, k = 3 Output : -1
         */
        /**
         * A simple solution is to create an array of size n*k, then run Kadane’s
         * algorithm. Time complexity would be O(nk) and auxiliary space would be O(n*k)
         * A better solution is to run a loop on same array and use modular arithmetic
         * to move back beginning after end of array.
         */
        // Returns sum of maximum sum
        // subarray created after
        // concatenating a[0..n-1] k times.
        static int maxSubArraySumRepeated(int a[], int n, int k) {
            int max_so_far = 0;
            int INT_MIN, max_ending_here = 0;

            for (int i = 0; i < n * k; i++) {
                // This is where it differs from
                // Kadane's algorithm. We use modular
                // arithmetic to find next element.
                max_ending_here = max_ending_here + a[i % n];

                if (max_so_far < max_ending_here)
                    max_so_far = max_ending_here;

                if (max_ending_here < 0)
                    max_ending_here = 0;
            }
            return max_so_far;
        }

        // Driver program to test maxSubArraySum
        public static void main(String[] args) {

            int a[] = { 10, 20, -30, -1 };
            int n = a.length;
            int k = 3;

            System.out.println("Maximum contiguous sum is " + maxSubArraySumRepeated(a, n, k));
        }
    }

    static class MaximumPathSumInMatrixStartingWithAnyCellOf0throwAndEndingWithAnyCellOfN_1thRow {
        /**
         * Given a N X N matrix Mat[N][N] of positive integers. There are only three possible moves from a cell (i, j) 
            (i+1, j)
            (i+1, j-1)
            (i+1, j+1)

        Starting from any column in row 0, return the largest sum of any of the paths up to row N-1.
        Examples: 
        

        Input : mat[4][4] = { {4, 2, 3, 4},
                            {2, 9, 1, 10},
                            {15, 1, 3, 0},
                            {16, 92, 41, 44} };
        Output :120
        path : 4 + 9 + 15 + 92 = 120 
         */
        /**
         * The above problem can be recursively defined. Let initial position be
         * MaximumPathSum(N-1, j), where j varies from 0 to N-1. We return maximum value
         * between all path that we start traversing (N-1, j) [ where j varies from 0 to
         * N-1]
         * 
         * 
         * i = N-1, j = 0 to N -1 
         * 
         * int MaximumPath(Mat[][N], I, j)
         * 
         * // IF we reached to first row of // matrix then return value of that //
         * element 
         * 
         * IF ( i == 0 && j = 0 ) 
         * return Mat[i][j]
         * 
         * // out of matrix bound 
         * IF( i = N || j < 0 ) return 0;
         * 
         * // call all rest position that we reached // from current position and find
         * maximum // between them and add current value in // that path 
         * 
         * return
         * max(MaximumPath(Mat, i-1, j), MaximumPath(Mat, i-1, j-1), MaximumPath(Mat,
         * i-1, j+1))) + Mat[i][j];
         * 
         * If we draw recursion tree of above recursive solution, we can observe
         * overlapping subproblems. Since the problem has overlapping subproblems, we
         * can solve it efficiently using Dynamic Programming. Below is Dynamic
         * Programming based solution.
         */
        static int N = 4;

        // function find maximum sum path
        static int MaximumPath(int Mat[][]) {
            int result = 0;

            // create 2D matrix to store the sum
            // of the path
            int dp[][] = new int[N][N + 2];

            // initialize all dp matrix as '0'
            for (int[] rows : dp)
                Arrays.fill(rows, 0);

            // copy all element of first column into
            // 'dp' first column
            for (int i = 0; i < N; i++)
                dp[0][i + 1] = Mat[0][i];

            for (int i = 1; i < N; i++)
                for (int j = 1; j <= N; j++)
                    dp[i][j] = Math.max(dp[i - 1][j - 1], Math.max(dp[i - 1][j], dp[i - 1][j + 1])) + Mat[i][j - 1];

            // Find maximum path sum that end ups
            // at any column of last row 'N-1'
            for (int i = 0; i <= N; i++)
                result = Math.max(result, dp[N - 1][i]);

            // return maximum sum path
            return result;
        }

        // driver code
        public static void main(String arg[]) {
            int Mat[][] = { { 4, 2, 3, 4 }, { 2, 9, 1, 10 }, { 15, 1, 3, 0 }, { 16, 92, 41, 44 } };

            System.out.println(MaximumPath(Mat));
        }
    }

    static class KnapsackMinimumCostToFillGivenWeightInaBag {
        /**
         * You are given a bag of size W kg and you are provided costs of packets
         * different weights of oranges in array cost[] where cost[i] is basically the
         * cost of ‘i’ kg packet of oranges. Where cost[i] = -1 means that ‘i’ kg packet
         * of orange is unavailable Find the minimum total cost to buy exactly W kg
         * oranges and if it is not possible to buy exactly W kg oranges then print -1.
         * It may be assumed that there is an infinite supply of all available packet
         * types. Note: array starts from index 1.
         * 
         * Examples:
         * 
         * Input : W = 5, cost[] = {20, 10, 4, 50, 100} Output : 14 We can choose two
         * oranges to minimize cost. First orange of 2Kg and cost 10. Second orange of
         * 3Kg and cost 4.
         * 
         * Input : W = 5, cost[] = {1, 10, 4, 50, 100} Output : 5 We can choose five
         * oranges of weight 1 kg.
         * 
         * Input : W = 5, cost[] = {1, 2, 3, 4, 5} Output : 5 Costs of 1, 2, 3, 4 and 5
         * kg packets are 1, 2, 3, 4 and 5 Rs respectively. We choose packet of 5kg
         * having cost 5 for minimum cost to get 5Kg oranges.
         * 
         * Input : W = 5, cost[] = {-1, -1, 4, 5, -1} Output : -1 Packets of size 1, 2
         * and 5 kg are unavailable because they have cost -1. Cost of 3 kg packet is 4
         * Rs and of 4 kg is 5 Rs. Here we have only weights 3 and 4 so by using these
         * two we can not make exactly W kg weight, therefore answer is -1.
         **/
        /**

        This problem is can be reduced to Unbounded Knapsack. So in the cost array, we first ignore those packets which are not available i.e; cost is -1 and then traverse the cost array and create two array val[] for storing the cost of ‘i’ kg packet of orange and wt[] for storing weight of the corresponding packet. Suppose cost[i] = 50 so the weight of the packet will be i and the cost will be 50. 
        Algorithm :

        Create matrix min_cost[n+1][W+1], where n is number of distinct weighted packets of orange and W is the maximum capacity of the bag.
        Initialize the 0th row with INF (infinity) and 0th Column with 0.
        Now fill the matrix
            if wt[i-1] > j then min_cost[i][j] = min_cost[i-1][j] ;
            if wt[i-1] <= j then min_cost[i][j] = min(min_cost[i-1][j], val[i-1] + min_cost[i][j-wt[i-1]]);
        If min_cost[n][W]==INF then output will be -1 because this means that we cant not make make weight W by using these weights else output will be min_cost[n][W].
         */
            // cost[] initial cost array including
            // unavailable packet W capacity of bag
            public static int MinimumCost(int cost[], int n, int W) {
                // val[] and wt[] arrays
                // val[] array to store cost of 'i' kg
                // packet of orange wt[] array weight of
                // packet of orange
                Vector<Integer> val = new Vector<Integer>();
                Vector<Integer> wt = new Vector<Integer>();

                // traverse the original cost[] array and skip
                // unavailable packets and make val[] and wt[]
                // array. size variable tells the available
                // number of distinct weighted packets
                int size = 0;
                for (int i = 0; i < n; i++) {
                    if (cost[i] != -1) {
                        val.add(cost[i]);
                        wt.add(i + 1);
                        size++;
                    }
                }

                n = size;
                int min_cost[][] = new int[n + 1][W + 1];

                // fill 0th row with infinity
                for (int i = 0; i <= W; i++)
                    min_cost[0][i] = Integer.MAX_VALUE;

                // fill 0'th column with 0
                for (int i = 1; i <= n; i++)
                    min_cost[i][0] = 0;

                // now check for each weight one by one and
                // fill the matrix according to the condition
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= W; j++) {
                        // wt[i-1]>j means capacity of bag is
                        // less then weight of item
                        if (wt.get(i - 1) > j)
                            min_cost[i][j] = min_cost[i - 1][j];

                        // here we check we get minimum cost
                        // either by including it or excluding
                        // it
                        else
                            min_cost[i][j] = Math.min(min_cost[i - 1][j],
                                    min_cost[i][j - wt.get(i - 1)] + val.get(i - 1));
                    }
                }

                // exactly weight W can not be made by
                // given weights
                return (min_cost[n][W] == Integer.MAX_VALUE) ? -1 : min_cost[n][W];
            }

            /* Driver program to test above function */
            public static void main(String[] args) {
                int cost[] = { 1, 2, 3, 4, 5 }, W = 5;
                int n = cost.length;

                System.out.println(MinimumCost(cost, n, W));
            }
    }

    static class MinimumSumOfMuliplicationsOfN_Numbers {
        /**
         * Given n integers. The task is to minimize the sum of multiplication of all the numbers by taking two adjacent numbers at a time and putting back their sum % 100 till a number is left. 
            Examples : 

            Input : 40 60 20 
            Output : 2400  
            Explanation: There are two possible cases:
            1st possibility: Take 40 and 60, so multiplication=2400
            and put back (60+40) % 100 = 0, making it 0, 20.
            Multiplying 0 and 20 we get 0 so 
            multiplication = 2400+0 = 2400. Put back (0+20)%100 = 20. 
            2nd possibility: take 60 and 20, so 60*20 = 1200,
            put back (60+20)%100 = 80, making it [40, 80] 
            multiply 40*80 to get 3200, so multiplication
            sum = 1200+3200 = 4400. Put back (40+80)%100 = 20 

            Input : 5 6 
            Output : 30 
            Explanation: Only possibility is 5*6=30 
         */
        /**
        Approach: The problem is a variation of Matrix chain Multiplication Dynamic Programming
        The idea is to partition N numbers into every possible value of k. Solve recursively for smaller parts and add the multiplication and store the minimum of them. Since we are dividing it into k parts, for every DPi,j we will have k partitions i<=k<j , store the minimum of them. So we get the formula similar to Matrix chain Multiplication Dynamic Programming. 

            DPi,j = min(DPi,j , (DPi,k+DPk+1,j+(cumulative_sumi,k*cumulative_sumk+1,j) ) ) 
            for every i<=k<j. 

        Since many subproblems will be repeating, hence we use memoization to store the values in a nXn matrix. 
         */
     
        // Used in recursive
        // memoized solution
        static long dp[][] = new long[1000][1000];

        // function to calculate the
        // cumulative sum from a[i] to a[j]
        static long sum(int a[], int i, int j) {
            long ans = 0;
            for (int m = i; m <= j; m++)
                ans = (ans + a[m]) % 100;
            return ans;
        }

        static long solve(int a[], int i, int j) {
            // base case
            if (i == j)
                return 0;

            // memoization, if the partition
            // has been called before then
            // return the stored value
            if (dp[i][j] != -1)
                return dp[i][j];

            // store a max value
            dp[i][j] = 100000000;

            // we break them into k partitions
            for (int k = i; k < j; k++) {
                // store the min of the
                // formula thus obtained
                dp[i][j] = Math.min(dp[i][j],
                        (solve(a, i, k) + solve(a, k + 1, j) + (sum(a, i, k) * sum(a, k + 1, j))));
            }

            // return the minimum
            return dp[i][j];
        }

        static void initialize(int n) {
            for (int i = 0; i <= n; i++)
                for (int j = 0; j <= n; j++)
                    dp[i][j] = -1;
        }

        // Driver code
        public static void main(String args[]) {
            int a[] = { 40, 60, 20 };
            int n = a.length;
            initialize(n);
            System.out.println(solve(a, 0, n - 1));

        }
    }

    static class LongestCommonSubstring {
        /**
         * Given two strings ‘X’ and ‘Y’, find the length of the longest common substring. 

        Examples : 

            Input : X = “GeeksforGeeks”, y = “GeeksQuiz” 
            Output : 5 
            Explanation:
            The longest common substring is “Geeks” and is of length 5.

            Input : X = “abcdxyz”, y = “xyzabcd” 
            Output : 4 
            Explanation:
            The longest common substring is “abcd” and is of length 4.

            Input : X = “zxabcdezy”, y = “yzabcdezx” 
            Output : 6 
            Explanation:
            The longest common substring is “abcdez” and is of length 6.
         */
        /**
         * Approach: Let m and n be the lengths of first and second strings
         * respectively. A simple solution is to one by one consider all substrings of
         * first string and for every substring check if it is a substring in second
         * string. Keep track of the maximum length substring. There will be O(m^2)
         * substrings and we can find whether a string is subsring on another string in
         * O(n) time (See this). So overall time complexity of this method would be O(n
         * * m2) 
         * 
         * Dynamic Programming can be used to find the longest common substring in
         * O(m*n) time. The idea is to find length of the longest common suffix for all
         * substrings of both strings and store these lengths in a table.
         */
        /**
         * The longest common suffix has following optimal substructure property. 
            If last characters match, then we reduce both lengths by 1 
            LCSuff(X, Y, m, n) = LCSuff(X, Y, m-1, n-1) + 1 if X[m-1] = Y[n-1] 
            If last characters do not match, then result is 0, i.e., 
            LCSuff(X, Y, m, n) = 0 if (X[m-1] != Y[n-1])
            Now we consider suffixes of different substrings ending at different indexes. 
            The maximum length Longest Common Suffix is the longest common substring. 
            LCSubStr(X, Y, m, n) = Max(LCSuff(X, Y, i, j)) where 1 <= i <= m and 1 <= j <= n 
         */
        /*
         * Returns length of longest common substring of X[0..m-1] and Y[0..n-1]
         */
        static int LCSubStr(char X[], char Y[], int m, int n) {
            // Create a table to store
            // lengths of longest common
            // suffixes of substrings.
            // Note that LCSuff[i][j]
            // contains length of longest
            // common suffix of
            // X[0..i-1] and Y[0..j-1].
            // The first row and first
            // column entries have no
            // logical meaning, they are
            // used only for simplicity of program
            int LCStuff[][] = new int[m + 1][n + 1];

            // To store length of the longest
            // common substring
            int result = 0;

            // Following steps build
            // LCSuff[m+1][n+1] in bottom up fashion
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0 || j == 0)
                        LCStuff[i][j] = 0;
                    else if (X[i - 1] == Y[j - 1]) {
                        LCStuff[i][j] = LCStuff[i - 1][j - 1] + 1;
                        result = Integer.max(result, LCStuff[i][j]);
                    } else
                        LCStuff[i][j] = 0;
                }
            }
            return result;
        }

        // Driver Code
        public static void main(String[] args) {
            String X = "OldSite:GeeksforGeeks.org";
            String Y = "NewSite:GeeksQuiz.com";

            int m = X.length();
            int n = Y.length();

            System.out.println(LCSubStr(X.toCharArray(), Y.toCharArray(), m, n));
        }
    }

    static class SumOfAllSubstringsOfStringRepresentingNumber {
        /**
         * Given an integer represented as a string, we need to get the sum of all possible substrings of this string.
            Examples:  

            Input  : num = “1234”
            Output : 1670
            Sum = 1 + 2 + 3 + 4 + 12 + 23 +
                34 + 123 + 234 + 1234 
                = 1670

            Input  : num = “421”
            Output : 491
            Sum = 4 + 2 + 1 + 42 + 21 + 421 = 491
         */
        /**
         * We can solve this problem by using dynamic programming. We can write a summation of all substrings on basis of the digit at which they are ending in that case, 
            Sum of all substrings = sumofdigit[0] + sumofdigit[1] + sumofdigit[2] … + sumofdigit[n-1] where n is length of string.
            Where sumofdigit[i] stores the sum of all substring ending at ith index digit, in the above example, 

            Example : num = "1234"
            sumofdigit[0] = 1 = 1
            sumofdigit[1] = 2 + 12  = 14
            sumofdigit[2] = 3 + 23  + 123 = 149
            sumofdigit[3] = 4 + 34  + 234 + 1234  = 1506
            Result = 1670

            Now we can get the relation between sumofdigit values and can solve the question iteratively. Each sumofdigit can be represented in terms of previous value as shown below, 

            For above example,
            sumofdigit[3] = 4 + 34 + 234 + 1234
                    = 4 + 30 + 4 + 230 + 4 + 1230 + 4
                    = 4*4 + 10*(3 + 23 +123)
                    = 4*4 + 10*(sumofdigit[2])
            In general, 
            sumofdigit[i]  =  (i+1)*num[i] + 10*sumofdigit[i-1]

            Using the above relation we can solve the problem in linear time. In the below code a complete array is taken to store sumofdigit, as each sumofdigit value requires just the previous value, we can solve this problem without allocating the complete array also. 
            
         */
        // Returns sum of all substring of num
        public static int sumOfSubstrings(String num) {
            int n = num.length();

            // allocate memory equal to length of string
            int sumofdigit[] = new int[n];

            // initialize first value with first digit
            sumofdigit[0] = num.charAt(0) - '0';
            int res = sumofdigit[0];

            // loop over all digits of string
            for (int i = 1; i < n; i++) {
                int numi = num.charAt(i) - '0';

                // update each sumofdigit from previous value
                sumofdigit[i] = (i + 1) * numi + 10 * sumofdigit[i - 1];

                // add current value to the result
                res += sumofdigit[i];
            }

            return res;
        }

        // Driver code to test above methods
        public static void main(String[] args) {
            String num = "1234";

            System.out.println(sumOfSubstrings(num));
        }
    }

    static class FindNumberOfEndlessPointsOrPointsWithAdjacentsAs1sInMatrix {
        /**
         * Given a binary N x N matrix, we need to find the total number of matrix positions from which there is an endless path. Any position (i, j) is said to have an endless path if and only if the position (i, j) has the value 1 and all the next positions in its row(i) and its column(j) should have value 1. If any position next to (i, j) either in a row(i) or in column(j) will have 0 then position (i, j) doesn’t have any endless path.

        Examples:  

        Input :  0 1 0
                1 1 1
                0 1 1
        Output : 4
        Endless points are (1, 1), (1, 2),
        (2, 1) and (2, 2). For all other
        points path to some corner is 
        blocked at some point.


        Input :  0 1 1
                1 1 0
                0 1 0
        Output : 1
        Endless point is (0, 1).
         */
        /**
         * Naive Approach : We traverse all positions, for every position, we check that
         * does this position has an endless path or not. If yes then count it otherwise
         * ignore it. But as usual, its time complexity seems to be high. Time
         * complexity: O(n3)
         * 
         * Advance Approach (Dynamic programming): We can easily say that if there is a
         * zero at any position, then it will block the path for all the positions left
         * to it and on top of it.
         * 
         * Also, we can say that any position (i,j) will have an endless row if (i,j+1)
         * will have an endless row and the value of (i, j) is 1. Similarly, we can say
         * that any position (i,j) will have an endless column if (i+1,j) will have an
         * endless column and the value of (i, j) is 1.
         * 
         * So we should maintain two matrices one for the row and one for the column.
         * Always start from the rightmost position for row and bottom-most position for
         * column and only check for next position whether it has an endless path or
         * not. And Finally, if any position will have an endless path in both row and
         * column matrix then that position is said to have an endless path.
         * 
         */

        static final int MAX = 100;

        // Returns count of endless points
        static int countEndless(boolean input[][], int n) {

            boolean row[][] = new boolean[n][n];
            boolean col[][] = new boolean[n][n];

            // Fills column matrix. For every column,
            // start from every last row and fill every
            // entry as blockage after a 0 is found.
            for (int j = 0; j < n; j++) {

                // flag which will be zero once we get
                // a '0' and it will be 1 otherwise
                boolean isEndless = true;
                for (int i = n - 1; i >= 0; i--) {

                    // encountered a '0', set the
                    // isEndless variable to false
                    if (input[i][j] == false)
                        isEndless = false;

                    col[i][j] = isEndless;
                }
            }

            // Similarly, fill row matrix
            for (int i = 0; i < n; i++) {
                boolean isEndless = true;
                for (int j = n - 1; j >= 0; j--) {
                    if (input[i][j] == false)
                        isEndless = false;
                    row[i][j] = isEndless;
                }
            }

            // Calculate total count of endless points
            int ans = 0;
            for (int i = 0; i < n; i++)
                for (int j = 1; j < n; j++)

                    // If there is NO blockage in row
                    // or column after this point,
                    // increment result.
                    if (row[i][j] && col[i][j])
                        ans++;

            return ans;
        }

        // driver code
        public static void main(String arg[]) {
            boolean input[][] = { { true, false, true, true }, { false, true, true, true }, { true, true, true, true },
                    { false, true, true, false } };
            int n = 4;

            System.out.print(countEndless(input, n));
        }
    }

    static class FindMaxPossibleStolenOrSumValueFromHousesWhereNoTwoElementsAreAdjacent {
        // Find Max sum subsequence where no two selected elements are adjacent
        /**
         * There are n houses build in a line, each of which contains some value in it.
         * A thief is going to steal the maximal value of these houses, but he can’t
         * steal in two adjacent houses because the owner of the stolen houses will tell
         * his two neighbors left and right side. What is the maximum stolen value?
         * Examples:
         * 
         * 
         * Input: hval[] = {6, 7, 1, 3, 8, 2, 4} Output: 19
         * 
         * Explanation: The thief will steal 6, 1, 8 and 4 from the house.
         * 
         * Input: hval[] = {5, 3, 4, 11, 2} Output: 16
         * 
         * Explanation: Thief will steal 5 and 11
         */
        /**
         * Naive Approach: Given an array, the solution is to find the maximum sum
         * subsequence where no two selected elements are adjacent. So the approach to
         * the problem is a recursive solution. So there are two cases.
         * 
         * 
         * If an element is selected then the next element cannot be selected. if an
         * element is not selected then the next element can be selected.
         * 
         * So the recursive solution can easily be devised. The sub-problems can be
         * stored thus reducing the complexity and converting the recursive solution to
         * a Dynamic programming problem.
         */
        /**
         * Algorithm: 
            Create an extra space dp, DP array to store the sub-problems.
            Tackle some basic cases, if the length of the array is 0, print 0, if the length of the array is 1, print the first element, if the length of the array is 2, print maximum of two elements.
            Update dp[0] as array[0] and dp[1] as maximum of array[0] and array[1]
            Traverse the array from the second element (2nd index) to the end of array.
            For every index, update dp[i] as maximum of dp[i-2] + array[i] and dp[i-1], this step defines the two cases, if an element is selected then the previous element cannot be selected and if an element is not selected then the previous element can be selected.
            Print the value dp[n-1]
         */
        // Function to calculate the maximum stolen value
        static int maxLoot(int hval[], int n) {
            if (n == 0)
                return 0;
            if (n == 1)
                return hval[0];
            if (n == 2)
                return Math.max(hval[0], hval[1]);

            // dp[i] represent the maximum value stolen
            // so far after reaching house i.
            int[] dp = new int[n];

            // Initialize the dp[0] and dp[1]
            dp[0] = hval[0];
            dp[1] = Math.max(hval[0], hval[1]);

            // Fill remaining positions
            for (int i = 2; i < n; i++)
                dp[i] = Math.max(hval[i] + dp[i - 2], dp[i - 1]);

            return dp[n - 1];
        }

        // Driver program
        public static void main(String[] args) {
            int hval[] = { 6, 7, 1, 3, 8, 2, 4 };
            int n = hval.length;
            System.out.println("Maximum loot value : " + maxLoot(hval, n));
        }
    }

    static class CountOfDifferentWaysToExpress_N_asSumOf1_3_and_4{
    /**
     * Given N, count the number of ways to express N as sum of 1, 3 and 4.

        Examples: 

        Input :  N = 4
        Output : 4 
        Explanation: 1+1+1+1 
                    1+3
                    3+1 
                    4 

        Input : N = 5 
        Output : 6
        Explanation: 1 + 1 + 1 + 1 + 1
                    1 + 4
                    4 + 1
                    1 + 1 + 3
                    1 + 3 + 1
                    3 + 1 + 1
            */

           /**
     * Approach : Divide the problem into sub-problems for solving it. Let DP[n] be
     * the be the number of ways to write N as the sum of 1, 3, and 4. Consider one
     * possible solution with n = x1 + x2 + x3 + … xn. If the last number is 1, then
     * sum of the remaining numbers is n-1. So the number that ends with 1 is equal
     * to DP[n-1]. Taking other cases into account where the last number is 3 and 4.
     * The final recurrence would be:
     * 
     * DPn = DPn-1 + DPn-3 + DPn-4
     * 
     * Base case : DP[0] = DP[1] = DP[2] = 1 and DP[3] = 2
     */
    // Function to count the
    // number of ways to represent
    // n as sum of 1, 3 and 4
    static int countWays(int n) {
        int DP[] = new int[n + 1];

        // base cases
        DP[0] = DP[1] = DP[2] = 1;
        DP[3] = 2;

        // iterate for all values from 4 to n
        for (int i = 4; i <= n; i++)
            DP[i] = DP[i - 1] + DP[i - 3] + DP[i - 4];

        return DP[n];
    }

    // driver code
    public static void main(String[] args) {
        int n = 10;
        System.out.println(countWays(n));
    }
}

    static class CountWaysToBuildOfficeandHousesInStreetUnderGivenConstraints {
        /**
         * There is a street of length n and as we know it has two sides. Therefore a total of 2 * n spots are available. In each of these spots either a house or an office can be built with following 2 restrictions: 

        1. No two offices on the same side of the street can be adjacent. 
        2. No two offices on different sides of the street can be exactly opposite to each other i.e. they can’t overlook each other. 
        There are no restrictions on building houses and each spot must either have a house or office. 
        Given length of the street n, find total number of ways to build the street.

        Examples:  

        Input : 2
        Output : 7
        Please see below diagram for explanation.

        Input : 3
        Output : 17
         */
        /**
         * Following image depicts the 7 possible ways for building the street with N = 2 
         * Image: https://media.geeksforgeeks.org/wp-content/uploads/count-ways-build-street.png
         */
        /**
         * 
         * Ways for building street with length 1
        with 2 houses: (H H) = {1}
        with 1 office and 1 house: (O H) (H O) = {2}
        (O O) is not allowed according to the problem statement.
        Total = 1  + 2 = 3

        For length = 2,
        with 2 houses: (H H) can be added to all
        the cases of length 1:

        (H H)    (H H)    (H H)
        (H H)    (O H)    (H O) = {3}

        with 1 office and 1 house:
        (O H) and (H O) can both be added to all 
        the cases of length 1 with last row (H H)

        (O H)    (H O)
        (H H)    (H H) = {2}

        when last row of a case of length 1 
        contains 1 office, it can only be 
        extended in one way to build an office
        in row 2. 
        (H O)    (O H)    
        (O H)    (H O) = {2}

        (O H)    (H O) (O O)    
        (O H)    (H O) (O H) etc are not allowed.

        Total = 3 + 2 + 2 = 7
         */
        /**
         * Since the problem can be solved by finding solution for smaller subproblems and then extending the same logic, it can be solved using dynamic programming. 
         * We move in steps of one unit length. For each row we have two options: 


         Build houses in both the spots 
         Build one house and one office
    
         The first one can be done without any constraints. There is one way of building houses in both the spots at length i. So total ways using this choice = total ways for length i – 1.
         For the second choice, if row (i-1) had houses in both spots we have two ways of building a office i.e. (H O) and (O H) 
         if row(i-1) had an office in one of its two spots we only have one way to build an office in row i.If prev row had (O H) curr row would have (H O) and similarly for prev row = (H O) curr row = (O H). 
         From the above logic, total ways with this choice = 2 * (choice1(i-1)) + choice2(i-1)
        We will build a 2D dp for this. 
        dp[0][i] indicates choice1 and dp[1][i] indicates choice2 for row i.

        Below is the implementation of above idea :  
         */
        // function to count ways of building
        // a street of n rows
        static long countWays(int n) {
            long dp[][] = new long[2][n + 1];

            // base case
            dp[0][1] = 1;
            dp[1][1] = 2;

            for (int i = 2; i <= n; i++) {

                // ways of building houses in both
                // the spots of ith row
                dp[0][i] = dp[0][i - 1] + dp[1][i - 1];

                // ways of building an office in one of
                // the two spots of ith row
                dp[1][i] = dp[0][i - 1] * 2 + dp[1][i - 1];
            }

            // total ways for n rows
            return dp[0][n] + dp[1][n];
        }

        // driver program for checking above function
        public static void main(String[] args) {

            int n = 5;
            System.out.print("Total no of ways with n = " + n + " are: " + countWays(n));
        }
    }

    static class CountPairsWhenaPersonCanFormPairWithAtmostOne {
        /**
         * Consider a coding competition on geeksforgeeks practice. Now their are n distinct participants taking part in the competition. A single participant can make pair with at most one other participant. We need count the number of ways in which n participants participating in the coding competition.
            Examples : 
            

            Input : n = 2
            Output : 2
            2 shows that either both participant 
            can pair themselves in one way or both 
            of them can remain single.

            Input : n = 3 
            Output : 4
            One way : Three participants remain single
            Three More Ways : [(1, 2)(3)], [(1), (2,3)]
            and [(1,3)(2)]
         */
        /**
         * 1) Every participant can either pair with another participant or can remain single. 
            2) Let us consider X-th participant, he can either remain single or 
            he can pair up with someone from [1, x-1].
         */
        static int numberOfWays(int x) {
            int dp[] = new int[x + 1];
            dp[0] = dp[1] = 1;

            for (int i = 2; i <= x; i++)
                dp[i] = dp[i - 1] + (i - 1) * dp[i - 2];

            return dp[x];
        }

        // Driver code
        public static void main(String[] args) {
            int x = 3;
            System.out.println(numberOfWays(x));

        }
    }

    static class CountPathsFromaPointToReachOrigin {
        /**
         * You are standing on a point (n, m) and you want to go to origin (0, 0) by taking steps either left or down i.e. from each point you are allowed to move either in (n-1, m) or (n, m-1). Find the number of paths from point to origin.
            Examples: 

            Input : 3 6
            Output : Number of Paths 84

            Input : 3 0
            Output : Number of Paths 1
         */
        /**
                     * As we are restricted to move down and left only we would run a recursive loop for each of the combinations of the 
            steps that can be taken.

            // Recursive function to count number of paths
            countPaths(n, m)
            {
                // If we reach bottom or top left, we are
                // have only one way to reach (0, 0)
                if (n==0 || m==0)
                    return 1;

                // Else count sum of both ways
                return (countPaths(n-1, m) + countPaths(n, m-1));
            } 
         */
    
        // DP based function to count number of paths
        static int countPaths(int n, int m) {
            int dp[][] = new int[n + 1][m + 1];

            // Fill entries in bottommost row and leftmost
            // columns
            for (int i = 0; i <= n; i++)
                dp[i][0] = 1;
            for (int i = 0; i <= m; i++)
                dp[0][i] = 1;

            // Fill DP in bottom up manner
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= m; j++)
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];

            return dp[n][m];
        }

        // Driver Code
        public static void main(String[] args) {
            int n = 3, m = 2;
            System.out.println(" Number of Paths " + countPaths(n, m));

        }
    }

    static class CountNumberOfWaysToCoverDistanceWith1_2_3_steps {
        /**
         * Given a distance ‘dist, count total number of ways to cover the distance with 1, 2 and 3 steps. 

        Examples: 

        Input: n = 3
        Output: 4
        Explantion:
        Below are the four ways
        1 step + 1 step + 1 step
        1 step + 2 step
        2 step + 1 step
        3 step

        Input: n = 4
        Output: 7
        Explantion:
        Below are the four ways
        1 step + 1 step + 1 step + 1 step
        1 step + 2 step + 1 step
        2 step + 1 step + 1 step 
        1 step + 1 step + 2 step
        2 step + 2 step
        3 step + 1 step
        1 step + 3 step
                */

        /**
         * Recursive solution  

            Approach: There are n stairs, and a person is allowed to next step, skip one position or skip two positions. So there are n positions. 
            The idea is standing at the ith position the person can move by i+1, i+2, i+3 position. 
            So a recursive function can be formed where at current index i the function is recursively called for i+1, i+2 and i+3 positions. 
            There is another way of forming the recursive function. 
            To reach position i, a person has to jump either from i-1, i-2 or i-3 position where i is the starting position. 
            
            Algorithm: 
                Create a recursive function (count(int n)) which takes only one parameter.
                Check the base cases. If the value of n is less than 0 then return 0, and if value of n is equal to zero then return 1 as it is the starting position.
                Call the function recursively with values n-1, n-2 and n-3 and sum up the values that are returned, i.e. sum = count(n-1) + count(n-2) + count(n-3).
                Return the value of sum.
         */
        /**
         * Approach: The idea is similar, but it can be observed that there are n states but the recursive function is called 3 ^ n times. That means that some states are called repeatedly. So the idea is to store the value of states. This can be done in two ways. 

            The first way is to keep the recursive structure intact and just store the value in a HashMap and whenever the function is called, return the value store without computing (Top-Down Approach).
            The second way is to take an extra space of size n and start computing values of states from 1, 2 .. to n, i.e. compute values of i, i+1, i+2 and then use them to calculate the value of i+3 (Bottom-Up Approach).
            Overlapping Subproblems in Dynamic Programming.
            Optimal substructure property in Dynamic Programming.
            Dynamic Programming(DP) problems

        Algorithm: 

            Create an array of size n + 1 and initialize the first 3 variables with 1, 1, 2. The base cases.
            Run a loop from 3 to n.
            For each index i, compute value of ith position as dp[i] = dp[i-1] + dp[i-2] + dp[i-3].
            Print the value of dp[n], as the Count of number of ways to cover a distance.
         */
        // Function returns count of ways to cover 'dist'
        static int printCountDP(int dist) {
            int[] count = new int[dist + 1];

            // Initialize base values. There is one way to
            // cover 0 and 1 distances and two ways to
            // cover 2 distance
            count[0] = 1;
            if (dist >= 1)
                count[1] = 1;
            if (dist >= 2)
                count[2] = 2;

            // Fill the count array in bottom up manner
            for (int i = 3; i <= dist; i++)
                count[i] = count[i - 1] + count[i - 2] + count[i - 3];

            return count[dist];
        }

        // driver program
        public static void main(String[] args) {
            int dist = 4;
            System.out.println(printCountDP(dist));
        }
    }

    static class CountOfArraysHavingConsecutiveElementWithDifferentValues {
        /**
         * Given three positive integers n, k and x. The task is to count the number of
         * different array that can be formed of size n such that each element is
         * between 1 to k and two consecutive element are different. Also, the first and
         * last elements of each array should be 1 and x respectively.
         * 
         * Examples :
         * 
         * Input : n = 4, k = 3, x = 2 Output : 3
         */
        /**
         * The idea is to use Dynamic Programming and combinatorics to solve the problem. 
            First of all, notice that the answer is same for all x from 2 to k. It can easily be proved. This will be useful later on. 
            Let the state f(i) denote the number of ways to fill the range [1, i] of array A such that A1 = 1 and Ai ≠ 1. 
            Therefore, if x ≠ 1, the answer to the problem is f(n)/(k – 1), because f(n) is the number of way where An is filled with a number from 2 to k, 
            and the answer are equal for all such values An, so the answer for an individual value is f(n)/(k – 1). 
            Otherwise, if x = 1, the answer is f(n – 1), because An – 1 ≠ 1, and the only number we can fill An with is x = 1. 

            Now, the main problem is how to calculate f(i). Consider all numbers that Ai – 1 can be. We know that it must lie in [1, k]. 

            If Ai – 1 ≠ 1, then there are (k – 2)f(i – 1) ways to fill in the rest of the array, because Ai cannot be 1 or Ai – 1 (so we multiply with (k – 2)), 
            and for the range [1, i – 1], there are, recursively, f(i – 1) ways.
            If Ai – 1 = 1, then there are (k – 1)f(i – 2) ways to fill in the rest of the array, because Ai – 1 = 1 means Ai – 2 ≠ 1 
            which means there are f(i – 2)ways to fill in the range [1, i – 2] and the only value that Ai cannot be 1, so we have (k – 1) choices for Ai.

            By combining the above, we get 

            f(i) = (k - 1) * f(i - 2) + (k - 2) * f(i - 1)

            This will help us to use dynamic programming using f(i).

            Below is the implementation of this approach:  
         */
        static int MAXN = 109;

        public static int countarray(int n, int k, int x) {
            int[] dp = new int[109];

            // Initialising dp[0] and dp[1].
            dp[0] = 0;
            dp[1] = 1;

            // Computing f(i) for each 2 <= i <= n.
            for (int i = 2; i < n; i++)
                dp[i] = (k - 2) * dp[i - 1] + (k - 1) * dp[i - 2];

            return (x == 1 ? (k - 1) * dp[n - 2] : dp[n - 1]);
        }

        // driver code
        public static void main(String[] args) {
            int n = 4, k = 3, x = 2;
            System.out.println(countarray(n, k, x));
        }
    }

    static class CountWaysToDivideCircleUsingNnonIntersectingChords {
       /**
        * Given a number N, find the number of ways you can draw N chords in a circle with 2*N points such that no 2 chords intersect. 
            Two ways are different if there exists a chord which is present in one way and not in other.
            Examples: 
            

            Input : N = 2
            Output : 2
            Explanation: If points are numbered 1 to 4 in 
            clockwise direction, then different ways to 
            draw chords are:
            {(1-2), (3-4)} and {(1-4), (2-3)}


            Input : N = 1
            Output : 1
            Explanation: Draw a chord between points 1 and 2. */ 
           /**
        * If we draw a chord between any two points, can you observe the current set of
        * points getting broken into two smaller sets S_1 and S_2. If we draw a chord
        * from a point in S_1 to a point in S_2, it will surely intersect the chord
        * we’ve just drawn. 
        
         So, we can arrive at a recurrence that Ways(n) = sum[i = 0
        * to n-1] { Ways(i)*Ways(n-i-1) }. 
        
        Here we iterate over i, assuming that size
        * of one of the sets is i and size of another set automatically is (n-i-1)
        * since we’ve already used a pair of points and i pair of points in one set.
        */
           static int chordCnt(int A) {

               // n = no of points required
               int n = 2 * A;

               // dp array containing the sum
               int[] dpArray = new int[n + 1];
               dpArray[0] = 1;
               dpArray[2] = 1;
               for (int i = 4; i <= n; i += 2) {
                   for (int j = 0; j < i - 1; j += 2) {
                       dpArray[i] += (dpArray[j] * dpArray[i - 2 - j]);
                   }
               }

               // returning the required number
               return dpArray[n];
           }

           public static void main(String[] args) {
               int N;
               N = 2;
               System.out.println(chordCnt(N));
               N = 1;
               System.out.println(chordCnt(N));
               N = 4;
               System.out.println(chordCnt(N));
           }
    }

    static class CountWaysToTileFloorOfSizeNxM_using_1xMsizeTiles {
        /**
         * Given a floor of size n x m and tiles of size 1 x m. The problem is to count the number of ways to tile the given floor using 1 x m tiles. A tile can either be placed horizontally or vertically. 
            Both n and m are positive integers and 2 < = m.
            Examples: 
            

            Input : n = 2, m = 3
            Output : 1
            Only one combination to place 
            two tiles of size 1 x 3 horizontally
            on the floor of size 2 x 3. 

            Input :  n = 4, m = 4
            Output : 2
            1st combination:
            All tiles are placed horizontally
            2nd combination:
            All tiles are placed vertically.
         */
        /**
                     * This problem is mainly a more generalized approach to the Tiling Problem. 
            Approach: For a given value of n and m, the number of ways to tile the floor can be obtained from the following relation. 
            

                        |  1, 1 < = n < m
            count(n) = |  2, n = m
                        | count(n-1) + count(n-m), m < n
                        
         */
            // function to count the total number of ways
            static int countWays(int n, int m) {
                // table to store values
                // of subproblems
                int count[] = new int[n + 1];
                count[0] = 0;

                // Fill the table upto value n
                int i;
                for (i = 1; i <= n; i++) {

                    // recurrence relation
                    if (i > m)
                        count[i] = count[i - 1] + count[i - m];

                    // base cases
                    else if (i < m || i == 1)
                        count[i] = 1;

                    // i = = m
                    else
                        count[i] = 2;
                }

                // required number of ways
                return count[n];
            }

            // Driver program
            public static void main(String[] args) {
                int n = 7;
                int m = 4;
                System.out.println("Number of ways = " + countWays(n, m));
            }
    }

    static class MinimumRemovalsFromArrayToMakeMax_minus_Min_LessThanEqualToK {
            /**
                     * Given N integers and K, find the minimum number of elements that should be removed, such that Amax-Amin<=K. After the removal of elements, Amax and Amin is considered among the remaining elements. 

        Examples: 

        Input : a[] = {1, 3, 4, 9, 10, 11, 12, 17, 20} 
                k = 4 
        Output : 5 
        Explanation: Remove 1, 3, 4 from beginning 
        and 17, 20 from the end.

        Input : a[] = {1, 5, 6, 2, 8}  K=2
        Output : 3
        Explanation: There are multiple ways to 
        remove elements in this case.
        One among them is to remove 5, 6, 8.
        The other is to remove 1, 2, 5
             */
            /**
             * Approach: Sort the given elements. Using the greedy approach, the best way is
             * to remove the minimum element or the maximum element and then check if
             * Amax-Amin <= K. There are various combinations of removals that have to be
             * considered. We write a recurrence relation to try every possible combination.
             * There will be two possible ways of removal, either we remove the minimum or
             * we remove the maximum. Let(i…j) be the index of elements left after removal
             * of elements. Initially, we start with i=0 and j=n-1 and the number of
             * elements removed is 0 at the beginning. We only remove an element if
             * a[j]-a[i]>k, the two possible ways of removal are (i+1…j) or (i…j-1). The
             * minimum of the two is considered. Let DPi, j be the number of elements that
             * need to be removed so that after removal a[j]-a[i]<=k.
             * 
             * Recurrence relation for sorted array:
             * 
             * DPi, j = 1+ (min(countRemovals(i+1, j), countRemovals(i, j-1))
             * 
             * Below is the implementation of the above idea:
             */

            static int MAX = 100;
            static int dp[][] = new int[MAX][MAX];

            // function to check all possible combinations
            // of removal and return the minimum one
            static int countRemovals(int a[], int i, int j, int k) {
                // base case when all elements are removed
                if (i >= j)
                    return 0;

                // if condition is satisfied, no more
                // removals are required
                else if ((a[j] - a[i]) <= k)
                    return 0;

                // if the state has already been visited
                else if (dp[i][j] != -1)
                    return dp[i][j];

                // when Amax-Amin>d
                else if ((a[j] - a[i]) > k) {

                    // minimum is taken of the removal
                    // of minimum element or removal
                    // of the maximum element
                    dp[i][j] = 1 + Math.min(countRemovals(a, i + 1, j, k), countRemovals(a, i, j - 1, k));
                }
                return dp[i][j];
            }

            // To sort the array and return the answer
            static int removals(int a[], int n, int k) {
                // sort the array
                Arrays.sort(a);

                // fill all stated with -1
                // when only one element
                for (int[] rows : dp)
                    Arrays.fill(rows, -1);
                if (n == 1)
                    return 0;
                else
                    return countRemovals(a, 0, n - 1, k);
            }

            // Driver code
            public static void main(String[] args) {
                int a[] = { 1, 3, 4, 9, 10, 11, 12, 17, 20 };
                int n = a.length;
                int k = 4;
                System.out.print(removals(a, n, k));
            }
    }

    static class MinimumStepsToMinimizeNto1asPerCondition {
        /**
         * Given a number n, count minimum steps to minimize it to 1 according to the
         * following criteria:
         * 
         * If n is divisible by 2 then we may reduce n to n/2. If n is divisible by 3
         * then you may reduce n to n/3. Decrement n by 1.
         * 
         * Examples:
         * 
         * Input : n = 10 Output : 3
         * 
         * Input : 6 Output : 2
         */
        /**
         * Dynamic Approach: 
            For finding minimum steps we have three possibilities for n and they are: 

            f(n) = 1 + f(n-1)
            f(n) = 1 + f(n/2) // if n is divisible by 2
            f(n) = 1 + f(n/3) // if n is divisible by 3

            Below is memoization based implementation of above recursive formula.
         */
        // function to calculate min steps
        static int getMinSteps(int n, int memo[]) {
            // base case
            if (n == 1)
                return 0;
            if (memo[n] != -1)
                return memo[n];

            // store temp value for
            // n as min( f(n-1),
            // f(n/2), f(n/3)) +1
            int res = getMinSteps(n - 1, memo);

            if (n % 2 == 0)
                res = Math.min(res, getMinSteps(n / 2, memo));
            if (n % 3 == 0)
                res = Math.min(res, getMinSteps(n / 3, memo));

            // store memo[n] and return
            memo[n] = 1 + res;
            return memo[n];
        }

        // This function mainly
        // initializes memo[] and
        // calls getMinSteps(n, memo)
        static int getMinSteps(int n) {
            int memo[] = new int[n + 1];

            // initialize memoized array
            for (int i = 0; i <= n; i++)
                memo[i] = -1;

            return getMinSteps(n, memo);
        }

        // Driver Code
        public static void main(String[] args) {
            int n = 10;
            System.out.println(getMinSteps(n));
        }
    }

    static class MinimumTimeToWriteCharactersUsingInsert_Delete_CopyOperation_onScreen {
        /**
         * We need to write N same characters on a screen and each time we can insert a character, delete the last character and copy and paste all written characters i.e. after copy operation count of total written character will become twice. Now we are given time for insertion, deletion and copying. We need to output minimum time to write N characters on the screen using these operations.

        Examples: 

        Input : N = 9    
                insert time = 1    
                removal time = 2    
                copy time = 1
        Output : 5
        N character can be written on screen in 5 time units as shown below,
        insert a character    
        characters = 1  total time = 1
        again insert character      
        characters = 2  total time = 2
        copy characters             
        characters = 4  total time = 3
        copy characters             
        characters = 8  total time = 4
        insert character           
        characters = 9  total time = 5
         */
        /**
         * We can solve this problem using dynamic programming. We can observe a pattern after solving some examples by hand that for writing each character we have two choices either get it by inserting or get it by copying, whichever takes less time. Now writing relation accordingly, 
        Let dp[i] be the optimal time to write i characters on screen then, 

        If i is even then,
        dp[i] = min((dp[i-1] + insert_time), 
                    (dp[i/2] + copy_time))
        Else (If i is odd)
        dp[i] = min(dp[i-1] + insert_time),
                    (dp[(i+1)/2] + copy_time + removal_time)

        In the case of odd, removal time is added because when (i+1)/2 characters will be copied one extra character will be on the screen which needs to be removed.
         */
        // method returns minimum time to write
        // 'N' characters
        static int minTimeForWritingChars(int N, int insert, int remove, int copy) {
            if (N == 0)
                return 0;
            if (N == 1)
                return insert;

            // declare dp array and initialize with zero
            int dp[] = new int[N + 1];

            // first char will always take insertion time
            dp[1] = insert;

            // loop for 'N' number of times
            for (int i = 2; i <= N; i++) {
                /*
                 * if current char count is even then choose minimum from result for (i-1) chars
                 * and time for insertion and result for half of chars and time for copy
                 */
                if (i % 2 == 0)
                    dp[i] = Math.min(dp[i - 1] + insert, dp[i / 2] + copy);

                /*
                 * if current char count is odd then choose minimum from result for (i-1) chars
                 * and time for insertion and result for half of chars and time for copy and one
                 * extra character deletion
                 */
                else
                    dp[i] = Math.min(dp[i - 1] + insert, dp[(i + 1) / 2] + copy + remove);
            }
            return dp[N];
        }

        // Driver code to test above methods
        public static void main(String[] args) {
            int N = 9;
            int insert = 1, remove = 2, copy = 1;
            System.out.println(minTimeForWritingChars(N, insert, remove, copy));
        }
    }

    static class CountAllPossiblePathsFromTopLeftToBottomRightOfMxN_Matrix {
        /**
         * The problem is to count all the possible paths from top left to bottom right of a mXn matrix with the constraints that from each cell you can either move only to right or down
        Examples : 
        

        Input :  m = 2, n = 2;
        Output : 2
        There are two paths
        (0, 0) -> (0, 1) -> (1, 1)
        (0, 0) -> (1, 0) -> (1, 1)

        Input :  m = 2, n = 3;
        Output : 3
        There are three paths
        (0, 0) -> (0, 1) -> (0, 2) -> (1, 2)
        (0, 0) -> (0, 1) -> (1, 1) -> (1, 2)
        (0, 0) -> (1, 0) -> (1, 1) -> (1, 2)
         */
        /**
         * We have discussed a solution to print all possible paths, counting all paths
         * is easier. Let NumberOfPaths(m, n) be the count of paths to reach row number
         * m and column number n in the matrix, NumberOfPaths(m, n) can be recursively
         * written as following.
         * 
        int numberOfPaths(int m, int n)
        {
            // If either given row number is first or given column
            // number is first
            if (m == 1 || n == 1)
                return 1;
        
            // If diagonal movements are allowed then the last
            // addition is required.
            return numberOfPaths(m - 1, n) + numberOfPaths(m, n - 1);
            // + numberOfPaths(m-1, n-1);
        }
         */
        // Returns count of possible paths to reach
        // cell at row number m and column number n from
        // the topmost leftmost cell (cell at 1, 1)
        static int numberOfPaths(int m, int n) {
            // Create a 2D table to store results
            // of subproblems
            int count[][] = new int[m][n];

            // Count of paths to reach any cell in
            // first column is 1
            for (int i = 0; i < m; i++)
                count[i][0] = 1;

            // Count of paths to reach any cell in
            // first column is 1
            for (int j = 0; j < n; j++)
                count[0][j] = 1;

            // Calculate count of paths for other
            // cells in bottom-up manner using
            // the recursive solution
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++)

                    // By uncommenting the last part the
                    // code calculates the total possible paths
                    // if the diagonal Movements are allowed
                    count[i][j] = count[i - 1][j] + count[i][j - 1]; // + count[i-1][j-1];
            }
            return count[m - 1][n - 1];
        }

        // Driver program to test above function
        public static void main(String args[]) {
            System.out.println(numberOfPaths(3, 3));
        }
    }

    static class CountWaysToFill_Nx4_Grid_using_1x4_Tiles {
        /**
         * Given a number n, count number of ways to fill a n x 4 grid using 1 x 4 tiles.
            Examples: 
            

            Input : n = 1
            Output : 1

            Input : n = 2
            Output : 1
            We can only place both tiles horizontally

            Input : n = 3
            Output : 1
            We can only place all tiles horizontally.

            Input : n = 4
            Output : 2
            The two ways are : 
            1) Place all tiles horizontally 
            2) Place all tiles vertically.

            Input : n = 5
            Output : 3
            We can fill a 5 x 4 grid in following ways : 
            1) Place all 5 tiles horizontally
            2) Place first 4 vertically and 1 horizontally.
            3) Place first 1 horizontally and 4 horizontally.
         */
        /**
         * This problem is mainly an extension of this tiling problem
            Let “count(n)” be the count of ways to place tiles on a “n x 4” grid, following two cases arise when we place the first tile. 
            

                Place the first tile horizontally : If we place first tile horizontally, the problem reduces to “count(n-1)”
                Place the first tile vertically : If we place first tile vertically, then we must place 3 more tiles vertically. So the problem reduces to “count(n-4)”

                Therefore, count(n) can be written as below. 
            

            count(n) = 1 if n = 1 or n = 2 or n = 3   
            count(n) = 2 if n = 4
            count(n) = count(n-1) + count(n-4) 

            This recurrence is similar to Fibonacci Numbers and can be solved using Dynamic programming.
         */
        // Function that count the number of ways to place 1 x 4 tiles
        // on n x 4 grid.
        static int count(int n) {
            // Create a table to store results of sub-problems
            // dp[i] stores count of ways for i x 4 grid.
            int[] dp = new int[n + 1];
            dp[0] = 0;
            // Fill the table from d[1] to dp[n]
            for (int i = 1; i <= n; i++) {
                // Base cases
                if (i >= 1 && i <= 3)
                    dp[i] = 1;
                else if (i == 4)
                    dp[i] = 2;

                else {
                    // dp(i-1) : Place first tile horizontally
                    // dp(i-4) : Place first tile vertically
                    // which means 3 more tiles have
                    // to be placed vertically.
                    dp[i] = dp[i - 1] + dp[i - 4];
                }
            }
            return dp[n];
        }

        // Driver program
        public static void main(String[] args) {
            int n = 5;
            System.out.println("Count of ways is: " + count(n));
        }

    }

    static class SmallestOrMinimumSumContiguousSubArray {
        /**
         * Given an array containing n integers. The problem is to find the sum of the
         * elements of the contiguous subarray having the smallest(minimum) sum.
         * Examples:
         * 
         * 
         * Input : arr[] = {3, -4, 2, -3, -1, 7, -5} Output : -6 Subarray is {-4, 2, -3,
         * -1} = -6
         * 
         * Input : arr = {2, 6, 8, 1, 4} Output : 1
         */
        // function to find the smallest sum contiguous
        // subarray
        static int smallestSumSubarr(int arr[], int n) {

            // to store the minimum value that is
            // ending up to the current index
            int min_ending_here = 2147483647;

            // to store the minimum value encountered
            // so far
            int min_so_far = 2147483647;

            // traverse the array elements
            for (int i = 0; i < n; i++) {

                // if min_ending_here > 0, then it could
                // not possibly contribute to the
                // minimum sum further
                if (min_ending_here > 0)
                    min_ending_here = arr[i];

                // else add the value arr[i] to
                // min_ending_here
                else
                    min_ending_here += arr[i];

                // update min_so_far
                min_so_far = Math.min(min_so_far, min_ending_here);
            }

            // required smallest sum contiguous
            // subarray value
            return min_so_far;
        }

        // Driver method
        public static void main(String[] args) {

            int arr[] = { 3, -4, 2, -3, -1, 7, -5 };
            int n = arr.length;

            System.out.print("Smallest sum: " + smallestSumSubarr(arr, n));
        }
    }

    static class PrintArrayElementOrSizeAfterRepeatedDeletionOfLIS {
        /**
         * Given an array arr[0..n-1] of the positive element. The task is to print the remaining elements of arr[] after repeated deletion of LIS (of size greater than 1). If there are multiple LIS with the same length, we need to choose the LIS that ends first.
        Examples: 

        Input : arr[] = {1, 2, 5, 3, 6, 4, 1}
        Output : 1
        Explanation : 
        {1, 2, 5, 3, 6, 4, 1} - {1, 2, 5, 6} = {3, 4, 1}
        {3, 4, 1} - {3, 4} = {1}

        Input : arr[] = {1, 2, 3, 1, 5, 2}
        Output : -1
        Explanation : 
        {1, 2, 3, 1, 5, 2} - {1, 2, 3, 5} = {1, 2}
        {1, 2} - {1, 2} = {}

        Input : arr[] = {5, 3, 2}
        Output : 3
         */
        // Function to conMaximum Sum LIS
        static Vector<Integer> findLIS(Vector<Integer> arr, int n) {
            // L[i] - The Maximum Sum Increasing
            // Subsequence that ends with arr[i]
            Vector<Integer>[] L = new Vector[n];

            for (int i = 0; i < L.length; i++)
                L[i] = new Vector<Integer>();

            // L[0] is equal to arr[0]
            L[0].add(arr.elementAt(0));

            // Start from index 1
            for (int i = 1; i < n; i++) {
                // For every j less than i
                for (int j = 0; j < i; j++) {
                    // L[i] = {MaxSum(L[j])} + arr[i]
                    // where j < i and arr[j] < arr[i]
                    if (arr.elementAt(i) > arr.elementAt(j) && (L[i].size() < L[j].size()))
                        L[i] = L[j];
                }

                // L[i] ends with arr[i]
                L[i].add(arr.elementAt(i));
            }

            // Set lis = LIS
            // whose size is max among all
            int maxSize = 1;
            Vector<Integer> lis = new Vector<>();

            for (Vector<Integer> x : L) {
                // The > sign makes sure that the LIS
                // ending first is chose.
                if (x.size() > maxSize) {
                    lis = x;
                    maxSize = x.size();
                }
            }
            return lis;
        }

        // Function to minimize array
        static void minimize(int input[], int n) {
            Vector<Integer> arr = new Vector<>();
            for (int i = 0; i < n; i++)
                arr.add(input[i]);

            while (arr.size() != 0) {
                // Find LIS of current array
                Vector<Integer> lis = findLIS(arr, arr.size());

                // If all elements are
                // in decreasing order
                if (lis.size() < 2)
                    break;

                // Remove lis elements from
                // current array. Note that both
                // lis[] and arr[] are sorted in
                // increasing order.
                for (int i = 0; i < arr.size() && lis.size() > 0; i++) {
                    // If first element of lis[] is found
                    if (arr.elementAt(i) == lis.elementAt(0)) {
                        // Remove lis element from arr[]
                        arr.removeAll(lis);
                        i--;

                        // Erase first element of lis[]
                        lis.remove(0);
                    }
                }
            }

            // print remaining element of array
            int i;
            for (i = 1; i < arr.size(); i++)
                System.out.print(arr.elementAt(i) + " ");

            // print -1 for empty array
            if (i == 0)
                System.out.print("-1");
        }

        // Driver function
        public static void main(String[] args) {
            int input[] = { 3, 2, 6, 4, 5, 1 };
            int n = input.length;

            // minimize array after deleting LIS
            minimize(input, n);
        }
    }

    static class RemoveEndElementsFromArrayToMaximizeSumOfProduct {
        /**
         * Given an array of N positive integers. We are allowed to remove element from either of the two ends i.e from the left side or right side of the array. Each time we remove an element, score is increased by value of element * (number of element already removed + 1). The task is to find the maximum score that can be obtained by removing all the element.
        Examples: 
        

        Input : arr[] = { 1, 3, 1, 5, 2 }.
        Output : 43
        Remove 1 from left side (score = 1*1 = 1)
        then remove 2, score = 1 + 2*2 = 5
        then remove 3, score = 5 + 3*3 = 14
        then remove 1, score = 14 + 1*4 = 18
        then remove 5, score = 18 + 5*5 = 43.

        Input :  arr[] = { 1, 2 }
        Output : 5.
         */
        /**
         * The idea is to use Dynamic Programming. Make a 2D matrix named dp[][]
         * initialised with 0, where dp[i][j] denote the maximum value of score from
         * index from index ito index j of the array. So, our final result will be
         * stored in dp[0][n-1]. 
         * 
         * Now, value for dp[i][j] will be maximum of arr[i] *
         * (number of element already removed + 1) + dp[i+ 1][j] or arr[j] * (number of
         * element already removed + 1) + dp[i][j – 1]. Below is the implementation of
         * this approach:
         */
        static final int MAX = 50;

        static int solve(int dp[][], int a[], int low, int high, int turn) {
            // If only one element left.
            if (low == high) {
                return a[low] * turn;
            }

            // If already calculated, return the value.
            if (dp[low][high] != 0) {
                return dp[low][high];
            }

            // Computing Maximum value when element at
            // index i and index j is to be chosed.
            dp[low][high] = Math.max(a[low] * turn + solve(dp, a, low + 1, high, turn + 1),
                    a[high] * turn + solve(dp, a, low, high - 1, turn + 1));

            return dp[low][high];
        }

        // Driven Program
        public static void main(String args[]) {
            int arr[] = { 1, 3, 1, 5, 2 };
            int n = arr.length;

            int dp[][] = new int[MAX][MAX];

            System.out.println(solve(dp, arr, 0, n - 1, 1));

        }
    }

    static class MinChangesToConvertArrayToStrictlyIncreasingIntegers {
        /** Given an array of n integers. Write a program to find minimum number of changes in array so that array is strictly increasing of integers. In strictly increasing array A[i] < A[i+1] for 0 <= i < n
        Examples: 
        

        Input : arr[] = { 1, 2, 6, 5, 4}
        Output : 2
        We can change a[2] to any value 
        between 2 and 5.
        and a[4] to any value greater then 5. 

        Input : arr[] = { 1, 2, 3, 5, 7, 11 }
        Output : 0
        Array is already strictly increasing.*/
        /**
         * The problem is variation of Longest Increasing Subsequence. The numbers which
         * are already a part of LIS need not to be changed. So minimum elements to
         * change is difference of size of array and number of elements in LIS. Note
         * that we also need to make sure that the numbers are integers. So while making
         * LIS, we do not consider those elements as part of LIS that cannot form
         * strictly increasing by inserting elements in middle. 
         * 
         * Example { 1, 2, 5, 3, 4
         * }, we consider length of LIS as three {1, 2, 5}, not as {1, 2, 3, 4} because
         * we cannot make a strictly increasing array of integers with this LIS.
         * 
         */
        // To find min elements to remove from array
        // to make it strictly increasing
        static int minRemove(int arr[], int n) {
            int LIS[] = new int[n];
            int len = 0;

            // Mark all elements of LIS as 1
            for (int i = 0; i < n; i++)
                LIS[i] = 1;

            // Find LIS of array
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[i] > arr[j] && (i - j) <= (arr[i] - arr[j]))
                        LIS[i] = Math.max(LIS[i], LIS[j] + 1);
                }
                len = Math.max(len, LIS[i]);
            }

            // Return min changes for array
            // to strictly increasing
            return n - len;
        }

        // Driver program to test minRemove()
        public static void main(String[] args) {
            int arr[] = { 1, 2, 6, 5, 4 };
            int n = arr.length;

            System.out.println(minRemove(arr, n));
        }
    }

    static class LongestAlternating_PositiveandNegative_Subarray_StartingAtEveryIndex {
        /**
         * A subarray is called alternating if any two consecutive numbers in it have opposite signs (i.e. one of them should be negative, whereas the other should be positive).
        Given an array of n integers. For each index i, we need to find the length if the longest alternating subarray starting at i. 
        Examples: 
        

        Input : a[] = {1, -5, 1, -5}
        Output : For index 0, {1, -5, 1, -5} = 4
                    index 1, {-5, 1, -5} = 3
                    index 2, {1, -5} = 2
                    index 3, {-5} = 1.
            
        Input :a[] = {-5, -1, -1, 2, -2, -3}
        Output : index 0 = 1,
                index 1 = 1,
                index 2 = 3,
                index 3 = 2,
                index 4 = 1,
                index 5 = 1,
         */
        /**
         * Efficient Approach: Observe that when a[i] and a[i+1] have opposite signs,
         * count[i] will be 1 more than count[i+1]. Otherwise when they have same sign
         * count[i] will be 1. We use Dynamic Programming here.
         */
        public static void longestAlternating(int arr[], int n) {
            int[] count = new int[n];

            // Fill count[] from end.
            count[n - 1] = 1;
            for (int i = n - 2; i >= 0; i--) {
                if (arr[i] * arr[i + 1] < 0)
                    count[i] = count[i + 1] + 1;
                else
                    count[i] = 1;
            }

            // Print result
            for (int i = 0; i < n; i++)
                System.out.print(count[i] + " ");
        }

        // driver program
        public static void main(String[] args) {
            int a[] = { -5, -1, -1, 2, -2, -3 };
            int n = 6;
            longestAlternating(a, n);
        }
    }

    static class WaysToSumToN_using_arrayElementsWithRepetitionAllowed {
        /**
         * Given a set of m distinct positive integers and a value ‘N’. The problem is to count the total number of ways we can form ‘N’ by doing sum of the array elements. Repetitions and different arrangements are allowed.
                Examples : 
                

                Input : arr = {1, 5, 6}, N = 7
                Output : 6

                Explanation:- The different ways are:
                1+1+1+1+1+1+1
                1+1+5
                1+5+1
                5+1+1
                1+6
                6+1

                Input : arr = {12, 3, 1, 9}, N = 14
                Output : 150
         */
        /**
         * Approach: The approach is based on the concept of dynamic programming. 

            countWays(arr, m, N)
                    Declare and initialize count[N + 1] = {0}
                    count[0] = 1
                    for i = 1 to N
                        for j = 0 to m - 1
                            if i >= arr[j]
                            count[i] += count[i - arr[j]]
                    return count[N] 

            Below is the implementation of above approach. 
         */
        static int arr[] = {1, 5, 6};
        // method to count the total number
        // of ways to sum up to 'N'
        static int countWays(int N) {
            int count[] = new int[N + 1];

            // base case
            count[0] = 1;

            // count ways for all values up
            // to 'N' and store the result
            for (int i = 1; i <= N; i++)
                for (int j = 0; j < arr.length; j++)

                    // if i >= arr[j] then
                    // accumulate count for value 'i' as
                    // ways to form value 'i-arr[j]'
                    if (i >= arr[j])
                        count[i] += count[i - arr[j]];

            // required number of ways
            return count[N];

        }

        // Driver code
        public static void main(String[] args) {
            int N = 7;
            System.out.println("Total number of ways = " + countWays(N));
        }

    }

    static class CountUniquePathsInMatrixWithObstacles {
        /**
         * Given a grid of size m * n, let us assume you are starting at (1, 1) and your goal is to reach (m, n). 
         * At any instance, if you are on (x, y), you can either go to (x, y + 1) or (x + 1, y).
            Now consider if some obstacles are added to the grids. How many unique paths would there be?
            An obstacle and empty space are marked as 1 and 0 respectively in the grid.

            Examples:  

            Input: [[0, 0, 0],
                    [0, 1, 0],
                    [0, 0, 0]]
            Output : 2
            There is only one obstacle in the middle.
         */
        /**
         We have discussed a problem to count the number of unique paths in a Grid when no obstacle was present in the grid. 
         But here the situation is quite different. While moving through the grid, we can get some obstacles which we can not jump and that way to reach the bottom right corner is blocked. 
        The most efficient solution to this problem can be achieved using dynamic programming. 
        Like every dynamic problem concept, we will not recompute the subproblems. A temporary 2D matrix will be constructed and value will be stored using the bottom up approach. 
        Approach

            Create a 2D matrix of same size of the given matrix to store the results.
            Traverse through the created array row wise and start filling the values in it.
            If an obstacle is found, set the value to 0.
            For the first row and column, set the value to 1 if obstacle is not found.
            Set the sum of the right and the upper values if obstacle is not present at that corresponding position in the given matrix
            Return the last value of the created 2d matrix
         */
        static int uniquePathsWithObstacles(int[][] A) {

            int r = 3, c = 3;

            // create a 2D-matrix and initializing
            // with value 0
            int[][] paths = new int[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    paths[i][j] = 0;
                }
            }

            // Initializing the left corner if
            // no obstacle there
            if (A[0][0] == 0)
                paths[0][0] = 1;

            // Initializing first column of
            // the 2D matrix
            for (int i = 1; i < r; i++) {
                // If not obstacle
                if (A[i][0] == 0)
                    paths[i][0] = paths[i - 1][0];
            }

            // Initializing first row of the 2D matrix
            for (int j = 1; j < c; j++) {

                // If not obstacle
                if (A[0][j] == 0)
                    paths[0][j] = paths[0][j - 1];
            }

            for (int i = 1; i < r; i++) {
                for (int j = 1; j < c; j++) {

                    // If current cell is not obstacle
                    if (A[i][j] == 0)
                        paths[i][j] = paths[i - 1][j] + paths[i][j - 1];
                }
            }

            // Returning the corner value
            // of the matrix
            return paths[r - 1][c-1];
        }

        // Driver code
        public static void main(String[] args) {
            int[][] A = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };

            System.out.print(uniquePathsWithObstacles(A));
        }
    }

    static class NumberOfNDigits_NonDecreasingOrIncreasingIntegers {
        /**
        Given an integer n > 0, which denotes the number of digits, the task to find the total number of n-digit positive integers which are non-decreasing in nature. 
        A non-decreasing integer is one in which all the digits from left to right are in non-decreasing form. ex: 1234, 1135, ..etc. 
        Note: Leading zeros also count in non-decreasing integers such as 0000, 0001, 0023, etc are also non-decreasing integers of 4-digits. 
        Examples : 
        

        Input : n = 1
        Output : 10
        Numbers are 0, 1, 2, ...9.

        Input : n = 2
        Output : 55

        Input : n = 4
        Output : 715
         */
        /**
         * Dynamic Programming: If we fill digits one by one from left to right, the
         * following conditions hold.
         * 
         * 
         * If current last digit is 9, we can fill only 9s in remaining places. So only
         * one solution is possible if current last digit is 9. If current last digit is
         * less than 9, then we can recursively compute count using following formula.
         * 
         * 
         * a[i][j] = a[i-1][j] + a[i][j + 1] For every digit j smaller than 9.
         * 
         * We consider previous length count and count to be increased by all greater
         * digits.
         * 
         * We build a matrix a[][] where a[i][j] = count of all valid i-digit
         * non-decreasing integers with j or greater than j as the leading digit. The
         * solution is based on below observations. We fill this matrix column-wise,
         * first calculating a[1][9] then using this value to compute a[2][8] and so on.
         * At any instant if we wish to calculate a[i][j] means number of i-digits
         * non-decreasing integers with leading digit as j or digit greater than j, we
         * should add up a[i-1][j] (number of i-1 digit integers which should start from
         * j or greater digit, because in this case if we place j as its left most digit
         * then our number will be i-digit non-decreasing number) and a[i][j+1] (number
         * of i-digit integers which should start with digit equals to greater than
         * j+1). So, a[i][j] = a[i-1][j] + a[i][j+1].
         */
        // Function that returns count of non- decreasing numbers
        // with n digits
        static int nonDecNums(int n) {
            // a[i][j] = count of all possible number
            // with i digits having leading digit as j
            int[][] a = new int[n + 1][10];

            // Initialization of all 0-digit number
            for (int i = 0; i <= 9; i++)
                a[0][i] = 1;

            // Initialization of all i-digit
            // non-decreasing number leading with 9
            for (int i = 1; i <= n; i++)
                a[i][9] = 1;

            // for all digits we should calculate
            // number of ways depending upon leading
            // digits
            for (int i = 1; i <= n; i++)
                for (int j = 8; j >= 0; j--)
                    a[i][j] = a[i - 1][j] + a[i][j + 1];

            return a[n][0];
        }

        // driver program
        public static void main(String[] args) {
            int n = 2;
            System.out.println("Non-decreasing digits = " + nonDecNums(n));
        }
    }

    static class WaysToArrangeNItemsOfTotalKDifferentColors {
        /**
         * 
         * We are given N items which are of total K different colors. Items of the same color are indistinguishable and colors can be numbered from 1 to K and count of items of each color is also given as k1, k2 and so on. Now we need to arrange these items one by one under a constraint that the last item of color i comes before the last item of color (i + 1) for all possible colors. Our goal is to find out how many ways this can be achieved.
        Examples: 
        

        Input : N = 3        
                k1 = 1    k2 = 2
        Output : 2
        Explanation :
        Possible ways to arrange are,
        k1, k2, k2
        k2, k1, k2 

        Input : N = 4        
                k1 = 2    k2 = 2
        Output : 3
        Explanation :
        Possible ways to arrange are,
        k1, k2, k1, k2
        k1, k1, k2, k2
        k2, k1, k1, k2 
         */
        /**
         * We can solve this problem using dynamic programming. Let dp[i] stores the
         * number of ways to arrange first i colored items. For one colored item answer
         * will be one because there is only one way. Now Let’s assume all items are in
         * a sequence. Now, to go from dp[i] to dp[i + 1], we need to put at least one
         * item of color (i + 1) at the very end, but the other items of color (i + 1)
         * can go anywhere in the sequence. The number of ways to arrange the item of
         * color (i + 1) is combination of (k1 + k2 .. + ki + k(i + 1) – 1) over (k(i +
         * 1) – 1) which can be represented as (k1 + k2 .. + ki + k(i + 1) – 1)C(k(i +
         * 1) – 1). In this expression we subtracted one because we need to put one item
         * at the very end. 
         * 
         * In below code, first we have calculated the combination
         * values, you can read more about that from here. After that we looped over all
         * different color and calculated the final value using above relation.
         */
        // method returns number of ways with which items
        // can be arranged
        static int waysToArrange(int N, int K, int[] k) {
            int[][] C = new int[N + 1][N + 1];
            int i, j;

            // Calculate value of Binomial Coefficient in
            // bottom up manner
            for (i = 0; i <= N; i++) {
                for (j = 0; j <= i; j++) {

                    // Base Cases
                    if (j == 0 || j == i) {
                        C[i][j] = 1;
                    }

                    // Calculate value using previously
                    // stored values
                    else {
                        C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]);
                    }
                }
            }

            // declare dp array to store result up to ith
            // colored item
            int[] dp = new int[K + 1];

            // variable to keep track of count of items
            // considered till now
            int count = 0;

            dp[0] = 1;

            // loop over all different colors
            for (i = 0; i < K; i++) {

                // populate next value using current value
                // and stated relation
                dp[i + 1] = (dp[i] * C[count + k[i] - 1][k[i] - 1]);
                count += k[i];
            }

            // return value stored at last index
            return dp[K];
        }

        // Driver code
        public static void main(String[] args) {
            int N = 4;
            int[] k = new int[] { 2, 2 };
            int K = k.length;
            System.out.println(waysToArrange(N, K, k));
        }
    }

    static class ProbabilityOfReachingPointWith_2_or_3_steps_atAtime{
        /**
         * A person starts walking from position X = 0, find the probability to reach exactly on X = N if she can only take either 2 steps or 3 steps. 
         * Probability for step length 2 is given i.e. P, probability for step length 3 is 1 – P.
        Examples : 
        

        Input : N = 5, P = 0.20
        Output : 0.32
        Explanation :-
        There are two ways to reach 5.
        2+3 with probability = 0.2 * 0.8 = 0.16
        3+2 with probability = 0.8 * 0.2 = 0.16
        So, total probability = 0.32.

        It is a simple dynamic programming problem. It is simple extension of this problem :- count-ofdifferent-ways-express-n-sum-1-3-4
Below is the implementation of the above approach.
         */
        // Returns probability to reach N
        static float find_prob(int N, float P) {
            double dp[] = new double[N + 1];
            dp[0] = 1;
            dp[1] = 0;
            dp[2] = P;
            dp[3] = 1 - P;

            for (int i = 4; i <= N; ++i)
                dp[i] = (P) * dp[i - 2] + (1 - P) * dp[i - 3];

            return ((float) (dp[N]));
        }

        // Driver code
        public static void main(String args[]) {
            int n = 5;
            float p = 0.2f;
            System.out.printf("%.2f", find_prob(n, p));
        }
    }

    static class NumberOfDecimalNumbersOfLengthK_thatAreStrictMonotone {
        /**
         * We call decimal number a monotone if:D[i] \leq D[i+1], 0 \leq i \leq |D|
         * 
         * Write a program which takes positive number n on input and returns number of
         * decimal numbers of length n that are strict monotone. Number can’t start with
         * 0.
         * 
         * Examples :
         * 
         * Input : 2 Output : 36 Numbers are 12, 13, ... 19, 23 24, ... 29, .... 89.
         * 
         * Input : 3 Output : 84
         * 
         * Explanations of this problem follows the same rules as applied on: Number of
         * decimal numbers of length k, that are monotone
         * 
         * 
         * The only difference is that now we cannot take duplicates, so previously
         * computed values are the one on the left and left top diagonal.
         */
        static int DP_s = 9;

        static int getNumStrictMonotone(int len) {
            // DP[i][j] is going to store monotone
            // numbers of length i+1 considering
            // j+1 digits (1, 2, 3, ..9)
            int[][] DP = new int[len][DP_s];

            // Unit length numbers
            for (int i = 0; i < DP_s; ++i)
                DP[0][i] = i + 1;

            // Building dp[] in bottom up
            for (int i = 1; i < len; ++i)
                for (int j = 1; j < DP_s; ++j)
                    DP[i][j] = DP[i - 1][j - 1] + DP[i][j - 1];

            return DP[len - 1][DP_s - 1];
        }

        public static void main(String[] args) {
            int n = 2;
            System.out.println(getNumStrictMonotone(n));
        }
    }

    static class WaysToSumToN_usingNumbers_GreaterThanOrEqualTo_M {
        /**
         * Given two natural number n and m. The task is to find the number of ways in
         * which the numbers that are greater than or equal to m can be added to get the
         * sum n. Examples:
         * 
         * 
         * Input : n = 3, m = 1 Output : 3 Following are three different ways to get sum
         * n such that each term is greater than or equal to m 1 + 1 + 1, 1 + 2, 3
         * 
         * Input : n = 2, m = 1 Output : 2 Two ways are 1 + 1 and 2
         */
        /**
         * The idea is to use Dynamic Programming by define 2D matrix, say dp[][]. dp[i][j] define the number of ways to get sum i using the numbers greater than or equal to j. 
         * So dp[i][j] can be defined as:
 

            If i < j, dp[i][j] = 0, because we cannot achieve smaller sum of i using numbers greater than or equal to j.
            If i = j, dp[i][j] = 1, because there is only one way to show sum i using number i which is equal to j.
            Else dp[i][j] = dp[i][j+1] + dp[i-j][j], because obtaining a sum i using numbers greater than or equal to j is equal to the sum of 
            obtaining a sum of i using numbers greater than or equal to j+1 and obtaining the sum of i-j using numbers greater than or equal to j.

        Below is the implementation of this approach: 
         */
   
        // Return number of ways to which numbers
        // that are greater than given number can
        // be added to get sum.
        static int numberofways(int n, int m) {
            int dp[][] = new int[n + 2][n + 2];

            dp[0][n + 1] = 1;

            // Filling the table. k is for numbers
            // greater than or equal that are allowed.
            for (int k = n; k >= m; k--) {

                // i is for sum
                for (int i = 0; i <= n; i++) {

                    // initializing dp[i][k] to number
                    // ways to get sum using numbers
                    // greater than or equal k+1
                    dp[i][k] = dp[i][k + 1];

                    // if i > k
                    if (i - k >= 0)
                        dp[i][k] = (dp[i][k] + dp[i - k][k]);
                }
            }

            return dp[n][m];
        }

        // Driver Program
        public static void main(String args[]) {
            int n = 3, m = 1;
            System.out.println(numberofways(n, m));
        }
    }
}
