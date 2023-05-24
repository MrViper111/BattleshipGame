package dev.mrviper111.game.enums;

public enum Direction {

    UP (0, -1),
    DOWN (0, 1),
    LEFT (-1, 0),
    RIGHT (1, 0);

    private final int columnOffset;
    private final int rowOffset;

    Direction(int columnOffset, int rowOffset) {
        this.columnOffset = columnOffset;
        this.rowOffset = rowOffset;
    }

    public int getColumnOffset() {
        return columnOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }
}
