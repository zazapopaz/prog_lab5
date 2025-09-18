package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.base.model.Movie;
import org.example.manager.CollectionManager;

import java.util.Optional;

/**
 * max_by_coordinates: показать элемент с максимальными координатами.
 */
public class MaxByCoordinatesCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public MaxByCoordinatesCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "max_by_coordinates";
    }

    @Override
    public String getDescription() {
        return "показать элемент с максимальными координатами";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 0) {
            throw new CommandArgumentException("Команда max_by_coordinates не принимает аргументы");
        }

        Optional<Movie> maxMovie = collectionManager.maxByCoordinates();
        if (maxMovie.isPresent()) {
            return "Элемент с максимальными координатами:\n" + maxMovie.get().toString();
        } else {
            return "Коллекция пуста";
        }
    }
}
