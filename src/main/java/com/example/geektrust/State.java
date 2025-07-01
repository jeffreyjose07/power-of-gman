package com.example.geektrust;

/**
 * Represents a state in the grid for G-Man's pathfinding.
 * Holds position (x, y), direction index, and power spent so far.
 */
public class State {
    public final int x, y, dirIdx, powerSpent;
    public State(int x, int y, int dirIdx, int powerSpent) {
        this.x = x;
        this.y = y;
        this.dirIdx = dirIdx;
        this.powerSpent = powerSpent;
    }
} 