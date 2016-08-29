import greenfoot.World; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.ArrayList;
import java.util.List;

/**
 * A menu composed of buttons.
 * 
 * @author Prof. Zwickmann
 */
public class Menu extends InvisibleActor
{
	private final List<Button> buttons = new ArrayList<Button>();

	public Menu() {
	}

	void addItem(String text, String accelerator, Runnable action) {
		buttons.add(new Button(text, action, accelerator));
	}

	@Override
	protected void addedToWorld(World world) {
		if (buttons.size() == 0) {
			return;
		}
		int distance = world.getWidth() / (buttons.size() + 1);
		int x = distance;
		int y = buttons.get(0).getImage().getHeight();
		for (Button button : buttons) {
			getWorld().addObject(button, x, y);
			x += distance;
		}
	}
}
