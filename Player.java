import java.awt.Color;

import apcs.Window;

public class Player {

	/*
	 * Player classes will change: - Color - Size - Block placing speed - Block size
	 * - Block life span
	 */

	private int x;
	private int y;
	private int width;
	private int height;

	private int speed;

	private Color color;

	private int deathCooldown = 0; // how long the player has been dead for
	private PlayerStates state = PlayerStates.ALIVE;
	private Classes playerClass;

	public Player(Classes playerClass) {
		this.playerClass = playerClass;

		// Generate the players starting spawn location
		int[] start = getSpawnLocation();
		this.x = start[0];
		this.y = start[1];

		switch (playerClass) {
		case GUNNER:
			this.width = 28;
			this.height = 28;
			this.speed = 7;
			this.color = Colors.LIGHT_GREEN;
			break;
		}

	}

	public void draw() {
		Window.out.color(color);
		Window.out.square(x, y, width);
	}

	public void update() {
		switch (state) {
		case ALIVE:
			alive();
			break;
		case DEAD:
			dead();
			break;
		case MENU:
			menu();
			break;
		}
	}

	private void alive() {
		// Get all active walls on the map

		// Do movement & check collisions
		if (Window.key.pressed('w') && y > 0) {
			y -= speed;
		}

		if (Window.key.pressed('a') && x > 0) {
			x -= speed;
		}

		if (Window.key.pressed('s') && y + height < Main.WINDOW_HEIGHT) {
			y += speed;
		}

		if (Window.key.pressed('d') && x + width < Main.WINDOW_WIDTH) {
			x += speed;
		}
	}

	private void dead() {
		this.setColor(Colors.RED);
		if (deathCooldown == 10000) {
			state = PlayerStates.ALIVE;
		}
		deathCooldown++;
	}

	private void menu() {
		// Maybe do this in another class?
	}

	private int[] getSpawnLocation() {
		// Generate random x
		int[] location = { (int) (Math.random() * Main.WINDOW_WIDTH), (int) (Math.random() * Main.WINDOW_HEIGHT) };

		// Make sure not colliding with any other entities

		// Make sure not clipping outside of the play-area

		return location;
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

	public void setColor(Color color) {
		this.color = color;
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

	public Color getColor() {
		return this.color;
	}
}
