package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Queue;

/**
 * Provides common movement operations for path finders.
 */
public abstract class AbstractPathFinder implements PathFinder {
    protected final Board board;
    protected final int moveCost;
    protected final int turnCost;

    protected AbstractPathFinder(Board board, int moveCost, int turnCost) {
        this.board = board;
        this.moveCost = moveCost;
        this.turnCost = turnCost;
    }

    /**
     * Determine the priority with which a state should be queued.
     * By default this is the cost spent so far.
     */
    protected int computePriority(Position pos, int costSoFar, Position dest) {
        return costSoFar;
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
