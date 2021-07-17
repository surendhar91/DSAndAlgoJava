package com.hacker.practice.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

public class GoogleInterviewQuestions {
    static class ValidateStackSequence {
        /**
         * Given two sequences pushed and popped with distinct values, return true if
         * and only if this could have been the result of a sequence of push and pop
         * operations on an initially empty stack.
         * 
         * Example 1:
         * 
         * Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1] Output: true Explanation:
         * We might do the following sequence: push(1), push(2), push(3), push(4), pop()
         * -> 4, push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1 Example 2:
         * 
         * Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2] Output: false Explanation:
         * 1 cannot be popped before 2.
         */
        /**
         * We have to push the items in order, so when do we pop them?
         * 
         * If the stack has say, 2 at the top, then if we have to pop that value next,
         * we must do it now. That's because any subsequent push will make the top of
         * the stack different from 2, and we will never be able to pop again.
         * 
         * Algorithm
         * 
         * For each value, push it to the stack.
         * 
         * Then, greedily pop values from the stack if they are the next values to pop.
         * 
         * At the end, we check if we have popped all the values successfully.
         */
        public boolean validateStackSequences(int[] pushed, int[] popped) {
            int N = pushed.length;
            Stack<Integer> stack = new Stack();
    
            int j = 0;
            for (int x: pushed) {
                stack.push(x);
                while (!stack.isEmpty() && j < N && stack.peek() == popped[j]) {
                    stack.pop();
                    j++;
                }
            }
    
            return j == N;
        }
    }
    
    static class ExpressiveWords {
        /**
         *  Sometimes people repeat letters to represent extra feeling, such as "hello" -> "heeellooo", "hi" -> "hiiii".  
         *  In these strings like "heeellooo", we have groups of adjacent letters that are all the same:  "h", "eee", "ll", "ooo".

            For some given string s, a query word is stretchy if it can be made to be equal to s by any number of applications of the following extension operation: 
            choose a group consisting of characters c, and add some number of characters c to the group so that the size of the group is 3 or more.

            For example, starting with "hello", we could do an extension on the group "o" to get "hellooo", but we cannot get "helloo" since the group "oo" has size less than 3.  
            Also, we could do another extension like "ll" -> "lllll" to get "helllllooo".  
            If s = "helllllooo", then the query word "hello" would be stretchy because of these two extension operations: query = "hello" -> "hellooo" -> "helllllooo" = s.

            Given a list of query words, return the number of words that are stretchy. 

            Example:
            Input: 
            s = "heeellooo"
            words = ["hello", "hi", "helo"]
            Output: 1
            Explanation: 
            We can extend "e" and "o" in the word "hello" to get "heeellooo".
            We can't extend "helo" to get "heeellooo" because the group "ll" is not size 3 or more.
         */
        /**
         * We have two pointers, use i to scan S, and use j to scan each word in words.
         * Firstly, for S and word, we can calculate the length of the susbtrings which
         * contains the repeated letters of the letter currently pointed by the two
         * pointers, and get len1 and len2.
         * 
         * The two letters currently pointed by the two pointers must be equal,
         * otherwise the word is not stretchy, we return false. Then, if we find that
         * len1 is smaller than 3, it means the letter cannot be extended, so len1 must
         * equals to len2, otherwise this word is not stretchy. In the other case, if
         * len1 equals to or larger than 3, we must have len1 equals to or larger than
         * len2, otherwise there are not enough letters in S to match the letters in
         * word.
         * 
         * Finally, if the word is stretchy, we need to guarantee that both of the two
         * pointers has scanned the whole string.
         */
        public int expressiveWords(String S, String[] words) {
            if (S == null || words == null) {
                return 0;
            }
            int count = 0;
            for (String word : words) {
                if (stretchy(S, word)) { //S word is heeellooo
                    count++;
                }
            }
            return count;
        }
        
        public boolean stretchy(String S, String word) {
            if (word == null) {
                return false;
            }
            int i = 0;
            int j = 0;
            while (i < S.length() && j < word.length()) {
                if (S.charAt(i) == word.charAt(j)) {
                    int len1 = getRepeatedLength(S, i);//e - len: 1,
                    int len2 = getRepeatedLength(word, j);//e - len: 3
                    if (len1 < 3 && len1 != len2 || len1 >= 3 && len1 < len2) { //len1>=3 && 1<3 
                        return false;
                    }
                    i += len1;
                    j += len2;
                } else {
                    return false;
                }
            }
            return i == S.length() && j == word.length();
        }
        
        public int getRepeatedLength(String str, int i) {
            int j = i;
            while (j < str.length() && str.charAt(j) == str.charAt(i)) {
                j++;
            }
            return j - i;
        }
    }
    
    static class DeleteNodeAndReturnForest {
        Set<Integer> to_delete_set;
        List<TreeNode> res;

        public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
            to_delete_set = new HashSet<>();
            res = new ArrayList<>();
            for (int i : to_delete)
                to_delete_set.add(i);
            helper(root, true);
            return res;
        }

