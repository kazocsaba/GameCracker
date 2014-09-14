package hu.kazocsaba.gamecracker.game;

import java.util.Iterator;

/**
 * A sequence of valid moves from an initial position (not necessarily the {@link Game#getInitialPosition() game's
 * initial position}).
 *
 * @param <P> the concrete position type
 * @param <M> the concrete move type
 * @param <T> the concrete transformation type
 * @author Kazó Csaba
 */
public interface Match<
    P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>>
    extends Iterable<Match.Point<P, M, T>> {

  /**
   * A point in the history of a match. Each point corresponds to a position, and identifies the
   * move made from that position, except for the last point, where the move is {@code null}.
   *
   * <p>Instances of this class can be obtained from {@link Match#iterator()}, and are only valid
   * for the duration of the iteration. The behavior of a match point is undefined if the match has
   * been modified after the point is returned by the iterator.
   *
   * @param <P> the concrete Position type
   * @param <M> the concrete Move type
   * @param <T> the concrete Transformation type
   */
  public interface Point<
      P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>> {

    /**
     * Returns the position at this point.
     *
     * @return the position
     */
    public P getPosition();

    /**
     * Returns the move made from this position, or {@code null} if this is the current position of
     * the match.
     *
     * @return the move, or {@code null} if this is the last point of the match
     */
    public M getMove();
  }

  /**
   * Returns the current (last) position of the match. This position is never null; if the match
   * contains no moves, then the initial position is returned.
   *
   * @return the current position
   */
  public P getPosition();

  /**
   * Applies a move in the current position, and updates the match accordingly.
   *
   * @param move the move to make
   * @throws IllegalArgumentException if {@code move} is not one of the moves returned by
   * {@code getPosition().getMoves()}
   */
  public void move(M move);

  /**
   * Applies a move in the current position, and updates the match accordingly. This method behaves
   * equivalently to {@code move(getMove(moveIndex))}.
   *
   * @param moveIndex the index of the move to make
   */
  public void move(int moveIndex);

  /**
   * Undoes the last move, returning the match to the previous position.
   *
   * @throws IllegalStateException if the match contains no moves ({@code getLength() == 0})
   */
  public void back();

  /**
   * Returns the number of moves in this match.
   *
   * @return the number of moves
   */
  public int getLength();

  /**
   * Returns the number of possible moves in the current position. The return value is equal to
   * {@code getPosition().getMoves().size()}.
   *
   * @return the number of possible moves in the current position of the match
   */
  public int getMoveCount();

  /**
   * Returns a specified possible move in the current position. The return value is equal to
   * {@code getPosition().getMoves().get(index)}.
   *
   * @param index the index of the move
   * @return the specified move
   * @throws IndexOutOfBoundsException if the index is not valid
   */
  public M getMove(int index);

  /**
   * Returns an unmodifiable iterator over the history of the match. The iterator is never empty:
   * the first element corresponds to the initial position of the match, and the last element
   * contains the current position.
   *
   * @return an iterator over the match
   */
  @Override
  public Iterator<Point<P, M, T>> iterator();
}
