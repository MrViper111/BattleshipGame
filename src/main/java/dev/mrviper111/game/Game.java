package dev.mrviper111.game;

import dev.mrviper111.utils.InputHandler;

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

    }

}
