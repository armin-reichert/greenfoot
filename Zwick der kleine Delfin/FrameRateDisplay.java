// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays the frame rate.
 * 
 * @author Prof. Zwickmann
 */
public class FrameRateDisplay extends InvisibleActor
{
	private long last = 0;
	private long elapsed = 0;
	private long frames = 0;

	public void act() {
		showFPS();
	}

	private void showFPS() {
		long now = System.nanoTime();
		if (last == 0) {
			last = now;
		}
		elapsed += (now - last);
		last = now;
		++frames;
		if (elapsed >= 1000000000L) {
			getWorld().showText("FPS: " + frames, getX(), getY());
			frames = 0;
			elapsed = 0;
		}
	}

}
