/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.greedy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author surendhar-2481
 */
public class GreedyAlgorithms {
    
    
    void printMaxActivities(int s[],int f[],int n){
        /*
        You are given n activities with their start and finish times. Select the maximum number of activities that can be performed by a single person, assuming that a person can only work on a single activity at a time.
         Example:

         Consider the following 6 activities. 
         start[]  =  {1, 3, 0, 5, 8, 5};
         finish[] =  {2, 4, 6, 7, 9, 9};
         The maximum set of activities that can be executed 
         by a single person is {0, 1, 3, 4}
        
         The greedy choice is to always pick the next activity whose finish time is least among the remaining activities and the start time is more than or equal to the finish time of previously selected activity. We can sort the activities according to their finishing time so that we always consider the next activity as minimum finishing time activity.

         1) Sort the activities according to their finishing time
         2) Select the first activity from the sorted array and print it.
         3) Do following for remaining activities in the sorted array.
         …….a) If the start time of this activity is greater than the finish time of previously selected activity then select this activity and print it.
        */
        
        //Assumes that the finish time is always in sorted order
        int i = 0;//the first activity always get selected
        System.out.print(i+"");
        for(int j=0;j<n;j++){
            if(s[j] >= f[i]){
                System.out.print(" "+j);//j activity is selected.
                i = j;
            }
        }
    }
    void printCodes(MinHeapNode root, String str){
        if(root==null){
            return;
        }
        if(root.data!='$'){//as we reach a leaf node, print the element.
            System.out.println(root.data+":"+str);
        }
        printCodes(root.left, str+'0');//traverse left, append 0
        printCodes(root.right, str+'1');//traverse right, append 1
    }
    class MinHeapNode implements Comparable {

        char data;//one of the input characters
        int freq;//frequency of the character
        MinHeapNode left, right;

        MinHeapNode(char data, int freq) {
            this.data = data;
            this.freq = freq;
            this.left = this.right = null;
        }

        @Override
        public int compareTo(Object m) {
            MinHeapNode node = (MinHeapNode) m;
            return this.freq - node.freq;//Sort by ascending order
        }

        public String toString() {
            return this.data + " " + this.freq;
        }

    }
    
