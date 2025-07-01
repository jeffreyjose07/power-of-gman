package com.example.geektrust.command;

public class DestinationCommand implements Command {
    private final int x;
    private final int y;

    public DestinationCommand(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(CommandContext context) {
        context.setDestination(x, y);
    }
}
