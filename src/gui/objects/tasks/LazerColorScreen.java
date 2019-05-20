package gui.objects.tasks;

import gui.impl.LazerColorPicker;
import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class LazerColorScreen implements ButtonTask {


	@Override
	public void run(Color color) {
		//Sets screen to one filled with many options to change the color of your lazer
		Launcher.getGame().getMenuManager().setCurrentScreen(new LazerColorPicker());

	}

}
