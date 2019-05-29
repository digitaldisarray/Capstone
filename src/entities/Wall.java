/*
 * represents a wall that can be placed to stop enemy bullets or zombies
 * Author: The Mustngs
 * Last edited: 5/22/2019
 * */

package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import base.Launcher;

public class Wall extends Entity {

	private boolean remove;
	private long startTime;
	private long lastTime;
	private long duration;
	
	//Constructor
	public Wall(int x, int y, int width, int height, Color color, int wallDirection, long duration) {
		super(x, y, width, height, color);
		startTime = System.currentTimeMillis();
		lastTime = startTime;
		this.duration = duration;
	}

	public void draw(Graphics2D g) {// Draws the wall
		long time = System.currentTimeMillis();
		g.setColor(Launcher.getGame().getPlayer().getWallColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		lastTime = time;
		if (time - startTime > duration) {
			remove = true;
		}
	}

	public void setRemoveToTrue() {//Changes the remove boolean
		remove = true;
	}

	public boolean shouldRemove() {//Changes the remove boolean
		return remove;
	}

}
