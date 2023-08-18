package com.example.board.exception;

public class PasswordIsNotMatchException extends RuntimeException{

    public PasswordIsNotMatchException(String message) {
        super(message);
    }
}