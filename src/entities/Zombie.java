/*Represents an enemy zombie that chases you
 * Author: The Mustangs
 * Last edited: 5/22/2019
 * 
 * */

package entities;

import java.awt.Color;
import java.io.File;

import base.BetterSound;
import base.Game;
import base.Launcher;

public class Zombie extends Entity {

	boolean alive, isMoving = true;
	private Vector2D moving;
	private double velocity;
	private static int zombieKills = 0;

	// Constructor
	public Zombie(int x, int y, int width, int height, Color color, double velocityp) {
		super(x, y, width, height, color);
		alive = true;
		velocity = velocityp;
	}

	// Simpler constructor
	public Zombie(int x, int y) {
		super(x, y, 20, 20, Color.GRAY);
		alive = true;
	}

	// Makes the zombie move towards the player
	public void tick() {
		int playerX = Launcher.getGame().getPlayer().getX();
		int playerY = Launcher.getGame().getPlayer().getY();

		double width = playerX - getX();
		double height = playerY - getY();

		double angle = Math.atan(height / width);

		if (width < 0)
			angle = -Math.PI + angle;

		moving = new Vector2D(Math.cos(angle), Math.sin(angle));
		moving = moving.normalize();

		if (width == 0 && height == 0) {
			setX(playerX);
			setY(playerY);
		} else {
			if (isMoving) {
				setX((int) (getX() + (moving.getX() * velocity) + 0.5));
				setY((int) (getY() + (moving.getY() * velocity) + 0.5));
			}
		}

	}

	// Collision
	public void tryCollide(Entity entity) {
		// Make sure we are not colliding with ourself

		if (entity.equals(this)) {
			return;
		}

		// See if we colide with something, if so what is it
		if (Launcher.getGame().collides(getX(), getY(), getWidth(), getHeight(), entity.getX(), entity.getY(),
				entity.getWidth(), entity.getHeight())) {

			if (entity instanceof Lazer) {

				alive = false;
				if(Game.PlayMusic) {
					BetterSound oof = new BetterSound(new File("resources" +Game.fileSeparator +"Oof.wav"), true, false);
					oof.setVolume(0.65);
				}
				zombieKills++;
				//				((Lazer) entity).setRemove(true);
			}

			if (entity instanceof Wall) {
				isMoving = false;
			}
		}
	}

	// Returns how many zombies you have killed
	public static int getZombieKills() {
		return zombieKills;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setMovingState(boolean moving) {
		isMoving = moving;
	}

	public static void resetZombieKills() {
		zombieKills = 0;
	}

	public void setDead() {
		alive = false;
	}

}
