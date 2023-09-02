package com.example.voltage.clientimtation.exception;

import com.example.voltage.clientimtation.dto.response.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<Object> handleAllExceptions(Exception ex , WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler({AccessDeniedException.class})
    public  ResponseEntity<Object> handleAccessDeniedException(Exception ex , WebRequest request){
        return new ResponseEntity<Object>("Access denied" , new HttpHeaders(),HttpStatus.FORBIDDEN);
    }
}
