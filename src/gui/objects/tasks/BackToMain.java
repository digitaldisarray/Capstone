/*
 * This class represents a button task that sets the screen to main meny
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */
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
