import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * Counts and displays lives.
 * 
 * @author Prof. Zwickmann
 */
public class LivesCounter extends Actor
{
	private final GreenfootImage heart;
	private int lives;

	public LivesCounter() {
		lives = 1;
		heart = getImage();
		heart.scale(30, 30);
		setImage(new GreenfootImage(1 + lives * heart.getWidth(), heart.getHeight()));
		updateImage();
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int n) {
		if (lives != n) {
			lives = n;
			updateImage();
		}
	}

	private void updateImage() {
		final GreenfootImage image = getImage();
		image.clear();
		image.scale(1 + lives * heart.getWidth(), heart.getHeight());
		for (int i = 0; i < lives; ++i) {
			image.drawImage(heart, i * heart.getWidth(), 0);
		}
	}
}
