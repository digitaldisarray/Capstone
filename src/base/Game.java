package base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import client.Protocol;
import entities.EnemyLazer;
import entities.EnemyPlayer;
import entities.Entity;
import entities.Lazer;
import entities.Player;
import entities.Zombie;
import gui.MenuManager;

public class Game implements Runnable {

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
	MenuManager mManager;

	// Graphics objects
	private BufferStrategy bs;
	private Graphics2D g;

	// The local player
	Player player;

	// Store the last recorded fps
	int lastFPS;

	// The entities in the game
	ArrayList<Entity> entities = new ArrayList<>();

	// Used for filtering entities
	ArrayList<Entity> filtered = new ArrayList<>();
	
	// Object for getting new info from server
	ClientRecivingThread clientThread;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	// Init anything we need here like asset managers
	private void init() {
		window = new Window(title, width, height);
		mManager = new MenuManager();
		player = new Player((int) (Math.random() * 700) + 50, (int) (Math.random() * 500) + 50, 10, 10, new Color(0, 100, 80), "Test Player");
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

					e.tick();
					e.draw(g);
				}

			} else {
				// MUTLIPLAYER
				// Draw, collide, and update things
				for (Entity e : entities) {
					if(e instanceof EnemyLazer) {
						player.tryCollide(e);
					}
					
					e.tick();
					e.draw(g);
				}
			}
		}

		// Draw FPS
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + lastFPS, 1, 15);

		// End Graphics
		bs.show();
		g.dispose();
	}

	long lastSpawn = System.currentTimeMillis();
	int spawnRate = 1000;

	private void spawnZombie() {
		if (System.currentTimeMillis() - lastSpawn >= spawnRate) {
			entities.add(new Zombie((int) (Math.random() * 700 + 50), (int) (Math.random() * 700 + 50), 20, 20,
					Color.GRAY, 1.0));
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

	public double getXScaleFactor() {

		return window.getCanvas().getWidth() / 800.0;
	}

	public double getYScaleFactor() {

		return window.getCanvas().getHeight() / 600.0;
	}

	public Window getWindow() {
		return this.window;
	}

	public Player getPlayer() {
		return player;
	}

	public void setInGame(boolean b) {
		inGame = b;
	}

	public boolean isInGame() {
		return inGame;
	}

	public MenuManager getMenuManager() {
		return mManager;
	}

	public boolean hasViewedMessage(boolean chicken) {
		return chicken;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public void startClientThread() {
		clientThread = new ClientRecivingThread(Launcher.getClient().getSocket());
		clientThread.start();
	}
	
	public boolean isConnected() {
		return connected;
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
					System.out.println("My ID: " + id);

				} else if (sentence.startsWith("NewClient")) {
					int pos1 = sentence.indexOf(',');
					int pos2 = sentence.indexOf('-');
					int pos3 = sentence.indexOf('|');
					int x = Integer.parseInt(sentence.substring(9, pos1));
					int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
					int dir = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
					int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));
					
					// Is not local player
					if (id != player.getID()) {
						// Does not already exist in list
						for(Entity e : entities) {
							if(e instanceof EnemyPlayer && e.getID() == id) {
								exists = true;
							}
						}
						
						// If it does not exists in the array list, add it
						if(!exists) {
							entities.add(new EnemyPlayer(x, y, id));
						}
						exists = false;
					}
				} else if (sentence.startsWith("Update")) {
					System.out.println("Client side update recived");
					int pos1 = sentence.indexOf(',');
					int pos2 = sentence.indexOf('-');
					int pos3 = sentence.indexOf('|');
					int x = Integer.parseInt(sentence.substring(6, pos1));
					int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
					int dir = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
					int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));

					if (id != player.getID()) {
						for(Entity e : entities) {
							if(e.getID() == id) {
								e.setX(x);
								e.setY(y);
							}
						}
						
//						boardPanel.getTank(id).setXpoistion(x);
//						boardPanel.getTank(id).setYposition(y);
//						boardPanel.getTank(id).setDirection(dir);
//						boardPanel.repaint();
					}

				} else if (sentence.startsWith("Shot")) { // New projectile
//					int id = Integer.parseInt(sentence.substring(4));
//
//					if (id != player.getID()) {
//						boardPanel.getTank(id).Shot();
//						entities.add(new EnemyLazer());
//					}

				} else if (sentence.startsWith("Remove")) {
//					int id = Integer.parseInt(sentence.substring(6));
//
//					if (id == clientTank.getTankID()) {
//						int response = JOptionPane.showConfirmDialog(null,
//								"Sorry, You are loss. Do you want to try again ?", "Tanks 2D Multiplayer Game",
//								JOptionPane.OK_CANCEL_OPTION);
//						if (response == JOptionPane.OK_OPTION) {
//							// client.closeAll();
//							setVisible(false);
//							dispose();
//
//							new ClientGUI();
//						} else {
//							System.exit(0);
//						}
//					} else {
//						boardPanel.removeTank(id);
//					}
				} else if (sentence.startsWith("Exit")) {
//					int id = Integer.parseInt(sentence.substring(4));
//
//					if (id != clientTank.getTankID()) {
//						boardPanel.removeTank(id);
//					}
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
