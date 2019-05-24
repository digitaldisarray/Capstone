package entities;

public class Vector2D {
	private double x, y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D() {
		x = 0;
		y = 0;
	}

	public void zero() {
		x = 0;
		y = 0;
	}

	public double dot(Vector2D v) {
		return x * v.getX() + y * v.getY();
	}

	public boolean isZero() {
		if (x == 0 && y == 0)
			return true;
		return false;
	}

	public static double Distance(Vector2D a, Vector2D b) {
		return a.substract(b).getMagnitude();
	}

	public Vector2D normalize() {
		return new Vector2D(x / getMagnitude(), y / getMagnitude());
	}

	public Vector2D substract(Vector2D v) {
		return new Vector2D(x - v.getX(), y - v.getY());
	}

	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.getX(), y + v.getY());
	}

	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2D multyplyByScalar(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public Vector2D divideByScalar(double scalar) {
		x /= scalar;
		y /= scalar;
		return this;
	}

	public void truncate(double value) {
		if (x > value)
			x = value;
		if (y > value)
			y = value;
		if (x < -value)
			x = -value;
		if (y < -value)
			y = -value;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		return "x: " + (int) x + " y: " + (int) y;
	}
}
