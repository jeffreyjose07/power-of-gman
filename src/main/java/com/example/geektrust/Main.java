package com.example.geektrust;

import com.example.geektrust.command.CommandProcessor;

public class Main {
    private static final int ERROR_NO_INPUT = 1;
    private static final int ERROR_FILE_NOT_FOUND = 2;
    private static final int ERROR_IO = 3;
    private static final int ERROR_UNEXPECTED = 4;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No input file provided.");
            System.err.println("Usage: java -jar <jar-file> <input-file>");
            System.exit(ERROR_NO_INPUT);
        }

        try {
            new CommandProcessor().run(args[0]);
        } catch (java.io.FileNotFoundException e) {
            System.exit(ERROR_FILE_NOT_FOUND);
        } catch (java.io.IOException e) {
            System.exit(ERROR_IO);
        } catch (RuntimeException e) {
            System.exit(ERROR_UNEXPECTED);
        }
    }
}
