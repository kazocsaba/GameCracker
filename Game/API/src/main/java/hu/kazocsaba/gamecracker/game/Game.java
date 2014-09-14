package hu.kazocsaba.gamecracker.game;

/**
 * A game. This class is used to access the initial position, and it also provides I/O functions on
 * the game objects: the {@link Position positions}, {@link Move moves}, and
 * {@link Transformation transformations}.
 *
 * @param <P> the concrete position type
 * @param <M> the concrete move type
 * @param <T> the concrete transformation type
 * @author Kazó Csaba
 */
public abstract class Game<
    P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>> {

  /**
   * Returns the initial position of the game.
   *
   * @return the initial position
   */
  public abstract P getInitialPosition();

  /**
   * Returns the category function for computing the categories of this game's positions.
   *
   * @return a category function for this game
   */
  public abstract CategoryFunction<P> getCategoryFunction();

  /**
   * Returns the serializer for positions of this game.
   *
   * @return a position serializer for this game
   */
  public abstract PositionSerializer<P> getPositionSerializer();

  /**
   * Returns the serializer for transformations of this game.
   *
   * @return a transformation serializer for this game
   */
  public abstract TransformationSerializer<T> getTransformationSerializer();

  /**
   * Returns an upper limit to the number of valid moves in a position of a valid game. If a
   * position {@code pos} is reachable from the initial position via {@link Position#move(Move)},
   * then {@code pos.getMoves().size()} must not exceed the value returned by this function.
   *
   * @return the maximum number of possible moves in a valid position
   */
  public abstract int getMaxPossibleMoves();

  /**
   * Returns the name of this game.
   *
   * @return the name of this game
   */
  public abstract String getName();

  /**
   * Returns the move represented by the specified string. This function is able to parse at least
   * the {@code String}s returned by the {@code toString} functions of the game's moves.
   *
   * @param s the string to parse
   * @return the move represented by the argument
   * @throws IllegalArgumentException if the string cannot be interpreted as a move of this game
   */
  public abstract M parseMove(String s);

  @Override
  public String toString() {
    return getName();
  }

  /**
   * Creates a new graphical component for this game. The new component initially shows the
   * {@link #getInitialPosition() initial position} of this game.
   *
   * @return a new game component
   */
  public abstract GameComponent<P, M, T> createComponent();

  /**
   * Returns the identity of the transformation type corresponding to this game.
   *
   * @return the identity transformation
   */
  public abstract T getIdentityTransformation();
}
