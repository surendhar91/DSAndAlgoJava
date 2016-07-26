/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.heap;

/**
 *
 * @author surendhar-2481
 */
public class Heap {
    
    public static void heapTestData(){
//        minHeapTestData();
//        heapSortTestData();
        sortKSortedArrayTestData();
    }
    
    static void minHeapTestData(){
        MinHeap h = new MinHeap(11);
        h.insertKey(3);
        h.insertKey(2);
        h.deleteKey(1);
        h.insertKey(15);
        h.insertKey(5);
        h.insertKey(4);
        h.insertKey(45);
        System.out.println("Minimum value extracted is "+h.extractMin());
        System.out.println("Min value obtained after extraction is "+h.getMin());
        h.decreaseKey(2, 1);
        System.out.println("Min value obtained after decreasing key is "+h.getMin());
    }

    private static void heapSortTestData() {
        int arr[] = {12, 11,5, 6, 7,13};
        int n = arr.length;
        new MaxHeap().heapSort(arr);
    }
    
    static void sortKSortedArrayTestData(){
        int k = 3;
        int arr[] = {2, 6, 3, 12, 56, 8};
        sortK(arr, arr.length, k);
        System.out.println("Following is the sorted array");
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"\t");
        }
    }
       // Given an array of size n, where every element is k away from its target
    // position, sorts the array in O(nLogk) time.
    static void sortK(int arr[], int n, int k) {//The Min Heap based method takes O(nLogk) time and uses O(k) auxiliary space.
        
        //Take k+1 elements and construct a heap array
        MinHeap h = new MinHeap(n);
        for(int i=0;i<=k&&i<n;i++){
            h.insertKey(arr[i]);
        }
        // i is index for remaining elements in arr[] and ti
        // is target index of for cuurent minimum element in
        // Min Heapm 'hp'.
        for(int i=k+1,ti=0;ti<n;i++,ti++){
            //Starting from k+1 elements
            // If there are remaining elements, then place
            // root of heap at target index and add arr[i]
            // to Min Heap
            if(i<n){//if the remaining elements exist, then call replaceMin() -> it will return the old minimum data (adjusts the k sorted elements in result arr) and adds arr[i] data to min heap..
                arr[ti] = h.replaceMin(arr[i]);
            }else{//if no remaining elements exist, then extract all the elements from heap.
                // Otherwise place root at its target index and
                // reduce heap size
                arr[ti] = h.extractMin();
            }
        }
    }
    
}
class MaxHeap{
    
    void maxHeapify(int[] heap_arr, int heap_size, int i){
        int largest = i;
        int l = 2*i+1;
        int r = 2*i+2;
        if(l<heap_size&&heap_arr[l]>heap_arr[i]){
            largest = l;
        }
        if(r<heap_size && heap_arr[r]>heap_arr[largest]){
            largest = r;
        }
        if(largest!=i){
            //swap
            int temp = heap_arr[largest];
            heap_arr[largest] = heap_arr[i];
            heap_arr[i] = temp;
            maxHeapify(heap_arr, heap_size, largest);//do the recursive call for max heapify
        }
    }
    void heapSort(int arr[]){
        /*
        Heap Sort Algorithm for sorting in increasing order:
            1. Build a max heap from the input data.
            2. At this point, the largest item is stored at the root of the heap. Replace it with the last item of the heap followed by reducing the size of heap by 1. Finally, heapify the root of tree.
            3. Repeat above steps until size of heap is greater than 1.
        */
        int n = arr.length;
        
        //Build max heap (by rearranging half of the array)
        for(int i=n/2-1;i>=0;i--){
            //The upper half will always be the elements having atleast one child. (leaves will not be there.)
            //Hence, travesing only the upper half is enough, for getting the first largest element. (largest element stored at the root of the heap)
            maxHeapify(arr, n, i);
        }
        System.out.println("Befor sorting..");
        for(int i=0;i<n;i++){
            System.out.println(arr[i]+"\t");
        }
        for(int i=n-1;i>=0;i--){
            //arr[0] will have the maximum element at the start of iteration.
            //Move the current root to end of the array, moving max element to the end array (sorting)
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            
            maxHeapify(arr, i, 0);
        }
        System.out.println("after sorting..");
        for(int i=0;i<n;i++){
            System.out.println(arr[i]+"\t");
        }
    }
}
class MinHeap{
    /*
         A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, and all nodes are as far left as possibl
    */
    int heap_arr[];
    int capacity;
    int heap_size;
    
