package org.example.fieldReader;

import org.example.manager.iomanager.IOManager;

/**
 * Ридер целочисленных значений.
 */
public class IntFieldReader extends BaseFieldReader {
    public IntFieldReader(IOManager io) { super(io); }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(ask(prompt));
            } catch (Exception e) {
                error("Ожидалось целое число");
            }
        }
    }
}