        private TreeNode helper(TreeNode node, boolean is_root) {
            if (node == null)
                return null;
            boolean deleted = to_delete_set.contains(node.val);
            if (is_root && !deleted)
                res.add(node);
            node.left = helper(node.left, deleted);
            node.right = helper(node.right, deleted);
            return deleted ? null : node;
        }
    }
    
    static class TransformOneStringToAnotherString_ChangingAllOccurrencesOfLetter {
        /**
         * Given 2 strings s and t, determine if you can convert s into t. The rules
         * are:
         * 
         * You can change 1 letter at a time. Once you changed a letter you have to
         * change all occurrences of that letter.
         */
        /**
         * Given two strings str1 and str2 of the same length, determine whether you can transform str1 into str2 by doing zero or more conversions.

            In one conversion you can convert all occurrences of one character in str1 to any other lowercase English character.

            Return true if and only if you can transform str1 into str2.

            Example 1:
            Input: str1 = "aabcc", str2 = "ccdee"
            Output: true
            Explanation: Convert 'c' to 'e' then 'b' to 'd' then 'a' to 'c'. Note that the order of conversions matter.

            Example 2:
            Input: str1 = "leetcode", str2 = "codeleet"
            Output: false
            Explanation: There is no way to transform str1 to str2.

            Note:
            1 <= str1.length == str2.length <= 10^4
            Both str1 and str2 contain only lowercase English letters.
         */
        public boolean canConvert(String str1, String str2) {
            if (str1.equals(str2)) {
                return true;
            }
            Map<Character, Character> map = new HashMap<>();
            for (int i = 0; i < str1.length(); i++) {
                char c1 = str1.charAt(i);
                char c2 = str2.charAt(i);
                if (map.containsKey(c1) && map.get(c1) != c2) {
                    return false;
                }
                map.put(c1, c2);
            }
            return new HashSet<Character>(map.values()).size() < 26;
        }
    }
    
    static class ConfusingNumberII {
        /**
         * We can rotate digits by 180 degrees to form new digits. When 0, 1, 6, 8, 9 are rotated 180 degrees, they become 0, 1, 9, 8, 6 respectively. 
         * When 2, 3, 4, 5 and 7 are rotated 180 degrees, they become invalid.

        A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.(Note that the rotated number can be greater than the original number.)

        Given a positive integer N, return the number of confusing numbers between 1 and N inclusive.

        Example 1:
        Input: 20
        Output: 6
        Explanation: 
        The confusing numbers are [6,9,10,16,18,19].
        6 converts to 9.
        9 converts to 6.
        10 converts to 01 which is just 1.
        16 converts to 91.
        18 converts to 81.
        19 converts to 61.

        Example 2:
        Input: 100
        Output: 19
        Explanation: 
        The confusing numbers are [6,9,10,16,18,19,60,61,66,68,80,81,86,89,90,91,98,99,100].

        Note:
        1 <= N <= 10^9
         */
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        public int confusingNumberII(int N) {
            map.put(0, 0);//Store the rotationally identicals in a map.
            map.put(1, 1);
            map.put(6, 9);
            map.put(8, 8);
            map.put(9, 6);
            helper(N, 0);
            return res;
        }
        private void helper(int N, long cur) {
            if (isConfusingNumber(cur)) {
                res++;
            }
    
            for (Integer i : map.keySet()) {
              long next = cur * 10 + i; // multiply current with 10 and add the integer in the keyset.
              if (next <= N && next != 0) {
                  helper(N, next);//do a DFS traversal on next.
              }
            }
        }
        private boolean isConfusingNumber(long n) {
            long src = n;// 66
            long res = 0;
            // Given 666, this turns 999. It processes the least digit one by one, get it's mapping, and appends it to res in 10s
            while (n > 0) {
                System.out.println("n is "+n);
                System.out.println("n%10 is" + n%10);
                res = res * 10 + map.get((int) n % 10); 
                n /= 10;//0
            }
            return res != src;// 11 ! = 11
        }
        public static void main(String[] args){
            ConfusingNumberII cNumber = new ConfusingNumberII();
            cNumber.confusingNumberII(700);
            System.out.println(cNumber.isConfusingNumber(666));
        }
    }

    static class TwoSumProblem {
        /**
         * Given an array of integers nums and an integer target, return indices of the
         * two numbers such that they add up to target.
         * 
         * You may assume that each input would have exactly one solution, and you may
         * not use the same element twice.
         * 
         * You can return the answer in any order.
         * 
         * Example 1:
         * 
         * Input: nums = [2,7,11,15], target = 9 Output: [0,1] Output: Because nums[0] +
         * nums[1] == 9, we return [0, 1]. Example 2:
         * 
         * Input: nums = [3,2,4], target = 6 Output: [1,2] Example 3:
         * 
         * Input: nums = [3,3], target = 6 Output: [0,1]
         */
        public int[] twoSum(int[] numbers, int target) {
            int[] result = new int[2];
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int i = 0; i < numbers.length; i++) {
                if (map.containsKey(target - numbers[i])) {
                    result[1] = i;
                    result[0] = map.get(target - numbers[i]);
                    return result;
                }
                map.put(numbers[i], i);
            }
            return result;
        }
    }

    static class SplitArrayIntoConsecutiveSubsequences {
        /**
         * You are given an integer array nums that is sorted in non-decreasing order.
         * 
         * Determine if it is possible to split nums into one or more subsequences such
         * that both of the following conditions are true:
         * 
         * Each subsequence is a consecutive increasing sequence (i.e. each integer is
         * exactly one more than the previous integer). All subsequences have a length
         * of 3 or more. Return true if you can split nums according to the above
         * conditions, or false otherwise.
         * 
         * A subsequence of an array is a new array that is formed from the original
         * array by deleting some (can be none) of the elements without disturbing the
         * relative positions of the remaining elements. (i.e., [1,3,5] is a subsequence
         * of [1,2,3,4,5] while [1,3,2] is not).
         * 
         * 
         * Example 1:
         * 
         * Input: nums = [1,2,3,3,4,5] Output: true Explanation: nums can be split into
         * the following subsequences: [1,2,3,3,4,5] --> 1, 2, 3 [1,2,3,3,4,5] --> 3, 4,
         * 5 Example 2:
         * 
         * Input: nums = [1,2,3,3,4,4,5,5] Output: true Explanation: nums can be split
         * into the following subsequences: [1,2,3,3,4,4,5,5] --> 1, 2, 3, 4, 5
         * [1,2,3,3,4,4,5,5] --> 3, 4, 5 Example 3:
         * 
         * Input: nums = [1,2,3,4,4,5] Output: false Explanation: It is impossible to
         * split nums into consecutive increasing subsequences of length 3 or more.
         * 
         */
        public boolean isPossible(int[] nums) {
            // Basically a number when an existing group is already available, join to it and set vacancy for the next number, and set this current number as set/occupied.
            // we will need to create a new group if an existing group is not already available, if the group has formed, create vacancy for the next number, and join the number to the group.

            // This hashmap tells us about whether a number in num is available for a job or not
            HashMap<Integer,Integer> avaibilityMap = new HashMap<>();
            
            // This hashmap tells a number (say x), if there is a job vacancy for them
            HashMap<Integer,Integer> wantMap = new HashMap<>();
            
            // We store the count of every num in nums into avaibilityMap. Basically, a number's count is the avaibility of it.
            for(int i : nums){
                avaibilityMap.put(i, avaibilityMap.getOrDefault(i,0)+1);
            }
            
            // We iterate through each number in the nums array. Remember the story ? So, treat them like a person.
            for(int i=0;i<nums.length;i++){
                // First we check if our current num/person is available. If it is not we just continue with next num/person
                if(avaibilityMap.get(nums[i])<=0){
                    continue;
                }
                
                // If the person is available, first we check if there is a job vacancy for him/her. Basically, is someone looking for him/her?
                else if(wantMap.getOrDefault(nums[i],0)>0){
                    // Yes, someone is looking, so we decrease the avaibility count of that number
                    avaibilityMap.put(nums[i], avaibilityMap.getOrDefault(nums[i],0)-1);
                    
                    // we also decrease its count from the job vacancy space / wantMap
                    wantMap.put(nums[i], wantMap.getOrDefault(nums[i],0)-1);
                    
                    // Then as a goodwill, he/she will also create a job vacancy for (num[i]+1) in job vacancy space / wantMap, as we need consecutive numbers only
                    wantMap.put(nums[i]+1, wantMap.getOrDefault(nums[i]+1,0)+1);
                }
                
                // Ooh, we are here means nums[i] was not able to find a job.
                // so, nums[i] tries to start his/her own company by checking avaibility of his/her friends i.e. (nums[i]+1) and (nums[i]+2)
                else if(avaibilityMap.getOrDefault(nums[i],0)>0 && avaibilityMap.getOrDefault(nums[i]+1,0)>0 && avaibilityMap.getOrDefault(nums[i]+2,0)>0){

                
                    
                    // Yay! both 2 friends are available. Let's start a company.
                    // So we decrease the avaibility count of nums[i], (nums[i]+1) and (nums[i]+2) from the 
                    // avaibilityMap
                    avaibilityMap.put(nums[i], avaibilityMap.getOrDefault(nums[i],0)-1);
                    avaibilityMap.put(nums[i]+1, avaibilityMap.getOrDefault(nums[i]+1,0)-1);
                    avaibilityMap.put(nums[i]+2, avaibilityMap.getOrDefault(nums[i]+2,0)-1);
                    
                    // Also, as a goodwill, we create a new job vacancy for (nums[i]+3), as we need consecutive numbers only
                    wantMap.put(nums[i]+3, wantMap.getOrDefault(nums[i]+3,0)+1);
                }
                
                // Bad luck case, nums[i] not able to start his/her company, so just return false
                else{
                    return false;
                }
            }
            
            // All good till here so we return true
            return true;
        }
    }

    static class CheckIfAnArrayCanBeSplitIntoSubsetsOfKConsecutiveElements_HandsOfStraights {
        /**
         * Given an array arr[] and integer K, the task is to split the array into subsets of size K, such that each subset consists of K consecutive elements.

            Examples: 

            Input: arr[] = {1, 2, 3, 6, 2, 3, 4, 7, 8}, K = 3 
            Output: true 
            Explanation: 
            The given array of length 9 can be split into 3 subsets {1, 2, 3}, {2, 3, 4} and {6, 7, 8} such that each subset consists of 3 consecutive elements.

            Input: arr[] = [1, 2, 3, 4, 5], K = 4 
            Output: false 
            Explanation: 
            The given array of length 5 cannot be split into subsets of 4. 
         */
        /**
         * Store the frequencies of all array elements in a HashMap
            Traverse the TreeMap.
            For every element present in the TreeMap, check if all its occurrences can be grouped in a subsets with its next (K – 1) consecutive elements or not. 
            If so, reduce the frequencies of the elements included in the subsets accordingly (by number of the current eleemnt's occurrences) in the TreeMap and proceed forward.
            
            If any element is found which cannot be grouped into a subset of K consecutive elements, print False. Otherwise print True.
         */
        static boolean groupInKConsecutive(int[] arr, int K) {
            // Stores the frequencies of
            // array elements
            TreeMap<Integer, Integer> elementToFrequencies = new TreeMap<Integer, Integer>();

            for (int h : arr) {
                if (elementToFrequencies.containsKey(h))
                    elementToFrequencies.put(h, elementToFrequencies.get(h) + 1);
                else
                    elementToFrequencies.put(h, 1);
            }

            // Traverse the map
            for (Map.Entry<Integer, Integer> c : elementToFrequencies.entrySet()) {
                int currElement = c.getKey();
                int elementCount = c.getValue();

                // Check if all its occurrences can
                // be grouped into K subsets
                if (elementCount > 0) {

                    // Traverse next K elements
                    for (int i = 1; i < K; ++i) { // Traverse the next K elements in the sorted key set. 

                        // If the element is not
                        // present in the array
                        if (!elementToFrequencies.containsKey(currElement + i)) {
                            return false;
                        }

                        elementToFrequencies.put(currElement + i, elementToFrequencies.get(currElement + i) - elementCount);
                        //reduce the subsequent elements by the element count.

                        // If it cannot be split into
                        // required number of subsets
                        if (elementToFrequencies.get(currElement + i) < 0)
                        //If we can't divide it into subsets, return false.
                            return false;
                    }
                }
            }
            return true;
        }
    }

    static class LoggerRateLimiterusingLinkedHashMap {
        /**
         * Design a logger system that receive stream of messages along with its
         * timestamps, each message should be printed if and only if it is not printed
         * in the last 10 seconds.
         * 
         * Given a message and a timestamp (in seconds granularity), return true if the
         * message should be printed in the given timestamp, otherwise returns false.
         * 
         * It is possible that several messages arrive roughly at the same time.
         * 
         * 
         */
        public class Logger {

            public Map<String, Integer> map;
            int lastSecond = 0;
        
            /** Initialize your data structure here. */
            public Logger() {
                map = new java.util.LinkedHashMap<String, Integer>(100, 0.6f, true) {
                    // the last param sets the hashmap to access ordering from least accessed entry to most recently accessed entry. 
                    // The last elements in the map will be the most recently used ones.
                    protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                        //remove the stale entry.
                        return lastSecond - eldest.getValue() > 10;
                    }
                };
            }
        
            /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
                If this method returns false, the message will not be printed.
                The timestamp is in seconds granularity. */
            public boolean shouldPrintMessage(int timestamp, String message) {
                lastSecond = timestamp;
                if(!map.containsKey(message)||timestamp - map.get(message) >= 10){//If the message is new, or not printed in the last 10 seconds
                    map.put(message,timestamp);
                    return true;
                }
                return false;
            }
        }
    }

    static class OpenTheLockUsingBFS {
        /**
         * You have a lock in front of you with 4 circular wheels. Each wheel has 10
         * slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can
         * rotate freely and wrap around: for example we can turn '9' to be '0', or '0'
         * to be '9'. Each move consists of turning one wheel one slot.
         * 
         * The lock initially starts at '0000', a string representing the state of the 4
         * wheels.
         * 
         * You are given a list of deadends dead ends, meaning if the lock displays any
         * of these codes, the wheels of the lock will stop turning and you will be
         * unable to open it.
         * 
         * Given a target representing the value of the wheels that will unlock the
         * lock, return the minimum total number of turns required to open the lock, or
         * -1 if it is impossible.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
         * Output: 6 Explanation: A sequence of valid moves would be "0000" -> "1000" ->
         * "1100" -> "1200" -> "1201" -> "1202" -> "0202". Note that a sequence like
         * "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid, because the
         * wheels of the lock become stuck after the display becomes the dead end
         * "0102". Example 2:
         * 
         * Input: deadends = ["8888"], target = "0009" Output: 1 Explanation: We can
         * turn the last wheel in reverse to move from "0000" -> "0009". Example 3:
         * 
         * Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"],
         * target = "8888" Output: -1 Explanation: We can't reach the target without
         * getting stuck. Example 4:
         * 
         * Input: deadends = ["0000"], target = "8888" Output: -1
         */
        public int openLock(String[] deadends, String target) {
            Queue<String> q = new LinkedList<>();
            Set<String> deads = new HashSet<>(Arrays.asList(deadends));
            Set<String> visited = new HashSet<>();
            q.offer("0000");
            visited.add("0000");
            int level = 0;
            while(!q.isEmpty()) {
                int size = q.size();
                while(size > 0) {
                    String s = q.poll();
                    if(deads.contains(s)) {
                        size --;
                        continue;
                    }
                    if(s.equals(target)) return level;
                    StringBuilder sb = new StringBuilder(s);
                    for(int i = 0; i < 4; i ++) {
                        char c = sb.charAt(i);
                        String s1 = sb.substring(0, i) + (c == '9' ? 0 : c - '0' + 1) + sb.substring(i + 1);
                        String s2 = sb.substring(0, i) + (c == '0' ? 9 : c - '0' - 1) + sb.substring(i + 1);
                        if(!visited.contains(s1) && !deads.contains(s1)) {
                            q.offer(s1);
                            visited.add(s1);
                        }
                        if(!visited.contains(s2) && !deads.contains(s2)) {
                            q.offer(s2);
                            visited.add(s2);
                        }
                    }
                    size --;
                }
                level ++;
            }
            return -1;
        }
    }

    static class BullsAndCows {
        /**
         * You are playing the Bulls and Cows game with your friend.
         * 
         * You write down a secret number and ask your friend to guess what the number
         * is. When your friend makes a guess, you provide a hint with the following
         * info:
         * 
         * The number of "bulls", which are digits in the guess that are in the correct
         * position. The number of "cows", which are digits in the guess that are in
         * your secret number but are located in the wrong position. Specifically, the
         * non-bull digits in the guess that could be rearranged such that they become
         * bulls. Given the secret number secret and your friend's guess guess, return
         * the hint for your friend's guess.
         * 
         * The hint should be formatted as "xAyB", where x is the number of bulls and y
         * is the number of cows. Note that both secret and guess may contain duplicate
         * digits.
         * 
         * Example 1:
         * 
         * Input: secret = "1807", guess = "7810" Output: "1A3B" Explanation: Bulls are
         * connected with a '|' and cows are underlined: "1807" | "7810" Example 2:
         * 
         * Input: secret = "1123", guess = "0111" Output: "1A1B" Explanation: Bulls are
         * connected with a '|' and cows are underlined: "1123" "1123" | or | "0111"
         * "0111" Note that only one of the two unmatched 1s is counted as a cow since
         * the non-bull digits can only be rearranged to allow one 1 to be a bull.
         * Example 3:
         * 
         * Input: secret = "1", guess = "0" Output: "0A0B" Example 4:
         * 
         * Input: secret = "1", guess = "1" Output: "1A0B"
         */
        public String getHint(String secret, String guess) {
            //table records the appearance of a digit

            // Because the equivalent characters are considered, 
            // we use this method of counter to track whether the digit is found in the secret/guess. 

            //digit from secret will increase the counter
            //digit from guess will decrease the counter
            int[] count = new int[10];
            
            int counterA = 0, counterB = 0;
            
            for(int i = 0; i < secret.length(); i++){
                int a = secret.charAt(i) - '0', b = guess.charAt(i) - '0';
                if( a == b){
                    //accurate match (same digit with same position)
                    counterA ++;
                }else{
                    
                    //if prev part of guess has curr digit in secret
                    //then we found a pair that has same digit with different position
                    if(count[a] < 0) counterB ++;
                    
                    //if prev part of secret has curr digit in guess
                    //then we found a pair that has same digit with different position                
                    if(count[b] > 0) counterB ++;
                    
                    count[a] ++; // secret digit increments the counter. 
                    count[b] --; // guess digit decrements the counter. 
                }
            }
            
            return counterA + "A" + counterB + "B";
        }
    }

    static class LongestContinousSubarrayWithAbsDiffLessThanOrEqualToLimit
    {
        /**Given an array of integers nums and an integer limit, return the size of the longest non-empty subarray such that the absolute difference between any two elements of this subarray is less than or equal to limit.
        Example 1:

        Input: nums = [8,2,4,7], limit = 4
        Output: 2 
        Explanation: All subarrays are: 
        [8] with maximum absolute diff |8-8| = 0 <= 4.
        [8,2] with maximum absolute diff |8-2| = 6 > 4. 
        [8,2,4] with maximum absolute diff |8-2| = 6 > 4.
        [8,2,4,7] with maximum absolute diff |8-2| = 6 > 4.
        [2] with maximum absolute diff |2-2| = 0 <= 4.
        [2,4] with maximum absolute diff |2-4| = 2 <= 4.
        [2,4,7] with maximum absolute diff |2-7| = 5 > 4.
        [4] with maximum absolute diff |4-4| = 0 <= 4.
        [4,7] with maximum absolute diff |4-7| = 3 <= 4.
        [7] with maximum absolute diff |7-7| = 0 <= 4. 
        Therefore, the size of the longest subarray is 2.
        Example 2:

        Input: nums = [10,1,2,4,7,2], limit = 5
        Output: 4 
        Explanation: The subarray [2,4,7,2] is the longest since the maximum absolute diff is |2-7| = 5 <= 5.
        Example 3:

        Input: nums = [4,2,2,2,4,4,2,2], limit = 0
        Output: 3 */
        // Function that prints the longest sub-array
        // where the absolute difference between any
        // two element is not greater than X
        static void longestSubarray(int A[], int N, int X) {

            // Initialize a variable to store
            // length of longest sub-array
            int maxLen = 0;

            // Initialize a variable to store the
            // beginning of the longest sub-array
            int beginning = 0;

            // Initialize a map to store the maximum
            // and the minimum elements for a given window
            TreeMap<Integer, Integer> window = new TreeMap<>();

            // Initialize the window
            int start = 0, end = 0;

            // Loop througth the array
            for (; end < N; end++) {

                // Increment the count of that
                // element in the window
                window.put(A[end], window.getOrDefault(A[end], 0) + 1);

                // Find the maximum and minimum element
                // in the current window
                int minimum = window.firstKey();
                int maximum = window.lastKey();

                // If the difference is not
                // greater than X
                if (maximum - minimum <= X) {

                    // Update the length of the longest
                    // sub-array and store the beginning
                    // of the sub-array
                    if (maxLen < end - start + 1) {
                        maxLen = end - start + 1;
                        beginning = start;
                    }
                }

                // Decrease the size of the window
                else {
                    while (start < end) {

                        // Remove the element at start
                        window.put(A[start], window.get(A[start]) - 1);

                        // Remove the element from the window
                        // if its count is zero
                        if (window.get(A[start]) == 0) {

                            window.remove(A[start]);
                        }

                        // Increment the start of the window
                        start++;

                        // Find the maximum and minimum element
                        // in the current window
                        minimum = window.firstKey();
                        maximum = window.lastKey();

                        // Stop decreasing the size of window
                        // when difference is not greater
                        if (maximum - minimum <= X)
                            break;
                    }
                }
            }

            // Print the longest sub-array
            for (int i = beginning; i < beginning + maxLen; i++)
                System.out.print(A[i] + " ");
        }

        // Driver Code
        public static void main(String[] args) {

            // Given array
            int arr[] = { 15, 10, 1, 2, 4, 7, 2 }, X = 5;

            // store the size of the array
            int n = arr.length;

            // Function call
            longestSubarray(arr, n, X);
        }
    }

    static class LongestContinousSubarrayInBinaryArrayWithEqual1sAnd0s {
        /**
         * Given a binary array nums, return the maximum length of a contiguous subarray
         * with an equal number of 0 and 1.
         * 
         * Example 1:
         * 
         * Input: nums = [0,1] Output: 2 Explanation: [0, 1] is the longest contiguous
         * subarray with an equal number of 0 and 1. Example 2:
         * 
         * Input: nums = [0,1,0] Output: 2 Explanation: [0, 1] (or [1, 0]) is a longest
         * contiguous subarray with equal number of 0 and 1.
         */
        /**
         * 
         * Approach 1:
         * 
         * 
         * In this approach, we make use of a countcount variable, which is used to
         * store the relative number of ones and zeros encountered so far while
         * traversing the array. The countcount variable is incremented by one for every
         * \text{1}1 encountered and the same is decremented by one for every \text{0}0
         * encountered.
         * 
         * We start traversing the array from the beginning. If at any moment, the
         * countcount becomes zero, it implies that we've encountered equal number of
         * zeros and ones from the beginning till the current index of the array(ii).
         * Not only this, another point to be noted is that if we encounter the same
         * countcount twice while traversing the array, it means that the number of
         * zeros and ones are equal between the indices corresponding to the equal
         * countcount values. The following figure illustrates the observation for the
         * sequence [0 0 1 0 0 0 1 1]:
         * 
         * Contiguous_Array
         * 
         * In the above figure, the subarrays between (A,B), (B,C) and (A,C) (lying
         * between indices corresponing to count = 2count=2) have equal number of zeros
         * and ones.
         * 
         * Another point to be noted is that the largest subarray is the one between the
         * points (A, C). Thus, if we keep a track of the indices corresponding to the
         * same countcount values that lie farthest apart, we can determine the size of
         * the largest subarray with equal no. of zeros and ones easily.
         * 
         * Now, the countcount values can range between \text{-n}-n to \text{+n}+n, with
         * the extreme points corresponding to the complete array being filled with all
         * 0's and all 1's respectively. Thus, we make use of an array arrarr(of size
         * \text{2n+1}2n+1to keep a track of the various countcount's encountered so
         * far. We make an entry containing the current element's index (ii) in the
         * arrarr for a new countcount encountered everytime. Whenever, we come across
         * the same countcount value later while traversing the array, we determine the
         * length of the subarray lying between the indices corresponding to the same
         * countcount values.
         */
        public int findMaxLengthUsingCountArray(int[] nums) {
            int[] arr = new int[2 * nums.length + 1];
            Arrays.fill(arr, -2);
            arr[nums.length] = -1;
            int maxlen = 0, count = 0;
            for (int i = 0; i < nums.length; i++) {
                count = count + (nums[i] == 0 ? -1 : 1);
                if (arr[count + nums.length] >= -1) {
                    maxlen = Math.max(maxlen, i - arr[count + nums.length]);
                    // To keep track of the indices corresponding to the same count values that lie farthest apart.
                    // We can determine the size of the largest subarray with equal no of zeros and ones easily.
                } else {
                    // For a new count encountered everytime, we make an entry containing the current element's index in the arr,
                    // so that it can be used next.
                    arr[count + nums.length] = i;
                }
    
            }
            return maxlen;
        }

        /**
         * We make an entry for a count in the map whenever the count is
         * encountered first, and later on use the correspoding index to find the length
         * of the largest subarray with equal no. of zeros and ones when the same
         * count is encountered again.
         */
        public int findMaxLengthUsingMap(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, -1);
            int maxlen = 0, count = 0;
            for (int i = 0; i < nums.length; i++) {
                count = count + (nums[i] == 1 ? 1 : -1);
                if (map.containsKey(count)) {
                    maxlen = Math.max(maxlen, i - map.get(count));
                } else {
                    map.put(count, i);
                }
            }
            return maxlen;
        }
        /**
         * Example
         *  Index  0  1  2  3  4  5  6
         *  Arr = [0, 1, 0, 0, 1, 1, 0]
         * 
         * Initial: Count = 0 Maxlen = 0, Map: {0, -1}
         * 0: count=-1, maxlen=0, map:{(0,-1), (-1, 0)}
         * 1: count=0, maxlen=2, map: {(0,-1), (-1, 0)}
         * 0: count=-1, maxlen=2, map: {(0,-1),(-1,0)}
         * 0: count=-2, maxlen=2, map:{(0,-1),(-1,0),(-2,3)}
         * 4: count=-1, maxlen=4, map:{(0,-1),(-1,0),(-2,3)}
         * 1: count=0, maxlen=6, map:{(0,-1),(-1,0),(-2,3)}
         * 0: count=-1, maxlen=6, map:{(0,-1, (-1,0), (-2,3))}
         */
    }
    
    static class ShuffleAnArray {
        /**
         * Given an integer array nums, design an algorithm to randomly shuffle the
         * array. All permutations of the array should be equally likely as a result of
         * the shuffling.
         * 
         * Implement the Solution class:
         * 
         * Solution(int[] nums) Initializes the object with the integer array nums.
         * int[] reset() Resets the array to its original configuration and returns it.
         * int[] shuffle() Returns a random shuffling of the array.
         * 
         * 
         * Example 1:
         * 
         * Input ["Solution", "shuffle", "reset", "shuffle"] [[[1, 2, 3]], [], [], []]
         * Output [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
         * 
         * Explanation Solution solution = new Solution([1, 2, 3]); solution.shuffle();
         * // Shuffle the array [1,2,3] and return its result. // Any permutation of
         * [1,2,3] must be equally likely to be returned. // Example: return [3, 1, 2]
         * solution.reset(); // Resets the array back to its original configuration
         * [1,2,3]. Return [1, 2, 3] solution.shuffle(); // Returns the random shuffling
         * of array [1,2,3]. Example: return [1, 3, 2]
         * 
         * Solution: 
         * On each iteration of the algorithm, we generate a random integer between the
         * current index and the last index of the array. Then, we swap the elements at
         * the current index and the chosen index - this simulates drawing (and
         * removing) the element from the hat, as the next range from which we select a
         * random index will not include the most recently processed one. One small, yet
         * important detail is that it is possible to swap an element with itself -
         * otherwise, some array permutations would be more likely than others.
         */
        private int[] array;
        private int[] original;

        Random rand = new Random();

        private int randRange(int min, int max) {
            return rand.nextInt(max - min) + min;
        }

        private void swapAt(int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        public ShuffleAnArray(int[] nums) {
            array = nums;
            original = nums.clone();
        }

        public int[] reset() {
            array = original;
            original = original.clone();
            return original;
        }

        public int[] shuffle() {
            for (int i = 0; i < array.length; i++) {
                swapAt(i, randRange(i, array.length));
            }
            return array;
        }
    }

    static class CheckIfAnArrayCanBecomeNonDecreasingArrayWithAtmost1Change {
        /**
         * Given an array nums with n integers, your task is to check if it could become
         * non-decreasing by modifying at most one element.
         * 
         * We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for
         * every i (0-based) such that (0 <= i <= n - 2).
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: nums = [4,2,3] Output: true Explanation: You could modify the first 4
         * to 1 to get a non-decreasing array. Example 2:
         * 
         * Input: nums = [4,2,1] Output: false Explanation: You can't get a
         * non-decreasing array by modify at most one element.
         */
        /**
         * The problem requires that every number has to be equal or greater than
         * previous number. If we encounter a failing condition where the number is not
         * greater or equal to previous (smaller than previous) we need to make a
         * correction. Correction can be made in either of two ways:
         * 
         * Make the previous number smaller or equal to current number Make the current
         * number equal to previous number We can do (1) as long as the number at
         * position i-2 is equal or lower than the current element. (if i-2 is valid) In
         * case 1 below we can do this at (3) (i = 2) as the element 1 (i = 0) fulfills
         * 1 <= 3. We can replace 7 with 3. However, this cannot be done in case 2 as 4
         * <= 3 does not satisfy.
         * 
         * Correction with technique (1) takes priority as there is no risk in lowering
         * the value but there is a risk associated if the value is increased. (Consider
         * scenario in case 1 if we replace 3 with 7, it will fail to satisfy the
         * condition for the last element)
         * 
         * We have to make correction with (2) if we cannot achieve it by (1). In which
         * case we increase the value of current element by matching previous element.
         * In case 2, we replace 3 with 7.
         * 
         * Also we only compare condition with the previous element only because as we
         * move forward we know the previous numbers are already validated .
         **/
                    /**
            Case 1:
                7
                /\    4
                /  \  /
            /    \/
            /      3
            1
            
            Case 2:

                        9
                        /
            7          /
            /\        /
            /  \      /
            /    \    /
            4      \  /
                \/
                3(i)
                    */
        public boolean checkPossibility(int[] nums) {
            int cnt = 0;                                                                    //the number of changes
            for(int i = 1; i < nums.length && cnt<=1 ; i++){
                if(nums[i-1] > nums[i]){
                    cnt++;
                    if(i-2<0 || nums[i-2] <= nums[i])nums[i-1] = nums[i];                    //modify nums[i-1] of a priority
                    else nums[i] = nums[i-1];                                                //have to modify nums[i]
                }
            }
            return cnt<=1; 
        }
    }

    static class MergeSortedArray {
        /**
         * You are given two integer arrays nums1 and nums2, sorted in non-decreasing
         * order, and two integers m and n, representing the number of elements in nums1
         * and nums2 respectively.
         * 
         * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
         * 
         * The final sorted array should not be returned by the function, but instead be
         * stored inside the array nums1. To accommodate this, nums1 has a length of m +
         * n, where the first m elements denote the elements that should be merged, and
         * the last n elements are set to 0 and should be ignored. nums2 has a length of
         * n.
         * 
         * Example 1:
         * 
         * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3 Output:
         * [1,2,2,3,5,6] Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
         * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming
         * from nums1. Example 2:
         * 
         * Input: nums1 = [1], m = 1, nums2 = [], n = 0 Output: [1] Explanation: The
         * arrays we are merging are [1] and []. The result of the merge is [1]. Example
         * 3:
         * 
         * Input: nums1 = [0], m = 0, nums2 = [1], n = 1 Output: [1] Explanation: The
         * arrays we are merging are [] and [1]. The result of the merge is [1]. Note
         * that because m = 0, there are no elements in nums1. The 0 is only there to
         * ensure the merge result can fit in nums1.
         */
        void merge(int A[], int m, int B[], int n) {
            int i=m-1;
            int j=n-1;
            int k = m+n-1;
            while(i >=0 && j>=0)
            {
                if(A[i] > B[j])
                    A[k--] = A[i--];
                else
                    A[k--] = B[j--];
            }
            while(j>=0)
                A[k--] = B[j--];
        }
    }

    static class ProductOfArrayExceptSelf {
        /**
         * Given an integer array nums, return an array answer such that answer[i] is
         * equal to the product of all the elements of nums except nums[i].
         * 
         * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit
         * integer.
         * 
         * You must write an algorithm that runs in O(n) time and without using the
         * division operation.
         * 
         * 
         * Example 1:
         * 
         * Input: nums = [1,2,3,4] Output: [24,12,8,6] Example 2:
         * 
         * Input: nums = [-1,1,0,-3,3] Output: [0,0,9,0,0]
         */
        /**
         * Given numbers [2, 3, 4, 5], regarding the third number 4, the product of array except 4 is 2*3*5 
         * which consists of two parts: left 2*3 and right 5. The product is left*right. 
         * We can get lefts and rights:

            Numbers:     2    3    4     5
            Lefts:            2  2*3 2*3*4
            Rights:  3*4*5  4*5    5      
            Let’s fill the empty with 1:

            Numbers:     2    3    4     5
            Lefts:       1    2  2*3 2*3*4
            Rights:  3*4*5  4*5    5     1
            We can calculate lefts and rights in 2 loops. The time complexity is O(n).

            We store lefts in result array. If we allocate a new array for rights. 
            The space complexity is O(n). To make it O(1), we just need to store it in a variable which is right
        */
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int[] res = new int[n];
            res[0] = 1;
            for (int i = 1; i < n; i++) {
                res[i] = res[i - 1] * nums[i - 1];
            }
            int right = 1;
            for (int i = n - 1; i >= 0; i--) {
                res[i] *= right;
                right *= nums[i];
            }
            return res;
        }

    }
    
    static class SquaresOfSortedArray {
        /**
         * Given an integer array nums sorted in non-decreasing order, return an array
         * of the squares of each number sorted in non-decreasing order.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: nums = [-4,-1,0,3,10] Output: [0,1,9,16,100] Explanation: After
         * squaring, the array becomes [16,1,0,9,100]. After sorting, it becomes
         * [0,1,9,16,100]. Example 2:
         * 
         * Input: nums = [-7,-3,2,3,11] Output: [4,9,9,49,121]
         */
        public int[] sortedSquares(int[] A) {
            int n = A.length;
            int[] result = new int[n];
            int i = 0, j = n - 1;
            for (int p = n - 1; p >= 0; p--) {
                if (Math.abs(A[i]) > Math.abs(A[j])) {
                    result[p] = A[i] * A[i];
                    i++;
                } else {
                    result[p] = A[j] * A[j];
                    j--;
                }
            }
            return result;
        }
    }
    
    static class RemoveDuplicatesFromSortedArray {
        /**
         * Given an integer array nums sorted in non-decreasing order, remove the
         * duplicates in-place such that each unique element appears only once. The
         * relative order of the elements should be kept the same.
         * 
         * Since it is impossible to change the length of the array in some languages,
         * you must instead have the result be placed in the first part of the array
         * nums. More formally, if there are k elements after removing the duplicates,
         * then the first k elements of nums should hold the final result. It does not
         * matter what you leave beyond the first k elements.
         * 
         * Return k after placing the final result in the first k slots of nums.
         * 
         * Do not allocate extra space for another array. You must do this by modifying
         * the input array in-place with O(1) extra memory.
         * 
         * Custom Judge:
         * 
         * The judge will test your solution with the following code:
         * 
         * int[] nums = [...]; // Input array int[] expectedNums = [...]; // The
         * expected answer with correct length
         * 
         * int k = removeDuplicates(nums); // Calls your implementation
         * 
         * assert k == expectedNums.length; 
         * for (int i = 0; i < k; i++) { 
         * assert nums[i] == expectedNums[i]; 
         * } 
         * 
         * If all assertions pass, then your solution will be
         * accepted.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: nums = [1,1,2] Output: 2, nums = [1,2,_] Explanation: Your function
         * should return k = 2, with the first two elements of nums being 1 and 2
         * respectively. It does not matter what you leave beyond the returned k (hence
         * they are underscores). Example 2:
         * 
         * Input: nums = [0,0,1,1,1,2,2,3,3,4] Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
         * Explanation: Your function should return k = 5, with the first five elements
         * of nums being 0, 1, 2, 3, and 4 respectively. It does not matter what you
         * leave beyond the returned k (hence they are underscores).
         */

         // nums = [1,1,2]
         public int removeDuplicates(int[] A, int n) {
             int count = 0;
             for (int i = 1; i < n; i++) {
                 if (A[i] == A[i - 1])
                     count++;
                 else
                     A[i - count] = A[i];
                     // Go back by 1 element and set 2 there.
             }
             return n - count;
         }
    }
    
    static class TimeNeededToInformAllEmployeesUsingDFS {
        /**
         * A company has n employees with a unique ID for each employee from 0 to n - 1. The head of the company is the one with headID.

        Each employee has one direct manager given in the manager array where manager[i] is the direct manager of the i-th employee, manager[headID] = -1. Also, it is guaranteed that the subordination relationships have a tree structure.

        The head of the company wants to inform all the company employees of an urgent piece of news. He will inform his direct subordinates, and they will inform their subordinates, and so on until all employees know about the urgent news.

        The i-th employee needs informTime[i] minutes to inform all of his direct subordinates (i.e., After informTime[i] minutes, all his direct subordinates can start spreading the news).

        Return the number of minutes needed to inform all the employees about the urgent news.

        

        Example 1:

        Input: n = 1, headID = 0, manager = [-1], informTime = [0]
        Output: 0
        Explanation: The head of the company is the only employee in the company.
        Example 2:


        Input: n = 6, headID = 2, manager = [2,2,-1,2,2,2], informTime = [0,0,1,0,0,0]
        Output: 1
        Explanation: The head of the company with id = 2 is the direct manager of all the employees in the company and needs 1 minute to inform them all.
        The tree structure of the employees in the company is shown.
        Example 3:


        Input: n = 7, headID = 6, manager = [1,2,3,4,5,6,-1], informTime = [0,6,5,4,3,2,1]
        Output: 21
        Explanation: The head has id = 6. He will inform employee with id = 5 in 1 minute.
        The employee with id = 5 will inform the employee with id = 4 in 2 minutes.
        The employee with id = 4 will inform the employee with id = 3 in 3 minutes.
        The employee with id = 3 will inform the employee with id = 2 in 4 minutes.
        The employee with id = 2 will inform the employee with id = 1 in 5 minutes.
        The employee with id = 1 will inform the employee with id = 0 in 6 minutes.
        Needed time = 1 + 2 + 3 + 4 + 5 + 6 = 21.
        Example 4:

        Input: n = 15, headID = 0, manager = [-1,0,0,1,1,2,2,3,3,4,4,5,5,6,6], informTime = [1,1,1,1,1,1,1,0,0,0,0,0,0,0,0]
        Output: 3
        Explanation: The first minute the head will inform employees 1 and 2.
        The second minute they will inform employees 3, 4, 5 and 6.
        The third minute they will inform the rest of employees.
        Example 5:

        Input: n = 4, headID = 2, manager = [3,3,-1,2], informTime = [0,0,162,914]
        Output: 1076
         */

        /**
         * dfs find out the time needed for each employees.
        The time for a manager = max(manager's employees) + informTime[manager]

        Time O(N), Space O(N)
         */
        public int numOfMinutes(final int n, final int headID, final int[] manager, final int[] informTime) {
            final Map<Integer, List<Integer>> graph = new HashMap<>();
            int total = 0;
            for (int i = 0; i < manager.length; i++) {
                int j = manager[i];
                if (!graph.containsKey(j))
                    graph.put(j, new ArrayList<>());
                graph.get(j).add(i);
            }
            return dfs(graph, informTime, headID);
        }
    
        private int dfs(final Map<Integer, List<Integer>> graph, final int[] informTime, final int cur) {
            int max = 0;
            if (!graph.containsKey(cur))
                return max;
            for (int i = 0; i < graph.get(cur).size(); i++)
                max = Math.max(max, dfs(graph, informTime, graph.get(cur).get(i)));
            return max + informTime[cur];
        }
    }

    static class DivideArrayInSetsOfKConsecutiveNumbers {
        /**Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into sets of k consecutive numbers
            Return True if it is possible. Otherwise, return False.

            Example 1:

            Input: nums = [1,2,3,3,4,4,5,6], k = 4
            Output: true
            Explanation: Array can be divided into [1,2,3,4] and [3,4,5,6].
            Example 2:

            Input: nums = [3,2,1,2,3,4,3,4,5,9,10,11], k = 3
            Output: true
            Explanation: Array can be divided into [1,2,3] , [2,3,4] , [3,4,5] and [9,10,11].
            Example 3:

            Input: nums = [3,3,2,2,1,1], k = 3
            Output: true
            Example 4:

            Input: nums = [1,2,3,4], k = 3
            Output: false
            Explanation: Each array should be divided in subarrays of size 3. */

            // This is same as CheckIfAnArrayCanBeSplitIntoSubsetsOfKConsecutiveElements_HandsOfStraights
    }

    static class SnapshotArray {
        /** Implement a SnapshotArray that supports the following interface:

        SnapshotArray(int length) initializes an array-like data structure with the given length.  Initially, each element equals 0.
        void set(index, val) sets the element at the given index to be equal to val.
        int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
        int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
        

        Example 1:

        Input: ["SnapshotArray","set","snap","set","get"]
        [[3],[0,5],[],[0,6],[0,0]]
        Output: [null,null,0,null,5]
        Explanation: 
        SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
        snapshotArr.set(0,5);  // Set array[0] = 5
        snapshotArr.snap();  // Take a snapshot, return snap_id = 0
        snapshotArr.set(0,6);
        snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
        */
        
        // Time O(logS) Space O(S) where S is the number of set called.
        TreeMap<Integer, Integer>[] A;
        int snap_id = 0;

        public SnapshotArray(int length) {
            A = new TreeMap[length];
            for (int i = 0; i < length; i++) {
                A[i] = new TreeMap<Integer, Integer>();
                A[i].put(0, 0);
            }
        }

        public void set(int index, int val) {
            A[index].put(snap_id, val);
        }

        public int snap() {
            return snap_id++;
        }

        public int get(int index, int snap_id) {
            return A[index].floorEntry(snap_id).getValue();
        }
    }

    static class MovingAverageFromDataStream {
        /** 
         * 
         * Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
        Example:
        1
        2
        3
        4
        5
        MovingAverage m = new MovingAverage(3);
        m.next(1) = 1
        m.next(10) = (1 + 10) / 2
        m.next(3) = (1 + 10 + 3) / 3
        m.next(5) = (10 + 3 + 5) / 3
         * 
        */
        /** Initialize your data structure here. */
        private int size;
        private ArrayList<Integer> array;
        private int sum;

        public MovingAverageFromDataStream(int size) {
            this.size = size;
            this.array = new ArrayList<>();
            this.sum = 0;
        }

        public double next(int val) {
            this.sum += val;
            this.array.add(val);
            if (this.array.size() > this.size) {
                this.sum -= this.array.remove(0);
            }
            return (1.0 * this.sum) / this.array.size();
        }
    }

    static class EvaluateDivision {
        /**
         * You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.

        You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.

        Return the answers to all queries. If a single answer cannot be determined, return -1.0.

        Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.

        Example 1:

        Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
        Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
        Explanation: 
        Given: a / b = 2.0, b / c = 3.0
        queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
        return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
        Example 2:

        Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
        Output: [3.75000,0.40000,5.00000,0.20000]
        Example 3:

        Input: equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
        Output: [0.50000,2.00000,-1.00000,-1.00000]
         */
        public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        
            /* Build graph. */
            Map<String, Map<String, Double>> graph = buildGraph(equations, values);
            double[] result = new double[queries.length];
            
            for (int i = 0; i < queries.length; i++) {
                result[i] = getPathWeight(queries[i][0], queries[i][1], new HashSet<>(), graph);
            }  
            
            return result;
        }
        
        private double getPathWeight(String start, String end, Set<String> visited, Map<String, Map<String, Double>> graph) {
            
            /* Rejection case. */
            if (!graph.containsKey(start)) 
                return -1.0;
            
            /* Accepting case. */
            if (graph.get(start).containsKey(end))
                return graph.get(start).get(end);
            
            visited.add(start);
            for (Map.Entry<String, Double> neighbour : graph.get(start).entrySet()) {
                if (!visited.contains(neighbour.getKey())) {
                    double productWeight = getPathWeight(neighbour.getKey(), end, visited, graph);
                    if (productWeight != -1.0)
                        return neighbour.getValue() * productWeight;
                }
            }
            
            return -1.0;
        }
        
        private Map<String, Map<String, Double>> buildGraph(String[][] equations, double[] values) {
            Map<String, Map<String, Double>> graph = new HashMap<>();
            String u, v;
            
            for (int i = 0; i < equations.length; i++) {
                u = equations[i][0];
                v = equations[i][1];
                graph.putIfAbsent(u, new HashMap<>());
                graph.get(u).put(v, values[i]);
                graph.putIfAbsent(v, new HashMap<>());
                graph.get(v).put(u, 1 / values[i]);
            }
            
            return graph;
        }
    }

    static class FindLargestSquareContainingOnly1sAndReturnItsArea {
        public int maximalSquare(char[][] matrix) {
            /**
             * To appy DP, we define the state as the maximal size (square = size * size) of
             * the square that can be formed till point (i, j), denoted as dp[i][j].
             * 
             * For the topmost row (i = 0) and the leftmost column (j = 0), we have dp[i][j]
             * = matrix[i][j] - '0', meaning that it can at most form a square of size 1
             * when the matrix has a '1' in that cell.
             * 
             * When i > 0 and j > 0, if matrix[i][j] = '0', then dp[i][j] = 0 since no
             * square will be able to contain the '0' at that cell. If matrix[i][j] = '1',
             * we will have dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1, which
             * means that the square will be limited by its left, upper and upper-left
             * neighbors.
             */
            if(matrix.length == 0)
                return 0;
            int m = matrix.length, n = matrix[0].length, sz = 0;
            int[][] dp = new int[m][n];

            for(int[] row: dp)
                Arrays.fill(row, 0);
            
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i==0 || j==0 || matrix[i][j] == '0') {
                        dp[i][j] = matrix[i][j] - '0';
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    }
                    sz = Math.max(dp[i][j], sz);
                }
            }
            return sz*sz;
        }
    }

    static class SequenceReconstruction_constructShortestSuperSequence_fromSequences {
        /**
         * Check whether the original sequence org can be uniquely reconstructed from
         * the sequences in seqs. The org sequence is a permutation of the integers from
         * 1 to n, with 1 ≤ n ≤ 104. Reconstruction means building a shortest common
         * supersequence of the sequences in seqs (i.e., a shortest sequence so that all
         * sequences in seqs are subsequences of it). Determine whether there is only
         * one sequence that can be reconstructed from seqs and it is the org sequence.
         * 
         * Example 1:
         * 
         * Input: org: [1,2,3], seqs: [[1,2],[1,3]]
         * 
         * Output: false
         * 
         * Explanation: [1,2,3] is not the only one sequence that can be reconstructed,
         * because [1,3,2] is also a valid sequence that can be reconstructed. Example
         * 2:
         * 
         * Input: org: [1,2,3], seqs: [[1,2]]
         * 
         * Output: false
         * 
         * Explanation: The reconstructed sequence can only be [1,2]. Example 3:
         * 
         * Input: org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]
         * 
         * Output: true
         * 
         * Explanation: The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct
         * the original sequence [1,2,3]. Example 4:
         * 
         * Input: org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]
         * 
         * Output: true
         * 
         */
    }

    static class StoneGame_FindMaxStonesScoresPlayerCanGet_Using_DP_and_DFS {
        /**
         * Alice and Bob continue their games with piles of stones.  There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i].  The objective of the game is to end with the most stones. 

        Alice and Bob take turns, with Alice starting first.  Initially, M = 1.

        On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M.  Then, we set M = max(M, X).

        The game continues until all the stones have been taken.

        Assuming Alice and Bob play optimally, return the maximum number of stones Alice can get.

        Example 1:

        Input: piles = [2,7,9,4,4]
        Output: 10
        Explanation:  
        If Alice takes one pile at the beginning, Bob takes two piles, then Alice takes 2 piles again. 
        Alice can get 2 + 4 + 4 = 10 piles in total. 
        If Alice takes two piles at the beginning, then Bob can take all three piles left. 
        In this case, Alice get 2 + 7 = 9 piles in total. So we return 10 since it's larger. 
        Example 2:

        Input: piles = [1,2,3,4,5,100]
        Output: 104
         */
        // the sum from piles[i] to the end
        private int[] sums;

        // hash[i][M] store Alex max score from pile[i] for the given M
        // i range (0, n)
        // M range (0, n), actually M can at most reach to n/2
        private int[][] hash;

        public int stoneGameII(int[] piles) {

            if (piles == null || piles.length == 0)
                return 0;

            int n = piles.length;
            sums = new int[n];
            sums[n - 1] = piles[n - 1];

            // the sum from piles[i] to the end
            for (int i = n - 2; i >= 0; i--) {
                sums[i] = sums[i + 1] + piles[i];
            }

            hash = new int[n][n];
            return helper(piles, 0, 1);
        }

        // helper method return the Alex max score from pile[i] for the given M
        private int helper(int[] a, int i, int M) {
            // base case
            if (i == a.length)
                return 0;
            // when the left number of piles is less then 2M, the player can get all of them
            if (2 * M >= a.length - i) {
                return sums[i];
            }
            // already seen before
            if (hash[i][M] != 0)
                return hash[i][M];

            // the min value the next player can get
            int min = Integer.MAX_VALUE;
            for (int x = 1; x <= 2 * M; x++) {
                min = Math.min(min, helper(a, i + x, Math.max(M, x)));
            }

            // Alex max stones = all the right stones - the min stones Lee can get
            hash[i][M] = sums[i] - min;

            return hash[i][M];
        }
    }

    static class FindMaxScoreFromAnArrayWhenYouCanChooseKElementsFromTheLeftOrRight_MaxPointsWithCards {
        /**
         * There are several cards arranged in a row, and each card has an associated
         * number of points. The points are given in the integer array cardPoints.
         * 
         * In one step, you can take one card from the beginning or from the end of the
         * row. You have to take exactly k cards.
         * 
         * Your score is the sum of the points of the cards you have taken.
         * 
         * Given the integer array cardPoints and the integer k, return the maximum
         * score you can obtain.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: cardPoints = [1,2,3,4,5,6,1], k = 3 Output: 12 Explanation: After the
         * first step, your score will always be 1. However, choosing the rightmost card
         * first will maximize your total score. The optimal strategy is to take the
         * three cards on the right, giving a final score of 1 + 6 + 5 = 12. Example 2:
         * 
         * Input: cardPoints = [2,2,2], k = 2 Output: 4 Explanation: Regardless of which
         * two cards you take, your score will always be 4. Example 3:
         * 
         * Input: cardPoints = [9,7,7,9,7,7,9], k = 7 Output: 55 Explanation: You have
         * to take all the cards. Your score is the sum of points of all cards. Example
         * 4:
         * 
         * Input: cardPoints = [1,1000,1], k = 1 Output: 1 Explanation: You cannot take
         * the card in the middle. Your best score is 1. Example 5:
         * 
         * Input: cardPoints = [1,79,80,1,1,1,200,1], k = 3 Output: 202
         */
        /**
         * Key idea: You can’t choose 2nd element from the beginning unless you have
         * chosen the first one. Similarly, you can’t choose 2nd element from last
         * unless you have chosen the last one.
         * 
         * So now just try all possible combinations. Choose 0 from the beginning and K
         * from the last, 1 from front and K-1 from last and so on until K from
         * beginning and 0 from behind. Maximum out of all those combinations is the
         * answer.
         * 
         * To make it faster to find sum of first i cards, store the cumulative sum from the beginning to current index i in an array. In the similar way, store cumulative sums from the back in separate array.

            How to solve exactly?

            Find cumulative sum from beginning to the current index.
            Find cumulative sum from behind till the current index.
            If you choose i elements from front, you will need to choose k-i elements from behind.
            Sum of first i elements = cumulativeSumFromFront[i],
            Sum of last (k-i) elements = cumulativeSumFromBehind[K-i].
            So points obtained when choosing i elements from the front = cumulativeSumFromFront[i] + cumulativeSumFromBehind[K-i]
            Repeat Step 3 for all i ranging from 0 to K.
            Return the maximum value of points reached.
         */
        int findMaxScore(int[] cardPoints, int k) {
            int sum = 0;
            int n = cardPoints.length;

            int[] cummulativeSumFromFront = new int[n + 1];
            Arrays.fill(cummulativeSumFromFront, 0);
            int[] cummulativeSumFromBehind = new int[n + 1];
            Arrays.fill(cummulativeSumFromBehind, 0);

            sum = 0;
            for (int i = 0; i < n; i++) {
                sum += cardPoints[i];
                cummulativeSumFromFront[i + 1] = sum;
            }
            sum = 0;
            for (int i = n - 1; i >= 0; i--) {
                sum += cardPoints[i];
                cummulativeSumFromBehind[i] = sum;
            }

            // Reversing is optional. I reversed it so that it would be easy
            // to access sum of last (k-i) elements by just indexing at [k-i]
            // Otherwise, I would have had to index it at [n-k+i] which would
            // have made it difficult to read.
            // Arrays.reverse(cumulativeSumFromBehind);

            int answer = 0;
            for (int i = 0; i <= k; i++) {
                answer = Math.max(answer, cummulativeSumFromFront[i] // Sum of first 'i' cards.
                        + cummulativeSumFromBehind[k - i]); // Sum of last 'k-i' cards.
            }
            return answer;
        }
    }

    static class GroupXNumbersOfSameIntegerFromArray {
        /**
         * In a deck of cards, each card has an integer written on it.
         * 
         * Return true if and only if you can choose X >= 2 such that it is possible to
         * split the entire deck into 1 or more groups of cards, where:
         * 
         * Each group has exactly X cards. All the cards in each group have the same
         * integer.
         * 
         * 
         * Example 1:
         * 
         * Input: deck = [1,2,3,4,4,3,2,1] Output: true Explanation: Possible partition
         * [1,1],[2,2],[3,3],[4,4]. Example 2:
         * 
         * Input: deck = [1,1,1,2,2,2,3,3] Output: false Explanation: No possible
         * partition. Example 3:
         * 
         * Input: deck = [1] Output: false Explanation: No possible partition. Example
         * 4:
         * 
         * Input: deck = [1,1] Output: true Explanation: Possible partition [1,1].
         * Example 5:
         * 
         * Input: deck = [1,1,2,2,2,2] Output: true Explanation: Possible partition
         * [1,1],[2,2],[2,2].
         */
        /**
         * Intuition and Algorithm
         * 
         * Again, say there are C_i cards of number i. These must be broken down into
         * piles of X cards each, ie. C_i % X == 0 for all i.
         * 
         * Thus, X must divide the greatest common divisor of C_i. If this greatest
         * common divisor g is greater than 1, then X = g will satisfy. Otherwise, it
         * won't.
         */
        public static boolean hasGroupsSizeX(int[] deck) {
            int[] count = new int[10000];
            for (int c: deck)
                count[c]++;
    
            int g = -1;
            for (int i = 0; i < 10000; ++i)
                if (count[i] > 0) {
                    if (g == -1)
                        g = count[i];
                    else
                        g = gcd(g, count[i]);
                }
    
            return g >= 2;
        }
    
        public static int gcd(int x, int y) {
            System.out.println("x,y is "+x+","+y);
            return x == 0 ? y : gcd(y%x, x);
        }

        public static void main(String[] args){
            int deck[] = new int [] {1,1,1,2,2,2,3,3};// The common divisor of (3, 2) is 1, which is less than 2.
            hasGroupsSizeX(deck);
        }
    }

    static class FindTwoNonOverlappingSubarraysWithTargetSumAndMinLength {
        /**
         * Given an array of integers arr and an integer target.
         * 
         * You have to find two non-overlapping sub-arrays of arr each with a sum equal
         * target. There can be multiple answers so you have to find an answer where the
         * sum of the lengths of the two sub-arrays is minimum.
         * 
         * Return the minimum sum of the lengths of the two required sub-arrays, or
         * return -1 if you cannot find such two sub-arrays.
         * 
         * Example 1:
         * 
         * Input: arr = [3,2,2,4,3], target = 3 Output: 2 Explanation: Only two
         * sub-arrays have sum = 3 ([3] and [3]). The sum of their lengths is 2. Example
         * 2:
         * 
         * Input: arr = [7,3,4,7], target = 7 Output: 2 Explanation: Although we have
         * three non-overlapping sub-arrays of sum = 7 ([7], [3,4] and [7]), but we will
         * choose the first and third sub-arrays as the sum of their lengths is 2.
         * 
         * Example 3:
         * 
         * Input: arr = [4,3,2,6,2,3,4], target = 6 Output: -1 Explanation: We have only
         * one sub-array of sum = 6. Example 4:
         * 
         * Input: arr = [5,5,4,4,5], target = 3 Output: -1 Explanation: We cannot find a
         * sub-array of sum = 3. Example 5:
         * 
         * Input: arr = [3,1,1,1,5,1,2,1], target = 3 Output: 3 Explanation: Note that
         * sub-arrays [1,2] and [2,1] cannot be an answer because they overlap.
         */
        public int minSumOfLengths(int[] arr, int target) {
            int[] dp = new int[arr.length]; // dp[i] := length of smallest subarray in range arr[0]...arr[i] with sum =
                                            // target
            Arrays.fill(dp, Integer.MAX_VALUE);

            int res = Integer.MAX_VALUE;

            // sliding window
            int sum = 0; // sum of current window
            int l = 0;
            for (int r = 0; r < arr.length; r++) {

                // invite new element into RHS
                sum += arr[r];

                // contract until valid
                while (l <= r && sum > target) {
                    sum -= arr[l];
                    l++;
                }

                // if valid, process
                if (sum == target) {

                    // current window arr[l]...a[r]
                    int lenCurr = r - l + 1;

                    // want best from prev iterations, dp[l-1]
                    // Get the best length from previous iterations.
                    int prevBest = (l == 0) ? Integer.MAX_VALUE : dp[l - 1];

                    // process res
                    if (prevBest != Integer.MAX_VALUE) {
                        res = Math.min(res, lenCurr + prevBest);
                        // Res stores the total minimum sum of all subarrays.
                        // To find the minimum sum of lengths of all the subarrays found so far...
                    }

                    // now process dp
                    dp[r] = (r == 0) ? lenCurr : Math.min(lenCurr, dp[r - 1]);
                    // At this point r, store the min length.
                } else {
                    // else not valid, inherit dp[prev iteration]
                    dp[r] = (r == 0) ? Integer.MAX_VALUE : dp[r - 1];
                    // Extend the previous length.
                }
            }

            return (res == Integer.MAX_VALUE) ? -1 : res;
        }
    }

    static class LongestStringChainWithWordsFromDictOfWords {
        /**
         * You are given an array of words where each word consists of lowercase English
         * letters.
         * 
         * wordA is a predecessor of wordB if and only if we can insert exactly one
         * letter anywhere in wordA without changing the order of the other characters
         * to make it equal to wordB.
         * 
         * For example, "abc" is a predecessor of "abac", while "cba" is not a
         * predecessor of "bcad". A word chain is a sequence of words [word1, word2,
         * ..., wordk] with k >= 1, where word1 is a predecessor of word2, word2 is a
         * predecessor of word3, and so on. A single word is trivially a word chain with
         * k == 1.
         * 
         * Return the length of the longest possible word chain with words chosen from
         * the given list of words.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: words = ["a","b","ba","bca","bda","bdca"] Output: 4 Explanation: One
         * of the longest word chains is ["a","ba","bda","bdca"]. Example 2:
         * 
         * Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"] Output: 5 Explanation:
         * All the words can be put in a word chain ["xb", "xbc", "cxbc", "pcxbc",
         * "pcxbcf"]. Example 3:
         * 
         * Input: words = ["abcd","dbqca"] Output: 1 Explanation: The trivial word chain
         * ["abcd"] is one of the longest word chains. ["abcd","dbqca"] is not a valid
         * word chain because the ordering of the letters is changed.
         */
        public static int longestStrChain(String[] words) {
            Map<String, Integer> dp = new HashMap<>();
            //This map stores the longest length found so far for every string.
            Arrays.sort(words, (a, b)->a.length() - b.length());
            int res = 0;
            for (String word : words) {
                int longestLengthFoundSoFar = 0;
                for (int i = 0; i < word.length(); ++i) {
                    String prev = word.substring(0, i) + word.substring(i + 1);// skip one letter.
                    longestLengthFoundSoFar = Math.max(longestLengthFoundSoFar, dp.getOrDefault(prev, 0) + 1);
                    // for bda, ba already exists and has the max length, add +1. This will be the longest length found so far.
                }
                // System.out.println("best is "+longestLengthFoundSoFar);
                dp.put(word, longestLengthFoundSoFar);// for this found word, add this length to the map for next iteration.
                res = Math.max(res, longestLengthFoundSoFar);
            }
            return res;
        }
        public static void main(String[] args)    {
            String[] words = new String[]{"a","b","ba", "bda"};
            longestStrChain(words);
        }
    }

    static class DecodeString_RepeatElementsInString {
        /**
         * Given an encoded string, return its decoded string.
         * 
         * The encoding rule is: k[encoded_string], where the encoded_string inside the
         * square brackets is being repeated exactly k times. Note that k is guaranteed
         * to be a positive integer.
         * 
         * You may assume that the input string is always valid; No extra white spaces,
         * square brackets are well-formed, etc.
         * 
         * Furthermore, you may assume that the original data does not contain any
         * digits and that digits are only for those repeat numbers, k. For example,
         * there won't be input like 3a or 2[4].
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: s = "3[a]2[bc]" Output: "aaabcbc" Example 2:
         * 
         * Input: s = "3[a2[c]]" Output: "accaccacc" Example 3:
         * 
         * Input: s = "2[abc]3[cd]ef" Output: "abcabccdcdcdef" Example 4:
         * 
         * Input: s = "abc3[cd]xyz" Output: "abccdcdcdxyz"
         * 
         */
        public static String solution(String s) {
            Stack<Integer> intStack = new Stack<>();
            Stack<StringBuilder> strStack = new Stack<>();
            StringBuilder cur = new StringBuilder();
            int k = 0;
            for (char ch : s.toCharArray()) {
                if (Character.isDigit(ch)) {
                    k = k * 10 + ch - '0';
                    System.out.println("k is "+k);
                } else if (ch == '[') {
                    System.out.println("string builder " + cur);
                    intStack.push(k);
                    strStack.push(cur);
                    cur = new StringBuilder();
                    k = 0; // start of new window.
                } else if (ch == ']') {
                    // When the close bracket is encountered, 
                    // append the popped elements at the last to the current string.
                    StringBuilder tmp = cur; // The last element in the given input.
                    System.out.println("Temp string builder "+ tmp);
                    cur = strStack.pop(); // Pop the string in stack
                    System.out.println("Popped string builder "+ cur);
                    for (k = intStack.pop(); k > 0; --k)
                        cur.append(tmp); // Append the current element to the popped string for k times. 
                    System.out.println("Appended string builder "+ cur);
                } else
                    cur.append(ch);//append the character to the string builder.
            }
            /**
             * Output:
             *  k is 3
                string builder 
                k is 2
                string builder a
                Temp string builder c
                Popped string builder a
                Appended string builder acc
                Temp string builder acc
                Popped string builder 
                Appended string builder accaccacc
                accaccacc
             */
            return cur.toString();
        }
        public static void main(String[] args)    {
            System.out.println(solution("3[a2[c]]"));
        }
    }

    static class DesignHitCounter {

    }

    static class CandyCrush {


    }

    static class FindAndReplaceSubStringInString {
        /**
         * You are given a 0-indexed string s that you must perform k replacement
         * operations on. The replacement operations are given as three 0-indexed
         * parallel arrays, indices, sources, and targets, all of length k.
         * 
         * To complete the ith replacement operation:
         * 
         * Check if the substring sources[i] occurs at index indices[i] in the original
         * string s. If it does not occur, do nothing. Otherwise if it does occur,
         * replace that substring with targets[i]. For example, if s = "abcd",
         * indices[i] = 0, sources[i] = "ab", and targets[i] = "eee", then the result of
         * this replacement will be "eeecd".
         * 
         * All replacement operations must occur simultaneously, meaning the replacement
         * operations should not affect the indexing of each other. The testcases will
         * be generated such that the replacements will not overlap.
         * 
         * For example, a testcase with s = "abc", indices = [0, 1], and sources =
         * ["ab","bc"] will not be generated because the "ab" and "bc" replacements
         * overlap. Return the resulting string after performing all replacement
         * operations on s.
         * 
         * A substring is a contiguous sequence of characters in a string.
         * 
         * Example 1:
         * 
         * 
         * Input: s = "abcd", indices = [0, 2], sources = ["a", "cd"], targets = ["eee",
         * "ffff"] Output: "eeebffff" Explanation: "a" occurs at index 0 in s, so we
         * replace it with "eee". "cd" occurs at index 2 in s, so we replace it with
         * "ffff". Example 2:
         * 
         * 
         * Input: s = "abcd", indices = [0, 2], sources = ["ab","ec"], targets =
         * ["eee","ffff"] Output: "eeecd" Explanation: "ab" occurs at index 0 in s, so
         * we replace it with "eee". "ec" does not occur at index 2 in s, so we do
         * nothing.
         */
        /**
         * Solution
         * 
         * The technique is like "piece table", which is used in editors. We record all
         * the valid operations first and put them into a piece table, then iterate the
         * string index to "apply" these operations.
         */

        public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
            Map<Integer, Integer> table = new HashMap<>();
            for (int i = 0; i < indexes.length; i++) {
                // if a match is found in the original string, record it
                if (S.startsWith(sources[i], indexes[i])) {
                    // record all the valid operations and put them into table.
                    table.put(indexes[i], i);
                    // Store the string's index as key, and the value as index of indices array.
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < S.length();) {
                if (table.containsKey(i)) {
                    // if a replacement was recorded before
                    sb.append(targets[table.get(i)]); // Get the index of indices array and search for the target.
                    i += sources[table.get(i)].length(); // jump by length of the string.
                } else {
                    // if no replacement happened at this index
                    sb.append(S.charAt(i));
                    i++;
                }
            }
            return sb.toString();
        }
    }

    static class ReorganizeCharsSuchThatAnyTwoAdjacentCharactersAreNotSame {
        /**
         * Given a string s, rearrange the characters of s so that any two adjacent
         * characters are not the same.
         * 
         * Return any possible rearrangement of s or return "" if not possible.
         * 
         * Example 1:
         * 
         * Input: s = "aab" Output: "aba" Example 2:
         * 
         * Input: s = "aaab" Output: ""
         */
        /**
         * count letter appearance and store in hash[i] find the letter with largest
         * occurence. put the letter into even index numbe (0, 2, 4 ...) char array put
         * the rest into the array
         **/
        public String reorganizeString(String S) {
            /**
             * We construct the resulting string in sequence: at position 0, 2, 4, ... and
             * then 1, 3, 5, ... In this way, we can make sure there is always a gap between
             * the same characters
             * 
             * Consider this example: "aaabbbcdd", we will construct the string in this way:
             * 
             * a _ a _ a _ _ _ _ // fill in "a" at position 0, 2, 4 a b a _ a _ b _ b //
             * fill in "b" at position 6, 8, 1 a b a c a _ b _ b // fill in "c" at position
             * 3 a b a c a d b d b // fill in "d" at position 5, 7
             */
            int[] hash = new int[26];
            for (int i = 0; i < S.length(); i++) {
                hash[S.charAt(i) - 'a']++;
            }
            int max = 0, letter = 0;
            for (int i = 0; i < hash.length; i++) {
                if (hash[i] > max) {
                    max = hash[i];
                    letter = i;
                }
            }
            if (max > (S.length() + 1) / 2) {
                return "";
            }
            char[] res = new char[S.length()];
            int idx = 0;
            while (hash[letter] > 0) {
                res[idx] = (char) (letter + 'a');
                idx += 2;
                hash[letter]--;
            }
            for (int i = 0; i < hash.length; i++) {
                while (hash[i] > 0) {
                    if (idx >= res.length) {
                        idx = 1;
                    }
                    res[idx] = (char) (i + 'a');
                    idx += 2;
                    hash[i]--;
                }
            }
            return String.valueOf(res);
        }

        /**
         * We store a heap of (count, letter). [In Python, our implementation stores
         * negative counts.]
         * 
         * We pop the top two elements from the heap (representing different letters
         * with positive remaining count), and then write the most frequent one that
         * isn't the same as the most recent one written. After, we push the correct
         * counts back onto the heap.
         * 
         * Actually, we don't even need to keep track of the most recent one written. If
         * it is possible to organize the string, the letter written second can never be
         * written first in the very next writing.
         * 
         * At the end, we might have one element still on the heap, which must have a
         * count of one. If we do, we'll add that to the answer too.
         */
        public String reorganizeStringWithHeap(String S) {
            int N = S.length();
            int[] count = new int[26];
            for (char c : S.toCharArray())
                count[c - 'a']++;
            PriorityQueue<MultiChar> pq = new PriorityQueue<MultiChar>(
                    (a, b) -> a.count == b.count ? a.letter - b.letter : b.count - a.count);

            for (int i = 0; i < 26; ++i)
                if (count[i] > 0) {
                    if (count[i] > (N + 1) / 2)
                        return "";
                    pq.add(new MultiChar(count[i], (char) ('a' + i)));
                }

            StringBuilder ans = new StringBuilder();
            while (pq.size() >= 2) {
                MultiChar mc1 = pq.poll();
                MultiChar mc2 = pq.poll();
                /*
                 * This code turns out to be superfluous, but explains what is happening if
                 * (ans.length() == 0 || mc1.letter != ans.charAt(ans.length() - 1)) {
                 * ans.append(mc1.letter); ans.append(mc2.letter); } else {
                 * ans.append(mc2.letter); ans.append(mc1.letter); }
                 */
                ans.append(mc1.letter);
                ans.append(mc2.letter);
                if (--mc1.count > 0)
                    pq.add(mc1);
                if (--mc2.count > 0)
                    pq.add(mc2);
            }
            if (pq.size() > 0)
                ans.append(pq.poll().letter);
            return ans.toString();
        }

        class MultiChar {
            int count;
            char letter;

            MultiChar(int ct, char ch) {
                count = ct;
                letter = ch;
            }
        }
    }

    static class FindMinimumWindowSubstringInString {
        /**
         * Given two strings s and t of lengths m and n respectively, return the minimum
         * window substring of s such that every character in t (including duplicates)
         * is included in the window. If there is no such substring, return the empty
         * string "".
         * 
         * The testcases will be generated such that the answer is unique.
         * 
         * A substring is a contiguous sequence of characters within the string.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: s = "ADOBECODEBANC", t = "ABC" Output: "BANC" Explanation: The minimum
         * window substring "BANC" includes 'A', 'B', and 'C' from string t. Example 2:
         * 
         * Input: s = "a", t = "a" Output: "a" Explanation: The entire string s is the
         * minimum window. Example 3:
         * 
         * Input: s = "a", t = "aa" Output: "" Explanation: Both 'a's from t must be
         * included in the window. Since the largest window of s only has one 'a',
         * return empty string.
         */
        static String minWindow(String s, String t) {
            /**
             * Let me explain this algorithm.

            1. Use two pointers: start and end to represent a window.
            2. Move end to find a valid window.
            3. When a valid window is found, move start to find a smaller window.
            To check if a window is valid, we use a map to store (char, count) for chars in t. And use counter for the number of chars of t to be found in s. The key part is m[s[end]]--;. We decrease count for each char in s. If it does not exist in t, the count will be negative.

            To really understand this algorithm, please see my code which is much clearer, because there is no code like if(map[s[end++]]++>0) counter++;.
             */
            int [] targetMap = new int[128];
            // Statistic for count of char in t
            for (char c : t.toCharArray()) {
                targetMap[c]++;//set the expectation.
            }
            // counter represents the number of chars of t to be found in s.
            int start = 0, end = 0, counter = t.length(), minStart = 0, minLen = Integer.MAX_VALUE;
            int size = s.length();
            
            /***
             * 
             *  Take an example 
             *  0 1 2 3 4 5 6 7 8 9 10 11 12
             *  A D O B E C O D E B A  N  C 
             * 
             * 1st window:- 
             * 
             *  First while loop
            *    start = 0, end = 0, until all the character of t is found (i.e. counter becomes zero), increase end.  
             *   start will become zero, end will become 5.
             *  Nested while loop
             *    A valid window is found. Counter is zero, end is 6. Set minLength. 
             *    Increment start to find the smaller window. 
             *    How? When the character in T is removed out of the window, the counter pointer in the nested while loop will become 1.
             *    
             *  This counter reset will restart the iteration to find the next window.
             * 
             * 2nd window:-
             * 
             * End is incremented until 10. 
             * start=1, end =10. This window contains the letters of T string. 
             * In the nested loop, end becomes 11. start is 1. Record the min length.
             * Start is incremented until 6 'C' character. Now this character has map count as 1.
             * Reset the iteration to find the 3rd window.
             * 
             * 3rd window:-
             * 
             * start is 6, end is 12.
             * Inside while start is incremented until 9. Counter becomes 1.
             * Go to the next iteration. End is incremented, it becomes 13, exit the loop.
             * 
             * The last stored min length is 4, start is 9.
             */
            // Move end to find a valid window.
            while (end < size) {
                // If char in s exists in t, decrease counter
                final char sEnd = s.charAt(end);
                if (targetMap[sEnd] > 0)
                    counter--;
                // Decrease m[s[end]]. If char does not exist in t, m[s[end]] will be negative.
                targetMap[sEnd]--;
                end++;
                // When we found a valid window, move start to find smaller window.
                while (counter == 0) {
                    System.out.println("Inside while start is "+ start + " end is "+end);
                    if (end - start < minLen) {
                        minStart = start;
                        minLen = end - start;
                    }
                    final char sStart = s.charAt(start);
                    targetMap[sStart]++;
                    // When char exists in t, increase counter.
                    if (targetMap[sStart] > 0)
                    {   
                        System.out.println("Index is "+start+" Char is "+s.charAt(start)+" map value is "+targetMap[sStart]);
                        counter++;
                    }
                    start++;
                }
                System.out.println("Outside while start is "+start +" end is "+end);
            }
            System.out.println("Minstart is "+minStart + " min length is "+minLen);
            if (minLen != Integer.MAX_VALUE)
                return s.substring(minStart, minStart + minLen);
            return "";
        }

        public static void main(String[] args){
            String s = "ADOBECODEBANC";
            String t = "ABC";
            System.out.println(minWindow(s, t));
        }


    }

    static class NumberOfSubstringsContainingAllThreeCharacters {
        /**
         * Given a string s consisting only of characters a, b and c.
         * 
         * Return the number of substrings containing at least one occurrence of all
         * these characters a, b and c.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: s = "abcabc" Output: 10 Explanation: The substrings containing at
         * least one occurrence of the characters a, b and c are "abc", "abca", "abcab",
         * "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again). Example 2:
         * 
         * Input: s = "aaacb" Output: 3 Explanation: The substrings containing at least
         * one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".
         * Example 3:
         * 
         * Input: s = "abc" Output: 1
         */
        int numberOfSubstrings(String s) {
            int num = 0;
            int begin = 0;
            int end = 0;
            int count[] = new int[3];
            while(end < s.length()) {
                count[s.charAt(end)-'a']++;
                while(count[0] > 0 && count[1] > 0 && count[2] > 0) {
                    num += s.length() - end;
                    /**
                     * Why ??
                     * 
                     * This is the best line ever
                     * 
                     * a a a b b c c a b c when all a, b, c > 0 for first time at j = 5 the n after
                     * while loop i will be at i = 3, we will add 3 to result because there would be
                     * three substrings from three a's.
                     * 
                     * Then a,b,c > 0 at j = 7 ,then we will move i until i = 5 then we will add 5
                     * to result because there could be 5 substrings starting from 0 to second b.
                     * 
                     * And similarly we proceed....
                     * 
                     */
                    count[s.charAt(begin)-'a']--;
                    begin++;
                }
                end++;
            }
            return num;
        }

    }

    static class LongestSubstringWithAtMostKDistinctCharacters {
        /**
         * Given a string S, find the length of the longest substring T 
         * that contains at most k distinct characters.

            Example
            Example 1:

            Input: S = "eceba" and k = 3
            Output: 4
            Explanation: T = "eceb"
            Example 2:

            Input: S = "WORLD" and k = 4
            Output: 4
            Explanation: T = "WORL" or "ORLD"
         */
        public int lengthOfLongestSubstringKDistinct(String s, int k) {
            int[] map = new int[256];
            int start = 0, end = 0, maxLen = Integer.MIN_VALUE, counter = 0;
        
            while (end < s.length()) {
              final char sEnd = s.charAt(end);
              if (map[sEnd] == 0) counter++;
              // If the count is zero for the char, then it's distinct character.
              map[sEnd]++;// increment the count of the char.
              end++;//extend the window.
        
              //for example, eceba. end will be a, where the counter is 4.
              while (counter > k) { // 4>3
                //once k distinct characters are found.  
                final char sStart = s.charAt(start);// increment the start, e is the first start
                //certain characters such as e in eceba will have count as 2
                if (map[sStart] == 1) counter--; //e's count is 2.
                // WHEN the counter is decreased, we signal to extend the window incrementing end if the string still has space.
                map[sStart]--; // e's count becomes 1.
                start++;// c is the start.
                // In the next iteration, counter will be decremented, c will have count zero, start will become e.
              }
              maxLen = Math.max(maxLen, end - start);
            }
        
            return maxLen;
          }
    }

    static class LongestSubstringWithAtMost2DistinctCharacters {
        public int lengthOfLongestSubstringTwoDistinct(String s) {
            int[] map = new int[128];
            int start = 0, end = 0, maxLen = 0, counter = 0;
        
            while (end < s.length()) {
              final char sEnd = s.charAt(end);
              if (map[sEnd] == 0) counter++;
              map[sEnd]++;
              end++;
        
              while (counter > 2) {
                final char sStart = s.charAt(start);
                if (map[sStart] == 1) counter--;
                map[sStart]--;
                start++;
              }
    
              maxLen = Math.max(maxLen, end - start);
            }
        
            return maxLen;
          }
    }

    static class LongestSubstringWithoutRepeatingCharacters {
        /**
         * Given a string s, find the length of the longest substring without repeating characters.

            Example 1:

            Input: s = "abcabcbb"
            Output: 3
            Explanation: The answer is "abc", with the length of 3.
            Example 2:

            Input: s = "bbbbb"
            Output: 1
            Explanation: The answer is "b", with the length of 1.
            Example 3:

            Input: s = "pwwkew"
            Output: 3
            Explanation: The answer is "wke", with the length of 3.
            Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
            Example 4:

            Input: s = ""
            Output: 0
         */
        public int lengthOfLongestSubstring2(String s) {
            int[] map = new int[128];
            int start = 0, end = 0, maxLen = 0, counter = 0;
        
            while (end < s.length()) {
              final char sEnd = s.charAt(end);
              if (map[sEnd] > 0) counter++; 
              // If there is a repeating character found in the current window, shrink the window with start.
              map[sEnd]++;
              end++;
        
              while (counter > 0) {
                final char sStart = s.charAt(start);
                if (map[sStart] > 1) counter--; 
                // If a repeating character found in start...end, increment the start, 
                // and decrement the count for the repeating character
                map[sStart]--;
                start++;
              }
        
              maxLen = Math.max(maxLen, end - start);
            }
        
            return maxLen;
          }
    }

    static class ReplaceTheSubstringForBalancedString {
        /**
         * You are given a string containing only 4 kinds of characters 'Q', 'W', 'E'
         * and 'R'.
         * 
         * A string is said to be balanced if each of its characters appears n/4 times
         * where n is the length of the string.
         * 
         * Return the minimum length of the substring that can be replaced with any
         * other string of the same length to make the original string s balanced.
         * 
         * Return 0 if the string is already balanced.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: s = "QWER" Output: 0 Explanation: s is already balanced. Example 2:
         * 
         * Input: s = "QQWE" Output: 1 Explanation: We need to replace a 'Q' to 'R', so
         * that "RQWE" (or "QRWE") is balanced. Example 3:
         * 
         * Input: s = "QQQW" Output: 2 Explanation: We can replace the first "QQ" to
         * "ER". Example 4:
         * 
         * Input: s = "QQQQ" Output: 3 Explanation: We can replace the last 3 'Q' to
         * make s = "QWER".
         * 
         */
        /**
         * The idea is to first count up each type of character. Since we know there are
         * only 4 characters: Q, W, E, R, we can easily count them up using an int[] arr
         * of length 4.
         * 
         * Then once we count them up, we look at the number of occurrences of each and
         * see if any of them > N/4 (where N is the length of the String). If they are,
         * this means that we need this freq[character] - (N/4) number of this character
         * in the substring we choose to replace.
         * 
         * E.g. If we have N = 12 and freq[Q] = freq[0] = 6. Since we know each
         * character must occur N/4 = 12/4 = 3 times. We have 3 extra Qs. So we need to
         * make sure our substring at the end has 3 Qs in it. The same principle applies
         * when there are multiple characters > (N/4).
         * 
         * Essentially, we reduced the problem to finding a minimum substring containing
         * a certain number of each character.
         * 
         * Then we go to the freq array and subtract (N/4) from each of freq[Q],
         * freq[W], freq[E], freq[R]. If it is below 0 (this means our substring does
         * not need to contain this letter since we are already in demand of this
         * letter), then we just set it to 0.
         * 
         * Then for our sliding window approach - see more:
         * https://www.geeksforgeeks.org/window-sliding-technique/
         * 
         * We update freq[] so that freq[char] always represents how many characters we
         * need still of each char to get the substring that we need. It is okay for
         * freq[char] to be < 0 as this mean we have more characters than we need (which
         * is fine). Each time we have an eligible substring, we update our minLen
         * variable and try to shrink the window from the left as much as possible.
         * 
         * In the end we get the minimum length of a substring containing at least the
         * characters we need to replace with other characters.
         * 
         * 
         */
        // Checks that freq[char] <= 0 meaning we have an elligible substring
        private boolean fulfilled(int[] freq) {
            boolean fulfilled = true;
            for (int f : freq) {
                if (f > 0)
                    fulfilled = false;
            }
            return fulfilled;
        }

        // Q 0 W 1 E 2 R 3
        private int charToIdx(char c) {
            switch (c) {
                case 'Q':
                    return 0;
                case 'W':
                    return 1;
                case 'E':
                    return 2;
            }
            return 3;
        }

        public int balancedString(String s) {
            // 1) Find freq of each first
            int N = s.length();
            int required = N / 4;

            int[] freq = new int[4];
            for (int i = 0; i < N; ++i) {
                char c = s.charAt(i);
                ++freq[charToIdx(c)];
            }

            // 2) Determine the ones we need to change
            boolean equal = true;
            for (int i = 0; i < 4; ++i) {
                if (freq[i] != required)
                    equal = false;
                freq[i] = Math.max(0, freq[i] - required);
            }

            if (equal)
                return 0; // Early return if all are equal

            // 3) Use sliding window and try to track what more is needed to find smallest
            // window
            int start = 0;
            int minLen = N; // Maximum will only ever be N

            for (int end = 0; end < N; ++end) {
                char c = s.charAt(end);
                --freq[charToIdx(c)];

                while (fulfilled(freq)) {
                    minLen = Math.min(end - start + 1, minLen);

                    char st = s.charAt(start);
                    ++freq[charToIdx(st)];
                    ++start;
                }
            }

            return minLen;
        }
    }
    
    static class SubarraysWithKDifferentIntegers {
        /**
         * Given an array nums of positive integers, call a (contiguous, not necessarily
         * distinct) subarray of nums good if the number of different integers in that
         * subarray is exactly k.
         * 
         * (For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)
         * 
         * Return the number of good subarrays of nums.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: nums = [1,2,1,2,3], k = 2 Output: 7 Explanation: Subarrays formed with
         * exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2],
         * [1,2,1,2]. Example 2:
         * 
         * Input: nums = [1,2,1,3,4], k = 3 Output: 3 Explanation: Subarrays formed with
         * exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
         */
        /**
         * This problem is a hard ask, until you realize that we've actually solved this
         * problem before in other sliding window problems. Generally, the sliding
         * window problems have some kind of aggregate, atMost k, largest substring, min
         * substring with k etc. They're always "given an array or string, find some
         * computed sub problem" value.
         * 
         * So how do we use this our advantage? Well, the ask: different integers in
         * that subarray is exactly K is exactly this. We can rewrite the problem to
         * something like this:
         * 
         * subArrayExactlyK = subArrayAtMostK - subArrayAtMostK - 1. This is basically
         * saying, give me the amount of subarrays we can form with at least 3, and give
         * me the amount of subarrays we can form with at least 2, and the diff between
         * the two will be only subarrays at 3 (since we have eliminated everything 2
         * and under).
         * 
         * 
         * Example: Input: A = [1,2,1,2,3], K = 2 Output: 7
         * 
         * subArrayAtMostK = 12 if k = 2, there are 12 possible subarrays that are at
         * least 2 values. This is the array possibilities we create, where the count is
         * the end - start (see below code example), since a sub problem will contribute
         * to the overall amount of possibilities. You see below for the AtMostK
         * problems if this concept is confusing. The other trick here is that in other
         * atMostK problems, they ask for length, but length can also be a substitute
         * for amount of sub problems, since the length of any given range, say 1212,
         * also constitutes 4 different subarray possibilities. [1, 12, 121, 1212, 23]
         * 
         * Example: A = [1,2,1,2,3], K = 2
         * 
         * Here's what we get:
         * 
         * [1] is one valid result of a contiguous subarray that has at most K different
         * integers and has the length of 1.
         * 
         * [1,2] is one valid result of a contiguous subarray that has at most K
         * different integers and has the length of 2.
         * 
         * 
         * [1,2,1] is one valid result of a contiguous subarray that has at most K
         * different integers and has the length of 3.
         * 
         * [1,2,1,2] is one valid result of a contiguous subarray that has at most K
         * different integers and has the length of 4.
         * 
         * Then when we see the last element 3, we will see that the only valid
         * contiguous subarray with at most K (which is 2) different integers that can
         * be created is:
         * 
         * [2,3] is one valid result of a contiguous subarray that has at most K
         * different integers and has the length of 2. So our function will return the
         * sum of the length of all of those subarrays:
         * 
         * 1 + 2 + 3 + 4 which is 10 different contiguous subarrays with at most K
         * different integers for the subarray [1,2,1,2].
         * 
         * 2 which is 2 different contiguous subarrays with at most K different integers
         * for the subarray [2,3].
         * 
         * Here the answer is 10 + 2 which is 12 different contiguous subarrays with at
         * most K = 2 different integers. 1 + 2 + 3 + 4 + 2 = 12
         * 
         * subArrayAtMostK - 1 = 5 since k = 1, every subarray is a single element, so
         * the length of the array. There are by definition, only 5 different subarrays
         * that can be formed. [1, 2, 1, 2, 3] 1 + 1 + 1 + 1 + 1 = 5
         * 
         * So the amount of subarrays possible with at least 2 - the amount of subarrays
         * with at least 1 = the exactly subarrays that contain only 2, since we have
         * stripped out answers with only 1.
         * 
         * 12 - 5 = 7;
         * 
         * 
         */
        /**
         * I still don't get why the number of contiguous subarrays is equal to the sum
         * of lengths. Why does that work? 
         * 
         We want to find all valid contiguous
         * subarrays that[1,2,1,2,3] would produce with at most K different integers.
         * You will notice though -- that when we say at most K different integers -- we
         * only use K to help us find the valid windows (ex. [1,2,1,2] and [2,3]) Once
         * we have those valid windows though, we don't really care what K is (that's
         * because at most means 0 to K unique Integers, which literally means any
         * contiguous subarray now). So, stop thinking about how K will affect the
         * subarrays to understand the summation of lengths.
         * 
         * Okay, so now that we have our (let's call them "complete") valid subarrays
         * [1,2,1,2] and [2,3] -- we can begin to understand why we take the summation
         * of lengths to count all subarrays. Remember how we listed all the subarrays
         * earlier and how I asked you to remember why we group subarrays by length?
         * 
         * Here's why: [1,2,1,2] will produce 1 subarrays of length 4, 2 subarrays of
         * length 3, 3 subarrays of length 2, and 4 subarray of length 1 (see above)
         * [2,3] will produce 1 subarrays of length 2, 2 subarray of length 1 (see
         * above)
         * 
         * Do you notice anything?
         * 
         * [1,2,1,2] = 1 + 2 + 3 + 4 (sum of our 1 subarrays of length 4, 2 subarray of
         * length 3, etc.) = 10 [2,3] = 1 + 2 (*Special case: this creates 1 subarray of
         * length 2, and 2 subarray of length 2, but since our [2] one was accounted for
         * already, we only get 2 new subarrays so subtract 1) - 1 = 2
         * 
         * When we summed the different lengths (see above where we listed the
         * iterations), we also get the same growth (i.e. 1 + 2 + 3 + 4)!
         * 
         * So another way to understand this in the context of this problem is, that the
         * code above will produce valid (sliding) window (like [1,2], [1,2,1],
         * [1,2,1,2]). As we expand the length of that window, we can sum the length of
         * those windows to get our different combinations because if our "complete"
         * window was [1,2,1,2], we could do 1 + 2 + 3 + 4 (or length of [1] + length of
         * [1,2] + length of [1,2,1] + length of [1,2,1,2]).
         * 
         * We also noticed that for [2,3], by only adding 2, we were able to ignore that
         * duplicate subarray. The sliding window did not return [2] because the window
         * expanded to [1,2,1,2,3] -- invalidating the window and then compressed the
         * window to [2,3] by moving i forward. This allowed us to skip those duplicate
         * subarrays. You can expand this to other examples including where K is larger.
         * The fact that our sliding window compresses by moving forward i will allow
         * the lower summations to be ignored (i.e. our duplicate subarrays).
         */
         public int subarraysWithKDistinct(int[] A, int K) {
            int i = helper(A, K);
            int j = helper(A, K - 1);

            return i - j;
        }

        private int helper(int[] A, int K) {
            if (A == null || A.length == 0) {
                return 0;
            }

            int start = 0;
            int end = 0;
            int len = 0;
            Map<Integer, Integer> map = new HashMap<>();

            List<String> pairs = new ArrayList<>();

            while (end < A.length) {
                int endNum = A[end];

                map.put(endNum, map.getOrDefault(endNum, 0) + 1);

                end++;

                while (map.size() > K) {
                    int startNum = A[start];

                    map.put(startNum, map.get(startNum) - 1);

                    if (map.get(startNum) == 0) {
                        map.remove(startNum);
                    }

                    start++;
                }

                len += end - start;

                // just a hack to visualize the pairs we build for learning and comprehension
                StringBuilder sb = new StringBuilder();
                for (int i = start; i < end; i++) {
                    sb.append(A[i]);
                }
                pairs.add(sb.toString());
            }

            System.out.println(pairs.toString());

            return len;
        }
    }

    static class CountNumberOfNiceSubarrays {
        /**
         * Given an array of integers nums and an integer k. A continuous subarray is
         * called nice if there are k odd numbers on it.
         * 
         * Return the number of nice sub-arrays.
         * 
         * Example 1:
         * 
         * Input: nums = [1,1,2,1,1], k = 3 Output: 2 Explanation: The only sub-arrays
         * with 3 odd numbers are [1,1,2,1] and [1,2,1,1]. Example 2:
         * 
         * Input: nums = [2,4,6], k = 1 Output: 0 Explanation: There is no odd numbers
         * in the array. Example 3:
         * 
         * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2 Output: 16
         */
        /**
         * My personal feedback after 15 sliding window type of questions is that
         * basically you need to figure out if this problem can be solved using the
         * sliding window code template. The keyword you might want to pay attention to
         * is something like subarray/substring with at most/least K different
         * numbers/letters. Then if the question is asking for a subarray/substring with
         * exact K different numbers/letters, that's where the strategy from 992 could
         * be applied to.
         */

        public int numberOfSubarrays(int[] A, int k) {
            /**
             * 
             * Window 1:
             * 
             * End is indicated by [], start by ()
             * 
             * ([2]) 2 1 1 2 2 1 2 start=0, end=0
             * odd=0, count=0
             * 
             * (2) 2 1 [1] 2 2 1 2 start=0, end=3
             * odd=2, count=0 
             * 
             * When odd is equal to k, we can analyse our left part 
             * and count how many subarrays with odd == k we can produce.
              We are doing it with start iterating until we get to odd number.

             * 2 2 (1) [1] 2 2 1 2 start=2, end=3
             * odd=2, count=3
             * We can see that there are 3 subarrays we can produce with odd == k.
             * 
             * Start is at odd number.
             * 
             * Continue with end again, until we find odd==k in the next window. 
             * Every next even number we meet is going to double previous count. Let's see:
             * 
             * 2 2 (1) 1 2 (2) 1 2 start=3, end=5
             * count = 3+3+3 = 9
             * 
             When we meet odd number again we need to reset count=0
             and repeat the above steps.
             * 
             */
            int res = 0, start = 0, end = 0, count = 0, odd=0;
            // Actually it's same as three pointers.
            // Though we use count to count the number of even numebers.
            while(end < A.length) {
                if (A[end] % 2 == 1) {
                    odd++;
                    count = 0;
                }
                while (odd == k) {
                    // if k odd numbers are found, analyze the left part and count how many subarrays
                    // with odd==k we can produce, we're doing it with start iterating until we get to odd number.
                    //k += A[i++] & 1     Replacing this line with the below two lines: 
                    if(A[start] % 2 == 1) {
                        odd--;  //odd>=k
                    }
                    count++;
                    start++;
                }
                end++; // Increment until we encounter odd of k
                res += count;// continue with end again, Since odd >= k then every next even number we meet
                             // is going to double previous count.

                            // When we meet odd number again we need to reset count=1 and repeat step (2) with j again. In our example there's going to be only one subarray:
            }
            return res;
        }
    }
    
    static class FuritIntoBaskets_LengthOfLongestSubarrayWithAtmost2DistinctIntegers_SlidingWindowForKElements {
        /**
         * You are visiting a farm that has a single row of fruit trees arranged from
         * left to right. The trees are represented by an integer array fruits where
         * fruits[i] is the type of fruit the ith tree produces.
         * 
         * You want to collect as much fruit as possible. However, the owner has some
         * strict rules that you must follow:
         * 
         * You only have two baskets, and each basket can only hold a single type of
         * fruit. There is no limit on the amount of fruit each basket can hold.
         * Starting from any tree of your choice, you must pick exactly one fruit from
         * every tree (including the start tree) while moving to the right. The picked
         * fruits must fit in one of your baskets. Once you reach a tree with fruit that
         * cannot fit in your baskets, you must stop. Given the integer array fruits,
         * return the maximum number of fruits you can pick.
         * 
         * Example 1:
            Input: fruits = [1,2,1]
            Output: 3
            Explanation: We can pick from all 3 trees.
            Example 2:

            Input: fruits = [0,1,2,2]
            Output: 3
            Explanation: We can pick from trees [1,2,2].
            If we had started at the first tree, we would only pick from trees [0,1].
            Example 3:

            Input: fruits = [1,2,3,2,2]
            Output: 4
            Explanation: We can pick from trees [2,3,2,2].
            If we had started at the first tree, we would only pick from trees [1,2].
            Example 4:

            Input: fruits = [3,3,3,1,2,1,1,2,3,3,4]
            Output: 5
            Explanation: We can pick from trees [1,2,1,1,2].
         */

        public static int totalFruit(int[] tree) {
            int begin = 0, end = 0, type = 0, len = 0;
            Map<Integer, Integer> map = new HashMap<>();
            while (end < tree.length) {
                int in = tree[end++]; // new character gets in from string right.
                if (map.getOrDefault(in, 0) == 0)
                    type++;
                map.put(in, map.getOrDefault(in, 0) + 1);
                while (type > 2) {
                    int out = tree[begin++]; // old character gets out from string left.
                    System.out.println("Out: " + out + " freq " + map.get(out));
                    if (map.put(out, map.get(out) - 1) == 1)
                    // Returns the value to which this map previously associated the key,
                    //or null if the map contained no mapping for the key.
                    /** the above can be written as
                     *  
                     * if(map.get(out)==1)
                     * {
                     *      map.put(out, map.get(out)-1);
                     * }
                     * 
                     */
                        type--;
                }
                len = Math.max(len, end - begin);
            }
            return len;
        }

        public static void main(String[] args){
            System.out.println(totalFruit(new int[]{3,3,3,1,2,1,1,2,3,3,4}));
        }

    }
    
    static class MaxConsecutiveOnesInBinaryArrayIfYouCanFlipAtMostKZeroes {
        /**
         * Given a binary array nums and an integer k, return the maximum number of
         * consecutive 1's in the array if you can flip at most k 0's.
         * 
         * Example 1:
         * 
         * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2 Output: 6 Explanation:
         * [1,1,1,0,0,1,1,1,1,1,1] Bolded numbers were flipped from 0 to 1. The longest
         * subarray is underlined. Example 2:
         * 
         * Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3 Output: 10
         * Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1] Bolded numbers were
         * flipped from 0 to 1. The longest subarray is underlined.
         */
        public int longestOnes(int[] A, int K) {
            int start = 0;
            int end = 0;
            int max = 0;
            
            int numZeroes = 0;
            for (end= 0; end<A.length; end++) {
        
                if (A[end]==0) numZeroes++;
                
                if (numZeroes > K) {
                    // If the used number of zeroes > K, then you will need to increment the start
                    // To identify the next window such that number of zeroes == k
                    // 
                    if (A[start]==0) numZeroes--;
                    start ++;
                }
                if (numZeroes <= K) {
                    // this is probably what I could come up during interview...
                    max = Math.max(max, end-start +1 );
                }
            }
            return max;
        }
    }
    
    static class BinarySubarraysWithSum_PrefixSumWithHashMap {
        /**
         * Given a binary array nums and an integer goal, return the number of non-empty
         * subarrays with a sum goal.
         * 
         * A subarray is a contiguous part of the array.
         * 
         * Example 1:
         * 
         * Input: nums = [1,0,1,0,1], goal = 2 Output: 4 Explanation: The 4 subarrays
         * are bolded and underlined below: [1,0,1,0,1] [1,0,1,0,1] [1,0,1,0,1]
         * [1,0,1,0,1] Example 2:
         * 
         * Input: nums = [0,0,0,0,0], goal = 0 Output: 15
         */
        /**
         * Intuition
         * 
         * Let P[i] = A[0] + A[1] + ... + A[i-1]. Then P[j+1] - P[i] = A[i] + A[i+1] +
         * ... + A[j], the sum of the subarray [i, j].
         * 
         * Hence, we are looking for the number of i < j with P[j] - P[i] = S.
         * 
         * Algorithm
         * 
         * For each j, let's count the number of i with P[j] = P[i] + S. This is
         * analogous to counting the number of subarrays ending in j with sum S.
         * 
         * It comes down to counting how many P[i] + S we've seen before. We can keep
         * this count on the side to help us find the final answer.
         */
        public int numSubarraysWithSum(int[] A, int S) {
            int N = A.length;
            int[] P = new int[N + 1];
            for (int i = 0; i < N; ++i)
                P[i+1] = P[i] + A[i];
    
            Map<Integer, Integer> count = new HashMap();
            int ans = 0;
            for (int x: P) {
                ans += count.getOrDefault(x, 0);
                count.put(x+S, count.getOrDefault(x+S, 0) + 1);
            }
    
            return ans;
        }
    }

    static class SubstringWithConcatenationOfAllWordsInArray {
        /**
         * You are given a string s and an array of strings words of the same length.
         * Return all starting indices of substring(s) in s that is a concatenation of
         * each word in words exactly once, in any order, and without any intervening
         * characters.
         * 
         * You can return the answer in any order.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: s = "barfoothefoobarman", words = ["foo","bar"] Output: [0,9]
         * Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar"
         * respectively. The output order does not matter, returning [9,0] is fine too.
         * Example 2:
         * 
         * Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
         * Output: [] Example 3:
         * 
         * Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"] Output:
         * [6,9,12]
         * 
         */
        public static List<Integer> findSubstring(String source, String[] words) {
            /**
             * The idea of the algorithm is similar to above. 
             * 
             * The difference is this initializes a map with all the occurrences of words in words[]. 
             * Starts iterating the string of length (words[0].length) in source.
             * Extracts the word of length and check if it matches with any word in the word map.
             * If it does, add or increment to the current map (which tracks what and how many words were traversed).
             * If the word is found, it's incremented
             * 
             * 
             */
            List<Integer> res = new LinkedList<>();
            if (words.length == 0 || source.length() < words.length * words[0].length())
                return res;
            int N = source.length();
            int M = words.length; // *** length
            int wl = words[0].length();
            Map<String, Integer> wordMap = new HashMap<>(), curMap = new HashMap<>();
            for (String s : words) {
                // map contains the words and their occurrences that we should check for existence in source string.
                if (wordMap.containsKey(s))
                    wordMap.put(s, wordMap.get(s) + 1);
                else
                    wordMap.put(s, 1);
            }
            String str = null, tmp = null;
            for (int i = 0; i < wl; i++) {
                int count = 0; // remark: reset count
                int start = i;
                System.out.println("I is "+ i);
                for (int r = i; r + wl <= N; r += wl) { 
                    // Iterates the source string by word length.
                    /** Iteration for I-0 is bar, foo, the, foo, bar, man */
                    str = source.substring(r, r + wl);
                    System.out.println(" Inside for loop "+ str);
                    // System.out.println("Freq Map "+ map);
                    System.out.println(" Index map in For loop " + curMap);
                    if (wordMap.containsKey(str)) {
                        // If the substring of length 3 extracted from the source contains the word in the words[] array
                        if (curMap.containsKey(str))
                            curMap.put(str, curMap.get(str) + 1);
                        else
                            curMap.put(str, 1);
                        System.out.println(" Index map in if condition " + curMap);
                        
                        
                        if (curMap.get(str) <= wordMap.get(str))
                            count++;// Increment the count if the word is found.
                        while (curMap.get(str) > wordMap.get(str)) {
                            // If the word is repeating, for example, good is found twice in the source string.
                            System.out.println(" Inside while loop map "+ wordMap + " current map "+ curMap + " str is "+ str);
                            tmp = source.substring(start, start + wl);
                            curMap.put(tmp, curMap.get(tmp) - 1);//
                            start += wl; //Go to next window

                            // the same as
                            // https://leetcode.com/problems/longest-substring-without-repeating-characters/
                            if (curMap.get(tmp) < wordMap.get(tmp))
                                count--;

                        }
                        if (count == M) {// if all the words are found
                            res.add(start);// add the start index to the result.
                            tmp = source.substring(start, start + wl);
                            // This will be the first string found. in the example, it's bar.
                            System.out.println(" Temp is "+ tmp);
                            System.out.println(" before CurMap in If count==M is "+ curMap);
                            curMap.put(tmp, curMap.get(tmp) - 1);
                            // {bar=0, foo=1}
                            System.out.println(" after CurMap in If count==M is "+ curMap);
                            start += wl;// Go to next window: foothe, foo is the start, the is the next word(r)
                            count--;
                        }
                    } else {
                        // Clear the map when you encounter a substring which is not in the words map.
                        // This restarts to find a new concatenation of words from a different index.
                        curMap.clear();
                        count = 0;
                        start = r + wl;// not contain, so move the start
                        System.out.println("EXITED.....");
                    }
                }
                curMap.clear();
            }
            return res;
        }

        public static void main(String[] args) throws Exception {
            String source = "barfoothefoobarman";
            String words[] = new String[]{"foo","bar"};
            // String source2 = "wordgoodgoodgoodbestword";
            // String words2[] = new String[]{"word","good","best","word"};
            System.out.println(findSubstringSimple(source, words));
        }

        public static List<Integer> findSubstringSimple(String s, String[] words) {
            if (s == null || words == null || s.length() == 0 || words.length == 0) {
                return new ArrayList<>();
            }
            Map<String, Integer> counts = new HashMap<>();
            for (String word : words) { //Add the expected times from words array to map.
                counts.put(word, counts.getOrDefault(word, 0) + 1);
            }
            
            List<Integer> r = new ArrayList<>();
            int sourceLength = s.length();
            int num = words.length;
            int wordLen = words[0].length();
    
            for (int i = 0; i < sourceLength - num * wordLen + 1; i++) { // i< Source string - concatenated length of words.
                String sub = s.substring(i, i + num * wordLen);// Extract substring from i to concat length.
                if (isConcat(sub, counts, wordLen)) {
                    // check if all the words of required count is found in the substring.
                    r.add(i);//add the index to result.
                }
            }
            return r;
        }
        
        /**
         * */
        private static boolean isConcat(String sub, Map<String, Integer> counts, int wordLen) {
            Map<String, Integer> seen = new HashMap<>();
            for (int i = 0; i < sub.length(); i += wordLen) {
                String sWord = sub.substring(i, i + wordLen);// Take each substring of length wordLen, and put it into seen.
                seen.put(sWord, seen.getOrDefault(sWord, 0) + 1);
            }
            return seen.equals(counts);//If the seen and expected are equals return true.
        }
    }
    
    static class FindAllAnagramsInString {
        /** Given two strings s and p, return an array of all the start indices of p's anagrams in s. 
         * You may return the answer in any order.

            Example 1:

            Input: s = "cbaebabacd", p = "abc"
            Output: [0,6]
            Explanation:
            The substring with start index = 0 is "cba", which is an anagram of "abc".
            The substring with start index = 6 is "bac", which is an anagram of "abc".
            Example 2:

            Input: s = "abab", p = "ab"
            Output: [0,1,2]
            Explanation:
            The substring with start index = 0 is "ab", which is an anagram of "ab".
            The substring with start index = 1 is "ba", which is an anagram of "ab".
            The substring with start index = 2 is "ab", which is an anagram of "ab".
 */
        public List<Integer> findAnagrams(String src, String tar) {
            List<Integer> result = new LinkedList<>();
            if(tar.length()> src.length()) return result;
            Map<Character, Integer> targetMap = new HashMap<>();
            for(char c : tar.toCharArray()){
                // a character expected number of times.
                targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
            }
            int counter = targetMap.size();// equals to number of characters in target word
            
            int begin = 0, end = 0;
            int head = 0;
            int len = Integer.MAX_VALUE;
            
            
            while(end < src.length()){
                char sourceChar = src.charAt(end);
                if( targetMap.containsKey(sourceChar) ){//If the source's char is the expected char
                    targetMap.put(sourceChar, targetMap.get(sourceChar)-1);//decrement the expected.
                    if(targetMap.get(sourceChar) == 0) counter--; // if all the occurrences of char is found, decrement counter
                }
                end++;
                
                while(counter == 0){//when all the characteres are found in the source.
                    char tempc = src.charAt(begin);// Go to next window, incrementing begin.
                    // Get the char at begin.
                    if(targetMap.containsKey(tempc)){// if it already exists in target
                        targetMap.put(tempc, targetMap.get(tempc) + 1);// revert the decrement we made.
                        if(targetMap.get(tempc) > 0){
                            // Increment counter to check for the next window
                            counter++;
                        }
                    }
                    if(end-begin == tar.length()){
                        result.add(begin);//add the result.
                    }
                    begin++;
                }
                
            }
            return result;
        }
    }
   
    static class ShortestWayToFormString_FindMinNumOfSubseqOfSourceSuchThatTheirConcatenationEqualsTarget {
        /**
         * From any string, we can form a subsequence of that string by deleting some
         * number of characters (possibly no deletions).
         * 
         * Given two strings source and target, return the minimum number of
         * subsequences of source such that their concatenation equals target. If the
         * task is impossible, return -1.
         */
        /**
         * Example 1:

            Input: source = "abc", target = "abcbc"
            Output: 2
            Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".
            Example 2:

            Input: source = "abc", target = "acdbc"
            Output: -1
            Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.
            Example 3:

            Input: source = "xyz", target = "xzyxz"
            Output: 3
            Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
            

            Constraints:

            Both the source and target strings consist of only lowercase English letters from "a"-"z".
            The lengths of source and target string are between 1 and 1000.
            Hints
            Which conditions have to been met in order to be impossible to form the target string?
            If there exists a character in the target string which doesn't exist in the source string then it will be impossible to form the target string.
            Assuming we are in the case which is possible to form the target string, how can we assure the minimum number of used subsequences of source?
            For each used subsequence try to match the leftmost character of the current subsequence with the leftmost character of the target string, 
            if they match then erase both character otherwise erase just the subsequence character whenever the current subsequence gets empty, 
            reset it to a new copy of subsequence and increment the count, do this until the target sequence gets empty. 
            Finally return the count.
         */
        int shortestWay(String source, String target) {
        
            // Uses two pointer technique
            /**
             * 
             *  abc: xyz, abcbc: xzyxz
             *  Idea is to use two pointers on the source and target words.
             *  If both the words have same char, increment both the pointers, 
             *  else increment the source pointer and pause the target pointer.
             *  If the source reaches end, set the source to 0, 
             *  and increment counter (which means the number of subsequence found so far).
             *  
             *  source = "abc", target = "acdbc" 
             *   
             *  If a particular char is not found in the source, we should return -1.
             *  To do this, keep a variable on the targetIndex before the traversal on both the words.
             *  After traversal if the source is exhausted and the target index remains the same, then return -1.
             */
        
            int n = source.length();
            int m = target.length();
            
            if(n == 0 || m == 0)  return 0;
            
            int tIndex = 0;
            int sIndex = 0;
            
            int count = 0;
            while(tIndex < m) {
                
                int st = tIndex;
                while(sIndex < n && tIndex < m) {
                    
                    if(source.charAt(sIndex) == target.charAt(tIndex)) {
                        sIndex++;
                        tIndex++;
                    }else {
                        sIndex++;
                    }
                }
                if(sIndex == n && st == tIndex) {
                    return -1;
                }
                if(sIndex == n || tIndex == m) {
                    sIndex = 0;
                    count++;
                }
            }
            return count;
        }
    }

    static class IsSourceSubsequenceOfTarget {
        /**
         * Given a string s and a string t, check if s is subsequence of t.
         * 
         * A subsequence of a string is a new string which is formed from the original
         * string by deleting some (can be none) of the characters without disturbing
         * the relative positions of the remaining characters. (ie, "ace" is a
         * subsequence of "abcde" while "aec" is not).
         * 
         * Follow up: If there are lots of incoming S, say S1, S2, ... , Sk where k >=
         * 1B, and you want to check one by one to see if T has its subsequence. In this
         * scenario, how would you change your code?
         * 
         * Example 1:
         * 
         * Input: s = "abc", t = "ahbgdc" Output: true Example 2:
         * 
         * Input: s = "axc", t = "ahbgdc" Output: false
         * 
         */
        String source, target;
        Integer leftBound, rightBound;

        private boolean rec_isSubsequence(int leftIndex, int rightIndex) {
            // Time complexity: O(T), space complexity: O(T), due to recursion.
            // base cases
            if (leftIndex == leftBound)
                return true;
            if (rightIndex == rightBound)
                return false;

            // consume both strings or just the target string
            if (source.charAt(leftIndex) == target.charAt(rightIndex))
                ++leftIndex;
            ++rightIndex;

            return rec_isSubsequence(leftIndex, rightIndex);// tail recursion.
        }

        public boolean isSubsequence(String s, String t) {
            this.source = s;
            this.target = t;
            this.leftBound = s.length();
            this.rightBound = t.length();

            return rec_isSubsequence(0, 0);
        }

        public boolean isSubsequenceUsingTwoPointers(String s, String t) {
            /**
             * Time Complexity: O(|T|) Space Complexity: O(1)
             */
            Integer leftBound = s.length(), rightBound = t.length();
            Integer pLeft = 0, pRight = 0;

            while (pLeft < leftBound && pRight < rightBound) {
                // move both pointers or just the right pointer
                if (s.charAt(pLeft) == t.charAt(pRight)) {
                    pLeft += 1;
                }
                pRight += 1;
            }
            return pLeft == leftBound;
        }

        public boolean isSubsequenceUsingMap(String s, String t) {
            // Source: abc, target: abbadc. store the indices of target in hashmap.
            // precomputation, build the hashmap out of the target string,
            // so that for S1,S2....Sn we don't traverse the target string.
            HashMap<Character, List<Integer>> letterIndicesTable = new HashMap<>();
            for (int i = 0; i < t.length(); ++i) {
                if (letterIndicesTable.containsKey(t.charAt(i)))
                    letterIndicesTable.get(t.charAt(i)).add(i);
                else {
                    ArrayList<Integer> indices = new ArrayList<Integer>();
                    indices.add(i);
                    letterIndicesTable.put(t.charAt(i), indices);
                }
            }

            Integer currMatchIndex = -1;
            for (char letter : s.toCharArray()) {
                if (!letterIndicesTable.containsKey(letter))
                    return false; // no match, early exist

                boolean isMatched = false;
                // greedy match with linear search, we can use binary search as well, in which
                // case
                // the time complexity will be O(T) + O(SlogT), LogT for binary search, S is the
                // source word.
                for (Integer matchIndex : letterIndicesTable.get(letter)) {
                    if (currMatchIndex < matchIndex) {
                        /**
                         * For instance, for the character a whose corresponding indices are [0, 3] , we
                         * need to pick an index out of all the appearances as a match. Suppose at
                         * certain moment, the ltter pointer is located at the index 1 . Then, the
                         * suitable greedy match would be the index of 3 , which is the first index that
                         * is larger than the current position of the target pointer.
                         */
                        currMatchIndex = matchIndex;
                        isMatched = true;
                        break;
                    }
                }

                if (!isMatched)
                    return false;
            }

            // consume all characters in the source string
            return true;
        }

        /**
         * With dynamic programming, essentially we build a matrix ( dp[row][col] )
         * where each element in the matrix represents the maximal number of deletions
         * that we can have between a prefix of source string and a prefix of the target
         * string, namely source[0:col] and target[0:row] .
         * 
         * dp table
         * 
         * In the above graph, we show an example of the dp matrix. For instance, for
         * the element of dp[2][3] as we highlighted, it indicates that the maximal
         * number of deletions ( i.e. matches) that we can have between the source
         * prefix ac and the target prefix abc is 2.
         * 
         * Suppose that we can obtain this dp matrix, the problem becomes simple.
         * 
         * Once we have the dp matrix, it boils down to see if we can have as many
         * deletions as the number of characters in the source string, i.e. if we could
         * consume the entire source string with the matches from the target string.
         * 
         * In other words, it suffices to examine the last row of the dp matrix (
         * dp[len(source)] ), to see if there is any number that is equal to the length
         * of the source string.
         * 
         * Since the problem of edit distance is a hard one and so with its solution, we
         * invite you to check out our article of edit distance , which explains the
         * solution in details.
         * 
         * Here we present a modified version of the edit distance solution, based on
         * the above idea.
         */
        public boolean isSubsequenceUsingDP(String s, String t) {
            // Time and space complexity: O(S T)

        Integer sourceLen = s.length(), targetLen = t.length();
        // the source string is empty
        if (sourceLen == 0)
            return true;

        int[][] dp = new int[sourceLen + 1][targetLen + 1];
        // DP calculation, we fill the matrix column by column, bottom up
        for (int col = 1; col <= targetLen; ++col) {
            for (int row = 1; row <= sourceLen; ++row) {
                if (s.charAt(row - 1) == t.charAt(col - 1))
                    // find another match
                    dp[row][col] = dp[row - 1][col - 1] + 1;
                else
                    // retrieve the maximal result from previous prefixes
                    dp[row][col] = Math.max(dp[row][col - 1], dp[row - 1][col]);
            }
            // check if we can consume the entire source string,
            // with the current prefix of the target string.
            if (dp[sourceLen][col] == sourceLen)
                return true;
        }

        // matching failure
        return false;
    }

    }

    static class NumberOfMatchingSubsequencesOfStringFromArrayOfWords {
        /**
         * Given string S and a dictionary of words words, find the number of words[i]
         * that is a subsequence of S.
         * 
         * Example : Input: S = "abcde" words = ["a", "bb", "acd", "ace"] Output: 3
         * Explanation: There are three words in words that are a subsequence of S :
         * "a", "acd", "ace". Note:
         * 
         * All words in words and S will only consists of lowercase letters. The length
         * of S will be in the range of [1, 50000]. The length of words will be in the
         * range of [1, 5000]. The length of words[i] will be in the range of [1, 50].
         */
        /***
         * 
         * Intuition
         * 
            Since the length of S is large, let's think about ways to iterate through S only once, 
            instead of many times as in the brute force solution.

            We can put words into buckets by starting character. If for example we have words = ['dog', 'cat', 'cop'] , 
            then we can group them 'c' : ('cat', 'cop'), 'd' : ('dog',) . 
            This groups words by what letter they are currently waiting for. 
            Then, while iterating through letters of S , we will move our words through different buckets.

            For example, if we have a string like S = 'dcaog' :

            heads = 'c' : ('cat', 'cop'), 'd' : ('dog',) at beginning;
            heads = 'c' : ('cat', 'cop'), 'o' : ('og',) after S[0] = 'd' ;
            heads = 'a' : ('at',), 'o' : ('og', 'op') after S[0] = 'c' ;
            heads = 'o' : ('og', 'op'), 't': ('t',) after S[0] = 'a' ;
            heads = 'g' : ('g',), 'p': ('p',), 't': ('t',) after S[0] = 'o' ;
            heads = 'p': ('p',), 't': ('t',) after S[0] = 'g' ;
            Algorithm

            Instead of a dictionary, we'll use an array heads of length 26 , one entry for each letter of the alphabet. 
            For each letter in S , we'll take all the words waiting for that letter, and have them wait for the next letter in that word. 
            If we use the last letter of some word, it adds 1 to the answer.
         */
        public int numMatchingSubseq(String S, String[] words) {
            int ans = 0;
            ArrayList<Node>[] heads = new ArrayList[26];
            for (int i = 0; i < 26; ++i)
                heads[i] = new ArrayList<Node>();
    
            for (String word: words)
                heads[word.charAt(0) - 'a'].add(new Node(word, 0));
    
            for (char c: S.toCharArray()) {
                ArrayList<Node> old_bucket = heads[c - 'a'];
                heads[c - 'a'] = new ArrayList<Node>();
    
                for (Node node: old_bucket) {
                    node.index++;
                    if (node.index == node.word.length()) {
                        ans++;
                    } else {
                        heads[node.word.charAt(node.index) - 'a'].add(node);
                    }
                }
                old_bucket.clear();
            }
            return ans;
        }
        class Node {
            String word;
            int index;
            public Node(String w, int i) {
                word = w;
                index = i;
            }
        }
    }

    static class BackspaceStringCompare {
        /**
         * 
         * Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.
            Note that after backspacing an empty text, the text will continue empty.

            Example 1:

            Input: S = "ab#c", T = "ad#c"
            Output: true
            Explanation: Both S and T become "ac".
            Example 2:

            Input: S = "ab##", T = "c#d#"
            Output: true
            Explanation: Both S and T become "".
            Example 3:

            Input: S = "a##c", T = "#a#c"
            Output: true
            Explanation: Both S and T become "c".
            Example 4:

            Input: S = "a#c", T = "b"
            Output: false
            Explanation: S becomes "c" while T becomes "b".
            Note:

            1 <= S.length <= 200
            1 <= T.length <= 200
            S and T only contain lowercase letters and '#' characters.
         */
        
         /**
          * When writing a character, it may or may not be part of the final string
          * depending on how many backspace keystrokes occur in the future.
          * 
          * If instead we iterate through the string in reverse, then we will know how
          * many backspace characters we have seen, and therefore whether the result
          * includes our character.
          * 
          * Algorithm
          * 
          * Iterate through the string in reverse. If we see a backspace character, the
          * next non-backspace character is skipped. If a character isn't skipped, it is
          * part of the final answer.
          * 
          */
          public boolean backspaceCompare(String S, String T) {
            int i = S.length() - 1, j = T.length() - 1;
            int skipS = 0, skipT = 0;
    
            while (i >= 0 || j >= 0) { // While there may be chars in build(S) or build (T)
                while (i >= 0) { // Find position of next possible char in build(S)
                    if (S.charAt(i) == '#') {skipS++; i--;}
                    else if (skipS > 0) {skipS--; i--;}
                    else break;
                }
                while (j >= 0) { // Find position of next possible char in build(T)
                    if (T.charAt(j) == '#') {skipT++; j--;}
                    else if (skipT > 0) {skipT--; j--;}
                    else break;
                }
                // If two actual characters are different
                if (i >= 0 && j >= 0 && S.charAt(i) != T.charAt(j))
                    return false;
                // If expecting to compare char vs nothing
                if ((i >= 0) != (j >= 0))
                    return false;
                i--; j--;
            }
            return true;
        }

        /**
         * The Leetcode file system keeps a log each time some user performs a change
         * folder operation.
         * 
         * The operations are described below:
         * 
         * "../" : Move to the parent folder of the current folder. (If you are already
         * in the main folder, remain in the same folder). "./" : Remain in the same
         * folder. "x/" : Move to the child folder named x (This folder is guaranteed to
         * always exist). You are given a list of strings logs where logs[i] is the
         * operation performed by the user at the ith step.
         * 
         * The file system starts in the main folder, then the operations in logs are
         * performed.
         * 
         * Return the minimum number of operations needed to go back to the main folder
         * after the change folder operations.
         */
        /**
         * Input: logs = ["d1/","d2/","../","d21/","./"] Output: 2 Explanation: Use this
         * change folder operation "../" 2 times and go back to the main folder.
         */
        public int minOperations(String[] logs) {
            int i = 0;
            for(String x: logs){
                if(x.charAt(0)=='.'){
                    if(x.charAt(1)=='.'&&i>0){
                        i--;
                    }
                }
                else{
                    i++;
                }
            }
            return i;
        }

        /**You are keeping score for a baseball game with strange rules. The game consists of several rounds, where the scores of past rounds may affect future rounds' scores.

        At the beginning of the game, you start with an empty record. You are given a list of strings ops, where ops[i] is the ith operation you must apply to the record and is one of the following:

        An integer x - Record a new score of x.
        "+" - Record a new score that is the sum of the previous two scores. It is guaranteed there will always be two previous scores.
        "D" - Record a new score that is double the previous score. It is guaranteed there will always be a previous score.
        "C" - Invalidate the previous score, removing it from the record. It is guaranteed there will always be a previous score.
        Return the sum of all the scores on the record.

        

        Example 1:

        Input: ops = ["5","2","C","D","+"]
        Output: 30
        Explanation:
        "5" - Add 5 to the record, record is now [5].
        "2" - Add 2 to the record, record is now [5, 2].
        "C" - Invalidate and remove the previous score, record is now [5].
        "D" - Add 2 * 5 = 10 to the record, record is now [5, 10].
        "+" - Add 5 + 10 = 15 to the record, record is now [5, 10, 15].
        The total sum is 5 + 10 + 15 = 30. 
        
        Let's maintain the value of each valid round on a stack as we process the data. A stack is ideal since we only deal with operations involving the last or second-last valid round.
        
        */
        public int calPoints(String[] ops) {
            Stack<Integer> stack = new Stack();
    
            for(String op : ops) {
                if (op.equals("+")) {
                    int top = stack.pop();
                    int newtop = top + stack.peek();
                    stack.push(top);
                    stack.push(newtop);
                } else if (op.equals("C")) {
                    stack.pop();
                } else if (op.equals("D")) {
                    stack.push(2 * stack.peek());
                } else {
                    stack.push(Integer.valueOf(op));
                }
            }
    
            int ans = 0;
            for(int score : stack) ans += score;
            return ans;
        }


    }

    static class EncodeAndDecodeStrings  {
        /**
         * Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is decoded back to the original list of strings.
            Machine 1 (sender) has the function:

            string encode(vector<string> strs) {
            // ... your code
            return encoded_string;
            }
            Machine 2 (receiver) has the function:
            vector<string> decode(string s) {
            //... your code
            return strs;
            }
            So Machine 1 does:

            string encoded_string = encode(strs);
            and Machine 2 does:

            vector<string> strs2 = decode(encoded_string);
            strs2 in Machine 2 should be the same as strs in Machine 1.

            Implement the encode and decode methods.

            Note:

            The string may contain any possible characters out of 256 valid ascii characters. Your algorithm should be generalized enough to work on any possible characters.
            Do not use class member/global/static variables to store states. Your encode and decode algorithms should be stateless.
            Do not rely on any library method such as eval or serialize methods. You should implement your own encode/decode algorithm.
         */
        /**
         * Naive solution here is to join strings using delimiters.
         * 
         * What to use as a delimiter? Each string may contain any possible characters
         * out of 256 valid ascii characters.
         * 
         * Seems like one has to use non-ASCII unichar character, for example
         * unichr(257) in Python and Character.toString((char)257) in Java (it's
         * character ā ).
         */
        class NaiveSolution {
            // Encodes a list of strings to a single string.
            public String encode(List<String> strs) {
                if (strs.size() == 0)
                    return Character.toString((char) 258);

                String d = Character.toString((char) 257);
                StringBuilder sb = new StringBuilder();
                for (String s : strs) {
                    sb.append(s);
                    sb.append(d);
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            }

            // Decodes a single string to a list of strings.
            public List<String> decode(String s) {
                String d = Character.toString((char) 258);
                if (s.equals(d))
                    return new ArrayList();

                d = Character.toString((char) 257);
                return Arrays.asList(s.split(d, -1));
            }
        }

        class ChunkedTransferEncoding {
            /**
             * Time complexity : (N) both for encode and decode, where N is a number of
             * strings in the input array.
             * 
             * Space complexity : (1) for encode to keep the output, since the output is
             * one string.(N) for decode keep the output, since the output is an array of
             * strings.
             */
            /**
             * Pay attention to this approach because last year Google likes to ask that
             * sort of low-level optimisation. Serialize and deserialize BST problem is a
             * similar example.
             * 
             * This approach is based on the encoding used in HTTP v1.1 . It doesn't depend
             * on the set of input characters, and hence is more versatile and effective
             * than Approach 1.
             * 
             * Data stream is divided into chunks. Each chunk is preceded by its size in
             * bytes.
             * 
             * Encoding Algorithm
             * 
             * fig
             * 
             * Iterate over the array of chunks, i.e. strings.
             * 
             * For each chunk compute its length, and convert that length into 4-bytes
             * string.
             * 
             * Append to encoded string :
             * 
             * 4-bytes string with information about chunk size in bytes.
             * 
             * Chunk itself.
             * 
             * Return encoded string.
             * 
             * Decoding Algorithm
             * 
             * fig
             * 
             * Iterate over the encoded string with a pointer i initiated as 0. While i < n
             * :
             * 
             * Read 4 bytes s[i: i + 4] . It's chunk size in bytes. Convert this 4-bytes
             * string to integer length .
             * 
             * Move the pointer by 4 bytes i += 4 .
             * 
             * Append to the decoded array string s[i: i + length] .
             * 
             * Move the pointer by length bytes i += length .
             * 
             * Return decoded array of strings.
             * 
             */
            // Encodes string length to bytes string
            public String intToString(String s) {
                int x = s.length();
                char[] bytes = new char[4];
                for (int i = 3; i > -1; --i) {
                    bytes[3 - i] = (char) (x >> (i * 8) & 0xff);
                }
                return new String(bytes);
            }

            // Encodes a list of strings to a single string.
            public String encode(List<String> strs) {
                StringBuilder sb = new StringBuilder();
                for (String s : strs) {
                    sb.append(intToString(s));
                    sb.append(s);
                }
                return sb.toString();
            }

            // Decodes bytes string to integer
            public int stringToInt(String bytesStr) {
                int result = 0;
                for (char b : bytesStr.toCharArray())
                    result = (result << 8) + (int) b;
                return result;
            }

            // Decodes a single string to a list of strings.
            public List<String> decode(String s) {
                int i = 0, n = s.length();
                List<String> output = new ArrayList();
                while (i < n) {
                    int length = stringToInt(s.substring(i, i + 4));
                    i += 4;
                    output.add(s.substring(i, i + length));
                    i += length;
                }
                return output;
            }
        }
    }

    static class SerializeDeserializeBinaryTree {
        class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
          
            TreeNode(int x) {
              val = x;
            }
          }
        public TreeNode rdeserialize(List<String> l) {
            // Recursive deserialization.
            if (l.get(0).equals("null")) {
              l.remove(0);
              return null;
            }
        
            TreeNode root = new TreeNode(Integer.valueOf(l.get(0)));
            l.remove(0);
            root.left = rdeserialize(l);
            root.right = rdeserialize(l);
        
            return root;
          }
        
          // Decodes your encoded data to tree.
          public TreeNode deserialize(String data) {
            String[] data_array = data.split(",");
            List<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
            return rdeserialize(data_list);
          }
    }

    static class StringCompression {
        /**
         * Given an array of characters chars, compress it using the following
         * algorithm:
         * 
         * Begin with an empty string s. For each group of consecutive repeating
         * characters in chars:
         * 
         * If the group's length is 1, append the character to s. Otherwise, append the
         * character followed by the group's length. The compressed string s should not
         * be returned separately, but instead be stored in the input character array
         * chars. Note that group lengths that are 10 or longer will be split into
         * multiple characters in chars.
         * 
         * After you are done modifying the input array, return the new length of the
         * array.
         * 
         * 
         * Follow up: Could you solve it using only O(1) extra space?
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: chars = ["a","a","b","b","c","c","c"] Output: Return 6, and the first
         * 6 characters of the input array should be: ["a","2","b","2","c","3"]
         * Explanation: The groups are "aa", "bb", and "ccc". This compresses to
         * "a2b2c3". Example 2:
         * 
         * Input: chars = ["a"] Output: Return 1, and the first character of the input
         * array should be: ["a"] Explanation: The only group is "a", which remains
         * uncompressed since it's a single character. Example 3:
         * 
         * Input: chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"] Output:
         * Return 4, and the first 4 characters of the input array should be:
         * ["a","b","1","2"]. Explanation: The groups are "a" and "bbbbbbbbbbbb". This
         * compresses to "ab12". Example 4:
         * 
         * Input: chars = ["a","a","a","b","b","a","a"] Output: Return 6, and the first
         * 6 characters of the input array should be: ["a","3","b","2","a","2"].
         * Explanation: The groups are "aaa", "bb", and "aa". This compresses to
         * "a3b2a2". Note that each group is independent even if two groups have the
         * same character.
         * 
         * 
         * Approach #1: Read and Write Heads [Accepted] Intuition
         * 
         * We will use separate pointers read and write to mark where we are reading and
         * writing from. Both operations will be done left to right alternately: we will
         * read a contiguous group of characters, then write the compressed version to
         * the array. At the end, the position of the write head will be the length of
         * the answer that was written.
         * 
         * Algorithm
         * 
         * Let's maintain anchor , the start position of the contiguous group of
         * characters we are currently reading.
         * 
         * Now, let's read from left to right. We know that we must be at the end of the
         * block when we are at the last character, or when the next character is
         * different from the current character.
         * 
         * When we are at the end of a group, we will write the result of that group
         * down using our write head. chars[anchor] will be the correct character, and
         * the length (if greater than 1) will be read - anchor + 1 . We will write the
         * digits of that number to the array.
         */
        public int compress(char[] chars) {
            int i = 0, j = 0;
            int index = 0;
            while (i < chars.length) {
                char c = chars[i];
                chars[index++] = c;
                j = i;
                while (j < chars.length && chars[j] == c) {
                    j++;
                }
                int freq = j - i;
                if (freq != 1 && freq < 10) {
                    chars[index++] = (char)(freq + '0');
                } else if (freq >= 10){
                    String freqStr = Integer.toString(freq);
                    for (int k = 0; k < freqStr.length(); k++) {
                        chars[index++] = freqStr.charAt(k);
                    }
                }
                i = j;
            }
            return index;
        }
    }

    static class RearrangeStringSuchThatIdenticalCharsAreKDistanceApart {
        public String rearrangeString(String s, int k) {
            /**
             * Given a non-empty string s and an integer k, rearrange the string such that
             * the same characters are at least distance k from each other.
             * 
             * All input strings are given in lowercase letters. If it is not possible to
             * rearrange the string, return an empty string "".
             * 
             * Example 1:
             * 
             * Input: s = "aabbcc", k = 3 Output: "abcabc" Explanation: The same letters are
             * at least distance 3 from each other. Example 2:
             * 
             * Input: s = "aaabc", k = 3 Output: "" Explanation: It is not possible to
             * rearrange the string. Example 3:
             * 
             * Input: s = "aaadbbcc", k = 2 Output: "abacabcd" Explanation: The same letters
             * are at least distance 2 from each other.
             */
            if (s == null || s.length() == 0 || k <= 0) {
                return s;
            }
            
            int[] hash = new int[26];
            StringBuilder res = new StringBuilder();
            
            for (char c : s.toCharArray()) {
                hash[c - 'a']++; // Mainain a hash for all the characters on its frequency.
            }
            
            Queue<Node> pQueue = new PriorityQueue<>((pre, next) -> next.count - pre.count); // Max occurence first.
            Queue<Node> waitingList = new LinkedList<>();
            
            for (int i = 0; i < 26; i++) {
                if (hash[i] > 0) {
                    pQueue.offer(new Node((char)(i + 'a'), hash[i]));// add the characters to the queue.
                }
            }
            
            while (!pQueue.isEmpty()) {
                Node now = pQueue.poll();//Get the max occurence
                res.append(now.val);// append it first.
                now.count--;// reduce the count
                
                waitingList.add(now);// add the character to waiting list.
                if (waitingList.size() >= k) {
                    // if the waiting list becomes size k, poll and add it to the queue, if the count is >0
                    Node temp = waitingList.poll();
                    if (temp.count > 0) {
                        pQueue.offer(temp);
                    }
                }
            }
            
            return res.length() == s.length() ? res.toString() : "";
            
        }
        
        private class Node {
            char val;
            int count;
            
            public Node(char val, int count) {
                this.val = val;
                this.count = count;
            }
        }
    }

    static class GroupShiftedStrings {
        /**
         * Description Given a string, we can "shift" each of its letter to its
         * successive letter, for example: "abc" -> "bcd". We can keep "shifting" which
         * forms the sequence:
         * 
         * "abc" -> "bcd" -> ... -> "xyz" Given a list of non-empty strings which
         * contains only lowercase alphabets, group all strings that belong to the same
         * shifting sequence.
         * 
         * Example:
         * 
         * Input: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
         * 
         * Output: [ ["abc","bcd","xyz"], ["az","ba"], ["acef"], ["a","z"] ]
         */
        public static List<List<String>> groupStrings(String[] strings) {
            Map<String, List<String>> map = new HashMap<>();

            for (int i = 0; i < strings.length; i++) {
                String s;
                if (strings[i].length() > 1) {
                    StringBuilder sb = new StringBuilder();
                    String current = strings[i];
                    System.out.println(current);
                    for (int j = 0; j < current.length() - 1; j++) {
                        sb.append(((current.charAt(j + 1) - 'a') - (current.charAt(j) - 'a') + 26) % 26).append(':');
                        System.out.println(sb.toString());
                        /**
                         * Figure out  the difference between the next and the current char, and keep appending it.
                         * Add the key to map with this string added to the list.
                         * abc 
                            1:
                            1:1:
                            bcd
                            1:
                            1:1:
                            acef
                            2:
                            2:2:
                            2:2:1:
                            xyz
                            1:
                            1:1:
                            az
                            25:
                            ba
                            25:
                         */
                    }
                    s = sb.toString();
                } else {
                    s = "a";
                }

                if (map.containsKey(s)) {
                    map.get(s).add(strings[i]);
                } else {
                    List<String> l = new ArrayList<>();
                    l.add(strings[i]);
                    map.put(s, l);
                }
            }

            return new ArrayList<>(map.values());
        }
        public static void main(String[] args) throws Exception {
            String[] str = new String[] {"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"};
            groupStrings(str);
        }
    }

    static class SwapAdjacentCharactersInLRString {
        /**
         * In a string composed of 'L', 'R', and 'X' characters, like "RXXLRXRXL", 
         * a move consists of either replacing one occurrence of "XL" with "LX", or 
         * replacing one occurrence of "RX" with "XR". 
         * Given the starting string start and the ending string end, 
         * return True if and only if there exists a sequence of moves to transform one string to the other.
            Example 1:

            Input: start = "RXXLRXRXL", end = "XRLXXRRLX"
            Output: true
            Explanation: We can transform start to end following these steps:
            RXXLRXRXL ->
            XRXLRXRXL ->
            XRLXRXRXL ->
            XRLXXRRXL ->
            XRLXXRRLX
            Example 2:

            Input: start = "X", end = "L"
            Output: false
            Example 3:

            Input: start = "LLR", end = "RRL"
            Output: false
            Example 4:

            Input: start = "XL", end = "LX"
            Output: true
            Example 5:

            Input: start = "XLLR", end = "LXLX"
            Output: false
         */
        public boolean canTransform(String start, String end) {
            if (!start.replace("X", "").equals(end.replace("X", ""))) 
            //if the number of X's aren't equal, return false.
                return false;
            
            int p1 = 0;
            int p2 = 0;
            
            while(p1 < start.length() && p2 < end.length()){
                
                // get the non-X positions of 2 strings
                while(p1 < start.length() && start.charAt(p1) == 'X'){
                    p1++;
                }
                while(p2 < end.length() && end.charAt(p2) == 'X'){
                    p2++;
                }
                
                //if both of the pointers reach the end the strings are transformable
                if(p1 == start.length() && p2 == end.length()){
                    return true;
                }
                // if only one of the pointer reach the end they are not transformable
                if(p1 == start.length() || p2 == end.length()){
                    return false;
                }
                
                if(start.charAt(p1) != end.charAt(p2)){ 
                    // The non-X positions are found, if they characters aren't equal, then return false.
                    return false;
                }
                // if the character is 'L', it can only be moved to the left. p1 should be greater or equal to p2.
                // Consider example: XL. This can be converted to LX.
                if(start.charAt(p1) == 'L' && p2 > p1){
                    return false;
                }
                // if the character is 'R', it can only be moved to the right. p2 should be greater or equal to p1.
                // Consider example: RX. This can be convered to XR.
                if(start.charAt(p1) == 'R' && p1 > p2){
                    return false;
                }
                p1++;
                p2++;
            }
            return true;
        }
    
    }

    static class MinimumInsertionsToMakeStringPalindrome {
        // A DP function to find minimum number
        // of insersions
        static int findMinInsertionsDP(char str[], int n) {
            // Create a table of size n*n. table[i][j]
            // will store minumum number of insertions
            // needed to convert str[i..j] to a palindrome.
            int table[][] = new int[n][n];
            int l, h, gap;

            // Fill the table
            for (gap = 1; gap < n; ++gap)
                for (l = 0, h = gap; h < n; ++l, ++h)
                    table[l][h] = (str[l] == str[h]) ? table[l + 1][h - 1]
                            : (Integer.min(table[l][h - 1], table[l + 1][h]) + 1);

            // Return minimum number of insertions
            // for str[0..n-1]
            return table[0][n - 1];
        }

        // Driver program to test above function.
        public static void main(String args[]) {
            String str = "geeks";
            System.out.println(findMinInsertionsDP(str.toCharArray(), str.length()));
        }
    }

    static class RemoveAllAdjacentDuplicatesInStringOfKlength {
        /**
         * Given a string S of lowercase letters, a duplicate removal consists of
         * choosing two adjacent and equal letters, and removing them.
         * 
         * We repeatedly make duplicate removals on S until we no longer can.
         * 
         * Return the final string after all such duplicate removals have been made. It
         * is guaranteed the answer is unique.
         * 
         * Example 1:
         * 
         * Input: "abbaca" Output: "ca" Explanation: For example, in "abbaca" we could
         * remove "bb" since the letters are adjacent and equal, and this is the only
         * possible move. The result of this move is that the string is "aaca", of which
         * only "aa" is possible, so the final string is "ca".
         */
        /**
         * We could trade an extra space for speed. The idea is to use an output stack
         * to keep track of only non duplicate characters. Here is how it works:
         * 
         * Current string character is equal to the last element in stack? Pop that last
         * element out of stack.
         * 
         * Current string character is not equal to the last element in stack? Add the
         * current character into stack.
         * 
         * Which data structure to use as the stack here?
         * 
         * Something that is fast to convert to string for output, for example list in
         * Python and StringBuilder in Java.
         * 
         * !?!../Documents/1047_LIS.json:1000,478!?!
         * 
         * Algorithm
         * 
         * Initiate an empty output stack.
         * 
         * Iterate over all characters in the string.
         * 
         * Current element is equal to the last element in stack? Pop that last element
         * out of stack.
         * 
         * Current element is not equal to the last element in stack? Add the current
         * element into stack.
         * 
         * Convert stack into string and return it.
         */
        public String removeDuplicates(String S) {
            StringBuilder sb = new StringBuilder();
            int sbLength = 0;
            for (char character : S.toCharArray()) {
                if (sbLength != 0 && character == sb.charAt(sbLength - 1)){
                    sb.deleteCharAt(sbLength - 1);
                    sbLength--;
                }
                else {
                    sb.append(character);
                    sbLength++;
                }
            }
            return sb.toString();
        }

        /**
         * You are given a string s and an integer k, a k duplicate removal consists of
         * choosing k adjacent and equal letters from s and removing them, causing the
         * left and the right side of the deleted substring to concatenate together.
         * 
         * We repeatedly make k duplicate removals on s until we no longer can.
         * 
         * Return the final string after all such duplicate removals have been made. It
         * is guaranteed that the answer is unique.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: s = "abcd", k = 2 Output: "abcd" Explanation: There's nothing to
         * delete. Example 2:
         * 
         * Input: s = "deeedbbcccbdaa", k = 3 Output: "aa" Explanation: First delete
         * "eee" and "ccc", get "ddbbbdaa" Then delete "bbb", get "dddaa" Finally delete
         * "ddd", get "aa" Example 3:
         * 
         * Input: s = "pbbcggttciiippooaais", k = 2 Output: "ps"
         */
        /**
         * Save the character c and its count to the stack. If the next character c is
         * same as the last one, increment the count. Otherwise push a pair (c, 1) into
         * the stack. I used a dummy element ('#', 0) to avoid empty stack.
         * 
         */
        public String removeDuplicates(String s, int k) {
            int[] count = new int[s.length()];
            StringBuilder sb = new StringBuilder();
            for (char c : s.toCharArray()) {
                sb.append(c);
                int last = sb.length() - 1;
                count[last] = 1 + (last > 0 && sb.charAt(last) == sb.charAt(last - 1) ? count[last - 1] : 0);
                if (count[last] >= k)
                    sb.delete(sb.length() - k, sb.length());
            }
            return sb.toString();
        }
    }

    static class RepeatedSubstringPattern_CheckIfStringContainsRepeatedSubstring {
            /**
             * Description Given a non-empty string check if it can be constructed by taking
             * a substring of it and appending multiple copies of the substring together.
             * You may assume the given string consists of lowercase English letters only
             * and its length will not exceed 10000.
             * 
             * 
             * 
             * Example 1:
             * 
             * Input: "abab" Output: True Explanation: It's the substring "ab" twice.
             * Example 2:
             * 
             * Input: "aba" Output: False Example 3:
             * 
             * Input: "abcabcabcabc" Output: True Explanation: It's the substring "abc" four
             * times. (And the substring "abcabc" twice.)
             * 
             */
            /**
             * Approach 2: Concatenation Repeated pattern string looks like PatternPattern ,
             * and the others like Pattern1Pattern2 .
             * 
             * Let's double the input string:
             * 
             * PatternPattern --> PatternPatternPatternPattern
             * 
             * Pattern1Pattern2 --> Pattern1Pattern2Pattern1Pattern2
             * 
             * Now let's cut the first and the last characters in the doubled string:
             * 
             * PatternPattern --> *atternPatternPatternPatter*
             * 
             * Pattern1Pattern2 --> *attern1Pattern2Pattern1Pattern*
             * 
             * It's quite evident that if the new string contains the input string, the
             * input string is a repeated pattern string.
             * 
             * Implementation
             */
            public boolean repeatedSubstringPattern(String s) {
                return (s + s).substring(1, 2 * s.length() - 1).contains(s);
            }

    }

    static class ValidParanthesisString {
        /**
         * Given a string s containing only three types of characters: '(', ')' and '*',
         * return true if s is valid.
         * 
         * The following rules define a valid string:
         * 
         * Any left parenthesis '(' must have a corresponding right parenthesis ')'. Any
         * right parenthesis ')' must have a corresponding left parenthesis '('. Left
         * parenthesis '(' must go before the corresponding right parenthesis ')'. '*'
         * could be treated as a single right parenthesis ')' or a single left
         * parenthesis '(' or an empty string "".
         * 
         * Example 1:
         * 
         * Input: s = "()" Output: true Example 2:
         * 
         * Input: s = "(*)" Output: true Example 3:
         * 
         * Input: s = "(*))" Output: true
         */
        public static boolean checkValidString(String s) {
            /**
             * Time Complexity: O(N^3), where NN is the length of the string. There
             * are O(N^2) states corresponding to entries of dp, and we do an average
             * of O(N) work on each state.
             * 
             * Space Complexity: O(N^2), the space used to store intermediate results
             * in dp.
             */
            int n = s.length();
            if (n == 0) return true;
            boolean[][] dp = new boolean[n][n];
    
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '*') dp[i][i] = true;
                if (i < n-1 &&
                        (s.charAt(i) == '(' || s.charAt(i) == '*') &&
                        (s.charAt(i+1) == ')' || s.charAt(i+1) == '*')) {
                    dp[i][i+1] = true;
                }
            }
            
            int j;

            for (int L = 2; L <= n; L++) {
                for (int i = 0; i < n-L+1; i++) {
                    j = i + L - 1 ;
                    if (s.charAt(i) == '*' && dp[i + 1][j] == true) {
                        dp[i][j] = true;
                    } else if (s.charAt(i) == '(' || s.charAt(i) == '*') {
                        /**
                         * Let dp[i][j] be true if and only if the interval s[i], s[i+1], ..., s[j] can
                         * be made valid. Then dp[i][j] is true only if:
                         * 
                         * s[i] is '*', and the interval s[i+1], s[i+2], ..., s[j] can be made valid;
                         * 
                         * or, s[i] can be made to be '(', and there is some k in [i+1, j] such that
                         * s[k] can be made to be ')', plus the two intervals cut by s[k] (s[i+1: k] and
                         * s[k+1: j+1]) can be made valid;
                         */
                        for (int k = i + 1; k <= j; k++) {
                            if ((s.charAt(k) == ')' || s.charAt(k) == '*') && (k == i + 1 || dp[i + 1][k - 1])
                                    && (k == j || dp[k + 1][j])) {
                                dp[i][j] = true;
                            }
                        }
                    }
                }
            }
            return dp[0][n-1];
        }

        public static boolean checkValidStringGreedy(String s) {
            /**
             * When checking whether the string is valid, we only cared about the "balance":
             * the number of extra, open left brackets as we parsed through the string. For
             * example, when checking whether '(()())' is valid, we had a balance of 1, 2,
             * 1, 2, 1, 0 as we parse through the string: '(' has 1 left bracket, '((' has
             * 2, '(()' has 1, and so on. This means that after parsing the first i symbols,
             * (which may include asterisks,) we only need to keep track of what the balance
             * could be.
             * 
             * For example, if we have string '(***)', then as we parse each symbol, the set
             * of possible values for the balance is [1] for '('; [0, 1, 2] for '(*'; [0, 1,
             * 2, 3] for '(**'; [0, 1, 2, 3, 4] for '(***', and [0, 1, 2, 3] for '(***)'.
             * 
             * Furthermore, we can prove these states always form a contiguous interval.
             * Thus, we only need to know the left and right bounds of this interval. That
             * is, we would keep those intermediate states described above as [lo, hi] = [1,
             * 1], [0, 2], [0, 3], [0, 4], [0, 3].
             * 
             * Algorithm
             * 
             * Let lo, hi respectively be the smallest and largest possible number of open
             * left brackets after processing the current character in the string.
             * 
             * If we encounter a left bracket (c == '('), then lo++, otherwise we could
             * write a right bracket, so lo--. If we encounter what can be a left bracket (c
             * != ')'), then hi++, otherwise we must write a right bracket, so hi--. If hi <
             * 0, then the current prefix can't be made valid no matter what our choices
             * are. Also, we can never have less than 0 open left brackets. At the end, we
             * should check that we can have exactly 0 open left brackets.
             * 
             */
                int lo = 0, hi = 0;
                for (char c: s.toCharArray()) {
                    lo += c == '(' ? 1 : -1;
                    hi += c != ')' ? 1 : -1;
                    if (hi < 0) break;
                    lo = Math.max(lo, 0);
                }
                return lo == 0;
        }

        public static void main(String[] args)
        {
            String s = "(*))";
            System.out.println(checkValidString(s));
        }
    }

    static class CountNumberOfPalindromicSubstrings {
        /**
         * Given a string s, return the number of palindromic substrings in it.
         * 
         * A string is a palindrome when it reads the same backward as forward.
         * 
         * A substring is a contiguous sequence of characters within the string.
         * 
         * 
         * 
         * Example 1:
         * 
         * Input: s = "abc" Output: 3 Explanation: Three palindromic strings: "a", "b",
         * "c". Example 2:
         * 
         * Input: s = "aaa" Output: 6 Explanation: Six palindromic strings: "a", "a",
         * "a", "aa", "aa", "aaa".
         */
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
    }

    static class InterleavingString {
        /**
         * Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of
         * s1 and s2.
         * 
         * An interleaving of two strings s and t is a configuration where they are
         * divided into non-empty substrings such that:
         * 
         * s = s1 + s2 + ... + sn
         * 
         * t = t1 + t2 + ... + tm
         * 
         * |n - m| <= 1 The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1
         * + t2 + s2 + t3 + s3 + ... Note: a + b is the concatenation of strings a and
         * b.
         * 
         * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac" Output: true Example 2:
         * 
         * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc" Output: false Example 3:
         * 
         * Input: s1 = "", s2 = "", s3 = "" Output: true
         */
        /**
         * For example, let the given string be A and the other strings be B and C. Let
         * A = “XXY”, string B = “XXZ” and string C = “XXZXXXY”. Create a recursive
         * function that takes parameters A, B, and C. To handle all cases, two
         * possibilities need to be considered.
         * 
         * If the first character of C matches the first character of A, we move one
         * character ahead in A and C and recursively check. If the first character of C
         * matches the first character of B, we move one character ahead in B and C and
         * recursively check. If any of the above function returns true or A, B and C
         * are empty then return true else return false.
         */
        /**
         * Approach: The above recursive solution certainly has many overlapping
         * sub-problems. For example, if we consider A = “XXX”, B = “XXX” and C =
         * “XXXXXX” and draw a recursion tree, there will be many overlapping
         * subproblems. Therefore, like any other typical Dynamic Programming problems,
         * we can solve it by creating a table and store results of sub-problems in a
         * bottom-up manner. The top-down approach of the above solution can be modified
         * by adding a Hash Map.
         * 
         * Algorithm:
         * 
         * Create a DP array (matrix) of size M*N, where m is the size of the first
         * string and n is the size of the second string. Initialize the matrix to
         * false.
         * 
         * If the sum of sizes of smaller strings is not equal to the size of the larger
         * string then return false and break the array as they cant be the interleaved
         * to form the larger string.
         * 
         * Run a nested loop the outer loop from 0 to m and the inner loop from 0 to n.
         * Loop counters are i and j.
         * 
         * If the values of i and j are both zeroes then mark dp[i][j] as true. If the
         * value of i is zero and j is non zero and the j-1 character of B is equal to
         * j-1 character of C the assign dp[i][j] as dp[i][j-1] and similarly if j is 0
         * then match i-1 th character of C and A and if it matches then assign dp[i][j]
         * as dp[i-1][j].
         * 
         * Take three characters x, y, z as (i-1)th character of A and (j-1)th character
         * of B and (i + j – 1)th character of C.
         * 
         * if x matches with z and y does not match with z then assign dp[i][j] as
         * dp[i-1][j] similarly if x is not equal to z and y is equal to z then assign
         * dp[i][j] as dp[i][j-1]
         * 
         * if x is equal to y and y is equal to z then assign dp[i][j] as bitwise OR of
         * dp[i][j-1] and dp[i-1][j]. return value of dp[m][n].
         */
        // The main function that returns
        // true if C is an interleaving of A
        // and B, otherwise false.
        static boolean isInterleaved(String A, String B, String C) {

            // Find lengths of the two strings
            int M = A.length(), N = B.length();

            // Let us create a 2D table to store
            // solutions of subproblems. C[i][j]
            // will br true if C[0..i+j-1] is an
            // interleaving of A[0..i-1] and B[0..j-1].
            boolean IL[][] = new boolean[M + 1][N + 1];

            // IL is default initialised by false

            // C can be an interleaving of A and B
            // only if the sum of lengths of A and B
            // is equal to length of C
            if ((M + N) != C.length())
                return false;

            // Process all characters of A and B

            for (int i = 0; i <= M; i++) {
                for (int j = 0; j <= N; j++) {

                    // Two empty strings have an
                    // empty strings as interleaving
                    if (i == 0 && j == 0)
                        IL[i][j] = true;

                    // A is empty
                    else if (i == 0) {
                        if (B.charAt(j - 1) == C.charAt(j - 1))
                            IL[i][j] = IL[i][j - 1];
                    }

                    // B is empty
                    else if (j == 0) {
                        if (A.charAt(i - 1) == C.charAt(i - 1))
                            IL[i][j] = IL[i - 1][j];
                    }

                    // Current character of C matches
                    // with current character of A,
                    // but doesn't match with current
                    // character if B
                    else if (A.charAt(i - 1) == C.charAt(i + j - 1) && B.charAt(j - 1) != C.charAt(i + j - 1))
                        IL[i][j] = IL[i - 1][j];

                    // Current character of C matches
                    // with current character of B, but
                    // doesn't match with current
                    // character if A
                    else if (A.charAt(i - 1) != C.charAt(i + j - 1) && B.charAt(j - 1) == C.charAt(i + j - 1))
                        IL[i][j] = IL[i][j - 1];

                    // Current character of C matches
                    // with that of both A and B
                    else if (A.charAt(i - 1) == C.charAt(i + j - 1) && B.charAt(j - 1) == C.charAt(i + j - 1))
                        IL[i][j] = (IL[i - 1][j] || IL[i][j - 1]);
                }
            }
            return IL[M][N];
        }
        
        static void test(String A, String B, String C) {
            if (isInterleaved(A, B, C))
                System.out.println(C + " is interleaved of " + A + " and " + B);
            else
                System.out.println(C + " is not interleaved of " + A + " and " + B);
        }

        // Driver code
        public static void main(String[] args) {
            // test("XXY", "XXZ", "XXZXXXY");
            // test("XY", "WZ", "WZXY");
            // test("XY", "X", "XXY");
            // test("YX", "X", "XXY");
            // test("XXY", "XXZ", "XXXXZY");
            test("aabcc", "dbbca", "aadbbcbcac");
            test("aabcc", "dbbca", "aadbbbaccc");
        }

    }

    static class MultiplyStrings {
        /**
         * Given two non-negative integers num1 and num2 represented as strings, return
         * the product of num1 and num2, also represented as a string.
         * 
         * Note: You must not use any built-in BigInteger library or convert the inputs
         * to integer directly.
         * Example 1:
         * 
         * Input: num1 = "2", num2 = "3" Output: "6" Example 2:
         * 
         * Input: num1 = "123", num2 = "456" Output: "56088"
         * 
         * Constraints:
         * 
         * 1 <= num1.length, num2.length <= 200 num1 and num2 consist of digits only.
         * Both num1 and num2 do not contain any leading zero, except the number 0
         * itself.
         */
        /**
         * Start from right to left, perform multiplication on every pair of digits, and
         * add them together. Let's draw the process! From the following draft, we can
         * immediately conclude:
         * 
         * `num1[i] * num2[j]` will be placed at indices `[i + j`, `i + j + 1]`
         *      
         *  Index 1     1  [2]   3     index i
         *  Index 0        [4]   5     index j
         *              ----------
         *                  1    5
         *              1   0
         *          0   5
         *              1   2
         *         [0   8]            indices [i+j, i+j+1]
         *       0  4
         *             
         * Index[0  1   2   3    4]
         * <------------------------                       
         *        [p1, p2]
         */    
        public String multiply(String num1, String num2) {
            int m = num1.length(), n = num2.length();
            int[] pos = new int[m + n];

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                    int p1 = i + j, p2 = i + j + 1;
                    int sum = mul + pos[p2];

                    pos[p1] += sum / 10;
                    pos[p2] = (sum) % 10;
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int p : pos)
                if (!(sb.length() == 0 && p == 0))
                    sb.append(p);
            return sb.length() == 0 ? "0" : sb.toString();
        }
        
    }

    static class StringToInteger_myAtoI {
        /**
         * Implement the myAtoi(string s) function, which converts a string to a 32-bit
         * signed integer (similar to C/C++'s atoi function).
         * 
         * The algorithm for myAtoi(string s) is as follows:
         * 
         * Read in and ignore any leading whitespace. Check if the next character (if
         * not already at the end of the string) is '-' or '+'. Read this character in
         * if it is either. This determines if the final result is negative or positive
         * respectively. Assume the result is positive if neither is present. Read in
         * next the characters until the next non-digit charcter or the end of the input
         * is reached. The rest of the string is ignored. Convert these digits into an
         * integer (i.e. "123" -> 123, "0032" -> 32). If no digits were read, then the
         * integer is 0. Change the sign as necessary (from step 2). If the integer is
         * out of the 32-bit signed integer range [-231, 231 - 1], then clamp the
         * integer so that it remains in the range. Specifically, integers less than
         * -231 should be clamped to -231, and integers greater than 231 - 1 should be
         * clamped to 231 - 1. Return the integer as the final result. Note:
         * 
         * Only the space character ' ' is considered a whitespace character. Do not
         * ignore any characters other than the leading whitespace or the rest of the
         * string after the digits.
         * 
         * 
         * Example 1:
         * 
         * Input: s = "42" Output: 42 Explanation: The underlined characters are what is
         * read in, the caret is the current reader position. Step 1: "42" (no
         * characters read because there is no leading whitespace) ^ Step 2: "42" (no
         * characters read because there is neither a '-' nor '+') ^ Step 3: "42" ("42"
         * is read in) ^ The parsed integer is 42. Since 42 is in the range [-231, 231 -
         * 1], the final result is 42. Example 2:
         * 
         * Input: s = " -42" Output: -42 Explanation: Step 1: " -42" (leading whitespace
         * is read and ignored) ^ Step 2: " -42" ('-' is read, so the result should be
         * negative) ^ Step 3: " -42" ("42" is read in) ^ The parsed integer is -42.
         * Since -42 is in the range [-231, 231 - 1], the final result is -42. Example
         * 3:
         * 
         * Input: s = "4193 with words" Output: 4193 Explanation: Step 1: "4193 with
         * words" (no characters read because there is no leading whitespace) ^ Step 2:
         * "4193 with words" (no characters read because there is neither a '-' nor '+')
         * ^ Step 3: "4193 with words" ("4193" is read in; reading stops because the
         * next character is a non-digit) ^ The parsed integer is 4193. Since 4193 is in
         * the range [-231, 231 - 1], the final result is 4193. Example 4:
         * 
         * Input: s = "words and 987" Output: 0 Explanation: Step 1: "words and 987" (no
         * characters read because there is no leading whitespace) ^ Step 2: "words and
         * 987" (no characters read because there is neither a '-' nor '+') ^ Step 3:
         * "words and 987" (reading stops immediately because there is a non-digit 'w')
         * ^ The parsed integer is 0 because no digits were read. Since 0 is in the
         * range [-231, 231 - 1], the final result is 0. Example 5:
         * 
         * Input: s = "-91283472332" Output: -2147483648 Explanation: Step 1:
         * "-91283472332" (no characters read because there is no leading whitespace) ^
         * Step 2: "-91283472332" ('-' is read, so the result should be negative) ^ Step
         * 3: "-91283472332" ("91283472332" is read in) ^ The parsed integer is
         * -91283472332. Since -91283472332 is less than the lower bound of the range
         * [-231, 231 - 1], the final result is clamped to -231 = -2147483648.
         * 
         */
        public int myAtoi(String str) {
            int index = 0;
            int total = 0;
            int sign = 1;
            
            // Check if empty string
            if(str.length() == 0)
                return 0;
            
            // remove white spaces from the string
            while(index < str.length() && str.charAt(index) == ' ')
                index++;
            
            if (index == str.length()) return 0;
            
            // get the sign
            if(str.charAt(index) == '+' || str.charAt(index) == '-') {
                sign = str.charAt(index) == '+' ? 1 : -1;
                index++;
            }
            
            // convert to the actual number and make sure it's not overflow
            while(index < str.length()) {
                int digit = str.charAt(index) - '0';
                if(digit < 0 || digit > 9) break;
                
                // check for overflow
                if(Integer.MAX_VALUE / 10 < total || Integer.MAX_VALUE / 10 == total && Integer.MAX_VALUE % 10 < digit)
                    return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                
                total = total*10 + digit;
                index++; // don't forget to increment the counter
            }
            return total*sign;
        }
    }

    static class ReverseString {
        public void reverseString(char[] s) {
            int left = 0, right = s.length - 1;
            while (left < right) {
                char tmp = s[left];
                s[left++] = s[right];
                s[right--] = tmp;
            }
        }
    }

    static class EncodeStringWithShortestLength {
        /**
         * Given a non-empty string, encode the string such that its encoded length is
         * the shortest. The encoding rule is: k[encoded_string], where the
         * encoded_string inside the square brackets is being repeated exactly k times.
         * 
         * Note:
         * 
         * k will be a positive integer and encoded string will not be empty or have
         * extra space. You may assume that the input string contains only lowercase
         * English letters. The string's length is at most 160. If an encoding process
         * does not make the string shorter, then do not encode it. If there are several
         * solutions, return any of them is fine. Example 1:
         * 
         * Input: "aaa" Output: "aaa" Explanation: There is no way to encode it such
         * that it is shorter than the input string, so we do not encode it. Example 2:
         * 
         * Input: "aaaaa" Output: "5[a]" Explanation: "5[a]" is shorter than "aaaaa" by
         * 1 character. Example 3:
         * 
         * Input: "aaaaaaaaaa" Output: "10[a]" Explanation: "a9[a]" or "9[a]a" are also
         * valid solutions, both of them have the same length = 5, which is the same as
         * "10[a]". Example 4:
         * 
         * Input: "aabcaabcd" Output: "2[aabc]d" Explanation: "aabc" occurs twice, so
         * one answer can be "2[aabc]d". Example 5:
         * 
         * Input: "abbbabbbcabbbabbbc" Output: "2[2[abbb]c]" Explanation: "abbbabbbc"
         * occurs twice, but "abbbabbbc" can also be encoded to "2[abbb]c", so one
         * answer can be "2[2[abbb]c]".
         */
        public static String encode(String s) {
            int n = s.length();

            if (s.length() <= 3) {
                return s;
            }

            String[][] dp = new String[n][n];

            for (int len = 1; len <= n; len++) {
                for (int i = 0; i < n - len + 1; i++) {
                    int j = i + len - 1;

                    dp[i][j] = s.substring(i, i + len); //Get substring of chain length.

                    // No need to encode if the current len of substring is less than 4
                    if (len <= 3) {
                        continue;
                    }
                    // System.out.println("I-> "+i+" Len -> "+len);

                    // Try all possible splits.
                    // System.out.println("I->"+ i + " J-> "+j + " str is " +dp[i][j]);
                    for (int k = i; k < j; k++) {
                        String left = dp[i][k];
                        String right = dp[k + 1][j];

                        //System.out.println("Left string is "+left + " right string is "+ right);
                        if (left.length() + right.length() < dp[i][j].length()) {
                            dp[i][j] = dp[i][k] + dp[k + 1][j]; 
                            // The shortest length would've been already found in i..k, k+1..j, 
                            // so we just have to merge the strings i...j 
                            // But, it's possible we find 5[a] in the left and 5[a] in the right, which can be further compressed.
                        }
                    //    System.out.println("output string is "+dp[i][j]);
                    }

                    // Try to compress the string and if it's less than the above set value, set it.
                    String collapsedStr = collapse(dp, s.substring(i, i + len), i);//Get string of chain length.
                    if (collapsedStr.length() < dp[i][j].length()) {
                        dp[i][j] = collapsedStr;
                        System.out.println("Return " + collapsedStr);
                    }
                }
            }
            return dp[0][n - 1];
        }

        public static String collapse(String[][] dp, String s, int start) {
            // System.out.println("collapse " + s);
            // Check if there is repeated pattern in s
            int index = (s + s).indexOf(s, 1);
            // No repeated pattern in this case
            if (index >= s.length()) {
                return s;
            } else {
                //check for repeated pattern and if found, then divide the length by the index found.
                System.out.println("Index is "+index + " length is "+s.length());
                System.out.println("collapse " + s);
                return (s.length() / index) + "[" + dp[start][start + index - 1] + "]";
                // [start][start+index-1] will contain the character 'a', or the common char found.
            }
        }

        public static void main(String[] args)
        {
            System.out.println(encode("aaaaa"));
        }

    }

    static class LongestWordInDictionaryThroughDeletion {
        /**
         * Given a string s and a string array dictionary, return the longest string in
         * the dictionary that can be formed by deleting some of the given string
         * characters. If there is more than one possible result, return the longest
         * word with the smallest lexicographical order. If there is no possible result,
         * return the empty string.
         * 
         * Example 1:
         * 
         * Input: s = "abpcplea", dictionary = ["ale","apple","monkey","plea"] Output:
         * "apple" Example 2:
         * 
         * Input: s = "abpcplea", dictionary = ["a","b","c"] Output: "a"
         */
        /**
         * The matching condition in the given problem requires that we need to consider
         * the matching string in the dictionary with the longest length and in case of
         * same length, the string which is smallest lexicographically. To ease the
         * searching process, we can sort the given dictionary's strings based on the
         * same criteria, such that the more favorable string appears earlier in the
         * sorted dictionary.
         * 
         * Now, instead of performing the deletions in ss, we can directly check if any
         * of the words given in the dictionary(say xx) is a subsequence of the given
         * string ss, starting from the beginning of the dictionary. This is because, if
         * xx is a subsequence of ss, we can obtain xx by performing delete operations
         * on ss.
         * 
         * If xx is a subsequence of ss every character of xx will be present in ss.
         */
        /**
         * Time complexity : O(n⋅xlogn+n⋅x). Here nn
         * refers to the number of strings in list dd and xx refers to average string
         * length. Sorting takes O(nlogn) and isSubsequence takes O(x) to
         * check whether a string is a subsequence of another string or not.
         * 
         * Space complexity : O(logn). Sorting takes O(logn) space in
         * average case.
         */
        public String findLongestWordUsingSort(String s, List < String > d) {
            Collections.sort(d, new Comparator < String > () {
                public int compare(String s1, String s2) {
                    return s2.length() != s1.length() ? s2.length() - s1.length() : s1.compareTo(s2);
                }
            });
            for (String str: d) {
                if (isSubsequence(str, s))
                    return str;
            }
            return "";
        }

        /**
         * Without sorting:
         * 
         * Since sorting the dictionary could lead to a huge amount of extra effort, we
         * can skip the sorting and directly look for the strings xx in the unsorted
         * dictionary dd such that xx is a subsequence in ss. If such a string xx is
         * found, we compare it with the other matching strings found till now based on
         * the required length and lexicographic criteria. Thus, after considering every
         * string in dd, we can obtain the required result.
         */

         /**
          * Time complexity : O(n⋅x). One iteration over all strings is required. 
          Here n refers to the number of strings in list d and x refers to average string length.

            Space complexity : O(x).
          */
        public boolean isSubsequence(String x, String y) {
            int j = 0;
            for (int i = 0; i < y.length() && j < x.length(); i++)
                if (x.charAt(j) == y.charAt(i))
                    j++;
            return j == x.length();
        }

        public String findLongestWord(String s, List<String> d) {
            String max_str = "";
            for (String str : d) {
                if (isSubsequence(str, s)) {
                    if (str.length() > max_str.length()
                            || (str.length() == max_str.length() && str.compareTo(max_str) < 0))
                        max_str = str;
                }
            }
            return max_str;
        }
    }
    // Top 50 questions
}

