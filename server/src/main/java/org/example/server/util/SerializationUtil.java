package org.example.server.util;

import org.example.base.response.ServerResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Утилиты для сериализации.
 */
public class SerializationUtil {
    public static ByteBuffer serializeServerResponse(ServerResponse response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(response);
        oos.close();
        
        byte[] data = baos.toByteArray();
        return ByteBuffer.wrap(data);
    }
}
