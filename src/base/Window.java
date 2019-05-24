package base;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import entities.Vector2D;

public class Window {

	// Window property variables
	private JFrame frame;
	private Canvas canvas;

	private String title;
	private int width, height;

	// The angle between the mouse and player
	private double mouseDeg;
	private Vector2D shooting;
	private int wallDirection;

	// Stores all key states on the keyboard
	private boolean[] keyPressed = new boolean[256];
	private boolean[] keyTyped = new boolean[256]; // Nobody is going to have 256 key anti ghosting but too bad

	// Booleans for storing mouse presses
	private boolean[] mouse = new boolean[3];

	// Constructor for a new Window object
	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;

		init();
	}

	// Instantiate the window
	private void init() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(820, 640));

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		// canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(800, 600));

		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		frame.setCursor(cursor);

		// Add key listener to the canvas
		canvas.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();

				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_UP:
					c = 'w';
					break;
				case KeyEvent.VK_LEFT:
					c = 'a';
					break;
				case KeyEvent.VK_DOWN:
					c = 's';
					break;
				case KeyEvent.VK_RIGHT:
					c = 'd';
					break;
				}

				if (c >= 0 && c < keyPressed.length)
					keyPressed[c] = keyTyped[c] = true;

			}

			@Override
			public void keyReleased(KeyEvent e) {
				char c = e.getKeyChar(); // may be CHAR_UNDEFINED

				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_UP:
					c = 'w';
					break;
				case KeyEvent.VK_LEFT:
					c = 'a';
					break;
				case KeyEvent.VK_DOWN:
					c = 's';
					break;
				case KeyEvent.VK_RIGHT:
					c = 'd';
					break;
				}

				if (c >= 0 && c < keyPressed.length)
					keyPressed[c] = false;
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}
		});

		// Add a mouse listener to the canvas
		canvas.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println(e.getX());
//				System.out.println(e.getY());
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (!Launcher.getGame().isInGame()) {
					Launcher.getGame().mManager.getCurrent().passPressEvent(e.getX(), e.getY()); // Passes the mouse
																									// click to where it
																									// can be used
				}

				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					mouse[0] = true;
					break;
				case MouseEvent.BUTTON2:
					mouse[1] = true;
					break;
				case MouseEvent.BUTTON3:
					mouse[2] = true;
					break;
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!Launcher.getGame().isInGame()) {
					Launcher.getGame().mManager.getCurrent().passReleaseEvent(e.getX(), e.getY());
				}

				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					mouse[0] = false;
					break;
				case MouseEvent.BUTTON2:
					mouse[1] = false;
					break;
				case MouseEvent.BUTTON3:
					mouse[2] = false;
					break;
				}
			}

		});

		frame.add(canvas);
		frame.pack();
	}

	// Get the canvas object
	public Canvas getCanvas() {
		return canvas;
	}

	// Function used to tell if a certain key is pressed
	public boolean isKeyPressed(char key) {
		return key >= 0 && key < keyPressed.length ? keyPressed[key] : false;
	}

	// Having this here magically fixes our mouse events
	public void actionPerformed(ActionEvent e) {

	}

	public boolean isMousePressed(int button) {
		switch (button) {
		case MouseEvent.BUTTON1:
			if (mouse[0])
				return true;
			else
				return false;

		case MouseEvent.BUTTON2:
			if (mouse[1])
				return true;
			else
				return false;
		case MouseEvent.BUTTON3:
			if (mouse[2])
				return true;
			else
				return false;
		}

		// Probably unreachable but who knows
		return false;
	}

	// Updates mouse position & calculates angles from player to mouse
	Point p;
	public void updateMouseCoords() {
		p = MouseInfo.getPointerInfo().getLocation();
		double mx = p.getX() / Launcher.getGame().getXScaleFactor() - frame.getX();
		double my = p.getY() - 30 / Launcher.getGame().getYScaleFactor() - frame.getY();
//		System.out.println("mx: " + mx + "my: " + my);
		
		int playerX = Launcher.getGame().getPlayer().getX() + Launcher.getGame().getPlayer().getWidth() / 2;
		int playerY = Launcher.getGame().getPlayer().getY() + Launcher.getGame().getPlayer().getHeight() / 2;

		double width = mx - playerX;
		double height = my - playerY;

		double angle = Math.atan(height / width);

		if (width < 0)
			angle = -Math.PI + angle;

		shooting = new Vector2D(Math.cos(angle), Math.sin(angle));
		shooting = shooting.normalize();

		
//		// Old math
		double tanValue = Math.abs(((double) my - playerY) / ((double) mx - playerX));

		if (mx == playerX) {
			if (my < playerY)
				mouseDeg = 90;
			else if (my > playerY)
				mouseDeg = 270;
		}

		if (my == playerY) {
			if (mx < playerX)
				mouseDeg = 180;
			else if (mx > playerX)
				mouseDeg = 0;
		}

		if (mx > playerX) {
			if (my < playerY)
				mouseDeg = Math.toDegrees(Math.atan(tanValue));
			else if (my > playerY)
				mouseDeg = 360 - Math.toDegrees(Math.atan(tanValue));
		}

		if (mx < playerX) {
			if (my < playerY)
				mouseDeg = 180 - Math.toDegrees(Math.atan(tanValue));
			else if (my > playerY)
				mouseDeg = 180 + Math.toDegrees(Math.atan(tanValue));
		}
		if(mouseDeg >= 45 && mouseDeg <= 135) {
			wallDirection = 1;
		}
		
		if(mouseDeg > 135 && mouseDeg < 225) {
			wallDirection = 2;
		}
		if(mouseDeg >= 225 && mouseDeg <= 315) {
			wallDirection = 3;
			}
		
		if(mouseDeg > 315 || mouseDeg < 45) {
			wallDirection = 4;
		}
		
		System.out.println("Wall Direction" + wallDirection);
		
	}

//	public double getMouseDeg() {// Calls updateMouseCoords, forces angle to update to the current angle of the
//									// mouse
//		updateMouseCoords();
//		System.out.println("Radians: " + Math.toRadians(mouseDeg) + "  Degrees: " + mouseDeg);
//		return mouseDeg;
//	}

	public Vector2D getMouseDeg() {
		updateMouseCoords();
		return shooting;
	}
	public int getWallDirection() {
		updateMouseCoords();
		return wallDirection;
	}
	public JFrame getFrame() {
		return frame;
	}
	

}