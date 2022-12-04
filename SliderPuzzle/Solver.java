
import java.util.Comparator;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private Board initial;
    
    private MinPQ<GameNode> minPq;
    private MinPQ<GameNode> twinPq;

    private boolean solveable = true; 
    private BoardOrder bo = new BoardOrder();
    
    private int moves = -1;
    private Stack<Board> boards;

    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        
        minPq = new MinPQ<GameNode>(bo);
        GameNode root = new GameNode(initial, null);
        root.priority = root.board.manhattan() + this.moves;
        minPq.insert(root);        


        twinPq = new MinPQ<GameNode>(bo);
        GameNode twinRoot = new GameNode(initial.twin(), null);
        twinRoot.priority = twinRoot.board.manhattan() + this.moves;
        twinPq.insert(twinRoot);

        boards = new Stack<Board>();
        solvePuzzle();

    }
    private void solvePuzzle() {
        boolean solved = false;
        while (!solved) {
            
            GameNode parentNode = minPq.delMin();
            
            boards.push(parentNode.board);
            
            if(parentNode.board.isGoal()) {
                solved = true;
            }

            
            StdOut.println("=================== Parent  ====================");
            StdOut.print(parentNode.board.toString());
            StdOut.println("=================== parent ====================");

            this.moves++;
            
            StdOut.println("=================== childrens  ====================");
            for (Board board: parentNode.board.neighbors()) {
    
                GameNode childNode = new GameNode(board, parentNode.board);

                
                if (parentNode.prev != null && childNode.board.equals(parentNode.prev.board)) continue;

                    childNode.priority = board.manhattan() + this.moves;
                    StdOut.println("priotiry  = " + childNode.priority);
                    StdOut.println("moves     = "  + this.moves);
                    StdOut.println("manhattan = " + board.manhattan());
                    StdOut.println("hamming   = " + board.hamming());
                    StdOut.print(childNode.board.toString());
                    
                    minPq.insert(childNode);
            }
            StdOut.println("=================== childrens ====================");


            GameNode twinParentNode = twinPq.delMin();
            if(twinParentNode.board.isGoal()) {
                solveable = false;
                solved = true;
            }

            // StdOut.println("=================== Twin Parent  ====================");
            // StdOut.print(twinParentNode.board.toString());
            // StdOut.println("=================== Twin Parent ====================");
            // if (twinParentNode.board.equals(parentNode.board)) break;
            

            // this.moves++;

            // StdOut.println("=================== Twin Child  ====================");
            for (Board board: twinParentNode.board.neighbors()) {
    
                GameNode childNode = new GameNode(board, twinParentNode.board);
                childNode.priority = board.manhattan() + this.moves;
                
                // StdOut.print(childNode.board.toString());
                if (twinParentNode.prev != null && childNode.board.equals(twinParentNode.prev.board)) continue;
                
                twinPq.insert(childNode);
            }
            // StdOut.println("=================== Twin Child  ====================");
        }
    }

    private class GameNode {
        Board board;
        GameNode prev;
        int priority;

        public GameNode(Board board,Board prev) {
            this.board = board;
            prev = null;
        }
    }
    private class BoardOrder implements Comparator<GameNode> {
        
        @Override
        public int compare(GameNode n1, GameNode n2) {
            if (n1.priority < n2.priority) return -1;
            if (n1.priority > n2.priority) return 1;
            return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solveable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!solveable) {
            return -1;
        }
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return boards;
    }

    // test client (see below) 
    public static void main(String[] args) {

        In in = new In("./8puzzle/puzzle4x4-10.txt");
        // In in = new In("./8puzzle/puzzle04.txt");
        // In in = new In(args[0]);
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
        if (!solver.isSolvable()) {
            // StdOut.println(solver.moves());
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                // StdOut.println(solver.moves());
                StdOut.println(board);
            }
            StdOut.println("Minimum number of moves = " + solver.moves());
        }

    }
}
