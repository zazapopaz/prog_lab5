package org.example.command;

import org.example.base.iomanager.IOManager;

/**
 * Интерфейс для пользовательских команд.
 */
public interface UserCommand {
    String getName();
    String getDescription();
    boolean execute(String args, IOManager io);
}
