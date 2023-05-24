package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Bot {

    private static final HashMap<Location, String> pastMoves = new HashMap<>();

    public static void placeRandomShips(Board board, Difficulty difficulty) {

        for (int i = 0; i < difficulty.getAllocatedShips().length; i++) {
            ShipType ship = difficulty.getAllocatedShips()[i];

            board.addRandomShip(ship);
        }

    }

    public static Location getAttackLocation(Difficulty difficulty) {
        int randomRow = ThreadLocalRandom.current().nextInt(0, difficulty.getBoardSize() - 1);
        int randomColumn = ThreadLocalRandom.current().nextInt(0, difficulty.getBoardSize() - 1);

        return new Location(randomRow, randomColumn);
    }

}
