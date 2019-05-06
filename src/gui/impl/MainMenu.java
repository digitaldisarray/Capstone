package gui.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import gui.Screen;
import gui.objects.Button;

public class MainMenu implements Screen, MouseListener {

	ArrayList<Button> buttons = new ArrayList<>();
	

	
	@Override
	public void init() {
		buttons.add(new Button(10, 10, 100, 40, "Hello World!", Color.GRAY, null));
		
	}

	@Override
	public void draw(Graphics2D g) {
		for (Button button : buttons) {
			button.draw(g);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Button button : buttons) {
			if (e.getX() >= button.getX() && e.getX() <= button.getX() + button.getWidth() && e.getY() >= button.getY()
					&& e.getY() <= button.getY() + button.getHeight()) {
				button.clicked();
			}
		}
		

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Add hover effect

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Add hover effect

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void actionPerformed(ActionEvent e) {

	}
}
