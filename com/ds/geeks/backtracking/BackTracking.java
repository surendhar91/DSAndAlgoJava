/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author surendhar-2481
 */
public class BackTracking {
    
       
        
        void permute(char[] a, int l, int r){
            
            //Refer: http://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
            
            // l -starting index of the string
            // r - ending index of the string
            if(l==r){
                System.out.println(""+new String(a));
            }else{
                for(int i=l;i<=r;i++){
                    char temp;
                    temp = a[l];
                    a[l] = a[i];
                    a[i] = temp;
                    
                    permute(a,l+1,r);//increment the starting index and recursively permute
                    
                    temp = a[l];//Back tracking, bringing to the original form
                    a[l] = a[i];
                    a[i] = temp;
                }
            }
        }
        
        /* This function solves the Knight Tour problem using
             Backtracking.  This function mainly uses solveKTUtil()
             to solve the problem. It returns false if no complete
             tour is possible, otherwise return true and prints the
             tour.
             Please note that there may be more than one solutions,
             this function prints one of the feasible solutions.  */
        boolean solveKnightsTour(){
            
            int sol[][] = new int[8][8];
            
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    sol[i][j] = -1;//initialize the solution matrix to -1
                }
            }
            sol[0][0] = 0;//first the knight will be at 0 block
        
            /* xMove[] and yMove[] define next move of Knight.
             xMove[] is for next value of x coordinate
             yMove[] is for next value of y coordinate */
            int xMove[] = new int[]{2, 1, -1, -2, -2, -1, 1, 2};
            int yMove[] = new int[]{1, 2, 2, 1, -1, -2, -2, -1};
            
            if(solveKnightsTourUtil(0,0,1,sol,xMove,yMove)==false){
                System.out.println("Solution doesn't exist..");
                return false;
            }else{
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        System.out.print(sol[i][j]+" ");
                    }
                    System.out.println("");
                }
                return true;
            }
        }
        
        boolean isSafe(int x, int y, int sol[][]) {
            int N = sol.length;
            return (x >= 0 && x < N && y >= 0
                && y < N && sol[x][y] == -1);
        }
        private boolean solveKnightsTourUtil(int x, int y, int movei, int[][] sol, int[] xMove, int[] yMove) {
            //now knight is at location x,y
            int next_x, next_y;
            if(movei==(8*8)){
                return true;//if reached the end of chess, then return true
            }
            
            for(int k=0;k<8;k++){
                next_x = x+xMove[k];
                next_y = y+yMove[k];
                if(isSafe(next_x,next_y,sol)){
                    sol[next_x][next_y] = movei;
                    /*
                     a) Add one of the next moves to solution vector and recursively 
                     check if this move leads to a solution. (A Knight can make maximum 
                     eight moves. We choose one of the 8 moves in this step).
                     */
                    if (solveKnightsTourUtil(next_x, next_y, movei + 1, sol, xMove, yMove)) {
                        return true;//if w
                    } else {
                        /*
                         If the move chosen in the above step doesn't lead to a solution
                         then remove this move from the solution vector and try other 
                         alternative moves.
                         */
                        sol[next_x][next_y] = -1;//Back Tracking, not leading to a solution
                    }
                }
            }
            
            /*
             If none of the alternatives work then return false (Returning false 
             will remove the previously added item in recursion and if false is 
             returned by the initial call of recursion then "no solution exists" )
            */
            return false;
        }
        
        boolean solveMaze(int maze[][]){
            
            /*
             We have discussed Backtracking and Knight’s tour problem in Set 1. Let us discuss Rat in a Maze as another example problem that can be solved using Backtracking.

             A Maze is given as N*N binary matrix of blocks where source block is the upper left most block i.e., maze[0][0] and destination block is lower rightmost block i.e., maze[N-1][N-1]. A rat starts from source and has to reach destination. The rat can move only in two directions: forward and down.
             In the maze matrix, 0 means the block is dead end and 1 means the block can be used in the path from source to destination. Note that this is a simple version of the typical Maze problem. For example, a more complex version can be that the rat can move in 4 directions and a more complex version can be with limited number of moves.
             */
            int N = maze.length;
            int sol[][] = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            };
            if(solveMazeUtil(maze,0,0,sol)==false){//start from source vertex (0,0)
                System.out.println("Solution doesn't exist");
                return false;
            }else{
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        System.out.print(sol[i][j] + " ");
                    }
                    System.out.println("");
                }
                return true;
            }
        }
        private boolean isSafe(int[][] maze, int x, int y) {
            int N = maze.length;
            // if (x,y outside maze) return false
            if (x >= 0 && x < N && y >= 0 && y < N && maze[x][y] == 1) {
                    return true;
            }

            return false;
        }
        private boolean solveMazeUtil(int[][] maze, int x, int y, int[][] sol) {
            
            int N = maze.length;
            if(x==N-1 && y==N-1){//if maze reached the destination, return true as such
                sol[x][y] = 1;//important
                return true;
            }
            
            if(isSafe(maze,x,y)){
                sol[x][y] = 1;
                
                if(solveMazeUtil(maze, x+1, y, sol)){
                    return true;//if moving in horizontal direction provides solution, return true
                }
                 
                if(solveMazeUtil(maze, x, y+1, sol)){
                    return true; //move down and check if it provides solution
                }
                
                sol[x][y] = 0;//both of them doesn't yield solution
                
                return false;
            }
            return false;
            
        }
        
        private boolean isQueenSafe(int board[][],int row,int col){
            //we need to check only the left side for attacking queens.
            //This function is called when col queens are already placed in columns from 0 to col-1
            
            int N = board.length;
            for(int i=0;i<col;i++){
                //check for the left side of queen
                if(board[row][i]==1){//already placed
                    return false;
                }
            }
            
            //check for the upper diagonal of queen
            
            for(int i=row,j=col;i>=0&&j>=0;i--,j--){
                if(board[i][j]==1){
                    return false;
                }
            }
            
            //check for the lower diagonal of queen
            
            for(int i=row,j=col;i<N&&j>=0;i++,j--){
                if(board[i][j]==1){
                    return false;
                }
            }
            return true;//safe to place elswhere..
            
        }
        //A recursive util to solve N Queen problem
        boolean solveNQUtil(int board[][],int col){
            //Base case: if reached the solution return true
            int N = board.length;
            if(col>=N){
                return true;
            }
            for(int i=0;i<N;i++){
                //For each row, check if the queen can be placed for the column col
                if(isQueenSafe(board, i, col)){
                    //If it's safe to place, then place the queen here.
                    board[i][col] = 1; /* Place this queen in board[i][col] */
                    
                     /* recur to place rest of the queens */
                    if(solveNQUtil(board, col+1)){//place the queen on next col, if it returns true, then it's safe to place
                        return true;
                    }
                    /* If placing queen in board[i][col]
                     doesn't lead to a solution, then
                     remove queen from board[i][col] */
                    board[i][col] = 0;//back track, if the queen can't be placed, recur for the rest of rows for placing queens at the col
                }
            }
            /* If queen can not be place in any row in
             this colum col  then return false */
            return false;
        }
        
        boolean solveNQ(){
            int board[][] = new int[][]{{0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
            };
            int N = board.length;
            if (solveNQUtil(board, 0) == false) {
                System.out.println("Solution doesn't exist..");
                return false;
            }else{
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        System.out.print(board[i][j] + " ");
                    }
                    System.out.println("");
                }
            }
            return true;

        }
        int totalNodesCount = 0 ;
        void subset_sum(int s[],int tuple[], int s_size,int t_size,int sum,int ite,int target_sum){
            //Inputs
            //s - Set Vector
            //tuple - tuple vector
            //s_size - set size
            //t_size - tuple size
            //sum - sum found so far
            //ite - nodes count
            //target_sum - sum to be found
            
            //http://www.geeksforgeeks.org/backttracking-set-4-subset-sum/ -> For the tree
            totalNodesCount++;
            
            //Base case:
            
            if(target_sum==sum){
                printSubSet(tuple, t_size);
                //Exclude previously added item and consider next candidate..
                //ite - current iterator is required
//                subset_sum(s,tuple,s_size,t_size-1,sum-s[ite-1],ite,target_sum);//Back Track, exclude the previously added item sum-s[ite], t_size-1
                return ;
            }else{
                for(int i=ite;i<s_size;i++){//generates the node breadth wise
                    //t_size is the current level index..
                    tuple[t_size] = s[i];//for an iteration, t_size is going to be same, as we are generating the breadth level of nodes
                    subset_sum(s,tuple,s_size,t_size+1,sum+s[i],i+1,target_sum);
                    //Consider next level node (along depth) -> for this branch , do the sum..
                }
            }
        }
        void printSubSet(int a[], int size){
            for (int i = 0; i < size; i++) {
                System.out.print(" "+a[i]);
            }
            System.out.println("");
        }
        
        boolean isColoringSafe(int v,int graph[][],int color[],int c){
            for(int i=0;i<graph.length;i++){
                if(graph[v][i]==1&&color[i]==c){
                    return false;//if the color 'c' is already assigned for any of the adjacent vertex of v, then return false, as we can't color them
                }
            }
            return true;
        }
        
        boolean graphMColoringUtil(int graph[][],int color[],int m,int v,int V){
            
            //Base case:
            if(v==V){//if all the vertices are colored then return true.
                return true;
            }
            
            //for the vertex v, check if any color can be assigned
            for(int c=1;c<=m;c++){
                
                if(isColoringSafe(v, graph, color, c)){
                
                       color[v] = c;//coloring the vertex v with color c
                       
                       if(graphMColoringUtil(graph, color, m, v+1, V)){
                           return true;//recur for teh rest of the vertices, if they returned true, then this vertex can be colored c
                       }
                       
                       color[v] = 0;//back track
                        /* If assigning color c doesn't lead
                   to a solution then remove it */
                    
                }
                
            }
             /* If no color can be assigned to this vertex
           then return false */
            return false;
            
        
        }
        boolean graphMColoring(int graph[][],int color[],int V,int m){
                
            for(int i=0;i<V;i++){
                color[i] = 0;//initially the colors are unassigned for all the vertices
            }
            
            if(graphMColoringUtil(graph,color,m,0,V)){
                System.out.println("Solution Exists: Following"
                        + " are the assigned colors");
                for (int i = 0; i < V; i++) {
                    System.out.print(" " + color[i] + " ");
                }
                System.out.println();
                return true;
            }else{
                System.out.println("Solution doesn't exist..");
                return false;
            }
        }
        boolean isSudokuSafe(int[][] grid,int row,int col,int num){
            int N = grid.length;
            //Check if it the number is already used in row
            for(int j=0;j<N;j++){
                if(grid[row][j]==num){
                    return false;
                }
            }
            //check if the number is already used in column
            for(int i=0;i<N;i++){
                if(grid[i][col]==num){
                    return false;
                }
            }
            
            //check if the number is already used in 3*3 sub grid instance.
            int boxStartRow = row - row%3;
            int boxStartCol = col - col%3;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){//3*3 matrix traversal
                    if(grid[i+boxStartRow][j+boxStartCol]==num){
                        return false;
                    }
                }
            }
            return true;
        }
        
        boolean solveSudoku(int[][] grid){
            
            //Base case: If there is no unassigned location, we are done
            int row=0,col = 0;
            int N = grid.length;
            boolean unassignedExist = false;
            for(row=0;row<N;row++){
                for(col=0;col<N;col++)
                {
                    if(grid[row][col] == 0){
                        unassignedExist = true;
                        break;
                    }
                    
                }
                if(unassignedExist){
                    break;
                }
            }
//                        System.out.println("Row "+row+" Col "+col);

            if(!unassignedExist){
                return true;//reached the end of matrix, and all the cells are assigned, then we found the solution return true;
            }
            
            for(int num=1;num<=9;num++){//from the break statemnet.
                //try 1 by 1 number from 0 to 9 and check if the sudoko is safe
                if(isSudokuSafe(grid,row,col,num)){
                    
                    grid[row][col] = num;//place the number in the cell
                    
                    //check recursively, if placing the number in that cell, yields solution
                    
                    if(solveSudoku(grid)){
                        return true;
                    }
                    
                    //failure, unmark and try again
                    grid[row][col] = 0;
                    
                }
            }
            
            return false;//if none of the number can be placed, then return false..
            //Does backtracking
        }
        
        void tugOfWarUtil(int[] arr, int n, boolean[] currElements, int no_of_selectedelements, boolean[] sol, Map minDiffMap, int sum, int curr_sum, int cur_position) {
        
            //currElements - To track inclusion / exclusion of element in the current set
            //no_of_selectedelements - Number of selected elements in the current set
            //sol - stores the solution of subset whose diff is minimum
            //MinDiff Map - map is used, instead of integer for finding the min diff found so far, as we are recursively calling the method, pass by reference not supported in java
            //sum - sum of all the elements in the array
            //cur_sum - sum of elements included in the set
            //cur_position - position we are in
            
            if(cur_position==n){
                return ;//if we are going out of bound return
            }
            
            // checks that the numbers of elements left are not less than the
    // number of elements required to form the solution
            if((n/2-no_of_selectedelements)>(n-cur_position)){
                // n/2 - no_of_selectedelements - Number of elements required to form the subset.
                return ;
            }
            
            //consider the case, when the current element is not included in the subset
            tugOfWarUtil(arr, n, currElements, no_of_selectedelements, sol, minDiffMap, sum, curr_sum, cur_position+1);
            
            //adding the current element to the subset
            currElements[cur_position] = true;
            curr_sum += arr[cur_position];
            no_of_selectedelements++;
            
            if(n/2==no_of_selectedelements){//if we had found that the number of elements required is equal to the selected elements
                
//                System.out.println("Current sum "+curr_sum);
                int min_diff = (Integer)minDiffMap.get("Min");
                if(Math.abs(sum/2-curr_sum)<min_diff){//check if the solution formed is better than the solution found so far.
                    // the diff between the current sum and sum/2 is minimal, then the solution exist
                    min_diff = Math.abs(sum/2-curr_sum);
                    minDiffMap.put("Min",min_diff);//Caller function also should be known that the min diff found , hence updating in the map
                    System.out.println("Min diff is "+min_diff);
                    for(int i=0;i<n;i++){
                        sol[i] = currElements[i];//copy the currelements to solution vector
                    }
                }
                
            }else{
                //consider the case, when the currenet element is inlcluded in the subset
                tugOfWarUtil(arr, n, currElements, no_of_selectedelements, sol, minDiffMap, sum, curr_sum, cur_position+1);
            }
            
            currElements[cur_position] = false;//Remove current element, before returning to the caller of this funciton.
            //if the solution is found, then currelements is unnecessary, as we had copied the values to solution vector
            //if the solution is not found, then we need to remove the current element from the set..
        }
        
        void tugOfWar(int arr[], int n){
            /*
            http://www.geeksforgeeks.org/tug-of-war/
            
                Given a set of n integers, divide the set in two subsets of n/2 sizes each such that the difference of the sum of two subsets is as minimum as possible. 
            
                If n is even, then sizes of two subsets must be strictly n/2 and if n is odd, then size of one subset must be (n-1)/2 and size of other subset must be (n+1)/2.

             For example, 
            
                let given set be {3, 4, 5, -3, 100, 1, 89, 54, 23, 20}, the size of set is 10. Output for this set should be {4, 100, 1, 23, 20} and {3, 5, -3, 89, 54}. 
            
                Both output subsets are of size 5 and sum of elements in both subsets is same (148 and 148).
            
                Let us consider another example where n is odd. Let given set be {23, 45, -34, 12, 0, 98, -99, 4, 189, -1, 4}. The output subsets should be {45, -34, 12, 98, -1} and {23, 0, -99, 4, 189, 4}. 
            
                The sums of elements in two subsets are 120 and 121 respectively.
            */
            boolean currElements[] = new boolean[n];//boolean to track inclusion / exlusion of element in the current set
            boolean sol[] = new  boolean[n];//To store the final solution (such that the min diff between the two subset is achieved)
            int min_diff = Integer.MAX_VALUE;
            
            int sum = 0;
            for(int i=0;i<n;i++){
                sum += arr[i];
                sol[i] = currElements[i] = false;
            }
            System.out.println("Total sum is "+sum);
            HashMap minDiffMap = new HashMap();
            minDiffMap.put("Min", min_diff);
            tugOfWarUtil(arr,n,currElements,0,sol,minDiffMap,sum,0,0);
            
            System.out.println("The first subset is ");
            for(int i=0;i<n;i++){
                if(sol[i]){
                    System.out.print(arr[i]+"\t");
                }
            }
            System.out.println("The second subset is ");
            for(int i=0;i<n;i++){
                if(!sol[i]){
                    System.out.print(arr[i]+"\t");
                }
            }
            
            
        }
        
        class Puzzle{
        
        }
        
        boolean solveCrypArithmeticPuzzle(Puzzle puzzle, String lettersToAssign){
            /*
            Newspapers and magazines often have crypt-arithmetic puzzles of the form:

             SEND
             + MORE
             --------
             MONEY
             -------- 

             The goal here is to assign each letter a digit from 0 to 9 so that the arithmetic works out correctly. The rules are that all occurrences of a letter must be assigned the same digit, and no digit can be assigned to more than one letter.

             First, create a list of all the characters that need assigning to pass to Solve
             If all characters are assigned, return true if puzzle is solved, false otherwise
             Otherwise, consider the first unassigned character
             for (every possible choice among the digits not in use)
             make that choice and then recursively try to assign the rest of the characters
             if recursion sucessful, return true
             if !successful, unmake assignment and try another digit

             If all digits have been tried and nothing worked, return false to trigger backtracking
            
            */
            if(lettersToAssign.isEmpty()){
                //no more choices to make.
                return puzzleSolved(puzzle);//check arithmetic to see if works
            }
            
            for(int digit=0;digit<=9;digit++){//try all digits
                if(assignLetterToDigit(lettersToAssign.charAt(0),digit)){//check if we can assign the digit to this letter
                   if(solveCrypArithmeticPuzzle(puzzle, lettersToAssign.substring(1))){//recursively check if we obtain solution for the rest of the strings, with the digit 
                       return true;
                   } 
                   //if we were not able to obtain the solution, then unassign letter from digit, back tracking, do for the other digits
                   unAssignLetterFromDigit(lettersToAssign.charAt(0),digit);
                }
            }
            return false;// If we were not able to obtain solution, then return false.
            
        }
        boolean unAssignLetterFromDigit(char letter, int digit){
            return true;
        }
        
        boolean assignLetterToDigit(char letter,int digit){
            return true;
        }
        boolean puzzleSolved(Puzzle puzzle){
            return true;
        }
         
        void mGraphColoringTestData(){
        /* Create following graph and test whether it is
         3 colorable
         (3)---(2)
         |   / |
         |  /  |
         | /   |
         (0)---(1)
         */
        int graph[][] = {{0, 1, 1, 1},
        {1, 0, 1, 0},
        {1, 1, 0, 1},
        {1, 0, 1, 0},};
        int m = 3; // Number of colors
        int color[] = new int[graph.length];
        graphMColoring(graph, color, graph.length, m);
    }
        
        void subSetTestData(){
            
//             int weights[] = new int[]{10, 7, 5, 18, 12, 20, 15};
             int weights[] = new int[]{10, 7, 5, 18, 4,12, 20,2, 15};
             
             int size = weights.length;
             
             System.out.println("Size "+size);
             int target_sum = 22;
             
             int tuple[] = new int[size];
             //tuple size initially 0
             subset_sum(weights, tuple, size, 0, 0, 0, target_sum);
            
             System.out.println("Total nodes generated "+totalNodesCount);
        }
        void solveMazeTestData(){
            int maze[][] = new int[][]{{1, 0, 0, 0},
            {1, 1, 0, 1},
         {0, 1, 0, 0},
         {1, 1, 1, 1}
         };

            solveMaze(maze);
        }
        
        void permutationTestData(){
            char str[] = "ABC".toCharArray();
            permute(str, 0, str.length-1);
        }
        
        void sudokuTestData(){
            int grid[][] = new int[][]{{3, 0, 6, 5, 0, 8, 4, 0, 0},
            {5, 2, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 7, 0, 0, 0, 0, 3, 1},
            {0, 0, 3, 0, 1, 0, 0, 8, 0},
            {9, 0, 0, 8, 6, 3, 0, 0, 5},
            {0, 5, 0, 0, 9, 0, 6, 0, 0},
            {1, 3, 0, 0, 0, 0, 2, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 4},
            {0, 0, 5, 2, 0, 6, 3, 0, 0}};
            if (solveSudoku(grid) == true) {
                int N = grid.length;
                for(int row=0;row<N;row++){
                    for(int col=0;col<N;col++){
                        System.out.print(grid[row][col]+"\t");
                    }
                    System.out.println("");
                }
            }else{
                System.out.println("No solution exists..");
            }
        }
        
        void tugOfWarTestData(){
            int arr[] = new int[]{23, 45, -34, 12, 0, 98, -99, 4, 189, -1, 4};
            tugOfWar(arr, arr.length);
        }
    
        public void backTrackingTestData(){
//            permutationTestData();
//            solveKnightsTour();
//            solveMazeTestData();
//            solveNQ();
//            subSetTestData();
//            mGraphColoringTestData();
//            sudokuTestData();
            tugOfWarTestData();
        }

    

    

    

    

}
