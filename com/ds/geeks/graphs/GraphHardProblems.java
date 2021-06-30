/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.graphs;

/**
 *
 * @author surendhar-2481
 */
public class GraphHardProblems {
    
    void greedyGraphColoring(Graph g){
        
        //Time Complexity: O(V^2 + E) in worst case.
        /*
            There is no efficient algorithm available for coloring a graph with minimum number of colors as the problem is a known NP Complete problem. 
            
            There are approximate algorithms to solve the problem though. Following is the basic Greedy Algorithm to assign colors. 
            It doesn’t guarantee to use minimum colors, but it guarantees an upper bound on the number of colors. 
            
            The basic algorithm never uses more than d+1 colors where d is the maximum degree of a vertex in the given graph.
        */
        int V = g.vertices;
        
        int[] result = new int[V];// To store if the vertex is colored with color number
        boolean[] colorAvailable = new boolean[V];//If the color n is available or not
        for(int i=0;i<V;i++){
            result[i]      =   -1;
            colorAvailable[i] = false;
        }
        
        result[0] = 0;
        
        
        for(int u=0;u<V;u++){

            //Iterate through all the vertices
            for(Integer v: g.arr[u]){
                //For each adjacent node, check if the color is already assigned
                if(result[v]!=-1){
                    colorAvailable[result[v]] = true;//Mark the color available to false, for color used by the vertex v
                }
            }
            int cr =0;
            for(cr=0;cr<V;cr++){
                if(!colorAvailable[cr]){
                    break;
                }
            }//Get the lowest / first available color that is available to color the vertex u
            
            result[u] = cr;//Assigned the color cr to u
            
            for(Integer v:g.arr[u]){
                //This is mandatory to reset the values set to false, as same color can be assigned to a number of vertices, given that they are not adjacent to each other.
                
                //Reset the values set to false for the next iteration
                if(result[v]!=-1){
                    colorAvailable[result[v]] = false;
                }
            }
        }
        
        // print the result
        for (int u = 0; u < V; u++)
            System.out.println("Vertex " + u + " --->  Color "
                                + result[u]);
        
        /*
            The above algorithm doesn’t always use minimum number of colors. Also, the number of colors used sometime depend on the order in which vertices are processed. 
        
            For example, consider the following two graphs. Note that in graph on right side, vertices 3 and 4 are swapped. 
        
            If we consider the vertices 0, 1, 2, 3, 4 in left graph, we can color the graph using 3 colors. But if we consider the vertices 0, 1, 2, 3, 4 in right graph, we need 4 colors.
           
            The order in which the vertices are picked is important.
        
            Many people have suggested different ways to find an ordering that work better than the basic algorithm on average. 
        
            The most common is Welsh–Powell Algorithm which considers vertices in descending order of degrees.
        
        */
        
    }
    
    void travelingSalesManProblem(){
        /*Both of the solutions are infeasible. In fact, there is no polynomial time solution available for this problem as the problem is a known NP-Hard problem. There are approximate algorithms to solve the problem though. The approximate algorithms work only if the problem instance satisfies Triangle-Inequality.

         Triangle-Inequality: The least distant path to reach a vertex j from i is always to reach j directly from i, rather than through some other vertex k (or vertices), i.e., dis(i, j) is always less than or equal to dis(i, k) + dist(k, j). The Triangle-Inequality holds in many practical situations.
         When the cost function satisfies the triangle inequality, we can design an approximate algorithm for TSP that returns a tour whose cost is never more than twice the cost of an optimal tour. The idea is to use Minimum Spanning Tree (MST). Following is the MST based algorithm.

         Algorithm:
         1)	Let 1 be the starting and ending point for salesman.
         2)	Construct MST from with 1 as root using Prim’s Algorithm.
         3)	List vertices visited in preorder walk of the constructed MST and add 1 at the end.

         Let us consider the following example. The first diagram is the given graph. The second diagram shows MST constructed with 1 as root. The preorder traversal of MST is 1-2-4-3. Adding 1 at the end gives 1-2-4-3-1 which is the output of this algorithm.
        
         In this case, the approximate algorithm produces the optimal tour, but it may not produce optimal tour in all cases.

        How is this algorithm 2-approximate? The cost of the output produced by the above algorithm is never more than twice the cost of best possible output. Let us see how is this guaranteed by the above algorithm.
            Let us define a term full walk to understand this. A full walk is lists all vertices when they are first visited in preorder, it also list vertices when they are returned after a subtree is visited in preorder. The full walk of above tree would be 1-2-1-4-1-3-1.
            Following are some important facts that prove the 2-approximateness.
                1) The cost of best possible Travelling Salesman tour is never less than the cost of MST. (The definition of MST says, it is a minimum cost tree that connects all vertices).
                2) The total cost of full walk is at most twice the cost of MST (Every edge of MST is visited at-most twice)
                3) The output of the above algorithm is less than the cost of full walk. In above algorithm, we print preorder walk as output. In prreorder walk, two or more edges of full walk are replaced with a single edge. For example, 2-1 and 1-4 are replaced by 1 edge 2-4. So if the graph follows triangle inequality, then this is always true.

        From the above three statements, we can conclude that the cost of output produced by the approximate algorithm is never more than twice the cost of best possible solution.
        
         */

    }
    
