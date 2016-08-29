import java.util.List;

/**
 * Power-up, das alle im Spiel befindlichen normalen Bälle für eine bestimmte
 * Zeit in Superbälle verwandelt.
 * 
 * @author Zwickmann
 */
public class SuperballPower extends Power
{
	public SuperballPower(int duration) {
		super("Super-Ball-Verwandlung", duration);
	}

	public SuperballPower() {
		this(Cfg.DEFAULT_POWER_DURATION);
	}

	@Override
	void activate() {
		@SuppressWarnings("unchecked")
		List<NormalBall> balls = getWorld().getObjects(NormalBall.class);
		for (NormalBall ball : balls) {
			Superball superBall = new Superball(duration);
			superBall.setSpeed(ball.getSpeed());
			getWorld().addObject(superBall, ball.getX(), ball.getY());
			getWorld().removeObject(ball);
		}
	}
}
