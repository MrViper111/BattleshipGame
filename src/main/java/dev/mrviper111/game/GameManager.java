package dev.mrviper111.game;

import java.util.Arrays;

public class GameManager {

    public static String[] MAX_ROWS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    public static String[] MAX_COLUMNS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

    public static Location parseLocation(String locationStr) {

        System.out.println(locationStr.substring(0, 1));
        System.out.println(locationStr.substring(1));

        int column = Arrays.toString(MAX_COLUMNS).indexOf(locationStr.substring(0, 1)) - 1;
        int row = Integer.parseInt(locationStr.substring(1)) - 1;

        return new Location(row, column);
    }

}
