package com.example.geektrust.command;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;

public class SourceCommand implements Command {
    private final Position position;
    private final Direction direction;

    public SourceCommand(int x, int y, Direction direction) {
        this.position = new Position(x, y);
        this.direction = direction;
    }

    @Override
    public void execute(CommandContext context) {
        context.updateSource(position, direction);
    }
}
