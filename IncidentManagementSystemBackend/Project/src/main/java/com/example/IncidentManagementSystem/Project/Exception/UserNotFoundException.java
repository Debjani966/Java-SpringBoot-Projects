package com.example.IncidentManagementSystem.Project.Exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause, HttpStatus notFound) {
        super(message, cause);
    }
}
