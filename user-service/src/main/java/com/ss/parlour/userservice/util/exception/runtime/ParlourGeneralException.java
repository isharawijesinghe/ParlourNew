package com.ss.parlour.userservice.util.exception.runtime;

public class ParlourGeneralException extends RuntimeException {
    public ParlourGeneralException(){
        super();
    }

    public ParlourGeneralException(String message,Exception e){
        super(message,e);
    }

    public ParlourGeneralException(Exception e){
        super(e.getMessage(),e);
    }

    public ParlourGeneralException(String message){
        super(message);
    }
}
