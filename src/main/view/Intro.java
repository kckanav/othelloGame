package main.view;

import main.model.Othello;
import main.controller.OthelloController;
import main.view.FrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Intro implements ActionListener {

    private JFrame frame;
    private JButton singlePlayer;
    private JButton multiPlayer;
    private JButton gameRules;
    private boolean done;
    private Boolean isSinglePlayer;


    public Intro() {
        createFrame();
        isSinglePlayer = null;
        // Starting it
        frame.setLocationRelativeTo(null); // Centering it
        frame.toFront();
        frame.setVisible(true);
    }

    public synchronized boolean getGameType() {
        while (isSinglePlayer == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isSinglePlayer;
    }

    public synchronized void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayer) {
            frame.dispose();
            isSinglePlayer = true;
            notifyAll();
        } else if (e.getSource() == multiPlayer) {
            isSinglePlayer = false;
            frame.dispose();
            notifyAll();
        } else {

        }
    }

    private void createFrame() {
        frame = new JFrame();
        // frame settings
        int size = FrameView.SIZE;
        frame.setSize(40 * size, 24 * size);
        frame.setTitle("Game Settings");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        // Heading settings
        JPanel panel = new JPanel();
        contentPane.add(panel, "Center"); // position
        JLabel head = new JLabel("Welcome to Othello", JLabel.CENTER); // text
        head.setPreferredSize(new Dimension(40*size, 3 * size)); // size
        JLabel ques = new JLabel("What type of game do you want to play?", JLabel.CENTER);
        ques.setPreferredSize(new Dimension(40 * size, 4 * size));
        head.setFont(new Font("Aerial", Font.BOLD, 20));
        ques.setFont(new Font("Aerial", Font.PLAIN, 15));

        // Adding them
        panel.add(head);
        panel.add(ques);

        addButtons(panel);
    }

    private void addButtons(JPanel panel) {
        // Adding the player options
        singlePlayer = new JButton("Single-Player game");
        multiPlayer = new JButton("Multi-Player game");
        gameRules = new JButton("How to Play?");
        singlePlayer.addActionListener(this);
        multiPlayer.addActionListener(this);
        gameRules.addActionListener(this);
        panel.add(singlePlayer);
        panel.add(multiPlayer);
        panel.add(gameRules);
    }

}
