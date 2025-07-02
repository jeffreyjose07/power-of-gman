package com.example.geektrust.command;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class CommandFactory {
    private static final int SOURCE_ARGS_COUNT = 4;
    private static final int DEST_ARGS_COUNT = 3;
    private static final int PRINT_ARGS_COUNT = 1;

    @FunctionalInterface
    private interface CommandParser {
        Command parse(String[] parts, Board board);
    }

    private final Map<String, CommandParser> parsers = new HashMap<>();

    CommandFactory() {
        register("SOURCE", CommandFactory::parseSource);
        register("DESTINATION", CommandFactory::parseDestination);
        register("PRINT_POWER", CommandFactory::parsePrintPower);
    }

    void register(String name, CommandParser parser) {
        parsers.put(name, parser);
    }

    Command fromLine(String line, Board board) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.trim().split("\\s+");
        CommandParser parser = parsers.get(parts[0]);
        if (parser == null) {
            throw new IllegalArgumentException("Unknown command: " + parts[0]);
        }
        return parser.parse(parts, board);
    }

    private static Command parseSource(String[] parts, Board board) {
        validateLength(parts, SOURCE_ARGS_COUNT);
        int sX = parseInt(parts[1], "source X");
        int sY = parseInt(parts[2], "source Y");
        Direction dir = Direction.fromString(parts[3]);
        validateCoord(board, sX, sY);
        return new SourceCommand(sX, sY, dir);
    }

    private static Command parseDestination(String[] parts, Board board) {
        validateLength(parts, DEST_ARGS_COUNT);
        int dX = parseInt(parts[1], "destination X");
        int dY = parseInt(parts[2], "destination Y");
        validateCoord(board, dX, dY);
        return new DestinationCommand(dX, dY);
    }

    private static Command parsePrintPower(String[] parts, Board board) {
        validateLength(parts, PRINT_ARGS_COUNT);
        return new PrintPowerCommand();
    }

    private static void validateLength(String[] parts, int expected) {
        if (parts.length != expected) {
            throw new IllegalArgumentException(
                    String.format("Expected %d arguments but got %d", expected - 1, parts.length - 1));
        }
    }

    private static int parseInt(String str, String name) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + name + ": " + str);
        }
    }

    private static void validateCoord(Board board, int x, int y) {
        if (!board.inBounds(x, y)) {
            throw new IllegalArgumentException(
                    String.format("coordinate must be between 0 and %d", board.getSize() - 1));
        }
    }
}
