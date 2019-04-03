import java.util.ArrayList;

import apcs.Window;

public class Game {
	private String username;
	private ArrayList<Entity> entities = new ArrayList<>();

	public Game(String username) {
		this.username = username;

		// Debug entities
		// entities.add(new Entity);
	}

	public boolean run() {
		// Init a player
		// TODO: Make a class selection menu
		Player player = new Player(Classes.GUNNER);

		// Insert networking stuff that gets other players and objects from the server

		// Main loop
		boolean gameIsRunning = true;
		while (gameIsRunning) {
			Window.out.background(100, 100, 100);

			// Draw and update the player
			player.update();
			player.draw();

			// Draw and update entities
			for (Entity ent : entities) {
				ent.update();
				ent.draw();
			}

			Window.frame();
		}

		return true;
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}
}
