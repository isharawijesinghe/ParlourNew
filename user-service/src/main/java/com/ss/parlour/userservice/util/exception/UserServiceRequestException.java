package com.ss.parlour.userservice.util.exception;

public class UserServiceRequestException extends RuntimeException{

    public UserServiceRequestException(String message){
        super(message);
    }

    public UserServiceRequestException(String message, Throwable ex){
        super(message, ex);
    }


}
