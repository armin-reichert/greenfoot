import greenfoot.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;

/**
 * Ballon, der explodiert, wenn der Ball ihn trifft.
 * 
 * @author Zwickmann
 */
public class Balloon extends BreakoutActor
{
    private static final Map<String, GreenfootImage> SCALED_IMAGES = new HashMap<String, GreenfootImage>();
    
    private static GreenfootImage getScaledImage(String imageName) {
        GreenfootImage scaledImage = SCALED_IMAGES.get(imageName);
        if (scaledImage == null) {
            scaledImage = new GreenfootImage(imageName);
            scaledImage.scale(Cfg.BALLOON_WIDTH, Cfg.BALLOON_HEIGHT);
            SCALED_IMAGES.put(imageName, scaledImage);
        }
        return new GreenfootImage(scaledImage);
    }
    
	protected int value;
	protected int lives;
	protected Power power;
	protected int turnDegrees;
	private boolean exploding;

	public Balloon() {
		exploding = false;
	}

	public static class Builder
	{
		private final Balloon balloon;

		public Builder() {
			balloon = new Balloon();
		}

		public Builder value(int value) {
			balloon.value = value;
			return this;
		}

		public Builder lives(int lives) {
			balloon.lives = lives;
			return this;
		}

		public Builder power(Power power) {
			balloon.power = power;
			return this;
		}

		public Builder turnDegrees(int degrees) {
			balloon.turnDegrees = degrees;
			return this;
		}

		public Builder imageName(String imageName) {
			balloon.setImage(getScaledImage(imageName));
			return this;
		}

		public Builder speed(Vector speed) {
			balloon.setSpeed(speed);
			return this;
		}

		public Balloon build() {
			return balloon;
		}
	}

	public boolean isExploding() {
		return exploding;
	}

	public void resetExploding() {
		exploding = false;
	}

	@Override
	public void act() {
		if (!exploding) {
			move();
			turn(turnDegrees);
			checkCollisions();
			checkFloor();
		}
	}

	void checkCollisions() {
		@SuppressWarnings("unchecked")
		List<Wall> walls = getIntersectingObjects(Wall.class);
		if (!walls.isEmpty()) {
			onCollisionWithWalls(walls);
		}
	}

	void checkFloor() {
		if (getY() >= Cfg.PANEL_MAX_Y - getImage().getHeight() / 2) {
			getSpeed().revertVertical();
		}
	}

	void hit(Ball ball) {
		if (exploding)
			return;

		game().changeScore(value);
		exploding = true;
		lives -= 1;
		Greenfoot.playSound("platzen.wav");
		getWorld().addObject(new BalloonExplosion(this), getX(), getY());
		TextView pointsText = new TextView.Builder(false).text("+" + value).color(Color.YELLOW)
				.font(Resources.TEXTFONT.deriveFont(Font.PLAIN, 30)).build();
		getWorld().addObject(pointsText, getX(), getY() + (pointsText.getImage().getHeight() + Cfg.BALLOON_HEIGHT) / 2);
	}
}
