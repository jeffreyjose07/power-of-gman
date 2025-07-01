package com.example.geektrust;

public class Main {
    private static final int ERROR_NO_INPUT = 1;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No input file provided.");
            System.err.println("Usage: java -jar <jar-file> <input-file>");
            System.exit(ERROR_NO_INPUT);
        }

        new CommandProcessor().run(args[0]);
    }
}
