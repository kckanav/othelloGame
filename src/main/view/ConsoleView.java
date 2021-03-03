package main.view;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleView implements OthelloView {


    private PrintStream output = System.out;
    private Scanner console;

    /**
     * Creates a new console view for the game.
     */
    public ConsoleView() {
        console = new Scanner(System.in);
    }

    /**
     * Updates the score of the game in the game view
     *
     * @param scores an array containing the scores in the format [blackScore, whiteScore]
     */
    @Override
    public void updateScore(int[] scores) {
        output.println("Black Score: " + scores[0]);
        output.println("White score: " + scores[1]);
    }

    /**
     * Updates the game board in the user game view
     *
     * @param board
     */
    @Override
    public void updateBoard(int[][] board) {
        print(board);
    }

    /**
     * Presents an introduction of the game to the user, and determines if the game is single player game,
     * or multiplayer
     *
     * @return true iff the game is single player, false if the game is multiplayer. In single player, the other player
     * is the computer.
     */
    @Override
    public boolean getIntro() {
        intro();
        return isSinglePlayer();
    }

    /**
     * Gets the next move the specified player wants to play in the game
     *
     * @param player The player whose next move is needed
     * @return the next move spot as "ColumnRow". An example is "B3" for
     * column 2 and row 3 on the board.
     */
    @Override
    public int getNextMove(int player) {
        String currPlayer = player == 1? "black" : "white";
        output.print("Next move for " + currPlayer + " player: ");
        String move = console.next();
        while (move.length() != 2) {
            output.print("Invalid coordinates! Next move: ");
            move = console.next();
        }
        return position(move);
    }

    /**
     * Displays the message to the user
     *
     * @param message the message to display
     */
    @Override
    public void displayMessage(String message) {
        output.println(message);
    }

    /**
     * Displays to the user where the last move was played in the game
     *
     * @param point where the last disc was placed in the format row * 10 + col
     */
    @Override
    public void setLastMove(int point) {
        String s = reversePosition(point / 10, point % 10);
        output.println("Last move was played at " + s);
    }


    /**
     * Prints the introduction of the game.
     */
    private void intro() {
        output.println("Welcome to the game of Othello");
        output.println("This game will be played turn wise.");
        output.println("You can quit anytime you like by entering 'Q'");
        output.println("Player 1's turn");
    }

    /**
     * Gets the user input to determine how many players are playing the game
     * @return true if the game is single player and a computer player is neeeded, false
     * if there are 2 players
     */
    private boolean isSinglePlayer() {
        output.print("So, how many players are playing the game? (1 if alone/ 2 for multiplayer) ");
        int players = console.nextInt();
        return players == 1;
    }

    /**
     * Prints out the current state of the game.
     */
    private void print(int[][] board) {
        output.print("    ");
        for (int i = 0; i < board.length; i++) {
            output.print(((char) (i + 65)) + "  ");
        }
        output.println();
        // Loop through all rows
        for (int i = 0; i < board.length; i++) {
            output.print(i + 1 + " | ");
            // Loop through all elements of current row
            for (int j = 0; j < board[i].length; j++) {
                output.print(board[i][j] + "  ");
            }
            output.println();
        }
    }

    /**
     * Translates the String position on the board to a numerical value.
     * @param pos the string representing the position in the format ColumnRow.
     *            Example: "E4" for column 5 and row 4
     * @return the position on the board as an Integer in the format row * 10 + col
     *         the position is generated for a 2 dimensional matrix with 0 indexing.
     *         So, "E4" would be row 3 and col 4 and return 34.
     */
    private int position(String pos) {
        int col = pos.charAt(0) - 65;
        int row = pos.charAt(1) - 49;
        return row * 10 + col;
    }

    /**
     * Translates an integer position into a string position
     * @param row the row in the 0 indexed board of the position
     * @param col the col in the 0 indexed board of the position
     * @return the string indicating the postion. Example:- for row 3 and col 4, "E4"
     */
    private String reversePosition(int row, int col) {
        return "" + ((char) (col + 65)) + ((char)(row + 49));
    }
}
