package org.example.exceptions;

/**
 * Исключение для ошибок аргументов команд.
 */
public class CommandArgumentException extends Exception {
    public CommandArgumentException() { super(); }
    public CommandArgumentException(String message) { super(message); }
    public CommandArgumentException(String message, Throwable cause) { super(message, cause); }
}


