package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.manager.CommandHistoryManager;

import java.util.List;

/**
 * history: показать историю команд.
 */
public class HistoryCommand implements ServerCommand {
    private final CommandHistoryManager historyManager;

    public HistoryCommand(CommandHistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "показать историю команд";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 0) {
            throw new CommandArgumentException("Команда history не принимает аргументы");
        }

        List<String> history = historyManager.getHistory();
        if (history.isEmpty()) {
            return "История команд пуста";
        }

        StringBuilder result = new StringBuilder();
        result.append("Последние команды:\n");
        for (int i = 0; i < history.size(); i++) {
            result.append(String.format("%d. %s\n", i + 1, history.get(i)));
        }
        
        return result.toString();
    }
}
