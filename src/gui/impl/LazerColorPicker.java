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
import gui.objects.tasks.SetLazerColorButton;

// New screen filled with color options for your lazer
public class LazerColorPicker implements Screen {

	ArrayList<Button> buttons = new ArrayList<>();
	Color grey = new Color(224, 224, 224);
	Font font = new Font("Serif", 10, 30);
	Color grey2 = new Color(190, 203, 211);

	// Adds all the options to the screen
	@Override
	public void init() {
		buttons.add(new Button(250, 170, 100, 109, "", new SetLazerColorButton(), Color.BLUE));
		buttons.add(new Button(350, 170, 100, 109, "", new SetLazerColorButton(), Color.ORANGE));
		buttons.add(new Button(450, 170, 100, 109, "", new SetLazerColorButton(), Color.RED));
		buttons.add(new Button(250, 279, 100, 109, "", new SetLazerColorButton(), Color.CYAN));
		buttons.add(new Button(350, 279, 100, 109, "", new SetLazerColorButton(), Color.GREEN));
		buttons.add(new Button(450, 279, 100, 109, "", new SetLazerColorButton(), Color.magenta));
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
		g.fillRect(250, 94, 300, 410);
		g.setColor(Color.BLACK);
		g.setFont(font);
		String title = "Lazer Color";
		int stringWidth = g.getFontMetrics().stringWidth(title);
		int difference = (300 - stringWidth) / 2;
		g.drawString(title, 250 + difference, 150);
		for (Button button : buttons) {
			button.draw(g);
		}
	}

	// Visual to indicate to user that button has been clicked
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
