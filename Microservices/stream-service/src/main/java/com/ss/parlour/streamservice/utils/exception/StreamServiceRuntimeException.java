package com.ss.parlour.streamservice.utils.exception;

public class StreamServiceRuntimeException extends RuntimeException {

    public StreamServiceRuntimeException(Exception e, String message){
        super(message,e);
    }

    public StreamServiceRuntimeException(String message){
        super(message);
    }

    public StreamServiceRuntimeException(String message, Throwable ex){
        super(message, ex);
    }
}
