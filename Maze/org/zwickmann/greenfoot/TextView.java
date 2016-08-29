package org.zwickmann.greenfoot;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

/**
 * Permanent or temporary formatted text.
 * 
 * @author Zwickmann
 */
public class TextView extends Actor
{
	private int padding = 10;
	private String text = "";
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Color bgColor = Color.WHITE;
	private Color color = Color.BLACK;

	public TextView() {
	}

	private int getLineSkip() {
		return font.getSize() / 3;
	}

	private Rectangle2D computeBounds(String[] lines, Graphics2D g) {
		double w = 0;
		double h = 0;
		for (String line : lines) {
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(line, g);
			w = Math.max(w, bounds.getWidth());
			h += bounds.getHeight();
			h += getLineSkip();
		}
		return new Rectangle((int) w, (int) h);
	}

	private int computeLineWidth(String line, Graphics2D g) {
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(line, g);
		return (int) bounds.getWidth();
	}

	private void paint() {
		Graphics2D g = getImage().getAwtImage().createGraphics();
		g.setFont(font);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		String[] lines = text.split("\\n");
		Rectangle2D bounds = computeBounds(lines, g);
		int w = (int) Math.ceil(bounds.getWidth()) + 1;
		int h = (int) Math.ceil(bounds.getHeight()) + 1;
		setImage(new GreenfootImage(w + padding, h + padding));
		if (bgColor != null) {
			getImage().setColor(bgColor);
			getImage().fill();
		}
		getImage().setColor(color);
		getImage().setFont(font);
		int y = font.getSize() + padding / 2;
		for (String line : lines) {
			int x = (w - computeLineWidth(line, g)) / 2 + padding / 2;
			getImage().drawString(line, x, y);
			y += font.getSize() + getLineSkip();
		}
		g.dispose();
	}

	public void setText(String text) {
		this.text = text == null ? "" : text;
		paint();
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		paint();
	}

	public void setColor(Color color) {
		this.color = color;
		paint();
	}

	public void setSize(int size) {
		setFont(font.deriveFont(Font.PLAIN, size));
		paint();
	}

	public void setFont(Font font) {
		this.font = font;
		paint();
	}

}
