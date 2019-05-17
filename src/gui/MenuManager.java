package gui;

import java.awt.Graphics2D;

import gui.impl.MainMenu;

public class MenuManager {
	Screen current;
	
	public MenuManager() {
		current = new MainMenu();
		current.init();
	}
	
	public void draw(Graphics2D g) {
		current.draw(g);
	}
	
	public void setCurrentScreen(Screen screen) {
		this.current = screen;
		this.current.init();
	}
	
	public Screen getCurrent() {
		return this.current;
	}
}
