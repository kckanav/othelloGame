// A white color player.

/**
 * This class represents a computer player. Allows functionality to get a computer player move on an ongoing game of othello.
 * It can be instantiated with any valid game of othello, and it tracks the game's updates automatically.
 */

import java.util.Stack;

public class ComPlayer {

    private static final int INNER_CENTER = 3;
    private static final int OUTER_CENTER = 1;
    private static final int BORDER = 3;
    private static final int CORNERS = 5;

    private final Othello game;

    /**
     * The value of the computer player
     */
    public final int val;

    /**
     * Creates a new computer player over this game of type val
     * @param game the game of othello in which the computer player is needed
     * @param val 1 if com player is for black, 0 if it is white
     * @throws IllegalArgumentException if game is null or val is any integer other than 0 or 1
     */
    public ComPlayer(Othello game, int val) {
        if (game == null || val > 1 || val < 0) {
            throw new IllegalArgumentException("Invalid player value");
        }
        this.game = game;
        this.val = val;
    }

    /**
     * Computes a new move for the computer player based on all the strategies implemented. Finds the best place to move on
     * the current board.
     * @return an integer representing the new place, as row * 10 + column
     */
    public int move() {
        int num = 0;
        Stack<Integer> s = new Stack<>();
        int bestRow = -1;
        int bestCol = -1;
        int initialS = scoreRatio();
        int posS = positionScore();
        int max = -10;
        for (int i = 0; i < game.size(); i++) {
            for (int j = 0; j < game.size(); j++) {
                boolean canBePlaced = game.place(i, j, val);
                if (canBePlaced) {
                    int diff = scoreAdv(initialS, posS);
                    if (diff > max) {
                        max = diff;
                        bestRow = i;
                        bestCol = j;
                    }
                    num++;
                    game.remove(i, j);
                }
            }
        }
        System.out.println(num);
        return bestRow * 10 + bestCol;
    }

    /**
     * Calculates the position score for the entire board
     * @return an Integer representing by what score the computer player is leading in the game. A negative
     * value indicates the player is losing
     */
    private int positionScore() {
        int[][] board = game.board;
        int size = game.size();
        int whiteScore = 0;
        int blackScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    int a = board[i][j];
                    if (a == 1) {
                        blackScore += position(i, j);
                    } else {
                        whiteScore += position(i, j);
                    }
                }
            }
        }
        if (val == 1) {
            return blackScore - whiteScore;
        } else {
            return whiteScore - blackScore;
        }
    }

    /**
     * Calculates the position score for a particular place on the board. It calculates position score based on where a certain
     * point is loacted on the board, and that is determined using some strategy.
     * @param row postion row
     * @param col position column
     * @return the score for the position
     */
    private int position(int row, int col) {
        int size = game.size();
        if ((row == size / 2 - 1 || row == size / 2) && (col == size / 2 - 1|| col == size / 2)) { // Inner center
            return INNER_CENTER;
        } else if ((row >= size / 4 && row < size - size / 4 ) && (col >= size / 4 && col < size - size / 4 )) { // Outer center
            return OUTER_CENTER;
        } else if((row == size - 1 || row == 0) && (col == size - 1 || col == 0)) { // corners
            return CORNERS;
        } else if ((row == size - 1 || row == 0) || (col == size - 1 || col == 0)) { // border
            return BORDER;
        } else {
            return 0;
        }
    }

    /**
     * Returns the total score difference between the computer player and the user. Negative score indicates computer
     * is losing
     * @return the score difference
     */
    private int scoreRatio() {
        if (val == 1) { // com is black
            return game.black - game.white;
        } else { // com is white
            return game.white - game.black;
        }
    }

    /**
     * Calculates the difference of overall board between two different versions of the board. So, essentially helps in
     * seeing how much the score difference is before and after a possible move to see the viability of the move.
     * @param initialScoreRatio the initital score of the board (Tile score)
     * @param initialPositionScore the score based on the psotion of the tiles
     * @return the overall score difference in the board.
     */
    private int scoreAdv(int initialScoreRatio, int initialPositionScore) {
        // 0.5 * tile score difference + position score difference
        return (int)((scoreRatio() - initialScoreRatio) * 0.5) + (positionScore() - initialPositionScore);
    }

}
