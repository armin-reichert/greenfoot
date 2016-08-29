import greenfoot.GreenfootImage;

/**
 * Power-up, das einen zusätzlichen Superball ins Spiel bringt.
 * 
 * @author Zwickmann
 */
public class ExtraSuperballPower extends Power
{
	private static final GreenfootImage IMAGE = new GreenfootImage(40, 20);

	static {
		GreenfootImage steelBall = new GreenfootImage("steel-ball.png");
		GreenfootImage goldBall = new GreenfootImage("gold-ball.png");
		steelBall.scale(20, 20);
		goldBall.scale(20, 20);
		IMAGE.drawImage(steelBall, 0, 0);
		IMAGE.drawImage(goldBall, 20, 0);
	}

	public ExtraSuperballPower() {
		super("Extra-Superball", Cfg.DEFAULT_POWER_DURATION);
		setImage(IMAGE);
	}

	@Override
	void activate() {
		game().extraBall(new Superball(duration));
		game().playBall();
	}

}
