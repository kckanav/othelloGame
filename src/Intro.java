import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Intro implements ActionListener {

    public JFrame frame;
    private JButton singlePlayer;
    private JButton multiPlayer;
    private JButton gameRules;
    private OthelloBoard game;

    public Intro(OthelloBoard game) {

        this.game = game;
        int size = game.getSize();
        // frame settings
        frame = new JFrame();
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

        // Starting it
        frame.setLocationRelativeTo(null); // Centering it
        frame.toFront();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayer) {
            game.isSinglePLayer = true;
            frame.dispose();
            game.frame().setVisible(true);
            game.frame().toFront();
        } else if (e.getSource() == multiPlayer) {
            game.isSinglePLayer = false;
            frame.dispose();
            game.frame().setVisible(true);
            game.frame().toFront();
        } else {

        }
    }

}
