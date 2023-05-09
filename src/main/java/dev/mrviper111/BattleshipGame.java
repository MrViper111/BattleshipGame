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

        String username;
        Difficulty difficulty;

        while (true) {
            username = CLIHandler.promptString("Enter a username: ");

            if (username.contains(" ")) {
                System.out.println("[Error] Your username cannot contain any spaces.");
            } else if (username.length() > 16) {
                System.out.println("[Error] The length of your username cannot be more than 16 characters.");
            } else {
                break;
            }
        }

        System.out.println("\nDifficulty options...");
        System.out.println("  1. Easy - 4 ships on a 8x8 board");
        System.out.println("  2. Medium - 5 ships on a 10x10 board");
        System.out.println("  3. Hard - 7 ships on a 15x15 board\n");

        while (true) {
            String difficultyStr = CLIHandler.promptString("Enter a difficulty (1-3): ").toLowerCase();

            if (difficultyStr.equals("1") || difficultyStr.equals("easy")) {
                difficulty = Difficulty.EASY;
                break;
            } else if (difficultyStr.equals("2") || difficultyStr.equals("medium")) {
                difficulty = Difficulty.MEDIUM;
                break;
            } else if (difficultyStr.equals("3") || difficultyStr.equals("hard")) {
                difficulty = Difficulty.HARD;
                break;
            } else {
                System.out.println("[Error] Please enter a valid difficulty (1-3).");
            }
        }

        System.out.println("\nStarting game...\n");

        CLIHandler.clear();

        Game game = new Game(difficulty, username);
        game.init();
    }

}
