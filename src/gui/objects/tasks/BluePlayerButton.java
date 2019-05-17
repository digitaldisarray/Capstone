package gui.objects.tasks;

import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class BluePlayerButton implements ButtonTask {


	@Override
	public void run() {
		Launcher.getGame().getPlayer().setPlayerColor(Color.BLUE);

	}

}
