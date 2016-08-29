/**
 * Der normale Spielball. Zerstört Ballons nur von unten.
 * 
 * @author Zwickmann
 */
public class NormalBall extends Ball
{
	@Override
	void onCollisionWithBalloon(Balloon balloon) {
		if (getSpeed().getY() < 0) {
			balloon.hit(this);
			getSpeed().revertVertical();
		}
	}
}
