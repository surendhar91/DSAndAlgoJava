/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarysearchtree;

import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author surendhar-2481
 */
public class BinarySearchTree {
    class Node{
        int data;
        Node left,right;
        int lcount;
        Node(int data){
            this.data = data;
            this.left = this.right = null;
            this.lcount = 0;//number of elements in the left subtree
        }
    }
    Node root = null;
    
    Node insertRecur(Node root, int data){
        if(root==null){
            return new Node(data);
        }else if(data<root.data){
            root.left = insertRecur(root.left,data);//go to the left subtree
        }else if(data>root.data){
            root.right = insertRecur(root.right,data);
        }
        return root;
    }
    void insert(int data){
        root = insertRecur(root,data);
    }
    
    void delete(int data){
        root = deleteRecur(root,data);
    }
    Node deleteRecur(Node root, int data){
        if(root==null){
            return null;
        }else if(data<root.data){
            root.left  = deleteRecur(root.left,data);
        }else if(data>root.data){
            root.right = deleteRecur(root.right,data);
        }else{
            //if the element is found
            
            
            //If the element has one or no child
            if(root.left==null){
                return root.right;
            }
            else if(root.right==null){
                return root.left;
            }
            
            //If it has both children, we need to find the inorder successor in the right subtree
            root.data = findInOrderSuccessor(root.right).data;
            
            root.right = deleteRecur(root.right, root.data);//delete the inorder successor element in the right subtree.
            
        }
        return root;
    }
    
    Node findInOrderSuccessor(Node root){
        
        if(root==null){
            return root;
        }
        while(root.left!=null){
            root = root.left;
        }
        return root;
        
    }
    
    void findInOrderPredecessorAndSuccessor(Node root, Node[] inSucPre, int data){
        //Pre and suc will initially be null
        if(root==null){
            return ;
        }
        if(data<root.data){
            inSucPre[1] = root;//data is less than the root's data, then the successor of the data will be root
            findInOrderPredecessorAndSuccessor(root.left,  inSucPre, data);
        }else if(data>root.data){
            inSucPre[0] = root;//data is greater than the root's data, then the predecessor of the data will be root
            findInOrderPredecessorAndSuccessor(root.right, inSucPre, data);
        }else{
            //Data is found
            
            if(root.left!=null){//find the right most element of left subtree.
                root = root.left;
                while(root.right!=null){
                    root = root.right;
                }
                inSucPre[0] = root;
            }
            
            if(root.right!=null){//find the left most element of right subtree, successor of data found
                root = root.right;
                while(root.left!=null){
                    root = root.left;
                }
                inSucPre[1]  = root;
            }
            return ;
        }
        
    }
    
    Node lowestCommonAncestorBST(Node root, int n1, int n2){
        if(root==null)
        {
            return root;
        }
         // If both n1 and n2 are smaller than root, then LCA lies in left
        if(root.data>n1 && root.data>n2){//Note that n1 and n2 are elements in the tree
            return lowestCommonAncestorBST(root.left, n1, n2);
        }
        
         // If both n1 and n2 are greater than root, then LCA lies in right
        if(root.data<n1 && root.data<n2){//lca lies in right subtree
            return lowestCommonAncestorBST(root.right, n1, n2);
        }
        //element found.
        return root;
    }
    
    void inorder(){
        inorderRecur(root);
    }
    
    void inorderRecur(Node root){
        if(root==null){
            return ;
        }
        inorderRecur(root.left);
        System.out.println(root.data);
        inorderRecur(root.right);
    }
    
    Node insertIterative(Node root, Node node){
    
        Node traverseNode, currentParent;
        traverseNode = currentParent = root;
        while(traverseNode!=null){
            currentParent = traverseNode;
            if(node.data<traverseNode.data){
                traverseNode.lcount++;//increment the left subtree count of the traverseNode
                traverseNode = traverseNode.left;
            }else{
                traverseNode = traverseNode.right;
            }
        }
        if(root==null){
            root = node;
        }else if(node.data<currentParent.data){//find the last element where we had to insert the given node
            currentParent.left = node;
        }else{
            currentParent.right = node;
        }
        return root;
    }
    
