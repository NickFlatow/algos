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
    GameTree gameTree;
    boolean first = true;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        minPq = new MinPQ<Solver>(bo);

        minPq.insert(this);

        boards = new Stack<Board>();
        // gameTree = new GameTree(this.initial);
        gameTree = new GameTree();

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
        Stack<GameNode> nodes;
        // GameNode parentNode = root;

        // public GameTree(Board board) {
        public GameTree() {
            // root = new GameNode(initial);
            nodes = new Stack<GameNode>();
            // nodes.push(root);
        }

        public void push(Board board) {
            GameNode gn = new GameNode(board);
            gn.prev = root;
            nodes.push(gn);
        }

        public GameNode getParent() {
            // if (nodes.isEmpty()) {
            //     return null;
            // }
            // return nodes.peek();
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
    //TODO
    // public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        boards.push(minPq.min().initial);
        if(minPq.min().initial.isGoal()) {
            StdOut.println("=================== BOARDS ====================");
            for(Board b: boards) {
                StdOut.print(b);
            }
            StdOut.println("=================== BOARDS ====================");
            return boards;
        }
    
        Solver s = minPq.min();
        // GameNode parentNode = new GameNode(s.initial);
        gameTree.setRoot(s.initial);
        minPq.delMin();

        // gameTree.push(s.initial);
        GameNode parentNode = gameTree.getParent();
        // GameNode parentNode = new GameNode(s.initial);

        StdOut.println("=================== TREE ====================");
        StdOut.println(parentNode.board);
        if (parentNode.prev != null) { 
            StdOut.println(parentNode.prev.board);
        }else {
            StdOut.println(null);
        }
        StdOut.println("=================== TREE ====================");

        s.moves++;
        for (Board b: s.initial.neighbors()) {
            gameTree.push(b);
            Solver mySolver = new Solver(b);
            // mySolver.moves++;
            mySolver.moves += s.moves();
            
            // StdOut.println("neighbor board b :"); 
            
            // int priority = mySolver.initial.manhattan() + mySolver.moves();
            
            // StdOut.println("Priority  = " + priority);
            // StdOut.println("Manhattan = " + b.manhattan());
            // StdOut.println("moves     = " + mySolver.moves());
            // StdOut.println(b.toString());

            // StdOut.println(gameTree.min().initial.toString());

            // gameTree.insert(mySolver);



            //TODO UPDATE PARENT NODE ON EACH ITERATION
            // GameNode parentCheck = gameTree.peek();
            if (parentNode.prev != null && mySolver.initial.equals(parentNode.prev.board)) continue;
            
            minPq.insert(mySolver);
            

        }
        

        // StdOut.println("=================== MIN ====================");
        // StdOut.print(minPq.min().initial.toString());
        // StdOut.println("=================== MIN ====================");

        // this.moves++;

        // StdOut.println("=================== BOARDS ====================");
        // for(Board b: boards) {
        //     StdOut.print(b);
        // }
        // StdOut.println("=================== BOARDS ====================");

        StdOut.println("=================== minPq ====================");
        minPq.forEach(solver -> {
            int primmy = solver.moves() + solver.initial.manhattan();
            StdOut.println("Priority  = " + primmy);
            StdOut.println("moves     = " + solver.moves());
            StdOut.println("Manhattan = " + solver.initial.manhattan());
            StdOut.println("\r" +solver.initial.toString());
        });
        StdOut.println("=================== minPq ====================");


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
