package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

/**
 * help: показать справку по командам.
 */
public class HelpCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public HelpCommand(ClientCommandManager commandManager) {
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
    public boolean execute(String args, IOManager io) {
        try {
            var response = commandManager.sendCommand("help", new String[0]);
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
