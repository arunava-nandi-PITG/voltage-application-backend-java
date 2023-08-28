package com.example.voltage.clientimtation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FormatNotValidException extends  RuntimeException{

    public FormatNotValidException(String message){
        super(message);
    }
}
