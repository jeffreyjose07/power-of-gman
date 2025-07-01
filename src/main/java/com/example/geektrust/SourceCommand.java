package com.example.geektrust;

public class SourceCommand implements Command {
    private final int x;
    private final int y;
    private final Direction direction;

    public SourceCommand(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void execute(CommandContext context) {
        context.setSource(x, y, direction);
    }
}
