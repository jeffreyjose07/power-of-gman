package com.example.geektrust;

public class GMan {
    private Position position;
    private Direction direction;
    private final Board board;

    public GMan(int x, int y, Direction dir) {
        this(new Board(), new Position(x, y), dir);
    }

    public GMan(Board board, Position position, Direction dir) {
        this.board = board;
        this.position = position;
        this.direction = dir;
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

    public void moveForward() {
        position = position.move(direction);
    }

    public void turnLeft() {
        direction = direction.left();
    }

    public void turnRight() {
        direction = direction.right();
    }

    public boolean inBounds() {
        return board.inBounds(position);
    }
}
