package org.example.manager;

import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.exception.ServerErrorResponseException;
import org.example.network.NetworkClient;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Менеджер команд клиента.
 */
public class ClientCommandManager {
    private final Map<String, org.example.command.UserCommand> commands = new LinkedHashMap<>();
    private final CommandHistoryManager historyManager;
    private final NetworkClient networkClient;

    public ClientCommandManager(CommandHistoryManager historyManager, NetworkClient networkClient) {
        this.historyManager = historyManager;
        this.networkClient = networkClient;
    }

    public void register(org.example.command.UserCommand command) {
        commands.put(command.getName(), command);
    }

    public Collection<org.example.command.UserCommand> list() { 
        return commands.values(); 
    }

    public boolean executeLine(String line, org.example.base.iomanager.IOManager io) {
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
        org.example.command.UserCommand cmd = commands.get(name);
        if (cmd == null) {
            io.writeError("Неизвестная команда: " + name);
            return true;
        }
        historyManager.add(name);
        try {
            return cmd.execute(args, io);
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }

    public ServerResponse sendCommand(String commandName, String[] arguments) throws ServerErrorResponseException {
        ClientCommandRequest request = new ClientCommandRequest(commandName, arguments);
        networkClient.sendUserCommand(request);
        return networkClient.getServerResponse();
    }

    public CommandHistoryManager getHistoryManager() { 
        return historyManager; 
    }
}
