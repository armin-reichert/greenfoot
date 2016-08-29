import greenfoot.World;

import java.util.List;

/**
 * Ball Basisklasse.
 * 
 * @author Zwickmann
 */
public abstract class Ball extends BreakoutActor
{
	protected boolean temporaryState = false;

	public Ball() {
		getImage().scale(Cfg.BALL_DIAMETER, Cfg.BALL_DIAMETER);
	}

	@Override
	public void act() {
		move();
		if (!temporaryState) {
			checkCollisions();
			checkFallingThrough();
		}
	}

	@Override
	public void addedToWorld(World world) {
		if (world instanceof Breakout) {
			gravity = game().getGravity();
		}
	}

	void checkCollisions() {
		Paddle paddle = (Paddle) getOneIntersectingObject(Paddle.class);
		if (paddle != null) {
			onCollisionWithPaddle(paddle);
		}
		Balloon balloon = (Balloon) getOneIntersectingObject(Balloon.class);
		if (balloon != null) {
			onCollisionWithBalloon(balloon);
		}
		@SuppressWarnings("unchecked")
		List<Wall> walls = getIntersectingObjects(Wall.class);
		if (!walls.isEmpty()) {
			onCollisionWithWalls(walls);
		}
	}

	void onCollisionWithPaddle(Paddle paddle) {
		int paddleTop = paddle.getY() - paddle.getHeight() / 2;
		int ballBottom = getY() + Cfg.BALL_DIAMETER / 2;
		if (ballBottom > paddleTop) {
			setLocation(getX(), paddleTop - Cfg.BALL_DIAMETER / 2);
		}
		paddle.hitsBall(this);
	}

	abstract void onCollisionWithBalloon(Balloon balloon);

	void checkFallingThrough() {
		if (getY() >= Cfg.PANEL_MAX_Y) {
			game().onBallFallingThrough(this);
		}
	}
}
