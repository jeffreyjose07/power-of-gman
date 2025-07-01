package com.example.geektrust;

public class GMan {
    private int x, y, dirIdx;
    public static final String[] DIRECTIONS = {"N", "E", "S", "W"};
    public static final int[][] DIR_DELTAS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // N, E, S, W
    public static final int GRID_SIZE = 7;

    public GMan(int x, int y, String dir) {
        this.x = x;
        this.y = y;
        this.dirIdx = getDirectionIndex(dir);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDirIdx() { return dirIdx; }

    public void moveForward() {
        x += DIR_DELTAS[dirIdx][0];
        y += DIR_DELTAS[dirIdx][1];
    }

    public void turnLeft() {
        dirIdx = (dirIdx + 3) % 4;
    }

    public void turnRight() {
        dirIdx = (dirIdx + 1) % 4;
    }

    public static int getDirectionIndex(String dir) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i].equals(dir)) return i;
        }
        throw new IllegalArgumentException("Invalid direction: " + dir);
    }

    public static boolean inBounds(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }
} 