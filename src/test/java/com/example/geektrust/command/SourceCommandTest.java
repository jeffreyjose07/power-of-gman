package com.example.geektrust.command;

import com.example.geektrust.command.CommandContext;
import com.example.geektrust.command.SourceCommand;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.PathFindingStrategy;
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
        assertEquals(new Position(2,3), ctx.getSource());
        assertEquals(Direction.S, ctx.getDirection());
    }
}
