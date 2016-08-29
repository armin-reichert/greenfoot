import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Game configuration.
 * 
 * @author Zwickmann
 */
public class Cfg
{
	static boolean RUNNING_INSIDE_BLUEJ = false;

	// Find out if running inside IDE
	static {
		try {
			Cfg.class.getClassLoader().loadClass("bluej.debugger.Debugger");
			RUNNING_INSIDE_BLUEJ = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Game panel dimensions
	static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	static final int PANEL_HEIGHT = RUNNING_INSIDE_BLUEJ ? SCREEN_SIZE.height - 200 : SCREEN_SIZE.height - 150;
	static final int PANEL_WIDTH = (int) (1.33 * PANEL_HEIGHT);
	static final int WALL_THICKNESS = (int) (PANEL_WIDTH / 40.0);
	static final int PANEL_MIN_X = Cfg.WALL_THICKNESS;
	static final int PANEL_MAX_X = PANEL_WIDTH - Cfg.WALL_THICKNESS - 1;
	static final int PANEL_MIN_Y = Cfg.WALL_THICKNESS;
	static final int PANEL_MAX_Y = PANEL_HEIGHT - 1;

	// Paddle dimensions
	static final int PADDLE_WIDTH_NORMAL = (int) ((PANEL_WIDTH - WALL_THICKNESS) / 8.0);
	static final int PADDLE_WIDTH_SMALL = (int) (0.75 * PADDLE_WIDTH_NORMAL);
	static final int PADDLE_WIDTH_WIDE = (int) (1.33 * PADDLE_WIDTH_NORMAL);
	static final int PADDLE_HEIGHT = (int) (PADDLE_WIDTH_NORMAL / 5.0);
	static final int PADDLE_Y = PANEL_HEIGHT - 30;

	// Balls
	static final int BALL_DIAMETER = (int) (PANEL_WIDTH / 40.0);
	static final int INITIAL_BALL_SPEED = (int) (PANEL_HEIGHT / 70.0);
	static final int MAX_BALL_SPEED = (int) (PANEL_HEIGHT / 40.0);
	static final int MAX_BALLS_IN_PLAY = 2;

	// Balloons (4x5 Format)
	static final int BALLOON_WIDTH = (int) (PANEL_WIDTH / 15.0);
	static final int BALLOON_HEIGHT = (int) (BALLOON_WIDTH * 1.25);

	// Powerups
	static final int DEFAULT_POWER_DURATION = 300;

	// Text font
	static final int NORMAL_FONTSIZE = 18;

	public static void main(String[] args) {
	}
}
