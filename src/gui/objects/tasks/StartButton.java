package gui.objects.tasks;

import gui.objects.ButtonTask;

import base.Launcher;

public class StartButton implements ButtonTask {


	@Override
	public void run() {
		
		Launcher.getGame().setInGame(true);

	}

}
