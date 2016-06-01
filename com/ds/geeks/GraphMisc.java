/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks;

import java.util.Random;

/**
 *
 * @author surendhar-2481
 */
public class GraphMisc {
    
    boolean canBeChained(String[] arr){

        /*
         
         Given an array of strings, find if the given strings can be chained to form a circle. A string X can be put before another string Y in circle if the last character of X is same as first character of Y.

         Examples:

         Input: arr[] = {"geek", "king"}
         Output: Yes, the given strings can be chained.
         Note that the last character of first string is same
         as first character of second string and vice versa is
         also true.

         Input: arr[] = {"for", "geek", "rig", "kaf"}
         Output: Yes, the given strings can be chained.
         The strings can be chained as "for", "rig", "geek" 
         and "kaf"

         The idea is to create a directed graph of all characters and then find if their is an eulerian circuit in the graph or not. If there is an eulerian circuit, then chain can be formed, otherwise not.
         Note that a directed graph has eulerian circuit only if in degree and out degree of every vertex is same, and all non-zero degree vertices form a single strongly connected component.

         Following are detailed steps of the algorithm.

         1) Create a directed graph g with number of vertices equal to the size of alphabet. We have created a graph with 26 vertices in the below program.

         2) Do following for every string in the given array of strings.
         …..a) Add an edge from first character to last character of the given graph.

         3) If the created graph has eulerian circuit, then return true, else return false.
        */
        
        Graph g = new Graph(26);
        for(int i=0;i<arr.length;i++){
            String curr = arr[i];
//            System.out.println((curr.charAt(0)-'a')+"->"+(curr.charAt(curr.length()-1)-'a'));
            g.addEdge(curr.charAt(0)-'a', curr.charAt(curr.length()-1)-'a');
        }
        boolean chainSuccess = new Connectivity().isEulerianCycleInDirected(g,true);//Most of the edges will be zero degree vertex, hence when checking eulerian for directed, 
        //we need to ensure that nonZeroStronglyConnected is called
        System.out.println(chainSuccess?"Can be chained":"Cannot be chained");
        return chainSuccess;
    }
    
    void findOrderOfChars(String[] arr,int alpha){
        /*
        Given a sorted dictionary (array of words) of an alien language, find order of characters in the language.

         Examples:

         Input:  words[] = {"baa", "abcd", "abca", "cab", "cad"}
         Output: Order of characters is 'b', 'd', 'a', 'c'
         Note that words are sorted and in the given language "baa" 
         comes before "abcd", therefore 'b' is before 'a' in output.
         Similarly we can find other orders.

         Input:  words[] = {"caa", "aaa", "aab"}
         Output: Order of characters is 'c', 'a', 'b'
        
         The idea is to create a graph of characters and then find topological sorting of the created graph. Following are the detailed steps.

         1) Create a graph g with number of vertices equal to the size of alphabet in the given alien language. For example, if the alphabet size is 5, then there can be 5 characters in words. Initially there are no edges in graph.

         2) Do following for every pair of adjacent words in given sorted array.
         …..a) Let the current pair of words be word1 and word2. One by one compare characters of both words and find the first mismatching characters.
         …..b) Create an edge in g from mismatching character of word1 to that of word2.

         3) Print topological sorting of the above created graph.
        
         */
        
        /*
            Example 1:
        
            b  --> a  --> c
            #    /
            #   /
            d
        
            Topological order is b d a c
        */  
        Graph g = new Graph(alpha,true,false);//number of alphabets
        
        for(int i=0;i<arr.length-1;i++){
            String word1 =arr[i];
            String word2 = arr[i+1];//Get two adjacent words
            for(int j=0;j<Math.min(word1.length(), word2.length());j++){
                if(word1.charAt(j)!=word2.charAt(j)){
                    g.addEdge(word1.charAt(j)-'a', word2.charAt(j)-'a',0);//Using adjNode for topological sort..
                    break;//find the first mismatching character and add it to the edge
                }
            }
        }
        g.topologicalSort();
    
    }
    
