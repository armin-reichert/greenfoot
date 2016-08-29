import java.awt.Color;

import org.zwickmann.graph.BreadthFirstTraversal;
import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.TraversalState;
import org.zwickmann.graph.event.EdgeEvent;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.graph.event.VertexEvent;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.Direction;
import org.zwickmann.grid.ObservableGrid;
import org.zwickmann.grid.rendering.DefaultGridRenderingModel;
import org.zwickmann.grid.rendering.GridRenderer;

/**
 * Animation for breadth-first path-finder. Shows the distances as the BFS
 * traverses the graph and colors the cell after the distances have been
 * computed.
 * 
 * @author Zwickmann
 */
public class AnimatedBFSPathFinder extends AnimatedPathFinder<BreadthFirstTraversal<Integer, DefaultEdge<Integer>>>
{
	/**
	 * This style knows about distances from the source vertex.
	 * 
	 */
	private class Style extends DefaultGridRenderingModel<Integer>
	{
		public Style(int cellSize) {
			super(cellSize);
		}

		private Color getDistanceColor(Integer p) {
			if (maxDistance == -1) {
				return super.getCellBgColor(p);
			}
			float hue = 0.15f;
			if (maxDistance != 0) {
				hue += 0.7f * pathFinder.getDistance(p) / maxDistance;
			}
			return Color.getHSBColor(hue, .5f, 1f);
		}

		@Override
		public String getCellText(Integer p) {
			int dist = pathFinder.getDistance(p);
			return dist == -1 ? "" : String.valueOf(dist);
		}

		@Override
		public Color getCellBgColor(Integer p) {
			if (solution.contains(p)) {
				return getCellHighlightColor();
			}
			return getDistanceColor(p);
		}

		@Override
		public Color getPassageColor(Integer p, Direction dir) {
			if (solution.contains(p)) {
				Integer q = grid.getCellTowards(p, dir);
				if (q != null) {
					if (solution.contains(q)) {
						return getCellHighlightColor();
					} else {
						return getDistanceColor(p);
					}
				}
			}
			return getDistanceColor(p);
		}
	}

	private final BreadthFirstTraversal<Integer, DefaultEdge<Integer>> pathFinder;
	private final Style style;
	private int maxDistance;

	public AnimatedBFSPathFinder(BitGrid grid, Integer source, GridRenderer<Integer, DefaultEdge<Integer>> renderer,
			int gridCellSize) {
		this(grid, source, renderer, gridCellSize, -1);
	}

	public AnimatedBFSPathFinder(BitGrid grid, Integer source, GridRenderer<Integer, DefaultEdge<Integer>> renderer,
			int gridCellSize, int maxDistance) {
		super(grid, renderer);
		pathFinder = new BreadthFirstTraversal<>(grid, source);
		style = new Style(gridCellSize);
		renderer.setRenderingModel(style);
		this.maxDistance = maxDistance;
	}

	@Override
	protected BreadthFirstTraversal<Integer, DefaultEdge<Integer>> getPathFinder() {
		return pathFinder;
	}

	@Override
	public DefaultGridRenderingModel<Integer> getStyle() {
		return style;
	}

	@Override
	public void execute() {
		pathFinder.run();
		maxDistance = pathFinder.getMaxDistance();
		super.execute();
	}

	@Override
	public void vertexChanged(VertexEvent<Integer, DefaultEdge<Integer>> e) {
		TraversalState state = (TraversalState) e.getNewValue();
		if (state == TraversalState.COMPLETED && maxDistance >= 0) {
			renderer.renderCell(grid, e.getVertex());
		}
	}

	@Override
	public void edgeChanged(EdgeEvent<Integer, DefaultEdge<Integer>> e) {
		TraversalState state = (TraversalState) e.getNewValue();
		if (state == TraversalState.VISITED) {
			renderer.renderPassage(grid, e.getEdge(), true);
		}
	}

	@Override
	public void graphChanged(GraphEvent<Integer, DefaultEdge<Integer>> e) {
		renderer.renderGrid(grid);
	}
}