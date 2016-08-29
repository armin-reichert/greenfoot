import java.awt.Color;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.DepthFirstTraversal;
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
 * Animation for a depth-first-search traversal through the grid.
 * 
 * @author Zwickmann
 */
public class AnimatedDFSPathFinder extends AnimatedPathFinder<DepthFirstTraversal<Integer, DefaultEdge<Integer>>>
{
	private class Style extends DefaultGridRenderingModel<Integer>
	{
		public Style(int cellSize) {
			super(cellSize);
		}

		private Color getStateColor(Integer p) {
			return pathFinder.getState(p) == TraversalState.VISITED ? getCellActiveColor() : super.getCellBgColor(p);
		}

		@Override
		public Color getCellBgColor(Integer p) {
			return solution.contains(p) ? getCellHighlightColor() : getStateColor(p);
		}

		@Override
		public Color getPassageColor(Integer p, Direction dir) {
			if (solution.contains(p) && solution.contains(grid.getCellTowards(p, dir))) {
				return getCellHighlightColor();
			}
			return getStateColor(p);
		}
	};

	private final DepthFirstTraversal<Integer, DefaultEdge<Integer>> pathFinder;
	private final Style style;

	public AnimatedDFSPathFinder(BitGrid grid, Integer source, Integer target,
			GridRenderer<Integer, DefaultEdge<Integer>> renderer, int gridCellSize) {
		super(grid, renderer);
		pathFinder = new DepthFirstTraversal<>(grid, source, target);
		style = new Style(gridCellSize);
		renderer.setRenderingModel(style);
	}

	@Override
	protected DepthFirstTraversal<Integer, DefaultEdge<Integer>> getPathFinder() {
		return pathFinder;
	}

	@Override
	public DefaultGridRenderingModel<Integer> getStyle() {
		return style;
	}

	@Override
	public void vertexChanged(VertexEvent<Integer, DefaultEdge<Integer>> e) {
		renderer.renderCell(grid, e.getVertex());
	}

	@Override
	public void edgeChanged(EdgeEvent<Integer, DefaultEdge<Integer>> e) {
		renderer.renderPassage(grid, e.getEdge(), true);
	}

	@Override
	public void graphChanged(GraphEvent<Integer, DefaultEdge<Integer>> e) {
		renderer.renderGrid(grid);
	}
}
