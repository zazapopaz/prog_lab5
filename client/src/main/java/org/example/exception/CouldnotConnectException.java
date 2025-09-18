package org.example.exception;

/**
 * Исключение для ошибок подключения.
 */
public class CouldnotConnectException extends Exception {
    public CouldnotConnectException() { super(); }
    public CouldnotConnectException(String message) { super(message); }
    public CouldnotConnectException(String message, Throwable cause) { super(message, cause); }
}
