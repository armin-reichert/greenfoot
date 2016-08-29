import java.util.ArrayList;
import java.util.List;
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Manages the display of flash messages.
 * 
 * @author Prof. Zwickmann
 */
public class FlashMessageManager extends InvisibleActor
{
	private final int flashTime; // in frames
	private final List<String> pendingMessages = new ArrayList<String>();

	public FlashMessageManager(int flashTime) {
		this.flashTime = flashTime;
	}

	public void act() {
		if (!isFlashMessageDisplayed() && pendingMessages.size() > 0) {
			showMessage(pendingMessages.remove(0));
		}
	}

	public void showMessage(String text, int flashTime) {
		showMessageInternal(text, flashTime);
	}

	public void showMessage(String text) {
		showMessageInternal(text, flashTime);
	}

	private void showMessageInternal(String text, int flashTime) {
		if (getWorld() == null) {
			return;
		}
		if (isFlashMessageDisplayed()) {
			pendingMessages.add(text);
		} else {
			getWorld().addObject(new FlashText(text, flashTime), getWorld().getWidth() / 2, getWorld().getHeight() / 2);
		}
	}

	private boolean isFlashMessageDisplayed() {
		return getWorld().getObjects(FlashText.class).size() > 0;
	}
}