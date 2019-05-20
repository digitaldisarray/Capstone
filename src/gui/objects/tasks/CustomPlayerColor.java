package gui.objects.tasks;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import base.Launcher;
import gui.objects.ButtonTask;

public class CustomPlayerColor implements ButtonTask {

	@Override
	public void run(Color color) {
		final JFrame parent = new JFrame();
		int pc1 = 0, pc2 = 0, pc3 = 0; //Initializes integers used to create RGB so the color is black if not changed

		try {//Tries to create RBG value from input
			JOptionPane.showMessageDialog(parent, "Enter a value between 0 and 255. Invalid inputs will reset color to black");

			pc1 = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter level of red", null));
			pc2 = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter level of green", null));
			pc3 = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter level of blue", null));
		} catch (Exception e) {

		}

	
			Color playerColor = new Color(pc1, pc2, pc3);//Creates color from input
			Launcher.getGame().getPlayer().setPlayerColor(playerColor);
	
	}

}
