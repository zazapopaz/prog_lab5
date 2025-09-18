package org.example.command;

/**
 * Интерфейс для серверных команд.
 */
public interface ServerCommand {
    String getName();
    String getDescription();
    String execute(String[] arguments) throws Exception;
}
