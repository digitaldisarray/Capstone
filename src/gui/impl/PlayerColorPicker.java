package gui.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import gui.Screen;
import gui.objects.Button;

import gui.objects.tasks.BackToMain;
import gui.objects.tasks.CustomLazerColor;
import gui.objects.tasks.CustomPlayerColor;
import gui.objects.tasks.SetLazerColorButton;
import gui.objects.tasks.SetPlayerColorButton;

public class PlayerColorPicker implements Screen {

	ArrayList<Button> buttons = new ArrayList<>();
	Color grey = new Color(224, 224, 224);
	Color grey2 = new Color(190, 203, 211);
	Font font = new Font("Serif", 10, 30);

	// Adds color options
	@Override
	public void init() {
		buttons.add(new Button(250, 170, 100, 109, "", new SetPlayerColorButton(), Color.BLUE));
		buttons.add(new Button(350, 170, 100, 109, "", new SetPlayerColorButton(), Color.ORANGE));
		buttons.add(new Button(450, 170, 100, 109, "", new SetPlayerColorButton(), Color.RED));
		buttons.add(new Button(250, 279, 100, 109, "", new SetPlayerColorButton(), Color.CYAN));
		buttons.add(new Button(350, 279, 100, 109, "", new SetPlayerColorButton(), Color.GREEN));
		buttons.add(new Button(450, 279, 100, 109, "", new SetPlayerColorButton(), Color.magenta));
		buttons.add(new Button(250, 464, 300, 55, "Back to Main", new BackToMain(), grey2));
		buttons.add(new Button(250, 389, 300, 55, "Custom Color (RGB)",
				new CustomLazerColor(), grey2));

	}

	@Override
	public void draw(Graphics2D g) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("a.gif");
		g.drawImage(image, 0, 0, 800, 600, null);
		g.setColor(grey);
		g.fillRect(400 - 150, 300 - (413 / 2), 300, 410);
		g.setColor(Color.BLACK);
		g.setFont(font);
		String title = "Player Color";
		int stringWidth = g.getFontMetrics().stringWidth(title);
		int difference = (300 - stringWidth) / 2;
		g.drawString("Player Color", 250 + difference, 150);
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
