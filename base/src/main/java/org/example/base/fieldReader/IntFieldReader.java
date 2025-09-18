package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

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