    void kargerAlgorithmFindMinCut(MST graph){
        
        /*
        
         Given an undirected and unweighted graph, find the smallest cut (smallest number of edges that disconnects the graph into two components).
        
         A Simple Solution use Max-Flow based s-t cut algorithm to find minimum cut. 
         Consider every pair of vertices as source ‘s’ and sink ‘t’, and call minimum s-t cut algorithm to find the s-t cut. 
         Return minimum of all s-t cuts. Best possible time complexity of this algorithm is O(V5) for a graph. 
         [How? there are total possible V2 pairs and s-t cut algorithm for one pair takes O(V*E) time and E = O(V2)].
        
         Below Karger’s algorithm can be implemented in O(E) = O(V2) time.

         1)  Initialize contracted graph CG as copy of original graph
         2)  While there are more than 2 vertices.
         a) Pick a random edge (u, v) in the contracted graph.
         b) Merge (or contract) u and v into a single vertex (update 
         the contracted graph).
         c) Remove self-loops
         3) Return cut represented by two vertices. More info: refer http://www.geeksforgeeks.org/kargers-algorithm-for-minimum-cut-set-1-introduction-and-implementation/
        
        Applications of karger Algorithm find min cut
        
         1) In war situation, a party would be interested in finding minimum number of links that break communication network of enemy.

         2) The min-cut problem can be used to study reliability of a network (smallest number of edges that can fail).

         3) Study of network optimization (find a maximum flow).

         4) Clustering problems (edges like associations rules) Matching problems (an NC algorithm for min-cut in directed graphs would result in an NC algorithm for maximum matching in bipartite graphs)

         5) Matching problems (an NC algorithm for min-cut in directed graphs would result in an NC algorithm for maximum matching in bipartite graphs)
         */
        int V = graph.V;
        int E = graph.E;
        
        MST.subset subSets[] = new MST.subset[V];//Create a subset for V vertices
        
        for(int v=0;v<V;v++){//for v vertices initialize the parent to be itself
            subSets[v] = graph.new subset();
            subSets[v].parent=v;
            subSets[v].rank = 0;
        }
        
        int vertices = graph.V;//Initially there is no contracted vertices
        //flag to store the vertices contracted so far..
        Random r =new Random();
        while(vertices>2){
            
            int i = r.nextInt(E);//pick a random edge..
            System.out.println("Randomly picked vertex is "+i);
            // Find vertices (or sets) of two corners
            // of current edge
            int subset1 = graph.find(subSets, graph.edge[i].src);
            int subset2 = graph.find(subSets, graph.edge[i].dest);
            // If two corners belong to same subset,
             // then no point considering this edge
            if(subset1==subset2){
                continue;
            }else{
                // Else contract the edge (or combine the
                // corners of edge into one vertex)
                System.out.println("Contracting edge "+graph.edge[i].src+" -> "+graph.edge[i].dest);
                vertices--;
                graph.Union(subSets, subset1, subset2);
            }
        }
        
        // Now we have two vertices (or subsets) left in
    // the contracted graph, so count the edges between
    // two components and return the count.
        int cutEdges = 0;
        for(int i=0;i<E;i++){
            int subset1 = graph.find(subSets, graph.edge[i].src);
            int subset2 = graph.find(subSets, graph.edge[i].dest);
            
            if(subset1!=subset2){
                cutEdges++;
            }
        }
        System.out.println("Number of cut edges is -> "+cutEdges);
        
        
    }
    
    void kargerMinCutTestData(){

        int V = 4;  // Number of vertices in graph
        int E = 5;  // Number of edges in graph
        MST graph = new MST(V, E);
        // add edge 0-1
        
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        // add edge 0-2
        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        // add edge 0-3
        graph.edge[2].src = 0;
        graph.edge[2].dest = 3;
        // add edge 1-3
        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        // add edge 2-3
        graph.edge[4].src = 2;
        graph.edge[4].dest = 3;
        
        kargerAlgorithmFindMinCut(graph);
        

    }
    
    void canBeChainedTestData(){
        String[] arr = new String[]{"for", "geek", "rig", "kaf"};
        canBeChained(arr);
        arr = new String[]{"aab", "abb"};
        canBeChained(arr);
    }
    
    void findOrderOfCharsInLangTestData(){
        String words[] = new String[]{"caa", "aaa", "aab"};
        findOrderOfChars(words, 3);
        
    }
    
    void graphMiscTestData(){
//        canBeChainedTestData();
//        findOrderOfCharsInLangTestData();
        kargerMinCutTestData();
    }
}
