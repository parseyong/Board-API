package com.example.board.exception.handler;

import com.example.board.exception.AuthenticationNumIsNotMatchException;
import com.example.board.exception.DuplicatedEmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoolSMSExceptionHandler {

    @ExceptionHandler(AuthenticationNumIsNotMatchException.class)
    public ResponseEntity<ErrorMsg> authenticationNumIsNotMatch(AuthenticationNumIsNotMatchException ex){
        return ResponseEntity.badRequest().body(new ErrorMsg(ex.getMessage()));
    }
}
