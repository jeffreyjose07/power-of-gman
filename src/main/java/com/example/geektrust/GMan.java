package com.example.geektrust;


public class GMan {
    private int x;
    private int y;
    private int dirIdx;
    
    
    public static final String[] DIRECTIONS = {"N", "E", "S", "W"};
    
    
    public static final int[][] DIR_DELTAS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static final int X_INDEX = 0;
    public static final int Y_INDEX = 1;

    public static final int NUM_DIRECTIONS = 4;
    public static final int TURN_LEFT_OFFSET = 3;
    public static final int TURN_RIGHT_OFFSET = 1;
    
    
    public static final int GRID_SIZE = 7;

    
    public GMan(int x, int y, String dir) {
        this.x = x;
        this.y = y;
        this.dirIdx = getDirectionIndex(dir);
    }

    
    public int getX() {
        return x;
    }

    
    public int getY() {
        return y;
    }

    
    public int getDirIdx() {
        return dirIdx;
    }

    
    public void moveForward() {
        x += DIR_DELTAS[dirIdx][X_INDEX];
        y += DIR_DELTAS[dirIdx][Y_INDEX];
    }

    
    public void turnLeft() {
        dirIdx = (dirIdx + TURN_LEFT_OFFSET) % NUM_DIRECTIONS;
    }

    
    public void turnRight() {
        dirIdx = (dirIdx + TURN_RIGHT_OFFSET) % NUM_DIRECTIONS;
    }

    
    public static int getDirectionIndex(String dir) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i].equals(dir)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid direction: " + dir + ". Must be one of: N, E, S, W");
    }

    
    public static boolean inBounds(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }
}