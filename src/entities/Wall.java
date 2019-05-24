package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import base.Launcher;

public class Wall extends Entity {

	private boolean remove;
	private long startTime;
	private long lastTime;
	private long duration;

	public Wall(int x, int y, int width, int height, Color color, int wallDirection, long duration) {
		super(x, y, width, height, color);
		startTime = System.currentTimeMillis();
		lastTime = startTime;
		this.duration = duration;
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics2D g) {// Draws the lazer
		long time = System.currentTimeMillis();
		g.setColor(Launcher.getGame().getPlayer().getWallColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		lastTime = time;
		if (time - startTime > duration) {
			remove = true;
		}
	}

	public void setRemoveToTrue() {
		remove = true;
	}

	public boolean shouldRemove() {
		return remove;
	}

}
