package com.ss.parlour.mainservice.exception;

public class MainServiceRuntimeException extends RuntimeException {
    public MainServiceRuntimeException(Exception e,String message){
        super(message,e);
    }

    public MainServiceRuntimeException(String message){
        super(message);
    }
}
