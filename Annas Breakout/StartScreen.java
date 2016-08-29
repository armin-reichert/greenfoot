import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Startbildschirm.
 * 
 * @author Zwickmann
 */
public class StartScreen extends World
{
	private int x;
	private int y;

	public StartScreen() {
		super(Cfg.PANEL_WIDTH, Cfg.PANEL_HEIGHT, 1, false);
		if (Cfg.RUNNING_INSIDE_BLUEJ) {
			showText("Development mode", Cfg.WALL_THICKNESS + 80, Cfg.PANEL_HEIGHT - 20);
		}

		// walls
		addObject(new Wall(Cfg.PANEL_WIDTH, Cfg.WALL_THICKNESS), Cfg.PANEL_WIDTH / 2, Cfg.WALL_THICKNESS / 2);
		addObject(new Wall(Cfg.WALL_THICKNESS, Cfg.PANEL_HEIGHT), Cfg.WALL_THICKNESS / 2, Cfg.PANEL_HEIGHT / 2);
		addObject(new Wall(Cfg.WALL_THICKNESS, Cfg.PANEL_HEIGHT), Cfg.PANEL_WIDTH - Cfg.WALL_THICKNESS / 2, Cfg.PANEL_HEIGHT / 2);

		// title
		y = 3 * Cfg.WALL_THICKNESS;
		TextView title = new TextView.Builder(true).text("Annas Breakout").color(Color.WHITE).size(2 * Cfg.NORMAL_FONTSIZE)
				.build();
		addObject(title, Cfg.PANEL_WIDTH / 2, y);

		// balloons
		y += 2 * title.getImage().getHeight();
		String[] names = { "Hagen", "Armin", "Peter", "Stanze", "Valerie", "Sophia", "Anna" };
		int colWidth = (Cfg.PANEL_WIDTH - 2 * Cfg.WALL_THICKNESS) / names.length;
		x = Cfg.WALL_THICKNESS + colWidth / 2;
		for (String name : names) {
			addObject(new Balloon.Builder().imageName(name + ".png").build(), x, y);
			x += colWidth;
		}

		// power-ups
		List<Actor> powerUps = new ArrayList<Actor>();
		powerUps.add(new BombPower());
		powerUps.add(new PointsDistractionPower(-100));
		powerUps.add(new SuperballPower());
		y += 100;
		addActorRow(powerUps, y);

		powerUps.clear();
		powerUps.add(new ExtraballPower());
		powerUps.add(new ExtraSlowballPower());
		powerUps.add(new ExtraSuperballPower());
		powerUps.add(new ExtraZickZackBallPower());
		y += 75;
		addActorRow(powerUps, y);

		powerUps.clear();
		powerUps.add(new SmallPaddlePower());
		powerUps.add(new BigPaddlePower());
		y += 75;
		addActorRow(powerUps, y);

		// paddle
		Paddle paddle = new Paddle(Paddle.NORMAL);
		addObject(paddle, 0, 0);
		paddle.toMiddle();

		// balls
		Ball normalBall = new NormalBall();
		addObject(normalBall, paddle.getX(), paddle.getY() - Cfg.PADDLE_HEIGHT);

		Greenfoot.setSpeed(50);

		prepare();
	}

	private void addActorRow(List<Actor> actors, int y) {
		int colWidth = (Cfg.PANEL_WIDTH - 2 * Cfg.WALL_THICKNESS) / actors.size();
		x = Cfg.WALL_THICKNESS + colWidth / 2;
		for (Actor a : actors) {
			addObject(a, x, y);
			x += colWidth;
		}
	}

	@Override
	public void started() {
		Breakout game = new Breakout();
		game.startGame();
		Greenfoot.setWorld(game);
	}

	/**
	 * Bereite die Welt für den Programmstart vor. Das heißt: Erzeuge die Anfangs-
	 * Objekte und füge sie der Welt hinzu.
	 */
	private void prepare()
	{
		BallStock ballstock = new BallStock();
		addObject(ballstock, 589, 585);
		ballstock.setLocation(739, 599);
		ballstock.setLocation(726, 574);
		ballstock.setLocation(737, 580);
		ballstock.setLocation(750, 590);
	}
}
