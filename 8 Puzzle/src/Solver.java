import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    //rep
    private List<Node> mySolution;
    private List<Board> mySolution2;

    private int moves;
    private int twinMoves;
    private int finalMoves;
    private Board myBoard;
    private Board myTwinBoard;
    private MinPQ<Node> myPQueue;
    private MinPQ<Node> myTwinPQueue;
    private static final Comparator<Node> ByMinHeuristic = new ByMinHeuristic();
    private int size;
    private boolean solutionFound;
    
    private class Node {
        
        public Board board;
        public int moves;
        public Node prev;
        
        Node(Board board, int moves, Node prev) {
            
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }
        
        
    }
    private static class ByMinHeuristic implements Comparator<Node>
    {
        
        @Override
        public int compare(Node node1, Node node2)
        {
            if (node1.moves + node1.board.manhattan() > node2.moves + node2.board.manhattan())
                return 1;
            else if (node1.moves + node1.board.manhattan() < node2.moves + node2.board.manhattan())
                return -1;
            else return 0;
        }
    }
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        // fail fast
        if (initial == null)
            throw new java.lang.NullPointerException();
        
        // init rep
        myBoard = initial;
        myTwinBoard = initial.twin();


        
        size = initial.dimension();
        
        myPQueue = new MinPQ<Node>(size, ByMinHeuristic);
        myTwinPQueue = new MinPQ<Node>(size, ByMinHeuristic);
        
        solutionFound = false;
        
        mySolution = new ArrayList<Node>();
        mySolution2 = new ArrayList<Board>();
        
        moves = 0;
        twinMoves = 0;
        finalMoves = 0;
    
//      first node
        Node firstNode = new Node(myBoard, 0, null);
        Node firstTwinNode = new Node(myTwinBoard, 0, null);
        
//      Insert first node
        myPQueue.insert(firstNode);
        myTwinPQueue.insert(firstTwinNode);

        
        while (!myPQueue.min().board.isGoal() && !myTwinPQueue.min().board.isGoal()) {

            // delete min Node -> add it to the solution
            firstNode = myPQueue.delMin();
            firstTwinNode = myTwinPQueue.delMin();
            mySolution.add(firstNode);
            
            Board minBoard = firstNode.board;
            Board minTwinBoard = firstTwinNode.board;
            // StdOut.print(minTwinBoard.toString());
            
            moves++;
            twinMoves++;
            // my board
            for (Board b : minBoard.neighbors()) {
                if (firstNode.prev != null) {
                    if (!firstNode.prev.board.equals(b)) {
                        Node node = new Node(b, firstNode.moves + 1, firstNode);
                        myPQueue.insert(node);
                    }
                }
                else {
                    Node node = new Node(b, firstNode.moves + 1, firstNode);
                    myPQueue.insert(node);
                }
            }
                
            // twin board
            for (Board b : minTwinBoard.neighbors()) {
                if (firstTwinNode.prev != null) {
                    if (!firstTwinNode.prev.board.equals(b)) {
                        Node node = new Node(b, firstTwinNode.moves + 1, firstTwinNode);
                        myTwinPQueue.insert(node);

                    }
                }
                else {
                    Node node = new Node(b, firstTwinNode.moves + 1, firstTwinNode);
                    myTwinPQueue.insert(node);
                }
               
            }

        }
            
        
        if (myPQueue.min().board.isGoal()) {
            mySolution.add(myPQueue.delMin());
            solutionFound = true;        
            if (mySolution.isEmpty()) {
                

                mySolution2.add(myBoard);
            }
            else {
                Node next = mySolution.get(mySolution.size()-1);
                while (next != null) {
                    finalMoves++;

                    mySolution2.add(next.board);
                    next = next.prev;
                    
                }
                Collections.reverse(mySolution2);
            }
        }
        else if (myTwinPQueue.min().board.isGoal()) {
            solutionFound = false;  
            
        }
    }
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        return this.solutionFound;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (solutionFound)
            return finalMoves - 1   ;
        else
            return -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
       if (solutionFound)
           return mySolution2;
       else
           return null;
    }
    

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
