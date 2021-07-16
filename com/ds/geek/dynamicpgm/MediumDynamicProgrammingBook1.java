package com.ds.geek.dynamicpgm;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MediumDynamicProgrammingBook1 {
    
    static class PrintItemsIn0_1_Knapsack {
        /** 
         * 
         * Given weights and values of n items, put these items in a knapsack of capacity W to get the maximum total value in the knapsack. In other words, given two integer arrays, val[0..n-1] and wt[0..n-1] represent values and weights associated with n items respectively. Also given an integer W which represents knapsack capacity, find out the items such that sum of the weights of those items of a given subset is smaller than or equal to W. You cannot break an item, either pick the complete item or don’t pick it (0-1 property).
            Prerequisite : 0/1 Knapsack
            Examples : 
            

            Input : val[] = {60, 100, 120};
                    wt[] = {10, 20, 30};
                    W = 50;
            Output : 220 //maximum value that can be obtained
                    30 20 //weights 20 and 30 are included. 

            Input : val[] = {40, 100, 50, 60};
                    wt[] = {20, 10, 40, 30};
                    W = 60;
            Output : 200
                    30 20 10

            Approach : 
            Let val[] = {1, 4, 5, 7}, wt[] = {1, 3, 4, 5} 
            W = 7. 
            The 2d knapsack table will look like : 
            

            2d knapsack table

            Start backtracking from K[n][W].Here K[n][W] is 9.
            Since this value comes from the top (shown by grey arrow), the item in this row is not included. Go vertically upward in the table without including this in the knapsack. Now, this value K[n-1][W] which is 9 doesn’t come from the top which means the item in this row is included and go vertically up and then left by the weight of the included item ( shown by black arrow). Continuing this process include weights 3 and 4 with a total value 9 in the knapsack. 
        */

        // A utility function that returns
        // maximum of two integers
        static int max(int a, int b) {
            return (a > b) ? a : b;
        }

        // Prints the items which are put
        // in a knapsack of capacity W
        static void printknapSack(int W, int wt[], int val[], int n) {
            int i, w;
            int K[][] = new int[n + 1][W + 1];

            // Build table K[][] in bottom up manner
            for (i = 0; i <= n; i++) {
                for (w = 0; w <= W; w++) {
                    if (i == 0 || w == 0)
                        K[i][w] = 0;
                    else if (wt[i - 1] <= w)
                        K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
                    else
                        K[i][w] = K[i - 1][w];
                }
            }

            // stores the result of Knapsack
            int res = K[n][W];
            System.out.println(res);

            w = W;
            for (i = n; i > 0 && res > 0; i--) {

                // either the result comes from the top
                // (K[i-1][w]) or from (val[i-1] + K[i-1]
                // [w-wt[i-1]]) as in Knapsack table. If
                // it comes from the latter one/ it means
                // the item is included.
                if (res == K[i - 1][w])
                    continue;
                else {

                    // This item is included.
                    System.out.print(wt[i - 1] + " ");

                    // Since this weight is included its
                    // value is deducted
                    res = res - val[i - 1];
                    w = w - wt[i - 1];
                }
            }
        }

        // Driver code
        public static void main(String arg[]) {
            int val[] = { 60, 100, 120 };
            int wt[] = { 10, 20, 30 };
            int W = 50;
            int n = val.length;

            printknapSack(W, wt, val, n);
        }
    }

    static class UnboundedKnapsack_RepetitionOfItems_Allowed {
        /**
         * Given a knapsack weight W and a set of n items with certain value vali and weight wti, we need to calculate the maximum amount that could make up this quantity exactly. This is different from classical Knapsack problem, here we are allowed to use unlimited number of instances of an item.
        Examples: 

        Input : W = 100
            val[]  = {1, 30}
            wt[] = {1, 50}
        Output : 100
        There are many ways to fill knapsack.
        1) 2 instances of 50 unit weight item.
        2) 100 instances of 1 unit weight item.
        3) 1 instance of 50 unit weight item and 50
        instances of 1 unit weight items.
        We get maximum value with option 2.

        Input : W = 8
            val[] = {10, 40, 50, 70}
            wt[]  = {1, 3, 4, 5}       
        Output : 110 
        We get maximum value with one unit of
        weight 5 and one unit of weight 3.
         */
        /**
         * Its an unbounded knapsack problem as we can use 1 or more instances of any resource. A simple 1D array, say dp[W+1] can be used such that dp[i] stores the maximum value which can achieved using all items and i capacity of knapsack. Note that we use 1D array here which is different from classical knapsack where we used 2D array. Here number of items never changes. We always have all items available.
        We can recursively compute dp[] using below formula

        dp[i] = 0
        dp[i] = max(dp[i], dp[i-wt[j]] + val[j] 
                        where j varies from 0 
                        to n-1 such that:
                        wt[j] <= i

        result = d[W]
         */
   
        private static int max(int i, int j) {
            return (i > j) ? i : j;
        }

        // Returns the maximum value with knapsack
        // of W capacity
        private static int unboundedKnapsack(int W, int n, int[] val, int[] wt) {

            // dp[i] is going to store maximum value
            // with knapsack capacity i.
            int dp[] = new int[W + 1];

            // Fill dp[] using above recursive formula
            for (int i = 0; i <= W; i++) {
                for (int j = 0; j < n; j++) {
                    if (wt[j] <= i) {
                        dp[i] = max(dp[i], dp[i - wt[j]] + val[j]);
                    }
                }
            }
            return dp[W];
        }

        // Driver program
        public static void main(String[] args) {
            int W = 100;
            int val[] = { 10, 30, 20 };
            int wt[] = { 5, 10, 15 };
            int n = val.length;
            System.out.println(unboundedKnapsack(W, n, val, wt));
        }
    }

    static class TempleOfferings {
        /**
         * Consider a devotee wishing to give offerings to temples along with a mountain range. 
         * The temples are located in a row at different heights. Each temple should receive at least one offer. 
         * If two adjacent temples are at different altitudes, then the temple that is higher up should receive more offerings than the one that is lower down. 
         * If two adjacent temples are at the same height, then their offerings relative to each other do not matter. 
         * Given the number of temples and the heights of the temples in order, find the minimum number of offerings to bring.

            Examples:

            Input  : 3
                    1 2 2
            Output : 4
            All temples must receive at-least one offering.
            Now, the second temple is at a higher altitude
            compared to the first one. Thus it receives one
            extra offering. 
            The second temple and third temple are at the 
            same height, so we do not need to modify the 
            offerings. Offerings given are therefore: 1, 2,
            1 giving a total of 4.

            Input  : 6
                    1 4 3 6 2 1
            Output : 10
            We can distribute the offerings in the following
            way, 1, 2, 1, 3, 2, 1. The second temple has to 
            receive more offerings than the first due to its 
            height being higher. The fourth must receive more
            than the fifth, which in turn must receive more 
            than the sixth. Thus the total becomes 10.
         */
        /**
         * We notice that each temple can either be above, below or at the same level as
         * the temple next to it. The offerings required at each temple are equal to the
         * maximum length of the chain of temples at a lower height as shown in the
         * image.
         * 
         * Temples
         * 
         * Naive Approach To follow the given rule, a temple must be offered at least
         * x+1 where x is the maximum of the following two.
         */
        /**
         * Number of temples on left in increasing order. Number of temples on right in
         * increasing order.
         * 
         * A naive method of solving this problem would be for each temple, go to the
         * left until altitude increases, and do the same for the right.
         * 
         * By using Dynamic Programming, we can improve the time complexity. In this
         * method, we create a structure of length n which maintains the maximum
         * decreasing chain to the left of each temple and the maximum decreasing chain
         * to the right of each temple. We go through once from 0 to N setting the value
         * of left for each temple. We then go from N to 0 setting the value of right
         * for each temple. We then compare the two and pick the maximum for each
         * temple.
         */
        // To store count of increasing order temples
        // on left and right (including current temple)
        public static class Temple {
            public int L;
            public int R;
        };

        // Returns count of minimum offerings for
        // n temples of given heights.
        static int offeringNumber(int n, int[] templeHeight) {

            // Initialize counts for all temples
            Temple[] chainSize = new Temple[n];

            for (int i = 0; i < n; ++i) {
                chainSize[i] = new Temple();
                chainSize[i].L = -1;
                chainSize[i].R = -1;
            }

            // Values corner temples
            chainSize[0].L = 1;
            chainSize[n - 1].R = 1;

            // Filling left and right values
            // using same values of
            // previous(or next)
            for (int i = 1; i < n; ++i) {
                if (templeHeight[i - 1] < templeHeight[i])
                    chainSize[i].L = chainSize[i - 1].L + 1;
                else
                    chainSize[i].L = 1;
            }

            for (int i = n - 2; i >= 0; --i) {
                if (templeHeight[i + 1] < templeHeight[i])
                    chainSize[i].R = chainSize[i + 1].R + 1;
                else
                    chainSize[i].R = 1;
            }

            // Computing max of left and right for all
            // temples and returing sum.
            int sum = 0;
            for (int i = 0; i < n; ++i)
                sum += Math.max(chainSize[i].L, chainSize[i].R);

            return sum;
        }

        // Driver code
        public static void main(String[] s) {
            int[] arr1 = { 1, 2, 2 };
            System.out.println(offeringNumber(3, arr1));

            int[] arr2 = { 1, 4, 3, 6, 2, 1 };
            System.out.println(offeringNumber(6, arr2));
        }

    }

    static class DiceThrow_NumberOfWaysToGetSumGivenNDicesWithMFaces {
        /** Given n dice each with m faces, numbered from 1 to m, find the number of ways to get sum X. X is the summation of values on each face when all the dice are thrown.*/
        /**
         * 
         * The Naive approach is to find all the possible combinations of values from n dice and keep on counting the results that sum to X.

            This problem can be efficiently solved using Dynamic Programming (DP).  

            Let the function to find X from n dice is: Sum(m, n, X)
            The function can be represented as:
            Sum(m, n, X) = Finding Sum (X - 1) from (n - 1) dice plus 1 from nth dice
                        + Finding Sum (X - 2) from (n - 1) dice plus 2 from nth dice
                        + Finding Sum (X - 3) from (n - 1) dice plus 3 from nth dice
                            ...................................................
                            ...................................................
                            ...................................................
                        + Finding Sum (X - m) from (n - 1) dice plus m from nth dice

            So we can recursively write Sum(m, n, x) as following
            Sum(m, n, X) = Sum(m, n - 1, X - 1) + 
                        Sum(m, n - 1, X - 2) +
                        .................... + 
                        Sum(m, n - 1, X - m)

            Why DP approach? 
            The above problem exhibits overlapping subproblems. See the below diagram. Also, see this recursive implementation. 
            Let there be 3 dice, each with 6 faces and we need to find the number of ways to get sum 8

            Sum(6, 3, 8) = Sum(6, 2, 7) + Sum(6, 2, 6) + Sum(6, 2, 5) + 
               Sum(6, 2, 4) + Sum(6, 2, 3) + Sum(6, 2, 2)

            To evaluate Sum(6, 3, 8), we need to evaluate Sum(6, 2, 7) which can 
            recursively written as following:
            Sum(6, 2, 7) = Sum(6, 1, 6) + Sum(6, 1, 5) + Sum(6, 1, 4) + 
                        Sum(6, 1, 3) + Sum(6, 1, 2) + Sum(6, 1, 1)

            We also need to evaluate Sum(6, 2, 6) which can recursively written
            as following:
            Sum(6, 2, 6) = Sum(6, 1, 5) + Sum(6, 1, 4) + Sum(6, 1, 3) +
                        Sum(6, 1, 2) + Sum(6, 1, 1)
            ..............................................
            ..............................................
            Sum(6, 2, 2) = Sum(6, 1, 1)

            Please take a closer look at the above recursion. The sub-problems in RED are solved first time and sub-problems in BLUE are solved again (exhibit overlapping sub-problems). Hence, storing the results of the solved sub-problems saves time.

            Following is implementation of Dynamic Programming approach.  
         */

         public static long findWays(int f, int d, int s) {
             // Create a table to store results of subproblems. One extra
             // row and column are used for simpilicity (Number of dice
             // is directly used as row index and sum is directly used
             // as column index). The entries in 0th row and 0th column
             // are never used.
             long mem[][] = new long[d + 1][s + 1];
             // Table entries for no dices
             // If you do not have any data, then the value must be 0, so the result is 1
             mem[0][0] = 1;
             // Iterate over dices
             for (int i = 1; i <= d; i++) {
                 // Iterate over sum
                 for (int j = i; j <= s; j++) {
                     // The result is obtained in two ways, pin the current dice and spending 1 of
                     // the value,
                     // so we have mem[i-1][j-1] remaining combinations, to find the remaining
                     // combinations we
                     // would have to pin the values ??above 1 then we use mem[i][j-1] to sum all
                     // combinations
                     // that pin the remaining j-1's. But there is a way, when "j-f-1> = 0" we would
                     // be adding
                     // extra combinations, so we remove the combinations that only pin the
                     // extrapolated dice face and
                     // subtract the extrapolated combinations.
                     mem[i][j] = mem[i][j - 1] + mem[i - 1][j - 1];
                     if (j - f - 1 >= 0)
                         mem[i][j] -= mem[i - 1][j - f - 1];
                 }
             }
             return mem[d][s];
         }

         /**
          * Main
          *
          * @param args
          */
         public static void main(String[] args) {
             System.out.println(findWays(4, 2, 1));
             System.out.println(findWays(2, 2, 3));
             System.out.println(findWays(6, 3, 8));
             System.out.println(findWays(4, 2, 5));
             System.out.println(findWays(4, 3, 5));
         }
    }

    static class FindIfInputStringCanBeSegmentedIntoSpaceSeparatedSequenceOfDictWords_WorkBreak {
        /**
         * 
         * Given an input string and a dictionary of words, find out if the input string
         * can be segmented into a space-separated sequence of dictionary words. See
         * following examples for more details. This is a famous Google interview
         * question, also being asked by many other companies now a days.
         * 
         * Consider the following dictionary { i, like, sam, sung, samsung, mobile, ice,
         * cream, icecream, man, go, mango}
         * 
         * Input: ilike Output: Yes The string can be segmented as "i like".
         * 
         * Input: ilikesamsung Output: Yes The string can be segmented as "i like
         * samsung" or "i like sam sung".
         */
        /**
         * Why Dynamic Programming? The above problem exhibits overlapping sub-problems. For example, see the following partial recursion tree for string “abcde” in worst case.
         */
        // set to hold dictionary values
        private static Set<String> dictionary = new HashSet<>();

        public static void recursiveSolution(String[] args) {

            // array of strings to be added in dictionary set.
            String temp_dictionary[] = { "mobile", "samsung", "sam", "sung", "man", "mango", "icecream", "and", "go",
                    "i", "like", "ice", "cream" };

            // loop to add all strings in dictionary set
            for (String temp : temp_dictionary) {
                dictionary.add(temp);
            }

            // sample input cases
            System.out.println(wordBreak("ilikesamsung"));
            System.out.println(wordBreak("iiiiiiii"));
            System.out.println(wordBreak(""));
            System.out.println(wordBreak("ilikelikeimangoiii"));
            System.out.println(wordBreak("samsungandmango"));
            System.out.println(wordBreak("samsungandmangok"));

        }

        // returns true if the word can be segmented into parts such
        // that each part is contained in dictionary
        public static boolean wordBreak(String word) {
            int size = word.length();

            // base case
            if (size == 0)
                return true;

            // else check for all words
            for (int i = 1; i <= size; i++) {
                // Now we will first divide the word into two parts ,
                // the prefix will have a length of i and check if it is
                // present in dictionary ,if yes then we will check for
                // suffix of length size-i recursively. if both prefix and
                // suffix are present the word is found in dictionary.

                if (dictionary.contains(word.substring(0, i)) && wordBreak(word.substring(i, size)))
                    return true;
            }

            // if all cases failed then return false
            return false;
        }

        /*
         * A utility function to check whether a word is present in dictionary or not.
         * An array of strings is used for dictionary. Using array of strings for
         * dictionary is definitely not a good idea. We have used for simplicity of the
         * program
         */
        static boolean dictionaryContains(String word) {
            String dictionary[] = { "mobile", "samsung", "sam", "sung", "man", "mango", "icecream", "and", "go", "i",
                    "like", "ice", "cream" };
            int size = dictionary.length;
            for (int i = 0; i < size; i++)
                if (dictionary[i].equals(word))
                    return true;
            return false;
        }

        // Returns true if string can be segmented into space separated
        // words, otherwise returns false
        static boolean wordBreakDP(String str) {

            /**
             * [length of s][size of dict][avg length of words in dict]
             * for(int i = 1; i <= s.length(); i++){
            for(String str: dict){
                if(str.length() <= i){
                    if(f[i - str.length()]){
                        if(s.substring(i-str.length(), i).equals(str)){
                            f[i] = true;
                            break;
                        }
                    }
                }
            }
        }
                     *  //Second DP
                for(int i=1; i <= s.length(); i++){
                    for(int j=0; j < i; j++){
                        if(f[j] && dict.contains(s.substring(j, i))){
                            f[i] = true;
                            break;
                        }
                    }
                }
             */
            int size = str.length();
            if (size == 0)
                return true;

            // Create the DP table to store results of subroblems. The value wb[i]
            // will be true if str[0..i-1] can be segmented into dictionary words,
            // otherwise false.
            boolean wb[] = new boolean[size + 1];

            for (int len = 1; len <= size; len++) {
                // if wb[i] is false, then check if current prefix can make it true.
                // Current prefix is "str.substr(0, i)"
                if (wb[len] == false && dictionaryContains(str.substring(0, len)))
                // this makes sure that we find the first sub string which exists in the dictionary.
                    wb[len] = true;

                // wb[i] is true, then check for all substrings starting from
                // (i+1)th character and store their results.
                if (wb[len] == true) {
                    // If we reached the last prefix
                    if (len == size)
                        return true;

                    for (int j = len + 1; j <= size; j++) {
                        // Update wb[j] if it is false and can be updated
                        // Note the parameter passed to dictionaryContains() is
                        // substring starting from index 'i' and length 'j-i'
                        System.out.println("I is "+len+ " J is "+ j +" j-i is "+(j-len));
                        if (wb[j] == false && dictionaryContains(str.substring(len, j - len)))
                            wb[j] = true;

                        // If we reached the last character
                        if (j == size && wb[j] == true)
                            return true;
                    }
                }
            }

            
            for (int i = 1; i <= size;i++) System.out.print(" "+ wb[i]);

            // If we have tried all prefixes and none of them worked
            return false;
        }

        // Driver program to test above functions
        public static void main(String[] args) {
            System.out.println(wordBreakDP("ilikesamsung"));
            // System.out.println(wordBreakDP("iiiiiiii"));
            // System.out.println(wordBreak(""));
            // System.out.println(wordBreak("ilikelikeimangoiii"));
            // System.out.println(wordBreak("samsungandmango"));
            // System.out.println(wordBreak("samsungandmangok"));
        }
    }

    static class TileStackingProblem {
      /**
       * A stable tower of height n is a tower consisting of exactly n tiles of unit height stacked vertically in such a way, that no bigger tile is placed on a smaller tile. An example is shown below :  */  
      /**
       * We have an infinite number of tiles of sizes 1, 2, …, m. The task is to calculate the number of the different stable towers of height n that can be built from these tiles, with a restriction that you can use at most k tiles of each size in the tower.
        Note: Two towers of height n are different if and only if there exists a height h (1 <= h <= n), such that the towers have tiles of different sizes at height h.
        Examples: 
        

        Input : n = 3, m = 3, k = 1.
        Output : 1
        Possible sequences: { 1, 2, 3}. 
        Hence answer is 1.

        Input : n = 3, m = 3, k = 2.
        Output : 7
        {1, 1, 2}, {1, 1, 3}, {1, 2, 2},
        {1, 2, 3}, {1, 3, 3}, {2, 2, 3}, 
        {2, 3, 3}.
       */
      /**
       * We basically need to count a number of decreasing sequences of length n using numbers from 1 to m where every number can be used at most k times. We can recursively compute count for n using count for n-1. 
            The idea is to use Dynamic Programming. Declare a 2D array dp[][], where each state dp[i][j] denotes the number of decreasing sequences of length i using numbers from j to m. 
            We need to take care of the fact that a number can be used most of k time. 
            This can be done by considering 1 to k occurrences of a number. Hence, our recurrence relation becomes: 
            {DP[i][j] = sum {x=0}^{k}[i-x][j-1]}
            Also, we can use the fact that for a fixed j we are using the consecutive values of previous k values of i. Hence, we can maintain a prefix sum array for each state. Now we have got rid of the k factor for each state.
            Below is the implementation of this approach: 
       */
      static int N = 100;

      static int possibleWays(int n, int m, int k) {
          int[][] dp = new int[N][N];
          int[][] presum = new int[N][N];

          for (int i = 0; i < N; i++) {
              for (int j = 0; j < N; j++) {
                  dp[i][j] = 0;
                  presum[i][j] = 0;
              }
          }

          // Initialing 0th row to 0.
          for (int i = 1; i < n + 1; i++) {
              dp[0][i] = 0;
              presum[0][i] = 1;
          }

          // Initialing 0th column to 0.
          for (int i = 0; i < m + 1; i++) {
              presum[i][0] = dp[i][0] = 1;
          }

          // For each row from 1 to m
          for (int i = 1; i < m + 1; i++) {

              // For each column from 1 to n.
              for (int j = 1; j < n + 1; j++) {

                  // Initialing dp[i][j] to presum of (i - 1, j).
                  dp[i][j] = presum[i - 1][j];
                  if (j > k) {
                      dp[i][j] -= presum[i - 1][j - k - 1];
                  }
              }

              // Calculating presum for each i, 1 <= i <= n.
              for (int j = 1; j < n + 1; j++) {
                  presum[i][j] = dp[i][j] + presum[i][j - 1];
              }
          }

          return dp[m][n];
      }

      // Driver Program
      public static void main(String[] args) {
          int n = 3, m = 3, k = 2;
          System.out.println(possibleWays(n, m, k));
      }
    }

    static class HighwayBillboardProblem {
        /**
         * Consider a highway of M miles. The task is to place billboards on the highway
         * such that revenue is maximized. The possible sites for billboards are given
         * by number x1 < x2 < ….. < xn-1 < xn, specifying positions in miles measured
         * from one end of the road. If we place a billboard at position xi, we receive
         * a revenue of ri > 0. There is a restriction that no two billboards can be
         * placed within t miles or less than it. 
         * 
         * Note : All possible sites from x1 to
         * xn are in range from 0 to M as need to place billboards on a highway of M
         * miles.
         */
        /**
         * Input : M = 20
        x[]       = {6, 7, 12, 13, 14}
        revenue[] = {5, 6, 5,  3,  1}
        t = 5
        Output: 10
        By placing two billboards at 6 miles and 12
        miles will produce the maximum revenue of 10.

        Input : M = 15
                x[] = {6, 9, 12, 14}
                revenue[] = {5, 6, 3, 7}
                t = 2
        Output : 18  
         */
        /**
         * Let maxRev[i], 1 <= i <= M, be the maximum revenue generated from beginning
         * to i miles on the highway. Now for each mile on the highway, we need to check
         * whether this mile has the option for any billboard, if not then the maximum
         * revenue generated till that mile would be same as maximum revenue generated
         * till one mile before. But if that mile has the option for billboard then we
         * have 2 options: 
         * 1. Either we will place the billboard, ignore the billboard
         * in previous t miles, and add the revenue of the billboard placed. 
         * 
         * 2. Ignore
         * this billboard. So maxRev[i] = max(maxRev[i-t-1] + revenue[i], maxRev[i-1])
         * Below is implementation of this approach:
         */
        static int maxRevenue(int m, int[] x, int[] revenue, int n, int t) {

            // Array to store maximum revenue
            // at each miles.
            int[] maxRev = new int[m + 1];
            for (int i = 0; i < m + 1; i++)
                maxRev[i] = 0;

            // actual minimum distance between
            // 2 billboards.
            int nxtbb = 0;
            for (int i = 1; i <= m; i++) {
                // check if all billboards are
                // already placed.
                if (nxtbb < n) {
                    // check if we have billboard for
                    // that particular mile. If not,
                    // copy the previous maximum revenue.
                    if (x[nxtbb] != i) {
                        System.out.println("i is " + i + " maxRev[i] becomes " + maxRev[i - 1]);
                        maxRev[i] = maxRev[i - 1];
                    }

                    // we do have billboard for this mile.
                    else {
                        // We have 2 options, we either take
                        // current or we ignore current billboard.

                        // If current position is less than or
                        // equal to t, then we can have only
                        // one billboard.
                        if (i <= t)
                            maxRev[i] = Math.max(maxRev[i - 1], revenue[nxtbb]);

                        // Else we may have to remove
                        // previously placed billboard
                        else {
                            // if (i>=7) {
                            System.out.println("I is " + i + " maxRev [i-t-1] " + maxRev[i - t - 1] + " maxRev[i-1] "
                                    + maxRev[i - 1]);
                            // }
                            maxRev[i] = Math.max(maxRev[i - t - 1] + revenue[nxtbb], maxRev[i - 1]); // i-t-1 removes
                                                                                                     // the previously
                                                                                                     // placed
                                                                                                     // billboard, else
                                                                                                     // i-1 considers
                                                                                                     // the previous
                                                                                                     // billboard.

                        }

                        nxtbb++;
                    }
                } else
                    maxRev[i] = maxRev[i - 1];
            }

            return maxRev[m];
        }

        // Driver Code
        public static void main(String[] args) {
            int m = 20;
            int[] x = new int[] { 6, 7, 12, 13, 14 };
            int[] revenue = new int[] { 5, 6, 5, 3, 1 };
            int n = x.length;
            int t = 5;
            System.out.println(maxRevenue(m, x, revenue, n, t));
        }
    }

    static class LargestIndependentSetProblem {
        /**
         * Given a Binary Tree, find size of the Largest Independent Set(LIS) in it. A subset of all tree nodes is an independent set if there is no edge between any two nodes of the subset. 

        For example, consider the following binary tree. The largest independent set(LIS) is {10, 40, 60, 70, 80} and size of the LIS is 5.
         */
        /**
         * A Dynamic Programming solution solves a given problem using solutions of subproblems in bottom up manner. Can the given problem be solved using solutions to subproblems? If yes, then what are the subproblems? Can we find largest independent set size (LISS) for a node X if we know LISS for all descendants of X? If a node is considered as part of LIS, then its children cannot be part of LIS, but its grandchildren can be. Following is optimal substructure property.

        1) Optimal Substructure: 
        Let LISS(X) indicates size of largest independent set of a tree with root X. 

            LISS(X) = MAX { (1 + sum of LISS for all grandchildren of X),
                            (sum of LISS for all children of X) }

        The idea is simple, there are two possibilities for every node X, either X is a member of the set or not a member. If X is a member, then the value of LISS(X) is 1 plus LISS of all grandchildren. If X is not a member, then the value is sum of LISS of all children.

        2) Overlapping Subproblems 
        Following is recursive implementation that simply follows the recursive structure mentioned above. 
         */
        // A utility function to find
        // max of two integers
        static int max(int x, int y) {
            return (x > y) ? x : y;
        }

        /*
         * A binary tree node has data, pointer to left child and a pointer to right
         * child
         */
        static class Node {
            int data;
            Node left, right;
        };

        // The function returns size of the
        // largest independent set in a given
        // binary tree
        static int LISS(Node root) {
            if (root == null)
                return 0;

            // Calculate size excluding the current node
            int size_excl = LISS(root.left) + LISS(root.right);

            // Calculate size including the current node
            int size_incl = 1;
            if (root.left != null)
                size_incl += LISS(root.left.left) + LISS(root.left.right);
            if (root.right != null)
                size_incl += LISS(root.right.left) + LISS(root.right.right);

            // Return the maximum of two sizes
            return max(size_incl, size_excl);
        }

        // A utility function to create a node
        static Node newNode(int data) {
            Node temp = new Node();
            temp.data = data;
            temp.left = temp.right = null;
            return temp;
        }

        // Driver Code
        public static void main(String args[]) {
            // Let us construct the tree
            // given in the above diagram
            Node root = newNode(20);
            root.left = newNode(8);
            root.left.left = newNode(4);
            root.left.right = newNode(12);
            root.left.right.left = newNode(10);
            root.left.right.right = newNode(14);
            root.right = newNode(22);
            root.right.right = newNode(25);

            System.out.println("Size of the Largest" + " Independent Set is " + LISS(root));
        }
    }

    static class FindMaxAmountOfTasksPerformInN_days_whenTasksAreHighAndLowEffort_andUnderConstraint {
        /**
         * You are given n days and for each day (di) you could either perform a high effort tasks (hi) or a low effort tasks (li) or no task with the constraint that you can choose a high-effort tasks only if you chose no task on the previous day. Write a program to find the maximum amount of tasks you can perform within these n days.
            Examples: 
            

            No. of days (n) = 5
            Day      L.E.   H.E
            1        1       3
            2        5       6
            3        4       8
            4        5       7
            5        3       6
            Maximum amount of tasks 
                    = 3 + 5 + 4 + 5 + 3 
                    = 20
         */
        /**
         * Optimal Substructure 
            To find the maximum amount of tasks done till i’th day, we need to compare 2 choices: 
            

                Go for high effort tasks on that day, then find the maximum amount of tasks done till (i – 2) th day.
                Go for low effort task on that day and find the maximum amount of tasks done till (i – 1) th day.

            Let high [1…n] be the input array for high effort task amount on i’th day and low [1…n] be the input array for low effort task amount on ith day. 
            Let max_task (high [], low [], i) be the function that returns maximum amount of task done till ith day, so it will return max(high[i] + max_task(high, low, (i – 2)), low [i] + max_task (high, low, (i – 1)))
            Therefore, the problem has optimal substructure property as the problem can be solved using solutions to subproblems.
            Overlapping Subproblems 
            Following is a simple recursive implementation of the High-effort vs. Low-effort task problem. The implementation simply follows the recursive structure mentioned above. So, High-effort vs. Low-effort Task Problem has both properties of a dynamic programming problem. 
         */
        // Returns the maximum among the 2 numbers
        static int max(int x, int y)
        {
            return (x > y ? x : y);
        }
        
        // Returns maximum amount of task that can be
        // done till day n
        static int maxTasks(int []high, int []low, int n)
        {
            // An array task_dp that stores the maximum
            // task done
            int[] task_dp = new int[n + 1];
        
            // If n = 0, no solution exists
            task_dp[0] = 0;
        
            // If n = 1, high effort task on that day will
            // be the solution
            task_dp[1] = high[0];
        
            // Fill the entire array determining which
            // task to choose on day i
            for (int i = 2; i <= n; i++)
                task_dp[i] = Math.max(high[i - 1] + task_dp[i - 2],
                                low[i - 1] + task_dp[i - 1]);
            return task_dp[n];
        }
        
        // Driver code
        public static void main(String[] args)
        {
            int n = 5;
            int []high = {3, 6, 8, 7, 6};
            int []low = {1, 5, 4, 5, 3};
            System.out.println(maxTasks(high, low, n));
        }
    }

    static class PrintLongestBitonicSubsequence {
        /**
         * TheThe Longest Bitonic Subsequence problem is to find the longest subsequence
         * of a given sequence such that it is first increasing and then decreasing. A
         * sequence, sorted in increasing order is considered Bitonic with the
         * decreasing part as empty. Similarly, decreasing order sequence is considered
         * Bitonic with the increasing part as empty.
         * 
         * Examples:
         * 
         * Input: [1, 11, 2, 10, 4, 5, 2, 1] Output: [1, 2, 10, 4, 2, 1] OR [1, 11, 10,
         * 5, 2, 1] OR [1, 2, 4, 5, 2, 1]
         * 
         * Input: [12, 11, 40, 5, 3, 1] Output: [12, 11, 5, 3, 1] OR [12, 40, 5, 3, 1]
         * 
         * Input: [80, 60, 30, 40, 20, 10] Output: [80, 60, 30, 20, 10] OR [80, 60, 40,
         * 20, 10]
         * 
         * In previous post, we have discussed about Longest Bitonic Subsequence
         * problem. However, the post only covered code related to finding maximum sum
         * of increasing subsequence, but not to the construction of subsequence. In
         * this post, we will discuss how to construct Longest Bitonic Subsequence
         * itself.
         * 
         * Let arr[0..n-1] be the input array. We define vector LIS such that LIS[i] is
         * itself is a vector that stores Longest Increasing Subsequence of arr[0..i]
         * that ends with arr[i]. Therefore for an index i, LIS[i] can be recursively
         * written as –
         */
        /**
         * LIS[0] = {arr[O]}
        LIS[i] = {Max(LIS[j])} + arr[i] where j < i and arr[j] < arr[i] 
            = arr[i], if there is no such j

        We also define a vector LDS such that LDS[i] is itself is a vector that stores Longest Decreasing Subsequence of arr[i..n] that starts with arr[i]. Therefore for an index i, LDS[i] can be recursively written as –

        LDS[n] = {arr[n]}
        LDS[i] = arr[i] + {Max(LDS[j])} where j > i and arr[j] < arr[i] 
            = arr[i], if there is no such j

        For example, for array [1 11 2 10 4 5 2 1],

        LIS[0]: 1
        LIS[1]: 1 11
        LIS[2]: 1 2
        LIS[3]: 1 2 10
        LIS[4]: 1 2 4
        LIS[5]: 1 2 4 5
        LIS[6]: 1 2
        LIS[7]: 1

        LDS[0]: 1
        LDS[1]: 11 10 5 2 1
        LDS[2]: 2 1
        LDS[3]: 10 5 2 1
        LDS[4]: 4 2 1
        LDS[5]: 5 2 1
        LDS[6]: 2 1
        LDS[7]: 1

        Therefore, Longest Bitonic Subsequence can be

        LIS[1] + LDS[1] = [1 11 10 5 2 1]        OR
        LIS[3] + LDS[3] = [1 2 10 5 2 1]        OR
        LIS[5] + LDS[5] = [1 2 4 5 2 1]

        Below is the implementation of above idea –


         */
        // Utility function to print Longest Bitonic
        // Subsequence
        static void print(Vector<Integer> arr, int size) {
            for (int i = 0; i < size; i++)
                System.out.print(arr.elementAt(i) + " ");
        }

        // Function to construct and print Longest
        // Bitonic Subsequence
        static void printLBS(int[] arr, int n) {

            // LIS[i] stores the length of the longest
            // increasing subsequence ending with arr[i]
            @SuppressWarnings("unchecked")
            Vector<Integer>[] LIS = new Vector[n];

            for (int i = 0; i < n; i++)
                LIS[i] = new Vector<>();

            // initialize LIS[0] to arr[0]
            LIS[0].add(arr[0]);

            // Compute LIS values from left to right
            for (int i = 1; i < n; i++) {

                // for every j less than i
                for (int j = 0; j < i; j++) {

                    if ((arr[i] > arr[j]) && LIS[j].size() > LIS[i].size()) {
                        for (int k : LIS[j])
                            if (!LIS[i].contains(k))
                                LIS[i].add(k);
                    }
                }
                LIS[i].add(arr[i]);
            }

            /*
             * LIS[i] now stores Maximum Increasing Subsequence of arr[0..i] that ends with
             * arr[i]
             */

            // LDS[i] stores the length of the longest
            // decreasing subsequence starting with arr[i]
            @SuppressWarnings("unchecked")
            Vector<Integer>[] LDS = new Vector[n];

            for (int i = 0; i < n; i++)
                LDS[i] = new Vector<>();

            // initialize LDS[n-1] to arr[n-1]
            LDS[n - 1].add(arr[n - 1]);

            // Compute LDS values from right to left
            for (int i = n - 2; i >= 0; i--) {

                // for every j greater than i
                for (int j = n - 1; j > i; j--) {
                    if (arr[j] < arr[i] && LDS[j].size() > LDS[i].size())
                        for (int k : LDS[j])
                            if (!LDS[i].contains(k))
                                LDS[i].add(k);
                }
                LDS[i].add(arr[i]);
            }

            // reverse as vector as we're inserting at end
            for (int i = 0; i < n; i++)
                Collections.reverse(LDS[i]);

            /*
             * LDS[i] now stores Maximum Decreasing Subsequence of arr[i..n] that starts
             * with arr[i]
             */
            int max = 0;
            int maxIndex = -1;
            for (int i = 0; i < n; i++) {

                // Find maximum value of size of
                // LIS[i] + size of LDS[i] - 1
                if (LIS[i].size() + LDS[i].size() - 1 > max) {
                    max = LIS[i].size() + LDS[i].size() - 1;
                    maxIndex = i;
                }
            }

            // print all but last element of LIS[maxIndex] vector
            print(LIS[maxIndex], LIS[maxIndex].size() - 1);

            // print all elements of LDS[maxIndex] vector
            print(LDS[maxIndex], LDS[maxIndex].size());
        }

        // Driver Code
        public static void main(String[] args) {
            int[] arr = { 1, 11, 2, 10, 4, 5, 2, 1 };
            int n = arr.length;

            printLBS(arr, n);
        }
    }

    static class CountAllPalindromicSubsequenceInGivenString {
        /**
         * Find how many palindromic subsequences (need not necessarily be distinct) can be formed in a given string. Note that the empty string is not considered as a palindrome. 
            Examples: 

            Input : str = "abcd"
            Output : 4
            Explanation :- palindromic  subsequence are : "a" ,"b", "c" ,"d" 

            Input : str = "aab"
            Output : 4
            Explanation :- palindromic subsequence are :"a", "a", "b", "aa"

            Input : str = "aaaa"
            Output : 15

            Initial Values : i= 0, j= n-1;

            The above problem can be recursively defined. 

            CountPS(i,j)
            // Every single character of a string is a palindrome 
            // subsequence 
            if i == j
            return 1 // palindrome of length 1

            // If first and last characters are same, then we 
            // consider it as palindrome subsequence and check
            // for the rest subsequence (i+1, j), (i, j-1)
            Else if (str[i] == str[j)]
            return   countPS(i+1, j) + countPS(i, j-1) + 1;

            else
            // check for rest sub-sequence and  remove common
            // palindromic subsequences as they are counted
            // twice when we do countPS(i+1, j) + countPS(i,j-1)
            return countPS(i+1, j) + countPS(i, j-1) - countPS(i+1, j-1)
         */
         // Function return the total palindromic
         // subsequence
         static int countPS(String str) {
             int N = str.length();

             // create a 2D array to store the count
             // of palindromic subsequence
             int[][] cps = new int[N][N];

             // palindromic subsequence of length 1
             for (int i = 0; i < N; i++)
                 cps[i][i] = 1;

             // check subsequence of length L is
             // palindrome or not
             for (int L = 2; L <= N; L++) {
                 for (int i = 0; i <= N - L; i++) {
                     int k = L + i - 1;
                     if (str.charAt(i) == str.charAt(k)) {
                         cps[i][k] = cps[i][k - 1] + cps[i + 1][k] + 1;
                     } else {
                         cps[i][k] = cps[i][k - 1] + cps[i + 1][k] - cps[i + 1][k - 1];
                     }
                 }
             }

             // return total palindromic subsequence
             return cps[0][N - 1];
         }

         // Driver program
         public static void main(String args[]) {
             String str = "abcb";
             System.out.println("Total palindromic " + "subsequence are : " + countPS(str));
         }
    }

    static class CountPalindromeSubStringsInaString {
        /**
         * Given a string, the task is to count all palindrome sub string in a given string. Length of palindrome sub string is greater than or equal to 2. 

            Examples: 

            Input : str = "abaab"
            Output: 3
            Explanation : 
            All palindrome substring are :
            "aba" , "aa" , "baab" 

            Input : str = "abbaeae"
            Output: 4
            Explanation : 
            All palindrome substring are : 
            "bb" , "abba" ,"aea","eae"

            The above problem can be recursively defined. 

            Initial Values : i = 0, j = n-1;
            Given string 'str'

            CountPS(i, j)
            
            // If length of string is 2 then we 
            // check both character are same or not 
            If (j == i+1)
                return str[i] == str[j]
            //this condition shows that in recursion if i crosses j then it will be a invalid substring or
            //if i==j that means only one character is remaining and we require substring of length 2 
            //in both the conditions we need to return 0
            Else if(i == j ||  i > j) return 0;
            Else If str[i..j] is PALINDROME 
                // increment count by 1 and check for 
                // rest palindromic substring (i, j-1), (i+1, j)
                // remove common palindrome substring (i+1, j-1)
                return  countPS(i+1, j) + countPS(i, j-1) + 1 -
                                            countPS(i+1, j-1);

                Else // if NOT PALINDROME 
                // We check for rest palindromic substrings (i, j-1)
                // and (i+1, j)
                // remove common palindrome substring (i+1 , j-1)
                return  countPS(i+1, j) + countPS(i, j-1) - 
                                        countPS(i+1 , j-1);
         */
        // Returns total number of palindrome substring of
        // length greater then equal to 2
        static int CountPS(char str[], int n) {
            // create empty 2-D matrix that counts all
            // palindrome substring. dp[i][j] stores counts of
            // palindromic substrings in st[i..j]
            int dp[][] = new int[n][n];

            // P[i][j] = true if substring str[i..j] is
            // palindrome, else false
            boolean P[][] = new boolean[n][n];

            // palindrome of single length
            for (int i = 0; i < n; i++)
                P[i][i] = true;

            // palindrome of length 2
            for (int i = 0; i < n - 1; i++) {
                if (str[i] == str[i + 1]) {
                    P[i][i + 1] = true;
                    dp[i][i + 1] = 1;
                }
            }

            // Palindromes of length more than 2. This loop is
            // similar to Matrix Chain Multiplication. We start
            // with a gap of length 2 and fill the DP table in a
            // way that gap between starting and ending indexes
            // increases one by one by outer loop.
            for (int gap = 2; gap < n; gap++) {
                // Pick starting point for current gap
                for (int i = 0; i < n - gap; i++) {
                    // Set ending point
                    int j = gap + i;

                    // If current string is palindrome
                    if (str[i] == str[j] && P[i + 1][j - 1])
                        P[i][j] = true;

                    // Add current palindrome substring ( + 1)
                    // and rest palindrome substring (dp[i][j-1]
                    // + dp[i+1][j]) remove common palindrome
                    // substrings (- dp[i+1][j-1])
                    if (P[i][j] == true)
                        dp[i][j] = dp[i][j - 1] + dp[i + 1][j] + 1 - dp[i + 1][j - 1];
                    else
                        dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1];
                }
            }

            // return total palindromic substrings
            return dp[0][n - 1];
        }

        // Driver code
        public static void main(String[] args) {
            String str = "abaab";
            System.out.println(CountPS(str.toCharArray(), str.length()));
        }
    }

    static class FindNumberOfPalindromicSubsequenceOfLengthK {
        /**
         * Given a string S of length n and a positive integer k. The task is to find number of Palindromic Subsequences of length k where k <= 3.
            Examples: 
            

            Input : s = "aabab", k = 2
            Output : 4

            Input : s = "aaa", k = 3
            Output : 1

            For k = 1, we can easily say that number of characters in string will be the answer. 
            For k = 2, we can easily make pairs of same characters so we have to maintain the count of each character in string and then calculate 
            

            sum = 0
            for character 'a' to 'z'
            cnt = count(characater)
            sum = sum + cnt*(cnt-1)/2
            sum is the answer.

            Now as k increases, it became difficult to find. How to find answer for k = 3 ? So the idea is to see that palindromes of length 3 will be of the format TZT, so we have to maintain two matrices, one to calculate the prefix sum of each character, and one to calculate suffix sum of each character in the string. 
            Prefix sum for a character T at index i is L[T][i] i.e number of times T has occured in the range [0, i](indices). 
            Suffix sum for a character T at index i is R[T] has occurred in the range [i, n – 1](indices). 
            Both the matrices will be 26*n and one can precompute both these matrices in complexity O(26*n) where n is the length of the string. 
            Now how to compute the subsequence ? Think over this: for an index i suppose a character X appears n1 times in the range [0, i – 1] and n2 times in the range [i + 1, n – 1] then the answer for this character will be n1 * n2 i.e L[X][i-1] * R[X][i + 1], this will give the count of subsequences of the format X-s[i]-X where s[i] is the character at i-th index. So for every index i you will have to count the product of 
            

            L[X][i-1] * R[X][i+1], 
            where i is the range [1, n-2]  and 
                X will be from 'a' to 'z'

            Below is the implementation of this approach: 
         */
        static final int MAX = 100;
        static final int MAX_CHAR = 26;

        // Precompute the prefix and suffix array.
        static void precompute(String s, int n, int l[][], int r[][]) {
            l[s.charAt(0) - 'a'][0] = 1;

            // Precompute the prefix 2D array
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < MAX_CHAR; j++)
                    l[j][i] += l[j][i - 1];

                l[s.charAt(i) - 'a'][i]++;
            }

            r[s.charAt(n - 1) - 'a'][n - 1] = 1;

            // Precompute the Suffix 2D array.
            for (int i = n - 2; i >= 0; i--) {
                for (int j = 0; j < MAX_CHAR; j++)
                    r[j][i] += r[j][i + 1];

                r[s.charAt(i) - 'a'][i]++;
            }
        }

        // Find the number of palindromic subsequence of
        // length k
        static int countPalindromes(int k, int n, int l[][], int r[][]) {
            int ans = 0;

            // If k is 1.
            if (k == 1) {
                for (int i = 0; i < MAX_CHAR; i++)
                    ans += l[i][n - 1];

                return ans;
            }

            // If k is 2
            if (k == 2) {

                // Adding all the products of prefix array
                for (int i = 0; i < MAX_CHAR; i++)
                    ans += ((l[i][n - 1] * (l[i][n - 1] - 1)) / 2);

                return ans;
            }

            // For k greater than 2. Adding all the products
            // of value of prefix and suffix array.
            for (int i = 1; i < n - 1; i++)
                for (int j = 0; j < MAX_CHAR; j++)
                    ans += l[j][i - 1] * r[j][i + 1];

            return ans;
        }

        // Driver code
        public static void main(String[] args) {
            String s = "aabab";
            int k = 2;
            int n = s.length();
            int l[][] = new int[MAX_CHAR][MAX];
            int r[][] = new int[MAX_CHAR][MAX];

            precompute(s, n, l, r);

            System.out.println(countPalindromes(k, n, l, r));
        }
    }

    static class CountOfPalindromicSubstringsInIndexRange {
        /**
         * Given a string str of small alphabetic characters other than this we will be given many substrings of this string in form of index tuples. We need to find out the count of the palindromic substrings in given substring range. 
            Examples: 
            

            Input : String str = "xyaabax"
                    Range1 = (3, 5)   
                    Range2 = (2, 3) 
            Output : 4
                    3
            For Range1,  substring is "aba"
            Count of palindromic substring in "aba" is 
            four : "a", "b", "aba", "a"
            For Range2,  substring is "aa"
            Count of palindromic substring in "aa" is 
            3 : "a", "a", "aa"

            We can solve this problem using dynamic programming. First we will make a 2D array isPalin, isPalin[i][j] will be 1 if string(i..j) is a palindrome otherwise it will be 0. After constructing isPalin we will construct another 2D array dp, dp[i][j] will tell the count of palindromic substring in string(i..j) 
            Now we can write the relation among isPalin and dp values as shown below, 
            

            // isPalin[i][j] will be 1 if ith and jth characters 
            // are equal and mid substring str(i+1..j-1) is also
            // a palindrome
            isPalin[i][j] = (str[i] == str[j]) and 
                            (isPalin[i + 1][j – 1])
                        
            // Similar to set theory we can write the relation among
            // dp values as,
            // dp[i][j] will be addition of number of palindromes from 
            // i to j-1 and i+1 to j  subtracting palindromes from i+1
            // to j-1 because they are counted twice once in dp[i][j-1] 
            // and then in dp[i + 1][j] plus 1 if str(i..j) is also a
            // palindrome
            dp[i][j] = dp[i][j-1] + dp[i+1][j] - dp[i+1][j-1] + 
                                                isPalin[i][j];
         */
        // Function to construct the dp array
        static void constructDp(int dp[][], String str) {
            int l = str.length();

            // declare 2D array isPalin, isPalin[i][j] will
            // be 1 if str(i..j) is palindrome
            int[][] isPalin = new int[l + 1][l + 1];

            // initialize dp and isPalin array by zeros
            for (int i = 0; i <= l; i++) {
                for (int j = 0; j <= l; j++) {
                    isPalin[i][j] = dp[i][j] = 0;
                }
            }

            // loop for starting index of range
            for (int i = l - 1; i >= 0; i--) {
                // initialize value for one character strings as 1
                isPalin[i][i] = 1;
                dp[i][i] = 1;

                // loop for ending index of range
                for (int j = i + 1; j < l; j++) {
                    /*
                     * isPalin[i][j] will be 1 if ith and jth characters are equal and mid substring
                     * str(i+1..j-1) is also a palindrome
                     */
                    isPalin[i][j] = (str.charAt(i) == str.charAt(j) && (i + 1 > j - 1 || (isPalin[i + 1][j - 1]) != 0))
                            ? 1
                            : 0;

                    /*
                     * dp[i][j] will be addition of number of palindromes from i to j-1 and i+1 to j
                     * subtracting palindromes from i+1 to j-1 (as counted twice) plus 1 if
                     * str(i..j) is also a palindrome
                     */
                    dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1] + isPalin[i][j];
                }
            }
        }

        // method returns count of palindromic substring in range (l, r)
        static int countOfPalindromeInRange(int dp[][], int l, int r) {
            return dp[l][r];
        }

        // driver program
        public static void main(String args[]) {
            int MAX = 50;
            String str = "xyaabax";
            int[][] dp = new int[MAX][MAX];
            constructDp(dp, str);

            int l = 3;
            int r = 5;
            System.out.println(countOfPalindromeInRange(dp, l, r));
        }
    }

    static class ShortestCommonSuperSequence {
        /**
         * Given two strings str1 and str2, the task is to find the length of the shortest string that has both str1 and str2 as subsequences.

            Examples : 

            Input:   str1 = "geek",  str2 = "eke"
            Output: 5
            Explanation: 
            String "geeke" has both string "geek" 
            and "eke" as subsequences.

            Input:   str1 = "AGGTAB",  str2 = "GXTXAYB"
            Output:  9
            Explanation: 
            String "AGXGTXAYB" has both string 
            "AGGTAB" and "GXTXAYB" as subsequences.

            This problem is closely related to longest common subsequence problem. Below are steps.
            1) Find Longest Common Subsequence (lcs) of two given strings. For example, lcs of “geek” and “eke” is “ek”. 
            2) Insert non-lcs characters (in their original order in strings) to the lcs found above, and return the result. So “ek” becomes “geeke” which is shortest common supersequence.
            Let us consider another example, str1 = “AGGTAB” and str2 = “GXTXAYB”. LCS of str1 and str2 is “GTAB”. Once we find LCS, we insert characters of both strings in order and we get “AGXGTXAYB”
            How does this work? 
            We need to find a string that has both strings as subsequences and is shortest such string. If both strings have all characters different, then result is sum of lengths of two given strings. If there are common characters, then we don’t want them multiple times as the task is to minimize length. Therefore, we fist find the longest common subsequence, take one occurrence of this subsequence and add extra characters. 

            Length of the shortest supersequence  
            = (Sum of lengths of given two strings) 
            - (Length of LCS of two given strings) 

            Below is the implementation of above idea. The below implementation only finds length of the shortest supersequence. 
         */
        // Function to find length of the
        // shortest supersequence of X and Y.
        static int shortestSuperSequence(String X, String Y) {
            int m = X.length();
            int n = Y.length();

            // find lcs
            int l = lcs(X, Y, m, n);

            // Result is sum of input string
            // lengths - length of lcs
            return (m + n - l);
        }

        // Returns length of LCS
        // for X[0..m - 1], Y[0..n - 1]
        static int lcs(String X, String Y, int m, int n) {
            int[][] L = new int[m + 1][n + 1];
            int i, j;

            // Following steps build L[m + 1][n + 1]
            // in bottom up fashion. Note that
            // L[i][j] contains length of LCS
            // of X[0..i - 1]and Y[0..j - 1]
            for (i = 0; i <= m; i++) {
                for (j = 0; j <= n; j++) {
                    if (i == 0 || j == 0)
                        L[i][j] = 0;

                    else if (X.charAt(i - 1) == Y.charAt(j - 1))
                        L[i][j] = L[i - 1][j - 1] + 1;

                    else
                        L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }

            // L[m][n] contains length of LCS
            // for X[0..n - 1] and Y[0..m - 1]
            return L[m][n];
        }

        // Driver code
        public static void main(String args[]) {
            String X = "AGGTAB";
            String Y = "GXTXAYB";

            System.out.println("Length of the shortest " + "supersequence is " + shortestSuperSequence(X, Y));
        }
    }

    static class MaximumSumAlternatingSequence_Increasing_Decreasing_Increasing {
        /**
         * Given an array, the task is to find sum of maximum sum alternating
         * subsequence starting with first element. Here alternating sequence means
         * first decreasing, then increasing, then decreasing, … For example 10, 5, 14,
         * 3 is an alternating sequence. Note that the reverse type of sequence
         * (increasing – decreasing – increasing -…) is not considered alternating here.
         * Examples:
         * 
         * 
         * Input : arr[] = {4, 3, 8, 5, 3, 8} Output : 28 Explanation: The alternating
         * subsequence (starting with first element) that has above maximum sum is {4,
         * 3, 8, 5, 8}
         * 
         * Input : arr[] = {4, 8, 2, 5, 6, 8} Output : 14 The alternating subsequence
         * (starting with first element) that has above maximum sum is {4, 2, 8}
         **/
        /**
            This problem is similar to Longest Increasing Subsequence (LIS) problem. and can be solved using Dynamic Programming.

            Create two empty array that store result of maximum
            sum  of alternate sub-sequence
            inc[] : inc[i] stores results of maximum sum alternating
                    subsequence ending with arr[i] such that arr[i]
                    is greater than previous element of the subsequence 
            dec[] : dec[i] stores results of maximum sum alternating
                    subsequence ending with arr[i] such that arr[i]
                    is less than previous element of the subsequence 

            Include first element of 'arr' in both inc[] and dec[] 
            inc[0] = dec[0] = arr[0]

            // Maintain a flag i.e. it will makes the greater
            // elements count only if the first decreasing element
            // is counted.
            flag  = 0 

            Traversal two loops
            i goes from 1 to  n-1 
                j goes 0 to i-1
                IF arr[j] > arr[i]
                    dec[i] = max(dec[i], inc[j] + arr[i])
                
                    // Denotes first decreasing is found
                    flag = 1 
            
                ELSE IF arr[j] < arr[i] && flag == 1 
                    inc[i] = max(inc[i], dec[j]+arr[i]);
                
            Final Last Find maximum value inc[] and dec[] .

            Below is implementation of above idea.
         **/
        

        // Return sum of maximum sum alternating
        // sequence starting with arr[0] and is first
        // decreasing.
        static int maxAlternateSum(int arr[], int n) {
            if (n == 1)
                return arr[0];

            // create two empty array that store result of
            // maximum sum of alternate sub-sequence

            // stores sum of decreasing and increasing
            // sub-sequence
            int dec[] = new int[n];

            // store sum of increasing and decreasing sun-sequence
            int inc[] = new int[n];

            // As per question, first element must be part
            // of solution.
            dec[0] = inc[0] = arr[0];

            int flag = 0;

            // Traverse remaining elements of array
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    // IF current sub-sequence is decreasing the
                    // update dec[j] if needed. dec[i] by current
                    // inc[j] + arr[i]
                    if (arr[j] > arr[i]) {
                        dec[i] = Math.max(dec[i], inc[j] + arr[i]);

                        // Revert the flag , if first decreasing
                        // is found
                        flag = 1;
                    }

                    // If next element is greater but flag should be 1
                    // i.e. this element should be counted after the
                    // first decreasing element gets counted
                    else if (arr[j] < arr[i] && flag == 1)

                        // If current sub-sequence is increasing
                        // then update inc[i]
                        inc[i] = Math.max(inc[i], dec[j] + arr[i]);
                }
            }

            // find maximum sum in b/w inc[] and dec[]
            int result = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                if (result < inc[i])
                    result = inc[i];
                if (result < dec[i])
                    result = dec[i];
            }

            // return maximum sum alternate sun-sequence
            return result;
        }

        // Driver Method
        public static void main(String[] args) {
            int arr[] = { 8, 2, 3, 5, 7, 9, 10 };
            System.out.println("Maximum sum = " + maxAlternateSum(arr, arr.length));
        }
    }

    static class LongestAlternatingSequence_Inreasing_Decreasing_Increasing {
        /**
         * A sequence {x1, x2, .. xn} is alternating sequence if its elements satisfy one of the following relations : 

            x1 < x2 > x3 < x4 > x5 < …. xn or 
            x1 > x2 < x3 > x4 < x5 > …. xn

            Examples :

            Input: arr[] = {1, 5, 4}
            Output: 3
            The whole arrays is of the form  x1 < x2 > x3 

            Input: arr[] = {1, 4, 5}
            Output: 2
            All subsequences of length 2 are either of the form 
            x1 < x2; or x1 > x2

            Input: arr[] = {10, 22, 9, 33, 49, 50, 31, 60}
            Output: 6
            The subsequences {10, 22, 9, 33, 31, 60} or
            {10, 22, 9, 49, 31, 60} or {10, 22, 9, 50, 31, 60}
            are longest subsequence of length 6.

            This problem is an extension of longest increasing subsequence problem, but requires more thinking for finding optimal substructure property in this.
            We will solve this problem by dynamic Programming method, Let A is given array of length n of integers. 
            We define a 2D array las[n][2] such that las[i][0] contains longest alternating subsequence ending at index i and last element is greater than its previous element and las[i][1] contains longest alternating subsequence ending at index i and last element is smaller than its previous element, then we have following recurrence relation between them,  

            las[i][0] = Length of the longest alternating subsequence 
                    ending at index i and last element is greater
                    than its previous element
            las[i][1] = Length of the longest alternating subsequence 
                    ending at index i and last element is smaller
                    than its previous element

            Recursive Formulation:
            las[i][0] = max (las[i][0], las[j][1] + 1); 
                        for all j < i and A[j] < A[i] 
            las[i][1] = max (las[i][1], las[j][0] + 1); 
                        for all j < i and A[j] > A[i]

            The first recurrence relation is based on the fact that, If we are at position i and this element has to bigger than its previous element 
            then for this sequence (upto i) to be bigger we will try to choose an element j ( < i) such that A[j] < A[i] i.e. A[j] can become A[i]’s previous element and las[j][1] + 1 is bigger than las[i][0] then we will update las[i][0]. 
            Remember we have chosen las[j][1] + 1 not las[j][0] + 1 to satisfy alternate property because in las[j][0] last element is bigger than 
            its previous one and A[i] is greater than A[j] which will break the alternating property if we update. 
            So above fact derives first recurrence relation, similar argument can be made for second recurrence relation also. 
         */
        // Function to return longest
        // alternating subsequence length
        static int zzis(int arr[], int n) {
            /*
             * las[i][0] = Length of the longest alternating subsequence ending at index i
             * and last element is greater than its previous element las[i][1] = Length of
             * the longest alternating subsequence ending at index i and last element is
             * smaller than its previous element
             */
            int las[][] = new int[n][2];

            /* Initialize all values from 1 */
            for (int i = 0; i < n; i++)
                las[i][0] = las[i][1] = 1;

            int res = 1; // Initialize result

            /* Compute values in bottom up manner */
            for (int i = 1; i < n; i++) {
                // Consider all elements as
                // previous of arr[i]
                for (int j = 0; j < i; j++) {
                    // If arr[i] is greater, then
                    // check with las[j][1]
                    if (arr[j] < arr[i] && las[i][0] < las[j][1] + 1)
                        las[i][0] = las[j][1] + 1;

                    // If arr[i] is smaller, then
                    // check with las[j][0]
                    if (arr[j] > arr[i] && las[i][1] < las[j][0] + 1)
                        las[i][1] = las[j][0] + 1;
                }

                /*
                 * Pick maximum of both values at index i
                 */
                if (res < Math.max(las[i][0], las[i][1]))
                    res = Math.max(las[i][0], las[i][1]);
            }

            return res;
        }

        /* Driver program */
        public static void main(String[] args) {
            int arr[] = { 10, 22, 9, 33, 49, 50, 31, 60 };
            int n = arr.length;
            System.out.println("Length of Longest " + "alternating subsequence is " + zzis(arr, n));
        }
    }

    static class ShortestUncommonSubsequence {
        /**
         * Given two strings S and T, find the length of the shortest subsequence in S which is not a subsequence in T. If no such subsequence is possible, return -1. A subsequence is a sequence that appears in the same relative order, but not necessarily contiguous. A string of length n has 2^n different possible subsequences.
            String S of length m (1 <= m <= 1000) 
            String T of length n (1 <= n <= 1000)
            Examples: 

            Input : S = “babab” T = “babba”
            Output : 3
            The subsequence “aab” of length 3 is 
            present in S but not in T.

            Input :  S = “abb” T = “abab”
            Output : -1
            There is no subsequence that is present 
            in S but not in T.

            Efficient (Dynamic Programming)
            1.Optimal substructure: Consider two strings S and T of length m and n respectively & let the function to find the shortest 
            uncommon subsequence be shortestSeq (char *S, char *T). For each character in S, if it is not present in T then that character is the answer itself. 
            Otherwise, if it is found at index k then we have the choice of either including it in the shortest uncommon subsequence or not. 
            

            If it is included answer = 1 + ShortestSeq( S + 1, T + k + 1) 
            If not included answer =  ShortestSeq( S + 1, T) 
            The minimum of the two is the answer.

            Thus, we can see that this problem has optimal substructure property as it can be solved by using solutions to subproblems.
            2.Overlapping Subproblems 
            Following is a simple recursive implementation of the above problem. 
                    / Return minimum of following two
            // Not including current char in answer
            // Including current char
            return Math.min(shortestSeq(Arrays.copyOfRange(S, 1, S.length), T, m - 1, n),
                            1 + shortestSeq(Arrays.copyOfRange(S, 1, S.length),
                            Arrays.copyOfRange(T, k + 1, T.length), m - 1, n - k - 1));
         */
        static final int MAX = 1005;

        // Returns length of shortest common subsequence
        static int shortestSeq(char[] S, char[] T) {
            int m = S.length, n = T.length;

            // declaring 2D array of m + 1 rows and
            // n + 1 columns dynamically
            int dp[][] = new int[m + 1][n + 1];

            // T string is empty
            for (int i = 0; i <= m; i++) {
                dp[i][0] = 1;
            }

            // S string is empty
            for (int i = 0; i <= n; i++) {
                dp[0][i] = MAX;
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    char ch = S[i - 1];
                    int k;
                    for (k = j - 1; k >= 0; k--) {
                        if (T[k] == ch) {
                            break;
                        }
                    }

                    // char not present in T
                    if (k == -1) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][k] + 1);
                    }
                }
            }

            int ans = dp[m][n];
            if (ans >= MAX) {
                ans = -1;
            }
            return ans;
        }

        // Driver code
        public static void main(String[] args) {
            char S[] = "babab".toCharArray();
            char T[] = "babba".toCharArray();
            int m = S.length, n = T.length;
            System.out.println("Length of shortest" + "subsequence is : " + shortestSeq(S, T));
        }
    }

    static class CountDistinctSubsequences {
        /**
         * Given a string, find the count of distinct subsequences of it.
         * 
         * Examples:
         * 
         * Input : str = "gfg" Output : 7 The seven distinct subsequences are "", "g",
         * "f", "gf", "fg", "gg" and "gfg"
         * 
         * Input : str = "ggg" Output : 4 The four distinct subsequences are "", "g",
         * "gg" and "ggg"
         * 
         * The problem of counting distinct subsequences is easy if all characters of
         * input string are distinct. The count is equal to nC0 + nC1 + nC2 + … nCn =
         * 2n. How to count distinct subsequences when there can be repetition in input
         * string? A Simple Solution to count distinct subsequences in a string with
         * duplicates is to generate all subsequences. For every subsequence, store it
         * in a hash table if it doesn’t exist already. The time complexity of this
         * solution is exponential and it requires exponential extra space.
         */
        /**
         * An Efficient Solution doesn’t require the generation of subsequences.  

            Let countSub(n) be count of subsequences of 
            first n characters in input string. We can
            recursively write it as below. 

            countSub(n) = 2*Count(n-1) - Repetition

            If current character, i.e., str[n-1] of str has
            not appeared before, then 
            Repetition = 0

            Else:
            Repetition  =  Count(m)
            Here m is index of previous occurrence of
            current character. We basically remove all
            counts ending with previous occurrence of
            current character.

            How does this work? 
            If there are no repetitions, then count becomes double of count for n-1 because we get count(n-1) more subsequences by adding current character at the end of all subsequences possible with n-1 length. 
            If there repetitions, then we find a count of all distinct subsequences ending with the previous occurrence. This count can be obtained by recursively calling for an index of the previous occurrence. 
            Since the above recurrence has overlapping subproblems, we can solve it using Dynamic Programming. 

            Below is the implementation of the above idea.
         */
        static final int MAX_CHAR = 256;

        // Returns count of distinct sunsequences of str.
        static int countSub(String str) {
            // Create an array to store index
            // of last
            int[] last = new int[MAX_CHAR];
            Arrays.fill(last, -1);

            // Length of input string
            int n = str.length();

            // dp[i] is going to store count of distinct
            // subsequences of length i.
            int[] dp = new int[n + 1];

            // Empty substring has only one subsequence
            dp[0] = 1;

            // Traverse through all lengths from 1 to n.
            for (int i = 1; i <= n; i++) {
                // Number of subsequences with substring
                // str[0..i-1]
                dp[i] = 2 * dp[i - 1];

                // If current character has appeared
                // before, then remove all subsequences
                // ending with previous occurrence.
                if (last[(int) str.charAt(i - 1)] != -1)
                    dp[i] = dp[i] - dp[last[(int) str.charAt(i - 1)]];

                // Mark occurrence of current character
                last[(int) str.charAt(i - 1)] = (i - 1);
            }

            return dp[n];
        }

        // Driver code
        public static void main(String args[]) {
            System.out.println(countSub("gfg"));
        }
    }

    static class CountDistinctOccurrencesAsSubsequence {
        /**
         * Given a two strings S and T, find the count of distinct occurrences of T in S as a subsequence.
            Examples: 
            

            Input: S = banana, T = ban
            Output: 3
            Explanation: T appears in S as below three subsequences.
            [ban], [ba  n], [b   an]

            Input: S = geeksforgeeks, T = ge
            Output: 6
            Explanation: T appears in S as below three subsequences.
            [ge], [     ge], [g e], [g    e] [g     e]
            and [     g e]      
         */
        /**
         * Approach: Create a recursive function such that it returns count of subsequences of S that match T. Here m is the length of T and n is length of S. 
         * This problem can be recursively defined as below. 
 
                Given the string T is an empty string, returning 1 as an empty string can be the subsequence of all.
                Given the string S is an empty string, returning 0 as no string can be the subsequence of an empty string.
                If the last character of S and T do not match, then remove the last character of S and call the recursive function again. 
                Because the last character of S cannot be a part of the subsequence or remove it and check for other characters.
                If the last character of S match then there can be two possibilities, first there can be a subsequence where the last character of S is a part of it and second where it is not a part of the subsequence. 
                So the required value will be the sum of both. Call the recursive function once with last character of both the strings removed and again with only last character of S removed.
         */
        /**
         * Since there are overlapping subproblems in the above recurrence result, Dynamic Programming approach can be applied to solve the above problem. Store the subproblems in a Hashmap or an array and return the value when the function is called again.
        Algorithm: 
        

            Create a 2D array mat[m+1][n+1] where m is length of string T and n is length of string S. mat[i][j] denotes the number of distinct subsequence of substring S(1..i) and substring T(1..j) so mat[m][n] contains our solution. 
            
            Initialize the first column with all 0s. An empty string can’t have another string as suhsequence
            Initialize the first row with all 1s. An empty string is a subsequence of all.
            Fill the matrix in bottom-up manner, i.e. all the sub problems of the current string is calculated first.
            Traverse the string T from start to end. (counter is i)
            For every iteration of the outer loop, Traverse the string S from start to end. (counter is j)
            If the character at ith index of string T matches with jth character of string S, the value is obtained considering two cases. First, is all the substrings without last character in S and second is the substrings without last characters in both, i.e mat[i+1][j] + mat[i][j] .
            Else the value will be same even if jth character of S is removed, i.e. mat[i+1][j]
            Print the value of mat[m-1][n-1] as the answer.
         */
        static int findSubsequenceCount(String S, String T) {
            int m = T.length();
            int n = S.length();

            // T can't appear as a subsequence in S
            if (m > n)
                return 0;

            // mat[i][j] stores the count of
            // occurrences of T(1..i) in S(1..j).
            int mat[][] = new int[m + 1][n + 1];

            // Initializing first column with
            // all 0s. An emptystring can't have
            // another string as suhsequence
            for (int i = 1; i <= m; i++)
                mat[i][0] = 0;

            // Initializing first row with all 1s.
            // An empty string is subsequence of all.
            for (int j = 0; j <= n; j++)
                mat[0][j] = 1;

            // Fill mat[][] in bottom up manner
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    // If last characters don't match,
                    // then value is same as the value
                    // without last character in S.
                    if (T.charAt(i - 1) != S.charAt(j - 1))
                        mat[i][j] = mat[i][j - 1];

                    // Else value is obtained considering two cases.
                    // a) All substrings without last character in S
                    // b) All substrings without last characters in
                    // both.
                    else
                        mat[i][j] = mat[i][j - 1] + mat[i - 1][j - 1];
                }
            }

            /*
             * uncomment this to print matrix mat for (int i = 1; i <= m; i++, cout << endl)
             * for (int j = 1; j <= n; j++) System.out.println ( mat[i][j] +" ");
             */
            return mat[m][n];
        }

        // Driver code to check above method
        public static void main(String[] args) {
            String T = "ge";
            String S = "geeksforgeeks";
            System.out.println(findSubsequenceCount(S, T));
        }
    }

    static class LongestCommonIncreasingSubsequence {
        /**
         * Prerequisites : LCS, LIS
            Given two arrays, find length of the longest common increasing subsequence [LCIS] and print one of such sequences (multiple sequences may exist)
            Suppose we consider two arrays – 
            arr1[] = {3, 4, 9, 1} and 
            arr2[] = {5, 3, 8, 9, 10, 2, 1}
            Our answer would be {3, 9} as this is the longest common subsequence which is increasing also.
         */
        /**
         * The idea is to use dynamic programming here as well. We store the longest
         * common increasing sub-sequence ending at each index of arr2[]. We create an
         * auxiliary array table[] such that table[j] stores length of LCIS ending with
         * arr2[j]. At the end, we return maximum value from this table. For filling
         * values in this table, we traverse all elements of arr1[] and for every
         * element arr1[i], we traverse all elements of arr2[]. If we find a match, we
         * update table[j] with length of current LCIS. To maintain current LCIS, we
         * keep checking valid table[j] values. Below is the program to find length of
         * LCIS.
         */
        // Returns the length and the LCIS of two
        // arrays arr1[0..n-1] and arr2[0..m-1]
        static int LCIS(int arr1[], int n, int arr2[], int m) {
            // table[j] is going to store length of
            // LCIS ending with arr2[j]. We initialize
            // it as 0,
            int table[] = new int[m];
            for (int j = 0; j < m; j++)
                table[j] = 0;

            // Traverse all elements of arr1[]
            for (int i = 0; i < n; i++) {
                // Initialize current length of LCIS
                int current = 0;

                // For each element of arr1[], trvarse
                // all elements of arr2[].
                for (int j = 0; j < m; j++) {
                    // If both the array have same
                    // elements. Note that we don't
                    // break the loop here.
                    if (arr1[i] == arr2[j])
                        if (current + 1 > table[j])
                            table[j] = current + 1;

                    /*
                     * Now seek for previous smaller common element for current element of arr1
                     */
                    if (arr1[i] > arr2[j])
                        if (table[j] > current)
                            current = table[j];
                }
            }

            // The maximum value in table[] is out
            // result
            int result = 0;
            for (int i = 0; i < m; i++)
                if (table[i] > result)
                    result = table[i];

            return result;
        }

        /* Driver program to test above function */
        public static void main(String[] args) {
            int arr1[] = { 3, 4, 9, 1 };
            int arr2[] = { 5, 3, 8, 9, 10, 2, 1 };

            int n = arr1.length;
            int m = arr2.length;

            System.out.println("Length of LCIS is " + LCIS(arr1, n, arr2, m));
        }

    }

    static class LongestCommonSubsequenceFormedByConsecutiveSegmentsOfLengthK{
        /**
         * Given two strings s1, s2 and K, find the length of the longest subsequence formed by consecutive segments of at least length K.
        Examples: 
        

        Input : s1 = aggayxysdfa
                s2 = aggajxaaasdfa 
                k = 4
        Output : 8 
        Explanation: aggasdfa is the longest
        subsequence that can be formed by taking
        consecutive segments, minimum of length 4.
        Here segments are "agga" and "sdfa" which 
        are of length 4 which is included in making 
        the longest subsequence. 

        Input : s1 = aggasdfa 
                s2 = aggajasdfaxy
                k = 5
        Output : 5 

        Input: s1 = "aabcaaaa" 
            s2 = "baaabcd"  
                k = 3
        Output: 4 
        Explanation: "aabc" is the longest subsequence that 
        is formed by taking segment of minimum length 3. 
        The segment is of length 4. 
         */
        /**
         * Create a LCS[][] array where LCSi, j denotes the length of the longest common
         * subsequence formed by characters of s1 till i and s2 till j having
         * consecutive segments of at least length K. Create a cnt[][] array to count
         * the length of the common segment. cnti, j= cnti-1, j-1+1 when
         * s1[i-1]==s2[j-1]. If characters are not equal then segments are not equal
         * hence mark cnti, j as 0. 
         * 
         * When cnti, j>=k, then update the lcs value by adding
         * the value of lcsi-a, j-a where a is the length of the segments a<=cnti, j.
         * The answer for the longest subsequence with consecutive segments of at least
         * length k will be stored in lcs[n][m] where n and m are the length of string1
         * and string2.
         */

         // Returns the length of the longest common subsequence
        // with a minimum of length of K consecutive segments
        static int longestSubsequenceCommonSegment(int k, String s1, String s2) {
            // length of strings
            int n = s1.length();
            int m = s2.length();

            // declare the lcs and cnt array
            int lcs[][] = new int[n + 1][m + 1];
            int cnt[][] = new int[n + 1][m + 1];

            // iterate from i=1 to n and j=1 to j=m
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {

                    // stores the maximum of lcs[i-1][j] and lcs[i][j-1]
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);

                    // when both the characters are equal
                    // of s1 and s2
                    if (s1.charAt(i - 1) == s2.charAt(j - 1))
                        cnt[i][j] = cnt[i - 1][j - 1] + 1;

                    // when length of common segment is
                    // more than k, then update lcs answer
                    // by adding that segment to the answer
                    if (cnt[i][j] >= k) {

                        // formulate for all length of segments
                        // to get the longest subsequence with
                        // consecutive Common Segment of length
                        // of min k length
                        for (int a = k; a <= cnt[i][j]; a++)

                            // update lcs value by adding
                            // segment length
                            lcs[i][j] = Math.max(lcs[i][j], lcs[i - a][j - a] + a);

                    }
                }
            }

            return lcs[n][m];
        }

        // driver code to check the above function
        public static void main(String[] args) {
            int k = 4;
            String s1 = "aggasdfa";
            String s2 = "aggajasdfa";
            System.out.println(longestSubsequenceCommonSegment(k, s1, s2));
        }
    }

    static class PrintMaximumSumIncreasingSubsequence {
        /**
         * The Maximum Sum Increasing Subsequence problem is to find the maximum sum
         * subsequence of a given sequence such that all elements of the subsequence are
         * sorted in increasing order.
         * 
         * Examples:
         * 
         * Input: [1, 101, 2, 3, 100, 4, 5] Output: [1, 2, 3, 100]
         * 
         * Input: [3, 4, 5, 10] Output: [3, 4, 5, 10]
         * 
         * Input: [10, 5, 4, 3] Output: [10]
         * 
         * Input: [3, 2, 6, 4, 5, 1] Output: [3, 4, 5]
         */
        /**
         * In previous post, we have discussed the Maximum Sum Increasing Subsequence problem. However, the post only covered code related to finding maximum sum of increasing subsequence, 
         * but not to the construction of subsequence. In this post, we will discuss how to construct Maximum Sum Increasing Subsequence itself.

            Let arr[0..n-1] be the input array. We define vector L such that L[i] is itself is a vector that stores Maximum Sum Increasing Subsequence of arr[0..i] that ends with arr[i]. 
            Therefore for index i, L[i] can be recursively written as 

            L[0] = {arr[0]}
            L[i] = {MaxSum(L[j])} + arr[i] where j < i and arr[j] < arr[i]
                = arr[i], if there is no j such that arr[j] < arr[i]

            For example, for array [3, 2, 6, 4, 5, 1], 
            L[0]: 3
            L[1]: 2
            L[2]: 3 6
            L[3]: 3 4
            L[4]: 3 4 5
            L[5]: 1

            Below is the implementation of the above idea – 
         */
        // Utility function to calculate sum of all
        // vector elements
        static int findSum(Vector<Integer> arr) {
            int sum = 0;
            for (int i : arr)
                sum += i;
            return sum;
        }

        // Function to construct Maximum Sum Increasing
        // Subsequence
        static void printMaxSumIs(int[] arr, int n) {

            // L[i] - The Maximum Sum Increasing
            // Subsequence that ends with arr[i]
            @SuppressWarnings("unchecked")
            Vector<Integer>[] L = new Vector[n];

            for (int i = 0; i < n; i++)
                L[i] = new Vector<>();

            // L[0] is equal to arr[0]
            L[0].add(arr[0]);

            // start from index 1
            for (int i = 1; i < n; i++) {

                // for every j less than i
                for (int j = 0; j < i; j++) {

                    /*
                     * L[i] = {MaxSum(L[j])} + arr[i] where j < i and arr[j] < arr[i]
                     */
                    if ((arr[i] > arr[j]) && (findSum(L[i]) < findSum(L[j]))) {
                        for (int k : L[j])
                            if (!L[i].contains(k))
                                L[i].add(k);
                    }
                }

                // L[i] ends with arr[i]
                L[i].add(arr[i]);

                // L[i] now stores Maximum Sum Increasing
                // Subsequence of arr[0..i] that ends with
                // arr[i]
            }

            Vector<Integer> res = new Vector<>(L[0]);
            // res = L[0];

            // find max
            for (Vector<Integer> x : L)
                if (findSum(x) > findSum(res))
                    res = x;

            // max will contain result
            for (int i : res)
                System.out.print(i + " ");
            System.out.println();
        }

        // Driver Code
        public static void main(String[] args) {
            int[] arr = { 3, 2, 6, 4, 5, 1 };
            int n = arr.length;

            // construct and print Max Sum IS of arr
            printMaxSumIs(arr, n);
        }
    }

    static class LongestIncreasingOddEvenSubsequence {
        /**
         * Given an array of size n. The problem is to find the length of the
         * subsequence in the given array such that all the elements of the subsequence
         * are sorted in increasing order and also they are alternately odd and even.
         * Note that the subsequence could start either with the odd number or with the
         * even number. Examples:
         * 
         * Input : arr[] = {5, 6, 9, 4, 7, 8} Output : 6 {5, 6, 9,4,7,8} is the required
         * longest increasing odd even subsequence which is the array itself in this
         * case
         * 
         * Input : arr[] = {1, 12, 2, 22, 5, 30, 31, 14, 17, 11} {1,12,5,30,31,14,17} is
         * the required longest increasing odd even subsequence Output : 7
         * 
         * Efficient Approach: 
            Let L(i) be the length of the LIOES (Longest Increasing Odd Even Subsequence) ending at index i such that arr[i] is the last element of the LIOES. 
            Then, L(i) can be recursively written as: 
            L(i) = 1 + max( L(j) ) where 0 < j < i and (arr[j] < arr[i]) and (arr[i]+arr[j])%2 != 0; or 
            L(i) = 1, if no such j exists. 
            To find the LIOES for a given array, we need to return max(L(i)) where 0 < i < n. 
            A dynamic programming approach has been implemented below for the above mentioned recursive relation
         */
        // function to find the longest
        // increasing odd even subsequence
        public static int longOddEvenIncSeq(int arr[], int n) {
            // lioes[i] stores longest increasing odd
            // even subsequence ending at arr[i]
            int[] lioes = new int[n];

            // to store the length of longest
            // increasing odd even subsequence
            int maxLen = 0;

            // Initialize LIOES values for all indexes
            for (int i = 0; i < n; i++)
                lioes[i] = 1;

            // Compute optimized LIOES values
            // in bottom up manner
            for (int i = 1; i < n; i++)
                for (int j = 0; j < i; j++)
                    if (arr[i] > arr[j] && (arr[i] + arr[j]) % 2 != 0 && lioes[i] < lioes[j] + 1)
                        lioes[i] = lioes[j] + 1;

            // Pick maximum of all LIOES values
            for (int i = 0; i < n; i++)
                if (maxLen < lioes[i])
                    maxLen = lioes[i];

            // required maximum length
            return maxLen;
        }

        // driver function
        public static void main(String argc[]) {
            int[] arr = new int[] { 1, 12, 2, 22, 5, 30, 31, 14, 17, 11 };
            int n = 10;
            System.out.println("Longest Increasing Odd" + " Even Subsequence: " + longOddEvenIncSeq(arr, n));
        }
    }

    static class CountNumberOfIncreasingSubsequencesOfSizeK {
        /**
         * Given an array arr[] containing n integers. The problem is to count number of increasing subsequences in the array of size k.
            Examples: 
            

            Input : arr[] = {2, 6, 4, 5, 7}, 
                        k = 3
            Output : 5
            The subsequences of size '3' are:
            {2, 6, 7}, {2, 4, 5}, {2, 4, 7},
            {2, 5, 7} and {4, 5, 7}.

            Input : arr[] = {12, 8, 11, 13, 10, 15, 14, 16, 20}, 
                        k = 4
            Output : 39
         */
        /**
         * Approach: The idea is to use Dynamic Programming by define 2D matrix, say
         * dp[][]. dp[i][j] stores the count of increasing subsequences of size i ending
         * with element arr[j]. So dp[i][j] can be defined as:
         * 
         * 
         * dp[i][j] = 1, where i = 1 and 1 <= j <= n 
         * dp[i][j] = sum(dp[i-1][j]), where 1< i <= k, i <= j <= n and arr[m] < arr[j] for (i-1) <= m < j.
         * 
         * Below is the implementation of above approach:
         */
        // function to count number of increasing
        // subsequences of size k
        static int numOfIncSubseqOfSizeK(int arr[], int n, int k) {
            int dp[][] = new int[k][n], sum = 0;

            // count of increasing subsequences of size 1
            // ending at each arr[i]
            for (int i = 0; i < n; i++) {
                dp[0][i] = 1;
            }

            // building up the matrix dp[][]
            // Here 'l' signifies the size of
            // increassing subsequence of size (l+1).
            for (int l = 1; l < k; l++) {

                // for each increasing subsequence of size 'l'
                // ending with element arr[i]
                for (int i = l; i < n; i++) {

                    // count of increasing subsequences of size 'l'
                    // ending with element arr[i]
                    dp[l][i] = 0;
                    for (int j = l - 1; j < i; j++) {
                        if (arr[j] < arr[i]) {
                            dp[l][i] += dp[l - 1][j];
                        }
                    }
                }
            }

            // sum up the count of increasing subsequences of
            // size 'k' ending at each element arr[i]
            for (int i = k - 1; i < n; i++) {
                sum += dp[k - 1][i];
            }

            // required number of increasing
            // subsequences of size k
            return sum;
        }

        // Driver program to test above
        public static void main(String[] args) {
            int arr[] = { 12, 8, 11, 13, 10, 15, 14, 16, 20 };
            int n = arr.length;
            int k = 4;

            System.out
                    .print("Number of Increasing Subsequences of size " + k + " = " + numOfIncSubseqOfSizeK(arr, n, k));

        }
    }

    static class PrintLongestIncreasingConsecutiveSubsequenceWithAdjacentDifferenceAsOne {
        /**
         * Given n elements, write a program that prints the longest increasing
         * subsequence whose adjacent element difference is one. Examples:
         * 
         * 
         * Input : a[] = {3, 10, 3, 11, 4, 5, 6, 7, 8, 12} Output : 3 4 5 6 7 8
         * Explanation: 3, 4, 5, 6, 7, 8 is the longest increasing subsequence whose
         * adjacent element differs by one. Input : a[] = {6, 7, 8, 3, 4, 5, 9, 10}
         * Output : 6 7 8 9 10 Explanation: 6, 7, 8, 9, 10 is the longest increasing
         * subsequence
         * 
         * We have discussed how to find length of Longest Increasing consecutive
         * subsequence. To print the subsequence, we store index of last element. Then
         * we print consecutive elements ending with last element. Given below is the
         * implementation of the above approach:
         * 
         */
        // function that returns the length of the
        // longest increasing subsequence
        // whose adjacent element differ by 1
        public static void longestSubsequence(int[] a, int n) {

            // stores the index of elements
            HashMap<Integer, Integer> mp = new HashMap<>();

            // stores the length of the longest
            // subsequence that ends with a[i]
            int[] dp = new int[n];

            int maximum = Integer.MIN_VALUE;

            // iterate for all element
            int index = -1;
            for (int i = 0; i < n; i++) {

                // if a[i]-1 is present before i-th index
                if (mp.get(a[i] - 1) != null) {

                    // last index of a[i]-1
                    int lastIndex = mp.get(a[i] - 1) - 1;

                    // relation
                    dp[i] = 1 + dp[lastIndex];
                } else
                    dp[i] = 1;

                // stores the index as 1-index as we need to
                // check for occurrence, hence 0-th index
                // will not be possible to check
                mp.put(a[i], i + 1);

                // stores the longest length
                if (maximum < dp[i]) {
                    maximum = dp[i];
                    index = i;
                }
            }

            // We know last element of sequence is
            // a[index]. We also know that length
            // of subsequence is "maximum". So We
            // print these many consecutive elements
            // starting from "a[index] - maximum + 1"
            // to a[index].
            for (int curr = a[index] - maximum + 1; curr <= a[index]; curr++)
                System.out.print(curr + " ");
        }

        // Driver Code
        public static void main(String[] args) {
            int[] a = { 3, 10, 3, 11, 4, 5, 6, 7, 8, 12 };
            int n = a.length;
            longestSubsequence(a, n);
        }
    }

    static class PrintLongestIncreasingSubsequence {
        /**
         * The Longest Increasing Subsequence (LIS) problem is to find the length of the
         * longest subsequence of a given sequence such that all elements of the
         * subsequence are sorted in increasing order. For example, the length of LIS
         * for {10, 22, 9, 33, 21, 50, 41, 60, 80} is 6 and LIS is {10, 22, 33, 50, 60,
         * 80}.
         * 
         * The longest Increasing Subsequence (LIS) problem is to find the length of the
         * longest subsequence of a given sequence such that all elements of the
         * subsequence are sorted in increasing order. Examples:
         * 
         * 
         * Input: [10, 22, 9, 33, 21, 50, 41, 60, 80] Output: [10, 22, 33, 50, 60, 80]
         * OR [10 22 33 41 60 80] or any other LIS of same length.
         * 
         * In the previous post, we have discussed The Longest Increasing Subsequence problem. 
         * However, the post only covered code related to the querying size of LIS, but not the construction of LIS. 
         * In this post, we will discuss how to print LIS using a similar DP solution discussed earlier.
            Let arr[0..n-1] be the input array. We define vector L such that L[i] is itself is a vector that stores LIS of arr that ends with arr[i]. For example, for array [3, 2, 6, 4, 5, 1], 

            L[0]: 3
            L[1]: 2
            L[2]: 2 6
            L[3]: 2 4
            L[4]: 2 4 5
            L[5]: 1

            Therefore, for index i, L[i] can be recursively written as – 

            L[0] = {arr[O]}
            L[i] = {Max(L[j])} + arr[i] 
            where j < i and arr[j] < arr[i] and if there is no such j then L[i] = arr[i]

            Below is the implementation of the above idea – 
         */
        // Utility function to print LIS
        static void printLIS(Vector<Integer> arr) {
            for (int x : arr)
                System.out.print(x + " ");
            System.out.println();
        }

        // Function to conand print
        // Longest Increasing Subsequence
        static void constructPrintLIS(int arr[], int n) {
            // L[i] - The longest increasing
            // sub-sequence ends with arr[i]
            Vector<Integer> L[] = new Vector[n];
            for (int i = 0; i < L.length; i++)
                L[i] = new Vector<Integer>();

            // L[0] is equal to arr[0]
            L[0].add(arr[0]);

            // Start from index 1
            for (int i = 1; i < n; i++) {
                // Do for every j less than i
                for (int j = 0; j < i; j++) {
                    // L[i] = {Max(L[j])} + arr[i]
                    // where j < i and arr[j] < arr[i]
                    if ((arr[i] > arr[j]) && (L[i].size() < L[j].size() + 1))
                        L[i] = L[j];
                }

                // L[i] ends with arr[i]
                L[i].add(arr[i]);
            }

            // L[i] now stores increasing sub-sequence of
            // arr[0..i] that ends with arr[i]
            Vector<Integer> max = L[0];

            // LIS will be max of all increasing sub-
            // sequences of arr
            for (Vector<Integer> x : L)
                if (x.size() > max.size())
                    max = x;

            // max will contain LIS
            printLIS(max);
        }

        // Driver function
        public static void main(String[] args) {
            int arr[] = { 3, 2, 4, 5, 1 };
            int n = arr.length;

            // print LIS of arr
            constructPrintLIS(arr, n);
        }
    }

    static class LongestZigZagSubsequence_AlternatingSubsequence {
        /**
         * The longest Zig-Zag subsequence problem is to find length of the longest subsequence of given sequence such that all elements of this are alternating. 
            If a sequence {x1, x2, .. xn} is alternating sequence then its element satisfy one of the following relation : 

            x1 < x2 > x3 < x4 > x5 < …. xn or 
            x1 > x2 < x3 > x4 < x5 > …. xn 

            Examples :

            Input: arr[] = {1, 5, 4}
            Output: 3
            The whole arrays is of the form  x1 < x2 > x3 

            Input: arr[] = {1, 4, 5}
            Output: 2
            All subsequences of length 2 are either of the form 
            x1 < x2; or x1 > x2

            Input: arr[] = {10, 22, 9, 33, 49, 50, 31, 60}
            Output: 6
            The subsequences {10, 22, 9, 33, 31, 60} or
            {10, 22, 9, 49, 31, 60} or {10, 22, 9, 50, 31, 60}
            are longest Zig-Zag of length 6.

            This problem is an extension of longest increasing subsequence problem, but requires more thinking for finding optimal substructure property in this.
            We will solve this problem by dynamic Programming method, Let A is given array of length n of integers. 
            We define a 2D array Z[n][2] such that Z[i][0] contains longest Zig-Zag subsequence ending at index i and last element is greater 
            than its previous element and Z[i][1] contains longest Zig-Zag subsequence ending at index i and last element is smaller than its previous element, 
            then we have following recurrence relation between them, 

            Z[i][0] = Length of the longest Zig-Zag subsequence 
                    ending at index i and last element is greater
                    than its previous element
            Z[i][1] = Length of the longest Zig-Zag subsequence 
                    ending at index i and last element is smaller
                    than its previous element

            Recursive Formulation:
            Z[i][0] = max (Z[i][0], Z[j][1] + 1); 
                        for all j < i and A[j] < A[i] 
            Z[i][1] = max (Z[i][1], Z[j][0] + 1); 
                        for all j < i and A[j] > A[i]

            The first recurrence relation is based on the fact that, If we are at position i and this element has to bigger than 
            its previous element then for this sequence (upto i) to be bigger we will try to choose an element j ( < i) 
            such that A[j] < A[i] i.e. A[j] can become A[i]’s previous element and Z[j][1] + 1 is bigger than Z[i][0] then we will update Z[i][0]. 
            Remember we have chosen Z[j][1] + 1 not Z[j][0] + 1 to satisfy alternate property because in Z[j][0] last element is bigger than 
            its previous one and A[i] is greater than A[j] which will break the alternating property if we update. So above fact derives first recurrence relation, 
            similar argument can be made for second recurrence relation also. 
         */

        // Function to return longest
        // Zig-Zag subsequence length
        static int zzis(int arr[], int n) {
            /*
             * Z[i][0] = Length of the longest Zig-Zag subsequence ending at index i and
             * last element is greater than its previous element Z[i][1] = Length of the
             * longest Zig-Zag subsequence ending at index i and last element is smaller
             * than its previous element
             */
            int Z[][] = new int[n][2];

            /* Initialize all values from 1 */
            for (int i = 0; i < n; i++)
                Z[i][0] = Z[i][1] = 1;

            int res = 1; // Initialize result

            /* Compute values in bottom up manner */
            for (int i = 1; i < n; i++) {
                // Consider all elements as
                // previous of arr[i]
                for (int j = 0; j < i; j++) {
                    // If arr[i] is greater, then
                    // check with Z[j][1]
                    if (arr[j] < arr[i] && Z[i][0] < Z[j][1] + 1)
                        Z[i][0] = Z[j][1] + 1;

                    // If arr[i] is smaller, then
                    // check with Z[j][0]
                    if (arr[j] > arr[i] && Z[i][1] < Z[j][0] + 1)
                        Z[i][1] = Z[j][0] + 1;
                }

                /*
                 * Pick maximum of both values at index i
                 */
                if (res < Math.max(Z[i][0], Z[i][1]))
                    res = Math.max(Z[i][0], Z[i][1]);
            }

            return res;
        }

        /* Driver program */
        public static void main(String[] args) {
            int arr[] = { 10, 22, 9, 33, 49, 50, 31, 60 };
            int n = arr.length;
            System.out.println("Length of Longest " + "Zig-Zag subsequence is " + zzis(arr, n));
        }
    }

    static class LargestSumZigZagSequenceInaMatrix {
        /**
         * Given a matrix of size n x n, find the sum of the Zigzag sequence with the largest sum. A zigzag sequence starts from the top and ends at the bottom. Two consecutive elements of sequence cannot belong to the same column. 

        Examples: 

        Input : mat[][] = 3  1  2
                        4  8  5
                        6  9  7
        Output : 18
        Zigzag sequence is: 3->8->7
        Another such sequence is 2->4->7

        Input : mat[][] =  4  2  1
                        3  9  6
                        11  3 15
        Output : 28

        This problem has an Optimal Substructure. 

        Maximum Zigzag sum starting from arr[i][j] to a 
        bottom cell can be written as :
        zzs(i, j) = arr[i][j] + max(zzs(i+1, k)), 
                    where k = 0, 1, 2 and k != j
        zzs(i, j) = arr[i][j], if i = n-1 

        We have to find the largest among all as
        Result = zzs(0, j) where 0 <= j < n
         */
        static int MAX = 100;

        // Returns largest sum of a Zigzag
        // sequence starting from (i, j)
        // and ending at a bottom cell.
        static int largestZigZagSumRec(int mat[][], int i, int j, int n) {

            // If we have reached bottom
            if (i == n - 1)
                return mat[i][j];

            // Find the largest sum by considering all
            // possible next elements in sequence.
            int zzs = 0;

            for (int k = 0; k < n; k++)
                if (k != j)
                    zzs = Math.max(zzs, largestZigZagSumRec(mat, i + 1, k, n));

            return zzs + mat[i][j];
        }

        // Returns largest possible sum of a Zizag
        // sequence starting from top and ending
        // at bottom.
        static int largestZigZag(int mat[][], int n) {
            // Consider all cells of top row as starting
            // point
            int res = 0;
            for (int j = 0; j < n; j++)
                res = Math.max(res, largestZigZagSumRec(mat, 0, j, n));

            return res;
        }

        // Driver program to test above
        public static void main(String[] args) {
            int n = 3;

            int mat[][] = { { 4, 2, 1 }, { 3, 9, 6 }, { 11, 3, 15 } };
            System.out.println("Largest zigzag sum: " + largestZigZag(mat, n));
        }
    }

    static class FindAllDistinctSubsetOrSubsequenceSumsOfanArray {
        /**
         * Given a set of integers, find a distinct sum that can be generated from the subsets of the given sets and print them in increasing order. It is given that sum of array elements is small.

            Examples:  

            Input  : arr[] = {1, 2, 3}
            Output : 0 1 2 3 4 5 6
            Distinct subsets of given set are
            {}, {1}, {2}, {3}, {1,2}, {2,3}, 
            {1,3} and {1,2,3}.  Sums of these
            subsets are 0, 1, 2, 3, 3, 5, 4, 6
            After removing duplicates, we get
            0, 1, 2, 3, 4, 5, 6  

            Input : arr[] = {2, 3, 4, 5, 6}
            Output : 0 2 3 4 5 6 7 8 9 10 11 12 
                    13 14 15 16 17 18 20

            Input : arr[] = {20, 30, 50}
            Output : 0 20 30 50 70 80 100

            The time complexity of the above problem can be improved using Dynamic Programming, 
            especially when the sum of given elements is small. We can make a dp table with rows 
            containing the size of the array and the size of the column will be the sum of all the elements in the array.  
         */
        // Uses Dynamic Programming to
        // find distinct subset sums
        static void printDistSum(int arr[], int n) {
            int sum = 0;
            for (int i = 0; i < n; i++)
                sum += arr[i];

            // dp[i][j] would be true if arr[0..i-1]
            // has a subset with sum equal to j.
            boolean[][] dp = new boolean[n + 1][sum + 1];

            // There is always a subset with 0 sum
            for (int i = 0; i <= n; i++)
                dp[i][0] = true;

            // Fill dp[][] in bottom up manner
            for (int i = 1; i <= n; i++) {
                dp[i][arr[i - 1]] = true;
                for (int j = 1; j <= sum; j++) {
                    // Sums that were achievable
                    // without current array element
                    if (dp[i - 1][j] == true) {
                        dp[i][j] = true;
                        dp[i][j + arr[i - 1]] = true;
                    }
                }
            }

            // Print last row elements
            for (int j = 0; j <= sum; j++)
                if (dp[n][j] == true)
                    System.out.print(j + " ");
        }

        // Driver code
        public static void main(String[] args) {
            int arr[] = { 2, 3, 4, 5, 6 };
            int n = arr.length;
            printDistSum(arr, n);
        }
    }

    static class PrintAllLongestCommonSubsequencesInLexicographicalOrder {
        /**
         * You are given two strings.Now you have to print all longest common
         * sub-sequences in lexicographical order? Examples:
         * 
         * 
         * Input : str1 = "abcabcaa", str2 = "acbacba" Output: ababa abaca abcba acaba
         * acaca acbaa acbca
         * 
         * This problem is an extension of longest common subsequence. We first find
         * length of LCS and store all LCS in 2D table using Memoization (or Dynamic
         * Programming). Then we search all characters from ‘a’ to ‘z’ (to output sorted
         * order) in both strings. If a character is found in both strings and current
         * positions of character lead to LCS, we recursively search all occurrences
         * with current LCS length plus 1. Below is the implementation of algorithm.
         */
        static int MAX = 100;

        // length of lcs
        static int lcslen = 0;

        // dp matrix to store result of sub calls for lcs
        static int[][] dp = new int[MAX][MAX];

        // A memoization based function that returns LCS of
        // str1[i..len1-1] and str2[j..len2-1]
        static int lcs(String str1, String str2, int len1, int len2, int i, int j) {
            int ret = dp[i][j];

            // base condition
            if (i == len1 || j == len2)
                return ret = 0;

            // if lcs has been computed
            if (ret != -1)
                return ret;
            ret = 0;

            // if characters are same return previous + 1 else
            // max of two sequences after removing i'th and j'th
            // char one by one
            if (str1.charAt(i) == str2.charAt(j))
                ret = 1 + lcs(str1, str2, len1, len2, i + 1, j + 1);
            else
                ret = Math.max(lcs(str1, str2, len1, len2, i + 1, j), lcs(str1, str2, len1, len2, i, j + 1));
            return ret;
        }

        // Function to print all routes common sub-sequences of
        // length lcslen
        static void printAll(String str1, String str2, int len1, int len2, char[] data, int indx1, int indx2,
                int currlcs) {

            // if currlcs is equal to lcslen then print it
            if (currlcs == lcslen) {
                data[currlcs] = '\0';
                System.out.println(new String(data));
                return;
            }

            // if we are done with all the characters of both string
            if (indx1 == len1 || indx2 == len2)
                return;

            // here we have to print all sub-sequences lexicographically,
            // that's why we start from 'a'to'z' if this character is
            // present in both of them then append it in data[] and same
            // remaining part
            for (char ch = 'a'; ch <= 'z'; ch++) {

                // done is a flag to tell that we have printed all the
                // subsequences corresponding to current character
                boolean done = false;

                for (int i = indx1; i < len1; i++) {

                    // if character ch is present in str1 then check if
                    // it is present in str2
                    if (ch == str1.charAt(i)) {
                        for (int j = indx2; j < len2; j++) {

                            // if ch is present in both of them and
                            // remaining length is equal to remaining
                            // lcs length then add ch in sub-sequenece
                            if (ch == str2.charAt(j) && lcs(str1, str2, len1, len2, i, j) == lcslen - currlcs) {
                                data[currlcs] = ch;
                                printAll(str1, str2, len1, len2, data, i + 1, j + 1, currlcs + 1);
                                done = true;
                                break;
                            }
                        }
                    }

                    // If we found LCS beginning with current character.
                    if (done)
                        break;
                }
            }
        }

        // This function prints all LCS of str1 and str2
        // in lexicographic order.
        static void prinlAllLCSSorted(String str1, String str2) {

            // Find lengths of both strings
            int len1 = str1.length(), len2 = str2.length();

            // Find length of LCS
            for (int i = 0; i < MAX; i++) {
                for (int j = 0; j < MAX; j++) {
                    dp[i][j] = -1;
                }
            }
            lcslen = lcs(str1, str2, len1, len2, 0, 0);

            // Print all LCS using recursive backtracking
            // data[] is used to store individual LCS.
            char[] data = new char[MAX];
            printAll(str1, str2, len1, len2, data, 0, 0, 0);
        }

        // Driver code
        public static void main(String[] args) {
            String str1 = "abcabcaa", str2 = "acbacba";
            prinlAllLCSSorted(str1, str2);
        }
    }

    static class PrintLongestCommonSubsequence {
        /**
         * Given two sequences, print all longest subsequence present in both of them.
            Examples: 
            

            Input: 
            string X = "AGTGATG"
            string Y = "GTTAG"
            Output: 
            GTAG
            GTTG

            Input: 
            string X = "AATCC"
            string Y = "ACACG"
            Output:  
            ACC
            AAC

            Input: 
            string X = "ABCBDAB"
            string Y = "BDCABA"
            Output:  
            BCAB
            BCBA
            BDAB
         */
        /**
         * We have discussed Longest Common Subsequence (LCS) problem here. The function
         * discussed there was mainly to find the length of LCS. We have also discussed
         * how to print the longest subsequence here. But as LCS for two strings is not
         * unique, in this post we will print out all the possible solutions to LCS
         * problem.
         * 
         * Following is detailed algorithm to print the all LCS. We construct
         * L[m+1][n+1] table as discussed in the previous post and traverse the 2D array
         * starting from L[m][n]. For current cell L[i][j] in the matrix,
         * 
         * 
         * a) If the last characters of X and Y are same (i.e. X[i-1] == Y[j-1]), then
         * the character must be present in all LCS of substring X[0…i-1] and Y[0..j-1].
         * We simply recurse for L[i-1][j-1] in the matrix and append current character
         * to all LCS possible of substring X[0…i-2] and Y[0..j-2].
         * 
         * b) If the last characters of X and Y are not same (i.e. X[i-1] != Y[j-1]),
         * then LCS can be constructed from either top side of the matrix (i.e.
         * L[i-1][j]) or from left side of matrix (i.e. L[i][j-1]) depending upon which
         * value is greater. If both the values are equal(i.e. L[i-1][j] == L[i][j-1]),
         * then it will be constructed from both sides of matrix. So based on values at
         * L[i-1][j] and L[i][j-1], we go in direction of greater value or go in both
         * directions if the values are equal. Below is recursive implementation of
         * above idea –
         */
        static int N = 100;

        static int[][] L = new int[N][N];

        /*
         * Returns set containing all LCS for X[0..m-1], Y[0..n-1]
         */
        static Set<String> findLCS(String X, String Y, int m, int n) {
            // construct a set to store possible LCS
            Set<String> s = new HashSet<>();

            // If we reaches end of either String,
            // return a empty set
            if (m == 0 || n == 0) {
                s.add("");
                return s;
            }

            // If the last characters of X and Y are same
            if (X.charAt(m - 1) == Y.charAt(n - 1)) {
                // recurse for X[0..m-2] and Y[0..n-2]
                // in the matrix
                Set<String> tmp = findLCS(X, Y, m - 1, n - 1);

                // append current character to all possible LCS
                // of subString X[0..m-2] and Y[0..n-2].
                for (String str : tmp)
                    s.add(str + X.charAt(m - 1));
            }

            // If the last characters of X and Y are not same
            else {
                // If LCS can be constructed from top side of
                // the matrix, recurse for X[0..m-2] and Y[0..n-1]
                if (L[m - 1][n] >= L[m][n - 1])
                    s = findLCS(X, Y, m - 1, n);

                // If LCS can be constructed from left side of
                // the matrix, recurse for X[0..m-1] and Y[0..n-2]
                if (L[m][n - 1] >= L[m - 1][n]) {
                    Set<String> tmp = findLCS(X, Y, m, n - 1);

                    // merge two sets if L[m-1][n] == L[m][n-1]
                    // Note s will be empty if L[m-1][n] != L[m][n-1]
                    s.addAll(tmp);
                }
            }
            return s;
        }

        /* Returns length of LCS for X[0..m-1], Y[0..n-1] */
        static int LCS(String X, String Y, int m, int n) {
            // Build L[m+1][n+1] in bottom up fashion
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0 || j == 0)
                        L[i][j] = 0;
                    else if (X.charAt(i - 1) == Y.charAt(j - 1))
                        L[i][j] = L[i - 1][j - 1] + 1;
                    else
                        L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
            return L[m][n];
        }

        // Driver Code
        public static void main(String[] args) {
            String X = "AGTGATG";
            String Y = "GTTAG";
            int m = X.length();
            int n = Y.length();

            System.out.println("LCS length is " + LCS(X, Y, m, n));

            Set<String> s = findLCS(X, Y, m, n);

            for (String str : s)
                System.out.println(str);
        }
    }

    static class FindLengthOfLongestBalancedSubsequence {
        /**
         * Given a string S, find the length of the longest balanced subsequence in it. A balanced string is defined as:- 

                A null string is a balanced string.
                If X and Y are balanced strings, then (X)Y and XY are balanced strings.

            Examples: 

            Input : S = "()())"
            Output : 4

            ()() is the longest balanced subsequence 
            of length 4.

            Input : s = "()(((((()"
            Output : 4
         */
        /**
         * Approach 1: A brute force approach is to find all subsequences of the given string S and check for all possible subsequences if they form a balanced sequence. 
         * If yes, compare it with the maximum value.
            The better approach is to use Dynamic Programming. 
            Longest Balanced Subsequence (LBS), can be recursively defined as below. 

            LBS of substring str[i..j] : 
            If str[i] == str[j]
                LBS(str, i, j) = LBS(str, i + 1, j - 1) + 2
            Else
                LBS(str, i, j) = max(LBS(str, i, k) +
                                    LBS(str, k + 1, j))
                                Where i <= k < j   

            Declare a 2D matrix dp[][], where our state dp[i][j] will denote the length of the longest balanced subsequence from index i to j. 
            We will compute this state in order of increasing j – i. For a particular state dp[i][j], we will try to match the jth symbol with the kth symbol. 
            That can be done only if S[k] is ‘(‘ and S[j] is ‘)’. We will take the max of 2 + dp[i][k – 1] + dp[k + 1][j – 1] for all such possible k and also max(dp[i + 1][j], dp[i][j – 1]) and put the value in dp[i][j]. 
            In this way, we can fill out all the dp states. dp[0][length of string – 1] (considering 0 indexing) will be our answer.
            Below is the implementation of this approach:  
         */
        static int maxLength(String s, int n) {
            int dp[][] = new int[n][n];

            // Considering all balanced substrings
            // of length 2
            for (int i = 0; i < n - 1; i++)
                if (s.charAt(i) == '(' && s.charAt(i + 1) == ')')
                    dp[i][i + 1] = 2;

            // Considering all other substrings
            for (int l = 2; l < n; l++) {
                for (int i = 0, j = l; j < n; i++, j++) {
                    if (s.charAt(i) == '(' && s.charAt(j) == ')')
                        dp[i][j] = 2 + dp[i + 1][j - 1];

                    for (int k = i; k < j; k++)
                        dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k + 1][j]);
                }
            }

            return dp[0][n - 1];
        }

        // Driver Code
        public static void main(String[] args) {
            String s = "()(((((()";
            int n = s.length();
            System.out.println(maxLength(s, n));
        }
    }

    static class FindNonDecreasingOrIncreasingSubsequenceOfSizeK_withMinimumSum {
        /**
         * Given a sequence of n integers, you have to find out the non-decreasing subsequence of length k with minimum sum. If no sequence exists output -1.
            Examples : 
            

            Input : [58 12 11 12 82 30 20 77 16 86], 
                    k = 3
            Output : 39
            {11 + 12 + 16}

            Input : [58 12 11 12 82 30 20 77 16 86], 
                    k = 4
            Output : 120
            {11 + 12 + 20 + 77}

            Input : [58 12 11 12 82 30 20 77 16 86], 
                    k = 5
            Output : 206
         */
        /**
         * Let solve(i, k) be the minimum sum of a subsequence of size k ending at index i. Then there would be two states: 
            1. Include current element. {solve(j, k-1) + a[i]} 
            2. Exclude current element. {solve(j, k)} 
            Our recurrence state would be: 

            
            dp[i][k] = min(solve(j, k-1) + a[i], solve(j, k)) 
            if a[i] >= a[j] for all 0 <= j <= i.
         */
        public static int MAX = 100;
        public static int inf = 1000000;

        // Table used for memoization
        public static int[][] dp = new int[MAX][MAX];

        // initialize
        static void initialize() {
            for (int i = 0; i < MAX; i++)
                for (int j = 0; j < MAX; j++)
                    dp[i][j] = -1;
        }

        // Function to find non-decreasing sequence
        // of size k with minimum sum
        static int solve(int arr[], int i, int k) {
            // If already computed
            if (dp[i][k] != -1)
                return dp[i][k];

            // Corner cases
            if (i < 0)
                return inf;
            if (k == 1) {
                int ans = inf;
                for (int j = 0; j <= i; j++)
                    ans = Math.min(ans, arr[j]);
                return ans;
            }

            // Recursive computation
            int ans = inf;
            for (int j = 0; j < i; j++)
                if (arr[i] >= arr[j])
                    ans = Math.min(ans, Math.min(solve(arr, j, k), solve(arr, j, k - 1) + arr[i]));
                else
                    ans = Math.min(ans, solve(arr, j, k));

            dp[i][k] = ans;

            return dp[i][k];
        }

        // driver program
        public static void main(String[] args) {
            initialize();
            int a[] = { 58, 12, 11, 12, 82, 30, 20, 77, 16, 86 };
            int n = a.length;
            int k = 4;
            System.out.println(solve(a, n - 1, k));
        }
    }

    static class LongestCommonSubsequenceWithAtmostK_changesAllowed {
        /**
         * Given two sequence P and Q of numbers. The task is to find Longest Common Subsequence of two sequences if we are allowed to change at most k element in first sequence to any value.
        Examples: 

        Input : P = { 8, 3 }
                Q = { 1, 3 }
                K = 1
        Output : 2
        If we change first element of first
        sequence from 8 to 1, both sequences 
        become same.

        Input : P = { 1, 2, 3, 4, 5 }
                Q = { 5, 3, 1, 4, 2 }
                K = 1
        Output : 3
        By changing first element of first
        sequence to 5 to get the LCS ( 5, 3, 4 }.
         */
        /**
         * The idea is to use Dynamic Programming. Define a 3D matrix dp[][][], where dp[i][j][k] defines the Longest Common Subsequence for the first i numbers of first array, 
         * first j number of second array when we are allowed to change at max k number in the first array. 
            Therefore, recursion will look like 

            If P[i] != Q[j], 
                dp[i][j][k] = max(dp[i - 1][j][k], 
                                dp[i][j - 1][k], 
                                dp[i - 1][j - 1][k - 1] + 1) 
            If P[i] == Q[j], 
                dp[i][j][k] = max(dp[i - 1][j][k], 
                                dp[i][j - 1][k], 
                                dp[i - 1][j - 1][k] + 1)

            Below is the implementation of this approach:
         */
        static int MAX = 10;

        // Return LCS with at most k changes allowed.
        static int lcs(int[][][] dp, int[] arr1, int n, int[] arr2, int m, int k) {

            // If at most changes is less than 0.
            if (k < 0)
                return -10000000;

            // If any of two array is over.
            if (n < 0 || m < 0)
                return 0;

            // Making a reference variable to dp[n][m][k]
            int ans = dp[n][m][k];

            // If value is already calculated, return
            // that value.
            if (ans != -1)
                return ans;

            try {

                // calculating LCS with no changes made.
                ans = Math.max(lcs(dp, arr1, n - 1, arr2, m, k), lcs(dp, arr1, n, arr2, m - 1, k));

                // calculating LCS when array element are same.
                if (arr1[n - 1] == arr2[m - 1])
                    ans = Math.max(ans, 1 + lcs(dp, arr1, n - 1, arr2, m - 1, k));

                // calculating LCS with changes made.
                ans = Math.max(ans, 1 + lcs(dp, arr1, n - 1, arr2, m - 1, k - 1));
            } catch (Exception e) {
            }
            return ans;
        }

        // Driver Code
        public static void main(String[] args) {
            int k = 1;
            int[] arr1 = { 1, 2, 3, 4, 5 };
            int[] arr2 = { 5, 3, 1, 4, 2 };
            int n = arr1.length;
            int m = arr2.length;

            int[][][] dp = new int[MAX][MAX][MAX];
            for (int i = 0; i < MAX; i++)
                for (int j = 0; j < MAX; j++)
                    for (int l = 0; l < MAX; l++)
                        dp[i][j][l] = -1;

            System.out.println(lcs(dp, arr1, n, arr2, m, k));
        }
    }

    static class FindNumberOfPathsToReachDestinationInMatrix_with_ExactlyKcoins {
        /** Given a matrix where every cell has some number of coins. Count number of ways to reach bottom right from top left with exactly k coins. 
         * We can move to (i+1, j) and (i, j+1) from a cell (i, j).

        Example: 

        Input:  k = 12
                mat[][] = { {1, 2, 3},
                            {4, 6, 5},
                            {3, 2, 1}
                        };
        Output:  2
        There are two paths with 12 coins
        1 -> 2 -> 6 -> 2 -> 1
        1 -> 2 -> 3 -> 5 -> 1*/
        /**
         * The above problem can be recursively defined as below:  

            pathCount(m, n, k):   Number of paths to reach mat[m][n] from mat[0][0] 
                                with exactly k coins

            If (m == 0 and n == 0)
            return 1 if mat[0][0] == k else return 0
            Else:
                pathCount(m, n, k) = pathCount(m-1, n, k - mat[m][n]) + 
                                    pathCount(m, n-1, k - mat[m][n]) 

            Below is the implementation of above recursive algorithm. 
         */
        static final int R = 3;
        static final int C = 3;

        // Recursive function to count paths with sum k from
        // (0, 0) to (m, n)
        static int pathCountRec(int mat[][], int m, int n, int k) {
            // Base cases
            if (m < 0 || n < 0) {
                return 0;
            }
            if (m == 0 && n == 0 && (k == mat[m][n])) {
                return 1;
            }

            // (m, n) can be reached either through (m-1, n) or
            // through (m, n-1)
            return pathCountRec(mat, m - 1, n, k - mat[m][n]) + pathCountRec(mat, m, n - 1, k - mat[m][n]);
        }

        // A wrapper over pathCountRec()
        static int pathCount(int mat[][], int k) {
            return pathCountRec(mat, R - 1, C - 1, k);
        }

        // Driver code
        public static void main(String[] args) {
            int k = 12;
            int mat[][] = { { 1, 2, 3 }, { 4, 6, 5 }, { 3, 2, 1 } };
            System.out.println(pathCount(mat, k));
        }
    }

    static class CollectMaximumCoinsBeforeHittingDeadEnd {
        /**
         * Given a character matrix where every cell has one of the following values.

            'C' -->  This cell has coin

            '#' -->  This cell is a blocking cell. 
                    We can not go anywhere from this.

            'E' -->  This cell is empty. We don't get
                    a coin, but we can move from here.  

            Initial position is cell (0, 0) and initial direction is right.

            Following are rules for movements across cells.

            If face is Right, then we can move to below cells

                Move one step ahead, i.e., cell (i, j+1) and direction remains right.
                Move one step down and face left, i.e., cell (i+1, j) and direction becomes left.

            If face is Left, then we can move to below cells
                Move one step ahead, i.e., cell (i, j-1) and direction remains left.
                Move one step down and face right, i.e., cell (i+1, j) and direction becomes right.

            Final position can be anywhere and final direction can also be anything. The target is to collect maximum coins. 

            The above problem can be recursively defined as below:

            maxCoins(i, j, d):  Maximum number of coins that can be 
                                collected if we begin at cell (i, j)
                                and direction d.
                                d can be either 0 (left) or 1 (right)

            // If this is a blocking cell, return 0. isValid() checks
            // if i and j are valid row and column indexes.
            If (arr[i][j] == '#' or isValid(i, j) == false)
                return 0

            // Initialize result
            If (arr[i][j] == 'C')
                result = 1;
            Else 
                result = 0;

            If (d == 0) // Left direction 
                return result +  max(maxCoins(i+1, j, 1),  // Down
                                        maxCoins(i, j-1, 0)); // Ahead in left

            If (d == 1) // Right direction 
                return result +  max(maxCoins(i+1, j, 1),  // Down
                                        maxCoins(i, j+1, 0)); // Ahead in right

            The idea is to use a 3 dimensional table dp[R][C][k] where R is number of rows, C is number of columns and d is direction. Below is Dynamic Programming based C++ implementation.
         */// to check whether current cell is out of the grid or not
        int R = 5, C = 5;

        boolean isValid(int i, int j) {
            return (i >= 0 && i < R && j >= 0 && j < C);
        }

        // dir = 0 for left, dir = 1 for right. This function returns
        // number of maximum coins that can be collected starting from
        // (i, j).
        int maxCoinsUtil(char arr[][], int i, int j, int dir, int dp[][][]) {
            // If this is a invalid cell or if cell is a blocking cell
            if (isValid(i, j) == false || arr[i][j] == '#')
                return 0;

            // If this subproblem is already solved than return the
            // already evaluated answer.
            if (dp[i][j][dir] != -1)
                return dp[i][j][dir];

            // Check if this cell contains the coin 'C' or if its 'E'.
            dp[i][j][dir] = (arr[i][j] == 'C') ? 1 : 0;

            // Get the maximum of two cases when you are facing right
            // in this cell
            if (dir == 1) // Direction is right
                dp[i][j][dir] += Math.max(maxCoinsUtil(arr, i + 1, j, 0, dp), // Down
                        maxCoinsUtil(arr, i, j + 1, 1, dp)); // Ahead in rught

            // Get the maximum of two cases when you are facing left
            // in this cell
            if (dir == 0) // Direction is left
                dp[i][j][dir] += Math.max(maxCoinsUtil(arr, i + 1, j, 1, dp), // Down
                        maxCoinsUtil(arr, i, j - 1, 0, dp)); // Ahead in left

            // return the answer
            return dp[i][j][dir];
        }

        // This function mainly creates a lookup table and calls
        // maxCoinsUtil()
        int maxCoins(char arr[][]) {
            // Create lookup table and initialize all values as -1
            int dp[][][] = new int[R][C][2];
            Arrays.fill(dp, -1);

            // As per the question initial cell is (0, 0) and direction
            // is right
            return maxCoinsUtil(arr, 0, 0, 1, dp);
        }

        // Driver program to test above function
        void main() {
            char arr[][] = new char[][] { { 'E', 'C', 'C', 'C', 'C' }, { 'C', '#', 'C', '#', 'E' },
                    { '#', 'C', 'C', '#', 'C' }, { 'C', 'E', 'E', 'C', 'E' }, { 'C', 'E', '#', 'C', 'E' } };

            System.out.println("Maximum number of collected coins is " + maxCoins(arr));
        }
    }

    static class FindWhoWillWinTheCoinGameWhereEveryPlayerHasThreeChoices {
        /**
         * A and B are playing a game. At the beginning there are n coins. Given two more numbers x and y. In each move a player can pick x or y or 1 coins. A always starts the game. The player who picks the last coin wins the game or the person who is not able to pick any coin loses the game. For a given value of n, find whether A will win the game or not if both are playing optimally.

            Examples: 

            Input :  n = 5, x = 3, y = 4
            Output : A
            There are 5 coins, every player can pick 1 or
            3 or 4 coins on his/her turn.
            A can win by picking 3 coins in first chance.
            Now 2 coins will be left so B will pick one 
            coin and now A can win by picking the last coin.

            Input : 2 3 4
            Output : B

            Let us take few example values of n for x = 3, y = 4. 
            n = 0 A can not pick any coin so he losses 
            n = 1 A can pick 1 coin and win the game 
            n = 2 A can pick only 1 coin. Now B will pick 1 coin and win the game 
            n = 3 4 A will win the game by picking 3 or 4 coins 
            n = 5, 6 A will choose 3 or 4 coins. Now B will have to choose from 2 coins so A will win.
            We can observe that A wins game for n coins only when B loses for coins n-1 or n-x or n-y. 
         */
          
        // To find winner of game
        static boolean findWinner(int x, int y, int n) {
            // To store results
            boolean[] dp = new boolean[n + 1];

            Arrays.fill(dp, false);

            // Initial values
            dp[0] = false;
            dp[1] = true;

            // Computing other values.
            for (int i = 2; i <= n; i++) {

                // If A losses any of i-1 or i-x
                // or i-y game then he will
                // definitely win game i
                if (i - 1 >= 0 && dp[i - 1] == false)
                    dp[i] = true;
                else if (i - x >= 0 && dp[i - x] == false)
                    dp[i] = true;
                else if (i - y >= 0 && dp[i - y] == false)
                    dp[i] = true;

                // Else A loses game.
                else
                    dp[i] = false;
            }

            // If dp[n] is true then A will
            // game otherwise he losses
            return dp[n];
        }

        // Driver program to test findWinner();
        public static void main(String args[]) {
            int x = 3, y = 4, n = 5;
            if (findWinner(x, y, n) == true)
                System.out.println('A');
            else
                System.out.println('B');
        }
    }

    static class ProbabilityOfGettingAtleastKheadsInNtossesOfCoins {
        /**
         * Given N number of coins, the task is to find probability of getting at least K number of heads after tossing all the N coins simultaneously.
        Example : 
        

        Suppose we have 3 unbiased coins and we have to
        find the probability of getting at least 2 heads,
        so there are 23 = 8 ways to toss these
        coins, i.e.,
        HHH, HHT, HTH, HTT, THH, THT, TTH, TTT 

        Out of which there are 4 set which contain at
        least 2 Heads i.e.,
        HHH, HHT, HH, THH

        So the probability is 4/8 or 0.5

        The probability of exactly k success in n trials with probability p of success in any trial is given by: 

        Method 1 (Naive) 
        A Naive approach is to store the value of factorial in dp[] array and call it directly whenever it is required. But the problem of this approach is that we can only able to store it up to certain value, after that it will lead to overflow.
        Below is the implementation of above approach 

        public static double fact[];
            
            // Returns probability of getting at least k
            // heads in n tosses.
            public static double probability(int k, int n)
            {
                double ans = 0;
                for (int i = k; i <= n; ++ i)
            
                    // Probability of getting exactly i
                    // heads out of n heads
                    ans += fact[n] / (fact[i] * fact[n-i]);
            
                // Note: 1 << n = pow(2, n)
                ans = ans / (1 << n);
                return ans;
            }
            
            public static void precompute()
            {
                // Preprocess all factorial only upto 19,
                // as after that it will overflow
                fact[0] = fact[1] = 1;
            
                for (int i = 2; i < 20; ++i)
                    fact[i] = fact[i - 1] * i;
            }

        Another way is to use Dynamic programming and logarithm. log() is indeed useful to store the factorial of any number without worrying about overflow. Let’s see how we use it:
         */
        /**
         * At first let see how n! can be written.
        n! = n * (n-1) * (n-2) * (n-3) * ... * 3 * 2 * 1

        Now take log on base 2 both the sides as:
        => log(n!) = log(n) + log(n-1) + log(n-2) + ... + log(3) 
                + log(2) + log(1)

        Now whenever we need to find the factorial of any number, we can use
        this precomputed value. For example:
        Suppose if we want to find the value of nCi which can be written as:
        => nCi = n! / (i! * (n-i)! )

        Taking log2() both sides as:
        => log2 (nCi) = log2 ( n! / (i! * (n-i)! ) )
        => log2 (nCi) = log2 ( n! ) - log2(i!) - log2( (n-i)! )  `

        Putting dp[num] = log2 (num!), we get:
        => log2 (nCi) = dp[n] - dp[i] - dp[n-i] 

        But as we see in above relation there is an extra factor of 2n which
        tells the probability of getting i heads, so
        => log2 (2n) = n.

        We will subtract this n from above result to get the final answer:
        => Pi (log2 (nCi)) = dp[n] - dp[i] - dp[n-i] - n

        Now: Pi (nCi) = 2 dp[n] - dp[i] - dp[n-i] - n

        Tada! Now the questions boils down the summation of Pi for all i in
        [k, n] will yield the answer which can be calculated easily without
        overflow.
         */

        static int MAX = 100001;

        // dp[i] is going to store Log ( i !) in base 2
        static double dp[] = new double[MAX];

        static double probability(int k, int n) {
            double ans = 0.0; // Initialize result

            // Iterate from k heads to n heads
            for (int i = k; i <= n; ++i) {
                double res = dp[n] - dp[i] - dp[n - i] - n;
                ans += Math.pow(2.0, res);
            }

            return ans;
        }

        static void precompute() {
            // Preprocess all the logarithm value on base 2
            for (int i = 2; i < MAX; ++i)
                dp[i] = (Math.log(i) / Math.log(2)) + dp[i - 1];
        }
     
        // Driver code
        public static void main(String args[]) {
            precompute();

            // Probability of getting 2 head out of 3 coins
            System.out.println(probability(2, 3));

            // Probability of getting 3 head out of 6 coins
            System.out.println(probability(3, 6));

            // Probability of getting 500 head out of 10000 coins
            System.out.println(probability(500, 1000));
        }
        
    }

    static class CountAllIncreasingSubsequences {
        /**
         * We are given an array of digits (values lie in range from 0 to 9). The task is to count all the sub sequences possible in array such that in each subsequence every digit is greater than its previous digits in the subsequence.

        Examples: 

        Input : arr[] = {1, 2, 3, 4}
        Output: 15
        There are total increasing subsequences
        {1}, {2}, {3}, {4}, {1,2}, {1,3}, {1,4}, 
        {2,3}, {2,4}, {3,4}, {1,2,3}, {1,2,4}, 
        {1,3,4}, {2,3,4}, {1,2,3,4}

        Input : arr[] = {4, 3, 6, 5}
        Output: 8
        Sub-sequences are {4}, {3}, {6}, {5}, 
        {4,6}, {4,5}, {3,6}, {3,5}

        Input : arr[] = {3, 2, 4, 5, 4}
        Output : 14
        Sub-sequences are {3}, {2}, {4}, {3,4},
        {2,4}, {5}, {3,5}, {2,5}, {4,5}, {3,2,5}
        {3,4,5}, {4}, {3,4}, {2,4}
         */
        /**
         * A Simple Solution is to use Dynamic Programming Solution of Longest Increasing Subsequence (LIS) problem. Like LIS problem, we first compute count of increasing subsequences ending at every index. Finally, we return sum of all values (In LCS problem, we return max of all values).

        // We count all increasing subsequences ending at every 
        // index i
        subCount(i) = Count of increasing subsequences ending 
                    at arr[i]. 

        // Like LCS, this value can be recursively computed
        subCount(i) = 1 + ∑ subCount(j) 
                    where j is index of all elements
                    such that arr[j] < arr[i] and j < i.
        1 is added as every element itself is a subsequence
        of size 1.

        // Finally we add all counts to get the result.
        Result = ∑ subCount(i)
                where i varies from 0 to n-1.

        For example, arr[] = {3, 2, 4, 5, 4}

        // There are no smaller elements on left of arr[0] 
        // and arr[1]
        subCount(0) = 1
        subCount(1) = 1  

        // Note that arr[0] and arr[1] are smaller than arr[2]
        subCount(2) = 1 + subCount(0) + subCount(1)  = 3

        subCount(3) = 1 + subCount(0) + subCount(1) + subCount(2) 
                    = 1 + 1 + 1 + 3
                    = 6
        
        subCount(3) = 1 + subCount(0) + subCount(1)
                    = 1 + 1 + 1
                    = 3
                                    
        Result = subCount(0) + subCount(1) + subCount(2) + subCount(3)
            = 1 + 1 + 3 + 6 + 3
            = 14.
         */
        /**
         * The above solution doesn’t use the fact that we have only 10 possible values in given array. We can use this fact by using an array count[] such that count[d] stores current count digits smaller than d. 

        For example, arr[] = {3, 2, 4, 5, 4}

        // We create a count array and initialize it as 0.
        count[10] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}

        // Note that here value is used as index to store counts
        count[3] += 1 = 1  // i = 0, arr[0] = 3
        count[2] += 1 = 1  // i = 1, arr[0] = 2

        // Let us compute count for arr[2] which is 4
        count[4] += 1 + count[3] + count[2] += 1 + 1 + 1  = 3

        // Let us compute count for arr[3] which is 5
        count[5] += 1 + count[3] + count[2] + count[4] 
                += 1 + 1 + 1 + 3
                = 6

        // Let us compute count for arr[4] which is 4
        count[4] += 1 + count[0] + count[1]
                += 1 + 1 + 1
                += 3
                = 3 + 3
                = 6
                    
        Note that count[] = {0, 0, 1, 1, 6, 6, 0, 0, 0, 0}                  
        Result = count[0] + count[1] + ... + count[9]
            = 1 + 1 + 6 + 6 {count[2] = 1, count[3] = 1
                                count[4] = 6, count[5] = 6} 
            = 14.
         */
        // Function To Count all the sub-sequences
        // possible in which digit is greater than
        // all previous digits arr[] is array of n
        // digits
        static int countSub(int arr[], int n) {
            // count[] array is used to store all
            // sub-sequences possible using that
            // digit count[] array covers all
            // the digit from 0 to 9
            int count[] = new int[10];

            // scan each digit in arr[]
            for (int i = 0; i < n; i++) {
                // count all possible sub-
                // sequences by the digits
                // less than arr[i] digit
                for (int j = arr[i] - 1; j >= 0; j--)
                    count[arr[i]] += count[j];

                // store sum of all sub-sequences
                // plus 1 in count[] array
                count[arr[i]]++;
            }

            // now sum up the all sequences
            // possible in count[] array
            int result = 0;
            for (int i = 0; i < 10; i++)
                result += count[i];

            return result;
        }

        // Driver program to run the test case
        public static void main(String[] args) {
            int arr[] = { 3, 2, 4, 5, 4 };
            int n = arr.length;

            System.out.println(countSub(arr, n));
        }
    }

    static class CountNumberOfPathsWithAtmostK_turnsInMatrix {
        /**
         *  Given a “m x n” matrix, count number of paths to reach bottom right from top left with maximum k turns allowed.

            What is a turn? A movement is considered turn, if we were moving along row and now move along column. OR we were moving along column and now move along row.

            There are two possible scenarios when a turn can occur
            at point (i, j):

            Turns Right: (i-1, j)  ->  (i, j)  ->  (i, j+1)
                                Down        Right

            Turns Down:  (i, j-1)  ->  (i, j)  ->  (i+1, j)
                                Right        Down

            Examples:

            Input:  m = 3, n = 3, k = 2
            Output: 4
            See below diagram for four paths with 
            maximum 2 turns.

            Input:  m = 3, n = 3, k = 1
            Output: 2 
         */
        /**
         * countPaths(i, j, k): Count of paths to reach (i,j) from (0, 0)
            countPathsDir(i, j, k, 0): Count of paths if we reach (i, j) 
                                    along row. 
            countPathsDir(i, j, k, 1): Count of paths if we reach (i, j) 
                                    along column. 
            The fourth parameter in countPathsDir() indicates direction.

            Value of countPaths() can be written as:
            countPaths(i, j, k) = countPathsDir(i, j, k, 0) + 
                                countPathsDir(i, j, k, 1) 

            And value of  countPathsDir() can be recursively defined as:

            // Base cases

            // If current direction is along row
            If (d == 0) 
            // Count paths for two cases
            // 1) We reach here through previous row.
            // 2) We reach here through previous column, so number of 
            //    turns k reduce by 1.
            countPathsDir(i, j, k, d) = countPathsUtil(i, j-1, k, d) +
                                        countPathsUtil(i-1, j, k-1, !d);

            // If current direction is along column
            Else 
            // Similar to above
            countPathsDir(i, j, k, d) =  countPathsUtil(i-1, j, k, d) +
                                        countPathsUtil(i, j-1, k-1, !d);

            We can solve this problem in Polynomial Time using Dynamic Programming. The idea is to use a 4 dimensional table dp[m][n][k][d] where m is number of rows, n is number of columns, k is number of allowed turns and d is direction.

            Below is Dynamic Programming based implementation.
         */
        static int MAX = 100;

        // table to store results of subproblems
        static int[][][][] dp = new int[MAX][MAX][MAX][2];

        // Returns count of paths to reach (i, j) from (0, 0)
        // using at-most k turns. d is current direction
        // d = 0 indicates along row, d = 1 indicates along
        // column.
        static int countPathsUtil(int i, int j, int k, int d) {
            // If invalid row or column indexes
            if (i < 0 || j < 0)
                return 0;

            // If current cell is top left itself
            if (i == 0 && j == 0)
                return 1;

            // If 0 turns left
            if (k == 0) {
                // If direction is row, then we can reach here
                // only if direction is row and row is 0.
                if (d == 0 && i == 0)
                    return 1;

                // If direction is column, then we can reach here
                // only if direction is column and column is 0.
                if (d == 1 && j == 0)
                    return 1;

                return 0;
            }

            // If this subproblem is already evaluated
            if (dp[i][j][k][d] != -1)
                return dp[i][j][k][d];

            // If current direction is row,
            // then count paths for two cases
            // 1) We reach here through previous row.
            // 2) We reach here through previous column,
            // so number of turns k reduce by 1.
            if (d == 0)
                return dp[i][j][k][d] = countPathsUtil(i, j - 1, k, d)
                        + countPathsUtil(i - 1, j, k - 1, d == 1 ? 0 : 1);

            // Similar to above if direction is column
            return dp[i][j][k][d] = countPathsUtil(i - 1, j, k, d) + countPathsUtil(i, j - 1, k - 1, d == 1 ? 0 : 1);
        }

        // This function mainly initializes 'dp' array
        // as -1 and calls countPathsUtil()
        static int countPaths(int i, int j, int k) {
            // If (0, 0) is target itself
            if (i == 0 && j == 0)
                return 1;

            // Initialize 'dp' array
            for (int p = 0; p < MAX; p++) {
                for (int q = 0; q < MAX; q++) {
                    for (int r = 0; r < MAX; r++)
                        for (int s = 0; s < 2; s++)
                            dp[p][q][r][s] = -1;
                }
            }

            // Recur for two cases: moving along row and along column
            return countPathsUtil(i - 1, j, k, 1) + // Moving along row
                    countPathsUtil(i, j - 1, k, 0); // Moving along column
        }

        // Driver Code
        public static void main(String[] args) {
            int m = 3, n = 3, k = 2;
            System.out.println("Number of paths is " + countPaths(m - 1, n - 1, k));
        }
    }

    static class CountNumberOfWaysToJumpToReachEndInArray {
        /**
         * Given an array of numbers where each element represents the max number of jumps that can be made forward from that element. For each array element, count the number of ways jumps can be made from that element to reach the end of the array. If an element is 0, then a move cannot be made through that element. The element that cannot reach the end should have a count “-1”.
            Examples:

            Input : {3, 2, 0, 1}
            Output : 2 1 -1 0
            For 3 number of steps or jumps that 
            can be taken are 1, 2 or 3. The different ways are:
            3 -> 2 -> 1
            3 -> 1

            For 2 number of steps or jumps that 
            can be taken are 1, or 2. The different ways are:
            2 -> 1

            For 0 number of steps or jumps that 
            can be taken are 0. 
            One cannot move forward from this point.

            For 1 number of steps or jumps that 
            can be taken are 1. But the element is at
            the end so no jump is required.

            Input : {1, 3, 5, 8, 9, 1, 0, 7, 6, 8, 9}
            Output : 52 52 28 16 8 -1 -1 4 2 1 0
         */
        /**
         * This problem is a variation of the Minimum number of jumps to reach end(Method 3). Here we need to count all the ways to reach the end from every cell.
        The solution is a modified version of the solution to the problem of the Minimum number of jumps to reach end(Method 3). 
        This problem aims to count the different ways to jump from each element to reach the end. For each element, the count is being calculated by adding the counts of all those forward elements that can reach the end and to which the current element could reach + 1(if the element can directly reach the end).

        Algorithm: 

        countWays(arr, n)
            Initialize array count_jump[n] = {0}

            count_jump[n-1] = 0
            for i = n-2 to 0
                if arr[i] >= (n-i-1)
                count_jump[i]++
                for j=i+1; j < n-1 && j <= arr[i]+i; i++
                if count_jump[j] != -1
                    count_jump[i] += count_jump[j]
                if count_jump[i] == 0
                count_jump[i] = -1

            for i = 0 to n-1
                print count_jump[i]
         */
         
        // function to count ways to jump to
        // reach end for each array element
        static void countWaysToJump(int arr[], int n) {

            // count_jump[i] store number of ways
            // arr[i] can reach to the end
            int count_jump[] = new int[n];
            Arrays.fill(count_jump, 0);

            // Last element does not require to jump.
            // Count ways to jump for remaining
            // elements
            for (int i = n - 2; i >= 0; i--) {

                // if the element can directly
                // jump to the end
                if (arr[i] >= n - i - 1)
                    count_jump[i]++;

                // add the count of all the elements
                // that can reach to end and arr[i] can
                // reach to them
                for (int j = i + 1; j < n - 1 && j <= arr[i] + i; j++)

                    // if element can reach to end then add
                    // its count to count_jump[i]
                    if (count_jump[j] != -1)
                        count_jump[i] += count_jump[j];

                // if arr[i] cannot reach to the end
                if (count_jump[i] == 0)
                    count_jump[i] = -1;
            }

            // print count_jump for each
            // array element
            for (int i = 0; i < n; i++)
                System.out.print(count_jump[i] + " ");
        }

        // driver code
        public static void main(String[] args) {

            int arr[] = { 1, 3, 5, 8, 9, 1, 0, 7, 6, 8, 9 };
            int n = arr.length;

            countWaysToJump(arr, n);
        }
    }

    static class CountNumberOfWaysToReachDestinationInMazeOrMatrix {
        /**
         * Given a maze with obstacles, count number of paths to reach rightmost-bottommost cell from topmost-leftmost cell. A cell in given maze has value -1 if it is a blockage or dead end, else 0.
            From a given cell, we are allowed to move to cells (i+1, j) and (i, j+1) only.

            Examples: 

            Input: maze[R][C] =  {{0,  0, 0, 0},
                                {0, -1, 0, 0},
                                {-1, 0, 0, 0},
                                {0,  0, 0, 0}};
            Output: 4
            There are four possible paths as shown in
            below diagram.
         */
        /**
         * This problem is an extension of below problem.
         * 
         * Backtracking | Set 2 (Rat in a Maze) 
         * 
         * In this post a different solution is
         * discussed that can be used to solve the above Rat in a Maze problem also. The
         * idea is to modify the given grid[][] so that grid[i][j] contains count of
         * paths to reach (i, j) from (0, 0) if (i, j) is not a blockage, else
         * grid[i][j] remains -1.
         */
        /**
         * We can recursively compute grid[i][j] using below 
            formula and finally return grid[R-1][C-1]

            // If current cell is a blockage
            if (maze[i][j] == -1)
                maze[i][j] = -1; //  Do not change

            // If we can reach maze[i][j] from maze[i-1][j]
            // then increment count.
            else if (maze[i-1][j] > 0)
                maze[i][j] = (maze[i][j] + maze[i-1][j]);

            // If we can reach maze[i][j] from maze[i][j-1]
            // then increment count.
            else if (maze[i][j-1] > 0)
                maze[i][j] = (maze[i][j] + maze[i][j-1]);

            Below is the implementation of above idea. 
         */
        static int R = 4;
        static int C = 4;

        // Returns count of possible paths in
        // a maze[R][C] from (0,0) to (R-1,C-1)
        static int countPaths(int maze[][]) {
            // If the initial cell is blocked,
            // there is no way of moving anywhere
            if (maze[0][0] == -1)
                return 0;

            // Initializing the leftmost column
            for (int i = 0; i < R; i++) {
                if (maze[i][0] == 0)
                    maze[i][0] = 1;

                // If we encounter a blocked cell
                // in leftmost row, there is no way
                // of visiting any cell directly below it.
                else
                    break;
            }

            // Similarly initialize the topmost row
            for (int i = 1; i < C; i++) {
                if (maze[0][i] == 0)
                    maze[0][i] = 1;

                // If we encounter a blocked cell in
                // bottommost row, there is no way of
                // visiting any cell directly below it.
                else
                    break;
            }

            // The only difference is that if a cell
            // is -1, simply ignore it else recursively
            // compute count value maze[i][j]
            for (int i = 1; i < R; i++) {
                for (int j = 1; j < C; j++) {
                    // If blockage is found,
                    // ignore this cell
                    if (maze[i][j] == -1)
                        continue;

                    // If we can reach maze[i][j] from
                    // maze[i-1][j] then increment count.
                    if (maze[i - 1][j] > 0)
                        maze[i][j] = (maze[i][j] + maze[i - 1][j]);

                    // If we can reach maze[i][j] from
                    // maze[i][j-1] then increment count.
                    if (maze[i][j - 1] > 0)
                        maze[i][j] = (maze[i][j] + maze[i][j - 1]);
                }
            }

            // If the final cell is blocked,
            // output 0, otherwise the answer
            return (maze[R - 1][C - 1] > 0) ? maze[R - 1][C - 1] : 0;
        }

        // Driver code

        public static void main(String[] args) {
            int maze[][] = { { 0, 0, 0, 0 }, { 0, -1, 0, 0 }, { -1, 0, 0, 0 }, { 0, 0, 0, 0 } };
            System.out.println(countPaths(maze));

        }
    }

    static class CountAllTripletsWhoseSumIsEqualToPerfectCube {
        /**
         * Given an array of n integers, count all different triplets whose sum is equal to the perfect cube i.e, for any i, j, k(i < j < k) satisfying the condition that a[i] + a[j] + a[j] = X3 where X is any integer. 3 ≤ n ≤ 1000, 1 ≤ a[i, j, k] ≤ 5000 
        Example: 
        

        Input:
        N = 5
        2 5 1 20 6
        Output:
        3
        Explanation:
        There are only 3 triplets whose total sum is a perfect cube.
        Indices  Values SUM
        0 1 2    2 5 1   8
        0 1 3    2 5 20  27
        2 3 4    1 20 6  27
        Since 8 and 27 are prefect cube of 2 and 3.
         */
        /**
         * Naive approach is to iterate over all the possible numbers by using 3 nested
         * loops and check whether their sum is a perfect cube or not. The approach
         * would be very slow as time complexity can go up to O(n3).
         * 
         * An Efficient approach is to use dynamic programming and basic mathematics.
         * According to the given condition sum of any of three positive integers is not
         * greater than 15000. Therefore there can be only 24(150001/3) cubes are
         * possible in the range of 1 to 15000.
         * 
         * 
         * Now instead of iterating all triplets we can do much better by the help of
         * above information. Fixed first two indices i and j such that instead of
         * iterating over all k(j < k ≤ n), we can iterate over all the 24 possible
         * cubes, and for each one, (let’s say P) check how many occurrence of P – (a[i]
         * + a[j]) are in a[j+1, j+2, … n].
         * 
         * 
         * But if we compute the number of occurrences of a number say K in a[j+1, j+2,
         * … n] then this would again be counted as slow approach and would definitely
         * give TLE. So we have to think of a different approach.
         * 
         * 
         * Now here comes to a Dynamic Programming. Since all numbers are smaller than
         * 5000 and n is at most 1000. Hence we can compute a DP array as, dp[i][K]:=
         * Number of occurrence of K in A[i, i+1, i+2 … n]
         */
        public static int dp[][];

        // Function to calculate all occurrence of
        // a number in a given range
        public static void computeDpArray(int arr[], int n) {
            for (int i = 0; i < n; ++i) {
                for (int j = 1; j <= 15000; ++j) {

                    // if i == 0
                    // assign 1 to present state

                    if (i == 0 && j == arr[i])
                        dp[i][j] = 1;
                    else if (i == 0)
                        dp[i][j] = 0;

                    // else add +1 to current state
                    // with previous state
                    else if (arr[i] == j)
                        dp[i][j] = dp[i - 1][j] + 1;
                    else
                        dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // Function to calculate triplets whose sum
        // is equal to the pefect cube
        public static int countTripletSum(int arr[], int n) {
            computeDpArray(arr, n);

            int ans = 0; // Initialize answer
            for (int i = 0; i < n - 2; ++i) {
                for (int j = i + 1; j < n - 1; ++j) {
                    for (int k = 1; k <= 24; ++k) {
                        int cube = k * k * k;

                        int rem = cube - (arr[i] + arr[j]);

                        // count all occurrence of
                        // third triplet in range
                        // from j+1 to n
                        if (rem > 0)
                            ans += dp[n - 1][rem] - dp[j][rem];
                    }
                }
            }
            return ans;
        }

        /* Driver program to test above function */
        public static void main(String[] args) {
            int arr[] = { 2, 5, 1, 20, 6 };
            int n = arr.length;
            dp = new int[1001][15001];

            System.out.println(countTripletSum(arr, n));

        }
    }

    static class CountNumberOfSubsetsHavingParticularXORvalue {
      /**
       * Given an array arr[] of n numbers and a number K, find the number of subsets of arr[] having XOR of elements as K
        Examples : 

        Input:   arr[]  = {6, 9, 4,2}, k = 6
        Output:  2
        The subsets are {4, 2} and {6}

        Input:   arr[]  = {1, 2, 3, 4, 5}, k = 4
        Output:  4
        The subsets are {1, 5}, {4}, {1, 2, 3, 4}
                        and {2, 3, 5} */  

    /**
     * Dynamic Programming Approach O(n*m): 
    We define a number m such that m = pow(2,(log2(max(arr))+1))­ – 1. This number is actually the maximum value any XOR subset will acquire. We get this number by counting bits in largest number. We create a 2D array dp[n+1][m+1], such that dp[i][j] equals to the number of subsets having XOR value j from subsets of arr[0…i-1].

    We fill the dp array as following: 

    We initialize all values of dp[i][j] as 0.
    Set value of dp[0][0] = 1 since XOR of an empty set is 0.
    Iterate over all the values of arr[i] from left to right and for each arr[i], iterate over all the possible values of XOR i.e from 0 to m (both inclusive) and fill the dp array asfollowing: 
           for i = 1 to n: 
                 for j = 0 to m: 
                       dp[i][j] = dp[i­-1][j] + dp[i­-1][j^arr[i-1]] 
    This can be explained as, if there is a subset arr[0…i­-2] with XOR value j, then there also exists a subset arr[0…i-1] with XOR value j. also if there exists a subset arr[0….i-2] with XOR value j^arr[i] then clearly there exist a subset arr[0…i-1] with XOR value j, as j ^ arr[i-1] ^ arr[i-1] = j.
    Counting the number of subsets with XOR value k: Since dp[i][j] is the number of subsets having j as XOR value from the subsets of arr[0..i-1], then the number of subsets from set arr[0..n] having XOR value as K will be dp[n][K]
     */

    // Returns count of subsets of arr[] with
    // XOR value equals to k.
    static int subsetXOR(int[] arr, int n, int k) {

        // Find maximum element in arr[]
        int max_ele = arr[0];

        for (int i = 1; i < n; i++)
            if (arr[i] > max_ele)
                max_ele = arr[i];

        // Maximum possible XOR value
        int m = (1 << (int) (Math.log(max_ele) / Math.log(2) + 1)) - 1;
        if (k > m) {
            return 0;
        }

        // The value of dp[i][j] is the number
        // of subsets having XOR of their
        // elements as j from the set arr[0...i-1]
        int[][] dp = new int[n + 1][m + 1];

        // Initializing all the values of dp[i][j] as 0
        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= m; j++)
                dp[i][j] = 0;

        // The xor of empty subset is 0
        dp[0][0] = 1;

        // Fill the dp table
        for (int i = 1; i <= n; i++)
            for (int j = 0; j <= m; j++)
                dp[i][j] = dp[i - 1][j] + dp[i - 1][j ^ arr[i - 1]];

        // The answer is the number of
        // subset from set arr[0..n-1]
        // having XOR of elements as k
        return dp[n][k];
    }

    // Driver code
    public static void main(String arg[]) {
        int[] arr = { 1, 2, 3, 4, 5 };
        int k = 4;
        int n = arr.length;

        System.out.println("Count of subsets is " + subsetXOR(arr, n, k));
    }
    }

    static class CountPossibleDecodingsOfGivenDigitSequenceToString {
        /**
         * Let 1 represent ‘A’, 2 represents ‘B’, etc. Given a digit sequence, count the number of possible decodings of the given digit sequence. 

        Examples: 

        Input:  digits[] = "121"
        Output: 3
        // The possible decodings are "ABA", "AU", "LA"

        Input: digits[] = "1234"
        Output: 3
        // The possible decodings are "ABCD", "LCD", "AWD"

        An empty digit sequence is considered to have one decoding. 
        It may be assumed that the input contains valid digits from 0 to 9 and there are no leading 0’s, no extra trailing 0’s, and no two or more consecutive 0’s.
         */
        /**
         * This problem is recursive and can be broken into sub-problems. We start from
         * the end of the given digit sequence. We initialize the total count of
         * decodings as 0. We recur for two subproblems.
         * 
         * 1) If the last digit is non-zero, recur for the remaining (n-1) digits and
         * add the result to the total count.
         * 
         * 2) If the last two digits form a valid character (or smaller than 27), recur
         * for remaining (n-2) digits and add the result to the total count.
         * 
         * Following is the implementation of the above approach.
         */
            // recuring function to find
            // ways in how many ways a
            // string can be decoded of length
            // greater than 0 and starting with
            // digit 1 and greater.
            static int countDecoding(char[] digits, int n) {
                // base cases
                if (n == 0 || n == 1)
                    return 1;

                // for base condition "01123" should return 0
                if (digits[0] == '0')
                    return 0;

                // Initialize count
                int count = 0;

                // If the last digit is not 0, then
                // last digit must add to
                // the number of words
                if (digits[n - 1] > '0')
                    count = countDecoding(digits, n - 1);

                // If the last two digits form a number
                // smaller than or equal to 26,
                // then consider last two digits and recur
                if (digits[n - 2] == '1' || (digits[n - 2] == '2' && digits[n - 1] < '7'))
                    count += countDecoding(digits, n - 2);

                return count;
            }

            // Given a digit sequence of length n,
            // returns count of possible decodings by
            // replacing 1 with A, 2 woth B, ... 26 with Z
            static int countWays(char[] digits, int n) {
                if (n == 0 || (n == 1 && digits[0] == '0'))
                    return 0;
                return countDecoding(digits, n);
            }

            // Driver code
            public static void main(String[] args) {
                char digits[] = { '1', '2', '3', '4' };
                int n = digits.length;
                System.out.printf("Count is %d", countWays(digits, n));
            }
    }    

    static class CountNumberOfWaysToPartitionSetIntoK_Subsets {
        /**
         * Given two numbers n and k where n represents a number of elements in a set, find a number of ways to partition the set into k subsets.
            Example: 

            Input: n = 3, k = 2
            Output: 3
            Explanation: Let the set be {1, 2, 3}, we can partition
                        it into 2 subsets in following ways
                        {{1,2}, {3}},  {{1}, {2,3}},  {{1,3}, {2}}  

            Input: n = 3, k = 1
            Output: 1
            Explanation: There is only one way {{1, 2, 3}}
         */
        /**
         * Recursive Solution

        Approach: Firstly, let’s define a recursive solution to find the solution for nth element. There are two cases. 
            The previous n – 1 elements are divided into k partitions, i.e S(n-1, k) ways. Put this nth element into one of the previous k partitions. So, count = k * S(n-1, k)
            The previous n – 1 elements are divided into k – 1 partitions, i.e S(n-1, k-1) ways. Put the nth element into a new partition ( single element partition).So, count = S(n-1, k-1)
            Total count = k * S(n-1, k) + S(n-1, k-1).
        Algorithm: 
            Create a recursive function which accepts two parameters, n and k. The function returns total number of partitions of n elements into k sets.
            Handle the base cases. If n = 0 or k = 0 or k > n return 0 as there cannot be any subset. If n is equal to k or k is equal to 1 return 1.
            Else calculate the value as follows: S(n, k) = k*S(n-1, k) + S(n-1, k-1), i.e call recursive function with the recuried parameter and calculate the value of S(n, k).
            Return the sum.
        Implementation:

                // Returns count of different partitions of n
        // elements in k subsets
        int countP(int n, int k)
        {
        // Base cases
        if (n == 0 || k == 0 || k > n)
            return 0;
        if (k == 1 || k == n)
            return 1;
        
        // S(n+1, k) = k*S(n, k) + S(n, k-1)
        return  k*countP(n-1, k) + countP(n-1, k-1);
        }
         */
        /**
         *     Approach: The time complexity of above recursive solution is exponential. The solution can be optimized by reducing the overlapping subproblems. Below is recursion tree of countP(10,7). The subproblem countP(8,6) or CP(8,6) is called multiple times.

    So this problem has both properties (see Type 1 and Type 2) of a dynamic programming problem. Like other typical Dynamic Programming(DP) problems, recomputations of same subproblems can be avoided by constructing a temporary array dp[][] in bottom up manner using the shown recursive formula.
    Next comes the reduction of the sub-problems to optimize the complexity of the problem. This can be done in two ways: 
        1. bottom-up manner: This keeps the recursive structure intact and stores the values in a hashmap or a 2D array. Then compute the value only once and when the function is called next return the value.
        2. top-down manner: This keeps a 2D array of size n*k, where dp[i][j] represents a total number of partitions of i elements into j sets. Fill in the base cases for dp[i][0] and dp[0][i]. For a value (i,j), the values of dp[i-1][j] and dp[i-1][j-1] is needed. So fill the DP from row 0 to n and column 0 to k.
    Algorithm: 
        Create a Dp array dp[n+1][k+1] of size ( n + 1 )* ( k + 1 ) .
        Fill the values of basic cases. For all values of i from 0 to n fill dp[i][0] = 0 and for all values of i from 0 to k fill dp[0][k] = 0
        Run a nested loop, the outer loop from 1 to n, and inner loop from 1 to k.
        For index i and j (outer loop and inner loop respectively), calculate dp[i][j] = j * dp[i – 1][j] + dp[i – 1][j – 1] and if j == 1 or i == j, calculate dp[i][j] = 1.
        Print values dp[n][k]
    Implementation:
         */
   
        // Returns count of different partitions of n
        // elements in k subsets
        static int countP(int n, int k) {
            // Table to store results of subproblems
            int[][] dp = new int[n + 1][k + 1];

            // Base cases
            for (int i = 0; i <= n; i++)
                dp[i][0] = 0;
            for (int i = 0; i <= k; i++)
                dp[0][k] = 0;

            // Fill rest of the entries in dp[][]
            // in bottom up manner
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= k; j++)
                    if (j == 1 || i == j)
                        dp[i][j] = 1;
                    else
                        dp[i][j] = j * dp[i - 1][j] + dp[i - 1][j - 1];

            return dp[n][k];

        }

        // Driver program
        public static void main(String[] args) {
            System.out.println(countP(5, 2));
        }
    }

    static class CountWaysToAssignUniqueCapToEveryPerson_BitMasking {
        /**
         * Consider the below problems statement.

        There are 100 different types of caps each having a unique id from 1 to 100. Also, there are ‘n’ persons each having a collection of a variable number of caps. One day all of these persons decide to go in a party wearing a cap but to look unique they decided that none of them will wear the same type of cap. So, count the total number of arrangements or ways such that none of them is wearing the same type of cap.

        Constraints: 1 <= n <= 10 Example:

        The first line contains the value of n, next n lines contain collections 
        of all the n persons.
        Input: 
        3
        5 100 1     // Collection of the first person.
        2           // Collection of the second person.
        5 100       // Collection of the third person.

        Output:
        4
        Explanation: All valid possible ways are (5, 2, 100),  (100, 2, 5),
                    (1, 2, 5) and  (1, 2, 100)
         */
        /**
         * Suppose we have a collection of elements which are numbered from 1 to N. If we want to represent a subset of this set then it can be encoded by a sequence of N bits (we usually call this sequence a “mask”). In our chosen subset the i-th element belongs to it if and only if the i-th bit of the mask is set i.e., it equals to 1. For example, the mask 10000101 means that the subset of the set [1… 8] consists of elements 1, 3 and 8. We know that for a set of N elements there are total 2N subsets thus 2N masks are possible, one representing each subset. Each mask is, in fact, an integer number written in binary notation.

        Our main methodology is to assign a value to each mask (and, therefore, to each subset) and thus calculate the values for new masks using values of the already computed masks. Usually our main target is to calculate value/solution for the complete set i.e., for mask 11111111. Normally, to find the value for a subset X we remove an element in every possible way and use values for obtained subsets X’1, X’2… ,X’k to compute the value/solution for X. This means that the values for X’i must have been computed already, so we need to establish an ordering in which masks will be considered. It’s easy to see that the natural ordering will do: go over masks in increasing order of corresponding numbers. Also, We sometimes, start with the empty subset X and we add elements in every possible way and use the values of obtained subsets X’1, X’2… ,X’k to compute the value/solution for X.

        We mostly use the following notations/operations on masks:
        bit(i, mask) – the i-th bit of mask
        count(mask) – the number of non-zero bits in the mask
        first(mask) – the number of the lowest non-zero bit in the mask
        set(i, mask) – set the ith bit in the mask
        check(i, mask) – check the ith bit in the mask

        How is this problem solved using Bitmasking + DP?
        The idea is to use the fact that there are upto 10 persons. So we can use an integer variable as a bitmask to store which person is wearing a cap and which is not.

        Let i be the current cap number (caps from 1 to i-1 are already 
        processed). Let integer variable mask indicates that the persons w
        earing and not wearing caps.  If i'th bit is set in mask, then 
        i'th person is wearing a cap, else not.

                    // consider the case when ith cap is not included 
                            // in the arrangement
        countWays(mask, i) = countWays(mask, i+1) +             
                            
                            // when ith cap is included in the arrangement
                            // so, assign this cap to all possible persons 
                            // one by one and recur for remaining persons.
                            ∑ countWays(mask | (1 << j), i+1)
                            for every person j that can wear cap i 
        
        Note that the expression "mask | (1 << j)" sets j'th bit in mask.
        And a person can wear cap i if it is there in the person's cap list
        provided as input.

        If we draw the complete recursion tree, we can observe that many subproblems are solved again and again. So we use Dynamic Programming. A table dp[][] is used such that in every entry dp[i][j], i is mask and j is cap number.

        Since we want to access all persons that can wear a given cap, we use an array of vectors, capList[101]. A value capList[i] indicates the list of persons that can wear cap i.

        Below is the implementation of above idea.
         */
        static final int MOD = 1000000007;

        // for input
        // static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // capList[i]'th vector contains the list of persons having a cap with id i
        // id is between 1 to 100 so we declared an array of 101 vectors as indexing
        // starts from 0.
        static Vector<Integer> capList[] = new Vector[101];

        // dp[2^10][101] .. in dp[i][j], i denotes the mask i.e., it tells that
        // how many and which persons are wearing cap. j denotes the first j caps
        // used. So, dp[i][j] tells the number ways we assign j caps to mask i
        // such that none of them wears the same cap
        static int dp[][] = new int[1025][101];

        // This is used for base case, it has all the N bits set
        // so, it tells whether all N persons are wearing a cap.
        static int allmask;

        // Mask is the set of persons, i is cap-id (OR the
        // number of caps processed starting from first cap).
        static long countWaysUtil(int mask, int i) {
            // If all persons are wearing a cap so we
            // are done and this is one way so return 1
            if (mask == allmask)
                return 1;

            // If not everyone is wearing a cap and also there are no more
            // caps left to process, so there is no way, thus return 0;
            if (i > 100)
                return 0;

            // If we already have solved this subproblem, return the answer.
            if (dp[mask][i] != -1)
                return dp[mask][i];

            // Ways, when we don't include this cap in our arrangement
            // or solution set.
            long ways = countWaysUtil(mask, i + 1);

            // size is the total number of persons having cap with id i.
            int size = capList[i].size();

            // So, assign one by one ith cap to all the possible persons
            // and recur for remaining caps.
            for (int j = 0; j < size; j++) {
                // if person capList[i][j] is already wearing a cap so continue as
                // we cannot assign him this cap
                if ((mask & (1 << capList[i].get(j))) != 0)
                    continue;

                // Else assign him this cap and recur for remaining caps with
                // new updated mask vector
                else
                    ways += countWaysUtil(mask | (1 << capList[i].get(j)), i + 1);
                ways %= MOD;
            }

            // Save the result and return it.
            return dp[mask][i] = (int) ways;
        }

        // Reads n lines from standard input for current test case
        static void countWays(int n) throws Exception {
            // ----------- READ INPUT --------------------------
            String str;
            String split[];
            int x;

            for (int i = 0; i < n; i++) {
                str = br.readLine();
                split = str.split(" ");

                // while there are words in the split[]
                for (int j = 0; j < split.length; j++) {
                    // add the ith person in the list of cap if with id x
                    x = Integer.parseInt(split[j]);
                    capList[x].add(i);
                }

            }
            // ----------------------------------------------------

            // All mask is used to check of all persons
            // are included or not, set all n bits as 1
            allmask = (1 << n) - 1;

            // Initialize all entries in dp as -1
            for (int[] is : dp) {
                for (int i = 0; i < is.length; i++) {
                    is[i] = -1;
                }
            }

            // Call recursive function count ways
            System.out.println(countWaysUtil(0, 1));
        }

        // Driver method
        public static void main(String args[]) throws Exception {
            int n; // number of persons in every test case

            // initializing vector array
            for (int i = 0; i < capList.length; i++)
                capList[i] = new Vector<>();

            n = Integer.parseInt(br.readLine());
            countWays(n);
        }
    }

    static class CountBinaryStringsOfLength_N_WithK_asNumberOftimesAdjacent_1s_Appear {
        /**
         * Given two integers n and k, count the number of binary strings of length n with k as number of times adjacent 1’s appear.

        Examples: 

        Input  : n = 5, k = 2
        Output : 6
        Explanation:
        Binary strings of length 5 in which k number of times
        two adjacent set bits appear.
        00111  
        01110
        11100
        11011
        10111
        11101

        Input  : n = 4, k = 1
        Output : 3
        Explanation:
        Binary strings of length 3 in which k number of times
        two adjacent set bits appear.
        0011  
        1100
        0110
         */
        /**
         * 
         * Lets try writing the recursive function for the above problem statement: 
        1) n = 1, only two binary strings exist with length 1, not having any adjacent 1’s 
            String 1 : “0” 
            String 2 : “1”
        2) For all n > 1 and all k, two cases arise 
            a) Strings ending with 0 : String of length n can be created by appending 0 to all strings of length n-1 having k times two adjacent 1’s ending with both 0 and 1 (Having 0 at n’th position will not change the count of adjacent 1’s). 
            b) Strings ending with 1 : String of length n can be created by appending 1 to all strings of length n-1 having k times adjacent 1’s and ending with 0 and to all strings of length n-1 having k-1 adjacent 1’s and ending with 1.

        Example: let s = 011 i.e. a string ending with 1 having adjacent count as 1. Adding 1 to it, s = 0111 increase the count of adjacent 1.  

        Let there be an array dp[i][j][2] where dp[i][j][0]
        denotes number of binary strings with length i having
        j number of two adjacent 1's and ending with 0.
        Similarly dp[i][j][1] denotes the same binary strings
        with length i and j adjacent 1's but ending with 1.
        Then: 
            dp[1][0][0] = 1 and dp[1][0][1] = 1
            For all other i and j,
                dp[i][j][0] = dp[i-1][j][0] + dp[i-1][j][1]
                dp[i][j][1] = dp[i-1][j][0] + dp[i-1][j-1][1]

        Then, output dp[n][k][0] + dp[n][k][1]
         */
        static int countStrings(int n, int k) {
            // dp[i][j][0] stores count of binary
            // strings of length i with j consecutive
            // 1's and ending at 0.
            // dp[i][j][1] stores count of binary
            // strings of length i with j consecutive
            // 1's and ending at 1.
            int dp[][][] = new int[n + 1][k + 1][2];

            // If n = 1 and k = 0.
            dp[1][0][0] = 1;
            dp[1][0][1] = 1;

            for (int i = 2; i <= n; i++) {

                // number of adjacent 1's can not exceed i-1
                for (int j = 0; j < i && j < k + 1; j++) {
                    dp[i][j][0] = dp[i - 1][j][0] + dp[i - 1][j][1];
                    dp[i][j][1] = dp[i - 1][j][0];

                    if (j - 1 >= 0) {
                        dp[i][j][1] += dp[i - 1][j - 1][1];
                    }
                }
            }

            return dp[n][k][0] + dp[n][k][1];
        }

        // Driver code
        public static void main(String[] args) {
            int n = 5, k = 2;
            System.out.println(countStrings(n, k));
        }
    }

    static class CountOfStringsThatCanBeFormedUsing_a_b_c_withAtmost_oneB_twoCs_allowed {
        /**
         * Given a length n, count the number of strings of length n that can be made using ‘a’, ‘b’ and ‘c’ with at-most one ‘b’ and two ‘c’s allowed.
        Examples : 
        

        Input : n = 3 
        Output : 19 
        Below strings follow given constraints:
        aaa aab aac aba abc aca acb acc baa
        bac bca bcc caa cab cac cba cbc cca ccb 

        Input  : n = 4
        Output : 39

        A simple solution is to recursively count all possible combination of string that can be mode up to latter ‘a’, ‘b’, and ‘c’. 
        Below is implementation of above idea 

        static int countStr(int n,
                    int bCount,
                    int cCount)
        {
            // Base cases
            if (bCount < 0 || cCount < 0) return 0;
            if (n == 0) return 1;
            if (bCount == 0 && cCount == 0) return 1;
        
            // Three cases, we choose, a or b or c
            // In all three cases n decreases by 1.
            int res = countStr(n - 1, bCount, cCount);
            res += countStr(n - 1, bCount - 1, cCount);
            res += countStr(n - 1, bCount, cCount - 1);
        
            return res;
        }
         */
        /**
         * Efficient Solution 
        If we drown a recursion tree of above code, we can notice that same values appear multiple times. So we store results which are used later if repeated.
         */
        // n is total number of characters.
        // bCount and cCount are counts of 'b'
        // and 'c' respectively.

        static int countStrUtil(int[][][] dp, int n, int bCount, int cCount) {

            // Base cases
            if (bCount < 0 || cCount < 0) {
                return 0;
            }
            if (n == 0) {
                return 1;
            }
            if (bCount == 0 && cCount == 0) {
                return 1;
            }

            // if we had saw this combination previously
            if (dp[n][bCount][cCount] != -1) {
                return dp[n][bCount][cCount];
            }

            // Three cases, we choose, a or b or c
            // In all three cases n decreases by 1.
            int res = countStrUtil(dp, n - 1, bCount, cCount);
            res += countStrUtil(dp, n - 1, bCount - 1, cCount);
            res += countStrUtil(dp, n - 1, bCount, cCount - 1);

            return (dp[n][bCount][cCount] = res);
        }

        // A wrapper over countStrUtil()
        static int countStr(int n, int bCount, int cCount) {
            int[][][] dp = new int[n + 1][2][3];
            for (int i = 0; i < n + 1; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 3; k++) {
                        dp[i][j][k] = -1;
                    }
                }
            }
            return countStrUtil(dp, n, bCount, cCount);
        }

        // Driver code
        public static void main(String[] args) {
            int n = 3; // Total number of characters
            int bCount = 1, cCount = 2;
            System.out.println(countStr(n, bCount, cCount));
        }
    }

    static class CountDigitGroupingsOfNumberStringWithGivenConstraints {
        /**
         * We are given a string consisting of digits, we may group these digits into sub-groups (but maintaining their original order). The task is to count the number of groupings such that for every sub-group except the last one, the sum of digits in a sub-group is less than or equal to the sum of the digits in the sub-group immediately on its right. 
        For example, a valid grouping of digits of number 1119 is (1-11-9). Sum of digits in first subgroup is 1, next subgroup is 2, and last subgroup is 9. Sum of every subgroup is less than or equal to its immediate right. 
        Examples : 
        

        Input : "1119"
        Output: 7
        Sub-groups: [1-119], [1-1-19], [1-11-9], [1-1-1-9],
                    [11-19] and [111-9].
        Note : Here we have included [1119] in the group and
            the sum of digits is 12 and this group has no 
            immediate right.

        Input : "1234"
        Output: 6
        Sub-groups : [1234], [1-234], [12-34], [1-2-3-4],
                    [12-3-4] and [1-2-34]

        Let “length” be the length of the input number. A recursive solution is to consider every position from 0 length-1. 
        For every position, recursively count all possible subgroups after it. Below is C++ implementation of the naive recursive solution.
         */
        // Function to find
        // the subgroups
        static int countGroupsRecursion(int position, int previous_sum, int length, String num) {
            // Terminating Condition
            if (position == length)
                return 1;

            int res = 0;

            // sum of digits
            int sum = 0;

            // Traverse all digits from
            // current position to rest
            // of the length of string
            for (int i = position; i < length; i++) {
                sum += (num.charAt(i) - '0');

                // If forward_sum is greater
                // than the previous sum,
                // then call the method again
                if (sum >= previous_sum)

                    // Note : We pass current
                    // sum as previous sum
                    res += countGroupsRecursion(i + 1, sum, length, num);
            }

            // Total number of subgroups
            // till current position
            return res;
        }

        // Driver Code
        public static void main(String[] args) {
            String num = "1119";
            int len = num.length();
            System.out.println(countGroupsRecursion(0, 0, len, num));
        }
        /**
         * If we take a closer look at the above recursive solution, we notice that
         * there may be overlapping subproblems. For example, if the input number is
         * 12345, then for position = 3 and previous_sum = 3, we recur two times.
         * Similarly, for position 4 and previous_sum = 7, we recur two times. Therefore
         * the above solution can be optimized using Dynamic Programming. Below is a
         * Dynamic Programming based solution for this problem.
         * 
            The maximum sum of digits can be 9*length where ‘length’ is length of input num.
            Create a 2D array int dp[MAX][9*MAX] where MAX is maximum possible length of input number. A value dp[position][previous] is going to store result for ‘position’ and ‘previous_sum’. 
            
            If current subproblem has been evaluated i.e; dp[position][previous_sum] != -1, then use this result, else recursively compute its value. 
            
            If by including the current position digit in sum i.e; sum = sum + num[position]-‘0’, sum becomes greater than equal to previous sum, 
            then increment the result and call the problem for next position in the num.
            If position == length, then we have been traversed current subgroup successfully and we return 1;

            Below is the implementation of the above algorithm.
 
         */
        // Maximum length of
        // input number string
        static int MAX = 40;

        // A memoization table to store
        // results of subproblems length
        // of string is 40 and maximum
        // sum will be 9 * 40 = 360.
        static int dp[][] = new int[MAX][9 * MAX + 1];

        // Function to find the count
        // of splits with given condition
        static int countGroups(int position, int previous_sum, int length, char[] num) {
            // Terminating Condition
            if (position == length)
                return 1;

            // If already evaluated for
            // a given sub problem then
            // return the value
            if (dp[position][previous_sum] != -1)
                return dp[position][previous_sum];

            // countGroups for current
            // sub-group is 0
            dp[position][previous_sum] = 0;

            int res = 0;

            // sum of digits
            int sum = 0;

            // Traverse all digits from
            // current position to rest
            // of the length of string
            for (int i = position; i < length; i++) {
                sum += (num[i] - '0');

                // If forward_sum is greater
                // than the previous sum,
                // then call the method again
                if (sum >= previous_sum)

                    // Note : We pass current
                    // sum as previous sum
                    res += countGroups(i + 1, sum, length, num);
            }

            dp[position][previous_sum] = res;

            // total number of subgroups
            // till current position
            return res;
        }

        // Driver Code
        public static void countGroupsDP(String[] args) {
            char num[] = "1119".toCharArray();
            int len = num.length;

            // Initialize dp table
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < 9 * MAX + 1; j++) {
                    dp[i][j] = -1;
                }
            }
            System.out.println(countGroups(0, 0, len, num));
        }
    }

    static class CountAllPossiblePathsWalksFromSourceToDestinationWithExactlyKEdges {
        /**
         * Given a directed graph and two vertices ‘u’ and ‘v’ in it, count all possible
         * walks from ‘u’ to ‘v’ with exactly k edges on the walk. The graph is given
         * adjacency matrix representation where the value of graph[i][j] as 1 indicates
         * that there is an edge from vertex i to vertex j and a value 0 indicates no
         * edge from i to j.
         * 
         * For example, consider the following graph. Let source ‘u’ be vertex 0,
         * destination ‘v’ be 3 and k be 2. The output should be 2 as there are two
         * walks from 0 to 3 with exactly 2 edges. The walks are {0, 2, 3} and {0, 1, 3}
         */
    }
}
