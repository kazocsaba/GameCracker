package hu.kazocsaba.gamecracker.game;

/**
 * A transformation that can be applied to positions and moves.
 * <p>
 * Implementations of this interface should be enumeration types.
 *
 * @param <T> the concrete transformation type
 * @author Kazó Csaba
 */
public interface Transformation<T extends Transformation<T>> {
	/**
	 * Returns whether this transformation is the identity. Transforming positions and moves with the identity
	 * transformation results in the same objects.
	 *
	 * @return {@code true} if this is the identity transformation
	 */
	public boolean isIdentity();

	/**
	 * Returns whether this transformation switches the players. If this function returns {@code true}, then for a
	 * position {@code pos}, {@code pos.transform(this).getStatus() == pos.getStatus().getOther()} will be {@code true},
	 * otherwise it will be {@code false}.
	 *
	 * @return {@code true} if this transformation switches the players
	 */
	public boolean isPlayerSwitching();
	
	/**
	 * Returns the inverse of this transformation. Applying a transformation on a position or a move, and then applying
	 * the inverse transformation on the result must return the original position or move.
	 * 
	 * @return the inverse of this transformation
	 */
	public T inverse();
	
	/**
	 * Returns the transformation that is the composition of this transformation and the argument. The composition is the
	 * transformation that is equivalent to applying this transformation, and then applying {@code trans} to the result.
	 * 
	 * @param trans a transformation
	 * @return the composition of this transformation and the argument
	 */
	public T compose(T trans);
}
