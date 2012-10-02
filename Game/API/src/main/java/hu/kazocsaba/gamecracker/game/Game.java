package hu.kazocsaba.gamecracker.game;

/**
 * A game. This class is used to access the initial position, and it also provides I/O functions on the game objects:
 * the {@link Position positions}, {@link Move moves}, and {@link Transformation transformations}.
 *
 * @author Kazó Csaba
 */
public abstract class Game {
	/**
	 * Returns the initial position of the game.
	 *
	 * @return the initial position
	 */
	public abstract Position getInitialPosition();
}
