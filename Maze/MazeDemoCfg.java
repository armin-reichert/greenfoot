
import org.zwickmann.graph.PathFinderAlgorithm;
import org.zwickmann.graph.TraversalState;
import org.zwickmann.grid.rendering.GridDisplayStyle;
import org.zwickmann.maze.generator.AldousBroderUST;
import org.zwickmann.maze.generator.BinaryTree;
import org.zwickmann.maze.generator.Eller;
import org.zwickmann.maze.generator.IterativeDFS;
import org.zwickmann.maze.generator.KruskalMST;
import org.zwickmann.maze.generator.PrimMST;
import org.zwickmann.maze.generator.RandomTraversal;
import org.zwickmann.maze.generator.RecursiveDFS;
import org.zwickmann.maze.generator.RecursiveDivision;
import org.zwickmann.maze.generator.core.MazeGeneratorInfo;
import org.zwickmann.maze.generator.wilson.WilsonUSTCollapsingCircle;
import org.zwickmann.maze.generator.wilson.WilsonUSTCollapsingWalls;
import org.zwickmann.maze.generator.wilson.WilsonUSTExpandingCircle;
import org.zwickmann.maze.generator.wilson.WilsonUSTExpandingCircles;
import org.zwickmann.maze.generator.wilson.WilsonUSTExpandingSquare;
import org.zwickmann.maze.generator.wilson.WilsonUSTHilbertCurve;
import org.zwickmann.maze.generator.wilson.WilsonUSTLeftToRightSweep;
import org.zwickmann.maze.generator.wilson.WilsonUSTRandomCell;
import org.zwickmann.maze.generator.wilson.WilsonUSTRightToLeftSweep;
import org.zwickmann.maze.generator.wilson.WilsonUSTRowsTopDown;

/**
 * Maze demo configuration.
 * 
 * @author Zwickmann
 */
public class MazeDemoCfg
{
	public static MazeGeneratorInfo[] MAZE_GENERATORS = {
			new MazeGeneratorInfo(IterativeDFS.class, false, TraversalState.UNVISITED, "Iterative Depth-First-Traversal"),
			new MazeGeneratorInfo(RecursiveDFS.class, false, TraversalState.UNVISITED,
					"Recursive Depth-First-Traversal (stack might overflow!)"),
			new MazeGeneratorInfo(RandomTraversal.class, false, TraversalState.UNVISITED, "Random Breadth-First-Traversal"),
			new MazeGeneratorInfo(KruskalMST.class, false, TraversalState.UNVISITED, "Kruskal Minimum Spanning Tree"),
			new MazeGeneratorInfo(PrimMST.class, false, TraversalState.UNVISITED, "Prim Minimum Spanning Tree"),
			new MazeGeneratorInfo(RecursiveDivision.class, true, TraversalState.COMPLETED, "Recursive Division"),
			new MazeGeneratorInfo(BinaryTree.class, false, TraversalState.UNVISITED, "Binary Tree"),
			new MazeGeneratorInfo(Eller.class, false, TraversalState.UNVISITED, "Eller's Algorithm"),
			new MazeGeneratorInfo(AldousBroderUST.class, false, TraversalState.UNVISITED,
					"Aldous-Broder Uniform Spanning Tree (slow!)"),
			new MazeGeneratorInfo(WilsonUSTRandomCell.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (random)"),
			new MazeGeneratorInfo(WilsonUSTRowsTopDown.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (row-by-row)"),
			new MazeGeneratorInfo(WilsonUSTLeftToRightSweep.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (left to right)"),
			new MazeGeneratorInfo(WilsonUSTRightToLeftSweep.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (right to left)"),
			new MazeGeneratorInfo(WilsonUSTCollapsingWalls.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (collapsing horizontally)"),
			new MazeGeneratorInfo(WilsonUSTExpandingCircle.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (circle, expanding)"),
			new MazeGeneratorInfo(WilsonUSTCollapsingCircle.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (circle, collapsing)"),
			new MazeGeneratorInfo(WilsonUSTExpandingCircles.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (circles, expanding)"),
			new MazeGeneratorInfo(WilsonUSTExpandingSquare.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (square, expanding)"),
			new MazeGeneratorInfo(WilsonUSTHilbertCurve.class, false, TraversalState.UNVISITED,
					"Wilson Uniform Spanning Tree (Hilbert curve)"), };

	public static Integer[] GRID_CELLSIZES = { 128, 64, 32, 16, 8, 4, 2 };
	public static int GRID_CELL_SIZE = 16;
	public static GridDisplayStyle GRID_DISPLAY_STYLE = GridDisplayStyle.PASSAGES;
	public static MazeGeneratorInfo MAZE_GENERATOR = MAZE_GENERATORS[0];
	public static boolean ANIMATE_GENERATION = true;
	public static PathFinderAlgorithm PATH_FINDER = PathFinderAlgorithm.BFS;
	public static boolean LOOP_MODE = true;
	public static boolean CHANGE_ALGORITHM_AUTOMATICALLY = true;
}
