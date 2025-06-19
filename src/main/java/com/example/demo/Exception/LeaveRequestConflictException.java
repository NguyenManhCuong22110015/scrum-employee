package com.example.demo.Exception;

public class LeaveRequestConflictException extends RuntimeException {
    public LeaveRequestConflictException(String message) {
        super(message);
    }
    
    public LeaveRequestConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
