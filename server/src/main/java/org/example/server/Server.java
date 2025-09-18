package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.ServerCommandManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Server - класс, в котором происходит вся серверная логика.
 */
public class Server {
    private final Logger logger = LogManager.getRootLogger();
    private final ServerCommandManager commandManager;
    private final int port;
    private final Consumer<Void> saveCollectionProcedure;
    private final Selector selector;

    public Server(ServerCommandManager commandManager, int port, Consumer<Void> saveCollectionProcedure) throws IOException {
        this.commandManager = commandManager;
        this.port = port;
        this.saveCollectionProcedure = saveCollectionProcedure;
        this.selector = Selector.open();

        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress("0.0.0.0", this.port));
        channel.configureBlocking(false);

        channel.register(selector, SelectionKey.OP_READ);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveCollectionProcedure.accept(null);
            logger.info("Коллекция автоматически сохранена при завершении работы сервера");
        }));
    }


    public void cycle() throws IOException {
        while (true) {
            selector.select(100);

            if(System.in.available() > 0) {
                executeConsoleCommand();
            }

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {

                if(System.in.available() > 0) {
                    executeConsoleCommand();
                }

                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isReadable()) {
                    try {
                        DatagramChannel channel = (DatagramChannel) key.channel();

                        ClientRequestWithIP request = getClientRequest(channel);
                        if(request.clientResponse() == null) {
                            logger.error(String.format("Пришел запрос от клиента %s, который не удалось обработать", request.ip()));
                            send(channel, request.ip(), ServerResponseType.ERROR, "Не удалось разобрать запрос");
                            continue;
                        }

                        ClientCommandRequest commandRequest = request.clientResponse();

                        logger.info(String.format("Пришло сообщения от %s:\n%s", request.ip(), commandRequest));

                        var serverResponse = commandManager.execute(commandRequest.getCommandName(), commandRequest.getArguments());

                        if(serverResponse.getType() == ServerResponseType.SUCCESS) {
                            logger.info("Сообщение обработано успешно: " + serverResponse);
                        }
                        else {
                            logger.warn("Во время обработки сообщении произошла ошибка: " + serverResponse);
                        }

                        try {
                            send(channel, request.ip(), serverResponse);
                        } catch (IOException e) {
                            logger.error("Ошибка при отправке сообщения: " + e.getMessage());
                        }
                    } catch (Exception e) {
                        logger.error("Критическая ошибка обработки запроса: " + e.getMessage(), e);
                        // Сервер продолжает работу после ошибки
                    }
                }
            }
        }
    }

    private void executeConsoleCommand() {
        try {
            byte[] buffer = new byte[1024];

            int bytesRead = System.in.read(buffer);

            if (bytesRead <= 0) {
                logger.warn("Неверный ввод");
                return;
            }

            String input = new String(buffer, 0, bytesRead).trim();
            logger.info("Получено: " + input);

            if(!input.equalsIgnoreCase("save")) {
                logger.error("Неверный ввод" );
            }

            logger.info("Получена команда о сохранении коллекции");

            try {
                saveCollectionProcedure.accept(null);
            }
            catch (Exception e) {
                logger.error("Не удалось сохранить коллекцию");
                logger.error(e.getMessage());
            }
            logger.info("Коллекция сохранена");
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private ClientRequestWithIP getClientRequest(DatagramChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8192);

        InetSocketAddress from = (InetSocketAddress) channel.receive(buffer);

        buffer.flip();

        ClientCommandRequest request;
        try {
            request = org.example.server.util.DeserializationUtil.deserializeClientRequest(buffer);
        } catch (ClassNotFoundException ex) {
            return new ClientRequestWithIP(null, from);
        }

        return new ClientRequestWithIP(request, from);
    }

    public void send(DatagramChannel channel, InetSocketAddress toSend, ServerResponseType responseType, String responeMessage) throws IOException {
        ServerResponse response = new ServerResponse(responseType, responeMessage);
        send(channel, toSend, response);
    }


    public void send(DatagramChannel channel, InetSocketAddress toSend, ServerResponse response) throws IOException {
        var responseSerialized = org.example.server.util.SerializationUtil.serializeServerResponse(response);
        channel.send(responseSerialized, toSend);
        logger.info("Сообщение отправлено " + toSend);
    }
}