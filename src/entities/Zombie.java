package entities;

import java.awt.Color;

public class Zombie extends Entity {

	public Zombie(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
	}
	
	public Zombie(int x, int y) {
		super(x, y, 20, 20, Color.GRAY);
	}
	
	
	
}
