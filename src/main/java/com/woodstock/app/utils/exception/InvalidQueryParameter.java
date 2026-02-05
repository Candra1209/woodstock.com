package com.woodstock.app.utils.exception;

public class InvalidQueryParameter extends RuntimeException{

    public InvalidQueryParameter(String message) {
        super(message);
    }
}
