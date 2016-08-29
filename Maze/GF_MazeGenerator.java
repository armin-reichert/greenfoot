import greenfoot.Greenfoot;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.event.EdgeEvent;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.graph.event.VertexEvent;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.rendering.GridRenderer;
import org.zwickmann.maze.generator.core.GridAlgorithm;

public class GF_MazeGenerator extends AnimatedMazeGenerator
{
	public GF_MazeGenerator(BitGrid grid, GridAlgorithm<Integer> generator,
			GridRenderer<Integer, DefaultEdge<Integer>> renderer, int gridCellSize) {
		super(grid, generator, renderer);
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
