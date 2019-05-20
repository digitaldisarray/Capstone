package base;

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
}
