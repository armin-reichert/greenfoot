package org.zwickmann.greenfoot;

/**
 * Interface for drop actions.
 * 
 * @see {@link DraggableActor}
 * 
 * @author Zwickmann
 */
public interface DropAction
{
	/**
	 * Called when a drag-and-drop action from the given start position to the
	 * given end position is executed.
	 * 
	 * @param dragStartX
	 *          x-coordinate of drag-start-position
	 * @param dragStartY
	 *          y-coordinate of drag-start-position
	 * @param dropX
	 *          x-coordinate of drop-position
	 * @param dropY
	 *          y-coordinate of drop-position
	 */
	public void drop(int dragStartX, int dragStartY, int dropX, int dropY);

	/**
	 * @return <code>true</code> if the drop-action is enabled
	 */
	public boolean isEnabled();
}
