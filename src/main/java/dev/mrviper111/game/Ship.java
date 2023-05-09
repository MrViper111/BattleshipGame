package dev.mrviper111.game;

public class Ship {

    private String name;
    private String id;
    private int size;

    public Ship(String name, String id, int size) {
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
