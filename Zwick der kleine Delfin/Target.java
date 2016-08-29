import greenfoot.GreenfootSound; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Interface for Zwick's targets.
 * 
 * @author Prof. Zwickmann
 */
public interface Target
{
	public int getPointsChange(int points);

	public int getLifesChange(int life);

	public String getConsumedMessage();

	public GreenfootSound getConsumedSound();

	public void consumed();
}
