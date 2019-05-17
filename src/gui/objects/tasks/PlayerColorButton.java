package gui.objects.tasks;

import gui.impl.PlayerColorPicker;
import gui.objects.ButtonTask;

import base.Launcher;

public class PlayerColorButton implements ButtonTask {


	@Override
	public void run() {
		
		Launcher.getGame().getMenuManager().setCurrentScreen(new PlayerColorPicker());

	}

}
