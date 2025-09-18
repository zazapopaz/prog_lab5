package org.example.command;

import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.IOManager;
import org.example.base.model.Movie;
import org.example.manager.ClientCommandManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 * add_if_min: добавить элемент, если он меньше минимального.
 */
public class AddIfMinCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public AddIfMinCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "добавить элемент, если он меньше минимального";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            MovieReader movieReader = new MovieReader(io);
            Movie movie = movieReader.readMovieInteractive(true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(movie);
            oos.close();
            
            String serializedMovie = Base64.getEncoder().encodeToString(baos.toByteArray());
            
            var response = commandManager.sendCommand("add_if_min", new String[]{serializedMovie});
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
