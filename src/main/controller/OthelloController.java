package main.controller;

import main.model.computerPlayers.AttackingComPlayer;
import main.model.Othello;
import main.model.computerPlayers.ComPlayer;
import main.view.*;

/**
 * This class controls the game of othello and provides a way for the game to interact with the game view.
 */
public class OthelloController {

    protected final Othello game;
    protected final OthelloView view;
    protected boolean blackPlayerTurn;
    private boolean isSinglePlayer;
    private ComPlayer comPlayer;

    /**
     * Creates a new controller for the game of othello with the given game and view
     * @param game the game to play
     * @param view the view of the game
     */
    public OthelloController(Othello game, OthelloView view) {
        this.game = game;
        this.view = view;
        blackPlayerTurn = true;
       comPlayer = new AttackingComPlayer(game, Othello.WHITE_PLAYER_REP);
    }

    /**
     * Controls the game and the view to allow the user to play the game.
     */
    public void playGame() {
        setPlayerStatus();
        view.updateBoard(game.getBoard());
        while (!game.isOver()) {
            int move;
            if (isSinglePlayer && !blackPlayerTurn) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                move = comPlayer.move();
                game.place(move / 10, move % 10, Othello.WHITE_PLAYER_REP);
                view.updateBoard(game.getBoard());
                view.setLastMove(move);
                view.updateScore(game.getScores());
            } else {
                int player = blackPlayerTurn ? Othello.BLACK_PLAYER_REP : Othello.WHITE_PLAYER_REP;
                move = placePoint(player);
            }
            if (!isSinglePlayer) {
                view.setLastMove(move);
            }
            blackPlayerTurn = !blackPlayerTurn;
        }
    }

    /**
     * places a disc in the game for the player by taking input from the view and interacting with the game.
     * @param player the player who is placing the disc
     * @return the point where the disc was placed as row * 10 + col in the zero indexed game board.
     */
    protected int placePoint(int player) {
        String currPlayer = player == Othello.BLACK_PLAYER_REP ? "Black": "White";
        view.displayMessage("Now the " + currPlayer + " places disc");
        int move = view.getNextMove(player);
        while (!game.place(move / 10, move % 10, player)) {
            view.displayMessage("Cannot place the disc there");
            move = view.getNextMove(player);
        }
        view.updateBoard(game.getBoard());
        view.updateScore(game.getScores());
        return move;
    }

    /**
     * Gets the introduction of the game and sets the number of players playing the game.
     */
    private void setPlayerStatus() {
        this.isSinglePlayer = view.getIntro();
    }
}