/*Represents your player on the screen
 * Author: The Mustangs
 * Last edited: 5/22/2019
 * 
 * */

package entities;//Epic imports

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import base.BetterSound;
import base.Game;
import base.Launcher;
import client.Protocol;

public class Player extends Entity {

	String name; // The players name

	// The lazers and walls that belong to the player
	ArrayList<Lazer> lazers = new ArrayList<>();
	ArrayList<Wall> walls = new ArrayList<>();

	// The cool down for shooting and walls
	private int lazer_ms_cooldown = 250;
	private final int WALL_MS_COOLDOWN = 2111;

	// The last time that a wall was placed or shot fired
	private long lastShot = 0;
	private long lastWall = 0;

	// The player movement speed
	private final int MOVEMENT_SPEED = 3;

	// Distance that a wall is from the player
	private final int DISTANCE_FROM_PLAYER = 25;

	// Default colors
	private static Color playerColor = Color.BLACK;
	private static Color lazerColor = Color.RED;
	private static Color wallColor = Color.RED;

	// Health variables
	public final int START_HEALTH = 1000;
	private int health = START_HEALTH;

	// Death variables
	private final int DEATH_COOLDOWN = 5000;
	private long lastDeathMS;
	private boolean deadOnMultiplayer = false;

	// Used to optimize player update packets for networking
	boolean hasMoved = false;

	public Player(int x, int y, int width, int height, Color color, String name) {
		super(x, y, width, height, color);
		this.name = name;
	}

	@Override
	public void draw(Graphics2D g) {

		if (!Launcher.getGame().isConnected() || (!deadOnMultiplayer && Launcher.getGame().isConnected()))
			g.setColor(playerColor);
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		for (Lazer lazer : lazers) {
			g.setColor(lazerColor);
			lazer.draw(g);
		}

		for (Wall wall : walls) {
			g.setColor(wallColor);
			wall.draw(g);
		}

		// while the death cooldown is not up
		if (System.currentTimeMillis() - lastDeathMS < DEATH_COOLDOWN) {
			g.setColor(Color.RED);
			g.drawString("YOU DIED", 400 - g.getFontMetrics().stringWidth("YOU DIED") / 2, 300);
		} else if (deadOnMultiplayer == true) {
			deadOnMultiplayer = false;

			setX((int) (Math.random() * 750) + 50);
			setY((int) (Math.random() * 550) + 50);

			// Send respawn packet
			Launcher.getClient().sendToServer(new Protocol().RespawnPacket(getX(), getY(), 1, getID()));
		}
	}

