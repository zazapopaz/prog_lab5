package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.StandartIOManager;
import org.example.base.model.Movie;
import org.example.manager.CollectionManager;

/**
 * remove_greater: удалить элементы больше заданного.
 */
public class RemoveGreaterCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
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
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 1) {
            throw new CommandArgumentException("Команда remove_greater принимает 1 аргумент: сериализованный фильм");
        }

        try {
            byte[] movieData = java.util.Base64.getDecoder().decode(arguments[0]);
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(movieData);
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            Movie sample = (Movie) ois.readObject();
            ois.close();

            int removedCount = collectionManager.removeGreater(sample);
            return "Удалено элементов: " + removedCount;
        } catch (Exception e) {
            return "Ошибка десериализации фильма: " + e.getMessage();
        }
    }
}
