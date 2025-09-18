package org.example.commands;

import org.example.fieldReader.MovieReader;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.Movie;

/**
 * add_if_min {element}: добавить новый элемент, если он меньше минимального в коллекции.
 */
public class AddIfMinCommand extends UserCommand{
    public AddIfMinCommand(CollectionManager cm, FileManager xml) {
        super("add_if_min", "добавить новый элемент, если он меньше минимального", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) {
        MovieReader reader = new MovieReader(io);
        Movie m = reader.readMovieInteractive(true);
        boolean added = collectionManager.addIfMin(m);
        io.writeLine(added ? "Элемент добавлен" : "Элемент не минимален, не добавлен");
        return true;
    }
}
