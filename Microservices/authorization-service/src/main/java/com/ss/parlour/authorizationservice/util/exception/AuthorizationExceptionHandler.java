package com.ss.parlour.authorizationservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AuthorizationExceptionHandler {

    @ExceptionHandler(AuthorizationRuntimeException.class)
    public ResponseEntity<Object> handleAuthorizationServiceException(AuthorizationRuntimeException ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        AuthorizationServiceErrorMessage userServiceException = new AuthorizationServiceErrorMessage(
                ex.getMessage(),
                ex,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(userServiceException, badRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCommonException(Exception ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        AuthorizationServiceErrorMessage userServiceException = new AuthorizationServiceErrorMessage(
                ex.getMessage(),
                ex,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(userServiceException, badRequest);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeCommonException(RuntimeException ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        AuthorizationServiceErrorMessage userServiceException = new AuthorizationServiceErrorMessage(
                ex.getMessage(),
                ex,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(userServiceException, badRequest);
    }


}
