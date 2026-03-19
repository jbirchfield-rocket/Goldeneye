package com.goldeneye.exception;

/**
 * 
 * @author scanalesR
 */
public class InvalidLocationException extends RuntimeException {
    public InvalidLocationException() {
    }

    public InvalidLocationException(String msg) {
        super(msg);
    }
}
