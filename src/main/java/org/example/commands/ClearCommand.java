package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * clear: очистить коллекцию.
 */
public class ClearCommand extends UserCommand {
    public ClearCommand(CollectionManager cm, FileManager xml) {
        super("clear", "очистить коллекцию", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        collectionManager.clear();
        io.writeLine("Коллекция очищена");
        return true;
    }
}
