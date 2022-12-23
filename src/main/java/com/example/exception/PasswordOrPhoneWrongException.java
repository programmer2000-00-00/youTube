package com.example.exception;

public class PasswordOrPhoneWrongException extends RuntimeException{
    public PasswordOrPhoneWrongException(String message) {
        super(message);
    }
}