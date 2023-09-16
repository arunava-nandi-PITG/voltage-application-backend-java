package com.example.voltage.clientimtation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;



//    public ErrorDetails(List<ObjectError> allErrors, String error) {
//        this.message = error;
//        String temp = allErrors.stream().map(e -> {
//            if (e instanceof FieldError) {
//                return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            } else {
//                return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            }
//        }).collect(Collectors.joining(","));
//        this.message = "[" + temp + "]";
//    }

}

