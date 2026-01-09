package com.example.sistemaplanetas.exception;

public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
}
