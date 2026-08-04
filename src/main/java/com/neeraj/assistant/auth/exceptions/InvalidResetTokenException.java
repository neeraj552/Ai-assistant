package com.neeraj.assistant.auth.exceptions;

public class InvalidResetTokenException extends RuntimeException {

    public InvalidResetTokenException(String message) {
        super(message);
    }

}