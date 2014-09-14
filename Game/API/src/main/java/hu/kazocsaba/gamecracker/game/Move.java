package hu.kazocsaba.gamecracker.game;

/**
 * Represents a move of a game. From the perspective of the API, a move is only used in relation to
 * a position, so it does not need to contain any information beyond what is required to interpret
 * it in the context of a position.
 *
 * <p>Implementations of this interface should be immutable.
 *
 * @param <M> the concrete Move type
 * @param <T> the concrete Transformation type
 * @author Kazó Csaba
 */
public abstract class Move<M extends Move<M, T>, T extends Transformation<T>> {

  /**
   * Transforms this move with the specified transformation and returns the result.
   *
   * @param t the transformation to apply
   * @return the result of the transformation
   * @throws IllegalArgumentException if the argument is not a valid transformation for this game
   */
  public abstract M transform(T t);

  /**
   * Returns whether the specified object is "equal to" this one. Beside the general contract of the
   * equals method, it is required that if two moves {@code m1} and {@code m2} are equal, then for
   * any position {@code pos}:
   * <ol>
   * <li>if {@code m1} is valid in {@code pos}, then {@code m2} must also be valid, and they must
   * lead to equal positions: {@code pos.move(m1).equals(pos.move(m2))};</li>
   * <li>if {@code m1} is not valid in {@code pos}, then {@code m2} must also be invalid.</li>
   * </ol>
   *
   * @param obj the object with which to compare
   * @return true if this object is equal to the argument
   */
  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();
}