    int findKthSmallestElementInBST(Node root, int k){
        // O(h) time complexity height of the BST, O(1) extra space.
            int res = -1;
            if(root!=null){
                //lcount represent the number of elements in the left subtree
                if((root.lcount+1)==k){//left subtree count + 1(current node)
                    return root.data;
                }else if(k<=root.lcount){
                    return findKthSmallestElementInBST(root.left, k);
                }else if(k>root.lcount){
                    return findKthSmallestElementInBST(root.right, k-(root.lcount+1));//traverse right subtree for finding the kth smallest element
                }
            }
            return res;
    }
    void mergeBST(Node root1, Node root2){
        // inserting tree2 nodes one by one to tree1 takes O(n~2) complexity in the worst case.
        // Traversing in order traversal of both the trees. O(m), and O(n) time. Merge them in O(m+n) time. construct a BST with these nodes in O(m+n) time for a balanced BST with O(m+n) extra space. 
        // space complexity however required is O(m+n).

        //Time: O(m+n) -> Space: O(height of the first tree + height of the second tree)
        Node currentNode1, currentNode2;
        // s1 is stack to hold nodes of first BST
        Stack<Node> stack1 = new Stack<Node>();
        // s2 is stack to hold nodes of second BST
        Stack<Node> stack2 = new Stack<Node>();
        
        currentNode1 = root1;
        currentNode2 = root2;
        
        //if either of the tree is null, then print the other tree
        if(root1==null){
            inorderRecur(root1);
            return ;
        }
        if(root2==null){
            inorderRecur(root2);
            return ;
        }
        
        while((currentNode1!=null || currentNode2!=null || !stack1.isEmpty() || !stack2.isEmpty())){
            
            if(currentNode1!=null || currentNode2!=null ){
                //push all the left elements to the stack
                if(currentNode1!=null){
                    stack1.push(currentNode1);
                    currentNode1 = currentNode1.left;
                }
                if(currentNode2!=null){
                    stack2.push(currentNode2);
                    currentNode2 = currentNode2.left;
                }
            }else{
                
                 // If we reach a NULL node and either of the stacks is empty,
            // then one tree is exhausted, ptint the other tree
                if(stack1.isEmpty()){
                    //print the stack 2 elements
                    while(!stack2.isEmpty()){
                        currentNode2 = stack2.pop();
                        currentNode2.left = null;//if we didn't set left to null, then Left tree elements are traversed again
                        inorderRecur(currentNode2);
                    }
                    return ;
                }
                
                if(stack2.isEmpty()){
                    //print the stack 1 elements
                    while(!stack1.isEmpty()){
                        currentNode1 = stack1.pop();
                        currentNode1.left = null;//Left tree elements are already traversed.
                        inorderRecur(currentNode1);
                    }
                    return ;
                }
                
                currentNode1 = stack1.pop();
                currentNode2 = stack2.pop();
                
                //Add the larger element to the corresponding stack again, pop the smallest element and print as such.. Traverse the right subtree of the smallest element.
                if(currentNode1.data<currentNode2.data){
                    System.out.print(" "+currentNode1.data);
                    currentNode1 = currentNode1.right;//traverse the right subtree of the current node and add all those elements to the stack, for next comparison.
                    //readd the currentNode2 element to stack 2
                    stack2.push(currentNode2);
                    currentNode2 = null;//Because, we don't want to readd them in the loop.
                }else{
                    System.out.print(" "+currentNode2.data);
                    currentNode2 = currentNode2.right;//we popped the currentNode 2 element from stack 2..
                    stack1.push(currentNode1);
                    currentNode1 = null;
                }
                
                
            }
            
        }
        
    }
    
    void correctBSTUtil(Node root, Node[] nodeArr){
        //O(n) complexity to correct the binary search tree where two nodes were swapped.
        Node first = nodeArr[0];
        Node middle = nodeArr[1];
        Node last = nodeArr[2];
        Node prev = nodeArr[3];
        if(root!=null){
            //do the inorder traversal
            correctBSTUtil(root.left,nodeArr);
            
            if(nodeArr[3]!=null && root.data<nodeArr[3].data){//prev
                
                if(first!=null){
                    //already one element is found with the violation
                    nodeArr[2]   = root;//last = root
                }else{
                    nodeArr[0]  = nodeArr[3];//first = prev
                    nodeArr[1] = root;//middle = current node
                }
                
            }
            nodeArr[3] = root;//mark this node as prev for the next traversal..
            
            correctBSTUtil(root.right, nodeArr);
        }
    }
    
