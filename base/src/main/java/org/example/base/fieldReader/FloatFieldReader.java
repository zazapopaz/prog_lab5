package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

/**
 * Ридер значений float.
 */
public class FloatFieldReader extends BaseFieldReader {
    public FloatFieldReader(IOManager io) { super(io); }

    public float readPositive(String prompt) {
        while (true) {
            try {
                float v = Float.parseFloat(ask(prompt));
                if (v <= 0) throw new IllegalArgumentException();
                return v;
            } catch (Exception e) {
                error("Ожидалось число Float > 0");
            }
        }
    }
}
