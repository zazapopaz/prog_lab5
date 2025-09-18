package org.example.command;

import org.example.manager.CollectionManager;

/**
 * show: вывести все элементы коллекции.
 */
public class ShowCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }

    @Override
    public String execute(String[] arguments) {
        StringBuilder result = new StringBuilder();
        collectionManager.getAll().forEach(m -> result.append(m.toString()).append("\n"));
        return result.toString();
    }
}
