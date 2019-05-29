/*
 * This class represents a button task that sets the lazer color given in the parameters of it
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */
package gui.objects.tasks;

import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class SetLazerColorButton implements ButtonTask {


	@Override
	public void run(Color color) {
		
		//Sets the color of the lazer
		Launcher.getGame().getPlayer().setLazerColor(color);

	}

}
