/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.graphs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author surendhar-2481
 */
public class Connectivity {
    
    boolean isReachableFromSrcToDest(Graph graph,int src, int dest){
        boolean visited[] = new boolean[graph.vertices];
        
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(src);
        visited[src] = true;
        while(!queue.isEmpty()){
            int node =queue.poll();
            
            List<Integer> adjNodes = graph.arr[node];
            
            for(Integer adjNode:adjNodes){
                if(adjNode==dest)
                    return true;
                if(!visited[adjNode]){
                    visited[adjNode] = true;
                    queue.add(adjNode);
                }
            }
            
        }
        return false;
    }
    Graph getTransclosure(Graph graph){
        Graph retGraph = new Graph(graph.vertices);
        for(int v=0;v<graph.vertices;v++){
            List<Integer> adjNodes = graph.arr[v];
            for(Integer node:adjNodes){
                retGraph.arr[node].add(v);//Reversing the graph
                //Get the adjacent node of graph and set that node's adjacency to v
                retGraph.in[v]++;//in degree of vertex v is incremented in the return graph
            }
        }
        return retGraph;
    }
    boolean isStronglyConnectedGraph(Graph graph){
        /*
        It is easy for undirected graph, we can just do a BFS and DFS starting from any vertex. If BFS or DFS visits all vertices, then the given undirected graph is connected. This approach won’t work for a directed graph. For example, consider the following graph which is not strongly connected. If we start DFS (or BFS) from vertex 0, we can reach all vertices, but if we start from any other vertex, we cannot reach all vertices.
        
        How to do for directed graph?
        A simple idea is to use a all pair shortest path algorithm like Floyd Warshall or find Transitive Closure of graph. Time complexity of this method would be O(v3).

        We can also do DFS V times starting from every vertex. If any DFS, doesn’t visit all vertices, then graph is not strongly connected. 
        This algorithm takes O(V*(V+E)) time which can be same as transitive closure for a dense graph.

        A better idea can be Strongly Connected Components (SCC) algorithm. We can find all SCCs in O(V+E) time. 
        If number of SCCs is one, then graph is strongly connected. The algorithm for SCC does extra work as it finds all SCCs.
        */
        boolean[] visited = new boolean[graph.vertices];
        /*
        1) Initialize all vertices as not visited.

        2) Do a DFS traversal of graph starting from any arbitrary vertex v. If DFS traversal doesn’t visit all vertices, then return false.

        3) Reverse all arcs (or find transpose or reverse of graph)

        4) Mark all vertices as not-visited in reversed graph.

        5) Do a DFS traversal of reversed graph starting from same vertex v (Same as step 2). If DFS traversal doesn’t visit all vertices, then return false. Otherwise return true.

        The idea is, if every node can be reached from a vertex v, and every node can reach v, then the graph is strongly connected. 
        In step 2, we check if all vertices are reachable from v. 
        In step 4, we check if all vertices can reach v (In reversed graph, if all vertices are reachable from v, then all vertices can reach v in original graph).
        */
        graph.DFSUtil(0, visited);
        
        graph = getTransclosure(graph);//Reverse the graph
        
        for(int i=0;i<graph.vertices;i++){
            visited[i] =false;
        }
        
        graph.DFSUtil(0, visited);//doing a dfs traversal on the reversed graph
        
        // If all vertices are not visited in second DFS, then
        // return false
        for(int i=0;i<graph.vertices;i++){
            if(!visited[i])
                return false;// if not visited.
        }
        return true;
    }
    void ArticulationPoints(Graph graph){
        /*
        The idea is to use DFS (Depth First Search). In DFS, we follow vertices in tree form called DFS tree. 
        In DFS tree, a vertex u is parent of another vertex v, if v is discovered by u (obviously v is an adjacent of u in graph). 
        In DFS tree, a vertex u is articulation point if one of the following two conditions is true.
        1) u is root of DFS tree and it has at least two children.
        2) u is not root of DFS tree and it has a child v such that no vertex in subtree rooted with v has a back edge to one of the ancestors (in DFS tree) of u.
        */
        boolean visited[] = new boolean[graph.vertices];
        boolean articPoint[] = new boolean[graph.vertices];//To store the result of articulation point
        int disc[] = new int[graph.vertices];//To store the discovery time of each vertex
        //For dfs traversal, calculate the discovery time of each non visited vertices, and use that for finding articulation point
        int low[] = new int[graph.vertices];
        int parent[] = new int[graph.vertices];//To store the parent vertex, while traversing.
        /*
        We maintain an array disc[] to store discovery time of vertices. 
        For every node u, we need to find out the earliest visited vertex (the vertex with minimum discovery time) that can be reached from subtree rooted with u. 
        So we maintain an additional array low[] which is defined as follows.

        low[u] = min(disc[u], disc[w]) 
        where w is an ancestor of u and there is a back edge from 
        some descendant of u to w.
        */
        
        //Example
        /*
        
                 a                          A
                /  \                       /
               b    |                     b
              /     |                    /
             c  ----                    C
                dis     low                     dis     low
             a  0        0              a       0       0
             b  1        0              b       1       1
             c  2        0              c       2       2
        */
        for(int i=0;i<graph.vertices;i++)
        {
            parent[i] = NIL;
            visited[i] = false;
            articPoint[i] = false;
        }
        
        for(int i=0;i<graph.vertices;i++){
            if(visited[i]==false){
                APUtil(i,graph,visited,disc,low,parent,articPoint);
            }
        }
        for(int i=0;i<graph.vertices;i++){
            if(articPoint[i]==true){
                System.out.print(i+" ");
            }
        }
    }
    
