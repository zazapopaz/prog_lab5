package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

/**
 * Ридер значений Long с поддержкой null.
 */
public class LongWrapperFieldReader extends BaseFieldReader {
    public LongWrapperFieldReader(IOManager io) { super(io); }

    public Long readNullablePositive(String prompt) {
        while (true) {
            String s = ask(prompt);
            if (s == null || s.isEmpty()) return null;
            try {
                long v = Long.parseLong(s);
                if (v <= 0) throw new IllegalArgumentException();
                return v;
            } catch (Exception e) {
                error("Ожидалось число > 0");
            }
        }
    }
}
