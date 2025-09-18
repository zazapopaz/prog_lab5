package org.example.exception;

/**
 * Исключение для ошибок сериализации.
 */
public class SerializationException extends Exception {
    public SerializationException() { super(); }
    public SerializationException(String message) { super(message); }
    public SerializationException(String message, Throwable cause) { super(message, cause); }
}
