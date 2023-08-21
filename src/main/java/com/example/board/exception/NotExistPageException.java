package com.example.board.exception;

public class NotExistPageException extends RuntimeException{

    public NotExistPageException(String message) {
        super(message);
    }
}