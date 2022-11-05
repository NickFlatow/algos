import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   
    int[][] intGrid;
    boolean[][] boolGrid;
    WeightedQuickUnionUF uf;
    int virtaulTop = 0;
    int virtaulBottom;
    int openSites;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0){ throw new IllegalArgumentException();}

        boolean [][] boolGrid = new boolean[n][n];
        this.boolGrid = boolGrid;

        this.intGrid = this.initIntGrid(n);
        this.uf = this.initUnion(n);
    }
    private int[][] initIntGrid(int n){
        int [][] intGrid = new int[n][n];
        int count = 0;
        for (int i = 0; i < intGrid.length;i++ ){
            for(int j = 0; j < intGrid[i].length; j++){
                intGrid[i][j] = count;
                count++;
            }
        }
        return intGrid;
    }
    private WeightedQuickUnionUF initUnion(int n){
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n*n);

        for (int i = 0; i < this.intGrid.length;i++){
            uf.union(this.virtaulTop, intGrid[0][i]);
        }
        this.virtaulBottom = this.intGrid[this.intGrid.length -1][this.intGrid.length -1];
        for (int i = 0; i < intGrid.length;i++){
            uf.union(this.virtaulBottom, this.intGrid[this.intGrid.length-1][i]);
        } 

        return uf;
    }
    private void IllegalArgumentExceptionCheck(int row, int col) throws IllegalArgumentException{
        if ((row < 1 || row > this.intGrid.length) || (col < 1 || col > this.intGrid.length)){
            throw new IllegalArgumentException("Illegal  Argument for intGrid");
        }
    }
    public int getIntGridCell(int row, int col){
        return this.intGrid[row-1][col-1];
    }
    private boolean getBoolGridCell(int row, int col){
        return this.boolGrid[row-1][col-1];
    }
    private void openSite(int row, int col){
        this.boolGrid[row-1][col-1] = true;
        this.openSites++;
    }

    // private boolean connected(int p, int q){
    //     if (this.getIntGridCell(p, q))
    // }

    //Grid from 1-n
    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        this.IllegalArgumentExceptionCheck(row, col);

        //is this already open?
        if (isOpen(row,col)){ return; }

        //if not open then open
        this.openSite(row, col);
        
        //check neighbors are already open if yes union them;(used 1-n grid indexing)
        if (col - 1 >= 1){
            if(isOpen(row,col-1)){
                uf.union(this.getIntGridCell(row, col-1),this.getIntGridCell(row, col));
            }
        }
        if (col + 1 <= this.intGrid.length){
            if (isOpen(row,col+1)){
                uf.union(this.getIntGridCell(row, col+1),this.getIntGridCell(row, col));
            }
        }
        if (row + 1 <= this.intGrid.length){ 
            if(isOpen(row + 1,col)){
                uf.union(this.getIntGridCell(row +1 , col),this.getIntGridCell(row, col));
            }
        }
        if (row - 1 >= 1){ 
            if(isOpen(row - 1,col)){
                uf.union(this.getIntGridCell(row-1, col),this.getIntGridCell(row, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IllegalArgumentException{
        this.IllegalArgumentExceptionCheck(row, col);
        return this.getBoolGridCell(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        this.IllegalArgumentExceptionCheck(row, col);
        // return this.uf.find(this.virtaulTop) == this.uf.find(this.getIntGridCell(row, col));
        return this.getBoolGridCell(row, col);
    }

    // // returns the number of open sites
    public int numberOfOpenSites(){
        return this.openSites;
    }

    // // does the system percolate?
    public boolean percolates(){
        return uf.find(this.virtaulTop) == uf.find(this.virtaulBottom); 
    }


    public void printBoolGrid(){
        for (int i = 0; i < boolGrid.length;i++ ){
            for(int j = 0; j < boolGrid[i].length; j++){
                if(boolGrid[i][j]){
                    System.out.print("\u001B[32m" +"[" +boolGrid[i][j] + " ] " + "\u001B[0m");
                }else{
                    System.out.print("\u001B[31m" +"[" +boolGrid[i][j] + "] " + "\u001B[0m");
                }
            }
            System.out.println();
        }
    }


    public void printIntGrid(){
        for (int i = 0; i < this.intGrid.length;i++ ){
            for(int j = 0; j < this.intGrid[i].length; j++){
                System.out.print("[" + this.intGrid[i][j]  + "] ");
            }
            System.out.println();
        }
        
    }

    // test client (optional)
    public static void main(String[] args){

        Percolation p = new Percolation(3);
        // p.open(1,2);
        // p.open(2,1);
        // p.open(2,3);
        // p.open(3,2);

        // //open the middle site
        // p.open(2,2);

        p.open(1,2);
        p.open(2,1);
        p.open(3,1);
        p.printBoolGrid();
        // System.out.println((p.uf.find(p.getIntGridCell(1, 1)) ==  p.uf.find(p.getIntGridCell(2, 2))));

        // System.out.println(args[0]);
        // int sideLength = Integer.parseInt(args[0]);
        // StdRandom.setSeed(20);
        // boolean percolated = false;
        // int sideLength = 5;
        // Percolation p = new Percolation(sideLength);
        
        // int n = sideLength*sideLength;
        // // System.out.println("I'm a blank guy");
        // // p.printBoolGrid();
        
        // while (n > 0){
        //     // System.out.println();
        //     int randCol = StdRandom.uniformInt(0,sideLength);
        //     int randRow = StdRandom.uniformInt(0,sideLength);
        //     p.open(randRow,randCol);
        //     if (p.percolates()){
        //         double output = (double)n/(sideLength*sideLength);
        //         System.out.println("Percolation Threshold: " + output);
        //         percolated = true;
        //         break;
        //     }
        //     // System.out.println("randCol: " + randCol + " randRow: " +randRow);
        //     // p.printBoolGrid();
            
        //     n--;
        // }
        // if(!percolated){
        //     System.out.println("did not percolate");
        // }
        // System.out.println();
        // p.printBoolGrid();
        // System.out.println(p.uf.count());
        // // p.printIntGrid();
    }
}
