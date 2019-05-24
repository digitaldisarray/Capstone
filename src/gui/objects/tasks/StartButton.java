package gui.objects.tasks;

import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class StartButton implements ButtonTask {

	@Override
	public void run(Color color) {
		Launcher.getGame().setInGame(true);
	}

}
