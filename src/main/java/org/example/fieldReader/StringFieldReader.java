package org.example.fieldReader;

import org.example.manager.iomanager.IOManager;

/**
 * Ридер строковых значений.
 */
public class StringFieldReader extends BaseFieldReader {
    public StringFieldReader(IOManager io) {
        super(io);
    }

    public String readNonEmpty(String prompt) {
        while (true) {
            String s = ask(prompt);
            if (s != null && !s.trim().isEmpty()) return s;
            error("Строка не может быть пустой");
        }
    }
}


