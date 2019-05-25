package entities;

import java.awt.Color;

public class EnemyPlayer extends Entity {

	public EnemyPlayer(int x, int y, int id) {
		super(x, y, 10, 10, Color.CYAN);
		setID(id);
	}
	
}
