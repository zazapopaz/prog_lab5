package org.example.fieldReader;

import org.example.manager.iomanager.IOManager;
import org.example.models.Coordinates;

/**
 * Ридер для объектов Coordinates.
 */
public class CoordinatesFieldReader extends BaseFieldReader {
    public CoordinatesFieldReader(IOManager io) { super(io); }

    public Coordinates read() {
        IntFieldReader intReader = new IntFieldReader(io);
        int x = intReader.readInt("Введите координату x (int): ");
        Long y;
        while (true) {
            try {
                String s = ask("Введите координату y:");
                y = Long.parseLong(s);
                if (y <= -803) throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                error("y должно быть числом > -803");
            }
        }
        return new Coordinates(x, y);
    }
}


