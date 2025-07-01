package com.example.geektrust;

public class Board {
    public static final int DEFAULT_SIZE = 7;

    private final int size;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public boolean inBounds(Position p) {
        return inBounds(p.getX(), p.getY());
    }
}
