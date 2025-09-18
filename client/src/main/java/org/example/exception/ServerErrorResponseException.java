package org.example.exception;

/**
 * Исключение для ошибок ответа сервера.
 */
public class ServerErrorResponseException extends Exception {
    private final boolean shouldRetry;

    public ServerErrorResponseException(String message, boolean shouldRetry) {
        super(message);
        this.shouldRetry = shouldRetry;
    }

    public boolean shouldRetry() {
        return shouldRetry;
    }
}
