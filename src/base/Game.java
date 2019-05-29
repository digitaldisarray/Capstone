/*Class: Game
 * 
 * Purpose: To set framework for the game. Creates some graphics objects,windows, and menus
 * Author: The Mustangs
 * Last edited: 5/22/2019
 * */

package base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import entities.EnemyLazer;
import entities.EnemyPlayer;
import entities.Entity;
import entities.Lazer;
import entities.Player;
import entities.Wall;
import entities.Zombie;
import gui.MenuManager;

public class Game implements Runnable {

	private BetterSound music;
	public static final String fileSeparator = System.getProperty("file.separator");
	public static Boolean PlayMusic = true;
	
	// Window properties
	private Window window;
	public int width, height;
	public String title;

	// Thread variables
	private boolean running = false;
	private Thread thread;

	// Game state booleans
	private boolean inGame = false;
	private boolean connected = false;

	// The menu manager object
	private MenuManager mManager;

	// Graphics objects
	private BufferStrategy bs;
	private Graphics2D g;

	// The local player
	private Player player;

	// Store the last recorded fps
	private int lastFPS;

	// The entities in the game
	private ArrayList<Entity> entities = new ArrayList<>();

	// Used for filtering entities in the game loop
	private ArrayList<Entity> filtered = new ArrayList<>();

	// For filtering entities outside of game loop
	private ArrayList<Integer> removals = new ArrayList<>();

	// Object for getting new info from server
	private ClientRecivingThread clientThread;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	// Init anything we need here like asset managers
	private void init() {
		music = new BetterSound(new File("resources" +fileSeparator +"BackMusic.wav"), true, true);
		window = new Window(title, width, height);
		mManager = new MenuManager();
		player = new Player((int) (Math.random() * 700) + 50, (int) (Math.random() * 500) + 50, 10, 10,
				new Color(0, 100, 80), "Test Player");
		
		
	}

	int x = 0;

	private void tick() {
		x += 1;
	}

	// Render things on the display
	private void render() {
		// Get the buffer strategy, get the graphics, and scale
		bs = window.getCanvas().getBufferStrategy();
		if (bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		g = (Graphics2D) bs.getDrawGraphics();
		g.scale(window.getCanvas().getWidth() / 800.0, window.getCanvas().getHeight() / 600.0);

		g.clearRect(0, 0, width, height);

		if (!inGame) {
			mManager.draw(g);
		} else {
			// Draw and move the player
			player.tick();
			player.draw(g);

			// SINGLEPLAYER
			if (!connected) {
				// Spawn zombies
				spawnZombie();

				// Remove dead zombies
				for (Entity e : entities) {
					if (e instanceof Zombie) {
						if ((Zombie) e != null) {
							if (((Zombie) e).isAlive()) {
								filtered.add(e);
							}
						}
					} else {
						filtered.add(e);
					}
				}
				entities = filtered;
				filtered = new ArrayList<Entity>();

				// Draw, collide, and update zombies
				for (Entity e : entities) {
					// Collide lazers and entities

					for (Lazer l : player.getLazers()) {
						e.tryCollide(l);
					}

					for (Wall l : player.getWalls()) {
						e.tryCollide(l);
					}

					if (player.getWalls().size() == 0) {
						if (e instanceof Zombie) {
							((Zombie) e).setMovingState(true);
						}

					}
					player.tryCollide(e);

					e.tick();
					e.draw(g);
				}

				g.setColor(Color.BLACK);
				g.drawString("Eliminations: " + Zombie.getZombieKills(),
						720 - g.getFontMetrics().stringWidth("Eliminations: " + Zombie.getZombieKills()), 15);
			} else {
				// MUTLIPLAYER

				g.drawRect(680, 20, 110, 20);
				g.drawString(
						"Health: " + Launcher.getGame().getPlayer().getHealth() + "/"
								+ Launcher.getGame().getPlayer().getStartHelath(),
						725 - g.getFontMetrics().stringWidth("Health: "), 35);

				// Draw, collide, and update things
				for (Entity e : entities) {
					if (e instanceof EnemyLazer) {
						player.tryCollide(e);
					}

					e.tick();
					e.draw(g);

					// Remove those dead lazers
					if (e instanceof EnemyLazer) {
						if (((EnemyLazer) e).shouldRemove()) {
							removals.add(entities.indexOf(e));
						}
					}
				}

				for (Integer i : removals) {
					filtered.remove(i.intValue());
				}
				removals.clear();

				entities = filtered;
			}
		}

		if (inGame) {
			if (Launcher.getGame().getPlayer().getHealth() > 500)
				g.setColor(Color.GREEN);
			if (Launcher.getGame().getPlayer().getHealth() > 250 && Launcher.getGame().getPlayer().getHealth() <= 500)
				g.setColor(Color.ORANGE);
			if (Launcher.getGame().getPlayer().getHealth() <= 250)
				g.setColor(Color.RED);

			g.fillRect(680, 20, (int) (((double) Launcher.getGame().getPlayer().getHealth()
					/ Launcher.getGame().getPlayer().getStartHelath()) * 110), 20);
			g.setColor(Color.BLACK);
			g.drawRect(680, 20, 110, 20);
			g.drawString(
					"Health: " + Launcher.getGame().getPlayer().getHealth() + "/"
							+ Launcher.getGame().getPlayer().getStartHelath(),
					725 - g.getFontMetrics().stringWidth("Health: "), 35);
		}

		// Draw FPS
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + lastFPS, 1, 15);

		// End Graphics
		bs.show();
		g.dispose();
	}

