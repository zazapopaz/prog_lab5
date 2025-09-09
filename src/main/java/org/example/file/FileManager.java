package org.example.file;

import org.example.exceptions.DeserializationException;
import org.example.models.Movie;

import java.io.IOException;
import java.util.Collection;

/**
 * Менеджер файлов.
 */
public class FileManager {
    private final JacksonXmlFileManager delegate;

    public FileManager(String filePath) {
        this.delegate = new JacksonXmlFileManager(filePath);
    }

    public Collection<Movie> readAll() throws IOException, DeserializationException {
        return delegate.readAll();
    }

    public void writeAll(Collection<Movie> collection) throws IOException {
        delegate.writeAll(collection);
    }
}


