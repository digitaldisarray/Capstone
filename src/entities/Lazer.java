package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Lazer extends Entity {
	static int totalNumber;

	private double degrees;
	private double velocity;// this is in pixles/millisecond
	private boolean remove;
	private long startTime;
	private long lastTime;
	private long duration;

	public Lazer(int x, int y, int width, int height, Color color, double degrees, double velocity, long duration) {
		super(x, y, width, height, color);
		this.degrees = degrees;
		this.velocity = velocity;
		remove = false;
		startTime = System.currentTimeMillis();
		lastTime = startTime;
		this.duration = duration;
		totalNumber = totalNumber + 1;

	}

	@Override
	public void draw(Graphics2D g) {// Draws the lazer
		long time = System.currentTimeMillis();

		// Moves x and y coordinates accordingly every time lazer is redrawn
		super.setX((int) (super.getX() + ((int) (time - lastTime)) * velocity * Math.cos(-Math.toRadians(degrees))));
		super.setY((int) (super.getY() + ((int) (time - lastTime)) * velocity * Math.sin(-Math.toRadians(degrees))));

//		AffineTransform old = g.getTransform();
//		AffineTransform n = AffineTransform.getRotateInstance(-Math.toRadians(degrees + 90), getX(), getY());

//		g.transform(n);
		g.setColor(this.getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
//		g.setTransform(old);

		// Time is used to hopefully reduce screen lag due to too many objects being created
		lastTime = time;
		if (time - startTime > duration) {
			remove = true;
		}
	}

	public boolean shouldRemove() {
		return remove;
	}
}
