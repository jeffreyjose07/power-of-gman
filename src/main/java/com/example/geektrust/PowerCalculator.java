package com.example.geektrust;

import java.util.*;

/**
 * Implements the pathfinding strategy using Dijkstra's algorithm to calculate
 * the minimum power required for G-Man to reach a destination from a source.
 */
public class PowerCalculator implements PathFindingStrategy {
    public static final int INITIAL_POWER = 200;
    public static final int MOVE_COST = 10;
    public static final int TURN_COST = 5;

    /**
     * Calculates the remaining power after taking the shortest path from source to destination.
     *
     * @param sX Source X coordinate
     * @param sY Source Y coordinate
     * @param dX Destination X coordinate
     * @param dY Destination Y coordinate
     * @param dir Initial direction (N, E, S, W)
     * @return Remaining power, or 0 if unreachable or invalid coordinates
     */
    @Override
    public int calculatePower(int sX, int sY, int dX, int dY, String dir) {
        if (!GMan.inBounds(sX, sY) || !GMan.inBounds(dX, dY)) {
            return 0;
        }
        int minPowerSpent = dijkstra(sX, sY, dX, dY, dir);
        int remaining = INITIAL_POWER - minPowerSpent;
        return Math.max(remaining, 0);
    }

    /**
     * Performs Dijkstra's algorithm to find the minimum power spent from source to destination.
     *
     * @param sX Source X coordinate
     * @param sY Source Y coordinate
     * @param dX Destination X coordinate
     * @param dY Destination Y coordinate
     * @param dir Initial direction (N, E, S, W)
     * @return Minimum power spent to reach the destination, or Integer.MAX_VALUE if unreachable
     */
    private int dijkstra(int sX, int sY, int dX, int dY, String dir) {
        int startDirIdx = GMan.getDirectionIndex(dir);
        int[][][] minPower = initializePowerGrid();
        PriorityQueue<State> pq = initializePriorityQueue(sX, sY, startDirIdx);
        
        minPower[sX][sY][startDirIdx] = 0;
        int minPowerSpent = Integer.MAX_VALUE;
        
        while (!pq.isEmpty()) {
            State curr = pq.poll();
            
            if (isDestinationReached(curr, dX, dY)) {
                minPowerSpent = Math.min(minPowerSpent, curr.getPowerSpent());
                continue;
            }
            
            // Try moving forward
            tryMovingForward(curr, minPower, pq);
            
            // Try turning left
            tryTurning(curr, minPower, pq, (curr.getDirIdx() + 3) % 4);
            
            // Try turning right
            tryTurning(curr, minPower, pq, (curr.getDirIdx() + 1) % 4);
        }
        
        return minPowerSpent;
    }
    
    /**
     * Initializes the power grid with maximum integer values.
     * 
     * @return 3D array representing minimum power to reach each (x,y) position facing each direction
     */
    private int[][][] initializePowerGrid() {
        int[][][] minPower = new int[GMan.GRID_SIZE][GMan.GRID_SIZE][4];
        for (int[][] arr2d : minPower) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        return minPower;
    }
    
    /**
     * Initializes the priority queue with the starting state.
     * 
     * @param sX Source X coordinate
     * @param sY Source Y coordinate
     * @param startDirIdx Starting direction index
     * @return PriorityQueue initialized with the starting state
     */
    private PriorityQueue<State> initializePriorityQueue(int sX, int sY, int startDirIdx) {
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getPowerSpent));
        pq.add(new State(sX, sY, startDirIdx, 0));
        return pq;
    }
    
    /**
     * Checks if the current state is the destination.
     * 
     * @param current The current state
     * @param destX Destination X coordinate
     * @param destY Destination Y coordinate
     * @return true if current position matches destination, false otherwise
     */
    private boolean isDestinationReached(State current, int destX, int destY) {
        return current.getX() == destX && current.getY() == destY;
    }
    
    /**
     * Attempts to move forward from the current state.
     * 
     * @param current The current state
     * @param minPower 3D array tracking minimum power to reach each state
     * @param pq The priority queue for Dijkstra's algorithm
     */
    private void tryMovingForward(State current, int[][][] minPower, PriorityQueue<State> pq) {
        int nx = current.getX() + GMan.DIR_DELTAS[current.getDirIdx()][0];
        int ny = current.getY() + GMan.DIR_DELTAS[current.getDirIdx()][1];
        
        if (GMan.inBounds(nx, ny)) {
            int newPower = current.getPowerSpent() + MOVE_COST;
            if (newPower < minPower[nx][ny][current.getDirIdx()]) {
                minPower[nx][ny][current.getDirIdx()] = newPower;
                pq.add(new State(nx, ny, current.getDirIdx(), newPower));
            }
        }
    }
    
    /**
     * Attempts to turn from the current state.
     * 
     * @param current The current state
     * @param minPower 3D array tracking minimum power to reach each state
     * @param pq The priority queue for Dijkstra's algorithm
     * @param newDir The new direction after turning
     */
    private void tryTurning(State current, int[][][] minPower, PriorityQueue<State> pq, int newDir) {
        int turnPower = current.getPowerSpent() + TURN_COST;
        if (turnPower < minPower[current.getX()][current.getY()][newDir]) {
            minPower[current.getX()][current.getY()][newDir] = turnPower;
            pq.add(new State(current.getX(), current.getY(), newDir, turnPower));
        }
    }
}