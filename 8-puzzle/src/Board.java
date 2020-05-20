import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private int[][] board;

    private int hammingDistance = 0;

    private int manhattanDistance = 0;

    private int[] blankSquarePosition;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles.length == 0)
            throw new IllegalArgumentException("tiles cannot be empty");
        this.board = tiles;
        int currentOrderedTile = 1;
        for (int i = 0; i < this.board.length; i++) {
           if (this.board[i].length != this.board.length)
               throw new IllegalArgumentException("tiles must be an n *n grid");
           for (int j = 0; j < this.board.length; j++) {
               if (this.board[i][j] == 0) {
                   this.blankSquarePosition = new int[]{i, j};
               } else if (this.board[i][j] != currentOrderedTile) {
                   this.hammingDistance += 1;
                   int[] coordinatesForMisplacedTile = this.getCoordinates(this.board[i][j]);
                   this.manhattanDistance += (Math.abs(coordinatesForMisplacedTile[0] - i) +
                           Math.abs(coordinatesForMisplacedTile[1] - j));
               }
            currentOrderedTile += 1;
           }
        }
        if (this.blankSquarePosition == null)
            throw new IllegalArgumentException("No blank square found");
    }

    // string representation of this board
    public String toString() {
        String[] boardString = new String[this.size()];
        for (int i = 0; i < this.size(); i++) {
            String[] currentRow = new String[this.size()];
            for (int j = 0; j < this.size(); j++)
               currentRow[j] = this.board[i][j] + " ";
            boardString[i] = String.join(" ", currentRow);
        }
        return String.join("\n", boardString);
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
       return this.board[row][col];
    }

    // board size n
    public int size() {
        return this.board.length;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int currentOrderTile = 1;
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j ++) {
                if (currentOrderTile != this.board[i][j]) return false;
            }
        }
        return true;
    }

    // // does this board equal y?
    public boolean equals(Object y) {
       if (this == y)
           return true;
       if (y == null || getClass() != y.getClass())
           return false;
       Board yBoard = (Board) y;
       if (yBoard.size() != this.size()) return false;
       for (int i = 0; i < this.size(); i++) {
           for (int j = 0; j < this.size(); i++) {
               if (this.tileAt(i, j) != yBoard.tileAt(i,j))
                   return false;
           }
       }
       return true;
    }

    // // all neighboring boards
    public Iterable<Board> neighbors() {
        return new NeighborsIterable();
    }

    private class NeighborsIterable implements Iterable<Board> {

        private int[][] neighborCoordinates = new int[4][];

        private int numberOfNeighbors = 0;

        public NeighborsIterable() {
            int currentNeighborIndex = 0;
            // check left of blank square
            if (Board.this.blankSquarePosition[1] > 0) {
                neighborCoordinates[currentNeighborIndex] = new int[]{
                        Board.this.blankSquarePosition[0],
                        Board.this.blankSquarePosition[1] - 1
                };
                currentNeighborIndex += 1;
            }

            // check right of blank square
            if (Board.this.blankSquarePosition[1] < Board.this.size() - 1) {
               neighborCoordinates[currentNeighborIndex] = new int[]{
                       Board.this.blankSquarePosition[0],
                       Board.this.blankSquarePosition[1] + 1
               };
               currentNeighborIndex += 1;
            }

            // check top of blank square
            if (Board.this.blankSquarePosition[0] > 0) {
                neighborCoordinates[currentNeighborIndex] = new int[]{
                        Board.this.blankSquarePosition[0] - 1,
                        Board.this.blankSquarePosition[1]
                };
                currentNeighborIndex += 1;
            }

            // check bottom of blank square
            if (Board.this.blankSquarePosition[0] < Board.this.size() - 1) {
                neighborCoordinates[currentNeighborIndex] = new int[]{
                        Board.this.blankSquarePosition[0] + 1,
                        Board.this.blankSquarePosition[1]
                };
                currentNeighborIndex += 1;
            }
            this.numberOfNeighbors = currentNeighborIndex;
        }
        public Iterator<Board> iterator() {
            return new NeighborsIterator();
        }

        private class NeighborsIterator implements Iterator<Board> {

           int currentNeighborIndex = 0;
           public boolean hasNext() {
               return this.currentNeighborIndex < NeighborsIterable.this.numberOfNeighbors;
           }

           public Board next() {
                if (!this.hasNext())
                    throw new NoSuchElementException();
                int[][] neighborTiles = new int[Board.this.size()][];
                for (int i = 0; i < Board.this.size(); i++) {
                    int[] row = new int[Board.this.size()];
                    for (int j = 0; j < Board.this.size(); j++)
                        row[j] = Board.this.board[i][j];
                    neighborTiles[i] = row;
                }
                int[] currentNeighborCoordinates = NeighborsIterable.this.neighborCoordinates[this.currentNeighborIndex];
                neighborTiles[Board.this.blankSquarePosition[0]][Board.this.blankSquarePosition[1]] =
                        neighborTiles[currentNeighborCoordinates[0]][currentNeighborCoordinates[1]];
                neighborTiles[currentNeighborCoordinates[0]][currentNeighborCoordinates[1]] = 0;
                this.currentNeighborIndex += 1;
                return new Board(neighborTiles);
           }

           public void remove() {
               throw new UnsupportedOperationException();
           }
        }
    }

    // // is this board solvable?
    // public boolean isSolvable() {}

    public int[] getCoordinates(int tileNumber) {
        //TODO check that tileNumber is not out of bounds
        int row = (tileNumber / this.size()) - 1;
        int col = tileNumber % this.size();
        if (col == 0) col = this.size() - 1;
        else {
            row += 1;
            col = -1 + col;
        }
        return new int[]{row, col};
    }

    // unit testing (required)
    public static void main(String[] args) {
        int[][] testTiles = new int[3][3];
        testTiles[0] = new int[]{0,1,3};
        testTiles[1] = new int[]{4,8,2};
        testTiles[2] = new int[]{7,6,5};
        Board testBoard = new Board(testTiles);
        System.out.println(testBoard);
        System.out.println(Arrays.toString(testBoard.getCoordinates(13)));
        System.out.println(testBoard.hamming());
        System.out.println(testBoard.manhattan());
        for (Board neighbor : testBoard.neighbors()) {
            System.out.println(neighbor);
            System.out.println("____");
        }
    }

}