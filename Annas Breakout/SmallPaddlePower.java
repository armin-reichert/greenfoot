/**
 * Power-down, das den Schläger verkleinert.
 * 
 * @author Zwickmann
 */
public class SmallPaddlePower extends Power
{
	public SmallPaddlePower(int duration) {
		super("Kleiner Schläger", duration);
	}

	public SmallPaddlePower() {
		this(Cfg.DEFAULT_POWER_DURATION);
	}

	@Override
	void activate() {
		game().getPaddle().setPaddleSize(Paddle.SMALL, duration);
	}
}
