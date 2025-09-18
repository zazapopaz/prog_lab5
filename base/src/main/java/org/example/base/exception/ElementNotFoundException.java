package org.example.base.exception;

/**
 * Исключение для случая отсутствия элемента.
 */
public class ElementNotFoundException extends Exception {
    public ElementNotFoundException() { super(); }
    public ElementNotFoundException(String message) { super(message); }
}
