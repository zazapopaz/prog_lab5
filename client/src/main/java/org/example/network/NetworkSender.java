package org.example.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NetworkSender {
    private static final Logger logger = LogManager.getLogger(NetworkSender.class);

    public static void sendUserCommand(ClientCommandRequest clientCommandRequest, DatagramChannel channel, InetSocketAddress address) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(clientCommandRequest);
            objectOutputStream.flush();

            byte[] data = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(data);

            int bytesSent = channel.send(buffer, address);
            logger.info("Отправлено " + bytesSent + " байт команды: " + clientCommandRequest.getCommandName());

        } catch (IOException e) {
            logger.error("Ошибка отправки команды: " + e.getMessage());
        }
    }
}