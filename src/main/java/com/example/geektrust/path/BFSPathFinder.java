package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.LinkedList;
import java.util.Queue;


public class BFSPathFinder extends AbstractPathFinder {

    public BFSPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
    }

    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int[][][] minCost = newCostArray();
        Queue<State> q = new LinkedList<>();
        enqueueStart(source, startDir, minCost, q);
        int best = Integer.MAX_VALUE;

        while (!q.isEmpty()) {
            State cur = q.poll();
            if (cur.getX() == dest.getX() && cur.getY() == dest.getY()) {
                best = Math.min(best, cur.getPowerSpent());
                continue;
            }
            if (cur.getPowerSpent() >= best) {
                continue;
            }
            moveForward(cur, minCost, q, dest);
            turnLeft(cur, minCost, q, dest);
            turnRight(cur, minCost, q, dest);
        }
        return best;
    }
}
