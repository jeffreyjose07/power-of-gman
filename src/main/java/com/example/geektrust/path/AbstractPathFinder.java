package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Arrays;
import java.util.Queue;


public abstract class AbstractPathFinder implements PathFinder {
    protected final Board board;
    protected final int moveCost;
    protected final int turnCost;

    protected AbstractPathFinder(Board board, int moveCost, int turnCost) {
        this.board = board;
        this.moveCost = moveCost;
        this.turnCost = turnCost;
    }


    protected int computePriority(Position pos, int costSoFar, Position dest) {
        return costSoFar;
    }


    protected int[][][] newCostArray() {
        int size = board.getSize();
        int dirCount = Direction.values().length;
        int[][][] costs = new int[size][size][dirCount];
        for (int[][] arr2d : costs) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        return costs;
    }


    protected void enqueueStart(Position source, Direction startDir,
                                int[][][] costs, Queue<State> q) {
        q.add(new State(source, startDir, 0));
        costs[source.getX()][source.getY()][startDir.ordinal()] = 0;
    }

    protected void moveForward(State cur, int[][][] costs, Queue<State> q, Position dest) {
        int nx = cur.getX() + cur.getDirection().dx();
        int ny = cur.getY() + cur.getDirection().dy();
        if (board.inBounds(nx, ny)) {
            int newCost = cur.getPowerSpent() + moveCost;
            int dirIdx = cur.getDirection().ordinal();
            if (newCost < costs[nx][ny][dirIdx]) {
                costs[nx][ny][dirIdx] = newCost;
                int priority = computePriority(new Position(nx, ny), newCost, dest);
                q.add(new State(nx, ny, cur.getDirection(), priority));
            }
        }
    }

    protected void turnLeft(State cur, int[][][] costs, Queue<State> q, Position dest) {
        Direction left = cur.getDirection().left();
        int lCost = cur.getPowerSpent() + turnCost;
        if (lCost < costs[cur.getX()][cur.getY()][left.ordinal()]) {
            costs[cur.getX()][cur.getY()][left.ordinal()] = lCost;
            int priority = computePriority(new Position(cur.getX(), cur.getY()), lCost, dest);
            q.add(new State(cur.getX(), cur.getY(), left, priority));
        }
    }

    protected void turnRight(State cur, int[][][] costs, Queue<State> q, Position dest) {
        Direction right = cur.getDirection().right();
        int rCost = cur.getPowerSpent() + turnCost;
        if (rCost < costs[cur.getX()][cur.getY()][right.ordinal()]) {
            costs[cur.getX()][cur.getY()][right.ordinal()] = rCost;
            int priority = computePriority(new Position(cur.getX(), cur.getY()), rCost, dest);
            q.add(new State(cur.getX(), cur.getY(), right, priority));
        }
    }
}
