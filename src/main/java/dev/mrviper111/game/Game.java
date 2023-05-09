package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.utils.CLIHandler;

public class Game {

    private final Difficulty difficulty;
    private final String username;
    private Board playerBoard;
    private Board botBoard;

    public Game(Difficulty difficulty, String username) {
        this.difficulty = difficulty;
        this.username = username;
    }

    public void init() {
        System.out.println("----------------------------------------------------------");
        System.out.println("Welcome to the Battleship game!");
        System.out.println("instructions");
        System.out.println("----------------------------------------------------------");

        this.playerBoard = new Board(this.difficulty);
        this.botBoard = new Board(this.difficulty);

        playerBoard.printBoard();
        System.out.println("This is your current board, it looks pretty empty so we're going to have to place some ships.\n");

        String test = CLIHandler.promptString("test: ");
        Location t = GameManager.parseLocation(test);
        System.out.println(t.getRow() + " " + t.getColumn());

    }

}