    MinHeap(int capacity){
        this.heap_arr = new int[capacity];
        this.capacity = capacity;
        heap_size = 0;
    }
    
    int parent(int i){
        return (i-1)/2;
    }
    
    int left(int i){
        return (2*i+1);
    }
    int right(int i){
        return (2*i+2);
    }
    
    int getMin(){
        return heap_arr[0];
    }
    
    /*
        1) insert(): Inserting a new key takes O(Logn) time. We add a new key at the end of the tree. 
        IF new key is greater than its parent, then we don’t need to do anything. Otherwise, we need to traverse up to fix the violated heap property.
    */
    void insertKey(int data_value){
       
       if(heap_size==capacity){
           System.out.println("Insertion of key failed in Min heap, capacity reached..");
           return ;
       }
        
       heap_arr[heap_size] = data_value;
       heap_size++;
       int i = heap_size - 1;
       while(i!=0 && heap_arr[parent(i)]>heap_arr[i]){
           int temp = heap_arr[parent(i)];
           heap_arr[parent(i)] = heap_arr[i];
           heap_arr[i] = temp;
           //swapping the elements
           i = parent(i);
       }
    }
    
    /*
        decreaseKey(): Decreases value of key. 
        Time complexity of this operation is O(Logn). If the decreases key value of a node is greater than parent of the node, then we don’t need to do anything. Otherwise, we need to traverse up to fix the violated heap property.
    */
    
    void decreaseKey(int i, int data_value){
        heap_arr[i] = data_value;
        //traverse up to fix the violated heap property.
        while(i!=0&&heap_arr[parent(i)]>heap_arr[i]){
           int temp = heap_arr[parent(i)];
           heap_arr[parent(i)] = heap_arr[i];
           heap_arr[i] = temp;
           //swapping the elements
           i = parent(i);
        }
    }
    
    void deleteKey(int i){
        decreaseKey(i, Integer.MIN_VALUE);//set the key to minus infinite, so that this will reach the root.
        //then call extract Min method
        extractMin();
    }
    
    int extractMin(){
        //Method to remove the root element from the tree
        if(heap_size<=0){
            return Integer.MAX_VALUE;
        }
        
        //Base case
        if(heap_size==1){
            heap_size--;
            return heap_arr[0];
        }
        
        int root = heap_arr[0];
        
        heap_arr[0] = heap_arr[heap_size-1];//get the last element from the array and set it as root
        heap_size--;//decrease the size
        
        minHeapify(0);//call min heapify property
        return root;
    }
    
    void minHeapify(int i){
        
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if(l<heap_size && heap_arr[l]< heap_arr[i]){
            smallest = l;
        }
        if(r<heap_size && heap_arr[r]<heap_arr[smallest]){
            smallest = r;
        }
        if(smallest!=i){
            int temp = heap_arr[smallest];
            heap_arr[smallest] = heap_arr[i];
            heap_arr[i] = temp;
            minHeapify(smallest);//Traverse down the tree..
        }
    }
    
    int replaceMin(int x){
        //returns the old minimum element of the heap, and replaces the root with the given value x
        int root = heap_arr[0];
        heap_arr[0] = x;
        if(root<x){//if x value is greater than x, then we need to call minheapify function
            minHeapify(0);
        }
        return root;
    }
    
