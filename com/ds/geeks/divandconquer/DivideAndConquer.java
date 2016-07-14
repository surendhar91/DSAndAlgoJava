/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.divandconquer;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author surendhar-2481
 */
public class DivideAndConquer {
    /*
     A typical Divide and Conquer algorithm solves a problem using following three steps.

     1. Divide: Break the given problem into subproblems of same type.
     2. Conquer: Recursively solve these subproblems
     3. Combine: Appropriately combine the answers
         
    
    
     Divide and Conquer (D & C) vs Dynamic Programming (DP)
     Both paradigms (D & C and DP) divide the given problem into subproblems and solve subproblems. 
     
    IMPORTANT: How to choose one of them for a given problem? Divide and Conquer should be used when same subproblems are not evaluated many times. 
     
     Otherwise Dynamic Programming or Memoization should be used. 
     For example, Binary Search is a Divide and Conquer algorithm, we never evaluate the same subproblems again. 
     
     On the other hand, for calculating nth Fibonacci number, Dynamic Programming should be preferred 
     */
    
        int power(int x,int y){
            if(y==0)
                return 1;//if anything to the power of 0, it is 1
            else if(y%2==0){
                //if y is an even integer, 2^4, 4 is even, we can split divide it as 2^2 * 2^2
                return power(x,y/2)*power(x,y/2);
            }else{
                //if y is an odd integer, 2^5, then 2 * 2^2 * 2^2
                return x*power(x,y/2)*power(x,y/2);
            }
            //Time complexity: o(n)
        }
        
        int optPower(int x,int y){
            if(y==0)
                return 1;//if anything to the power of 0, it is 1
            
            int temp = power(x,y/2);//calculating the same sub problem again on multiplication has been avoided here.
            if(y%2==0){
                //if y is an even integer, 2^4, 4 is even, we can split divide it as 2^2 * 2^2
                return temp*temp;
            }else{
                //if y is an odd integer, 2^5, then 2 * 2^2 * 2^2
                return x*temp*temp;
            }
        }
        int median(int arr[],int n){
            if(n%2==0){
                //if even (say 4), arr[2]+arr[1]/2 
                return (arr[n/2]+arr[n/2-1])/2;
            }else{
                return arr[n/2];//if odd (say 5), the median is 2 (i.e.3 ) 
            }
        }
        
        int getMedian(int arr1[], int arr2[], int n){
            /*
             Question: There are 2 sorted arrays A and B of size n each. Write an algorithm to find the median of the array obtained after merging the above 2 arrays(i.e. array of length 2n). The complexity should be O(log(n))
            
             This method works by first getting medians of the two sorted arrays and then comparing them.

             Let ar1 and ar2 be the input arrays.

             Algorithm:

             1) Calculate the medians m1 and m2 of the input arrays ar1[] 
                and ar2[] respectively.
             2) If m1 and m2 both are equal then we are done.
                return m1 (or m2)
             3) If m1 is greater than m2, then median is present in one 
                of the below two subarrays.
                    a)  From first element of ar1 to m1 (ar1[0...|_n/2_|])
                    b)  From m2 to last element of ar2  (ar2[|_n/2_|...n-1])
             4) If m2 is greater than m1, then median is present in one    
                of the below two subarrays.
                    a)  From m1 to last element of ar1  (ar1[|_n/2_|...n-1])
                    b)  From first element of ar2 to m2 (ar2[0...|_n/2_|])
             5) Repeat the above process until size of both the subarrays 
                becomes 2.
             6) If size of the two arrays is 2 then use below formula to get 
                the median.
                Median = (max(ar1[0], ar2[0]) + min(ar1[1], ar2[1]))/2
            
            
             */
                if(n<=0){
                    return -1;
                }
                if(n==1){
                    return (arr1[0]+arr2[0])/2;
                }
                if(n==2){
                    return (Math.max(arr1[0], arr2[0])+Math.min(arr1[1], arr2[1]))/2;
                }
                
                int m1 = median(arr1,n);
                int m2 = median(arr2,n);
                
                if(m1==m2){//if both m1 and m2 from arr1 and arr2 are equal, then return m1
                    return m1;
                }
                
                if(m1<m2){//then median must exist in, arr1[m1...] and arr2[...m2]
                    if(n%2==0){
                        //even
                        return getMedian(Arrays.copyOfRange(arr1,n/2-1, arr1.length),arr2,n-n/2+1);
                    }
                    //odd,array is of size 5, 
                    //then arr1 - 3 arr1[2] , arr2 - 3 arr2[2]  -> 5 - 2 = (3) size is 3, we must include the median element also, see example below..
                    return getMedian(Arrays.copyOfRange(arr1, n/2, arr1.length),arr2,n-n/2);
                    /*
                    
                     Example:

                     ar1[] = {1, 12, 15, 26, 38}
                     ar2[] = {2, 13, 17, 30, 45}
                     For above two arrays m1 = 15 and m2 = 17
                     For above two arrays m1 = 15 and m2 = 17

                     For the above ar1[] and ar2[], m1 is smaller than m2. So median is present in one of the following two subarrays.

                     [15, 26, 38] and [2, 13, 17]
                     */
                }
                //if m1>m2, then median must exist in arr2[m2....] and arr1[...m1]
                
                /*
                    Let us repeat the process for above two subarrays:

                    m1 = 26 m2 = 13.
                    m1 is greater than m2. So the subarrays become

                    [15, 26] and [13, 17]
                */
                
                if(n%2==0){
                    return getMedian(Arrays.copyOfRange(arr2,n/2-1, arr2.length),arr1,n-n/2+1);
                }
                return getMedian(Arrays.copyOfRange(arr2, n/2, arr2.length),arr1,n-n/2);

        
        }
        
