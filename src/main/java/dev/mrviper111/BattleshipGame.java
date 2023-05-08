package dev.mrviper111;

import dev.mrviper111.game.Difficulty;
import dev.mrviper111.game.Game;
import dev.mrviper111.utils.InputHandler;

public class BattleshipGame {

    public static void main(String[] args) {

        System.out.println("--------------------------------");
        System.out.println("Welcome to Battleship!");
        System.out.println("This is a very low budget version of battleship.");
        System.out.println("So yeah if you don't know the instructions too bad!")
        System.out.println("--------------------------------\n");

        while (true) {
            String username = InputHandler.promptString("Enter a username: ");

            if (username.contains(" ")) {
                System.out.println("[Error] Your username cannot contain any spaces.");
            } else if (username.length() > 16) {
                System.out.println("[Error] The length of your username cannot be more than 16 characters.");
            } else {
                break;
            }
        }

        while (true) {
            String difficulty = InputHandler.promptString("Enter a difficulty (Easy, Medium, Hard): ");

            switch (difficulty.toLowerCase()) {
                case "easy": {
                    Game game = new Game(Difficulty.EASY, username);
                    break;
                }
                case "medium": {
                    Game game = new Game(Difficulty.MEDIUM, username);
                    break;
                }
                case "hard": {
                    Game game = new Game(Difficulty.HARD, username);
                    break;
                }
                default: {
                    System.out.println("[Error] Invalid difficulty. Please enter either easy, medium, or hard.");
                }
            }

            break;
        }

        System.out.println("\nStarting game...\n");

        System.out.print("\033[H\033[2J");  
        System.out.flush();

        game.init();
    }

}
