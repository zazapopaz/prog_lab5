package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

/**
 * Ридер значений double.
 */
public class DoubleFieldReader extends BaseFieldReader {
    public DoubleFieldReader(IOManager io) { super(io); }

    public double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(ask(prompt));
            } catch (Exception e) {
                error("Ожидалось число double");
            }
        }
    }
}
