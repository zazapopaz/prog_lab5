package org.example.commands;

import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;

/**
 * average_of_oscars_count: вывести среднее значение поля oscarsCount.
 */
public class AverageOfOscarsCount extends UserCommand {
    public AverageOfOscarsCount(CollectionManager cm, FileManager fileManager) {
        super("average_of_oscars_count", "вывести среднее значение поля oscarsCount", cm, new CommandHistoryManager(), fileManager);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        double avg = collectionManager.averageOfOscarsCount();
        io.writeLine(Double.isNaN(avg) ? "Нет значений oscarsCount" : String.valueOf(avg));
        return true;
    }
}
