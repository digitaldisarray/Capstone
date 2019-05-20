package gui.objects.tasks;

import gui.impl.MainMenu;
import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class BackToMain implements ButtonTask {


	@Override
	public void run(Color color) {
		
		Launcher.getGame().getMenuManager().setCurrentScreen(new MainMenu());

	}

}
