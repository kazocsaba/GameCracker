package hu.kazocsaba.gamecracker.game;

/**
 * A mapping between a game position and a category. Similar to a hash code, the category is a number that aids the lookup
 * of positions. If two positions are related with a transformation, then their categories must be the same.
 * <p>
 * Implementations must be immutable, therefore they are thread-safe.
 * 
 * @param <P> the concrete Position type
 * @author Kazó Csaba
 */
public abstract class CategoryFunction<P extends Position<P,?,?>> {
	/**
	 * Computes the category of a position. This function must guarantee that for any positions {@code p1} and {@code p2},
	 * if {@code p1.getTransformationTo(p2) != null}, then {@code category(p1) == category(p2)}.
	 * 
	 * @param position a position
	 * @return the category of the position
	 */
	public abstract long category(P position);
}
