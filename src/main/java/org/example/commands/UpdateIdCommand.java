package org.example.commands;

import org.example.fieldReader.MovieReader;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.Movie;
import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.ElementNotFoundException;

/**
 * update id {element}: обновить значение элемента по заданному id.
 */
public class UpdateIdCommand extends UserCommand{
    public UpdateIdCommand(CollectionManager cm, FileManager xml) {
        super("update", "обновить значение элемента по id", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) throws CommandArgumentException, ElementNotFoundException {
        if (args == null || args.isEmpty()) {
            throw new CommandArgumentException("Использование: update id");
        }
        try {
            int id = Integer.parseInt(args.trim().split("\\s+")[0]);
            if (collectionManager.findById(id).isEmpty()) {
                throw new ElementNotFoundException("Элемент с id=" + id + " не найден");
            }
            MovieReader reader = new MovieReader(io);
            Movie m = reader.readMovieInteractive(true);
            boolean ok = collectionManager.updateById(id, m);
            io.writeLine(ok ? "Элемент обновлен" : "Не удалось обновить элемент");
        } catch (NumberFormatException e) {
            throw new CommandArgumentException("id должен быть числом");
        }
        return true;
    }
}
