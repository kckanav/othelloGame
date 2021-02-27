import java.util.Scanner;

public class OthelloMain {

    private static Othello game;
    private static boolean isBlack;

    public static void main(String[] args) {
        intro();
        game = new Othello();
//        String[] moves = {"D4", "E5"};
//        String[] moves1 = {"E4", "D5"};
//        game.place(moves, 1);
//        game.place(moves1, 2);
        Scanner s = new Scanner(System.in);
        game.print();
        String str = s.next();
        isBlack = true;
        while (!gameEnd(str)) {
            playGame(s, str);
            str = s.next();
        }        // printResults();
    }

    public static void intro() {
        System.out.println("Welcome to the game of Othello");
        System.out.println("This game will be played turn wise.");
        System.out.println("You can quit anytime you like by entering 'Q'");
        System.out.println("Player 1's turn");
    }

    public static boolean gameEnd(String s) {
        s = s.toLowerCase();
        if (s.startsWith("q")) {
            return true;
        }
        if (isBlack) {
            return !game.isPLaceAvail(1);
        } else {
            return !game.isPLaceAvail(2);
        }
    }

    public static void playGame(Scanner s, String str) {
        while (!game.isLegal(str) || !game.isEmpty(str)) {
            if (!game.isLegal(str)) { System.out.println("Place not in board!"); }
            else if (!game.isEmpty(str)) { System.out.println("Place already occupied!"); }
            System.out.println("Please enter valid place");
            str = s.next();
        }
        if (isBlack) {
            if (game.isPLaceAvail(1)) {
                while (!game.isLegal(str) || !game.isEmpty(str) || !game.place(str, 1)) {
                    System.out.println("Not a valid move");
                    System.out.println("Please enter a valid place");
                    str = s.next();
                }
            }
            isBlack = false;
            game.print();
            game.remove(game.position(str)[0], game.position(str)[1]);
            System.out.println("Player 2's turn");
             game.move();
             isBlack = true;
            game.print();
//        } else {
//            // game.move();
//            if (game.isPLaceAvail(2)) {
//                while (!game.isLegal(str) || !game.isEmpty(str) || !game.place(str, 2)) {
//                    System.out.println("Not a valid move");
//                    System.out.println("Please enter a valid place");
//                    str = s.next();
//                }
//            }
//            isBlack = true;
//            game.print();
//            System.out.println("Player 1's turn");
        }
    }
}


