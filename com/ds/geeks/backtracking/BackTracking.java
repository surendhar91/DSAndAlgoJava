/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.backtracking;

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
        
        void permutationTestData(){
            char str[] = "ABC".toCharArray();
            permute(str, 0, str.length-1);
        }
    
        public void backTrackingTestData(){
            permutationTestData();
            solveKnightsTour();
        }

    

}
