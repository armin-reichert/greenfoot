import greenfoot.Greenfoot;
import greenfoot.World; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.util.List;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.Direction;
import org.zwickmann.grid.rendering.DefaultGridRenderingModel;
import org.zwickmann.grid.rendering.GridAsPassagesRenderer;
import org.zwickmann.grid.traversals.HilbertCurve;

/**
 * Draws a Hilbert curve inside a grid from all 4 corners and runs
 * 
 * @author Zwickmann
 */
public class HilbertCurveWorld extends World
{
	private static class Orientation
	{
		int startX, startY;
		Direction[] dir = new Direction[4];

		Orientation(int startX, int startY, Direction... dir) {
			this.startX = startX;
			this.startY = startY;
			this.dir = dir;
		}
	}

	private BitGrid grid;
	private GridAsPassagesRenderer<Integer, DefaultEdge<Integer>> renderer;
	private DefaultGridRenderingModel<Integer> normalRendering;
	private Orientation[] orientations;

	private int depth;
	private int gridSize;
	private int gridCellSize;

	public HilbertCurveWorld() {
		super(512, 512, 1);
	}

	private void setDepth(int i) {
		depth = i;
		gridSize = 1 << depth;
		gridCellSize = getHeight() / gridSize;
		normalRendering = new DefaultGridRenderingModel<Integer>(gridCellSize) {
			@Override
			public Color getCellBgColor(Integer p) {
				return grid.degree(p) == 0 ? Color.BLACK : Color.WHITE;
			};
		};
		renderer = new GridAsPassagesRenderer<>(getBackground().getAwtImage().createGraphics(), normalRendering);
		grid = new BitGrid(gridSize, gridSize);
		orientations = createOrientations(gridSize);
	}

	@Override
	public void act() {
		for (int d = 1; d <= 8; ++d) {
			setDepth(d);
			for (Orientation orient : orientations) {
				grid.removeAllEdges();
				getBackground().setColor(Color.BLACK);
				getBackground().fill();
				renderer.renderGrid(grid);
				showHilbertCurve(orient);
				Greenfoot.delay(200);
			}
		}
	}

	private Orientation[] createOrientations(int gridSize) {
		return new Orientation[] {
				new Orientation(0, 0, Direction.W, Direction.N, Direction.E, Direction.S),
				new Orientation(gridSize - 1, 0, Direction.N, Direction.E, Direction.S, Direction.W),
				new Orientation(0, gridSize - 1, Direction.S, Direction.W, Direction.N, Direction.E),
				new Orientation(gridSize - 1, gridSize - 1, Direction.S, Direction.E, Direction.N, Direction.W), };
	};

	private void showHilbertCurve(Orientation orient) {
		HilbertCurve<Integer> curve = new HilbertCurve<>(grid, depth, orient.dir[0], orient.dir[1], orient.dir[2],
				orient.dir[3]);
		List<Integer> path = curve.getPath(grid.getCell(orient.startX, orient.startY));
		drawCurve(path);
		Greenfoot.delay(100);
		runPathFinder(path);
		Greenfoot.delay(100);
	}

	private void drawCurve(List<Integer> path) {
		DefaultEdge<Integer> e = new DefaultEdge<Integer>(0, 0);
		Integer parent = null;
		for (Integer cell : path) {
			if (parent != null) {
				e.setHead(parent);
				e.setTail(cell);
				grid.addEdge(e);
				renderer.renderPassage(grid, e, true);
				Greenfoot.delay(1);
			}
			parent = cell;
		}
	}

	private void runPathFinder(List<Integer> path) {
		AnimatedBFSPathFinder pathFinder = new AnimatedBFSPathFinder(grid, path.get(0), renderer, gridCellSize);
		pathFinder.execute();
		renderer.setRenderingModel(normalRendering);
	}
}
