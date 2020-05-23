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
        int currentOrderedTile = 1;
        int[][] goalTiles = new int[this.size()][];
        for (int i = 0; i < this.size(); i++) {
            int[] subArray = new int[this.size()];
            for (int j = 0; j < this.size(); j ++) {
                subArray[j] = currentOrderedTile;
                currentOrderedTile += 1;
            }
            goalTiles[i] = subArray;
        }
        goalTiles[this.size()-1][this.size()-1] = 0;
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++)
                if (this.board[i][j] != goalTiles[i][j]) return false;
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
           for (int j = 0; j < this.size(); j++) {
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
    public boolean isSolvable() {
        int[] boardInRowMajor = new int[this.size() * this.size() - 1];
        int currentIdx = 0;
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++)
                if (this.board[i][j] != 0) boardInRowMajor[currentIdx++] = this.board[i][j];
        }
        int[] aux = new int[boardInRowMajor.length];
        long numberOfInversions = Board.countNumberOfInversions(aux, boardInRowMajor, 0, boardInRowMajor.length -1);
        if (this.board.length % 2 == 0) {
            // even sized board
            return (numberOfInversions + this.blankSquarePosition[0]) % 2 != 0;
        }
        // odd size board
        return numberOfInversions % 2 == 0;
    }

    private static long countNumberOfInversions(int[] auxiliaryArray, int[] listOfNumbers, int lowIdx, int highIdx) {
        if (highIdx <= lowIdx) return 0;
        int midIdx = lowIdx + ((highIdx - lowIdx) / 2);
        long numberOfLeftInversions = Board.countNumberOfInversions(auxiliaryArray, listOfNumbers, lowIdx, midIdx);
        long numberOfRightInversions = Board.countNumberOfInversions(auxiliaryArray, listOfNumbers, midIdx + 1, highIdx);
        for (int i = lowIdx; i <= highIdx; i++)
            auxiliaryArray[i] = listOfNumbers[i];

        long numberOfSplitInversions = 0;
        int leftPointerIdx = lowIdx;
        int rightPointerIdx = midIdx + 1;
        for (int i = lowIdx; i <= highIdx; i++) {
           if (leftPointerIdx > midIdx) {
               listOfNumbers[i] = auxiliaryArray[rightPointerIdx];
               rightPointerIdx += 1;
           } else if (rightPointerIdx > highIdx) {
               listOfNumbers[i] = auxiliaryArray[leftPointerIdx];
               leftPointerIdx += 1;
           } else if (auxiliaryArray[leftPointerIdx] > auxiliaryArray[rightPointerIdx]) {
               listOfNumbers[i] = auxiliaryArray[rightPointerIdx];
               rightPointerIdx += 1;
               numberOfSplitInversions += (midIdx - leftPointerIdx + 1);
           } else {
               listOfNumbers[i] = auxiliaryArray[leftPointerIdx];
               leftPointerIdx += 1;
           }
        }

        return numberOfLeftInversions + numberOfRightInversions + numberOfSplitInversions;
    }

    private int[] getCoordinates(int tileNumber) {
        int row = (tileNumber / this.size()) - 1;
        int col = tileNumber % this.size();
        if (col == 0) col = this.size() - 1;
        else {
            row += 1;
            col = -1 + col;
        }
        return new int[]{row, col};
    }

    public static void main(String[] args) throws Exception {
//        BufferedReader fileReader = new BufferedReader(new FileReader(args[0]));
//        String lineRead = fileReader.readLine();
//        int[] testNums = new int[100000];
//        int currentIdx = 0;
//        while (lineRead != null) {
//           testNums[currentIdx] = Integer.parseInt(lineRead);
//           currentIdx += 1;
//           lineRead = fileReader.readLine();
//        }
//        int[] aux = new int[100000];
//        System.out.println(Board.countNumberOfInversions(aux, testNums, 0, testNums.length -1));
//        int[] aux = new int[6];
//        int[] testNums = new int[]{6,5,4,3,2,1};
//        System.out.println(Board.countNumberOfInversions(aux, testNums, 0, testNums.length - 1));
        int[][] testTiles = new int[4][];
        testTiles[0] = new int[]{1,2,3,4};
//        testTiles[1] = new int[]{5,6,0,8};
//        testTiles[2] = new int[]{9,10,7,11};
//        testTiles[3] = new int[]{13,14,15,12};

        testTiles[1] = new int[]{5,6,7,8};
        testTiles[2] = new int[]{9,10,11,12};
        testTiles[3] = new int[]{13,14,15,0};
        Board testBoard = new Board(testTiles);
        System.out.println(testBoard.isSolvable());
        System.out.println(testBoard.isGoal());
        // System.out.println(testBoard);
        // System.out.println(Arrays.toString(testBoard.getCoordinates(13)));
        // System.out.println(testBoard.hamming());
        // System.out.println(testBoard.manhattan());
        // for (Board neighbor : testBoard.neighbors()) {
        //     System.out.println(neighbor);
        //     System.out.println("____");
        // }
    }
}