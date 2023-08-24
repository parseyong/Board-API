package com.example.board.exception;

public class InvalidSaveFileException extends RuntimeException{

    public InvalidSaveFileException(String message) {
        super(message);
    }

}
