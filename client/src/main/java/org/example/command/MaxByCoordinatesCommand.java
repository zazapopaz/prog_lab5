package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

/**
 * max_by_coordinates: показать элемент с максимальными координатами.
 */
public class MaxByCoordinatesCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public MaxByCoordinatesCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "max_by_coordinates";
    }

    @Override
    public String getDescription() {
        return "показать элемент с максимальными координатами";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            var response = commandManager.sendCommand("max_by_coordinates", new String[0]);
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
