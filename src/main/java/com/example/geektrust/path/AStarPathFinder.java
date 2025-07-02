package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


public class AStarPathFinder extends AbstractPathFinder {

    public AStarPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
    }

    private int heuristic(Position p, Position dest) {
        int dx = Math.abs(p.getX() - dest.getX());
        int dy = Math.abs(p.getY() - dest.getY());
        return (dx + dy) * Math.min(moveCost, turnCost);
    }

    @Override
    protected int computePriority(Position pos, int costSoFar, Position dest) {
        return costSoFar + heuristic(pos, dest);
    }

    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int[][][] gScore = newCostArray();
        PriorityQueue<State> open = new PriorityQueue<>(Comparator.comparingInt(s -> s.getPowerSpent()));
        enqueueStart(source, startDir, gScore, open);
        int best = Integer.MAX_VALUE;

        while (!open.isEmpty()) {
            State cur = open.poll();
            if (cur.getX() == dest.getX() && cur.getY() == dest.getY()) {
                best = Math.min(best, cur.getPowerSpent());
                continue;
            }
            if (cur.getPowerSpent() >= best) {
                continue;
            }
            moveForward(cur, gScore, open, dest);
            turnLeft(cur, gScore, open, dest);
            turnRight(cur, gScore, open, dest);
        }
        return best;
    }
}
