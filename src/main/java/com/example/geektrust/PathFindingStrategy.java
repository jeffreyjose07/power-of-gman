package com.example.geektrust;

/**
 * Strategy interface for pathfinding algorithms for G-Man.
 */
public interface PathFindingStrategy {
    /**
     * Calculates the remaining power after taking the shortest path from source to destination.
     */
    int calculatePower(int sX, int sY, int dX, int dY, String dir);
} 