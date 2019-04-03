import apcs.Window;

public class Main {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	public static void main(String[] args) {
		// Setup
		Window.size(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Menu
		
		// Game
		Game game = new Game("test user");
		if(game.run()) {
			// Return back to menu
		}
		
		// Take back to menu
	}
}
