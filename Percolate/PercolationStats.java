import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] samples;
    private static final double CONFIDENCE_95 = 1.96;
    // double mean;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        this.IllegalArgumentExceptionCheck(n, trials);
        this.samples = new double[trials];
        int sideLength = n;

        for (int trial = 0; trial < trials;trial++){
            int totalCellCount = sideLength*sideLength; 
            int currentIteration = sideLength*sideLength;
            Percolation p = new Percolation(sideLength);
            
            while (currentIteration > 0){
                int randRowCell = StdRandom.uniformInt(1, sideLength+1);
                int randColCell = StdRandom.uniformInt(1, sideLength+1);
                p.open(randRowCell,randColCell);
                if(p.percolates()){
                    double percolationThreshold = (double)p.numberOfOpenSites()/totalCellCount; 
                    this.samples[trial] = percolationThreshold;
                    break;
                }
                currentIteration--;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(this.samples);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(this.samples);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return (this.mean() - (CONFIDENCE_95 * this.stddev()/Math.sqrt(this.samples.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return (this.mean() + (CONFIDENCE_95 * this.stddev()/Math.sqrt(this.samples.length)));
    }
    private void IllegalArgumentExceptionCheck(int n, int trials){
        if (n <= 0 || trials < 0){
            throw new IllegalArgumentException("Illegal Argument for PercolationStats");
        }
    }

   // test client (see below)
   public static void main(String[] args){
    //TODO Test 12: check that exception is thrown if either n or trials is out of bounds



    // Test 7: check that PercolationStats calls open() until system percolates(What?)
        int gridsize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(gridsize, trials);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
   }

}
