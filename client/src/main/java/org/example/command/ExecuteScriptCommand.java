package org.example.command;

import org.example.base.iomanager.FileIOManager;
import org.example.base.iomanager.IOManager;
import org.example.manager.ClientCommandManager;

import java.io.FileNotFoundException;

/**
 * execute_script: выполнить скрипт из файла.
 */
public class ExecuteScriptCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public ExecuteScriptCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "выполнить скрипт из файла";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        if (args == null || args.trim().isEmpty()) {
            io.writeError("Команда execute_script требует аргумент: путь к файлу");
            return true;
        }

        try {
            String filePath = args.trim();
            IOManager fileIO = new FileIOManager(filePath);
            
            io.writeLine("Выполнение скрипта: " + filePath);
            
            String line;
            while ((line = fileIO.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                io.writeLine("> " + line);
                boolean shouldContinue = commandManager.executeLine(line, io);
                if (!shouldContinue) {
                    break;
                }
            }
            
            io.writeLine("Скрипт выполнен");
            return true;
        } catch (FileNotFoundException e) {
            io.writeError("Файл не найден: " + args);
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения скрипта: " + e.getMessage());
            return true;
        }
    }
}
