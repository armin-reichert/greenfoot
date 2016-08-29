import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Die Wunderperle.
 * 
 * @author Prof. Zwickmann
 * @version 25.9.2014
 */
public class Pearl extends Actor implements Target
{
	private static final int LIFETIME = 100;
	private static final int SIZE = 40;

	private int remainingLifetime;
	private final GreenfootImage savedImage;

	public Pearl() {
		remainingLifetime = LIFETIME;
		getImage().scale(SIZE, SIZE);
		savedImage = getImage();
	}

	@Override
	public void act() {
		if (remainingLifetime == 0) {
			getWorld().removeObject(this);
		} else {
			if (remainingLifetime < LIFETIME / 2) {
				blink(4);
			} else if (remainingLifetime < LIFETIME / 4) {
				blink(2);
			}
			--remainingLifetime;
		}
	}

	@Override
	public int getLifesChange(int lifes) {
		return lifes + 1;
	}

	@Override
	public int getPointsChange(int points) {
		return points;
	}

	@Override
	public String getConsumedMessage() {
		return Resources.getText("EXTRA_LIFE");
	}

	@Override
	public GreenfootSound getConsumedSound() {
		return Resources.Sounds.ExtraLife.$;
	}

	@Override
	public void consumed() {
		getWorld().removeObject(this);
	}

	private void blink(int duration) {
		if (remainingLifetime % (2 * duration) < duration) {
			setImage((GreenfootImage) null);
		} else {
			setImage(savedImage);
		}
	}
}