    boolean isBinaryConnectedGraph(Graph graph){
        //An undirected graph is called Biconnected if there are two vertex-disjoint paths between any two vertices. In a Biconnected Graph, there is a simple cycle through any two vertices.
        /*
            Graph is said to be Binary connected when the following is true
            1) Graph is connected [i.e. all vertices are visited]
            2) No articulation point in this graph. [Ensures, that there is a cycle between any two vertices]
        */
        /*
        We start from any vertex and do DFS traversal. In DFS traversal, we check if there is any articulation point. 
        If we don’t find any articulation point, then the graph is Biconnected. Finally, we need to check whether all vertices were reachable in DFS or not. 
        If all vertices were not reachable, then the graph is not even connected.
        */
        boolean visited[] = new boolean[graph.vertices];
        boolean articPoint[] = new boolean[graph.vertices];
        int disc[] = new int[graph.vertices];
        int low[] = new int[graph.vertices];
        int parent[] = new int[graph.vertices];
        for(int i=0;i<graph.vertices;i++)
        {
            parent[i] = NIL;
            visited[i] = false;
            articPoint[i] = false;
        }
        
        APUtil(0,graph,visited,disc,low,parent,articPoint);
        // Call the recursive helper function to find if there is an
        // articulation/ point in given graph. We do DFS traversal
        // starring from vertex 0
        for(int i=0;i<graph.vertices;i++){
            if(articPoint[i]==true){
                return false;
            }
        }
        for(int i=0;i<graph.vertices;i++){
            if(visited[i]==false){
                return false;
            }
        }
        return true;
    }
    void bridgeUtil(int u,Graph graph, boolean visited[],int disc[],int low[],int parent[]){
        
        visited[u] = true;
        
        int children = 0;
        
        disc[u]= low[u] = ++time;
        
        List<Integer> edges = graph.arr[u];
        for(Integer v:edges){
            
            if(!visited[v]){
                parent[v] = u;
                
                bridgeUtil(v,graph,visited,disc,low,parent);
                
                low[u] = Math.min(low[u], low[v]);
                
                if(low[v]>disc[u]){
                    //If there isn't any back edge, then the edge u-v is a bridge
                    System.out.println(u+" "+v);
                }
            }else if(v!=parent[u]){
                low[u] = Math.min(low[u], disc[v]);
            }
        }
        
    }
    void bridge(Graph graph){
//        An edge in an undirected connected graph is a bridge iff removing it disconnects the graph.
    /*
        Like Articulation Points,bridges represent vulnerabilities in a connected network and are useful for designing reliable networks. 
        For example, in a wired computer network, an articulation point indicates the critical computers and a bridge indicates the critical wires or connections.
        */
//        The idea is similar to O(V+E) algorithm for Articulation Points. We do DFS traversal of the given graph. 
        //In DFS tree an edge (u, v) (u is parent of v in DFS tree) is bridge if there does not exit any other alternative to reach u or an ancestor of u from subtree rooted with v. 
        //As discussed in the previous post, the value low[v] indicates earliest visited vertex reachable from subtree rooted with v. The condition for an edge (u, v) to be a bridge is, “low[v] > disc[u]”.

        
        //If there isn't a back edge exists for a vertex v and the child of v has no connection to the ancestor of v, then the edge is not a bridge
        boolean visited[] = new boolean[graph.vertices];
        int disc[] = new int[graph.vertices];
        int low[] = new int[graph.vertices];
        int parent[] = new int[graph.vertices];
        
        for(int i=0;i<graph.vertices;i++){
            parent[i]= NIL;
            visited[i] = false;
        }
        
        for(int i=0;i<graph.vertices;i++){
            if(visited[i]==false){
                bridgeUtil(i,graph,visited,disc,low,parent);
            }
        }
    }
    private void APUtil(int u, Graph graph, boolean[] visited, int[] disc, int[] low, int[] parent, boolean[] articPoint) {
        
        int children = 0;
        
        visited[u] = true;//mark this node as visited
        
        disc[u] = low[u] = time++;
        
        
        List<Integer> edges = graph.arr[u];
        for(Integer v:edges){//For each of the adjacent node, if not visited
            if(!visited[v]){
                
                children++;
                
                parent[v] = u;
                
                APUtil(v,graph,visited,disc,low,parent,articPoint);
                
                low[u] = Math.min(low[u], low[v]);//v is already traversed, it would have the low value (i.e. it would have already determined, if it has a back edge or not
                //u is an articulation point in the following cases.
                if(parent[u]==NIL && children > 1){
                    //Case 1: if it is a root node, and has more than one child, then it is a articulate point
                    articPoint[u] = true;
                }
                if(parent[u]!=NIL && low[v]>=disc[u]){
                //If non root node, and not back edge formed from subtree to the ancestor of u, then it is a articulate point
                    articPoint[u] = true;
                }
                
           }else if(v!=parent[u]){//for the c->a in the given example,
               //c being the u, a being the v, in DFS Traversal
               //V is not the parent, but an ancestor, then
               low[u] = Math.min(low[u], disc[v]);//If there is a back edge, then the low value of current node, would point to the discovery time of vertex v(which exists in the ancestor of u)
           }
        }
    }
            
