package entities;

import java.awt.Color;

import base.Launcher;

public class Zombie extends Entity {

	boolean alive;

	public Zombie(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		alive = true;
	}

	public Zombie(int x, int y) {
		super(x, y, 20, 20, Color.GRAY);
		alive = true;
	}

	public void tick() {
		// TODO: Move towards player
	}

	public void tryCollide(Entity entity) {
		// Make sure we are not colliding with ourself
		if (entity.equals(this)) {
			return;
		}

		// See if we colide with something, if so what is it
		if (Launcher.collides(getX(), getY(), getWidth(), getHeight(), entity.getX(), entity.getY(), entity.getWidth(),
				entity.getHeight())) {
			if (entity instanceof Lazer) {
				alive = false;
			}
		}
	}

	public boolean isAlive() {
		return alive;
	}
}