        int mergeSort(int arr[],int temp[],int left, int right){
        
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
        
        class Point{
            int x,y;
            Point(int x,int y){
                this.x = x;
                this.y = y;
            }
            @Override
            public String toString(){
                return "x-> "+this.x+" y-> "+this.y;
            }
        }
        
        double distPoint(Point p1,Point p2){
            double doubleValue = Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
            return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
            
        }
        
        double burteForceSoln(Point P[],int n){
            
            double min = Double.MAX_VALUE;
            
            for(int i=0;i<n;i++){
                for(int j=i+1;j<n;j++){
//                    System.out.println("Distance vector obtained is "+distPoint(P[i], P[j]));
                    if(distPoint(P[i], P[j])<min){
                        min = distPoint(P[i],P[j]);
                    }
                }
            }
//            System.out.println("Min value returning "+min);
            return min;
        }
        
        double stripClosest(Point P[],int size, double d){
        
            double min = d;
            
            P = Arrays.copyOfRange(P, 0, size);
            
            Arrays.sort(P, new Comparator<Point>(){

                @Override
                public int compare(Point o1, Point o2) {
                    return o1.y - o2.y;
                }
                
            });
            
            for(int i=0;i<size;i++){
//                System.out.println(""+P[i]);
                for(int j=i+1;j<size&&((P[i].y-P[j].y)<d);j++){//points that are at the closest distance d of y co ordinates
                    if(distPoint(P[i], P[j])<min){//if the distance is less than the already calculated value d, then update it as the minimum and return to the caller
                        min = distPoint(P[i], P[j]);
                    }
                }
            }
//            System.out.println("Min valure returning.. "+min);
            return min;//the minimum distance obtained
            
        }
        
        double closestPairOfPointsInAPlane(Point P[], int n){
            //  --> http://www.geeksforgeeks.org/closest-pair-of-points/   -> Closest pair of points in a plane
            
            //if there are 2 or three points, use brute force
            if(n<=3){
//                System.out.println("Array size is "+P.length+" n values is "+n);
                return burteForceSoln(P, n);
            }
            
            //Divide the problem into sub problems
            int mid = n/2;
            Point midPoint = P[mid];
//            System.out.println("Mid point is "+midPoint);
            // Consider the vertical line passing through the middle point
            // calculate the smallest distance dl on left of middle point and
            // dr on right side
            double dl = closestPairOfPointsInAPlane(P, mid);
            double dr = closestPairOfPointsInAPlane(Arrays.copyOfRange(P, mid, n), n-mid);//starting from mid+1 to n, the number of elements in the obtained array will be n-mid
            
            // Find the smaller of two distances
            double d = Math.min(dl, dr);
            
//            System.out.println("DL "+dl+" DR "+dr);
             // Build an array strip[] that contains points close (closer than d)
             // to the line passing through the middle point
             int j=0;
             Point[] strip = new Point[n];
             for(int i=0;i<n;i++){
                 if(Math.abs(P[i].x - midPoint.x)<d){//find the points that are closer to the middle point within the distance d
                   strip[j++] = P[i];   //Finds all the points in a plane that are at the closest distance d..
//                   System.out.println("Strip "+j+" "+P[i]);
                 }
             }
             //j is the size of strip point array
             return Math.min(d, stripClosest(strip,j,d));
            
        }
        
        double findClosestPairsInPlance(Point P[], int n){
            Arrays.sort(P, new Comparator<Point>(){

                @Override
                public int compare(Point o1, Point o2) {
                    return o1.x - o2.x;
                }
                
            });
            return closestPairOfPointsInAPlane(P, n);
        }
        
        void findClosestPairsInPlaneTestData(){
             Point P[] = new Point[6];
             P[0] = new Point(2,3);
             P[1] = new Point(12,30);
             P[2] = new Point(40,50);
             P[3] = new Point(5,1);
             P[4] = new Point(12,10);
             P[5] = new Point(3,4);
             System.out.println("Distance obtained between the closest pair is "+findClosestPairsInPlance(P, 6));
        }
        
        void inversionsTestData(){
            //O(nlogn) approach
            int arr[] = {1, 20, 6, 4, 5};
            int temp[] = new int[arr.length];
            System.out.println("Number of inversions are "+mergeSort(arr, temp, 0, arr.length-1));
        }
        
        void getMedianTestData(){
            
            //Time complexity: o(logn) time for divide and conquer approach.
            int ar1[] = {1, 12, 15, 26, 38};
            int ar2[] = {2, 13, 17, 30, 45};
            if(ar1.length==ar2.length){
                System.out.println("Median is "+getMedian(ar1, ar2, ar1.length));
            }else{
                System.out.println("Doesn't work for lengths of unequal size..");
            }
        }
        
        void powerTestData(){
            int x = 2;
            int y = 3;
            System.out.println("Power of 2^3 is "+power(x, y));
            System.out.println("Optimized power of 2^3 is "+optPower(x,y));
        }
    
            public void divideAndConquerTestData(){
//                powerTestData();
//                getMedianTestData();
//                inversionsTestData();
                findClosestPairsInPlaneTestData();
            
            }
}
