package gui;

public class Vector {
	public volatile double x;
	public volatile double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector rotate(double angle) {
		return new Vector(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
	}
}
