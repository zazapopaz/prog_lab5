package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.manager.CollectionManager;

/**
 * average_of_oscars_count: среднее количество оскаров.
 */
public class AverageOfOscarsCountCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public AverageOfOscarsCountCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "average_of_oscars_count";
    }

    @Override
    public String getDescription() {
        return "среднее количество оскаров";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 0) {
            throw new CommandArgumentException("Команда average_of_oscars_count не принимает аргументы");
        }

        double average = collectionManager.averageOfOscarsCount();
        if (Double.isNaN(average)) {
            return "В коллекции нет фильмов с указанным количеством оскаров";
        } else {
            return String.format("Среднее количество оскаров: %.2f", average);
        }
    }
}
