import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    double[] samples;
    double mean;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
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
        double a =  1.96 * this.stddev();
        double b = Math.sqrt(this.samples.length);
        double c = a/b;
        double e = this.mean() - c;
        return e;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double a =  1.96 * this.stddev();
        double b = Math.sqrt(this.samples.length);
        double c = a/b;
        double e = this.mean() + c;
        return e;
    }

   // test client (see below)
   public static void main(String[] args){
        int gridsize = 200;
        int trials = 100;
        PercolationStats ps = new PercolationStats(gridsize, trials);

        System.out.println("mean: " + ps.mean());
        System.out.println("stddev: " + ps.stddev());
        System.out.println("[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
   }

}
