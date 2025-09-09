package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.CommandManager;
import org.example.manager.iomanager.IOManager;

/**
 * history: вывести последние 8 команд (без аргументов).
 */
public class HistoryCommand extends UserCommand {
    private final CommandManager registry;

    public HistoryCommand(CollectionManager cm, FileManager fileManager, CommandManager registry) {
        super("history", "вывести последние 8 команд", cm, new CommandHistoryManager(), fileManager);
        this.registry = registry;
    }

    @Override
    public boolean execute(String args, IOManager io) {
        var list = registry.getHistoryManager().getHistory();
        for (String s : list) io.writeLine(s);
        return true;
    }
}
