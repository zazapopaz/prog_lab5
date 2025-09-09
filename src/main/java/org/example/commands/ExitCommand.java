package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * exit: завершить программу без сохранения.
 */
public class ExitCommand extends UserCommand {
    public ExitCommand(CollectionManager cm, FileManager xml) {
        super("exit", "завершить программу (без сохранения)", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        io.writeLine("Выход без сохранения...");
        return false;
    }
}
