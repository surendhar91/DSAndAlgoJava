/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author surendhar-2481
 */
public class Graph {
    public int vertices;
    public List<Integer> arr[];
    public int in[];//To store indegree of a vertex
    public Graph(int v){
        vertices = v;
        arr = new LinkedList[v];
        in = new int[v];
        for(int i=0;i<v;i++){
            arr[i] = new LinkedList<Integer>();
            in[i] = 0;
        }
    }
    public Graph(int v, boolean isUndirected, String isConcurrent){
        vertices = v;
        this.isUndirected = isUndirected;
        arr = new CopyOnWriteArrayList[v];
        for(int i=0;i<v;i++){
            arr[i] = new CopyOnWriteArrayList<Integer>();
            
        }
    }
    public Graph(int v, boolean isUndirected){
        this(v);
        this.isUndirected = isUndirected;
    }
    void addEdge(int v,int w){
        arr[v].add(w);//adding edge w to v graph
        in[w]++;
        if(this.isUndirected){
            arr[w].add(v);
            in[v]++;
        }
    }
    void removeEdge(int v,int w){
        arr[v].remove((Object)w);
        in[w]--;
        if(this.isUndirected){
            arr[w].remove((Object)v);
            in[v]--;
        }
    }
    
    
    void BFS(int source){
        //given a source, do the breadth first traversal
        boolean visited[] = new boolean[this.vertices];
        //Time Complexity: O(V+E) where V is number of vertices in the graph and E is number of edges in the graph.
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);//adding source vertex
        visited[source] = true;
        while(queue.size()!=0){
            int startVertex = queue.poll();
            System.out.println(startVertex+" ");
            List<Integer> adjList = arr[startVertex];
            for(Integer edge:adjList){
                if(!visited[edge]){
                    visited[edge] = true;
                    queue.add(edge);
                }
            }
            
        }
    
    }
    void DFSUtil(int src, boolean[] visited){
        //For an unweighted graph,  dfs traversal produces the minimum spanning tree and all pair shortest path
        visited[src] = true;
        System.out.println(src + " ");
        List<Integer> adjList = arr[src];
        for(Integer edge: adjList){
            if(!visited[edge]){
                DFSUtil(edge, visited);
            }
        }
    
    }
    int DFSCount(int src, boolean[] visited){
        visited[src] = true;
        int count = 1;
        List<Integer> adjList = arr[src];
        for(Integer edge: adjList){
            if(!visited[edge]){
                count += DFSCount(edge, visited);
            }
        }
        return count;
    }
    void DFSUtilPushStack(int src,boolean[] visited,Stack stack){
        visited[src]= true;
        List<Integer> adjList = arr[src];
        for(Integer edge:adjList){
            if(!visited[edge]){
                DFSUtilPushStack(edge, visited,stack);
            }
        }
        // All vertices reachable from v are processed by now,
        // push v to Stack
        stack.push(src);
    }
    void DFS(int v){
        boolean visited[] = new boolean[this.vertices];//this is necessary.
        DFSUtil(v,visited);
    }
    boolean isCyclicDirectedGraphUsingRecursiveStack(){//holds only for directed graph
        boolean[] recStack = new boolean[this.vertices];
        boolean[] visited  = new boolean[this.vertices];
        
        for(int i=0;i<this.vertices;i++){
            recStack[i] = false;
            /*
             There is a cycle in a graph only if there is a back edge present in the graph. A back edge is an edge that is from a node to itself (selfloop) or one of its ancestor in the tree produced by DFS.
            
            To detect a back edge, we can keep track of vertices currently in recursion stack of function for DFS traversal. 
            
            IMPORTANT: If we reach a vertex that is already in the recursion stack, then there is a cycle in the tree. 
            */
            
            visited[i] = false; //To mark that the verex has been visited
        }
        for(int i=0;i<this.vertices;i++){
            if(isCyclicUtil(1, visited, recStack)){
                return true;
            }
        }
        return false;
    }
    boolean isCyclicUtil(int vertex,boolean[] visited, boolean[] recStack){
//        Time Complexity of this method is same as time complexity of DFS traversal which is O(V+E).
        if(!visited[vertex]){//only if the vertex is not visited.
            // Mark the current node as visited and part of recursion stack
            System.out.println(vertex+" ");
            visited[vertex] = true;
            recStack[vertex] = true;

             // Recur for all the vertices adjacent to this vertex
            List<Integer> adjList = arr[vertex];//get the adjacency list of a vertex

            for (Integer edge : adjList) {
                if (!visited[edge] && isCyclicUtil(edge, visited, recStack)) {
                    return true;
                } else if (recStack[edge]) {//if already visited, could be a loop, hence find the element in recursive stack, if present true
                    //for self loop
                    return true;
                }
            }
        }else{
            //   2
           //   / \
           //  0   3
           //  /   /   
           //   1
            recStack[vertex] =false;// remove the vertex from recursion stack
        }
        return false;
    }

    

    

    

    
    
    private enum Color{WHITE, GREY, BLACK};
    
    boolean isCyclicUsingColors(){
        
        /*
    WHITE : Vertex is not processed yet.  Initially
        all vertices are WHITE.

GRAY : Vertex is being processed (DFS for this 
       vertex has started, but not finished which means
       that all descendants (ind DFS tree) of this vertex
       are not processed yet (or this vertex is in function
       call stack)

BLACK : Vertex and all its descendants are 
        processed.

While doing DFS, if we encounter an edge from current 
vertex to a GRAY vertex, then this edge is back edge 
and hence there is a cycle.
    */
        //initialilze all the vertex  to white - as they are not processed
        Color colors[] = new Color[this.vertices];
        for(int i=0;i<this.vertices;i++){
            colors[i]=Color.WHITE;
        }
        
        for(int i=0;i<this.vertices;i++){
            if(colors[i]==Color.WHITE && isCyclicUtilUsingColor(i, colors)){
                return true;
            }
        }
        return false;
    }
    
    private boolean isCyclicUtilUsingColor(int vertex, Color[] colors) {
       colors[vertex] = Color.GREY; //in processing.
       
       List<Integer> adjList = arr[vertex];
       for(Integer edge:adjList){
           
           if(colors[edge]==Color.GREY){//Back edge
               return true;
           }
           
           if(colors[edge]==Color.WHITE && isCyclicUtilUsingColor(edge, colors)){//check the cyclic utiling with edge..
               return true;
           } 
       }
       colors[vertex] = Color.BLACK;//fully processed
       return false;
    }
    private int find(int[] parent, int vertex) {//returns the subset representative, this vertex is a part of
        if(parent[vertex]==-1)
            return vertex;
        return find(parent,parent[vertex]);
        // A utility function to find the subset of an element i
    }
    // A utility function to do union of two subsets
    private void union(int[] parent, int src, int dest) {
        int xSet = find(parent,src);//this can be avoided.
        int ySet = find(parent,dest);
        parent[xSet] = ySet;
    }
    
    public boolean isCycleUnDirected(Graph graph){
        //Note that the implementation of union() and find() is naive and takes O(n) time in worst case. 
        /*
         Let us consider the following graph:

         0
         |  \
         |    \
         1-----2
         For each edge, make subsets using both the vertices of the edge. If both the vertices are in the same subset, a cycle is found.

         Initially, all slots of parent array are initialized to -1 (means there is only one item in every subset).

         0   1   2
         -1 -1  -1 
         Now process all edges one by one.

         Edge 0-1: Find the subsets in which vertices 0 and 1 are. Since they are in different subsets, we take the union of them. For taking the union, either make node 0 as parent of node 1 or vice-versa.

         0   1   2    <----- 1 is made parent of 0 (1 is now representative of subset {0, 1})
         1  -1  -1
         Edge 1-2: 1 is in subset 1 and 2 is in subset 2. So, take union.

         0   1   2    <----- 2 is made parent of 1 (2 is now representative of subset {0, 1, 2})
         1   2  -1
         Edge 0-2: 0 is in subset 2 and 2 is also in subset 2. Hence, including this edge forms a cycle.

         How subset of 0 is same as 2?
         0->1->2 // 1 is parent of 0 and 2 is parent of 1
         */
        int[] parent = new int[graph.vertices]; //this is used for find and union of subsets.

        //We are using and union - find algorithm to test if the there is a cycle in undirected graph
        //This method assumes that there is no self cycle
        for (int i = 0; i < graph.vertices; i++) {
            parent[i] = -1;// Initialize all subsets as single element sets
        }

        for (int i = 0; i < graph.vertices; i++) {
            int src = i;
            List<Integer> edge = graph.arr[src];//edge.
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
    
    public boolean isCycleUndirectedUsingParent(Graph graph){//Using DFS Approach, finding the cycle in a undirected graph
//        The time complexity of the union-find algorithm is O(ELogV). Like directed graphs, we can use DFS to detect cycle in an undirected graph in O(V+E) time. 
//        We do a DFS traversal of the given graph. For every visited vertex ‘v’, if there is an adjacent ‘u’ such that u is already visited and u is not parent of v, then there is a cycle in graph.
    /*
        in case of undirected graph there is just 2 case -

         1. node previously visited
         2. not visited
         if not visited then recurse it and if visited then just check it is not the parent.
         But with directed graph this is not the case- we also have have to consider the entire hierarchy so it require a recstack
        */
        boolean visited[] = new boolean[graph.vertices];
        
        for(int i=0;i<graph.vertices;i++){
            if(!visited[i]&&isCycleUtilParentUndirected(graph,visited,i, -1))//vertex must not be visited
                return true;
        }
        return false;
    }
    private boolean isCycleUtilParentUndirected(Graph graph,boolean[] visited, int currentNode, int parent) {
        visited[currentNode]= true;// Mark the current node as visited
        
        List<Integer> allEdges = graph.arr[currentNode];
        // Recur for all the vertices adjacent to this vertex
        for(Integer destination:allEdges){
            // If an adjacent is not visited, then recur for that
            // adjacent --- Using DFS
            if(!visited[destination]&&graph.isCycleUtilParentUndirected(graph,visited,destination,currentNode)){
                return true;
            }else if(parent!=currentNode){
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
                return true;
            }
        }
        return false;
        
    }
    
    void topologicalSortUtil(int vertex,boolean visited[], Stack stack){
        //What is a topological sorting ? Ensures that element u is visited before v
        //Topological Sorting for a graph is not possible if the graph is not a DAG.
        /*
        In DFS, we print a vertex and then recursively call DFS for its adjacent vertices. 
        In topological sorting, we need to print a vertex before its adjacent vertices
        */
        visited[vertex] = true;
        
        LinkedList<AdjListNode> adjList = this.arrAdjNode[vertex];
        for(AdjListNode adjNode:adjList){
            //for each adjacent node in the adjacent list of vertex v
            if(!visited[adjNode.v]){
                topologicalSortUtil(adjNode.v, visited, stack);//Use DFS
            }
        }
        /*
        We can modify DFS to find Topological Sorting of a graph. 
        In DFS, we start from a vertex, we first print it and then recursively call DFS for its adjacent vertices. 
        In topological sorting, we use a temporary stack. We don’t print the vertex immediately, 
        we first recursively call topological sorting for all its adjacent vertices, then push it to a stack. 
        Finally, print contents of stack. 
        Note that a vertex is pushed to stack only when all of its adjacent vertices (and their adjacent vertices and so on) are already in stack.
        */
        
        //As all the adjacent nodes are processed, push this element to the stack..
        stack.add(vertex);
        //so that, as we pop the element, first element would be u then the vertex v etc..
    }
    
    void findLongestPath(int source){
        //Find longest path in a directed acyclic graph
        
        // In fact, the Longest Path problem is NP-Hard for a general graph. However, the longest path problem has a linear time solution for directed acyclic graphs. 
        //The idea is similar to linear time solution for shortest path in a directed acyclic graph., we use Tological Sorting.
        Stack<Integer> stack = new Stack<Integer>();
        
        boolean visited[] = new boolean[this.vertices];
        //initially all the vertices visited property is false..
        
        for(int i=0;i<this.vertices;i++){
            //for each vertex in the graph, find the topological sorting..
            if(!visited[i]){
                topologicalSortUtil(i, visited, stack);
            }
        }
        int dist[] = new int[this.vertices];
        dist[source] = 0;//distance of the source vertex is 0
        for(int i=0;i<this.vertices;i++){
            if(i!=source){
                dist[i]=Integer.MIN_VALUE;//Negative infinite for all the other vertices..
            }
        }
        //Now we obtained the graph in linear fashion, through topological sort util..
        
        while(!stack.empty()){
            
            int u = stack.peek();
            // Get the next vertex from topological order
            stack.pop();
            if(dist[u]!=Integer.MIN_VALUE){//initially the source vertex..
                // Update distances of all adjacent vertices    
                LinkedList<AdjListNode> adjList = arrAdjNode[u];
                for(AdjListNode adjNode:adjList){//adjNode is 
                    if(dist[adjNode.v]<dist[u]+adjNode.weight){//maintains the longest path between u to v.
                        //distance to destination is less than the distance from source to weight [Edge between u->v]
                        dist[adjNode.v]=dist[u]+adjNode.weight;
                    }
                }
            }
        }
        System.out.println("printing the longest calculated distances..");
        for(int i=0;i<this.vertices;i++){
            if(dist[i]==Integer.MIN_VALUE){
                System.out.println(i+" Infinite");
            }
            else{
                System.out.println(i+" distance "+dist[i]);
            }
        }
        
    }
    
    void topologicalSort()
    {
        Stack stack = new Stack();
 
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[this.vertices];
        for (int i = 0; i < this.vertices; i++)
            visited[i] = false;
 
        // Call the recursive helper function to store Topological
        // Sort starting from all vertices one by one
        for (int i = 0; i < this.vertices; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);
 
        // Print contents of stack
        while (stack.empty()==false)
            System.out.print(stack.pop() + " ");
    }
    
    boolean isBiPartite(int source){
        /*
        
        A Bipartite Graph is a graph whose vertices can be divided into two independent sets, U and V such that every edge (u, v) 
        either connects a vertex from U to V or a vertex from V to U. In other words, 
        for every edge (u, v), either u belongs to U and v to V, or u belongs to V and v to U. 
        We can also say that there is no edge that connects vertices of same set.
        
        */
        //given a source, do the breadth first traversal
        boolean visited[] = new boolean[this.vertices];
        //Time Complexity: O(V+E) where V is number of vertices in the graph and E is number of edges in the graph.
        /*
         A bipartite graph is possible if the graph coloring is possible using two colors such that vertices in a set are colored with the same color.
        */
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int colorArr[] = new int[this.vertices];
        for(int i=0;i<this.vertices;i++){
            colorArr[i]=-1;//initially the color is unassigned..
        }
        queue.add(source);//adding source vertex
        colorArr[source]=1;//1 is the first color 0 is the second color -1 is color unassigned.
//        visited[source] = true;
        while(queue.size()!=0){
            int startVertex = queue.poll();
            System.out.println(startVertex+" ");
            List<Integer> adjList = arr[startVertex];
            for(Integer adjNode:adjList){
                if(colorArr[adjNode]==-1){
                    colorArr[adjNode] = 1-colorArr[startVertex];//assign alternate color to the adjacent node.
                    queue.add(adjNode);
                }else if(colorArr[adjNode]==colorArr[startVertex]){
                    // An edge from u to v exists and destination v is
                    // colored with same color as u
                    return false;
                //already visited and check if the colorArr
                }
            }
            
        }
        return true;
        /*
        example, Not a partite graph.
                    red   --       blue
                 /
            blue                    |
                  \  
                    red   --       blue
        */      
    }
// This function returns minimum number of dice throws required to
// Reach last cell from 0'th cell in a snake and ladder game.
// move[] is an array of size N where N is no. of cells on board
// If there is no snake or ladder from cell i, then move[i] is -1
// Otherwise move[i] contains cell to which snake or ladder at i
// takes to.
    
    
    /*
    The idea is to consider the given snake and ladder board as a directed graph with number of vertices equal to the number of cells in the board. 
    The problem reduces to finding the shortest path in a graph. 
    Every vertex of the graph has an edge to next six vertices if next 6 vertices do not have a snake or ladder. 
    If any of the next six vertices has a snake or ladder, then the edge from current vertex goes to the top of the ladder or tail of the snake. 
    Since all edges are of equal weight, we can efficiently find shortest path using Breadth First Search of the graph.
    */
    static int getMinDiceThrows(int move[], int N){
        //Mininum dice needed to reach the destination of snake ladder problem from source vertex.
        boolean visited[] = new boolean[N];
        for(int i=0;i<N;i++){
            visited[i] =false;//Mark all the nodes as not visited..
        }
        //use bfs traversal
        Queue<QueueEntry> queue = new LinkedList<QueueEntry>();//Must use Queue Interface, LinkedList not working properly..
        visited[0] = true;//mark the source vertex as visited.
        queue.add(new QueueEntry(0, 0));//distance from vertex 0 to source vertex is 0
        QueueEntry qe = null;
        while(!queue.isEmpty()){
            //as long as the queue is not empty..
            qe = queue.peek();//get the next node in the queue
            int v = qe.v;//vertex number of queueentry
            
            if(v==N-1){
                break;//if reached the destination, then break from the loop
                //qe.dist will be having the number of throws to reach the destination..
            }
            queue.remove();
            
            for(int j=v+1;j<=v+6&&j<N;++j){
                //Dice -> Traversing from 1 to 6
                if(!visited[j]){//only if the node is not visited, do the following..
                    QueueEntry aNode = new QueueEntry();
                    aNode.dist = (qe.dist + 1);//Only one throw needed to reach the 6 cell.. 
                    visited[j] = true;//Marking the node as visited.
                    
                    if(move[j]!=-1){//either a ladder or snake.
                        aNode.v = move[j];//if this dice is thrown, and it's a snake or ladder, you will climb up or down to the move[j] element.
                    }else{
                        aNode.v = j;
                    }
                    queue.add(aNode);//push the aNode to the queue..
                }
            }
        }
        return qe.dist;//number of min throws needed to reach the destination..
        
    }
    static void minCashFlow(int graph[][],int N){
        //given a matrix of i, j where i pays to j
        //find the minimum cash flow among them to settle the debts.
        int[] amount = new int[N];//N is the number of persons.
        //Calculate the net amount, Net Amount for each person is Credit - Debit of a person
        for(int i=0;i<N;i++){
            amount[i]=0;
        }
        for(int p=0;p<N;p++){
            //p is the person for whom the net amount has to be calculated.
            for(int i=0;i<N;i++){
                amount[p]+=(graph[i][p]//credit
                           -graph[p][i])//debit
                          ;
            }
        }
        minCashFlowRecur(amount);
    }
    static int getMaxIndex(int amount[]){
        int maxIndex = 0;
        for(int i=0;i<amount.length;i++){
            if(amount[i]>amount[maxIndex]){
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    static int getMinIndex(int amount[]){
        int minIndex = 0;
        for(int i=0;i<amount.length;i++){
            if(amount[i]<amount[minIndex]){
                minIndex = i;
            }
        }
        return minIndex;
    }
    static int minOf2(int a,int b){
        return (a<b)?a:b;
    }
    /*
    The idea is to use Greedy algorithm where at every step, settle all amounts of one person and recur for remaining n-1 persons.
How to pick the first person? To pick the first person, 
    calculate the net amount for every person where net amount is obtained by subtracting all debts (amounts to pay) from all credits (amounts to be paid). 
    
    Once net amount for every person is evaluated, find two persons with maximum and minimum net amounts. 
    These two persons are the most creditors and debtors. The person with minimum of two is our first person to be settled and removed from list. 
    Let the minimum of two amounts be x. We pay ‘x’ amount from the maximum debtor to maximum creditor and settle one person. 
    
    If x is equal to the maximum debit, then maximum debtor is settled, else maximum creditor is settled.
    */
    static void minCashFlowRecur(int amount[]){
        //now that we have calculated the net amount.
        //Get the max creditor
        int maxCreditor = getMaxIndex(amount);
        int maxDebitor  = getMinIndex(amount);
        
        if(amount[maxCreditor]==0&&amount[maxDebitor]==0){//if both amounts are 0, then all the amounts are settled.
            return ;
        }
        
        int min = minOf2(-amount[maxDebitor],amount[maxCreditor]);//find the minimum amount among the 2
        amount[maxCreditor] -= min;//settle the amount to creditor from debitor
        amount[maxDebitor] += min;//add the min amount to debitor - debitor will be 0
        System.out.println("Person "+maxDebitor+" pays amount "+min+" to person "+maxCreditor);
        
        minCashFlowRecur(amount);
    }
    String dictionary[] = {"GEEKS","FOR","QUIZ","GO"};
    boolean isWord(String str){//if the string is a word, then return true.
        for(int i=0;i<dictionary.length;i++){
            if(str.equalsIgnoreCase(dictionary[i])){
                return true;
            }
        }
        return false;
    }
    void findWordsUtil(char boggle[][], boolean visited[][],int i,int j,StringBuilder str){
        visited[i][j] = true;//mark this cell as visited
        //where [i][j] is the cell, whose char we are going to start with for finding the words..
        //Do DFS..
        str.append(boggle[i][j]);

        if(isWord(str.toString())){
            System.out.println(str);
        }
        //Traverse through eight adjacent cells
        for(int row=i-1;row<=i+1&&row<boggle.length;row++){//boggle.length is M
            for(int col=j-1;col<=j+1&&col<boggle[i].length;col++){//boggle[i].length is N
                if(row>=0&&col>=0&&!visited[row][col]){//shouldn't have been visited
                    findWordsUtil(boggle,visited,row,col,str);
                }
            }
        }
        str.deleteCharAt(str.length()-1);//deleting the character appended in this recursive function
        visited[i][j]=false;//resetting the visited propery
    }
    void findWords(char boggle[][],int m,int n){
        //from a boggle, find the words, check if it's present in dictionary
     /*
        Given a dictionary, a method to do lookup in dictionary and a M x N board where every cell has one character. 
        
        Find all possible words that can be formed by a sequence of adjacent charactersNote that we can move to any of 8 adjacent characters,
        
        but a word should not have multiple instances of same cell.

Example:

Input: dictionary[] = {"GEEKS", "FOR", "QUIZ", "GO"};
       boggle[][]   = {{'G','I','Z'},
                       {'U','E','K'},
                       {'Q','S','E'}};
      isWord(str): returns true if str is present in dictionary
                   else false.

Output:  Following words of dictionary are present
         GEEKS
         QUIZ
        
        */   
        boolean visited[][] = new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                visited[i][j] =false;
            }
        }//mark all the cells as not visited
        StringBuilder str = new StringBuilder();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                findWordsUtil(boggle,visited,i,j,str);
            }
        }
    }
    
    class AdjListNode{
        int v;
        int weight;
        public AdjListNode(int v,int weight){
            this.v = v;
            this.weight = weight;
        }
    }
    private boolean isUndirected = false;
    public LinkedList<AdjListNode> arrAdjNode[];
    public Graph(int v, boolean weightAssign, boolean isUndirected){
        vertices = v;
        arrAdjNode = new LinkedList[v];
        this.isUndirected = isUndirected;
        for(int i=0;i<v;i++){
            arrAdjNode[i] = new LinkedList<AdjListNode>();
        }
    }
    void addEdge(int v,int w,int weight){
        //v -> w is the edge, weight is the weight for the edge..
        AdjListNode adjNode = new AdjListNode(w,weight);
        arrAdjNode[v].add(adjNode);
        if(isUndirected){
            arrAdjNode[w].add(new AdjListNode(v,weight));
        }
    }
    
    
    static class QueueEntry{
        int v;
        int dist; //distance from the source vertex, note it's not the weight of the edge..
        public QueueEntry(int v,int dist){
            this.v = v;
            this.dist = dist;
        }
        public QueueEntry(){
        }
    }
   
}
