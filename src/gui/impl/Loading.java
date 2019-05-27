/*
 * This class represents an animated screen that runs for a few seconds whe the program starts.
 * It has no functional value, it is purely for looks
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */

package gui.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import base.Launcher;
import gui.Screen;
import gui.objects.Button;
import gui.objects.tasks.BackToMain;

public class Loading implements Screen {

	ArrayList<Button> buttons = new ArrayList<>();
	Color grey = new Color(224, 224, 224);
	Color grey2= new Color(190, 203, 211);
	Font font = new Font("Serif", 10, 30);
	long startTime = System.currentTimeMillis();

	@Override
	public void init() {// Adds color options
		buttons.add(new Button(798, 0, 2, 2, "", new BackToMain(), grey2));
	}

	@Override
	public void draw(Graphics2D g) {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("A.Lo.gif");

		g.drawImage(image, 350, 150, 100, 100, null);
		g.drawRect(300, 290, 200, 20);
		double rWidth = 0.0;
		long currentTime = 0;
		currentTime = System.currentTimeMillis();
		
		for (Button button : buttons) {
			button.draw(g);
		}
		g.setColor(Color.GREEN);
		if (rWidth != 200.0) {
			rWidth = (currentTime - startTime) / 25; // 25 for 5 seconds, 50 for 10
			g.fillRect(301, 291, (int) rWidth, 19);

		}
		if (startTime + 5000 <= currentTime) {
			Launcher.getGame().getMenuManager().setCurrentScreen(new MainMenu());
		}
	}

	@Override
	public void passPressEvent(int x, int y) {
		for (Button button : buttons) {
			if (button.contains(x, y)) {
				button.setBold(true);
			}
		}
	}

	@Override
	public void passReleaseEvent(int x, int y) {
		for (Button button : buttons) {
			if (button.isBold()) {
				button.setBold(false);
			}

			if (button.contains(x, y)) {
				button.clicked();
			}
		}
	}

}
