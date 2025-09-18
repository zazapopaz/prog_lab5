package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.iomanager.IOManager;
import org.example.base.iomanager.StandartIOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.command.*;
import org.example.exception.CouldnotConnectException;
import org.example.manager.ClientCommandManager;
import org.example.manager.CommandHistoryManager;
import org.example.network.NetworkClient;

/**
 * Главный класс клиентского приложения.
 */
public class Main {
    private static final Logger logger = LogManager.getRootLogger();
    private static NetworkClient networkClient;

    public static void main(String[] args) {
        try {
            networkClient = new NetworkClient("localhost", 8080);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    logger.info("Отправка команды сохранения перед выходом...");
                    ClientCommandRequest saveRequest = new ClientCommandRequest("save", new String[]{});
                    networkClient.sendUserCommand(saveRequest);

                    Thread.sleep(500);
                    logger.info("Сервер уведомлен о необходимости сохранения данных");
                } catch (Exception e) {
                    logger.error("Ошибка при отправке команды сохранения: " + e.getMessage());
                }
            }));

            CommandHistoryManager historyManager = new CommandHistoryManager();
            ClientCommandManager commandManager = new ClientCommandManager(historyManager, networkClient);

            registerCommands(commandManager);

            IOManager io = new StandartIOManager();

            runInteractiveMode(commandManager, io);

        } catch (CouldnotConnectException e) {
            logger.error("Не удалось подключиться к серверу: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Ошибка запуска клиента: " + e.getMessage());
        }
    }

    private static void registerCommands(ClientCommandManager commandManager) {
        commandManager.register(new InfoCommand(commandManager));
        commandManager.register(new ShowCommand(commandManager));
        commandManager.register(new AddCommand(commandManager));
        commandManager.register(new AddIfMinCommand(commandManager));
        commandManager.register(new ClearCommand(commandManager));
        commandManager.register(new ExecuteScriptCommand(commandManager));
        commandManager.register(new ExitCommand());
        commandManager.register(new HelpCommand(commandManager));
        commandManager.register(new HistoryCommand(commandManager));
        commandManager.register(new MaxByCoordinatesCommand(commandManager));
        commandManager.register(new MinByCreationTimeCommand(commandManager));
        commandManager.register(new RemoveByIdCommand(commandManager));
        commandManager.register(new RemoveGreaterCommand(commandManager));
        commandManager.register(new SaveCommand());
        commandManager.register(new UpdateCommand(commandManager));
        commandManager.register(new AverageOfOscarsCountCommand(commandManager));
    }

    private static void runInteractiveMode(ClientCommandManager commandManager, IOManager io) {
        io.writeLine("Клиент запущен. Введите команду или 'help' для справки.");

        while (true) {
            try {
                io.prompt("> ");
                String line = io.readLine();

                if (line == null) {
                    io.writeLine("Завершение программы...");
                    break;
                }

                boolean shouldContinue = commandManager.executeLine(line, io);
                if (!shouldContinue) {
                    break;
                }

            } catch (Exception e) {
                io.writeError("Ошибка: " + e.getMessage());
            }
        }
    }
}