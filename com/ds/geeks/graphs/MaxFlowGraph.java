/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.graphs;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author surendhar-2481
 */
public class MaxFlowGraph {
    /*
    Very very important
    
        This is an important problem as it arises in many practical situations. Examples include, maximizing the transportation with given traffic limits, maximizing packet flow in computer networks.
    
    
    */
    
    //Returns the maximum flow from s to t in the given graph
    int fordFulkerson(int graph[][],int s,int t){
        
        //Idea here is to use a residual graph matrix that would contain initially the original matrix
        
        /*
            To find an augmenting path, we can either do a BFS or DFS of the residual graph. 
            We have used BFS in below implementation. Using BFS, we can find out if there is a path from source to sink. BFS also builds parent[] array. 
            Using the parent[] array, we traverse through the found path and find possible flow through this path by finding minimum residual capacity along the path. 
            We later add the found path flow to overall flow.
        
        */
        int u,v;
        int vertices = graph.length;
        int rGraph[][] = new int[vertices][vertices];
        
        for(int i=0;i<vertices;i++){
            for(int j=0;j<vertices;j++){
                rGraph[i][j] = graph[i][j];//Initialize the original matrix to residual matrix
            }
        }
        //This array is filled by bfs, to store path - for travesal to find the maximum flow.
        int parent[] = new int[vertices];
        
        int max_flow = 0;//no flow initially
        
        while(BFS(rGraph,s,t,parent)){
//            System.out.println("-------------------");
             // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int current_path_flow = Integer.MAX_VALUE;
            for(v=t;v!=s;v=parent[v]){//Traversing from target to source.
                u = parent[v]; //edge start
                current_path_flow = Math.min(current_path_flow, rGraph[u][v]);//Find minimum residual capacity along the path traversed
            }
            
            //update residual capacities of the forward edge along the path -> Subtracting the current path flow
            //Backward edge -> add current path flow to them.
            
            for(v=t;v!=s;v=parent[v]){
                u=parent[v];
//                System.out.print(u+" "+v+"||");
                rGraph[u][v] -= current_path_flow;//u->v edge subtract path flow
                rGraph[v][u] += current_path_flow;//v->u edge exists, then add path flow
            }
//            System.out.println("Current path flow "+current_path_flow);
//            System.out.println("After subtracting current path flow..Residual graph");
//            for(int i=0;i<vertices;i++){
//                for(int j=0;j<vertices;j++){
//                    System.out.print(rGraph[i][j]+"\t");//Initialize the original matrix to residual matrix
//                }
//                System.out.println("");
//            }
            max_flow+=current_path_flow;//add current path flow to the max flow.
        
        }
        return max_flow;
    }
    
    boolean BFS(int graph[][],int source, int dest,int parent[]){
        //given a source, do the breadth first traversal
        boolean visited[] = new boolean[graph.length];
        
        for(int i=0;i<graph.length;i++)
        {
            visited[i] = false;
        }        
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);//adding source vertex
        visited[source] = true;
        parent[source] = -1;
        
