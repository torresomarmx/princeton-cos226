import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBoard {

    private Board board;

    @Before
    public void setUp() {
        int[][] testTiles = new int[3][3];
        testTiles[0] = new int[]{8,1,3};
        testTiles[1] = new int[]{4,0,2};
        testTiles[2] = new int[]{7,6,5};
        this.board = new Board(testTiles);
    }

    @After
    public void tearDown() {
        this.board = null;
    }

    @Test
    public void testGetHammingDistance() {
        assertEquals(5, this.board.hamming());
    }

    @Test
    public void testGetManhattanDistance() {
        assertEquals(10, this.board.manhattan());
    }

    @Test
    public void testIsSolvable() {
        assertTrue(this.board.isSolvable());
    }

    @Test
    public void testIsNotSolvable() {
        int[][] tiles = new int[2][2];
        tiles[0] = new int[]{1,0};
        tiles[1] = new int[]{2,3};
        assertFalse((new Board(tiles).isSolvable()));
    }
}
