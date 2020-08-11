// A white color player.

public class ComPlayer extends Othello {

    private int[][] board;

    public ComPlayer() {
        // board = super.board;
    }

    public boolean move() {
        boolean b = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[1].length; j++) {
                b = safe(i, j, 2, false);
            }
        }
        return b;
    }
}
