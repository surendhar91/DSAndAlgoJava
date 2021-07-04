/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author surendhar-2481
 */
public class Graph {
    public int vertices;
    public List<Integer> arr[];
    public int in[];// To store indegree of a vertex

    public Graph(int v) {
        vertices = v;
        arr = new LinkedList[v];
        in = new int[v];
        for (int i = 0; i < v; i++) {
            arr[i] = new LinkedList<Integer>();
            in[i] = 0;
        }
    }

    public Graph(int v, boolean isUndirected, String isConcurrent) {
        vertices = v;
        this.isUndirected = isUndirected;
        arr = new CopyOnWriteArrayList[v];// ??
        for (int i = 0; i < v; i++) {
            arr[i] = new CopyOnWriteArrayList<Integer>();

        }
    }

    public Graph(int v, boolean isUndirected) {
        this(v);
        this.isUndirected = isUndirected;
    }

    void addEdge(int v, int w) {
        arr[v].add(w);// adding edge w to v graph
        in[w]++;
        if (this.isUndirected) {
            arr[w].add(v);
            in[v]++;
        }
    }

    void removeEdge(int v, int w) {
        arr[v].remove((Object) w);
        in[w]--;
        if (this.isUndirected) {
            arr[w].remove((Object) v);
            in[v]--;
        }
    }

