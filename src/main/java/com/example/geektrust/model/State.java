package com.example.geektrust.model;

public class State {
    private final Position position;
    private final Direction direction;
    private final int powerSpent;

    public State(int x, int y, Direction direction, int powerSpent) {
        this(new Position(x, y), direction, powerSpent);
    }

    public State(Position position, Direction direction, int powerSpent) {
        this.position = position;
        this.direction = direction;
        this.powerSpent = powerSpent;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getPowerSpent() {
        return powerSpent;
    }
}
