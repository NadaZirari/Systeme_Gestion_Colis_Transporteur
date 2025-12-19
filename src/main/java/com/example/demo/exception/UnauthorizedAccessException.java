package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedAccessException extends ResponseStatusException {
    
    public UnauthorizedAccessException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
    
    public UnauthorizedAccessException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, cause);
    }
}
