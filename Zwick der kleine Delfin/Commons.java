import greenfoot.Actor;

/**
 * Common utility methods.
 * 
 * @author Prof. Zwickmann
 * @version 13.10.2014
 */
public class Commons
{
	private Commons() {
	}

	public static void wrapActorAtWorldEdge(Actor actor) {
		final int x = actor.getX();
		final int y = actor.getY();
		final int w = actor.getWorld().getWidth();
		final int h = actor.getWorld().getHeight();
		if (x == w - 1) {
			actor.setLocation(0, y);
		} else if (x == 0) {
			actor.setLocation(w - 1, y);
		}
		if (y == h - 1) {
			actor.setLocation(x, 0);
		} else if (y == 0) {
			actor.setLocation(x, h - 1);
		}
	}

	public static String trim(String s, int n) {
		if (s.length() < n) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n - s.length(); ++i) {
				sb.append(' ');
			}
			sb.append(s);
			return sb.toString();
		} else {
			return s.substring(0, n);
		}
	}

}
