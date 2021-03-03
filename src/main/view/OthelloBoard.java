//package main.view;
//
//import main.model.Disc;
//import main.model.ComPlayer;
//import main.model.Othello;
//import main.controller.OthelloController;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Stack;
//
//public class OthelloBoard extends Othello implements ActionListener {
//
//    private int size = 8;
//    private Disc[][] myButtons;
//    private JFrame f;
//    private JLabel infoPanel;
//    private boolean isBlack = true;
//    private JLabel score;
//    private HashMap<Disc, Integer> map;
//    private ComPlayer player;
//    private int lastMove = -1;
//
//    public static void main(String[] args) {
//        Othello othelloBoard = new OthelloBoard();
//    }
//
//    // TODO: Game results code.
//    public OthelloBoard() {
//        super();
//        Intro intro = new Intro(this);
//        // Creating the frame
//        f = new JFrame();
//        f.setSize(90 * size + 50, 90 * size + 80);
//        f.setTitle("Othello");
//        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
//        Container contentPane = f.getContentPane();
//
//        map = new HashMap<>();
//
//        // Adding infoPanel at the top
//        JPanel p = new JPanel(new GridLayout(1,2,10,10));
//        Dimension d = new Dimension(100 * size + 50, (int)(f.getHeight() * 0.1));
//        p.setPreferredSize(d);
//
//        Font fh = new Font("Georgia", Font.PLAIN, 20);
//        infoPanel = new JLabel("Play!", JLabel.CENTER);
//        infoPanel.setFont(fh);
//
//        // Score
//        // TODO: Proper comments here.
//
//        JPanel sa = new JPanel(new FlowLayout());
//        JLabel scoreHead = new JLabel("Score", JLabel.CENTER);
//        score = new JLabel("Black:  " + black + "     " + "White:  " + white, JLabel.CENTER);
//        d.setSize(d.getWidth() / 2, d.getHeight());
//        scoreHead.setPreferredSize(new Dimension((int)d.getWidth() / 2, (int)d.getHeight() / 3));
//        score.setPreferredSize(new Dimension((int)d.getWidth() / 2, (int)d.getHeight() / 3));
//        sa.setPreferredSize(d);
//        scoreHead.setFont(fh);
//        score.setFont(fh);
//        sa.add(scoreHead);
//        sa.add(score);
//        p.setBackground(new Color(82, 95, 12));
//        infoPanel.setBackground(null);
//        p.setBorder(BorderFactory.createLineBorder(new Color(82, 95, 12), 5));
//        p.add(sa);
//        p.add(infoPanel);// hardCoded sizing
//
//        contentPane.add(p, "North");
//
//        // add buttons in the middle for the chess squares
//        p = new JPanel(new GridLayout(size, size, 4, 4));
//        p.setBorder(BorderFactory.createLineBorder(new Color(29, 59, 16), 5));
//        contentPane.add(p, "Center");
//        p.setBackground(new Color(29, 59, 16));
//
//        myButtons = new Disc[size][size];
//        Font f24 = new Font("Serif", Font.BOLD, 24);
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                Disc b = new Disc(false, null);
//                b.setBackground(new Color(67, 133, 32));
//                map.put(b, i * 10 + j);
//                b.addActionListener(this);
//                p.add(b);
//                myButtons[i][j] = b;
//            }
//        }
//        String[] moves = {"D4", "E5"};
//        String[] moves1 = {"E4", "D5"};
//        place(moves, 1);
//        place(moves1, 2);
//        player = new ComPlayer(this, 2);
//        // bring it on...
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
//        // f.toFront();
//        intro.frame.toFront();
//    }
//
//    private void updateScore() {
//        score.setText("Black:  " + black + "     " + "White:  " + white);
//    }
//
//    public boolean legal(Double row, Double col) {
//
//        return false;
//    }
//
//
//    public JFrame frame() {return f;}
//
//    public int getSize() {return size;}
//
//    public void isSinglePlayer(boolean b) {isSinglePLayer = b;}
//
//    public void place(String[] pos, int val) {
//        super.place(pos, val);
//        for (String s: pos) {
//            int[] posi = super.position(s);
//            if (val == 1) {
//                myButtons[posi[0]][posi[1]].setBackground(Color.BLACK);
//            } else {
//                myButtons[posi[0]][posi[1]].setBackground(Color.WHITE);
//            }
//        }
//    }
//
//    public boolean place(String pos, int val) {
//        int row = position(pos)[0];
//        int col = position(pos)[1];
//        return place(row, col, val);
//    }
//
//    public boolean place(int row, int col,  int val) {
//        if (super.place(row, col, val)) {
//            // int[] posi = super.position(pos);
//            if (val == 1) {
//                myButtons[row][col].setBackground(Color.BLACK);
//            } else {
//                myButtons[row][col].setBackground(Color.WHITE);
//            }
//            return true;
//        }
//        return false;
//    }
//
//
//    public void change(int row, int col, int val) {
//        super.change(row, col, val);
//        if (val == 1) {
//            myButtons[row][col].setBackground(Color.BLACK);
//        } else {
//            myButtons[row][col].setBackground(Color.WHITE);
//        }
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        int pos = map.get(e.getSource());
//        if (isSinglePLayer != null) {
//            if (isSinglePLayer) {
//                playSinglePlayer(pos / 10, pos % 10);
//            } else {
//                playMove(pos / 10, pos % 10);
//            }
//        }
//        if (isGameOver()) {
//            infoPanel.setText("Game Over");
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    myButtons[i][j].removeActionListener(this);
//                }
//            }
//        }
//    }
//
//    public boolean isGameOver() {
//        return !(isPLaceAvail(1) && isPLaceAvail(2));
//    }
//
//    protected void playSinglePlayer(int row, int col) {
//        if (isBlack) {
//            if(place(row, col ,1)) {
//                infoPanel.setText("Your Turn");
//                // print();
//                // remove(row, col);
//                updateScore();
//                isBlack = false;
//                int pos = player.move();
//                if (!place(pos / 10, pos % 10, player.val));
//                updateMove(pos);
//                //  print();
//                isBlack = true;
//                updateScore();
//            } else {
//                infoPanel.setText("Invalid move");
//            }
//        }
//    }
//
//    protected void playMove(int row, int col) {
//        if (isBlack) {
//            if(place(reversePosition(row, col),1)) {
//                infoPanel.setText("Now the white player moves");
//                print();
//                updateScore();
//                isBlack = false;
//            } else {
//                infoPanel.setText("Invalid move");
//            }
//        } else {
//            if(place(reversePosition(row, col), 2)) {
//                infoPanel.setText("Now the Black player moves");
//                print();
//                updateScore();
//                isBlack = true;
//            } else {
//                infoPanel.setText("Invalid move");
//            }
//        }
//    }
//    private void updateMove(int pos) {
//        if (lastMove != -1) {
//            myButtons[lastMove / 10][lastMove % 10].setBorder(null);
//        }
//        myButtons[pos / 10][pos % 10].setBorder(BorderFactory.createTitledBorder("Last Move"));
//        lastMove = pos;
//    }
//}

