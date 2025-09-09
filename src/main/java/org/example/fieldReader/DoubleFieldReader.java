package org.example.fieldReader;

import org.example.manager.iomanager.IOManager;

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


