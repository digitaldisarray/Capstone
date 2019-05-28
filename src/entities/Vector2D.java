/*
 * Used to help with zombie movement and shooting angle.
 * This is not our code, credit goes to Joshua Hernandez
 */

package entities;

public class Vector2D {
	private double x, y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D normalize() {
		return new Vector2D(x / getMagnitude(), y / getMagnitude());
	}

	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
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
}