    void kCentresProblem(){
        /*
         Given n cities and distances between every pair of cities, select k cities to place warehouses (or ATMs or Cloud Server) such that the maximum distance of a city to a warehouse (or ATM or Cloud Server) is minimized.

         For example consider the following four cities, 0, 1, 2 and 3 and distances between them, how do place 2 ATMs among these 4 cities so that the maximum distance of a city to an ATM is minimized.   
        
         There is no polynomial time solution available for this problem as the problem is a known NP-Hard problem. There is a polynomial time Greedy approximate algorithm, the greedy algorithm provides a solution which is never worse that twice the optimal solution. The greedy solution works only if the distances between cities follow Triangular Inequality (Distance between two points is always smaller than sum of distances through a third point).

         The 2-Approximate Greedy Algorithm:
         1) Choose the first center arbitrarily.

         2) Choose remaining k-1 centers using the following criteria.
         Let c1, c2, c3, … ci be the already chosen centers. Choose
         (i+1)’th center by picking the city which is farthest from already
         selected centers, i.e, the point p which has following value as maximum
         Min[dist(p, c1), dist(p, c2), dist(p, c3), …. dist(p, ci)]

         The following diagram taken from here illustrates above algorithm.
        
         Example (k = 3 in the above shown Graph)
         a) Let the first arbitrarily picked vertex be 0.

         b) The next vertex is 1 because 1 is the farthest vertex from 0.(value is 10)

         c) Remaining cities are 2 and 3. Calculate their distances from already selected centers (0 and 1). The greedy algorithm basically calculates following values.

         Minimum of all distanced from 2 to already considered centers
         Min[dist(2, 0), dist(2, 1)] = Min[7, 8] = 7

         Minimum of all distanced from 3 to already considered centers
         Min[dist(3, 0), dist(3, 1)] = Min[6, 5] = 5

         After computing the above values, the city 2 is picked as the value corresponding to 2 is maximum.

         Note that the greedy algorithm doesn’t give best solution for k = 2 as this is just an approximate algorithm with bound as twice of optimal.
        
         */
    }
    boolean isHamSafe(int v, int graph[][],int path[],int pos){
        //Ensure that the element v is adjacent to the previously added vertex (pos-1)
        if(graph[path[pos-1]][v]==0)
            return false;
        
        for(int i=0;i<pos;i++){
            //Up until pos, check if the element is already added to the path, if already added return false
            if(path[i]==v){
                return false;
            }
        }
        return true;
    }
    
    boolean hamCycleUtil(int graph[][],int path[],int pos){
    
        if(pos==graph.length){//If all the elements are traveresed
        
            if(graph[path[pos-1]][path[0]]==1){
                return true;//IF there exists a path from last traversed vertex to first vertex, then hamiltonian cycle exists
            }else{
                return false;
            }
        }
        
        for(int v=1;v<graph.length;v++){//Ensures that every vertex is visited
            //v vertices do the following
            if(isHamSafe(v, graph, path, pos)){//not already added to path and adjacent to the previously added
                
                path[pos] = v;//add the v element to path at pos
                
                if(hamCycleUtil(graph,path,pos+1)){//note that previously added vertex is passed in path array
                    return true;
                }
                
                //If adding the element to path, doesn't lead to a solution, then remove the element from pos path
                path[pos] = -1;
            }
            
        }
        
        return false;//If no vertex can be added to hamiltonian cycle constructed so far, then return false.
    
    }
    boolean hamCycle(int graph[][]){
    /*  Hamiltonian Path in an undirected graph is a path that visits each vertex exactly once. 
        A Hamiltonian cycle (or Hamiltonian circuit) is a Hamiltonian Path such that there is an edge (in graph) from the last vertex to the first vertex of the Hamiltonian Path. 
        
        Determine whether a given graph contains Hamiltonian Cycle or not. If it contains, then print the path. Following are the input and output of the required function.

Input:
A 2D array graph[V][V] where V is the number of vertices in graph and graph[V][V] is adjacency matrix representation of the graph. A value graph[i][j] is 1 if there is a direct edge from i to j, otherwise graph[i][j] is 0.

Output:
An array path[V] that should contain the Hamiltonian Path. path[i] should represent the ith vertex in the Hamiltonian Path. The code should also return false if there is no Hamiltonian Cycle in the graph.

For example, a Hamiltonian Cycle in the following graph is {0, 1, 2, 4, 3, 0}. There are more Hamiltonian Cycles in the graph like {0, 3, 4, 2, 1, 0}

(0)--(1)--(2)
 |   / \   |
 |  /   \  | 
 | /     \ |
(3)-------(4)
And the following graph doesn’t contain any Hamiltonian Cycle.

(0)--(1)--(2)
 |   / \   |
 |  /   \  | 
 | /     \ |
(3)      (4) 
        
        
        */
    /*
        
        Backtracking Algorithm
        
        Create an empty path array and add vertex 0 to it. Add other vertices, starting from the vertex 1. Before adding a vertex, check for whether it is adjacent to the previously added vertex and not already added to the path array.
        
        If we find such a vertex, we add the vertex as part of the solution. If we do not find a vertex then we return false.
        
        */
        
        int V      = graph.length;
        int path[] = new int[V];//This is going to store the vertex that was visited at position pos
        
        for(int i=0;i<V;i++){
            path[i] = -1;
        }
        path[0] = 0;
        if(!hamCycleUtil(graph,path,1)){
            System.out.println("Given graph is not a hamiltonian - solution doesn't exist");
            return false;
        }
        System.out.println("Solution Exists: Following" +
                           " is one Hamiltonian Cycle");
        
        for (int i = 0; i < V; i++)
            System.out.print(" " + path[i] + " ");
 
        // Let us print the first vertex again to show the
        // complete cycle
        System.out.println(" " + path[0] + " ");
        return true;
        
    } 
    
