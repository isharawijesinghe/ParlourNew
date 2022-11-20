package com.ss.parlour.authorizationservice.util.exception;

public class AuthorizationRuntimeException extends RuntimeException{

    public AuthorizationRuntimeException(String message){
        super(message);
    }

    public AuthorizationRuntimeException(String message, Throwable ex){
        super(message, ex);
    }


}
