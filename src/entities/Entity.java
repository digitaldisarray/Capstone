package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	
	private UUID uuid; // Used to avoid duplicate entities on the server
	
	public Entity(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.uuid = UUID.randomUUID();
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Color getColor() {
		return color;
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
}
