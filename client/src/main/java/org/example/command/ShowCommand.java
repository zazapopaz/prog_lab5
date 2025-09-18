package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

/**
 * show: вывести все элементы коллекции.
 */
public class ShowCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public ShowCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            var response = commandManager.sendCommand("show", new String[0]);
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
