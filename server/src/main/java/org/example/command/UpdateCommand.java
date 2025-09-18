package org.example.command;

import org.example.base.exception.CommandArgumentException;
import org.example.base.exception.ElementNotFoundException;
import org.example.base.fieldReader.MovieReader;
import org.example.base.iomanager.StandartIOManager;
import org.example.base.model.Movie;
import org.example.manager.CollectionManager;

/**
 * update: обновить элемент по ID.
 */
public class UpdateCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить элемент по ID";
    }

    @Override
    public String execute(String[] arguments) throws Exception {
        if (arguments.length != 2) {
            throw new CommandArgumentException("Команда update принимает 2 аргумента: ID и сериализованный фильм");
        }

        try {
            int id = Integer.parseInt(arguments[0]);
            
            // Проверяем, существует ли элемент с таким ID
            if (!collectionManager.findById(id).isPresent()) {
                throw new ElementNotFoundException("Элемент с ID " + id + " не найден");
            }

            byte[] movieData = java.util.Base64.getDecoder().decode(arguments[1]);
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(movieData);
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            Movie newData = (Movie) ois.readObject();
            ois.close();

            boolean updated = collectionManager.updateById(id, newData);
            if (updated) {
                return "Элемент с ID " + id + " успешно обновлен";
            } else {
                return "Ошибка при обновлении элемента";
            }
        } catch (NumberFormatException e) {
            throw new CommandArgumentException("ID должен быть числом");
        } catch (Exception e) {
            return "Ошибка десериализации фильма: " + e.getMessage();
        }
    }
}
