import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;

/**
 * Ein Bombenschrapnell.
 * 
 * @author Prof. Zwickmann
 */
public class Shrapnel extends Actor
{
	private int speed;
	private int maxSpeed;

	public Shrapnel() {
		maxSpeed = 50;
		speed = 5 + Greenfoot.getRandomNumber(5);
		setRotation(Greenfoot.getRandomNumber(360));
		int width = 15 + Greenfoot.getRandomNumber(15);
		getImage().scale(width, 30 - width);
	}

	@Override
	public void act() {
		if (speed < maxSpeed) {
			speed *= 2;
		}
		move(speed);
		if (isAtEdge()) {
			getWorld().removeObject(this);
		}
	}
}