    // Method to check if all non-zero degree vertices are
    // connected. It mainly does DFS traversal starting from
    boolean isNonZeroConnected(Graph graph){
        
        boolean[] visited = new boolean[graph.vertices];
        
        for(int i=0;i<graph.vertices;i++){
            visited[i] = false;
        }
        int i;
        //find a vertex with non zero degree
        for(i=0;i<graph.vertices;i++){
            if(graph.arr[i].size()!=0){
                break;
            }
        }
        if(i==graph.vertices){//if all the vertices are zero vertex, then return true
            return true;//eulierian cycle
        }
        
        graph.DFSUtil(i, visited);//Traverse dfs from vertex i (should be a non zero vertex)
        
        //few edges, may be of zero degree
        for(i=0;i<graph.vertices;i++){
            if(visited[i]==false && graph.arr[i].size()>0){
                //Non zero degree vertex, if not visited, then it's not connected
                return false;
            }
        }
        return true;
    }
    
    boolean isNonZeroStronglyConnected(Graph graph){
        
         boolean[] visited = new boolean[graph.vertices];
        
        for(int i=0;i<graph.vertices;i++){
            visited[i] = false;
        }
        int i;
        //find a vertex with non zero degree
        for(i=0;i<graph.vertices;i++){
            if(graph.arr[i].size()!=0){
                break;
            }
        }
        if(i==graph.vertices){//if all the vertices are zero vertex, then return true
            return true;//eulierian cycle
        }
        
        graph.DFSUtil(i, visited);//Traverse dfs from vertex i (should be a non zero vertex)
        
        //few edges, may be of zero degree
        for(int j=0;j<graph.vertices;j++){
            if(visited[j]==false && graph.arr[j].size()>0){
                //Non zero degree vertex, if not visited, then it's not connected
                return false;
            }
        }
        graph = getTransclosure(graph);
        
        for(int j=0;j<graph.vertices;j++){
            visited[j] = false;
        }
        
        graph.DFSUtil(i, visited);
        
        //few edges, may be of zero degree
        for(int j=0;j<graph.vertices;j++){
            if(visited[j]==false && graph.arr[j].size()>0){
                //Non zero degree vertex, if not visited, then it's not connected
                return false;
            }
        }
        
        return true;
        
    }
    int isEulerian(Graph graph){//For undirected graph
        
        /*
        Eulerian Path is a path in graph that uses every edge of a graph exactly once. Eulerian Circuit is an Eulerian Path which starts and ends on the same vertex.
        
        Eulerian Cycle
            An undirected graph has Eulerian cycle if following two conditions are true.
            ….a) All vertices with non-zero degree are connected. We don’t care about vertices with zero degree because they don’t belong to Eulerian Cycle or Path (we only consider all edges).
            ….b) All vertices have even degree.

        Eulerian Path
            An undirected graph has Eulerian Path if following two conditions are true.
            ….a) Same as condition (a) for Eulerian Cycle
            ….b) If zero or two vertices have odd degree and all other vertices have even degree. Note that only one vertex with odd degree is not possible in an undirected graph (sum of all degrees is always even in an undirected graph)

            Note that a graph with no edges is considered Eulerian because there are no edges to traverse.
        
        
        A graph is called Eulerian if it has an Eulerian Cycle and called Semi-Eulerian if it has an Eulerian Path. 
        
        */
        //Point a
        if(!isNonZeroConnected(graph)){
            return 0;//not an eulerian graph
        }   
        //finding the number of vertices with odd degree
        int odd=0;
        for(int i=0;i<graph.vertices;i++){
            if(graph.arr[i].size()%2!=0){
                //if odd degree
                odd++;
            }
        }
        if(odd>2){
            //If more than two vertices found with odd degree, then not an eulerian graph
            return 0;
        }
        return (odd==2)?1:2;
        //If odd is zero -> return 1 (All vertices have even degree) - Eulerian cycle
        //If odd is two -> return 2 (Two vertices have odd degree ) - Contains eulerian path - Eulerian graph, but semi eulerian
        //odd cannot be 1 -> For undirected graph, only one vertex with odd degree is not possible
        
    }

    private void eulerianTest(Graph g1) {
        int res = isEulerian(g1);
        if (res == 0)
            System.out.println("graph is not Eulerian");
        else if (res == 1)
            System.out.println("graph has a Euler path");
        else
           System.out.println("graph has a Euler cycle");
    }
    
    void printEulerTour(Graph graph){
        //CopyToWriteArrayList is imporant, allows concurrent modification.
        /*
            Following is Fleury’s Algorithm for printing Eulerian trail or cycle.
                1. Make sure the graph has either 0 or 2 odd vertices.
                2. If there are 0 odd vertices, start anywhere. If there are 2 odd vertices, start at one of them.
                3. Follow edges one at a time. If you have a choice between a bridge and a non-bridge, always choose the non-bridge.
                4. Stop when you run out of edges.
        */
        int oddVertex=0;
        
        for(int i=0;i<graph.vertices;i++){
            
            if((graph.arr[i].size()&1)>0){
                oddVertex = i;
                break;
            }
        }
        //vertex i -> using first odd vertex, print euler path.
        System.out.println("Odd vertex adj list "+graph.arr[oddVertex]);
        printEulerUtil(graph,oddVertex);
        System.out.println("");

    
    }

