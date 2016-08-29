import greenfoot.Actor;

import java.util.List;

/**
 * Actor im Breakout-Spiel.
 * 
 * @author Zwickmann
 */
public abstract class BreakoutActor extends Actor
{
	protected Vector gravity;
	protected Vector speed;

	protected BreakoutActor() {
		gravity = new Vector();
		speed = new Vector();
	}

	public Vector getSpeed() {
		return speed;
	}

	public void setSpeed(Vector speed) {
		this.speed = new Vector(speed);
	}

	public Breakout game() {
		return (Breakout) getWorld();
	}

	public void move() {
		int newX = (int) Math.round(getX() + speed.getX() + gravity.getX());
		int newY = (int) Math.round(getY() + speed.getY() + gravity.getY());
		setLocation(newX, newY);
	}

	boolean onCollisionWithWalls(List<Wall> walls) {
		boolean collision = false;
		for (Wall wall : walls) {
			if (game().isWall(wall)) {
				speed.revertHorizontal();
				collision = true;
			}
			if (game().isCeiling(wall) && speed.getY() < 0) {
				speed.revertVertical();
				collision = true;
			}
		}
		return collision;
	}
}
