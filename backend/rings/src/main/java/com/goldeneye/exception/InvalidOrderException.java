package com.goldeneye.exception;

/**
 * 
 * @author scanalesR
 */
public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException() {
    }

    public InvalidOrderException(String msg) {
        super(msg);
    }
}
