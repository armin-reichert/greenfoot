import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Der Schläger kann Bälle zurückspielen und Power-Ups aufnehmen. Drückt man die
 * Pfeiltasten eine Zeit lang, so beschleunigt der Schläger.
 * 
 * @author Zwickmann
 */
public class Paddle extends BreakoutActor
{
	static final Dimension NORMAL = new Dimension(Cfg.PADDLE_WIDTH_NORMAL, Cfg.PADDLE_HEIGHT);
	static final Dimension WIDE = new Dimension(Cfg.PADDLE_WIDTH_WIDE, Cfg.PADDLE_HEIGHT);
	static final Dimension SMALL = new Dimension(Cfg.PADDLE_WIDTH_SMALL, Cfg.PADDLE_HEIGHT);

	private static final Vector KICK = new Vector(0.0, -12.0);

	private static final double STARTSPEED = 9.0;
	private static final double MAXSPEED = 30.0;
	private static final double ACCELERATION = 2.0;
	private static final int ACCELERATION_DELAY = 10;

	private static final Color NORMAL_COLOR = new Color(0, 100, 0);
	private static final Color HIT_COLOR = NORMAL_COLOR.brighter();

	private int width;
	private int height;
	private int minX;
	private int maxX;

	private boolean moveLeft = false;
	private boolean moveRight = false;
	private int moveRepetitions = 0;
	private int sizeCountdown = 0;
	private int colorCountdown = 0;
	private Color bgColor;

	public Paddle(Dimension size) {
		setPaddleSize(size, 0);
		bgColor = NORMAL_COLOR;
		updateImage();
	}

	@Override
	public void act() {
		checkSizeChange();
		checkColorChange();
		checkMoveAction();
		move();
		checkCollisionWithBorders();
		updateImage();
	}

	/**
	 * Sets the paddle size to the given dimension and starts the countdown for
	 * the given duration.
	 * 
	 * @param size
	 *          the paddle size
	 * @param duration
	 *          the number of ticks until the size is reset to normal
	 */
	public void setPaddleSize(Dimension size, int duration) {
		width = (int) size.getWidth();
		height = (int) size.getHeight();
		minX = Cfg.PANEL_MIN_X + width / 2;
		maxX = Cfg.PANEL_MAX_X - width / 2;
		setImage(new GreenfootImage(width, height));
		updateImage();
		sizeCountdown = duration;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	private void checkSizeChange() {
		if (sizeCountdown > 0) {
			--sizeCountdown;
			if (sizeCountdown == 0) {
				setPaddleSize(NORMAL, 0);
			}
		}
	}

	private void checkColorChange() {
		if (colorCountdown > 0) {
			--colorCountdown;
			if (colorCountdown == 0) {
				bgColor = NORMAL_COLOR;
			}
		}
	}

	private void checkMoveAction() {
		boolean wasMovingLeft = moveLeft;
		boolean wasMovingRight = moveRight;
		if (Greenfoot.isKeyDown("left")) {
			moveLeft = true;
			moveRight = false;
			if (wasMovingLeft)
				++moveRepetitions;
			else
				moveRepetitions = 0;
		} else if (Greenfoot.isKeyDown("right")) {
			moveLeft = false;
			moveRight = true;
			if (wasMovingRight)
				++moveRepetitions;
			else
				moveRepetitions = 0;
		} else {
			moveLeft = moveRight = false;
			moveRepetitions = 0;
		}

		if (!moveLeft && !moveRight) {
			getSpeed().setNeutral();
		} else {
			if (moveRepetitions > ACCELERATION_DELAY) {
				double newSpeed = getSpeed().getLength() + ACCELERATION;
				if (newSpeed <= MAXSPEED) {
					getSpeed().setLength(newSpeed);
				}
			} else {
				int direction = moveLeft ? 180 : 0;
				setSpeed(new Vector(direction, STARTSPEED));
			}
		}
	}

	public void toMiddle() {
		setLocation(Cfg.PANEL_WIDTH / 2, Cfg.PADDLE_Y);
	}

	public void hitsBall(Ball ball) {
		if (ball.getSpeed().getY() < 0) {
			return; // ignore collisions from below
		}
		double oldSpeedAmount = ball.getSpeed().getLength();
		double distFromMiddle = 2.0 * (ball.getX() - getX()) / width;
		if (distFromMiddle > 1.0) {
			distFromMiddle = 1.0;
		} else if (distFromMiddle < -1.0) {
			distFromMiddle = -1.0;
		}
		ball.getSpeed().revertVertical();
		Vector deviation = new Vector(10.0 * distFromMiddle, 0.0);
		ball.getSpeed().add(deviation);
		ball.getSpeed().add(KICK);
		ball.getSpeed().setLength(oldSpeedAmount);
		Greenfoot.playSound("plopp.wav");
		bgColor = HIT_COLOR;
		colorCountdown = 10;
	}

	private void checkCollisionWithBorders() {
		if (getX() < minX) {
			setLocation(minX, getY());
		}
		if (getX() > maxX) {
			setLocation(maxX, getY());
		}
	}

	private void updateImage() {
		final GreenfootImage image = getImage();
		final Graphics2D g = image.getAwtImage().createGraphics();
		final int sideWidth = 20;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(bgColor);
		g.fillArc(0, 0, sideWidth, height, 0, 360);
		g.fillArc(width - sideWidth, 0, sideWidth, height, 0, 360);
		image.setColor(bgColor);
		image.fillRect(sideWidth / 2, 0, width - sideWidth, height);
		image.setColor(new Color(255, Math.max(255 - moveRepetitions * ACCELERATION_DELAY, 0), 0));
		image.fillRect(4, height / 2 - 2, width - 8, 4);
		g.dispose();
	}

}
