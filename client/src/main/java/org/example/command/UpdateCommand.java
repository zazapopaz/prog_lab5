package org.example.command;

import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.IOManager;
import org.example.base.model.Movie;
import org.example.manager.ClientCommandManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 * update: обновить элемент по ID.
 */
public class UpdateCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public UpdateCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить элемент по ID";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        if (args == null || args.trim().isEmpty()) {
            io.writeError("Команда update требует аргумент: ID");
            return true;
        }

        try {
            int id = Integer.parseInt(args.trim());
            
            MovieReader movieReader = new MovieReader(io);
            Movie newData = movieReader.readMovieInteractive(true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(newData);
            oos.close();
            
            String serializedMovie = Base64.getEncoder().encodeToString(baos.toByteArray());
            
            var response = commandManager.sendCommand("update", new String[]{String.valueOf(id), serializedMovie});
            io.writeLine(response.getMessage());
            return true;
        } catch (NumberFormatException e) {
            io.writeError("ID должен быть числом");
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
