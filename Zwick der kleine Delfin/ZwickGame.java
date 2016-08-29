import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * Zwick, der kleine Delfin.
 * 
 * @author Anna und Prof. Zwickmann
 */
public class ZwickGame extends BetterWorld
{
	private enum State {
		Initial, IntroRunning, Ready, Running
	};

	private static final int WIDTH = 900;
	private static final int HEIGHT = 600;

	private static final int NORMAL_SPEED = 42;
	private static final int DEMO_SPEED = NORMAL_SPEED;

	private final Zwick zwick = new Zwick();
	private final GameInfo gameInfo = new GameInfo();
	private final Menu gameMenu = new Menu();
	private final GameStart gameStart = new GameStart(zwick);
	private final GameOver gameOver = new GameOver(120);

	private ZwickGameLevel currentLevel;
	private String playerName;
	private State state;

	private boolean demoMode = false;
	private final boolean showIntro = true;

	public ZwickGame() {
		super(WIDTH, HEIGHT);
		createGameMenu();
		Greenfoot.setSpeed(NORMAL_SPEED);
		state = State.Initial;
	}

	@Override
	public void act() {

		super.act();

		switch (state) {

		case Initial:
			if (showIntro) {
				Greenfoot.setWorld(new ZwickGameIntro(this));
				state = State.IntroRunning;
			} else {
				onIntroFinished();
			}
			break;

		case IntroRunning:
			// waiting for "onIntroFinished" event
			break;

		case Ready:
			// waiting for user choice
			zwick.swimAround();
			break;

		case Running:
			if (currentLevel.endedWithSuccess()) {
				nextLevel();
			} else if (currentLevel.endedWithFailure()) {
				finishGame();
			}
			break;
		}
	}

	@Override
	public void started() {
		Resources.Sounds.IntroMusic.$.playLoop();
	}

	@Override
	public void stopped() {
		Resources.Sounds.IntroMusic.$.pause();
	}

	public String getPlayerName() {
		return playerName;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public Zwick getZwick() {
		return zwick;
	}

	public ZwickGameLevel getCurrentLevel() {
		return currentLevel;
	}

	public boolean isDemoMode() {
		return demoMode;
	}

	public void onIntroFinished() {
		Greenfoot.setWorld(this);
		setBackground(new GreenfootImage("riff1.jpg"));
		addObject(gameStart, 0, 0);
		addObject(gameMenu, 0, 0);
		state = State.Ready;
	}

	private void createGameMenu() {
		gameMenu.addItem(Resources.getText("HIGHSCORES"), "h", new Runnable() {
			@Override
			public void run() {
				HighScoresView.showDialog();
			}
		});
		gameMenu.addItem(Resources.getText("NEWGAME"), "n", new Runnable() {
			@Override
			public void run() {
				startGame();
			}
		});
		gameMenu.addItem(Resources.getText("DEMOMODE"), "d", new Runnable() {
			@Override
			public void run() {
				demoMode = true;
				startGame();
			}
		});
	}

	private void startGame() {
		if (demoMode) {
			Greenfoot.setSpeed(DEMO_SPEED);
			playerName = "Demo Mode";
		} else {
			Greenfoot.setSpeed(NORMAL_SPEED);
			readPlayerName();
			if (playerName == null) {
				return;
			}
		}
		zwick.reset();
		Resources.Sounds.IntroMusic.$.stop();
		startLevel(new ReefLevel(this, 1));
		state = State.Running;
	}

	private void startLevel(ZwickGameLevel level) {
		currentLevel = level;
		gameInfo.setGameLevel(currentLevel);
		Greenfoot.setWorld(currentLevel);
	}

	public void nextLevel() {
		startLevel(new ReefLevel(this, currentLevel.getLevelNumber() + 1));
	}

	private void finishGame() {
		removeObjects(getObjects(null));
		addObject(gameMenu, 0, 0);
		if (demoMode) {
			playerName = null;
			Greenfoot.setSpeed(NORMAL_SPEED);
			demoMode = false;
		} else {
			Date endTime = Calendar.getInstance().getTime();
			HighScores scores = HighScores.read();
			scores.addScore(endTime, playerName, zwick.getPoints(), currentLevel.getLevelNumber());
			scores.write();

			gameOver.setPlayerName(playerName);
			gameOver.setPoints(zwick.getPoints());
			gameOver.setRank(scores.getPointsRank(zwick.getPoints()));
			addObject(gameOver, 0, 0);
		}
		currentLevel = null;
		state = State.Ready;
	}

	private void readPlayerName() {
		String defaultName = playerName == null ? Resources.getText("UNKNOWN_PLAYER") : playerName;
		do {
			playerName = JOptionPane.showInputDialog("Spielername eingeben", defaultName);
			if (playerName == null) { // "cancel"
				return;
			}
		} while (playerName != null && playerName.trim().length() == 0);
	}

}