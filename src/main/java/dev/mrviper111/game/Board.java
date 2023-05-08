package dev.mrviper111.game;

import java.util.Arrays;

public class Board {

    private Difficulty difficulty;
    private String[][] board;
    private int boardSize;
    private int shipCount;
    private int[][] boardData;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;

        switch (this.difficulty) {
            case EASY -> {
                this.boardSize = 8;
            }
            case MEDIUM -> {
                this.boardSize = 10;
            }
            case HARD -> {
                this.boardSize = 15;
            }
        }

        this.shipCount = this.boardSize / 2;
        this.board = new String[this.boardSize][this.boardSize];
        this.boardData = new String[this.boardSize][this.boardSize];
    }

    public boolean attack(int row, int column) {
        if (this.board[row][column].equals("1")) {
            this.board[row][column] = "X";
            return true;
        } else {
            this.board[row][column] = "x";
            return false;
        }
    }

    public void placeShips() {
        // ship shit
    }

    public void printBoard() {
        for (int i = 0; i < this.boardSize; i++) {

            for (int j = 0; j < this.boardSize; j++) {
                System.out.print(this.board[i][j] + "    ");
            }

            System.out.println("\n");
        }
    }

}
