/**
 * Basisklasse für Power-Ups.
 * 
 * @author Zwickmann
 */
public abstract class Power extends BreakoutActor
{
	protected final String name;
	protected final int duration;

	public Power(String name, int duration) {
		this.name = name;
		this.duration = duration;
		setSpeed(new Vector(0.0, 4.0));
	}

	@Override
	public void act() {
		if (getWorld() instanceof StartScreen)
			return;

		move();
		Paddle paddle = (Paddle) getOneIntersectingObject(Paddle.class);
		if (paddle != null) {
			activate();
			if (name != null) {
				getWorld().addObject(new TextView.Builder(false).text(name).build(), getX(), getY() - 50);
			}
			getWorld().removeObject(this);
		} else if (getY() >= Cfg.PANEL_MAX_Y) {
			getWorld().removeObject(this);
		}
	}

	abstract void activate();
}
