


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *Class Percolation 
 * 
 * @author accl
 *
 *
 */
public class Percolation {

    private boolean[] booleanGrid;
    private WeightedQuickUnionUF gridQuickFindUF;
    private WeightedQuickUnionUF fullnessUF;
    private int size;
    private int virtualTop;
    private int virtualBottom;   
    
    /**
     * 
     * Constructor: Creates a new percolation object. For all the class coordinates (1,1)
     * means the upper-left square of the grid
     * 
     * @param n the size of the percolation grid
     */
    
    public Percolation(int n) 
    {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException("n must be greater than 0");
        
        size = n;
        booleanGrid = new boolean[n * n];
        virtualTop = n*n+1;
        virtualBottom = n*n + 2;
        
        gridQuickFindUF = new WeightedQuickUnionUF(virtualBottom+1);
        fullnessUF = new WeightedQuickUnionUF(virtualBottom+1);
        
        if (size == 1) {
            
            gridQuickFindUF.union(virtualTop, 0);
            fullnessUF.union(virtualTop, 0);
            gridQuickFindUF.union(virtualBottom, 0);
            booleanGrid[0] = false;
        }
        else {
            
            for (int i = 0; i < n*n + 2; i++)
            {
    
                    if (i >= 0 && i < n) // connect top to virtual top
                    {   
                        gridQuickFindUF.union(virtualTop, i);
                        fullnessUF.union(virtualTop, i);
                        
                    }
                    
                    if (i >= size * (size - 1) && i < size * size) // Connect bottom to virtual bottom
                    {   
                        
                        gridQuickFindUF.union(virtualBottom, i);
                       
                    }
                    
                    if (i >= 0 && i < n*n) {
                        
                        booleanGrid[i] = false;
                    }
                    
            }
        }
        
    }
    /** 
     * Opens a cell in the grid. It updates the gridQuickFindUF with the new connected components
     * 
     * @param row
     * @param col
     */
    public void open(int row, int col)       // open site (row, col) if it is not open already
    {
        
        int rowPrime = row-1;
        int colPrime = col-1;
        
        // StdOut.println("open " + rowPrime + ", " + colPrime);
        if (rowPrime > size || rowPrime < 0)
            throw new java.lang.IndexOutOfBoundsException();
        if (colPrime > size || colPrime < 0)
            throw new java.lang.IndexOutOfBoundsException(); 

        booleanGrid[gridToOneDimension(rowPrime, colPrime)] = true; // opens the square
        
        int leftSquareCol = colPrime-1;
        int rightSquareCol = colPrime+1;
        int upSquareRow = rowPrime-1;
        int downSquareRow = rowPrime+1;
        
        // check if up is open and connect it to the current square if so
        if (upSquareRow >= 0 && upSquareRow < size)
        {
           
            if (isOpen(upSquareRow+1, colPrime+1)) {
                gridQuickFindUF.union(gridToOneDimension(upSquareRow, colPrime), 
                        gridToOneDimension(rowPrime, colPrime));
                fullnessUF.union(gridToOneDimension(upSquareRow, colPrime), 
                        gridToOneDimension(rowPrime, colPrime));
            }
        }
        
        // check if down is open and connect it to the current square if so
        if (downSquareRow >= 0 && downSquareRow < size)
        {
            
            if (isOpen(downSquareRow+1, colPrime+1)) {
                gridQuickFindUF.union(gridToOneDimension(downSquareRow, colPrime), 
                        gridToOneDimension(rowPrime, colPrime));
                
                fullnessUF.union(gridToOneDimension(downSquareRow, colPrime), 
                        gridToOneDimension(rowPrime, colPrime));
            }
        }
        
        // check if left is open and connect it to the current square if so    
        if (leftSquareCol >= 0 && leftSquareCol < size  && rowPrime < size)
        {
            
            if (isOpen(rowPrime+1, leftSquareCol+1)) {
                gridQuickFindUF.union(gridToOneDimension(rowPrime, leftSquareCol), 
                        gridToOneDimension(rowPrime, colPrime));
                fullnessUF.union(gridToOneDimension(rowPrime, leftSquareCol), 
                        gridToOneDimension(rowPrime, colPrime));
            }
        }
        
        // check if right is open and connect it to the current square if so   
        if (rightSquareCol >= 0 && rightSquareCol < size)
        {
            
            if (isOpen(rowPrime+1, rightSquareCol+1))
            {
                gridQuickFindUF.union(gridToOneDimension(rowPrime, rightSquareCol), 
                        gridToOneDimension(rowPrime, colPrime));
                fullnessUF.union(gridToOneDimension(rowPrime, rightSquareCol), 
                        gridToOneDimension(rowPrime, colPrime));
                
            }
        }
    }
  
    /**
     * Function that determines if a cell in the grid is open.
     * 
     * 
     * @param row
     * @param col
     * @return true if the (row, col) position is open, false otherwise
     */
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row > size || row <= 0)
            throw new java.lang.IndexOutOfBoundsException();
        if (col > size || col <= 0)
            throw new java.lang.IndexOutOfBoundsException(); 
        
        int rowPrime = row-1;
        int colPrime = col-1;
        

        // StdOut.println("row " + (rowPrime) + " col " + colPrime);
        return booleanGrid[gridToOneDimension(rowPrime, colPrime)];
        
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row > size || row <= 0)
            throw new java.lang.IndexOutOfBoundsException();
        if (col > size || col <= 0)
            throw new java.lang.IndexOutOfBoundsException(); 
        
        int rowPrime = row-1;
        int colPrime = col-1;

        return fullnessUF.connected(virtualTop, gridToOneDimension(rowPrime, colPrime)) && 
                isOpen(row, col);
    }
    /**
     * 
     * Function that calculates if the grid percolates
     * 
     * 
     * @return true if the grid percolates, false otherwise
     */
    public boolean percolates()              // does the system percolate?
    {
        if (size == 1) {
            return gridQuickFindUF.connected(virtualBottom, virtualTop) && isOpen(1, 1);   
        }
        else
            return gridQuickFindUF.connected(virtualBottom, virtualTop);
    }
    
    /**
     * returns one dimension coordinates for row,col pair
     * 
     * @param row is the row where 0 is the upper row
     * @param col is the col where 0 is the left row
     * @return one dimension coordinates where 0 is the upper-left square and n*n-1 is the bottom-right square
     */
    private int gridToOneDimension(int row, int col) {
        
        
        return row * size + col; 
    }
    
    
/**
 * 
 * Main function for testing - not yet developed
 * 
 * @param args
 */
    public static void main(String[] args)   // test client (optional)
    {

        
        

    }

}
