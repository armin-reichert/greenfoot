
/**
 * Basisklasse für Levels.
 * 
 * @author Prof. Zwickmann
 */
public abstract class ZwickGameLevel extends BetterWorld
{
	protected final ZwickGame game;
	protected final int levelNumber;

	public ZwickGameLevel(ZwickGame game, int levelNumber) {
		super(game.getWidth(), game.getHeight());
		this.game = game;
		this.levelNumber = levelNumber;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public ZwickGame getGame() {
		return game;
	}

	public abstract boolean endedWithSuccess();

	public abstract boolean endedWithFailure();

}
