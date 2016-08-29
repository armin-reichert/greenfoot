/**
 * Power-up, das einen langsamen Ball ins Spiel bringt.
 * 
 * @author Zwickmann
 */
public class ExtraSlowballPower extends Power
{
	public ExtraSlowballPower() {
		super("Langsamer Extra-Ball", Cfg.DEFAULT_POWER_DURATION);
	}

	@Override
	void activate() {
		Vector speed = game().getBallSpeed();
		speed.scale(0.75);
		Slowball ball = new Slowball(duration);
		ball.setSpeed(speed);
		game().extraBall(ball);
		game().playBall();
	}
}
