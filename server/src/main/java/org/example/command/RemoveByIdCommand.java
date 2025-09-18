package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.base.exception.ElementNotFoundException;
import org.example.manager.CollectionManager;

/**
 * remove_by_id: удалить элемент по ID.
 */
public class RemoveByIdCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент по ID";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 1) {
            throw new CommandArgumentException("Команда remove_by_id принимает 1 аргумент: ID");
        }

        try {
            int id = Integer.parseInt(arguments[0]);
            boolean removed = collectionManager.removeById(id);
            if (removed) {
                return "Элемент с ID " + id + " успешно удален";
            } else {
                throw new ElementNotFoundException("Элемент с ID " + id + " не найден");
            }
        } catch (NumberFormatException e) {
            throw new CommandArgumentException("ID должен быть числом");
        }
    }
}
