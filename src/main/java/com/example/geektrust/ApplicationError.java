package com.example.geektrust;

public enum ApplicationError {
    NO_INPUT(1),
    FILE_NOT_FOUND(2),
    IO_ERROR(3),
    UNEXPECTED_ERROR(4);

    private final int exitCode;

    ApplicationError(int exitCode) {
        this.exitCode = exitCode;
    }

    public int getExitCode() {
        return exitCode;
    }
}
