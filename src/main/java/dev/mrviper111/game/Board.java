package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private String[][] board;
    private String[][] enemyDisplayBoard;
//    public ArrayList<String[][]> boardHistory;

    private final Difficulty difficulty;
    private final int boardSize;
    private int shipCount;

    private final String[] rows;
    private final String[] columns;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;

        this.boardSize = difficulty.getBoardSize();
        this.shipCount = this.boardSize / 2;

        this.board = new String[this.boardSize][this.boardSize];
        this.enemyDisplayBoard = new String[this.boardSize][this.boardSize];
//        this.boardHistory = new ArrayList<>();

        this.rows = new String[this.boardSize];
        this.columns = new String[this.boardSize];

        for (String[] array : this.board) {
            Arrays.fill(array, "-");
        }

        for (String[] array : this.enemyDisplayBoard) {
            Arrays.fill(array, "-");
        }

        for (int i = 0; i < this.boardSize; i++) {
            this.rows[i] = GameManager.MAX_ROWS[i];
            this.columns[i] = GameManager.MAX_COLUMNS[i];
        }

//        this.boardHistory.add(this.board);
//
//        System.out.println("\n\n\nHISTORY");
//        for (String[][] board : this.boardHistory) {
//            for (String[] b : board) {
//                System.out.println(Arrays.toString(b));
//            }
//            System.out.println("\n");
//        }
//        System.out.println("\n\n\n");

    }

    public boolean attack(Location location) {
        int row = location.getRow();
        int column = location.getColumn();

        if (this.board[row][column].equals("1")) {
            this.board[row][column] = "X";
            this.enemyDisplayBoard[row][column] = "X";
            return true;

        } else if (this.enemyDisplayBoard[row][column].equals("X")) {
            return false;

        } else {
            this.board[row][column] = "0";
            this.enemyDisplayBoard[row][column] = "0";
            return false;
        }

    }

    private void placeShip(ShipType ship, Location location, Direction direction) {
        for (int i = 0; i < ship.getSize(); i++) {
            this.board[location.getRow() + (direction.getRowOffset() * i)][location.getColumn() + (direction.getColumnOffset() * i)] = "1";
        }
    }

    public boolean tryPlaceShip(ShipType ship, Direction direction, Location location) {
        if (!validateShipLocation(ship, direction, location)) {
            return false;
        }

        this.placeShip(ship, location, direction);
        return true;
    }

    public boolean validateShipLocation(ShipType ship, Direction direction, Location location) {
        for (int i = 0; i < ship.getSize(); i++) {
            if (this.board[location.getRow() + (direction.getRowOffset() * i)][location.getColumn() + (direction.getColumnOffset() * i)].equals("1")) {
                return false;
            }
        }

        return true;
    }

    public boolean isShipAlive() {
        for (String[] row : this.board) {
            if (Arrays.toString(row).contains("1")) {
                return true;
            }
        }

        return false;
    }

    public void addRandomShip(ShipType ship) {
        int upperBound = this.boardSize - ship.getSize() + 1;

        while (true) {
            Direction direction = new Direction[]{Direction.RIGHT, Direction.DOWN}[ThreadLocalRandom.current().nextInt(0, 2)];
            int column = ThreadLocalRandom.current().nextInt(0, upperBound);
            int row = ThreadLocalRandom.current().nextInt(0, upperBound);

            Location location = new Location(row, column);

            if (!validateShipLocation(ship, direction, location)) {
                continue;
            }

            this.placeShip(ship, location, direction);
            return;
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

    public void printEnemyDisplayBoard() {
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
                System.out.print(this.enemyDisplayBoard[i][j] + "    ");
            }

            System.out.println("\n");
        }
    }

}
