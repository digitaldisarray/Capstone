package gui.objects;

import java.awt.Color;
import java.awt.Graphics2D;

public class Button {
	private int x, y, width, height;
	private String text;
	private Color color;
	private ButtonTask task;
	
	// TODO: Create class called click task
	
	public Button(int x, int y, int width, int height, String text, Color color, ButtonTask task) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.color = color;
		this.task = task;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g.drawString(text, x, y + height / 2 + 5);
	}
	
	public void clicked() {
		task.run();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