    void printVertexCover(Graph graph){
        
        //NP Hard problem - with an approximation algorithm
        /*
            A vertex cover of an undirected graph is a subset of its vertices such that for every edge (u, v) of the graph, either ‘u’ or ‘v’ is in vertex cover. 
        
        
            NOTE: Although the name is Vertex Cover, the set covers all edges of the given graph.
            
            For example. refer http://www.geeksforgeeks.org/vertex-cover-problem-set-1-introduction-approximate-algorithm-2/
        
            In other words, minimum vertex cover is a subset of vertices that connects the graph mainly.
        
            Vertex Cover Problem is a known NP Complete problem, i.e., there is no polynomial time solution for this unless P = NP. 
        
            There are approximate polynomial time algorithms to solve the problem though. Following is a simple approximate algorithm adapted from CLRS book.
            
            How well the above algorithm perform?
                It can be proved that the above approximate algorithm never finds a vertex cover whose size is more than twice the size of minimum possible vertex cover (Refer this for proof)
        
        */
        
        boolean visited[] = new boolean[graph.vertices];
        for(int i=0;i<graph.vertices;i++){
            visited[i] = false;
        }
        
        //Get all edges one by one
        for(int u=0;u<graph.vertices;u++){
            //As we traverse a edge, mark vertex u and v as visited, so that both u and v will not be visited from any other vertex
            //This edge will be considered for vertex cover subset.
            if(!visited[u]){
            
                // Go through all adjacents of u and pick the
                // first not yet visited vertex (We are basically
                //  picking an edge (u, v) from remaining edges.
                for(Integer v:graph.arr[u]){
                    if(!visited[v]){
                         // Add the vertices (u, v) to the result
                         // set. We make the vertex u and v visited
                         // so that all edges from/to them would
                         // be ignored
                        visited[u] = true;
                        visited[v] = true;
                        break;// Vertex v will
                    }
                }
                
            }
        }
        // Print the vertex cover
        for (int j=0; j<graph.vertices; j++)
            if (visited[j])
              System.out.print(j+" ");
    
    }
    
    void printVertexCoverTestData(){
        Graph g = new Graph(7);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        printVertexCover(g);
        
    }
    
    void graphColoringTestData(){
        Graph g1 = new Graph(5,true);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);
        System.out.println("Coloring of graph 1");
        greedyGraphColoring(g1);
 
        System.out.println();
        Graph g2 = new Graph(5,true);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(1, 2);
        g2.addEdge(1, 4);
        g2.addEdge(2, 4);
        g2.addEdge(4, 3);
        System.out.println("Coloring of graph 2 ");
        greedyGraphColoring(g2);
    }
    
    void hamiltonianTestData(){
        /* Let us create the following graph
           (0)--(1)--(2)
            |   / \   |
            |  /   \  |
            | /     \ |
           (3)-------(4)    */
        int graph1[][] = {{0, 1, 0, 1, 0},
            {1, 0, 1, 1, 1},
            {0, 1, 0, 0, 1},
            {1, 1, 0, 0, 1},
            {0, 1, 1, 1, 0},
        };
 
        // Print the solution
        hamCycle(graph1);
 
        /* Let us create the following graph
           (0)--(1)--(2)
            |   / \   |
            |  /   \  |
            | /     \ |
           (3)       (4)    */
        int graph2[][] = {{0, 1, 0, 1, 0},
            {1, 0, 1, 1, 1},
            {0, 1, 0, 0, 1},
            {1, 1, 0, 0, 0},
            {0, 1, 1, 0, 0},
        };
 
        // Print the solution
        hamCycle(graph2);
    }
    
    void hardProblemsTestData(){
//        graphColoringTestData();
//        hamiltonianTestData();
//        printVertexCoverTestData();
    }
}
