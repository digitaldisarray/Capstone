/*
 * Main instance of the game. COntains main methods, gets the program running.
 * Author: THe Mustangs
 * Last edited: 5/22/2019
 * */

package base;

import java.awt.Rectangle;

import client.Client;
import server.ServerGUI;

public class Launcher {
	// The main instance of the game
	private static Game game;
	private static Client client;
	
	public static void main(String[] args) {
		// Create a new game or server instance based on args and start it
		if(args.length == 0) {
			game = new Game("Game", 800, 600);
			game.start();
			client = new Client();
		} else if(args[0].equals("-server")) {
			ServerGUI server = new ServerGUI();
		}
	}
	
	// Get the game instance
	public static Game getGame() {
		return game;
	}
	
	public static Client getClient() {
		return client;
	}
	
	// Checks if two rectangles are colliding
	public static boolean collides(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {
		return new Rectangle(x1, y1, width1, height1).intersects(new Rectangle(x2, y2, width2, height2));
	}
}
