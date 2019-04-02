import apcs.Window;

public class Main {
	public static void main(String[] args) {
		// Setup
		Window.size(800, 600);
		
		// Menu
		
		// Game
		Game game = new Game("test user");
		if(game.run()) {
			// Return back to menu
		}
		
		// Take back to menu
	}
}
