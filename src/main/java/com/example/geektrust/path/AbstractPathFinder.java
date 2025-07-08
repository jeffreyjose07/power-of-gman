package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Queue;


public abstract class AbstractPathFinder implements PathFinder {
    protected final Board board;
    protected final PathFinderStateManager stateManager;
    protected final PathFinderCostCalculator costCalculator;
    protected final PathFinderMoveValidator moveValidator;

    protected AbstractPathFinder(Board board, int moveCost, int turnCost) {
        this.board = board;
        this.stateManager = new PathFinderStateManager(board.getSize());
        this.costCalculator = new PathFinderCostCalculator(moveCost, turnCost);
        this.moveValidator = new PathFinderMoveValidator(board);
    }
    
    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int[][][] costs = stateManager.createCostArray();
        Queue<State> queue = createQueue();
        stateManager.initializeStartState(source, startDir, costs, queue);
        int best = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            State cur = queue.poll();
            
            if (moveValidator.shouldSkipState(cur, best)) {
                continue;
            }
            
            if (moveValidator.isDestinationReached(cur, dest)) {
                best = Math.min(best, cur.getPowerSpent());
                continue;
            }
            
            exploreNeighbors(cur, costs, queue, dest);
        }
        
        return best;
    }
    
    protected abstract Queue<State> createQueue();
    
    protected void exploreNeighbors(State cur, int[][][] costs, Queue<State> queue, Position dest) {
        moveForward(cur, costs, queue, dest);
        turnLeft(cur, costs, queue, dest);
        turnRight(cur, costs, queue, dest);
    }

    protected int computePriority(Position pos, int costSoFar, Position dest) {
        return costCalculator.computePriority(pos, costSoFar, dest);
    }

    protected void moveForward(State cur, int[][][] costs, Queue<State> q, Position dest) {
        Position nextPos = moveValidator.calculateNextPosition(cur);
        
        if (!moveValidator.isValidMove(nextPos)) {
            return;
        }
        
        int newCost = costCalculator.calculateMoveCost(cur);
        
        if (!stateManager.shouldUpdateCost(nextPos, cur.getDirection(), newCost, costs)) {
            return;
        }
        
        stateManager.updateCostAndEnqueue(nextPos, cur.getDirection(), newCost, costs, q);
    }

    protected void turnLeft(State cur, int[][][] costs, Queue<State> q, Position dest) {
        performTurn(cur, cur.getDirection().left(), costs, q);
    }

    protected void turnRight(State cur, int[][][] costs, Queue<State> q, Position dest) {
        performTurn(cur, cur.getDirection().right(), costs, q);
    }
    
    private void performTurn(State cur, Direction newDirection, int[][][] costs, Queue<State> q) {
        int turnCostValue = costCalculator.calculateTurnCost(cur);
        Position currentPos = new Position(cur.getX(), cur.getY());
        
        if (!stateManager.shouldUpdateCost(currentPos, newDirection, turnCostValue, costs)) {
            return;
        }
        
        stateManager.updateCostAndEnqueue(currentPos, newDirection, turnCostValue, costs, q);
    }
}