    void correctBSTOfSwappedNodes(Node root){
        /*Two of the nodes of a Binary Search Tree (BST) are swapped. Fix (or correct) the BST.

         Input Tree:
         10
         /  \
         5    8
         / \
         2   20

         In the above tree, nodes 20 and 8 must be swapped to fix the tree.  
         Following is the output tree
         10
         /  \
         5    20
         / \
         2   8
         We can solve this in O(n) time and with a single traversal of the given BST. 
         Since inorder traversal of BST is always a sorted array, the problem can be reduced to a problem where two elements of a sorted array are swapped. 
         There are two cases that we need to handle:

         1. The swapped nodes are not adjacent in the inorder traversal of the BST.

         For example, Nodes 5 and 25 are swapped in {3 5 7 8 10 15 20 25}. 
         The inorder traversal of the given tree is 3 25 7 8 10 15 20 5 
         If we observe carefully, during inorder traversal, we find node 7 is smaller than the previous visited node 25. Here save the context of node 25 (previous node). 
         Again, we find that node 5 is smaller than the previous node 20. This time, we save the context of node 5 ( current node ). Finally swap the two node’s values.

         2. The swapped nodes are adjacent in the inorder traversal of BST.

         For example, Nodes 7 and 8 are swapped in {3 5 7 8 10 15 20 25}. 
         The inorder traversal of the given tree is 3 5 8 7 10 15 20 25 
         Unlike case #1, here only one point existzs where a node value is smaller than previous node value. e.g. node 7 is smaller than node 8.

         How to Solve? We will maintain three pointers, first, middle and last. 
         When we find the first point where current node value is smaller than previous node value, we update the first with the previous node & middle with the current node. 
         When we find the second point where current node value is smaller than previous node value, we update the last with the current node. 
         In case #2, we will never find the second point. So, last pointer will not be updated. 
         After processing, if the last node value is null, then two swapped nodes of BST are adjacent.
        
        */
        
        Node first, middle, last, prev;
        
        first = middle = last = prev = null;
        
        Node nodeArr[] = new Node[4];
        
        for(int i=0;i<nodeArr.length;i++){
            nodeArr[i] = null;
        }
        
        correctBSTUtil(root,nodeArr);
        
        first = nodeArr[0];
        middle = nodeArr[1];
        last = nodeArr[2];
        prev = nodeArr[3];
        
        if(first!=null && last!=null){
            //Case 1: Not adjacenet 
            //copy on the data
            int temp = last.data;
            last.data = first.data;
            first.data =temp;
            
            
        }else if(first!=null && middle!=null){
            int temp = middle.data;
            middle.data = first.data;
            first.data =temp;
        }
        inorderRecur(root);
    }
    
    int ceilValueBST(Node root, int key){
        /**
        
                      8
                    /   \    
                  4      12
                /  \    /  \
               2    6  10   14

            Key: 11  Floor: 10  Ceil: 12
            Key: 1   Floor: -1  Ceil: 2
            Key: 6   Floor: 6   Ceil: 6
            Key: 15  Floor: 14  Ceil: -1
         */
        //For example, consider designing memory management system in which free nodes are arranged in BST. Find best fit for the input request.
        
        /* 
        
         Ceil Value Node: Node with smallest data larger than or equal to key value.

         Imagine we are moving down the tree, and assume we are root node. The comparison yields three possibilities,

         A) Root data is equal to key. We are done, root data is ceil value.

         B) Root data < key value, certainly the ceil value can’t be in left subtree. Proceed to search on right subtree as reduced problem instance.

         C) Root data > key value, the ceil value may be in left subtree. We may find a node with is larger data than key value in left subtree, if not the root itself will be ceil node.
        
        */
        if(root==null){
            return -1;
        }
        
        if(root.data==key){
            return root.data;
        }
        
        if(root.data<key){
            //key value is higher, hence the key value must reside in right subtree.
            return ceilValueBST(root.right, key);
        }else{
            int ceil = ceilValueBST(root.left, key);
            ceil     = (ceil>=key)//if we find a node whose value is larger than the key value in left subtree, then it's valid
                        ?ceil:root.data;//if not, then we shall return the current node's data
            return ceil;
        }
        
    }
    
