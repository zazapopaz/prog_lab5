package org.example.file;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.exceptions.DeserializationException;
import org.example.models.Movie;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Менеджер XML файлов с использованием Jackson.
 */
public class JacksonXmlFileManager {
    private final String filePath;
    private final XmlMapper xmlMapper;

    public JacksonXmlFileManager(String filePath) {
        this.filePath = filePath;
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.registerModule(new JavaTimeModule());
    }

    public Collection<Movie> readAll() throws IOException, DeserializationException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Файл не найден: " + filePath);
        }
        
        try {
            MovieCollection collection = xmlMapper.readValue(file, MovieCollection.class);
            return collection.getMovies();
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new DeserializationException("Ошибка парсинга XML: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Ошибка чтения файла: " + e.getMessage(), e);
        }
    }

    public void writeAll(Collection<Movie> movies) throws IOException {
        try {
            MovieCollection collection = new MovieCollection(movies);
            xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filePath), collection);
        } catch (Exception e) {
            throw new IOException("Ошибка записи XML: " + e.getMessage(), e);
        }
    }

    /**
     * Вспомогательный класс для сериализации коллекции фильмов.
     */
    public static class MovieCollection {
        @com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper(localName = "movies")
        @com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty(localName = "movie")
        private List<Movie> movies;

        public MovieCollection() {}

        public MovieCollection(Collection<Movie> movies) {
            this.movies = List.copyOf(movies);
        }

        public List<Movie> getMovies() {
            return movies;
        }

        public void setMovies(List<Movie> movies) {
            this.movies = movies;
        }
    }
}
