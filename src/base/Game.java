package base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import entities.Player;
import gui.MenuManager;

public class Game implements Runnable {
	private Window window;
	public int width, height;
	public String title;

	private boolean running = false;
	private Thread thread;
	
	private boolean inGame = false;
	private boolean connected = false;
	MenuManager mManager;
	
	private BufferStrategy bs;
	private Graphics2D g;
	
	Player player;

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
			player.tick();
			player.draw(g);
		}

		// End Graphics
		bs.show();
		g.dispose();
	}

	// Run the game
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
				//dSystem.out.println("FPS/TPS: " + ticks);
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
}
