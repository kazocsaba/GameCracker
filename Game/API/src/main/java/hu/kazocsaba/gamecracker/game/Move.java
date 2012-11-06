package hu.kazocsaba.gamecracker.game;

/**
 * Represents a move of a game. From the perspective of the API, a move is only used in relation to a position, so it
 * does not need to contain any information beyond what is required to interpret it in the context of a position.
 * <p>
 * Implementations of this interface should be immutable.
 * 
 * @param <M> the concrete Move type
 * @param <T> the concrete Transformation type
 * @author Kazó Csaba
 */
public interface Move<M extends Move<M, T>, T extends Transformation<T>> {
	/**
	 * Transforms this move with the specified transformation and returns the result.
	 * 
	 * @param t the transformation to apply
	 * @return the result of the transformation
	 * @throws IllegalArgumentException if the argument is not a valid transformation for this game
	 */
	public M transform(T t);
}
