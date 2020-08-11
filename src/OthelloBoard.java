import Elements.Disc;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.HashMap;
import java.util.Stack;

public class OthelloBoard extends Othello implements ActionListener {

    private int size = 8;
    private Disc[][] myButtons;
    private JFrame f;
    private JLabel infoPanel;
    private boolean isBlack = true;
    private JLabel score;
    private HashMap<Disc, Integer> map;

    // TODO: Game results code.
    public OthelloBoard() {
        super();
        Intro intro = new Intro(this);

        // Creating the frame
        f = new JFrame();
        f.setSize(90 * size + 50, 90 * size + 80);
        f.setTitle("Othello");
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        Container contentPane = f.getContentPane();

        map = new HashMap<>();

        // Adding infoPanel at the top
        JPanel p = new JPanel(new GridLayout(1,2,10,10));
        Dimension d = new Dimension(100 * size + 50, (int)(f.getHeight() * 0.1));
        p.setPreferredSize(d);

        Font fh = new Font("Georgia", Font.PLAIN, 20);
        infoPanel = new JLabel("Play!", JLabel.CENTER);
        infoPanel.setFont(fh);

        // Score
        // TODO: Proper comments here.

        JPanel sa = new JPanel(new FlowLayout());
        JLabel scoreHead = new JLabel("Score", JLabel.CENTER);
        score = new JLabel("Black:  " + black + "     " + "White:  " + white, JLabel.CENTER);
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
        p = new JPanel(new GridLayout(size, size, 4, 4));
        p.setBorder(BorderFactory.createLineBorder(new Color(29, 59, 16), 5));
        contentPane.add(p, "Center");
        p.setBackground(new Color(29, 59, 16));

        myButtons = new Disc[size][size];
        Font f24 = new Font("Serif", Font.BOLD, 24);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Disc b = new Disc(false, null);
                b.setBackground(new Color(67, 133, 32));
                map.put(b, i * 10 + j);
                b.addActionListener(this);
                p.add(b);
                myButtons[i][j] = b;
            }
        }
        String[] moves = {"D4", "E5"};
        String[] moves1 = {"E4", "D5"};
        place(moves, 1);
        place(moves1, 2);
        // bring it on...
        f.setLocationRelativeTo(null);
        // f.setVisible(true);
        // f.toFront();
        intro.frame.toFront();
    }

    private void updateScore() {
        score.setText("Black:  " + black + "     " + "White:  " + white);
    }

    public JFrame frame() {return f;}

    public int getSize() {return size;}

    public void isSinglePlayer(boolean b) {isSinglePLayer = b;}

    public void place(String[] pos, int val) {
        super.place(pos, val);
        for (String s: pos) {
            int[] posi = super.position(s);
            if (val == 1) {
                myButtons[posi[0]][posi[1]].setBackground(Color.BLACK);
            } else {
                myButtons[posi[0]][posi[1]].setBackground(Color.WHITE);
            }
        }
    }

    public boolean place(String pos, int val) {
        if (super.place(pos, val)) {
            int[] posi = super.position(pos);
            if (val == 1) {
                myButtons[posi[0]][posi[1]].setBackground(Color.BLACK);
            } else {
                myButtons[posi[0]][posi[1]].setBackground(Color.WHITE);
            }
            return true;
        }
        return false;
    }

    // TODO: Introduce a change method so that you don't have to clone Stack.
    // TODO: The same method in both classes.
//    protected void flipDiscs(@NotNull Stack<Integer> s, int val) {
//        Stack<Integer> stack = (Stack<Integer>) s.clone();
//        super.flipDiscs(s, val);
//        while(!stack.isEmpty()) {
//            int pos = stack.pop();
//
//        }
//    }

    public void change(int row, int col, int val) {
        super.change(row, col, val);
        if (val == 1) {
            myButtons[row][col].setBackground(Color.BLACK);
        } else {
            myButtons[row][col].setBackground(Color.WHITE);
        }
    }

    private void pause() {
        pause(500);
    }

    private void pause(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = map.get(e.getSource());
        if (isSinglePLayer != null) {
            if (isSinglePLayer) {
                playSinglePlayer(pos / 10, pos % 10);
            } else {
                playMove(pos / 10, pos % 10);
            }
        }
    }

    protected void playSinglePlayer(int row, int col) {
        if (isBlack) {
            if(place(reversePosition(row, col),1)) {
                infoPanel.setText("Your Turn");
                print();
                updateScore();
                isBlack = false;
                move();
                print();
                isBlack = true;
                updateScore();
            } else {
                infoPanel.setText("Invalid move");
            }
        }
    }

    protected void playMove(int row, int col) {
        if (isBlack) {
            if(place(reversePosition(row, col),1)) {
                infoPanel.setText("Now the white player moves");
                print();
                updateScore();
                isBlack = false;
            } else {
                infoPanel.setText("Invalid move");
            }
        } else {
            if(place(reversePosition(row, col), 2)) {
                infoPanel.setText("Now the Black player moves");
                print();
                updateScore();
                isBlack = true;
            } else {
                infoPanel.setText("Invalid move");
            }
        }
    }
}
