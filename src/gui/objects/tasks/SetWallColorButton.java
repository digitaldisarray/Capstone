package gui.objects.tasks;

import gui.objects.ButtonTask;


import java.awt.Color;

import base.Launcher;

public class SetWallColorButton implements ButtonTask {
	

	public void run(Color color) {

		Launcher.getGame().getPlayer().setWallColor(color);

	}

}
