package org.example.network;

import org.example.exception.InvalidPortException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class ClientConnection {
    public static DatagramChannel connect(InetSocketAddress address) throws InvalidPortException {
        try {
            if (address.getPort() <= 0 || address.getPort() > 65535) {
                throw new InvalidPortException("Неверный порт: " + address.getPort());
            }

            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);

            return channel;

        } catch (IOException e) {
            throw new InvalidPortException("Ошибка подключения: " + e.getMessage());
        }
    }
}