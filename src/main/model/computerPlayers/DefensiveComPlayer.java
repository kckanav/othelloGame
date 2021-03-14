package main.model.computerPlayers;

import main.model.Othello;

public class DefensiveComPlayer extends ComPlayer {

    public DefensiveComPlayer(Othello game, int val) {
        super(game, val);
    }

    /**
     * Returns the move the computer player wants to move.
     *
     * @return the move coordinates as row * 10 + col in the 0 indexed 2D board, or -1 if no move is found
     */
    @Override
    public int move() {
        return 0;
    }

    /**
     * Calculates the position score for a particular place on the board. It calculates position score based on where a certain
     * point is located on the board, and that is determined using some strategy.
     *
     * @param row position row
     * @param col position column
     * @return the score for the position
     */
    @Override
    protected int position(int row, int col) {
        return 0;
    }
}
