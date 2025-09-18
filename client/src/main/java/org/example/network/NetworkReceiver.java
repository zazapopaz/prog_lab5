package org.example.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeoutException;

public class NetworkReceiver {
    public static ByteBuffer receiveBuffer(DatagramChannel channel, int bufferSize, int timeout)
            throws IOException, InterruptedException, TimeoutException {

        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout * 1000L) {
            InetSocketAddress sender = (InetSocketAddress) channel.receive(buffer);
            if (sender != null) {
                buffer.flip();
                return buffer;
            }
            Thread.sleep(100);
        }

        throw new TimeoutException("Таймаут ожидания ответа от сервера");
    }
}