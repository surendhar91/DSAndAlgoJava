/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author surendhar-2481
 */
public class Hash {
    
    public static void hashTestData(){
        Hash h = new Hash();
//        h.printPairsOfSumTestData();
//        h.checkDuplicationsWithinKDistanceArrTestData();
//         h.findItenariesGivenAListOfTicketsTestData();
        h.findNumberOfEmployessUnderManagerTestData();
    }
    
    void printPairsOfSumTestData(){
         int A[] = {1, 4, 45, 6, 10, 8};
         int n = 16;
         printPairsOfSum(A, n);
    }
    
    void printPairsOfSum(int arr[], int sum){
        
        HashMap<Integer,Boolean> binMap = new HashMap<Integer,Boolean>();
        
        for(int i=0;i<arr.length;i++){
            
            int temp  = sum - arr[i];
            Boolean isExists = binMap.get(temp);
            
            if(isExists!=null && isExists.booleanValue()){
                System.out.println("Pair of given sum "+sum+" already exists "+temp+","+arr[i]);
            }
            
            binMap.put(arr[i], Boolean.TRUE);
            
        }
        
        Arrays.sort(arr);//sort the array
        int l=0,r=arr.length-1;
        while(l<r){
            
            if(arr[l]+arr[r]==sum){
                System.out.println("Pair exists of sum "+sum+" -> "+arr[l]+","+arr[r]);
                l++;r--;
//                break;
            }else if(arr[l]+arr[r]>sum){
                r--;
            }else{
                l++;
            }
            
        }
        
        
    }
    
    void checkDuplicationsWithinKDistanceArrTestData(){
        int arr[] = {10, 5, 3, 4, 3, 5, 6};
        if (checkDuplicationsWithinKDistanceArr(arr, 3))
           System.out.println("Duplicates found");
        else
           System.out.println("Duplicates Not found");
    }
    
    boolean checkDuplicationsWithinKDistanceArr(int arr[], int k){
        HashSet<Integer> set = new HashSet<Integer>();
        for(int i=0;i<arr.length;i++){
            
            if(set.contains(arr[i])){
                return true;//if the element already exists, return duplicate found
            }
            
            set.add(arr[i]);
            
            if(i>=k){//if the current index is greater than or equal to k, then remove i-k elements from the set
                set.remove(arr[i-k]);
            }
        }
        return false;
    }
    
    void findItenariesGivenAListOfTicketsTestData(){
        Map<String, String> dataSet = new HashMap<String, String>();
        dataSet.put("Chennai", "Banglore");
        dataSet.put("Bombay", "Delhi");
        dataSet.put("Goa", "Chennai");
        dataSet.put("Delhi", "Goa");
        
        findItenariesGivenAListOfTickets(dataSet);
    }
    
    void findItenariesGivenAListOfTickets(Map<String, String> dataSet){
        //One Solution is to build a graph and do Topological Sorting of the graph. Time complexity of this solution is O(n).
        
        //Other soution
        /*
            1) Create a HashMap of given pair of tickets.  Let the created 
         HashMap be 'dataset'. Every entry of 'dataset' is of the form 
         "from->to" like "Chennai" -> "Banglore"

         2) Find the starting point of itinerary.
         a) Create a reverse HashMap.  Let the reverse be 'reverseMap'
         Entries of 'reverseMap' are of the form "to->form". 
         Following is 'reverseMap' for above example.
         "Banglore"-> "Chennai" 
         "Delhi"   -> "Bombay" 
         "Chennai" -> "Goa"
         "Goa"     ->  "Delhi"
 
         b) Traverse 'dataset'.  For every key of dataset, check if it
         is there in 'reverseMap'.  If a key is not present, then we 
         found the starting point. In the above example, "Bombay" is
         starting point.

         3) Start from above found starting point and traverse the 'dataset' 
         to print itinerary.
        */
        HashMap<String,String> reverseMap = new HashMap<String,String>();
        //Construct a reverese map ( to -> from )
        for(Map.Entry<String,String> entry:dataSet.entrySet()){
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        
        String startPoint = null;
        for(Map.Entry<String, String> entry:dataSet.entrySet()){
            if(!reverseMap.containsKey(entry.getKey())){//If the element is not found in reverse map, set the starting point and break out of the loop
                startPoint = entry.getKey();
                break;
            }
        }
        if(startPoint==null){
            System.out.println("Invalid input");
            return ;
        }
        
        String to = dataSet.get(startPoint);
        while(to!=null){
            System.out.println(startPoint+" -> "+to);
            startPoint = to;
            to = dataSet.get(startPoint);
        }
    }
    
    void findNumberOfEmployessUnderManagerTestData(){
        Map<String, String> dataSet = new HashMap<String, String>();
        dataSet.put("A", "C");
        dataSet.put("B", "C");
        dataSet.put("C", "F");
        dataSet.put("D", "E");
        dataSet.put("E", "F");
        dataSet.put("F", "F");
        findNumberOfEmployeesUnderManager(dataSet);
        System.out.println("result = "+mgrEmpCountMap);
            
    }
    
    static Map<String,Integer> mgrEmpCountMap = new HashMap<String,Integer>();
    
    void findNumberOfEmployeesUnderManager(Map<String,String> dataSet){
        
        HashMap<String,List<String>> mgrMap = new HashMap<String,List<String>>();
//         1. Create a reverse map with Manager->DirectReportingEmployee 
//    combination. Off-course employee will be multiple so Value 
//    in Map is List of Strings.
//        "C" --> "A", "B",
//        "E" --> "D" 
//        "F" --> "C", "E", "F"
        for(Map.Entry<String,String> entry:dataSet.entrySet()){
            String emp = entry.getKey();
            String mgr = entry.getValue();
            
            if(!emp.equals(mgr)){//avoiding emp - emp entry
                
                List<String> empList = mgrMap.get(mgr);
                if(empList==null){
                    empList  = new ArrayList();
                }
                empList.add(emp);
                mgrMap.put(mgr, empList);
            }
            
        }
        
        for(String emp:dataSet.keySet()){
            //for each employee A, store the result of number of employees managed by A
            populateEmployeesManaged(emp, mgrMap);
        }
        
    }

    private int populateEmployeesManaged(String emp, HashMap<String, List<String>> mgrMap) {
        
        if(!mgrMap.containsKey(emp)){
            //No employees managed by employee emp
            mgrEmpCountMap.put(emp, 0);
            return 0;
        }
        
        if(mgrEmpCountMap.get(emp)!=null){
            //if already computed, return as such
            return mgrEmpCountMap.get(emp);
        }else{
            int count = 0 ;
            List<String> employeesManaged = mgrMap.get(emp);
            count = employeesManaged.size();//directly managed by emp
            for(String subEmp:employeesManaged){//indirect employees, call recursively
                count += populateEmployeesManaged(subEmp, mgrMap);
            }
            mgrEmpCountMap.put(emp, count);
            return count;
        }
        
        
    }
    
}
