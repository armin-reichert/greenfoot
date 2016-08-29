import greenfoot.Greenfoot;
import greenfoot.World;

import java.awt.Color;

/**
 * Der Game-Over Bildschirm.
 * 
 * @author Zwickmann
 */
public class GameOverScreen extends World
{
	private final Breakout game;

	private void createUI(int points, int level) {
		addObject(new Wall(Cfg.PANEL_WIDTH, Cfg.WALL_THICKNESS), Cfg.PANEL_WIDTH / 2, Cfg.WALL_THICKNESS / 2);
		addObject(new Wall(Cfg.WALL_THICKNESS, Cfg.PANEL_HEIGHT), Cfg.WALL_THICKNESS / 2, Cfg.PANEL_HEIGHT / 2);
		addObject(new Wall(Cfg.WALL_THICKNESS, Cfg.PANEL_HEIGHT), Cfg.PANEL_WIDTH - Cfg.WALL_THICKNESS / 2, Cfg.PANEL_HEIGHT / 2);
		int x = getWidth() / 2;
		addObject(new TextView.Builder(true).text("Game Over").size(60).color(Color.RED).build(), x, 200);
		addObject(new TextView.Builder(true).text("Du hast " + points + " Punkte im " + level + ". Level geschafft!")
				.size(30).color(Color.WHITE).build(), x, getHeight() / 2);
		addObject(new TextView.Builder(true).text("Drücke LEERTASTE für neues Spiel").size(30).color(Color.WHITE).build(), x, getHeight() - 100);
	}

	public GameOverScreen(Breakout game) {
		super(Cfg.PANEL_WIDTH, Cfg.PANEL_HEIGHT, 1, false);
		this.game = game;
		if (game != null) {
			createUI(game.getPoints(), game.getLevel());
		} else {
			createUI(1000, 42);
		}
	}

	public GameOverScreen() {
		this(null);
	}

	@Override
	public void act() {
		if (game != null && "space".equals(Greenfoot.getKey())) {
			Greenfoot.setWorld(game);
			game.startGame();
		}
	}
}
