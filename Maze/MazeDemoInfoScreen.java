import greenfoot.Greenfoot;

import java.awt.Color;
import java.awt.Font;

import org.zwickmann.greenfoot.TextView;

/**
 * Write a description of class InfoScreen here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class MazeDemoInfoScreen extends MazeDemoWorld
{
	private final MazeDemoApp app;
	private final TextView info;
	private int state;

	public MazeDemoInfoScreen(MazeDemoApp app) {
		super(ScreenEstate.getAvailableWidth() / MazeDemoCfg.GRID_CELL_SIZE, ScreenEstate.getAvailableHeight()
				/ MazeDemoCfg.GRID_CELL_SIZE, MazeDemoCfg.GRID_CELL_SIZE);
		this.app = app;
		info = new TextView();
		info.setText("");
		info.setBgColor(Color.WHITE);
		info.setColor(Color.BLUE);
		info.setFont(new Font("Arial Narrow", Font.BOLD, ScreenEstate.getAvailableWidth() / 30));
		addObject(info, getWidth() / 2, getHeight() / 2);
		state = 0;
	}

	@Override
	public void act() {
		if (paused())
			return;
		if (state == 0) {
			info.setText(app.getInfoText());
			++state;
			wait(3f);
		} else if (state == 1) {
			removeObject(info);
			Greenfoot.setWorld(app);
		}
	}
}
