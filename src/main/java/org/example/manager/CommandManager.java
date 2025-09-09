package org.example.manager;

import org.example.commands.UserCommand;
import org.example.manager.iomanager.IOManager;
import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.IdAlreadyExistsException;
import org.example.exceptions.UnknownCommandException;
import org.example.exceptions.ElementNotFoundException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Менеджер команд.
 */
public class CommandManager {
	private final Map<String, UserCommand> commands = new LinkedHashMap<>();
	private final CommandHistoryManager historyManager;

	public CommandManager(CommandHistoryManager historyManager) {
		this.historyManager = historyManager;
	}

	public void register(UserCommand command) {
		commands.put(command.getName(), command);
	}

	public Collection<UserCommand> list() { return commands.values(); }

	public boolean executeLine(String line, IOManager io) {
		if (line == null) return true;
		String trimmed = line.trim();
		if (trimmed.isEmpty()) return true;
		String name;
		String args = "";
		int space = trimmed.indexOf(' ');
		if (space < 0) {
			name = trimmed;
		} else {
			name = trimmed.substring(0, space);
			args = trimmed.substring(space + 1).trim();
		}
		UserCommand cmd = commands.get(name);
		if (cmd == null) {
			try {
				throw new UnknownCommandException("Неизвестная команда: " + name);
			} catch (UnknownCommandException e) {
				io.writeError(e.getMessage());
				return true;
			}
		}
		historyManager.add(name);
		try {
			return cmd.execute(args, io);
		} catch (CommandArgumentException | ElementNotFoundException | IdAlreadyExistsException e) {
			io.writeError(e.getMessage());
			return true;
		} catch (Exception e) {
			io.writeError("Ошибка выполнения команды: " + e.getMessage());
			return true;
		}
	}

	public CommandHistoryManager getHistoryManager() { return historyManager; }
}
