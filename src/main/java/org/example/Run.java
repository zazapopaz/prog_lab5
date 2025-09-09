package org.example;

import org.example.exceptions.DeserializationException;
import org.example.exceptions.IdAlreadyExistsException;
import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.manager.CommandManagerSetuper;
import org.example.manager.iomanager.IOManager;
import org.example.manager.iomanager.StandartIOManager;
import org.example.models.Movie;
import org.example.utils.MovieValidator;

import java.io.IOException;
import java.util.Collection;

/**
 * Основной класс приложения.
 */
public class Run {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final IOManager ioManager;
    private final String filepath;

    public Run(String filepath) {
        this.collectionManager = new CollectionManager();
        this.commandManager = new CommandManager(new org.example.manager.CommandHistoryManager());
        this.filepath = filepath;
        this.ioManager = new StandartIOManager();
        setup();
    }

    private void setup() {
        uploadCollection();
        ioManager.writeLine("Коллекция успешно загружена!");
        CommandManagerSetuper.SetupCommandManager(this.ioManager, this.collectionManager, this.commandManager, new org.example.file.FileManager(filepath));
    }

    private void uploadCollection() {
        try {
            var xmlManager = new org.example.file.FileManager(filepath);
            Collection<Movie> collection = xmlManager.readAll();
            for (var mv : collection) {
                String error = MovieValidator.validate(mv);
                if (error != null) {
                    ioManager.writeError("Пропуск некорректного элемента id=" + mv.getId() + ": " + error);
                    continue;
                }
                // Нормализуем авто-поля
                if (mv.getCreationDate() == null) {
                    mv.setCreationDate(java.time.ZonedDateTime.now());
                }
                // id: если отсутствует/<=0/дубликат, сгенерируем новый
                if (mv.getId() == null || mv.getId() <= 0 || collectionManager.findById(mv.getId()).isPresent()) {
                    mv.setId(null); // будет присвоен при addFromUser
                    try {
                        collectionManager.addFromUser(mv);
                    } catch (IdAlreadyExistsException e) {
                        ioManager.writeError("Ошибка при добавлении элемента: " + e.getMessage());
                    }
                } else {
                    collectionManager.addLoaded(mv);
                }
            }
        } catch (IOException e) {
            ioManager.writeError("Не удалось загрузить файл: " + e.getMessage());
        } catch (DeserializationException e) {
            ioManager.writeError("Ошибка парсинга XML файла: " + e.getMessage());
        }
    }

    public void run() {
        while (true) {
            ioManager.prompt("> ");
            String line = ioManager.readLine();
            boolean cont = commandManager.executeLine(line, ioManager);
            if (!cont) break;
        }
    }
}
