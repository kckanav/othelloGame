package test;/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import main.model.computerPlayers.AttackingComPlayer;
import main.model.Othello;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class GameTestDriver {

    // *********************************
    // ***  Interactive Test Driver  ***
    // *********************************

    public static void main(String[] args) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            GameTestDriver td;

            if (args.length == 0) {
                td = new GameTestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
                System.out.println("Running in interactive mode.");
                System.out.println("Type a line in the script testing language to see the output.");
            } else {
                String fileName = args[0];
                File tests = new File(fileName);

                System.out.println("Reading from the provided file.");
                System.out.println("Writing the output from running those tests to standard out.");
                if (tests.exists() || tests.canRead()) {
                    td = new GameTestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("  Run the gradle 'build' task");
        System.err.println("  Open a terminal at hw-graph/build/classes/java/test");
        System.err.println("  To read from a file: java graph.scriptTestRunner.GraphTestDriver <name of input script>");
        System.err.println("  To read from standard in (interactive): java graph.scriptTestRunner.GraphTestDriver");
    }

    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Othello> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;
    private AttackingComPlayer attackingComPlayer1;
    private AttackingComPlayer attackingComPlayer2;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new GraphTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public GameTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests()
            throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch (command) {
                case "game":
                    createGame(arguments);
                    break;
                case "move":
                    playerMove(arguments);
                    break;
                case "com":
                    computerPlayer(arguments);
                    break;
                case "remove":
                    undoLastMove(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGame(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String gameName = arguments.get(0);
        createGame(gameName);
    }

    private void createGame(String graphName) {
        Othello game = new Othello();
        attackingComPlayer1 = new AttackingComPlayer(game, 1);
        attackingComPlayer2 = new AttackingComPlayer(game, 2);
        graphs.put(graphName, game);
        game.print();
        output.println("created game " + graphName);
    }

    private void playerMove(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String gameName = arguments.get(0);
        int playerVal = Integer.parseInt(arguments.get(1));
        String move = arguments.get(2);

        playerMove(gameName, playerVal, move);
    }

    private void playerMove(String gameName, int playerVal, String point) {
        Othello game = graphs.get(gameName);
        if (game.place(position(point)[0], position(point)[1], playerVal)) {
            output.println("Placed a " + playerVal + " tile at " + point + " in " + gameName);
            game.print();
        } else {
            output.println("Could not place point");
        }
    }

    private void computerPlayer(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String gameName = arguments.get(0);
        int playerVal = Integer.parseInt(arguments.get(1));


        computerPlayer(gameName, playerVal);
    }

    private void computerPlayer(String name, int val) {
        Othello game = graphs.get(name);
        AttackingComPlayer player = val == 1 ? attackingComPlayer1 : attackingComPlayer2;
        int point = player.move();
        if (point != -1) {
            game.place(point / 10, point % 10, val);
            output.println("Placed a " + val + " tile at " + point + " in " + name);
            game.print();
        } else {
            output.println("There is no possible move to play for " + val);
        }
    }

    private void undoLastMove(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String gameName = arguments.get(0);
        String pos = arguments.get(1);
        undoLastMove(gameName, pos);
    }

    private void undoLastMove(String gameName, String pos) {
        Othello game = graphs.get(gameName);
        game.remove(position(pos)[0], position(pos)[1]);
        game.print();
    }

    private static int[] position(String pos) {
        int col = pos.charAt(0) - 65;
        int row = pos.charAt(1) - 49;
        return new int[]{row, col};
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
