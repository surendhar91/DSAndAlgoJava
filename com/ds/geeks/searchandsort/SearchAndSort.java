/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.searchandsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author surendhar-2481
 */
public class SearchAndSort {
    public static void searchAndSortTestData(){
        SearchAndSort sh = new SearchAndSort();
//        sh.binarySearchTestData();
//        sh.selectionSortTestData();
//        sh.bubbleSortTestData();
//        sh.insertionSortTestData();
//        sh.mergeSortTestData();
//        sh.quickSortTestData();
//        sh.countingSortTestData();
//        sh.radixSortTestData();
        sh.bucketSortTestData();
    }

    void binarySearchTestData(){
        int arr[] = {2, 3, 4, 10, 40};
        int x = 10;
        int result = binarySearch(arr, 0, arr.length-1, x);
        if(result==-1){
            System.out.println("Element is not present in array");
        }
        else{
                     System.out.println("Element is present at index "+result);
        }
    }
    
    void selectionSortTestData(){
         System.out.println("Selection sort ---->");
         int arr[] = {64, 25, 12, 22, 11};
         selectionSort(arr, arr.length);
         IntStream stream = Arrays.stream(arr);
         stream.forEach(x -> System.out.println(x));
    }
    
    int binarySearch(int arr[],int l,int r,int x){
        if(r>=l){
            
            int mid = l+(r-l)/2;
            
            if(arr[mid]==x){
                return mid;
            }else if(arr[mid]>x){//mid element is greater than x, then search for the left array
                return binarySearch(arr, l, mid-1, x);
            }else{
                return binarySearch(arr,mid+1,r,x);
            }
            
        }
        return -1;
    }
    void selectionSort(int arr[], int n){
        //keeps the sorted element at first..
        
        //The selection sort algorithm sorts an array by repeatedly finding the minimum element (considering ascending order) from unsorted part and putting it at the beginning. 
        //The algorithm maintains two subarrays in a given array.
        int min_idx;
        
        for(int i=0;i<n;i++){
            min_idx = i;
            for(int j=i+1;j<n;j++){
                if(arr[j]<arr[min_idx]){
                    min_idx = j;
                }
            }
            //swap
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
        
    }
    
    void bubbleSortTestData(){
        int arr[] = {64, 34, 25, 12, 22, 11, 90};
        int n     = arr.length;
        System.out.println("Bubble sort ---->");
        bubbleSort(arr, n);
        IntStream stream = Arrays.stream(arr);
        stream.forEach(x -> System.out.println(x));
    }
    
    void bubbleSort(int arr[],int n){
        //keeps the sorted element at last..
        
        //Bubble Sort is the simplest sorting algorithm that works by repeatedly swapping the adjacent elements if they are in wrong order.
        for(int i=0;i<n-1;i++){//last element is alloted for the first iteration
            for(int j=0;j<n-i-1;j++){
            //last i elements are already in place and sorted
                if(arr[j]>arr[j+1]){
                    //if the previous element is greater than the next element, swap them
                    int temp = arr[j];
                    arr[j]   = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
    
    void insertionSortTestData(){
        int arr[] = {12, 11, 13, 5, 6};
        System.out.println("Insertion sort--->");
        insertionSort(arr, arr.length);
        IntStream stream = Arrays.stream(arr);
        stream.forEach(x -> System.out.println(x));
        
    }
    
    void insertionSort(int arr[],int n){
        //Uses: Insertion sort is used when number of elements is small. It can also be useful when input array is almost sorted, only few elements are misplaced in complete big array.
        /*
         Algorithmic Paradigm: Incremental Approach
         Sorting In Place: Yes
         Stable: Yes
        */
        int j,key;
        for(int i=1;i<n;i++){
            key = arr[i];
            j = i-1;
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            
            //in j+1 position, the element that exists is key.
            while(j>=0 && arr[j]>key){//key is the card we hold
                arr[j+1] = arr[j];//Move the ele
                j = j-1;
            }
            arr[j+1] = key;//after all the movement, place the element in j+1th location.
        }
        
    }
    
    void mergeSortTestData(){
         int arr[] = {12, 11, 13, 5, 6, 7};
         int temp[] = new int[arr.length];
         mergeSort(arr, temp, 0, arr.length-1);
         System.out.println("Merge sort-->");
         IntStream stream = Arrays.stream(arr);
         stream.forEach(x -> System.out.println(x));
    }
    
    int mergeSort(int arr[],int temp[],int left, int right){
        /*
            Time complexity of Merge Sort is \Theta(nLogn) in all 3 cases (worst, average and best) as merge sort always divides the array in two halves and take linear time to merge two halves.
         Auxiliary Space: O(n)
         Algorithmic Paradigm: Divide and Conquer
         Sorting In Place: No in a typical implementation
         Stable: Yes
        
         */
            /*
             Inversion Count for an array indicates – how far (or close) the array is from being sorted. If array is already sorted then inversion count is 0. If array is sorted in reverse order that inversion count is the maximum. 
             Formally speaking, two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j

             Example:
             The sequence 2, 4, 1, 3, 5 has three inversions (2, 1), (4, 1), (4, 3).
            
                
             Suppose we know the number of inversions in the left half and right half of the array (let be inv1 and inv2), what kinds of inversions are not accounted for in Inv1 + Inv2? 
             The answer is – the inversions we have to count during the merge step. Therefore, to get number of inversions, we need to add number of inversions in left subarray, right subarray and merge().
            
             How to get number of inversions in merge()?
             In merge process, let i is used for indexing left sub-array and j for right sub-array. At any step in merge(), if a[i] is greater than a[j], then there are (mid – i) inversions. 
             because left and right subarrays are sorted, so all the remaining elements in left-subarray (a[i+1], a[i+2] … a[mid]) will be greater than a[j]
            
            
             */
            int inv_count = 0;
            if(left<right){
                int mid = (left+right)/2;
                inv_count = mergeSort(arr,temp,left,mid);
                inv_count += mergeSort(arr,temp,mid+1,right);
                inv_count += merge(arr,temp,left,mid+1,right);
            }
            return inv_count;
            
        }
        int merge(int arr[],int temp[],int left,int mid,int right){
            
            int i = left;
            int j = mid;
            int k = left;
            int inv_count = 0;
            while(i<=mid-1 && j<=right){
                
                if(arr[i]<=arr[j]){
                    temp[k++]=arr[i++];
                }
                else{
                    temp[k++] = arr[j++];
                    //when it comes to this else part, then mid - i elements is the number of inversions required for this subarray
                    inv_count = inv_count + (mid-i);
                }
            }
            /* Copy the remaining elements of left subarray
   (if there are any) to temp*/
            while(i<=mid-1){
                temp[k++] = arr[i++];
            }
             /* Copy the remaining elements of right subarray
   (if there are any) to temp*/
            while(j<=right){
                temp[k++] = arr[j++];
            }
            /*Copy back the merged elements to original array*/
            for(int m=left;m<=right;m++){
                arr[m] = temp[m];//copy the elements to original array.
            }
            
            return inv_count;
            
        }
        void quickSortTestData(){
            int arr[] = {10, 7, 8, 9, 1, 5};
            int n = arr.length;
            quickSort(arr,0,n-1);
            IntStream intstream = Arrays.stream(arr);
            System.out.println("Quick sort..");
            intstream.forEach(x -> System.out.println(x));
    //            IntPredicate pred = e -> e<8;
//            System.out.println("All match "+intstream.allMatch(pred));
        }
        
        
    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
        int partition(int arr[],int low,int high){
            //pick the last element as pivot
            int pivot = arr[high];
            int i =low-1;//index of first element in the array
            for(int j=i+1;j<=high-1;j++){//high is the pivot element
                if(arr[j]<=pivot){
                    i++;
                    //swap i and j
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
                //at the end of the loop, all the elements before i would be less than the pivot element
            }
            //now swap i and the pivot index
            int temp = arr[i+1];
            arr[i+1] = arr[high];//pivot element
            arr[high] = temp;
            
            return i+1;//pivot index
        
        }
        
        void quickSort(int arr[], int low, int high){
            if(low<high){
                int pivot = partition(arr,low,high);
                quickSort(arr,low,pivot-1);
                quickSort(arr,pivot+1,high);
            }
        }
        
        void countingSortTestData(){
            int arr[] = {1, 4, 1, 2, 7, 5, 2};
            int n = arr.length;
            countingSort(arr);
            Arrays.stream(arr).forEach(System.out::println);
        }
        
        void countingSort(int arr[]){
            
//            Time Complexity: O(n+k) where n is the number of elements in input array and k is the range of input.
//Auxiliary Space: O(n+k)
            /*
                Counting sort is a sorting technique based on keys between a specific range
            
             Let us understand it with the help of an example.

             For simplicity, consider the data in the range 0 to 9. 
             Input data: 1, 4, 1, 2, 7, 5, 2
             1) Take a count array to store the count of each unique object.
             Index:     0  1  2  3  4  5  6  7  8  9
             Count:     0  2  2  0   1  1  0  1  0  0

             2) Modify the count array such that each element at each index 
             stores the sum of previous counts. 
             Index:     0  1  2  3  4  5  6  7  8  9
             Count:     0  2  4  4  5  6  6  7  7  7

             The modified count array indicates the position of each object in 
             the output sequence.
 
             3) Output each object from the input sequence followed by 
             decreasing its count by 1.
             Process the input data: 1, 4, 1, 2, 7, 5, 2. Position of 1 is 2.
             Put data 1 at index 2 in output. Decrease count by 1 to place 
             next data 1 at an index 1 smaller than this index.
            */
            
            int[] output = new int[arr.length];
            
            int max      = Arrays.stream(arr).max().getAsInt();
            System.out.println("Max value obtained is "+max);
            int count[] = new int[max+1];
            for(int i=0;i<max+1;i++){
                count[i] = 0;
            }
            
            for(int i=0;i<arr.length;i++){
                count[arr[i]]++;//Store the count of object found in the given input
            }
            
            //modify the count array such that each element at each index stores the sum of previous counts
            for(int i=1;i<count.length;i++){
                count[i]+=count[i-1];
            }
            
            //output each object from the input sequence
            for(int i=0;i<arr.length;i++){
                output[count[arr[i]]-1] = arr[i];
                --count[arr[i]];
            }
            
            for(int i=0;i<arr.length;i++){
                arr[i] = output[i];
            }
            
        }
        
        void radixSortTestData(){
            int arr[] = {170, 45, 75, 90, 802, 24, 2, 66};
            int n = arr.length;
            radixSort(arr);
        }
        
        void radixSort(int arr[]){
//             time complexity is O((n+b) * logb(k)). where b is 10 (decimal system)
        /*  Counting sort is a linear time sorting algorithm that sort in O(n+k) time when elements are in range from 1 to k.

             What if the elements are in range from 1 to n2? 
             We can’t use counting sort because counting sort will take O(n2) which is worse than comparison based sorting algorithms. Can we sort such an array in linear time?
             Radix Sort is the answer. The idea of Radix Sort is to do digit by digit sort starting from least significant digit to most significant digit. Radix sort uses counting sort as a subroutine to sort.

             The Radix Sort Algorithm
             1) Do following for each digit i where i varies from least significant digit to the most significant digit.
             ………….a) Sort input array using counting sort (or any stable sort) according to the i’th digit.

             Example:
             Original, unsorted list:

             170, 45, 75, 90, 802, 24, 2, 66
             Sorting by least significant digit (1s place) gives: [*Notice that we keep 802 before 2, because 802 occurred before 2 in the original list, and similarly for pairs 170 & 90 and 45 & 75.]

             170, 90, 802, 2, 24, 45, 75, 66
             Sorting by next digit (10s place) gives: [*Notice that 802 again comes before 2 as 802 comes before 2 in the previous list.]

             802, 2, 24, 45, 66, 170, 75, 90
             Sorting by most significant digit (100s place) gives:

             2, 24, 45, 66, 75, 90, 170, 802
            
         */
            int m = Arrays.stream(arr).max().getAsInt();
            //using the max, we need to find the number of digits
            System.out.println("Max integer is "+m);
            for(int exp=1;m/exp>0;exp*=10){
                //1, 10, 100
                radixCountSort(arr,exp);
            }
            Arrays.stream(arr).forEach(System.out::println);
            
        }
         void radixCountSort(int arr[],int exp){
         
            int[] output = new int[arr.length];
            
            int count[] = new int[10];//digits has to fall under range 0-9
            Arrays.fill(count, 0);
            //Why? (arr[i]/exp)%10 -> (512 / 1) % 10 = 2 and (512 / 10)%10 = 51 %10 = 1 
            for(int i=0;i<arr.length;i++){
                count[(arr[i]/exp)%10]++;//Store the count of object found in the given input
            }
            
            //modify the count array such that each element at each index stores the sum of previous counts
            for(int i=1;i<count.length;i++){
                count[i]+=count[i-1];
            }
            
            //output each object from the input sequence
            for(int i=arr.length-1;i>=0;i--){//Traversing from the end is mandatory, for arriving at the sorted solution
                output[count[(arr[i]/exp)%10]-1] = arr[i];
                --count[(arr[i]/exp)%10];
            }
            
            for(int i=0;i<arr.length;i++){
                arr[i] = output[i];
            }
            
        }
         void bucketSortTestData(){
             double arr[] = {(0.897), (0.565), (0.656), (0.1234), (0.665), (0.3434)};
             bucketSort(arr);
         }
         
         void bucketSort(double arr[]){
             /*
             
              Bucket sort is mainly useful when input is uniformly distributed over a range. For example, consider the following problem. 
             
              Sort a large set of floating point numbers which are in range from 0.0 to 1.0 and are uniformly distributed across the range. How do we sort the numbers efficiently?

              A simple way is to apply a comparison based sorting algorithm. The lower bound for Comparison based sorting algorithm (Merge Sort, Heap Sort, Quick-Sort .. etc) is Ω(n Log n), i.e., 
              they cannot do better than nLogn.
              
             Can we sort the array in linear time? Counting sort can not be applied here as we use keys as index in counting sort. 
             
              Here keys are floating point numbers. 
              The idea is to use bucket sort. Following is bucket algorithm.

              bucketSort(arr[], n)
              1) Create n empty buckets (Or lists).
              2) Do following for every array element arr[i].
              .......a) Insert arr[i] into bucket[n*array[i]]
              3) Sort individual buckets using insertion sort.
              4) Concatenate all sorted buckets.
             
             */
             int n = arr.length;
             //Create n buckets
             ArrayList buckets[] = new ArrayList[n];
             for(int i=0;i<n;i++){
                 buckets[i] = new ArrayList();
             }
             int bucketIndex;
             for(int i=0;i<n;i++){
                 bucketIndex = (int) (arr[i] * n);
                 buckets[bucketIndex].add(arr[i]);
             }
             
             for(int i=0;i<buckets.length;i++){
                 //Sort individual buckets
                 Collections.sort(buckets[i]);
             }//sorted buckets..
//             Arrays.stream(buckets).forEach(System.out::println);
             
             int index = 0;
             for(int i=0;i<buckets.length;i++){
                 for(int j=0;j<buckets[i].size();j++){
                     arr[index++] = (double)buckets[i].get(j);
                 }
             }
             System.out.println("Sorted elements - bucket sort..");
             Arrays.stream(arr).forEach(System.out::println);
             
         }
}
