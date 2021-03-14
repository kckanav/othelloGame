package main.model.computerPlayers;

import main.model.Othello;

public abstract class ComPlayer {

    protected Othello game;
    private final int val;

    public ComPlayer(Othello game, int val) {
        if (game == null) {
            throw new IllegalArgumentException("Null game passed");
        }
        if (val != Othello.BLACK_PLAYER_REP && val != Othello.WHITE_PLAYER_REP) {
            throw new IllegalArgumentException("Invalid Value of player");
        }
        this.game = game;
        this.val = val;
    }

    /**
     * Calculates the position score for the entire board
     * @return an Integer representing by what score the computer player is leading in the game. A negative
     * value indicates the player is losing
     */
    public int positionScore() {
        int[][] board = game.getBoard();
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
     * Returns the move the computer player wants to move.
     * @return the move coordinates as row * 10 + col in the 0 indexed 2D board, or -1 if no move is found
     */
    public abstract int move();

    /**
     * Returns the value of the player this computer player is operating
     * @return {@code Othello.BLACK_PLAYER_REP} if the player is black, {@code Othello.WHITE_PLAYER_REP} if player is white.
     */
    public int getVal() {
        return this.val;
    }

    /**
     * Calculates the position score for a particular place on the board. It calculates position score based on where a certain
     * point is located on the board, and that is determined using some strategy.
     * @param row position row
     * @param col position column
     * @return the score for the position
     */
    protected abstract int position(int row, int col);

    /**
     * Returns the total score difference between the computer player and the user. Negative score indicates computer
     * is losing
     * @return the score difference
     */
    protected int scoreRatio() {
        int black = game.getScores()[0];
        int white = game.getScores()[1];
        if (val == Othello.BLACK_PLAYER_REP) {
            return black - white;
        } else { // com is white
            return white - black;
        }
    }

}
