package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Lazer extends Entity {
	double degrees;

	public Lazer(int x, int y, int width, int height, Color color, double degrees) {
		super(x, y, width, height, color);
		this.degrees = degrees;
	}

	public void draw(Graphics2D g) {
		AffineTransform old = g.getTransform();
		AffineTransform n = AffineTransform.getRotateInstance(-Math.toRadians(degrees + 90), getX(), getY());
		// g.rotate(Math.toRadians(degrees-90));
		g.transform(n);
		g.setColor(this.getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setTransform(old);
	}
}
