import greenfoot.Greenfoot;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.event.EdgeEvent;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.graph.event.VertexEvent;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.rendering.GridRenderer;

public class GF_BFSPathFinder extends AnimatedBFSPathFinder
{
	public GF_BFSPathFinder(BitGrid grid, Integer source, Integer target,
			GridRenderer<Integer, DefaultEdge<Integer>> renderer, int gridCellSize) {
		super(grid, source, renderer, gridCellSize, -1);
	}

	public GF_BFSPathFinder(BitGrid grid, Integer source, Integer target,
			GridRenderer<Integer, DefaultEdge<Integer>> renderer, int gridCellSize, int maxDistance) {
		super(grid, source, renderer, gridCellSize, maxDistance);
	}

	@Override
	public void edgeChanged(EdgeEvent<Integer, DefaultEdge<Integer>> e) {
		super.edgeChanged(e);
		Greenfoot.delay(1);
	}

	@Override
	public void graphChanged(GraphEvent<Integer, DefaultEdge<Integer>> e) {
		super.graphChanged(e);
		Greenfoot.delay(1);
	}

	@Override
	public void vertexChanged(VertexEvent<Integer, DefaultEdge<Integer>> e) {
		super.vertexChanged(e);
		Greenfoot.delay(1);
	}
}
