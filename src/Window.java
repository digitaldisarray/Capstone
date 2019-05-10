
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

	private boolean[] keyPressed = new boolean[256];
	private boolean[] keyTyped = new boolean[256];
	private boolean[] virtualKeyPressed = new boolean[1024];
	private boolean[] virtualKeyTyped = new boolean[1024];

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

		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {

				char c = e.getKeyChar();
				System.out.println(c);
				if (c >= 0 && c < keyPressed.length)
					keyPressed[c] = keyTyped[c] = true;

//				int vk = e.getKeyCode();
//				if (vk >= 0 && vk < virtualKeyPressed.length)
//					virtualKeyPressed[vk] = virtualKeyTyped[vk] = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				char c = e.getKeyChar(); // may be CHAR_UNDEFINED
				if (c >= 0 && c < keyPressed.length)
					keyPressed[c] = false;

//				int vk = e.getKeyCode();
//				if (vk >= 0 && vk < virtualKeyPressed.length)
//					virtualKeyPressed[vk] = false;
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getX());
				System.out.println(e.getY());
				Launcher.getGame().mManager.getCurrent().passKeyEvent(e.getX(), e.getY());			
				}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public boolean isKeyPressed(char key) {
		return key >= 0 && key < keyPressed.length ? keyPressed[key] : false;
	}

	public void actionPerformed(ActionEvent e) {

	}

}
