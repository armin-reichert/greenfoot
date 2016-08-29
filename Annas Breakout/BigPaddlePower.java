
/**
 * Power-up, das den Schläger vergrößert.
 * 
 * @author Zwickmann
 */
public class BigPaddlePower extends Power
{
	public BigPaddlePower(int duration) {
		super("Großer Schläger", duration);
	}

	public BigPaddlePower() {
		this(Cfg.DEFAULT_POWER_DURATION);
	}

	@Override
	void activate() {
		game().getPaddle().setPaddleSize(Paddle.WIDE, duration);
	}
}