	long lastSpawn = System.currentTimeMillis();
	int spawnRate = 2000;

	private void spawnZombie() {
		if (System.currentTimeMillis() - lastSpawn >= spawnRate) {
			entities.add(new Zombie((int) (Math.random() * 700 + 50), (int) (Math.random() * 700 + 50), 20, 20,
					Color.GRAY, 1.0));
			
			if(spawnRate > 400) {
				spawnRate -= 50;
			}
			
			lastSpawn = System.currentTimeMillis();
		}
	}

	// Run the game
	@Override
	public void run() {
		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}

			if (timer >= 1000000000) {
				// System.out.println("FPS/TPS: " + ticks);
				lastFPS = ticks;
				ticks = 0;
				timer = 0;
			}
		}

		stop();
	}

	// Start the game instance
	public synchronized void start() {
		if (running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// Stop the game instance
	public synchronized void stop() {
		if (!running) {
			return;
		}

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Get the scale factor for the x axis
	public double getXScaleFactor() {
		return window.getCanvas().getWidth() / 800.0;
	}

	// Get the scale factor for the y axis
	public double getYScaleFactor() {
		return window.getCanvas().getHeight() / 600.0;
	}

	// Get the array list of current entities
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	// Get the window object used by the game
	public Window getWindow() {
		return this.window;
	}

	// Get the player instance for the game
	public Player getPlayer() {
		return player;
	}

	// Set if the player is in the game or not
	public void setInGame(boolean b) {
		inGame = b;
	}

	// Get if the player is in the game or not
	public boolean isInGame() {
		return inGame;
	}

	// Get the menu manager for the game
	public MenuManager getMenuManager() {
		return mManager;
	}
	
	// Set if the player is connected to the server or not
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	// Start the client connection thread
	public void startClientThread() {
		clientThread = new ClientRecivingThread(Launcher.getClient().getSocket());
		clientThread.start();
	}

	// Get if the player is connected to the server
	public boolean isConnected() {
		return connected;
	}
	
	public static void setMusic(boolean b) {
		PlayMusic = b;
	}

	public BetterSound getMusic() {
		return music;
	}
	// Checks if two rectangles are colliding
	public boolean collides(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {
		return new Rectangle(x1, y1, width1, height1).intersects(new Rectangle(x2, y2, width2, height2));
	}

	public class ClientRecivingThread extends Thread {
		Socket clientSocket;
		DataInputStream reader;

		public ClientRecivingThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
			try {
				reader = new DataInputStream(clientSocket.getInputStream());
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

		boolean exists = false;
		boolean isRunning = true;

		public void run() {
			while (isRunning) {
				String sentence = "";
				try {
					sentence = reader.readUTF();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if (sentence.startsWith("ID")) {
					int id = Integer.parseInt(sentence.substring(2));
					player.setID(id);
//					System.out.println("My ID: " + id);

				} else if (sentence.startsWith("NewClient")) {
					int pos1 = sentence.indexOf(',');
					int pos2 = sentence.indexOf('-');
					int pos3 = sentence.indexOf('|');
					int x = Integer.parseInt(sentence.substring(9, pos1));
					int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
					int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));

					// Is not local player
					if (id != player.getID()) {
						// Does not already exist in list
						filtered = entities;
						for (Entity e : filtered) {
							if (e instanceof EnemyPlayer && e.getID() == id) {
								exists = true;
							}
						}

						// If it does not exists in the array list, add it
						if (!exists) {
							filtered.add(new EnemyPlayer(x, y, id));
						}
						exists = false;
					}
				} else if (sentence.startsWith("Update")) {
					int pos1 = sentence.indexOf(',');
					int pos2 = sentence.indexOf('-');
					int pos3 = sentence.indexOf('|');
					int x = Integer.parseInt(sentence.substring(6, pos1));
					int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
					int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));

					if (id != player.getID()) {
						filtered = entities;
						for (Entity e : filtered) {
							if (e.getID() == id) {
								e.setX(x);
								e.setY(y);
							}
						}
					}
				} else if (sentence.startsWith("LazerUpdate")) { // Lazer update
					try {
						int pos1 = sentence.indexOf(',');
						int pos2 = sentence.indexOf('-');
						int pos3 = sentence.indexOf('|');
						int x = Integer.parseInt(sentence.substring(11, pos1));
						int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
						String uuid = sentence.substring(pos3 + 1, sentence.length());

						for (Entity e : entities) {
							if (e instanceof EnemyLazer) {
								if (((EnemyLazer) e).getStringUUID().equals(uuid)) {
									e.setX(x);
									e.setY(y);
								}
							}
						}
					} catch (Exception e) {
						// Do nothing and wait for the removal request
					}
				} else if (sentence.startsWith("NewShot")) { // New lazer
					int pos1 = sentence.indexOf(',');
					int pos2 = sentence.indexOf('-');
					int pos3 = sentence.indexOf('|');
					int x = Integer.parseInt(sentence.substring(7, pos1));
					int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
					String uuid = sentence.substring(pos2 + 1, pos3);

					// Make sure it does not belong to us
					boolean belongsToUs = false;
					for (Lazer l : player.getLazers()) {
						if (l.getStringUUID().equals(uuid)) {
							belongsToUs = true;
						}
					}

					// TODO: Make it check that it is not a local lazer
					if (!belongsToUs) {
						// Does not already exist in list
						filtered = entities;
						for (Entity e : filtered) {
							if (e instanceof EnemyLazer) {
								if (((EnemyLazer) e).getStringUUID().equals(uuid)) {
									exists = true;
								}
							}
						}

						// If it does not exists in the array list, add it
						if (!exists) {
							filtered.add(new EnemyLazer(x, y, uuid));
						}
						exists = false;
					}

				} else if (sentence.startsWith("Remove")) {
					int id = Integer.parseInt(sentence.substring(6));

					if (id == player.getID()) {
						// The current player died
						// Probably wont use this part of if
						// This is because we do deaths on client side
					} else {
						// Remove the thing from entity array list
						removals.clear();
						for (Entity e : entities) {
							if (e.getID() == id) {
								removals.add(entities.indexOf(e));
							}
						}

					}
				} else if (sentence.startsWith("Exit")) {
					int id = Integer.parseInt(sentence.substring(4));

					// Remove the thing from entity array list
					removals.clear();
					for (Entity e : entities) {
						if (e.getID() == id) {
							removals.add(entities.indexOf(e));
						}
					}

				} else if (sentence.startsWith("Respawn")) {
					int pos1 = sentence.indexOf(',');
					int pos2 = sentence.indexOf('-');
					int pos3 = sentence.indexOf('|');
					int x = Integer.parseInt(sentence.substring(7, pos1));
					int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
					int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));

					// Is not local player
					if (id != player.getID()) {
						// Does not already exist in list
						filtered = entities;
						for (Entity e : filtered) {
							if (e instanceof EnemyPlayer && e.getID() == id) {
								exists = true;
							}
						}

						// If it does not exists in the array list, add it
						if (!exists) {
							filtered.add(new EnemyPlayer(x, y, id));
						}
						exists = false;
					}
				} else if (sentence.startsWith("Death")) {
					int id = Integer.parseInt(sentence.substring(5));

					// Remove the thing from entity array list
					if (id != player.getID()) {
						removals.clear();
						for (Entity e : entities) {
							if (e.getID() == id) {
								removals.add(entities.indexOf(e));
							}
						}
					}
				}
			}

			try {
				reader.close();
				clientSocket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
