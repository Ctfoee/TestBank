package com.example.test.exceptions;

public class WrongPinException extends RuntimeException{
    public WrongPinException() {
    }

    public WrongPinException(String message) {
        super(message);
    }
}
