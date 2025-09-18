package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

/**
 * remove_by_id: удалить элемент по ID.
 */
public class RemoveByIdCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public RemoveByIdCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент по ID";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        if (args == null || args.trim().isEmpty()) {
            io.writeError("Команда remove_by_id требует аргумент: ID");
            return true;
        }

        try {
            var response = commandManager.sendCommand("remove_by_id", new String[]{args.trim()});
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
