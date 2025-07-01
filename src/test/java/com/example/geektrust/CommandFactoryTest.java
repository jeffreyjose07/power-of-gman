package com.example.geektrust;

import com.example.geektrust.command.Command;
import com.example.geektrust.command.CommandContext;
import com.example.geektrust.command.CommandFactory;
import com.example.geektrust.command.DestinationCommand;
import com.example.geektrust.command.PrintPowerCommand;
import com.example.geektrust.command.SourceCommand;
import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.PathFindingStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandFactoryTest {

    private static class StubPathFinder implements PathFindingStrategy {
        private final int power;
        StubPathFinder(int p) { power = p; }
        @Override
        public int calculatePower(Position source, Position destination, Direction direction) {
            return power;
        }
    }

    @Test
    public void testParseSourceCommand() {
        CommandFactory factory = new CommandFactory();
        Board board = new Board();
        Command cmd = factory.fromLine("SOURCE 1 2 N", board);
        assertTrue(cmd instanceof SourceCommand);
        CommandContext ctx = new CommandContext(new StubPathFinder(0));
        cmd.execute(ctx);
        assertEquals(1, ctx.getSourceX());
        assertEquals(2, ctx.getSourceY());
        assertEquals(Direction.N, ctx.getDirection());
    }

    @Test
    public void testParseDestinationCommand() {
        CommandFactory factory = new CommandFactory();
        Board board = new Board();
        Command cmd = factory.fromLine("DESTINATION 2 3", board);
        assertTrue(cmd instanceof DestinationCommand);
        CommandContext ctx = new CommandContext(new StubPathFinder(0));
        cmd.execute(ctx);
        assertEquals(2, ctx.getDestX());
        assertEquals(3, ctx.getDestY());
    }

    @Test
    public void testParsePrintPowerCommand() {
        CommandFactory factory = new CommandFactory();
        Board board = new Board();
        Command cmd = factory.fromLine("PRINT_POWER", board);
        assertTrue(cmd instanceof PrintPowerCommand);
    }

    @Test
    public void testInvalidArgs() {
        CommandFactory factory = new CommandFactory();
        Board board = new Board();
        assertThrows(IllegalArgumentException.class,
                () -> factory.fromLine("SOURCE 1 N", board));
    }

    @Test
    public void testUnknownCommandReturnsNull() {
        CommandFactory factory = new CommandFactory();
        Board board = new Board();
        Command cmd = factory.fromLine("JUMP 1 1", board);
        assertNull(cmd);
    }
}
