package com.example.newsservicesecurity.exception;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String errorMessage){ super(errorMessage);}
}
