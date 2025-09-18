package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.base.model.Movie;
import org.example.manager.CollectionManager;

import java.util.Optional;

/**
 * min_by_creation_time: показать элемент с минимальным временем создания.
 */
public class MinByCreationTimeCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public MinByCreationTimeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "min_by_creation_time";
    }

    @Override
    public String getDescription() {
        return "показать элемент с минимальным временем создания";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 0) {
            throw new CommandArgumentException("Команда min_by_creation_time не принимает аргументы");
        }

        Optional<Movie> minMovie = collectionManager.minByCreationDate();
        if (minMovie.isPresent()) {
            return "Элемент с минимальным временем создания:\n" + minMovie.get().toString();
        } else {
            return "Коллекция пуста";
        }
    }
}