    void huffManCode(char[] data,int[] freq){
        /*
        Huffman coding is a lossless data compression algorithm. 
        
         The idea is to assign variable-legth codes to input characters, lengths of the assigned codes are based on the frequencies of corresponding characters. 
         The most frequent character gets the smallest code and the least frequent character gets the largest code.
        
         The variable-length codes assigned to input characters are Prefix Codes, means the codes (bit sequences) are assigned in such a way that the code assigned to one character is not prefix of code assigned to any other character. 
        
         This is how Huffman Coding makes sure that there is no ambiguity when decoding the generated bit stream.
        
        
         Let us understand prefix codes with a counter example. Let there be four characters a, b, c and d, and their corresponding variable length codes be 00, 01, 0 and 1. 
        
         This coding leads to ambiguity because code assigned to c is prefix of codes assigned to a and b. If the compressed bit stream is 0001, the de-compressed output may be “cccd” or “ccb” or “acd” or “ab”.
        
        
         There are mainly two major parts in Huffman Coding
         1) Build a Huffman Tree from input characters.
         2) Traverse the Huffman Tree and assign codes to characters.

         Steps to build Huffman Tree
         Input is array of unique characters along with their frequency of occurrences and output is Huffman Tree.

         1. Create a leaf node for each unique character and build a min heap of all leaf nodes (Min Heap is used as a priority queue. The value of frequency field is used to compare two nodes in min heap. Initially, the least frequent character is at root)

         2. Extract two nodes with the minimum frequency from the min heap.

         3. Create a new internal node with frequency equal to the sum of the two nodes frequencies. Make the first extracted node as its left child and the other extracted node as its right child. Add this node to the min heap.

         4. Repeat steps#2 and #3 until the heap contains only one node. The remaining node is the root node and the tree is complete.
        */
        int size = data.length;
        
        //Create a queue of heap nodes where frequency is the key for having them sorted.
        PriorityQueue<MinHeapNode> queue = new PriorityQueue<MinHeapNode>();
        for(int i=0;i<size;i++){
            queue.add(new MinHeapNode(data[i],freq[i]));//adding leaf nodes at the first for the input characters
        }
        
        MinHeapNode left, right;
        while(queue.size()!=1){//Iterate the queue, while the size of priority queue doesn't become 1
            left  = queue.poll();
            right = queue.poll();
            // Create a new internal node with frequency equal to the
        // sum of the two nodes frequencies. Make the two extracted
        // node as left and right children of this new node. Add
        // this node to the min heap
        // '$' is a special value for internal nodes, not used
            MinHeapNode internalNode = new MinHeapNode('$',left.freq+right.freq);//internal node
            internalNode.left = left;
            internalNode.right = right;
            queue.add(internalNode);
        }
        printCodes(queue.poll(), "");
        // O(nlogn) where n is the number of unique characters. If there are n nodes, extractMin() is called 2*(n – 1) times. extractMin() takes O(logn) time as it calles minHeapify(). So, overall complexity is O(nlogn).
    }
    MinHeapNode findMin(Queue<MinHeapNode> firstQueue, Queue<MinHeapNode> secondQueue){
         // Step 3.a: If second queue is empty, dequeue from first queue
        if(secondQueue.isEmpty()){
            return firstQueue.poll();
        }
        
    // Step 3.b: If first queue is empty, dequeue from second queue
   
        if(firstQueue.isEmpty()){
            return secondQueue.poll();
        }
        // Step 3.c:  Else, compare the front of two queues and dequeue minimum
        if(firstQueue.peek().freq < secondQueue.peek().freq){
            return firstQueue.poll();//Take the minimum of two and pop that element
        }
        return secondQueue.poll();
        
    }
    MinHeapNode buildHuffManTreeSortedInput(char[] data,int[] freq){
        
        //If the input is not sorted, it need to be sorted first before it can be processed by the above algorithm. Sorting can be done using heap-sort or merge-sort both of which run in Theta(nlogn). So, the overall time complexity becomes O(nlogn) for unsorted input.
        //given a sorted input, we can build a huffman tree and find code with O(n) time
        //For this, we are going to use two queues firstQueue and second queue
        int size = data.length;
        Queue<MinHeapNode> firstQueue = new LinkedList<MinHeapNode>();
        Queue<MinHeapNode> secondQueue = new LinkedList<MinHeapNode>();
        
        for(int i=0;i<size;i++){
            firstQueue.add(new MinHeapNode(data[i],freq[i]));//non decreasing order add it ot the queue.
        }
        
        while(!(firstQueue.isEmpty() && secondQueue.size()==1)){
        //First queue will be empty at the end of construction of tree and second queue will have only one element, which is root.
            MinHeapNode left = findMin(firstQueue, secondQueue);
            MinHeapNode right = findMin(firstQueue, secondQueue);
            
            MinHeapNode internal = new MinHeapNode('$',left.freq+right.freq);
            internal.left = left;
            internal.right = right;
            secondQueue.add(internal);
        }
        
        return secondQueue.poll();//return the root element from the second queue.
    }
    
    class Job implements Comparable{
        char id; //Job id
        int dead;// deadline of the job
        int profit;//profit if job is over before or on deadline

        @Override
        public int compareTo(Object o) {
            Job j = (Job)o;
            return j.profit-this.profit;
        }
        
