package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {

    public static final String[] MAX_ROWS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    public static final String[] MAX_COLUMNS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
    public static final Direction[] POSSIBLE_DIRECTIONS = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

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

}
