

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * This ADT represents an game of Othello, and allows the user to play the game either with another player, or with a computer player.
 * It represents the board and stores information about where the white and black tiles are, what the current score is, and allows
 * functionality to make a new move on the board for a player.
 *
 * Starts the game with the initial 4 tiles of 2 black and 2 whites in the center.
 */
public class Othello {

    public static final int BLACK_PLAYER_REP = 1;
    public static final int WHITE_PLAYER_REP = 2;
    public static final int DEFAULT_SIZE = 8;

    protected int[][] board;
    protected int size;
    protected int black;
    protected int white;
    protected Boolean isSinglePLayer;
    protected Queue<Integer> lastMove;

    /**
     * Constructs a new game of othello with the starting 4 tiles on the board.
     */
    public Othello() {
        board = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        size = 0;
        black = 0;
        white = 0;
        lastMove = new LinkedList<>();

        // adding the 4 starting tiles to the game
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {

                // absolute value of col - row
                int val = j - i;
                val = val < 0? -1 * val: val;

                board[DEFAULT_SIZE / 2 - i][DEFAULT_SIZE / 2 - j] = val + 1;
                if (val == 1) {
                    black++;
                } else {
                    white++;
                }
            }
        }
    }

    /**
     * Creates a new game of othello based on the specifics of some existing game.
     * @param board the board of the current game.
     * @param black the total black tiles on the board
     * @param white the total white tiles on the board
     * @param isSinglePLayer
     */
    public Othello(int[][] board, int black, int white, boolean isSinglePLayer) {
        this.board = board;
        this.black = black;
        this.white = white;
        size = black + white;
        this.isSinglePLayer = isSinglePLayer;
        lastMove = new LinkedList<>();
        checkrep();
    }

    private void checkrep() {
        if (DEBUG) {
            assert boardIsValid();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    assert board[i][j] >= 0 && board[i][j] < 2: "Invalid board value, expected 1 or 2";
                }
            }
        }
    }

    /**
     * Places tiles on the board for the player specified on the board from the given moves. It is expected that the moves
     * are valid on the board, as no computation is done on the board. If the board is invalid, behaviour of game is unexpected.
     * @param moves the array of all moves to play
     * @param val player val who is performing the moves
     */
    public void place(String[] moves, int val) {
        checkrep();
        for (String s: moves) {
            board[position(s)[0]][position(s)[1]] = val;
            if (val == 1) {
                black++;
            } else {
                white++;
            }
            size++;
        }
        checkrep();
    }

    // TODO: A method for the controller, and not the model
//    public boolean place(int row, int col, int val) {
//        if (row > 7 || row < 0 || col > 7 || col < 0) {
//            throw new IllegalArgumentException();
//        }
//        return place(row, col, val);
//    }

    /**
     * Places a tile on the board with the given placement for the specified player. Indicates
     * whether the move was successful or not.
     * @param row the row of the position to place on in the board,
     * @param col the col of the position to play
     * @param val the player value of the player moving.
     * @return true if the move was successfully played and changes to the board were done, false otherwise.
     */
    public boolean place(int row, int col, int val) {
        checkrep();
        assert row > 0 && row < 7 && col > 0 && col < 7: "OutOfBoundsExceptionKinda!";
        if (!legal(row, col) || board[row][col] != 0) {
            return false;
        }

        // I need to check can it be placed.
        if (safe(row, col, val, false)) {
            int act = board[row][col];
            board[row][col] = val;
            if (val == 1) {
                if (act == 2) {
                    white--;
                }
                black++;
            } else {
                if (act == 1) {
                    black--;
                }
                white++;
            }
            size++;
            return true;
        } else {
            // if (runnable != null) {runnable.run(); }
            return false;
        }
    }

    public void remove(int row, int col) {
        board[row][col] = 0;
        while(!lastMove.isEmpty()) {
            int a = lastMove.remove();
            reverse(a / 10, a % 10, board[a / 10][a % 10]);
        }
    }

    public boolean safe(int row, int col, int val, boolean justCheck) {
        int rowStart = -1;
        int rowEnd = 1;
        int colStart = -1;
        int colEnd = 1;
        if (row == 0) { rowStart = 0; }
        if (col == 0) { colStart = 0; }
        if (row == 7) { rowEnd = 0; }
        if (col == 7) { colEnd = 0; }
        return check(row, col, rowStart, rowEnd, colStart, colEnd, val, justCheck);
    }

    protected boolean check(int row, int col, int rowStart, int rowEnd, int colStart, int colEnd, int val, boolean justCheck) {
        if (!justCheck) {
            lastMove.clear();
        }
        Queue<Integer> q = new LinkedList<>();
        boolean found = false;
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                if (board[row + i][col + j] != 0 && board[row + i][col + j] != val) {
                    boolean temp = pattern(row + i, col + j, diff(row, col, row + i, col + j), val, q, justCheck);
                    if (temp) {
                        if (justCheck) {
                            return true;
                        }
                        found = true;
                    }
                    q.clear();
                }
            }
        }
        return found;
    }

    protected boolean pattern(int row, int col, int[] patten, int val, Queue<Integer> s, boolean justCheck) {
        if (board[row][col] == val) {
            if (justCheck) {
                return true;
            }
            lastMove.addAll(s);
            flipDiscs(s, val);
            return true;
        } else {
            s.add(10 * row + col);
            int nRow = row + patten[0];
            int nCol = col + patten[1];
            if (legal(nRow, nCol) && board[nRow][nCol] != 0) {
                return pattern(nRow, nCol, patten, val, s, justCheck);
            } else {
                return false;
            }
        }
    }

