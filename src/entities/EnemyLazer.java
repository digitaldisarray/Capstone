package entities;

import java.awt.Color;

public class EnemyLazer extends Entity {

	public EnemyLazer(int x, int y, int id) {
		super(x, y, 5, 5, Color.CYAN);
		setID(id);
	}

}
