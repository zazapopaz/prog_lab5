package org.example.command;

import org.example.base.iomanager.IOManager;

/**
 * save: сохранить коллекцию (недоступно на клиенте).
 */
public class SaveCommand implements UserCommand {
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию (доступно только на сервере)";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        io.writeError("Команда save недоступна на клиенте. Используйте команду save в консоли сервера.");
        return true;
    }
}