    boolean isPairPresentInBSTTree(Node root, int sum){
        // Returns true if a pair with target sum exists in BST, otherwise false

        //applicable for Binary tree also, given that the elements are in sorted order..
        /*
         The solution discussed below takes O(n) time, O(Logn) space and doesn’t modify BST. 
         The idea is same as finding the pair in sorted array (See method 1 of this for details).
        
         We traverse BST in Normal Inorder and Reverse Inorder simultaneously. In reverse inorder, we start from the rightmost node which is the maximum value node. 
        
         In normal inorder, we start from the left most node which is minimum value node. We add sum of current nodes in both traversals and compare this sum with given target sum.
        
        
         If the sum is same as target sum, we return true. If the sum is more than target sum, we move to next node in reverse inorder traversal, 
         otherwise we move to next node in normal inorder traversal.
        
        
         If any of the traversals is finished without finding a pair, we return false.
         */
         Stack<Node> inorderStack = new Stack<Node>();
         Stack<Node> reverseInStack = new Stack<Node>();
         
         Node current1 = root;
         Node current2 = root;
         
         int val1=0, val2=0;
         
         boolean done1 = false, done2 = false;
         while(true){
         
             while(!done1){
                  // Find next node in normal Inorder traversal. See following post
                  // http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion/
                 if(current1!=null){//push all the left tree elements to the stack..
                     inorderStack.push(current1);
                     current1 = current1.left;
                 }else{
                     if(inorderStack.isEmpty()){
                         done1 = true;//if the stack is empty, then we are done..
                     }else{
                         current1 = inorderStack.pop();
                         val1          = current1.data;
                         current1      = current1.right;
                         done1 = true;//next node is found
                     }
                 }
                 
                 
             }
             
             while(!done2){
             
                    if(current2!=null){
                        reverseInStack.push(current2);
                        current2 = current2.right;
                    }else{
                        if(reverseInStack.isEmpty()){
                            done2 = true;
                        }else{
                            current2 = reverseInStack.pop();
                            val2 = current2.data;
                            current2 = current2.left;
                            done2 = true;//next node in reverse inorder traversal is found..
                        }
                    }
                 
             }
             
             if(val1+val2==sum){
                 System.out.println("Pair found "+val1+" and "+val2+" = sum "+sum);
                 return true;
             }else if(val1+val2<sum){
                 done1 = false;//traverse higher in the left array
             }else if(val1+val2>sum){
                 done2 = false;//traverse lower in the right array.
             }
             
            // If any of the inorder traversals is over, then there is no pair
            // so return false
             if(val1>=val2){//if we crossed the traversal..
                 return false;
             }
             
         }
        
    }
 
