package com.ss.parlour.mainservice.utils.exception;

public class MainServiceRuntimeException extends RuntimeException {

    public MainServiceRuntimeException(Exception e,String message){
        super(message,e);
    }

    public MainServiceRuntimeException(String message){
        super(message);
    }

    public MainServiceRuntimeException(String message, Throwable ex){
        super(message, ex);
    }
}
