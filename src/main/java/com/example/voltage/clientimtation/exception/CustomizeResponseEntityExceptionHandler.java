package com.example.voltage.clientimtation.exception;

import com.example.voltage.clientimtation.dto.response.ErrorDetails;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex , WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<Object> handleAccessDeniedException(Exception ex , WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = ex.getConstraintViolations().stream().map(violation->violation.getPropertyPath()+ ": "+violation.getMessage()).toList();
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),errors.toString(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails,new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleInvalidCredentials (BadCredentialsException ex , WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(), req.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
