import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Permanent or temporary formatted text.
 * 
 * @author Zwickmann
 */
public class TextView extends Actor
{
	public static class Builder
	{
		private final TextView tv;

		public Builder(boolean permanent) {
			tv = new TextView("", permanent);
		}

		public Builder text(String text) {
			tv.text = text;
			return this;
		}

		public Builder color(Color color) {
			tv.color = color;
			return this;
		}

		public Builder size(int size) {
			tv.setFont(tv.font.deriveFont(Font.PLAIN, size));
			return this;
		}

		public Builder font(Font font) {
			tv.font = font;
			return this;
		}

		public TextView build() {
			tv.updateImage();
			return tv;
		}
	}

	private String text;
	private Font font = Resources.TEXTFONT.deriveFont(Font.PLAIN, 24);
	private Color color = Color.RED;
	private int countdown;

	public TextView(String text, boolean permanent) {
		this.countdown = permanent ? 0 : 30;
		this.text = text;
		updateImage();
	}

	private void updateImage() {
		Graphics2D g = getImage().getAwtImage().createGraphics();
		g.setFont(font);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
		int w = (int) Math.ceil(bounds.getWidth()) + 1;
		int h = (int) Math.ceil(bounds.getHeight()) + 1;
		setImage(new GreenfootImage(w, h));
		getImage().setColor(color);
		getImage().setFont(font);
		getImage().drawString(text, 0, font.getSize());
		g.dispose();
	}

	public void setText(String text) {
		this.text = text;
		updateImage();
	}

	public void setColor(Color color) {
		this.color = color;
		updateImage();
	}

	public void setSize(int size) {
		setFont(font.deriveFont(Font.PLAIN, size));
		updateImage();
	}

	public void setFont(Font font) {
		this.font = font;
		updateImage();
	}

	@Override
	public void act() {
		if (countdown > 0) {
			--countdown;
			if (countdown == 0) {
				getWorld().removeObject(this);
			}
		}
	}
}
