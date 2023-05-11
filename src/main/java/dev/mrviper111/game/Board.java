package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;

import java.util.Arrays;

public class Board {

    private Difficulty difficulty;
    private String[][] board;
    private int boardSize;
    private int shipCount;
    private String[][] boardData;

    private String[] rows;
    private String[] columns;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;

        this.boardSize = difficulty.getBoardSize();
        this.shipCount = this.boardSize / 2;
        this.board = new String[this.boardSize][this.boardSize];

        this.rows = new String[this.boardSize];
        this.columns = new String[this.boardSize];

        for (String[] array : this.board) {
            Arrays.fill(array, "-");
        }

        this.boardData = board.clone();

        for (int i = 0; i < this.boardSize; i++) {
            this.rows[i] = GameManager.MAX_ROWS[i];
            this.columns[i] = GameManager.MAX_COLUMNS[i];
        }

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

    public void placeShip(ShipType ship, Location location, Direction direction) {
        int row = location.getRow();
        int column = location.getColumn();

        switch (direction) {
            
            case UP: {
                for (int i = row; i < row - ship.getSize(); i--) {
                    this.board[i][column] = "1";
                    //this.boardData[i][column] = ship.getId();
                }
            }
            case DOWN: {
                for (int i = row; i < row + ship.getSize(); i++) {
                    this.board[i][column] = "1";
                    //this.boardData[i][column] = ship.getId();
                }
            }
            case LEFT: {
                // TODO: compute index not in loop
                for (int i = 0; i < ship.getSize(); i--) {
                    this.board[row][i] = "1";
                    //this.boardData[row][i] = ship.getId();
                    // 0 3
                }
            }
            case RIGHT: {
                for (int i = column; i < column + ship.getSize(); i++) {
                    this.board[row][i] = "1";
                    //this.boardData[row][i] = ship.getId();
                }
            }

        }

    }

    public void printBoard() {
        System.out.print("    ");
        for (int i = 0; i < this.boardSize; i++) {
            System.out.print(" [" + this.columns[i] + "] ");
        }
        System.out.println();

        for (int i = 0; i < this.boardSize; i++) {

            if (Integer.parseInt(this.rows[i]) < 10) {
                System.out.print(" ");
            }
            System.out.print("[" + this.rows[i] + "]  ");

            for (int j = 0; j < this.boardSize; j++) {
                System.out.print(this.board[i][j] + "    ");
            }

            System.out.println("\n");
        }
    }

}
