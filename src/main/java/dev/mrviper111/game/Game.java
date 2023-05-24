package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;
import dev.mrviper111.utils.CLIHandler;
import dev.mrviper111.utils.MediaPlayer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Game {

    private final Map<ShipType, Integer> availableShips;
    private final Difficulty difficulty;
    private Board playerBoard;
    private Board botBoard;

    private int shotsFired;
    private int hits;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.availableShips = GameManager.parseShipDuplicates(this.difficulty.getAllocatedShips());

        this.shotsFired = 0;
        this.hits = 0;
    }

    public void init() throws InterruptedException {

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
                int shipSelection;
                int shipsLeft;

                System.out.println("Available ships: ");
                int number = 0;

                for (ShipType type : availableShips.keySet()) {
                    int shipAmount = availableShips.get(type);
                    number++;

                    System.out.println(" " + number + ". (" + shipAmount + "x) - " + type.getName());
                }

                while (true) {
                    shipSelection = CLIHandler.promptInt("Which ship would you like to place (1-3): ");

                    if (shipSelection != -1 && shipSelection > availableShips.size() || shipSelection < 1) {
                        System.out.println("[Error] Please enter a valid selection (1-3).");
                        continue;
                    }

                    shipType = (ShipType) this.availableShips.keySet().toArray()[shipSelection - 1];

                    if (this.availableShips.get(shipType) < 1) {
                        this.availableShips.remove(shipType);
                        continue;
                    }

                    shipsLeft = availableShips.get(shipType) - 1;
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

                if (!this.playerBoard.tryPlaceShip(shipType, direction, location)) {
                    System.out.println("[Error] Invalid location.");
                    continue;
                }

                break;
            }

            this.playerBoard.printBoard();

        }

        System.out.println("The bot is placing their ships...");
        Bot.placeRandomShips(this.botBoard, this.difficulty);

        while (true) {
            CLIHandler.clear();

            System.out.println("Enemy board: ");
            this.botBoard.printEnemyDisplayBoard();

            Location attackLocation;

            while (true) {
                String attackLocationStr = CLIHandler.promptString("Where would you like to attack (ex: B3): ");

                try {
                    attackLocation = GameManager.parseLocation(attackLocationStr);
                } catch (Exception error) {
                    System.out.println("[Error] Invalid location. Please enter [A-" + GameManager.MAX_COLUMNS[this.difficulty.getBoardSize() - 1] + "][1-" + GameManager.MAX_ROWS[this.difficulty.getBoardSize() - 1] + "].");
                    continue;
                }

                break;
            }

            shotsFired++;
            MediaPlayer.playSound(MediaPlayer.Sound.FIRE);
            TimeUnit.SECONDS.sleep(2);

            if (this.botBoard.attack(attackLocation)) {
                System.out.println("Nice! You landed a hit!");
                MediaPlayer.playSound(MediaPlayer.Sound.EXPLODE);
                hits++;
            } else {
                System.out.println("You missed!");
                MediaPlayer.playSound(MediaPlayer.Sound.MISS);
            }

            System.out.println("\nEnemy board: ");
            this.botBoard.printEnemyDisplayBoard();

            TimeUnit.SECONDS.sleep(1);

            System.out.println("\nThe bot is now making their move...\n");

            TimeUnit.SECONDS.sleep(2);

            if (this.playerBoard.attack(Bot.getAttackLocation(this.playerBoard, this.difficulty))) {
                System.out.println("A hit was landed on you!\n");
                MediaPlayer.playSound(MediaPlayer.Sound.EXPLODE);
            } else {
                System.out.println("The enemy missed! We're safe for now!\n");
                MediaPlayer.playSound(MediaPlayer.Sound.MISS);
            }

            if (!this.playerBoard.isShipAlive()) {
                endGame(false);
            }

            if (!this.botBoard.isShipAlive()) {
                endGame(true);
            }

            System.out.println("Your board: ");
            this.playerBoard.printBoard();

            CLIHandler.promptString("\nPress enter anything to continue... ");

        }

    }

    public void endGame(boolean wonGame) {
        CLIHandler.clear();

        if (wonGame) {
            MediaPlayer.playSound(MediaPlayer.Sound.WIN);

            System.out.println("------------ Winner ------------");
            System.out.println(GameManager.getRandomMessage(GameManager.WIN_MESSAGES));
        } else {
            MediaPlayer.playSound(MediaPlayer.Sound.LOSE);

            System.out.println("------------ Loser ------------");
            System.out.println(GameManager.getRandomMessage(GameManager.LOSE_MESSAGES));
        }

        System.out.println();
        System.out.println("Statistics:");
        System.out.println("\tDifficulty played: " + this.difficulty.getName());
        System.out.println("\tShots fired: " + this.shotsFired);
        System.out.println("\tHits landed: " + this.hits);
        System.out.println("\tAccuracy: " + Math.round((((double) this.hits / this.shotsFired) * 100)) + "%");
        System.out.println("--------------------------------");

        System.exit(0);
    }

}
