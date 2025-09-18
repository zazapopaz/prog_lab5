package org.example.command;

import org.example.base.iomanager.IOManager;

/**
 * exit: завершить программу.
 */
public class ExitCommand implements UserCommand {
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "завершить программу";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        io.writeLine("Завершение программы...");
        return false; // false означает завершение программы
    }
}
