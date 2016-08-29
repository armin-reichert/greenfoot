package org.zwickmann.greenfoot;

/**
 * Selector for boolean values.
 * 
 * @author Zwickmann
 */
public abstract class BooleanValueSelector extends ValueSelector<Boolean>
{
	public BooleanValueSelector(String label) {
		super(label);
	}

	@Override
	public int numValues() {
		return 2;
	}

	@Override
	public void selectValue(Boolean b) {
		selection = b ? 1 : 0;
	}

	@Override
	public Boolean getValue() {
		return selection == 1; // 0 means "false" */
	}

	@Override
	public String getValueDisplayText() {
		return selection == 1 ? "YES" : "NO";
	}
}
