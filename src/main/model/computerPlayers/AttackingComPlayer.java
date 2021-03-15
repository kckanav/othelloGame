package main.model.computerPlayers;

import main.model.Othello;

/**
 * This class represents a computer player. Allows functionality to get a computer player move on an ongoing game of othello.
 * It can be instantiated with any valid game of othello, and it tracks the game's updates automatically.
 */
public class AttackingComPlayer extends ComPlayer {

    private static final int INNER_CENTER = 3;
    private static final int OUTER_CENTER = 1;
    private static final int BORDER = 3;
    private static final int CORNERS = 50; // including borders


    /**
     * Creates a new computer player over this game of type val
     * @param game the game of othello in which the computer player is needed
     * @param val 1 if com player is for black, 0 if it is white
     * @throws IllegalArgumentException if game is null or val is any integer other than 0 or 1
     */
    public AttackingComPlayer(Othello game, int val) {
        super(game, val);
    }

    /**
     * Returns the move the computer player wants to move.
     *
     * @return the move coordinates as row * 10 + col in the 0 indexed 2D board, or -1 if no move is found
     */
    @Override
    public int move() {
        if (!game.isPlaceAvail(getVal())) {
            return -1;
        }
        int num = 0;
        int bestRow = -1;
        int bestCol = -1;
        int initialS = scoreRatio();
        int posS = positionScore();
        int max = -10;
        for (int i = 0; i < game.size(); i++) {
            for (int j = 0; j < game.size(); j++) {
                boolean canBePlaced = game.place(i, j, getVal());
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
        return bestRow * 10 + bestCol;
    }



    /**
     * Calculates the position score for a particular place on the board. It calculates position score based on where a certain
     * point is loacted on the board, and that is determined using some strategy.
     * @param row postion row
     * @param col position column
     * @return the score for the position
     */
    protected int position(int row, int col) {
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
     * Calculates the difference of overall board between two different versions of the board. So, essentially helps in
     * seeing how much the score difference is before and after a possible move to see the viability of the move.
     * @param initialScoreRatio the initial score of the board (Tile score)
     * @param initialPositionScore the score based on the psotion of the tiles
     * @return the overall score difference in the board.
     */
    private int scoreAdv(int initialScoreRatio, int initialPositionScore) {
        // 0.5 * tile score difference + position score difference
        return (int)((scoreRatio() - initialScoreRatio) * 0.5) + (positionScore() - initialPositionScore);
    }

}
