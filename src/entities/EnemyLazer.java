package entities;
/*Class: EnemyLazer
 * 
 * Purpose: Lazers for the enemy players in multiplayer
 * Author: The Mustangs
 * Last edited: 5/22/2019
 * */
import java.awt.Color;

import base.Launcher;
import client.Protocol;

public class EnemyLazer extends Entity {

	private String uuid;

	private boolean remove;
	private long startTime;
	private long duration;

	//creates the enemy lazer and starts the timer
	public EnemyLazer(int x, int y, String uuid) {
		super(x, y, 5, 5, Color.MAGENTA);
		this.uuid = uuid;

		remove = false;
		startTime = System.currentTimeMillis();
		this.duration = 2000;
	}

	//removes the lazer when it is time to remove
	public void tick() {
		long time = System.currentTimeMillis();
		if (time - startTime > duration) {
			remove = true;
			Launcher.getClient().sendToServer(new Protocol().ShotRemovePacket(getStringUUID()));
		}
	}

	//returns string UUID
	public String getStringUUID() {
		return uuid;
	}

	//returns the boolean of remove
	public boolean shouldRemove() {
		return remove;
	}

}
