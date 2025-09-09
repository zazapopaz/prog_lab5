package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * save: сохранить коллекцию в файл (XML).
 */
public class SaveCommand extends UserCommand{
    public SaveCommand(CollectionManager cm, FileManager xml) {
        super("save", "сохранить коллекцию в файл", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            fileManager.writeAll(collectionManager.getAll());
        } catch (Exception e) {
            io.writeError("Ошибка сохранения: " + e.getMessage());
            return true;
        }
        io.writeLine("Коллекция сохранена");
        return true;
    }
}
