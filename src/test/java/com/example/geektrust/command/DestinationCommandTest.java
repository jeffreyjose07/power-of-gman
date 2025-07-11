package com.example.geektrust.command;

import com.example.geektrust.command.CommandContext;
import com.example.geektrust.command.DestinationCommand;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.PathFindingStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DestinationCommandTest {
    @Test
    public void testExecuteUpdatesContext() {
        CommandContext ctx = new CommandContext(new PathFindingStrategy() {
            @Override
            public int calculatePower(Position source, Position destination, Direction direction) {
                return 0;
            }
        });
        DestinationCommand cmd = new DestinationCommand(4, 5);
        cmd.execute(ctx);
        assertEquals(new Position(4,5), ctx.getDestination());
    }
}
