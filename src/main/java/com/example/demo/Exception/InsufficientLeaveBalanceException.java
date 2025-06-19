package com.example.demo.Exception;

public class InsufficientLeaveBalanceException extends RuntimeException {
    public InsufficientLeaveBalanceException(String message) {
        super(message);
    }
    
    public InsufficientLeaveBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
