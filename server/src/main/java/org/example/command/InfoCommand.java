package org.example.command;

import org.example.manager.CollectionManager;

import java.time.format.DateTimeFormatter;

/**
 * info: вывести информацию о коллекции (тип, дата инициализации, размер).
 */
public class InfoCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }

    @Override
    public String execute(String[] arguments) {
        StringBuilder result = new StringBuilder();
        result.append("Тип коллекции: ").append(collectionManager.getCollectionType()).append("\n");
        result.append("Дата инициализации: ").append(collectionManager.getInitializationTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)).append("\n");
        result.append("Количество элементов: ").append(collectionManager.size());
        return result.toString();
    }
}
