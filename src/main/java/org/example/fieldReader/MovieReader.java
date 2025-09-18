package org.example.fieldReader;

import org.example.manager.iomanager.IOManager;
import org.example.models.*;

/**
 * Ридер для объектов Movie.
 */
public class MovieReader {
    private final IOManager io;

    public MovieReader(IOManager io) {
        this.io = io;
    }

    public Movie readMovieInteractive(boolean generateAutoFields) {
        Movie m = new Movie();
        
        // Поля, которые НЕ генерируются автоматически (вводятся пользователем)
        StringFieldReader s = new StringFieldReader(io);
        m.setName(s.readNonEmpty("Введите название фильма: "));

        CoordinatesFieldReader cr = new CoordinatesFieldReader(io);
        m.setCoordinates(cr.read());

        LongWrapperFieldReader lw = new LongWrapperFieldReader(io);
        m.setOscarsCount(lw.readNullablePositive("Введите количество оскаров: "));

        LongFieldReader lf = new LongFieldReader(io);
        m.setBudget(lf.readPositive("Введите бюджет: "));

        EnumFieldReader ef = new EnumFieldReader(io);
        m.setGenre(ef.readEnum("Жанр", MovieGenre.class));
        m.setMpaaRating(ef.readEnum("MPAA рейтинг", MpaaRating.class));

        PersonFieldReader pr = new PersonFieldReader(io);
        m.setOperator(pr.read());
        
        // Поля id и creationDate НЕ устанавливаются здесь - они генерируются автоматически в CollectionManager.addFromUser()
        return m;
    }
}


