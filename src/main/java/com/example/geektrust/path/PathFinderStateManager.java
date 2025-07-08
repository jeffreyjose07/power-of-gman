package com.example.geektrust.path;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Arrays;
import java.util.Queue;

public class PathFinderStateManager {
    private final int boardSize;
    private final int directionCount;

    public PathFinderStateManager(int boardSize) {
        this.boardSize = boardSize;
        this.directionCount = Direction.values().length;
    }

    public int[][][] createCostArray() {
        int[][][] costs = new int[boardSize][boardSize][directionCount];
        for (int[][] arr2d : costs) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        return costs;
    }

    public void initializeStartState(Position source, Direction startDir, int[][][] costs, Queue<State> queue) {
        queue.add(new State(source, startDir, 0));
        costs[source.getX()][source.getY()][startDir.ordinal()] = 0;
    }

    public boolean shouldUpdateCost(Position pos, Direction dir, int newCost, int[][][] costs) {
        int dirIdx = dir.ordinal();
        return newCost < costs[pos.getX()][pos.getY()][dirIdx];
    }

    public void updateCostAndEnqueue(Position pos, Direction dir, int cost, int[][][] costs, Queue<State> queue) {
        int dirIdx = dir.ordinal();
        costs[pos.getX()][pos.getY()][dirIdx] = cost;
        queue.add(new State(pos.getX(), pos.getY(), dir, cost));
    }
}