        while(queue.size()!=0){
            int u = queue.poll();
//            System.out.println(startVertex+" ");
//            List<Integer> adjList = arr[startVertex];
            for(int v=0;v<graph.length;v++){
                if(!visited[v] && graph[u][v]>0){//Residual graph must be greater than zero for bfs traversal
                    visited[v] = true;
                    queue.add(v);
                    parent[v] = u;
                }
            }
        }
        return visited[dest];
    
    }
    void minCut(int graph[][],int s,int t){
        /*In a flow network, an s-t cut is a cut that requires the source ‘s’ and the sink ‘t’ to be in different subsets, and it consists of edges going from the source’s side to the sink’s side. 
         The capacity of an s-t cut is defined by the sum of capacity of each edge in the cut-set. (Source: Wiki)
         
         The problem discussed here is to find minimum capacity s-t cut of the given network. Expected output is all edges of the minimum cut.

         For example, in the following flow network, example s-t cuts are {{0 ,1}, {0, 2}}, {{0, 2}, {1, 2}, {1, 3}}, etc. The minimum s-t cut is {{1, 3}, {4, 3}, {4 5}} which has capacity as 12+7+4 = 23.
        
         Minimum Cut and Maximum Flow
            Like Maximum Bipartite Matching, this is another problem which can solved using Ford-Fulkerson Algorithm. This is based on max-flow min-cut theorem.

            The max-flow min-cut theorem states that in a flow network, the amount of maximum flow is equal to capacity of the minimum cut. See CLRS book for proof of this theorem.

            From Ford-Fulkerson, we get capacity of minimum cut. How to print all edges that form the minimum cut? The idea is to use residual graph.

            Following are steps to print all edges of minimum cut.

                1) Run Ford-Fulkerson algorithm and consider the final residual graph.

                2) Find the set of vertices that are reachable from source in the residual graph.

                3) All edges which are from a reachable vertex to non-reachable vertex are minimum cut edges. Print all such edges.
        */
        
         int u,v;
        int vertices = graph.length;
        int rGraph[][] = new int[vertices][vertices];
        
        for(int i=0;i<vertices;i++){
            for(int j=0;j<vertices;j++){
                rGraph[i][j] = graph[i][j];//Initialize the original matrix to residual matrix
            }
        }
        //This array is filled by bfs, to store path - for travesal to find the maximum flow.
        int parent[] = new int[vertices];
        
        int max_flow = 0;//no flow initially
        
        while(BFS(rGraph,s,t,parent)){
//            System.out.println("-------------------");
             // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int current_path_flow = Integer.MAX_VALUE;
            for(v=t;v!=s;v=parent[v]){//Traversing from target to source.
                u = parent[v]; //edge start
                current_path_flow = Math.min(current_path_flow, rGraph[u][v]);//Find minimum residual capacity along the path traversed
            }
            
            //update residual capacities of the forward edge along the path -> Subtracting the current path flow
            //Backward edge -> add current path flow to them.
            
            for(v=t;v!=s;v=parent[v]){
                u=parent[v];
                rGraph[u][v] -= current_path_flow;//u->v edge subtract path flow
                rGraph[v][u] += current_path_flow;//v->u edge exists, then add path flow
            }
        
        }
        //By this block, maximum flow has been achieved.
        System.out.println("Final Residual Graph..");
        for(int i=0;i<vertices;i++){
                for(int j=0;j<vertices;j++){
                    System.out.print(rGraph[i][j]+"\t");//Initialize the original matrix to residual matrix
                }
                System.out.println("");
        }
        boolean visited[] = new boolean[vertices];
        for(int i=0;i<vertices;i++){
            visited[i] = false;
        }
        //REsidual graph will have the edges set to zero, whose edges are involved in finding the maximum flow, hence the edge j will not be visited
        new Graph(vertices).dfs(rGraph,s,visited);
        //Found the reachable vertices from source in residual graph
        
        //Now find the vertices which are not reachable from this reachable vertices, but reachable in original matrix
        System.out.println("Minimum cut set of given graph is..");
        for(int i=0;i<vertices;i++){
            for(int j=0;j<vertices;j++){
                if(visited[i]&&!visited[j]&&graph[i][j]>0){
                    System.out.println(i+"-"+j);
                }
            }
        }
    }
    boolean bipartiteMatching(boolean bpGraph[][],int u,boolean seen[],int matchR[]){
        //matchR defines the applicant number who are assigned to job
        for(int v=0;v<matchR.length;v++){
            //For applicant u, try all the jobs [N]
            
            if(bpGraph[u][v] && !seen[v]){//Applicant u interested in v job and not seen
                
                seen[v] =true;//v job is seen                
                if(matchR[v]<0 || //if the job is not assigned to anyone, then assign it to u
                        bipartiteMatching(bpGraph, matchR[v], seen, matchR))//See if matchR[v] can find an other job, if so then reassign
                {
                    matchR[v] = u;
                    return true;
                }
                
            }
        }
        return false;
    }
    //returns maximum number of matching from M to N
    int maxBiPartiteMatching(boolean bpGraph[][],int M,int N){
        /*
        A matching in a Bipartite Graph is a set of the edges chosen in such a way that no two edges share an endpoint. 
        A maximum matching is a matching of maximum size (maximum number of edges). In a maximum matching, if any edge is added to it, it is no longer a matching. 
        There can be more than one maximum matchings for a given Bipartite Graph.

        Why do we care?
                There are many real world problems that can be formed as Bipartite Matching. For example, consider the following problem:
                There are M job applicants and N jobs. Each applicant has a subset of jobs that he/she is interested in. 
                Each job opening can only accept one applicant and a job applicant can be appointed for only one job. 
                Find an assignment of jobs to applicants in such that as many applicants as possible get jobs.
        
        
        Maximum Bipartite Matching and Max Flow Problem
                
                Maximum Bipartite Matching (MBP) problem can be solved by converting it into a flow network (See this video to know how did we arrive this conclusion). Following are the steps.

        1) Build a Flow Network
                There must be a source and sink in a flow network. So we add a source and add edges from source to all applicants. Similarly, add edges from all jobs to sink. The capacity of every edge is marked as 1 unit.

        2) Find the maximum flow.
                We use Ford-Fulkerson algorithm to find the maximum flow in the flow network built in step 1. The maximum flow is actually the MBP we are looking for.
        
        
        
        
        */
        
        //An array to keep track of the applicants assigned to jobs. 
        //The value of matchR[i] is the applicant number assinged to job i, the value -1 indicates nobody is assigned
        int matchR[] = new int[N];
        /*
        A simple way to implement this is to create a matrix that represents adjacency matrix representation of a directed graph with M+N+2 vertices. 
        Call the fordFulkerson() for the matrix. This implementation requires O((M+N)*(M+N)) extra space.

        Extra space can be be reduced and code can be simplified using the fact that the graph is bipartite and capacity of every edge is either 0 or 1. 
        
        
        The idea is to use DFS traversal to find a job for an applicant (similar to augmenting path in Ford-Fulkerson). 
        We call bpm() for every applicant, bpm() is the DFS based function that tries all possibilities to assign a job to the applicant.

        In bpm(), we one by one try all jobs that an applicant ‘u’ is interested in until we find a job, or all jobs are tried without luck. 
        
        For every job we try, we do following.
        If a job is not assigned to anybody, we simply assign it to the applicant and return true.
        If a job is assigned to somebody else say x, then we recursively check whether x can be assigned some other job. 
        To make sure that x doesn’t get the same job again, we mark the job ‘v’ as seen before we make recursive call for x. 
        If x can get other job, we change the applicant for job ‘v’ and return true. We use an array maxR[0..N-1] that stores the applicants assigned to different jobs.

        If bmp() returns true, then it means that there is an augmenting path in flow network and 1 unit of flow is added to the result in maxBPM().
        
        */
        for(int i=0;i<N;i++){
            matchR[i]=-1;//Initially all jobs are avaiable
        }
        
        int result = 0;//count of jobs assigned to applicants
        
        for(int u=0;u<M;u++){
            boolean[] seen = new boolean[N];
            for(int i=0;i<N;i++){
                seen[i] = false;//Marks all jobs as not seen for next applicant.
            }
            if(bipartiteMatching(bpGraph,u,seen,matchR)){
                result++;
            }
        }
        return result;
    }
    
    void channelAssignmentProblem(int bpGraph[][],int M,int N){
        /*
            There are M transmitter and N receiver stations. Given a matrix that keeps track of the number of packets to be transmitted from a given transmitter to a receiver. If the (i; j)-th entry of the matrix is k, it means at that time the station i has k packets for transmission to station j.
         During a time slot, a transmitter can send only one packet and a receiver can receive only one packet. Find the channel assignments so that maximum number of packets are transferred from transmitters to receivers during the next time slot.
            
         Example:

         0 2 0
         3 0 1
         2 4 0
         The above is the input format. We call the above matrix M. Each value M[i; j] represents the number of packets Transmitter ‘i’ has to send to Receiver ‘j’. The output should be:

         The number of maximum packets sent in the time slot is 3
         T1 -> R2
         T2 -> R3
         T3 -> R1 
        
        Similar to bipartite matching alogrithm - find a
        */
        int matchR[] = new int[N];
 // An array to keep track of the receivers assigned to the senders.
    // The value of matchR[i] is the sender ID assigned to receiver i.
    // the value -1 indicates nobody is assigned.
        
        for(int i=0;i<N;i++){
            matchR[i]=-1;// Initially all receivers are not mapped to any senders
        }
        
        int result = 0;   // Count of receivers assigned to senders
        
        for(int u=0;u<M;u++){
            boolean[] seen = new boolean[N];
            for(int i=0;i<N;i++){
                seen[i] = false;// Mark all receivers as not seen for next sender
            }
             // Find if the sender 'u' can be assigned to the receiver
            if(bipartiteMatching(bpGraph,u,seen,matchR)){
                result++;
            }
        }
        System.out.println("The number of maximum packets sent in the time slot is "+ result);
        
        for(int u=0;u<M;u++){
            if(matchR[u]+1!=0){//If the receiver u is assigned a transmitter, then print that
                System.out.println("T"+(matchR[u]+1)+"->R"+(u+1));
            }
        }
        
    
    }
    boolean bipartiteMatching(int bpGraph[][],int u,boolean seen[],int matchR[]){
        // Try every receiver one by one
        for(int v=0;v<matchR.length;v++){
            
            // If sender u has packets to send to receiver v and
        // receiver v is not already mapped to any other sender
        // just check if the number of packets is greater than '0'
        // because only one packet can be sent in a time frame anyways
            if(bpGraph[u][v]>0 && !seen[v]){
                
                seen[v] =true;//mark v as visited
                // If receiver 'v' is not assigned to any sender OR
            // previously assigned sender for receiver v (which is
            // matchR[v]) has an alternate receiver available. Since
            // v is marked as visited in the above line, matchR[v] in
            // the following recursive call will not get receiver 'v' again
                if(matchR[v]<0 || //if the job is not assigned to anyone, then assign it to u
                        bipartiteMatching(bpGraph, matchR[v], seen, matchR))
                {
                    matchR[v] = u;
                    return true;
                }
                
            }
        }
        return false;
    }
    void channelAssignmentTestData(){
        int table[][] = new int[][]{{0, 2, 0}, {3, 0, 1}, {2, 4, 0}};
        channelAssignmentProblem(table, 3, 3);
    }
    
    void findDisjointPathTestData(){
    
        /*
            Given a directed graph and two vertices in it, source ‘s’ and destination ‘t’, find out the maximum number of edge disjoint paths from s to t. 
            Two paths are said edge disjoint if they don’t share any edge.
            
            This problem can be solved by reducing it to maximum flow problem. Following are steps.
                1) Consider the given source and destination as source and sink in flow network. Assign unit capacity to each edge.
                2) Run Ford-Fulkerson algorithm to find the maximum flow from source to sink.
                3) The maximum flow is equal to the maximum number of edge-disjoint paths.

            When we run Ford-Fulkerson, we reduce the capacity by a unit. Therefore, the edge can not be used again. So the maximum flow is equal to the maximum number of edge-disjoint paths.
        */
        
        // Let us create a graph shown in the above example
    int graph[][] = new int[][]{ {0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1},
                        {0, 1, 0, 0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 0, 1, 0, 1},
                        {0, 0, 0, 0, 0, 0, 0, 0}
                      };
 
    int s = 0;
    int t = 7;
        System.out.println("There can be maximum " + fordFulkerson(graph, s, t)
         + " edge-disjoint paths from " + s +" to "+ t) ;
    }
    
    void fordFulkersonTestData(){
        // Let us create a graph shown in the above example
        int graph[][] =new int[][] { {0, 16, 13, 0, 0, 0},
                                     {0, 0, 10, 12, 0, 0},
                                     {0, 4, 0, 0, 14, 0},
                                     {0, 0, 9, 0, 0, 20},
                                     {0, 0, 0, 7, 0, 4},
                                     {0, 0, 0, 0, 0, 0}
                                   };
 
        System.out.println("The maximum possible flow is " +
                           fordFulkerson(graph, 0, 5));
    }
    
    void minCutTestData(){
         // Let us create a graph shown in the above example
    int graph[][] = new int[][]{ {0, 16, 13, 0, 0, 0},
                        {0, 0, 10, 12, 0, 0},
                        {0, 4, 0, 0, 14, 0},
                        {0, 0, 9, 0, 0, 20},
                        {0, 0, 0, 7, 0, 4},
                        {0, 0, 0, 0, 0, 0}
                      };
 
    minCut(graph, 0, 5);
    }
    
    void maxPartiteMatchingTestData(){
         // Let us create a bpGraph shown in the above example
        boolean bpGraph[][] = new boolean[][]{
            {false, true, true, false, false, false},
            {true, false, false, true, false, false},
            {false, false, true, false, false, false},
            {false, false, true, true, false, false},
            {false, false, false, false, false, false},
            {false, false, false, false, false, true}
        };
        System.out.println( "Maximum number of applicants that can"+
                            " get job is "+maxBiPartiteMatching(bpGraph,6,6));
    }
    
    void maxFlowTestData(){
//        fordFulkersonTestData();
//        findDisjointPathTestData();
//        minCutTestData();
//        maxPartiteMatchingTestData();
        channelAssignmentTestData();
    }
    
}
