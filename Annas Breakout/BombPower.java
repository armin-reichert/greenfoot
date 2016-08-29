import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Font;

/**
 * Power-up, das einen neuen Bombenball ins Spiel bringt.
 * 
 * @author Zwickmann
 */
public class BombPower extends Power
{
	private final int range;

	public BombPower(int duration, int range) {
		super("Extra-Bombenball", duration);
		this.range = range;
		createIcon();
	}

	public BombPower() {
		this(Cfg.DEFAULT_POWER_DURATION, 200);
	}

	@Override
	void activate() {
		game().extraBall(new BombBall(range, duration));
		game().playBall();
	}

	private void createIcon() {
		GreenfootImage bomb = getImage();
		GreenfootImage rangeText = new TextView.Builder(true).text(range + " kg").color(Color.BLUE)
				.font(Resources.TEXTFONT.deriveFont(Font.PLAIN, 18)).build().getImage();
		int width = Math.max(bomb.getWidth(), rangeText.getWidth());
		setImage(new GreenfootImage(width, bomb.getHeight()));
		getImage().drawImage(bomb, width / 2 - bomb.getWidth() / 2, 0);
		getImage().drawImage(rangeText, width / 2 - rangeText.getWidth() / 2, bomb.getHeight() / 2 - 6);
	}

}
