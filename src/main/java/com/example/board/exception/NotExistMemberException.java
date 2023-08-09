package com.example.board.exception;

public class NotExistMemberException extends RuntimeException{

    public NotExistMemberException(String message) {
        super(message);
    }
}
