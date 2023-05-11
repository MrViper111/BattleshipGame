package dev.mrviper111.game;

import dev.mrviper111.game.enums.ShipType;

import java.util.Arrays;
import java.util.HashMap;

public class GameManager {

    public static final String[] MAX_ROWS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    public static final String[] MAX_COLUMNS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

    public static Location parseLocation(String locationStr) {
        int column = Arrays.toString(MAX_COLUMNS).indexOf(locationStr.substring(0, 1)) - 1;
        int row = Integer.parseInt(locationStr.substring(1)) - 1;

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

        System.out.println(patrolBoatCount);
        System.out.println(destroyerCount);
        System.out.println(battleshipCount);

        shipMap.put(ShipType.PATROL_BOAT, patrolBoatCount);
        shipMap.put(ShipType.DESTROYER, destroyerCount);
        shipMap.put(ShipType.BATTLESHIP, battleshipCount);

        return shipMap;
    }

}
