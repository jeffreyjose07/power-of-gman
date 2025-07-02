package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Comparator;
import java.util.PriorityQueue;


public class DijkstraPathFinder extends AbstractPathFinder {

    public DijkstraPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
    }

    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int[][][] minCost = newCostArray();
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getPowerSpent));
        enqueueStart(source, startDir, minCost, pq);
        int best = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.getPowerSpent() >= best) {
                continue;
            }
            if (cur.getX() == dest.getX() && cur.getY() == dest.getY()) {
                best = Math.min(best, cur.getPowerSpent());
                continue;
            }
            moveForward(cur, minCost, pq, dest);
            turnLeft(cur, minCost, pq, dest);
            turnRight(cur, minCost, pq, dest);
        }
        return best;
    }
}
