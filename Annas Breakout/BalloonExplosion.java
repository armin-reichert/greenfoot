import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Animation for balloon that explodes.
 * 
 * @author Zwickmann
 */
public class BalloonExplosion extends Actor
{
	private final Balloon balloon;
	private int countdown;

	public BalloonExplosion(Balloon balloon) {
		this.balloon = balloon;
		countdown = 30;
		setImage((GreenfootImage) null);
	}

	@Override
	public void act() {
		if (countdown == 0) {
			if (balloon.power != null) {
				getWorld().addObject(balloon.power, getX(), getY());
			}
			if (balloon.lives == 0) {
				getWorld().removeObject(balloon);
			} else {
				balloon.getImage().setTransparency(150);
				balloon.resetExploding();
			}
			getWorld().removeObject(this);
		} else {
			int transparency = Math.max(0, balloon.getImage().getTransparency() - 7);
			balloon.getImage().setTransparency(transparency);
			--countdown;
		}
	}
}
