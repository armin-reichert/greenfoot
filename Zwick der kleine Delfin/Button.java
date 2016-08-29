import greenfoot.Greenfoot; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Write a description of class Button here.
 * 
 * @author Prof. Zwickmann
 * @version 1.11.2014
 */
public class Button extends Text
{
	private static String emphasize(String acc, String s) {
		int index = s.toLowerCase().indexOf(acc.toLowerCase());
		if (index == -1) {
			return s;
		}
		return s.substring(0, index) + "(" + s.substring(index, index + acc.length()) + ")"
				+ s.substring(index + acc.length());
	}

	private final Runnable action;
	private final String accelerator;
	private boolean active;

	public Button(String text, Runnable action, String accelerator) {
		super(accelerator != null ? emphasize(accelerator, text) : text, null, Color.WHITE, new Font("Arial Black",
				Font.PLAIN, 18));
		this.action = action;
		this.accelerator = accelerator;
		active = false;
	}

	public Runnable getAction() {
		return action;
	}

	public String getAccelerator() {
		return accelerator;
	}

	public void act() {
		if (active) {
			action.run();
			active = false;
		} else if (Greenfoot.mouseClicked(this) || accelerator != null && Greenfoot.isKeyDown(accelerator)) {
			active = true;
		}
	}

	@Override
	protected GreenfootImage createImage() {
		GreenfootImage content = super.createImage();
		int padding = 10;
		int w = content.getWidth() + 2 * padding;
		int h = content.getHeight() + 2 * padding;
		GreenfootImage img = new GreenfootImage(w, h);
		Graphics2D g = img.getAwtImage().createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(0, 0, 240));
		g.fillOval(0, 0, w - 1, h - 1);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.WHITE);
		g.drawOval(0, 0, w - 1, h - 1);
		img.drawImage(content, padding, padding);
		g.dispose();
		return img;
	}
}
