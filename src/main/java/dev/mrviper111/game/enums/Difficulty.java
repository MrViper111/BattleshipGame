package dev.mrviper111.game.enums;

public enum Difficulty {

    EASY ("easy", 8, ShipType.PATROL_BOAT, ShipType.PATROL_BOAT, ShipType.DESTROYER, ShipType.BATTLESHIP),
    MEDIUM ("medium", 10, ShipType.PATROL_BOAT, ShipType.PATROL_BOAT, ShipType.DESTROYER, ShipType.DESTROYER, ShipType.BATTLESHIP),
    HARD ("hard", 15, ShipType.PATROL_BOAT, ShipType.PATROL_BOAT, ShipType.PATROL_BOAT, ShipType.DESTROYER, ShipType.DESTROYER, ShipType.DESTROYER, ShipType.BATTLESHIP);

    private final String name;
    private final int boardSize;
    private final ShipType[] allocatedShips;

    Difficulty(String name, int boardSize, ShipType... allocatedShips) {
        this.boardSize = boardSize;
        this.name = name;
        this.allocatedShips = allocatedShips;
    }

    public String getName() {
        return this.name;
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public ShipType[] getAllocatedShips() {
        return this.allocatedShips;
    }

}
