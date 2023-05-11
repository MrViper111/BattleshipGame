package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;
import dev.mrviper111.utils.CLIHandler;

import java.util.*;

public class Game {

    private final Map<ShipType, Integer> availableShips;
    private final Difficulty difficulty;
    private Board playerBoard;
    private Board botBoard;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.availableShips = GameManager.parseShipDuplicates(this.difficulty.getAllocatedShips());
    }

    public void init() {

        System.out.println("----------------------------------------------------------");
        System.out.println("Welcome to the Battleship game!");
        System.out.println("instructions");
        System.out.println("----------------------------------------------------------");

        this.playerBoard = new Board(this.difficulty);
        this.botBoard = new Board(this.difficulty);

        playerBoard.printBoard();
        System.out.println("This is your current board, it looks pretty empty so we're going to have to place some ships.");
        System.out.println("Since you wanted to play on the difficulty " + this.difficulty.getName() + ", you can have " + this.difficulty.getBoardSize() / 2 + " ships.\n");

        for (int i = 0; i < this.difficulty.getBoardSize() / 2; i++) {
            ShipType shipType;
            Location location;
            Direction direction;

            while (true) {

                System.out.println("Available ships: ");
                int number = 0;

                for (ShipType type : availableShips.keySet()) {
                    int shipAmount = availableShips.get(type);
                    number++;
                    System.out.println(" " + number + ". (" + shipAmount + "x) - " + type.getName());
                }

                while (true) {
                    int shipSelection = CLIHandler.promptInt("Which ship would you like to place (1-3): ");

                    if (shipSelection != -1 && shipSelection > availableShips.size() || shipSelection < 1) {
                        System.out.println("[Error] Please enter a valid selection (1-3).");
                        continue;
                    }

                    shipType = (ShipType) this.availableShips.keySet().toArray()[shipSelection - 1];

                    if (this.availableShips.get(shipType) < 1) {
                        this.availableShips.remove(shipType);
                        continue;
                    }

                    int shipsLeft = availableShips.get(shipType) - 1;
                    availableShips.replace(shipType, shipsLeft);

                    if (shipsLeft < 1) {
                        availableShips.remove(shipType);
                    }

                    break;
                }

                while (true) {
                    String locationStr = CLIHandler.promptString("Where would you like to place the ship (ex: B3): ");

                    try {
                        location = GameManager.parseLocation(locationStr);
                    } catch (Exception error) {
                        System.out.println("[Error] Invalid location. Please enter [A-" + GameManager.MAX_COLUMNS[this.difficulty.getBoardSize() - 1] + "][1-" + GameManager.MAX_ROWS[this.difficulty.getBoardSize() - 1] + "].");
                        continue;
                    }

                    break;
                }

                System.out.println("Directions:");
                System.out.println("  1. Up");
                System.out.println("  2. Down");
                System.out.println("  3. Left");
                System.out.println("  4. Right");

                label:
                while (true) {
                    String directionStr = CLIHandler.promptString("In which direction would you like to position it: ");

                    switch (directionStr.toLowerCase()) {
                        case "1" -> {
                            direction = Direction.UP;
                            break label;
                        }
                        case "2" -> {
                            direction = Direction.DOWN;
                            break label;
                        }
                        case "3" -> {
                            direction = Direction.LEFT;
                            break label;
                        }
                        case "4" -> {
                            direction = Direction.RIGHT;
                            break label;
                        }
                        default -> {
                            System.out.println("[Error] Invalid input (1-4).");
                            continue;
                        }
                    }

                }

                try {
                    this.playerBoard.placeShip(shipType, location, Direction.RIGHT);
                } catch (ArrayIndexOutOfBoundsException error) {
                    System.out.println("You failed...");
                    continue;
                }

                break;
            }

            this.playerBoard.printBoard();

        }

    }

}
