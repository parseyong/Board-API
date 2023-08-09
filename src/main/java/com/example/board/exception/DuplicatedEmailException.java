package com.example.board.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicatedEmailException extends RuntimeException{

    public DuplicatedEmailException(String message) {
        super(message);
    }

}
