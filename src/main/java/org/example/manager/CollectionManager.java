package org.example.manager;

import org.example.exceptions.IdAlreadyExistsException;
import org.example.models.Movie;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер коллекции фильмов.
 */
public class CollectionManager {
	private final LinkedHashSet<Movie> movies = new LinkedHashSet<>();
	private final ZonedDateTime initializationTime = ZonedDateTime.now();
	private int nextId = 1;

	public String getCollectionType() { return movies.getClass().getName(); }
	public ZonedDateTime getInitializationTime() { return initializationTime; }
	public int size() { return movies.size(); }
	public Collection<Movie> getAll() { return Collections.unmodifiableCollection(movies); }

	public void clear() { movies.clear(); }

	public synchronized Movie addFromUser(Movie movie) throws IdAlreadyExistsException {
		int newId = generateId();
		if (findById(newId).isPresent()) {
			throw new IdAlreadyExistsException("ID " + newId + " уже существует в коллекции");
		}
		movie.setId(newId);
		movie.setCreationDate(ZonedDateTime.now());
		movies.add(movie);
		return movie;
	}

	public synchronized void addLoaded(Movie movie) {
		movies.add(movie);
		if (movie.getId() != null && movie.getId() >= nextId) {
			nextId = movie.getId() + 1;
		}
	}

	public synchronized Movie addFromUserWithId(Movie movie, int id) throws IdAlreadyExistsException {
		if (findById(id).isPresent()) {
			throw new IdAlreadyExistsException("ID " + id + " уже существует в коллекции");
		}
		movie.setId(id);
		movie.setCreationDate(ZonedDateTime.now());
		movies.add(movie);
		// Обновляем nextId если нужно
		if (id >= nextId) {
			nextId = id + 1;
		}
		return movie;
	}

	public synchronized boolean removeById(int id) {
		return movies.removeIf(m -> Objects.equals(m.getId(), id));
	}

	public synchronized boolean updateById(int id, Movie newData) {
		for (Movie movie : movies) {
			if (Objects.equals(movie.getId(), id)) {
				newData.setId(id);
				newData.setCreationDate(ZonedDateTime.now());
				movies.remove(movie);
				movies.add(newData);
				return true;
			}
		}
		return false;
	}

	public Optional<Movie> findById(int id) {
		return movies.stream().filter(m -> Objects.equals(m.getId(), id)).findFirst();
	}

	public Optional<Movie> minByCreationDate() {
		return movies.stream().min(Comparator.comparing(Movie::getCreationDate));
	}

	public Optional<Movie> maxByCoordinates() {
		return movies.stream().max(Comparator.comparing(Movie::getCoordinates));
	}

	public double averageOfOscarsCount() {
		return movies.stream()
				.filter(m -> m.getOscarsCount() != null)
				.mapToLong(m -> m.getOscarsCount())
				.average()
				.orElse(Double.NaN);
	}

	public boolean addIfMin(Movie movie) {
		Optional<Movie> currentMin = movies.stream().min(Movie::compareTo);
		if (currentMin.isEmpty() || movie.compareTo(currentMin.get()) < 0) {
			try {
				addFromUser(movie);
				return true;
			} catch (IdAlreadyExistsException e) {
				// В теории этого не должно происходить, так как ID генерируется автоматически
				return false;
			}
		}
		return false;
	}

	public int removeGreater(Movie sample) {
		int before = movies.size();
		movies.removeIf(m -> m.compareTo(sample) > 0);
		return before - movies.size();
	}

	private int generateId() { 
		int minFreeId = findMinFreeId();
		if (minFreeId > 0) {
			return minFreeId;
		}
		return nextId++;
	}
	
	private int findMinFreeId() {
		Set<Integer> usedIds = movies.stream()
			.map(Movie::getId)
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());

		for (int id = 1; id <= Integer.MAX_VALUE; id++) {
			if (!usedIds.contains(id)) {
				return id;
			}
		}
		return 0;
	}
}
