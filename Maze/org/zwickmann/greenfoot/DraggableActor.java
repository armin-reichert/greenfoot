package org.zwickmann.greenfoot;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import greenfoot.World;

/**
 * Actor that can be dragged and dropped.
 * 
 * @author Zwickmann
 */
public class DraggableActor extends Actor
{
	private final GreenfootImage dropForbiddenImage = new GreenfootImage("forbidden.gif");
	private GreenfootImage actorImage;
	private int startX;
	private int startY;
	private DropAction action;

	public DraggableActor() {
	}

	public DraggableActor(String imageFileName) {
		setImage(imageFileName);
	}

	@Override
	public void setImage(String imageFileName) throws IllegalArgumentException {
		super.setImage(imageFileName);
		actorImage = getImage();
	}

	@Override
	public void setImage(GreenfootImage image) {
		super.setImage(image);
		actorImage = getImage();
	}

	public void setAction(DropAction action) {
		this.action = action;
	}

	@Override
	protected void addedToWorld(World world) {
		dropForbiddenImage.scale(world.getCellSize(), world.getCellSize());
	}

	@Override
	public void act() {
		MouseInfo mouse = Greenfoot.getMouseInfo();
		if (Greenfoot.mousePressed(this)) {
			startX = getX();
			startY = getY();
		} else if (Greenfoot.mouseDragged(this)) {
			setImage(action != null && action.isEnabled() ? actorImage : dropForbiddenImage);
			setLocation(mouse.getX(), mouse.getY());
		} else if (Greenfoot.mouseDragEnded(this)) {
			setImage(actorImage);
			if (action != null && action.isEnabled()) {
				setLocation(mouse.getX(), mouse.getY());
				action.drop(startX, startY, mouse.getX(), mouse.getY());
			} else {
				setLocation(startX, startY);
			}
		}
	}
}
