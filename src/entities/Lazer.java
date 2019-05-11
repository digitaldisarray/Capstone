package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Lazer extends Entity{
	int degrees;
	
	public Lazer(int x, int y, int width, int height, Color color, int degrees) {
		super(x, y, width, height, color);
		this.degrees = degrees;
	}
	
	public void draw(Graphics2D g) {
		 g.rotate(Math.toRadians(degrees));
		 g.setColor(this.getColor());
		 g.drawRect(getX(), getY(), getWidth(), getHeight());
	}

}
