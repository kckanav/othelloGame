package main.controller;

import main.model.ComPlayer;
import main.model.Othello;
import main.view.OthelloView;

import java.util.Random;

/**
 * This class creates a simulation of the game of othello using two computer players
 */
public class OthelloSimulationController extends OthelloController {

    private ComPlayer player1;
    private ComPlayer player2;

    /**
     * Creates a new controller for the game of othello with the given game and view
     *
     * @param game the game to play
     * @param view the view of the game
     */
    public OthelloSimulationController(Othello game, OthelloView view) {
        super(game, view);
        player1 = new ComPlayer(game, BLACK_PLAYER_REP);
        player2 = new ComPlayer(game, WHITE_PLAYER_REP);
    }

    /**
     * Starts the simulation of the game using two computer players.
     */
    @Override
    public void playGame() {
        view.updateBoard(game.getBoard());
        getRandomStart();
        while (!game.isOver()) {
            ComPlayer player = blackPlayerTurn ? player1 : player2;
            int val = blackPlayerTurn ? BLACK_PLAYER_REP: WHITE_PLAYER_REP;
            int move = player.move();
            System.out.println(move);
            if (move != -1) {
                game.place(move / 10, move % 10, val);
                view.updateBoard(game.getBoard());
                view.setLastMove(move);
                view.updateScore(game.getScores());
            }
            blackPlayerTurn = !blackPlayerTurn;
        }
        view.endGame(game.getScores());
    }

    /**
     * Provides a random starting point for the game.
     */
    private void getRandomStart() {
        int[] starting = new int[]{53, 24, 35, 42};
        Random r = new Random();
        int index = r.nextInt(4);
        game.place(starting[index] / 10, starting[index] % 10, BLACK_PLAYER_REP);
        blackPlayerTurn = !blackPlayerTurn;
    }
}
