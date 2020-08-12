// A white color player.

import java.util.Stack;

public class ComPlayer {

    private int[][] board;
    private Othello game;
    private Othello perm;
    private int size;
    private int val;

    public ComPlayer(Othello game, int val) {
        board = game.board;
        perm = game;
        size = game.getBoardSize();
        this.val = val;
    }
    public int getVal(){return val; }

    private int positionScore() {
        int whiteScore = 0;
        int blackScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    int a = board[i][j];
                    if (a == 1) {
                        blackScore += position(i, j);
                    } else {
                        whiteScore += position(i, j);
                    }
                }
            }
        }
        if (val == 1) {
            return blackScore - whiteScore;
        } else {
            return whiteScore - blackScore;
        }
    }
    private int position(int row, int col) {
        int sum = 0;
        if ((row == size / 2 - 1 || row == size / 2) && (col == size / 2 - 1|| col == size / 2)) { // Inner center
            return 3;
        } else if ((row >= size / 4 && row < size - size / 4 ) && (row >= size / 4 && row < size - size / 4 )) { // Outer center
            return 1;
        } else if((row == size - 1 || row == 0) && (col == size - 1 || col == 0)) { // corners
            return 5;
        } else if ((row == size - 1 || row == 0) || (col == size - 1 || col == 0)) { // border
            return 3;
        } else {
            return 0;
        }
    }

    private int scoreRatio() {
        if (val == 2) {
            return game.white - game.black;
        } else {
            return game.black - game.white;
        }
    }

    private int scoreAdv(int initialScoreRatio, int initialPositionScore) {
        return (int)((scoreRatio() - initialScoreRatio) * 0.5) + (positionScore() - initialPositionScore);
    }

    public int move() {
        game = perm.clone();
        Stack<Integer> s = new Stack<>();
        int row = -1;
        int col = -1;
        int initialS = scoreRatio();
        int posS = positionScore();
        int max = -10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int prevS = scoreRatio();
                boolean spot = game.place(i, j, val);
                if (spot) {
                    int diff = scoreAdv(initialS, posS);
                    if (diff > max) {
                        max = diff;
                        row = i;
                        col = j;
                    }
                    game.print();
                    game.remove(i, j);
                    game.print();
                    initialS = scoreRatio();
                    posS = positionScore();
                }
            }
        }
        return row * 10 + col;
    }

//    private Stack<Integer> findPlaces(Stack<Integer> s, int val, int score, int layer) {
//        if (layer == 2) {
//            s
//        }
//    }
}
