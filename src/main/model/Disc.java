package main.model;

import javax.swing.*;
import java.awt.*;

public class Disc extends JButton {

    public static final int BLACK_PLAYER_REP = 1;
    public static final int WHITE_PLAYER_REP = 2;
    public static final int EMPTY_REP = 0;

    private boolean isOccupied;
    private int playerVal;

    /**
     * The position of the disc on the 0 indexed game board as row * 10 + col
     */
    public final int postion;

    public Disc(boolean occupied, int val, int position) {
        super();
        if (occupied) {
            occupy(val);
        } else {
            unoccupy();
        }
        this.postion = position;
    }

    /**
     * Occupies the disc for the player specified.
     * @param val the val of the player
     */
    public void occupy(int val) {
        if (val == BLACK_PLAYER_REP) {
            isOccupied = true;
            playerVal = val;
            this.setBackground(Color.BLACK);
        } else if (val == WHITE_PLAYER_REP) {
            isOccupied = true;
            playerVal = val;
            this.setBackground(Color.WHITE);
        } else if (val == EMPTY_REP) {
            unoccupy();
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }

    /**
     * Empties the disc content and sets the value to 0
     */
    public void unoccupy() {
        isOccupied = false;
        playerVal = EMPTY_REP;
        this.setBackground(new Color(67, 133, 32));
    }

    /**
     * returns the player val on the disc
     * @return 1 for black, 2 for white, 0 if unoccupied
     */
    public int getPlayerVal() {
        return playerVal;
    }

    /**
     * Checks if the disc is occupied
     * @return true if occupied by a player, false otherwise
     */
    public boolean isOccupied() {
        return isOccupied;
    }
}
