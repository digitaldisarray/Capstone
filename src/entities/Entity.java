/*Superclass for all entities. Makes all entities implement the basic required methods as well as making them have the right information and properties
 * 
 * Author: The Mustangs
 * Last edited: 5/22/2019
 * */

package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Entity implements Serializable {

	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;

	private int id; // Used to avoid duplicate entities on the server

	public Entity(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public void tick() {

	}

	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	public void tryCollide(Entity entity) {

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

}
