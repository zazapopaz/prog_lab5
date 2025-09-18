package org.example.manager;

import org.example.base.exception.IdAlreadyExistsException;
import org.example.base.model.Movie;

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
	public Collection<Movie> getAll() {
		return movies.stream()
				.sorted(Movie::compareTo)
				.collect(Collectors.toList());
	}

	public void clear() {
		movies.clear();
		nextId = 1;
	}

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
		if (movie.getId() == null) {
			try {
				addFromUser(movie);
			} catch (IdAlreadyExistsException e) {
				// Это не должно происходить для загруженных фильмов
				System.err.println("Ошибка при загрузке фильма: " + e.getMessage());
			}
			return;
		}

		if (findById(movie.getId()).isPresent()) {
			System.err.println("Пропуск дубликата фильма с ID: " + movie.getId());
			return;
		}

		movies.add(movie);


		if (movie.getId() >= nextId) {
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

		if (id >= nextId) {
			nextId = id + 1;
		}
		return movie;
	}

	public synchronized boolean removeById(int id) {
		boolean removed = movies.removeIf(m -> Objects.equals(m.getId(), id));
		if (removed) {
		}
		return removed;
	}

	public synchronized boolean updateById(int id, Movie newData) {
		Optional<Movie> existingMovie = movies.stream()
				.filter(m -> Objects.equals(m.getId(), id))
				.findFirst();

		if (existingMovie.isPresent()) {
			newData.setId(id);
			newData.setCreationDate(ZonedDateTime.now());
			movies.remove(existingMovie.get());
			movies.add(newData);
			return true;
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
		if (movies.isEmpty()) {
			return 0;
		}


		Set<Integer> usedIds = movies.stream()
				.map(Movie::getId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		int maxExistingId = usedIds.stream().max(Integer::compare).orElse(0);

		for (int id = 1; id <= maxExistingId + 1; id++) {
			if (!usedIds.contains(id)) {
				return id;
			}
		}

		return 0;
	}

	public void recalculateNextId() {
		if (movies.isEmpty()) {
			nextId = 1;
			return;
		}

		int maxId = movies.stream()
				.mapToInt(Movie::getId)
				.max()
				.orElse(0);

		nextId = maxId + 1;
	}
}