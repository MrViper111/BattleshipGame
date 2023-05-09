package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;

public class Board {

    private Difficulty difficulty;
    private String[][] board;
    private int boardSize;
    private int shipCount;
    private String[][] boardData;

    private String[] rows = new String[this.boardSize];
    private String[] columns = new String[this.boardSize];

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;

        this.boardSize = this.difficulty.
        this.shipCount = this.boardSize / 2;
        this.board = new String[this.boardSize][this.boardSize];
        this.boardData = board.clone();


//        for (int i = 0; i < this.boardSize; i++) {
//            this.rows[i] = GameManager.MAX_ROWS[i];
//            this.columns[i] = GameManager.MAX_COLUMNS[i];
//        }

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

    public void placeShip(Ship ship, Location location, Direction direction) {
        int row = location.getRow();
        int column = location.getColumn();

        switch (direction) {
            
            case UP: {
                for (int i = row; i < row - ship.getSize(); i--) {
                    this.board[i][column] = "1";
                }
            }
            case DOWN: {
                for (int i = row; i < row + ship.getSize(); i++) {
                    this.board[i][column] = "1";
                }
            }
            case LEFT: {
                for (int i = column; i < column - ship.getSize(); i--) {
                    this.board[row][i] = "1";
                }
            }
            case RIGHT: {
                for (int i = column; i < column + ship.getSize(); i++) {
                    this.board[i][column] = "1";
                }
            }

        }

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
