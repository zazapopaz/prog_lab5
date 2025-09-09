package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * min_by_creation_date: вывести объект с минимальной датой создания.
 */
public class MinByCreationTimeCommand extends UserCommand {
    public MinByCreationTimeCommand(CollectionManager cm, FileManager xml) {
        super("min_by_creation_date", "вывести объект с минимальной датой создания", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        io.writeLine(collectionManager.minByCreationDate().map(Object::toString).orElse("Коллекция пуста"));
        return true;
    }
}
