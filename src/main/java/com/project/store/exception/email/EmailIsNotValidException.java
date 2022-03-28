package com.project.store.exception.email;

public class EmailIsNotValidException extends RuntimeException{
    public EmailIsNotValidException(String message) {
        super(message);
    }
}
