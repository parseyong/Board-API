package com.example.board.exception.handler;

import com.example.board.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(NotExistBoardException.class)
    public ResponseEntity<ErrorMsg> notExistBoard(NotExistBoardException ex){
        return ResponseEntity.badRequest().body(new ErrorMsg(ex.getMessage()));
    }
    @ExceptionHandler(NotExistPageException.class)
    public ResponseEntity<ErrorMsg> notExistPage(NotExistPageException ex){
        return ResponseEntity.status(404).body(new ErrorMsg(ex.getMessage()));
    }
    @ExceptionHandler(InvalidBoardDeleteException.class)
    public ResponseEntity<ErrorMsg> canNotDeletePage(InvalidBoardDeleteException ex){
        return ResponseEntity.badRequest().body(new ErrorMsg(ex.getMessage()));
    }
    @ExceptionHandler(NotExistFileException.class)
    public ResponseEntity<ErrorMsg> notExistFile(NotExistFileException ex){
        return ResponseEntity.badRequest().body(new ErrorMsg(ex.getMessage()));
    }
}
