package com.example.geektrust;

import java.util.*;


public class PowerCalculator implements PathFindingStrategy {
    public static final int INITIAL_POWER = 200;
    public static final int MOVE_COST = 10;
    public static final int TURN_COST = 5;

    
    @Override
    public int calculatePower(Position source, Position destination, String dir) {
        if (!GMan.inBounds(source.getX(), source.getY()) || !GMan.inBounds(destination.getX(), destination.getY())) {
            return 0;
        }
        int minPowerSpent = dijkstra(source, destination, dir);
        int remaining = INITIAL_POWER - minPowerSpent;
        return Math.max(remaining, 0);
    }

    
    private int dijkstra(Position source, Position dest, String dir) {
        int startDirIdx = GMan.getDirectionIndex(dir);
        int[][][] minPower = initializePowerGrid();
        PriorityQueue<State> pq = initializePriorityQueue(source.getX(), source.getY(), startDirIdx);

        minPower[source.getX()][source.getY()][startDirIdx] = 0;
        int minPowerSpent = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            State curr = pq.poll();

            if (isDestinationReached(curr, dest.getX(), dest.getY())) {
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