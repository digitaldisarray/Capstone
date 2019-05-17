package gui.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gui.Screen;
import gui.objects.Button;
import gui.objects.tasks.BackToMain;
import gui.objects.tasks.OrangeLazerButton;

public class LazerColorPicker implements Screen {
	private int xClick, yClick;
	ArrayList<Button> buttons = new ArrayList<>();
	Color grey = new Color(224, 224, 224);
	Font font = new Font("Serif", 10, 30);

	@Override
	public void init() {
		buttons.add(new Button(400 - 150, -55 + 350 + 300 - (413 / 2), 300, 55, "Set Lazer Color Orange", Color.RED,
				new OrangeLazerButton()));
		buttons.add(new Button(400 - 150, -55 + 250 + 300 - (413 / 2), 300, 55, "Back to Main menu", Color.RED,
				new BackToMain()));

	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(grey);
		g.fillRect(400 - 150, 300 - (413 / 2), 300, 350);
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("Player Color", 345, 150);
		for (Button button : buttons) {
			button.draw(g);
		}
	}

	@Override
	public void passPressEvent(int x, int y) {
		for (Button button : buttons) {
			if (button.contains(x, y)) {
				button.clicked();
			}
		}
	}

	@Override
	public void passReleaseEvent(int x, int y) {
		for (Button button : buttons) {
			if (button.isBold()) {
				button.setBold(false);
			}
		}
	}

}
