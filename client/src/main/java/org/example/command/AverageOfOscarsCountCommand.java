package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

/**
 * average_of_oscars_count: среднее количество оскаров.
 */
public class AverageOfOscarsCountCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public AverageOfOscarsCountCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "average_of_oscars_count";
    }

    @Override
    public String getDescription() {
        return "среднее количество оскаров";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            var response = commandManager.sendCommand("average_of_oscars_count", new String[0]);
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
