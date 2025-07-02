package com.example.geektrust.path;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;

public interface PathFinder {
    int findMinPower(Position source, Position destination, Direction direction);
}
