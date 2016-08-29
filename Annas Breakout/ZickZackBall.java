/**
 * Ein Ball mit einer Zick-Zack-Flugbahn.
 * 
 * @author Zwickmann
 */
public class ZickZackBall extends MutatingBall
{
	private int phase;

	public ZickZackBall(int lifetime) {
		super(lifetime);
		phase = 0;
		temporaryState = false;
	}

	@Override
	public void move() {
		Vector speed = getPhaseSpeed();
		int newX = (int) Math.round(getX() + speed.getX());
		int newY = (int) Math.round(getY() + speed.getY());
		setLocation(newX, newY);
		if (++phase == 4)
			phase = 0;
		temporaryState = (phase == 1 || phase == 3);
	}

	private Vector getPhaseSpeed() {
		Vector speed = getSpeed();
		int alpha = 45;
		double length = 2 * speed.getLength() / (2.0 * Math.cos(Math.toRadians(alpha)));
		if (phase == 0 || phase == 3) {
			return new Vector(speed.getDirection() + 360 - alpha, length);
		} else {
			return new Vector(speed.getDirection() + alpha, length);
		}
	}

	@Override
	void onCollisionWithBalloon(Balloon balloon) {
		if (getSpeed().getY() < 0) {
			balloon.hit(this);
			getSpeed().revertVertical();
		}
	}
}
