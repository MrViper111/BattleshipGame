package dev.mrviper111.game;

public class Location {

    private final int row;
    private final int column;

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

}
