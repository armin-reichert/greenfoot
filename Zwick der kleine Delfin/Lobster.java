import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;

/**
 * Zwicks favorite meal.
 * 
 * @author Prof. Zwickmann
 */
public class Lobster extends Actor implements Target
{
	private static final int TURN_RADIUS = 30;
	private static final int TURN_PROBABILITY = 40;

	private int speed;

	public Lobster() {
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	@Override
	public void act() {
		crawl();
	}

	@Override
	public int getPointsChange(int points) {
		return points + 5;
	}

	@Override
	public int getLifesChange(int lifes) {
		return lifes;
	}

	@Override
	public String getConsumedMessage() {
		return "";
	}

	@Override
	public GreenfootSound getConsumedSound() {
		return Resources.Sounds.Smack.$;
	}

	@Override
	public void consumed() {
		getWorld().removeObject(this);
	}

	private void crawl() {
		move(speed);
		Commons.wrapActorAtWorldEdge(this);
		if (Greenfoot.getRandomNumber(100) < TURN_PROBABILITY) {
			turn(TURN_RADIUS - Greenfoot.getRandomNumber(2 * TURN_RADIUS));
		}
	}
}
