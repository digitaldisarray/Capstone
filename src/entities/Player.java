package entities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import base.Launcher;
import base.Window;

public class Player extends Entity {

	String name;
	int keyPressed;
	
	public Player(int x, int y, int width, int height, Color color, String name) {
		super(x, y, width, height, color);
		this.name = name;
	}
	
	public void tick(KeyEvent e) {
		
	}
	
	

}
