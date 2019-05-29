/*
 * This class represents a prompt that allows you to input custom rgb values for wall color
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */
package gui.objects.tasks;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import base.Launcher;
import gui.objects.ButtonTask;

public class CustomWallColor implements ButtonTask {
//Button task, prompts user to input an RGB code to set the lazer color
	@Override
	public void run(Color color) {
		final JFrame parent = new JFrame();// Parent JFrame for popup
		int lc1 = 0, lc2 = 0, lc3 = 0;// Initiates RBG int value, auto sets to black

		try {// Tries to make RGB code with input, does nothing if window has invalid values
				// or is exited
			JOptionPane.showMessageDialog(parent,
					"Enter a value between 0 and 255. Invalid inputs will reset color to black");

			lc1 = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter level of red", null));
			lc2 = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter level of green", null));
			lc3 = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter level of blue", null));
		} catch (Exception e) {

		}

		Color wallColor = new Color(lc1, lc2, lc3);//Creates color from input
		Launcher.getGame().getPlayer().setWallColor(wallColor);

	}

}
