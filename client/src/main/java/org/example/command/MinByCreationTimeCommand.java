package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

/**
 * min_by_creation_time: показать элемент с минимальным временем создания.
 */
public class MinByCreationTimeCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public MinByCreationTimeCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "min_by_creation_time";
    }

    @Override
    public String getDescription() {
        return "показать элемент с минимальным временем создания";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            var response = commandManager.sendCommand("min_by_creation_time", new String[0]);
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
