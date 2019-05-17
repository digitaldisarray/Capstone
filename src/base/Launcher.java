package base;
public class Launcher {
	private static Game game;
	
	public static void main(String[] args) {
		game = new Game("Game", 800, 600);
		game.start();
	}
	
	public static Game getGame() {
		return game;
	}
}
