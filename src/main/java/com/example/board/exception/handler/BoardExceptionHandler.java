package com.example.board.exception.handler;

import com.example.board.exception.NotExistBoardException;
import com.example.board.exception.NotExistMemberException;
import com.example.board.exception.NotExistPageException;
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
}
