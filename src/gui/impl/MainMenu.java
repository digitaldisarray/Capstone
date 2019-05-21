package gui.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.Toolkit;

import gui.Screen;
import gui.objects.Button;
import gui.objects.tasks.LazerColorButton;
import gui.objects.tasks.MultiplayerStartButton;
import gui.objects.tasks.PlayerColorButton;
import gui.objects.tasks.StartButton;

public class MainMenu implements Screen {

	ArrayList<Button> buttons = new ArrayList<>();
	Color grey = new Color(224, 224, 224);
	Font font = new Font("Serif", 10, 30);

	@Override
	public void init() {
		buttons.add(new Button(400 - 150, -55 + 155 + 300 - (413 / 2), 300, 55, "Singleplayer", new StartButton(),
				Color.GRAY));
		buttons.add(new Button(400 - 150, -55 + 220 + 300 - (413 / 2), 300, 55, "Multiplayer",
				new MultiplayerStartButton(), Color.GRAY));
		buttons.add(new Button(400 - 150, -55 + 285 + 300 - (413 / 2), 300, 55, "Player Color", new PlayerColorButton(),
				Color.GRAY));
		buttons.add(new Button(400 - 150, -55 + 350 + 300 - (413 / 2), 300, 55, "Lazer Color", new LazerColorButton(),
				Color.GRAY));
	}

	@Override
	public void draw(Graphics2D g) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("a.gif");
		g.drawImage(image, 0, 0, 800, 600, null);
		g.setColor(grey);
		g.fillRect(400 - 150, 300 - (413 / 2), 300, 350);
		g.setColor(Color.BLACK);
		g.setFont(font);
		String title = "Bracket";
		int stringWidth = g.getFontMetrics().stringWidth(title);
		int difference = (300 - stringWidth) / 2;
		g.drawString(title, 250 + difference, 150);
		for (Button button : buttons) {
			button.draw(g);
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
