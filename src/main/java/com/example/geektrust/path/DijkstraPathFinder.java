package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;


public class DijkstraPathFinder extends AbstractPathFinder {

    public DijkstraPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
    }

    @Override
    protected Queue<State> createQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(State::getPowerSpent));
    }
}
