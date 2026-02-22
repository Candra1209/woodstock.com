package com.woodstock.app.utils.exception.account;

public class AccountUserNotFoundException extends RuntimeException {
    public AccountUserNotFoundException(String message) {
        super(message);
    }
}
