import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.List;

/**
 * Die Korallenriff-Welt-Levels.
 * 
 * @author Prof. Zwickmann
 */
public class ReefLevel extends ZwickGameLevel
{
	private enum State {
		Initial, Running, Success, Failure
	};

	private static final byte[][] BOMB_TYPES = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, 1 },
			{ 1, -1 }, { -1, -1 } };

	private static final int NUM_REEF_IMAGES = 8;

	private final int numFishes;
	private final int numBombs;
	private final int numLobsters;
	private final int numObstacles;
	private final int numPearls;
	private final int bombSpeed;
	private final int dolphinSpeed;

	private int numLobstersAdded;
	private int numPearlsAdded;

	private int endCountdown;
	private List<Bomb> remainingBombs;

	private State state;

	public ReefLevel(ZwickGame game, int levelNumber) {
		super(game, levelNumber);

		numFishes = 5;
		numBombs = Math.min(levelNumber - 1, 8);
		numLobsters = Math.min(10 + 5 * levelNumber, 50);
		numObstacles = levelNumber > 4 ? Math.min(levelNumber - 4, 4) : 0;
		numPearls = 1;
		bombSpeed = levelNumber + 1;
		dolphinSpeed = 20 + 2 * levelNumber;

		numLobstersAdded = 0;
		numPearlsAdded = 0;

		int imageIndex = (levelNumber - 1) % NUM_REEF_IMAGES + 1;
		setBackground(new GreenfootImage("riff" + imageIndex + ".jpg"));

		addObject(game.getGameInfo(), 0, 30);
		setPaintOrder(GameInfo.class, LivesCounter.class, FlashText.class, Text.class);
		setFrameRateVisible(true);
		state = State.Initial;
	}

	@Override
	public void act() {

		super.act();

		if (game.isDemoMode() && "escape".equals(Greenfoot.getKey())) {
			flash("Interrupted");
			Greenfoot.delay(40);
			state = State.Failure;
		}

		switch (state) {

		case Initial:
			populateLevel();
			start();
			break;

		case Running:
			if (game.isDemoMode()) {
				getGame().getZwick().swimAround();
			}
			if (isComplete()) {
				state = State.Success;
			} else if (getGame().getZwick().isDead()) {
				remainingBombs = getObjects(Bomb.class);
				endCountdown = remainingBombs.size() * 30;
				if (endCountdown < 200) {
					endCountdown = 200;
				}
				state = State.Failure;
			} else {
				addLobstersSometimes(20);
				addPearlSometimes(2);
			}
			break;

		case Failure:
			playEndSequence();
			break;

		case Success:
			game.nextLevel();
			break;
		}
	}

	@Override
	public void started() {
		Resources.Sounds.ReefMusic.$.playLoop();
	}

	@Override
	public void stopped() {
		Resources.Sounds.ReefMusic.$.pause();
	}

	@Override
	public boolean endedWithSuccess() {
		return state == State.Success;
	}

	@Override
	public boolean endedWithFailure() {
		return state == State.Failure;
	}

	private boolean isComplete() {
		return getObjects(Lobster.class).isEmpty();
	}

	private void start() {
		Resources.Sounds.ReefMusic.$.setVolume(100);
		Resources.Sounds.ReefMusic.$.playLoop();
		flash(Resources.getText("LEVEL") + " " + levelNumber);
		Greenfoot.delay(30);
		state = State.Running;
	}

	private void playEndSequence() {
		if (endCountdown > 0) {
			if (!remainingBombs.isEmpty() && endCountdown % 30 == 0) {
				remainingBombs.remove(0).consumed();
			}
			int volume = Resources.Sounds.ReefMusic.$.getVolume();
			if (volume > 0) {
				Resources.Sounds.ReefMusic.$.setVolume(volume - 1);
			}
			--endCountdown;
		} else {
			Resources.Sounds.ReefMusic.$.stop();
			Greenfoot.setWorld(game);
		}
	}

	private void populateLevel() {
		// dolphin
		addObject(getGame().getZwick(), getWidth() / 2, getHeight() / 2);
		getGame().getZwick().setSpeed(dolphinSpeed);
		getGame().getZwick().turnRight();

		// reef population
		addObject(new Anchor(), getWidth() - 100, getHeight() - 100);

		for (int i = 0; i < numFishes; ++i) {
			addActorAtRandomLocation(new Greenfish());
		}

		for (int i = 0; i < numObstacles; ++i) {
			addActorAtRandomLocation(new Stone());
		}

		for (int i = 0; i < numLobsters / 3; ++i) {
			addLobster();
		}

		int i = 0;
		while (i < numBombs) {
			final Bomb bomb = new Bomb(BOMB_TYPES[i][0] * bombSpeed, BOMB_TYPES[i][1] * bombSpeed);
			addActorAtRandomLocation(bomb);
			if (getGame().getZwick().isHitByBomb()) {
				removeObject(bomb);
			} else {
				++i;
			}
		}
	}

	private void addLobstersSometimes(int probability) {
		if (numLobstersAdded >= numLobsters)
			return;

		if (Greenfoot.getRandomNumber(100) < probability) {
			addLobster();
		}
	}

	private void addLobster() {
		Lobster lobster = new Lobster();
		lobster.setSpeed(3 + Greenfoot.getRandomNumber(getLevelNumber()));
		addActorAtRandomLocation(lobster);
		++numLobstersAdded;
	}

	private void addPearlSometimes(int probability) {
		if (numPearlsAdded >= numPearls)
			return;

		if (Greenfoot.getRandomNumber(100) < probability) {
			Pearl pearl = new Pearl();
			int x = Greenfoot.getRandomNumber(getWidth());
			int y = getHeight() - pearl.getImage().getHeight() - Greenfoot.getRandomNumber(getHeight() / 2);
			addObject(pearl, x, y);
			++numPearlsAdded;
		}
	}

}