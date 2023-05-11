package dev.mrviper111.game.enums;

public enum ShipType {

    PATROL_BOAT ("Patrol Boat", "pb", 2),
    DESTROYER ("Destroyer", "d", 3),
    BATTLESHIP ("Battleship", "bs", 4);

    private final String name;
    private final String id;
    private final int size;

    ShipType(String name, String id, int size) {
        this.name = name;
        this.id = id;
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public int getSize() {
        return this.size;
    }

}
