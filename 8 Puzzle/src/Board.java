import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.StdOut;
public class Board {
    
    // rep
    private int[][] board;
    private int size;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {                                   // (where blocks[i][j] = block in row i, column j)
        if (blocks == null)
            throw new java.lang.NullPointerException();
        
        size = blocks.length;
        board = new int[size][size];
        
        for (int i = 0; i < blocks.length; i++) {
            
            for (int j = 0; j < blocks.length; j++) { 

                    board[i][j] = blocks[i][j];
            }
            
        }
    }
                                          
    public int dimension()                 // board dimension n
    {
        return size;
    }
    public int hamming()                   // number of blocks out of place
    {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            
            for (int j = 0; j < board.length; j++) { 

                if(board[i][j] != oneDCoords(i, j) && board[i][j] != 0)
                    count++;
            }
            
        }
        return count;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            
            for (int j = 0; j < board.length; j++) {

                if (board[i][j] != 0) {
                   count += Math.abs(twoDCoordsRow(board[i][j]) -i)  + Math.abs(twoDCoordsCol(board[i][j]) - j);
                }
                // StdOut.println("sq[" + i + "][" + j + "] = " + board[i][j] + ", expected row " + twoDCoordsRow(board[i][j]) + ", expected col " + twoDCoordsCol(board[i][j]) + ", count " + count);
                
            }
            
        }
        return count;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return this.manhattan() == 0;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] myTwinBoard = new int[this.size][this.size];
        for (int i = 0; i < board.length; i++) {
            
            for (int j = 0; j < board.length; j++) { 
                myTwinBoard[i][j] = board[i][j];
            }
            
        }
        
        if (myTwinBoard[0][0] != 0 && myTwinBoard[1][1] != 0) {
            int aux = myTwinBoard[0][0]; 
            myTwinBoard[0][0] = myTwinBoard[1][1];
            myTwinBoard[1][1] = aux;
        }
        else {
            int aux = myTwinBoard[1][0]; 
            myTwinBoard[1][0] = myTwinBoard[0][1];
            myTwinBoard[0][1] = aux;
            
        }

        return new Board(myTwinBoard);
    }
    
    @Override
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y != null)
            return this.toString().hashCode() == y.toString().hashCode();
        else
            return false;
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        List<Board> myListOfBoards = new ArrayList<Board>();
        int[][] myBoard = board;
        int aux = 0;
        for (int i = 0; i < board.length; i++) {
            
            for (int j = 0; j < board.length; j++) { 
                // up
                if (board[i][j] == 0) {

                    if (i-1 >= 0) {
                        
                        myBoard[i][j] = myBoard[i-1][j]; 
                        myBoard[i-1][j] = 0;
                        myListOfBoards.add(new Board(myBoard));

                        myBoard[i-1][j] = myBoard[i][j]; 
                        myBoard[i][j] = 0;
                    }
                    
                    // left
                    if (j-1 >= 0) {
                        
                        myBoard[i][j] = myBoard[i][j-1]; 
                        myBoard[i][j-1] = 0;
                        myListOfBoards.add(new Board(myBoard));

                        myBoard[i][j-1] = myBoard[i][j]; 
                        myBoard[i][j] = 0;
                    }
                    // right
                    if (j+1 < myBoard.length) {
                        
                        myBoard[i][j] = myBoard[i][j+1]; 
                        myBoard[i][j+1] = 0;
                        myListOfBoards.add(new Board(myBoard));

                        myBoard[i][j+1] = myBoard[i][j]; 
                        myBoard[i][j] = 0;
                    }
                    // down
                    if (i+1 < myBoard.length) {
                        
                        myBoard[i][j] = myBoard[i+1][j]; 
                        myBoard[i+1][j] = 0;
                        myListOfBoards.add(new Board(myBoard));

                        myBoard[i+1][j] = myBoard[i][j]; 
                        myBoard[i][j] = 0;
                    }
                    
                }
                
            }
            
        }
        return myListOfBoards;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        String s = size + "\n" + " ";
        for (int i = 0; i < board.length; i++) {
            
            for (int j = 0; j < board.length; j++) {
                
                if (board[i][j] < 10) {
                    s += " ";
                }
                if (board[i][j] < 100) {
                    s += " ";
                }
                s += " " + board[i][j] + " ";
                if (j == board.length -1)
                    s += "\n ";
            }
            
        }
        return s;
    }

    public static void main(String[] args) // unit tests (not graded)
    {
       
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Board initial2 = new Board(blocks);
        StdOut.println("is initial2 equal to initial?" + initial.equals(initial2));
        StdOut.println("is initial equal to initial twin?" + initial.equals(initial.twin()));
        StdOut.println("Size " + initial.dimension());
        StdOut.println("Hamming " + initial.hamming());
        StdOut.println("manhattan " + initial.manhattan());
        StdOut.println("is solved? " + initial.isGoal());
        StdOut.println(initial.toString());
        for (Board s : initial.neighbors()) {
            
            StdOut.println(s.toString());
            StdOut.println("manhattan " + s.manhattan());
        }
    }
    
    private int oneDCoords(int i, int j) {
        
        return i*size + j + 1;
    }
    
    private int twoDCoordsRow (int number) {
        
        if (number % size == 0)
            return number/size - 1;
        else
            return number/size;
    }
    
    private int twoDCoordsCol (int number) {
        if (number % (size) == 0)
            return size -1;
        else
            return number % (size) -1;
    }
    

}