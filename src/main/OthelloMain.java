package main;

import main.controller.*;
import main.model.Othello;
import main.view.*;

public class OthelloMain {

    public static final boolean needFrameView = true;
    public static final boolean needSimulator = false;

    public static void main(String[] args) {
        Othello game = new Othello();
        OthelloView view = needFrameView ? new FrameView(): new ConsoleView();
        OthelloController controller = needSimulator ?
                new OthelloSimulationController(game, view): new OthelloController(game, view);
        controller.playGame();
    }

}
