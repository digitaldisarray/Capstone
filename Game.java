import apcs.Window;

public class Game {
	private String username;

	public Game(String username) {
		this.username = username;
	}

	public boolean run() {
		// Insert networking stuff that gets other players and objects from the server

		// Main loop
		boolean gameIsRunning = true;
		while (gameIsRunning) {
			Window.out.background(100, 100, 100);
			Window.frame();
		}
		
		return true;
	}
}
