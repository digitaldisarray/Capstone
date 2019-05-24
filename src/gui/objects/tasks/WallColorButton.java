package gui.objects.tasks;

import gui.impl.PlayerColorPicker;
import gui.impl.WallColorPicker;
import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class WallColorButton implements ButtonTask {


	@Override
	public void run(Color color) {
		
		Launcher.getGame().getMenuManager().setCurrentScreen(new WallColorPicker());

	}

}
