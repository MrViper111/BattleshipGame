package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {

    public static final String[] MAX_ROWS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    public static final String[] MAX_COLUMNS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
    public static final Direction[] POSSIBLE_DIRECTIONS = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    public static final String[] HIT_MESSAGES = {"Nice! You landed a hit!", "Bullseye admiral! You hit em!", "Ah, a perfect shot as expected."};
    public static final String[] MISS_MESSAGES = {"WHAT! You fool you missed!", "You missed!", "What a shame, another round wasted..."};

    public static final String[] BOT_HIT_MESSAGES = {"Damn it! We've been hit!", "The enemy hit our ship!", "We're taking on more water! We've been hit!"};
    public static final String[] BOT_MISS_MESSAGES = {"The enemy missed! We're safe for now.", "Ha! They missed!"};

    public static final String[] WIN_MESSAGES = {"Excellent work capturing the seas!", "Good job admiral! You won the game!", "Impressive! I didn't expect you would\nemerge victorious."};
    public static final String[] LOSE_MESSAGES = {"Damn it admiral! YOU BLUNDERED!!!", "What a shame... you lost the game.\nHey, that rhymes!", "You have failed for the last time admi- CAPTAIN."};

    public static Location parseLocation(String locationStr) {
        int row;
        int column = -1;

        for (int i = 0; i < MAX_COLUMNS.length; i++) {
            if (locationStr.substring(0, 1).equalsIgnoreCase(MAX_COLUMNS[i])) {
                column = i;
            }
        }
        row = Integer.parseInt(locationStr.substring(1)) - 1;

        return new Location(row, column);
    }

    public static HashMap<ShipType, Integer> parseShipDuplicates(ShipType[] shipTypes) {
        HashMap<ShipType, Integer> shipMap = new HashMap<>();

        int patrolBoatCount = 0;
        int destroyerCount = 0;
        int battleshipCount = 0;

        for (ShipType shipType : shipTypes) {
            switch (shipType) {
                case PATROL_BOAT -> {
                    patrolBoatCount++;
                }
                case DESTROYER -> {
                    destroyerCount++;
                }
                case BATTLESHIP -> {
                    battleshipCount++;
                }
            }
        }

        shipMap.put(ShipType.PATROL_BOAT, patrolBoatCount);
        shipMap.put(ShipType.DESTROYER, destroyerCount);
        shipMap.put(ShipType.BATTLESHIP, battleshipCount);

        return shipMap;
    }

    public static String getRandomMessage(String[] messages) {
        return messages[ThreadLocalRandom.current().nextInt(0, messages.length - 1)];
    }

}
