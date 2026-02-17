package com.woodstock.app.utils.exception;

public class ReEnteredPasswordNotEqual extends RuntimeException {
    public ReEnteredPasswordNotEqual(String message) {
        super(message);
    }
}
