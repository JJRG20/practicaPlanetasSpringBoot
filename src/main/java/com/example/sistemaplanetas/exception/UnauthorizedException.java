package com.example.sistemaplanetas.exception;

public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
}