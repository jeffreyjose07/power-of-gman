package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SourceCommandTest {
    @Test
    public void testExecuteUpdatesContext() {
        CommandContext ctx = new CommandContext(new PathFindingStrategy() {
            @Override
            public int calculatePower(Position source, Position destination, Direction direction) {
                return 0;
            }
        });
        SourceCommand cmd = new SourceCommand(2, 3, Direction.S);
        cmd.execute(ctx);
        assertEquals(2, ctx.getSourceX());
        assertEquals(3, ctx.getSourceY());
        assertEquals(Direction.S, ctx.getDirection());
    }
}
