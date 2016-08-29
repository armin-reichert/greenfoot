import greenfoot.Greenfoot;
import greenfoot.World; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.TraversalState;
import org.zwickmann.graph.event.EdgeEvent;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.graph.event.GraphListener;
import org.zwickmann.graph.event.VertexEvent;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.rendering.DefaultGridRenderingModel;
import org.zwickmann.grid.rendering.GridAsPassagesRenderer;
import org.zwickmann.grid.traversals.Square;
import org.zwickmann.misc.StopWatch;

/**
 */
public class Circles extends World implements GraphListener<Integer, DefaultEdge<Integer>>
{
	BitGrid grid;
	GridAsPassagesRenderer<Integer, DefaultEdge<Integer>> renderer;
	StopWatch watch = new StopWatch();
	private TraversalState[] vertexState;

	public Circles() {
		super(ScreenEstate.getAvailableWidth() / MazeDemoCfg.GRID_CELL_SIZE, ScreenEstate.getAvailableHeight()
				/ MazeDemoCfg.GRID_CELL_SIZE, MazeDemoCfg.GRID_CELL_SIZE);
		grid = new BitGrid(getWidth(), getHeight());
		grid.addListener(this);
		vertexState = new TraversalState[grid.numVertices()];
		renderer = new GridAsPassagesRenderer<>(getBackground().getAwtImage().createGraphics(),
				new DefaultGridRenderingModel<Integer>(MazeDemoCfg.GRID_CELL_SIZE) {
					@Override
					public Color getCellBgColor(Integer p) {
						if (vertexState[p] == TraversalState.VISITED) {
							return Color.BLUE;
						}
						return super.getCellBgColor(p);
					}
				});
	}

	@Override
	public void act() {
		watch.start("Render grid");
		renderer.renderGrid(grid);
		watch.stop();
		for (int r = 0; r < getWidth(); ++r) {
			for (Integer cell : new Square<Integer>(grid, 0, 0, r)) {
				TraversalState oldState = vertexState[cell];
				vertexState[cell] = TraversalState.VISITED;
				grid.fireVertexStateChange(new VertexEvent<>(grid, cell, oldState, vertexState[cell]));
			}
		}
		Greenfoot.stop();
	}

	@Override
	public void vertexChanged(VertexEvent<Integer, DefaultEdge<Integer>> e) {
		renderer.renderCell(grid, e.getVertex());
		Greenfoot.delay(1);
	}

	@Override
	public void edgeChanged(EdgeEvent<Integer, DefaultEdge<Integer>> e) {

	}

	@Override
	public void graphChanged(GraphEvent<Integer, DefaultEdge<Integer>> e) {
	}

}
