import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * 
 * @author Zwickmann
 */
public class ScreenEstate
{
	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

	private static int WASTE_X, WASTE_Y;

	static {
		try {
			ClassLoader.getSystemClassLoader().loadClass("bluej.debugger.Debugger");
			WASTE_X = 400;
			WASTE_Y = 240;
		} catch (ClassNotFoundException e) {
			WASTE_X = 20;
			WASTE_Y = 130;
		}
	}

	public static int getAvailableWidth() {
		return SCREEN_SIZE.width - WASTE_X;
	}

	public static int getAvailableHeight() {
		return SCREEN_SIZE.height - WASTE_Y;
	}
}
