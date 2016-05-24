/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarytree;

/**
 *
 * @author surendhar-2481
 */
public class CustomTreeProblem {
    public static void CustomTreeProblemTestData(){
        
        /*
        You are given a set of links, e.g.

        a ---> b
        b ---> c
        b ---> d
        a ---> e 

        Print the tree that would form when each pair of these links that has the same character as start and end point is joined together. 
        You have to maintain fidelity w.r.t. the height of nodes, i.e. nodes at height n from root should be printed at same row or column. 
        For set of links given above, tree printed should be –

        -->a
            |-->b
            |   |-->c
            |   |-->d
            |-->e
        
        Note that these links need not form a single tree; they could form, ahem, a forest. Consider the following links

         a ---> b
         a ---> g
         b ---> c
         c ---> d
         d ---> e
         c ---> f
         z ---> y
         y ---> x
         x ---> w

         The output would be following forest.

         -->a
         |-->b
         |   |-->c
         |   |   |-->d
         |   |   |   |-->e
         |   |   |-->f
         |-->g

         -->z
         |-->y
         |   |-->x
         |   |   |-->w

        
        */
        
        Tree.TreeDriverTestData();
        
        /*
        Solution:  
                The idea is to maintain two arrays, one array for tree nodes and other for trees themselves (we call this array forest). 
                An element of the node array contains the TreeNode object that corresponds to respective character. 
                An element of the forest array contains Tree object that corresponds to respective root of tree.
        */
    }
}
class Tree{

    private TreeNode root;//parent node
    
    Tree(TreeNode root){
       this.root=root;
    }
    public Tree[] buildFromLinks(String[] links){
        
        Tree[] forest = new Tree[26];//To store the tree.
        TreeNode[] nodes = new TreeNode[26];//to store all the node values
        
        for(String link: links){
            String[] ends = link.split(" ");
            int start = (int)(ends[0].charAt(0)-'a');//start index of the character parsed
            int end = (int)(ends[1].charAt(0)-'a');//index of the character parsed
            
            if(nodes[start]==null){//if start of the link not seen before, then add it to both arrays [i.e nodes and tree]
                nodes[start] = new TreeNode((char)(start+'a'));
                // Note that it may be removed later when this character is
                // last character of a link. For example, let we first see
                // a--->b, then c--->a.  We first add 'a' to array of trees
                // and when we see link c--->a, we remove it from trees array.
                forest[start] = new Tree(nodes[start]);//set this start node as the parent.
            }
            if(nodes[end]==null){
                nodes[end] = new TreeNode((char)(end+'a'));
            }else{//if already found, then we must set forest[end] set to null if exists. Refer the note above.
               forest[end]=null;
            }
            //Now that start and end node are added, we must add the children
            nodes[start].addChild(nodes[end], end);//add the child nodes[end] at end index
        }
        return forest;
    
    }
    static void printForest(String[] links){
        
        Tree root = new Tree(new TreeNode('\0'));//dummy node
        Tree[] forest = root.buildFromLinks(links);//get the forest from the link
        for(Tree tree:forest){//for each tree in the forest.
            if(tree!=null){//some tree may be null, because we set the forest to have 26 trees.
                tree.root.printTreeIdented("");//root is a dummy node.
                System.out.println("");
            }
        }
    
    }
    static void TreeDriverTestData(){
        String[] links1 = {"a b", "b c", "b d", "a e"};
        System.out.println("------------ Forest 1 ----------------");
        printForest(links1);

        String[] links2 = {"a b", "a g", "b c", "c d", "d e", "c f",
            "z y", "y x", "x w"};
        System.out.println("------------ Forest 2 ----------------");
        printForest(links2);
    }
}
class TreeNode{
    TreeNode[] children;
    char c;
    
    TreeNode(char c){
       this.c=c;
       children = new TreeNode[26];
    }
    
    void addChild(TreeNode child, int index){
       this.children[index]=child;
    }

    void printTreeIdented(String indent) {
        System.out.println(indent+"-->"+c);
        for(TreeNode node:children){
            if(node!=null){//some node may be null
                node.printTreeIdented(indent+"   |");
            }
        }
    }
}
