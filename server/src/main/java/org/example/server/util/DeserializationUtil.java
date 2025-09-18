package org.example.server.util;

import org.example.base.response.ClientCommandRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

/**
 * Утилиты для десериализации.
 */
public class DeserializationUtil {
    public static ClientCommandRequest deserializeClientRequest(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (ClientCommandRequest) ois.readObject();
    }
}
