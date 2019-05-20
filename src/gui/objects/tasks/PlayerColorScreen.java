package gui.objects.tasks;

import gui.impl.PlayerColorPicker;
import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class PlayerColorScreen implements ButtonTask {


	@Override
	public void run(Color color) {
		//Sets screen to one with many options to change the color of your player
		Launcher.getGame().getMenuManager().setCurrentScreen(new PlayerColorPicker());

	}

}
