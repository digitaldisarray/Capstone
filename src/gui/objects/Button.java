package gui.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import base.Launcher;

public class Button {
	private int x, y, width, height;

	private String text;
	private Color color;
	private ButtonTask task;
	private boolean bold;

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
		
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		int stringWidth = g.getFontMetrics().stringWidth(text);

		int difference = (width - stringWidth) / 2;
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		if(bold) {
			g.drawRect(x + 1, y + 1, width - 1, height - 1);
		}
		
		g.drawString(text, x + difference, y + height / 2 + 5);

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

	public boolean contains(int xClick, int yClick) {
		xClick /= Launcher.getGame().getXScaleFactor();
		yClick /= Launcher.getGame().getYScaleFactor();

		if (xClick >= this.getX() && xClick <= this.getX() + this.getWidth()) {
			if (yClick >= this.getY() && yClick <= this.getY() + this.getHeight()) {
				return true;
			}
		}
		return false;
	}
	
	public void setBold(boolean input) {
		this.bold = input;
	}
	
	public boolean isBold() {
		return bold;
	}
}
