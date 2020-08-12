import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Othello {
    protected int[][] board;
    protected int boardSize = 8;
    protected int size;
    protected int black;
    protected int white;
    protected final Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
    protected Boolean isSinglePLayer;
    protected Queue<Integer> lastMove;

    public Othello() {
        board = new int[8][8];
        size = 0;
        black = 0;
        white = 0;
        lastMove = new LinkedList<>();
    }

    public Othello(int[][] board, int black, int white, boolean isSinglePLayer) {
        this.board = board;
        this.black = black;
        this.white = white;
        this.isSinglePLayer = isSinglePLayer;
        lastMove = new LinkedList<>();
    }

    public int getBoardSize() {return boardSize;}
    public void place(@NotNull String[] moves, int val) {
        for (String s: moves) {
            board[position(s)[0]][position(s)[1]] = val;
            if (val == 1) {
                black++;
            } else {
                white++;
            }
            size++;
        }
    }
    public boolean place(String pos, int val) {
        if (pos.length() != 2) {
            throw new IllegalArgumentException();
        }
        int col = pos.charAt(0) - 65;
        int row = pos.charAt(1) - 49;
        return place(row, col, val);
    }
    // Takes in the position to place a move as a string, and the player value
    // Return true if the move was successful, false otherwise.
    public boolean place(int row, int col, int val) {
        if (!legal(row, col) || board[row][col] != 0) {
            return false;
        }
        if (safe(row, col, val, false)) {
            int act = board[row][col];
            board[row][col] = val;
            if (val == 1) {
                if (act == 2) {
                    white--;
                }
                black++;
            } else {
                if (act == 1) {
                    black--;
                }
                white++;
            }
            size++;
            return true;
        } else {
            // if (runnable != null) {runnable.run(); }
            return false;
        }
    }

    public void remove(int row, int col) {
        board[row][col] = 0;
        while(!lastMove.isEmpty()) {
            int a = lastMove.remove();
            reverse(a / 10, a % 10, board[a / 10][a % 10]);
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
        if (!justCheck) {
            lastMove.clear();
        }
        Queue<Integer> q = new LinkedList<>();
        boolean found = false;
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                if (board[row + i][col + j] != 0 && board[row + i][col + j] != val) {
                    boolean temp = pattern(row, col, diff(row, col, row + i, col + j), val, q, justCheck);
                    if (temp) {
                        if (justCheck) {
                            return true;
                        }
                        found = true;
                    }
                    q.clear();
                }
            }
        }
        return found;
    }

    protected boolean pattern(int row, int col, int[] patten, int val, Queue<Integer> s, boolean justCheck) {
        if (board[row][col] == val) {
            if (justCheck) {
                return true;
            }
            lastMove.addAll(s);
            flipDiscs(s, val);
            return true;
        } else {
            int nRow = row + patten[0];
            int nCol = col + patten[1];
            if (legal(nRow, nCol) && board[nRow][nCol] != 0) {
                s.add(10 * nRow + nCol);
                return pattern(nRow, nCol, patten, val, s, justCheck);
            } else {
                return false;
            }
        }
    }

    protected void flipDiscs(@NotNull Queue<Integer> s, int val) {
        while(!s.isEmpty()) {
            int a = s.remove();
            change(a / 10, a % 10, val);
        }
    }

    protected void change(int row, int col, int val) {
        int act = board[row][col];
        if (val == 1 && act != val) {
            black++;
            if (act == 2) {
                white--;
            }
        } else if (val == 2 && act != val) {
            white++;
            if (act == 1) {
                black--;
            }
        }
        board[row][col] = val;
    }

    protected void reverse(int row, int col, int val) {
        // int act = board[row][col];
        if (val == 1) {
            black--;
            white++;
            board[row][col] = 2;
        } else {
            black++;
            white--;
            board[row][col] = 1;
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
        System.out.println(black + "" + white);
    }

    public boolean move() {
        boolean b = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[1].length; j++) {
                b = place(reversePosition(i, j), 2);
                if (b) {
                    return true;
                }
            }
        }
        return b;
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

    public Othello clone() {
        return new Othello(board.clone(), black, white, isSinglePLayer);
    }
}
