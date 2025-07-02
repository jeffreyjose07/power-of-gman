package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple breadth-first search over (x,y,direction) states.
 * Costs are multiples of 5 so the queue processes states in
 * increasing cost units.
 */
public class BFSPathFinder extends AbstractPathFinder {

    public BFSPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
    }

    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int size = board.getSize();
        int dirCount = Direction.values().length;
        int[][][] minCost = new int[size][size][dirCount];
        for (int[][] arr2d : minCost) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        Queue<State> q = new LinkedList<>();
        q.add(new State(source, startDir, 0));
        minCost[source.getX()][source.getY()][startDir.ordinal()] = 0;
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
            // move forward
            moveForward(cur, minCost, q, dest);
            // turn left
            turnLeft(cur, minCost, q, dest);
            // turn right
            turnRight(cur, minCost, q, dest);
        }
        return best;
    }
}
