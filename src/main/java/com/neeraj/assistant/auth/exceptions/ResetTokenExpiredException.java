package com.neeraj.assistant.auth.exceptions;

public class ResetTokenExpiredException extends RuntimeException {

    public ResetTokenExpiredException(String message) {
        super(message);
    }

}