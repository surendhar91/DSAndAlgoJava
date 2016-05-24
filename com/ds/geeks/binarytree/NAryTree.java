/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarytree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author surendhar-2481
 */
public class NAryTree {

    private static void NAryTreeMinItrTestData() {
        NAryTree tree1 = new NAryTree(17);
        tree1.addChild(0, 1);
        tree1.addChild(0, 2);
        tree1.addChild(0, 3);
        tree1.addChild(0, 4);
        tree1.addChild(0, 5);
        tree1.addChild(0, 6);

        tree1.addChild(1, 7);
        tree1.addChild(1, 8);
        tree1.addChild(1, 9);

        tree1.addChild(4, 10);
        tree1.addChild(4, 11);

        tree1.addChild(6, 12);

        tree1.addChild(7, 13);
        tree1.addChild(7, 14);
        tree1.addChild(10, 15);
        tree1.addChild(11, 16);
        
        tree1.getMinItrMain();
        
        NAryTree tree2 = new NAryTree(3);
        tree2.addChild(0, 1);
        tree2.addChild(0, 2);
        tree2.getMinItrMain();
        
        NAryTree tree9 = new NAryTree(25);
        tree9.addChild(0, 1);
        tree9.addChild(0, 2);
        tree9.addChild(0, 3);
        tree9.addChild(0, 4);
        tree9.addChild(0, 5);
        tree9.addChild(0, 6);

        tree9.addChild(1, 7);
        tree9.addChild(2, 8);
        tree9.addChild(3, 9);
        tree9.addChild(4, 10);
        tree9.addChild(5, 11);
        tree9.addChild(6, 12);

        tree9.addChild(7, 13);
        tree9.addChild(8, 14);
        tree9.addChild(9, 15);
        tree9.addChild(10, 16);
        tree9.addChild(11, 17);
        tree9.addChild(12, 18);

        tree9.addChild(13, 19);
        tree9.addChild(14, 20);
        tree9.addChild(15, 21);
        tree9.addChild(16, 22);
        tree9.addChild(17, 23);
        tree9.addChild(19, 24);
        tree9.getMinItrMain();
    }
    
    int N; //Number of nodes in the tree
    
    List<List<Integer>> adjNodes; //representing the list of childs for each node
    
    public NAryTree(int n){
        
        N = n;
        adjNodes = new ArrayList<List<Integer>>();//for each node, we may have multiple child
        for(int i=0;i<n;i++){
           adjNodes.add(i,new ArrayList<Integer>());
        }
    }
     
    void addChild(int vertex,int node){
        
        List<Integer> childNodeForVertex = adjNodes.get(vertex);
        childNodeForVertex.add(node);
        
    }
    
    void getMinItrMain(){//Greedy approach to solve the minimum number of iteration to pass info to all the nodes..
    
        //Minimum iteration for all the vertex as zero - Initialization phase
        
        Integer[] minItrVertex = new Integer[N];//For N vertices
        
        for(int i=0;i<N;i++){
            minItrVertex[i] = 0; //Initializing the min iteration for all the vertex as 0
        }
        getMinItr(0,minItrVertex);//0 being the root of the tree.
        
        System.out.println("Minimum iteration for N "+N+" is "+minItrVertex[0]);
    }
/*
    
    Nodes with height = 0: (Trivial case) Leaf node has no children (no information passing needed), so no of iterations would be ZERO.
     
    Nodes with height = 1: 
     Here node has to pass info to all the children one by one (all children are leaf node, so no more information passing further down). Since all children are leaf, node can pass info to any child in any order (pick any child who didn’t receive the info yet). One iteration needed for each child and so no of iterations would be no of children.So node with height 1 with n children will take n iterations.
     Take a counter initialized with ZERO, loop through all children and keep incrementing counter.
    
    Nodes with height > 1: 
     Let’s assume that there are n children (1 to n) of a node and minimum no iterations 
     for all n children are c1, c2, …., cn.
    
    To make sure maximum no of nodes participate in info passing in any iteration, 
       parent should 1st pass info to that child who will take maximum iteration to pass info further down in subsequent iterations. i.e. in any iteration, parent should choose the child who takes maximum iteration later on. It can be thought of as a greedy approach where parent choose that child 1st, who needs maximum no of iterations so that all subsequent iterations can be utilized efficiently.
    
    If parent goes in any other fashion, then in the end, there could be some nodes which are done quite early, 
       sitting idle and so bandwidth is not utilized efficiently in further iterations.
    
    If there are two children i and j with minimum iterations ci and cj where ci > cj, 
       then If parent picks child j 1st then no of iterations needed by parent to pass info to both children and their subtree would be:max (1 + cj, 2 + ci) = 2 + ci
    
    If parent picks child i 1st then no of iterations needed by parent to pass info to both children 
       and their subtree would be: max(1 + ci, 2 + cj) = 1 + ci (So picking ci gives better result than picking cj)
    
    */
    private void getMinItr(int vertex, Integer[] minItrVertex) {
        //Assume that in this method call, we are getting a vertex and an array of minItrVertex, which can be updated for the child of the given vertex
        
        //Step 1: Get the child count for this vertex
        List<Integer> vertexChilds = adjNodes.get(vertex);
        int childSize = vertexChilds.size();
        
        minItrVertex[vertex] = childSize;//minimum iteration would be the number of childs to the least\
        //Ensures the Base case:
        //If node is leaf, minItr = 0
        //If node's height is 1, minItr = children count
        
        //Step 2: For each of this child, we need to recursively calculate the minimum iteration that is going to take place on it's subtree.
        Integer[] cArr = new Integer[childSize]; //C(I)
        int k=0;
        for(int i=0;i<childSize;i++){//for this vertex, iterate through all of it's child.
            //These child's min iteration will be kept in cArr
            
           getMinItr(vertexChilds.get(i),minItrVertex); //minItrVertex is the value that has been global.
           
           cArr[k++] = minItrVertex[vertexChilds.get(i)];   
        }
        
        
        
        //Step 3: we are sorting in descending order, so that, the minimum number of iterations can be achieved.
        /*
         This tells that parent should always choose child i with max ci value in any iteration.
         SO here greedy approach is:
         sort all ci values decreasing order,
         let’s say after sorting, values are c1 > c2 > c3 > …. > cn
         take a counter c, set c = 1 + c1 (for child with maximum no of iterations)
         for all children i from 2 to n, c = c + 1 + ci
         then total no of iterations needed by parent is max(n, c)
        */
        Arrays.sort(cArr, Collections.reverseOrder());//sorting c(I)
        
        int c = 0 ;//gets the child with maximum no of iterations..
//        maxItrTemp = maxItrTemp + 1 ; 
               
        for(int i=0;i<cArr.length;i++){
           c = 1 + i + cArr[i]; // 1 is required [ 1+0+c[0] , 1+1+c[1]]
           minItrVertex[vertex]=Math.max(c, minItrVertex[vertex]);
           //Why max
           /*
           If parent picks child i 1st then no of iterations needed by parent to pass info to both children 
           and their subtree would be: max(1 + ci, 2 + cj) = 1 + ci 
           (So picking ci gives better result than picking cj)
           */
        }
        //Step 4: Now that we have obtained the maximum iteration
    }
    
    public static void NAryTreeTestData(){
        NAryTreeMinItrTestData();
    }
    
}
