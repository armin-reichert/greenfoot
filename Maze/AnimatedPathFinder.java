
import java.util.HashSet;
import java.util.Set;

import org.zwickmann.graph.DefaultEdge;
import org.zwickmann.graph.PathFinder;
import org.zwickmann.graph.event.GraphEvent;
import org.zwickmann.graph.event.GraphListener;
import org.zwickmann.grid.BitGrid;
import org.zwickmann.grid.rendering.DefaultGridRenderingModel;
import org.zwickmann.grid.rendering.GridRenderer;

/**
 * Animated path finder base class.
 * 
 * @param <PF>
 *          type of path finder
 * 
 * @author Zwickmann
 */
public abstract class AnimatedPathFinder<PF extends PathFinder<Integer>> implements
		GraphListener<Integer, DefaultEdge<Integer>>
{
	protected final BitGrid grid;
	protected final GridRenderer<Integer, DefaultEdge<Integer>> renderer;
	protected final Set<Integer> solution = new HashSet<Integer>();
	protected Iterable<Integer> solutionPath;

	public AnimatedPathFinder(BitGrid grid, GridRenderer<Integer, DefaultEdge<Integer>> renderer) {
		this.grid = grid;
		this.renderer = renderer;
	}

	public GridRenderer<Integer, DefaultEdge<Integer>> getRenderer() {
		return renderer;
	}

	/**
	 * @return the path-finder
	 */
	protected abstract PF getPathFinder();

	/**
	 * @return the rendering model for this path finder animation
	 */
	protected abstract DefaultGridRenderingModel<Integer> getStyle();

	/**
	 * Runs the path finder.
	 * 
	 * @param target
	 *          the target vertex
	 */
	public void execute() {
		grid.addListener(this);
		getPathFinder().run();
		grid.removeListener(this);
	}

	/**
	 * Hides the solution from the displayed grid.
	 * 
	 * @param target
	 *          the target vertex
	 * @param animated
	 *          if hiding the path should be animated
	 */
	public void hidePath(Integer target, boolean animated) {
		solution.clear();
		renderPath(solutionPath, animated);
	}

	/**
	 * Shows the solution in the displayed grid.
	 * 
	 * @param target
	 *          the target vertex
	 * @param animated
	 *          if showing the path should be animated
	 */
	public void showPath(Integer target, boolean animated) {
		updatePath(target);
		renderPath(solutionPath, animated);
	}

	/**
	 * Updates the solution for the given target.
	 * 
	 * @param target
	 *          the target vertex
	 */
	private void updatePath(Integer target) {
		solutionPath = getPathFinder().getPath(target);
		solution.clear();
		for (Integer p : solutionPath) {
			solution.add(p);
		}
	}

	private void renderPath(Iterable<Integer> path, boolean animated) {
		if (path == null) {
			return;
		}
		Integer parent = null;
		for (Integer p : path) {
			renderer.renderCell(grid, p);
			if (parent != null) {
				DefaultEdge<Integer> edge = grid.getEdge(parent, p);
				renderer.renderPassage(grid, edge, true);
			}
			parent = p;
		}
	}

	// GraphListener interface

	@Override
	public void graphChanged(GraphEvent<Integer, DefaultEdge<Integer>> e) {
	}
}
