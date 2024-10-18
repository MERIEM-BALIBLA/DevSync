package org.example.util;

public class ExeptionHandler extends RuntimeException{
    public ExeptionHandler(String message) {
        super(message);
    }
    public ExeptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
