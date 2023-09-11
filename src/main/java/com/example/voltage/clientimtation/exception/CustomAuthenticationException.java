package com.example.voltage.clientimtation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException(String message){
        super(message);
    }
}
