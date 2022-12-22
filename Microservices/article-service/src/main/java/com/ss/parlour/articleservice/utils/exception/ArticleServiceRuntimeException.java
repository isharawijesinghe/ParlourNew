package com.ss.parlour.articleservice.utils.exception;

public class ArticleServiceRuntimeException extends RuntimeException {

    public ArticleServiceRuntimeException(Exception e, String message){
        super(message,e);
    }

    public ArticleServiceRuntimeException(String message){
        super(message);
    }

    public ArticleServiceRuntimeException(String message, Throwable ex){
        super(message, ex);
    }
}
