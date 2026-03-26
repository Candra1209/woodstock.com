package com.woodstock.app.utils.exception.jobs;

public class ForbidenJobRoleAccessException extends RuntimeException {
    public ForbidenJobRoleAccessException(String message) {
        super(message);
    }
}
