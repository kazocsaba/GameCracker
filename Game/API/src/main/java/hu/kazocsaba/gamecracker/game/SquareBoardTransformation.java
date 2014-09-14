package hu.kazocsaba.gamecracker.game;

/**
 * A transformation that can be applied to a square board. Provides functions to apply the
 * transformation to the cells of a board of any size.
 *
 * @param <T> the concrete transformation type
 * @author Kazó Csaba
 */
public interface SquareBoardTransformation<
    T extends SquareBoardTransformation<T>> extends Transformation<T> {

  /**
   * Transforms a point and returns the x coordinate of the result. A {@code (size x size)} board is
   * assumed where the coordinates are in the range {@code [0, size-1]}.
   *
   * @param x the x coordinate of the point
   * @param y the y coordinate of the point
   * @param size the size of the board
   * @return the x coordinate of the result of the transformation
   */
  public int transformX(int x, int y, int size);

  /**
   * Transforms a point and returns the y coordinate of the result. A {@code (size x size)} board is
   * assumed where the coordinates are in the range {@code [0, size-1]}.
   *
   * @param x the x coordinate of the point
   * @param y the y coordinate of the point
   * @param size the size of the board
   * @return the x coordinate of the result of the transformation
   */
  public int transformY(int x, int y, int size);

}
