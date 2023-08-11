package com.example.board.exception;

public class NotExistBoardException extends RuntimeException{

    public NotExistBoardException(String message) {
        super(message);
    }
}
