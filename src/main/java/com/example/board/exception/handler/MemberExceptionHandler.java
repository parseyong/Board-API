package com.example.board.exception.handler;

import com.example.board.exception.DuplicatedEmailException;
import com.example.board.exception.NotExistMemberException;
import com.example.board.exception.PasswordIsNotMatchException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ErrorMsg> duplicatedEmail(DuplicatedEmailException ex){
        return ResponseEntity.badRequest().body(new ErrorMsg(ex.getMessage()));
    }

    @ExceptionHandler(NotExistMemberException.class)
    public ResponseEntity<ErrorMsg> notExistMember(NotExistMemberException ex){
        return ResponseEntity.status(404).body(new ErrorMsg(ex.getMessage()));
    }

    @ExceptionHandler(PasswordIsNotMatchException.class)
    public ResponseEntity<ErrorMsg> passwordException(PasswordIsNotMatchException ex){
        return ResponseEntity.badRequest().body(new ErrorMsg(ex.getMessage()));
    }
}



