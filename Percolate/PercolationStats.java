import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class PercolationStats {

    double[] mean;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){        
        int sideLength = n;
        this.mean = new double[trials];

        for(int trial = 0;trial < trials; trial++){
            int totalGridCells = sideLength*sideLength;
            Percolation p = new Percolation(n);
            
            while (totalGridCells > 1){
                // System.out.println();
                int randCol = StdRandom.uniformInt(1,sideLength+1);
                int randRow = StdRandom.uniformInt(1,sideLength+1);
                p.open(randRow,randCol);
                if (p.percolates()){
                    double output = (double)p.numberOfOpenSites()/(sideLength*sideLength);
                    
                    // p.printBoolGrid();
                    // System.out.println(p.numberOfOpenSites());
                    // if (output > 0.0){
                    this.mean[trial] = output;
                    // }
                    // StdOut.println("Percolation Threshold: " + output);
                    // StdOut.println();
                    // percolated = true;
                    break;
                }
                totalGridCells--;
            }
            // p.printBoolGrid();
            // System.out.println();
            
        }
   


    }

    // sample mean of percolation threshold
    public double mean(){
        double sum = 0.0;
        
        for(int i = 0; i < this.mean.length; i++){
            sum += this.mean[i];
        }
        return sum/this.mean.length;
    }

    // // sample standard deviation of percolation threshold
    // public double stddev()

    // // low endpoint of 95% confidence interval
    // public double confidenceLo()

    // // high endpoint of 95% confidence interval
    // public double confidenceHi()

   // test client (see below)
   public static void main(String[] args){
    // int gridsize = Integer.parseInt(args[0]);
    // int trials = Integer.parseInt(args[1]);
    int gridsize = 200;
    int trials = 100;
    PercolationStats ps = new PercolationStats(gridsize, trials);

    System.out.println(ps.mean());
   }

}
