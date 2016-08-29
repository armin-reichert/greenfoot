import greenfoot.Greenfoot; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.awt.Font;

/**
 * Die Einführungssequenz im Zwicki-Game.
 * 
 * @author Prof. Zwickmann
 * @version 18.10.2014
 */
public class ZwickGameIntro extends BetterWorld
{
	private enum State {
		Initial, Running, Complete
	};

	private static final int TEXT_DELAY = 78;
	private static final int MAX_ROTATION_SPEED = 60;
	private static final Color TEXT_COLOR = Color.WHITE;

	private final ZwickGame game;
	private final Text greeting;
	private final Anna anna;
	private int size;
	private int speedTimer;
	private int speed;
	private String[] lines;
	private int currentLine;
	private int textTimer;
	private State state;

	public ZwickGameIntro(ZwickGame game) {
		super(game.getWidth(), game.getHeight());
		this.game = game;

		anna = new Anna();
		size = Math.min(anna.getImage().getWidth(), anna.getImage().getHeight());
		speedTimer = 0;
		speed = 10;
		lines = Resources.getText("INTRO").split("\n");
		currentLine = 0;
		textTimer = 0;
		greeting = new Text("", null, TEXT_COLOR, Resources.Fonts.CooperBlack.getVariant(Font.BOLD, 36));
		state = State.Initial;
	}

	@Override
	public void act() {

		super.act();

		switch (state) {

		case Initial:
			showInitialScreen();
			break;

		case Running:
			showAnimation();
			break;

		case Complete:
			game.onIntroFinished();
			break;
		}
	}

	private boolean canceledByUser() {
		return null != Greenfoot.getKey();
	}

	private void showInitialScreen() {
		Greenfoot.delay(200);
		addObject(anna, getWidth() / 2, getHeight() / 2);
		addObject(greeting, getWidth() / 2, getHeight() - 50);
		greeting.setText(lines[currentLine++]);
		Greenfoot.delay(60);
		state = State.Running;
	}

	private void showAnimation() {
		if (canceledByUser()) {
			state = State.Complete;
			return;
		}
		if (currentLine < lines.length && textTimer == 0) {
			greeting.setText(lines[currentLine++]);
			textTimer = TEXT_DELAY;
		} else {
			--textTimer;
		}
		if (++speedTimer % 10 == 0) {
			int faster = (int) Math.round(speed * 1.1);
			if (faster <= MAX_ROTATION_SPEED) {
				speed = faster;
			}
			size -= 18;
			if (size > 0) {
				anna.getImage().scale(size, size);
			}
		}
		anna.getImage().rotate(speed);
		if (currentLine == lines.length && textTimer == 0) {
			stop();
		}
	}

	private void stop() {
		state = State.Complete;
	}

}
