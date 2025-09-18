package org.example.command;

import org.example.base.file.FileManager;
import org.example.manager.CollectionManager;

import java.io.IOException;

/**
 * save: сохранить коллекцию в файл (доступно только на сервере).
 */
public class SaveCommand implements ServerCommand {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл (только сервер)";
    }

    @Override
    public String execute(String[] arguments) {
        if (arguments.length != 0) {
            return "Команда save не принимает аргументы";
        }
        try {
            fileManager.saveAll(collectionManager.getAll());
            return "Коллекция сохранена";
        } catch (IOException e) {
            return "Ошибка сохранения: " + e.getMessage();
        }
    }
}


