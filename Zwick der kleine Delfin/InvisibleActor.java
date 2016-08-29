import greenfoot.Actor; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * An actor without visible presentation for itself, however its child
 * components may have a visible presentation.
 * 
 * @author Prof. Zwickmann
 */
public class InvisibleActor extends Actor
{
	public InvisibleActor() {
		setImage((GreenfootImage) null);
	}
}
