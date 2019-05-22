package base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import entities.Entity;
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
	
	ArrayList<Entity> entities = new ArrayList<>();
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	// Init anything we need here like asset managers
	private void init() {
		window = new Window(title, width, height);
		mManager = new MenuManager();
		player = new Player(50, 50, 10, 10, new Color(0, 100, 80), "Test Player");
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
		
		if(!inGame) {
			mManager.draw(g);
		} else {
			// Draw and move the player
			player.tick();
			player.draw(g);
			
			// SINGLEPLAYER
			if(!connected) {
				// Spawn zombies
				spawnZombie();
				
				// Check zombie collisions
				
				// Draw zombies
				for(Entity e : entities) {
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
		if(System.currentTimeMillis() - lastSpawn >= spawnRate) {
			entities.add(new Zombie((int) (Math.random() * 700 + 50), (int) (Math.random() * 700 + 50)));
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
}
