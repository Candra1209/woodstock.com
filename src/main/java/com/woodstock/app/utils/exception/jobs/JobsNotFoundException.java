package com.woodstock.app.utils.exception.jobs;

public class JobsNotFoundException extends RuntimeException {
    public JobsNotFoundException(String message) {
        super(message);
    }
}
