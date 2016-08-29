import greenfoot.World; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.awt.Font;

/**
 * Displays player name, points, lifes and level.
 * 
 * @author Prof. Zwickmann
 */
public class GameInfo extends InvisibleActor
{
	private static final Font INFO_FONT = new Font("Arial Black", Font.PLAIN, 20);
	private static final Color INFO_COLOR = Color.WHITE;

	private ZwickGameLevel gameLevel;
	private final Counter levelCounter;
	private final Counter pointsCounter;
	private final LivesCounter livesCounter;
	private final Text playerName;

	public GameInfo() {
		levelCounter = new Counter(Resources.getText("LEVEL") + " ");
		levelCounter.setFont(INFO_FONT);
		levelCounter.setBackground(null);
		levelCounter.setForeground(INFO_COLOR);
		levelCounter.setValue(1);

		pointsCounter = new Counter(Resources.getText("POINTS") + ": ");
		pointsCounter.setFont(INFO_FONT);
		pointsCounter.setBackground(null);
		pointsCounter.setForeground(INFO_COLOR);
		pointsCounter.setValue(0);

		livesCounter = new LivesCounter();
		livesCounter.setLives(0);

		playerName = new Text("", null, INFO_COLOR, INFO_FONT);
	}

	void setGameLevel(ZwickGameLevel gameLevel) {
		this.gameLevel = gameLevel;
		levelCounter.setValue(gameLevel.getLevelNumber());
		playerName.setText(gameLevel.getGame().getPlayerName());
		updateInfo();
	}

	@Override
	protected void addedToWorld(World world) {
		int x = getX();
		int y = getY();
		world.addObject(levelCounter, x + 50, y);
		world.addObject(pointsCounter, x + 200, y);
		world.addObject(playerName, x + 400, y);
		world.addObject(livesCounter, x + 650, y);
	}

	@Override
	public void act() {
		updateInfo();
	}

	private void updateInfo() {
		pointsCounter.setValue(gameLevel.getGame().getZwick().getPoints());
		livesCounter.setLives(gameLevel.getGame().getZwick().getLives());
	}
}
