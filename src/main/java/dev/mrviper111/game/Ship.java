package dev.mrviper111.game;

public class Ship {

    private enum ShipType {
        DESTROYER,
        BATTLESHIP,
        CARRIER
    }

    private final ShipType shipType;
    private final String id;

    public Ship(ShipType shipType, String id) {
        this.shipType = shipType;
        this.id = id;
    }

}
