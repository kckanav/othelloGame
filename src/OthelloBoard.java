import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class OthelloBoard extends Othello {
    private int size = 8;
    private JButton[][] myButtons;
    private JFrame f;
    private JLabel infoPanel;
    private boolean isBlack = true;

    public OthelloBoard() {
        super();
        f = new JFrame();
        f.setSize(60 * size + 50, 60 * size + 80);
        f.setTitle("Othello");
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        Container contentPane = f.getContentPane();
        HashMap<JButton, Integer> map = new HashMap<>();

        JPanel p = new JPanel();
        infoPanel = new JLabel("Welcome to othello");
        p.add(infoPanel);
        contentPane.add(p, "South");

        // add buttons in the middle for the chess squares
        p = new JPanel(new GridLayout(size, size, 4, 4));
        contentPane.add(p, "Center");
        p.setBackground(Color.black);

        myButtons = new JButton[size][size];
        Font f24 = new Font("Serif", Font.BOLD, 24);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton b = new JButton();
                b.setBackground(Color.GREEN);
                myButtons[i][j] = b;
                map.put(b, i * 10 + j);
                b.setFont(f24);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int pos = map.get(b);
                        if (isBlack) {
                            if(place(reversePosition(pos/10, pos%10),1)) {
                                infoPanel.setText("Now the white player moves");
                                print();
                                isBlack = false;
                            } else {
                                String s = infoPanel.getText();
                                infoPanel.setText("Invalid move");
//                                try {
//                                    Thread.sleep(200);
//                                } catch (Exception ex) {
//                                    throw new InternalError();
//                                }
                                infoPanel.setText(s);
                            }
                        } else {
                            if(place(reversePosition(pos/10, pos%10),2)) {
                                infoPanel.setText("Now the Black player moves");
                                print();
                                isBlack = true;
                            } else {
                                String s = infoPanel.getText();
                                infoPanel.setText("Invalid move");
//                                try {
//                                    Thread.sleep(200);
//                                } catch (Exception ex) {
//                                    throw new InternalError();
//                                }
                                infoPanel.setText(s);
                            }
                        }
                    }
                });
                p.add(b);
                myButtons[i][j] = b;
            }
        }
        // bring it on...
        f.setVisible(true);
        f.toFront();
    }

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

    protected void flipDiscs(@NotNull Stack<Integer> s, int val) {
        Stack<Integer> stack = (Stack<Integer>) s.clone();
        super.flipDiscs(s, val);
        while(!stack.isEmpty()) {
            int pos = stack.pop();
            if (val == 1) {
                myButtons[pos / 10][pos % 10].setBackground(Color.BLACK);
            } else {
                myButtons[pos / 10][pos % 10].setBackground(Color.WHITE);
            }

        }
    }
}
