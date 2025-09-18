package org.example.base.model;

import java.io.Serializable;

/**
 * Модель координат.
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {
	private static final long serialVersionUID = 1L;
	private int x;
	private Long y; // Значение должно быть > -803, не null

	public Coordinates() {}

	public Coordinates(int x, Long y) {
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public void setX(int x) { this.x = x; }

	public Long getY() { return y; }
	public void setY(Long y) { this.y = y; }

	@Override
	public int compareTo(Coordinates other) {
		if (other == null) return 1;
		int byX = Integer.compare(this.x, other.x);
		if (byX != 0) return byX;
		long thisY = this.y == null ? Long.MIN_VALUE : this.y;
		long otherY = other.y == null ? Long.MIN_VALUE : other.y;
		return Long.compare(thisY, otherY);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
