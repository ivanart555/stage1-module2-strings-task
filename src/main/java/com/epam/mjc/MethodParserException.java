package com.epam.mjc;

public class MethodParserException extends RuntimeException {

    public MethodParserException(String message) {
        super(message);
    }

    public MethodParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
