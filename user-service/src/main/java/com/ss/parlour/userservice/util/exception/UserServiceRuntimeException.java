package com.ss.parlour.userservice.util.exception;

public class UserServiceRuntimeException extends RuntimeException{

    public UserServiceRuntimeException(String message){
        super(message);
    }

    public UserServiceRuntimeException(String message, Throwable ex){
        super(message, ex);
    }


}
