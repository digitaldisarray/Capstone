/*
 * This class represents a task that sets the screen to the lazerColorPicker window
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */
package gui.objects.tasks;

import gui.impl.LazerColorPicker;
import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class LazerColorButton implements ButtonTask {


	@Override
	public void run(Color color) {
		
		Launcher.getGame().getMenuManager().setCurrentScreen(new LazerColorPicker());

	}

}
