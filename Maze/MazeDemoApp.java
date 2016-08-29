import greenfoot.Greenfoot;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.greenfoot.Command;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.ObservableGrid;
import org.zwickmann.grid.rendering.AbstractGridRenderer;
import org.zwickmann.grid.rendering.DefaultGridRenderingModel;
import org.zwickmann.grid.rendering.GridAsPassagesRenderer;
import org.zwickmann.grid.rendering.GridAsWallsRenderer;
import org.zwickmann.grid.rendering.GridRenderingModel;
import org.zwickmann.maze.generator.core.GridAlgorithm;
import org.zwickmann.misc.StopWatch;

/**
 * Maze demo base class.
 * 
 * @author Zwickmann
 */
public abstract class MazeDemoApp extends MazeDemoWorld
{
	static final Command CMD_SHOW_CONFIG = new Command("escape", "Open Configuration");
	static final Command CMD_RUN = new Command("space", "Run Demo");

	protected static void nextGenerator() {
		for (int i = 0; i < MazeDemoCfg.MAZE_GENERATORS.length; ++i) {
			if (MazeDemoCfg.MAZE_GENERATORS[i] == MazeDemoCfg.MAZE_GENERATOR) {
				int next = i < MazeDemoCfg.MAZE_GENERATORS.length - 1 ? i + 1 : 0;
				MazeDemoCfg.MAZE_GENERATOR = MazeDemoCfg.MAZE_GENERATORS[next];
				return;
			}
		}
	}

	protected final MazeDemoConfigScreen configScreen;

	protected final BitGrid grid;
	protected final AbstractGridRenderer<Integer, DefaultEdge<Integer>> gridRenderer;
	protected final StopWatch watch = new StopWatch();
	protected boolean firstAct = true;

	protected MazeDemoApp(MazeDemoConfigScreen configScreen) {
		super(ScreenEstate.getAvailableWidth() / MazeDemoCfg.GRID_CELL_SIZE, ScreenEstate.getAvailableHeight()
				/ MazeDemoCfg.GRID_CELL_SIZE, MazeDemoCfg.GRID_CELL_SIZE);
		this.configScreen = configScreen;
		grid = new BitGrid(ScreenEstate.getAvailableWidth() / MazeDemoCfg.GRID_CELL_SIZE,
				ScreenEstate.getAvailableHeight() / MazeDemoCfg.GRID_CELL_SIZE);
		gridRenderer = createRenderer(new DefaultGridRenderingModel<Integer>(MazeDemoCfg.GRID_CELL_SIZE));
	}

	@Override
	public void act() {
		if (paused())
			return;
		String key = Greenfoot.getKey();
		if (CMD_SHOW_CONFIG.matches(key)) {
			Greenfoot.setWorld(configScreen);
			return;
		}
		if (firstAct || MazeDemoCfg.LOOP_MODE || CMD_RUN.matches(key)) {
			run();
			wait(3f);
		}
		firstAct = false;
	}

	public abstract void run();

	public abstract String getInfoText();

	// Maze creation

	protected void createMaze(Integer start) throws Exception {
		GridAlgorithm<Integer> generator = MazeDemoCfg.MAZE_GENERATOR.getGeneratorClass().getConstructor(ObservableGrid.class)
				.newInstance(grid);
		if (MazeDemoCfg.ANIMATE_GENERATION) {
			generator = new GF_MazeGenerator(grid, generator, gridRenderer, getCellSize());
		}
		grid.clearState();
		grid.removeAllEdges();
		if (MazeDemoCfg.MAZE_GENERATOR.isStartingWithFullGrid()) {
			grid.addFullGridEdges();
			grid.fireGraphStateChange(new GraphEvent<>(grid));
		}
		watch.start("\nCreating maze, generator: " + MazeDemoCfg.MAZE_GENERATOR.getDescription());
		generator.run(start);
		watch.stop();
		System.err.println("Maze: " + grid.numVertices() + " cells, " + grid.numEdges() + " passages");
	}

	// Maze display

	protected BufferedImage getCanvas() {
		return getBackground().getAwtImage();
	}

	protected AbstractGridRenderer<Integer, DefaultEdge<Integer>> createRenderer(
			GridRenderingModel<Integer> renderingModel) {
		switch (MazeDemoCfg.GRID_DISPLAY_STYLE) {
		case PASSAGES:
			return new GridAsPassagesRenderer<>(getCanvas().createGraphics(), renderingModel);
		case WALLS:
			return new GridAsWallsRenderer<>(getCanvas().createGraphics(), renderingModel);
		default:
			throw new IllegalArgumentException("Illegal grid style: " + MazeDemoCfg.GRID_DISPLAY_STYLE);
		}
	}

	protected void renderGrid() {
		watch.start("Render grid...");
		gridRenderer.renderGrid(grid);
		watch.stop();
		Greenfoot.delay(1);
	}

	protected void clearCanvas() {
		Graphics2D g = getCanvas().createGraphics();
		g.setColor(gridRenderer.getRenderingModel().getGridBgColor());
		g.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
		Greenfoot.delay(1);
	}

	protected void sleep(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
		}
	}

	// I/O

	protected void saveCanvasAsPNG() {
		try {
			File pngFile = new File("maze_" + System.currentTimeMillis() + ".png");
			ImageIO.write(getCanvas(), "png", pngFile);
			System.err.println("Saved image as: " + pngFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
