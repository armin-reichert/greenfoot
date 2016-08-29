import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Actor displaying a formatted text.
 * 
 * @author Prof. Zwickmann
 */
public class Text extends Actor
{
	private static final Font DEFAULT_FONT = new Font("Arial Black", Font.PLAIN, 12);
	private static final Color DEFAULT_BACKGROUND = Color.WHITE;
	private static final Color DEFAULT_FOREGROUND = Color.BLACK;

	protected String text;
	protected Color background;
	protected Color foreground;
	protected Font font;

	public Text(String text, Color background, Color foreground, Font font) {
		this.text = text;
		this.background = background;
		this.foreground = foreground;
		this.font = font;
		updateImage();
	}

	public Text() {
		this("", DEFAULT_BACKGROUND, DEFAULT_FOREGROUND, DEFAULT_FONT);
	}

	public void setText(String text) {
		if (text == null) {
			text = "";
		}
		if (!this.text.equals(text)) {
			this.text = text;
			updateImage();
		}
	}

	public void setForeground(Color color) {
		if (color == null) {
			color = DEFAULT_FOREGROUND;
		}
		if (!this.foreground.equals(color)) {
			this.foreground = color;
			updateImage();
		}
	}

	public void setBackground(Color color) {
		if (this.background == null && color != null || !this.background.equals(color)) {
			this.background = color;
			updateImage();
		}
	}

	public void setFont(Font font) {
		if (this.font != null && font == null || !this.font.equals(font)) {
			this.font = font;
			updateImage();
		}
	}

	protected void updateImage() {
		setImage(createImage());
	}

	protected GreenfootImage createImage() {
		Graphics2D g = getImage().getAwtImage().createGraphics();
		g.setFont(font);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
		int w = (int) Math.ceil(bounds.getWidth()) + 1;
		int h = (int) Math.ceil(bounds.getHeight()) + 1;
		// System.err.println("Text=\"" + text + "\",bounds=" + w + "," + h);
		GreenfootImage image = new GreenfootImage(w, h);
		image.setFont(font);
		if (background != null) {
			image.setColor(background);
			image.fill();
		}
		image.setColor(foreground);
		image.drawString(text, 0, font.getSize());
		g.dispose();
		return image;
	}
}
