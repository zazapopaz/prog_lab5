package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * max_by_coordinates: вывести объект с максимальными координатами.
 */
public class MaxByCoordinatesCommand extends UserCommand {
    public MaxByCoordinatesCommand(CollectionManager cm, FileManager xml) {
        super("max_by_coordinates", "вывести объект с максимальными координатами", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        io.writeLine(collectionManager.maxByCoordinates().map(Object::toString).orElse("Коллекция пуста"));
        return true;
    }
}


