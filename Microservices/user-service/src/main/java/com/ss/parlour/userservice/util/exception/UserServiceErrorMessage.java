package com.ss.parlour.userservice.util.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class UserServiceErrorMessage {

    private final String message;
    private final String errorMessage;
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;

    public UserServiceErrorMessage(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime zonedDateTime){
        this.message = message;
        this.errorMessage = throwable.getMessage();
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getThrowable() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
