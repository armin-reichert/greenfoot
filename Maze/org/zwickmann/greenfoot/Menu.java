package org.zwickmann.greenfoot;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu base class.
 * 
 * @author Zwickmann
 */
public abstract class Menu extends Actor
{
	public static final Command CMD_PREV_ITEM = new Command("up", "Previous Menu Item");
	public static final Command CMD_NEXT_ITEM = new Command("down", "Next Menu Item");

	private List<Item> items = new ArrayList<>();
	private List<ValueSelector<?>> activeItems = new ArrayList<>();
	private int activeItemIndex;
	private int offsetY = 0;
	private int lineHeight = 30;

	public interface Item
	{
		public String getItemText(boolean active);
	};

	private class TextItem implements Item
	{
		private final String text;

		public TextItem(Object object) {
			this.text = String.valueOf(object);
		}

		@Override
		public String getItemText(boolean active) {
			return text;
		}

	}

	private class Space implements Item
	{
		@Override
		public String getItemText(boolean active) {
			return "";
		}
	}

	public Menu() {
		setImage((GreenfootImage) null);
	}

	@Override
	public void act() {
		home();
		show();
	}

	public void onKeyPress(String key) {
		if (CMD_PREV_ITEM.matches(key)) {
			prevItem();
		} else if (CMD_NEXT_ITEM.matches(key)) {
			nextItem();
		}
		if (activeItemIndex != -1) {
			activeItems.get(activeItemIndex).onKeyPress(key);
		}
		show();
	}

	private void prevItem() {
		if (--activeItemIndex < 0) {
			activeItemIndex = activeItems.size() - 1;
		}
		show();
	}

	private void nextItem() {
		if (++activeItemIndex == activeItems.size()) {
			activeItemIndex = 0;
		}
		show();
	}

	protected void home() {
		offsetY = 0;
	}

	protected void show() {
		home();
		for (Item item : items) {
			show(item);
		}
	}

	protected void show(Item item) {
		boolean active = activeItems.indexOf(item) == activeItemIndex;
		String text = item.getItemText(active);
		if (text != null && text.trim().length() > 0) {
			getWorld().showText(text, getX(), getY() + offsetY);
		}
		offsetY += lineHeight;
	}

	protected void line(Object object) {
		if (object instanceof ValueSelector<?>) {
			items.add((ValueSelector<?>) object);
			activeItems.add((ValueSelector<?>) object);
		} else {
			items.add(new TextItem(object));
		}
	}

	protected void line() {
		items.add(new Space());
	}
}
