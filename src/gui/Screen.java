/*Interface to beimplemented by classes that represent a screen
 * Author: The Mustangs
 * Last edited: 5/22/2019
 * 
 * */

package gui;

import java.awt.Graphics2D;

public interface Screen {

	// Used to initialize objects
	public abstract void init();
	
	// Drawing the screen and it's objects
	public abstract void draw(Graphics2D g);
	
	// Passing a mouse press event
	public abstract void passPressEvent(int x, int y);
	
	// Passing a key release event
	public abstract void passReleaseEvent(int x, int y);

}
