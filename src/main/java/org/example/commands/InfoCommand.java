package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

import java.time.format.DateTimeFormatter;

/**
 * info: вывести информацию о коллекции (тип, дата инициализации, размер).
 */
public class InfoCommand extends UserCommand {
    public InfoCommand(CollectionManager cm, FileManager xml) {
        super("info", "вывести информацию о коллекции", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        io.writeLine("Тип коллекции: " + collectionManager.getCollectionType());
        io.writeLine("Дата инициализации: " + collectionManager.getInitializationTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        io.writeLine("Количество элементов: " + collectionManager.size());
        return true;
    }
}
