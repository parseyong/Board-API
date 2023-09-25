package com.example.board.exception;

public class AuthenticationNumIsNotMatchException extends RuntimeException{
    public AuthenticationNumIsNotMatchException(String message) {
        super(message);
    }
}
