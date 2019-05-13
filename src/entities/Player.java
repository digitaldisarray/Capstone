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

	public Player(int x, int y, int width, int height, Color color, String name) {
		super(x, y, width, height, color);
		this.name = name;
	}

	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		for (Lazer lazer : lazers) {
			lazer.draw(g);
		}
	}

	public void tick() {

		// WASD Movement
		if (Launcher.getGame().getWindow().isKeyPressed('w'))
			this.setY(getY() - 2);

		if (Launcher.getGame().getWindow().isKeyPressed('a'))
			this.setX(getX() - 2);

		if (Launcher.getGame().getWindow().isKeyPressed('s'))
			this.setY(getY() + 2);

		if (Launcher.getGame().getWindow().isKeyPressed('d'))
			this.setX(getX() + 2);

		if (this.getX() < 0) {
			this.setX(0);
		}

		if (this.getY() < 0) {
			this.setY(0);
		}

		if (this.getX() > 800 - this.getWidth()) {
			this.setX(800 - this.getWidth());
		}

		if (this.getY() > 600 - this.getHeight()) {
			this.setY(600 - this.getHeight());
		}

		// Shooting
		if (System.currentTimeMillis() - lastShot > LAZER_MS_COOLDOWN) {
			if (Launcher.getGame().getWindow().isMousePressed(MouseEvent.BUTTON1)) {
				lazers.add(new Lazer(getX(), getY(), 3, 20, Color.RED, Launcher.getGame().getWindow().getMouseDeg()));
				lastShot = System.currentTimeMillis();
			}
		}

		for (Lazer lazer : lazers) {
			lazer.tick();
		}
	}

}
