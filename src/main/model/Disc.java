package main.model;

import javax.swing.*;
import java.awt.*;

public class Disc extends JButton {

    public static final int BLACK_PLAYER_REP = 1;
    public static final int WHITE_PLAYER_REP = 2;
    public static final int EMPTY_REP = 0;

    public boolean isOccupied;
    public int playerVal;

    public Disc(boolean occupied, int val) {
        super();
        if (occupied) {
            occupy(val);
        } else {
            unoccupy();
        }
    }

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

    public void unoccupy() {
        isOccupied = false;
        playerVal = EMPTY_REP;
        this.setBackground(new Color(67, 133, 32));
    }
}
