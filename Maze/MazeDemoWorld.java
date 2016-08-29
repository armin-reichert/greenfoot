import greenfoot.World;

public class MazeDemoWorld extends World
{
	private long startTime;
	private long pauseDuration;

	public MazeDemoWorld(int width, int height, int cellSize) {
		super(width, height, cellSize);
	}

	// method to initiate having actor hold up for allotted time
	public void wait(float duration) {
		pauseDuration = (long) (duration * 1000f);
		startTime = System.currentTimeMillis();
	}

	// method to return paused state
	protected boolean paused() {
		return System.currentTimeMillis() < startTime + pauseDuration;
	}
}
