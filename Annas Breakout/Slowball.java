/**
 * Ein langsamer Ball, der sich nach einer bestimmten Zeit in einen normalen
 * Ball verwandelt.
 * 
 * @author Zwickmann
 */
public class Slowball extends MutatingBall
{
	public Slowball(int lifetime) {
		super(lifetime);
	}

	@Override
	void onCollisionWithBalloon(Balloon balloon) {
		if (getSpeed().getY() < 0) {
			balloon.hit(this);
			getSpeed().revertVertical();
		}
	}

	@Override
	void onLifetimeFinished() {
		Ball normalBall = new NormalBall();
		normalBall.setSpeed(game().getBallSpeed());
		getWorld().addObject(normalBall, getX(), getY());
		getWorld().removeObject(this);
	}
}
