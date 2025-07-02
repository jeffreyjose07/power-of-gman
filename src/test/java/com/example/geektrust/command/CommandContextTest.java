package com.example.geektrust.command;

import com.example.geektrust.command.CommandContext;
import com.example.geektrust.service.PathFindingStrategy;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandContextTest {
    private static class StubPathFinder implements PathFindingStrategy {
        @Override
        public int calculatePower(Position source, Position destination, Direction direction) {
            return 42;
        }
    }

    @Test
    public void testDefaultsAndSetters() {
        StubPathFinder pf = new StubPathFinder();
        CommandContext ctx = new CommandContext(pf);
        // defaults
        assertEquals(new Position(0,0), ctx.getSource());
        assertEquals(Direction.N, ctx.getDirection());
        assertEquals(new Position(0,0), ctx.getDestination());
        assertSame(pf, ctx.getPathFinder());
        assertNotNull(ctx.getBoard());

        ctx.updateSource(new Position(1, 2), Direction.E);
        ctx.updateDestination(new Position(3, 4));
        assertEquals(new Position(1,2), ctx.getSource());
        assertEquals(Direction.E, ctx.getDirection());
        assertEquals(new Position(3,4), ctx.getDestination());
    }
}
