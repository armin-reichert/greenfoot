/**
 * Ein spezieller Ball der nach einer bestimmten Zeit zu einem normalen Ball
 * wird.
 * 
 * @author Zwickmann
 */
public abstract class MutatingBall extends Ball
{
	private int lifetime;

	public MutatingBall(int lifetime) {
		this.lifetime = lifetime;
	}

	@Override
	public final void act() {
		if (lifetime == 0) {
			onLifetimeFinished();
		} else {
			--lifetime;
			super.act();
		}
	}

	void onLifetimeFinished() {
		Ball normalBall = new NormalBall();
		normalBall.setSpeed(getSpeed());
		getWorld().addObject(normalBall, getX(), getY());
		getWorld().removeObject(this);
	}

}
