package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import base.Launcher;

public class Player extends Entity {

	String name; // The players name

	ArrayList<Lazer> lazers = new ArrayList<>();
	private final int LAZER_MS_COOLDOWN = 250;
	private long lastShot = 0;
	private final int MOVEMENT_SPEED = 3;
	private static Color playerColor = Color.BLACK;
	private static Color lazerColor = Color.RED;

	public Player(int x, int y, int width, int height, Color color, String name) {
		super(x, y, width, height, color);
		this.name = name;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(playerColor);
		g.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());

		for (Lazer lazer : lazers) {
			g.setColor(lazerColor);
			lazer.draw(g);
		}
	}

	// Does movement and shooting, also makes it so that player can't go off screen
	@Override
	public void tick() {

		// WASD Movement
		if (Launcher.getGame().getWindow().isKeyPressed('w'))
			this.setY(getY() - MOVEMENT_SPEED);

		if (Launcher.getGame().getWindow().isKeyPressed('a'))
			this.setX(getX() - MOVEMENT_SPEED);

		if (Launcher.getGame().getWindow().isKeyPressed('s'))
			this.setY(getY() + MOVEMENT_SPEED);

		if (Launcher.getGame().getWindow().isKeyPressed('d'))
			this.setX(getX() + MOVEMENT_SPEED);

		if (this.getX() < 0 + this.getWidth() / 2)
			this.setX(0 + this.getWidth() / 2);

		if (this.getY() < 0 + this.getWidth() / 2)
			this.setY(0 + this.getWidth() / 2);

		if (this.getX() > 800 - this.getWidth() / 2)
			this.setX(800 - this.getWidth() / 2);

		if (this.getY() > 600 - this.getHeight() / 2)
			this.setY(600 - this.getHeight() / 2);

		// Shooting
		if (System.currentTimeMillis() - lastShot > LAZER_MS_COOLDOWN) {
			if (Launcher.getGame().getWindow().isMousePressed(MouseEvent.BUTTON1)) {
				// This creates a new lazer. It leverages a lot of other classes and methods to get mouse position
				lazers.add(new Lazer(getX(), getY(), 5, 5, lazerColor, Launcher.getGame().getWindow().getMouseDeg(),
						0.5, 2000));

				lastShot = System.currentTimeMillis();
			}
		}

		// Used to remove old lazers to avoid an overflow error
		for (int i = 0; i < lazers.size(); i++) {
			Lazer lazer = lazers.get(i);
			lazer.tick();

			// Do this last
			if (lazer.shouldRemove()) {
				lazers.remove(i);
			}
		}
	}

	public void setPlayerColor(Color color) {
		playerColor = color;
	}

	public void setLazerColor(Color color) {
		lazerColor = color;
	}
	
	public ArrayList<Lazer> getLazers() {
		return lazers;
	}
	
}
