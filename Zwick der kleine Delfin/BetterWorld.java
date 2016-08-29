import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * World base class with some useful functionality.
 * 
 * @author Prof. Zwickmann
 */
public class BetterWorld extends World
{
	private FlashMessageManager flashMessageMgr;
	private FrameRateDisplay frameRateDisplay;

	private boolean flashMessagesEnabled;

	public BetterWorld(int width, int height) {
		super(width, height, 1);
		setFlashMessagesEnabled(true);
		setFrameRateVisible(false);
	}

	public void setFlashMessagesEnabled(boolean enabled) {
		flashMessagesEnabled = enabled;
		if (enabled) {
			if (flashMessageMgr == null) {
				flashMessageMgr = new FlashMessageManager(20);
			}
			if (flashMessageMgr.getWorld() == null) {
				addObject(flashMessageMgr, 0, 0);
			}
		} else if (flashMessageMgr != null) {
			removeObject(flashMessageMgr);
		}
	}

	public void setFrameRateVisible(boolean enabled) {
		if (enabled) {
			if (frameRateDisplay == null) {
				frameRateDisplay = new FrameRateDisplay();
			}
			if (frameRateDisplay.getWorld() == null) {
				addObject(frameRateDisplay, getWidth() - 50, getHeight() - 10);
			}
		} else if (frameRateDisplay != null) {
			removeObject(frameRateDisplay);
		}
	}

	/**
	 * Adds a "flash" message to this world that disappears automatically after
	 * the time assigned to the message manager.
	 * 
	 * @param String
	 *          text the message text
	 */
	protected void flash(String text) {
		if (flashMessagesEnabled) {
			flashMessageMgr.showMessage(text);
		}
	}

	/**
	 * Adds the given actor at a random world location.
	 * 
	 * @param Actor
	 *          actor the actor to be added
	 */
	protected void addActorAtRandomLocation(Actor actor) {
		int x = Greenfoot.getRandomNumber(getWidth());
		int y = Greenfoot.getRandomNumber(getHeight());
		addObject(actor, x, y);
	}
}
