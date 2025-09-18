package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;
import org.example.base.model.Location;
import org.example.base.model.Person;

import java.time.ZonedDateTime;

/**
 * Ридер для объектов Person.
 */
public class PersonFieldReader extends BaseFieldReader {
    public PersonFieldReader(IOManager io) { super(io); }

    public Person read() {
        StringFieldReader sf = new StringFieldReader(io);
        String name = sf.readNonEmpty("Введите имя оператора: ");

        ZonedDateTimeFieldReader zf = new ZonedDateTimeFieldReader(io);
        ZonedDateTime birthday = zf.readISO("Введите дату рождения (ISO-8601, напр. 2000-01-01T00:00:00+03:00[Europe/Moscow]): ");

        FloatFieldReader ff = new FloatFieldReader(io);
        float height = ff.readPositive("Введите рост (Float > 0): ");

        IntFieldReader intReader = new IntFieldReader(io);
        int weight;
        while (true) {
            weight = intReader.readInt("Введите вес (int > 0): ");
            if (weight > 0) break;
            error("Вес должен быть > 0");
        }

        LocationFieldReader lf = new LocationFieldReader(io);
        Location location = lf.readNullable();

        return new Person(name, birthday, height, weight, location);
    }
}
