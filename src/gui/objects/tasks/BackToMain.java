package gui.objects.tasks;

import gui.impl.MainMenu;
import gui.impl.PlayerColorPicker;
import gui.objects.ButtonTask;

import base.Launcher;

public class BackToMain implements ButtonTask {


	@Override
	public void run() {
		
		Launcher.getGame().getMenuManager().setCurrentScreen(new MainMenu());

	}

}
