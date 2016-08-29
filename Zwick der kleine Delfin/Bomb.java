import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootSound;

/**
 * A bomb.
 * 
 * @author Prof. Zwickmann
 */
public class Bomb extends Actor implements Target
{
	private int dx;
	private int dy;

	public Bomb() {
		this(0, 0);
	}

	public Bomb(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void act() {
		moveAndBounce();
		Commons.wrapActorAtWorldEdge(this);
	}

	@Override
	public int getPointsChange(int points) {
		return points;
	}

	@Override
	public int getLifesChange(int lifes) {
		ZwickGameLevel level = (ZwickGameLevel) getWorld();
		if (level.getGame().isDemoMode())
			return lifes;
		else
			return lifes - 1;
	}

	@Override
	public String getConsumedMessage() {
		return Resources.getRandomText("EXPLOSION");
	}

	@Override
	public GreenfootSound getConsumedSound() {
		return Resources.Sounds.Explosion.$;
	}

	@Override
	public void consumed() {
		explode();
	}

	private void explode() {
		int numShrapnels = 75;
		for (int i = 0; i < numShrapnels; ++i) {
			getWorld().addObject(new Shrapnel(), getX(), getY());
		}
		getWorld().removeObject(this);
	}

	private void moveAndBounce() {
		if (!getIntersectingObjects(Obstacle.class).isEmpty()) {
			// bounce back
			dx = -dx;
			dy = -dy;
			setLocation(getX() + 3 * dx, getY() + 3 * dy);
		} else {
			setLocation(getX() + dx, getY() + dy);
		}
	}
}
