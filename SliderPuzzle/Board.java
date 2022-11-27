import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.naming.directory.ModificationItem;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    int n;
    int [][] tiles;
    int [][] goalBoard = {{1,2,3}, {4,5,6}, {7,8,0}};

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                this.tiles[i][j] = tiles[i][j];
            }
        }
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

        // s.append("\n");
        // for (int i = 0; i < n ; i++) {
        //     for (int j = 0; j < n; j++){
        //         s.append(String.format("%2d ", goalBoard[i][j]));
        //     }
        //     s.append("\n");
        // }
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
        ArrayList<Board> boards = new ArrayList<Board>();
        //0 row, 1 col
        int [] blankTile = new int [2];

        // find location of blank tile
        blankTile = findBlankTile();

        //find and swap n,s,e,w if with in array boundries
        if (blankTile[0] - 1 >= 0 ) {
            int[] swapTile = {blankTile[0] - 1,blankTile[1]};
            swap(blankTile,swapTile);
            //add to boards
            boards.add(new Board(tiles));
            //swap back for next
            swap(blankTile,swapTile);
        }
        if (blankTile[0] + 1 < n ) {
            int[] swapTile = {blankTile[0] + 1,blankTile[1]};
            swap(blankTile,swapTile);
            //add to boards
            boards.add(new Board(tiles));
            //swap back for next
            swap(blankTile,swapTile);
        }

        if (blankTile[1] - 1 >= 0 ) {
            int[] swapTile = {blankTile[0],blankTile[1]-1};
            swap(blankTile,swapTile);
            //add to boards
            boards.add(new Board(tiles));
            //swap back for next
            swap(blankTile,swapTile);
        }
        if (blankTile[1] + 1 < n ) {
            int[] swapTile = {blankTile[0],blankTile[1]+1};
            swap(blankTile,swapTile);
            //add to boards
            boards.add(new Board(tiles));
            //swap back for next
            swap(blankTile,swapTile);
        }

        return boards;
    }
    private int[] findBlankTile() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) return new int[] {row ,col};
            }
        }
        return new int[] {-5,-5};
    }

    private void swap(int[] blankTile, int[]swapTile) {
        // StdOut.println(toString());
        int tmp = tiles[blankTile[0]][blankTile[1]];
        tiles[blankTile[0]][blankTile[1]] = tiles[swapTile[0]][swapTile[1]];
        tiles[swapTile[0]][swapTile[1]] = tmp;
        // StdOut.println(toString());
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        //TODO SWAP BACK?

        int row = 0;
        int col = 1;
        int swapRow = n-1;
        int swapCol = n-1;
        while (tiles[row][col] == 0) {
            row = StdRandom.uniformInt(n);
            col = StdRandom.uniformInt(n);
        }
        while (tiles[swapRow][swapCol] == 0 || (tiles[swapRow][swapCol] == tiles[row][col])) {
            swapRow = StdRandom.uniformInt(n);
            swapCol = StdRandom.uniformInt(n);
        }
        StdOut.println("row col : " + tiles[row][col]);
        StdOut.println("swap : " + tiles[swapRow][swapCol]);

        swap(new int[] {row,col},new int[]{swapRow,swapCol});

        Board twin =  new Board(tiles);

        swap(new int[] {row,col},new int[]{swapRow,swapCol});
        return twin;

    }


    // unit testing (not graded)
    public static void main(String[] args) {
        int n = 3;
        int[] rand = new int[] {1,0,3,4,2,5,7,8,6};
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles [i][j] = rand[k];
                k++;
            }
        }
        
    
        // solve the slider puzzle
        Board initial = new Board(tiles);
        StdOut.println(initial.toString());
        StdOut.println(initial.twin());
        // StdOut.println(initial.toString());
        // StdOut.println(initial.toString());
        
        // for(Board b :initial.neighbors()){
        //     StdOut.println(b);
        // }
        // StdOut.println("hamming: " + initial.hamming());
        




    }

}