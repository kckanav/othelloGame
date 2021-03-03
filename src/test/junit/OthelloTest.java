package test.junit;

import org.junit.Before;
import org.junit.Test;

public class OthelloTest {

    public final int SIZE = 8;
    int[][] board;

    @Before
    public void createBoard() {
        board = new int[SIZE][SIZE];
    }

    // ----------------- Testing getPathForPattern -----------------------

    @Test
    public void testCanPlaceAtBorder() {
    }
}
