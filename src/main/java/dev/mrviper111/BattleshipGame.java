package dev.mrviper111;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.Game;
import dev.mrviper111.utils.CLIHandler;

public class BattleshipGame {

    public static void main(String[] args) {

        CLIHandler.clear();

        System.out.println("--------------------------------");
        System.out.println("Welcome to Battleship!");
        System.out.println("This is a very low budget version of battleship.");
        System.out.println("So yeah if you don't know the instructions too bad!");
        System.out.println("--------------------------------\n");

        Difficulty difficulty;

        System.out.println("\nDifficulty options...");
        System.out.println("  1. Easy - 4 ships on a 8x8 board");
        System.out.println("  2. Medium - 5 ships on a 10x10 board");
        System.out.println("  3. Hard - 7 ships on a 15x15 board\n");

        label:
        while (true) {
            String difficultyStr = CLIHandler.promptString("Enter a difficulty (1-3): ").toLowerCase();

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
        game.init();
    }

}
