/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hacker.practice.graphs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author surendhar-2481
 */
public class JourneyToMoon {
    /**
     * Problem statement: https://www.hackerrank.com/challenges/journey-to-the-moon
     * Solution: https://www.hackerrank.com/challenges/journey-to-the-moon/forum/comments/215048
   */
    public static void main(String[] args) throws Exception {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int I = Integer.parseInt(temp[1]);
        Graph graph = new Graph(N, false);
        for (int i = 0; i < I; i++) {
            temp = bfr.readLine().split(" ");
            int a = Integer.parseInt(temp[0]);
            int b = Integer.parseInt(temp[1]);
            // Store a and b in an appropriate data structure of your choice
            graph.addEdge(a, b);
        }
        DFS dfs = new DFS(graph);
        dfs.computeDFSOfGraph();
        long combinations = 0;
        // Compute the final answer - the number of combinations
        //System.out.println(combinations);
    }
    
}
class DFS{
    Graph graph;
    boolean visited[];
    DFS(Graph graph){
        this.graph = graph;
        this.visited = new boolean[graph.getVertices()];
    }
    
    void DFSUtil(int src, List<Integer> visitedElements){
        visited[src] = true;
        visitedElements.add(src);
        List<Integer> adjElements = graph.getAdjacentElements(src);
        for(Integer adjElement:adjElements){
            if(!visited[adjElement]){
                DFSUtil(adjElement, visitedElements);
            }
        }
    }
    List<Integer> getDFS(int src){
        //boolean visited[] = new boolean[graph.getVertices()];
        List<Integer> visitedComponent = new ArrayList<Integer>();
        DFSUtil(src, visitedComponent);
        return visitedComponent;    
    }
    
    void computeDFSOfGraph(){
        List<Integer> elementSet = new ArrayList<Integer>();
        for(int i=0;i<graph.getVertices();i++){
            if(!visited[i]){
                List<Integer> dfsSet = getDFS(i);
                elementSet.add(dfsSet.size());
            }
        }
        long noOfPairs = 0;
        if(elementSet.size()>1){
            int a = elementSet.get(0);
            int b = elementSet.get(1);
            noOfPairs = a*b;//store the result in long..
            for(int i=2;i<elementSet.size();i++){
                Integer c = (Integer)elementSet.get(i);
                noOfPairs+=(a+b)*c;
                a = a+b;
                b = c;
            }
        }
        System.out.println(noOfPairs);
    }
    
    
}
class Graph{
    private List edgeArr[];
    private int vertices;
    private boolean isUndirectedGraph;
    public Graph(int vertices, boolean isUndirected){
        this.vertices = vertices;
        this.isUndirectedGraph = isUndirected;
        edgeArr = new LinkedList[this.vertices];
        for(int i=0;i<this.vertices;i++){
            edgeArr[i] = new LinkedList();
        }
    }
    public int getVertices(){
        return vertices;
    }
    public void addEdge(int u, int v){
        edgeArr[u].add(v);
        if(isUndirectedGraph){
            edgeArr[v].add(u);
        }
    }
    public List getAdjacentElements(int src){
        return edgeArr[src];
    }
    

 



}