//    protected boolean pattern(int row, int col, int[] patten, int val, Queue<Integer> s, boolean justCheck) {
//        if (board[row][col] == val) {
//            if (justCheck) {
//                return true;
//            }
//            lastMove.addAll(s);
//            flipDiscs(s, val);
//            return true;
//        } else {
//            int nRow = row + patten[0];
//            int nCol = col + patten[1];
//            if (legal(nRow, nCol) && board[nRow][nCol] != 0) {
//                s.add(10 * nRow + nCol);
//                return pattern(nRow, nCol, patten, val, s, justCheck);
//            } else {
//                return false;
//            }
//        }
//    }

    protected void flipDiscs(Queue<Integer> s, int val) {
        while(!s.isEmpty()) {
            int a = s.remove();
            change(a / 10, a % 10, val);
        }
    }

    protected void change(int row, int col, int val) {
        int act = board[row][col];
        if (val == 1 && act != val) {
            black++;
            if (act == 2) {
                white--;
            }
        } else if (val == 2 && act != val) {
            white++;
            if (act == 1) {
                black--;
            }
        }
        board[row][col] = val;
    }

    protected void reverse(int row, int col, int val) {
        // int act = board[row][col];
        if (val == 1) {
            black--;
            white++;
            board[row][col] = 2;
        } else {
            black++;
            white--;
            board[row][col] = 1;
        }

    }

    protected int[] diff(int row1, int col1, int row2, int col2) {
        return new int[]{(row2 - row1), (col2 - col1)};
    }

    public int[] scores(){
        return new int[]{black, white};
    }


    public boolean isPLaceAvail(int val) {
        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if (safe(i, j, val, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int[] position(String pos) {
        int col = pos.charAt(0) - 65;
        int row = pos.charAt(1) - 49;
        return new int[]{row, col};
    }

    protected String reversePosition(int row, int col) {
        return "" + ((char) (col + 65)) + ((char)(row + 49));
    }

    // Post: Prints out the current game board.
    public void print() {
        System.out.print("    ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(((char) (i + 65)) + "  ");
        }
        System.out.println();
        // Loop through all rows
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + 1 + " | ");
            // Loop through all elements of current row
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println(black + "" + white);
    }

    public boolean move() {
        boolean b = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[1].length; j++) {
                b = place(reversePosition(i, j), 2);
                if (b) {
                    return true;
                }
            }
        }
        return b;
    }

    // post: returns true iff row and col are legal for this board
    protected boolean legal(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }

    public boolean isLegal(String pos) {
        return legal(position(pos)[0], position(pos)[1]);
    }

    public boolean isEmpty(String pos) {
        return isLegal(pos) && board[position(pos)[0]][position(pos)[1]] == 0;
    }

    public int size() { return size; }

    public Othello clone() {
        // pause();
        return new Othello(board.clone(), black, white, isSinglePLayer);
    }
    private void pause() {
        pause(500);
    }

    private void pause(int delay) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
