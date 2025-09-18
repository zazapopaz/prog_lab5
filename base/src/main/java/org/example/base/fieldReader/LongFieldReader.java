package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

/**
 * Ридер значений long.
 */
public class LongFieldReader extends BaseFieldReader {
    public LongFieldReader(IOManager io) { super(io); }

    public long readPositive(String prompt) {
        while (true) {
            try {
                long v = Long.parseLong(ask(prompt));
                if (v <= 0) throw new IllegalArgumentException();
                return v;
            } catch (Exception e) {
                error("Ожидалось число > 0");
            }
        }
    }
}
