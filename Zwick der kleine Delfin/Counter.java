// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Anzeiger für Zählerstand.
 * 
 * @author Prof. Zwickmann
 */
public class Counter extends Text
{
	private int value;
	private String prefix;

	public Counter(String prefix) {
		this.prefix = prefix;
		updateText();
	}

	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			updateText();
		}
	}

	public int getValue() {
		return this.value;
	}

	private void updateText() {
		setText(prefix + value);
	}
}
