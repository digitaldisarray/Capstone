/*
 * Main instance of the game. Contains main methods, gets the program running.
 * Project: Java Capstone
 * Author: The Mustangs
 * Last edited: 5/28/2019
 */

package base;

import client.Client;
import server.ServerGUI;

public class Launcher {
	// The main instance of the game
	private static Game game;
	
	// Instance of the client connection
	private static Client client;
	
	public static void main(String[] args) {
		// Create a new game or server instance based on args and start it
		if(args.length == 0) {
			game = new Game("A game that is epic. (Epic)", 800, 600);
			game.start();
			client = new Client();
		} else if(args[0].equals("-server")) {
			new ServerGUI();
		}
	}
	
	// Get the game instance
	public static Game getGame() {
		return game;
	}
	
	// Get the client instance
	public static Client getClient() {
		return client;
	}
}
