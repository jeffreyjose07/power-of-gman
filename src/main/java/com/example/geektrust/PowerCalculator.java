package com.example.geektrust;

import java.util.*;


public class PowerCalculator implements PathFindingStrategy {
    public static final int INITIAL_POWER = 200;
    public static final int MOVE_COST = 10;
    public static final int TURN_COST = 5;

    
    @Override
    public int calculatePower(int sX, int sY, int dX, int dY, String dir) {
        if (!GMan.inBounds(sX, sY) || !GMan.inBounds(dX, dY)) {
            return 0;
        }
        int minPowerSpent = dijkstra(sX, sY, dX, dY, dir);
        int remaining = INITIAL_POWER - minPowerSpent;
        return Math.max(remaining, 0);
    }

    
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

            exploreNeighbours(curr, minPower, pq);
        }
        
        return minPowerSpent;
    }
    
    
    private int[][][] initializePowerGrid() {
        int[][][] minPower = new int[GMan.GRID_SIZE][GMan.GRID_SIZE][GMan.NUM_DIRECTIONS];
        for (int[][] arr2d : minPower) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        return minPower;
    }
    
    
    private PriorityQueue<State> initializePriorityQueue(int sX, int sY, int startDirIdx) {
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getPowerSpent));
        pq.add(new State(sX, sY, startDirIdx, 0));
        return pq;
    }
    
    
    private boolean isDestinationReached(State current, int destX, int destY) {
        return current.getX() == destX && current.getY() == destY;
    }


    private void exploreNeighbours(State current, int[][][] minPower, PriorityQueue<State> pq) {
        tryMovingForward(current, minPower, pq);
        tryTurning(current, minPower, pq,
                   (current.getDirIdx() + GMan.TURN_LEFT_OFFSET) % GMan.NUM_DIRECTIONS);
        tryTurning(current, minPower, pq,
                   (current.getDirIdx() + GMan.TURN_RIGHT_OFFSET) % GMan.NUM_DIRECTIONS);
    }


    private void tryMovingForward(State current, int[][][] minPower, PriorityQueue<State> pq) {
        int nx = current.getX() + GMan.DIR_DELTAS[current.getDirIdx()][GMan.X_INDEX];
        int ny = current.getY() + GMan.DIR_DELTAS[current.getDirIdx()][GMan.Y_INDEX];
        
        if (GMan.inBounds(nx, ny)) {
            int newPower = current.getPowerSpent() + MOVE_COST;
            if (newPower < minPower[nx][ny][current.getDirIdx()]) {
                minPower[nx][ny][current.getDirIdx()] = newPower;
                pq.add(new State(nx, ny, current.getDirIdx(), newPower));
            }
        }
    }
    
    
    private void tryTurning(State current, int[][][] minPower, PriorityQueue<State> pq, int newDir) {
        int turnPower = current.getPowerSpent() + TURN_COST;
        if (turnPower < minPower[current.getX()][current.getY()][newDir]) {
            minPower[current.getX()][current.getY()][newDir] = turnPower;
            pq.add(new State(current.getX(), current.getY(), newDir, turnPower));
        }
    }
}