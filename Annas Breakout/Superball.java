/**
 * Dieser Ball kann Ballons von jeder Seite aus zerstören und prallt nicht an
 * Ballons ab.
 * 
 * @author Zwickmann
 */
public class Superball extends MutatingBall
{
	public Superball() {
		this(-1);
	}

	public Superball(int lifetime) {
		super(lifetime);
	}

	void onCollisionWithBalloon(Balloon balloon) {
		balloon.hit(this);
	}
}
