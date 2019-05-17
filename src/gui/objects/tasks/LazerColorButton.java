package gui.objects.tasks;

import gui.impl.LazerColorPicker;
import gui.objects.ButtonTask;

import base.Launcher;

public class LazerColorButton implements ButtonTask {


	@Override
	public void run() {
		
		Launcher.getGame().getMenuManager().setCurrentScreen(new LazerColorPicker());

	}

}
