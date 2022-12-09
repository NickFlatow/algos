import java.util.Map;
import java.util.HashMap;
import java.util.Deque;
import java.util.ArrayDeque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private Board initialBoard;
    
    // Min priority queue to store the game nodes of the original puzzle
    private MinPQ<GameNode> minPq;
    
    // Min priority queue to store the game nodes of the puzzle's twin
    private MinPQ<GameNode> twinPq;

    // Flag to indicate whether the puzzle is solveable
    private boolean solveable = true; 

    // Map to store previously visited game nodes
    private Map<Board, Integer> visited;

    // Deque to store the sequence of boards that leads to the solution
    private Deque<Board> solution;

    // Game node representing the solved puzzle
    private GameNode solvedPuzzle;

    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initialBoard = initial;
        
        // Initialize the priority queues and the map of visited game nodes
        minPq = new MinPQ<>();
        twinPq = new MinPQ<>();
        visited = new HashMap<>();

        // Create the root game node and insert it into the minPq queue
        GameNode root = new GameNode(initialBoard, null);
        minPq.insert(root);        

        // Create the twin root game node and insert it into the twinPq queue
        GameNode twinRoot = new GameNode(initialBoard.twin(),null);
        twinPq.insert(twinRoot);

        // Initialize the deque to store the solution
        solution = new ArrayDeque<>();

        // Solve the puzzle
        solvedPuzzle = solvePuzzle();

    }

// Method to find a solution to the puzzle
private GameNode solvePuzzle() {
    // Continue searching for a solution until the puzzle is solved or unsolvable
    while (true) {

        // Get the game node with the lowest priority from the minPq queue
        GameNode parentNode = minPq.delMin();

        // Check if the parent node is the goal node
        if (parentNode.board.isGoal()) {
            // Puzzle is solved, return the game node representing the solution
            return parentNode;
        }

        // Add the parent node's board to the visited map with its move count as the value
        visited.put(parentNode.board, parentNode.moves);

        // Get the neighbors of the parent node's board
        Iterable<Board> neighbors = parentNode.board.neighbors();

        // Loop through the neighbors
        for (Board neighbor : neighbors) {

            // Check if the neighbor has been visited
            if (!visited.containsKey(neighbor)) {

                // Create a new game node for the neighbor
                GameNode neighborNode = new GameNode(neighbor, parentNode);

                // Insert the game node into the minPq queue
                minPq.insert(neighborNode);
            }
        }

        // Get the game node with the lowest priority from the twinPq queue
        GameNode twinParentNode = twinPq.delMin();

        // Check if the twin parent node is the goal node
        if (twinParentNode.board.isGoal()) {
            // Puzzle is unsolvable, set the solveable flag to false and return null
            solveable = false;
            return null;
        }

        // Add the twin parent node's board to the visited map with // its move count as the value
        visited.put(twinParentNode.board, twinParentNode.moves);

        // Get the neighbors of the twin parent node's board
        Iterable<Board> twinNeighbors = twinParentNode.board.neighbors();

        // Loop through the twin neighbors
        for (Board twinNeighbor : twinNeighbors) {

            // Check if the twin neighbor has been visited
            if (!visited.containsKey(twinNeighbor)) {

                // Create a new game node for the twin neighbor
                GameNode twinNeighborNode = new GameNode(twinNeighbor, twinParentNode);

                // Insert the game node into the twinPq queue
                twinPq.insert(twinNeighborNode);
            }   
        }
    }
}

// is the initial board solvable?
public boolean isSolvable() {
    return solveable;
}

// min number of moves to solve initial board; -1 if unsolvable
public int moves() {
    if (isSolvable()) {
        return solvedPuzzle.moves;
    } else {
        return -1;
    }
}

// sequence of boards in a shortest solution; null if unsolvable
public Iterable<Board> solution() {
    if (!isSolvable()) {
        return null;
    }

    // Create a stack to store the boards in reverse order
    Deque<Board> reverseSolution = new ArrayDeque<>();

    // Push the solved puzzle's board onto the stack
    reverseSolution.push(solvedPuzzle.board);

    // Set the current game node to the solved puzzle's previous game node
    GameNode current = solvedPuzzle.prev;

    // Loop through the previous game nodes until the root node is reached
    while (current != null) {

        // Push the current game node's // board onto the stack
        reverseSolution.push(current.board);

        // Set the current game node to its previous game node
        current = current.prev;
    }

    // Loop through the stack and add the boards to the solution deque in the correct order
    while (!reverseSolution.isEmpty()) {
        solution.add(reverseSolution.pop());
    }

    // Return the solution deque
    return solution;

}

// Inner class to represent a game node
private class GameNode implements Comparable<GameNode> {

    // The board associated with the game node
    public final Board board;

    // The previous game node
    public GameNode prev;

    // The number of moves to reach the game node
    public int moves;

    // The priority of the game node
    public int priority;

    // Compare two game nodes based on their priority
    @Override
    public int compareTo(GameNode other) {
        return Integer.compare(this.priority, other.priority);
    }

    // Constructor
    public GameNode(Board board, GameNode prev) {
        this.board = board;
        this.prev = prev;

        if (prev == null) {
            moves = 0;
        } else {
            moves = prev.moves + 1;
        }

        priority = board.manhattan() + moves;
        }
    }

// Main method to test the Solver class
public static void main(String[] args) {

    // Create a new board from the given file
    In in = new In("./8puzzle/puzzle2x2-unsolvable1.txt");
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            tiles[i][j] = in.readInt();
        }
    }
    Board initial = new Board(tiles);

    // Create a new Solver and solve the puzzle
    Solver solver = new Solver(initial);

    // Print the solution
    StdOut.println("Minimum number of moves = " + solver.moves());
    if (solver.isSolvable()) {
        for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}