    private void printEulerUtil(Graph graph,int u) {
        //VERY IMPORTANT: Notice that CopyOnWriteArrayList allows the modification of list, but it doesn’t change the iterator and we get same elements as it was on original list.
//        System.out.println("Node "+u);
//        if(u<graph.arr.length){
//                System.out.println("Processing edge " + u);
                for(Integer v:graph.arr[u]){
                    if (graph.arr[u].contains(v) && isValidEulerEdge(graph, u, v)) {//Refer very important note, first condition is important, checks if the element is not removed
                        //as the list would have been modified in removeEdge method call
                        
                        System.out.print(u + " " + v+" ");
                        graph.removeEdge(u, v);
                        printEulerUtil(graph, v);//traverse dfs to print the euler path
                    }
                }
                //Follow edges one at a time,

//        }
        
    }
    //Beware, calling this method -> remodifies the adjacent list
    private boolean isValidEulerEdge(Graph graph, int u, Integer v) {
        
        // The edge u-v is valid in one of the following two cases:
        // 1) If v is the only adjacent vertex of u
        int count = 0;
        for(Integer nodes:graph.arr[u]){
            count++;
        }
        if(count==1){
            return true;
        }
        
        return isEdgeBridge(graph,u,v);
    }

    private boolean isEdgeBridge(Graph graph, int u, Integer v) {
        
        boolean visited[] = new boolean[graph.vertices];
        
        for(int i=0;i<graph.vertices;i++){
            visited[i] = false;
        }
        int countBeforeRemoval = graph.DFSCount(u, visited);
        
        graph.removeEdge(u, v);
        
        for(int i=0;i<graph.vertices;i++){
            visited[i] = false;
        }
        int countAfterRemoval  = graph.DFSCount(u, visited);
        
        graph.addEdge(u, v);
        
        return (countBeforeRemoval>countAfterRemoval)?false:true;//important
        //count before removing the edge -> ensures that all the components are connected
        //count after removing the edge -> if the edge is a bridge, the connected components would have been reduced.
    }
    void printStronglyConnectedComponents(Graph graph){
        /*
            The above algorithm calls DFS, fins reverse of the graph and again calls DFS. 
        
            DFS takes O(V+E) for a graph represented using adjacency list. Reversing a graph also takes O(V+E) time. For reversing the graph, we simple traverse all adjacency lists.
        */
        //Find the strongly connected components of a graph
        
        //A directed graph is strongly connected if there is a path between all pairs of vertices. A strongly connected component (SCC) of a directed graph is a maximal strongly connected subgraph. For example, there are 3 SCCs in the following graph.
        
        
        /*
        
            Example
        
                1-->  0 --> 3
                ^    /      |
                |   /       *
                   *        4
                2
        
                Here, (0,1,2) (3), (4) are strongly connected components.
                
        */
        
        /*
        
        We can find all strongly connected components in O(V+E) time using Kosaraju’s algorithm. Following is detailed Kosaraju’s algorithm.
            1) Create an empty stack ‘S’ and do DFS traversal of a graph. In DFS traversal, after calling recursive DFS for adjacent vertices of a vertex, push the vertex to stack.
                In the above graph, if we start DFS from vertex 0, we get vertices in stack as 1, 2, 4, 3, 0.
            2) Reverse directions of all arcs to obtain the transpose graph.
            3) One by one pop a vertex from S while S is not empty. Let the popped vertex be ‘v’. Take v as source and do DFS (call DFSUtil(v)). 
                The DFS starting from v prints strongly connected component of v. In the above example, we process vertices in order 0, 3, 4, 2, 1 (One by one popped from stack).
        */
        
        /*
            How does this work ?
        
            1) DFS of a graph produces a single tree if all vertices are reachable from the DFS starting point
                - Otherwise, It would produce a forest. In other words, DFS of a SCC graph will always produce a tree.
        
            2) Important point to note is that chosing starting point also affects the production of tree or forest.
               Example, in the above diagram, if we start DFS from vertices 0 or 1 or 2, we get a tree as output. And if we start from 3 or 4, we get a forest.
        
            3) So, to print all SCCs, we would want to start from vertex 4 ( Sink vertex ), then move to 3 which is sink in the remaining set (set excluding 4) and finally any
               of the remaining vertices
               
            4) How do we find this sequence of picking vertices as starting points of DFS?
        
                --> Unfortunately, there is no direct way for getting this sequence. However, if we do a DFS of graph and store vertices according to their finish times, 
                we make sure that the finish time of a vertex that connects to other SCCs (other that its own SCC), 
                will always be greater than finish time of vertices in the other SCC (See this for proof). 
                For example, in DFS of above example graph, finish time of 0 is always greater than 3 and 4 (irrespective of the sequence of vertices considered for DFS). 
                And finish time of 3 is always greater than 4. DFS doesn’t guarantee about other vertices, 
                for example finish times of 1 and 2 may be smaller or greater than 3 and 4 depending upon the sequence of vertices considered for DFS.
        
        
            5) So to use this property, we do DFS traversal of complete graph and push every finished vertex to a stack. In stack, 3 always appears after 4, and 0 appear after both 3 and 4.
In the      In the next step, we reverse the graph. Consider the graph of SCCs. In the reversed graph, the edges that connect two components are reversed. 
            So the SCC {0, 1, 2} becomes sink and the SCC {4} becomes source. As discussed above, in stack, we always have 0 before 3 and 4. 
            So if we do a DFS of the reversed graph using sequence of vertices in stack, we process vertices from sink to source (in reversed graph). 
            That is what we wanted to achieve and that is all needed to print SCCs one by one.
        */
        Stack<Integer> stack = new Stack<Integer>();
        
        boolean[] visited = new boolean[graph.vertices];
        
        for(int i=0;i<graph.vertices;i++){
            visited[i] = false;
        }
        for(int i=0;i<graph.vertices;i++){
            if(visited[i]==false){
                graph.DFSUtilPushStack(i, visited, stack);
            }
        }
        graph = getTransclosure(graph);// reverse the graph
        
        for(int i=0;i<graph.vertices;i++){
            visited[i] = false;
        }
        
        while(!stack.isEmpty())
        {
            int v = stack.pop();
            
            if(visited[v]==false){//if already visited don't traverse again
                graph.DFSUtil(v, visited);
                System.out.println("");
            }
        }
    }
    void findReachabilityTransclosureMatrix(int[][] graph){
        
         int V = graph.length;
         int[][] reach = new int[V][V];
         
         int i,j,k;
         //Using floyd warshall algorithm, optimized - to find the reachability matrix
         
         for(i=0;i<V;i++){
             for(j=0;j<V;j++){
                 reach[i][j] = graph[i][j];//initialize the reachability matrix same as the input graph matrix
                 //Or we can say, the initial values of reachability matrix are based on shortest paths considering no intermediate vertex
             }
         }
         
         for(k=0;k<V;k++){
            for (i = 0; i < V; i++) {
                for (j = 0; j < V; j++) {
                    reach[i][j] = (reach[i][j]!=0) || //if already reachable in the input matrix don't change
                                   (reach[i][k]!=0&&reach[k][j]!=0) //If j is reachable through k, then set the value to boolean.
                                    ?1:0;
                }
            }
        }
         System.out.println("Following matrix is transitive closure"+
                           " of the given graph");
        for (i = 0; i < V; i++)
        {
            for (j = 0; j < V; j++)
                System.out.print(reach[i][j]+" ");
            System.out.println();
        }
    }

