package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.base.exception.IdAlreadyExistsException;
import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.StandartIOManager;
import org.example.base.model.Movie;
import org.example.manager.CollectionManager;

/**
 * add: добавить новый элемент в коллекцию.
 */
public class AddCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
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
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 1) {
            throw new CommandArgumentException("Команда add принимает 1 аргумент: сериализованный фильм");
        }

        try {
            // Десериализуем фильм из Base64
            byte[] movieData = java.util.Base64.getDecoder().decode(arguments[0]);
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(movieData);
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            Movie movie = (Movie) ois.readObject();
            ois.close();

            collectionManager.addFromUser(movie);
            return "Фильм успешно добавлен в коллекцию с ID: " + movie.getId();
        } catch (IdAlreadyExistsException e) {
            return "Ошибка: " + e.getMessage();
        } catch (Exception e) {
            return "Ошибка десериализации фильма: " + e.getMessage();
        }
    }
}
