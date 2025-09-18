package org.example.models;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Модель фильма.
 */
public class Movie implements Comparable<Movie> {
	private Integer id; // Поле не может быть null, > 0, уникально, генерируется автоматически
	private String name; // Поле не может быть null, строка не может быть пустой
	private Coordinates coordinates; // Поле не может быть null
	private ZonedDateTime creationDate; // Поле не может быть null, генерируется автоматически
	private Long oscarsCount; // > 0, может быть null
	private long budget; // > 0
	private MovieGenre genre; // Поле не может быть null
	private MpaaRating mpaaRating; // Поле не может быть null
	private Person operator; // Поле не может быть null

	public Movie() {
	}

	public Movie(Integer id,
	            String name,
	            Coordinates coordinates,
	            ZonedDateTime creationDate,
	            Long oscarsCount,
	            long budget,
	            MovieGenre genre,
	            MpaaRating mpaaRating,
	            Person operator) {
		this.id = id;
		this.name = name;
		this.coordinates = coordinates;
		this.creationDate = creationDate;
		this.oscarsCount = oscarsCount;
		this.budget = budget;
		this.genre = genre;
		this.mpaaRating = mpaaRating;
		this.operator = operator;
	}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Coordinates getCoordinates() { return coordinates; }
	public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

	public ZonedDateTime getCreationDate() { return creationDate; }
	public void setCreationDate(ZonedDateTime creationDate) { this.creationDate = creationDate; }

	public Long getOscarsCount() { return oscarsCount; }
	public void setOscarsCount(Long oscarsCount) { this.oscarsCount = oscarsCount; }

	public long getBudget() { return budget; }
	public void setBudget(long budget) { this.budget = budget; }

	public MovieGenre getGenre() { return genre; }
	public void setGenre(MovieGenre genre) { this.genre = genre; }

	public MpaaRating getMpaaRating() { return mpaaRating; }
	public void setMpaaRating(MpaaRating mpaaRating) { this.mpaaRating = mpaaRating; }

	public Person getOperator() { return operator; }
	public void setOperator(Person operator) { this.operator = operator; }

	@Override
	public int compareTo(Movie other) {
		if (other == null) return 1;
		int byName = this.nullSafe(this.name).compareToIgnoreCase(this.nullSafe(other.name));
		if (byName != 0) return byName;
		return Integer.compare(this.id == null ? 0 : this.id, other.id == null ? 0 : other.id);
	}

	private String nullSafe(String s) { return s == null ? "" : s; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Movie)) return false;
		Movie movie = (Movie) o;
		return Objects.equals(id, movie.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Movie{" +
				"id=" + id +
				", name='" + name + '\'' +
				", coordinates=" + coordinates +
				", creationDate=" + creationDate +
				", oscarsCount=" + oscarsCount +
				", budget=" + budget +
				", genre=" + genre +
				", mpaaRating=" + mpaaRating +
				", operator=" + operator +
				'}';
	}
}