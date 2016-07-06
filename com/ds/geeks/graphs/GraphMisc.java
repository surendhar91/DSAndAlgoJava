/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

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
    static final int NIL = 0;
    static final int INF = Integer.MAX_VALUE;
    int biPartiteGraphMaxMatchingHopcroftkarp(Graph g,int m, int n){
        
        /*
        The idea is to use BFS (Breadth First Search) to find augmenting paths. Since BFS traverses level by level, it is used to divide the graph in layers of matching and not matching edges. 
        
        A dummy vertex NIL is added that is connected to all vertices on left side and all vertices on right side. 
        
        Following arrays are used to find augmenting path. Distance to NIL is initialized as INF (infinite). 
        
        If we start from dummy vertex and come back to it using alternating path of distinct vertices, then there is an augmenting path.

        pairU[]: An array of size m+1 where m is number of vertices on left side of Bipartite Graph. pairU[u] stores pair of u on right side if u is matched and NIL otherwise.
        pairV[]: An array of size n+1 where n is number of vertices on right side of Bipartite Graph. pairU[v] stores pair of v on left side if v is matched and NIL otherwise.
        dist[]: An array of size m+1 where m is number of vertices on left side of Bipartite Graph. dist[u] is initialized as 0 if u is not matching and INF (infinite) otherwise. dist[] of NIL is also initialized as INF
        
        Once an augmenting path is found, DFS (Depth First Search) is used to add augmenting paths to current matching. DFS simply follows the distance array setup by BFS. It fills values in pairU[u] and pairV[v] if v is next to u in BFS.
        
        
        */
    
        int[] pairU = new int[m+1];//To store the pair of U in matching where U vertex from left side to V right side
        int[] pairV = new int[n+1];//To store the pair of V from right side to U left side
        
        //
        int[] dist  = new int[m+1];//dist[]: An array of size m+1 where m is number of vertices on left side of Bipartite Graph. dist[u] is initialized as 0 if u is not matching and INF (infinite) otherwise. dist[] of NIL is also initialized as INF
        
        //initialize all the pair of vertices from u as nil
        for(int u=0;u<m;u++){
            pairU[u] = NIL;//if u doesn't have any pair, then pairU[u] is NIL
            //initialize everything to be a free vertex
        }
        
        for(int v=0;v<n;v++){
            pairV[v] = NIL;
        }
        
        int result = 0;//to store the number of maximum matching
        
        while(biPartitieBFS(g,m, pairU, pairV, dist)){//find augmenting path
            for(int u=1;u<=m;u++){
                //find a free vertex
                // If current vertex is free and there is
                // an augmenting path from current vertex
                if(pairU[u]==NIL && biPartiteDFS(g,u,pairU, pairV, dist)){
                    System.out.println("Vertex "+u+" contains augmenting path..");
                    result++;
                }
            }
        }
        System.out.println("End..");
        for(int u=1;u<=m;u++){
            System.out.println(u+" "+pairU[u]);
        }
        return result;
    }
    
    boolean biPartitieBFS(Graph g,int m,int pairU[], int pairV[],int dist[]){
        
        
        Queue<Integer> queue = new LinkedList<Integer>();
        //Queue will store only the vertices from left side of bipartite graph
        
        //First layer of vertices (set distance as 0)
        
        for(int u=1;u<=m;u++){
            //If this is a free vertex, add it to queue
            /*
                Free vertex: Given a matching M, a node that is not part of matching is called free node. Initially all vertices as free (See first graph of below diagram). In second graph, u2 and v2 are free. In third graph, no vertex is free.
            
            */
            if(pairU[u]==NIL){//If this node is not visited in BFS traversal (free node), add it to the queue, set the distance as zero
                dist[u] = 0;
                queue.add(u);
            }else{
                dist[u] = INF;//else, set the distance of u as infinite, so that this vertex is considered next time
            }
        }
        dist[NIL] = INF;//NIL node will be connected to all the vertices on left side.
        System.out.println("-----------Start of BFS----------");
        while(!queue.isEmpty()){
            int u = queue.poll();
            //If this node is not NIL and can provide a shorter path to NIL (dummy node)
            if(dist[u]<dist[NIL]){
                System.out.println("Vertex u "+u+" dist["+u+"] = "+dist[u]);
                for(Integer edge:g.arr[u]){
                    //get all adjacent vertices of the dequeued vertex u
                    int v = edge;
//                    System.out.print("\t edge "+v+" dist[pairV["+v+"]] = "+dist[pairV[v]]);
                    //if pair of v is not considered so far, (v,pairV[v]) is not yet explored edge..
                    if(dist[pairV[v]]==INF){
                        dist[pairV[v]] = dist[u]+1;
                        System.out.print("\t"+u+"-->"+v+" : pair "+pairV[v]);
                        System.out.print("\t dist["+u+"]"+dist[pairV[v]]);
                        queue.add(pairV[v]);
                    }
                }
            }
        }
        System.out.println("--------Completion of BFS----------dist[NIL]"+dist[NIL]);
        return dist[NIL]!=INF;//If we could come back to NIL using alternating path of distinct vertices then there is an augmenting path
                
    
    }
    
    private boolean biPartiteDFS(Graph g, int u,int pairU[], int pairV[],int dist[]) {
       //returns true if there is an augmenting path beginning with free vertex u
        
        if(u!=NIL){//not a zero vertex
            
            System.out.println("DFS Node U -> "+u+" dist[u] is "+dist[u]);
            for(Integer v:g.arr[u]){
                
                if(dist[pairV[v]]==dist[u]+1){//follow the distances set by BFS
                    
                    System.out.println(u+"->"+v+" pairV["+v+"] = "+pairV[v]+" dist[pairV[]]="+dist[pairV[v]]);
                    //if dfs for a pair of v also returns true
                    if(biPartiteDFS(g,pairV[v],pairU,pairV,dist)){
                        System.out.println("Edge "+u+" -> "+v+" is in augment path.");
                        pairV[v] = u;
                        pairU[u] = v;
                        return true;
                    }
                    
                }
            }
            dist[u] = INF;//marks that the node u is not a free node
            return false;
        }
        return true;
        
        
    }
    
    int shortestChainLength(String start, String target,Set<String> words){
        /*
        Given a dictionary, and two words ‘start’ and ‘target’ (both of same length). Find length of the smallest chain from ‘start’ to ‘target’ if it exists, such that adjacent words in the chain only differ by one character and each word in the chain is a valid word i.e., it exists in the dictionary. It may be assumed that the ‘target’ word exists in dictionary and length of all dictionary words is same.

         Example:

         Input:  Dictionary = {POON, PLEE, SAME, POIE, PLEA, PLIE, POIN}
         start = TOON
         target = PLEA
         Output: 7
         Explanation: TOON - POON - POIN - POIE - PLIE - PLEE - PLEA
        
        */
        //Use BFS traversal to find the shortest chain length to reach the target word
        
        //given a source, do the breadth first traversal
        boolean visited[] = new boolean[words.size()];
        
        for(int i=0;i<words.size();i++){
            visited[i] = false;
        }
        
        class QItem{
            String word;//to store the word for queue.
            int length;//shortest chain length to reach this word
        }
        
        QItem item = new QItem();
        item.word   = start;
        item.length = 1;
        LinkedList<QItem> queue = new LinkedList<QItem>();
        queue.add(item);//adding start vertex
        while(queue.size()!=0){
            QItem currNode = queue.poll();
            
            Set<String> clonedSet = new HashSet<String>(words);
            for(String word: clonedSet){
                if(words.contains(word)&&isAdjacentWord(currNode.word, word)){//Adjacent means that word1 and word2 differs by only one character
                    
                    item.word   = word;
                    item.length = currNode.length+1;
                    queue.add(item);
                    
                    words.remove(word);
                    
                    if(target.equals(item.word)){
                        return item.length;//Return the minimum length if the target is reached.
                    }
                    
                }
            }
            
        }
        return 0;
    
    }
    boolean isAdjacentWord(String word1, String word2){
        
        int count=0;
        for(int i=0;i<Math.min(word1.length(),word2.length());i++){
            if(word1.charAt(i)!=word2.charAt(i)){
                count++;
            }
            if(count>1)return false;
        }
        //if differs by only one character then return true else return false.
        return count==1?true:false;
        
    }

    private void buildGraph(Contact[] arr, int[][] mat) {
        
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                mat[i][j] = 0;//initialize
            }
        }
        for(int i=0;i<arr.length;i++){
            for(int j=i+1;j<arr.length;j++){
                if(arr[i].field1.equals(arr[j].field1)||
                     arr[i].field1.equals(arr[j].field2)||
                        arr[i].field1.equals(arr[j].field3)||
                        arr[i].field2.equals(arr[j].field1)||
                        arr[i].field2.equals(arr[j].field2)||
                        arr[i].field2.equals(arr[j].field3)||
                        arr[i].field3.equals(arr[j].field1)||
                        arr[i].field3.equals(arr[j].field2)||
                        arr[i].field3.equals(arr[j].field3)
                        )
                {
                    mat[i][j] = 1;
                    mat[j][i] = 1;
                    break;
                }
            }
        }
    }
    void DFSVisit(int vertex, int[][] mat, boolean[] visited, Stack solution){
        visited[vertex] = true;

        for(int j=0;j<mat.length;j++){
            if(!visited[j] && mat[vertex][j]==1){
                DFSVisit(j, mat, visited, solution);
            }
        }
            solution.push(vertex);

    }
    
    class Contact{
        String field1,field2,field3;
    }
    void findSameContacts(Contact[] arr,int n){
        
        // Finds similar contacrs in an array of contacts
        Stack<Integer> solution = new Stack<Integer>();
        
        int mat[][] = new int[arr.length][arr.length];
        
        buildGraph(arr,mat);
        
        boolean[] visited = new boolean[mat.length];
        
        for(int i=0;i<visited.length;i++){
            visited[i] = false;
        }
        // Since, we made a graph with contacts as nodes with fields as links.
    // two nodes are linked if they represent the same person.
    // so, total number of connected components and nodes in each component
    // will be our answer.
        for(int i=0;i<mat.length;i++){
            if(!visited[i]){
                DFSVisit(i, mat, visited, solution);
                solution.push(-1);//A delimited for the connected components
            }
        }
        
        while(!solution.isEmpty()){
            int elemt = solution.pop();
            if(elemt==-1)System.out.println("");
            else System.out.print(elemt+"\t");
        }
        
    
    }
    
    void findSameContactsTestData(){
    
        Contact arr[] = new Contact[6];
        for(int i=0;i<arr.length;i++){
            arr[i] = new Contact();
        }
        arr[0].field1 = "Gaurav";
        arr[0].field2 = "gaurav@gmail.com";
        arr[0].field3 = "gaurav@gfgQA.com";
                
        arr[1].field1 = "Lucky";
        arr[1].field2 = "lucky@gmail.com";
        arr[1].field3 = "+1234567";
        
        arr[2].field1 = "gaurav123";
        arr[2].field2 = "+5412312";
        arr[2].field3 = "gaurav123@skype.com";
        
        arr[3].field1 = "gaurav1993";
        arr[3].field2 = "+5412312";
        arr[3].field3 = "gaurav@gfgQA.com";
        
        arr[4].field1 = "raja";
        arr[4].field2 = "+2231210";
        arr[4].field3 = "raja@gfg.com";
        
        arr[5].field1 = "bahubali";
        arr[5].field2 = "+878312";
        arr[5].field3 = "raja";
        
        findSameContacts(arr, arr.length);
    }
   
    
    void shortestChainLengthTestData(){
        
        CopyOnWriteArraySet<String> D = new CopyOnWriteArraySet<String>();
        D.add("poon");
        D.add("plee");
        D.add("same");
        D.add("poie");
        D.add("plie");
        D.add("poin");
        D.add("plea");
        String start = "toon";
        String target = "plea";
        System.out.println("Shortest chain length for the given dictionary is "+shortestChainLength(start, target, D));
    
    }
    
    void hopCroftTestData(){
        Graph g = new Graph(4+1,true);//1 is for dummy node
        g.addEdge(1, 2);
        g.addEdge(1, 3);
//        g.addEdge(2, 1);
        g.addEdge(3, 2);
        g.addEdge(4, 2);
        g.addEdge(4, 4);
 
        System.out.println("Size of maximum matching is " +biPartiteGraphMaxMatchingHopcroftkarp(g,4, 4));
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
//        kargerMinCutTestData();
//        hopCroftTestData();
//        shortestChainLengthTestData();
//        findSameContactsTestData();
    }

    
}
