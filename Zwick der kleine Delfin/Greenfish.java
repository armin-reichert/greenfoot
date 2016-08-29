import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;

/**
 * Ein grüner Fisch.
 * 
 * @author Prof. Zwickmann
 * @version 25.9.2014
 */
public class Greenfish extends Actor
{
	private static final int TURN_RADIUS = 10;

	private int speed;

	public Greenfish() {
		speed = 5 + Greenfoot.getRandomNumber(5);
	}

	@Override
	public void act() {
		swim();
	}

	private void swim() {
		move(speed);
		Commons.wrapActorAtWorldEdge(this);
		turn(TURN_RADIUS - Greenfoot.getRandomNumber(2 * TURN_RADIUS));
	}
}
