package org.example.command;

import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.IOManager;
import org.example.base.model.Movie;
import org.example.manager.ClientCommandManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 * add: добавить новый элемент в коллекцию.
 */
public class AddCommand implements UserCommand {
    private final ClientCommandManager commandManager;

    public AddCommand(ClientCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public boolean execute(String args, IOManager io) {
        try {
            // Создаем MovieReader для интерактивного ввода
            MovieReader movieReader = new MovieReader(io);
            Movie movie = movieReader.readMovieInteractive(true);

            // Сериализуем фильм в Base64 для передачи
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(movie);
            oos.close();
            
            String serializedMovie = Base64.getEncoder().encodeToString(baos.toByteArray());
            
            var response = commandManager.sendCommand("add", new String[]{serializedMovie});
            io.writeLine(response.getMessage());
            return true;
        } catch (Exception e) {
            io.writeError("Ошибка выполнения команды: " + e.getMessage());
            return true;
        }
    }
}
