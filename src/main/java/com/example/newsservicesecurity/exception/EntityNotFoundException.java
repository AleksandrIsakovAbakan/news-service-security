package com.example.newsservicesecurity.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
