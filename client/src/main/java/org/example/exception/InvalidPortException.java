package org.example.exception;

/**
 * Исключение для неверного порта.
 */
public class InvalidPortException extends Exception {
    public InvalidPortException() { super(); }
    public InvalidPortException(String message) { super(message); }
    public InvalidPortException(String message, Throwable cause) { super(message, cause); }
}
