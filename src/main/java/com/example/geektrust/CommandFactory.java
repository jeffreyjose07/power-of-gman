package com.example.geektrust;

class CommandFactory {
    private static final int SOURCE_ARGS_COUNT = 4;
    private static final int DEST_ARGS_COUNT = 3;
    private static final int PRINT_ARGS_COUNT = 1;

    Command fromLine(String line, Board board) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.trim().split("\\s+");
        switch (parts[0]) {
            case "SOURCE":
                validateLength(parts, SOURCE_ARGS_COUNT);
                int sX = parseInt(parts[1], "source X");
                int sY = parseInt(parts[2], "source Y");
                Direction dir = Direction.fromString(parts[3]);
                validateCoord(board, sX, sY);
                return new SourceCommand(sX, sY, dir);
            case "DESTINATION":
                validateLength(parts, DEST_ARGS_COUNT);
                int dX = parseInt(parts[1], "destination X");
                int dY = parseInt(parts[2], "destination Y");
                validateCoord(board, dX, dY);
                return new DestinationCommand(dX, dY);
            case "PRINT_POWER":
                validateLength(parts, PRINT_ARGS_COUNT);
                return new PrintPowerCommand();
            default:
                System.err.println("Warning: Unknown command: " + parts[0]);
                return null;
        }
    }

    private void validateLength(String[] parts, int expected) {
        if (parts.length != expected) {
            throw new IllegalArgumentException(
                    String.format("Expected %d arguments but got %d", expected - 1, parts.length - 1));
        }
    }

    private int parseInt(String str, String name) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + name + ": " + str);
        }
    }

    private void validateCoord(Board board, int x, int y) {
        if (!board.inBounds(x, y)) {
            throw new IllegalArgumentException(
                    String.format("coordinate must be between 0 and %d", board.getSize() - 1));
        }
    }
}
