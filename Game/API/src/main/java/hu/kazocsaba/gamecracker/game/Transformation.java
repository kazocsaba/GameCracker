package hu.kazocsaba.gamecracker.game;

/**
 * A transformation that can be applied to positions and moves.
 * <p>
 * Implementations of this interface should be enumeration types.
 *
 * @author Kazó Csaba
 */
public interface Transformation {
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
	public Transformation inverse();
}
