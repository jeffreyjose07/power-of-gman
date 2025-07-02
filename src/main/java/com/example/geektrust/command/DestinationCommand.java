package com.example.geektrust.command;

import com.example.geektrust.model.Position;

public class DestinationCommand implements Command {
    private final Position position;

    public DestinationCommand(int x, int y) {
        this.position = new Position(x, y);
    }

    @Override
    public void execute(CommandContext context) {
        context.updateDestination(position);
    }
}
