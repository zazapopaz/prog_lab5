package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.manager.CollectionManager;

/**
 * clear: очистить коллекцию.
 */
public class ClearCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 0) {
            throw new CommandArgumentException("Команда clear не принимает аргументы");
        }

        int size = collectionManager.size();
        collectionManager.clear();
        return "Коллекция очищена. Удалено элементов: " + size;
    }
}
