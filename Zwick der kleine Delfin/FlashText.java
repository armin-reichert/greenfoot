import java.awt.Color;
import java.awt.Font;

// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A text that disappears automatically after a defined lifetime.
 * 
 * @author Prof. Zwickmann
 */
public class FlashText extends Text
{
	private static final Color DEFAULT_BACKGROUND = null;
	private static final Color DEFAULT_FOREGROUND = Color.RED;
	private static final Font DEFAULT_FONT = Resources.Fonts.CooperBlack.getVariant(Font.PLAIN, 48);

	private int lifetime; // in frames

	public FlashText() {
		this("", 60);
	}

	public FlashText(String text, int lifetime) {
		this.text = text;
		this.lifetime = lifetime;
		background = DEFAULT_BACKGROUND;
		foreground = DEFAULT_FOREGROUND;
		font = DEFAULT_FONT;
		updateImage();
	}

	@Override
	public void act() {
		if (lifetime == 0) {
			getWorld().removeObject(this);
		} else {
			--lifetime;
		}
	}

}
