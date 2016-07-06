/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.graphs;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author surendhar-2481
 */
public class MST{
    //Minimum spanning tree 
    /*
    1) Create a set mstSet that keeps track of vertices already included in MST.
    2) Assign a key value to all vertices in the input graph. Initialize all key values as INFINITE. 
        Assign key value as 0 for the first vertex so that it is picked first.
    3) While mstSet doesn’t include all vertices
…   .a) Pick a vertex u which is not there in mstSet and has minimum key value.
…   .b) Include u to mstSet.
…   .c) Update key value of all adjacent vertices of u. To update the key values, iterate through all adjacent vertices. 
        For every adjacent vertex v, if weight of edge u-v is less than the previous key value of v, update the key value as weight of u-v
    */
    
    void primMST(int[][] graph){//connected, weighted and directed/undirected
        
        // mstSet Boolean to represent that the vertex is included in set MST
        int V = graph.length;
        boolean[] mstSet = new boolean[V];//V vertex
        
        //Array key[] is used to store key values of all vertices 
        int[] key = new int[V];
        
        int[] parent = new int[V]; // parent of a vertex in MST
        
        for(int i=0;i<V;i++){
            key[i]=Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        
        //to pick the first element
        key[0]=0;
        parent[0]=-1;
        
        for(int i=0;i<V-1;i++){//since 0th element is already in MST
            int u = findMinKey(key,mstSet);//find the minimum key value element from non MST Set
            
            mstSet[u] = true;//include this in mstSet
            //Now update all the adjacent vertices of u (key value with the weight), only when the weight is less than the key value of adjacent vertex.
            for(int v=0;v<V;v++){
                if(!mstSet[v] && graph[u][v]!=0 && graph[u][v] < key[v]){
                    key[v] = graph[u][v];//don't change the weight
                    //we are changing only the key value..
                    parent[v]=u;
                }
            }
            
        }
        printMST(parent,V,graph);
        
    }
    int findMinKey(int key[],boolean mstSet[]){
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int v=0;v<key.length;v++){
            if(!(mstSet[v])&&(key[v]<min)){
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
        
    }
    void printMST(int[] parent, int vertices, int graph[][]){
        System.out.println("Edge   Weight");
        for(int i=1;i<vertices;i++){
            //for 0  parent is -1
            System.out.println(parent[i]+"-"+i+"   "+graph[i][parent[i]]);//for 1 parent is 0 graph[1][0] represents the weight
        }
    }
    
    void primMSTUsingAdjList(Graph graph)
    {
        int V = graph.vertices;//number of vertices in a graph
        int key[] = new int[V];
        int parent[] = new int[V];
        MinHeap minHeap = new MinHeap(V);

        
        for(int i=1;i<V;i++){
            parent[i] = -1;
            key[i] = Integer.MAX_VALUE;
            minHeap.Heap[i] = new MinHeapNode(i, key[i]);
            minHeap.pos[i] = i;
        }
        minHeap.Heap[0] = new MinHeapNode(0,key[0]);//so that 0th vertex is picked up all the times..
        minHeap.pos[0] = 0;
        key[0] = 0;
        minHeap.size = V;
        while(!minHeap.isEmpty()){//while min heap is not empty do the following..
           Object[] allNodes = minHeap.Heap;
            System.out.println("---------");
            for(Object node:allNodes){
                if(node!=null){
                System.out.print(((MinHeapNode)node).key+"\t");}
            }
            MinHeapNode minNode = (MinHeapNode) minHeap.extractMin();//get the min element from the heap
            int u = minNode.vertex;
            LinkedList<Graph.AdjListNode> adjList = graph.arrAdjNode[u];
            //for each of node adjacent to the minimum vertex found..
            for (Graph.AdjListNode node:adjList) {
                //check if the node's vertex found in min heap, only then it's not in Constructed MST
                int v = node.v;
                if(minHeap.isInMinHeap(v)){
                    if(node.weight<key[v]){
                        key[v] = node.weight;
                        parent[v] = u;
                        minHeap.decreaseKey(v,key[v]);//update the key of adjacent vertex v.
                    }
                }
                
            }
        }
        System.out.println("Constructed MST using adjacency list..");
        for(int i=1;i<V;i++){
            System.out.println(parent[i]+"-"+i);
        }
        
    }    
    int V,E;
    Edge[] edge;
    class Edge implements Comparable<Edge>{

        int src,dest,weight;
        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
        public Edge(int src,int dest){
            this.src = src; this.dest = dest;
        }
        public Edge(){
        }
        
        @Override
        public String toString(){
            return "src "+src+" dest "+dest;
        }
        
    };
    class subset{
        int parent;
        int rank;//union find by rank
    };
    
    MST(int v,int e){//Create a graph with V vertex and E edges
        this.V = v;
        this.E = e;
        edge = new Edge[e];
        for(int i=0;i<e;i++){
            edge[i]=new Edge();
        }
    }
    MST(){
    
    }
    int find(subset sub[], int i){
        // find root and make root as parent of i (path compression)
        if(sub[i].parent!=i){
            sub[i].parent = find(sub,sub[i].parent);//recursively find the parent.
        }
        return sub[i].parent;//return the subset
    }
    void Union(subset sub[],int x,int y){
        //union method that does union of two sets x and y.
        //union by rank method
        int xroot = find(sub, x);
        int yroot = find(sub, y);
        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if(sub[xroot].rank<sub[yroot].rank){
            sub[yroot].parent = xroot;
        }else if(sub[yroot].rank<sub[xroot].rank){
            sub[xroot].parent = yroot;
        }else{
            // If ranks are same, then make one as root and increment
            // its rank by one
            sub[yroot].parent = xroot;
            sub[xroot].rank++;//this xroot will have one child.. union by rank method
        }
    }
    // The main function to construct MST using Kruskal's algorithm
    void KruskalMST()
    {//Connected weighted and undirected graph
        /*
        A minimum spanning tree (MST) or minimum weight spanning tree for a weighted, connected and 
        undirected graph is a spanning tree with weight less than or equal to the weight of every other spanning tree. 
        */
        Edge[] result = new Edge[V];//store the result of constructed MST
        int e = 0;//store the index of reuslt
        int i = 0;//store the index of sorted edges..
        for(i=0;i<V;i++){
            result[i] = new Edge();
        }
        //Step 1: sort the edge in non decreasing order
        Arrays.sort(this.edge);
        
        //Create a subset for each vertices..
        subset set[] = new subset[V];
        
        for(int v=0;v<V;v++){
            set[v] = new subset();
            set[v].parent = v;
            set[v].rank = 0;
        }
        i=0;//used to pick the next sorted edge..
        
        //Step 2: Find if a cycle is formed using union find, if cycle not formed include into the MST
        // Number of edges to be taken is equal to V-1
        while(e<V-1){//unless all the sorted edges are visited
            //Minimum spanning tree must have V edges..
            
            Edge edge = this.edge[i++];//pick the smallest edge weight and increment the index
            int x = find(set,edge.src);//find the subset of src vertex
            int y = find(set,edge.dest);//find the subset of dest vertex
            //if x==y then it would result in a cycle
            if(x!=y){
                // If including this edge does't cause cycle, include it
            // in result and increment the index of result for next edge
                result[e++] = edge;//include in the result MST tree.
                Union(set, x, y);
            }
        }
       System.out.println("Following are the edges in the constructed MST");
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src+" -- "+result[i].dest+" == "+
                               result[i].weight);
        /*
        O(ElogE) or O(ElogV). Sorting of edges takes O(ELogE) time. After sorting, we iterate through all edges and apply find-union algorithm. 
        The find and union operations can take atmost O(LogV) time. So overall complexity is O(ELogE + ELogV) time. 
        The value of E can be atmost V^2, so O(LogV) are O(LogE) same. Therefore, overall time complexity is O(ElogE) or O(ElogV)
        */
              
    }
    
    void boruvkaMST(){
        /*
        For every component, find the cheapest edge that connects it to some other component.

Component                Cheapest Edge that connects 
                         it to some other component
  {0}                           0-1
  {1}                           0-1
  {2}                           2-8
  {3}                           2-3
  {4}                           3-4
  {5}                           5-6
  {6}                           6-7
  {7}                           6-7
  {8}                           2-8 
The cheapest edges are highlighted with green color. Now MST becomes {0-1, 2-8, 2-3, 3-4, 5-6, 6-7}.
        
After above step, components are {{0,1}, {2,3,4,8}, {5,6,7}}. The components are encircled with blue color.
        
We again repeat the step, i.e., for every component, find the cheapest edge that connects it to some other component.

Component                Cheapest Edge that connects 
                         it to some other component
  {0,1}                        1-2 (or 0-7)
  {2,3,4,8}                    2-5
  {5,6,7}                      2-5
The cheapest edges are highlighted with green color. Now MST becomes {0-1, 2-8, 2-3, 3-4, 5-6, 6-7, 1-2, 2-5}

        At this stage, there is only one component {0, 1, 2, 3, 4, 5, 6, 7, 8} which has all edges. Since there is only one component left, we stop and return MST.
        
        */
        // An array to store index of the cheapest edge of
        // subset.  The stored index for indexing array 'edge[]'
        int[] cheapest = new int[V];
    
        subset[] sub = new subset[V];
        //Create V subsets with single elements
        for(int i=0;i<V;i++){
            sub[i] = new subset();
            sub[i].parent = i;
            sub[i].rank = 0;
            cheapest[i] = -1;
        }
        // Initially there are V different trees.
        // Finally there will be one tree that will be MST
        int numTrees = V;
        int MSTweight = 0;
        while(numTrees>1){
        
            //For joining the components, find the cheapest joining one another
            for(int e=0;e<E;e++){
            //Iterate through each edge..
                int set1 = find(sub,edge[e].src);
                int set2 = find(sub,edge[e].dest);
                //if set 1 and set 2 are the same, continue
                if(set1==set2)
                    continue;
                else{//Edge that joins the componenets
                    if (cheapest[set1] == -1 || edge[cheapest[set1]].weight > edge[e].weight) {
                    //if the cheapest of set 1 is not set
                        //If it's set, then check if the already stored cheapest edge of set 1 is less than the current edge weight, if it is then update the cheapest edge
                        cheapest[set1] = e;//store the index for edge array.
                    }
                    if (cheapest[set2] == -1 || edge[cheapest[set2]].weight > edge[e].weight) {
                        cheapest[set2] = e;
                    }
                }
            }
            // Consider the above picked cheapest edges and add them
            // to MST
            for (int v=0; v<V; v++)
            { //There is going to be V-1 Edges.. Vertex with no inbound edges [i.e.] parent will have cheapest[v]=-1
                if(cheapest[v]!=-1){//for the cheapest edges, we had found out in the above steps
                //We had found the cheapest edge, joining two components.
                    //Union the two sets, at the end..
                    int set1 = find(sub,edge[cheapest[v]].src);
                    int set2 = find(sub,edge[cheapest[v]].dest);
                    
                    if(set1==set2){
                        continue;
                    }
                    
                    MSTweight += edge[cheapest[v]].weight;//add the weight to MST weight
                    Union(sub, set1, set2);
                    numTrees--;//Number of trees reducing..
                }
                    
            }
            System.out.println("Weight of MST is "+ MSTweight);
            return;
        }
    }
    
    
    
}
class MinHeapNode{
        int vertex;
        int key;

