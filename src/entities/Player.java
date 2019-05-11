package entities;

import java.awt.Color;

import base.Launcher;

public class Player extends Entity {

	String name; // The players name

	public Player(int x, int y, int width, int height, Color color, String name) {
		super(x, y, width, height, color);
		this.name = name;
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
	}

}
