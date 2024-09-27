package com.example.IncidentManagementSystem.Project.Exception;

public class IncidentNotFoundException extends RuntimeException{
    public IncidentNotFoundException(String message)
    {
        super(message);
    }
    public IncidentNotFoundException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
