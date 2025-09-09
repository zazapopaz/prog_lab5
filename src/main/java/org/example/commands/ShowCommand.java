package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * show: вывести все элементы коллекции.
 */
public class ShowCommand extends UserCommand{
    public ShowCommand(CollectionManager cm, FileManager xml) {
        super("show", "вывести все элементы коллекции", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        collectionManager.getAll().forEach(m -> io.writeLine(m.toString()));
        return true;
    }
}
