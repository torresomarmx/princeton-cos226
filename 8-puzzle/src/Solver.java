import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Scanner;

public class Solver {

    private int numberOfMoves = 0;

    private ArrayDeque<Board> boardsFromGoalToStart = new ArrayDeque<>();

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;

        private SearchNode previousNode;

        private int numberOfMoves;

        public SearchNode(Board board, SearchNode previousNode, int numberOfMoves) {
            this.board = board;
            this.previousNode = previousNode;
            this.numberOfMoves = numberOfMoves;
        }

        public Board getBoard() { return this.board; }

        public SearchNode getPreviousNode() { return this.previousNode; }

        public int getNumberOfMoves() { return this.numberOfMoves; }

        @Override
        public int compareTo(SearchNode searchNode) {
            return (this.board.manhattan() + this.numberOfMoves) - (searchNode.board.manhattan() + searchNode.numberOfMoves);
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (!initial.isSolvable())
            throw new IllegalArgumentException("Board is unsolvable");
        MinPriorityQueue<SearchNode> pq = new MinPriorityQueue<>();
        pq.insert(new SearchNode(initial, null , 0));

        SearchNode goalNode = null;
        while (pq.numberOfElements() > 0) {
            SearchNode currentNode = pq.deleteMin();
            if (currentNode.getBoard().isGoal()) {
                goalNode = currentNode;
                break;
            } else {
                for (Board neighbor : currentNode.getBoard().neighbors()) {
                    if (!neighbor.equals(currentNode.getBoard()))
                        pq.insert(new SearchNode(neighbor, currentNode, currentNode.getNumberOfMoves() + 1));
                }
            }
        }
        if (goalNode == null)
            throw new IllegalArgumentException("An error occurred");
        this.numberOfMoves = goalNode.getNumberOfMoves();
        SearchNode currentSearchNodeBackTrace = goalNode;
        while (currentSearchNodeBackTrace != null) {
            this.boardsFromGoalToStart.add(currentSearchNodeBackTrace.getBoard());
            currentSearchNodeBackTrace = currentSearchNodeBackTrace.getPreviousNode();
        }
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.numberOfMoves;
    }
    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return new SolutionPathIterable();
    }

    private class SolutionPathIterable implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new SolutionPathIterator();
        }

        private class SolutionPathIterator implements Iterator<Board> {
            @Override
            public boolean hasNext() {
                return Solver.this.boardsFromGoalToStart.size() > 0;
            }

            @Override
            public Board next() {
                return Solver.this.boardsFromGoalToStart.removeLast();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner fileScanner = new Scanner(new BufferedInputStream(new FileInputStream(args[0])));
        int n = fileScanner.nextInt();
        int[][] testTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                testTiles[i][j] = fileScanner.nextInt();
        }
        Board testBoard = new Board(testTiles);
        Solver solver = new Solver(testBoard);

        System.out.println(String.format("Number of moves: %d", solver.moves()));
        for (Board boardInPath : solver.solution()) {
            System.out.println(boardInPath);
            System.out.println();
        }
    }
}
