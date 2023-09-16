package com.example.voltage.clientimtation.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<Object> handleAllExceptions(Exception e ){
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException errorDetails = new ApiException(e.getMessage(), internalServerError,LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, internalServerError);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        // 1. create payload
        ApiException apiException = new ApiException(e.getMessage(), notFound, LocalDateTime.now());
        // 2. return response body
        return new ResponseEntity<>(apiException, notFound);

    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(e.getMessage(), conflict, LocalDateTime.now());
        return  new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler(value = {RoleNotFoundException.class})
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(e.getMessage(), notFound, LocalDateTime.now());
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = {FormatNotValidException.class})
    public ResponseEntity<Object> handleFormatNotValidException(FormatNotValidException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), badRequest, LocalDateTime.now());
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException e){
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(e.getMessage(), conflict, LocalDateTime.now());
        return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler(value = {CustomAuthenticationException.class})
    public ResponseEntity<Object> handleCustomAuthenticationException(CustomAuthenticationException e){
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(e.getMessage(), forbidden, LocalDateTime.now());
        return new ResponseEntity<>(apiException, forbidden);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String , String >> handleMethodArgumentException(MethodArgumentNotValidException ex){
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return  new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public  ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e ){
        ApiException errorDetails = new ApiException(e.getMessage(),HttpStatus.FORBIDDEN,LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations().stream().map(violation->violation.getPropertyPath()+ ": "+violation.getMessage()).toList();
        ApiException errorDetails = new ApiException(errors.toString(),HttpStatus.BAD_REQUEST ,LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler( value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleInvalidCredentials (BadCredentialsException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException errorDetails = new ApiException (e.getMessage(), badRequest,LocalDateTime.now());
        return  new ResponseEntity<>(errorDetails, badRequest);
    }

}



