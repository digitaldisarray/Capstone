package gui;

import java.awt.Graphics2D;

import gui.impl.Loading;

public class MenuManager {
	Screen current;
	
	// Menu manager constructor
	public MenuManager() {
		// Set the current menu to the main menu and initialize it
		current = new Loading();
		current.init();
	}
	
	// Draw the current screen
	public void draw(Graphics2D g) {
		current.draw(g);
	}
	
	
	// Set the current screen to a new one
	public void setCurrentScreen(Screen screen) {
		this.current = screen;
		this.current.init();
	}
	
	// Get whatever the current screen is
	public Screen getCurrent() {
		return this.current;
	}
}
