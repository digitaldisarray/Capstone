package base;

import server.ServerGUI;

public class Launcher {
	// The main instance of the game
	private static Game game;
	
	public static void main(String[] args) {
		// Create a new game or server instance based on args and start it
		if(args.length == 0) {
			game = new Game("Game", 800, 600);
			game.start();
		} else if(args[0].equals("-server")) {
			ServerGUI serverGUI = new ServerGUI();
		}
	}
	
	// Get the game instance
	public static Game getGame() {
		return game;
	}
}
