package org.example.base.exception;

/**
 * Исключение для ошибок десериализации.
 */
public class DeserializationException extends Exception {
    public DeserializationException() { super(); }
    public DeserializationException(String message) { super(message); }
    public DeserializationException(String message, Throwable cause) { super(message, cause); }
}
