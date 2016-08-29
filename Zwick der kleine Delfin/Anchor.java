import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The anchor swings back and forth up to a given degree.
 * 
 * @author Prof. Zwickmann
 * @version 25.9.2014
 */
public class Anchor extends Actor
{
	private static final int MIN_ROTATION = 0;
	private static final int MAX_ROTATION = 90;
	private static final int STEP = 1;

	private boolean swingLeft = true;

	public Anchor() {
		setRotation(0);
	}

	@Override
	public void act() {
		if (swingLeft) {
			swingLeft();
		} else {
			swingRight();
		}
	}

	private void swingLeft() {
		if (getRotation() + STEP <= MAX_ROTATION) {
			turn(STEP);
		} else {
			swingLeft = false;
		}
	}

	private void swingRight() {
		if (getRotation() - STEP >= MIN_ROTATION) {
			turn(-STEP);
		} else {
			swingLeft = true;
		}
	}
}
