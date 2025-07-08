package com.example.geektrust.path;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

public class PathFinderCostCalculator {
    private final int moveCost;
    private final int turnCost;

    public PathFinderCostCalculator(int moveCost, int turnCost) {
        this.moveCost = moveCost;
        this.turnCost = turnCost;
    }

    public int calculateMoveCost(State currentState) {
        return currentState.getPowerSpent() + moveCost;
    }

    public int calculateTurnCost(State currentState) {
        return currentState.getPowerSpent() + turnCost;
    }

    public int computePriority(Position pos, int costSoFar, Position dest) {
        return costSoFar;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public int getTurnCost() {
        return turnCost;
    }
}