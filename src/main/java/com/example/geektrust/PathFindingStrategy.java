package com.example.geektrust;

/**
 * Defines the contract for pathfinding strategies used to calculate
 * the power required for G-Man to travel from a source to a destination.
 * 
 * <p>Implementations of this interface will provide different algorithms
 * for determining the optimal path and calculating the required power.</p>
 */
public interface PathFindingStrategy {
    /**
     * Calculates the remaining power after taking the shortest path from source to destination.
     *
     * @param sourceX The x-coordinate of the starting position (0-6)
     * @param sourceY The y-coordinate of the starting position (0-6)
     * @param destX The x-coordinate of the destination position (0-6)
     * @param destY The y-coordinate of the destination position (0-6)
     * @param direction The initial direction G-Man is facing ("N", "E", "S", or "W")
     * @return The remaining power after the journey, or 0 if the destination is unreachable
     * @throws IllegalArgumentException if the direction is invalid or coordinates are out of bounds
     */
    int calculatePower(int sourceX, int sourceY, int destX, int destY, String direction);
}