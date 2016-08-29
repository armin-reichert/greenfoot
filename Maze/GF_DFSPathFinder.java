import greenfoot.Greenfoot;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.event.EdgeEvent;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.graph.event.VertexEvent;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.rendering.GridRenderer;

public class GF_DFSPathFinder extends AnimatedDFSPathFinder
{
	public GF_DFSPathFinder(BitGrid grid, Integer source, Integer target,
			GridRenderer<Integer, DefaultEdge<Integer>> renderer, int gridCellSize) {
		super(grid, source, target, renderer, gridCellSize);
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
