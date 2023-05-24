package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;
import dev.mrviper111.utils.CLIHandler;
import dev.mrviper111.utils.MediaPlayer;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

    private final Map<ShipType, Integer> availableShips;
    private final Difficulty difficulty;

    private Board playerBoard;
    private Board botBoard;

    private int playerShotsFired;
    private int playerHits;

    private int botShotsFired;
    private int botHits;

    private final long startTime;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.availableShips = GameManager.parseShipDuplicates(this.difficulty.getAllocatedShips());

        this.playerShotsFired = 0;
        this.playerHits = 0;

        this.startTime = System.currentTimeMillis();
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
                    System.out.println("[Error] You can't place your ship here.");
                    continue;
                }

                break;
            }

            System.out.println();
            this.playerBoard.printBoard();

        }

        System.out.println("The enemy is placing their ships...");
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

            playerShotsFired++;
            MediaPlayer.playSound(MediaPlayer.Sound.FIRE);
            TimeUnit.SECONDS.sleep(2);

            if (this.botBoard.attack(attackLocation)) {
                System.out.println(GameManager.getRandomMessage(GameManager.HIT_MESSAGES));
                MediaPlayer.playSound(MediaPlayer.Sound.EXPLODE);
                playerHits++;
            } else {
                System.out.println(GameManager.getRandomMessage(GameManager.MISS_MESSAGES));
                MediaPlayer.playSound(MediaPlayer.Sound.MISS);
            }

            System.out.println("\nEnemy board: ");
            this.botBoard.printEnemyDisplayBoard();

            TimeUnit.SECONDS.sleep(1);

            System.out.println("\nThe bot is now making their move...\n");

            TimeUnit.SECONDS.sleep(2);

            if (this.playerBoard.attack(Bot.getAttackLocation(this.playerBoard, this.difficulty))) {
                System.out.println(GameManager.getRandomMessage(GameManager.BOT_HIT_MESSAGES));
                MediaPlayer.playSound(MediaPlayer.Sound.EXPLODE);
                botHits++;
            } else {
                System.out.println(GameManager.getRandomMessage(GameManager.BOT_MISS_MESSAGES));
                MediaPlayer.playSound(MediaPlayer.Sound.MISS);
            }
            System.out.println();
            botShotsFired++;

            if (!this.playerBoard.isShipAlive()) {
                endGame(false);
            }

            if (!this.botBoard.isShipAlive()) {
                endGame(true);
            }

            System.out.println("Your board: ");
            this.playerBoard.printBoard();

            CLIHandler.promptString("\nEnter anything to continue... ");

        }

    }

    public void endGame(boolean wonGame) {
        long endTime = System.currentTimeMillis();
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
        System.out.println("\tPlayer:");
        System.out.println("\t  - Shots fired: " + this.playerShotsFired);
        System.out.println("\t  - Hits landed: " + this.playerHits);
        System.out.println("\t  - Accuracy: " + Math.round((((double) this.playerHits / this.playerShotsFired) * 100)) + "%");
        System.out.println("\tBot:");
        System.out.println("\t  - Shots fired: " + this.botShotsFired);
        System.out.println("\t  - Hits landed: " + this.botHits);
        System.out.println("\t  - Accuracy: " + Math.round((((double) this.botHits / this.botShotsFired) * 100)) + "%");
        System.out.println();
        System.out.println("Difficulty played: " + this.difficulty.getName());
        System.out.println("The game lasted " + ((endTime - startTime) / 60000) + " minute(s) and " + (((endTime - startTime) / 1000) % 60) + " second(s).");
        System.out.println("--------------------------------");

        System.exit(0);
    }

}
