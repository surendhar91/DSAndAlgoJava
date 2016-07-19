/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarysearchtree;

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
        //O(m+n) -> O(height of the first tree + height of the second tree)
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
                    //print the stack 2 elements
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
        mergeBSTTestData();
    }
}
