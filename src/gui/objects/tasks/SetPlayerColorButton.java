package gui.objects.tasks;

import gui.objects.ButtonTask;


import java.awt.Color;

import base.Launcher;

public class SetPlayerColorButton implements ButtonTask {
	

	public void run(Color color) {
//Sets the color of your player
		Launcher.getGame().getPlayer().setPlayerColor(color);

	}

}
