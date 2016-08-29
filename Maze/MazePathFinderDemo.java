import greenfoot.Greenfoot;

import org.zwickmann.greenfoot.DraggableActor;
import org.zwickmann.greenfoot.DropAction;

/**
 * Demonstrates path finder algorithms in a maze.
 * 
 * @author Zwickmann
 */
public class MazePathFinderDemo extends MazeGenerationDemo
{
	private DraggableActor startMarker;
	private DraggableActor targetMarker;
	private AnimatedPathFinder<?> pathFinder;

	public MazePathFinderDemo(MazeDemoConfigScreen configScreen) {
		super(configScreen);
		int markerSize = Math.max(getCellSize() * 3 / 4, 32);
		addStartMarker(markerSize);
		addTargetMarker(markerSize);
		pathFinder = createPathFinder(getSource(), getTarget());
	}

	@Override
	public String getInfoText() {
		return super.getInfoText() + "\n\nPathfinder: " + MazeDemoCfg.PATH_FINDER;
	}

	@Override
	public void run() {
		if (nextMaze) {
			Greenfoot.setWorld(new MazeDemoInfoScreen(this));
			nextMaze = false;
			firstAct = true;
			return;
		}
		pathFinder.hidePath(getTarget(), false);
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
		pathFinder.execute();
		pathFinder.showPath(getTarget(), true);
		if (MazeDemoCfg.CHANGE_ALGORITHM_AUTOMATICALLY) {
			nextGenerator();
			nextMaze = true;
			wait(3f);
		}
	}

	private AnimatedPathFinder<?> createPathFinder(Integer source, Integer target) {
		switch (MazeDemoCfg.PATH_FINDER) {
		case BFS: {
			return new GF_BFSPathFinder(grid, getSource(), getTarget(), createRenderer(null), getCellSize());
		}
		case DFS: {
			return new GF_DFSPathFinder(grid, getSource(), getTarget(), createRenderer(null), getCellSize());
		}
		default:
			throw new IllegalArgumentException("Unknown path finder: " + MazeDemoCfg.PATH_FINDER);
		}
	}

	/**
	 * @return the grid position used as the start position for path finding.
	 */
	private Integer getSource() {
		return grid.getCell(startMarker.getX(), startMarker.getY());
	}

	/**
	 * @return the grid position used as the target position for path finding.
	 */
	private Integer getTarget() {
		return grid.getCell(targetMarker.getX(), targetMarker.getY());
	}

	private void addStartMarker(int size) {
		startMarker = new DraggableActor("ladybug1.png");
		startMarker.getImage().scale(size, size);
		startMarker.setAction(new DropAction() {

			@Override
			public void drop(int dragStartX, int dragStartY, int dropX, int dropY) {
				moveSource(dragStartX, dragStartY, dropX, dropY);
			}

			@Override
			public boolean isEnabled() {
				return true;
			}
		});
		addObject(startMarker, 0, getHeight() - 1);
	}

	/**
	 * Moves the the path finder source position.
	 */
	private void moveSource(int oldX, int oldY, int x, int y) {
		pathFinder.hidePath(grid.getCell(oldX, oldY), false);
		if (pathFinder instanceof AnimatedBFSPathFinder) {
			renderGrid();
		}
		pathFinder = createPathFinder(getSource(), getTarget());
		pathFinder.execute();
		pathFinder.showPath(getTarget(), true);
	}

	private void addTargetMarker(int size) {
		targetMarker = new DraggableActor("cherries.png");
		targetMarker.getImage().scale(size, size);
		targetMarker.setAction(new DropAction() {

			@Override
			public void drop(int dragStartX, int dragStartY, int dropX, int dropY) {
				moveTarget(dragStartX, dragStartY, dropX, dropY);
			}

			@Override
			public boolean isEnabled() {
				return true;
			}
		});
		addObject(targetMarker, getWidth() - 1, 0);
	}

	/**
	 * Moves the target position for path finding.
	 */
	private void moveTarget(int oldX, int oldY, int x, int y) {
		pathFinder.hidePath(grid.getCell(oldX, oldY), false);
		if (!(pathFinder instanceof AnimatedBFSPathFinder)) {
			pathFinder = createPathFinder(getSource(), getTarget());
			pathFinder.execute();
		}
		pathFinder.showPath(getTarget(), true);
	}
}
