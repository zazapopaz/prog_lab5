package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;
import org.example.base.model.Location;

/**
 * Ридер для объектов Location.
 */
public class LocationFieldReader extends BaseFieldReader {
    public LocationFieldReader(IOManager io) { super(io); }

    public Location readNullable() {
        String skip = ask("Введите локацию. Нажмите Enter, чтобы пропустить: ");
        if (skip == null || skip.isEmpty()) return null;

        DoubleFieldReader dReader = new DoubleFieldReader(io);
        double y = dReader.readDouble("Введите location.y (double): ");

        Double lx;
        while (true) {
            try {
                lx = Double.parseDouble(ask("Введите location.x (Double): "));
                break;
            } catch (Exception e) {
                error("x должно быть числом Double");
            }
        }

        String name;
        while (true) {
            name = ask("Введите location.name: ");
            if (name != null && !name.isEmpty()) break;
            error("name не может быть пустым");
        }

        return new Location(lx, y, name);
    }
}
