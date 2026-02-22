package com.woodstock.app.utils.exception.auth;

public class ReEnteredPasswordNotEqualException extends RuntimeException {
    public ReEnteredPasswordNotEqualException(String message) {
        super(message);
    }
}
