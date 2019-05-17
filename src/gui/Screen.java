package gui;

import java.awt.Graphics2D;

public interface Screen {

	public abstract void init();
	public abstract void draw(Graphics2D g);
	public abstract void passPressEvent(int x, int y); // Used to pass a key event
	public abstract void passReleaseEvent(int x, int y);

}
