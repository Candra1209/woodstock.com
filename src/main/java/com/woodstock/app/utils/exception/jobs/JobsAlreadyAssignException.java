package com.woodstock.app.utils.exception.jobs;

public class JobsAlreadyAssignException extends RuntimeException {
    public JobsAlreadyAssignException(String message) {
        super(message);
    }
}
