package entities;

import java.awt.Color;

public class EnemyLazer extends Entity {
	
	private String uuid;
	
	public EnemyLazer(int x, int y, String uuid) {
		super(x, y, 5, 5, Color.CYAN);
		this.uuid = uuid;
	}
	
	public String getStringUUID() {
		return uuid;
	}

}
