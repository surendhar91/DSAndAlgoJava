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
import sun.security.x509.X500Name;

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
//        sh.bucketSortTestData();
//        sh.printUnsortedSubArrayTestData();
//        sh.printKClosestElementTestData();
//        sh.sort1ToN2TestData();
//        sh.searchAlmostSortedArrayTestData();
        sh.sortInWaveFormTestData();
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
            
            int mid = l+(r-l)/2;//Best way to find the mid value for larger values of integer.
            
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
        
        //  Selection Sort makes least number of writes (it makes O(n) swaps). Minimum number of memory writes
        
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
    /*
     A sorting algorithm is said to be stable if two objects with equal keys appear in the same order in sorted output as they appear in the input unsorted array. 
     Some sorting algorithms are stable by nature like Insertion sort, Merge Sort, Bubble Sort, etc. And some sorting algorithms are not, like Heap Sort, Quick Sort, etc.
     */
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
//            quickSort(arr,0,n-1);
            iterativeQuickSort(arr, 0, n-1);
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
            /*
            In early versions of Quick Sort where leftmost (or rightmost) element is chosen as pivot, the worst occurs in following cases.

             1) Array is already sorted in same order.
             2) Array is already sorted in reverse order.
             3) All elements are same (special case of case 1 and 2)

             Since these cases are very common use cases, the problem was easily solved by choosing either a random index for the pivot, choosing the middle index of the partition or (especially for longer partitions) choosing the median of the first, middle and last element of the partition for the pivot. 
             With these modifications, the worst case of Quick sort has less chances to occur, but worst case can still occur if the input array is such that the maximum (or minimum) element is always chosen as pivot.
             */
            if(low<high){
                int pivot = partition(arr,low,high);
                quickSort(arr,low,pivot-1);
                quickSort(arr,pivot+1,high);
            }
        }
        
        void iterativeQuickSort(int arr[], int low, int high){
            //As you can see from the above, we are using recursive call for low - pivot and pivot - high sorting.
            
            //In this approach, we will be using auxillary stack
            int[] stack = new int[high-low+1];//(h-l+1) size of the stack
            
            int top = -1;
            stack[++top] = low;
            stack[++top] = high;
            
            //In this approach, right subarray to pivot is sorted first, then comes to the left subarray.
            while(top>=0){
                //while there are elements left in the stack..
                high = stack[top--];
                low  = stack[top--];
                
                int pivot = partition(arr,low,high);
                  // If there are elements on left side of pivot,
            // then push left side to stack
                if(pivot-1>low){
                    //add the low - pivot -1 to the stack
                    stack[++top] = low;
                    stack[++top] = pivot - 1;
                }
                  // If there are elements on right side of pivot,
            // then push right side to stack
                if(pivot+1<high){
                    stack[++top] = pivot+1;
                    stack[++top] = high;
                }
            }
        }
        
        /*
        
            Comparison based sorting algorithm: Lower bound
        
            A sorting algorithm is comparison based if it uses comparison operators to find the order between two numbers.  
            Comparison sorts can be viewed abstractly in terms of decision trees. 
        
            A decision tree is a full binary tree that represents the comparisons between elements that are performed by a particular sorting algorithm operating on an input of a given size. 
            The execution of the sorting algorithm corresponds to tracing a path from the root of the decision tree to a leaf. At each internal node, a comparison ai <= aj is made. 
            The left subtree then dictates subsequent comparisons for ai <= aj, and the right subtree dictates subsequent comparisons for ai > aj. When we come to a leaf, the sorting algorithm has established the ordering. 
            So we can say following about the decison tree.

            1) Each of the n! permutations on n elements must appear as one of the leaves of the decision tree for the sorting algorithm to sort properly.

            2) Let x be the maximum number of comparisons in a sorting algorithm. The maximum height of the decison tree would be x. A tree with maximum height x has at most 2^x leaves.

            After combining the above two facts, we get following relation.

  
                n!  <= 2^x

                Taking Log on both sides.
                log2(n!)  <= x

                Since log2(n!)  = Θ(nLogn),  we can say
                x = Ω(nLog2n)
            Therefore, any comparison based sorting algorithm must make at least nLog2n comparisons to sort the input array,
        
        */
        
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
         
         void printUnsortedSubArrayTestData(){
             int arr[] = {10, 12, 20, 30, 25, 40, 32, 31, 35, 50, 60};
             printUnsortedSubArray(arr);
         }
         
         
         //find unsorted subarray that when sorted, makes the whole array sorted
         void printUnsortedSubArray(int arr[]){
             int n = arr.length;
             int s = 0, e = n-1;
             
             for(s=0;s<n;s++){
                 if(arr[s]>arr[s+1]){
                     break;
                 }
             }
             if(s==n){
                 System.out.println("All the elements are sorted ");
                 return ;
             }
             
             for(e=n-1;e>0;e--){
                 if(arr[e-1]>arr[e]){
                     break;
                 }
             }
             /*
                1) Find the candidate unsorted subarray 
              a) Scan from left to right and find the first element which is greater than the next element. Let s be the index of such an element. In the above example 1, s is 3 (index of 30).
              b) Scan from right to left and find the first element (first in right to left order) which is smaller than the next element (next in right to left order). Let e be the index of such an element. In the above example 1, e is 7 (index of 31).
             */
             int max, min;
             max = arr[s];
             min = arr[e];
             
             for(int i=s+1;i<=e;i++){
                 //find the min and max element in the unsorted subarray
                 if(arr[i]<min){
                     min = arr[i];
                 }
                 if(arr[i]>max){
                     max = arr[i];
                 }
             }
//             2) Check whether sorting the candidate unsorted subarray makes the complete array sorted or not. If not, then include more elements in the subarray.
//a) Find the minimum and maximum values in arr[s..e]. Let minimum and maximum values be min and max. min and max for [30, 25, 40, 32, 31] are 25 and 40 respectively.
//b) Find the first element (if there is any) in arr[0..s-1] which is greater than min, change s to index of this element. There is no such element in above example 1.
//c) Find the last element (if there is any) in arr[e+1..n-1] which is smaller than max, change e to index of this element. In the above example 1, e is changed to 8 (index of 35)
             
            //find the firt element which is greater than min, if found change s index
             for(int i=0;i<=s-1;i++){
                 if(arr[i]>min){
                     s = i;
                 }
             }
             
             for(int i=e+1;i<=n-1;i++){
                 if(arr[i]<max){
                     e = i;
                 }
             }
             
             System.out.println("The unsorted subarray start index "+s+" End index "+e);
             System.out.println("Elements that are unsorted in subarray is.. ");
             Arrays.stream(Arrays.copyOfRange(arr, s, e+1)).forEach(System.out::println);
         }
         
         int findCrossOver(int arr[], int low, int high, int x){
             //Using binary search to find the cross over point,  .
             /* Function to find the cross over point (the point before
              which elements are smaller than or equal to x and after
              which greater than x)*/
             if(arr[high]<=x){
                 return high;//If x is greater than high, then return high
             }
             if(arr[low]>x){
                 return low; //if x is smaller, then return low index
             }
             int mid = (high+low)/2;
             
             //if x is same as middle element, then return x
             if(arr[mid]<=x && arr[mid]+1>x){
                 return mid;
             }
             
              /* If x is greater than arr[mid], then either arr[mid + 1]
          is ceiling of x or ceiling lies in arr[mid+1...high] */
             if(arr[mid]<x){
                 return findCrossOver(arr,mid+1,high,x);
             }
             return findCrossOver(arr,low,mid-1,x);
         }
         
         void printKClosestElement(int arr[],int x, int k){
             
             System.out.println("Closest element to X "+x+" were ");
             int count = 0;
             //find the cross over for element x
             int l = findCrossOver(arr,0,arr.length-1,x);
             int r = l+1;
             int n = arr.length;
             
             if(arr[l]==x)l--;//don't include x element in the result
             
             while(l>=0&&r<n&&count<k){
                 if(x-arr[l]<arr[r]-x){
                     System.out.println(arr[l--]);
                 }else{
                     System.out.println(arr[r++]);
                 }
                 count++;
             }
             
             while(l>=0&&count<k){
                 //if there are more elements on left side, print them
                 System.out.println(arr[l--]);
                 count++;
             }
             
             while(r<n&&count<k){
                 System.out.println(arr[r++]);
                 count++;
             }
             
         }
         
         void printKClosestElementTestData(){
             int arr[] = {12, 16, 22, 30, 35, 39, 42,
                 45, 48, 50, 53, 55, 56
             };
             int x = 35, k = 4;
             printKClosestElement(arr, x, k);
         }
         
         void sort1ToN2(int arr[]){
             /*
              Given an array of numbers of size n. It is also given that the array elements are in range from 0 to n2 – 1. Sort the given array in linear time.

              Examples:
              Since there are 5 elements, the elements can be from 0 to 24.
              Input: arr[] = {0, 23, 14, 12, 9}
              Output: arr[] = {0, 9, 12, 14, 23}

              Since there are 3 elements, the elements can be from 0 to 8.
              Input: arr[] = {7, 0, 2}
              Output: arr[] = {0, 2, 7}
             
              Solution: If we use Counting Sort, it would take O(n^2) time as the given range is of size n^2. Using any comparison based sorting like Merge Sort, Heap Sort, .. etc would take O(nLogn) time.
              Now question arises how to do this in 0(n)? Firstly, is it possible? Can we use data given in question? n numbers in range from 0 to n2 – 1?
              The idea is to use Radix Sort. Following is standard Radix Sort algorithm.

              1) Do following for each digit i where i varies from least 
              significant digit to the most significant digit.
              …………..a) Sort input array using counting sort (or any stable 
              sort) according to the i’th digit
             
             arr[] = {0, 10, 13, 12, 7}

              Let us consider the elements in base 5. For example 13 in
              base 5 is 23, and 7 in base 5 is 12.
              arr[] = {00(0), 20(10), 23(13), 22(12), 12(7)}

              After first iteration (Sorting according to the last digit in 
              base 5),  we get.
              arr[] = {00(0), 20(10), 12(7), 22(12), 23(13)}

              After second iteration, we get
              arr[] = {00(0), 12(7), 20(10), 22(12), 23(13)}
             */
             
             //sort the first digit 
             radixCountSort(arr, 1);
             
             int base = arr.length;
             
             radixCountSort(arr, arr.length);
             
             //O(n) time sorting..
         }
         
         void sort1ToN2TestData(){
              // Since array size is 7, elements should be from 0 to 48
             int arr[] = {40, 12, 45, 32, 33, 1, 22};
             int n = arr.length;
             System.out.println("Given array");
             Arrays.stream(arr).forEach(System.out::println);
             sort1ToN2(arr);

             System.out.println("Sorted array");
             Arrays.stream(arr).forEach(System.out::println);

         }
         
         int binaryAlmostSortSearch(int arr[],int low,int high, int x){
             
             if(low<=high){
                 
                 int mid = (low + (high-low))/2;
                 
                 if(arr[mid]==x)return mid;
                 
                 if(mid<high && arr[mid+1]==x)return mid+1;
                 
                 if(mid>low && arr[mid-1]==x) return mid-1;
                 
                 if(arr[mid]<x){
                     return binaryAlmostSortSearch(arr, mid+2, high, x);//skip by 2.
                 }
                 
                 return binaryAlmostSortSearch(arr, low, mid-2, x);
                 
             }
             //when we were not able to find the element, we will reach here..
             return -1;
         
         }
         
         void searchAlmostSortedArrayTestData(){
         
             /* 
              
              Given an array which is sorted, but after sorting some elements are moved to either of the adjacent positions, i.e., arr[i] may be present at arr[i+1] or arr[i-1]. Write an efficient function to search an element in this array. Basically the element arr[i] can only be swapped with either arr[i+1] or arr[i-1].

              For example consider the array {2, 3, 10, 4, 40}, 4 is moved to next position and 10 is moved to previous position.

              Example:

              Input: arr[] =  {10, 3, 40, 20, 50, 80, 70}, key = 40
              Output: 2 
              Output is index of 40 in given array

              Input: arr[] =  {10, 3, 40, 20, 50, 80, 70}, key = 90
              Output: -1
              -1 is returned to indicate element is not present 
             
             */
             int arr[] = {3, 2, 10, 4, 40};
             int n = arr.length;
             int x = 4;
             int result = binaryAlmostSortSearch(arr, 0, n - 1, x);
             if (result == -1) {
                 System.out.println("Element is not present in array");
             } else {
                 System.out.println("Element is present at index "
                         + result);
             }
             
         }
         
         void swap(int arr[],int a, int b){
             int temp = arr[a];
             arr[a] =arr[b];
             arr[b] = temp;
         }
         
         void sortInWave(int arr[]){
             //approach 1: sort the array and swap adjacenet elements.
             
//             System.out.println("Sorted in wave form..using sort approach");
//             Arrays.sort(arr);
//             
//             for(int i=0;i<arr.length-1;i+=2){
//                 swap(arr,i,i+1);
//             }
//             Arrays.stream(arr).forEach(System.out::println);
             
             //approach 2: O(n) time
             
             /*
              This can be done in O(n) time by doing a single traversal of given array. The idea is based on the fact that if we make sure that all even positioned (at index 0, 2, 4, ..) elements are greater than their adjacent odd elements, we don’t need to worry about odd positioned element. Following are simple steps.
              1) Traverse all even positioned elements of input array, and do following.
              ….a) If current element is smaller than previous odd element, swap previous and current.
              ….b) If current element is smaller than next odd element, swap next and current.
             */
             System.out.println("Sorted in wave form, using even traversal approach..");
             int n = arr.length;
             for(int i=0;i<arr.length;i+=2){
                 if(i>0 && arr[i]<arr[i-1]){
                     swap(arr,i,i-1);
                 }
                 if(i<n-1 && arr[i]<arr[i+1]){
                     swap(arr,i,i+1);
                 }
             
             }
             Arrays.stream(arr).forEach(System.out::println);

             
         }
         void sortInWaveFormTestData(){
             int arr[] = {10, 90, 49, 2, 1, 5, 23};
             sortInWave(arr);
         }
         
}
