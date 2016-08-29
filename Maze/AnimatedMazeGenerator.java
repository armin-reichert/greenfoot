import java.awt.Color;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.event.EdgeEvent;
import org.zwickmann.graph.event.EdgeLifetimeEvent;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.graph.event.GraphListener;
import org.zwickmann.graph.event.VertexEvent;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.ObservableGrid;
import org.zwickmann.grid.rendering.DefaultGridRenderingModel;
import org.zwickmann.grid.rendering.GridRenderer;
import org.zwickmann.grid.rendering.GridRenderingModel;
import org.zwickmann.maze.generator.core.GridAlgorithm;

/**
 * Animation of the maze generation algorithms.
 * 
 * @author Zwickmann
 */
public class AnimatedMazeGenerator implements GridAlgorithm<Integer>, GraphListener<Integer, DefaultEdge<Integer>>
{
	private class Style extends DefaultGridRenderingModel<Integer>
	{
		public Style(int cellSize) {
			super(cellSize);
		}

		@Override
		public Color getCellBgColor(Integer p) {
			switch (grid.getState(p)) {
			case COMPLETED:
				return super.getCellBgColor(p);
			case UNVISITED:
				return getGridBgColor();
			case VISITED:
				return super.getCellActiveColor();
			default:
				return getGridBgColor();
			}
		}
	};

	private final BitGrid grid;
	private final GridAlgorithm<Integer> generator;
	private final GridRenderer<Integer, DefaultEdge<Integer>> renderer;
	private final Style style;

	public AnimatedMazeGenerator(BitGrid grid, GridAlgorithm<Integer> generator,
			GridRenderer<Integer, DefaultEdge<Integer>> renderer) {
		this.grid = grid;
		this.generator = generator;
		this.renderer = renderer;
		this.style = new Style(renderer.getRenderingModel().getGridCellSize());
	}

	@Override
	public void run(Integer start) {
		GridRenderingModel<Integer> prevStyle = renderer.getRenderingModel();
		renderer.setRenderingModel(style);
		grid.addListener(this);
		generator.run(start);
		grid.removeListener(this);
		renderer.setRenderingModel(prevStyle);
	}

	public DefaultGridRenderingModel<Integer> getStyle() {
		return style;
	}

	@Override
	public void vertexChanged(VertexEvent<Integer, DefaultEdge<Integer>> e) {
		ObservableGrid<Integer, DefaultEdge<Integer>> grid = (ObservableGrid<Integer, DefaultEdge<Integer>>) e.getSource();
		renderer.renderCell(grid, e.getVertex());
	}

	@Override
	public void edgeChanged(EdgeEvent<Integer, DefaultEdge<Integer>> e) {
		ObservableGrid<Integer, DefaultEdge<Integer>> grid = (ObservableGrid<Integer, DefaultEdge<Integer>>) e.getSource();
		if (e instanceof EdgeLifetimeEvent<?, ?>) {
			EdgeLifetimeEvent<?, ?> lte = (EdgeLifetimeEvent<?, ?>) e;
			renderer.renderPassage(grid, e.getEdge(), lte.isAdded());
		} else {
			renderer.renderPassage(grid, e.getEdge(), true);
		}
	}

	@Override
	public void graphChanged(GraphEvent<Integer, DefaultEdge<Integer>> e) {
		ObservableGrid<Integer, DefaultEdge<Integer>> grid = (ObservableGrid<Integer, DefaultEdge<Integer>>) e.getSource();
		renderer.renderGrid(grid);
	}
}
