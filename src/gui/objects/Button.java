package gui.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import base.Launcher;

public class Button {
	// Coordinates and size of the button
	private int x, y, width, height;

	// The text that the button will display
	private String text;
	
	// The task that the button is assigned
	private ButtonTask task;

	// Should the button be displayed in bold mode
	private boolean bold;
	
	//The color of the button]
	private Color buttonColor;
	// Constructor to create a new button
	public Button(int x, int y, int width, int height, String text, ButtonTask task, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.task = task;
		buttonColor = color;
	}

	// Draw the button
	public void draw(Graphics2D g) {
		g.setColor(buttonColor);
		g.fillRect(x, y, width, height);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		int stringWidth = g.getFontMetrics().stringWidth(text);

		int difference = (width - stringWidth) / 2;
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		if (bold) {
			g.drawRect(x + 1, y + 1, width - 2, height - 2);
		}

		g.drawString(text, x + difference, y + height / 2 + 5);

	}

	// Run the task
	public void clicked() {
		task.run();
	}

	// If the button contains a certain x and y coordinate
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

	// Getters and setters
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

	public void setBold(boolean input) {
		this.bold = input;
	}

	public boolean isBold() {
		return bold;
	}
}
