package org.example.base.file;

import org.example.base.exception.DeserializationException;
import org.example.base.model.Movie;

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
    
    public void saveAll(Collection<Movie> collection) throws IOException {
        delegate.writeAll(collection);
    }
}
