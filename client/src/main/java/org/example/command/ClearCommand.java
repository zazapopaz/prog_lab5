package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

/**
 * clear: очистить коллекцию.
 */
public class ClearCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public ClearCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            var response = commandManager.sendCommand("clear", new String[0]);
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