    long binomialCoeff(int n, int k) {
        //nCk = n!/(n-k)!*(k)! = [n*(n-1)*---*(n-k+1)]/k!
        //5C3 = 5!/(5-3)!*(3)! = (5*4*3)*2(n-k)! / 2(n-k)! * 3(k)!
        long res = 1;

        // Since C(n, k) = C(n, n-k)
        if (k > n - k) {
            k = n - k;
        }

        // Calculate value of [n*(n-1)*---*(n-k+1)] / [k*(k-1)*---*1]
        for (int i = 0; i < k; ++i) {
            res *= (n - i);
            res /= (i + 1);
        }

        return res;
    }

// A Binomial coefficient based function to find nth catalan
// number in O(n) time
    long catalan(int n) {
    //        
    //        Total number of possible Binary Search Trees with n different keys = Catalan number Cn = (2n)!/(n+1)!*n!
    //
    //For n = 0, 1, 2, 3, … values of Catalan numbers are 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, …. So are numbers of Binary Search Trees.
        
        /*
        Consider all possible binary search trees with each element at the root. 
        
        If there are n nodes, then for each choice of root node, there are n – 1 non-root nodes and these non-root nodes must be partitioned into those that are less than a chosen root 
        and those that are greater than the chosen root.

         Let’s say node i is chosen to be the root. Then there are i – 1 nodes smaller than i and n – i nodes bigger than i. 
         For each of these two sets of nodes, there is a certain number of possible subtrees.

         Let t(n) be the total number of BSTs with n nodes. 
        
         The total number of BSTs with i at the root is t(i – 1) t(n – i). The two terms are multiplied together because the arrangements in the left and right subtrees are independent.
         That is, for each arrangement in the left tree and for each arrangement in the right tree, you get one BST with i at the root.

         Summing over i gives the total number of binary search trees with n nodes.
        */
        // Calculate value of 2nCn  = 2n! / n! * n! = 2n! / n+1 * n! * n! = binomialCoeff(2n , n)/(n+1);
        long c = binomialCoeff(2 * n, n);

        // return 2nCn/(n+1)
        return c / (n + 1);
    }
    int[] merge(int arr1[], int arr2[], int m,int n){
        
        int i,j,k;
        i=0;j=0;k=0;
        int[] mergeArr = new int[m+n];
        while(i<m&&j<n){
            if(arr1[i]<arr2[j]){
                // Pick the smaler element and put it in mergedArr
                mergeArr[k++]=arr1[i];
                i++;
            }else{
                mergeArr[k++]=arr2[j];
                j++;
            }
            
        }
        while(i<m){
            mergeArr[k++]=arr1[i];
            i++;
        }
        while(j<n){
            mergeArr[k++]=arr2[j];
            j++;
        }
        return mergeArr;
    }
    Node sortedArrayToBST(int[] arr, int start, int end){
        //O(m+n) time
        
        //Base case
        if(start>end){
            return null;
        }
        //Get the mid element
        int mid = (start+end)/2;
        
        Node root = new Node(arr[mid]);
         /* Recursively construct the left subtree and make it
       left child of root */
        root.left = sortedArrayToBST(arr, start, mid-1);
         /* Recursively construct the right subtree and make it
       right child of root */
        root.right = sortedArrayToBST(arr, mid+1, end);
        
        return root;
        
    }
    Node mergeTrees(Node root1, Node root2, int m, int n){
        
        /*
        You are given two balanced binary search trees e.g., AVL or Red Black Tree. Write a function that merges the two given balanced BSTs into a balanced binary search tree. Let there be m elements in first tree and n elements in the other tree. Your merge function should take O(m+n) time.

         In the following solutions, it is assumed that sizes of trees are also given as input. If the size is not given, then we can get the size by traversing the tree (See this).
         1) Do inorder traversal of first tree and store the traversal in one temp array arr1[]. This step takes O(m) time.
         2) Do inorder traversal of second tree and store the traversal in another temp array arr2[]. This step takes O(n) time.
         3) The arrays created in step 1 and 2 are sorted arrays. Merge the two sorted arrays into one array of size m + n. This step takes O(m+n) time.
         4) Construct a balanced tree from the merged array using the technique discussed in this post. This step takes O(m+n) time.
        
        Method 2:
        
        We can use a Doubly Linked List to merge trees in place. Following are the steps.

         1) Convert the given two Binary Search Trees into doubly linked list in place (Refer http://www.geeksforgeeks.org/the-great-tree-list-recursion-problem/ post for this step).
         2) Merge the two sorted Linked Lists (Refer http://www.geeksforgeeks.org/merge-two-sorted-linked-lists/ post for this step).
         3) Build a Balanced Binary Search Tree from the merged list created in step 2. (Refer http://www.geeksforgeeks.org/in-place-conversion-of-sorted-dll-to-balanced-bst/ post for this step)
        */
        int inorder1[] = new int[m];
        int index1[] = new int[1];
        index1[0] = 0;
        storeInOrder(root1,inorder1,index1);
        
        int inorder2[] = new int[n];
        int index2[] = new int[1];
        index2[0] = 0;
        storeInOrder(root2,inorder2,index2);
        
        int mergedArr[] = merge(inorder1, inorder2, m, n);//now that we have obtained sorted array, construct the tree using that.
        
        return sortedArrayToBST(mergedArr, 0, m+n-1);
        
        
    }
    
    void storeInOrder(Node root, int[] inorder, int[] index){//using index array, as this shall be incremented..
        if(root==null){
            return ;
        }
        storeInOrder(root.left,inorder,index);
        inorder[index[0]] = root.data;
        index[0]++;
        storeInOrder(root.right,inorder,index);
    }
    
    int countNodes(Node root){
        if(root==null){
            return 0;
        }else{
            return countNodes(root.left)+countNodes(root.right)+1;
        }
        
    }
    
    void arrayToBST(Node root, int[] arr, int[] index){
        if(root==null){
            return ;
        }
        arrayToBST(root.left, arr, index);
        
        root.data = arr[index[0]];
        index[0]++;
        
        arrayToBST(root.right, arr, index);
    }
    
