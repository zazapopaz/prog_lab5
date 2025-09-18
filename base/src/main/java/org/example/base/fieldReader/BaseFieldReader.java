package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

/**
 * Базовый класс для ридеров полей.
 */
public abstract class BaseFieldReader {
    protected final IOManager io;

    protected BaseFieldReader(IOManager io) {
        this.io = io;
    }

    protected String ask(String prompt) {
        io.prompt(prompt);
        return io.readLine();
    }

    protected void error(String message) {
        io.writeError(message);
    }

    protected void info(String message) {
        io.writeLine(message);
    }
}
