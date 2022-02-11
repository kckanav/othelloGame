package main.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This ADT represents an game of Othello, and allows the user to play the game either with another player, or with a computer player.
 * It represents the board and stores information about where the white and black tiles are, what the current score is, and allows
 * functionality to make a new move on the board for a player.
 *
 * Starts the game with the initial 4 tiles of 2 black and 2 whites in the center.
 *
 * IMPORTANT NOTE: The game is structured in such a way that it is currently not possible to remember each
 * position's history throughout the game. So, the remove() method only works for the most recently played move,
 * essentially working as an undo button that can only be used once before any new add operations.
 */
public class Othello {

    private static final boolean DEBUG = true;

    public static final int BLACK_PLAYER_REP = 1;
    public static final int WHITE_PLAYER_REP = 2;
    public static final int DEFAULT_SIZE = 8;


    protected int[][] board;
    protected int size;
    protected int black;
    protected int white;
    protected Queue<Integer> lastMoves; // All the discs that were flipped in the last move
    protected int lastMove;
    private boolean canRemove; // If current state allows for the last played move to be removed.

    /**
     * Constructs a new game of othello with the starting 4 tiles on the board.
     */
    public Othello() {

        board = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        black = 0;
        white = 0;
        lastMoves = new LinkedList<>();
        lastMove = -1;
        canRemove = false;

        // adding the 4 starting tiles to the game
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {

                // absolute value of col - row
                int val = j - i;
                val = val < 0? -1 * val: val;

                board[DEFAULT_SIZE / 2 - i][DEFAULT_SIZE / 2 - j] = val + 1;
                if (val == BLACK_PLAYER_REP) {
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
     */
    public Othello(int[][] board, int black, int white) {
        this.board = board;
        this.black = black;
        this.white = white;
        size = black + white;
        lastMoves = new LinkedList<>();
        canRemove = false;
        checkrep();
    }

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
        if (!legal(row, col) || board[row][col] != 0 || board[row][col] == val) {
            return false;
        }
        boolean wasPLaced = computeOrMutatePath(row, col, val, false);

        if (wasPLaced) {
            canRemove = true;
            lastMove = row * 10 + col;
            size++;
            if (val == BLACK_PLAYER_REP) {
                black++;
            } else {
                white++;
            }
            checkrep();
            return true;
        } else {
            // if (runnable != null) {runnable.run(); }
            return false;
        }
    }

    /**
     * Returns the current score of the game for each player
     * @return an array with the scores in the format [blackScore, whiteScore].
     */
    public int[] getScores() {
        return new int[]{black, white};
    }

    // TODO - What to return here
    /**
     * Returns the current representation of the board
     * @return an array representation of the board.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Undo the last move pleayed on the board
     * @return true if undo was successful, false otherwise.
     */
    public boolean undo() {
        try {
            remove(lastMove / 10, lastMove % 10);
            return true;
        } catch (Exception e) {
            System.out.println("Cannot Undo");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * removes the last played move in the game, along with all the tiles that were flipped as a consequence of
     * this move.
     * @param row the row of the last played move
     * @param col the column of the last played move
     * @throws RuntimeException if the remove operation cannot be performed on the current state of the game
     */
    public void remove(int row, int col) {
        checkrep();
        if (!canRemove) {
            throw new RuntimeException("Cannot remove at current state of the game");
        }
        int val = board[row][col];
        if (val == BLACK_PLAYER_REP) {
            black--;
            val = WHITE_PLAYER_REP;
        } else {
            white--;
            val = BLACK_PLAYER_REP;
        }
        board[row][col] = 0;
        flipDiscs(lastMoves, val);
        canRemove = false;
        lastMove = -1;
        lastMoves.clear();
        checkrep();
    }

    /**
     * Checks if the given point is a valid place to put the disc of the player given.
     * @param row the row of the point
     * @param col the col of the point
     * @param val the value of the player placing the disc
     * @return true iff the player can place their disc at the point in the current state of the game, false otehrwise
     */
    public boolean canPlaceAt(int row, int col, int val) {
        if (isNotOccupied(row, col)) {
            return computeOrMutatePath(row, col, val, true);
        } else {
            return false;
        }
    }

    /**
     * Checks if the given player can make any move on in the current state of the game
     * @param val the player to check for
     * @return true if there is a possible move, false if there is no place where the player can place a disc.
     */
    public boolean isPlaceAvail(int val) {
        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if (canPlaceAt(i, j, val)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a particular point is legal in the game
     * @param row row of the point to check
     * @param col col of the point
     * @return true if the point is legal in the game, false otherwise
     */
    public boolean legal(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }

    /**
     * Returns if the specific point in the borad is unoccupied
     * @param row the row of the point to check
     * @param col the column of the point
     * @return true if the point is unoccupied, true otherwise
     */
    public boolean isNotOccupied(int row, int col) {
        return board[row][col] == 0;
    }

    /**
     * Gets the size of the board the game is being played in.
     * @return the size of the board of the game
     */
    public int size() { return board.length; }

    /**
     * Clones the current game to a new game
     * @return the clone of this game as a new game
     */
    public Othello clone() {
        checkrep();
        // pause();
        return new Othello(board.clone(), black, white);
    }

    /** -------------- FIXME ---------- Game is currently over if player has no move, However, new player needs to mov
     * ----------------- again in that case !! Fix that ----------------------------
     */
    /**
     * Checks if the game is over or not. Checks if the next player has any possible moves, if not,
     * @return
     */
    public boolean isOver() {
        int player;
        if (legal(lastMove / 10, lastMove % 10)) {
            player = board[lastMove / 10][lastMove % 10];
            if (player == BLACK_PLAYER_REP) {
                return !isPlaceAvail(WHITE_PLAYER_REP);
            } else {
                return !isPlaceAvail(BLACK_PLAYER_REP);
            }
        }
        return !(isPlaceAvail(WHITE_PLAYER_REP) || isPlaceAvail(BLACK_PLAYER_REP));
    }

    /**
     * Prints out the current state of the game.
     */
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

    /**
     * checks the rep invariant of the game representation
     */
    private void checkrep() {
        if (DEBUG) {
            int w = 0;
            int b = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    assert board[i][j] >= 0 && board[i][j] < 2: "Invalid board value, expected 1 or 2";
                    boolean isNotIsolated = false;
                    if (board[i][j] == WHITE_PLAYER_REP) {
                        w++;
                    } else if (board[i][j] == BLACK_PLAYER_REP) {
                        b++;
                    }
                    for (int l = -1; l <= 1; l++) {
                        for (int k = -1; k <= 1; k++) {
                            if (legal(i + l, j + k)) {
                                isNotIsolated = board[i + l][j + k] != 0;
                            }
                        }
                    }
                    assert isNotIsolated;
                }
            }
            assert w == white && b == black: "Score calculation incorrect";
        }
    }


    /**
     * computes all the flips that needs to be flipped on the board if the place given is to be occupied
     * by the disc of the given player value. If justCheck is false, it also flips all the
     * discs on the board and the place given to the what it should be.
     * @param row the row of the place where val player places disc
     * @param col the col of the ^^
     * @param val the player value
     * @param justCheck true if existence of paths is needed, false if all discs needs to be flipped
     * @return true if there is at least one path, false otherwise.
     */
    private boolean computeOrMutatePath(int row, int col, int val, boolean justCheck) {
        if (!legal(row, col) || board[row][col] == val) {
            return false;
        }
        if (!justCheck && canPlaceAt(row, col, val)) {
            lastMoves.clear();
        }

        Queue<Integer> q = new LinkedList<>();
        boolean found = false;

        // Checking for all neighboring places around the place to check.
        for (int i = -1; i <= 1; i++) { // adjacent rows
            for (int j = -1; j <= 1; j++) { // adjacent columns
                if (i == j && j == 0) {
                    continue;
                }
                int currRow = row + i;
                int currCol = col + j;
                if (legal(currRow, currCol) && board[currRow][currCol] != 0 && board[currRow][currCol] != val) {
                    int[] pattern = getPattern(row, col, currRow, currCol);
                    getPathForPattern(currRow, currCol, pattern, val, q);
                    if (!q.isEmpty()) {
                        if (justCheck) {
                            return true;
                        }
                        flipDiscs(q, val);
                        lastMoves.addAll(q);
                        board[row][col] = val;
                        found = true;
                    }
                    q.clear();
                }
            }
        }
        return found;
    }

    /**
     * Computes all the discs that needs to be flipped from the given starting point and the pattern telling the
     * direction in which we need to find.
     *
     * The finding starts from the place adjacent to the actual starting point, and the actual starting point
     * is required to have the same value as the parameter val
     *
     * The queue passed is modified and is empty if no path is found
     * @param row the row of hte starting position
     * @param col the coloumn of the starting postion
     * @param patten the specific patten which describes the direction {@see computePattern}
     * @param val the value of the player
     */
    private void getPathForPattern(int row, int col, int[] patten, int val, Queue<Integer> s) {
//        assert board[row - patten[0]][col - patten[1]] == val: "Index out of bound, or Actual starting point is not correct";
        if (board[row][col] != val && board[row][col] != 0) {
            s.add(10 * row + col);
            int nRow = row + patten[0];
            int nCol = col + patten[1];
            if (legal(nRow, nCol) && board[nRow][nCol] != 0) {
                getPathForPattern(nRow, nCol, patten, val, s);
            } else {
                s.clear();
            }
        } else if (board[row][col] == 0) {
            s.clear();
        }
    }

    /**
     * Flips all the discs at postitions inside the queue to the value passed
     * @param s the queue containing all points
     * @param val the value the discs needs to flipped
     */
    private void flipDiscs(Queue<Integer> s, int val) {
       for (Integer a: s) {
            change(a / 10, a % 10, val);
        }
    }

    /**
     * Changes value of the disc at the specified point to that of the value, while also updating the
     * game state
     * @param row row of point
     * @param col columun
     * @param val the value to be switched to
     */
    private void change(int row, int col, int val) {
        int act = board[row][col];
        if (val == BLACK_PLAYER_REP && act != val) {
            black++;
            if (act == WHITE_PLAYER_REP) {
                white--;
            }
        } else if (val == WHITE_PLAYER_REP && act != val) {
            white++;
            if (act == BLACK_PLAYER_REP) {
                black--;
            }
        }
        board[row][col] = val;
    }

    /**
     * Returns the pattern between the two points. The pattern is just the direction the points point towards.
     * @param row1 row of point 1
     * @param col1 col of point 1
     * @param row2 row of point 2
     * @param col2 col of point 2
     * @return direction from point 2 to point 1 as array with [rowDirection, colDirection]
     */
    private int[] getPattern(int row1, int col1, int row2, int col2) {
        return new int[]{(row2 - row1), (col2 - col1)};
    }

}
