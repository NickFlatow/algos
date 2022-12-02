import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private int n;
    private int [][] tiles;
    private int [][] goalBoard;
    private int [] blankTile;
    
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("tiles == null");
        }    
        int goalCount = 1;
        this.n = tiles.length;

        this.goalBoard = new int[n][n];
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                
                this.tiles[i][j] = tiles[i][j];
                goalBoard[i][j] = goalCount;
                
                if (tiles[i][j] == 0) blankTile = new int[] {i ,j};
                
                goalCount++;
            }
        }
        goalBoard[n-1][n-1] = 0;
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n; j++){
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() { 
        int outOfPlaceTileCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) continue;
                if (this.tiles[i][j] != goalBoard[i][j]) outOfPlaceTileCount++;
            }
        }
        return outOfPlaceTileCount;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int totalManhattanDistance = 0;
        //get tile int for tiles
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                //get tile from tiles
                int tile = this.tiles[i][j];

                //find that tile in goalboard
                if (tile == 0) continue;
                int[] tileCoordsGoalBoard = findTileInGoalBoard(tile);
                totalManhattanDistance += this.distanceFromHome(tileCoordsGoalBoard, i, j);
            }
        }
        return totalManhattanDistance;
    }
    private int[] findTileInGoalBoard(int tile) {
        //TODO
        //already a sorted list
        //binary serach on 2d array?
        for (int row = 0; row < n; row++) {
            int rowUpperBounds = n * row + n;

            if (tile > rowUpperBounds) continue; //if tile isn't in row skip and check the next row
            
            //or bin search here?
            //TODO STOPWATCH WITH DIFFERENT VARIATIONS
            for (int col = 0; col < n; col++) { 
                if (goalBoard[row][col] == tile) return new int[] {row , col};
            }
        }

        return new int[] { -1,-1};
    }

    private int distanceFromHome(int[] tileCoordsGoalBoard, int i, int j) {
        return Math.abs(tileCoordsGoalBoard[0] - i) + Math.abs(tileCoordsGoalBoard[1] - j);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.deepEquals(tiles, goalBoard);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board board = (Board) y;
        return tiles.length == board.tiles.length && Arrays.deepEquals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();

        //find and swap n,s,e,w if with in array boundries
        if (blankTile[0] - 1 >= 0 ) {
            int[] swapTile = {blankTile[0] - 1,blankTile[1]};
            boards.push(swapBlankTile(blankTile,swapTile));
            
        }
        if (blankTile[0] + 1 < n ) {
            int[] swapTile = {blankTile[0] + 1,blankTile[1]};
            boards.push(swapBlankTile(blankTile,swapTile));
        }

        if (blankTile[1] - 1 >= 0 ) {
            int[] swapTile = {blankTile[0],blankTile[1]-1};
            boards.push(swapBlankTile(blankTile,swapTile));
        }
        if (blankTile[1] + 1 < n ) {
            int[] swapTile = {blankTile[0],blankTile[1]+1};
            boards.push(swapBlankTile(blankTile,swapTile));
        }

        return boards;
    }

    private void swap(int[][] copy, int[] blankTile, int[]swapTile) {
        // StdOut.println(toString());
        int tmp = copy[blankTile[0]][blankTile[1]];
        copy[blankTile[0]][blankTile[1]] = tiles[swapTile[0]][swapTile[1]];
        copy[swapTile[0]][swapTile[1]] = tmp;
        // StdOut.println(toString());
    }

    private Board swapBlankTile(int[] blankTile, int[]swapTile) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {      
                copy[i][j] = tiles[i][j];
            }
        }
        swap(copy, blankTile,swapTile);
        return new Board(copy);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {      
                copy[i][j] = tiles[i][j];
            }
        }

        int row = StdRandom.uniformInt(n);;
        int col = StdRandom.uniformInt(n);;
        int swapRow = StdRandom.uniformInt(n);;
        int swapCol = StdRandom.uniformInt(n);;
        while (copy[row][col] == 0) {
            row = StdRandom.uniformInt(n);
            col = StdRandom.uniformInt(n);
        }
        while (copy[swapRow][swapCol] == 0 || (copy[swapRow][swapCol] == copy[row][col])) {
            swapRow = StdRandom.uniformInt(n);
            swapCol = StdRandom.uniformInt(n);
        }
        // StdOut.println("row col : " + tiles[row][col]);
        // StdOut.println("swap : " + tiles[swapRow][swapCol]);

        swap(copy,new int[] {row,col},new int[]{swapRow,swapCol});

        Board twin =  new Board(copy);
        return twin;

    }


    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In("./8puzzle/puzzle16.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        int[][] tiles1 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
                tiles1[i][j] = tiles[i][j];    
            }
        }
    
        // solve the slider puzzle
        Board fin = new Board(tiles1);
        Board initial = new Board(tiles);
        StdOut.println(initial.isGoal());
        StdOut.println(initial.toString());
        StdOut.println(initial.twin());
        StdOut.println(initial.dimension());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.equals(fin));
        StdOut.println(initial.neighbors());
        // StdOut.println(initial.toString());


        // for (int i = 0; i < 20000; i++) { 
        //     Board twin1 = initial.twin();
        //     Board twin2 = initial.twin();
        //     if (twin1.equals(twin2)) {
        //         StdOut.println("duplicate");
        //     }
        // }
        
        // for(Board b :initial.neighbors()){
        //     StdOut.println(b);
        // }
        // StdOut.println("hamming: " + initial.hamming());
        




    }

}