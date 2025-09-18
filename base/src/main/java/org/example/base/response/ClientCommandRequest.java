package org.example.base.response;

import java.io.Serializable;

/**
 * Запрос команды от клиента к серверу.
 */
public class ClientCommandRequest implements Serializable {
    private final String commandName;
    private final String[] arguments;

    public ClientCommandRequest(String commandName, String[] arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "ClientCommandRequest{" +
                "commandName='" + commandName + '\'' +
                ", arguments=" + java.util.Arrays.toString(arguments) +
                '}';
    }
}
