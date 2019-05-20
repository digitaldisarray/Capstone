package base;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Window {

	// Window property variables
	private JFrame frame;
	private Canvas canvas;

	private String title;
	private int width, height;

	// The angle between the mouse and player
	private double mouseDeg;

	// Stores all key states on the keyboard
	private boolean[] keyPressed = new boolean[256];
	private boolean[] keyTyped = new boolean[256]; // Nobody is going to have 256 key anti ghosting but too bad

	// Booleans for storing mouse presses
	private boolean[] mouse = new boolean[2];

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

		frame.add(canvas);
		frame.pack();

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
				Launcher.getGame().mManager.getCurrent().passPressEvent(e.getX(), e.getY()); // Passes the mouse click
																								// to where it can be
																								// used

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
				Launcher.getGame().mManager.getCurrent().passReleaseEvent(e.getX(), e.getY());

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

	public boolean isMousePressed(int button) {//Determines wich bbutton is clicked
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

		// Prob unreachable but who knows
		return false;
	}

	public void updateMouseCoords() { // Updates the mouse coordinates and calculates the angle from the middle of the
										// entity to the mouse position
		// Called in getMouseDegrees, which is called when a click is registered
		Point p = MouseInfo.getPointerInfo().getLocation();
		int mx = (int) (p.getX() / Launcher.getGame().getXScaleFactor()) - frame.getX();
		int my = (int) (p.getY() / Launcher.getGame().getYScaleFactor()) - frame.getY();
		System.out.print(mx);
		// playerX, playerY
		int playerX = Launcher.getGame().getPlayer().getX() + Launcher.getGame().getPlayer().getWidth() / 2;
		int playerY = Launcher.getGame().getPlayer().getY() + Launcher.getGame().getPlayer().getHeight() / 2;

		double tanValue = Math.abs(((double) my - playerY) / ((double) mx - playerX));

		if (mx == playerX) {// Code to determine correct angle. Due to arctan domain, we must check multiple
							// times
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
	}

	public double getMouseDeg() {// Calls updateMouseCoords, forces angle to update to the current angle of the
									// mouse
		updateMouseCoords();// Recalculates angle of mouse
		System.out.println("Radians: " + Math.toRadians(mouseDeg) + "  Degrees: " + mouseDeg);
		return mouseDeg;
	}

}