        public String toString(){
            return this.id+":"+this.profit;
        }
    }
    void printJobScheduling(Job arr[], int n){
        //Time Complexity of the solution is O(n2). It can be optimized to almost O(n) by using union-find data structure. We will son be discussing the optimized solution.
        /*
        Given an array of jobs where every job has a deadline and associated profit if the job is finished before the deadline. 
        
         It is also given that every job takes single unit of time, so the minimum possible deadline for any job is 1. How to maximize total profit if only one job can be scheduled at a time.
        
         1) Sort all jobs in decreasing order of profit.
         2) Initialize the result sequence as first job in sorted jobs.
         3) Do following for remaining n-1 jobs
         .......a) If the current job can fit in the current result sequence 
         without missing the deadline, add current job to the result.
         Else ignore the current job.
        
        */
    /*Input:  Five Jobs with following deadlines and profits
   JobID     Deadline     Profit
     a         2           100
     b         1           19
     c         2           27
     d         1           25
     e         3           15
Output: Following is maximum profit sequence of jobs
        c, a, e
        
       
        sorted array [a:100, c:27, d:25, b:19, e:15]
        
        a's deadline is 2, check if 1 slot is available, it is available, hence add the job to result and occupy the slot 1
        c's deadline is 2, check if 1 slot is available it's not, checking if slot 0 is available, it is, then add the job to result and occupy the slot 0
        d's deadline is 1, no slot free
        b's deadline is 1, no slot free
        e's deadline is 3, check if 2 slot is available, it is available henc add the job to result and occupy the slot 2
        
        
        In the end, if one takes the job in order c,a,e one can achieve the maximum profit.
        
        */
        int result[] = new int[n];//to store result ( sequence of jobs )
        boolean slot[] = new boolean[n];//to keep track of free time slots
        
        Arrays.sort(arr);
        System.out.println("sorted array "+Arrays.asList(arr));
        for(int i=0;i<n;i++){
            slot[i] = false;//initialize all slots to be free
        }
        //Iterate through all given jobs
        for(int i=0;i<n;i++){
            //find a free slot for this job
            //we are starting from the last possible slot
            System.out.println("Finding a free slot for id "+arr[i].id+" whose dead line is "+arr[i].dead);
            for(int j=Math.min(n, arr[i].dead)-1;j>=0;j--){
                if(!slot[j]){
                    System.out.println("Occupying slot "+j);
                    slot[j] = true;//Make this slot occupied
                    result[j] = i;//add this job to result
                    break;//This is really imporant, as we break the loop when the slot is occupied.
                }
            }
        }
        for(int i=0;i<n;i++){
            if(slot[i]){
                System.out.print(arr[result[i]].id+" ");
            }
        }
        
        
    }
    
    void findMinNoOfCoins(int V){
        int[] deno = new int[]{1,2,5,10,20,50,100,500,1000};
        int n = deno.length;
        Queue<Integer> result = new LinkedList<Integer>();
        //Denomination traverse from n (choose the best and settle if not found)
        for(int i=n-1;i>=0;i--){
            while(V>=deno[i]){
                V-=deno[i];//subtract the denomination from V rupees
                //for 93 rupees, 50 20 20 10 2 1
                result.add(deno[i]);
            }
        }
        System.out.println(result.size());
        while(!result.isEmpty()){
            System.out.print(result.poll()+" ");
        }
           
        
    }
    
    void jobSequencingTestData(){
        Job arr[] = new Job[5];
        for(int i=0;i<5;i++){
            arr[i]= new Job();
        }
        arr[0].id = 'a';
        arr[0].dead = 2;
        arr[0].profit = 100;
        
        arr[1].id = 'b';
        arr[1].dead = 1;
        arr[1].profit = 19;
        
        arr[2].id = 'c';
        arr[2].dead = 2;
        arr[2].profit = 27;
        
        arr[3].id = 'd';
        arr[3].dead = 1;
        arr[3].profit = 25;
        
        arr[4].id = 'e';
        arr[4].dead = 3;
        arr[4].profit = 15;
        int n = 5;
        System.out.println("Following is maximum profit sequence of jobs");
        printJobScheduling(arr, n);
    }
    
    void findMinCoinsTestData()
    {
  //  Note that above approach may not work for all denominations. For example, it doesn’t work for denominations {9, 6, 5, 1} and V = 11. The above approach would print 9, 1 and 1. But we can use 2 denominations 5 and 6.
//For general input, we use below dynamic programming approach.
        int n = 93;
        findMinNoOfCoins(n);
    }
    
    
    void huffmanCodeTestData(){
        char arr[] = { 'a', 'b', 'c', 'd', 'e', 'f' };
        int freq[] = { 5, 9, 12, 13, 16, 45 };
//        huffManCode(arr, freq);
        MinHeapNode result = buildHuffManTreeSortedInput(arr, freq);
        printCodes(result,"");
    }
    void printMaxActivitiesTestData(){
        int s[] = {1, 3, 0, 5, 8, 5};
        int f[] = {2, 4, 6, 7, 9, 9};
        printMaxActivities(s, f, s.length);
    }
    
    public void greedyAlgorithmsTestData(){
    
//        printMaxActivitiesTestData();
//        huffmanCodeTestData();
//        jobSequencingTestData();
        findMinCoinsTestData();
    }
}
