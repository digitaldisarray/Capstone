package entities;

import java.awt.Color;

import base.Launcher;
import client.Protocol;

public class EnemyLazer extends Entity {

	private String uuid;

	private boolean remove;
	private long startTime;
	private long duration;

	public EnemyLazer(int x, int y, String uuid) {
		super(x, y, 5, 5, Color.CYAN);
		this.uuid = uuid;

		remove = false;
		startTime = System.currentTimeMillis();
		this.duration = 2000;
	}

	public void tick() {
		long time = System.currentTimeMillis();
		if (time - startTime > duration) {
			remove = true;
			Launcher.getClient().sendToServer(new Protocol().ShotRemovePacket(getStringUUID()));
		}
	}

	public String getStringUUID() {
		return uuid;
	}

	public boolean shouldRemove() {
		return remove;
	}

}
