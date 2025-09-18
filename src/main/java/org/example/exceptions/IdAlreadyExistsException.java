package org.example.exceptions;

/**
 * Исключение для дублирования ID.
 */
public class IdAlreadyExistsException extends Exception {
    public IdAlreadyExistsException() { super(); }
    public IdAlreadyExistsException(String message) { super(message); }
}