	// Does movement and shooting, also makes it so that player can't go off screen
	@Override
	public void tick() {

		// WASD Movement
		if (!deadOnMultiplayer) {
			if (Launcher.getGame().getWindow().isKeyPressed('w')) {
				hasMoved = true;
				this.setY(getY() - MOVEMENT_SPEED);
			}

			if (Launcher.getGame().getWindow().isKeyPressed('a')) {
				hasMoved = true;
				this.setX(getX() - MOVEMENT_SPEED);
			}

			if (Launcher.getGame().getWindow().isKeyPressed('s')) {
				hasMoved = true;
				this.setY(getY() + MOVEMENT_SPEED);
			}

			if (Launcher.getGame().getWindow().isKeyPressed('d')) {
				hasMoved = true;
				this.setX(getX() + MOVEMENT_SPEED);
			}

			if (hasMoved && Launcher.getGame().isConnected()) {
				// Send player x and y to server
				Launcher.getClient().sendToServer(new Protocol().UpdatePacket(getX(), getY(), getID(), 1));
			}
		}

		hasMoved = false;

		if (this.getX() < 0 + this.getWidth() / 2)
			this.setX(0 + this.getWidth() / 2);

		if (this.getY() < 0 + this.getWidth() / 2)
			this.setY(0 + this.getWidth() / 2);

		if (this.getX() > 800 - this.getWidth() / 2)
			this.setX(800 - this.getWidth() / 2);

		if (this.getY() > 600 - this.getHeight() / 2)
			this.setY(600 - this.getHeight() / 2);

		// Shooting
		if (System.currentTimeMillis() - lastShot > lazer_ms_cooldown && !deadOnMultiplayer) {
			if (Launcher.getGame().getWindow().isMousePressed(MouseEvent.BUTTON1)) {
				// This creates a new lazer. It leverages a lot of other classes and methods to get mouse position
				lazers.add(new Lazer(getX(), getY(), 5, 5, lazerColor, Launcher.getGame().getWindow().getMouseDeg(),
						0.5, 2000));
				if(Game.PlayMusic) {
					BetterSound pew = new BetterSound(new File("resources" +Game.fileSeparator +"Pew.wav"), true, false);
					pew.setVolume(0.7);
				}
				lastShot = System.currentTimeMillis();
			}
		}

		if (System.currentTimeMillis() - lastWall > WALL_MS_COOLDOWN && !deadOnMultiplayer) {
			if (Launcher.getGame().getWindow().isMousePressed(MouseEvent.BUTTON3)) {
				int x = getX(), y = getY(), width = 50, height = 5;

				if (Launcher.getGame().getWindow().getWallDirection() == 4) {
					width = 5;
					height = 50;
					x = getX() + DISTANCE_FROM_PLAYER;
					y = getY() - height / 2;
				}

				if (Launcher.getGame().getWindow().getWallDirection() == 1) {
					width = 50;
					height = 5;
					x = getX() - width / 2;
					y = getY() - DISTANCE_FROM_PLAYER;
				}

				if (Launcher.getGame().getWindow().getWallDirection() == 2) {
					width = 5;
					height = 50;
					x = getX() - DISTANCE_FROM_PLAYER;
					y = getY() - height / 2;
				}

				if (Launcher.getGame().getWindow().getWallDirection() == 3) {
					width = 50;
					height = 5;
					x = getX() - width / 2;
					y = getY() + DISTANCE_FROM_PLAYER;
				}

				walls.add(new Wall(x, y, width, height, Color.RED, Launcher.getGame().getWindow().getWallDirection(),
						2000));

				if(Game.PlayMusic) {
					BetterSound place = new BetterSound(new File("resources" +Game.fileSeparator +"Place.wav"), true, false);
					place.setVolume(0.85);
				}

				lastWall = System.currentTimeMillis();
			}

			// When they die

			if (health <= 0 && Launcher.getGame().isConnected() && Launcher.getGame().isInGame()) {
				lastDeathMS = System.currentTimeMillis();
				health = START_HEALTH;
				deadOnMultiplayer = true;

				// Send death request to server
				Launcher.getClient().sendToServer(new Protocol().DeathPacket(getID()));
			}
		}

		// Used to remove old lazers to avoid an overflow error
		for (int i = 0; i < lazers.size(); i++) {
			Lazer lazer = lazers.get(i);
			lazer.tick();

			// Do this last
			if (lazer.shouldRemove()) {
				lazers.remove(i);
			}
		}
		for (int i = 0; i < walls.size(); i++) {
			Wall wall = walls.get(i);
			wall.tick();

			// Do this last
			if (wall.shouldRemove()) {
				walls.remove(i);
			}
		}
	}

	public void tryCollide(Entity entity) {
		// Make sure we are not colliding with ourself

		if (entity.equals(this)) {
			return;
		}

		// See if we colide with something, if so what is it
		if (Launcher.getGame().collides(getX(), getY(), getWidth(), getHeight(), entity.getX(), entity.getY(),
				entity.getWidth(), entity.getHeight())) {
			if (entity instanceof EnemyLazer) {
				health -= 100;
			}

			if (entity instanceof Zombie) {
				health -= 1;
			}
		}

		if (health == 0 && !Launcher.getGame().isConnected() && Launcher.getGame().isInGame()) {
			final JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent,
					"Haha, you died. Loser. You had " + Zombie.getZombieKills() + " kills");
			Launcher.getGame().setInGame(false);

			for (Entity e : Launcher.getGame().getEntities()) {
				if (e instanceof Zombie) {
					((Zombie) e).setDead();
				}
			}
		}
	}

	public void setPlayerColor(Color color) {
		playerColor = color;
	}

	public void setLazerColor(Color color) {
		lazerColor = color;
	}

	public void setWallColor(Color color) {
		wallColor = color;
	}

	public ArrayList<Lazer> getLazers() {
		return lazers;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public Color getWallColor() {
		return wallColor;
	}

	public void setLazerCooldown(int cooldown) {
		lazer_ms_cooldown = cooldown;
	}

	public int getHealth() {
		return health;
	}

	public int getStartHelath() {
		return START_HEALTH;
	}

	public void resetHealth() {
		health = START_HEALTH;
	}

}
