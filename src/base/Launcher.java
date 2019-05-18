package base;
public class Launcher {
	// The main instance of the game
	private static Game game;
	
	public static void main(String[] args) {
		// Create a new game instance and start it
		game = new Game("Game", 800, 600);
		game.start();
	}
	
	// Get the game instance
	public static Game getGame() {
		return game;
	}
}
