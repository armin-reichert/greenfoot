import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;
import greenfoot.core.WorldHandler;
import greenfoot.event.WorldEvent;
import greenfoot.event.WorldListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Annas Breakout.
 * 
 * @author Zwickmann
 */
public class Breakout extends World implements WorldListener
{
	private static final Midi MUSIC = new Midi("music.mid");

	private int points;
	private Vector ballSpeed;
	private int level;
	private final List<Ball> ballsInStock;
	private final Vector gravity;
	private final Wall leftWall;
	private final Wall rightWall;
	private final Wall ceiling;
	private final Paddle paddle;
	private final BallStock ballStock;

	public Breakout() {
		super(Cfg.PANEL_WIDTH, Cfg.PANEL_HEIGHT, 1, false);
		gravity = new Vector(0.0, 2.0);
		ballsInStock = new ArrayList<Ball>();
		addObject(ceiling = new Wall(Cfg.PANEL_WIDTH, Cfg.WALL_THICKNESS), Cfg.PANEL_WIDTH / 2, Cfg.WALL_THICKNESS / 2);
		addObject(leftWall = new Wall(Cfg.WALL_THICKNESS, Cfg.PANEL_HEIGHT), Cfg.WALL_THICKNESS / 2, Cfg.PANEL_HEIGHT / 2);
		addObject(rightWall = new Wall(Cfg.WALL_THICKNESS, Cfg.PANEL_HEIGHT), Cfg.PANEL_WIDTH - Cfg.WALL_THICKNESS / 2, Cfg.PANEL_HEIGHT / 2);
		addObject(paddle = new Paddle(Paddle.NORMAL), Cfg.PANEL_WIDTH / 2, Cfg.PADDLE_Y);
		addObject(ballStock = new BallStock(ballsInStock), Cfg.PANEL_WIDTH - Cfg.WALL_THICKNESS - 20, Cfg.PANEL_HEIGHT - 10);
		setPaintOrder(TextView.class, Balloon.class, Ball.class);
		WorldHandler.getInstance().addWorldListener(this);
	}

	@Override
	public void worldCreated(WorldEvent e) {
	}

	@Override
	public void worldRemoved(WorldEvent e) {
		if (e.getWorld() == this) {
			stopMusic();
		}
	}

	@Override
	public void act() {
		if (getObjects(Balloon.class).size() == 0) {
			nextLevel();
		}
		cheats();
		showScore();
	}

	public void startGame() {
		points = 0;
		ballsInStock.clear();
		for (int i = 0; i < 5; ++i) {
			ballsInStock.add(new NormalBall());
		}
		ballSpeed = new Vector(300, Cfg.INITIAL_BALL_SPEED);
		level = 0;
		cleanUp();
		paddle.toMiddle();
		ballStock.setRightEdge(Cfg.PANEL_MAX_X);
		playBall();
		startMusic();
		nextLevel();
	}

	private void cleanUp() {
		@SuppressWarnings("unchecked")
		List<Actor> objects = getObjects(null);
		for (Actor actor : objects) {
			if (actor instanceof Wall || actor instanceof Paddle || actor instanceof BallStock)
				continue;
			removeObject(actor);
		}
	}

	private void nextLevel() {
		if (++level <= LevelBuilder.MAX_LEVEL) {
			new LevelBuilder(this, level).createLevel();
		} else {
			finishGame();
		}
	}

	public void finishGame() {
		stopMusic();
		Greenfoot.setWorld(new GameOverScreen(this));
	}

	@Override
	public void started() {
		startMusic();
	}

	@Override
	public void stopped() {
		stopMusic();
	}

	private void startMusic() {
		MUSIC.load();
		MUSIC.start();
	}

	private void stopMusic() {
		MUSIC.stop();
		MUSIC.close();
	}

	/**
	 * Returns a <strong>copy</strong> of the ball speed vector.
	 * 
	 * @return copy of ball speed vector
	 */
	public Vector getBallSpeed() {
		return new Vector(ballSpeed);
	}

	public Vector getGravity() {
		return gravity;
	}

	public int getLevel() {
		return level;
	}

	public int getPoints() {
		return points;
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public void extraBall(Ball ball) {
		ballsInStock.add(0, ball);
	}

	public void playBall() {
		if (getObjects(Ball.class).size() >= Cfg.MAX_BALLS_IN_PLAY)
			return;

		Ball ball = ballsInStock.remove(0);
		if (ball.getSpeed().getLength() == 0) {
			ball.setSpeed(ballSpeed);
		}
		addObject(ball, Cfg.PANEL_MIN_X + Cfg.BALL_DIAMETER / 2, Cfg.PANEL_MAX_Y - Cfg.BALL_DIAMETER / 2);
	}

	public void onBallFallingThrough(Ball ball) {
		removeObject(ball);
		if (getObjects(Ball.class).size() == 0) {
			if (ballsInStock.size() > 0) {
				Vector newSpeed = new Vector(ballSpeed);
				newSpeed.scale(1.01); // 1 Prozent schneller
				if (newSpeed.getLength() <= Cfg.MAX_BALL_SPEED) {
					ballSpeed = newSpeed;
				}
				playBall();
			} else {
				finishGame();
			}
		}
	}

	public boolean isWall(Wall wall) {
		return wall == leftWall || wall == rightWall;
	}

	public boolean isCeiling(Wall wall) {
		return wall == ceiling;
	}

	public void changeScore(int punkte) {
		this.points += punkte;
		if (this.points < 0) {
			this.points = 0;
		}
	}

	private void showScore() {
		showText("Punkte: " + points, Cfg.PANEL_MIN_X + 80, Cfg.PANEL_HEIGHT - 10);
		showText("Level " + level, Cfg.PANEL_WIDTH / 2, Cfg.PANEL_HEIGHT - 10);
	}

	private void cheats() {
		String key = Greenfoot.getKey();
		if ("y".equals(key)) {
			// "y": next level
			cleanUp();
			extraBall(new NormalBall());
			playBall();
			nextLevel();
		} else if (Greenfoot.isKeyDown("up") && Greenfoot.isKeyDown("down")) {
			// "up" + "down": activate superball power
			SuperballPower p = new SuperballPower(300);
			addObject(p, 0, Cfg.PANEL_HEIGHT + 10);
			p.activate();
		} else if (Greenfoot.isKeyDown("up") && Greenfoot.isKeyDown("left") && Greenfoot.isKeyDown("right")
				&& Greenfoot.getRandomNumber(100) % 19 == 0) {
			// "up" + "left" + "right" = extra ball
			extraBall(new NormalBall());
		} else if ("n".equals(key)) {
			// "n": extra normal ball
			extraBall(new NormalBall());
		} else if ("b".equals(key)) {
			// "b": extra bomb-ball
			extraBall(new BombBall(50 + 50 * Greenfoot.getRandomNumber(4), 300));
		} else if ("s".equals(key)) {
			// "s": extra super-ball
			extraBall(new Superball());
		}
	}

}