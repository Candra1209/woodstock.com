package com.woodstock.app.utils.exception.global;

public class InvalidQueryParameterException extends RuntimeException{

    public InvalidQueryParameterException(String message) {
        super(message);
    }
}
