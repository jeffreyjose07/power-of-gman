package com.example.geektrust;

/**
 * Represents a state in the grid for G-Man's pathfinding.
 * Holds position (x, y), direction index, and power spent so far.
 */
public class State {
    private final int x;
    private final int y;
    private final int dirIdx;
    private final int powerSpent;

    /**
     * Constructs a new State with the given parameters.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param dirIdx The direction index (0=N, 1=E, 2=S, 3=W)
     * @param powerSpent The amount of power spent to reach this state
     */
    public State(int x, int y, int dirIdx, int powerSpent) {
        this.x = x;
        this.y = y;
        this.dirIdx = dirIdx;
        this.powerSpent = powerSpent;
    }

    /**
     * @return The x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @return The direction index (0=N, 1=E, 2=S, 3=W)
     */
    public int getDirIdx() {
        return dirIdx;
    }

    /**
     * @return The amount of power spent to reach this state
     */
    public int getPowerSpent() {
        return powerSpent;
    }
}