package org.zwickmann.greenfoot;

/**
 * UI element for selecting from a list of values.
 * 
 * @author Zwickmann
 */
public abstract class ValueSelector<T> implements Menu.Item
{
	private static final Command CMD_PREV_VALUE = new Command("left", "Prev Value");
	private static final Command CMD_NEXT_VALUE = new Command("right", "Next Value");

	private final T[] values;
	private final String label;
	protected int selection;

	/**
	 * Creates a selector with the given label text.
	 * 
	 * @param label
	 *          the label text
	 */
	@SuppressWarnings("unchecked")
	public ValueSelector(String label, T... values) {
		this.label = label;
		this.values = values;
	}

	public void onKeyPress(String key) {
		if (CMD_NEXT_VALUE.matches(key)) {
			next();
		} else if (CMD_PREV_VALUE.matches(key)) {
			prev();
		}
	}

	/**
	 * @return the label of the selector
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the current value of the selector
	 */
	public T getValue() {
		return values[selection];
	}

	/**
	 * @return the display text for the current value
	 */
	public String getValueDisplayText() {
		return String.valueOf(getValue());
	}

	/**
	 * @return the number of editable values
	 */
	public int numValues() {
		return values.length;
	}

	/**
	 * @return the currently selected index
	 */
	public int getSelection() {
		return selection;
	}

	/**
	 * Sets the current selection index.
	 * 
	 * @param i
	 *          the new index
	 */
	public final void setSelection(int i) {
		if (0 <= i && i < numValues()) {
			selection = i;
			selectionChanged(getValue());
			return;
		}
		throw new IllegalArgumentException("Illegal selection value: " + i);
	}

	/**
	 * Sets the selection such that the given value is selected.
	 * 
	 * @param value
	 *          a possible value
	 */
	public void selectValue(T value) {
		for (int i = 0; i < values.length; ++i) {
			if (value.equals(values[i])) {
				selection = i;
				return;
			}
		}
		throw new IllegalArgumentException("Cannot select value: " + value);
	}

	protected abstract void selectionChanged(T newValue);

	private void next() {
		if (++selection == numValues()) {
			selection = 0;
		}
		setSelection(selection);
	}

	public void prev() {
		if (--selection == -1) {
			selection = numValues() - 1;
		}
		setSelection(selection);
	}

	@Override
	public String getItemText(boolean active) {
		StringBuilder s = new StringBuilder();
		if (active) {
			s.append("[ ");
		}
		s.append(getLabel()).append(": ").append(getValueDisplayText());
		if (active) {
			s.append(" ]");
		}
		return (s.toString());
	}

}
