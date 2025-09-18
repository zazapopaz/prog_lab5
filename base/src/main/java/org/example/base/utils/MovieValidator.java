package org.example.base.utils;

import org.example.base.model.*;

/**
 * Валидатор объектов Movie.
 */
public final class MovieValidator {
    private MovieValidator() {}

    public static String validate(Movie m) {
        if (m == null) return "Movie == null";
        if (isBlank(m.getName())) return "name: не может быть пустым";

        Coordinates c = m.getCoordinates();
        if (c == null) return "coordinates: не может быть null";
        if (c.getY() == null) return "coordinates.y: не может быть null";
        if (c.getY() <= -803) return "coordinates.y: должно быть > -803";

        Long oscars = m.getOscarsCount();
        if (oscars != null && oscars <= 0) return "oscarsCount: если задано, должно быть > 0";

        if (m.getBudget() <= 0) return "budget: должно быть > 0";

        if (m.getGenre() == null) return "genre: не может быть null";
        if (m.getMpaaRating() == null) return "mpaaRating: не может быть null";

        Person p = m.getOperator();
        if (p == null) return "operator: не может быть null";
        if (isBlank(p.getName())) return "operator.name: не может быть пустым";
        if (p.getBirthday() == null) return "operator.birthday: не может быть null";
        if (p.getHeight() <= 0) return "operator.height: должно быть > 0";
        if (p.getWeight() <= 0) return "operator.weight: должно быть > 0";

        Location loc = p.getLocation();

        return null;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}