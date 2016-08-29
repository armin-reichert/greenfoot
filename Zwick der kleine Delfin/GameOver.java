import greenfoot.Actor;
import greenfoot.World;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * The game-over screen.
 * 
 * @author Prof. Zwickmann
 */
public class GameOver extends InvisibleActor
{
	private String playerName;
	private int points;
	private int rank;
	private int lifetime;
	private int lifeCountdown;
	private List<Actor> components = new ArrayList<Actor>();

	public GameOver(int lifetime) {
		this.lifetime = lifetime;
		this.lifeCountdown = lifetime;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public void act() {
		if (lifeCountdown == lifetime) {
			Resources.Sounds.GameOver.$.play();
		}
		if (lifeCountdown > 0) {
			--lifeCountdown;
		} else {
			getWorld().removeObjects(components);
			getWorld().removeObject(this);
			lifeCountdown = lifetime;
		}
	}

	@Override
	protected void addedToWorld(World world) {
		components.clear();
		List<String> lines = new ArrayList<String>();
		lines.add(Resources.getText("GAME_OVER"));
		lines.add(playerName + " " + Resources.getText("REACHED"));
		lines.add(points + " " + Resources.getText("POINTS"));
		lines.add("(" + rank + ". " + Resources.getText("POSITION") + ")");
		Font font = Resources.Fonts.CooperBlack.getVariant(Font.BOLD, 36);
		Color fgColor = Color.RED;
		Color bgColor = null;
		int h = (int) Math.round(font.getSize() * 1.5);
		int x = world.getWidth() / 2;
		int y = (world.getHeight() - lines.size() * h) / 2;
		for (String line : lines) {
			Text text = new Text(line, bgColor, fgColor, font);
			components.add(text);
			world.addObject(text, x, y);
			y += h;
		}
	}
}
