package com.woodstock.app.utils.exception;

public class AccountUserNotFound extends RuntimeException {
    public AccountUserNotFound(String message) {
        super(message);
    }
}
