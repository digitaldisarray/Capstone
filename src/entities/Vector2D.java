/*
 * Used to help with zombie movement and shooting angle.
 * This is not our code, credit goes to Joshua Hernandez
 */

package entities;

public class Vector2D {
	private double x, y;

	// Init a new vector
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// Normalize the vector
	public Vector2D normalize() {
		return new Vector2D(x / getMagnitude(), y / getMagnitude());
	}

	// Get the magnitude of the vector
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	// Get x of vector
	public double getX() {
		return x;
	}

	// Set the x of the vector
	public void setX(double x) {
		this.x = x;
	}

	// Get the y of the vector
	public double getY() {
		return y;
	}
}
