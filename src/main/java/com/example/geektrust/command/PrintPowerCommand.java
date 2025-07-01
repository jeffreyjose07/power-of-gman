package com.example.geektrust.command;

import com.example.geektrust.model.Position;

public class PrintPowerCommand implements Command {
    @Override
    public void execute(CommandContext context) {
        Position src = new Position(context.getSourceX(), context.getSourceY());
        Position dst = new Position(context.getDestX(), context.getDestY());
        int power = context.getPathFinder().calculatePower(src, dst, context.getDirection());
        System.out.println("POWER " + power);
    }
}
