package gui.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import base.Launcher;
import base.Window;

import java.awt.Image;
import java.awt.Toolkit;

import gui.Screen;
import gui.objects.Button;
import gui.objects.tasks.LazerColorButton;
import gui.objects.tasks.MultiplayerStartButton;
import gui.objects.tasks.PlayerColorButton;
import gui.objects.tasks.StartButton;
import gui.objects.tasks.WallColorButton;

public class MainMenu implements Screen {

	ArrayList<Button> buttons = new ArrayList<>();
	Color grey = new Color(224, 224, 224);
	Font font = new Font("Serif", 10, 30);
	Color grey2 = new Color(190, 203, 211);

	@Override
	public void init() {
		buttons.add(
				new Button(250, 194, 300, 55, "Singleplayer", new StartButton(), grey2));
		buttons.add(new Button(250, 259, 300, 55, "Multiplayer",
				new MultiplayerStartButton(), grey2));
		buttons.add(new Button(250, 324, 300, 55, "Player Color", new PlayerColorButton(),
				grey2));
		buttons.add(new Button(250, 389, 300, 55, "Lazer Color", new LazerColorButton(),
				grey2));
		buttons.add(new Button(250, 454, 300, 55, "Wall Color", new WallColorButton(),
				grey2));

		JOptionPane.showMessageDialog(Launcher.getGame().getWindow().getFrame(), "WASD or arrow keys to move. Left-Click: for Shoot. Right-Click to place wall");
		Launcher.getGame().hasViewedMessage(true);
	}

	@Override
	public void draw(Graphics2D g) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("a.gif");
		g.drawImage(image, 0, 0, 800, 600, null);
		g.setColor(grey);
		g.fillRect(250, 94, 300, 405);
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
