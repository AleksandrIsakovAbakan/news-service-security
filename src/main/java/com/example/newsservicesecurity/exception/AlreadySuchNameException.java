package com.example.newsservicesecurity.exception;

public class AlreadySuchNameException extends RuntimeException{

    public AlreadySuchNameException(String errorMessage) {
        super(errorMessage);
    }
}
