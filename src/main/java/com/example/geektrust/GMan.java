package com.example.geektrust;

/**
 * Represents the G-Man character on the grid.
 * Encapsulates position, direction, and movement logic for the G-Man character.
 */
public class GMan {
    private int x;
    private int y;
    private int dirIdx;
    
    /** Array of cardinal directions in clockwise order: North, East, South, West. */
    public static final String[] DIRECTIONS = {"N", "E", "S", "W"};
    
    /** Delta coordinates for movement in each direction: {x, y} */
    public static final int[][] DIR_DELTAS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // N, E, S, W
    
    /** The size of the grid (7x7). */
    public static final int GRID_SIZE = 7;

    /**
     * Constructs a GMan at the given position and direction.
     *
     * @param x The initial x-coordinate (0-6)
     * @param y The initial y-coordinate (0-6)
     * @param dir The initial direction (N, E, S, W)
     * @throws IllegalArgumentException if the direction is invalid
     */
    public GMan(int x, int y, String dir) {
        this.x = x;
        this.y = y;
        this.dirIdx = getDirectionIndex(dir);
    }

    /**
     * Gets the current x-coordinate.
     *
     * @return The x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate.
     *
     * @return The y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the current direction index.
     *
     * @return The direction index (0=N, 1=E, 2=S, 3=W)
     */
    public int getDirIdx() {
        return dirIdx;
    }

    /**
     * Moves G-Man one unit forward in the current direction.
     * Does not check bounds - the caller should verify the move is valid.
     */
    public void moveForward() {
        x += DIR_DELTAS[dirIdx][0];
        y += DIR_DELTAS[dirIdx][1];
    }

    /**
     * Turns G-Man 90 degrees to the left (counter-clockwise).
     */
    public void turnLeft() {
        dirIdx = (dirIdx + 3) % 4; // +3 is equivalent to -1 mod 4
    }

    /**
     * Turns G-Man 90 degrees to the right (clockwise).
     */
    public void turnRight() {
        dirIdx = (dirIdx + 1) % 4;
    }

    /**
     * Gets the direction index for a given direction string.
     *
     * @param dir The direction as a string ("N", "E", "S", or "W")
     * @return The corresponding direction index (0-3)
     * @throws IllegalArgumentException if the direction string is invalid
     */
    public static int getDirectionIndex(String dir) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i].equals(dir)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid direction: " + dir + ". Must be one of: N, E, S, W");
    }

    /**
     * Checks if the given coordinates are within the grid bounds.
     *
     * @param x The x-coordinate to check
     * @param y The y-coordinate to check
     * @return true if the coordinates are within bounds, false otherwise
     */
    public static boolean inBounds(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }
}