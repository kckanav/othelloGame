package main;

import main.controller.OthelloController;
import main.model.Othello;
import main.view.ConsoleView;
import main.view.OthelloView;

public class OthelloMain {
    public static void main(String[] args) {
        Othello game = new Othello();
        System.out.println(game.isOver());
        OthelloView view = new ConsoleView();
        OthelloController controller = new OthelloController(game, view);
        controller.playGame();
    }
}
//
//import java.util.Scanner;
//
//public class OthelloMain {
//
//    private static Othello game;
//    private static boolean isBlack;
//
//    public static void main(String[] args) {
//        intro();
//        game = new Othello();
////        Scanner s = new Scanner(System.in);
////        game.print();
////        String str = s.next();
////        isBlack = true;
////        while (!gameEnd(str)) {
////            playGame(s, str);
////            str = s.next();
////        }
//        stimulateGame();
//    }
//
//    /**
//     * Prints the introduction of the game.
//     */
//    public static void intro() {
//        System.out.println("Welcome to the game of Othello");
//        System.out.println("This game will be played turn wise.");
//        System.out.println("You can quit anytime you like by entering 'Q'");
//        System.out.println("Player 1's turn");
//    }
//
//
//    public static boolean gameEnd(String s) {
//        s = s.toLowerCase();
//        if (s.startsWith("q")) {
//            return true;
//        }
//        if (isBlack) {
//            return !game.isPLaceAvail(1);
//        } else {
//            return !game.isPLaceAvail(2);
//        }
//    }
//
//    private static void stimulateGame() {
//        game.print();
//        ComPlayer player1 = new ComPlayer(game, 1);
//        ComPlayer player2 = new ComPlayer(game, 2);
//        boolean gameStatus = true;
//        while (gameStatus) {
//            boolean temp = place(player1.move(), 1);
//            game.print();
//            boolean temp2 = place(player2.move(), 2);
//            game.print();
//            gameStatus = temp2 || temp;
//        }
//        System.out.println("Game Over");
//    }
//
//    public static void playGame(Scanner s, String str) {
//        while (!isLegal(str) || !isOccupied(str)) {
//            if (!isLegal(str)) { System.out.println("Place not in board!"); }
//            else if (!isOccupied(str)) { System.out.println("Place already occupied!"); }
//            System.out.println("Please enter valid place");
//            str = s.next();
//        }
//        if (isBlack) {
//            if (game.isPLaceAvail(1)) {
//                while (!isLegal(str) || !isOccupied(str) || !place(str, 1)) {
//                    System.out.println("Not a valid move");
//                    System.out.println("Please enter a valid place");
//                    str = s.next();
//                }
//            }
//            isBlack = false;
//            game.print();
//            game.remove(position(str)[0], position(str)[1]);
//            System.out.println("Player 2's turn");
//            if (!move(2)) {
//                System.out.println("Game over");
//                return;
//            }
//            isBlack = true;
//            game.print();
//        }
//    }
//
//    private static int[] position(String pos) {
//        int col = pos.charAt(0) - 65;
//        int row = pos.charAt(1) - 49;
//        return new int[]{row, col};
//    }
//
//    private String reversePosition(int row, int col) {
//        return "" + ((char) (col + 65)) + ((char)(row + 49));
//    }
//
//    private static boolean place(String str, int val) {
//        return game.place(position(str)[0], position(str)[1], val);
//    }
//
//    private static boolean isLegal(String str) {
//        return game.legal(position(str)[0], position(str)[1]);
//    }
//
//    private static boolean isOccupied(String str) {
//        return game.isNotOccupied(position(str)[0], position(str)[1]);
//    }
//
//    private static boolean place(int point, int val) {
//        if (point == -1) {
//            return false;
//        }
//        return game.place(point / 10, point % 10, val);
//    }
//
//    private static boolean move(int val) {
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (game.place(i, j, val)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//}
//
//
