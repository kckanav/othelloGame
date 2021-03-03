package main.view;

public interface OthelloView {

    /**
     * Updates the score of the game in the game view
     */
    public void updateScore(int[] scores);

    /**
     * Updates the game board in the user game view
     */
    public void updateBoard(int[][] board);

    /**
     * Presents an introduction of the game to the user, and determines if the game is single player game,
     * or multiplayer
     * @return true iff the game is single player, false if the game is multiplayer. In single player, the other player
     * is the computer.
     */
    public boolean getIntro();

    /**
     * Gets the next move the specified player wants to play in the game
     * @param player The player whose next move is needed as its integer representation
     * @return the next move spot as "ColumnRow". An example is "B3" for
     * column 2 and row 3 on the board.
     */
    public int getNextMove(int player);

    /**
     * Displays the message to the user
     * @param message the message to display
     */
    public void displayMessage(String message);

    /**
     * Displays to the user where the last move was played in the game
     * @param point where the last disc was placed
     */
    public void setLastMove(int point);

}
