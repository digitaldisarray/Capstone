package gui.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import gui.Screen;
import gui.objects.Button;
import gui.objects.tasks.testTask;

public class MainMenu implements Screen{
	private int xClick, yClick;
	ArrayList<Button> buttons = new ArrayList<>();

	@Override

	

	public void init() {
		buttons.add(new Button(10, 10, 100, 40, "Hello World!", Color.GRAY, new testTask()));

	}

	@Override
	public void draw(Graphics2D g) {
		for (Button button : buttons) {
			button.draw(g);
		}
	}


	public void actionPerformed(ActionEvent e) {

	}
	
	
	public void passKeyEvent(int x, int y) {
		for (Button button : buttons) {
			if(button.contains(x, y))	
			button.clicked();
			
		}
	}
	
	
}
