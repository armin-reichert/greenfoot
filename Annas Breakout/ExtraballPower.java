import greenfoot.GreenfootImage;

/**
 * Power-up, das einen neuen normalen Ball ins Spiel bringt.
 * 
 * @author Zwickmann
 */
public class ExtraballPower extends Power
{
	private static final GreenfootImage IMAGE = new GreenfootImage(40, 20);

	static {
		GreenfootImage ballImg = new GreenfootImage("steel-ball.png");
		ballImg.scale(20, 20);
		IMAGE.drawImage(ballImg, 0, 0);
		IMAGE.drawImage(ballImg, 20, 0);
	}

	public ExtraballPower() {
		super("Extra-Ball", 0);
		setImage(IMAGE);
	}

	@Override
	void activate() {
		game().extraBall(new NormalBall());
		game().playBall();
	}
}