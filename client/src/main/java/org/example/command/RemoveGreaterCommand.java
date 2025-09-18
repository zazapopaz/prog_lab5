package org.example.command;

import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.IOManager;
import org.example.base.model.Movie;
import org.example.manager.ClientCommandManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 * remove_greater: удалить элементы больше заданного.
 */
public class RemoveGreaterCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public RemoveGreaterCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "удалить элементы больше заданного";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            MovieReader movieReader = new MovieReader(io);
            Movie sample = movieReader.readMovieInteractive(true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(sample);
            oos.close();
            
            String serializedMovie = Base64.getEncoder().encodeToString(baos.toByteArray());
            
            var response = commandManager.sendCommand("remove_greater", new String[]{serializedMovie});
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
