import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Animation für Explosion eines Bomben-Balls.
 * 
 * @author Zwickmann
 */
public class BombBallExplosion extends Actor
{
	static final Font TEXTFONT = Resources.TEXTFONT.deriveFont(Font.PLAIN, 18);

	private static final String[] TEXTS = { "BOOM", "ZAPP", "FATZ", "PADAUF" };

	private final String text;
	private final int textWidth;
	private final int radius;
	private int currentRadius;
	private boolean expanding;

	public BombBallExplosion() {
		this(100);
	}

	public BombBallExplosion(int radius) {
		this.radius = radius;
		currentRadius = 0;
		expanding = true;
		text = TEXTS[Greenfoot.getRandomNumber(TEXTS.length)] + "!!!";
		Graphics2D g = getImage().getAwtImage().createGraphics();
		g.setFont(TEXTFONT);
		textWidth = (int) g.getFontMetrics(TEXTFONT).getStringBounds(text, g).getWidth();
		g.dispose();
	}

	@Override
	public void act() {
		if (expanding) {
			currentRadius += 8;
			if (currentRadius > radius) {
				expanding = false;
			} else {
				updateImage();
			}
		} else {
			currentRadius -= 16;
			if (currentRadius < 0) {
				getWorld().removeObject(this);
			} else {
				updateImage();
			}
		}
	}

	void updateImage() {
		if (currentRadius <= 0)
			return;

		int diameter = currentRadius * 2;
		setImage(new GreenfootImage(diameter, diameter));
		Graphics2D g = getImage().getAwtImage().createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.drawOval(0, 0, diameter - 1, diameter - 1);
		if (diameter >= textWidth) {
			g.setFont(TEXTFONT);
			g.drawString(text, (diameter - textWidth) / 2, currentRadius);
		}
		g.dispose();
	}
}
