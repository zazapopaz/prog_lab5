package org.example.models;

import java.time.ZonedDateTime;

/**
 * Модель человека (оператора).
 */
public class Person {
	private String name; // не null, не пустая строка
	private ZonedDateTime birthday; // не null
	private Float height; // не null, > 0
	private int weight; // > 0
	private Location location; // может быть null

	public Person() {}

	public Person(String name, ZonedDateTime birthday, Float height, int weight, Location location) {
		this.name = name;
		this.birthday = birthday;
		this.height = height;
		this.weight = weight;
		this.location = location;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public ZonedDateTime getBirthday() { return birthday; }
	public void setBirthday(ZonedDateTime birthday) { this.birthday = birthday; }

	public Float getHeight() { return height; }
	public void setHeight(Float height) { this.height = height; }

	public int getWeight() { return weight; }
	public void setWeight(int weight) { this.weight = weight; }

	public Location getLocation() { return location; }
	public void setLocation(Location location) { this.location = location; }

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", birthday=" + birthday +
				", height=" + height +
				", weight=" + weight +
				", location=" + location +
				'}';
	}
}