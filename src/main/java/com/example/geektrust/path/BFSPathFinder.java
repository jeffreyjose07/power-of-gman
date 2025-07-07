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
    protected Queue<State> createQueue() {
        return new LinkedList<>();
    }
}
