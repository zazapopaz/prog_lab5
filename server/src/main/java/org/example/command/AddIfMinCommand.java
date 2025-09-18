package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.StandartIOManager;
import org.example.base.model.Movie;
import org.example.manager.CollectionManager;

/**
 * add_if_min: добавить элемент, если он меньше минимального.
 */
public class AddIfMinCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
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
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 1) {
            throw new CommandArgumentException("Команда add_if_min принимает 1 аргумент: сериализованный фильм");
        }

        try {
            byte[] movieData = java.util.Base64.getDecoder().decode(arguments[0]);
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(movieData);
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            Movie movie = (Movie) ois.readObject();
            ois.close();

            boolean added = collectionManager.addIfMin(movie);
            if (added) {
                return "Фильм добавлен в коллекцию (он меньше минимального)";
            } else {
                return "Фильм не добавлен (он не меньше минимального)";
            }
        } catch (Exception e) {
            return "Ошибка десериализации фильма: " + e.getMessage();
        }
    }
}
