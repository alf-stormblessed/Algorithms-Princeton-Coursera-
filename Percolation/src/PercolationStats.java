import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

        
        private double mean;
        private double[] samplesArray;
        private double stddev;
        private double confidenceLo;
        private double confidenceHi;
/**
 * Constructor for PercolationStats. It actually does all the work. Performs 
 * as many trials of nxn grids of percolation and calculates stats  
 * 
 * @param n the size of the grid
 * @param trials the number of trials to calculate
 */
        public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
        {
            
            if (n < 1 || trials < 1)
            {
                throw new IllegalArgumentException("arguments n and trials must be bigger than 1");
            }
            
            samplesArray = new double[trials];
            
            for (int i = 0; i < trials; i++)
            {
                
                // StdOut.println("trial number " + (i + 1) + " starting");
                
                Percolation percolation = new Percolation(n);

                
                int counter = 0;
                
                while (!percolation.percolates())
                {
                    boolean cellNotOpen = true;
                    int row = 1;
                    int col = 1;
                    
                    while (cellNotOpen)
                    {
                        row = StdRandom.uniform(n) + 1;
                        col = StdRandom.uniform(n) + 1;
                        cellNotOpen = percolation.isOpen(row, col);
                        
                    }
                    
                    percolation.open(row, col);
                    counter++;
                    // StdOut.println("Opening cell number " + counter);
                }
                
                samplesArray[i] = (double) counter / (n * n);

                
            }
            
            // StdOut.println("Calculating stats "); 
            mean = StdStats.mean(samplesArray);
            stddev = StdStats.stddev(samplesArray);
            confidenceLo = mean - (1.96 * stddev) / Math.sqrt(trials);
            confidenceHi = mean + (1.96 * stddev) / Math.sqrt(trials);           
            
            
        }
        
        /**
         * 
         * @return the mean calculated before
         */
        public double mean()                          // sample mean of percolation threshold
        {
            
            return mean;
        }
        /**
         * 
         *         
         * @return the stddev calculated before
         */
        public double stddev()                        // sample standard deviation of percolation threshold
        {
            return stddev;
            
        }
        /**
         * 
         * 
         * @return the confidenceLo calculated before
         */
        public double confidenceLo()                  // low  endpoint of 95% confidence interval
        {
            
            return confidenceLo;
        }
        
        /**
         * 
         * 
         * @return the confidenceHi calculated before
         */
        public double confidenceHi()                  // high endpoint of 95% confidence interval
        {
            return confidenceHi;
            
        }
        
        /**
         * Main function takes as arguments n and trials. Calls the constructor and performs trials times 
         * the percolation experiment of nxn sized grid and 
         * prints the stats on console
         * 
         * @param args args[0] -> n, args[1] -> trials
         */
        public static void main(String[] args)    // test client (described below)
        {
            int n = 0;
            int trials = 0; 
            if (args.length > 0) 
            {
                n = Integer.parseInt(args[0]);
                trials = Integer.parseInt(args[1]);

            }          
            
            
            
            StdOut.println("Starting " + trials + " trials of Percolation of size " + n + "x" + n);
            PercolationStats myPercolationStats = new PercolationStats(n, trials);
            
            StdOut.println("mean = " + myPercolationStats.mean());
            
            StdOut.println("stddev = " + myPercolationStats.stddev());
            
            StdOut.println("95% confidence interval = " + myPercolationStats.confidenceHi() +
                    ", " + myPercolationStats.confidenceHi());
            
        }
    
}
