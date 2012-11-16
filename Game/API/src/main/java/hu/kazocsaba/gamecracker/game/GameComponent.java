package hu.kazocsaba.gamecracker.game;

import java.awt.Component;

/**
 * A graphical representation for a game. It supports displaying a position and can allow the user to input a move.
 * <p>
 * The actual component can be accessed using {@link #getComponent()}.
 * <p>
 * Functions of this class should only be called on the Event Dispatch Thread (EDT). If a {@code MoveReceiver}
 * is registered, its {@code onMove} function is also only called on the EDT, and it is safe to alter the state of
 * the game component from this callback function.
 * @author Kazó Csaba
 */
public abstract class GameComponent<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> {
	/**
	 * Listener interface for receiving moves input by the user.
	 * 
	 * @param <M> the move type
	 * @param <T> the transformation type
	 */
	public interface MoveReceiver<M extends Move<M,T>, T extends Transformation<T>> {
		/**
		 * Called when the user enters a move through the component. It is not guaranteed that the move is legal in the
		 * current position.
		 * 
		 * @param move a move entered by the user
		 */
		public void onMove(M move);
	}
	/**
	 * Sets the position displayed by this component.
	 * 
	 * @param position the new position to display
	 * @throws NullPointerException if {@code position} is {@code null}
	 */
	public abstract void setPosition(P position);
	
	/**
	 * Returns the current position displayed by this component.
	 * 
	 * @return  the position of this component
	 */
	public abstract P getPosition();
	
	/**
	 * Returns the actual component that can be added to the user interface.
	 * 
	 * @return the GUI component
	 */
	public abstract Component getComponent();
	
	/**
	 * Sets the object that receives moves from this component. If {@code receiver} is {@code null}, then move input
	 * is disabled; otherwise the user is allowed to indicate moves through this component. These actions do not alter
	 * the current position shown in the component, and are not necessarily legal.
	 * 
	 * @param receiver the listener to receive moves, or {@code null} to disable move input
	 */
	public abstract void setMoveReceiver(MoveReceiver<M,T> receiver);
}
