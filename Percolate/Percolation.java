import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   

    private boolean[][] perc_boolGrid;
    private WeightedQuickUnionUF weighted_uf;
    private int virtaulTop = 0;
    private int virtaulBottom;
    private int openSites;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0){ throw new IllegalArgumentException(); }

        boolean [][] boolGrid = new boolean[n][n];
        this.perc_boolGrid = boolGrid;

        this.weighted_uf = this.initUnion(n);
    }

    private WeightedQuickUnionUF initUnion(int n) {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n*n);

        for (int i = 0; i < this.perc_boolGrid.length;i++){
            uf.union(this.virtaulTop, this.getCellIndex(1,i+1));
        }
        this.virtaulBottom = this.getCellIndex(this.perc_boolGrid.length,this.perc_boolGrid.length);
        for (int i = 0; i < perc_boolGrid.length;i++){
            uf.union(this.virtaulBottom, this.getCellIndex(this.perc_boolGrid.length,i+1));
        } 

        return uf;
    }
    private void IllegalArgumentExceptionCheck(int row, int col) {
        if ((row < 1 || row > this.perc_boolGrid.length) || (col < 1 || col > this.perc_boolGrid.length)){
            throw new IllegalArgumentException("Illegal  Argument for intGrid");
        }
    }

    public void printBoolGrid(){
        for (int i = 0; i < this.perc_boolGrid.length;i++ ){
            for(int j = 0; j < this.perc_boolGrid[i].length; j++){
                if(this.perc_boolGrid[i][j]){
                    System.out.print("\u001B[32m" +"[" +this.perc_boolGrid[i][j] + " ] " + "\u001B[0m");
                }else{
                    System.out.print("\u001B[31m" +"[" +this.perc_boolGrid[i][j] + "] " + "\u001B[0m");
                }
            }
            System.out.println();
        }
    }

    private int getCellIndex(int row, int col) {
        return (row-1) * this.perc_boolGrid.length + (col -1);
        // return this.perc_intGrid[row-1][col-1];
    }
    private boolean getBoolGridCell(int row, int col) {
        return this.perc_boolGrid[row-1][col-1];
    }
    private void openSite(int row, int col){
        this.perc_boolGrid[row-1][col-1] = true;
        this.openSites++;
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
        if (col - 1 >= 1){
            if(isOpen(row,col-1)) {
                weighted_uf.union(this.getCellIndex(row, col-1),this.getCellIndex(row, col));
            }
        }
        if (col + 1 <= this.perc_boolGrid.length) {
            if (isOpen(row,col+1)) {
                weighted_uf.union(this.getCellIndex(row, col+1),this.getCellIndex(row, col));
            }
        }
        if (row + 1 <= this.perc_boolGrid.length) { 
            if(isOpen(row + 1,col)) {
                weighted_uf.union(this.getCellIndex(row +1 , col),this.getCellIndex(row, col));
            }
        }
        if (row - 1 >= 1) { 
            if(isOpen(row - 1,col)) {
                weighted_uf.union(this.getCellIndex(row-1, col),this.getCellIndex(row, col));
            }
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
        // boolean find = 
        return (this.weighted_uf.find(this.virtaulTop) == this.weighted_uf.find(this.getCellIndex(row, col)) &&  this.getBoolGridCell(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weighted_uf.find(this.virtaulTop) == weighted_uf.find(this.virtaulBottom) && (this.numberOfOpenSites() >= 1); 
    }


    // // test client (optional)
    public static void main(String[] args){
        
        Percolation p = new Percolation(1);
        p.printBoolGrid();
        System.out.println("Percolates?: " + p.percolates());

    }
}
