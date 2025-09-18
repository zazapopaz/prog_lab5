package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.DeserializationException;
import org.example.base.exception.IdAlreadyExistsException;
import org.example.base.file.FileManager;
import org.example.base.model.Movie;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.ServerCommandManager;
import org.example.command.*;
import org.example.server.Server;
import org.example.base.utils.MovieValidator;

import java.io.IOException;
import java.util.Collection;

/**
 * Главный класс серверного приложения.
 */
public class Main {
    private static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Использование: java -jar [имя_файла].jar <имя_файла_коллекции> [порт]");
            return;
        }

        String filepath = args[0];
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 8080;
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(filepath);

        loadCollection(collectionManager, fileManager);

        collectionManager.recalculateNextId();

        CommandHistoryManager historyManager = new CommandHistoryManager();
        ServerCommandManager commandManager = new ServerCommandManager(historyManager);

        registerCommands(commandManager, collectionManager, fileManager);

        try {
            Server server = new Server(commandManager, port, (v) -> {
                try {
                    fileManager.saveAll(collectionManager.getAll());
                    logger.info("Коллекция сохранена в файл");
                } catch (IOException e) {
                    logger.error("Ошибка сохранения коллекции: " + e.getMessage());
                }
            });

            logger.info("Сервер запущен на порту " + port);
            server.cycle();
        } catch (IOException e) {
            logger.error("Ошибка запуска сервера: " + e.getMessage());
        }
    }

    private static void loadCollection(CollectionManager collectionManager, FileManager fileManager) {
        try {
            Collection<Movie> collection = fileManager.readAll();
            for (Movie movie : collection) {
                String error = MovieValidator.validate(movie);
                if (error != null) {
                    logger.warn("Пропуск некорректного элемента id=" + movie.getId() + ": " + error);
                    continue;
                }
                if (movie.getCreationDate() == null) {
                    movie.setCreationDate(java.time.ZonedDateTime.now());
                }
                if (movie.getId() == null || movie.getId() <= 0 || collectionManager.findById(movie.getId()).isPresent()) {
                    movie.setId(null);
                    try {
                        collectionManager.addFromUser(movie);
                    } catch (IdAlreadyExistsException e) {
                        logger.error("Ошибка при добавлении элемента: " + e.getMessage());
                    }
                } else {
                    collectionManager.addLoaded(movie);
                }
            }
            logger.info("Коллекция успешно загружена! Загружено элементов: " + collectionManager.size());
        } catch (IOException e) {
            logger.error("Не удалось загрузить файл: " + e.getMessage());
        } catch (DeserializationException e) {
            logger.error("Ошибка парсинга XML файла: " + e.getMessage());
        }
    }

    private static void registerCommands(ServerCommandManager commandManager, CollectionManager collectionManager, FileManager fileManager) {
        commandManager.register(new InfoCommand(collectionManager));
        commandManager.register(new ShowCommand(collectionManager));
        commandManager.register(new AddCommand(collectionManager));
        commandManager.register(new AddIfMinCommand(collectionManager));
        commandManager.register(new ClearCommand(collectionManager));
        commandManager.register(new HelpCommand(commandManager));
        commandManager.register(new HistoryCommand(commandManager.getHistoryManager()));
        commandManager.register(new MaxByCoordinatesCommand(collectionManager));
        commandManager.register(new MinByCreationTimeCommand(collectionManager));
        commandManager.register(new RemoveByIdCommand(collectionManager));
        commandManager.register(new RemoveGreaterCommand(collectionManager));
        commandManager.register(new UpdateCommand(collectionManager));
        commandManager.register(new AverageOfOscarsCountCommand(collectionManager));
        commandManager.register(new SaveCommand(collectionManager, fileManager));
    }
}