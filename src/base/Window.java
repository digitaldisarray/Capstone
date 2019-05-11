package base;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	private Canvas canvas;

	private String title;
	private int width, height;

	// Stores all key states on the keyboard
	private boolean[] keyPressed = new boolean[256];
	private boolean[] keyTyped = new boolean[256]; // Nobody is going to have 256 key anti ghosting but too bad

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;

		init();
	}

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
				if (c >= 0 && c < keyPressed.length)
					keyPressed[c] = keyTyped[c] = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				char c = e.getKeyChar(); // may be CHAR_UNDEFINED
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
				System.out.println(e.getX());
				System.out.println(e.getY());
				Launcher.getGame().mManager.getCurrent().passKeyEvent(e.getX(), e.getY());
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

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

}
