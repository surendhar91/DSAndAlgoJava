/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarytree;

import java.util.Arrays;

/**
 *
 * @author surendhar-2481
 */
public class ArrayOfAllInterpretations {
    /*
    Qn:
    
    Consider a coding system for alphabets to integers where ‘a’ is represented as 1, ‘b’ as 2, .. ‘z’ as 26. 
    
    Given an array of digits (1 to 9) as input, write a function that prints all valid interpretations of input array.

     Examples

     Input: {1, 1}
     Output: ("aa", 'k") 
     [2 interpretations: aa(1, 1), k(11)]

     Input: {1, 2, 1}
     Output: ("aba", "au", "la") 
     [3 interpretations: aba(1,2,1), au(1,21), la(12,1)]

     Input: {9, 1, 8}
     Output: {"iah", "ir"} 
     [2 interpretations: iah(9,1,8), ir(9,18)]
    */
    /**    		  “” {1,2,1}            Codes used in tree
                       /             \               "a" --> 1
                      /               \              "b" --> 2 
                  "a"{2,1}            "l"{1}         "l" --> 12
                 /        \          /     \
                /          \        /       \
            "ab"{1}        "au"    "la"      null
             /    \
            /      \
         "aba"      null

     * */

        private static final String alphabet[] = {"", "a", "b", "c", "d", "e",
        "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
        "s", "t", "u", "v", "w", "x", "v", "z"};
        //Initial call createTree(0,"",arr);
	static Node createTree(int data,String pString,int[] arr){//pString is the parentString, arr is the array of integers given above.

		//data is the one we used to create alphabet for this node.
		if(data>26)
			return null;
		//parentString + alphabet of data
		String dataToStr = pString + alphabet[data];
		Node node = new Node(dataToStr);
		//if array length is zero, then done.
		if(arr.length!=0){
			data = arr[0];//process singel digit
                        int newArr[] = Arrays.copyOfRange(arr, 1, arr.length);
			node.left = createTree(data,dataToStr,newArr); //pass on the dataToStr string [i.e dataToStr becomes the parent for the child] 
                        //for left, we will process single digit integer.
                        //right of the tree will be null if the arr is of size 0 or 1.
                        if(arr.length>1){
                            data = arr[0]*10+arr[1];//process two digit for right tree.
                            newArr = Arrays.copyOfRange(arr, 2, arr.length);//skip the 2 digit and proceed with the rest.
                            node.right = createTree(data,dataToStr,newArr);
                        }
		}
                return node;//left and right will be null.
	}
        // The main function that prints all interpretations of array
        static void printAllInterpretations(int[] arr) {
 
            // Step 1: Create Tree
            Node root = createTree(0, "", arr);

            // Step 2: Print Leaf nodes
            printleaf(root);

            System.out.println();  // Print new line
        }
        static void printleaf(Node root){
            if(root==null)
                return ;
            if(root.left==null&&root.right==null){
                System.out.print(root.dataStr+" ");
                return ;
            }
            if(root.left!=null){
                printleaf(root.left);
            }
            if(root.right!=null){
               printleaf(root.right);
            }
        }
        public static void nodeInterpretationTestData(){
            // aacd(1,1,3,4) amd(1,13,4) kcd(11,3,4)
            // Note : 1,1,34 is not valid as we don't have values corresponding
            // to 34 in alphabet
            int[] arr = {1, 1, 3, 4};
            printAllInterpretations(arr);

            // aaa(1,1,1) ak(1,11) ka(11,1)
            int[] arr2 = {1, 1, 1};
            printAllInterpretations(arr2);

            // bf(2,6) z(26)
            int[] arr3 = {2, 6};
            printAllInterpretations(arr3);

            // ab(1,2), l(12)  
            int[] arr4 = {1, 2};
            printAllInterpretations(arr4);

            // a(1,0} j(10)  
            int[] arr5 = {1, 0};
            printAllInterpretations(arr5);

            // "" empty string output as array is empty
            int[] arr6 = {};
            printAllInterpretations(arr6);

            // abba abu ava lba lu
            int[] arr7 = {1, 2, 2, 1};
            printAllInterpretations(arr7);

        }
}
class Node{
	String dataStr;
	Node left;
	Node right;
	
	Node(String dataString){
		this.dataStr = dataString;
	}
	
	public String getDataString(){
		return this.dataStr;
	}
	
}