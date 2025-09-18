package org.example.commands;

import org.example.fieldReader.MovieReader;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.Movie;

/**
 * remove_greater {element}: удалить все элементы, превышающие заданный.
 */
public class RemoveGreaterCommand extends UserCommand{
    public RemoveGreaterCommand(CollectionManager cm, FileManager xml) {
        super("remove_greater", "удалить элементы, превышающие заданный", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        MovieReader reader = new MovieReader(io);
        Movie m = reader.readMovieInteractive(true);
        int removed = collectionManager.removeGreater(m);
        io.writeLine("Удалено элементов: " + removed);
        return true;
    }
}
