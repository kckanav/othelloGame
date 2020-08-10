import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Stack;

public class Othello {
    protected int[][] board;
    protected int size;
    protected int black;
    protected int white;
    protected final Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

    public Othello() {
        board = new int[8][8];
        size = 0;
//        String[] moves = {"D4", "E5"};
//        String[] moves1 = {"E4", "D5"};
//        place(moves, 1);
//        place(moves1, 2);
        black = 0;
        white = 0;
    }

    public void place(@NotNull String[] moves, int val) {
        for (String s: moves) {
            board[position(s)[0]][position(s)[1]] = val;
            size++;
        }
    }

    // Takes in the position to place a move as a string, and the player value
    // Return true if the move was successful, false otherwise.
    public boolean place(@NotNull String pos, int val) {
        if (pos.length() != 2) {
            throw new IllegalArgumentException();
        }
        int col = pos.charAt(0) - 65;
        int row = pos.charAt(1) - 49;
        if (!legal(row, col) || board[row][col] != 0) {
            throw new IllegalArgumentException();
        }
        if (safe(row, col, val, false)) {
            board[row][col] = val;
            if (val == 1) {
                black++;
            } else {
                white++;
            }
            size++;
            return true;
        } else {
            if (runnable != null) {runnable.run(); }
            return false;
        }
    }

    public void remove(String pos) {
        int row = position(pos)[0];
        int col = position(pos)[1];
        if (!legal(row, col)) {
            throw new IllegalArgumentException();
        } else {
            board[row][col] = 0;
        }
    }

    public boolean safe(int row, int col, int val, boolean justCheck) {
        int rowStart = -1;
        int rowEnd = 1;
        int colStart = -1;
        int colEnd = 1;
        if (row == 0) { rowStart = 0; }
        if (col == 0) { colStart = 0; }
        if (row == 7) { rowEnd = 0; }
        if (col == 7) { colEnd = 0; }
        return check(row, col, rowStart, rowEnd, colStart, colEnd, val, justCheck);
    }

    protected boolean check(int row, int col, int rowStart, int rowEnd, int colStart, int colEnd, int val, boolean justCheck) {
        Stack<Integer> stack = new Stack();
        boolean found = false;
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                if (board[row + i][col + j] != 0 && board[row + i][col + j] != val) {
                    boolean temp = pattern(row, col, diff(row, col, row + i, col + j), val, stack, justCheck);
                    if (temp) { found = true; }
                }
                stack.removeAllElements();
            }
        }
        return found;
    }

    protected boolean pattern(int row, int col, int[] patten, int val, Stack<Integer> s, boolean justCheck) {
        if (board[row][col] == val) {
            if (justCheck) {
                return true;
            }
            flipDiscs(s, val);
            return true;
        } else {
            int nRow = row + patten[0];
            int nCol = col + patten[1];
            if (legal(nRow, nCol) && board[nRow][nCol] != 0) {
                s.push(10 * nRow + nCol);
                return pattern(nRow, nCol, patten, val, s, justCheck);
            } else {
                return false;
            }
        }
    }

    protected void flipDiscs(@NotNull Stack<Integer> s, int val) {
        while(!s.isEmpty()) {
            int a = s.pop();
            board[a / 10][a % 10] = val;
        }
    }

    protected int[] diff(int row1, int col1, int row2, int col2) {
        return new int[]{(row2 - row1), (col2 - col1)};
    }

    public int[] scores(){
        return new int[]{black, white};
    }

    public boolean isPLaceAvail(int val) {
        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if (safe(i, j, val, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int[] position(@NotNull String pos) {
        int col = pos.charAt(0) - 65;
        int row = pos.charAt(1) - 49;
        return new int[]{row, col};
    }
    protected String reversePosition(int row, int col) {
        return "" + ((char) (col + 65)) + ((char)(row + 49));
    }

    // Post: Prints out the current game board.
    public void print() {
        System.out.print("    ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(((char) (i + 65)) + "  ");
        }
        System.out.println();
        // Loop through all rows
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + 1 + " | ");
            // Loop through all elements of current row
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }

    // post: returns true iff row and col are legal for this board
    protected boolean legal(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }

    public boolean isLegal(String pos) {
        return legal(position(pos)[0], position(pos)[1]);
    }

    public boolean isEmpty(String pos) {
        return isLegal(pos) && board[position(pos)[0]][position(pos)[1]] == 0;
    }

    public int size() { return size; }
}