    //Tournament Tree
    /*
        For more details, Refer http://www.geeksforgeeks.org/tournament-tree-and-binary-heap/
    
        Applications / Usages:-
    
        1. Second Best Player 
        2. Median of Sorted Arrays
        3. Select smallest one million elements from one billion unsorted elements
    
    */
    
    
    /*
        Binary heap vs Binary search tree
    
    A typical Priority Queue requires following operations to be efficient.

     Get Top Priority Element (Get minimum or maximum)
     Insert an element
     Remove top priority element
     Decrease Key
     
    A Binary Heap supports above operations with following time complexities:

     O(1)
     O(Logn)
     O(Logn)
     O(Logn)
    
    A Self Balancing Binary Search Tree like AVL Tree, Red-Black Tree, etc can also support above operations with same time complexities.

     1) Finding minimum and maximum are not naturally O(1), but can be easily implemented in O(1) by keeping an extra pointer to minimum or maximum and updating the pointer with insertion and deletion if required. With deletion we can update by finding inorder predecessor or successor.
     2) Inserting an element is naturally O(Logn)
     3) Removing maximum or minimum are also O(Logn)
     4)Decrease key can be done in O(Logn) by doing a deletion followed by insertion. See this for details.
     
    So why is Binary Heap Preferred for Priority Queue?

     1) Since Binary Heap is implemented using arrays, there is always better locality of reference and operations are more cache friendly.
     2) Although operations are of same time complexity, constants in Binary Search Tree are higher.
     3) We can build a Binary Heap in O(n) time. Self Balancing BSTs require O(nLogn) time to construct.
     4) Binary Heap doesn’t require extra space for pointers.
     5) Binary Heap is easier to implement.
     6) There are variations of Binary Heap like Fibonacci Heap that can support insert and decrease-key in Θ(1) time
     
    Is Binary Heap always better?
     Although Binary Heap is for Priority Queue, BSTs have their own advantages and the list of advantages is in-fact bigger compared to binary heap.

     1) Searching an element in self-balancing BST is O(Logn) which is O(n) in Binary Heap.
     2) We can print all elements of BST in sorted order in O(n) time, but Binary Heap requires O(nLogn) time.
     3) Floor and ceil can be found in O(Logn) time.
     4) K’th largest/smallest element be found in O(Logn) time by augmenting tree with an additional field.
    
    */
    
    
    /*
    FIBONACCI HEAP:-
    
    Below are amortized time complexities of Fibonacci Heap.

     1) Find Min:      Θ(1)     [Same as both Binary and Binomial]
     2) Delete Min:    O(Log n) [Θ(Log n) in both Binary and Binomial]
     3) Insert:        Θ(1)     [Θ(Log n) in Binary and Θ(1) in Binomial]
     4) Decrease-Key:  Θ(1)     [Θ(Log n) in both Binary and Binomial]
     5) Merge:         Θ(1)     [Θ(m Log n) or Θ(m+n) in Binary and Θ(Log n) in Binomial]
    
     In Fibonacci Heap, trees can can have any shape even all trees can be single nodes 
    
     Fibonacci Heap maintains a pointer to minimum value (which is root of a tree). 
     All tree roots are connected using circular doubly linked list, so all of them can be accessed using single ‘min’ pointer.

     The main idea is to execute operations in “lazy” way. For example merge operation simply links two heaps, insert operation simply adds a new tree with single node. 
     The operation extract minimum is the most complicated operation. It does delayed work of consolidating trees. 
     This makes delete also complicated as delete first decreases key to minus infinite, then calls extract minimum.
    
    */
    
    /*
        Kth largest element in an array
    
    Method 4 (Use Max Heap)
    1) Build a Max Heap tree in O(n)
    2) Use Extract Max k times to get k maximum elements from the Max Heap O(klogn)
    
    Method 6 (Use Min Heap)
    This method is mainly an optimization of method 1. Instead of using temp[] array, use Min Heap.

     1) Build a Min Heap MH of the first k elements (arr[0] to arr[k-1]) of the given array. O(k)

     2) For each element, after the kth element (arr[k] to arr[n-1]), compare it with root of MH.
     ……a) If the element is greater than the root then make it root and call heapify for MH
     ……b) Else ignore it.
     // The step 2 is O((n-k)*logk)

     3) Finally, MH has k largest elements and root of the MH is the kth largest element.

     Time Complexity: O(k + (n-k)Logk) without sorted output. If sorted output is needed then O(k + (n-k)Logk + kLogk)
    
    */

}