/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarysearchtree;

/**
 *
 * @author surendhar-2481
 */
public class BinarySearchTree {
    class Node{
        int data;
        Node left,right;
        Node(int data){
            this.data = data;
            this.left = this.right = null;
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
        lowestCommonAncestorTestData();
    }
}
