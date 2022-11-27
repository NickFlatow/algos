import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    Board initial;
    MinPQ<Solver> minPq;
    BoardOrder bo = new BoardOrder();
    int moves = 0;
    Stack<Board> boards;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        minPq = new MinPQ<Solver>(bo);
        minPq.insert(this);
        boards = new Stack<Board>();
    }

    // private class BoardOrder implements Comparator<Board> {
        
    //     @Override
    //     public int compare(Board b1, Board b2) {
    //         if (b1.manhattan() + moves < b2.manhattan() + moves) return -1;
    //         if (b1.manhattan() + moves > b2.manhattan() + moves) return 1;
    //         return 0;
    //     }
    // }

    private class BoardOrder implements Comparator<Solver> {
        
        @Override
        public int compare(Solver s1, Solver s2) {
            if (s1.initial.manhattan() + s1.moves < s2.initial.manhattan() + s2.moves) return -1;
            if (s1.initial.manhattan() + s1.moves > s2.initial.manhattan() + s2.moves) return 1;
            return 0;
        }
    }
    // is the initial board solvable? (see below)
    // public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        boards.push(minPq.min().initial);
        if(minPq.min().initial.isGoal()) {
            return boards;
        }


        // minPq = new MinPQ<Solver>(bo);
        // minPq.insert(new Solver(initial));
        minPq.forEach(s -> {
            // StdOut.println(s.initial.toString());
            // s.moves++;
            for (Board b :s.initial.neighbors()) {
                Solver mySolver = new Solver(b);
                mySolver.moves++;
                minPq.insert(mySolver);
            }
            
            // StdOut.println("Manhattan = " + s.initial.manhattan());
            // StdOut.println("moves     = " + s.moves());
            // int priority = s.initial.manhattan() + s.moves();
            // StdOut.println("Priority = " + priority);
            // StdOut.println(s.initial.toString());
        });
        // StdOut.println("min :" + minPq.min().initial.toString());
        StdOut.println("Manhattan = " + minPq.min().initial.manhattan());
        StdOut.println("moves     = " + minPq.min().moves());
        int priority = minPq.min().initial.manhattan() + minPq.min().moves();
        StdOut.println("Priority = " + priority);
        StdOut.print(minPq.min().initial.toString());
        // boards.push(minPq.min().initial);
        minPq.delMin();
        this.moves++;

        this.solution();

        return boards;
    }

    // test client (see below) 
    public static void main(String[] args) {

        int n = 3;
        int[] rand = new int[] {0,1,3,4,2,5,7,8,6};
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
        Solver solver = new Solver(initial);
        solver.solution();

    }
}
