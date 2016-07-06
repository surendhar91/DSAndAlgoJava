/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.graphs;

import java.util.LinkedList;

/**
 *
 * @author surendhar-2481
 */
public class ShortestPath {
    
    static final int V=9;
    int findMin(int dist[],boolean sptSet[]){
    
       int min = Integer.MAX_VALUE;
       int minIndex=-1;
       for(int i=0;i<dist.length;i++){
           if(!sptSet[i]&&dist[i]<=min){
               min = dist[i];
               minIndex = i;
           }
       }
       return minIndex;
    }
    void printSolution(int dist[],int n){
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i+" \t\t "+dist[i]);
    }
    void dijkstra(int graph[][], int src){
    //find shortest path in a graph from src - similar to prim's algorithm
        
        boolean[] sptSet = new boolean[V];
        for(int i=0;i<V;i++){
            sptSet[i] = false;//mark all the nodes as visited.
        }
        int[] dist = new int[V];//key array - distance array for each vertex
        for(int i=0;i<V;i++){
            dist[i]=Integer.MAX_VALUE;
        }
        dist[src] = 0;
//        sptSet[src] = true; must be commented out
        
        for(int count=0;count<V-1;count++){
        //find shortest path for all the edges [V-1]
            int u = findMin(dist,sptSet);
            sptSet[u] = true;//mark this node as visited..
            //now that we have found vertex u
            for(int v=0;v<V;v++){
                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if(!sptSet[v] && graph[u][v]!=0 //edge exists and not in set.
                        && dist[u]!=Integer.MAX_VALUE //u is not max
                        && dist[u]+graph[u][v]<dist[v])//vertex v not in set
                {
                    dist[v] = dist[u]+graph[u][v];
                }
            }
        }
        printSolution(dist,V);
        
    
    }
    
    void dijkstraUsingAdjList(Graph graph,int src){
    
        int V = graph.vertices;//number of vertices in a graph
        int key[] = new int[V];//distance array
        MinHeap minHeap = new MinHeap(V);

        
        for(int v=0;v<V;v++){
            key[v] = Integer.MAX_VALUE;
            minHeap.Heap[v] = new MinHeapNode(v, key[v]);
            minHeap.pos[v] = v;
        }
        // Make dist value of src vertex as 0 so that it is extracted first
        minHeap.Heap[src] = new MinHeapNode(src,key[src]);//so that 0th vertex is picked up all the times..
        minHeap.pos[src] = src;
        key[src] = 0;
        minHeap.decreaseKey(src, key[src]);
        minHeap.size = V;
        while(!minHeap.isEmpty()){//while min heap is not empty do the following..
            MinHeapNode minNode = (MinHeapNode) minHeap.extractMin();//get the min element from the heap
            int u = minNode.vertex;
//            System.out.println("Min node traversed "+u);
            LinkedList<Graph.AdjListNode> adjList = graph.arrAdjNode[u];
            //for each of node adjacent to the minimum vertex found..
//                System.out.println("Adj node for node "+u);
            for (Graph.AdjListNode node:adjList) {
                //check if the node's vertex found in min heap, only then it's not in Constructed MST
                int v = node.v;
//                System.out.println("Node "+v);
                if(minHeap.isInMinHeap(v)&&key[u]!=Integer.MAX_VALUE){
                    if(node.weight+key[u]<key[v]){
                        key[v] = node.weight + key[u];
                        minHeap.decreaseKey(v,key[v]);//update the key of adjacent vertex v.
                    }
                }
                
            }
        }
        System.out.println("Dijkstra using adjacency list..");
        System.out.println("Vertex   Distance from Source");
        for(int i=0;i<V;i++){
            System.out.println(i+" \t\t "+key[i]);
        }
        
    }
    void bellmanFord(MST graph, int src){
        //Bellman ford algorithm finds the shortest path of a graph even if it contains negative cycle
        //Time complexity: o(VE) more than dijkstra's (greedy algorithm)
        //Bellman ford dynamic programming..
        //Finds shortest path of atmost 1 edge in 1st iteration
        //Shortest path of atmost 2 edge in 2nd iteration.
        int V = graph.V;
        int E = graph.E;
        //step 1: intialize distances from src to all other vertices as infinite
        //Src to src - distance is zero
        int[] dist = new int[V];
        for(int i=0;i<V;i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;
        for(int i=1;i<V;i++){
            //Bellman ford algorithm works with concept - there must be atleast V-1 Edges, 
            // if there is edge more than or equal to V, then there should exist a cycle
            for(int j=0;j<E;j++){
                //process all the edge.
                int u = graph.edge[j].src;
                int v= graph.edge[j].dest;
                if(dist[u]!=Integer.MAX_VALUE && dist[u]+graph.edge[j].weight < dist[v]){
                    dist[v] = dist[u]+graph.edge[j].weight;
                }
            
            }
            //Iteration 1: finds shortest path of graph of atmost 1 edge
            //Iteration i: finds shortest path of graph of atmost i edge
        }
        //step 3: check for negative -weight cycles.
        //The above step guarantees shortest distances if graph doesn't contain
        //negative cycle, if the step 3 finds a shortest distance to any vertex, then it means graph 
        //contains negative cycle
        
        for(int j=0;j<E;j++){
        int u = graph.edge[j].src;
                int v= graph.edge[j].dest;
                if(dist[u]!=Integer.MAX_VALUE && dist[v]>dist[u]+graph.edge[j].weight){
                    System.out.println("Graph contains negative weight cycle");
                    break;
                }
        }
        printArr(dist, V);
               
        
    }
    // A utility function used to print the solution
    void printArr(int dist[], int V)
    {
        System.out.println("Vertex   Distance from Source");
        for (int i=0; i<V; ++i)
            System.out.println(i+"\t\t"+dist[i]);
    }
    void floydWarshallAllPairShortestPath(int[][] graph,int V){
        //applies only to weighted directed graph.
        int[][] dist = new int[V][V];
        
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                dist[i][j] = graph[i][j]; 
            }
        }
        
        /*
        The idea is to one by one pick all vertices and update all shortest paths which include the picked vertex as an intermediate vertex in the shortest path. When we pick vertex number k as an intermediate vertex, we already have considered vertices {0, 1, 2, .. k-1} as intermediate vertices. For every pair (i, j) of source and destination vertices respectively, there are two possible cases.
        
        
        1) k is not an intermediate vertex in shortest path from i to j. We keep the value of dist[i][j] as it is.
        2) k is an intermediate vertex in shortest path from i to j. We update the value of dist[i][j] as dist[i][k] + dist[k][j].
        */
        
        for(int k=0;k<V;k++){//O(V^3)
            //if we are k vertex, it means that we had traversed k-1 vertices already, in the matrix dist
            // Matrix dist would have had the shortest path to reach k vertex
            for(int i=0;i<V;i++){
                for(int j=0;j<V;j++){
                    
                    if(dist[i][k]+dist[k][j]<dist[i][j]){//Don't use Integer.MAX_VALUE  - adding integer.max_value + integer.max_value will misbehave
                        
                        //If the path between i to j, traverses through k vertex and if the minimum distance is achieved through the same,
                        //then update dist[i][j] to be the path through k.
                        dist[i][j] = dist[i][k]+dist[k][j];
                    }
                }
            }
        }
        printSolution(dist,V);
    
    }
     void printSolution(int dist[][],int V)
    {
        System.out.println("Following matrix shows the shortest "+
                         "distances between every pair of vertices");
        for (int i=0; i<V; ++i)
        {
            for (int j=0; j<V; ++j)
            {
                if (dist[i][j]==99999)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j]+"   ");
            }
            System.out.println();
        }
    }
     
     void johnSonAlgorithm(){
         //All pair shortest path algorithm using Bellman ford and dijkstra's algorithm 
         //Time complexity of johnson algorithm is o(V2logV+VE)
         // calculate dijkstra's algorithm for V vertex - O(V(VLogV)) - will get all pair shortest path algorithm
         //Using dijkstra's is way better than floyd warshall, but the problem with dijkstra's is it doesn't deal with negative weight edge
  
         //Idea of johnson algorithm is to reweight all edges and make them positive, then apply dijkstra's algorithm for every vertex
         
        
         //Assign a weight to every vertex. let the weight assigned to vertex u be h[u]
         // We reweight edges using vertex weights.
         // Ex., for an edge(u,v) of weight w(u,v) the new weight becomes w(u,v)+h[u]-h[v]
         
         //The great thing about this reweighting is, all set of paths between any two vertices are increased by same amount and all negative weights become non-negative. Consider any path between two vertices s and t, weight of every path is increased by h[s] – h[t], all h[] values of vertices on path from s to t cancel each other.
         
         //But how do we calculate h[] values? Bellman ford algorithm is used for this purpose.
         //A new vertex is added to the graph and connected to all existing vertices. The shortest distance values from new vertex to all existing vertices are h[] values.
         
         
         /*
         Algorithm:
        1) Let the given graph be G. Add a new vertex s to the graph, add edges from new vertex to all vertices of G. Let the modified graph be G’.

        2) Run Bellman-Ford algorithm on G’ with s as source. Let the distances calculated by Bellman-Ford be h[0], h[1], .. h[V-1]. If we find a negative weight cycle, then return. Note that the negative weight cycle cannot be created by new vertex s as there is no edge to s. All edges are from s.

        3) Reweight the edges of original graph. For each edge (u, v), assign the new weight as “original weight + h[u] – h[v]”.

        4) Remove the added vertex s and run Dijkstra’s algorithm for every vertex.
         
         */
         
         // for more info, refer http://www.geeksforgeeks.org/johnsons-algorithm/
         
     }
     
     void shortestPathTestData(){
//        bellmanFordTestData();
//        floydWarshallTestData();
//        findShortestPathWithKEdgesTestData();
         countWalksWithKEdgesTestData();
    }
    static void floydWarshallTestData(){
        /* Let us create the following weighted graph
           10
        (0)------->(3)
        |         /|\
        5 |          |
        |          | 1
        \|/         |
        (1)------->(2)
           3           */
        int INF = 99999;
        int graph[][] = { {0,   5,  INF, 10},
                          {INF, 0,   3, INF},
                          {INF, INF, 0,   1},
                          {INF, INF, INF, 0}
                        };
//        AllPairShortestPath a = new AllPairShortestPath();
 
        // Print the solution
        new ShortestPath().floydWarshallAllPairShortestPath(graph, 4);
    
    }
    static void bellmanFordTestData(){
        /*
        1) Negative weights are found in various applications of graphs. 
        For example, instead of paying cost for a path, we may get some advantage if we follow the path.

2) Bellman-Ford works better (better than Dijksra’s) for distributed systems. 
        Unlike Dijksra’s where we need to find minimum value of all vertices, in Bellman-Ford, edges are considered one by one.
        
        ike other Dynamic Programming Problems, the algorithm calculate shortest paths in bottom-up manner. 
        It first calculates the shortest distances for the shortest paths which have at-most one edge in the path. 
        Then, it calculates shortest paths with at-nost 2 edges, and so on. 
        After the ith iteration of outer loop, the shortest paths with at most i edges are calculated.
        There can be maximum |V| – 1 edges in any simple path, that is why the outer loop runs |v| – 1 times.
        */
        System.out.println("Bellman ford shortest path..");
        int V = 5;  // Number of vertices in graph
        int E = 8;  // Number of edges in graph
 
        MST graph = new MST(V, E);
 
        // add edge 0-1 (or A-B in above figure)
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = -1;
 
        // add edge 0-2 (or A-C in above figure)
        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        graph.edge[1].weight = 4;
 
        // add edge 1-2 (or B-C in above figure)
        graph.edge[2].src = 1;
        graph.edge[2].dest = 2;
        graph.edge[2].weight = 3;
 
        // add edge 1-3 (or B-D in above figure)
        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 2;
 
        // add edge 1-4 (or A-E in above figure)
        graph.edge[4].src = 1;
        graph.edge[4].dest = 4;
        graph.edge[4].weight = 2;
 
        // add edge 3-2 (or D-C in above figure)
        graph.edge[5].src = 3;
        graph.edge[5].dest = 2;
        graph.edge[5].weight = 5;
 
        // add edge 3-1 (or D-B in above figure)
        graph.edge[6].src = 3;
        graph.edge[6].dest = 1;
        graph.edge[6].weight = 1;
 
        // add edge 4-3 (or E-D in above figure)
        graph.edge[7].src = 4;
        graph.edge[7].dest = 3;
        graph.edge[7].weight = -3;
 
        new ShortestPath().bellmanFord(graph, 0);
    }
    int findShortestPathFromSrcToDestWithKEdges(int[][] graph,int u,int v,int k,int V){
        int INF = Integer.MAX_VALUE;
        int sp[][][] = new int[V][V][k+1];//find shortest path from source u to vertex v with k edges in between.
        //One way is to iterate through the adjacency list of u up until we reach v, then find the shortest path.
        //This requires a recursive solution of finding the shortest path, by decrementing k-1 and iterating through the adjacent vertices of u
        for(int e=0;e<=k;e++){
            for(int i=0;i<V;i++){
                for(int j=0;j<V;j++){
                //Using dynamic programming, in a bottom up manner, we are going to find the shortest path from src to dest with k edges.
                /*
                     Table to be filled up using DP. The value sp[i][j][e] will
                     store weight of the shortest path from i to j with exactly
                     k edges
                    
                    The idea is to build a 3D table where first dimension is source, 
                    second dimension is destination, third dimension is number of edges from source to destination,
                    and the value is count of walks.
                    */    
                
                    sp[i][j][e] = INF;//initialize to infintiy
                    if(e==0&&i==j){
                        sp[i][j][e] = 0;//shortest path to src vertex itself is zero
                    }
                    if(e==1&&graph[i][j]!=INF){
                        //For the edge i->j, with only no vertex in between, set the shortest path to be the weight of the edge itself
                        sp[i][j][e] = graph[i][j];
                    }
                    if(e>1){
                        //Traverse the adjacent list only when the edge is greater than one
                        //i.e. i->a->j, a is in between 
                       for(int a=0;a<V;a++){
                           //There exists a edge from i -> a and i is not equal to a or j is not equal to a
                           if(graph[i][a]!=INF && i!=a && j!=a && sp[a][j][e-1]!=INF){
                               //sp[a][j][e-1] means a->j edge, (e-1) denotes the edge 2-1 = 1 (i.e) the shortest path to reach from a to j
                               sp[i][j][e] = Math.min(sp[i][j][e], graph[i][a]+sp[a][j][e-1]);
                           }
                       }
                    }
                }
            }
        }
        
        return sp[u][v][k];
    }
    int countWalksWithKEdges(int[][] graph,int u,int v,int k,int V){
        
        int count[][][] = new int[V][V][k+1];
        
        for(int e=0;e<=k;e++){
            //loop for k edges
            for(int i=0;i<V;i++){
                for(int j=0;j<V;j++){
                    if(e==0&&i==j){
                        count[i][j][e] = 1;//To itself is 1 edge.
                    }
                    if(e==1&&graph[i][j]!=0){
                        count[i][j][e] = 1;
                    }
                    //go to adjacent vertices only when the number of edge is more than one.
                    if(e>1){
                        //Traverse the adjacent list only when the edge is greater than one
                        //i.e. i->a->j, a is in between 
                        for(int a=0;a<V;a++){
                            //There exists a edge from i -> a
                            if(graph[i][a]!=0){
                                //count[a][j][e-1] means the number of edges from a to j with e-1 walks.
                                count[i][j][e] += count[a][j][e-1];
                            }
                        }
                    }
                }
            }
        }
        return count[u][v][k];
    }
    void findShortestPathWithKEdgesTestData(){
        //Time complexity of the above DP based solution is O(V3K) which is much better than the naive solution.
        /* Let us create the graph shown in above diagram*/
        int INF = Integer.MAX_VALUE;
        int graph[][] = new int[][]{ {0, 10, 3, 2},
                                     {INF, 0, INF, 7},
                                     {INF, INF, 0, 6},
                                     {INF, INF, INF, 0}
                                   };
        ShortestPath t = new ShortestPath();
        int u = 0, v = 3, k = 2;
        System.out.println("Weight of the shortest path is "+
                           t.findShortestPathFromSrcToDestWithKEdges(graph, u, v, k,4));
    }

    private void countWalksWithKEdgesTestData() {
        /* Let us create the graph shown in above diagram*/
        int graph[][] =new int[][] { {0, 1, 1, 1},
                                     {0, 0, 0, 1},
                                     {0, 0, 0, 1},
                                     {0, 0, 0, 0}
                                    };
        int u = 0, v = 3, k = 2;
        System.out.println("Number of walks with K Edges is "+countWalksWithKEdges(graph, u, v, k, graph.length));
    }
}
