package com.project.store.exception.email;

public class EmailIsAlreadyTaken extends RuntimeException {
    public EmailIsAlreadyTaken(String message) {
        super(message);
    }
}
