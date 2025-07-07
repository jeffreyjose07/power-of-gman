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
    
    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int[][][] costs = newCostArray();
        Queue<State> queue = createQueue();
        enqueueStart(source, startDir, costs, queue);
        int best = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            State cur = queue.poll();
            
            if (shouldSkipState(cur, best)) {
                continue;
            }
            
            if (isDestinationReached(cur, dest)) {
                best = Math.min(best, cur.getPowerSpent());
                continue;
            }
            
            exploreNeighbors(cur, costs, queue, dest);
        }
        
        return best;
    }
    
    protected abstract Queue<State> createQueue();
    
    protected boolean shouldSkipState(State cur, int best) {
        return cur.getPowerSpent() >= best;
    }
    
    protected boolean isDestinationReached(State cur, Position dest) {
        return cur.getX() == dest.getX() && cur.getY() == dest.getY();
    }
    
    protected void exploreNeighbors(State cur, int[][][] costs, Queue<State> queue, Position dest) {
        moveForward(cur, costs, queue, dest);
        turnLeft(cur, costs, queue, dest);
        turnRight(cur, costs, queue, dest);
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
        Position nextPos = calculateNextPosition(cur);
        
        if (!isValidMove(nextPos)) {
            return;
        }
        
        int newCost = cur.getPowerSpent() + moveCost;
        
        if (!shouldUpdateCost(nextPos, cur.getDirection(), newCost, costs)) {
            return;
        }
        
        updateCostAndEnqueue(nextPos, cur.getDirection(), newCost, costs, q);
    }
    
    private Position calculateNextPosition(State cur) {
        int nx = cur.getX() + cur.getDirection().dx();
        int ny = cur.getY() + cur.getDirection().dy();
        return new Position(nx, ny);
    }
    
    private boolean isValidMove(Position pos) {
        return board.inBounds(pos);
    }
    
    private boolean shouldUpdateCost(Position pos, Direction dir, int newCost, int[][][] costs) {
        int dirIdx = dir.ordinal();
        return newCost < costs[pos.getX()][pos.getY()][dirIdx];
    }
    
    private void updateCostAndEnqueue(Position pos, Direction dir, int cost, int[][][] costs, Queue<State> q) {
        int dirIdx = dir.ordinal();
        costs[pos.getX()][pos.getY()][dirIdx] = cost;
        q.add(new State(pos.getX(), pos.getY(), dir, cost));
    }

    protected void turnLeft(State cur, int[][][] costs, Queue<State> q, Position dest) {
        performTurn(cur, cur.getDirection().left(), costs, q);
    }

    protected void turnRight(State cur, int[][][] costs, Queue<State> q, Position dest) {
        performTurn(cur, cur.getDirection().right(), costs, q);
    }
    
    private void performTurn(State cur, Direction newDirection, int[][][] costs, Queue<State> q) {
        int turnCostValue = cur.getPowerSpent() + turnCost;
        Position currentPos = new Position(cur.getX(), cur.getY());
        
        if (!shouldUpdateCost(currentPos, newDirection, turnCostValue, costs)) {
            return;
        }
        
        updateCostAndEnqueue(currentPos, newDirection, turnCostValue, costs, q);
    }
}
