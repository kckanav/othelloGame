package main.view;

import javax.swing.*;
import main.model.Disc;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class FrameView implements ActionListener, OthelloView {


    public static final int SIZE = 8;

    private JFrame f;
    private JLabel infoPanel;
    private JLabel score;
    private Disc[][] myButtons;
    private Disc lastDiscClick;
    private int lastMove;

    public FrameView() {
        createFrame();
        lastDiscClick = null;

        // bring it on...
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.toFront();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        while (lastDiscClick != null) {
            try {
                wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        lastDiscClick = (Disc) e.getSource();
        notifyAll();
    }

    /**
     * Updates the score of the game in the game view
     *
     * @param scores
     */
    @Override
    public void updateScore(int[] scores) {
        score.setText("Black: " + scores[0] + " | White: " + scores[1]);
    }

    /**
     * Updates the game board in the user game view
     *
     * @param board the current low level representation of the board to copy in the frame view
     */
    @Override
    public void updateBoard(int[][] board) {
        myButtons[lastMove / 10][lastMove % 10].setBorder(null);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                Disc curr = myButtons[i][j];
                curr.occupy(board[i][j]);
                if (curr.isOccupied()) {
                    curr.removeActionListener(this);
                }
            }
        }
    }

    /**
     * Presents an introduction of the game to the user, and determines if the game is single player game,
     * or multiplayer
     *
     * @return true iff the game is single player, false if the game is multiplayer. In single player, the other player
     * is the computer.
     */
    @Override
    public boolean getIntro() {
        Intro intro = new Intro();
        return intro.getGameType();
    }

    /**
     * Gets the next move the specified player wants to play in the game
     *
     * @param player The player whose next move is needed as its integer representation
     * @return the next move spot as "ColumnRow". An example is "B3" for
     * column 2 and row 3 on the board.
     */
    @Override
    public synchronized int getNextMove(int player) {
        while (lastDiscClick == null) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        int position = lastDiscClick.postion;
        lastDiscClick = null;
        return position;
    }

    /**
     * Displays the message to the user
     *
     * @param message the message to display
     */
    @Override
    public void displayMessage(String message) {
        infoPanel.setText(message);
    }

    /**
     * Displays to the user where the last move was played in the game
     *
     * @param point where the last disc was placed
     */
    @Override
    public void setLastMove(int point) {
        myButtons[lastMove / 10][lastMove % 10].setBorder(null);
        myButtons[point / 10][point % 10].setBorder(BorderFactory.createTitledBorder("Last Move"));
        lastMove = point;
    }

    /**
     * Displays the view after the game has ended, along with statistics of the game
     *
     * @param scores the final scores for the players of the game
     */
    @Override
    public void endGame(int[] scores) {

    }

    private void createFrame() {
        // Creating the frame
        f = new JFrame();
        f.setSize(90 * SIZE + 50, 90 * SIZE + 80);
        f.setTitle("Othello");
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        Container contentPane = f.getContentPane();


        // Adding infoPanel at the top
        JPanel p = new JPanel(new GridLayout(1,2,10,10));
        Dimension d = new Dimension(100 * SIZE + 50, (int)(f.getHeight() * 0.1));
        p.setPreferredSize(d);

        Font fh = new Font("Georgia", Font.PLAIN, 20);
        infoPanel = new JLabel("Play!", JLabel.CENTER);
        infoPanel.setFont(fh);

        // Score
        // TODO: Proper comments here.

        JPanel sa = new JPanel(new FlowLayout());
        JLabel scoreHead = new JLabel("Score", JLabel.CENTER);
        score = new JLabel("", JLabel.CENTER);
        d.setSize(d.getWidth() / 2, d.getHeight());
        scoreHead.setPreferredSize(new Dimension((int)d.getWidth() / 2, (int)d.getHeight() / 3));
        score.setPreferredSize(new Dimension((int)d.getWidth() / 2, (int)d.getHeight() / 3));
        sa.setPreferredSize(d);
        scoreHead.setFont(fh);
        score.setFont(fh);
        sa.add(scoreHead);
        sa.add(score);
        p.setBackground(new Color(82, 95, 12));
        infoPanel.setBackground(null);
        p.setBorder(BorderFactory.createLineBorder(new Color(82, 95, 12), 5));
        p.add(sa);
        p.add(infoPanel);// hardCoded sizing

        contentPane.add(p, "North");

        // add buttons in the middle for the chess squares
        p = new JPanel(new GridLayout(SIZE, SIZE, 4, 4));
        p.setBorder(BorderFactory.createLineBorder(new Color(29, 59, 16), 5));
        contentPane.add(p, "Center");
        p.setBackground(new Color(29, 59, 16));

        myButtons = new Disc[SIZE][SIZE];
        Font f24 = new Font("Serif", Font.BOLD, 24);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Disc b = new Disc(false, Disc.EMPTY_REP, i * 10 + j);
                b.setBackground(new Color(67, 133, 32));
                b.addActionListener(this);
                p.add(b);
                myButtons[i][j] = b;
            }
        }
    }
}