    void binaryToBST(Node root){
//        Following is a 3 step solution for converting Binary tree to Binary Search Tree.
//1) Create a temp array arr[] that stores inorder traversal of the tree. This step takes O(n) time.
//2) Sort the temp array arr[]. Time complexity of this step depends upon the sorting algorithm. In the following implementation, Quick Sort is used which takes (n^2) time. This can be done in O(nLogn) time using Heap Sort or Merge Sort.
//3) Again do inorder traversal of tree and copy array elements to tree nodes one by one. This step takes O(n) time.
        
        if(root==null){
            return ;
        }
        int n = countNodes(root);
        
        int arr[] = new int[n];
        
        int index[] = new int[1];
        index[0] = 0;
        
        storeInOrder(root, arr, index);
        
        Arrays.sort(arr);//will have sorted elements.
        
        //now copy the sorted array elements to binary search tree (maintaining the structure of binary tree)
        int[] arrIndex = new int[1];
        arrIndex[0]=0;
        arrayToBST(root, arr,arrIndex);
        
        return ;
        
    }
    
    static void mergeTreeTestData(){
        /* Create following tree as first balanced BST
         100
         /  \
         50    300
         / \
         20  70
         */
        BinarySearchTree bst1 = new BinarySearchTree();
        bst1.insert(100);
        Node root1 = bst1.root;
        root1.left = bst1.new Node(50);
        root1.right = bst1.new Node(300);
        root1.left.left = bst1.new Node(20);
        root1.left.right = bst1.new Node(70);

        /* Create following tree as second balanced BST
         80
         /  \
         40   120
         */
        BinarySearchTree bst2 = new BinarySearchTree();
        bst2.insert(80);
        Node root2 = bst2.root;
        root2.left = bst2.new Node(40);
        root2.right = bst2.new Node(120);
        
        Node mergedTree = bst2.mergeTrees(root1, root2, 5, 3);
        
        System.out.println("Following is the inorder traversal of merged tree");
        bst2.inorderRecur(mergedTree);
    }
    
    static void binaryToBSTTestData(){
        /* Constructing tree given in the above figure
         10
         /  \
         30   15
         /      \
         20       5   */
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(10);
        Node root = bst.root;
        root.left = bst.new Node(30);
        root.right = bst.new Node(15);
        root.left.left = bst.new Node(20);
        root.right.right = bst.new Node(5);
        
        bst.binaryToBST(root);
        
        System.out.println("Following is the inorder traversal of converted binary search tree");
        bst.inorderRecur(root);
    }
    
    static void totalBSTOfNodeNTestData(){
        System.out.println("Number of binary search tree with 4 nodes is "+new BinarySearchTree().catalan(4));
    }
    
    static void isPairPresentInTreeOfSumTestData(){
    
        /*
         15
         /     \
         10      20
         / \     /  \
         8  12   16  25    */
        BinarySearchTree bst1 = new BinarySearchTree();
        bst1.insert(15);
        Node root = bst1.root;
        root.left = bst1.new Node(10);
        root.right = bst1.new Node(20);
        root.left.left = bst1.new Node(8);
        root.left.right = bst1.new Node(12);
        root.right.left = bst1.new Node(16);
        root.right.right = bst1.new Node(25);

        int target = 33;
        if (bst1.isPairPresentInBSTTree(root, target) == false) {
            System.out.println("\n No such values are found\n");
        }
    }
    
    static void correctBSTNodesSwapTestData(){
        
        BinarySearchTree bst1 = new BinarySearchTree();

        bst1.insert(6);
        Node root = bst1.root;
        root.left = bst1.new Node(10);
        root.right = bst1.new Node(2);
        root.left.left = bst1.new Node(1);
        root.left.right = bst1.new Node(3);
        root.right.right = bst1.new Node(12);
        root.right.left = bst1.new Node(7);
        
        bst1.inorderRecur(root);
        
        bst1.correctBSTOfSwappedNodes(root);
        
    }
    
    static void ceilBSTTestData(){
        BinarySearchTree bst1 = new BinarySearchTree();
        bst1.insert(8);
        bst1.insert(4);
        bst1.insert(12);
         bst1.insert(2);
        bst1.insert(6);
        bst1.insert(10);
        bst1.insert(14);
        for(int i=0;i<16;i++){
            System.out.println("Ceil value of i "+i+" is "+bst1.ceilValueBST(bst1.root, i));
        }
    }
    
