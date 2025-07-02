package com.example.geektrust.command;

import com.example.geektrust.service.PathFindingStrategy;
import com.example.geektrust.service.PowerCalculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class CommandProcessor {
    private final CommandContext context;
    private final CommandFactory factory = new CommandFactory();

    public CommandProcessor() {
        this(new PowerCalculator());
    }

    public CommandProcessor(PathFindingStrategy pathFinder) {
        this.context = new CommandContext(pathFinder);
    }

    public void process(String inputFile) throws IOException {
        processInputFile(inputFile);
    }

    private void processInputFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fis)) {
            while (scanner.hasNextLine()) {
                processCommand(scanner.nextLine().trim());
            }
        }
    }

    private void processCommand(String commandLine) {
        Command command = factory.fromLine(commandLine, context.getBoard());
        if (command != null) {
            command.execute(context);
        }
    }
}
