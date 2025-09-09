package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * help: вывести справку по доступным командам.
 */
public class HelpCommand extends UserCommand {
    private final CommandManager registry;

    public HelpCommand(CollectionManager cm, CommandManager registry, FileManager xml) {
        super("help", "вывести справку по доступным командам", cm, new CommandHistoryManager(), xml);
        this.registry = registry;
    }

    @Override
    public boolean execute(String args, IOManager io) {
        registry.list().forEach(c -> io.writeLine(c.getName() + " : " + c.getDescription()));
        return true;
    }
}
