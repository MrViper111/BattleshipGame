package dev.mrviper111.game;

import dev.mrviper111.game.enums.Difficulty;
import dev.mrviper111.game.enums.Direction;
import dev.mrviper111.game.enums.ShipType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Bot {

    private static final ArrayList<Location> pastMoves = new ArrayList<>();

    public static void placeRandomShips(Board board, Difficulty difficulty) {

        for (int i = 0; i < difficulty.getAllocatedShips().length; i++) {
            ShipType ship = difficulty.getAllocatedShips()[i];

            board.addRandomShip(ship);
        }

    }

    private static Location getRandomNewLocation(Difficulty difficulty) {
        Location location;

        while (true) {
            int row = ThreadLocalRandom.current().nextInt(0, difficulty.getBoardSize() - 1);
            int column = ThreadLocalRandom.current().nextInt(0, difficulty.getBoardSize() - 1);

            location = new Location(row, column);

            if (pastMoves.contains(location)) {
                continue;
            }

            break;
        }

        return location;
    }

    public static Location getAttackLocation(Board board, Difficulty difficulty) {
        int row;
        int column;

        switch (difficulty) {

            case MEDIUM, HARD, TEST -> {

                if (pastMoves.isEmpty()) {
                    row = ThreadLocalRandom.current().nextInt(0, difficulty.getBoardSize() - 1);
                    column = ThreadLocalRandom.current().nextInt(0, difficulty.getBoardSize() - 1);

                    Location location = new Location(row, column);

                    pastMoves.add(location);
                    return new Location(row, column);
                }

                Location latestMove = pastMoves.get(pastMoves.size() - 1);
                Location location;

                if (board.getBoard()[latestMove.getRow()][latestMove.getColumn()].equals("1") || board.getBoard()[latestMove.getRow()][latestMove.getColumn()].equals("X")) {

                    while (true) {
                        Direction randomDirection = GameManager.POSSIBLE_DIRECTIONS[ThreadLocalRandom.current().nextInt(0, GameManager.POSSIBLE_DIRECTIONS.length - 1)];
                        location = new Location(latestMove.getRow() + randomDirection.getRowOffset(), latestMove.getColumn() + randomDirection.getColumnOffset());

                        if (location.getRow() > difficulty.getBoardSize() - 1 || location.getRow() < 0) {
                            continue;
                        }

                        if (location.getColumn() > difficulty.getBoardSize() - 1 || location.getColumn() < 0) {
                            continue;
                        }

                        if (pastMoves.contains(location)) {
                            continue;
                        }

                        break;
                    }

                    pastMoves.add(location);
                    return location;

                } else if (board.getBoard()[latestMove.getRow()][latestMove.getColumn()].equals("0") || board.getBoard()[latestMove.getRow()][latestMove.getColumn()].equals("-")) {
                    Location randomLocation = getRandomNewLocation(difficulty);

                    pastMoves.add(randomLocation);
                    return randomLocation;
                }

            }

            default -> {
                Location randomLocation = getRandomNewLocation(difficulty);

                pastMoves.add(randomLocation);
                return randomLocation;
            }

        }

        Location defaultLocation = getRandomNewLocation(difficulty);

        pastMoves.add(defaultLocation);
        return defaultLocation;
    }

}
