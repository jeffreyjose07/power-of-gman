package com.example.geektrust.model;

public enum Direction {
    N(0, 1),
    E(1, 0),
    S(0, -1),
    W(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public Direction left() {
        return values()[(ordinal() + 3) % values().length];
    }

    public Direction right() {
        return values()[(ordinal() + 1) % values().length];
    }

    public static Direction fromString(String dir) {
        for (Direction d : values()) {
            if (d.name().equals(dir)) {
                return d;
            }
        }
        throw new IllegalArgumentException(
            "Invalid direction: " + dir + ". Must be one of: N, E, S, W");
    }
}
