import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.List;

/**
 * Zwick, der kleine Delfin.
 * 
 * @author Prof. Zwickmann
 */
public class Zwick extends Actor
{
	private enum State {
		SWIMMING, TUMBLING, DEAD;
	};

	private static final GreenfootImage rightImg = new GreenfootImage("delfin-right.gif");
	private static final GreenfootImage mirrorImg = new GreenfootImage("delfin-mirror.gif");

	private int speed;
	private int actualSpeed;
	private boolean turbo;
	private int points;
	private int lives;
	private int countdown;
	private State state;

	public Zwick() {
		reset();
	}

	public void reset() {
		speed = 0;
		actualSpeed = 0;
		turbo = false;
		points = 0;
		lives = 1;
		countdown = 0;
		state = State.SWIMMING;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getSpeed() {
		return actualSpeed;
	}

	public void setSpeed(int s) {
		actualSpeed = speed;
		speed = s;
	}

	public boolean isDead() {
		return state == State.DEAD;
	}

	@Override
	public void act() {

		switch (state) {
		case SWIMMING:
			processUserInput();
			if (canSwim()) {
				if (turbo)
					turboSwim();
				else
					swim();
			}
			checkTargetCollisions();
			break;

		case TUMBLING:
			tumble();
			break;

		case DEAD:
			swim();
			break;
		}
	}

	public void turnRight() {
		setRotation(0);
		setImage(rightImg);
	}

	public void turnDown() {
		setRotation(90);
	}

	public void turnLeft() {
		setRotation(180);
		setImage(mirrorImg);
	}

	public void turnUp() {
		setRotation(270);
	}

	public void turnRandomly() {
		int random = Greenfoot.getRandomNumber(4);
		switch (random) {
		case 0:
			turnRight();
			break;
		case 1:
			turnDown();
			break;
		case 2:
			turnLeft();
			break;
		case 3:
			turnUp();
			break;
		default:
			break;
		}
	}

	public void swimAround() {
		if (getSpeed() == 0) {
			setSpeed(5);
		}
		int random = Greenfoot.getRandomNumber(3000);
		if (random < 33) {
			turnRight();
		} else if (random < 66) {
			turnLeft();
		} else if (random < 76) {
			turnUp();
		} else if (random < 86) {
			turnDown();
		}
		if (random < 66) {
			setSpeed(5 + Greenfoot.getRandomNumber(20));
		}
	}

	private void startTurbo(int duration) {
		if (turbo || state != State.SWIMMING || countdown > 0)
			return;

		((BetterWorld) getWorld()).flash(Resources.getText("TURBO_SPEED"));
		countdown = duration;
		setSpeed(2 * actualSpeed);
	}

	private void turboSwim() {
		if (countdown > 0) {
			swim();
			--countdown;
		} else {
			setSpeed(actualSpeed / 2);
		}
	}

	private boolean canSwim() {
		boolean canSwim = true;
		int x = getX();
		int y = getY();
		// simulate swim step
		move(actualSpeed);
		Commons.wrapActorAtWorldEdge(this);
		if (isObstacleAhead()) {
			canSwim = false;
		}
		// undo swim step
		setLocation(x, y);
		return canSwim;
	}

	private void swim() {
		move(actualSpeed);
		Commons.wrapActorAtWorldEdge(this);
		// accelerate or brake
		if (actualSpeed < speed) {
			++actualSpeed;
		} else if (actualSpeed > speed) {
			--actualSpeed;
		}
	}

	private void startTumbling(int duration) {
		if (state == State.TUMBLING)
			return;

		countdown = duration;
		state = State.TUMBLING;
	}

	private void tumble() {
		if (countdown > 0) {
			turn(30);
			--countdown;
		} else {
			state = State.SWIMMING;
		}
	}

	private void die() {
		setRotation(-45);
		actualSpeed = speed = 2;
		state = State.DEAD;
	}

	private boolean isObstacleAhead() {
		Obstacle obstacle = (Obstacle) getOneIntersectingObject(Obstacle.class);
		if (obstacle == null) {
			return false;
		}
		Actor obstacleActor = (Actor) obstacle;
		int rotation = getRotation();
		if (rotation == 0) {
			return obstacleActor.getX() >= getX();
		} else if (rotation == 90) {
			return obstacleActor.getY() >= getY();
		} else if (rotation == 180) {
			return obstacleActor.getX() <= getX();
		} else if (rotation == 270) {
			return obstacleActor.getY() <= getY();
		}
		return false;
	}

	public boolean isHitByBomb() {
		return isTouching(Bomb.class);
	}

	private void processUserInput() {
		if (Greenfoot.isKeyDown("left")) {
			turnLeft();
		}
		if (Greenfoot.isKeyDown("right")) {
			turnRight();
		}
		if (Greenfoot.isKeyDown("up")) {
			turnUp();
		}
		if (Greenfoot.isKeyDown("down")) {
			turnDown();
		}
		final String key = Greenfoot.getKey();
		if ("t".equals(key)) {
			startTurbo(90);
		}
	}

	private void checkTargetCollisions() {
		List<Target> targets = getIntersectingObjects(Target.class);
		for (Target target : targets) {
			if (target instanceof Bomb) {
				startTumbling(60);
			}
			int oldLifes = lives;
			lives = Math.max(target.getLifesChange(lives), 0);
			if (lives < oldLifes) {
				// lost lives(s)
				if (lives > 0) {
					// ((BetterWorld)getWorld()).flash(Resources.getText("LIFE_LOST"));
				} else {
					((BetterWorld) getWorld()).flash(Resources.getText("DEAD"));
					die();
				}
			}
			points = target.getPointsChange(points);
			if (target.getConsumedMessage() != null && target.getConsumedMessage().length() > 0) {
				((BetterWorld) getWorld()).flash(target.getConsumedMessage());
			}
			if (target.getConsumedSound() != null) {
				target.getConsumedSound().play();
			}
			target.consumed();
		}
	}

}
