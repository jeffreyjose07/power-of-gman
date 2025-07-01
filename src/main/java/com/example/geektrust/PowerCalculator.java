package com.example.geektrust;

import java.util.*;

/**
 * Calculates the minimum power required for G-Man to reach a destination using a pathfinding strategy.
 */
public class PowerCalculator implements PathFindingStrategy {
    public static final int INITIAL_POWER = 200;
    public static final int MOVE_COST = 10;
    public static final int TURN_COST = 5;

    static class State {
        int x, y, dirIdx, powerSpent;
        State(int x, int y, int dirIdx, int powerSpent) {
            this.x = x;
            this.y = y;
            this.dirIdx = dirIdx;
            this.powerSpent = powerSpent;
        }
    }

    /**
     * Calculates the remaining power after taking the shortest path from source to destination.
     * @param sX Source X
     * @param sY Source Y
     * @param dX Destination X
     * @param dY Destination Y
     * @param dir Initial direction (N, E, S, W)
     * @return Remaining power, or 0 if unreachable
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
     */
    private int dijkstra(int sX, int sY, int dX, int dY, String dir) {
        int startDirIdx = GMan.getDirectionIndex(dir);
        int[][][] minPower = new int[GMan.GRID_SIZE][GMan.GRID_SIZE][4];
        for (int[][] arr2d : minPower) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.powerSpent));
        pq.add(new State(sX, sY, startDirIdx, 0));
        minPower[sX][sY][startDirIdx] = 0;
        int minPowerSpent = Integer.MAX_VALUE;
        while (!pq.isEmpty()) {
            State curr = pq.poll();
            if (curr.x == dX && curr.y == dY) {
                minPowerSpent = Math.min(minPowerSpent, curr.powerSpent);
                continue;
            }
            // Move forward
            int nx = curr.x + GMan.DIR_DELTAS[curr.dirIdx][0];
            int ny = curr.y + GMan.DIR_DELTAS[curr.dirIdx][1];
            if (GMan.inBounds(nx, ny)) {
                int newPower = curr.powerSpent + MOVE_COST;
                if (newPower < minPower[nx][ny][curr.dirIdx]) {
                    minPower[nx][ny][curr.dirIdx] = newPower;
                    pq.add(new State(nx, ny, curr.dirIdx, newPower));
                }
            }
            // Turn left
            int leftDir = (curr.dirIdx + 3) % 4;
            int leftPower = curr.powerSpent + TURN_COST;
            if (leftPower < minPower[curr.x][curr.y][leftDir]) {
                minPower[curr.x][curr.y][leftDir] = leftPower;
                pq.add(new State(curr.x, curr.y, leftDir, leftPower));
            }
            // Turn right
            int rightDir = (curr.dirIdx + 1) % 4;
            int rightPower = curr.powerSpent + TURN_COST;
            if (rightPower < minPower[curr.x][curr.y][rightDir]) {
                minPower[curr.x][curr.y][rightDir] = rightPower;
                pq.add(new State(curr.x, curr.y, rightDir, rightPower));
            }
        }
        return minPowerSpent;
    }
} 