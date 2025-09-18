package org.example.fieldReader;

import org.example.manager.iomanager.IOManager;

/**
 * Ридер значений перечислений.
 */
public class EnumFieldReader extends BaseFieldReader {
    public EnumFieldReader(IOManager io) { super(io); }

    public <E extends Enum<E>> E readEnum(String title, Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        StringBuilder sb = new StringBuilder();
        for (E v : values) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(v.name());
        }
        info(title + ":");
        info("Доступно: " + sb);
        while (true) {
            String token = ask("Введите одно из значений: ");
            for (E v : values) {
                if (v.name().equalsIgnoreCase(token)) {
                    return Enum.valueOf(enumClass, v.name());
                }
            }
            error("Неверное значение. Повторите ввод.");
        }
    }
}


