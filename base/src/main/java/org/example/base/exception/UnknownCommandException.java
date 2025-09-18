package org.example.base.exception;

/**
 * Исключение для неизвестной команды.
 */
public class UnknownCommandException extends Exception {
    public UnknownCommandException() { super(); }
    public UnknownCommandException(String message) { super(message); }
}
