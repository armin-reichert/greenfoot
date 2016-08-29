import greenfoot.Greenfoot;
import greenfoot.World;

import org.zwickmann.greenfoot.Command;

/**
 * Maze demo configuration screen.
 * 
 * @author Zwickmann
 */
public class MazeDemoConfigScreen extends World
{
	static final Command CMD_START_GENERATION_DEMO = new Command("f1", "Maze Generation Demo");
	static final Command CMD_START_PATHFINDER_DEMO = new Command("f2", "Path Finding Demo");

	private final MazeDemoConfigMenu menu;

	public MazeDemoConfigScreen() {
		super(ScreenEstate.getAvailableWidth(), ScreenEstate.getAvailableHeight(), 1);
		menu = new MazeDemoConfigMenu();
		addObject(menu, getWidth() / 2, 20);
	}

	@Override
	public void act() {
		String key = Greenfoot.getKey();
		if (CMD_START_GENERATION_DEMO.matches(key)) {
			Greenfoot.setWorld(new MazeDemoInfoScreen(new MazeGenerationDemo(this)));
		} else if (CMD_START_PATHFINDER_DEMO.matches(key)) {
			Greenfoot.setWorld(new MazeDemoInfoScreen(new MazePathFinderDemo(this)));
		} else {
			menu.onKeyPress(key);
		}
	}
}
