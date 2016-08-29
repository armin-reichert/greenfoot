import greenfoot.Greenfoot;

/**
 * Demonstration of maze generation algorithms.
 * 
 * @author Zwickmann
 */
public class MazeGenerationDemo extends MazeDemoApp
{
	protected boolean nextMaze;

	public MazeGenerationDemo(MazeDemoConfigScreen configScreen) {
		super(configScreen);
	}

	@Override
	public String getInfoText() {
		int gw = ScreenEstate.getAvailableWidth() / MazeDemoCfg.GRID_CELL_SIZE;
		int gh = ScreenEstate.getAvailableHeight() / MazeDemoCfg.GRID_CELL_SIZE;
		return MazeDemoCfg.MAZE_GENERATOR.getDescription() + "\n" + gw + " x " + gh + " = " + (gw * gh) + " cells";
	}

	@Override
	public void run() {
		if (nextMaze) {
			Greenfoot.setWorld(new MazeDemoInfoScreen(this));
			nextMaze = false;
			firstAct = true;
			return;
		}
		clearCanvas();
		try {
			createMaze(0);
			if (!MazeDemoCfg.ANIMATE_GENERATION) {
				renderGrid();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} catch (StackOverflowError e) {
			System.err.println("*** Stack Overflow ***");
			Greenfoot.stop();
		}
		if (MazeDemoCfg.CHANGE_ALGORITHM_AUTOMATICALLY) {
			nextGenerator();
			nextMaze = true;
			wait(3f);
		}
	}
}
