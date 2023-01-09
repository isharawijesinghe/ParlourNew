package com.ss.parlour.notificationserver.utils.exception;

public class NotificationServiceRuntimeException extends RuntimeException {

    public NotificationServiceRuntimeException(Exception e, String message){
        super(message,e);
    }

    public NotificationServiceRuntimeException(String message){
        super(message);
    }

    public NotificationServiceRuntimeException(String message, Throwable ex){
        super(message, ex);
    }
}
