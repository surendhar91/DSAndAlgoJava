/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarytree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author surendhar-2481
 */
public class BinaryTree {
    
    void printTopViewOfBinaryTree(BinaryNode root){
        /*
        
        Top view of a binary tree is the set of nodes visible when the tree is viewed from the top. 
        Given a binary tree, print the top view of it. The output nodes can be printed in any order. Expected time complexity is O(n)

         A node x is there in output if x is the topmost node at its horizontal distance. 
        Horizontal distance of left child of a node x is equal to horizontal distance of x minus 1, and that of right child is horizontal distance of x plus 1. 
        
        */
        if(root==null) return ;
        
        Queue<QBinaryItem> que = new LinkedList<QBinaryItem>();
        
        HashSet<Integer> hLevelSet = new HashSet<Integer>();
        
        QBinaryItem rootItem  = new QBinaryItem(root, 0);
        que.add(rootItem);
        
        
        while(!que.isEmpty()){ //as long as the queue is not empty
            //Level order traversal
            QBinaryItem currentItem = que.remove();
            
            BinaryNode currNode     = currentItem.node;
            int  hLevel     = currentItem.level;
            // If this is the first node at its horizontal distance,
            // then this node is in top view
            if(!hLevelSet.contains(hLevel)){
                hLevelSet.add(hLevel);//Ensures that only one node printing in a vertical level
                //if that level has not added, then 
                System.out.println(currentItem.node.data+" ");
            }
            
            if(currNode.left!=null){
                que.add(new QBinaryItem(currNode.left, hLevel-1));
            }
            if(currNode.right!=null){
                que.add(new QBinaryItem(currNode.right, hLevel+1));
            }
        }
        
    }
    void printBottomViewOfBinaryTree(BinaryNode root){
        //Bottom view of binary tree is printing the elements that are last in a horizontal axis.
        
        Queue<QBinaryItem> que = new LinkedList<QBinaryItem>();

//        HashSet<Integer> hLevelSet = new HashSet<Integer>();
        //Instead of using hashset, we will be using map <HLevel, Nodeencountered>
        Map<Integer,BinaryNode> hLevelVsNode= new HashMap<Integer,BinaryNode>();
        QBinaryItem rootItem = new QBinaryItem(root, 0);
        que.add(rootItem);

        while (!que.isEmpty()) { //as long as the queue is not empty
            //Level order traversal
            QBinaryItem currentItem = que.remove();

            BinaryNode currNode = currentItem.node;
            int hLevel = currentItem.level;
            //To override the last node at its horizontal distance,
            // then this node is in bottom view
            hLevelVsNode.put(hLevel, currNode);

            if (currNode.left != null) {
                que.add(new QBinaryItem(currNode.left, hLevel - 1));
            }
            if (currNode.right != null) {
                que.add(new QBinaryItem(currNode.right, hLevel + 1));
            }
        }
        Set<Entry<Integer,BinaryNode>> hLevelVsNodeSet=hLevelVsNode.entrySet();
        
        Iterator<Entry<Integer,BinaryNode>> itr = hLevelVsNodeSet.iterator();
        while(itr.hasNext()){
        
            Map.Entry<Integer,BinaryNode> currEntry = itr.next();
            System.out.println(currEntry.getValue().data+" ");
        }
    }
    
    public void BinaryTreeTestData(){
//        printTopViewOfBinaryTreeTestData();
          printBottonViewOfBinaryTreeTestData();
    }

    private void printTopViewOfBinaryTreeTestData() {
//      
        /*
        Create following Binary Tree
                  1
                /  \
                2    3
                \
                  4
                    \
                     5
                       \
                        6
        */
        BinaryNode root = new BinaryNode(1);
        root.left = new BinaryNode(2);
        root.right = new BinaryNode(3);
        root.left.right = new BinaryNode(4);
        root.left.right.right = new BinaryNode(5);
        root.left.right.right.right = new BinaryNode(6);
        System.out.println("Following are nodes in top view of Binary Tree");
        printTopViewOfBinaryTree(root);
    }

    private void printBottonViewOfBinaryTreeTestData() {
        BinaryNode root = new BinaryNode(20);
        root.left = new BinaryNode(8);
        root.right = new BinaryNode(22);
        root.left.left = new BinaryNode(5);
        root.left.right = new BinaryNode(3);
        root.right.left = new BinaryNode(4);
        root.right.right = new BinaryNode(25);
        root.left.right.left = new BinaryNode(10);
        root.left.right.right = new BinaryNode(14);
        System.out.println("Bottom view of the given binary tree:");
        printBottomViewOfBinaryTree(root);
    }
    
}
class BinaryNode{
    int data;
    BinaryNode left,right;
    BinaryNode(int data){
        this.data = data;
        left = null;
        right = null;
    }
}
class QBinaryItem{
    BinaryNode node;
    int level;
    QBinaryItem(BinaryNode node, int level){
        this.node = node;
        this.level = level;
    }
}
