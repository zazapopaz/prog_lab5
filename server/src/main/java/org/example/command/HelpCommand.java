package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.manager.ServerCommandManager;

import java.util.Collection;

/**
 * help: показать справку по командам.
 */
public class HelpCommand implements ServerCommand {
    private final ServerCommandManager commandManager;

    public HelpCommand(ServerCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "показать справку по командам";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 0) {
            throw new CommandArgumentException("Команда help не принимает аргументы");
        }

        StringBuilder help = new StringBuilder();
        help.append("Доступные команды:\n");
        
        Collection<ServerCommand> commands = commandManager.list();
        for (ServerCommand command : commands) {
            help.append(String.format("%-20s - %s\n", command.getName(), command.getDescription()));
        }
        
        return help.toString();
    }
}
