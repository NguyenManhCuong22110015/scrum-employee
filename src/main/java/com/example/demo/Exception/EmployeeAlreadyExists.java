package com.example.demo.Exception;

public class EmployeeAlreadyExists extends RuntimeException{
    public EmployeeAlreadyExists(String message) {
        super(message);
    }


}
