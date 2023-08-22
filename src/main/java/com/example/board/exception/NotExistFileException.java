package com.example.board.exception;

public class NotExistFileException extends RuntimeException{
    public NotExistFileException(String message){
        super(message);
    }
}
