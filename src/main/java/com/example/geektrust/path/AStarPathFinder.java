package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;


public class AStarPathFinder extends AbstractPathFinder {

    public AStarPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
    }

    private int heuristic(Position p, Position dest) {
        int dx = Math.abs(p.getX() - dest.getX());
        int dy = Math.abs(p.getY() - dest.getY());
        return (dx + dy) * Math.min(costCalculator.getMoveCost(), costCalculator.getTurnCost());
    }

    @Override
    protected int computePriority(Position pos, int costSoFar, Position dest) {
        return costSoFar + heuristic(pos, dest);
    }

    @Override
    protected Queue<State> createQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(State::getPowerSpent));
    }
}
