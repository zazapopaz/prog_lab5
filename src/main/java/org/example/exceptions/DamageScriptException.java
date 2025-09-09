package org.example.exceptions;

/**
 * Исключение для ошибок поврежденного скрипта.
 */
public class DamageScriptException extends Exception {
    public DamageScriptException() { super(); }
    public DamageScriptException(String message) { super(message); }
}


