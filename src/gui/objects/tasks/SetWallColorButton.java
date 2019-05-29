/*
 * This class represents a button task that sets the wall color given in the parameters of it
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */
package gui.objects.tasks;

import gui.objects.ButtonTask;


import java.awt.Color;

import base.Launcher;

public class SetWallColorButton implements ButtonTask {
	

	public void run(Color color) {

		Launcher.getGame().getPlayer().setWallColor(color);

	}

}
