package entities;

import java.awt.Color;

import base.Launcher;

public class Zombie extends Entity {

	boolean alive;
	private Vector2D moving;
	private double velocity, zombieDeg;

	public Zombie(int x, int y, int width, int height, Color color, double velocityp) {
		super(x, y, width, height, color);
		alive = true;
		velocity = velocityp;
	}

	public Zombie(int x, int y) {
		super(x, y, 20, 20, Color.GRAY);
		alive = true;
	}

	public void tick() {
		// TODO: Move towards player
		
		double x = getX();
		double y = getY();
//		System.out.println("x: " + x + "my: " + my);
		
		int playerX = Launcher.getGame().getPlayer().getX() + Launcher.getGame().getPlayer().getWidth() / 2;
		int playerY = Launcher.getGame().getPlayer().getY() + Launcher.getGame().getPlayer().getHeight() / 2;

		double width = playerX - x;
		double height =  playerY - y;

		double angle = Math.atan(height / width);

		if (width < 0)
			angle = -Math.PI + angle;

		moving = new Vector2D(Math.cos(angle), Math.sin(angle));
		moving = moving.normalize();
		setX((int) (getX() + (moving.getX() * velocity) + 0.5));
		setY((int) (getY() + (moving.getY() * velocity) + 0.5));

		


	}

	public void tryCollide(Entity entity) {
		// Make sure we are not colliding with ourself
		if (entity.equals(this)) {
			return;
		}

		// See if we colide with something, if so what is it
		if (Launcher.collides(getX(), getY(), getWidth(), getHeight(), entity.getX(), entity.getY(), entity.getWidth(),
				entity.getHeight())) {
			if (entity instanceof Lazer) {
				alive = false;
			}
		}
	}

	public boolean isAlive() {
		return alive;
	}
}