    private void findReachabilityUsingDFS(Graph g) {
        
        int V = g.vertices;
        boolean[][] reach = new boolean[V][V];
        
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                reach[i][j] = false;
            }
        }
        
        for(int i=0;i<V;i++){
            //every vertex is reachable to self
            reachabilityDFSUtil(i, i, g, reach);
        }
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                System.out.print(reach[i][j]+" ");
            }
            System.out.println("");
        }
    }
    private void reachabilityDFSUtil(int s, int v,Graph g, boolean[][] reach){
        reach[s][v] = true;
        //Find all the vertices reachable through v
        List<Integer> adjNodes = g.arr[v];
        for(Integer edge:adjNodes){
            if(!reach[s][edge]){//from the src to edge node is not reachable, then recurse
                reachabilityDFSUtil(s, edge, g, reach);//Traverse DFS from src to edge node.
            }
        }
        
    }
    
    int countIslands(int[][] M){
        /*
            
        This is an variation of the standard problem: “Counting number of connected components in a undirected graph”.

        Before we go to the problem, let us understand what is a connected component. 
        A connected component of an undirected graph is a subgraph in which every two vertices are connected to each other by a path(s), and which is connected to no other vertices outside the subgraph.
        
        A group of connected 1s forms an island. For example, the below matrix contains 5 islands

	                {1, 1, 0, 0, 0},
                        {0, 1, 0, 0, 1},
                        {1, 0, 0, 1, 1},
                        {0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1}
A cell in 2D matrix can be connected to 8 neighbors. So, unlike standard DFS(), where we recursively call for all adjacent vertices, here we can recursive call for 8 neighbors only. We keep track of the visited 1s so that they are not visited again.
        
        */
        
        int V = M.length;
        int count = 0;
        boolean[][] visited = new boolean[V][V];
        
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                visited[i][j] = false;
            }
        }
        
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                if(M[i][j]==1&&!visited[i][j]){//If this is a new vertex, not yet visited, then increment the island count
                    //and recursive for the neighbors.
                    DFSNeighbor(M,i,j,visited);
                    count++;
                }
            }
        }
        return count;
    }
    boolean isSafeToTraverse(int[][] M,int row,int col,boolean[][] visited){
    
        int V = M.length;
        return row>=0 && row<V && col >=0 && col < V && M[row][col]==1 && !visited[row][col]; //Need to have edge from row to col and not visited, then it's safe to traverse
        
    }
    void DFSNeighbor(int[][] M,int row,int col,boolean[][] visited){
    
        int[] rowNbr = new int[]{-1,-1,-1,0,0,1,1,1};//8 neighbors in a matrix
        int[] colNbr = new int[]{-1,0,1,-1,1,-1,0,1};
        
        visited[row][col] = true;
        for(int i=0;i<8;i++){
            if(isSafeToTraverse(M,row+rowNbr[i],col+colNbr[i],visited)){//Recur for all connected neighbors
                DFSNeighbor(M, row+rowNbr[i], col+colNbr[i], visited);
            }
        }
    }
    boolean isEulerianCycleInDirected(Graph graph, boolean nonZeroVertex){
        /*
        How to check if a directed graph is eulerian?
         A directed graph has an eulerian cycle if following conditions are true (Source: Wiki)
         1) All vertices with nonzero degree belong to a single strongly connected component (A subgraph in which there is a path between all pairs of vertices)
         2) In degree and out degree of every vertex is same.
        
        To compare in degree and out degree, we need to store in degree an out degree of every vertex. Out degree can be obtained by size of adjacency list. In degree can be stored by creating an array of size equal to number of vertices.
        */
        if(!nonZeroVertex){
            if (!isStronglyConnectedGraph(graph)) {//considers that the given graph is a non zero vertex
//            System.out.println("");
                return false;
            }
        }else{
            if(!isNonZeroStronglyConnected(graph)){
                return false;
            }
        }
        for(int i=0;i<graph.vertices;i++){
            if(graph.arr[i].size()!=graph.in[i]){
                return false;
            }
        }
            
        return true;
    
    }
    int bccCount = 0;
    void BCCUtil(Graph g, int u, int disc[], int low[], Stack<MST.Edge> st, int parent[],boolean visited[]){
        
        //Example
        
        /*
                4
              /   \
            2 ----- 3
             \     /
                1
                |
                0
        
              disc   low (after traversing)
           0    0     1
           1    1     2
           2    2     2
           3    3     2
           4    4 
        
        */
        
        
        // A recursive function that finds and prints strongly connected
    // components using DFS traversal
    // u --> The vertex to be visited next
    // disc[] --> Stores discovery times of visited vertices
    // low[] -- >> earliest visited vertex (the vertex with minimum
    //             discovery time) that can be reached from subtree
    //             rooted with current vertex
    // *st -- >> To store visited edges
        int children = 0;
        
        visited[u] = true;//mark this node as visited
        
        disc[u] = low[u] = ++time;//Pre increment is important.
        
        List<Integer> edges = g.arr[u];
        for(Integer v:edges){//For each of the adjacent node, if not visited
//            System.out.println("vertex "+v);
            if(!visited[v]){//If at all not discovered / visited
                
                children++;
                
                parent[v] = u;
                
//                APUtil(v,graph,visited,disc,low,parent,articPoint);
                st.add(new MST().new Edge(u,v)); //src to dest add edge
                BCCUtil(g, v, disc, low, st, parent, visited);
                
                low[u] = Math.min(low[u], low[v]);//v is already traversed, it would have the low value (i.e. it would have already determined, if it has a back edge or not
                //u is an articulation point in the following cases.
                if((parent[u]==NIL && children > 1) || (parent[u]!=NIL && low[v]>=disc[u])){
                    //Case 1: if it is a root node, and has more than one child, then it is a articulate point
                    //If non root node, and not back edge formed from subtree to the ancestor of u, then it is a articulate point
                    System.out.println("Processed Vertex, on finding articulation point "+v);
                    System.out.println("Articulation point "+u);
                    System.out.println("Stack Elements"+st);
                    while(st.peek().src!=u || st.peek().dest!=v){
                        System.out.print(st.peek().src+"--"+st.peek().dest+" ");
                        st.pop();
                    }
                    //print the edge that we are in //u--v
                    System.out.println(st.peek().src+"--"+st.peek().dest+" ");
                    st.pop();
                    bccCount++;//Increment the number of binary connected component

                }
                
           }else if(v!=parent[u] && disc[v]<low[u]){
                System.out.println("Src "+u+" Dest v "+v+" visited? "+visited[v]);
                 System.out.println("Disc of v "+disc[v]+
                         "Low of u "+low[u]);
               //for the c->a in the given example,
               //c being the u, a being the v, in DFS Traversal
               //V is not the parent, but an ancestor, then
               low[u] = Math.min(low[u], disc[v]);//If there is a back edge, then the low value of current node, would point to the discovery time of vertex v(which exists in the ancestor of u)
               st.add(new MST().new Edge(u,v));
               
           }
        }
    }
    void BCC(Graph g)
    {
        int disc[] = new int[g.vertices];
        int low[] = new int[g.vertices];
        int parent[] = new int[g.vertices];
        boolean visited[] = new boolean[g.vertices];
        Stack<MST.Edge> st = new Stack<MST.Edge>();
 
        // Initialize disc and low, and parent arrays
        for (int i = 0; i < g.vertices; i++)
        {
            disc[i] = -1;
            low[i] = -1;
            parent[i] = -1;
            visited[i] = false;
        }
 
        for (int i = 0; i < g.vertices; i++)
        {
            if (disc[i]==-1)//If there is any disconnected sub graph, for that, this method will be called.
                BCCUtil(g,i, disc, low, st, parent,visited);
 
            int j = 0;
 
            // If stack is not empty, pop all edges from stack
            if(st.size()>0){
                System.out.println("popping all edges from the stack..");
            }
            while (st.size() > 0)
            {
                j = 1;
                System.out.print(st.peek().src + "--" +
                                 st.peek().dest + " ");
                st.pop();
            }
            if (j == 1)
            {
                System.out.println();
                bccCount++;//For the sub graph, increment the bccCount
            }
        }
    }
    void sccUtil(Graph g, int u,int disc[],int low[],Stack st){
      
        disc[u] = low[u] = ++time;
        
        st.add(u);
        
        List<Integer> edges = g.arr[u];
        
        for(Integer v:edges){//For each of the adjacent node, if not visited
            
            if(disc[v]==-1){
                //If not visited, then recursively call sccUtil
                sccUtil(g,v,disc,low,st);
                low[u] = Math.min(low[u], low[v]);//check if the subtree rooted with 'v' has a connection to one of the ancestors of 'u'
            }
            else if(st.contains(v)){//If it's already visited, ensure that the edge being traversed is not a cross edge.
                //It is a back edge, when the vertex v is contained in Stack
                low[u] = Math.min(low[u],disc[v]);//Same as the one for AP Util
             
           }
        }
        //Now that the elements are traversed, we need to print the strongly connected component
        //for this, we need to find the head node of the sub graph which is strongly connected component. 
        // if low[u] == disc[u] then it's the head node
        if(low[u]==disc[u]){
            while((Integer)st.peek()!=u){
                System.out.print(st.peek()+" ");
                st.pop();
            }
            System.out.print(st.peek()+" ");
            System.out.println("");
            st.pop();
        }
        
    }
    void printStronglyConnectedGraphUsingLowDisc(Graph g){
        int disc[] = new int[g.vertices];
        int low[] = new int[g.vertices];
        Stack stack = new Stack();
        
        for(int i=0;i<g.vertices;i++){
            disc[i] = -1;
            low[i] = -1;
        }
        for(int i=0;i<g.vertices;i++){
            if(disc[i]==-1){
                sccUtil(g, i, disc, low, stack);
            }
        }
    }
    void bccTestData(){
    
        Graph g = new Graph(12);
        g.addEdge(0,1);
        g.addEdge(1,0);
        g.addEdge(1,2);
        g.addEdge(2,1);
        g.addEdge(1,3);
        g.addEdge(3,1);
        g.addEdge(2,3);
        g.addEdge(3,2);
        g.addEdge(2,4);
        g.addEdge(4,2);
        g.addEdge(3,4);
        g.addEdge(4,3);
        g.addEdge(1,5);
        g.addEdge(5,1);
        g.addEdge(0,6);
        g.addEdge(6,0);
        g.addEdge(5,6);
        g.addEdge(6,5);
        g.addEdge(5,7);
        g.addEdge(7,5);
        g.addEdge(5,8);
        g.addEdge(8,5);
        g.addEdge(7,8);
        g.addEdge(8,7);
        g.addEdge(8,9);
        g.addEdge(9,8);
        g.addEdge(10,11);
        g.addEdge(11,10);
 
        BCC(g);
 
        System.out.println("Above are " + bccCount +
                           " biconnected components in graph");
    }
    
    void printSccTestDataUsingDiscAndLow(){
        Graph g1 = new Graph(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        System.out.println("Sccs in first graph");
        printStronglyConnectedGraphUsingLowDisc(g1);
        Graph g2 = new Graph
        (4);
    g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        System.out.println("Sccs in second graph");

        printStronglyConnectedGraphUsingLowDisc(g2);
        Graph g3 = new Graph
        (7);
    g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        System.out.println("Sccs in third graph");
        printStronglyConnectedGraphUsingLowDisc(g3);
        Graph g4 = new Graph
        (11);
        g4.addEdge(0, 1);
        g4.addEdge(0, 3);
        g4.addEdge(1, 2);
        g4.addEdge(1, 4);
        g4.addEdge(2, 0);
        g4.addEdge(2, 6);
        g4.addEdge(3, 2);
        g4.addEdge(4, 5);
        g4.addEdge(4, 6);
        g4.addEdge(5, 6);
        g4.addEdge(5, 7);
        g4.addEdge(5, 8);
        g4.addEdge(5, 9);
        g4.addEdge(6, 4);
        g4.addEdge(7, 9);
        g4.addEdge(8, 9);
        g4.addEdge(9, 8);
        System.out.println("Sccs in fourth graph");
        printStronglyConnectedGraphUsingLowDisc(g4);
        Graph g5=new Graph
        (5);
    g5.addEdge(0, 1);
        g5.addEdge(1, 2);
        g5.addEdge(2, 3);
        g5.addEdge(2, 4);
        g5.addEdge(3, 0);
        g5.addEdge(4, 2);
        System.out.println("Sccs in fifth graph");
        printStronglyConnectedGraphUsingLowDisc(g5);

    }
    static final int NIL = -1;
    int time = 0;
    void isReachableFromSrcToDestTestData(){
        // Create a graph given in the above diagram
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
 
        int u = 1;
        int v = 3;
        if (new Connectivity().isReachableFromSrcToDest(g,u, v))
            System.out.println("There is a path from " + u +" to " + v);
        else
            System.out.println("There is no path from " + u +" to " + v);;
 
        u = 3;
        v = 1;
        if (new Connectivity().isReachableFromSrcToDest(g,u, v))
            System.out.println("There is a path from " + u +" to " + v);
        else
            System.out.println("There is no path from " + u +" to " + v);;
    }
    void stronglyConnectedGraphTestData(){
     // Time complexity of above implementation is sane as Depth First Search which is O(V+E) if the graph is represented using adjacency matrix representation.
        // Create graphs given in the above diagrams
        System.out.println("Strongly connected graph..");
        Graph g1 = new Graph(5);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2, 3);
        g1.addEdge(3, 0);
        g1.addEdge(2, 4);
        g1.addEdge(4, 2);
        if (this.isStronglyConnectedGraph(g1))
            System.out.println("Yes");
        else
            System.out.println("No");
 
        Graph g2 = new Graph(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        if (this.isStronglyConnectedGraph(g2))
            System.out.println("Yes");
        else
            System.out.println("No");
    }
    void articulationPointTestData(){
        System.out.println("Articulation points in first graph ");
        Graph g1 = new Graph(5,true);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        ArticulationPoints(g1);
        System.out.println();
 
        System.out.println("Articulation points in Second graph");
        Graph g2 = new Graph(4,true);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        ArticulationPoints(g2);
        System.out.println();
 
        System.out.println("Articulation points in Third graph ");
        Graph g3 = new Graph(7,true);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        ArticulationPoints(g3);
    }
    void binaryConnectedGraphTestData(){
        //Time Complexity: The above function is a simple DFS with additional arrays. So time complexity is same as DFS which is O(V+E) for adjacency list representation of graph.


        // Create graphs given in above diagrams
        Graph g1 =new Graph(2,true);
        g1.addEdge(0, 1);
        if (isBinaryConnectedGraph(g1))
            System.out.println("Yes");
        else
            System.out.println("No");
 
        Graph g2 =new Graph(5,true);
        g2.addEdge(1, 0);
        g2.addEdge(0, 2);
        g2.addEdge(2, 1);
        g2.addEdge(0, 3);
        g2.addEdge(3, 4);
        g2.addEdge(2, 4);
        if (isBinaryConnectedGraph(g2))
            System.out.println("Yes");
        else
            System.out.println("No");
 
        Graph g3 = new Graph(3,true);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        if (isBinaryConnectedGraph(g3))
            System.out.println("Yes");
        else
            System.out.println("No");
 
        Graph g4 = new Graph(5,true);
        g4.addEdge(1, 0);
        g4.addEdge(0, 2);
        g4.addEdge(2, 1);
        g4.addEdge(0, 3);
        g4.addEdge(3, 4);
        if (isBinaryConnectedGraph(g4))
            System.out.println("Yes");
        else
            System.out.println("No");
 
        Graph g5= new Graph(3,true);
        g5.addEdge(0, 1);
        g5.addEdge(1, 2);
        g5.addEdge(2, 0);
        if (isBinaryConnectedGraph(g5))
            System.out.println("Yes");
        else
            System.out.println("No");
    }
    void bridgeInGraphTestData(){
    
        // Create graphs given in above diagrams
        System.out.println("Bridges in first graph ");
        Graph g1 = new Graph(5,true);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        bridge(g1);
        System.out.println();
 
        System.out.println("Bridges in Second graph");
        Graph g2 = new Graph(4,true);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        bridge(g2);
        System.out.println();
 
        System.out.println("Bridges in Third graph ");
        Graph g3 = new Graph(7,true);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        bridge(g3);
    }
    void EulerianTestData(){
        // Let us create and test graphs shown in above figures
        Graph g1 = new Graph(5,true);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        eulerianTest(g1);
 
        Graph g2 = new Graph(5,true);
        g2.addEdge(1, 0);
        g2.addEdge(0, 2);
        g2.addEdge(2, 1);
        g2.addEdge(0, 3);
        g2.addEdge(3, 4);
        g2.addEdge(4, 0);
        eulerianTest(g2);
        
        Graph g3 = new Graph(5,true);
        g3.addEdge(1, 0);
        g3.addEdge(0, 2);
        g3.addEdge(2, 1);
        g3.addEdge(0, 3);
        g3.addEdge(3, 4);
        g3.addEdge(1, 3);
        eulerianTest(g3);
 
        // Let us create a graph with 3 vertices
        // connected in the form of cycle
        Graph g4 = new Graph(3,true);
        g4.addEdge(0, 1);
        g4.addEdge(1, 2);
        g4.addEdge(2, 0);
        eulerianTest(g4);
 
        // Let us create a graph with all veritces
        // with zero degree
        Graph g5 = new Graph(3,true);
        eulerianTest(g5);
        
        Graph g = new Graph(5,false);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 0);
  //O(V + E)
        if (isEulerianCycleInDirected(g,false))
            System.out.println("Given directed graph is eulerian ");
        else
            System.out.println("Given directed graph is NOT eulerian ");
    }
    
    void printEulerTestData(){
        Graph g1 = new Graph(4,true,"concurrent");
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(2, 3);
        printEulerTour(g1);

        Graph g2 = new Graph(3,true,"concurrent");
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 0);
        printEulerTour(g2);

        Graph g3 = new Graph(5,true,"concurrent");
        g3.addEdge(1, 0);
        g3.addEdge(0, 2);
        g3.addEdge(2, 1);
        g3.addEdge(0, 3);
        g3.addEdge(3, 4);
        g3.addEdge(3, 2);
        g3.addEdge(3, 1);
        g3.addEdge(2, 4);
        printEulerTour(g3);
    }
    void printSccTestData()
    {
        // Create a graph given in the above diagram
        Graph g = new Graph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);
 
        System.out.println("Following are strongly connected components "+
                           "in given graph ");
        printStronglyConnectedComponents(g);
    }
    
    void printReachabilityTransclosureTestData(){
        /* Let us create the following weighted graph
           10
        (0)------->(3)
        |         /|\
      5 |          |
        |          | 1
        \|/        |
        (1)------->(2)
           3           */
 
        /* Let us create the following weighted graph
 
              10
         (0)------->(3)
          |         /|\
        5 |          |
          |          | 1
         \|/         |
         (1)------->(2)
            3           */
         int graph[][] = new int[][]{ {1, 1, 0, 1},
                                      {0, 1, 1, 0},
                                      {0, 0, 1, 1},
                                      {0, 0, 0, 1}
                                    };
 
         // Print the solution
         //Time Complexity: O(V3) where V is number of vertices in the given graph.
         findReachabilityTransclosureMatrix(graph);
         
         Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
        findReachabilityUsingDFS(g);
    }
    void countIslandsTestData(){
        int M[][]=  new int[][] {{1, 1, 0, 0, 0},
                                 {0, 1, 0, 0, 1},
                                 {1, 0, 0, 1, 1},
                                 {0, 0, 0, 0, 0},
                                 {1, 0, 1, 0, 1}
                                };
        System.out.println("Number of islands is "+countIslands(M));
    }
    void connectivityTestData(){
//        isReachableFromSrcToDestTestData();
//        stronglyConnectedGraphTestData();
//        articulationPointTestData();
//        binaryConnectedGraphTestData();
//        bridgeInGraphTestData();
//        EulerianTestData();
//        printEulerTestData();
//        printSccTestData();
//        printReachabilityTransclosureTestData();
//        countIslandsTestData();
//          bccTestData();
//        printSccTestDataUsingDiscAndLow();
    }
    
    

    
}
