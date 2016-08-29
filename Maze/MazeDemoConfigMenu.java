import org.zwickmann.graph.PathFinderAlgorithm;
import org.zwickmann.greenfoot.BooleanValueSelector;
import org.zwickmann.greenfoot.Menu;
import org.zwickmann.greenfoot.ValueSelector;
import org.zwickmann.grid.rendering.GridDisplayStyle;
import org.zwickmann.maze.generator.core.MazeGeneratorInfo;

/**
 * Menu for editing the maze demo configuration.
 * 
 * @author Zwickmann
 */
public class MazeDemoConfigMenu extends Menu
{
	private final ValueSelector<Integer> gridCellSize = new ValueSelector<Integer>("Grid Resolution",
			MazeDemoCfg.GRID_CELLSIZES) {

		@Override
		protected void selectionChanged(Integer newValue) {
			MazeDemoCfg.GRID_CELL_SIZE = newValue;
		}

		@Override
		public String getValueDisplayText() {
			int w = ScreenEstate.getAvailableWidth() / MazeDemoCfg.GRID_CELL_SIZE, h = ScreenEstate.getAvailableHeight()
					/ MazeDemoCfg.GRID_CELL_SIZE, n = w * h;
			return w + " x " + h + " = " + n + " cells";
		};
	};

	private final ValueSelector<GridDisplayStyle> gridStyle = new ValueSelector<GridDisplayStyle>("Drawing Style",
			GridDisplayStyle.values()) {

		@Override
		protected void selectionChanged(GridDisplayStyle newValue) {
			MazeDemoCfg.GRID_DISPLAY_STYLE = newValue;
		}
	};

	private final ValueSelector<MazeGeneratorInfo> mazeGeneratorSelector = new ValueSelector<MazeGeneratorInfo>(
			"Maze Generation Algorithm", MazeDemoCfg.MAZE_GENERATORS) {

		@Override
		protected void selectionChanged(MazeGeneratorInfo newValue) {
			MazeDemoCfg.MAZE_GENERATOR = newValue;
		}

		@Override
		public String getValueDisplayText() {
			return MazeDemoCfg.MAZE_GENERATORS[selection].getDescription();
		};
	};

	private final ValueSelector<PathFinderAlgorithm> pathFinderAlgorithm = new ValueSelector<PathFinderAlgorithm>(
			"Pathfinder Algorithm", PathFinderAlgorithm.values()) {

		@Override
		protected void selectionChanged(PathFinderAlgorithm newValue) {
			MazeDemoCfg.PATH_FINDER = newValue;
		}
	};

	private final BooleanValueSelector animateGeneration = new BooleanValueSelector("Animate Generation") {

		@Override
		protected void selectionChanged(Boolean newValue) {
			MazeDemoCfg.ANIMATE_GENERATION = newValue;
		};
	};

	private final BooleanValueSelector autoChangeGeneration = new BooleanValueSelector("Change Algorithm Automatically") {

		@Override
		protected void selectionChanged(Boolean newValue) {
			MazeDemoCfg.CHANGE_ALGORITHM_AUTOMATICALLY = newValue;
		};
	};

	private final BooleanValueSelector loopMode = new BooleanValueSelector("Run in loop") {

		@Override
		protected void selectionChanged(Boolean newValue) {
			MazeDemoCfg.LOOP_MODE = newValue;
		};
	};

	public MazeDemoConfigMenu() {
		line("Maze Demo Settings");
		line();
		line(gridCellSize);
		line(gridStyle);
		line();
		line(mazeGeneratorSelector);
		line(animateGeneration);
		line(autoChangeGeneration);
		line(loopMode);
		line();
		line(pathFinderAlgorithm);
		line();
		line(MazeDemoConfigScreen.CMD_START_GENERATION_DEMO);
		line(MazeDemoConfigScreen.CMD_START_PATHFINDER_DEMO);
		line(MazeDemoApp.CMD_SHOW_CONFIG);
		line(MazeGenerationDemo.CMD_RUN);

		// Synchronize menu with configuration values
		gridCellSize.selectValue(MazeDemoCfg.GRID_CELL_SIZE);
		gridStyle.selectValue(MazeDemoCfg.GRID_DISPLAY_STYLE);
		mazeGeneratorSelector.selectValue(findCurrentGenerator());
		animateGeneration.selectValue(MazeDemoCfg.ANIMATE_GENERATION);
		autoChangeGeneration.selectValue(MazeDemoCfg.CHANGE_ALGORITHM_AUTOMATICALLY);
		pathFinderAlgorithm.selectValue(MazeDemoCfg.PATH_FINDER);
		loopMode.selectValue(MazeDemoCfg.LOOP_MODE);
	}

	private static MazeGeneratorInfo findCurrentGenerator() {
		for (int i = 0; i < MazeDemoCfg.MAZE_GENERATORS.length; ++i) {
			if (MazeDemoCfg.MAZE_GENERATORS[i] == MazeDemoCfg.MAZE_GENERATOR) {
				return MazeDemoCfg.MAZE_GENERATOR;
			}
		}
		return MazeDemoCfg.MAZE_GENERATORS[0];
	}

}