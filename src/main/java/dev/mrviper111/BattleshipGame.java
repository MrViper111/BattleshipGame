package dev.mrviper111;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.Game;
import dev.mrviper111.game.enums.ShipType;
import dev.mrviper111.utils.CLIHandler;
import dev.mrviper111.utils.MediaPlayer;

import java.util.concurrent.TimeUnit;

public class BattleshipGame {

    public static void main(String[] args) throws InterruptedException {
        CLIHandler.clear();
        MediaPlayer.playSound(MediaPlayer.Sound.DISTANT_BOMBS);

        System.out.println("--------------------------------");
        System.out.println("Welcome to Battleship!");
        System.out.println("This is a very low budget version of battleship.");
        System.out.println("So yeah if you don't know the instructions too bad!");
        System.out.println("--------------------------------\n");

        TimeUnit.SECONDS.sleep(2);

        Difficulty difficulty;

        System.out.println("\nDifficulty options...");
        System.out.println("  1. Easy\n\t - 8x8 board\n\t - 4 ships\n\t - No bot intelligence");
        System.out.println("  2. Medium\n\t - 10x10 board\n\t - 5 ships\n\t - Bot intelligence");
        System.out.println("  3. Hard\n\t - 15x15 board\n\t - 7 ships\n\t - Bot intelligence");

        label:
        while (true) {
            String difficultyStr = CLIHandler.promptString("\nEnter a difficulty (1-3): ").toLowerCase();

            switch (difficultyStr) {
                case "1":
                case "easy":
                    difficulty = Difficulty.EASY;
                    break label;
                case "2":
                case "medium":
                    difficulty = Difficulty.MEDIUM;
                    break label;
                case "3":
                case "hard":
                    difficulty = Difficulty.HARD;
                    break label;
                default:
                    System.out.println("[Error] Please enter a valid difficulty (1-3).");
                    break;
            }
        }

        System.out.println("\nStarting game...\n");
        CLIHandler.clear();

        Game game = new Game(difficulty);

        MediaPlayer.playSound(MediaPlayer.Sound.DING);
        game.init();
    }

}