    void BFS(int source) {
        // given a source, do the breadth first traversal
        boolean visited[] = new boolean[this.vertices];
        // Time Complexity: O(V+E) where V is number of vertices in the graph and E is
        // number of edges in the graph.
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);// adding source vertex
        visited[source] = true;
        while (queue.size() != 0) {
            int startVertex = queue.poll();
            System.out.println(startVertex + " ");
            List<Integer> adjList = arr[startVertex];
            for (Integer edge : adjList) {
                if (!visited[edge]) {
                    visited[edge] = true;
                    queue.add(edge);
                }
            }

        }

    }

    void DFSUtil(int src, boolean[] visited) {
        // For an unweighted graph, dfs traversal produces the minimum spanning tree and
        // all pair shortest path
        visited[src] = true;
        System.out.println(src + " ");
        List<Integer> adjList = arr[src];
        for (Integer edge : adjList) {
            if (!visited[edge]) {
                DFSUtil(edge, visited);
            }
        }

    }

    // A DFS based function to find all reachable vertices from s. The function
    // marks visited[i] as true if i is reachable from s. The initial values in
    // visited[] must be false. We can also use BFS to find reachable vertices
    void dfs(int graph[][], int u, boolean visited[]) {
        visited[u] = true;
        for (int v = 0; v < graph.length; v++)
            if (graph[u][v] > 0 && !visited[v])
                dfs(graph, v, visited);
    }

    int DFSCount(int src, boolean[] visited) {
        visited[src] = true;
        int count = 1;
        List<Integer> adjList = arr[src];
        for (Integer edge : adjList) {
            if (!visited[edge]) {
                count += DFSCount(edge, visited);
            }
        }
        return count;
    }

    void DFSUtilPushStack(int src, boolean[] visited, Stack stack) {
        visited[src] = true;
        List<Integer> adjList = arr[src];
        for (Integer edge : adjList) {
            if (!visited[edge]) {
                DFSUtilPushStack(edge, visited, stack);
            }
        }
        // All vertices reachable from v are processed by now,
        // push v to Stack
        stack.push(src);
    }

    void DFS(int v) {
        boolean visited[] = new boolean[this.vertices];// this is necessary.
        DFSUtil(v, visited);
    }

    boolean isCyclicDirectedGraphUsingRecursiveStack() {// holds only for directed graph
        /**
         * Given a directed graph, check whether the graph contains a cycle or not. Your
         * function should return true if the given graph contains at least one cycle,
         * else return false. Example, 1 ----| ^ v | |> 2------- | | | | | | | | | | v |
         * 0--- 3-- | ^ ^ | | | |__| | -------------- Input: n = 4, e = 6 0 -> 1, 0 ->
         * 2, 1 -> 2, 2 -> 0, 2 -> 3, 3 -> 3 Output: Yes Explanation: 3->3 is a cycle,
         * 2->0->2 is a cycle, 2->0->1->2 is a cycle.
         */
        boolean[] recStack = new boolean[this.vertices];
        boolean[] visited = new boolean[this.vertices];

        for (int i = 0; i < this.vertices; i++) {
            recStack[i] = false;
            /*
             * There is a cycle in a graph only if there is a back edge present in the
             * graph. A back edge is an edge that is from a node to itself (selfloop) or one
             * of its ancestor in the tree produced by DFS.
             * 
             * To detect a back edge, we can keep track of vertices currently in recursion
             * stack of function for DFS traversal.
             * 
             * IMPORTANT: If we reach a vertex that is already in the recursion stack, then
             * there is a cycle in the tree.
             */

            visited[i] = false; // To mark that the verex has been visited
        }
        for (int i = 0; i < this.vertices; i++) {
            if (isCyclicUtil(i, visited, recStack)) {// if any of the DFS tree has a cycle
                // call this function to detect cycle in different DFS trees.
                return true;
            }
        }
        return false;
    }

    boolean isCyclicUtil(int vertex, boolean[] visited, boolean[] recStack) {
        // Time Complexity of this method is same as time complexity of DFS traversal
        // which is O(V+E).

        if (!visited[vertex]) {// only if the vertex is not visited.
            // Mark the current node as visited and part of recursion stack
            System.out.println(vertex + " ");
            visited[vertex] = true;
            recStack[vertex] = true;

            // Recur for all the vertices adjacent to this vertex
            List<Integer> adjList = arr[vertex];// get the adjacency list (i.e. for all children) of a vertex, parent is
                                                // added to the recur stack

            for (Integer edge : adjList) {
                if (!visited[edge] && isCyclicUtil(edge, visited, recStack)) {
                    // if any of the children has a cycle, return true.
                    return true;
                } else if (recStack[edge]) {
                    // if already visited, could be a loop, hence find the element in recursive
                    // stack, if present true
                    // for self loop or edge to the ancestor
                    return true;
                }
            }
        } else { // if the node is already visited, remove the vertex from recursion stack.
            // 2
            // / \
            // 0 3
            // / /
            // 1
            recStack[vertex] = false;// remove the vertex from recursion stack
        }
        return false;
    }

    private enum Color {
        WHITE, GREY, BLACK
    };

    boolean isCyclicUsingColors() {

        /*
         * WHITE : Vertex is not processed yet. Initially all vertices are WHITE.
         * 
         * GRAY : Vertex is being processed (DFS for this vertex has started, but not
         * finished which means that all descendants (in the DFS tree) of this vertex
         * are not processed yet (or this vertex is in function call stack)
         * 
         * BLACK : Vertex and all its descendants are processed.
         * 
         * While doing DFS, if we encounter an edge from current vertex to a GRAY
         * vertex, then this edge is back edge and hence there is a cycle.
         */
        // initialilze all the vertex to white - as they are not processed
        Color colors[] = new Color[this.vertices];
        for (int i = 0; i < this.vertices; i++) {
            colors[i] = Color.WHITE;
        }

        for (int i = 0; i < this.vertices; i++) {
            if (colors[i] == Color.WHITE && isCyclicUtilUsingColor(i, colors)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCyclicUtilUsingColor(int vertex, Color[] colors) {
        colors[vertex] = Color.GREY; // in processing.

        List<Integer> adjList = arr[vertex];
        for (Integer edge : adjList) {

            if (colors[edge] == Color.GREY) {// Back edge
                return true;
            }

            if (colors[edge] == Color.WHITE && isCyclicUtilUsingColor(edge, colors)) {// check the cyclic utiling with
                                                                                      // edge..
                return true;
            }
        }
        colors[vertex] = Color.BLACK;// fully processed
        return false;
    }

    private int find(int[] parent, int vertex) {// returns the subset representative, this vertex is a part of
        if (parent[vertex] == -1)
            return vertex;
        return find(parent, parent[vertex]);
        // A utility function to find the subset of an element i
    }

    // A utility function to do union of two subsets
    private void union(int[] parent, int src, int dest) {
        int xSet = find(parent, src);// this can be avoided.
        int ySet = find(parent, dest);
        parent[xSet] = ySet;
    }

    public boolean isCycleUnDirected(Graph graph) {
        // Note that the implementation of union() and find() is naive and takes O(n)
        // time in worst case.
        /*
         * Let us consider the following graph:
         * 
         * 0 | \ | \ 1-----2 For each edge, make subsets using both the vertices of the
         * edge. If both the vertices are in the same subset, a cycle is found.
         * 
         * Initially, all slots of parent array are initialized to -1 (means there is
         * only one item in every subset).
         * 
         * 0 1 2 -1 -1 -1 Now process all edges one by one.
         * 
         * Edge 0-1: Find the subsets in which vertices 0 and 1 are. Since they are in
         * different subsets, we take the union of them. For taking the union, either
         * make node 0 as parent of node 1 or vice-versa.
         * 
         * 0 1 2 <----- 1 is made parent of 0 (1 is now representative of subset {0, 1})
         * 1 -1 -1 Edge 1-2: 1 is in subset 1 and 2 is in subset 2. So, take union.
         * 
         * 0 1 2 <----- 2 is made parent of 1 (2 is now representative of subset {0, 1,
         * 2}) 1 2 -1 Edge 0-2: 0 is in subset 2 and 2 is also in subset 2. Hence,
         * including this edge forms a cycle.
         * 
         * How subset of 0 is same as 2? 0->1->2 // 1 is parent of 0 and 2 is parent of
         * 1
         */
        int[] parent = new int[graph.vertices]; // this is used for find and union of subsets.

        // We are using and union - find algorithm to test if the there is a cycle in
        // undirected graph
        // This method assumes that there is no self cycle
        for (int i = 0; i < graph.vertices; i++) {
            parent[i] = -1;// Initialize all subsets as single element sets
        }

        for (int i = 0; i < graph.vertices; i++) {
            int src = i;
            List<Integer> edge = graph.arr[src];// edge.
            for (Integer dest : edge) {
                // Iterate through all edges of graph, find subset of both
                // vertices of every edge, if both subsets are same, then
                // there is cycle in graph.
                int xSet = graph.find(parent, src);
                int ySet = graph.find(parent, dest);

                if (xSet == ySet) {
                    return true;
                }
                graph.union(parent, src, dest);

            }
        }
        return false;
    }

    public boolean isCycleUndirectedUsingParent(Graph graph) {// Using DFS Approach, finding the cycle in a undirected
                                                              // graph
        // The time complexity of the union-find algorithm is O(ELogV). Like directed
        // graphs, we can use DFS to detect cycle in an undirected graph in O(V+E) time.
        // We do a DFS traversal of the given graph. For every visited vertex ‘v’, if
        // there is an adjacent ‘u’ such that u is already visited and u is not parent
        // of v, then there is a cycle in graph.
        /*
         * in case of undirected graph there is just 2 case -
         * 
         * 1. node previously visited
         * 
         * 2. not visited
         * 
         * if not visited then recurse it and if visited then just check it is not the
         * parent. But with directed graph this is not the case- we also have have to
         * consider the entire hierarchy so it require a recstack
         */
        boolean visited[] = new boolean[graph.vertices];

        for (int i = 0; i < graph.vertices; i++) {
            if (!visited[i] && isCycleUtilParentUndirected(graph, visited, i, -1))// vertex must not be visited
                return true;
        }
        return false;
    }

    private boolean isCycleUtilParentUndirected(Graph graph, boolean[] visited, int currentNode, int parent) {
        visited[currentNode] = true;// Mark the current node as visited

        List<Integer> allEdges = graph.arr[currentNode];
        // Recur for all the vertices adjacent to this vertex
        for (Integer destination : allEdges) {
            // If an adjacent is not visited, then recur for that
            // adjacent --- Using DFS
            if (!visited[destination] && graph.isCycleUtilParentUndirected(graph, visited, destination, currentNode)) {
                return true;
            } else if (parent != currentNode) {
                // If an adjacent is visited and not parent of current
                // vertex, then there is a cycle.
                return true;
            }
        }
        return false;

    }

    void topologicalSortUtil(int vertex, boolean visited[], Stack stack) {// Applicable for directed acyclic graph
        // What is a topological sorting ? Ensures that element u is visited before v
        // Topological Sorting for a graph is not possible if the graph is not a DAG.
        /*
         * In DFS, we print a vertex and then recursively call DFS for its adjacent
         * vertices. In topological sorting, we need to print a vertex before its
         * adjacent vertices
         */
        visited[vertex] = true;

        LinkedList<AdjListNode> adjList = this.arrAdjNode[vertex];
        for (AdjListNode adjNode : adjList) {
            // for each adjacent node in the adjacent list of vertex v
            if (!visited[adjNode.v]) {
                topologicalSortUtil(adjNode.v, visited, stack);// Use DFS
            }
        }
        /*
         * We can modify DFS to find Topological Sorting of a graph. In DFS, we start
         * from a vertex, we first print it and then recursively call DFS for its
         * adjacent vertices. In topological sorting, we use a temporary stack. We don’t
         * print the vertex immediately, we first recursively call topological sorting
         * for all its adjacent vertices, then push it to a stack. Finally, print
         * contents of stack. Note that a vertex is pushed to stack only when all of its
         * adjacent vertices (and their adjacent vertices and so on) are already in
         * stack.
         */

        // As all the adjacent nodes are processed, push this element to the stack..
        stack.add(vertex);
        // so that, as we pop the element, first element would be u then the vertex v
        // etc..
    }

    void findLongestPath(int source) {
        // Find longest path in a directed acyclic graph
        // image address:
        // https://media.geeksforgeeks.org/wp-content/uploads/LongestPath-2.png
        // In fact, the Longest Path problem is NP-Hard for a general graph. However,
        // the longest path problem has a linear time solution for directed acyclic
        // graphs.
        // The idea is similar to linear time solution for shortest path in a directed
        // acyclic graph., we use Tological Sorting.
        Stack<Integer> stack = new Stack<Integer>();

        boolean visited[] = new boolean[this.vertices];
        // initially all the vertices visited property is false..

        for (int i = 0; i < this.vertices; i++) {
            // for each vertex in the graph, find the topological sorting..
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        int dist[] = new int[this.vertices];
        dist[source] = 0;// distance of the source vertex is 0
        for (int i = 0; i < this.vertices; i++) {
            if (i != source) {
                dist[i] = Integer.MIN_VALUE;// Negative infinite for all the other vertices..
            }
        }
        // Now we obtained the graph in linear fashion, through topological sort util..

        while (!stack.empty()) {

            int u = stack.peek();
            // Get the next vertex from topological order
            stack.pop();
            if (dist[u] != Integer.MIN_VALUE) {// initially the source vertex..
                // Update distances of all adjacent vertices
                LinkedList<AdjListNode> adjList = arrAdjNode[u];
                for (AdjListNode adjNode : adjList) {// adjNode is
                    if (dist[adjNode.v] < dist[u] + adjNode.weight) {// maintains the longest path between u to v.
                        // distance to destination is less than the distance from source to weight [Edge
                        // between u->v]
                        dist[adjNode.v] = dist[u] + adjNode.weight;
                    }
                }
            }
        }
        System.out.println("printing the longest calculated distances..");
        for (int i = 0; i < this.vertices; i++) {
            if (dist[i] == Integer.MIN_VALUE) {
                System.out.println(i + " Infinite");
            } else {
                System.out.println(i + " distance " + dist[i]);
            }
        }

    }

    void topologicalSort() {
        // image:
        // https://media.geeksforgeeks.org/wp-content/uploads/20200818211917/Topological-Sorting-1.png
        Stack stack = new Stack();

        // Mark all the vertices as not visited
        boolean visited[] = new boolean[this.vertices];
        for (int i = 0; i < this.vertices; i++)
            visited[i] = false;

        // Call the recursive helper function to store Topological
        // Sort starting from all vertices one by one
        for (int i = 0; i < this.vertices; i++)
            if (visited[i] == false) // on manual paper work, make sure you start from the vertices, rather diving
                                     // deep on each vertex.
                topologicalSortUtil(i, visited, stack);

        // Print contents of stack
        while (stack.empty() == false)
            System.out.print(stack.pop() + " ");
    }

    boolean isBiPartite(int source) {
        /*
         * 
         * A Bipartite Graph is a graph whose vertices can be divided into two
         * independent sets, U and V such that every edge (u, v) either connects a
         * vertex from U to V or a vertex from V to U. In other words, for every edge
         * (u, v), either u belongs to U and v to V, or u belongs to V and v to U. We
         * can also say that there is no edge that connects vertices of same set.
         * 
         */
        // given a source, do the breadth first traversal
        boolean visited[] = new boolean[this.vertices];
        // Time Complexity: O(V+E) where V is number of vertices in the graph and E is
        // number of edges in the graph.
        /*
         * A bipartite graph is possible if the graph coloring is possible using two
         * colors such that vertices in a set are colored with the same color.
         */
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int colorArr[] = new int[this.vertices];
        for (int i = 0; i < this.vertices; i++) {
            colorArr[i] = -1;// initially the color is unassigned..
        }
        queue.add(source);// adding source vertex
        colorArr[source] = 1;// 1 is the first color 0 is the second color -1 is color unassigned.
        // visited[source] = true;
        while (queue.size() != 0) {
            int startVertex = queue.poll();
            System.out.println(startVertex + " ");
            List<Integer> adjList = arr[startVertex];
            for (Integer adjNode : adjList) {
                if (colorArr[adjNode] == -1) {
                    colorArr[adjNode] = 1 - colorArr[startVertex];// assign alternate color to the adjacent node.
                    queue.add(adjNode);
                } else if (colorArr[adjNode] == colorArr[startVertex]) {
                    // An edge from u to v exists and destination v is
                    // colored with same color as u
                    return false;
                    // already visited and check if the colorArr
                }
            }

        }
        return true;
        /*
         * example, Not a partite graph. red -- blue / blue | \ red -- blue
         */
    }
    // This function returns minimum number of dice throws required to
    // Reach last cell from 0'th cell in a snake and ladder game.
    // move[] is an array of size N where N is no. of cells on board
    // If there is no snake or ladder from cell i, then move[i] is -1
    // Otherwise move[i] contains cell to which snake or ladder at i
    // takes to.

    /*
     * The idea is to consider the given snake and ladder board as a directed graph
     * with number of vertices equal to the number of cells in the board. The
     * problem reduces to finding the shortest path in a graph. Every vertex of the
     * graph has an edge to next six vertices if next 6 vertices do not have a snake
     * or ladder. If any of the next six vertices has a snake or ladder, then the
     * edge from current vertex goes to the top of the ladder or tail of the snake.
     * Since all edges are of equal weight, we can efficiently find shortest path
     * using Breadth First Search of the graph.
     */
    static int getMinDiceThrows(int move[], int N) {
        // Mininum dice needed to reach the destination of snake ladder problem from
        // source vertex.
        /**
         * Image:
         * https://media.geeksforgeeks.org/wp-content/uploads/snake-and-ladders.jpg For
         * example, consider the board shown, the minimum number of dice throws required
         * to reach cell 30 from cell 1 is 3. Following are the steps:
         * 
         * a) First throw two on dice to reach cell number 3 and then ladder to reach 22
         * b) Then throw 6 to reach 28. c) Finally through 2 to reach 30.
         */
        boolean visited[] = new boolean[N];
        for (int i = 0; i < N; i++) {
            visited[i] = false;// Mark all the nodes as not visited..
        }
        // use bfs traversal
        Queue<QueueEntry> queue = new LinkedList<QueueEntry>();// Must use Queue Interface, LinkedList not working
                                                               // properly..
        visited[0] = true;// mark the source vertex as visited.
        queue.add(new QueueEntry(0, 0));// distance from vertex 0 to source vertex is 0
        QueueEntry qe = null;
        while (!queue.isEmpty()) {
            // as long as the queue is not empty..
            qe = queue.peek();// get the next node in the queue
            int v = qe.v;// vertex number of queueentry

            if (v == N - 1) {
                break;// if reached the destination, then break from the loop
                // qe.dist will be having the number of throws to reach the destination..
            }
            queue.remove();

            for (int j = v + 1; j <= v + 6 && j < N; ++j) {
                // Dice -> Traversing from 1 to 6
                if (!visited[j]) {// only if the node is not visited, do the following..
                    QueueEntry aNode = new QueueEntry();
                    aNode.dist = (qe.dist + 1);// Only one throw needed to reach the 6 cell..
                    visited[j] = true;// Marking the node as visited.

                    if (move[j] != -1) {// either a ladder or snake.
                        aNode.v = move[j];// if this dice is thrown, and it's a snake or ladder, you will climb up or
                                          // down to the move[j] element.
                    } else {
                        aNode.v = j;
                    }
                    queue.add(aNode);// push the aNode to the queue..
                }
            }
        }
        return qe.dist;// number of min throws needed to reach the destination..

    }

    static void minCashFlow(int graph[][], int N) {
        /**
         * // graph[i][j] indicates the amount that person i needs to // pay person j
         * int amount[][] = new int[][]{ {0, 1000, 2000},
         * 
         * {0, 0, 5000},
         * 
         * {0, 0, 0}};
         * 
         * Calculate net amount. Net amount is credit - debit. Each element indicate the
         * debt to be paid.
         * 
         * Net Amount A: -3000, B: -4000, C: 7000 Calculate max credit, and max debitor.
         * C is max creditor, B is max debitor. Take absolute min of these two: 4k
         * Settle the 4k amount subtracting from max creditor, and additing it to max
         * debitor Now, A: -3000, B: 0, C: 3000 Do the recursion again.
         */
        // given a matrix of i, j where i pays to j
        // find the minimum cash flow among them to settle the debts.
        int[] amount = new int[N];// N is the number of persons.
        // Calculate the net amount, Net Amount for each person is Credit - Debit of a
        // person
        for (int i = 0; i < N; i++) {
            amount[i] = 0;
        }
        for (int p = 0; p < N; p++) {
            // p is the person for whom the net amount has to be calculated.
            for (int i = 0; i < N; i++) {
                amount[p] += (graph[i][p]// credit
                        - graph[p][i])// debit
                ;
            }
        }
        minCashFlowRecur(amount);
    }

    static int getMaxIndex(int amount[]) {
        int maxIndex = 0;
        for (int i = 0; i < amount.length; i++) {
            if (amount[i] > amount[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    static int getMinIndex(int amount[]) {
        int minIndex = 0;
        for (int i = 0; i < amount.length; i++) {
            if (amount[i] < amount[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    static int minOf2(int a, int b) {
        return (a < b) ? a : b;
    }

    /*
     * The idea is to use Greedy algorithm where at every step, settle all amounts
     * of one person and recur for remaining n-1 persons. How to pick the first
     * person? To pick the first person, calculate the net amount for every person
     * where net amount is obtained by subtracting all debts (amounts to pay) from
     * all credits (amounts to be paid).
     * 
     * Once net amount for every person is evaluated, find two persons with maximum
     * and minimum net amounts. These two persons are the most creditors and
     * debtors. The person with minimum of two is our first person to be settled and
     * removed from list. Let the minimum of two amounts be x. We pay ‘x’ amount
     * from the maximum debtor to maximum creditor and settle one person.
     * 
     * If x is equal to the maximum debit, then maximum debtor is settled, else
     * maximum creditor is settled.
     */
    static void minCashFlowRecur(int amount[]) {
        // now that we have calculated the net amount.
        // Get the max creditor
        int maxCreditor = getMaxIndex(amount);
        int maxDebitor = getMinIndex(amount);

        if (amount[maxCreditor] == 0 && amount[maxDebitor] == 0) {// if both amounts are 0, then all the amounts are
                                                                  // settled.
            return;
        }

        int min = minOf2(-amount[maxDebitor], amount[maxCreditor]);// find the minimum amount among the 2
        amount[maxCreditor] -= min;// settle the amount to creditor from debitor
        amount[maxDebitor] += min;// add the min amount to debitor - debitor will be 0
        System.out.println("Person " + maxDebitor + " pays amount " + min + " to person " + maxCreditor);

        minCashFlowRecur(amount);
    }

    String dictionary[] = { "GEEKS", "FOR", "QUIZ", "GO" };

    boolean isWord(String str) {// if the string is a word, then return true.
        for (int i = 0; i < dictionary.length; i++) {
            if (str.equalsIgnoreCase(dictionary[i])) {
                return true;
            }
        }
        return false;
    }

    void findWordsUtil(char boggle[][], boolean visited[][], int i, int j, StringBuilder str) {
        visited[i][j] = true;// mark this cell as visited
        // where [i][j] is the cell, whose char we are going to start with for finding
        // the words..
        // Do DFS..
        str.append(boggle[i][j]);

        if (isWord(str.toString())) {
            System.out.println(str);
        }
        // Traverse through eight adjacent cells
        for (int row = i - 1; row <= i + 1 && row < boggle.length; row++) {// boggle.length is M
            for (int col = j - 1; col <= j + 1 && col < boggle[i].length; col++) {// boggle[i].length is N
                if (row >= 0 && col >= 0 && !visited[row][col]) {// shouldn't have been visited
                    findWordsUtil(boggle, visited, row, col, str);
                }
            }
        }
        str.deleteCharAt(str.length() - 1);// deleting the character appended in this recursive function
        visited[i][j] = false;// resetting the visited propery
    }

    void findWords(char boggle[][], int m, int n) {
        // from a boggle, find the words, check if it's present in dictionary
        /*
         * Given a dictionary, a method to do lookup in dictionary and a M x N board
         * where every cell has one character.
         * 
         * Find all possible words that can be formed by a sequence of adjacent
         * characters
         * 
         * Note that we can move to any of 8 adjacent characters,
         * 
         * but a word should not have multiple instances of same cell.
         * 
         * Example:
         * 
         * Input: dictionary[] = {"GEEKS", "FOR", "QUIZ", "GO"}; boggle[][] =
         * {{'G','I','Z'}, {'U','E','K'}, {'Q','S','E'}}; isWord(str): returns true if
         * str is present in dictionary else false.
         * 
         * Output: Following words of dictionary are present GEEKS QUIZ
         * 
         */
        boolean visited[][] = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j] = false;
            }
        } // mark all the cells as not visited
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                findWordsUtil(boggle, visited, i, j, str);
            }
        }
    }

    // The function to do DFS traversal. It uses recursive DFSUtil()
    int countTreesOrConnectedComponentsOfGraphUsingDFS() {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        boolean visited[] = new boolean[this.vertices];
        int res = 0;

        // Call the recursive helper function to print DFS traversal
        // starting from all vertices one by one
        for (int i = 0; i < this.vertices; ++i) {
            if (visited[i] == false) {
                DFSUtil(i, visited);
                res++;
            }
        }
        return res;
    }

    static class MinimumInitialVerticesToReachWholeMatrix {
        // Key point: In a matrix, find the starting indexes (the larger...smaller
        // elements), and do DFS traversals to completely cover
        // the matrix
        /**
         * We are given a matrix that contains different values in each cell. Our aim is
         * to find the minimal set of positions in the matrix such that the entire
         * matrix can be traversed starting from the positions in the set. We can
         * traverse the matrix under the below conditions:
         * 
         * We can move only to those neighbors that contain values less than or equal to
         * the current cell’s value. A neighbor of the cell is defined as the cell that
         * shares a side with the given cell. Examples:
         * 
         * Input : {1 2 3} {2 3 1} {1 1 1} Output : {1 1} {0 2} If we start from 1, 1 we
         * can cover 6 vertices in the order (1, 1) -> (1, 0) -> (2, 0) -> (2, 1) -> (2,
         * 2) -> (1, 2). We cannot cover the entire matrix with this vertex. Remaining
         * vertices can be covered (0, 2) -> (0, 1) -> (0, 0).
         * 
         * Input : 3 3 1 1 Output : 0 1 If we start from 0, 1, we can traverse the
         * entire matrix from this single vertex in this order (0, 0) -> (0, 1) -> (1,
         * 1) -> (1, 0). Traversing the matrix in this order satisfies all the
         * conditions stated above. From the above examples, we can easily identify that
         * in order to use a minimum number of positions, we have to start from the
         * positions having the highest cell value. Therefore, we pick the positions
         * that contain the highest value in the matrix. We take the vertices having the
         * highest value in a separate array. We perform DFS at every vertex starting
         * from the highest value. If we encounter any unvisited vertex during dfs then
         * we have to include this vertex in our set. When all the cells have been
         * processed, then the set contains the required vertices.
         * 
         * How does this work? We need to visit all vertices and to reach the largest
         * values we must start with them. If the two largest values are not adjacent,
         * then both of them must be picked. If the two largest values are adjacent,
         * then any of them can be picked as moving to equal value neighbors is allowed.
         */

        /**
         * 1>Sort all the cell values descendingly on weight.
         * 
         * 2>Create a bool visited array for each cell.
         * 
         * 3>As the greatest values will give the most farthest traversals, we start DFS
         * with the greatest values and update VISITED accordingly.
         * 
         * 4>Ultimately keep doing this for all cells unless the cells have already been
         * visited in the previous DFS traversals for previous heavier cells.
         * 
         * 5>Ultimately the answer will be the number of fresh DFS traversals we did on
         * the sorted array.
         */
        static class MatNode {
            int value;
            int row, col;

            MatNode(int value, int row, int col) {
                this.value = value;
                this.row = row;
                this.col = col;
            }
        }

        public void DFSUtil(boolean visited[][], int N, int M, int n, int m, int matrix[][]) {
            visited[N][M] = true;
            // we don't have to reverse the visited property here, because we traverse from
            // the largest element..smallest element per constraint.
            if (((N - 1) >= 0) && (matrix[N - 1][M] <= matrix[N][M]) && (!visited[N - 1][M]))
                DFSUtil(visited, N - 1, M, n, m, matrix);
            if (((N + 1) < n) && (matrix[N + 1][M] <= matrix[N][M]) && (!visited[N + 1][M]))
                DFSUtil(visited, N + 1, M, n, m, matrix);
            if (((M - 1) >= 0) && (matrix[N][M - 1] <= matrix[N][M]) && (!visited[N][M - 1]))
                DFSUtil(visited, N, M - 1, n, m, matrix);
            if (((M + 1) < m) && (matrix[N][M + 1] <= matrix[N][M]) && (!visited[N][M + 1]))
                DFSUtil(visited, N, M + 1, n, m, matrix);
        }

        // n,m size of matrix nxm
        public void DFS(int n, int m, int matrix[][]) {
            boolean visited[][] = new boolean[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    visited[i][j] = false;
                }
            }

            List<MatNode> nodes = new ArrayList<MatNode>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    nodes.add(new MatNode(matrix[i][j], i, j));
                }
            }

            int N, M;
            Collections.sort(nodes, new Comparator<MatNode>() {

                @Override
                public int compare(MatNode o1, MatNode o2) {
                    return o2.value - o1.value;
                }

            });

            // for (int i = 0; i < nodes.size(); i++) {
            // System.out.print(nodes.get(i).value + " ");
            // }

            for (int i = 0; i < nodes.size(); i++) {
                N = nodes.get(i).row;
                M = nodes.get(i).col;
                if (!visited[N][M]) {
                    DFSUtil(visited, N, M, n, m, matrix);
                    System.out.println("{" + N + "," + M + "}");
                    // prints only the starting node, doesn't print the path.
                    // The assumption is the solution will always have nodes which cover all the
                    // paths in the matrix.
                }
            }
        }
    }

    static class WaterJugProblem {
        static boolean canMeasureWaterBFS(int x, int y, int z) {
            // add all the states to the queue
            // Time complexity of BFS is O(mx + ny) which can be treated as linear (Because
            // there are only 2x + 2y possible states.)
            Deque<int[]> queue = new ArrayDeque<>();
            Set<String> seen = new HashSet<>();
            queue.addLast(new int[] { 0, 0 });
            seen.add(0 + "," + 0);
            while (queue.size() > 0) {
                int[] currSearch = queue.removeFirst();
                int a = currSearch[0], b = currSearch[1]; // Current water levels of the jars x and y.
                System.out.println(a + "," + b);
                if (a + b == z)
                    return true;

                // Make all next possible moves with the current state of the jugs:

                List<int[]> nextMoves = new ArrayList<>();
                nextMoves.add(new int[] { x, b }); // Fill x.
                nextMoves.add(new int[] { a, y }); // Fill y.
                nextMoves.add(new int[] { 0, b }); // Empty x.
                nextMoves.add(new int[] { a, 0 }); // Empty y.
                nextMoves.add(new int[] { Math.max(0, a - (y - b)), Math.min(a + b, y) }); // Put x into y.
                nextMoves.add(new int[] { Math.min(a + b, x), Math.max(0, b - (x - a)) }); // Put y into x.

                for (int[] move : nextMoves) {
                    String key = move[0] + "," + move[1];
                    if (!seen.contains(key)) {
                        seen.add(key);
                        queue.addLast(move);
                    }
                }
            }
            /**
             * (0,0)
             * 
             * (4,0) (0,3)
             * 
             * (4,3) (1,3) (3,0)
             * 
             * (1,0) (3,3)
             * 
             * (0,1) (4,2)
             * 
             * (4,1) (0,2)
             */
            return false; // There is no way to measure z by using jugs x and y.
        }
    }

    static class PathInRectangeWithCircles {
        /**
         * There is a m*n rectangular matrix whose top-left(start) location is (1, 1)
         * and bottom-right(end) location is (m*n). There are k circles each with radius
         * r. Find if there is any path from start to end without touching any circle.
         * The input contains values of m, n, k, r and two array of integers X and Y,
         * each of length k. (X[i], Y[i]) is the center of ith circle. Source : Directi
         * Interview
         * 
         * Examples:
         * 
         * Input : m = 5, n = 5, k = 2, r = 1, X = {1, 3}, Y = {3, 3} Output : Possible
         * 
         * Here is a path from start to end point.
         * 
         * Input : m = 5, n = 5, k = 2, r = 1, X = {1, 1}, Y = {2, 3}. Output : Not
         * Possible
         * 
         * 
         * Check if the centre of a cell (i, j) of the rectangle comes within any of the
         * circles then do not traverse through that cell and mark that as ‘blocked’.
         * Mark rest of the cells initially as ‘unvisited’. Then use BFS to find out
         * shortest path of each cell from starting position. If the end cell is visited
         * then we will return “Possible” otherwise “Not Possible”.
         * 
         * Algorithm :
         * 
         * 
         * 
         * 1. Take an array of size m*n. Initialize all the cells to 0.
         * 
         * 2. For each cell of the rectangle check whether it comes within any circle or
         * not (by calculating the distance of that cell from each circle). If it comes
         * within any circle then change the value of that cell to -1(‘blocked’).
         * 
         * 3. Now, apply BFS from the starting cell and if a cell can be reached then
         * change the value of that cell to 1.
         * 
         * 4. If the value of the ending cell is 1, then return ‘Possible’, otherwise
         * return ‘Not Possible’.
         */
        /*
         * the idea is to start traversing the rectangle co-ordinates one by one from
         * each (i,j) i have 8 choices to move , for doing this i will use queue and
         * visited array , visited help in reducing same call at each (i,j) i will first
         * check wheter the point lies inside any circle or not by calculating it
         * distance from centre of each circle ,if distance is greater than r then point
         * lies outside the circle , after my queue is empty if the (x,y) co-ordinate is
         * marked as visited means it is possible to reach there
         */

        // these are the moves which i can take from any position i,j
        int dpx[] = new int[] { 1, 1, 1, -1, -1, -1, 0, 0 };
        int dpy[] = new int[] { -1, 1, 0, 1, -1, 0, -1, 1 };

        // this function is used to check wheter the i,j column lies inside the
        // rectangle or not
        boolean valid(int i, int j, int A, int B) {
            if (i < 0 || i > A || j < 0 || j > B) {
                return false;
            }
            return true;
        }

        // this function check wheter the point x,y lies inside any circle or not
        boolean inCircle(int r, int c, int K, int R, List<Integer> X, List<Integer> Y) {
            // K number of circles, R is radius.
            // Xi,Yi denotes the center of the circle
            for (int i = 0; i < K; i++) {
                int x = X.get(i) - 1; // X and Y are given as positions, we get the indexes decrementing 1.
                int y = Y.get(i) - 1;
                if (((r - x) * (r - x) + (c - y) * (c - y)) <= (R * R))
                    return true;
            }
            return false;
        }

        String findPathInRectangeWithCircleBFS(int M, int N, int K, int R, List<Integer> X, List<Integer> Y) {
            // visited array
            boolean vis[][] = new boolean[M + 1][N + 1];
            Queue<int[]> myque = new LinkedList<int[]>();
            myque.add(new int[] { 0, 0 });
            vis[0][0] = true;
            // first checking wheter the point (0,0) lies in circle or not
            if (inCircle(0, 0, K, R, X, Y))
                return "NO";

            while (!myque.isEmpty()) {
                // pick first element of queue
                int[] temp = myque.poll();
                // checking all 8 possible moves
                for (int i = 0; i < 8; i++) {
                    int r = temp[0] + dpx[i];
                    int c = temp[1] + dpy[i];
                    if (valid(r, c, M, N) && !vis[r][c] && !inCircle(r, c, K, R, X, Y)) {

                        myque.add(new int[] { r, c });
                        vis[r][c] = true;
                    }
                }
            }
            if (vis[M][N])
                return "YES";
            else
                return "NO";
        }
    }

    static class HeightOfTreeFromParentArray {
        // Recur For Ancestors of node and
        // store height of node at last
        static int fillHeightDFS(int p[], int node, int visited[], int height[]) {
            // If root node
            if (p[node] == -1) {

                // mark root node as visited
                visited[node] = 1;
                return 0;
            }

            // If node is already visited
            if (visited[node] == 1)
                return height[node];

            // Visit node and calculate its height
            visited[node] = 1;

            // recur for the parent node
            height[node] = 1 + fillHeightDFS(p, p[node], visited, height);

            // return calculated height for node
            return height[node];
        }

        static int findHeightUsingDFS(int parent[], int n) {
            // To store max height
            int ma = 0;

            // To check whether or not node is visited before
            int[] visited = new int[n];

            // For Storing Height of node
            int[] height = new int[n];

            for (int i = 0; i < n; i++) {
                visited[i] = 0;
                height[i] = 0;
            }

            for (int i = 0; i < n; i++) {

                // If not visited before
                if (visited[i] != 1)

                    height[i] = fillHeightDFS(parent, i, visited, height);

                // store maximum height so far
                ma = Math.max(ma, height[i]);
            }
            return ma;
        }
    }

    static class MinimumEdgesBwnVerticesBFS {
        static int minEdgesBFS(Graph g, int u, int v, int n) {
            // BFS traversal will always find the shortest path, or the minimum edge to
            // reach a destination.
            boolean[] visited = new boolean[g.vertices];// false by default
            int[] distance = new int[g.vertices];// 0 by default
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.add(u);
            while (!queue.isEmpty()) {
                int x = queue.poll();
                List<Integer> adjList = g.arr[x];
                for (int i = 0; i < adjList.size(); i++) {
                    int adjNode = adjList.get(i);
                    if (!visited[adjNode]) {
                        distance[adjNode] = distance[x] + 1;
                        visited[adjNode] = true;
                        queue.add(adjNode);
                    }
                }
            }
            return distance[v];
        }
    }

    static class NodesWithinKDistanceFromSetofNodes {
        static int bfsWithDistance(Graph g, int source, boolean marked[], Vector<Integer> dist) {
            // returns the largest marked distance node from source.
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.add(source);
            dist.setElementAt(0, source); // mark this node as visited and set the distnace to 0
            int lastTraversedMarkedNode = -1;
            while (!queue.isEmpty()) {
                int currentNode = queue.poll();
                if (marked[currentNode]) {
                    // This returns the largest marked distant node.
                    lastTraversedMarkedNode = currentNode;
                }
                List<Integer> adjList = g.arr[currentNode];
                for (int i = 0; i < adjList.size(); i++) {
                    Integer adjNode = adjList.get(i);
                    if (dist.get(adjNode) == -1) {
                        dist.setElementAt(dist.get(currentNode) + 1, adjNode);// visited is true, set to distance
                                                                              // from source
                        queue.add(adjNode);
                    }
                }
            }
            return lastTraversedMarkedNode;
        }

        static Vector<Integer> init(int V) {
            return new Vector<Integer>(Collections.nCopies(V, -1));
        }

        static int nodesWithinKDistance(Graph g, int[] markedNodes, int K) {
            /**
             * Given an undirected tree with some marked nodes and a positive number K. We
             * need to print the count of all such nodes which have distance from all marked
             * nodes less than K that means every node whose distance from all marked nodes
             * is less than K, should be counted in the result. Examples:
             * 
             * Image: https://media.geeksforgeeks.org/wp-content/uploads/nodeAtKDistance.png
             * 
             * In above tree we can see that node with index 0, 2, 3, 5, 6, 7 have distances
             * less than 3 from all the marked nodes. so answer will be 6
             * 
             * 
             */
            /**
             * We can solve this problem using breadth first search. Main thing to observe
             * in this problem is that if we find two marked nodes which are at largest
             * distance from each other considering all pairs of marked nodes then if a node
             * is at a distance less than K from both of these two nodes then it will be at
             * a distance less than K from all the marked nodes because these two nodes
             * represents the extreme limit of all marked nodes, if a node lies in this
             * limit then it will be at a distance less than K from all marked nodes
             * otherwise not.
             * 
             * As in above example, node-1 and node-4 are most distant marked node so nodes
             * which are at distance less than 3 from these two nodes will also be at
             * distance less than 3 from node 2 also. Now first distant marked node we can
             * get by doing a bfs from any random node, second distant marked node we can
             * get by doing another bfs from marked node we just found from the first bfs
             * and in this bfs we can also found distance of all nodes from first distant
             * marked node and to find distance of all nodes from second distant marked node
             * we will do one more bfs, so after doing these three bfs we can get distance
             * of all nodes from two extreme marked nodes which can be compared with K to
             * know which nodes fall in K-distance range from all marked nodes.
             */
            boolean marked[] = new boolean[g.vertices];
            for (int i = 0; i < markedNodes.length; i++) {
                marked[markedNodes[i]] = true;
            }

            // Lists to store distances.
            int V = g.vertices + 1;
            Vector<Integer> temp = init(V);

            int leftDistantNode = bfsWithDistance(g, 0, marked, temp);

            Vector<Integer> distancesFromLeftNode = init(V);

            int rightDistantNode = bfsWithDistance(g, leftDistantNode, marked, distancesFromLeftNode);

            Vector<Integer> distancesFromRightNode = init(V);

            bfsWithDistance(g, rightDistantNode, marked, distancesFromRightNode);

            int res = 0;
            for (int i = 0; i < V - 1; i++) {
                if (distancesFromRightNode.get(i) <= K && distancesFromLeftNode.get(i) <= K) {
                    res++;
                }
            }
            return res;
        }
    }

    static class CountNonAccessiblePairOfPositionsInMatrixUsingDFS {

        int k;

        // Counts number of vertices connected
        // in a component containing x.
        // Stores the count in k.
        void dfs(Graph graph, boolean visited[], int x) {
            for (int i = 0; i < graph.arr[x].size(); i++) {
                // traverse adjacency list
                if (!visited[graph.arr[x].get(i)]) {
                    // Incrementing the number of node in
                    // a connected component.
                    (k)++;

                    visited[graph.arr[x].get(i)] = true;
                    dfs(graph, visited, graph.arr[x].get(i));
                }
            }
        }

        // Return the number of count of non-accessible cells.
        int countNonAccessibleUsingDFS(Graph graph, int N) {
            /**
             * Given a positive integer N. Consider a matrix of N X N. No cell can be
             * accessible from any other cell, except the given pair cell in the form of
             * (x1, y1), (x2, y2) i.e there is a path (accessible) between (x2, y2) to (x1,
             * y1). The task is to find the count of pairs (a1, b1), (a2, b2) such that cell
             * (a2, b2) is not accessible from (a1, b1). Examples:
             * 
             * Image: https://media.geeksforgeeks.org/wp-content/uploads/matrixAcessible.png
             * 
             * Input : N = 2 Allowed path 1: (1, 1) (1, 2) Allowed path 2: (1, 2) (2, 2)
             * 
             * Output : 6 Cell (2, 1) is not accessible from any cell and no cell is
             * accessible from it.
             * 
             * Inaccessible paths are:
             * 
             * (1, 1) - (2, 1) (1, 2) - (2, 1) (2, 2) - (2, 1) (2, 1) - (1, 1) (2, 1) - (1,
             * 2) (2, 1) - (2, 2)
             * 
             * 
             */
            boolean[] visited = new boolean[N * N + N];
            // The matrix has 4 elements, since we set the vertex 4 in visited array in DFS,
            // we will need more space, so [N*N+N]

            int ans = 0;
            for (int i = 1; i <= N * N; i++) {
                if (!visited[i]) {
                    visited[i] = true;

                    // Initialize count of connected
                    // vertices found by DFS starting
                    // from i.
                    k = 1;
                    dfs(graph, visited, i);

                    // Update result
                    ans += k * (N * N - k);
                    // 3 * (4 - 3) = 3 * 1 = 3 on DFS tree (1). 3 nodes in the tree are
                    // disconnnected from 1 node in the other tree.
                    // 1 * (4 - 1) = 1 * 3 = 3 on DFS tree (3), 1 node is disconnected from all the
                    // remaining elements in the matrix.
                }
            }
            return ans;
        }

        // Inserting the edge between edge.
        void insertpath(Graph graph, int N, Pair a, Pair b) {
            // Mapping the cell coordinate into node number.
            // (X-1)*N + Y -> converts the co-ordinates in matrix to numbers.
            int x = (a.x - 1) * N + a.y;
            int y = (b.x - 1) * N + b.y;

            // Inserting the edge.
            graph.addEdge(x, y);
        }

        static class Pair {
            // Matrix coordinate
            int x, y;

            Pair(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }

    static class MinimumMovesToReachDestinationInMatrixUnderConstraints {
        /**
         * Given a N X N matrix (M) filled with 1 , 0 , 2 , 3 . Find the minimum numbers
         * of moves needed to move from source to destination (sink) . while traversing
         * through blank cells only. You can traverse up, down, right and left. A value
         * of cell 1 means Source. A value of cell 2 means Destination. A value of cell
         * 3 means Blank cell. A value of cell 0 means Blank Wall.
         * 
         * Note : there is only single source and single destination.they may be more
         * than one path from source to destination(sink).each move in matrix we
         * consider as ‘1’
         * 
         * Examples:
         * 
         * Input : M[3][3] = {{ 0 , 3 , 2 }, { 3 , 3 , 0 }, { 1 , 3 , 0 }}; Output : 4
         * 
         * Input : M[4][4] = {{ 3 , 3 , 1 , 0 }, { 3 , 0 , 3 , 3 }, { 2 , 3 , 0 , 3 }, {
         * 0 , 3 , 3 , 3 }}; Output : 4
         */

        /**
         * The idea is to use a Level graph ( Breadth First Traversal ). Consider each
         * cell as a node and each boundary between any two adjacent cells be an edge.
         * so the total number of Node is N*N.
         * 
         * 1. Create an empty Graph having N*N node ( Vertex ). 2. Push all nodes into a
         * graph. 3. Note down the source and sink vertices. 4. Now Apply level graph
         * concept ( that we achieve using BFS). In which we find the level of every
         * node from the source vertex. After that, we return ‘Level[d]’ ( d is the
         * destination ). (which is the minimum move from source to sink )
         * 
         */

        static int BFS(Graph g, int s, int d) {
            // Base case
            if (s == d)
                return 0;

            // make initial distance of all vertex -1
            // from source
            int[] distance = new int[g.vertices];// N * N + 2
            for (int i = 0; i < g.vertices; i++)
                distance[i] = -1;

            // Create a queue for BFS
            Queue<Integer> queue = new LinkedList<Integer>();

            // Mark the source node level[s] = '0'
            distance[s] = 0;
            queue.add(s);

            // it will be used to get all adjacent
            // vertices of a vertex
            while (!queue.isEmpty()) {
                // Dequeue a vertex from queue
                s = queue.poll();

                // Get all adjacent vertices of the
                // dequeued vertex s. If a adjacent has
                // not been visited ( level[i] < '0') ,
                // then update level[i] == parent_level[s] + 1
                // and enqueue it
                for (int i = 0; i < g.arr[s].size(); i++) {
                    // Else, continue to do BFS
                    int adjNode = g.arr[s].get(i);
                    if (distance[adjNode] < 0 || distance[adjNode] > distance[s] + 1) {
                        distance[adjNode] = distance[s] + 1;
                        queue.add(adjNode);
                    }
                }

            }
            // return minimum moves from source to sink
            return distance[d];
        }

        static int res = Integer.MAX_VALUE;// This has to be here.

        static int DFS(Graph g, int s, int d, boolean visited[]) {
            // Returns the minimum number of moves.

            // Base condition for the recursion
            if (s == d)
                return 0;

            // Initializing the result
            visited[s] = true;
            for (int i = 0; i < g.arr[s].size(); i++) {
                int adjNode = g.arr[s].get(i);
                if (!visited[adjNode])

                    // comparing the res with
                    // the result of DFS
                    // to get the minimum moves
                    res = Math.min(res, 1 + DFS(g, adjNode, d, visited));
            }
            return res;
        }

        static boolean isSafe(int i, int j, int M[][]) {
            int N = M[0].length;
            if ((i < 0 || i >= N) || (j < 0 || j >= N) || M[i][j] == 0)
                return false;
            return true;
        }

        // Returns minimum numbers of moves from a source (a
        // cell with value 1) to a destination (a cell with
        // value 2)
        static int MinimumPath(int M[][], int N) {
            int s = -1, d = -1; // source and destination
            int V = N * N + 2;

            Graph g = new Graph(V);

            // create graph with n*n node
            // each cell consider as node
            int k = 1; // Number of current vertex
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (M[i][j] != 0) { // Ignore the walls.
                        // connect all 4 adjacent cell to
                        // current cell
                        if (isSafe(i, j + 1, M))
                            g.addEdge(k, k + 1);
                        if (isSafe(i, j - 1, M))
                            g.addEdge(k, k - 1);
                        if (j < N - 1 && isSafe(i + 1, j, M))
                            g.addEdge(k, k + N);
                        if (i > 0 && isSafe(i - 1, j, M))
                            g.addEdge(k, k - N);
                    }

                    // source index
                    if (M[i][j] == 1)
                        s = k;

                    // destination index
                    if (M[i][j] == 2)
                        d = k;
                    k++;
                }
            }

            // find minimum moves
            // return BFS(g, s, d);
            boolean[] visited = new boolean[V];
            return DFS(g, s, d, visited);
        }
    }

    static class MinimumStepsToReachTargetByKnight {
        // Class for storing a cell's data
        static class cell {
            int x, y;
            int dis;

            public cell(int x, int y, int dis) {
                this.x = x;
                this.y = y;
                this.dis = dis;
            }
        }

        // Utility method returns true if (x, y) lies
        // inside Board
        static boolean isInside(int x, int y, int N) {
            if (x >= 1 && x <= N && y >= 1 && y <= N)
                return true;
            return false;
        }

        // Method returns minimum step
        // to reach target position
        static int minStepToReachTarget(int knightPos[], int targetPos[], int N) {
            /**
             * Given a square chessboard of N x N size, the position of Knight and position
             * of a target is given. We need to find out the minimum steps a Knight will
             * take to reach the target position. Examples:
             * 
             * 
             * In above diagram Knight takes 3 step to reach from (4, 5) to (1, 1) (4, 5) ->
             * (5, 3) -> (3, 2) -> (1, 1) as shown in diagram
             */
            /**
             * Approach: This problem can be seen as shortest path in unweighted graph.
             * Therefore we use BFS to solve this problem. We try all 8 possible positions
             * where a Knight can reach from its position. If reachable position is not
             * already visited and is inside the board, we push this state into queue with
             * distance 1 more than its parent state. Finally we return distance of target
             * position, when it gets pop out from queue. Below code implements BFS for
             * searching through cells, where each cell contains its coordinate and distance
             * from starting node. In worst case, below code visits all cells of board,
             * making worst-case time complexity as O(N^2)
             */
            // x and y direction, where a knight can move
            int dx[] = { -2, -1, 1, 2, -2, -1, 1, 2 };
            int dy[] = { -1, -2, -2, -1, 1, 2, 2, 1 };

            // queue for storing states of knight in board
            Queue<cell> q = new LinkedList<>();

            // push starting position of knight with 0 distance
            q.add(new cell(knightPos[0], knightPos[1], 0));

            cell t;
            int x, y;
            boolean visit[][] = new boolean[N + 1][N + 1];

            // make all cell unvisited
            for (int i = 1; i <= N; i++)
                for (int j = 1; j <= N; j++)
                    visit[i][j] = false;

            // visit starting state
            visit[knightPos[0]][knightPos[1]] = true;

            // loop untill we have one element in queue
            while (!q.isEmpty()) {
                t = q.poll();

                // if current cell is equal to target cell,
                // return its distance
                if (t.x == targetPos[0] && t.y == targetPos[1])
                    return t.dis;

                // loop for all reachable states
                for (int i = 0; i < 8; i++) {
                    x = t.x + dx[i];
                    y = t.y + dy[i];

                    // If reachable state is not yet visited and
                    // inside board, push that state into queue
                    if (isInside(x, y, N) && !visit[x][y]) {
                        visit[x][y] = true;
                        q.add(new cell(x, y, t.dis + 1));
                    }
                }
            }
            return Integer.MAX_VALUE;
        }
    }

    static class ConvertNumberXtoYWithMulAndSub {
        static class Node {
            int val;
            int steps;

            Node(int val, int steps) {
                this.val = val;
                this.steps = steps;
            }
        }

        private static int minOperations(int src, int target) {
            /**
             * Given a initial number x and two operations which are given below:
             * 
             * Multiply number by 2. Subtract 1 from the number.
             * 
             * The task is to find out minimum number of operation required to convert
             * number x into y using only above two operations. We can apply these
             * operations any number of times. Constraints: 1 <= x, y <= 1000 Example:
             * 
             * Input : x = 4, y = 7 Output : 2 We can transform x into y using following two
             * operations. 1. 4*2 = 8 2. 8-1 = 7
             * 
             * Input : x = 2, y = 5 Output : 4 We can transform x into y using following
             * four operations. 1. 2*2 = 4 2. 4-1 = 3 3. 3*2 = 6 4. 6-1 = 5 Answer = 4 Note
             * that other sequences of two operations would take more operations.
             */
            /**
             * The idea is to use BFS for this. We run a BFS and create nodes by multiplying
             * with 2 and subtracting by 1, thus we can obtain all possible numbers
             * reachable from starting number. Important Points : 1) When we subtract 1 from
             * a number and if it becomes < 0 i.e. Negative then there is no reason to
             * create next node from it (As per input constraints, numbers x and y are
             * positive). 2) Also, if we have already created a number then there is no
             * reason to create it again. i.e. we maintain a visited array.
             */
            Set<Node> visited = new HashSet<>(1000);
            Queue<Node> queue = new LinkedList<Node>();

            Node node = new Node(src, 0);

            queue.offer(node);
            visited.add(node);

            while (!queue.isEmpty()) {
                Node temp = queue.poll();
                visited.add(temp);

                if (temp.val == target) {
                    return temp.steps;
                }

                int mul = temp.val * 2;
                int sub = temp.val - 1;

                // given constraints
                if (mul > 0 && mul < 1000) {
                    Node nodeMul = new Node(mul, temp.steps + 1);
                    queue.offer(nodeMul);
                }
                if (sub > 0 && sub < 1000) {
                    Node nodeSub = new Node(sub, temp.steps + 1);
                    queue.offer(nodeSub);
                }
            }
            return -1;
        }
    }

    static class CheckIfTwoNodesInNTreeAreOnSamePath {
        /**
         * Given a tree (not necessarily a binary tree) and a number of queries such
         * that every query takes two nodes of the tree as parameters. For every query
         * pair, find if two nodes are on the same path from the root to the bottom.
         * 
         * For example, consider the below tree, if given queries are (1, 5), (1, 6),
         * and (2, 6), then answers should be true, true, and false respectively.
         * 
         * Image: https://media.geeksforgeeks.org/wp-content/uploads/treesTwoNode.png
         * 
         * Note that 1 and 5 lie on the same root to leaf path, so do 1 and 6, but 2 and
         * 6 are not on the same root to leaf path.
         * 
         * It is obvious that the Depth First Search technique is to be used to solve
         * the above problem, the main problem is how to respond to multiple queries
         * fast. Here our graph is a tree which may have any number of children. Now DFS
         * in a tree, if started from root node, proceeds in a depth search manner i.e.
         * Suppose root has three children and those children have only one child with
         * them so if DFS is started then it first visits the first child of root node
         * then will go deep to the child of that node. The situation with a small tree
         * can be shown as follows:
         */
        /**
         * The order of visiting the nodes will be – 1 2 5 3 6 4 7 . Thus, other
         * children nodes are visited later until completely one child is successfully
         * visited till depth. To simplify this if we assume that we have a watch in our
         * hand, and we start walking from the root in DFS manner.
         * 
         * Intime – When we visit the node for the first time Outtime- If we again visit
         * the node later but there are no children unvisited we call it outtime,
         * 
         * Note: Any node in its sub-tree will always have intime < its children (or
         * children of children) because it is always visited first before children (due
         * to DFS) and will have outtime > all nodes in its sub-tree because before
         * noting the outtime it waits for all of its children to be marked visited.
         * 
         * For any two nodes u, v if they are in the same path then,
         * 
         * Intime[v] < Intime[u] and Outtime[v] > Outtime[u] OR Intime[u] < Intime[v]
         * and Outtime[u ]> Outtime[v]
         * 
         * If a given pair of nodes follows any of the two conditions, then they are on
         * the same root to the leaf path. Else not on the same path (If two nodes are
         * on different paths it means that no one is in the subtree of each other).
         * 
         * Example, From the diagram below to understand more, we can have some
         * examples. DFS algorithm as modified above will result in the following intime
         * and outtime for the vertex of the tree as labeled there. Now we will consider
         * all the cases.
         * 
         * * Image: https://media.geeksforgeeks.org/wp-content/uploads/treesTwoNode1.png
         * 
         * Case 1: Nodes 2 and 4: Node 2 has intime less than node 4 but since 4 is in
         * its sub tree so it will have a greater exit time than 4 . Thus, condition is
         * valid and both are on the same path. Case 2: Nodes 7 and 6: Node 7 has intime
         * less than node 6 but since both nodes are not in each other’s sub tree so
         * their exit time does not follow the required condition.
         */
        static int MAX = 100001;

        // To keep track of visited vertices in DFS
        static boolean[] visit = new boolean[MAX];

        // To store start and end time of all vertices
        // during DFS.
        static int[] intime = new int[MAX];
        static int[] outtime = new int[MAX];

        // Initially timer is zero
        static int timer = 0;

        // Does DFS of given graph and fills arrays
        // intime[] and outtime[]. These arrays are used
        // to answer given queries.
        static void dfs(Graph g, int v) {
            visit[v] = true;

            // Increment the timer as you enter
            // the recursion for v
            ++timer;

            // Upgrade the in time for the vertex
            intime[v] = timer;

            for (int i = 0; i < g.arr[v].size(); i++) {
                int adj = g.arr[v].get(i);
                if (!visit[adj])
                    dfs(g, adj);// dfs traversal on adjacency.
            }

            // Increment the timer as you exit the
            // recursion for v
            ++timer;

            // Upgrade the outtime for that node
            outtime[v] = timer;
        }

        // Returns true if 'u' and 'v' lie on
        // same root to leaf path else false.
        static boolean query(int u, int v) {
            return ((intime[u] < intime[v] && outtime[u] > outtime[v])
                    || (intime[v] < intime[u] && outtime[v] > outtime[u]));
        }
    }

    static class MinDistanceFromGuardInBank {
        /**
         * Given a matrix that is filled with ‘O’, ‘G’, and ‘W’ where ‘O’ represents
         * open space, ‘G’ represents guards and ‘W’ represents walls in a Bank. Replace
         * all of the O’s in the matrix with their shortest distance from a guard,
         * without being able to go through any walls. Also, replace the guards with 0
         * and walls with -1 in output matrix. Expected Time complexity is O(MN) for a M
         * x N matrix.
         * 
         * Examples:
         * 
         * O ==> Open Space G ==> Guard W ==> Wall
         * 
         * Input: O O O O G
         * 
         * O W W O O
         * 
         * O O O W O
         * 
         * G W W W O
         * 
         * O O O O G
         * 
         * Output: 3 3 2 1 0
         * 
         * 2 -1 -1 2 1
         * 
         * 1 2 3 -1 2
         * 
         * 0 -1 -1 -1 1
         * 
         * 1 2 2 1 0
         * 
         * The idea is to do BFS. We first enqueue all cells containing the guards and
         * loop till queue is not empty. For each iteration of the loop, we dequeue the
         * front cell from the queue and for each of its four adjacent cells, if cell is
         * an open area and its distance from guard is not calculated yet, we update its
         * distance and enqueue it. Finally after BFS procedure is over, we print the
         * distance matrix.
         * 
         * Below are implementation of above idea –
         * 
         * 
         */

        // Similar to rotten oranges problem.

        // Store dimensions of the matrix
        int M = 5;
        int N = 5;

        class Node {
            int i, j, dist;

            Node(int i, int j, int dist) {
                this.i = i;
                this.j = j;
                this.dist = dist;
            }
        }

        // These arrays are used to get row
        // and column numbers of 4 neighbors
        // of a given cell
        int row[] = { -1, 0, 1, 0 };
        int col[] = { 0, 1, 0, -1 };

        // Return true if row number and
        // column number is in range
        boolean isValid(int i, int j) {
            if ((i < 0 || i > M - 1) || (j < 0 || j > N - 1))
                return false;

            return true;
        }

        // Return true if current cell is
        // an open area and its distance
        // from guard is not calculated yet
        boolean isSafe(int i, int j, char matrix[][], int output[][]) {
            if (matrix[i][j] == 'O' && output[i][j] == -1)
                return true;

            return false;
        }

        // Function to replace all of the O's
        // in the matrix with their shortest
        // distance from a guard
        void findDistance(char matrix[][]) {
            int output[][] = new int[M][N];
            Queue<Node> q = new LinkedList<Node>();

            // Finding Guards location and
            // adding into queue
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {

                    // Initialize each cell as -1
                    output[i][j] = -1;

                    if (matrix[i][j] == 'G') {
                        q.add(new Node(i, j, 0));

                        // Guard has 0 distance
                        output[i][j] = 0;
                    }
                }
            }

            // Do till queue is empty
            while (!q.isEmpty()) {

                // Get the front cell in the queue
                // and update its adjacent cells
                Node curr = q.peek();
                int x = curr.i;
                int y = curr.j;
                int dist = curr.dist;

                // Do for each adjacent cell
                for (int i = 0; i < 4; i++) {

                    // If adjacent cell is valid, has
                    // path and not visited yet,
                    // en-queue it.
                    if (isValid(x + row[i], y + col[i])) {
                        if (isSafe(x + row[i], y + col[i], matrix, output)) {
                            output[x + row[i]][y + col[i]] = dist + 1;
                            q.add(new Node(x + row[i], y + col[i], dist + 1));
                        }
                    }
                }

                // Dequeue the front cell as
                // its distance is found
                q.poll();
            }

            // Print output matrix
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print(output[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    static class SumOfMinElementsInAllConnectedComponentsOfGraphUsingDFS {
        /**
         * Given an array A of N numbers where Ai represent the value of the (i+1)th
         * node. Also given are M pair of edges where u and v represent the nodes that
         * are connected by an edge. The task is to find the sum of the minimum element
         * in all the connected components of the given undirected graph. If a node has
         * no connectivity to any other node, count it as a component with one node.
         * 
         * Examples:
         * 
         * A indicates the position of elements in the array. Min sum of connected
         * components is dependent on the value of the positions in the array. Input:
         * a[] = {1, 6, 2, 7, 3, 8, 4, 9, 5, 10} m = 5
         * 
         * 1 2
         * 
         * 3 4
         * 
         * 5 6
         * 
         * 7 8
         * 
         * 9 10
         * 
         * Output: 15 Connected components are: 1–2 (1), 3–4 (2), 5–6 (3), 7–8(4) and
         * 9–10(5) Sum of Minimum of all them : 1 + 2 + 3 + 4 + 5 = 15
         * 
         * Input: a[] = {2, 5, 3, 4, 8} m = 2
         * 
         * 1 4
         * 
         * 4 5 Image link:
         * https://media.geeksforgeeks.org/wp-content/cdn-uploads/Screen-Shot-2018-06-22-at-1.11.51-PM.png
         */
        // DFS function that visits all
        // connected nodes from a given node
        static void dfs(int node, int a[], Graph g, int mini, boolean[] visited) {
            // Stores the minimum
            mini = Math.min(mini, a[node]);// get the node value from the node index.

            // Marks node as visited
            visited[node] = true;

            // Traversed in all the connected nodes
            for (int adjNode : g.arr[node + 1]) {
                // The data stored in the graph stores the indexes as such 1...10, but the node
                // passed in to the method comes from the array a, whose indexes start 0...9, so
                // we will need to increment here, and decrement in dfs call
                if (!visited[adjNode])
                    dfs(adjNode - 1, a, g, mini, visited);
            }
        }

        // Function that returns the sum of all minimums
        // of connected componenets of graph
        static int minimumSumConnectedComponents(int a[], Graph g, int n) {
            // Initially sum is 0
            int sum = 0;

            boolean visited[] = new boolean[n + 1];
            // Traverse for all nodes
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    int mini = a[i];
                    dfs(i, a, g, mini, visited);
                    sum += mini;
                }
            }
            // Returns the answer
            return sum;
        }
    }

    static class PrintAllPathsFromSourceToDestinationInGraphOrNTree_BFSandDFS {
        /**
         * Given a directed graph, a source vertex ‘s’ and a destination vertex ‘d’,
         * print all paths from given ‘s’ to ‘d’. Consider the following directed graph.
         * Let the s be 2 and d be 3. There are 3 different paths from 2 to 3.
         * 
         * Image: https://media.geeksforgeeks.org/wp-content/cdn-uploads/allPaths.png
         * 
         * 
         */
        /**
         * DFS
         * 
         * Approach:
         * 
         * 
         * 1. The idea is to do Depth First Traversal of given directed graph. 2. Start
         * the DFS traversal from source. 3. Keep storing the visited vertices in an
         * array or HashMap say ‘path[]’. 4. If the destination vertex is reached, print
         * contents of path[]. 5. The important thing is to mark current vertices in the
         * path[] as visited also so that the traversal doesn’t go in a cycle.
         */
        // Prints all paths from
        // 's' to 'd'
        private static void printAllPathsUsingDFS(Graph g, int s, int d) {
            boolean[] visited = new boolean[g.vertices + 1];
            ArrayList<Integer> pathList = new ArrayList<>();

            // add source to path[]
            pathList.add(s);

            // Call recursive utility
            printAllPathsUtil(g, s, d, visited, pathList);
        }

        // A recursive function to print
        // all paths from 'u' to 'd'.
        // isVisited[] keeps track of
        // vertices in current path.
        // localPathList<> stores actual
        // vertices in the current path
        static void printAllPathsUtil(Graph g, Integer u, Integer d, boolean[] visited, List<Integer> localPathList) {

            if (u.equals(d)) {
                System.out.println(localPathList);
                // if match found then no need to traverse more till depth
                return;
            }

            // Mark the current node
            visited[u] = true;

            // Recur for all the vertices
            // adjacent to current vertex
            for (Integer i : g.arr[u]) {
                if (!visited[i]) {
                    // store current node
                    // in path[]
                    localPathList.add(i);
                    printAllPathsUtil(g, i, d, visited, localPathList);

                    // remove current node
                    // in path[]
                    localPathList.remove(i);
                }
            }
            // Mark the current node
            visited[u] = false; // This is must and it allows printing nodes which participate in more than one
                                // path.
        }

        /**
         * create a queue which will store path(s) of type vector initialise the queue
         * with first path starting from src
         * 
         * 1. Now run a loop till queue is not empty
         * 
         * 2. get the frontmost path from queue
         * 
         * 3. check if the lastnode of this path is destination
         * 
         * 4. if true then print the path
         * 
         * 5. run a loop for all the vertices connected to the current vertex i.e.
         * lastnode extracted from path
         * 
         * 6.if the vertex is not visited in current path a) create a new path from
         * earlier path and append this vertex b) insert this new path to queue
         */

        private static boolean isNotVisited(int x, List<Integer> path) {
            int size = path.size();
            for (int i = 0; i < size; i++)
                if (path.get(i) == x)
                    return false;

            return true;
        }

        static void findPathsUsingBFS(Graph g, int src, int dst) {

            // Create a queue which stores
            // the paths
            Queue<List<Integer>> queue = new LinkedList<>();
            // Create a queue which will store paths of type vector

            // Path vector to store the current path
            List<Integer> path = new ArrayList<>();
            path.add(src);
            queue.offer(path);// initialize the queue with first path starting from src.
            // queue: [2]
            while (!queue.isEmpty()) {
                path = queue.poll();
                int last = path.get(path.size() - 1);

                // If last vertex is the desired destination
                // then print the path
                if (last == dst) {
                    printPath(path);
                }

                // Traverse to all the nodes connected to
                // current vertex and push new path to queue
                List<Integer> adjNodes = g.arr[last];
                for (int i = 0; i < adjNodes.size(); i++) {
                    if (isNotVisited(adjNodes.get(i), path)) {
                        List<Integer> newpath = new ArrayList<>(path);
                        newpath.add(adjNodes.get(i));
                        queue.offer(newpath);
                    }
                    // At the end of the loop for 2, the queue will contain [20, 21], in the next

                    // iteration, queue is polled: 20, the last element is 0, it's unvisited, add
                    // it's adjacent nodes to
                    // [21 203 201] -> [203, 201, 213] -> [201, 213] -> [213, 2013] -> priint all.
                }
            }
        }

        private static void printPath(List<Integer> path) {
            int size = path.size();
            for (Integer v : path) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

    static class MinEdgeReversalToMakeARoot_Hard {
        // Count number of reverse edges.
        /**
         * Given a directed tree with V vertices and V-1 edges, we need to choose such a
         * root (from given nodes from where we can reach to every other node) with a
         * minimum number of edge reversal.
         * 
         * We can solve this problem using DFS. we start dfs at any random node of given
         * tree and at each node we store its distance from starting node assuming all
         * edges as undirected and we also store number of edges which need to be
         * reversed in the path from starting node to current node, let’s denote such
         * edges as back edges so back edges are those which point towards the node in a
         * path. With this dfs, we also calculate total number of edge reversals in the
         * tree. After this computation, at each node we can calculate ‘number of edge
         * reversal to reach every other node’ as follows,
         * 
         * Let total number of reversals in tree when some node is chosen as starting
         * node for dfs is R then if we want to reach every other node from node i we
         * need to reverse all back edges from path node i to starting node and we also
         * need to reverse all other back edges other than node i to starting node path.
         * First part will be (distance of node i from starting node – back edges count
         * at node i) because we want to reverse edges in path from node i to starting
         * node it will be total edges (i.e. distance) minus back edges from starting
         * node to node i (i.e. back edge count at node i). The second part will be
         * (total edge reversal or total back edges of tree R – back edge count of node
         * i). After calculating this value at each node we will choose minimum of them
         * as our result.
         * 
         * In below code, in the given edge direction weight 0 is added and in reverse
         * direction weight 1 is added which is used to count reversal edges in dfs
         * method.
         * 
         * REFER COMMENT FOR MORE DETAILS ON HOW THE ALGO IS WORKING
         */
        // pair class
        static class Edge {
            int nextNode, weight;

            // in case of graph, this edge stores the adjacent node and the weight. weight
            // is
            // 1 when the edge is reverse.
            Edge(int dest, int weight) {
                this.nextNode = dest;
                this.weight = weight;
            }
        }

        static class Pair {
            int distance;
            int backEdgeCount; // reversal count

            Pair(int dist, int beCount) {
                this.distance = dist;
                this.backEdgeCount = beCount;
            }
        }

        // method to dfs in tree and populates disRev values
        static int dfs(Vector<Vector<Edge>> g, Pair disRev[], boolean visit[], int u) {
            // visit current node
            visit[u] = true;
            int totalRev = 0;

            // looping over all neighbors
            Vector<Edge> adjacentEdges = g.get(u);
            for (int i = 0; i < adjacentEdges.size(); i++) {
                int v = adjacentEdges.get(i).nextNode; // adjacent node.
                // for source node (u) 1, the adjacent edges are (0, 1), (2, 1). 0 is already
                // visited. distance of 2 is set.
                if (!visit[v]) {
                    // distance of v will be one more than distance of u
                    disRev[v].distance = disRev[u].distance + 1; // increment distance of v to u + 1.

                    // initialize back edge count same as
                    // parent node's count
                    disRev[v].backEdgeCount = disRev[u].backEdgeCount; // back edge count of v is ame as u.

                    // if there is a reverse edge from u to i,
                    // then only update
                    if (adjacentEdges.get(i).weight != 0) {
                        // for example, node 1 has reversal edges in graph: (0, 1), and (2, 1), where 1
                        // indicates
                        // the reverse edges to 0 and 2. i.e. we don't have an outward edge in the given
                        // input from 1.
                        disRev[v].backEdgeCount = disRev[u].backEdgeCount + 1;
                        totalRev++;
                    }
                    totalRev += dfs(g, disRev, visit, v);
                }
            }

            // return total reversal in subtree rooted at u
            return totalRev;
        }

        // method prints root and minimum number of edge reversal
        static void printMinEdgeReverseForRootNode(int edges[][], int e) {
            // number of nodes are one more than number of edges
            int V = e + 1;

            // data structure to store directed tree
            Vector<Vector<Edge>> g = new Vector<Vector<Edge>>();

            for (int i = 0; i < V + 1; i++)
                g.add(new Vector<Edge>());

            // disRev stores two values - distance and back
            // edge count from root node
            Pair disRev[] = new Pair[V];

            for (int i = 0; i < V; i++)
                disRev[i] = new Pair(0, 0);

            boolean visit[] = new boolean[V];

            int u, v;
            for (int i = 0; i < e; i++) {
                // create an undirected graph from given edges.
                u = edges[i][0];
                v = edges[i][1];

                // add 0 weight in direction of u to v
                // i.e. Because edge u->v exists in the given input, add 0 weight.
                // for example, (2,1) edge is in the input, 2-> (0,0)
                g.get(u).add(new Edge(v, 0));

                // Edge v->u doesn't exist in the given input, add 1 weight.
                // Add an edge to graph: 1 -> (2, 1)
                g.get(v).add(new Edge(u, 1));
            }

            // initialize all variables
            for (int i = 0; i < V; i++) {
                visit[i] = false;
                disRev[i].distance = disRev[i].backEdgeCount = 0;
            }

            int root = 0;

            // dfs populates disRev data structure and
            // store total reverse edge counts
            int totalRev = dfs(g, disRev, visit, root);

            // UnComment below lines to print each node's
            // distance and edge reversal count from root node
            for (int i = 0; i < V; i++)
                System.out.println(i + " : " + disRev[i].distance + " " + disRev[i].backEdgeCount);
            /**
             * For the input, this will print
             * 
             * 0: 0 0
             * 
             * 1: 1 0
             * 
             * 2: 2 1
             * 
             * 3: 3 2
             * 
             * 4: 4 2
             * 
             * 5: 5 3
             * 
             * 6: 6 3
             * 
             * 7: 7 4
             * 
             * Total reverse edge is 4.
             * 
             * Image link:
             * https://media.geeksforgeeks.org/wp-content/uploads/20210108221118/edgeReverseRoot-300x101.png
             */
            int res = Integer.MAX_VALUE;

            // loop over all nodes to choose minimum edge reversal
            for (int i = 0; i < V; i++) {
                // (reversal in path to i) + (reversal
                // in all other tree parts)

                // REVERSAL in path to I: i.e. at node 3, distance is 3 (from source node 0),
                // and backedge count is 2.
                // If 3 is the root node, the number of edges to be reversed before the node 3
                // is (distance - backedge count) at node i i.e. 3 - 2 = 1. The edge 0->1 needs
                // to be reversed.

                // REVERSAL in all OTHER tree parts: The number of reversals required after the
                // node 3 is totalReversals - backedge count at node 3. i.e. 4 - 2 = 2. The
                // edges 5->4, 7->6 should be reversed.
                int edgesToRev = (totalRev - disRev[i].backEdgeCount) + (disRev[i].distance - disRev[i].backEdgeCount);

                // choose minimum among all values
                if (edgesToRev < res) {
                    res = edgesToRev;
                    root = i;
                }
            }

            // print the designated root and total
            // edge reversal made
            System.out.println(root + " " + res);// The result is 3 3
        }
    }

    static class MinStepsToReachEndOfArrayUnderConstraints {
        /**
         * Given an array containing one digit numbers only, assuming we are standing at
         * first index, we need to reach to end of array using minimum number of steps
         * where in one step, we can jump to neighbor indices or can jump to a position
         * with same value. In other words, if we are at index i, then in one step you
         * can reach to, arr[i-1] or arr[i+1] or arr[K] such that arr[K] = arr[i] (value
         * of arr[K] is same as arr[i]) Examples:
         * 
         * 
         * Input : arr[] = {5, 4, 2, 5, 0} Output : 2 Explanation : Total 2 step
         * required. We start from 5(0), in first step jump to next 5 and in second step
         * we move to value 0 (End of arr[]).
         * 
         * Input : arr[] = [0, 1, 2, 3, 4, 5, 6, 7, 5, 4, 3, 6, 0, 1, 2, 3, 4, 5, 7]
         * Output : 5 Explanation : Total 5 step required. 0(0) -> 0(12) -> 6(11) ->
         * 6(6) -> 7(7) -> (18) (inside parenthesis indices are shown)
         * 
         * This problem can be solved using BFS. We can consider the given array as
         * unweighted graph where every vertex has two edges to next and previous array
         * elements and more edges to array elements with same values. Now for fast
         * processing of third type of edges, we keep 10 vectors which store all indices
         * where digits 0 to 9 are present. In above example, vector corresponding to 0
         * will store [0, 12], 2 indices where 0 has occurred in given array. Another
         * Boolean array is used, so that we don’t visit same index more than once. As
         * we are using BFS and BFS proceeds level by level, optimal minimum steps are
         * guaranteed.
         */

        static int getMinStepsToReachEnd(int[] arr, int N) {
            // visit boolean array checks whether
            // current index is previously visited
            boolean[] visit = new boolean[N];

            // distance array stores distance of
            // current index from starting index
            int[] distance = new int[N];

            // digit vector stores indices where a
            // particular number resides
            Vector<Integer>[] digit = new Vector[10];
            for (int i = 0; i < 10; i++)
                digit[i] = new Vector<>();

            // In starting all index are unvisited
            for (int i = 0; i < N; i++)
                visit[i] = false;

            // storing indices of each number
            // in digit vector
            for (int i = 1; i < N; i++)
                digit[arr[i]].add(i);

            // for starting index distance will be zero
            distance[0] = 0;
            visit[0] = true;

            // Creating a queue and inserting index 0.
            Queue<Integer> q = new LinkedList<>();
            q.add(0);

            // loop untill queue in not empty
            while (!q.isEmpty()) {
                // Get an item from queue, q.
                int idx = q.peek();
                q.remove();

                // If we reached to last
                // index break from loop
                if (idx == N - 1)
                    break;

                // Find value of dequeued index
                int d = arr[idx];

                // looping for all indices with value as d.
                for (int i = 0; i < digit[d].size(); i++) {
                    int nextidx = digit[d].get(i);
                    if (!visit[nextidx]) {
                        visit[nextidx] = true;
                        q.add(nextidx);

                        // update the distance of this nextidx
                        distance[nextidx] = distance[idx] + 1;
                    }
                }

                // clear all indices for digit d,
                // because all of them are processed
                digit[d].clear();

                // checking condition for previous index
                if (idx - 1 >= 0 && !visit[idx - 1]) {
                    visit[idx - 1] = true;
                    q.add(idx - 1);
                    distance[idx - 1] = distance[idx] + 1;
                }

                // checking condition for next index
                if (idx + 1 < N && !visit[idx + 1]) {
                    visit[idx + 1] = true;
                    q.add(idx + 1);
                    distance[idx + 1] = distance[idx] + 1;
                }
            }

            // N-1th position has the final result
            return distance[N - 1];
        }
    }

    static class DisplaySteppingNumbersInRange {
        /**
         * Given two integers ‘n’ and ‘m’, find all the stepping numbers in range [n,
         * m]. A number is called stepping number if all adjacent digits have an
         * absolute difference of 1. 321 is a Stepping Number while 421 is not.
         * 
         * Examples :
         * 
         * Input : n = 0, m = 21 Output : 0 1 2 3 4 5 6 7 8 9 10 12 21
         * 
         * Input : n = 10, m = 15 Output : 10, 12
         */
        // This Method checks if an integer n
        // is a Stepping Number
        public static boolean isStepNum(int n) {
            // Initialize prevDigit with -1
            int prevDigit = -1;

            // Iterate through all digits of n and compare
            // difference between value of previous and
            // current digits
            while (n > 0) {
                // Get Current digit
                int curDigit = n % 10;

                // Single digit is consider as a
                // Stepping Number
                if (prevDigit != -1) {
                    // Check if absolute difference between
                    // prev digit and current digit is 1
                    if (Math.abs(curDigit - prevDigit) != 1)
                        return false;
                }
                n /= 10;
                prevDigit = curDigit;
            }
            return true;
        }

        // A brute force approach based function to find all
        // stepping numbers.
        public static void displaySteppingNumbers(int n, int m) {
            // Iterate through all the numbers from [N,M]
            // and check if it is a stepping number.
            for (int i = n; i <= m; i++)
                if (isStepNum(i))
                    System.out.print(i + " ");
        }
    }

    static class FindLengthOfLargestRegionInBinaryBooleanMatrixUsingDFS {
        /**
         * Consider a matrix with rows and columns, where each cell contains either a
         * ‘0’ or a ‘1’ and any cell containing a 1 is called a filled cell. Two cells
         * are said to be connected if they are adjacent to each other horizontally,
         * vertically, or diagonally. If one or more filled cells are also connected,
         * they form a region. find the length of the largest region.
         * 
         * Examples:
         * 
         * Input : M[][5] = { 0 0 1 1 0
         * 
         * 1 0 1 1 0
         * 
         * 0 1 0 0 0
         * 
         * 0 0 0 0 1 }
         * 
         * Output : 6 In the following example, there are 2 regions one with length 1
         * and the other as 6. So largest region: 6
         * 
         * Input : M[][5] = { 0 0 1 1 0
         * 
         * 0 0 1 1 0
         * 
         * 0 0 0 0 0
         * 
         * 0 0 0 0 1 }
         * 
         * 
         * Output: 4 In the following example, there are 2 regions one with length 1 and
         * the other as 4. So largest region: 4
         * 
         */
        /**
         * A cell in 2D matrix can be connected to at most 8 neighbours. So in DFS, make
         * recursive calls for 8 neighbours of that cell. Keep a visited Hash-map to
         * keep track of all visited cells. Also keep track of the visited 1’s in every
         * DFS and update maximum length region.
         */
        int ROW, COL, count;

        // A function to check if a given cell (row, col)
        // can be included in DFS
        boolean isSafe(int[][] M, int row, int col, boolean[][] visited) {
            // row number is in range, column number is in
            // range and value is 1 and not yet visited
            return ((row >= 0) && (row < ROW) && (col >= 0) && (col < COL) && (M[row][col] == 1 && !visited[row][col]));
        }

        // A utility function to do DFS for a 2D boolean
        // matrix. It only considers the 8 neighbours as
        // adjacent vertices
        void DFS(int[][] M, int row, int col, boolean[][] visited) {
            // These arrays are used to get row and column
            // numbers of 8 neighbours of a given cell
            int[] rowNbr = { -1, -1, -1, 0, 0, 1, 1, 1 };
            int[] colNbr = { -1, 0, 1, -1, 1, -1, 0, 1 };

            // Mark this cell as visited
            visited[row][col] = true;

            // Recur for all connected neighbours
            for (int k = 0; k < 8; k++) {
                if (isSafe(M, row + rowNbr[k], col + colNbr[k], visited)) {
                    // increment region length by one
                    count++;
                    DFS(M, row + rowNbr[k], col + colNbr[k], visited);
                }
            }
        }

        // The main function that returns largest length region
        // of a given boolean 2D matrix
        int largestRegion(int[][] M) {
            // Make a boolean array to mark visited cells.
            // Initially all cells are unvisited
            boolean[][] visited = new boolean[ROW][COL];

            // Initialize result as 0 and traverse through the
            // all cells of given matrix
            int result = 0;
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {

                    // If a cell with value 1 is not
                    if (M[i][j] == 1 && !visited[i][j]) {

                        // visited yet, then new region found
                        count = 1;
                        DFS(M, i, j, visited);

                        // maximum region
                        result = Math.max(result, count);
                    }
                }
            }
            return result;
        }
    }

    static class FindNumberOfIslandsOrConnectedComponentsInGraphUsingDFS {
        /**
         * Given a boolean 2D matrix, find the number of islands. A group of connected
         * 1s forms an island. For example, the below matrix contains 5 islands Example:
         * 
         * 
         * Input : mat[][] = {{1, 1, 0, 0, 0}, {0, 1, 0, 0, 1}, {1, 0, 0, 1, 1}, {0, 0,
         * 0, 0, 0}, {1, 0, 1, 0, 1} Output : 5
         * 
         * This is a variation of the standard problem: “Counting the number of
         * connected components in an undirected graph”.
         * 
         * 
         * 
         * Before we go to the problem, let us understand what is a connected component.
         * A connected component of an undirected graph is a subgraph in which every two
         * vertices are connected to each other by a path(s), and which is connected to
         * no other vertices outside the subgraph. For example, the graph shown below
         * has three connected components.
         * 
         * Image:
         * https://media.geeksforgeeks.org/wp-content/uploads/connectedcomponents.png
         * 
         * A graph where all vertices are connected with each other has exactly one
         * connected component, consisting of the whole graph. Such a graph with only
         * one connected component is called a Strongly Connected Graph. The problem can
         * be easily solved by applying DFS() on each component. In each DFS() call, a
         * component or a sub-graph is visited. We will call DFS on the next un-visited
         * component. The number of calls to DFS() gives the number of connected
         * components. BFS can also be used. What is an island? A group of connected 1s
         * forms an island. For example, the below matrix contains 4 islands
         */

        // No of rows and columns
        static final int ROW = 5, COL = 5;

        // A function to check if a given cell (row, col) can
        // be included in DFS
        boolean isSafe(int M[][], int row, int col, boolean visited[][]) {
            // row number is in range, column number is in range
            // and value is 1 and not yet visited
            return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL) && (M[row][col] == 1 && !visited[row][col]);
        }

        // A utility function to do DFS for a 2D boolean matrix.
        // It only considers the 8 neighbors as adjacent vertices
        void DFS(int M[][], int row, int col, boolean visited[][]) {
            // These arrays are used to get row and column numbers
            // of 8 neighbors of a given cell
            int rowNbr[] = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
            int colNbr[] = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };

            // Mark this cell as visited
            visited[row][col] = true;

            // Recur for all connected neighbours
            for (int k = 0; k < 8; ++k)
                if (isSafe(M, row + rowNbr[k], col + colNbr[k], visited))
                    DFS(M, row + rowNbr[k], col + colNbr[k], visited);
        }

        // The main function that returns count of islands in a given
        // boolean 2D matrix
        int countIslands(int M[][]) {
            // Make a bool array to mark visited cells.
            // Initially all cells are unvisited
            boolean visited[][] = new boolean[ROW][COL];

            // Initialize count as 0 and travese through the all cells
            // of given matrix
            int count = 0;
            for (int i = 0; i < ROW; ++i)
                for (int j = 0; j < COL; ++j)
                    if (M[i][j] == 1 && !visited[i][j]) // If a cell with
                    { // value 1 is not
                      // visited yet, then new island found, Visit all
                      // cells in this island and increment island count
                        DFS(M, i, j, visited);
                        ++count;
                    }

            return count;
        }
    }

    static class CountAllPossiblePathsBetweenTwoVerticesSourceAndDestinationUsingDFS {
        /**
         * Count the total number of ways or paths that exist between two vertices in a
         * directed graph. These paths don’t contain a cycle, the simple enough reason
         * is that a cycle contains an infinite number of paths and hence they create a
         * problem. Examples:
         * 
         * Image: https://media.geeksforgeeks.org/wp-content/uploads/paths1.png
         * 
         * Input: Count paths between A and E Output : Total paths between A and E are 4
         * Explanation: The 4 paths between A and E are: A -> E A -> B -> E A -> C -> E
         * A -> B -> D -> C -> E
         * 
         * Input : Count paths between A and C Output : Total paths between A and C are
         * 2 Explanation: The 2 paths between A and C are: A -> C A -> B -> D -> C
         */
        /**
         * Algorithm:
         * 
         * Create a recursive function that takes index of node of a graph and the
         * destination index. Keep a global or a static variable count to store the
         * count. Keep a record of the nodes visited in the current path by passing a
         * visited array by value (instead of reference, which would not be limited to
         * the current path). If the current nodes is the destination increase the
         * count. Else for all the adjacent nodes, i.e. nodes that are accessible from
         * the current node, call the recursive function with the index of adjacent node
         * and the destination. Print the Count.
         */

        // A recursive method to count
        // all paths from 'u' to 'd'.

        int countPathsUtil(Graph g, int u, int d, int pathCount) {

            // If current vertex is same as
            // destination, then increment count
            if (u == d) {
                pathCount++;
            }

            // Recur for all the vertices
            // adjacent to this vertex
            else {
                Iterator<Integer> i = g.arr[u].listIterator();
                while (i.hasNext()) {
                    int adjNode = i.next();
                    // for the given input, the paths are incremented only when the target is
                    // reached.
                    // 0->3, 0->1->3, 0->2->3. On first iteration, the incremented path count is
                    // returned,
                    // which is then passed to the second iteration and so on.
                    pathCount = countPathsUtil(g, adjNode, d, pathCount);
                }
            }
            return pathCount;
        }

        // Returns count of
        // paths from 's' to 'd'
        int countPaths(Graph g, int s, int d) {

            // Call the recursive method
            // to count all paths
            int pathCount = 0;
            pathCount = countPathsUtil(g, s, d, pathCount);
            return pathCount;
        }

    }

    static class MoveWeighingScaleAlternatelyUnderGivenConstraintsUsingDFS {
        /**
         * Given a weighting scale and an array of different positive weights where we
         * have an infinite supply of each weight. Our task is to put weights on left
         * and right pans of scale one by one in such a way that pans move to that side
         * where weight is put i.e. each time, pans of scale moves to alternate sides.
         * 
         * We are given another integer ‘steps’, times which we need to perform this
         * operation. Another constraint is that we can’t put same weight consecutively
         * i.e. if weight w is taken then in next step while putting the weight on
         * opposite pan we can’t take w again.
         * 
         * Examples:
         * 
         * Let weight array is [7, 11] and steps = 3 then 7, 11, 7 is the sequence in
         * which weights should be kept in order to move scale alternatively.
         * 
         * Let another weight array is [2, 3, 5, 6] and steps = 10 then, 3, 2, 3, 5, 6,
         * 5, 3, 2, 3 is the sequence in which weights should be kept in order to move
         * scale alternatively.
         * 
         * This problem can be solved by doing DFS among scale states.
         * 
         * We traverse among various DFS states for the solution where each DFS state
         * will correspond to actual difference value between left and right pans and
         * current step count. Instead of storing weights of both pans, we just store
         * the difference residue value and each time chosen weight value should be
         * greater than this difference and shouldn’t be equal to previously chosen
         * value of weight. If it is, then we call the DFS method recursively with new
         * difference value and one more step.
         * 
         * Please see below code for better understanding,
         */
        // DFS method to traverse among
        // states of weighting scales
        static boolean dfs(int residue, int curStep, int[] wt, int[] arr, int N, int steps) {

            // If we reach to more than required steps,
            // return true
            if (curStep >= steps)
                return true;

            // Try all possible weights and
            // choose one which returns 1 afterwards
            for (int i = 0; i < N; i++) {

                /*
                 * Try this weight only if it is greater than current residue and not same as
                 * previous chosen weight
                 */
                /** If current step is 0, anyway do DFS */
                if (curStep - 1 < 0 || (arr[i] > residue && arr[i] != wt[curStep - 1])) {

                    // assign this weight to array and
                    // recur for next state
                    wt[curStep] = arr[i];
                    if (dfs(arr[i] - residue, curStep + 1, wt, arr, N, steps))
                        return true;
                }
            }

            // if any weight is not possible,
            // return false
            return false;
        }

        // method prints weights for alternating scale
        // and if not possible prints 'not possible'
        static void printWeightOnScale(int[] arr, int N, int steps) {
            int[] wt = new int[steps];

            // call dfs with current residue as 0
            // and current steps as 0
            if (dfs(0, 0, wt, arr, N, steps)) {
                for (int i = 0; i < steps; i++)
                    System.out.print(wt[i] + " ");
                System.out.println();
            } else
                System.out.println("Not Possible");
        }
    }

    static class BFSOnConnectedComponentsOfGraphOrDisconnectedGraph {
        // Helper function for BFS
        public static void bfshelp(Graph g, int s, boolean[] visited) {
            // Create a queue for BFS
            LinkedList<Integer> q = new LinkedList<>();

            // Mark the current node as visited and enqueue it
            q.add(s);
            visited[s] = true;

            while (!q.isEmpty()) {
                // Dequeue a vertex from queue and print it
                s = q.poll();
                System.out.print(s + " ");

                // Check whether the current node is
                // connected to any other node or not
                if (!g.arr[s].isEmpty()) {
                    Iterator<Integer> i = g.arr[s].listIterator();

                    // Get all adjacent vertices of the dequeued
                    // vertex f. If an adjacent has not been visited,
                    // then mark it visited and enqueue it

                    while (i.hasNext()) {
                        int adjNode = i.next();
                        if (!visited[adjNode]) {
                            visited[adjNode] = true;
                            q.add(adjNode);
                        }
                    }
                }
            }
        }

        // BFS function to check each node
        public static void bfs(Graph g) {
            int vertex = g.vertices;
            boolean[] visited = new boolean[vertex + 1];

            for (int i = 0; i < vertex; i++) {
                // Checking whether the node is visited or not
                if (!visited[i]) {
                    bfshelp(g, i, visited);
                }
            }
        }
    }

    class AdjListNode {
        int v;
        int weight;

        public AdjListNode(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }

    private boolean isUndirected = false;
    public LinkedList<AdjListNode> arrAdjNode[];

    public Graph(int v, boolean weightAssign, boolean isUndirected) {
        vertices = v;
        arrAdjNode = new LinkedList[v];
        this.isUndirected = isUndirected;
        for (int i = 0; i < v; i++) {
            arrAdjNode[i] = new LinkedList<AdjListNode>();
        }
    }

    void addEdge(int v, int w, int weight) {
        // v -> w is the edge, weight is the weight for the edge..
        AdjListNode adjNode = new AdjListNode(w, weight);
        arrAdjNode[v].add(adjNode);
        if (isUndirected) {
            arrAdjNode[w].add(new AdjListNode(v, weight));
        }
    }

    static class QueueEntry {
        int v;
        int dist; // distance from the source vertex, note it's not the weight of the edge..

        public QueueEntry(int v, int dist) {
            this.v = v;
            this.dist = dist;
        }

        public QueueEntry() {
        }
    }

    static void testDataCountTrees() {
        Graph g = new Graph(5);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(3, 4);

        System.out.println(g.countTreesOrConnectedComponentsOfGraphUsingDFS());
    }

    public static void main(String[] args) throws Exception {

        // testDataFindPathInRectangeWithCircles();
        // testDataFindHeightGivenParentArray();
        // testDataMinEdgeBwnVerticesUsingBFS();
        // testDataNodesWithinKDistanceUsingBFS();
        // testDataCountNonAccessiblePairOfPositionsInMatrix();
        // testDataMinimumMovesToReachDestinationInMatrix();
        // testDataMinStepsToReachTargetByKnightInChessMatrix();
        // testDataConvertNumberFromXtoYUsingMulandSub();
        // testDataCheckIfTwoNodesAreOnSamePath();
        // testDataFindShortestDistantFromGuardInBank();
        // testDataSumOfMinElementsInAllConnectedComponentsOfGraph();
        // testDataPrintAllPathsInAGraphOrNaryTreeUsingSearchTraversal();
        // testDataMinEdgeReversalsToMakeRoot();
        // testDataMinStepsToReachEndOfArray();
        // testDataDisplaySteppingNumbers();
        // testDataFindLengthOfLargestRegionInBinaryBooleanMatrixUsingDFS();
        // testDataCouldNumberOfIslandOrConnectedComponentsInGraph();
        // testDataCountAllPossiblePathsBetweenTwoVerticesSourceAndDestination();
        // testDataMoveWeighingScaleAlternatelyUnderGivenConstraintsUsingDFS();
        testDataBFSonConnectedComponentsOfGraphOrDisconnectedGraph();
    }

    static void testDataWaterJugProblem() {
        int Jug1 = 4, Jug2 = 3, target = 2;

        System.out.println("Path from initial state " + "to solution state ::"
                + WaterJugProblem.canMeasureWaterBFS(Jug1, Jug2, target));
    }

    static void testDataMinimumInitialVerticesToTraverseWholeMatrix() {
        int[][] matrix = { { 1, 2, 3 }, { 2, 3, 1 }, { 1, 1, 1 } };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matrix[i][j] + "\t");
            }

            System.out.println();
        }
        new MinimumInitialVerticesToReachWholeMatrix().DFS(3, 3, matrix);
    }

    static void testDataFindPathInRectangeWithCircles() {
        // Test case 1
        int m1 = 5, n1 = 5, k1 = 2, r1 = 1;
        List<Integer> X1 = Arrays.asList(1, 3);
        List<Integer> Y1 = Arrays.asList(3, 3);

        PathInRectangeWithCircles object = new PathInRectangeWithCircles();
        // Function call
        System.out.println(object.findPathInRectangeWithCircleBFS(m1, n1, k1, r1, X1, Y1));

        // Test case 2
        int m2 = 5, n2 = 5, k2 = 2, r2 = 1;
        List<Integer> X2 = Arrays.asList(1, 1);
        List<Integer> Y2 = Arrays.asList(2, 3);
        System.out.println(object.findPathInRectangeWithCircleBFS(m2, n2, k2, r2, X2, Y2));

        int m3 = 2, n3 = 3, k3 = 1, r3 = 1;
        List<Integer> X3 = Arrays.asList(2);
        List<Integer> Y3 = Arrays.asList(3);
        System.out.println(object.findPathInRectangeWithCircleBFS(m3, n3, k3, r3, X3, Y3));
    }

    static void testDataFindHeightGivenParentArray() {
        int parent[] = { -1, 0, 0, 0, 3, 1, 1, 2 };
        int n = parent.length;

        System.out.println("Height of N-ary Tree = " + HeightOfTreeFromParentArray.findHeightUsingDFS(parent, n));
    }

    static void testDataMinEdgeBwnVerticesUsingBFS() {
        // To store adjacency list of graph
        int n = 9;
        Graph g = new Graph(9, true);// undirected graph.

        g.addEdge(0, 1);
        g.addEdge(0, 7);
        g.addEdge(1, 7);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(2, 8);
        g.addEdge(3, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        int u = 0;
        int v = 5;
        System.out.println(MinimumEdgesBwnVerticesBFS.minEdgesBFS(g, u, v, n));
    }

    static void testDataNodesWithinKDistanceUsingBFS() {

        Graph g = new Graph(10, true);
        g.addEdge(1, 0);
        g.addEdge(0, 3);
        g.addEdge(0, 8);
        g.addEdge(2, 3);
        g.addEdge(3, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 5);
        g.addEdge(5, 9);

        int marked[] = { 1, 2, 4 };
        int K = 3;

        System.out.println(NodesWithinKDistanceFromSetofNodes.nodesWithinKDistance(g, marked, K));
    }

    static void testDataCountNonAccessiblePairOfPositionsInMatrix() {
        int N = 2;

        /**
         * Given a positive integer N. Consider a matrix of N X N. No cell can be
         * accessible from any other cell, except the given pair cell in the form of
         * (x1, y1), (x2, y2) i.e there is a path (accessible) between (x2, y2) to (x1,
         * y1). The task is to find the count of pairs (a1, b1), (a2, b2) such that cell
         * (a2, b2) is not accessible from (a1, b1).
         */
        CountNonAccessiblePairOfPositionsInMatrixUsingDFS object = new CountNonAccessiblePairOfPositionsInMatrixUsingDFS();
        Graph graph = new Graph(N * N + 1, true);// undirected graph
        object.insertpath(graph, N, new CountNonAccessiblePairOfPositionsInMatrixUsingDFS.Pair(1, 1),
                new CountNonAccessiblePairOfPositionsInMatrixUsingDFS.Pair(1, 2));
        object.insertpath(graph, N, new CountNonAccessiblePairOfPositionsInMatrixUsingDFS.Pair(1, 2),
                new CountNonAccessiblePairOfPositionsInMatrixUsingDFS.Pair(2, 2));

        System.out.println(object.countNonAccessibleUsingDFS(graph, N));
    }

    static void testDataMinimumMovesToReachDestinationInMatrix() {
        int M[][] = new int[][] { { 3, 3, 1, 0 }, { 3, 0, 3, 3 }, { 2, 3, 0, 3 }, { 0, 3, 3, 3 } };
        System.out.println(MinimumMovesToReachDestinationInMatrixUnderConstraints.MinimumPath(M, M[0].length));
    }

    static void testDataMinStepsToReachTargetByKnightInChessMatrix() {
        int N = 30;
        int knightPos[] = { 1, 1 };
        int targetPos[] = { 30, 30 };
        System.out.println(MinimumStepsToReachTargetByKnight.minStepToReachTarget(knightPos, targetPos, N));
    }

    static void testDataConvertNumberFromXtoYUsingMulandSub() {
        // int x = 2, y = 5;
        int x = 4, y = 7;
        System.out.println(ConvertNumberXtoYWithMulAndSub.minOperations(x, y));
    }

    static void testDataCheckIfTwoNodesAreOnSamePath() {

        // Let us create above shown tree
        int n = 9; // total number of nodes

        Graph graph = new Graph(n + 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 6);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(5, 7);
        graph.addEdge(5, 8);
        graph.addEdge(5, 9);

        // Start dfs (here root node is 1)
        CheckIfTwoNodesInNTreeAreOnSamePath.dfs(graph, 1);

        // Below are calls for few pairs of nodes
        if (CheckIfTwoNodesInNTreeAreOnSamePath.query(1, 5))
            System.out.print("Yes\n");
        else
            System.out.print("No\n");

        if (CheckIfTwoNodesInNTreeAreOnSamePath.query(2, 9))
            System.out.print("Yes\n");
        else
            System.out.print("No\n");

        if (CheckIfTwoNodesInNTreeAreOnSamePath.query(2, 6))
            System.out.print("Yes\n");
        else
            System.out.print("No\n");
    }

    static void testDataFindShortestDistantFromGuardInBank() {

        char matrix[][] = { { 'O', 'O', 'O', 'O', 'G' }, { 'O', 'W', 'W', 'O', 'O' }, { 'O', 'O', 'O', 'W', 'O' },
                { 'G', 'W', 'W', 'W', 'O' }, { 'O', 'O', 'O', 'O', 'G' } };

        new MinDistanceFromGuardInBank().findDistance(matrix);

    }

    static void testDataSumOfMinElementsInAllConnectedComponentsOfGraph() {
        int a[] = { 1, 6, 2, 7, 3, 8, 4, 9, 5, 10 };
        int n = 10;
        Graph g = new Graph(n + 1, true);
        // Add edges
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addEdge(5, 6);
        g.addEdge(7, 8);
        g.addEdge(9, 10);

        // Calling Function
        System.out.println(
                SumOfMinElementsInAllConnectedComponentsOfGraphUsingDFS.minimumSumConnectedComponents(a, g, n));
    }

    static void testDataPrintAllPathsInAGraphOrNaryTreeUsingSearchTraversal() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(2, 0);
        g.addEdge(2, 1);
        g.addEdge(1, 3);
        int s = 2, d = 3;
        System.out.println("path from src " + s + " to dst " + d + " are ");

        // Function for finding the paths
        PrintAllPathsFromSourceToDestinationInGraphOrNTree_BFSandDFS.printAllPathsUsingDFS(g, s, d);
        PrintAllPathsFromSourceToDestinationInGraphOrNTree_BFSandDFS.findPathsUsingBFS(g, s, d);
    }

    static void testDataMinEdgeReversalsToMakeRoot() {

        int edges[][] = { { 0, 1 }, { 2, 1 }, { 3, 2 }, { 3, 4 }, { 5, 4 }, { 5, 6 }, { 7, 6 } };
        int e = edges.length;

        MinEdgeReversalToMakeARoot_Hard.printMinEdgeReverseForRootNode(edges, e);
    }

    static void testDataMinStepsToReachEndOfArray() {
        int arr[] = { 0, 1, 2, 3, 4, 5, 6, 7, 5, 4, 3, 6, 0, 1, 2, 3, 4, 5, 7 };
        int N = arr.length;
        System.out.println(MinStepsToReachEndOfArrayUnderConstraints.getMinStepsToReachEnd(arr, N));
    }

    static void testDataDisplaySteppingNumbers() {
        int n = 0, m = 21;

        // Display Stepping Numbers in the range [n,m]
        DisplaySteppingNumbersInRange.displaySteppingNumbers(n, m);
    }

    static void testDataFindLengthOfLargestRegionInBinaryBooleanMatrixUsingDFS() {
        int M[][] = { { 0, 0, 1, 1, 0 }, { 1, 0, 1, 1, 0 }, { 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1 } };

        FindLengthOfLargestRegionInBinaryBooleanMatrixUsingDFS object = new FindLengthOfLargestRegionInBinaryBooleanMatrixUsingDFS();
        // Function call
        object.ROW = 4;
        object.COL = 5;
        System.out.println(object.largestRegion(M));
    }

    static void testDataCouldNumberOfIslandOrConnectedComponentsInGraph() {
        int M[][] = new int[][] { { 1, 1, 0, 0, 0 }, { 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 1 }, { 0, 0, 0, 0, 0 },
                { 1, 0, 1, 0, 1 } };
        FindNumberOfIslandsOrConnectedComponentsInGraphUsingDFS I = new FindNumberOfIslandsOrConnectedComponentsInGraphUsingDFS();
        System.out.println("Number of islands is: " + I.countIslands(M));
    }

    static void testDataCountAllPossiblePathsBetweenTwoVerticesSourceAndDestination() {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 4);

        CountAllPossiblePathsBetweenTwoVerticesSourceAndDestinationUsingDFS object = new CountAllPossiblePathsBetweenTwoVerticesSourceAndDestinationUsingDFS();
        int s = 0, d = 3;
        System.out.println(object.countPaths(g, s, d));
    }

    static void testDataMoveWeighingScaleAlternatelyUnderGivenConstraintsUsingDFS() {
        int[] arr = { 2, 3, 5, 6 };
        int N = arr.length;
        int steps = 10;
        MoveWeighingScaleAlternatelyUnderGivenConstraintsUsingDFS.printWeightOnScale(arr, N, steps);
    }

    static void testDataBFSonConnectedComponentsOfGraphOrDisconnectedGraph() {
        int v = 5;
        Graph g = new Graph(v);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        BFSOnConnectedComponentsOfGraphOrDisconnectedGraph.bfs(g);
    }
    /**
     * 1. https://www.geeksforgeeks.org/count-number-trees-forest/ 
     * 2.https://www.geeksforgeeks.org/minimum-initial-vertices-traverse-whole-matrix-given-conditions/
     * 3. https://www.geeksforgeeks.org/water-jug-problem-using-bfs/ 4.
     * https://www.geeksforgeeks.org/path-rectangle-containing-circles/ 5.
     * https://www.geeksforgeeks.org/height-generic-tree-parent-array/ 6.
     * https://www.geeksforgeeks.org/minimum-number-of-edges-between-two-vertices-of-a-graph/
     * 7.
     * https://www.geeksforgeeks.org/count-nodes-within-k-distance-from-all-nodes-in-a-set/
     * 8. https://www.geeksforgeeks.org/number-pair-positions-matrix-not-accessible/
     * 9.
     * https://www.geeksforgeeks.org/find-minimum-numbers-moves-needed-move-one-cell-matrix-another/
     * 10. https://www.geeksforgeeks.org/minimum-steps-reach-target-knight/ 11.
     * https://www.geeksforgeeks.org/minimum-number-operation-required-convert-number-x-y/
     * 12.
     * https://www.geeksforgeeks.org/check-if-two-nodes-are-on-same-path-in-a-tree/
     * 13. https://www.geeksforgeeks.org/find-shortest-distance-guard-bank/ 14.
     * https://www.geeksforgeeks.org/boggle-find-possible-words-board-characters/
     * 15.
     * https://www.geeksforgeeks.org/minimum-time-required-so-that-all-oranges-become-rotten/
     * 16.
     * https://www.geeksforgeeks.org/sum-of-the-minimum-elements-in-all-connected-components-of-an-undirected-graph/
     * 17.
     * https://www.geeksforgeeks.org/print-paths-given-source-destination-using-bfs/
     * 18. https://www.geeksforgeeks.org/minimum-edge-reversals-to-make-a-root/ 19.
     * https://www.geeksforgeeks.org/bfs-disconnected-graph/ 20.
     * https://www.geeksforgeeks.org/move-weighting-scale-alternate-given-constraints/
     * 21. https://www.geeksforgeeks.org/stepping-numbers/ 22.
     * https://www.geeksforgeeks.org/count-possible-paths-two-vertices/ 23.
     * https://www.geeksforgeeks.org/find-length-largest-region-boolean-matrix/ 24.
     * https://www.geeksforgeeks.org/minimum-steps-reach-end-array-constraints/
     */

}
