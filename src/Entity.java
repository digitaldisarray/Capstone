import java.awt.Color;

import apcs.Window;

public class Entity {
	
	private int x;
	private int y;
	private int width;
	private int height;

	private Shapes shape;
	private Color color;
	
	private boolean colisions;

	public Entity(int x, int y, int width, int height, Shapes shape, Color color, boolean colisions) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.shape = shape;
		this.color = color;
		this.colisions = colisions;
	}

	public void draw() {
		Window.out.color(color);

		switch (shape) {
		case SQUARE:
			Window.out.square(x, y, width);
			break;
		case CIRCLE:
			Window.out.circle(x, y, width / 2);
			break;
		case OVAL:
			Window.out.oval(x, y, width, height);
			break;
		}
	}
	
	public void update() {
		
	}
	
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setShape(Shapes shape) {
		this.shape = shape;
	}
	
	
	public int getX() {
		return this.x; 
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Shapes getShape() {
		return this.shape;
	}
}
