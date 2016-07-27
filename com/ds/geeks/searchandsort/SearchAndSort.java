/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.searchandsort;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author surendhar-2481
 */
public class SearchAndSort {
    public static void searchAndSortTestData(){
        SearchAndSort sh = new SearchAndSort();
        sh.binarySearchTestData();
        sh.selectionSortTestData();
        sh.bubbleSortTestData();
        sh.insertionSortTestData();
        sh.mergeSortTestData();
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
}