//package main.view;
//
//        import main.model.Disc;
//        import main.controller.OthelloController;
//
//        import javax.swing.*;
//        import java.awt.*;
//        import java.awt.event.ActionEvent;
//        import java.awt.event.ActionListener;
//        import java.util.HashMap;
//        import java.util.Map;
//
//public class BoardView implements ActionListener {
//
//    public final int SIZE = 8;
//
//    private final JFrame f;
//    private final JLabel infoPanel;
//    private final JLabel score;
//    private final Disc[][] myButtons;
//    private final Map<Disc, Integer> buttonLocation;
//    private OthelloController controller;
//
//    public OthelloView(OthelloController controller) {
//
//        this.controller = controller;
//        // Creating the frame -------------------------
//        f = new JFrame();
//        f.setSize(90 * SIZE + 50, 90 * SIZE + 80);
//        f.setTitle("Othello");
//        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
//        Container contentPane = f.getContentPane();
//        // -----------------------------------------------------
//
//        // creating font -------------------------------------
//        Font font = new Font("Georgia", Font.PLAIN, 20);
//        //---------------------------------------------------
//
//        // Adding infoPanel at the top -----------------------------------------
//        JPanel p = new JPanel(new GridLayout(1,2,10,10));
//        Dimension d = new Dimension(100 * SIZE + 50, (int)(f.getHeight() * 0.1));
//        p.setPreferredSize(d);
//        infoPanel = new JLabel("Play!", JLabel.CENTER);
//        infoPanel.setFont(font);
//        // ---------------------------------------------------------------------
//
//        // Adding score to the infoPanel--------------------------
//        JPanel sa = new JPanel(new FlowLayout());
//        JLabel scoreHead = new JLabel("Score", JLabel.CENTER);
//        score = new JLabel();
//        score.setHorizontalTextPosition(JLabel.CENTER);
//        d.setSize(d.getWidth() / 2, d.getHeight());
//        scoreHead.setPreferredSize(new Dimension((int)d.getWidth() / 2, (int)d.getHeight() / 3));
//        score.setPreferredSize(new Dimension((int)d.getWidth() / 2, (int)d.getHeight() / 3));
//        sa.setPreferredSize(d);
//        scoreHead.setFont(font);
//        score.setFont(font);
//        sa.add(scoreHead);
//        sa.add(score);
//        p.setBackground(new Color(82, 95, 12));
//        infoPanel.setBackground(null);
//        p.setBorder(BorderFactory.createLineBorder(new Color(82, 95, 12), 5));
//        p.add(sa);
//        p.add(infoPanel);// hardCoded sizing
//        contentPane.add(p, "North");
//        // -----------------------------------------------------------------------
//
//        // add buttons in the middle for the chess squares
//        p = new JPanel(new GridLayout(SIZE, SIZE, 4, 4));
//        p.setBorder(BorderFactory.createLineBorder(new Color(29, 59, 16), 5));
//        contentPane.add(p, "Center");
//        p.setBackground(new Color(29, 59, 16));
//
//        myButtons = new Disc[SIZE][SIZE];
//        buttonLocation = new HashMap<>();
//        Font f24 = new Font("Serif", Font.BOLD, 24);
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                Disc b = new Disc(false, 0);
//                b.setBackground(new Color(67, 133, 32));
//                buttonLocation.put(b, i * 10 + j);
//                b.addActionListener(this);
//                p.add(b);
//                myButtons[i][j] = b;
//            }
//        }
//    }
//
//    public void setInfoPanelText(String text) {
//        infoPanel.setText(text);
//    }
//
//    public void setScore(int white, int black) {
//        score.setText("Black:  " + black + "     " + "White:  " + white);
//    }
//
//    public void setBoard(int[][] board) {
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                if (board[i][j] != myButtons[i][j].playerVal) {
//                    myButtons[i][j].occupy(board[i][j]);
//                }
//            }
//        }
//    }
//
//    /**
//     * Invoked when an action occurs.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        controller.placePoint(buttonLocation.get(e.getSource()));
//    }
//
//    public void showBoard() {
//        // bring it on...
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
//        f.toFront();
//    }
//
//    public void updateScore();
//    public void updateBoard();
//    public boolean getIntro();
//}

