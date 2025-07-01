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
        int minPowerSpent = bfs(sX, sY, dX, dY, dir);
        int remaining = INITIAL_POWER - minPowerSpent;
        return Math.max(remaining, 0);
    }

    /**
     * Performs BFS to find the minimum power spent from source to destination.
     */
    private int bfs(int sX, int sY, int dX, int dY, String dir) {
        int startDirIdx = GMan.getDirectionIndex(dir);
        boolean[][][] visited = new boolean[GMan.GRID_SIZE][GMan.GRID_SIZE][4];
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(sX, sY, startDirIdx, 0));
        visited[sX][sY][startDirIdx] = true;
        int minPowerSpent = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            if (isDestination(curr, dX, dY)) {
                minPowerSpent = Math.min(minPowerSpent, curr.powerSpent);
                continue;
            }
            enqueueForward(curr, queue, visited);
            enqueueTurn(curr, queue, visited, true);  // left
            enqueueTurn(curr, queue, visited, false); // right
        }
        return minPowerSpent;
    }

    /**
     * Checks if the current state is at the destination.
     */
    private boolean isDestination(State curr, int dX, int dY) {
        return curr.x == dX && curr.y == dY;
    }

    /**
     * Enqueues the forward move if valid.
     */
    private void enqueueForward(State curr, Queue<State> queue, boolean[][][] visited) {
        int nx = curr.x + GMan.DIR_DELTAS[curr.dirIdx][0];
        int ny = curr.y + GMan.DIR_DELTAS[curr.dirIdx][1];
        if (GMan.inBounds(nx, ny) && !visited[nx][ny][curr.dirIdx]) {
            visited[nx][ny][curr.dirIdx] = true;
            queue.add(new State(nx, ny, curr.dirIdx, curr.powerSpent + MOVE_COST));
        }
    }

    /**
     * Enqueues a turn (left or right) if not already visited.
     * @param left true for left, false for right
     */
    private void enqueueTurn(State curr, Queue<State> queue, boolean[][][] visited, boolean left) {
        int newDir = left ? (curr.dirIdx + 3) % 4 : (curr.dirIdx + 1) % 4;
        if (!visited[curr.x][curr.y][newDir]) {
            visited[curr.x][curr.y][newDir] = true;
            queue.add(new State(curr.x, curr.y, newDir, curr.powerSpent + TURN_COST));
        }
    }
} 