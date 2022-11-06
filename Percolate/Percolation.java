import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   

    private boolean[][] percBoolGrid;
    private WeightedQuickUnionUF backwashUf;
    private WeightedQuickUnionUF normalUf;
    private int virtaulTop = 0;
    private int virtaulBottom;
    private int openSites;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0){ throw new IllegalArgumentException(); }

        boolean [][] boolGrid = new boolean[n][n];
        this.percBoolGrid = boolGrid;
        this.virtaulBottom = n*n + 1;
        this.backwashUf = new WeightedQuickUnionUF(n * n + 2);
        this.normalUf = new WeightedQuickUnionUF(n * n + 1); //without bottom index;
    }
    private void IllegalArgumentExceptionCheck(int row, int col) {
        if ((row < 1 || row > this.percBoolGrid.length) || (col < 1 || col > this.percBoolGrid.length)){
            throw new IllegalArgumentException("Illegal  Argument for intGrid");
        }
    }

    private int getCellIndex(int row, int col) {
        return (row-1) * this.percBoolGrid.length + (col -1);
        // return this.perc_intGrid[row-1][col-1];
    }
    private boolean getBoolGridCell(int row, int col) {
        return this.percBoolGrid[row-1][col-1];
    }
    private void openSite(int row, int col){
        this.percBoolGrid[row-1][col-1] = true;

        if (row == 1){
            this.backwashUf.union(this.getCellIndex(row, col),this.virtaulTop);
            this.normalUf.union(this.getCellIndex(row, col),this.virtaulTop);
        }
        if(row == this.percBoolGrid.length){
            this.backwashUf.union(this.getCellIndex(row, col),this.virtaulBottom);
        }

        this.openSites++;
    }

    public void printBoolGrid(){
        for (int i = 0; i < this.percBoolGrid.length;i++ ){
            for(int j = 0; j < this.percBoolGrid[i].length; j++){
                if(this.percBoolGrid[i][j]){
                    System.out.print("\u001B[32m" +"[" +this.percBoolGrid[i][j] + " ] " + "\u001B[0m");
                }else{
                    System.out.print("\u001B[31m" +"[" +this.percBoolGrid[i][j] + "] " + "\u001B[0m");
                }
            }
            System.out.println();
        }
    }

    //Grid from 1-n
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.IllegalArgumentExceptionCheck(row, col);

        //is this already open?
        if (isOpen(row,col)){ return; }

        //if not open then open
        this.openSite(row, col);
        
        //check neighbors are already open if yes union them;(used 1-n grid indexing)
        if (col - 1 >= 1 && isOpen(row,col-1)) {
            backwashUf.union(this.getCellIndex(row, col-1),this.getCellIndex(row, col));
            normalUf.union(this.getCellIndex(row, col-1),this.getCellIndex(row, col));
        }
        if (col + 1 <= this.percBoolGrid.length && isOpen(row,col+1)) {
            backwashUf.union(this.getCellIndex(row, col+1),this.getCellIndex(row, col));
            normalUf.union(this.getCellIndex(row, col+1),this.getCellIndex(row, col));
        }
        if (row + 1 <= this.percBoolGrid.length && isOpen(row + 1,col)) {
            backwashUf.union(this.getCellIndex(row +1 , col),this.getCellIndex(row, col));
            normalUf.union(this.getCellIndex(row +1 , col),this.getCellIndex(row, col));
        }
        if (row - 1 >= 1 && isOpen(row - 1,col)) {
            backwashUf.union(this.getCellIndex(row-1, col),this.getCellIndex(row, col));
            normalUf.union(this.getCellIndex(row-1, col),this.getCellIndex(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        this.IllegalArgumentExceptionCheck(row, col);
        return this.getBoolGridCell(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        this.IllegalArgumentExceptionCheck(row, col);
        return (this.normalUf.find(this.virtaulTop) == this.normalUf.find(this.getCellIndex(row, col)) &&  this.getBoolGridCell(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return backwashUf.find(this.virtaulTop) == backwashUf.find(this.virtaulBottom) && (this.numberOfOpenSites() >= 1); 
    }


    // // test client (optional)
    // public static void main(String[] args){
        
    //     Percolation p = new Percolation(1);
    //     p.printBoolGrid();
    //     System.out.println("Percolates?: " + p.percolates());

    // }
}
