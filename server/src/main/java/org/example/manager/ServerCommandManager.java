package org.example.manager;

import org.example.base.exception.CommandArgumentException;
import org.example.base.exception.IdAlreadyExistsException;
import org.example.base.exception.UnknownCommandException;
import org.example.base.exception.ElementNotFoundException;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.command.ServerCommand;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Менеджер команд сервера.
 */
public class ServerCommandManager {
	private final Map<String, ServerCommand> commands = new LinkedHashMap<>();
	private final CommandHistoryManager historyManager;

	public ServerCommandManager(CommandHistoryManager historyManager) {
		this.historyManager = historyManager;
	}

	public void register(ServerCommand command) {
		commands.put(command.getName(), command);
	}

	public Collection<ServerCommand> list() { return commands.values(); }

	public ServerResponse execute(String commandName, String[] arguments) {
		ServerCommand cmd = commands.get(commandName);
		if (cmd == null) {
			return new ServerResponse(ServerResponseType.ERROR, "Неизвестная команда: " + commandName);
		}
		
		historyManager.add(commandName);
		
		try {
			String result = cmd.execute(arguments);
			return new ServerResponse(ServerResponseType.SUCCESS, result);
		} catch (CommandArgumentException | ElementNotFoundException | IdAlreadyExistsException e) {
			return new ServerResponse(ServerResponseType.ERROR, e.getMessage());
		} catch (Exception e) {
			return new ServerResponse(ServerResponseType.ERROR, "Ошибка выполнения команды: " + e.getMessage());
		}
	}

	public CommandHistoryManager getHistoryManager() { return historyManager; }
}
