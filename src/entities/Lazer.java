package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import base.Launcher;
import client.Protocol;

public class Lazer extends Entity {
	static int totalNumber;

	private Vector2D direction;
	private double velocity;// this is in pixles/millisecond
	private boolean remove;
	private long startTime;
	private long lastTime;
	private long duration;

	public Lazer(int x, int y, int width, int height, Color color, Vector2D vector2d, double velocity, long duration) {
		super(x, y, width, height, color);
		this.direction = vector2d;
		this.velocity = velocity;
		remove = false;
		startTime = System.currentTimeMillis();
		lastTime = startTime;
		this.duration = duration;
		totalNumber = totalNumber + 1;
		
		if (Launcher.getGame().isConnected()) {
			Launcher.getClient().sendToServer(new Protocol().ShotPacket(getX(), getY(), getID(), 1));
		}
	}

	@Override
	public void draw(Graphics2D g) {// Draws the lazer
		long time = System.currentTimeMillis();

		setX((int) (getX() + (direction.getX() * 10) + 0.5));
		setY((int) (getY() + (direction.getY() * 10) + 0.5));

		// Moves x and y coordinates accordingly every time lazer is redrawn
//		super.setX((int) (super.getX() + ((int) (time - lastTime)) * velocity * Math.cos(-Math.toRadians(degrees))));
//		super.setY((int) (super.getY() + ((int) (time - lastTime)) * velocity * Math.sin(-Math.toRadians(degrees))));

		// Old rotation code
//		AffineTransform old = g.getTransform();
//		AffineTransform n = AffineTransform.getRotateInstance(-Math.toRadians(degrees + 90), getX(), getY());

//		g.transform(n);
		g.setColor(this.getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
//		g.setTransform(old);

		// Time is to reduce lag on the screen
		lastTime = time;
		if (time - startTime > duration) {
			remove = true;
		}
		
		if (Launcher.getGame().isConnected()) {
			Launcher.getClient().sendToServer(new Protocol().ShotUpdatePacket(getX(), getY(), getID(), 1));
		}
	}

	public void setRemove(boolean r) {
		remove = r;
	}

	public boolean shouldRemove() {
		return remove;
	}
}
