package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.CommandManager;
import org.example.manager.iomanager.FileIOManager;
import org.example.manager.iomanager.IOManager;
import org.example.exceptions.DamageScriptException;

/**
 * execute_script file_name: исполнить команды из указанного файла (без рекурсии).
 */
public class ExecuteScriptCommand extends UserCommand {
    private final CommandManager commandManager;

    public ExecuteScriptCommand(CollectionManager cm, FileManager xml, CommandManager commandManager) {
        super("execute_script", "выполнить команды из файла", cm, new CommandHistoryManager(), xml);
        this.commandManager = commandManager;
    }

    @Override
    public boolean execute(String args, IOManager io) throws DamageScriptException {
        if (args == null || args.isEmpty()) {
            throw new DamageScriptException("Использование: execute_script file_name");
        }
        try {
            IOManager fileIO = new FileIOManager(args.trim());
            String line;
            while ((line = fileIO.readLine()) != null) {
                if (!commandManager.executeLine(line, io)) {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new DamageScriptException("Ошибка чтения скрипта: " + e.getMessage());
        }
        return true;
    }
}
