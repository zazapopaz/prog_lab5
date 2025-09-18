package org.example.commands;

import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.IdAlreadyExistsException;
import org.example.fieldReader.MovieReader;
import org.example.file.FileManager;
import org.example.manager.CollectionManager;
import org.example.manager.CommandHistoryManager;
import org.example.manager.iomanager.IOManager;
import org.example.models.Movie;

/**
 * add {element}: добавить новый элемент в коллекцию (интерактивный ввод полей с валидацией).
 * Сначала запрашивает ID, если пустой - генерирует автоматически.
 */
public class AddCommand extends UserCommand {
    public AddCommand(CollectionManager cm, FileManager xml) {
        super("add", "добавить новый элемент в коллекцию", cm, new CommandHistoryManager(), xml);
    }

    @Override
    public boolean execute(String args, IOManager io) throws IdAlreadyExistsException, CommandArgumentException {
        // Запрашиваем ID у пользователя
        io.writeLine("Введите ID для нового элемента (или нажмите Enter для автоматической генерации):");
        String idInput = io.readLine().trim();
        
        MovieReader reader = new MovieReader(io);
        Movie m = reader.readMovieInteractive(true);
        
        if (idInput.isEmpty()) {
            // Автоматическая генерация ID
            collectionManager.addFromUser(m);
            io.writeLine("Элемент добавлен с автоматически сгенерированным ID: " + m.getId());
        } else {
            // Ручное задание ID
            try {
                int id = Integer.parseInt(idInput);
                if (id <= 0) {
                    throw new CommandArgumentException("ID должен быть положительным числом");
                }
                collectionManager.addFromUserWithId(m, id);
                io.writeLine("Элемент добавлен с ID: " + id);
            } catch (NumberFormatException e) {
                throw new CommandArgumentException("ID должен быть числом");
            }
        }
        return true;
    }
}
