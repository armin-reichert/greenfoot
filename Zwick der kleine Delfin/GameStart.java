import greenfoot.World; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.awt.Font;

/**
 * Start screen for the Zwick game.
 * 
 * @author Prof. Zwickmann
 */
public class GameStart extends InvisibleActor
{
	private final Text greeting;
	private final Text copyright;
	private final Zwick zwick;

	public GameStart(Zwick zwick) {
		this.zwick = zwick;
		greeting = new Text(Resources.getText("GREETING"), null, Color.WHITE,
				Resources.Fonts.CooperBlack.getVariant(Font.BOLD, 36));
		copyright = new Text(Resources.getText("COPYRIGHT"), null, Color.WHITE,
				Resources.Fonts.CooperBlack.getVariant(Font.BOLD, 24));
	}

	@Override
	public void addedToWorld(World world) {
		int x = world.getWidth() / 2;
		int y = world.getHeight() / 2;
		int h = 36;
		world.addObject(greeting, x, y - 2 * h);
		world.addObject(zwick, x, y);
		world.addObject(copyright, x, world.getHeight() - h);
	}
}
