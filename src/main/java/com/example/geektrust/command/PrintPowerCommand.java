package com.example.geektrust.command;

import com.example.geektrust.model.Position;

public class PrintPowerCommand implements Command {
    @Override
    public void execute(CommandContext context) {
        Position src = context.getSource();
        Position dst = context.getDestination();
        int power = context.getPathFinder().calculatePower(src, dst, context.getDirection());
        System.out.println("POWER " + power);
    }
}
