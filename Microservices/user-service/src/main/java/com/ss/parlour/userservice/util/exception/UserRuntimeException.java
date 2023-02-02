package com.ss.parlour.userservice.util.exception;

public class UserRuntimeException extends RuntimeException{

    public UserRuntimeException(String message){
        super(message);
    }

    public UserRuntimeException(String message, Throwable ex){
        super(message, ex);
    }


}
