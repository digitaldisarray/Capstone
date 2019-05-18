package gui.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import gui.Screen;
import gui.objects.Button;
import gui.objects.tasks.LazerColorButton;
import gui.objects.tasks.PlayerColorButton;
import gui.objects.tasks.StartButton;

public class MainMenu implements Screen {

	ArrayList<Button> buttons = new ArrayList<>();
	Color grey = new Color(224, 224, 224);
	Font font = new Font("Serif", 10, 30);

	@Override
	public void init() {
		buttons.add(new Button(400 - 150, -55 + 350 + 300 - (413 / 2), 300, 55, "Enter Game", new StartButton()));
		buttons.add(
				new Button(400 - 150, -55 + 250 + 300 - (413 / 2), 300, 55, "Player Color", new PlayerColorButton()));
		buttons.add(new Button(400 - 150, -55 + 150 + 300 - (413 / 2), 300, 55, "Lazer Color", new LazerColorButton()));

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(grey);
		g.fillRect(400 - 150, 300 - (413 / 2), 300, 350);
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("Bracket", 345, 150);
		for (Button button : buttons) {
			button.draw(g);
		}
	}

	public void actionPerformed(ActionEvent e) {

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