    static void mergeBSTTestData(){
        BinarySearchTree bst1 = new BinarySearchTree();
        bst1.insert(3);
        bst1.insert(1);
        bst1.insert(5);
        
        BinarySearchTree bst2 = new BinarySearchTree();
        bst2.insert(4);
        bst2.insert(2);
        bst2.insert(6);
        
        bst1.mergeBST(bst1.root,bst2.root);
        return;
    }
    
    static void findKthSmallestElementInBSTTestData(){
        int ele[] = { 20, 8, 22, 4, 12, 10, 14 };
        BinarySearchTree bstTree = new BinarySearchTree();
        for(int i=0;i<ele.length;i++){
//            tree.root = tree.insertIterative(tree.root, new Node(ele[i]));
            bstTree.root = bstTree.insertIterative(bstTree.root, new BinarySearchTree().new Node(ele[i]));
        }
        bstTree.inorder();
        for(int i=1;i<ele.length;i++){
            System.out.println("Kth smallest element for k "+i+" is "+bstTree.findKthSmallestElementInBST(bstTree.root,i));
        }
    }
    
    static void lowestCommonAncestorTestData(){
          // Let us construct the BST shown in the above figure
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(20);
        tree.insert(8);
        tree.insert(22);
        tree.insert(4);
        tree.insert(12);
        tree.insert(10);
        tree.insert(14);
 
        int n1 = 10, n2 = 14;
        Node t = tree.lowestCommonAncestorBST(tree.root, n1, n2);
        System.out.println("LCA of " + n1 + " and " + n2 + " is " + t.data);
 
        n1 = 14;
        n2 = 8;
        t = tree.lowestCommonAncestorBST(tree.root, n1, n2);
        System.out.println("LCA of " + n1 + " and " + n2 + " is " + t.data);
 
        n1 = 10;
        n2 = 22;
        t = tree.lowestCommonAncestorBST(tree.root, n1, n2);
        System.out.println("LCA of " + n1 + " and " + n2 + " is " + t.data);
    }
    
    static void findInOrderPredecessorAndSuccessorTestData(){
         BinarySearchTree tree = new BinarySearchTree();
 
        /* Let us create following BST
              50
           /     \
          30      70
         /  \    /  \
        20   40  60   80 */
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
        
        Node[] inSucPre = new Node[2];
        int key = 65;
        tree.findInOrderPredecessorAndSuccessor(tree.root,inSucPre,key);
        Node pre = inSucPre[0];
        if(pre!=null){
            System.out.println("Predecessor is "+pre.data);
        }else{
            System.out.println("No predecessor");
        }
        Node suc = inSucPre[1];
        if(suc!=null){
            System.out.println("Successor is "+suc.data);
        }else{
            System.out.println("No successor");
        }
    }
    
    static void deleteBSTTestData(){
        BinarySearchTree tree = new BinarySearchTree();
 
        /* Let us create following BST
              50
           /     \
          30      70
         /  \    /  \
        20   40  60   80 */
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
 
        System.out.println("Inorder traversal of the given tree");
        tree.inorder();
 
        System.out.println("\nDelete 20");
        tree.delete(20);
        System.out.println("Inorder traversal of the modified tree");
        tree.inorder();
 
        System.out.println("\nDelete 30");
        tree.delete(30);
        System.out.println("Inorder traversal of the modified tree");
        tree.inorder();
 
        System.out.println("\nDelete 50");
        tree.delete(50);
        System.out.println("Inorder traversal of the modified tree");
        tree.inorder();
    }
    
    static void insertBSTTestData(){
        BinarySearchTree tree = new BinarySearchTree();
 
        /* Let us create following BST
              50
           /     \
          30      70
         /  \    /  \
       20   40  60   80 */
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
 
        // print inorder traversal of the BST
        tree.inorder();
    }
    
    public static void binarySearchTreeTestData(){
//        insertBSTTestData();
//        deleteBSTTestData();
//        findInOrderPredecessorAndSuccessorTestData();
//        lowestCommonAncestorTestData();
//        findKthSmallestElementInBSTTestData();
//        mergeBSTTestData();
//        correctBSTNodesSwapTestData();
//        ceilBSTTestData();
//        isPairPresentInTreeOfSumTestData();
//        totalBSTOfNodeNTestData();
//        mergeTreeTestData();
        binaryToBSTTestData();
    }
}
