package org.example.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.exception.CouldnotConnectException;
import org.example.exception.InvalidPortException;
import org.example.exception.ServerErrorResponseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeoutException;

public class NetworkClient {
    private static final Logger logger = LogManager.getLogger(NetworkClient.class);
    private final InetSocketAddress serverAddress;
    private final DatagramChannel channel;

    public NetworkClient(String ip, int port) throws CouldnotConnectException {
        this.serverAddress = new InetSocketAddress(ip, port);
        try {
            this.channel = ClientConnection.connect(this.serverAddress);
            logger.info("Подключено к серверу: " + ip + ":" + port);
        } catch (InvalidPortException e) {
            throw new CouldnotConnectException("Неверный порт: " + port);
        }
    }

    public void sendUserCommand(ClientCommandRequest clientCommandRequest) {
        NetworkSender.sendUserCommand(clientCommandRequest, channel, serverAddress);
    }

    public ServerResponse getServerResponse() throws ServerErrorResponseException {
        try {
            var buffer = NetworkReceiver.receiveBuffer(this.channel, 8192, 10);

            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream ois = new ObjectInputStream(bais);

            Object response = ois.readObject();
            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;

                if (serverResponse.getType() == ServerResponseType.ERROR) {
                    logger.warn("Ошибка от сервера: " + serverResponse.getMessage());
                    throw new ServerErrorResponseException(serverResponse.getMessage(), false);
                }

                logger.info("Получен ответ: " + serverResponse.getMessage());
                return serverResponse;
            } else {
                throw new ServerErrorResponseException("Неверный формат ответа", true);
            }

        } catch (IOException e) {
            logger.error("Сетевая ошибка: " + e.getMessage());
            throw new ServerErrorResponseException("Ошибка связи с сервером", true);
        } catch (ClassNotFoundException e) {
            logger.error("Ошибка десериализации: " + e.getMessage());
            throw new ServerErrorResponseException("Ошибка формата данных", true);
        } catch (TimeoutException e) {
            logger.error("Таймаут соединения");
            throw new ServerErrorResponseException("Сервер не отвечает", true);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerErrorResponseException("Операция прервана", true);
        }
    }
}