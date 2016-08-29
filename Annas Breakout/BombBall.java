import java.util.List;

/**
 * Dieser Ball zerstört auch alle Ballons in einem bestimmten Umkreis des
 * getroffenen Ballons.
 */
public class BombBall extends MutatingBall
{
	private final int range;

	public BombBall(int range, int lifetime) {
		super(lifetime);
		this.range = range;
	}

	public BombBall() {
		this(0, -1);
	}

	@Override
	void onCollisionWithBalloon(Balloon balloon) {
		if (balloon.isExploding())
			return;

		@SuppressWarnings("unchecked")
		List<Balloon> balloonsInRange = getObjectsInRange(range, Balloon.class);
		for (Balloon b : balloonsInRange) {
			if (!b.isExploding()) {
				b.hit(this);
			}
		}
		getSpeed().revertVertical();
		getWorld().addObject(new BombBallExplosion(range), getX(), getY());
	}
}