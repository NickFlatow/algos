
import java.util.Comparator;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private Board initial;
    private MinPQ<Solver> minPq;
    private BoardOrder bo = new BoardOrder();
    private int moves = 0;
    private Stack<Board> boards;
    private GameTree gameTree;
    
    private GameTree gameTreeTwin;
    private MinPQ<Solver> twinMinpq;
    private Boolean solvable = false;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        minPq = new MinPQ<Solver>(bo);
        minPq.insert(this);
        boards = new Stack<Board>();
        gameTree = new GameTree();

        //minPq should be of type node
        twinMinpq = new MinPQ<Solver>(bo);
        Solver twinSolver = new Solver(initial.twin());
        twinMinpq.insert(twinSolver);
        gameTreeTwin = new GameTree();
    }

    private class GameNode {
        Board board;
        GameNode prev;

        public GameNode(Board board) {
            this.board = board;
            prev = null;
        }
    }
    private class GameTree {
        GameNode root = null;

        public void push(Board board) {
            GameNode gn = new GameNode(board);
            gn.prev = root;
        }

        public GameNode getParent() {
            return root;
        }
        public void setRoot(Board b) {
            if (root != null ) {
                GameNode oldRoot = root;
                root = new GameNode(b);
                root.prev = oldRoot;
            } else {
                root = new GameNode(b);
            }

        }
    }

    private class BoardOrder implements Comparator<Solver> {
        
        @Override
        public int compare(Solver s1, Solver s2) {
            if (s1.initial.manhattan() + s1.moves < s2.initial.manhattan() + s2.moves) return -1;
            if (s1.initial.manhattan() + s1.moves > s2.initial.manhattan() + s2.moves) return 1;
            return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        solution();
        return solvable;
    }

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
    
        Solver s = minPq.min();
        gameTree.setRoot(s.initial);
        minPq.delMin();

        GameNode parentNode = gameTree.getParent();

        this.moves++;

        for (Board b: s.initial.neighbors()) {
            gameTree.push(b);
            
            Solver mySolver = new Solver(b);
            mySolver.moves += s.moves();
            
            if (parentNode.prev != null && mySolver.initial.equals(parentNode.prev.board)) continue;
            
            minPq.insert(mySolver);
        }

        if(twinMinpq.min().initial.isGoal()) {
            solvable = false;
        }
    
        Solver twinS = twinMinpq.min();
        gameTreeTwin.setRoot(twinS.initial);
        twinMinpq.delMin();

        GameNode twinParentNode = gameTreeTwin.getParent();

        // this.moves++;

        for (Board b: twinS.initial.neighbors()) {
            gameTreeTwin.push(b);
            
            Solver twinSolver = new Solver(b);
            twinSolver.moves += twinS.moves();
            
            if (twinParentNode.prev != null && twinSolver.initial.equals(twinParentNode.prev.board)) continue;
            
            twinMinpq.insert(twinSolver);
        }

        this.solution();

        return boards;
    }

    // test client (see below) 
    public static void main(String[] args) {

        In in = new In("./8puzzle/puzzle04.txt");
        int n = in.readInt();

        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();  
            }
        }
        
    
        // solve the slider puzzle
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
