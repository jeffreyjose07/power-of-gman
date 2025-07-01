package com.example.geektrust;

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
        assertEquals(0, ctx.getSourceX());
        assertEquals(0, ctx.getSourceY());
        assertEquals(Direction.N, ctx.getDirection());
        assertEquals(0, ctx.getDestX());
        assertEquals(0, ctx.getDestY());
        assertSame(pf, ctx.getPathFinder());
        assertNotNull(ctx.getBoard());

        ctx.setSource(1, 2, Direction.E);
        ctx.setDestination(3, 4);
        assertEquals(1, ctx.getSourceX());
        assertEquals(2, ctx.getSourceY());
        assertEquals(Direction.E, ctx.getDirection());
        assertEquals(3, ctx.getDestX());
        assertEquals(4, ctx.getDestY());
    }
}
