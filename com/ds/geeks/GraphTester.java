/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks;

/**
 *
 * @author surendhar-2481
 */
public class GraphTester {
    public static void main(String[] args) {
        Graph g = new Graph(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println("Following is Breadth First Traversal "
                + "(starting from vertex 2)");

        g.BFS(2);

        g = new Graph(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println("Following is Depth First Traversal "
                + "(starting from vertex 2)");

        g.DFS(2);

        System.out.println("Graph Cycle test..");
        g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        if (g.isCyclicDirectedGraphUsingRecursiveStack()) {
            System.out.println("Graph contains cycle");
        } else {
            System.out.println("Graph doesn't contain cycle");
        }

        System.out.println("Graph Cycle test using colors...");
        g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        if (g.isCyclicUsingColors()) {
            System.out.println("Graph contains cycle");
        } else {
            System.out.println("Graph doesn't contain cycle");
        }

        System.out.println("Graph cycle test for directed - using union find O(ElogV) time and DFS O(E+V) time..");
        
         /* Let us create following graph
         0
        |  \
        |    \
        1-----2 */
        int V = 3, E = 3;
        Graph graph = new Graph(V);
 
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(0, 2);

 
//        if (graph.isCycleUnDirected(graph))
        if(graph.isCycleUndirectedUsingParent(graph))
            System.out.println( "graph contains cycle" );
        else
            System.out.println( "graph doesn't contain cycle" );
        
        //find longest distances..
        
        // Create a graph given in the above diagram.  Here vertex numbers are
    // 0, 1, 2, 3, 4, 5 with following mappings:
    // 0=r, 1=s, 2=t, 3=x, 4=y, 5=z
    graph = new Graph(6,true,false);
    graph.addEdge(0, 1, 5);
    graph.addEdge(0, 2, 3);
    graph.addEdge(1, 3, 6);
    graph.addEdge(1, 2, 2);
    graph.addEdge(2, 4, 4);
    graph.addEdge(2, 5, 2);
    graph.addEdge(2, 3, 7);
    graph.addEdge(3, 5, 1);
    graph.addEdge(3, 4, -1);
    graph.addEdge(4, 5, -2);
 
    int s = 1;
    System.out.println("Following are longest distances from source vertex " +s +" \n");
    graph.findLongestPath(s);
    
    graph = new Graph(6,true,false);
    graph.addEdge(5, 2, 0 );
        graph.addEdge(5, 0,0);
        graph.addEdge(4, 0,0);
        graph.addEdge(4, 1,0);
        graph.addEdge(2, 3,0);
        graph.addEdge(3, 1,0);
    //finding topological sort
    System.out.println("Following is a Topological " +
                           "sort of the given graph");
    graph.topologicalSort();//5,4,3,2,1,0 is the topological sorting of the given graph..
    
        System.out.println("Graph partite testing..");
        
        graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1,0);
        graph.addEdge(1,2);
        graph.addEdge(2, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3,0);
        graph.addEdge(3,2);
        System.out.println("Is partite graph from source 0 is "+graph.isBiPartite(0));
    
        
        System.out.println("Snake ladder problem..");
        
        // Let us construct the board given in above diagram
    int N = 30;
    int[] moves = new int[N];
    for (int i = 0; i<N; i++)
        moves[i] = -1;
 
    // Ladders
    moves[2] = 21;
    moves[4] = 7;
    moves[10] = 25;
    moves[19] = 28;
 
    // Snakes
    moves[26] = 0;
    moves[20] = 8;
    moves[16] = 3;
    moves[18] = 6;
    
        System.out.println("Min dice needed is "+Graph.getMinDiceThrows(moves, N));
        
        System.out.println("--------Minimum cash flow Greedy Graph-----");
         // graph[i][j] indicates the amount that person i needs to
    // pay person j
    int amount[][] = new int[][]{ {0, 1000, 2000},
                        {0, 0, 5000},
                        {0, 0, 0},};
 
    // Print the solution
    Graph.minCashFlow(amount,3);
        
    
        System.out.println("------------Following words of dictionary were present----");
        char boggle[][] = new char[][]{{'G','I','Z'},
                         {'U','E','K'},
                         {'Q','S','E'}};
 
    graph.findWords(boggle,3,3);
        
    
    //Add directions to undirected graph - very interesting problem..
    /*
    
    Given a graph with both directed and undirected edges. It is given that the directed edges don’t form cycle. 
    How to assign directions to undirected edges so that the graph (with all directed edges) remains acyclic even after the assignment?
    
    
    The idea is to use Topological Sorting. Following are two steps used in the algorithm.

1) Consider the subgraph with directed edges only and find topological sorting of the subgraph. 
    
    In the above example, topological sorting is {0, 5, 1, 2, 3, 4}. Below diagram shows topological sorting for the above example graph.
    
2) Use above topological sorting to assign directions to undirected edges. For every undirected edge (u, v), assign it direction from u to v if u comes before v in topological sorting, else assign it direction from v to u.

    Below diagram shows assigned directions in the example graph
    */
    
    //prim's algorithm
    /* Let us create the following graph
           2    3
        (0)--(1)--(2)
        |    / \   |
        6| 8/   \5 |7
        | /      \ |
        (3)-------(4)
             9          */
        System.out.println("-----------Minimum spanning tree using prim's algorithm");//O(V2) time for finding the minimum spanning tree using prim's algorithm
        MST t = new MST();
        int prim[][] = new int[][] {{0, 2, 0, 6, 0},
                                    {2, 0, 3, 8, 5},
                                    {0, 3, 0, 0, 7},
                                    {6, 8, 0, 0, 9},
                                    {0, 5, 7, 9, 0},
                                   };
 
        // Print the solution
        t.primMST(prim);
        
        graph = new Graph(9, true,true);
        graph.addEdge(0, 1, 4);
    graph.addEdge(0, 7, 8);
    graph.addEdge(1, 2, 8);
    graph.addEdge(1, 7, 11);
    graph.addEdge(2, 3, 7);
    graph.addEdge(2, 8, 2);
    graph.addEdge(2, 5, 4);
    graph.addEdge(3, 4, 9);
    graph.addEdge(3, 5, 14);
    graph.addEdge(4, 5, 10);
    graph.addEdge(5, 6, 2);
    graph.addEdge(6, 7, 1);
    graph.addEdge(6, 8, 6);
    graph.addEdge(7, 8, 7);
    t.primMSTUsingAdjList(graph);
 
    /* Let us create following weighted graph
                 10
            0--------1
            |  \     |
           6|   5\   |15
            |      \ |
            2--------3
                4       */
        V = 4;  // Number of vertices in graph
        E = 5;  // Number of edges in graph
        t = new MST(V, E);
 
        // add edge 0-1
        t.edge[0].src = 0;
        t.edge[0].dest = 1;
        t.edge[0].weight = 10;
 
        // add edge 0-2
        t.edge[1].src = 0;
        t.edge[1].dest = 2;
        t.edge[1].weight = 6;
 
        // add edge 0-3
        t.edge[2].src = 0;
        t.edge[2].dest = 3;
        t.edge[2].weight = 5;
 
        // add edge 1-3
        t.edge[3].src = 1;
        t.edge[3].dest = 3;
        t.edge[3].weight = 15;
 
        // add edge 2-3
        t.edge[4].src = 2;
        t.edge[4].dest = 3;
        t.edge[4].weight = 4;
 
        t.KruskalMST();
        t.boruvkaMST();
        
        int grap[][] = new int[][]{{0, 4, 0, 0, 0, 0, 0, 8, 0},
                                  {4, 0, 8, 0, 0, 0, 0, 11, 0},
                                  {0, 8, 0, 7, 0, 4, 0, 0, 2},
                                  {0, 0, 7, 0, 9, 14, 0, 0, 0},
                                  {0, 0, 0, 9, 0, 10, 0, 0, 0},
                                  {0, 0, 4, 0, 10, 0, 2, 0, 0},
                                  {0, 0, 0, 14, 0, 2, 0, 1, 6},
                                  {8, 11, 0, 0, 0, 0, 1, 0, 7},
                                  {0, 0, 2, 0, 0, 0, 6, 7, 0}
                                 };
        ShortestPath spt = new ShortestPath();
        spt.dijkstra(grap, 0);
        
        V = 9;
    graph = new Graph(V,true,true);
    graph.addEdge(0, 1, 4);
    graph.addEdge(0, 7, 8);
    graph.addEdge(1, 2, 8);
    graph.addEdge(1, 7, 11);
    graph.addEdge(2, 3, 7);
    graph.addEdge(2, 8, 2);
    graph.addEdge(2, 5, 4);
    graph.addEdge(3, 4, 9);
    graph.addEdge(3, 5, 14);
    graph.addEdge(4, 5, 10);
    graph.addEdge(5, 6, 2);
    graph.addEdge(6, 7, 1);
    graph.addEdge(6, 8, 6);
    graph.addEdge(7, 8, 7);
 
    spt.dijkstraUsingAdjList(graph, 0);
    spt.shortestPathTestData();
    
//    new Connectivity().connectivityTestData();
//    new GraphHardProblems().hardProblemsTestData();
//      new MaxFlowGraph().maxFlowTestData();
      new GraphMisc().graphMiscTestData();
    }
}
