package org.example.models;

/**
 * Модель географического местоположения.
 */
public class Location {
	private Double x; // не null
	private double y;
	private String name; // не null

	public Location() {}

	public Location(Double x, double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public Double getX() { return x; }
	public void setX(Double x) { this.x = x; }

	public double getY() { return y; }
	public void setY(double y) { this.y = y; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@Override
	public String toString() {
		return "Location{" +
				"x=" + x +
				", y=" + y +
				", name='" + name + '\'' +
				'}';
	}
}