        public MinHeapNode(int vertex,int key){
            this.vertex = vertex;
            this.key = key;
        }

    }
class MinHeap
{
    public MinHeapNode[] Heap;
    public int size;
    public int maxsize;
    public int pos[];
    
    private static final int FRONT = 1;
 
    public MinHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new MinHeapNode[this.maxsize];
        pos = new int[this.maxsize];
//        Heap[0] = neInteger.MIN_VALUE;
    }
    
    boolean isEmpty(){
        return size==0;
    }
 
    private void swap(int fpos, int spos)
    {
        MinHeapNode tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
 
    private void minHeapify(int idx)
    {
        int smallest, left, right;
        smallest = idx;
        left = 2*idx+1;
        right = 2*idx+2;
        
        if(left < size && Heap[left].key < Heap[smallest].key){
            smallest = left;
        }
        if(right<size && Heap[right].key < Heap[smallest].key){
            smallest = right;
        }
        if(smallest!=idx){
            MinHeapNode smallestNode = Heap[smallest];
            MinHeapNode idxNode      = Heap[idx];
            
            pos[smallestNode.vertex] = idx;
            pos[idxNode.vertex] = smallest;
            
            swap(smallest, idx);
            
            minHeapify(smallest);
        }
    }
    //function to decrease key value of a given vertex v.
    //This function uses pos[] of min heap to get the current index of node in min heap
    void decreaseKey(int v,int key){
        int i = pos[v];//get the index of v in heap array
        
        Heap[i].key = key;
        
        //travel up while the complete tree is not heapified
        //This is a O(log n) loop
        while(i>=0 && Heap[i].key < Heap[(i-1)/2].key){
            pos[Heap[i].vertex] = (i-1)/2;
            pos[Heap[(i-1)/2].vertex] = i;
            swap(i, (i-1)/2);
            i=(i-1)/2;
        }
    }
    
    MinHeapNode extractMin(){
        if(isEmpty()){
            return null;    
        }
        
        MinHeapNode root = Heap[0];
        
        MinHeapNode lastNode = Heap[size-1];
        Heap[0] = lastNode;
        
        pos[root.vertex] = size-1;
        pos[lastNode.vertex] = 0;
        
        --size;//removing the node from the heap..
        minHeapify(0);
        
        return root;
    }
    boolean isInMinHeap(int v){
        if(pos[v] < size){//position of vertex is less than size
            return true;
        }
        return false;
    }
}
