package Elements;

import javax.swing.*;

public class Disc extends JButton {

    public boolean isOccupied;
    public boolean isBlack;

    public Disc(boolean occupied, Boolean isBlack) {
        super();
        if (isBlack != null & !occupied) {
            this.isBlack = isBlack;
        }
        this.isOccupied = occupied;
    }
}
