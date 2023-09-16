package com.example.voltage.clientimtation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND)
public class EmailAlreadyExistsException extends  RuntimeException {
    public EmailAlreadyExistsException (String message){
        super(message);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
