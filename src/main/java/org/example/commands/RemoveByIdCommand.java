package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;
import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.ElementNotFoundException;

/**
 * remove_by_id id: удалить элемент из коллекции по его id.
 */
public class RemoveByIdCommand extends UserCommand{
    public RemoveByIdCommand(CollectionManager cm, FileManager xml) {
        super("remove_by_id", "удалить элемент по id", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) throws CommandArgumentException, ElementNotFoundException {
        if (args == null || args.isEmpty()) {
            throw new CommandArgumentException("Использование: remove_by_id id");
        }
        try {
            int id = Integer.parseInt(args.trim());
            boolean removed = collectionManager.removeById(id);
            if (!removed) throw new ElementNotFoundException("Элемент не найден");
            io.writeLine("Удалено");
        } catch (NumberFormatException e) {
            throw new CommandArgumentException("id должен быть числом");
        }
        return true;
    }
}
