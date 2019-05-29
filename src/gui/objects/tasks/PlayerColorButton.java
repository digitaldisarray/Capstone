/*
 * This class represents a button task that sets the screen to the player color picker
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */
package gui.objects.tasks;

import gui.impl.PlayerColorPicker;
import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class PlayerColorButton implements ButtonTask {


	@Override
	public void run(Color color) {
		
		Launcher.getGame().getMenuManager().setCurrentScreen(new PlayerColorPicker());

	}

}
