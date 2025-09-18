package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

import java.time.ZonedDateTime;

/**
 * Ридер значений ZonedDateTime в формате ISO-8601.
 */
public class ZonedDateTimeFieldReader extends BaseFieldReader {
    public ZonedDateTimeFieldReader(IOManager io) { super(io); }

    public ZonedDateTime readISO(String prompt) {
        while (true) {
            try {
                return ZonedDateTime.parse(ask(prompt));
            } catch (Exception e) {
                error("Неверный формат даты, ожидается ISO-8601");
            }
        }
    }
}
