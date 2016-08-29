import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;

import java.awt.Font;
import java.util.ResourceBundle;

/**
 * Manages resources (texts, fonts, sounds).
 * 
 * @author Prof. Zwickmann
 */
public class Resources
{
	private static final ResourceBundle TEXTS = ResourceBundle.getBundle("texts");

	public static String getText(String key) {
		return TEXTS.getString(key);
	}

	public static String[] getTexts(String key) {
		return getText(key).split("\\|");
	}

	public static String getRandomText(String key) {
		String[] texts = getTexts(key);
		return texts[Greenfoot.getRandomNumber(texts.length)];
	}

	public enum Sounds {
		Explosion("explosion.mp3"),

		ExtraLife("extralife.mp3"),

		GameOver("gameover.mp3"),

		IntroMusic("intro.mp3"),

		ReefMusic("nemesis.mp3"),

		Smack("schmatzer.wav");

		private Sounds(String filename) {
			this.$ = new GreenfootSound(filename);
		}

		public final GreenfootSound $;
	}

	public enum Fonts {
		CooperBlack("fonts/COOPBL.TTF");

		private Fonts(String path) {
			try {
				$ = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(path));
			} catch (Exception e) {
				e.printStackTrace();
				$ = new Font("Arial", Font.PLAIN, 12);
			}
		}

		public Font getVariant(int style, int size) {
			return $.deriveFont(style, size);
		}

		private Font $;
	}

	private Resources() {
	}
}
